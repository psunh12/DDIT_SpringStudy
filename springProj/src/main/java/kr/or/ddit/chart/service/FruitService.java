package kr.or.ddit.chart.service;

import java.util.List;

import org.json.simple.JSONObject;

import kr.or.ddit.chart.vo.FruitVO;

public interface FruitService {
	//메소드 시그니처
	public JSONObject fruitList(String gubun);
	
}
