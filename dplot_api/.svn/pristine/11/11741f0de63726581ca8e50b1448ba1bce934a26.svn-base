package com.dplot.admin.service.partners;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.*;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.*;
import com.dplot.util.CryptHash;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminPartnersApplyServiceImpl extends MallBaseService implements AdminPartnersApplyService {

    @Autowired
    private DealerMapper dealerMapper;

    @Autowired
    private DealerChargeMapper dealerChargeMapper;

    @Autowired
    private DealerDelivMapper dealerDelivMapper;

    @Autowired
    private MallConfigService cs;

    @Autowired
    private PartnershipService partnershipService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConfigDealerMapper configDealerMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ERPService erpService;

    @Autowired
    private CJMessageService cjMessageService;

    @Override
    public SOMap selectPartnersList(SOMap param) throws Exception {
        param.put("siteid", cs.getStr("siteid"));

        int page = Integer.parseInt(param.get("page").toString());
        int pageCount = Integer.parseInt(param.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        param.put("startpage", startPage);
        param.put("endpage", pageCount);

        SOMap result = new SOMap();
        List<SOMap> partnersList = dealerMapper.selectPartnsersApplyList(param);
        SOMap stateCount = dealerMapper.selectPartnsersApplyListStateCount(param);

        result.put("list", partnersList);
        result.put("listcount", (stateCount != null) ? stateCount.get("total_cnt") : 0);
        result.put("waitcnt", (stateCount != null) ? stateCount.get("wait_cnt") : 0);
        result.put("approvalcnt", (stateCount != null) ? stateCount.get("approval_cnt") : 0);
        result.put("deniedcnt", (stateCount != null) ? stateCount.get("denied_cnt") : 0);

        return result;
    }

    @Override
    public int updatePartnersState(SOMap param) throws Exception {
        String[] userNoArray = (String[]) param.get("user_no_arr");
        for(String userNo : userNoArray){
            SOMap dbParam = new SOMap();
            dbParam.put("siteid", cs.getStr("siteid"));
            dbParam.put("userno", userNo);
            dbParam.put("aud", "U");
            erpService.insertPartnershipERPData(dbParam);
        }
        return dealerMapper.updatePartnersReqDealst(param);
    }

    @Override
    public SOMap selectPartnsersDetail(SOMap param) throws Exception {
        SOMap result = new SOMap();

        //파트너사 상세정보 조회
        result.put("detailinfo", dealerMapper.selectPartnsersDetail(param));

        SOMap fileParam = new SOMap();
        String[] partnersImageTypes = {"IGT029", "IGT030", "IGT031"};
        fileParam.put("orgidx", param.get("userno"));
        fileParam.put("imgtypes", partnersImageTypes);
        List<SOMap> partnersFileList = fileMapper.selectFileList(fileParam);
        Map<String, List<Map<String, Object>>> partnersFileListMap = partnersFileList.stream().collect(Collectors.groupingBy(a -> a.get("imgtype").toString()));

        SOMap reMap = new SOMap();
        reMap.put(CMConst.IMG_TYPE_PARTNER1, partnersFileListMap.containsKey(CMConst.IMG_TYPE_PARTNER1) ? partnersFileListMap.get(CMConst.IMG_TYPE_PARTNER1).get(0) : "");
        reMap.put(CMConst.IMG_TYPE_PARTNER2, partnersFileListMap.containsKey(CMConst.IMG_TYPE_PARTNER2) ? partnersFileListMap.get(CMConst.IMG_TYPE_PARTNER2).get(0) : "");
        reMap.put(CMConst.IMG_TYPE_PARTNER3, partnersFileListMap.containsKey(CMConst.IMG_TYPE_PARTNER3) ? partnersFileListMap.get(CMConst.IMG_TYPE_PARTNER3).get(0) : "");

        result.put("partnersFiles", reMap);

        //파트너사 담당자 정보 조회
        result.put("dealerchargelist", selectDealerCharge(param));
        //판매유형리스트 조회
        result.put("prodtypelist", partnershipService.prodTypeList());
        result.put("deliverylist", dealerDelivMapper.selectPartnersDeliveryList(param).stream().filter(a-> "T".equals(a.get("isvalid"))).collect(Collectors.toList()));

        SOMap soMap = new SOMap();
        //담당자 타입 코드 리스트 조회
        soMap.put("cmclass", "CHARGETYPE");
        result.put("chargetypelist", commonService.getCodeList(soMap));

        //은행 타입 코드 리스트 조회
        soMap.put("cmclass", "BANKTYPE");
        result.put("banktypelist", commonService.getCodeList(soMap));

        //파트너사 권한 코드 리스트 조회
        soMap.put("cmclass", "MUADDAUTHTYPE");
        result.put("authtypelist", commonService.getCodeList(soMap));

        soMap.put("siteid", cs.getStr("siteid"));
        SOMap configDealerData = configDealerMapper.selectConfigDealer(soMap);
        result.put("configauthtypeorigin", configDealerData.get("muaddauthtype"));

        return result;
    }

    @Override
    public List<SOMap> selectDealerCharge(SOMap param) {
        return dealerChargeMapper.selectDealerCharge(param);
    }

    @Override
    public SOMap initPassword(SOMap param){
        SOMap resultMap = new SOMap();
        resultMap.put("code", "success");
        String userPw = String.format("%s%s", RandomStringUtils.randomAlphanumeric(6), RandomStringUtils.randomNumeric(2)).toLowerCase();
        String cryptPw = CryptHash.hash(userPw);
        param.put("userpw", cryptPw);

        int result = userMapper.updateUserPasswordByNo(param);

        if(result >  0){
            SOMap messageParam = new SOMap();
            messageParam.put("msg_type", "SMS");
            messageParam.put("receiver_number", param.get("mobile"));

            messageParam.put("msg", String.format("[D.PLOT] 임시비밀번호는 \n%s 입니다.", userPw));

            try {
                SOMap sendResult = cjMessageService.sendMessage(messageParam);
                if(!"200".equals(sendResult.get("httpcode").toString())){
                    resultMap.put("code", "fail");
                }
            } catch(Exception e){
                resultMap.put("code", "fail");
                e.printStackTrace();
            }
        }

        return resultMap;
    }

    @Override
    public SOMap deletePartnersCharger(SOMap param) throws Exception {
        SOMap result = new SOMap();
        dealerChargeMapper.deletePartnershipCharge(param);
        //담당자 타입 코드 리스트 조회

        param.put("siteid", cs.getStr("siteid"));
        param.put("aud", "U");
        erpService.insertPartnershipERPData(param);

        SOMap soMap = new SOMap();
        soMap.put("cmclass", "CHARGETYPE");
        result.put("chargetypelist", commonService.getCodeList(soMap));

        result.put("list", selectDealerCharge(param));
        return result;
    }
}
