package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품회원MAP Mapper(T_MEMBER_GOODS)
 * @fileName	: MemberGoodsMapper.java
 * @author		: JSK
 * @date		: 2021.11.16
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.16	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface MemberGoodsMapper {
	
	/**
	 * 상품별회원 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectGoodsMemberList(SOMap params);
	
	/**
	 * 상품별회원 추가
	 * @param params
	 * @return int
	 */
	int insertMemberGoods(SOMap params);
	
	/**
	 * 상품별회원 삭제
	 * @param params
	 * @return int
	 */
	int deleteMemberGoods(SOMap params);
}
