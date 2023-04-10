package com.dplot.admin.service.common;

import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.ConfigMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.util.HttpConnectionUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class ExternalServiceImpl extends MallBaseService implements ExternalService{

    @Autowired
    private DealerMapper dealerMapper;

    @Autowired
    private ConfigMapper configMapper;

    @Resource(name="propertiesFactory")
    private Properties prop;

    @Override
    public SOMap partnersSignRequest(SOMap param) throws Exception {

        String url = prop.getProperty("modu.sign.api.url");
        String templateId = prop.getProperty("modu.sign.template.id");

        SOMap result = new SOMap();
        SOMap detail = dealerMapper.selectPartnsersDetail(param);

        Map<String, Object> sendData = new HashMap<>();
        Map<String, Object> document = new HashMap<>();

        List<Map<String, Object>> requestInputList = new ArrayList<>();
        IntStream.range(0, 3).forEach(i -> {
            int index = i+1;
            Map<String, Object> map = new HashMap<>();
            map.put("dataLabel", String.format("협력사명%s",index));
            map.put("value", detail.get("compname"));
            requestInputList.add(map);

            Map<String, Object> map2 = new HashMap<>();
            map2.put("dataLabel", String.format("주소%s",index));
            map2.put("value", String.format("%s %s", detail.get("addr"), detail.get("addrdetail")));
            requestInputList.add(map2);

            Map<String, Object> map3 = new HashMap<>();
            map3.put("dataLabel", String.format("상호%s",index));
            map3.put("value", detail.get("compname"));
            requestInputList.add(map3);

            Map<String, Object> map4 = new HashMap<>();
            map4.put("dataLabel", String.format("대표자%s",index));
            map4.put("value", detail.get("reprename"));
            requestInputList.add(map4);
        });

        List<Map<String, Object>> participantList = new ArrayList<>();
//        Map<String, Object> participantMapMaster = new HashMap<>();
//        Map<String, Object> inDataMapMaster = new HashMap<>();
//        inDataMapMaster.put("type", "KAKAO");
//        inDataMapMaster.put("value", sendPhone);
//        participantMapMaster.put("role", "다다");
//        participantMapMaster.put("name", "다다");
//        participantMapMaster.put("excluded", false);
//        participantMapMaster.put("signingMethod", inDataMapMaster);
//
//        participantList.add(participantMapMaster);

        Map<String, Object> participantMapPartners = new HashMap<>();
        Map<String, Object> inDataMapPartners = new HashMap<>();

        inDataMapPartners.put("type", "EMAIL");
        inDataMapPartners.put("value", detail.get("chargemail"));

        participantMapPartners.put("role", "협력사");
        participantMapPartners.put("name", detail.get("compname"));
        participantMapPartners.put("excluded", false);
        participantMapPartners.put("signingMethod", inDataMapPartners);

        participantList.add(participantMapPartners);

        document.put("title", String.format("위탁판매 표준거래계약서(%s)", detail.get("compname")));
        document.put("requesterInputMappings", requestInputList);
        document.put("participantMappings", participantList);

        sendData.put("templateId", templateId);
        sendData.put("document", document);

        Map<String, Object> header = getModuSignAuthenticationHeader();
        Map<String, Object> map = HttpConnectionUtil.httpsRequest(url, "POST", sendData, header);
        result.putAll(map);

        if(map.containsKey("httpCode") && ("201".equals(map.get("httpCode").toString()) || "200".equals(map.get("httpCode").toString()))){
            SOMap soMap = new SOMap();
            soMap.put("no", param.get("userno"));
            soMap.put("signid", map.get("id"));
            soMap.put("dealercontst", "DCS002");
            int updateSignCnt = dealerMapper.updateSignStateAndModuId(soMap);
            map.put("resultCnt", updateSignCnt);

            result.put("code", "success");
        } else {
            result.put("code", "fail");
        }

        return result;
    }

    @Override
    public int partnersSignStateUpdate(SOMap param) throws Exception {
        SOMap updateParam = new SOMap();
        Map<String, Object> event = (Map<String, Object>) param.get("event");
        Map<String, Object> document = (Map<String, Object>) param.get("document");
        updateParam.put("signid", document.get("id"));

        String[] rejectArr = {"document_rejected", "document_request_canceled", "document_signing_canceled"};
        if(Arrays.asList(rejectArr).contains(event.get("type").toString())){
            updateParam.put("dealercontst", "DCS001");
        } else if("document_all_signed".equals(event.get("type"))){
//            updateParam.put("signurl", getContractUrl(document.get("id").toString()));
            updateParam.put("dealercontst", "DCS003");
        }

        return dealerMapper.updateSignState(updateParam);
    }

    @Override
    public String getContractUrl(String moduId) {
        String path = prop.getProperty("modu.sign.document.path");
        String subPath= prop.getProperty("modu.sign.document.sub.path");

        String url = String.format("%s/%s%s", path, moduId,subPath);
        Map<String, Object> header = getModuSignAuthenticationHeader();
        Map<String, Object> urlResult = HttpConnectionUtil.httpsRequest(url, "GET", null, header);
        String contractUrl = "";
        if(urlResult != null && Status.OK.getKey() == Integer.parseInt(urlResult.get("httpCode").toString())){
            contractUrl = urlResult.get("embeddedUrl").toString();
        }
        return contractUrl;
    }

    @Override
    public int updateConfigInstaToken(String code, String serverName) throws Exception {
        int result = 0;

        boolean csResult = cs.init(serverName);

        String instaClientId = prop.getProperty("instagram.client.id");
        String instaServiceKey = prop.getProperty("instagram.app.service.key");
        String instaRedirectURI = prop.getProperty("instagram.redirect.uri");
        String instaFeedURL = prop.getProperty("instagram.feed.url");
        String instaTokenURL = prop.getProperty("instagram.token.url");

        Map<String, Object> sendData = new HashMap<>();
        sendData.put("client_id", instaClientId);
        sendData.put("client_secret", instaServiceKey);
        sendData.put("grant_type", "authorization_code");
        sendData.put("redirect_uri", instaRedirectURI);
        sendData.put("code", code);

        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded");

        //전달 받은 코드로 1시간짜리 토큰을 먼저 생성
        Map<String, Object> httpResult = HttpConnectionUtil.httpsRequest(instaTokenURL, "POST", sendData, header);

        if(httpResult != null && Status.OK.getKey() == Integer.parseInt(httpResult.get("httpCode").toString())){
            String instaLongTokenURL = String.format("%s/access_token?grant_type=ig_exchange_token&client_secret=%s&access_token=%s", instaFeedURL, instaServiceKey, httpResult.get("access_token"));
            //요청에 성공했을 경우 발급받은 토큰으로(1시간짜리) 60일짜리 토큰을 재발급 처리
            Map<String, Object> httpResultLongToken = HttpConnectionUtil.httpsRequest(instaLongTokenURL, "GET", null, null);

            if(httpResultLongToken != null && Status.OK.getKey() == Integer.parseInt(httpResultLongToken.get("httpCode").toString())){
                SOMap param = new SOMap();
                param.put("siteid", cs.getStr("siteid"));
                param.put("instatoken", httpResultLongToken.get("access_token"));
                result = configMapper.updateConfig(param);
                cs.setStr("instatoken", httpResultLongToken.get("access_token").toString());
            }
        }

        return result;
    }

    private Map<String, Object> getModuSignAuthenticationHeader(){
        Map<String, Object> header = new HashMap<>();
        String id = prop.getProperty("modu.sign.api.id");
        String apiKey = prop.getProperty("modu.sign.api.key");

        String auth = id + ":" + apiKey;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        header.put("Authorization", authHeaderValue);
        header.put("Content-Type", "application/json");

        return header;
    }
}
