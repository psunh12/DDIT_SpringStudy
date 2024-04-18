package kr.or.ddit.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.mapper.ContactInfoMapper;
import kr.or.ddit.service.ContactInfoService;
import kr.or.ddit.vo.ContactInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContactInfoServiceImpl implements ContactInfoService {
	
	//업로드 대상 경로
	@Inject
	String uploadFolder;
	
	@Inject
	ContactInfoMapper contactInfoMapper;

	//방문 정보 입력
	@Transactional
	@Override
	public int createPost(ContactInfoVO contactInfoVO) {
		log.info("contactInfoVO : " + contactInfoVO);
		/*
		 ContactInfoVO(ciCd=null, ciName=개똥이, ciMail=test@test.com, ciSubj=취업상담, 
		 	ciImgUrl=/2024/02/06/asdflk_개똥이.jpg, ciMesg=<figure class="image"><img src="http://localhost/resources/upload/b5cde164-ba71-434f-bcf4-8afd3e58fee9.jpg"></figure>, 
		 	ciRegDt=Sat Feb 17 00:00:00 KST 2024, registerId=null, registDt=null, 
		 	uploadFile=org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@42bed612)
		 */
		//파일업로드
		//연월일
		File uploadPath = new File(uploadFolder, getFolder());
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		//스프링파일객체
		MultipartFile multipartFile = contactInfoVO.getUploadFile();
		//원본 파일명
		String uploadFileName = multipartFile.getOriginalFilename();
		//UUID_파일명
		uploadFileName = UUID.randomUUID().toString()
				+ "_" + uploadFileName;
		//설계
		File saveFile = new File(uploadPath, uploadFileName);
		
		int result = 0;
		
		try {
			//파일복사 실행
			multipartFile.transferTo(saveFile);
			//웹경로
			contactInfoVO.setCiImgUrl(
					"/" + getFolder().replace("\\", "/") + "/" + uploadFileName
					);
			result = this.contactInfoMapper.createPost(contactInfoVO);
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage());
		}
		
		return result;
	}
	
	//방문정보 상세
	@Override
	public ContactInfoVO detail(String ciCd) {
		return this.contactInfoMapper.detail(ciCd);
	}
	
	//연/월/일 폴더 생성
	public String getFolder() {
		//2022-11-16 형식(format) 지정
		//간단한 날짜 형식
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//날짜 객체 생성(java.util 패키지)
		Date date = new Date();
		//2022-11-16
		String str = sdf.format(date);
		//2024-01-30 -> 2024\\01\\30
		return str.replace("-", File.separator);
	}
	
	//이미지인지 판단. 썸네일은 이미지만 가능하므로..
	public boolean checkImageType(File file) {
		//MIME(Multipurpose Internet Mail Extensions) : 문서, 파일 또는 바이트 집합의 성격과 형식. 표준화
		//MIME 타입 알아냄. .jpeg / .jpg의 MIME타입 : image/jpeg
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

}
