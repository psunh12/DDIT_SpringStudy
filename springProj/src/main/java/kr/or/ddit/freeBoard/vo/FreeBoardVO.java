package kr.or.ddit.freeBoard.vo;

import lombok.Data;

@Data
public class FreeBoardVO {
	private int rnum;
	private int freeNo;
	private String freeTitle;
	private String freeWriter;
	private int parentNo;
}
