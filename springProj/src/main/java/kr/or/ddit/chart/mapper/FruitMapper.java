package kr.or.ddit.chart.mapper;

import java.util.List;

import kr.or.ddit.chart.vo.FruitVO;

public interface FruitMapper {
	
	//<select id="fruitList" parameterType="String" resultType="fruitVO">
	public List<FruitVO> fruitList(String gubun);
	
}
