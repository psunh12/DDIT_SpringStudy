package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

@Data
public class AttachVO {
	private int itemId;
	private int seq;
	private String pictureUrl;
	private long   pictureSize;
	private String pictureType;
	private Date regDate;
}
