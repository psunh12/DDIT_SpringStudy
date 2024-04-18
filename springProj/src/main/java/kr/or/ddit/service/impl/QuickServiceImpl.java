package kr.or.ddit.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.mapper.QuickMapper;
import kr.or.ddit.service.QuickService;
import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.Card2VO;
import kr.or.ddit.vo.CardVO;
import kr.or.ddit.vo.Likes2VO;
import kr.or.ddit.vo.LikesVO;
import kr.or.ddit.vo.QuickAttachVO;
import kr.or.ddit.vo.QuickVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuickServiceImpl implements QuickService {
	@Autowired
	String uploadFolder;
	
	@Autowired
	QuickMapper quickMapper;

	@Transactional
	@Override
	public int createPost(QuickVO quickVO) {
		//QuickVO[emailAdres=test@test.com,password=java,registerId=null,
		//		  registDt=null,updaterId=null,registDt=null, uploadFile=파일객체]
		quickVO.setRegisterId("admin");
		//QuickVO[emailAdres=test@test.com,password=java,registerId=admin,
		//		  registDt=null,updaterId=null,registDt=null, uploadFile=파일객체]
		
		int result = this.quickMapper.createPost(quickVO);
		
		File uploadPath = new File(uploadFolder,getFolder());
		
		//연월일 폴더 생성 실행
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		//원본 파일명
		String uploadFileName = "";
		//파일 크기 (byte(1) short(2) int(4) long(8))
		long   size = 0;
		//MIME타입 
		String mime = "";
		//ATTACH 테이블의 SEQ컬럼을 위한 카운터변수
		int seq = 1;
		
		MultipartFile[] uploadFile = quickVO.getUploadFile();
		
		//스프링 파일 객체 배열로부터 스프링 파일 객체를 하나씩 가져와보자
		//MultipartFile[] uploadFile
		for(MultipartFile multipartFile : uploadFile) {
			log.info("--------------");
			log.info("원본 파일명 : " + multipartFile.getOriginalFilename());
			log.info("파일 크기    : " + multipartFile.getSize());
			log.info("MIME타입  : " + multipartFile.getContentType());
			
			uploadFileName = multipartFile.getOriginalFilename();
			size = multipartFile.getSize();
			mime = multipartFile.getContentType();
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			//File객체 설계(어디로 복사할것인지? 경로)
			File saveFile = new File(uploadFolder+"\\"+getFolder(),uploadFileName);
			
			try {
				//파일 복사 실행
				//파일객체.복사하겠다To(saveFile)
				multipartFile.transferTo(saveFile);
				
				//ATTACH테이블 insert
				QuickAttachVO quickAttachVO = new QuickAttachVO();
				quickAttachVO.setItemId(quickVO.getEmailAdres());
				quickAttachVO.setSeq(seq++);
				//웹경로 : / + 연/월/일 + / + uuid_원본파일명
				/*
				 .replace("대상문자열(정규식불가)","바꿀문자")
				    replace("\\","/") : 역슬러시 두개를 슬러시로 바꿈
				 .replaceAll("대상문자열(정규식반영)","바꿀문자")
				 	replaceAll("\\","/") : 역슬러시 한개를 슬러시로 바꿈
				 	            \\  : escape 정규식 \
				 	            \" : "
				 */
				quickAttachVO.setPictureUrl("/" + getFolder().replace("\\", "/") + "/" 
						+ uploadFileName);
				quickAttachVO.setPictureSize(size);
				quickAttachVO.setPictureType(mime);
				log.info("quickAttachVO : " + quickAttachVO);
				
				result += this.quickMapper.insertQuickAttach(quickAttachVO);//자식테이블 N
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
			}
		}//end for
		
		//CARD 테이블에 insert
		/*
		cardVOList=[
			 CardVO(no=1, validMonth=20240207, emailAdres=null), 
			 CardVO(no=2, validMonth=20240307, emailAdres=null)
		 ]
		 */		
		List<CardVO> cardVOList = quickVO.getCardVOList();
		List<CardVO> cardVOList2 = new ArrayList<CardVO>();
		
		for(CardVO cardVO : cardVOList) {
			//CardVO(no=1, validMonth=20240207, emailAdres=test@test.com)
			cardVO.setEmailAdres(quickVO.getEmailAdres());//부모의 기본키 데이터 -> 자식의 외래키 데이터
			//CARD 테이블에 insert
//			result += this.quickMapper.insertCard(cardVO);
			cardVOList2.add(cardVO);
		}
		//CARD 테이블에 insert all
		result += this.quickMapper.insertCard(cardVOList2);
		
		//LIKES 테이블에 insert
		/*
		likesVOList=[
			 LikesVO(likesCode=null, likesTitle=운동, likesCont=탁구, emailAdres=null), 
			 LikesVO(likesCode=null, likesTitle=독서, likesCont=추리소설, emailAdres=null)
		 ]
		 */
		List<LikesVO> likesVOList = quickVO.getLikesVOList();
		List<LikesVO> likesVOList2 = new ArrayList<LikesVO>();
		
		for(LikesVO likesVO : likesVOList) {
			// LikesVO(likesCode=null, likesTitle=운동, likesCont=탁구, emailAdres=test@test.com)
			likesVO.setEmailAdres(quickVO.getEmailAdres());//부모의 기본키 데이터 -> 자식의 외래키 데이터
			
			likesVOList2.add(likesVO);
		}
		//LIKES 테이블에 insert all
		result += this.quickMapper.insertLikes(likesVOList2);
		
		return result;
	}
	
	//연/월/일 폴더 생성
	public String getFolder() {
		//2024-01-30 형식(format) 지정
		//간단한 날짜 형식
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//날짜 객체 생성(java.util 패키지)
		Date date = new Date();
		//2024-01-30
		String str = sdf.format(date);
		//2024-01-30 -> 2024\\01\\30
		return str.replace("-", File.separator);
	}
	
	//이미지인지 판단. 썸네일은 이미지만 가능하므로..
	public boolean checkImageType(File file) {
		//MIME(Multipurpose Internet Mail Extensions) : 문서, 파일 또는 바이트 집합의 성격과 형식. 표준화
		//MIME 타입 알아냄. .jpeg / .jpg의 MIME(ContentType)타입 : image/jpeg
		String contentType;
		try {
			contentType = Files.probeContentType(file.toPath());
			log.info("contentType : " + contentType);
			//image/jpeg는 image로 시작함->true
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//이 파일이 이미지가 아닐 경우
		return false;
	}

	//Quick 상세
	@Override
	public QuickVO detail(String emailAdres) {
		return this.quickMapper.detail(emailAdres);
	}

	@Override
	public int insertAllTest() {
		//기능(비즈니스 로직)을 Impl로 이동
		List<Card2VO> card2VOList 
			= new ArrayList<Card2VO>();
		
		Card2VO card2VO = new Card2VO();
		card2VO.setNo("3");
		card2VO.setValidMonth("202403");
		card2VO.setEmailAdres("test2@test.com");
		
		card2VOList.add(card2VO);
		
		card2VO = new Card2VO();
		card2VO.setNo("4");
		card2VO.setValidMonth("202404");
		card2VO.setEmailAdres("test2@test.com");
		
		card2VOList.add(card2VO);
		
		int result = this.quickMapper.insertAllTest(card2VOList);
		
		//LIKES2 테이블에 insert all
		List<Likes2VO> likes2VOList = new ArrayList<Likes2VO>();
		
		Likes2VO likes2VO = new Likes2VO();
		likes2VO.setLikesCode("LKS004");
		likes2VO.setLikesTitle("운동");
		likes2VO.setLikesCont("농구");
		likes2VO.setEmailAdres("test2@test.com");
		likes2VOList.add(likes2VO);
		
		likes2VO = new Likes2VO();
		likes2VO.setLikesCode("LKS005");
		likes2VO.setLikesTitle("독서");
		likes2VO.setLikesCont("추리소설");
		likes2VO.setEmailAdres("test2@test.com");
		likes2VOList.add(likes2VO);
		
		result += this.quickMapper.insertLikes2(likes2VOList);
		
		return result;
	}
}





