package kr.or.ddit.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QuickAttachVO {
	private String itemId;
	private int seq;
	private String pictureUrl;
	private long pictureSize;
	private String pictureType;
	private Date regDate;
}
