package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ComOrderGoodsMapper.java
 * @Project : datapick_api
 * @Date : 2022. 1. 26. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 1. 26.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ComOrderGoodsMapper {
	/**
	 * 주문상품 목록 조회
	 * @param dbParam
	 * @return
	 */
	List<SOMap> selectComOrderGoodsList(SOMap dbParam);
	
	/**
	 * 주문상품 적립금
	 * @param dbParam
	 * @return
	 */
	SOMap selectComOrderGoodsReserveAmt(SOMap dbParam);
	
	/**
	 * 주문상품 조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectComOrderGoods(SOMap dbParam);
	
	/**
	 * 주문상품정보 저장
	 * @param dbParam
	 * @return
	 */
	int insertComOrderGoods(SOMap dbParam);

	/**
	 * 마이페이지_주문상태별 갯수 조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectOrderGoodsCnt(SOMap dbParam);;

	/**
	 * 관리자-주문상품정보 조회
	 * @param dbParam
	 * @return SOMap
	 */
	SOMap selectComOrderGoodsInfo(SOMap dbParam);

	/**
	 * 관리자-주문상품목록 조회
	 * @param dbParam
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminOrderGoodsList(SOMap dbParam);

	/**
	 * 관리자-주문/클레임 상품목록 조회
	 * @param dbParam
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminOrderClaimGoodsList(SOMap dbParam);

	/**
	 * 관리자-공통 주문내역 조회
	 * @param dbParam
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminCommonOrderGoodsList(SOMap dbParam);
	
	/**
	 * FRONT > 1:1문의 > 주문상품
	 * @param dbParam
	 * @return
	 */
	List<SOMap> selectComOrderGoodsLIstDeliv(SOMap dbParam);

	/**
	 * ERP 전송용 주문 상품 데이터 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectOrderGoodsERPData(SOMap params);

	/**
	 * ERP 전송용 주문 상품 데이터 조회
	 * 배송시에 사용하고 해당 부분은 배송키(ORGDELIVIDX)로 주문상품을 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectComOrderGoodsReverseERPData(SOMap param);

	/**
	 * ERP 전달받은 직매입 주문 상품 확인.
	 */
	List<SOMap> selectComOrderGoodsExistCheck(SOMap param);

	/**
	 * ERP 전달받은 배송상태변경 처리 전 취소 CNT 체크
	 */
	SOMap selectComOrderGoodsCancelInfo(SOMap param);
}
