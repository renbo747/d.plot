package com.dplot.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;
import com.dplot.security.model.Banner;

/**
 * 
 * @FileName : BannerMapper.java
 * @Project : datapick_api
 * @Date : 2021. 11. 15. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 11. 15.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface BannerMapper {
	
	/**
	 * 배너 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectBannerList(SOMap params);
	
	/**
	 * Select banner list.
	 *
	 * @param params the params
	 * @return the array list
	 */
	ArrayList<SOMap> selectBannerListToSOMap(SOMap params);
	
	/**
	 * Select banner list mail.
	 *
	 * @param params the params
	 * @return the array list
	 */
	// ArrayList<SOMap> selectBannerListMail(SOMap params);
	ArrayList<Map<String, Object>> selectBannerListMail(Map<String, Object> params);
	
	SOMap selectBanner(SOMap params);
	
	int selectBannerCount(SOMap params);
	
	int updateBanner(Banner banner);
	
	int selectBannerMaxSort(SOMap params);
	
	int insertBanner(Banner banner);
	
	int deleteBanner(Banner banner);

	/**
	 * 메인배너 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMainBannerList(SOMap params);

	/**
	 * 메인배너 사용여부 별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectMainBannerListCnt(SOMap params);

	/**
	 * 메인배너 저장
	 * @param params
	 * @return
	 */
	int insertMainBanner(SOMap params);

	/**
	 * 메인배너 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectMainBannerDetail(SOMap params);

	/**
	 * 배너 수정
	 * @param params
	 * @return
	 */
	int updateMainBanner(SOMap params);

	/**
	 * 메인배너 기간에 따른 노출 수량 체크
	 * @param params
	 * @return
	 */
	int checkBannerCnt(SOMap params);
	
	/**
	 * 메인배너 정렬순서 변경
	 * @param params
	 * @return
	 */
	int updateMainBannerSortNum(SOMap params);
	
	/**
	 * 배너 전체 정렬순서 변경
	 * @param params
	 * @return
	 */
	int updateAllSortNum(SOMap params);

	/**
	 * 리스트로 수정 시 수량체크
	 * @param params
	 * @return
	 */
	int checkBannerCntList(SOMap params);

	/**
	 * 전시 shop 메인 베너 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontMainBanner(SOMap param);
}
