package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.Card2VO;
import kr.or.ddit.vo.CardVO;
import kr.or.ddit.vo.Likes2VO;
import kr.or.ddit.vo.LikesVO;
import kr.or.ddit.vo.QuickAttachVO;
import kr.or.ddit.vo.QuickVO;

public interface QuickMapper {

	public int createPost(QuickVO quickVO);

	public int insertQuickAttach(QuickAttachVO quickAttachVO);

	//Quick 상세
	public QuickVO detail(String emailAdres);

	//CARD테이블에 insert
	public int insertCard(List<CardVO> cardVOList);

	//LIKES 테이블에 insert
	public int insertLikes(List<LikesVO> likesVOList);

	
	public int insertAllTest(List<Card2VO> card2voList);

	public int insertLikes2(List<Likes2VO> likes2voList);

}
