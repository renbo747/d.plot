package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : CartMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 10. 
 * @Author : KTH
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 10.			KTH			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ComCartMapper {
	
	/**
	 * 장바구니등록
	 *
	 * @param params the params
	 * @return the int
	 */
	int insertCart(SOMap cart);
	
	/**
	 * 장바구니 상품옵션별 조회
	 *
	 * @param params the params
	 * @return the int
	 */
	int selectCartByGoods(SOMap param);
	
	/**
	 * 장바구니 개수 조회
	 *
	 * @param params the params
	 * @return the int
	 */
	int selectCartCnt(SOMap param);
	
	/**
	 * 장바구니목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectCartList(SOMap param);
	
	/**
	 * 파라미터로 장바구니목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectCartListByItems(SOMap param);
	
	
	/**
	 * 오래된 장바구니목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectCartListByMonth(SOMap param);
	
	/**
	 * 장바구니 수량 수정
	 * @param param
	 * @return
	 */
	int updateCart(SOMap param);
	
	/**
	 * 장바구니 수량 상품옵션별 수정
	 * @param param
	 * @return
	 */
	int updateCartByGoods(SOMap param);
	
	/**
	 * 장바구니 회원번호수정
	 * @param param
	 * @return
	 */
	int updateSessId(SOMap param);
	
	/**
	 * 장바구니항목삭제
	 *
	 * @param params the params
	 * @return the int
	 */
	int deleteCartByCartIdx(SOMap param);
	
	/**
	 * 장바구니 전체삭제
	 *
	 * @param params the params
	 * @return the int
	 */
	int deleteCart(SOMap param);
	
	/**
	 * 장바구니 전체삭제
	 *
	 * @param params the params
	 * @return the int
	 */
	int deleteCartByMonth(SOMap param);

	/**
	 * 관리자 - 회원 장바구니 리스트
	 * @param param
	 * @return
	 */
	List<SOMap> selectCartListByAdmin(SOMap param);

	/**
	 * 관리자 - 회원 장바구니 리스트 카운트
	 * @param param
	 * @return
	 */
	int selectCartListByAdminCount(SOMap param);

	/**
	 *	관리자 - 장바구니 통계 리스트
	 */
	List<SOMap> selectCartStatsList(SOMap param);

	/**
	 *	관리자 - 장바구니 통계 리스트
	 */
	SOMap selectCartStatsListCount(SOMap param);
}
