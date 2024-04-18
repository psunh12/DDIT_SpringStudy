package kr.or.ddit.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.service.ItemService;
import kr.or.ddit.vo.Item2VO;
import kr.or.ddit.vo.Item3VO;
import kr.or.ddit.vo.ItemVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

//자바빈 등록 및 관리
@Slf4j
@RequestMapping("/item")
@Controller
public class ItemController {
	
	//DI(의존성 주입) / IoC(제어의 역전)
	@Autowired
	String uploadFolder;
	
	@Autowired
	ItemService itemService;
	
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("item3VO", new Item3VO());
	}
	
	/* 
	요청URI : /item/register
	요청파라미터 : 
	요청방식 : get
	*/
	@GetMapping("/register")
	public String register() {
		//forwarding
		 HttpServletRequest request = 
			        ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		getClientIp(request);
		
		return "item/register";
	}
	
	
	public String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("X-Real-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("X-RealIP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("REMOTE_ADDR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
	
	
	/* 
	요청URI : /item/registerPost
	요청파라미터 : {itemName=삼성태블릿,price=120000,description=쓸만함,uploadFile=파일객체}
	요청방식 : post
	*/
	@PostMapping("/registerPost")
	public String registerPost(ItemVO itemVO) {
		//ItemVO[itemId=0,itemName=삼성태블릿,price=120000,description=쓸만함
		//	     pictureUrl=null,uploadFile=파일객체]
		log.info("itemVO : " + itemVO);
		//스프링 파일 객체
		MultipartFile multipartFile = itemVO.getUploadFile();
		
		log.info("이미지 파일 명 : " + multipartFile.getOriginalFilename());
		log.info("이미지 크기 : " + multipartFile.getSize());
		//MIME(Multipurpose Internet Mail Extensions) : 문서, 파일 또는 바이트 집합의 성격과 형식. 표준화
		// .jpg / .jpeg의 MIME 타입 : image/jpeg
		log.info("MIME 타입 : " + multipartFile.getContentType());
		
		//String uploadFolder = "C:\\eGovFrameDev-3.10.0-64bit\\workspace\\springProj\\src\\main\\webapp\\resources\\upload";
		
		//연월일 폴더 생성 설계
		//...\\upload \\ 2024\\01\\30
		File uploadPath = new File(uploadFolder,getFolder());
		
		//연월일 폴더 생성 실행
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		String uploadFileName = multipartFile.getOriginalFilename();
		
		//같은 날 같은 이미지 업로드 시 파일 중복 방지 시작----------------
		//java.util.UUID => 랜덤값 생성
		UUID uuid = UUID.randomUUID();
		//원래의 파일 이름과 구분하기 위해 _를 붙임(sdafjasdlfksadj_개똥이.jpg)
		uploadFileName = uuid.toString() + "_" + uploadFileName;
		//같은 날 같은 이미지 업로드 시 파일 중복 방지 끝----------------
		
		//설계
		// , : \\
		//uploadFolder : ...upload\\2024\\01\\30 + \\ + 개똥이.jpg
		File saveFile = new File(uploadPath, uploadFileName);
		
		try {
			//파일 복사 실행(설계대로)
			//스프링파일객체.transferTo(설계)
			multipartFile.transferTo(saveFile);
			
			//썸네일 처리
			//이미지인지 체킹
			if(checkImageType(saveFile)) {//이미지라면
				//설계
				FileOutputStream thumbnail = new FileOutputStream(
					new File(uploadPath, "s_"+uploadFileName)
				);
				//썸네일 생성
				Thumbnailator.createThumbnail(multipartFile.getInputStream(),
						thumbnail,100,100);
				thumbnail.close();
			}
			
			//ITEM 테이블에 반영
			//ItemVO[itemId=0,itemName=삼성태블릿,price=120000,description=쓸만함
			//	     pictureUrl=null,uploadFile=파일객체]
			itemVO.setItemId(0);//자동 데이터 생성
			//웹경로
			//getFolder().replace("\\", "/") : 2024/01/30
			// /2024/01/30/sdaflkfdsaj_개똥이.jpg
			itemVO.setPictureUrl(
					"/" + getFolder().replace("\\", "/") + "/" 
					+ uploadFileName
					);
			log.info("registerPost->itemVO : " + itemVO);
			int result = this.itemService.registerPost(itemVO);
			log.info("registerPost->result : " + result);
			
			
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage());
		}
		
		//redirect : 새로운 URL 요청
		return "redirect:/item/detail?itemId="+itemVO.getItemId();
	}
	
	/*
	요청URI : /item/registerPost2
	요청파라미터 : {itemName=삼성태블릿,price=120000,description=쓸만함
			,uploadFile=파일객체,uploadFile2=파일객체}
	요청방식 : post
	*/
	@PostMapping("/registerPost2")
	public String registerPost2(Item2VO item2VO) {
		/*
		 Item2VO(itemId=0, itemName=삼성태블릿, price=120000, description=쓸만함
		 	   , pictureUrl=null, pictureUrl2=null
		 	   , uploadFile=파일객체, uploadFile2=파일객체
		 */
		log.info("registerPost2->item2VO : " + item2VO);
		
		//jsp input type="file" -> 스프링파일객체
		MultipartFile multipartFile1 = item2VO.getUploadFile();
		MultipartFile multipartFile2 = item2VO.getUploadFile2();
		
		//스프링파일객체의 순수한 파일명 추출
		String uploadFileName1 = multipartFile1.getOriginalFilename();
		String uploadFileName2 = multipartFile2.getOriginalFilename();
		
		File uploadPath = new File(uploadFolder,getFolder());
		
		//연월일 폴더 생성 실행
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		//같은 날 같은 이미지 업로드 시 파일 중복 방지 시작----------------
		//java.util.UUID => 랜덤값 생성
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		//같은 날 같은 이미지 업로드 시 파일 중복 방지 끝----------------
		
		File saveFile = new File(uploadPath,uuid1 + "_" + uploadFileName1);
		File saveFile2 = new File(uploadPath,uuid2 + "_" + uploadFileName2);
		
		//스프링파일객체.복사(파일타입 객체)
		try {
			multipartFile1.transferTo(saveFile);
			multipartFile2.transferTo(saveFile2);
			
			//ITEM2테이블에 insert
			item2VO.setPictureUrl("/" 
					+ getFolder().replaceAll("\\", "/") + "/" 
					+ uuid1 + "_" + uploadFileName1);
			item2VO.setPictureUrl2("/" 
					+ getFolder().replaceAll("\\", "/") + "/" 
					+ uuid1 + "_" + uploadFileName2);
//			Item2VO(itemId=0, itemName=삼성태블릿, price=120000, description=쓸만함
//				 	   , pictureUrl=null, pictureUrl2=null
//				 	   , uploadFile=파일객체, uploadFile2=파일객체)
			int result = this.itemService.registerPost2(item2VO);
			log.info("registerPost2->result : " + result);
			
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage());
		}
		
		//2개의 파일을 업로드하고
		//ITEM2 테이블에 insert해보자
		
		//redirect : url 재요청
		return "redirect:/item/detail2?itemId="+item2VO.getItemId();
	}
	
	/* 
	요청URI : /item/registerPost3
	요청파라미터 : {itemName=삼성태블릿,price=120000,description=쓸만함
			   ,uploadFile=파일객체들}
	요청방식 : post
	*/
	@PostMapping("/registerPost3")
	public String registerPost3(Item3VO item3VO) {
		/*
		Item3VO(itemId=0, itemName=삼성태블릿, price=120000, description=쓸만함, 
		uploadFile=[파일객체1, 파일객체2, 파일객체3], attachVOList=null)
		 */
		log.info("registerPost3->item3VO : " + item3VO);
		
		File uploadPath = new File(uploadFolder,getFolder());
		
		//연월일 폴더 생성 실행
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		int result = this.itemService.registerPost3(item3VO);
		log.info("registerPost3->result : " + result);
		
		return "redirect:/item/detail3?itemId="+item3VO.getItemId();
	}
	
	/*
	요청URI : /item/registerAjaxPost3
	요청파라미터(formData) : {itemName=삼성태블릿,price=120000,description=쓸만함
			   ,uploadFile=파일객체들}
	요청방식 : post
	
	!!formData로 오면 골뱅이RequestBody를 안씀
	*/
	@ResponseBody
	@PostMapping("/registerAjaxPost3")
	public int registerAjaxPost3(Item3VO item3VO) {
		/*
		Item3VO(itemId=0, itemName=삼성태블릿, price=120000, description=쓸만함, 
		uploadFile=[파일객체1, 파일객체2, 파일객체3], attachVOList=null)
		 */
		log.info("registerPost3->item3VO : " + item3VO);
		
		File uploadPath = new File(uploadFolder,getFolder());
		
		//연월일 폴더 생성 실행
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		int result = this.itemService.registerPost3(item3VO);
		log.info("registerPost3->result : " + result);
		
		return item3VO.getItemId();
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
	
	/*
	 요청URI : /item/detail3?itemId=3
	 요청파라미터 : itemId=3
	 요청방식 : get
	 */
	@GetMapping("/detail3")
	public String detail3(int itemId, Model model) {
		log.info("detail3->itemId : " + itemId);
		
		//resultMap 사용하기(ITEM3(1)과 ATTACH(N) 조인)
		Item3VO item3VO = this.itemService.detail3(itemId);
		log.info("detail3->item3VO : " + item3VO);
		
		model.addAttribute("item3VO",item3VO);
		
		//forwarding : jsp
		return "item/detail3";
	}
}







