package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @author : ywm2004
 * @discription : 출석현황 Mapper (t_attend_detail)
 * @fileName : AttendDetailMapper.java
 * @date : 2021-12-29
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-29	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface AttendDetailMapper {

    /**
     * 출석 현황 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminAttendDetailPopupList(SOMap params);
}
