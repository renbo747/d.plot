package com.dplot.admin.service.stats;

import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

public interface AdminStatsService {

    SOMap selectNewMemberStats(SOMap params) throws Exception;

    List<Map<String, Object>> selectNewMemberStatsExcel(SOMap params) throws Exception;

    SOMap selectCartStatsList(SOMap params) throws Exception;

    List<Map<String, Object>> selectCartStatsListExcel(SOMap params) throws Exception;

    SOMap selectProductStatList(SOMap params) throws Exception;

    List<Map<String, Object>> selectProductStatListExcel(SOMap params) throws Exception;

    SOMap selectCategoryStatList(SOMap params) throws Exception;

    List<Map<String, Object>> selectCategoryStatListExcel(SOMap params) throws Exception;

    SOMap selectClaimStatList(SOMap params) throws Exception;

    List<Map<String, Object>> selectClaimStatListExcel(SOMap params) throws Exception;

    SOMap selectSaleDateStatList(SOMap params) throws Exception;

    List<Map<String, Object>> selectSaleDateStatListExcel(SOMap params) throws Exception;

    SOMap selectAgeStatList(SOMap params) throws Exception;

    List<Map<String, Object>> selectAgeStatListExcel(SOMap params) throws Exception;

    SOMap selectDateStatList(SOMap params) throws Exception;

    List<Map<String, Object>> selectDateStatListExcel(SOMap params) throws Exception;
}
