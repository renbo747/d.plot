package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

@MapperInterface
public interface CalculateMapper {
    int insertCalculateForBatch(SOMap param);

    /**
     * 정산 내역 조회
     */
    List<SOMap> selectCalculateList(SOMap param);

    /**
     * 정산 내역 카운트
     */
    int selectCalculateListCount(SOMap param);

    int updateCalculate(SOMap param);

    int updatePaymentDate(SOMap param);

    List<SOMap> selectCalculateExcelDownList(SOMap param);

    List<SOMap> selectCalculateDashBoard(SOMap param);

	List<SOMap> selectCalculateDashBoardPopup(SOMap param);

	int updatePartCfmDate(SOMap param);
}
