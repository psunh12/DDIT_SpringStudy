package kr.or.ddit.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ItemVO {
	private int itemId;
	private String itemName;
	private int price;
	private String description;
	private String pictureUrl;
	
	//상품이미지 파일 객체
	private MultipartFile uploadFile;
}
