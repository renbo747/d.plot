package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface DealerDelivMapper {

	/*
	 * 파트너 반품 택배사 목록
	 */
	List<SOMap> selectPartnersDeliveryList(SOMap param) throws Exception;
	
	/*
	 * 수동반품시 전체 택배사 목록
	 */
	List<SOMap> selectPartnersDeliveryTemp(SOMap param) throws Exception;

	/*
	 * 반품택배사 목록 조회
	 */
	List<SOMap> selectRtnLogisList(SOMap param) throws Exception;
	
	/*
	 * 수동반품택배사 목록 조회
	 */
	List<SOMap> selectRtnManLogisList(SOMap param) throws Exception;

	/**
	 * 파트너 반품 택배사
	 */
	SOMap selectPartnersDelivery(SOMap param) throws Exception;

	/*
	 * 파트너 택배사 계약 서비스 신청 등록
	 */
	int insertDealerDeliv(SOMap param) throws Exception;

	/**
	 * 파트너 택배사 계약 서비스 신청 수정
	 */
	int updateDealerDeliv(SOMap param) throws Exception;
	
	/**
	 * 택배사 코드 조회(공통코드 사용)
	 * */
	String selectDeliveryCode(String logisticsCode);
}
