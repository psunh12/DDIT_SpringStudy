package kr.or.ddit.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Item2VO {
	private int itemId;
	private String itemName;
	private int price;
	private String description;
	private String pictureUrl;
	private String pictureUrl2;
	
	//파일객체
	private MultipartFile uploadFile;
	private MultipartFile uploadFile2;
}


