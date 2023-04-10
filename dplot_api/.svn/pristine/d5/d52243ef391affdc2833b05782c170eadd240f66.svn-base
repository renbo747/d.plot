package com.dplot.admin.service.stats;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.ClaimGoodsMapper;
import com.dplot.mapper.ComCartMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminStatsServiceImpl extends MallBaseService implements AdminStatsService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ComCartMapper comCartMapper;

    @Autowired
    private ComOrderMapper comOrderMapper;

    @Autowired
    private ClaimGoodsMapper claimGoodsMapper;

    @Override
    public SOMap selectNewMemberStats(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        SOMap countResult = new SOMap();

        if(params.containsKey("type")){
            if("day".equals(params.get("type"))){
                result.put("list", memberMapper.selectNewMemberStatsByDay(params));
                countResult = memberMapper.selectNewMemberStatsByDayCount(params);
            } else if("week".equals(params.get("type"))){
                result.put("list", memberMapper.selectNewMemberStatsByWeek(params));
                countResult = memberMapper.selectNewMemberStatsByWeekCount(params);
            } else if("month".equals(params.get("type"))){
                result.put("list", memberMapper.selectNewMemberStatsByMonth(params));
                countResult = memberMapper.selectNewMemberStatsByMonthCount(params);
            }

            result.put("cnt", countResult.getOrDefault("cnt", "0"));
            result.put("total", countResult.getOrDefault("total", "0"));
            result.put("pc", countResult.getOrDefault("pc", "0"));
            result.put("mobile", countResult.getOrDefault("mobile", "0"));
            result.put("app", countResult.getOrDefault("app", "0"));
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectNewMemberStatsExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        List<SOMap> list = new ArrayList<>();

        params.put("siteid", cs.getStr("siteid"));

        SOMap countResult = new SOMap();
        Map<String, Object> map = new HashMap<>();
        if(params.containsKey("type")){
            if("day".equals(params.get("type"))){
                list = memberMapper.selectNewMemberStatsByDay(params);
                countResult = memberMapper.selectNewMemberStatsByDayCount(params);
            } else if("week".equals(params.get("type"))){
                list = memberMapper.selectNewMemberStatsByWeek(params);
                countResult = memberMapper.selectNewMemberStatsByWeekCount(params);
            } else if("month".equals(params.get("type"))){
                list = memberMapper.selectNewMemberStatsByMonth(params);
                countResult = memberMapper.selectNewMemberStatsByMonthCount(params);
            }

            map.put("joindate", "합계");
            map.put("total", countResult.getOrDefault("total", "0"));
            map.put("pc", countResult.getOrDefault("pc", "0"));
            map.put("mobile", countResult.getOrDefault("mobile", "0"));
            map.put("app", countResult.getOrDefault("app", "0"));

            result.add(map);
            result.addAll(list);
        }
        return result;
    }

    @Override
    public SOMap selectCartStatsList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = comCartMapper.selectCartStatsList(params);
        SOMap countInfo = comCartMapper.selectCartStatsListCount(params);

        result.put("list", list);
        result.put("listcount", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("cartcnt", (countInfo != null) ? countInfo.getStr("ordcnt") : "0");
        result.put("membercnt", (countInfo != null) ? countInfo.getStr("cnt") : "0");

        return result;
    }

    @Override
    public List<Map<String, Object>> selectCartStatsListExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));

        List<SOMap> list = comCartMapper.selectCartStatsList(params);
        SOMap countInfo = comCartMapper.selectCartStatsListCount(params);

        map.put("ranking", "합계");
        map.put("ordcnt", countInfo.getOrDefault("total", "0"));
        map.put("cnt", countInfo.getOrDefault("ordcnt", "0"));
        map.put("total", countInfo.getOrDefault("cnt", "0"));

        result.add(map);
        result.addAll(list);

        return result;
    }

    @Override
    public SOMap selectProductStatList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = comOrderMapper.selectStatsProductRankByList(params);
        SOMap countInfo = comOrderMapper.selectStatsProductRankByListCountInfo(params);

        result.put("total", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("frstordcnt", (countInfo != null) ? countInfo.getStr("frstordcnt") : "0");
        result.put("clmcnt", (countInfo != null) ? countInfo.getStr("clmcnt") : "0");
        result.put("recnt", (countInfo != null) ? countInfo.getStr("recnt") : "0");
        result.put("billing", (countInfo != null) ? countInfo.getStr("billing") : "0");
        result.put("price", (countInfo != null) ? countInfo.getStr("price") : "0");
        result.put("rtnamt", (countInfo != null) ? countInfo.getStr("rtnamt") : "0");
        result.put("orgamt", (countInfo != null) ? countInfo.getStr("orgamt") : "0");
        result.put("margin", (countInfo != null) ? countInfo.getStr("margin") : "0");
        result.put("marginper", (countInfo != null) ? countInfo.getStr("marginper") : "0");
        result.put("goodscpnamt", (countInfo != null) ? countInfo.getStr("goodscpnamt") : "0");
        result.put("list", list);
        return result;
    }

    @Override
    public List<Map<String, Object>> selectProductStatListExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> list = comOrderMapper.selectStatsProductRankByList(params);
        SOMap countInfo = comOrderMapper.selectStatsProductRankByListCountInfo(params);

        map.put("ranking", "합계");
        map.put("total", countInfo.getOrDefault("total", 0));
        map.put("frstordcnt", countInfo.getOrDefault("frstordcnt", "0"));
        map.put("clmcnt", countInfo.getOrDefault("clmcnt", "0"));
        map.put("recnt", countInfo.getOrDefault("recnt", "0"));
        map.put("billing", countInfo.getOrDefault("billing", "0"));
        map.put("price", countInfo.getOrDefault("price", "0"));
        map.put("rtnamt", countInfo.getOrDefault("rtnamt", "0"));
        map.put("orgamt", countInfo.getOrDefault("orgamt", "0"));
        map.put("margin", countInfo.getOrDefault("margin", "0"));
        map.put("marginper", countInfo.getOrDefault("marginper", "0"));
        map.put("goodscpnamt", countInfo.getOrDefault("goodscpnamt", "0"));

        result.add(map);
        result.addAll(list);

        return result;
    }

    @Override
    public SOMap selectCategoryStatList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = comOrderMapper.selectStatsCategoryRankByList(params);
        SOMap countInfo = comOrderMapper.selectStatsCategoryRankByListCountInfo(params);

        result.put("total", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("frstordcnt", (countInfo != null) ? countInfo.getStr("frstordcnt") : "0");
        result.put("clmcnt", (countInfo != null) ? countInfo.getStr("clmcnt") : "0");
        result.put("recnt", (countInfo != null) ? countInfo.getStr("recnt") : "0");
        result.put("billing", (countInfo != null) ? countInfo.getStr("billing") : "0");
        result.put("price", (countInfo != null) ? countInfo.getStr("price") : "0");
        result.put("rtnamt", (countInfo != null) ? countInfo.getStr("rtnamt") : "0");
        result.put("orgamt", (countInfo != null) ? countInfo.getStr("orgamt") : "0");
        result.put("margin", (countInfo != null) ? countInfo.getStr("margin") : "0");
        result.put("marginper", (countInfo != null) ? countInfo.getStr("marginper") : "0");
        result.put("goodscpnamt", (countInfo != null) ? countInfo.getStr("goodscpnamt") : "0");
        result.put("list", list);

        return result;
    }

    @Override
    public List<Map<String, Object>> selectCategoryStatListExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));

        List<SOMap> list = comOrderMapper.selectStatsCategoryRankByList(params);
        SOMap countInfo = comOrderMapper.selectStatsCategoryRankByListCountInfo(params);

        map.put("ranking", "합계");
        map.put("total", countInfo.getOrDefault("total", 0));
        map.put("frstordcnt", countInfo.getOrDefault("frstordcnt", "0"));
        map.put("clmcnt", countInfo.getOrDefault("clmcnt", "0"));
        map.put("recnt", countInfo.getOrDefault("recnt", "0"));
        map.put("billing", countInfo.getOrDefault("billing", "0"));
        map.put("price", countInfo.getOrDefault("price", "0"));
        map.put("rtnamt", countInfo.getOrDefault("rtnamt", "0"));
        map.put("orgamt", countInfo.getOrDefault("orgamt", "0"));
        map.put("margin", countInfo.getOrDefault("margin", "0"));
        map.put("marginper", countInfo.getOrDefault("marginper", "0"));
        map.put("goodscpnamt", countInfo.getOrDefault("goodscpnamt", "0"));

        result.add(map);
        result.addAll(list);

        return result;
    }

    @Override
    public SOMap selectClaimStatList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = claimGoodsMapper.selectStatsClaimRankByGoodsList(params);
        SOMap countInfo = claimGoodsMapper.selectStatsClaimRankByGoodsListCountInfo(params);

        result.put("list", list);
        result.put("total", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("frst", (countInfo != null) ? countInfo.getStr("frst") : "0");
        result.put("clm", (countInfo != null) ? countInfo.getStr("clm") : "0");
        result.put("cnt", (countInfo != null) ? countInfo.getStr("cnt") : "0");
        result.put("amt", (countInfo != null) ? countInfo.getStr("amt") : "0");

        return result;
    }

    @Override
    public List<Map<String, Object>> selectClaimStatListExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));

        List<SOMap> list = claimGoodsMapper.selectStatsClaimRankByGoodsList(params);
        SOMap countInfo = claimGoodsMapper.selectStatsClaimRankByGoodsListCountInfo(params);

        map.put("ranking", "합계");
        map.put("total", countInfo.getOrDefault("total", 0));
        map.put("frstordcnt", countInfo.getOrDefault("frst", "0"));
        map.put("clmcnt", countInfo.getOrDefault("clm", "0"));
        map.put("cnt", countInfo.getOrDefault("cnt", "0"));
        map.put("clmamt", countInfo.getOrDefault("amt", "0"));

        result.add(map);
        result.addAll(list);

        return result;
    }

    @Override
    public SOMap selectSaleDateStatList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = comOrderMapper.selectStatsSaleDateList(params);
        SOMap countInfo = comOrderMapper.selectStatsSaleDateListCountInfo(params);
//
        result.put("total", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("ordercnt", (countInfo != null) ? countInfo.getStr("ordercnt") : "0");
        result.put("salecnt", (countInfo != null) ? countInfo.getStr("salecnt") : "0");
        result.put("price", (countInfo != null) ? countInfo.getStr("price") : "0");
        result.put("pure", (countInfo != null) ? countInfo.getStr("pure") : "0");
        result.put("salepromoamt", (countInfo != null) ? countInfo.getStr("salepromoamt") : "0");
        result.put("goodscpnamt", (countInfo != null) ? countInfo.getStr("goodscpnamt") : "0");
        result.put("margin", (countInfo != null) ? countInfo.getStr("margin") : "0");
        result.put("rtnamt", (countInfo != null) ? countInfo.getStr("rtnamt") : "0");
        result.put("stamt", (countInfo != null) ? countInfo.getStr("stamt") : "0");
        result.put("delivamt", (countInfo != null) ? countInfo.getStr("delivamt") : "0");
        result.put("reserveamt", (countInfo != null) ? countInfo.getStr("reserveamt") : "0");
        result.put("epointamt", (countInfo != null) ? countInfo.getStr("epointamt") : "0");
        result.put("empreserveamt", (countInfo != null) ? countInfo.getStr("empreserveamt") : "0");
        result.put("realamt", (countInfo != null) ? countInfo.getStr("realamt") : "0");
        result.put("orgamt", (countInfo != null) ? countInfo.getStr("orgamt") : "0");
        result.put("list", list);

        return result;
    }

    @Override
    public List<Map<String, Object>> selectSaleDateStatListExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));

        List<SOMap> list = comOrderMapper.selectStatsSaleDateList(params);
        SOMap countInfo = comOrderMapper.selectStatsSaleDateListCountInfo(params);

        map.put("target", "합계");
        map.put("total", countInfo.getOrDefault("total", 0));
        map.put("ordercnt", countInfo.getOrDefault("ordercnt", "0"));
        map.put("salecnt", countInfo.getOrDefault("salecnt", "0"));
        map.put("price", countInfo.getOrDefault("price", "0"));
        map.put("pure", countInfo.getOrDefault("pure", "0"));
        map.put("salepromoamt", countInfo.getOrDefault("salepromoamt", "0"));
        map.put("goodscpnamt", countInfo.getOrDefault("goodscpnamt", "0"));
        map.put("margin", countInfo.getOrDefault("margin", "0"));
        map.put("rtnamt", countInfo.getOrDefault("rtnamt", "0"));
        map.put("stamt", countInfo.getOrDefault("stamt", "0"));
        map.put("delivamt", countInfo.getOrDefault("delivamt", "0"));
        map.put("reserveamt", countInfo.getOrDefault("reserveamt", "0"));
        map.put("epointamt", countInfo.getOrDefault("epointamt", "0"));
        map.put("empreserveamt", countInfo.getOrDefault("empreserveamt", "0"));
        map.put("realamt", countInfo.getOrDefault("realamt", "0"));
        map.put("orgamt", countInfo.getOrDefault("orgamt", "0"));

        result.add(map);
        result.addAll(list);

        return result;
    }

    @Override
    public SOMap selectAgeStatList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = comOrderMapper.selectAgeStatList(params);
        SOMap countInfo = comOrderMapper.selectAgeStatListCountInfo(params);
//
        result.put("total", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("humancnt", (countInfo != null) ? countInfo.getStr("humancnt") : "0");
        result.put("ordercnt", (countInfo != null) ? countInfo.getStr("ordercnt") : "0");
        result.put("pordercnt", (countInfo != null) ? countInfo.getStr("pordercnt") : "0");
        result.put("price", (countInfo != null) ? countInfo.getStr("price") : "0");
        result.put("unitamt", (countInfo != null) ? countInfo.getStr("unitamt") : "0");
        result.put("cnccnt", (countInfo != null) ? countInfo.getStr("cnccnt") : "0");
        result.put("exccnt", (countInfo != null) ? countInfo.getStr("exccnt") : "0");
        result.put("ramtcnt", (countInfo != null) ? countInfo.getStr("ramtcnt") : "0");
        result.put("rtncnt", (countInfo != null) ? countInfo.getStr("rtncnt") : "0");
        result.put("regcnt", (countInfo != null) ? countInfo.getStr("regcnt") : "0");
        result.put("smscnt", (countInfo != null) ? countInfo.getStr("smscnt") : "0");
        result.put("dorcnt", (countInfo != null) ? countInfo.getStr("dorcnt") : "0");
        result.put("rescnt", (countInfo != null) ? countInfo.getStr("rescnt") : "0");
        result.put("list", list);

        return result;
    }

    @Override
    public List<Map<String, Object>> selectAgeStatListExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));

        List<SOMap> list = comOrderMapper.selectAgeStatList(params);
        SOMap countInfo = comOrderMapper.selectAgeStatListCountInfo(params);

        map.put("target", "합계");
        map.put("total", countInfo.getOrDefault("total", 0));
        map.put("humancnt", countInfo.getOrDefault("humancnt", "0"));
        map.put("ordercnt", countInfo.getOrDefault("ordercnt", "0"));
        map.put("pordercnt", countInfo.getOrDefault("pordercnt", "0"));
        map.put("price", countInfo.getOrDefault("price", "0"));
        map.put("unitamt", countInfo.getOrDefault("unitamt", "0"));
        map.put("cnccnt", countInfo.getOrDefault("cnccnt", "0"));
        map.put("exccnt", countInfo.getOrDefault("exccnt", "0"));
        map.put("ramtcnt", countInfo.getOrDefault("ramtcnt", "0"));
        map.put("rtncnt", countInfo.getOrDefault("rtncnt", "0"));
        map.put("regcnt", countInfo.getOrDefault("regcnt", "0"));
        map.put("smscnt", countInfo.getOrDefault("smscnt", "0"));
        map.put("dorcnt", countInfo.getOrDefault("dorcnt", "0"));
        map.put("rescnt", countInfo.getOrDefault("rescnt", "0"));

        result.add(map);
        result.addAll(list);

        return result;
    }

    @Override
    public SOMap selectDateStatList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = comOrderMapper.selectDateStatList(params);
        SOMap countInfo = comOrderMapper.selectDateStatListCountInfo(params);

        result.put("total", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("ordercnt", (countInfo != null) ? countInfo.getStr("ordercnt") : "0");
        result.put("pordercnt", (countInfo != null) ? countInfo.getStr("pordercnt") : "0");
        result.put("price", (countInfo != null) ? countInfo.getStr("price") : "0");
        result.put("unitamt", (countInfo != null) ? countInfo.getStr("unitamt") : "0");
        result.put("cnccnt", (countInfo != null) ? countInfo.getStr("cnccnt") : "0");
        result.put("exccnt", (countInfo != null) ? countInfo.getStr("exccnt") : "0");
        result.put("ramtcnt", (countInfo != null) ? countInfo.getStr("ramtcnt") : "0");
        result.put("rtncnt", (countInfo != null) ? countInfo.getStr("rtncnt") : "0");
        result.put("regcnt", (countInfo != null) ? countInfo.getStr("regcnt") : "0");
        result.put("smscnt", (countInfo != null) ? countInfo.getStr("smscnt") : "0");
        result.put("dorcnt", (countInfo != null) ? countInfo.getStr("dorcnt") : "0");
        result.put("rescnt", (countInfo != null) ? countInfo.getStr("rescnt") : "0");
        result.put("list", list);

        return result;
    }

    @Override
    public List<Map<String, Object>> selectDateStatListExcel(SOMap params) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));

        List<SOMap> list = comOrderMapper.selectDateStatList(params);
        SOMap countInfo = comOrderMapper.selectDateStatListCountInfo(params);

        map.put("target", "합계");
        map.put("total", countInfo.getOrDefault("total", 0));
        map.put("ordercnt", countInfo.getOrDefault("ordercnt", "0"));
        map.put("pordercnt", countInfo.getOrDefault("pordercnt", "0"));
        map.put("price", countInfo.getOrDefault("price", "0"));
        map.put("unitamt", countInfo.getOrDefault("unitamt", "0"));
        map.put("cnccnt", countInfo.getOrDefault("cnccnt", "0"));
        map.put("exccnt", countInfo.getOrDefault("exccnt", "0"));
        map.put("ramtcnt", countInfo.getOrDefault("ramtcnt", "0"));
        map.put("rtncnt", countInfo.getOrDefault("rtncnt", "0"));
        map.put("regcnt", countInfo.getOrDefault("regcnt", "0"));
        map.put("smscnt", countInfo.getOrDefault("smscnt", "0"));
        map.put("dorcnt", countInfo.getOrDefault("dorcnt", "0"));
        map.put("rescnt", countInfo.getOrDefault("rescnt", "0"));

        result.add(map);
        result.addAll(list);

        return result;
    }
}
