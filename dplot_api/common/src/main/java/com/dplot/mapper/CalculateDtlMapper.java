package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

@MapperInterface
public interface CalculateDtlMapper {
    List<SOMap> selectCalculateDtlData(SOMap param);

    int insertCalculateDtlForBatch(SOMap param);

    int deleteCalculateDtl(SOMap param);

    List<SOMap> selectCalculateCsList(SOMap param);

    int selectCalculateCsListCount(SOMap param);

    List<SOMap> selectAdjustDetailList(SOMap param);

    int selectAdjustDetailListCount(SOMap param);

}
