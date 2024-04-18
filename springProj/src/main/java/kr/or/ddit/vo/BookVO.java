package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

//자바빈 클래스
//1) 멤버변수(프로퍼티) 2) 기본생성자 3) getter/setter메소드
//@Data=> PoJo(Plain순수한 old Java object)에 위배
public class BookVO {
	private int bookId;
	private String title;
	private String category;
	private int price;
	private Date insertDate;
	
	public BookVO() {}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Override
	public String toString() {
		return "BookVO [bookId=" + bookId + ", title=" + title + ", category=" + category + ", price=" + price
				+ ", insertDate=" + insertDate + "]";
	}
	
	
}


