package com.dplot.partners.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.AdminMenuMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.DealerChargeMapper;
import com.dplot.mapper.DealerMapper;

@Service
public class PartnersCommonServiceImpl extends MallBaseService implements PartnersCommonService{

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    @Autowired
    private DealerChargeMapper dealerChargeMapper;

    @Autowired
    private CJMessageService cjMessageService;

    @Autowired
    private ComOrderMapper comOrderMapper;

	@Autowired
	DealerMapper dealerMapper;

    @Override
    public List<SOMap> selectPartnersSubMenu(SOMap params) throws Exception {
        List<SOMap> sideMenuList = adminMenuMapper.selectPartnersSubMenu(params);
        List<SOMap> resultList = new ArrayList<>();

        if(sideMenuList.size() > 0){
            for(SOMap row : sideMenuList){
                if("1".equals(row.get("depth").toString())){
                    String code = row.get("code").toString();
                    List<SOMap> child = sideMenuList.stream().peek(obj-> {
                        obj.put("isactive", obj.containsKey("url") && obj.getStr("url").equals(params.get("path")));
                    }).filter(map -> code.equals(map.get("uppercode"))).collect(Collectors.toList());
                    row.put("childmenulist", child);
                    resultList.add(row);
                }
            }
        }


     	// 하위메뉴 isActive일때 상위메뉴 isActive 적용
		for (SOMap parentMenu : resultList) {
			parentMenu.put("isactive", parentMenu.containsKey("url") && parentMenu.get("url").equals(params.get("path")));
			List<SOMap> childMenuList = (List<SOMap>) parentMenu.get("childmenulist");
			for (SOMap childMenu : childMenuList) {
				if ((boolean) childMenu.get("isactive")) {
					parentMenu.put("isactive", true);
					break;
				}
			}
		}
//        resultList = resultList.stream().peek(row -> {
//            row.put("isactive", row.containsKey("url") && row.get("url").equals(params.get("path")));
//        }).collect(Collectors.toList());

        return resultList;
    }

    @Override
    public SOMap getChargerInfoByType(SOMap params) throws Exception {
        return dealerChargeMapper.getChargerInfo(params);
    }

    @Override
    public SOMap sendAuthMessage(SOMap params) throws Exception {
        SOMap result = new SOMap();

        SOMap messageParam = new SOMap();
        messageParam.put("msg_type", "SMS");
        messageParam.put("receiver_number", params.get("chargemobile"));

        int authNo = (int)(Math.random() * (999999 - 100000 + 1)) + 100000;
        messageParam.put("msg", String.format("[D.PLOT] 제휴담당자 인증번호\n[%d]를 입력해 주세요.", authNo));

        SOMap sendResult = cjMessageService.sendMessage(messageParam);
        if("200".equals(sendResult.get("httpcode").toString())){
            result.put("number", authNo);
        }
        return result;
    }

    @Override
    public SOMap selectPartnersSaleList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        //페이징 데이터
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        //기본 사항 SITE ID
        params.put("siteid", cs.getStr("siteid"));
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = comOrderMapper.selectPartnersSaleList(params);
        SOMap countInfo = comOrderMapper.selectPartnersSaleListCountInfo(params);

        result.put("total", (countInfo != null) ? countInfo.getInt("total") : 0);
        result.put("ordercnt", (countInfo != null) ? countInfo.getStr("ordercnt") : "0");
        result.put("frstordcnt", (countInfo != null) ? countInfo.getStr("frstordcnt") : "0");
        result.put("ordamt", (countInfo != null) ? countInfo.getStr("ordamt") : "0");
        result.put("delivamt", (countInfo != null) ? countInfo.getStr("delivamt") : "0");
        result.put("promoshareamt", (countInfo != null) ? countInfo.getStr("promoshareamt") : "0");
        result.put("cpnshareamt", (countInfo != null) ? countInfo.getStr("cpnshareamt") : "0");
        result.put("realamt", (countInfo != null) ? countInfo.getStr("realamt") : "0");
        result.put("price", (countInfo != null) ? countInfo.getStr("price") : "0");
        result.put("rtnamt", (countInfo != null) ? countInfo.getStr("rtnamt") : "0");
        result.put("list", list);

        return result;
    }

	@Override
	public List<Map<String, Object>> selectPartnersSaleListExcel(SOMap params) throws Exception {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		// 기본 사항 SITE ID
		params.put("siteid", cs.getStr("siteid"));

		List<SOMap> list = comOrderMapper.selectPartnersSaleList(params);
		SOMap countInfo = comOrderMapper.selectPartnersSaleListCountInfo(params);

		map.put("target", "합계");
		map.put("total", countInfo.getOrDefault("total", 0));
		map.put("ordercnt", countInfo.getOrDefault("ordercnt", "0"));
		map.put("frstordcnt", countInfo.getOrDefault("frstordcnt", "0"));
		map.put("ordamt", countInfo.getOrDefault("ordamt", "0"));
		map.put("delivamt", countInfo.getOrDefault("delivamt", "0"));
		map.put("promoshareamt", countInfo.getOrDefault("promoshareamt", "0"));
		map.put("cpnshareamt", countInfo.getOrDefault("cpnshareamt", "0"));
		map.put("realamt", countInfo.getOrDefault("realamt", "0"));
		map.put("price", countInfo.getOrDefault("price", "0"));
		map.put("rtnamt", countInfo.getOrDefault("rtnamt", "0"));
		map.put("orgamt", countInfo.getOrDefault("orgamt", "0"));

		result.add(map);
		result.addAll(list);

		return result;
	}
}
