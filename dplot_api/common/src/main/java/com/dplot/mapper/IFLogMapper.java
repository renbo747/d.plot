package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

@MapperInterface
public interface IFLogMapper {

    int insertIfLogERPData(SOMap param);

    int updateIFLog(SOMap param);

    int updateIFLogDate(SOMap param);

    List<SOMap> selectIFLogList(SOMap param);

    int insertIfLogERPDataForReserveAll(SOMap param);
}
