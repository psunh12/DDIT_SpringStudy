package kr.or.ddit.chart.service.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.chart.mapper.FruitMapper;
import kr.or.ddit.chart.service.FruitService;
import kr.or.ddit.chart.vo.FruitVO;

@Service
public class FruitServiceImpl implements FruitService {
	
	@Autowired
	FruitMapper fruitMapper;
	
	/*
	 오버로딩   : 동일한 테이블에 같은 이름에 메소드를 사용할 수 있음(파라미터의 개수, 파라미터 타입이 달라야 함)
	 오버라이드 : 상속받은 부모의 메소드를 자식 메소드에서 재정의함
	 */
	@Override
	public JSONObject fruitList(String gubun) {
		/*
		FRUIT01	귤	35000	fruit
		FRUIT02	딸기	88000	fruit
		FRUIT03	레몬	16500	fruit
		 */		
		List<FruitVO> fruitVOList = this.fruitMapper.fruitList(gubun);
		
		JSONObject data = new JSONObject();
		//fruitVOList -> json 데이터로 변환
		//1. cols 배열에 넣기(상품명, 금액)
		/*
		"cols":[
		{"id":"","label":"상품명","pattern":"","type":"string"},
		{"id":"","label":"금액","pattern":"","type":"number"}
		],
		 */
		JSONObject col1 = new JSONObject();	//상품명
		JSONObject col2 = new JSONObject(); //금액
		
			JSONArray title = new JSONArray();
			col1.put("label", "상품명");
			col1.put("type", "string");
			col2.put("label", "금액");
			col2.put("type", "number");
			title.add(col1);
			title.add(col2);
		data.put("cols", title);
		
		//2. rows 배열에 넣기
		//{"c":[{"v":"귤"},{"v":35000}]},
		JSONArray body = new JSONArray();	//rows
		
		for(FruitVO fruitVO : fruitVOList) {
			JSONObject prodName = new JSONObject();
			prodName.put("v", fruitVO.getFruitNm());	//상품명
			JSONObject money = new JSONObject();
			money.put("v", fruitVO.getFruitAmt());//금액
			JSONArray row = new JSONArray();
			row.add(prodName);
			row.add(money);
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell);
		}//end for
		
		data.put("rows", body);
		
		return data;
	}
	
}



