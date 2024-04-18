package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QuickVO {
	@NotBlank(message="이메일을 입력해주세요")
	@Email
	private String emailAdres;
	@NotBlank(message="비밀번호를 입력해주세요")
	private String password;
	private String registerId;
	private Date registDt;
	private String updaterId;
	private Date updateDt;	
	
	private MultipartFile[] uploadFile;
	
	//QUICK : QUICK_ATTACH = 1 : N
	private List<QuickAttachVO> quickAttachVOList;
	
	//QUICK : CARD = 1 : N
	private List<CardVO> cardVOList;
	
	//QUICK : LIKES = 1 : N
	private List<LikesVO> likesVOList;
}



