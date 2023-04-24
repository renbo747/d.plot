/**
 * 
 */
package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @FileName : WishMapper.java
 * @Project : datapick_api
 * @Date : 2021. 11. 26. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 11. 26.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
@MapperInterface
public interface WishMapper {
	
	/**
	 * 상품보관함 조회
	 *
	 * @param params the params
	 * @return the SOMap
	 */
	public SOMap selectWish(SOMap params);
	
	/**
	 * 상품보관함 등록
	 *
	 * @param params the params
	 * @return the integer
	 */
	public int insertWish(SOMap params);
	
	/**
	 * 상품보관함 수정
	 *
	 * @param params the params
	 * @return the integer
	 */
	public int updateWish(SOMap params);
	
	/**
	 * 찜한 상품 목록 조회
	 * @param dbParam
	 * @return
	 */
	public List<SOMap> selectWishList(SOMap dbParam);
	
	/**
	 * 찜한 상품 목록수 조회
	 * @param param
	 * @return
	 */
	public int selectWishListCnt(SOMap param);

	/**
	 * 찜한 상품 최근 30일에 등록된 날짜 목록 조회
	 * @param dbParam
	 * @return
	 */
	public List<SOMap> selectWishDaysList(SOMap dbParam);

	/**
	 * 관리자 - 회원 상세 찜한상품 리스트 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMemberWishListForAdmin(SOMap params);

	/**
	 * 관리자 - 회원 상세 찜한상품 리스트카운트 조회
	 * @param params
	 * @return
	 */
	int selectMemberWishListCountForAdmin(SOMap params);

	/**
	 * 찜한 상품 삭제 처리
	 * @param dbParam
	 * @return
	 */
	public int updateWishDel(SOMap param);

	/**
	 * 최근본 상품 위시 정보조회
	 * @param param
	 * @return
	 */
	public List<SOMap> selectRecentGoodsWish(SOMap param);

	/**
	 * 상품리스트의 좋아요 여부 조회
	 * @param param
	 * @return
	 */
	public List<SOMap> selectGoodsWish(SOMap param);
}
