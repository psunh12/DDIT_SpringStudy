package kr.or.ddit.service;

import kr.or.ddit.vo.Item2VO;
import kr.or.ddit.vo.Item3VO;
import kr.or.ddit.vo.ItemVO;

public interface ItemService {

	public int registerPost(ItemVO itemVO);

	public int registerPost2(Item2VO item2VO);

	public int registerPost3(Item3VO item3VO);

	public Item3VO detail3(int itemId);
	
}
