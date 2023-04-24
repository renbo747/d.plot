package com.dplot.front.service;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface BrandService {

	/**
	 *브랜드 목록 조회 
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public SOMap selectBrandList(SOMap param) throws Exception;

	/**
	 * 매거진 브랜드 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectMzBrandList(SOMap param) throws Exception;

	/**
	 * 매거진 브랜드 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectMzBrandDetail(SOMap param) throws Exception;

	/**
	 * 매거진 브랜드 좋아요 변경
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap changeWish(SOMap param) throws Exception;

	/**
	 * 상품목록 필터 브랜드 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectFilterBrandList(SOMap param) throws Exception;

}
