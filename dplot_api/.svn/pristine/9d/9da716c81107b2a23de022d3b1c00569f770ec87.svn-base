package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 이벤트 출석 체크 테이블 Mapper (t_dadaevent_attend)
 * @fileName : DadaEventAttendMapper.java
 * @date : 2021-12-27
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-27	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface DadaEventAttendMapper {

    /**
     * 출석 체크 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminDadaEventAttendList(SOMap params);

    /**
     * 엑셀 다운로드
     * 
     * @param params
     * @return
     */
    List<Map<String, Object>> selectAdminDadaEventAttendExcelDownload(SOMap params);
}
