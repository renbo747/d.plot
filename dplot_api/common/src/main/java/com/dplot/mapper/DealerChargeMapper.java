package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

@MapperInterface
public interface DealerChargeMapper {
	void insertPartnershipCharge(SOMap param);
	void updatePartnershipCharge(SOMap param);
	void deletePartnershipCharge(SOMap param);
	List<SOMap> selectDealerCharge(SOMap param);
	List<SOMap> selectDealerChargeList(SOMap params);
	SOMap getChargerInfo(SOMap params);

	/*
	 * ERP 전송 파트너사 담당자 조회
	 */
	List<SOMap> selectDealerChargeListERP(SOMap params);
}
