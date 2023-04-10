package com.dplot.admin.service.adjust;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

import java.util.List;

public interface AdminPartnersAdjustService {
    void partnersCalculateProcess(SOMap param);

    SOMap selectAdjustList(SOMap param) throws Exception;

    SOMap updateCalcStatus(SOMap param) throws Exception;

    SOMap reCalculate(SOMap param) throws Exception;

    SOMap selectAdjustCsList(SOMap param) throws Exception;

    SOMap selectAdjustDetailList(SOMap param) throws Exception;

    SOMap updatePaymentDate(SOMap param) throws Exception;

    SOMap selectPayCompareList(SOMap param) throws Exception;

    List<SOMap> calcExcelDownList(SOMap param) throws Exception;

	SOMap updateCalcCfm(SOMap param) throws Exception;
}
