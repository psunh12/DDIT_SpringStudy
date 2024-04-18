package kr.or.ddit.vo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/*
 스프링 MVC는 Bean Validation 기능을 이용해 요청 파라미터 값이 바인딩된 도메인 클래스
 의 입력값 검증을 함
 */
//요청 파라미터 : {studId=a001..}
//=> 바인딩 
//studVO.setStudId("a001")
//자바빈 클래스(=도메인 클래스)
//PoJo(Plain(단순한, 원래의) oriented(지향) Java Object)
/*
Bean Validation이 제공하는 제약 애너테이션
- NotNull : 빈 값 체크(int타입)
- NotBlank : null 체크, trim후 길이가 0인지 체크(String타입)
- Size : 글자 수 체크
- Email : 이메일 주소 형식 체크
- Past : 오늘보다 과거 날짜(ex. 생일)
- Future : 미래 날짜 체크(ex. 예약일)
- AssertFalse : false 값만 통과 가능
- AssertTrue : true 값만 통과 가능
- DecimalMax(value=) : 지정된 값 이하의 실수만 통과 가능
- DecimalMin(value=) : 지정된 값 이상의 실수만 통과 가능
- Digits(integer=,fraction=) : 대상 수가 지정된 정수와 소수 자리수보다 적을 경우 통과 가능
- Future : 대상 날짜가 현재보다 미래일 경우만 통과 가능
- Past : 대상 날짜가 현재보다 과거일 경우만 통과 가능
- Max(value) : 지정된 값보다 아래일 경우만 통과 가능
- Min(value) : 지정된 값보다 이상일 경우만 통과 가능
- NotNull : null 값이 아닐 경우만 통과 가능
- Null : null일 겨우만 통과 가능
- Pattern(regex=, flag=) : 해당 정규식을 만족할 경우만 통과 가능
- Size(min=, max=) : 문자열 또는 배열이 지정된 값 사이일 경우 통과 가능
- Valid : 대상 객체의 확인 조건을 만족할 경우 통과 가능
*/
//자바빈 클래스
//PoJo위배
@Data
public class StudVO {
	private int    rnum;
	@NotBlank
	private String studId;
	@NotBlank(message="이름을 입력해주세요")
	private String studNm;
	@NotBlank(message="비밀번호를 입력해주세요")
	private String studPw;
	private String enabled;
	private String studDet;//학생 상세->STUD_DET
	private String[] hobby;//학생 취미들
	private String gender;//성별
	private String nationality;//국적
	private String postNum;//우편번호
	private String studAddress;//주소
	private String studAddress2;//상세주소
	
	//StudVO : StudAuthVO = 1 : N
	private List<StudAuthVO> studAuthVOList;
	
	//STUD : HOBBY = 1 : N
	//중첩된(Nested) 자바빈
	private List<HobbyVO> hobbyVOList;
	
	//일반 생성자
	public StudVO(String studId, String studNm, String studPw) {
		this.studId = studId;
		this.studNm = studNm;
		this.studPw = studPw;
	}
	//기본 생성자
	public StudVO() {}
	
}



