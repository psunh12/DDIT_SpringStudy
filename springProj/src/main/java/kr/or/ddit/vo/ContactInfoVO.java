package kr.or.ddit.vo;

import java.util.Date;

import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ContactInfoVO {
	private String ciCd;
	@NotBlank(message="이름을 입력해주세요")
	private String ciName;
	@NotBlank(message="이메일주소를 입력해주세요")
	@Email
	private String ciMail;
	@NotBlank(message="제목을 입력해주세요")
	private String ciSubj;
	private String ciImgUrl;
	private String ciMesg;
	//String타입인 "2024-02-17"->Date타입으로 변환
	//Future : 오늘 이후의 날짜 체킹
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Future(message="오늘 이후의 날짜만 선택 가능합니다.")
	private Date ciRegDt;
	private String registerId;
	private Date registDt;
	//스프링 파일객체
	private MultipartFile uploadFile;
}



