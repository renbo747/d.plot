package com.dplot.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.dplot.common.service.ERPService;
import com.dplot.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.MallConfigService;
import com.dplot.common.service.PartnershipService;
import com.dplot.common.service.util.FileService;
import com.dplot.util.CryptHash;
import com.dplot.util.HttpConnectionUtil;
import com.dplot.util.ServletRequestInfoUtil;

@Service
public class PartnershipServiceImpl implements PartnershipService {

    private static final Logger logger = LoggerFactory.getLogger(PartnershipServiceImpl.class);

    @Autowired
    private DealerMapper dealerMapper;

    @Autowired
    private DealerInfoMapper dealerInfoMapper;

    @Autowired
    private DealerChargeMapper dealerChargeMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserTotalMapper userTotalMapper;

    @Autowired
    private UserLogMapper userLogMapper;

    @Autowired
    private MallConfigService cs;

    @Autowired
    private ConfigDealerMapper configDealerMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private ERPService erpService;

    @Autowired
    private ConfigTermMapper configTermMapper;

    @Resource(name="propertiesFactory")
    private Properties prop;

    @Override
    public boolean checkOverlapUserId(String userId) throws Exception {
        SOMap param = new SOMap();
        param.put("userId", userId);
        param.put("siteId", cs.getStr("siteid"));
        int result = userMapper.selectUserCount(param);
        return result > 0;
    }

    @Override
    public boolean bizNumberCheck(SOMap param) {
        String dataPortalUrl = prop.getProperty("data.portal.url");
        String dataPortalServiceKey = prop.getProperty("data.portal.serviceKey");
        
        String url = String.format("%s?serviceKey=%s", dataPortalUrl, dataPortalServiceKey);

        //공공데이터 포털서식 데이터 만들기 시작
        Map<String, Object> data = new HashMap<>();
        data.put("b_no", String.format("%s%s%s", param.get("bizno1"), param.get("bizno2"), param.get("bizno3")));
        data.put("start_dt", param.get("salesstdt"));
        data.put("p_nm", param.get("reprename"));

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(data);

        Map<String, Object> sendData = new HashMap<>();
        sendData.put("businesses", list);
        //공공데이터 포털서식 데이터 만들기 끝

        Map<String, Object> httpResultMap = HttpConnectionUtil.httpRequest(url, "POST", sendData);
        if(httpResultMap != null && "200".equals(httpResultMap.get("httpCode").toString())){
            String resultStatus = httpResultMap.get("status_code").toString();
            List<Map<String, Object>> resultDataList = (ArrayList<Map<String, Object>>) httpResultMap.get("data");

            String resultValid = "1";
            if(resultDataList != null){
                resultValid = resultDataList.get(0).get("valid").toString();
            }

            return resultStatus.equals("OK") && resultValid.equals("01");
        } else {
            return false;
        }
    }

    @Override
    public SOMap selectPartnershipJoinInitData(SOMap param) throws Exception {
        SOMap result = new SOMap();
        param.put("cmclass", "BANKTYPE");
        result.put("prodtypelist", prodTypeList());
        result.put("banktypelist", commonService.getCodeList(param));

        return result;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor={Throwable.class})
    public boolean insertPartnership(SOMap param, Map<String, MultipartFile> files){

        boolean result = false;

        try {
            SOMap user = new SOMap();
            user.put("userid", param.get("userid"));
            user.put("userpw", CryptHash.hash(param.get("userpw").toString()));
            //AS-IS COMMON.CONF.XML 파일에 USER_KIND 파트너사 8로 처리되어 있음.
            user.put("userkind", "8");
            user.put("usertype", "URT003");
            user.put("siteid", cs.getStr("siteid"));
            //T_USER 등록
            int userInsertCnt = userMapper.insertUser(user);
            logger.debug("userInsertCnt : " + userInsertCnt);

            int userNo = Integer.parseInt(user.get("no").toString());
            /** [2022-04-01]JSK: t_user_total삭제, t_user_log insert 추가 **/
            //T_USER_TOTAL 등록
            //SOMap userTotal = new SOMap();
            //userTotal.put("userno", userNo);
            //userTotalMapper.insertUserTotal(userTotal);
//    		SOMap userLog = new SOMap();
//    		userLog.put("userno", userNo);
//    		userLog.put("ip", ServletRequestInfoUtil.getRequestIp());
//    		userLogMapper.insertUserLog(userLog);
            
            SOMap partnership = new SOMap();
            SOMap partnershipInfo = new SOMap();
            List<SOMap> dealerChargeList = new ArrayList<>();

            MultipartFile businessFile = files.get("businessFile");
            MultipartFile bCopyFile = files.get("bCopyFile");
            MultipartFile cSellFile = files.get("cSellFile");


            //사업자등록증 파일
            if("application/pdf".equals(businessFile.getContentType())){
                fileService.uploadAttach(businessFile, userNo, CMConst.IMG_TYPE_PARTNER1);
            } else {
                fileService.uploadImage(businessFile, userNo, CMConst.IMG_TYPE_PARTNER1);
            }

            //통장사본 파일
            if("application/pdf".equals(bCopyFile.getContentType())){
                fileService.uploadAttach(bCopyFile, userNo, CMConst.IMG_TYPE_PARTNER2);
            } else {
                fileService.uploadImage(bCopyFile, userNo, CMConst.IMG_TYPE_PARTNER2);
            }

            //통신사본 파일
            if("application/pdf".equals(cSellFile.getContentType())){
                fileService.uploadAttach(cSellFile, userNo, CMConst.IMG_TYPE_PARTNER3);
            } else {
                fileService.uploadImage(cSellFile, userNo, CMConst.IMG_TYPE_PARTNER3);
            }

            //dealer : isAdSms, isAdMailing, bCopyFile, comSellNo, remitBirth, orgBCopyFile, orgBizNoFile, name, sellProdType, businessFile
            //파트너사 정보 T_DEALER
            partnership.put("userno", userNo);
            partnership.put("name", param.get("name"));
            partnership.put("state", "200");
            partnership.put("isadsms", param.get("isadsms"));
            partnership.put("isadmailing", param.get("isadmailing"));
            partnership.put("comsellno", param.get("comsellno"));
            partnership.put("remitbirth", param.get("remitbirth"));
            partnership.put("sellprodtype", param.get("sellprodtype"));
            partnership.put("dealerst", "DST001");
            partnership.put("reqdealst", "RDS001");
            partnership.put("dealercontst", "DCS001");
            partnership.put("handbrand", param.get("handbrand"));

            //T_DEALER 등록
            dealerMapper.insertPartnership(partnership);


            //dealerInfo : compName, tel, addr, email, addrDetail, bizNo, bizCondition, repMobile, repCI, remitBank, remitDepositor, remitAccount, repreName, bizItem
            //파트너사 상세 T_DEALER_INFO
            partnershipInfo.put("dealerno", userNo);
            partnershipInfo.put("compname", param.get("compname"));
            partnershipInfo.put("tel", param.get("tel"));
            partnershipInfo.put("mobile", param.get("repmobile"));
            partnershipInfo.put("email", param.get("email"));
            partnershipInfo.put("post", param.get("post"));
            partnershipInfo.put("addr", param.get("addr"));
            partnershipInfo.put("addrdetail", param.get("addrdetail"));

            partnershipInfo.put("sigungucode", param.get("sigungucode"));
            partnershipInfo.put("buildingcode", param.get("buildingcode"));
            partnershipInfo.put("roadnamecode", param.get("roadnamecode"));

            partnershipInfo.put("bizno", param.get("bizno"));
            partnershipInfo.put("bizcondition", param.get("bizcondition"));
            partnershipInfo.put("repmobile", param.get("repmobile"));
            partnershipInfo.put("repci", param.get("repci"));
            partnershipInfo.put("remitbank", param.get("remitbank"));
            partnershipInfo.put("remitdepositor", param.get("remitdepositor"));
            partnershipInfo.put("remitaccount", param.get("remitaccount"));
            partnershipInfo.put("reprename", param.get("reprename"));
            partnershipInfo.put("bizitem", param.get("bizitem"));

            SOMap codeParam = new SOMap();
            codeParam.put("siteid", cs.getStr("siteid"));

            SOMap configDealerData = configDealerMapper.selectConfigDealer(codeParam);
            partnershipInfo.put("muaddauthtype", configDealerData.get("muaddauthtype"));
            //T_DEALER_INFO 등록
            dealerInfoMapper.insertPartnershipInfo(partnershipInfo);

            SOMap salesCharge = new SOMap();
            SOMap accountCharge = new SOMap();
            SOMap taxCharge = new SOMap();
            //파트너사 담당자 T_DEALER_CHARGE (CHA001 : 영업) 디폴트 대표 담당자 영업
            salesCharge.put("userno", userNo);
            salesCharge.put("chargetype", "CHA001");
            salesCharge.put("chargename", param.get("saleschargename"));
            salesCharge.put("chargemobile", param.get("saleschargemobile"));
            salesCharge.put("chargeemail", param.get("saleschargeemail"));
            dealerChargeList.add(salesCharge);

            //(CHA002 : 정산)
            accountCharge.put("userno", userNo);
            accountCharge.put("chargetype", "CHA002");
            accountCharge.put("chargename", param.get("accountchargename"));
            accountCharge.put("chargemobile", param.get("accountchargemobile"));
            accountCharge.put("chargeemail", param.get("accountchargeemail"));
            dealerChargeList.add(accountCharge);

            //(CHA006 : 세금계산서 담당자)
            taxCharge.put("userno", userNo);
            taxCharge.put("chargetype", "CHA006");
            taxCharge.put("chargename", param.get("taxchargename"));
            taxCharge.put("chargemobile", param.get("taxchargemobile"));
            taxCharge.put("chargeemail", param.get("taxchargeemail"));
            dealerChargeList.add(taxCharge);

            //T_DEALER_CAHRGE 등록 (영업담당자, 정산담당자)
            for (SOMap data : dealerChargeList) {
                dealerChargeMapper.insertPartnershipCharge(data);
            }

            user.put("userno", userNo);
            user.put("aud", "A");
            erpService.insertPartnershipERPData(user);

            result = true;

        } catch(Exception e){
            logger.debug(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    @Override
    public SOMap updatePartnershipFile(SOMap param, Map<String, MultipartFile> files) {

        SOMap soMap = new SOMap();
        soMap.putAll(param);

        SOMap result = new SOMap();

        try {
            int userNo = Integer.parseInt(param.get("no").toString());

            //기존 파일이 존재할 경우에 파일삭제
            if(param.containsKey("deleteidx") && !"".equals(param.get("deleteidx"))){
                fileService.delete(Integer.parseInt(param.get("deleteidx").toString()));
            }

            if(files.containsKey("orgbiznofile")){
                MultipartFile businessFile = files.get("orgbiznofile");

                if("application/pdf".equals(businessFile.getContentType())){
                    SOMap s3Result = fileService.uploadAttach(businessFile, userNo, CMConst.IMG_TYPE_PARTNER1);
                    result.put(CMConst.IMG_TYPE_PARTNER1, s3Result);
                } else {
                    SOMap s3Result = fileService.uploadImage(businessFile, userNo, CMConst.IMG_TYPE_PARTNER1);
                    result.put(CMConst.IMG_TYPE_PARTNER1, s3Result);
                }

            }
            if(files.containsKey("orgbcopyfile")){
                MultipartFile bCopyFile = files.get("orgbcopyfile");

                if("application/pdf".equals(bCopyFile.getContentType())){
                    SOMap s3Result = fileService.uploadAttach(bCopyFile, userNo, CMConst.IMG_TYPE_PARTNER2);
                    result.put(CMConst.IMG_TYPE_PARTNER2, s3Result);
                } else {
                    SOMap s3Result = fileService.uploadImage(bCopyFile, userNo, CMConst.IMG_TYPE_PARTNER2);
                    result.put(CMConst.IMG_TYPE_PARTNER2, s3Result);
                }
            }
            if(files.containsKey("orgcsellfile")){
                MultipartFile cSellFile = files.get("orgcsellfile");

                if("application/pdf".equals(cSellFile.getContentType())){
                    SOMap s3Result = fileService.uploadAttach(cSellFile, userNo, CMConst.IMG_TYPE_PARTNER3);
                    result.put(CMConst.IMG_TYPE_PARTNER3, s3Result);
                } else {
                    SOMap s3Result = fileService.uploadImage(cSellFile, userNo, CMConst.IMG_TYPE_PARTNER3);
                    result.put(CMConst.IMG_TYPE_PARTNER3, s3Result);
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public SOMap deletePartnershipFile(SOMap param) {
        SOMap result = new SOMap();
        try {
            fileService.delete(Integer.parseInt(param.get("deleteidx").toString()));
            result.put("code", "success");
        } catch(Exception e){
            e.printStackTrace();
            result.put("code", "fail");
        }
        return result;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor={Throwable.class})
    public boolean updatePartnership(SOMap param) {
        boolean result = false;

        try {
            SOMap soMap = new SOMap();
            soMap.putAll(param);

            dealerMapper.updatePartnership(soMap);
            dealerInfoMapper.updatePartnershipInfo(soMap);

            List<Map<String, Object>> dealerChargeList = (List<Map<String, Object>>) param.get("dealerchargeinfo");
            List<Map<String, Object>> createDCList = dealerChargeList.stream().filter(a -> a.containsKey("isCreated")).collect(Collectors.toList());
            List<Map<String, Object>> updateDCList = dealerChargeList.stream().filter(a -> !a.containsKey("isCreated")).collect(Collectors.toList());

            for(Map<String, Object> map : createDCList){
                SOMap createMap = new SOMap();
                createMap.putAll(map);
                dealerChargeMapper.insertPartnershipCharge(createMap);
            }

            for(Map<String, Object> map : updateDCList){
                SOMap updateMap = new SOMap();
                updateMap.putAll(map);
                dealerChargeMapper.updatePartnershipCharge(updateMap);
            }

            SOMap logParam = new SOMap();
            logParam.put("userno", soMap.get("no"));
            logParam.put("siteid", cs.getStr("siteid"));
            logParam.put("aud", "U");
            erpService.insertPartnershipERPData(logParam);

            result = true;
        } catch(Exception e){
            e.printStackTrace();
            logger.debug(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public List<SOMap> prodTypeList() throws Exception {
        SOMap param = new SOMap();
        param.put("siteid", cs.getStr("siteid"));
        return categoryMapper.selectProdTypeList(param);
    }

    @Override
    public SOMap passwordUpdate(SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("code", "fail");
        result.put("msg", "사용자를 찾을 수 없습니다.");

        param.put("siteid", cs.getStr("siteid"));
        SOMap dealerInfo = dealerInfoMapper.selectDealerInfoByCI(param);

        //전달받은 CI와 ID로 사용자 조회하여 업데이트 처리
        if(dealerInfo != null){
            SOMap updateParam = new SOMap();
            String encPw = CryptHash.hash(param.getDbStr("userpw"));
            updateParam.put("no", dealerInfo.get("no"));
            updateParam.put("userpw", encPw);
            int cnt = userMapper.updateUser(updateParam);
            if(cnt > 0){
                result.put("code", "success");
                result.put("msg", "비밀번호가 정상적으로 변경 되었습니다.");
            }
        }

        return result;
    }

    @Override
    public SOMap findId(SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("code", "fail");
        result.put("msg", "사용자를 찾을 수 없습니다.");

        param.put("siteid", cs.getStr("siteid"));
        List<SOMap> dealerList = dealerInfoMapper.selectDealerInfoByCIList(param);

        //전달받은 CI와 ID로 사용자 조회하여 업데이트 처리
        if(dealerList != null && dealerList.size() > 0){
            result.put("code", "success");
            result.put("list", dealerList);
        }

        return result;
    }

    @Override
    public SOMap selectPartnersTerms(SOMap param) throws Exception {
        SOMap result = new SOMap();
        param.put("siteid", cs.getStr("siteid"));
        result.put("list",  configTermMapper.selectPartnersTerms(param));
        return result;
    }
}
