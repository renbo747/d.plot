package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

@MapperInterface
public interface MemberHistoryMapper {
    List<SOMap> selectMemberHistoryList(SOMap params) throws Exception;

    int insertMemberHistory(SOMap params) throws Exception;
}
