package com.dplot.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : RecomRewardMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 23. 
 * @Author : LCK
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 23.			LCK			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface RecomRewardMapper {

	/**
	 * 추천리워드 조회
	 * @param params
	 * @return
	 */
	SOMap selectRecomReward(SOMap params);

	/**
	 * 추천리워드 수정
	 * @param params
	 * @return
	 */
	int updateRecomReward(SOMap params);

	/**
	 * 추천리워드 등록
	 * @param params
	 * @return
	 */
	int insertRecomReward(SOMap params);

	/**
	 * 회원에게 지급된 리워드 정보
	 * @param param
	 * @return
	 */
	SOMap selectRewardPayInfo(SOMap param);

	/**
	 * 리워드 보상 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectUserRewardPayList(SOMap param);

	/**
	 * 리워드 보상 수 조회
	 * @param param
	 * @return
	 */
	int selectUserRewardPayListCnt(SOMap param);

	SOMap selectInviteInfo(SOMap param);
}
