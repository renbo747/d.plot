package com.dplot.common.service.impl;

import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.mapper.*;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.HttpConnectionUtil;
import com.dplot.util.Util;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CJMessageServiceImpl implements CJMessageService {
	private static final Logger logger = LoggerFactory.getLogger(CJMessageServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ReserveMapper reserveMapper;

    @Autowired
    private ComOrderMapper comOrderMapper;

    @Autowired
    private MemberMsgMapper memberMsgMapper;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private DealerMapper dealerMapper;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private InquiryMapper inquiryMapper;

    @Autowired
    private RewareMapper rewareMapper;

    @Resource(name="propertiesFactory")
    private Properties prop;

    @Override
    public SOMap sendMessage(SOMap param) {
        SOMap result = new SOMap();

        if(!Util.isEmpty(param.getStr("userno"))){
            try {
                SOMap member = memberMapper.selectMember(param);
                if(Util.isEmpty(member) || !"T".equals(member.getStr("isifsms"))){
                    result.put("httpCode", "401");
                    result.put("httpMessage", "정보성 동의여부 확인 바랍니다.");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        String environment = prop.getProperty("Globals.Profile");
//        String[] permissionNumber = prop.getProperty("cj.message.permission.number").split(",");

        Map<String, Object> sendParam = new HashMap<>();
        List<Map<String, Object>> sendList = new ArrayList<>();
        String messageId = prop.getProperty("cj.message.sms.id");
        String messagePw = prop.getProperty("cj.message.pw");
        String messageSenderNumber = prop.getProperty("cj.message.sender.number");
        String url = prop.getProperty("cj.message.sms.url");

        String auth = String.format("%s;%s", messageId, messagePw);

        Map<String, Object> row = new HashMap<>();
        row.put("msg_key", Instant.now().getEpochSecond());
        row.put("sub_id", messageId);
        row.put("sender_number", messageSenderNumber);
        row.put("receiver_number", param.get("receiver_number"));
        row.put("msg", param.get("msg"));
        sendList.add(row);

        if(sendList.size() <= 0){
            result.put("httpCode", "401");
            result.put("httpMessage", "발송 대상 사용자가 없습니다.");
//            result.put("httpMessage", "허용되지 않은 사용자 존재하지 않거나\n발송 대상 사용자가 없습니다.");
            return result;
        }

        sendParam.put("msg_type", param.get("msg_type"));
        sendParam.put("msg_data", sendList);

        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", auth);
        header.put("Content-Type", "application/json");

        Map<String, Object> sendResult = HttpConnectionUtil.httpsRequest(url, "POST", sendParam, header);
        if(sendResult != null && sendResult.containsKey("httpCode") && "200".equals(sendResult.get("httpCode").toString())){
            result.put("httpcode", sendResult.get("httpCode"));
            result.put("httpMessage", sendResult.get("httpMessage"));
            List<Map<String, Object>> resultList = (List<Map<String, Object>>) sendResult.get("results");

            //C100 성공 리턴코드
            long successCnt = resultList.stream().filter(a-> "C100".equals(a.get("code"))).count();
            long failCnt = resultList.stream().filter(a-> !"C100".equals(a.get("code"))).count();
            result.put("success", successCnt);
            result.put("fail", failCnt);
        } else {
            result.putAll(sendResult);
        }

        return result;
    }

    @Override
    public SOMap sendMessage(SOMap param, List<SOMap> list) {
        SOMap result = new SOMap();

        if(!Util.isEmpty(param.getStr("userno"))){
            try {
                SOMap member = memberMapper.selectMember(param);
                if(Util.isEmpty(member) || !"T".equals(member.getStr("isifsms"))){
                    result.put("httpCode", "401");
                    result.put("httpMessage", "정보성 동의여부 확인 바랍니다.");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> sendParam = new HashMap<>();
        List<Map<String, Object>> sendList = new ArrayList<>();
        String messageId = prop.getProperty("cj.message.sms.id");
        String messagePw = prop.getProperty("cj.message.pw");
        String messageSenderNumber = prop.getProperty("cj.message.sender.number");
        String url = prop.getProperty("cj.message.sms.url");

        String auth = String.format("%s;%s", messageId, messagePw);

        list.forEach(a -> {
            Map<String, Object> row = new HashMap<>();
            row.put("msg_key", Instant.now().getEpochSecond());
            row.put("sub_id", messageId);
            row.put("sender_number", messageSenderNumber);
            row.put("receiver_number", a.get("receiver_number"));
            row.put("msg", a.get("msg"));
            sendList.add(row);
        });

        if(sendList.size() <= 0){
            result.put("httpCode", "401");
            result.put("httpMessage", "발송 대상 사용자가 없습니다.");
//            result.put("httpMessage", "허용되지 않은 사용자 존재하지 않거나\n발송 대상 사용자가 없습니다.");
            return result;
        }

        sendParam.put("msg_type", param.get("msg_type"));
        sendParam.put("msg_data", sendList);

        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", auth);
        header.put("Content-Type", "application/json");

        Map<String, Object> sendResult = HttpConnectionUtil.httpsRequest(url, "POST", sendParam, header);
        if(sendResult != null && sendResult.containsKey("httpCode") && "200".equals(sendResult.get("httpCode").toString())){
            result.put("httpcode", sendResult.get("httpCode"));
            result.put("httpMessage", sendResult.get("httpMessage"));
            List<Map<String, Object>> resultList = (List<Map<String, Object>>) sendResult.get("results");

            //C100 성공 리턴코드
            long successCnt = resultList.stream().filter(a-> "C100".equals(a.get("code"))).count();
            long failCnt = resultList.stream().filter(a-> !"C100".equals(a.get("code"))).count();
            result.put("success", successCnt);
            result.put("fail", failCnt);
        } else {
            result.putAll(sendResult);
        }

        return result;
    }

    @Override
    public SOMap sendEmail(SOMap param, List<SOMap> list) {
        SOMap result = new SOMap();

        if(!"".equals(param.getStr("userno")) && !"0".equals(param.getStr("userno"))){
            try {
                SOMap member = memberMapper.selectMember(param);
                if(Util.isEmpty(member) || !"T".equals(member.getStr("isifmailing"))){
                    result.put("httpCode", "401");
                    result.put("httpMessage", "정보성 동의여부 확인 바랍니다.");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String url = prop.getProperty("cj.message.email.url");
        String sender = prop.getProperty("cj.message.email.sender");        //필수입력
        String sender_name = prop.getProperty("cj.message.email.sender.name");
        String username = prop.getProperty("cj.message.email.id");              //필수입력
        String key = prop.getProperty("cj.message.email.api.key");           //필수입력

//        String environment = prop.getProperty("Globals.Profile");
//        String[] permissionEmail = prop.getProperty("cj.message.permission.email").split(",");

        String body = (param.containsKey("template") && !"".equals(param.get("template").toString())) ? "" : param.get("content").toString();

        Map<String, Object> sendData = new HashMap<>();
        sendData.put("subject", param.get("subject"));
        sendData.put("body", body);

        if(param.containsKey("sender") && param.containsKey("sender_name")){
            sendData.put("sender", param.getStr("sender"));
            sendData.put("sender_name", param.getStr("sender_name"));
        } else {
            sendData.put("sender", sender);
            sendData.put("sender_name", sender_name);
        }

        // BODY가 없을 경우 TEMPLATE 사용으로 간주하여 이때만 파라미터 추가
        if("".equals(body)){
            sendData.put("template", param.get("template"));
            sendData.put("subject", "안녕하세요 D.PLOT 입니다.");
        }

        sendData.put("username", username);
        sendData.put("key", key);

        if(list.size() <= 0){
            result.put("httpCode", "401");
//            result.put("httpMessage", "허용되지 않은 사용자 존재하지 않거나\n발송 대상 사용자가 없습니다.");
            result.put("httpMessage", "발송 대상 사용자가 없습니다.");
            return result;
        }

        sendData.put("receiver", list);
        Map<String, Object> header = getEmailHeader();
        Map<String, Object> httpResultMap = HttpConnectionUtil.httpsRequest(url, "POST", sendData, header);
        if(httpResultMap != null && "200".equals(httpResultMap.get("httpCode").toString())){
        	logger.debug("httpResultMap : " + httpResultMap.toString());
            result.put("httpcode", httpResultMap.get("httpCode"));
            result.put("httpMessage", httpResultMap.get("httpMessage"));
            result.put("status", httpResultMap.get("status"));
            if(!"0".equals(httpResultMap.get("status").toString())){
                result.put("httpMessage", "이메일 발신 에러가 발생 하였습니다.");
            }
        }

        return result;
    }

    @Override
    public SOMap sendKakaoMessage(SOMap param, List<SOMap> list) {
        SOMap result = new SOMap();

        if(!"".equals(param.getStr("userno")) && !"0".equals(param.getStr("userno"))){
            try {
                SOMap member = memberMapper.selectMember(param);
                if(Util.isEmpty(member) || !"T".equals(member.getStr("isifsms"))){
                    result.put("httpCode", "401");
                    result.put("httpMessage", "정보성 동의여부 확인 바랍니다.");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String url = prop.getProperty("cj.message.kakao.url");
        String profileKey = prop.getProperty("cj.message.kakao.profile.key");
        String id = prop.getProperty("cj.message.kakao.alarm.talk.id");
        String messageSenderNumber = prop.getProperty("cj.message.sender.number");

        Map<String, Object> header = getKakaoHeader();

        if(list.size() <= 0){
            result.put("httpCode", "401");
            result.put("httpMessage", "발송 대상 사용자가 없습니다.");
            return result;
        }


        for(SOMap row : list){
            List<Map<String, Object>> sendList = new ArrayList<>();
            Map<String, Object> sendData = new HashMap<>();
            sendData.put("msg_type", "AT");

            Map<String, Object> sendRow = new HashMap<>();
            sendRow.put("msg_key", Instant.now().getEpochSecond());
            sendRow.put("sub_id", id);
            sendRow.put("sender_number", messageSenderNumber);
            sendRow.put("receiver_number", row.get("number"));
            sendRow.put("msg", row.get("msg"));
            sendRow.put("sender_key", profileKey);
            sendRow.put("template_code", param.get("template"));
            sendRow.put("failback_data", getFailbackData(param, row));
            if(row.containsKey("userno") && !"".equals(row.getStr("userno"))){
                sendRow.put("userno", row.get("userno"));
            }
            if(row.containsKey("linkurl") && !"".equals(row.getStr("linkurl"))){
                sendRow.put("linkurl", row.get("linkurl"));
            }
            
            if(param.get("attachment") != null && param.get("attachment") !="") {
            	sendRow.put("attachment", param.get("attachment"));
            }

            sendList.add(sendRow);
            sendData.put("msg_data", sendList);

            Map<String, Object> httpResultMap = HttpConnectionUtil.httpsRequest(url, "POST", sendData, header);
            if(httpResultMap != null && "200".equals(httpResultMap.get("httpCode").toString())){
                result.put("httpcode", httpResultMap.get("httpCode"));
                result.put("httpMessage", httpResultMap.get("httpMessage"));

                for (Map<String, Object> sendResultRow : sendList) {
                    SOMap dbParam = new SOMap();

                    String userNo = (sendResultRow.containsKey("userno") && !"".equals(sendResultRow.get("userno").toString())) ? sendResultRow.get("userno").toString() : param.getStr("userno");
                    String linkUrl = (sendResultRow.containsKey("linkurl") && !"".equals(sendResultRow.get("linkurl").toString())) ? sendResultRow.get("linkurl").toString() : param.getStr("url");
                    dbParam.put("userno", userNo);
                    dbParam.put("template", param.getStr("template"));
                    dbParam.put("sender_number", messageSenderNumber);
                    dbParam.put("receiver_number", sendResultRow.get("receiver_number"));
                    dbParam.put("msg", sendResultRow.get("msg"));
                    dbParam.put("url", linkUrl);
                    if(!"".equals(userNo)){
                        memberMsgMapper.insertMemberMsg(dbParam);
                    }
                }

            }
        }
        return result;
    }
    
    @Override
    public SOMap sendMemberEmail(SOMap param) {
        //공통 데이터 (탑/푸터 로고 및 프론트도메인)
        Context context = getEmailCommonContext();
    	
        List<SOMap> targetList = new ArrayList<>();
        context.setVariable("content", param.get("content"));
        String html = templateEngine.process("MailMember", context);
        
        SOMap target = new SOMap();
        target.put("name", param.get("name"));
        target.put("email", param.get("email"));
        
        targetList.add(target);
       
        param.put("content",html);
        
        return sendEmail(param, targetList);
    }

    @Override
    public SOMap sendADAgreeInfo(SOMap param) throws Exception{
        //공통 데이터 (탑/푸터 로고 및 프론트도메인)
        Context context = getEmailCommonContext();

        List<SOMap> targetList = memberMapper.selectAdAgreeMemberList(param);
        context.setVariable("path1", String.format("%s/%s", context.getVariable("mainUrl"), "assets/message/img/icon-lock-black-40px.svg"));
        context.setVariable("path2", String.format("%s/%s", context.getVariable("mainUrl"), "assets/message/img/icon-email-arrow-gray-30px.svg"));
        context.setVariable("path3", String.format("%s/%s", context.getVariable("mainUrl"), "assets/message/img/icon-profile-black-40px.svg"));
        context.setVariable("path4", String.format("%s/%s", context.getVariable("mainUrl"), "assets/message/img/icon-email-arrow-gray-30px.svg"));
        context.setVariable("path5", String.format("%s/%s", context.getVariable("mainUrl"), "assets/message/img/icon-id-card-black-40px.svg"));
        context.setVariable("path6", String.format("%s/%s", context.getVariable("mainUrl"), "assets/message/img/icon-email-arrow-gray-30px.svg"));
        context.setVariable("path7", String.format("%s/%s", context.getVariable("mainUrl"), "assets/message/img/icon-mail-black-40px.svg"));

        /**
         * 현재 날짜 가져오기
         */
        LocalDate now = LocalDate.now();
        context.setVariable("date1", String.format("이 메일은 %s년 %s월 %s일 기준 D.PLOT의 광고 정보 수신에 동의하신 회원님을 대상으로 발송되는 수신 동의 여부 확인 안내 메일입니다. 관련 법령에 따라 아래와 같이 안내드리오니 이용에 참고하시기 바랍니다.", now.getYear(), now.getMonthValue(), now.getDayOfMonth()));

        String html = templateEngine.process("AgreeRecieve", context);

        for (SOMap map : targetList) {
        	StringBuffer note1 = new StringBuffer();
        	note1.append(map.getStr("isadmailing").equals("T")?"이메일(" + map.getStr("ademailagreedate") + ")":"");
        	note1.append(map.getStr("isadsms").equals("T") && map.getStr("isadmailing").equals("T")?" / ":"");
        	note1.append(map.getStr("isadsms").equals("T")?"문자메시지(" + map.getStr("adsmsagreedate") + ")":"");

            map.put("note1", String.format("ㆍ수신동의 항목 및 일자 : %s", note1.toString()));
        }


        param.put("subject", "[D.PLOT] 광고성 정보 수신동의 확인 안내");
        param.put("content", html);
	    return sendEmail(param, targetList);
    }

    @Override
    public SOMap sendDormantAccount(SOMap param) {
        SOMap result = new SOMap();
        //공통 데이터 (탑/푸터 로고 및 프론트도메인)
        Context context = getEmailCommonContext();
        List<SOMap> targetList = memberMapper.selectDormancyMemberList(param);

        context.setVariable("loginUrl", String.format("%s/%s", context.getVariable("mainUrl"), "member/login"));
        String html = templateEngine.process("DormantAccount", context);

        if(targetList.size() > 0) {
            param.put("subject", "[D.PLOT] 휴면계정 전환안내");
            param.put("content", html);

            SOMap resultEmail = sendEmail(param, targetList);
            result.put("emailResult", resultEmail);

            //카카오톡 시작
            String templateText = "[D.PLOT] 휴면계정 전환 안내\n" +
                    "\n" +
                    "안녕하세요. D.PLOT입니다. \n" +
                    "회원님의 계정이 한달 후 휴면회원으로 전환 될 예정입니다. \n" +
                    "\n" +
                    "▶로그인\n" +
                    "#{URL}\n" +
                    "\n" +
                    "▶고객센터 : #{고객센터번호}";

            param.put("template", "A_dormant01");
            param.put("url", getFullPath("/member/login"));

            for (SOMap row : targetList) {
                templateText = templateText.replace("#{URL}", getFullPath("/member/login"))
                        .replace("#{고객센터번호}", "1666-3642");
                row.put("msg", templateText);
                row.put("number", row.getStr("mobile"));
            }

            SOMap resultKakao = sendKakaoMessage(param, targetList);
            result.put("kakaoResult", resultKakao);
        }

        return result;
    }

    @Override
    public SOMap sendJoinMember(SOMap param) {
        SOMap result = new SOMap();

        Context context = getEmailCommonContext();
        context.setVariable("loginUrl", String.format("%s/%s", context.getVariable("mainUrl"), "member/login"));
        String html = templateEngine.process("SuccessMembership", context);

        String templateText = "[D.PLOT] 회원가입 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "회원 가입이 완료되었습니다.\n" +
                "\n" +
                "<회원가입 정보>\n" +
                "■ 아이디: "+ param.get("userid") +"\n" +
                "\n" +
                "많은 이용 부탁드립니다.\n" +
                "감사합니다.";

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("name", param.get("username"));
        map.put("email", param.get("email"));
        map.put("note1", param.get("userid"));
        map.put("note2", param.get("regdate"));
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] 회원가입을 축하합니다.");
        param.put("content", html);

        SOMap resultEmail = sendEmail(param, targetList);
        param.put("template", "A_newjoin");
        SOMap resultKakao = sendKakaoMessage(param, targetList);

        result.put("emailResult", resultEmail);
        result.put("kakaoResult", resultKakao);

        return result;
    }

    @Override
    public SOMap sendPersonalInfo(SOMap param) {
        Context context = getEmailCommonContext();
        // TODO :  개인정보 처리방침 전문 URL 추가 해야됨
        List<SOMap> targetList = memberMapper.selectPersonalInfoMemberList(param);

        context.setVariable("privateUrl", String.format("%s/%s", context.getVariable("mainUrl"), "common/private/policy"));
        String html = templateEngine.process("PersonalInformation", context);

        param.put("subject", "[D.PLOT] 개인정보 이용내역 안내");
        param.put("content", html);
        return sendEmail(param, targetList);
    }

    @Override
    public SOMap sendNewsletterApply(SOMap param) {
        Context context = getEmailCommonContext();

        List<SOMap> targetList = new ArrayList<>();
        context.setVariable("loginUrl", String.format("%s/%s", context.getVariable("mainUrl"), "member/login"));
        context.setVariable("refuseUrl", String.format("%s%s", context.getVariable("mainUrl"), param.getStr("url")));
        String html = templateEngine.process("NewsLetterApplication", context);

        SOMap target = new SOMap();
        target.put("email", param.get("email"));
        targetList.add(target);

        param.put("subject", "[D.PLOT] 뉴스레터 신청을 감사 드립니다.");
        param.put("content", html);
        return sendEmail(param, targetList);
    }

    @Override
    public SOMap sendEmpAuthNumber(SOMap param) {
        Context context = getEmailCommonContext();
        context.setVariable("title", param.getStr("title"));
        context.setVariable("authNumber", param.getStr("number"));
        String html = templateEngine.process("executivesEmail", context);
        List<SOMap> targetList = new ArrayList<>();
        SOMap target = new SOMap();
        target.put("email", param.getStr("email"));
        targetList.add(target);

        String subject = (param.getStr("title").equals("임직원")) ? "[D.PLOT] 임직원 회원가입 인증번호 안내." : "[D.PLOT] 인증번호 안내.";
        param.put("subject", subject);
        param.put("content", html);
        return sendEmail(param, targetList);
    }

    @Override
    public SOMap sendPartnersApply(SOMap param) {

        Context context = getEmailCommonContext();
        context.setVariable("companyname", param.getStr("companyname"));
        context.setVariable("manager", param.getStr("manager"));
        context.setVariable("businessnumber", param.getStr("businessnumber"));
        context.setVariable("manageremail", param.getStr("manageremail"));
        context.setVariable("homepageurl", param.getStr("homepageurl"));
        context.setVariable("managerphone", param.getStr("managerphone"));
        context.setVariable("mainproduct", param.getStr("mainproduct"));
        context.setVariable("brandurl", param.getStr("brandurl"));
        context.setVariable("etc", param.getStr("etc"));

        String html = templateEngine.process("EntranceInquiry", context);
        List<SOMap> targetList = new ArrayList<>();
        SOMap target = new SOMap();
        String partnerMail = prop.getProperty("cj.message.email.partner.sender");
        target.put("email", partnerMail);
        targetList.add(target);

        param.put("subject", "[D.PLOT] 입점/제휴 문의");
        param.put("content", html);

        param.put("sender", param.getStr("manageremail"));
        param.put("sender_name", param.getStr("manager"));

        return sendEmail(param, targetList);
    }

    @Override
    public SOMap sendAsApply(SOMap param) {

        String templateText = "[D.PLOT] AS 신청 안내\n" +
                "\n" +
                "안녕하세요.D.PLOT입니다. \n" +
                "고객님의 AS 신청이 접수되었습니다. \n" +
                "접수하신 내용은 담당자 확인 후 AS 절차가 진행됩니다.\n" +
                "\n" +
                "▶ AS 내역 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        templateText = templateText.replace("#{URL}", getFullPath("/mypage/as/detail/"+param.getStr("asidx")))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] AS 신청 안내");
        param.put("template", "A_afterservice01");
        param.put("url", getFullPath("/mypage/as/detail/"+param.getStr("asidx")));

        return sendKakaoMessage(param, targetList);
    }

    @Override
    public SOMap sendAsComplete(SOMap param) {

        String templateText = "[D.PLOT] AS 완료 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "고객님의 AS 신청 건 처리가 완료되었습니다. \n" +
                "앞으로도 더 나은 서비스를 제공하기 위해 최선을 다하겠습니다.\n" +
                "\n" +
                "▶AS 내역 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        templateText = templateText.replace("#{URL}", getFullPath("/mypage/as/detail/"+param.getStr("asidx")))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] AS 완료 안내");
        param.put("template", "A_afterservice02");
        param.put("url", getFullPath("/mypage/as/detail/"+param.getStr("asidx")));

        return sendKakaoMessage(param, targetList);
    }

    @Override
    public SOMap sendCustomerQuestionsAndAnswer(SOMap param) {
        String templateText = "[D.PLOT] 문의 답변 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "회원님께서 작성하신 #{문의유형}의 답변이 완료 되었습니다. \n" +
                "\n" +
                "▶ 답변 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        String retUrl = ("1:1문의".equals(param.getStr("typename"))) ? getFullPath(String.format("/cs/inquiry/detail/inquiry/%s", param.getStr("idx"))) : getFullPath(String.format("/cs/inquiry/detail/qna/%s", param.getStr("idx")));

        templateText = templateText.replace("#{문의유형}", param.getStr("typename"))
                .replace("#{URL}", retUrl)
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] 문의 답변 안내");
        param.put("template", "A_inquiry");
        param.put("url", retUrl);

        return sendKakaoMessage(param, targetList);
    }

    @Override
    public SOMap sendDepositWithOutBankBook(SOMap param) {
        SOMap result = new SOMap();
        String templateText = "[D.PLOT] 무통장입금 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다.\n" +
                "고객님의 주문이 접수되었습니다.\n" +
                "빠른 배송을 위해 아래 계좌로 입금해 주세요.\n" +
                "\n" +
                "■ 주문번호 : #{주문번호} \n" +
                "■ 주문일자 : #{주문일자}\n" +
                "■ 주문상품 : #{주문상품개수}\n" +
                "■ 입금하실 금액 : #{입금금액} 원\n" +
                "■ 입금은행 : #{은행명}\n" +
                "■ 입금계좌 : #{계좌번호}\n" +
                "■ 예금주 : #{예금주}\n" +
                "■ 입금기한 : #{입금기한} 까지\n" +
                "\n" +
                "* 주문하신 최종 결제 금액과 입금 금액이 동일해야 결제완료 처리됩니다.\n" +
                "* 주문 접수 후 입금기한 일자 이내 미입금시 주문이 자동취소 됩니다. \n" +
                "\n" +
                "▶주문내역 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶고객센터 : #{고객센터번호}";

        String orderGoodsText = param.getStr("goodsname");
        if(param.getInt("ordcnt") > 0){
            orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
        }

        templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
                .replace("#{주문일자}", param.getStr("orderdate"))
                .replace("#{주문상품개수}", orderGoodsText)
                .replace("#{입금금액}", NumberFormat.getInstance().format(param.getInt("ordamt")))
                .replace("#{은행명}", param.getStr("bankname"))
                .replace("#{계좌번호}", param.getStr("accoutno"))
                .replace("#{예금주}", param.getStr("accountholder"))
                .replace("#{입금기한}", param.getStr("depositdate"))
                .replace("#{URL}", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] 무통장입금 안내");
        param.put("template", "A_deposit");
        param.put("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));

        SOMap resultKakao = sendKakaoMessage(param, targetList);
        result.put("kakaoResult", resultKakao);

        Context context = getEmailCommonContext();
        List<SOMap> emailTargetList = new ArrayList<>();

        String planMonthName = ("".equals(param.getStr("planmonth")) || "0".equals(param.getStr("planmonth"))) ? "일시불" : param.getStr("planmonth") + "개월 할부";
        context.setVariable("planmonthname", planMonthName);

        //고객정보
        String orderDate = param.getStr("orderday").replaceAll("-", ".");
        context.setVariable("ordname", param.get("ordname"));
        context.setVariable("ordno", param.get("ordno"));
        context.setVariable("orderdate", orderDate);
        context.setVariable("dadamembertype", param.get("dadamembertype"));

        context.setVariable("cardcompany", param.get("cardcompany"));
        context.setVariable("cashreceiptkey", param.get("cashreceiptkey"));
        context.setVariable("cashreceipttype", param.get("cashreceipttype"));
        //딜러정보 및 상품리스트
        context.setVariable("dealerinfo", param.get("dealerinfo"));
        context.setVariable("promotion", param.get("promotion"));

        //수령인정보
        context.setVariable("rcvname", param.get("rcvname"));
        context.setVariable("rcvtel1", Util.phoneFormat(param.getStr("rcvtel1")));
        context.setVariable("rcvaddrroad", param.getStr("addr"));
        context.setVariable("rcvreqdetail", param.getStr("rcvreqdetail"));
        context.setVariable("rcvreqtypenm", param.getStr("rcvreqtypenm"));
        context.setVariable("rcvreqtype", param.getStr("rcvreqtype"));

        //결제정보
        context.setVariable("ordtotprice", param.get("ordtotprice"));
        context.setVariable("dadadelivamt", param.get("dadadelivamt"));
        context.setVariable("ptndelivamt", param.get("ptndelivamt"));

        context.setVariable("basketcpnamt", param.get("basketcpnamt"));
        context.setVariable("totsalepromoamt", param.get("totsalepromoamt"));
        context.setVariable("totgoodscpnamt", param.get("totgoodscpnamt"));
        context.setVariable("totdelivcpnamt", param.get("totdelivcpnamt"));
        context.setVariable("paytotprice", param.get("paytotprice"));

        context.setVariable("reservetotamt", param.get("reservetotamt"));
        context.setVariable("epointtotamt", param.get("epointtotamt"));
        context.setVariable("empreservetotamt", param.get("empreservetotamt"));
        context.setVariable("paywaytype", param.get("paywaytype"));
        context.setVariable("paywaytypename", param.get("paywaytypename"));
        context.setVariable("trsbank", param.get("trsbank"));
        context.setVariable("cardcompanyname", param.get("cardcompanyname"));
        context.setVariable("paytotprice", param.get("paytotprice"));
        context.setVariable("virbank", param.get("virbank"));
        context.setVariable("accntnumber", param.get("accntnumber"));
        context.setVariable("vircusname", param.get("vircusname"));
        context.setVariable("virdueday", DateTimeUtil.getDateKrFormat(param.getStr("virdueday")));
        context.setVariable("rpaytotprice", param.get("rpaytotprice"));
        context.setVariable("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));
        context.setVariable("termurl", getFullPath("/termservice.html"));
        context.setVariable("drawurl", getFullPath("/withdrawSubscription.html"));

        String html = templateEngine.process("OrderDetail", context);
        param.remove("template");

        SOMap target = new SOMap();
        target.put("email", param.get("ordemail"));
        emailTargetList.add(target);

        param.put("content", html);
        SOMap resultEmail = sendEmail(param, emailTargetList);
        result.put("emailResult", resultEmail);

        return result;
    }

    @Override
    public SOMap sendShippingOut(SOMap param) {
        String templateText = "[D.PLOT] 배송 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "고객님께서 주문하신 상품이 발송되었습니다. \n" +
                "\n" +
                "■ 주문번호 : #{주문번호} \n" +
                "■ 주문일자 : #{주문일자}\n" +
                "■ 주문상품 : #{주문상품개수}\n" +
                "■ 배송정보 : #{택배사} (#{송장번호})\n" +
                "■ 배송지 : #{기본주소}\n" +
                "\n" +
                "▶ 배송정보 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        String orderGoodsText = param.getStr("goodsname");
        if(param.getInt("ordcnt") > 0){
            orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
        }

        templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
                .replace("#{주문일자}", param.getStr("orderdate"))
                .replace("#{주문상품개수}", orderGoodsText)
                .replace("#{택배사}", param.getStr("logistypename"))
                .replace("#{송장번호}", param.getStr("invoiceno"))
                .replace("#{기본주소}", param.getStr("addr"))
                .replace("#{URL}", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] 배송 안내");
        param.put("template", "A_delivery01");
        param.put("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));

        return sendKakaoMessage(param, targetList);
    }

	@Override
	public SOMap sendOrderCancelBeforeDelivery(SOMap param) {
	String templateText = 
			"[D-PLOT] 주문취소 안내\n"
			+ "\n"
			+ "안녕하세요. D-PLOT입니다.\n"
			+ "고객님께서 요청하신 주문이 취소 처리 되었습니다. \n"
			+ "\n"
			+ "■ 주문번호 : #{주문번호} \n"
			+ "■ 주문일자 : #{주문일자}\n"
			+ "■ 취소상품 : #{취소상품}\n"
			+ "■ 취소금액 : 총 #{총취소금액}원\n"
			+ "#{취소금액내역}\n"
			+ "\n"
			+ "※ 최종환불은 결제 수단에 따라 영업일 기준 2~5일 정도 소요되며, 사용하신 적립금 혹은 포인트는 즉시 환급됩니다.\n"
			+ "\n"
			+ "▶ 고객센터 : #{고객센터번호}";
	
		String orderGoodsText = param.getStr("goodsname");
		if(param.getInt("ordcnt") > 0){
			orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
		}
		String payWayTypeName = param.getStr("cancelwaytype");
		String ordAmtList = "";
		
		if(param.getInt("rtnpayamt") > 0) {
			ordAmtList += payWayTypeName + " " + NumberFormat.getInstance().format(param.getInt("rtnpayamt")) + "원";
		}
		if(param.getInt("rtnresamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "적립금 " + NumberFormat.getInstance().format(param.getInt("rtnresamt")) + "원";
		}
		if(param.getInt("rtnempresamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "임직원적립금 " + NumberFormat.getInstance().format(param.getInt("rtnempresamt")) + "원";
		}
		int totalAmt = param.getInt("rtnpayamt") + param.getInt("rtnresamt") + param.getInt("rtnempresamt");
		templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
			.replace("#{주문일자}", param.getStr("orderdate"))
			.replace("#{취소상품}", orderGoodsText)
			.replace("#{총취소금액}", NumberFormat.getInstance().format(totalAmt))
			.replace("#{취소금액내역}", ordAmtList)
			.replace("#{고객센터번호}", "1666-3642");

		List<SOMap> targetList = new ArrayList<>();
		SOMap map = new SOMap();
		map.put("msg", templateText);
		map.put("number", param.get("number"));
		targetList.add(map);

		// 버튼추가사항
		SOMap attachment = new SOMap();
		List<SOMap> button = new ArrayList<>();
		SOMap btnInfo = new SOMap();
		btnInfo.put("name", "취소 내역 확인");
		btnInfo.put("type", "WL");
		btnInfo.put("url_pc", "https://www.dplot.co.kr/mypage/claim");
		btnInfo.put("url_mobile", "https://m.dplot.co.kr/mypage/claim");
		button.add(btnInfo);
		attachment.put("button", button);
		
		param.put("attachment", attachment);
		param.put("subject", "[D-PLOT] 주문취소 안내");
		param.put("template", "ordercancel3");
		param.put("url", getFullPath(String.format("/mypage/claim/cancel-detail/%s", param.getStr("clmno"))));

		return sendKakaoMessage(param, targetList);
	}

	@Override
	public SOMap sendPayComplete(SOMap param) {
		SOMap result = new SOMap();
		String templateText = 
				"[D-PLOT] 주문 완료 안내\n"
				+ "\n"
				+ "안녕하세요. D-PLOT입니다. \n"
				+ "고객님의 주문이 완료 되었습니다. \n"
				+ "주문해 주셔서 감사합니다.\n"
				+ "\n"
				+ "■ 주문번호 : #{주문번호} \n"
				+ "■ 주문일자 : #{주문일자}\n"
				+ "■ 주문상품 : #{주문상품}\n"
				+ "■ 배송지 : #{기본주소}\n"
				+ "■ 결제금액 : 총 #{총결제금액}원\n"
				+ "#{주문금액내역}\n"
				+ "\n"
				+ "▶고객센터 : #{고객센터번호}";

		String orderGoodsText = param.getStr("goodsname");
		if(param.getInt("ordcnt") > 0){
			orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
		}
		
		String payWayTypeName = param.getStr("paywaytypename");
		String ordAmtList = "";
		if(param.getInt("rpaytotprice") > 0) {
			ordAmtList += payWayTypeName + " " + NumberFormat.getInstance().format(param.getInt("rpaytotprice")) + "원";
		}
		if(param.getInt("reservetotamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "적립금 " + NumberFormat.getInstance().format(param.getInt("reservetotamt")) + "원";
		}
		if(param.getInt("empreservetotamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "임직원적립금 " + NumberFormat.getInstance().format(param.getInt("empreservetotamt")) + "원";
		}

		int totAmt = param.getInt("rpaytotprice") + param.getInt("reservetotamt") + param.getInt("empreservetotamt");
		templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
				.replace("#{주문일자}", param.getStr("orderdt"))
				.replace("#{주문상품}", orderGoodsText)
				.replace("#{기본주소}", param.getStr("addr"))
				.replace("#{총결제금액}", NumberFormat.getInstance().format(totAmt))
				.replace("#{주문금액내역}", ordAmtList)
				.replace("#{고객센터번호}", "1666-3642");
		
		List<SOMap> targetList = new ArrayList<>();
		SOMap map = new SOMap();
		map.put("msg", templateText);
		map.put("number", param.get("number"));
		targetList.add(map);
		
		// 버튼추가사항
		SOMap attachment = new SOMap();
		List<SOMap> button = new ArrayList<>();
		SOMap btnInfo = new SOMap();
		btnInfo.put("name", "주문 내역 조회");
		btnInfo.put("type", "WL");
		btnInfo.put("url_pc", "https://www.dplot.co.kr/mypage/order");
		btnInfo.put("url_mobile", "https://m.dplot.co.kr/mypage/order");
		button.add(btnInfo);
		attachment.put("button", button);

		param.put("attachment", attachment);
		param.put("subject", "[D-PLOT] 주문 완료 안내");
		param.put("template", "ordercomplete2");
		param.put("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));

		SOMap resultKakao = sendKakaoMessage(param, targetList);
		result.put("kakaoResult", resultKakao);

		Context context = getEmailCommonContext();
		List<SOMap> emailTargetList = new ArrayList<>();

		String planMonthName = ("".equals(param.getStr("planmonth")) || "0".equals(param.getStr("planmonth"))) ? "일시불" : param.getStr("planmonth") + "개월 할부";
		context.setVariable("planmonthname", planMonthName);

        //고객정보
        String orderDate = param.getStr("orderday").replaceAll("-", ".");
        context.setVariable("ordname", param.get("ordname"));
        context.setVariable("ordno", param.get("ordno"));
        context.setVariable("orderdate", orderDate);
        context.setVariable("dadamembertype", param.get("dadamembertype"));

        context.setVariable("cardcompany", param.get("cardcompany"));
        context.setVariable("cashreceiptkey", param.get("cashreceiptkey"));
        context.setVariable("cashreceipttype", param.get("cashreceipttype"));
        //딜러정보 및 상품리스트
        context.setVariable("dealerinfo", param.get("dealerinfo"));
        context.setVariable("promotion", param.get("promotion"));

        //수령인정보
        context.setVariable("rcvname", param.get("rcvname"));
        context.setVariable("rcvtel1", Util.phoneFormat(param.getStr("rcvtel1")));
        context.setVariable("rcvaddrroad", param.getStr("addr"));
        context.setVariable("rcvreqdetail", param.getStr("rcvreqdetail"));
        context.setVariable("rcvreqtypenm", param.getStr("rcvreqtypenm"));
        context.setVariable("rcvreqtype", param.getStr("rcvreqtype"));



        //결제정보
        context.setVariable("ordtotprice", param.get("ordtotprice"));
        context.setVariable("dadadelivamt", param.get("dadadelivamt"));
        context.setVariable("ptndelivamt", param.get("ptndelivamt"));

        context.setVariable("basketcpnamt", param.get("basketcpnamt"));
        context.setVariable("totsalepromoamt", param.get("totsalepromoamt"));
        context.setVariable("totgoodscpnamt", param.get("totgoodscpnamt"));
        context.setVariable("totdelivcpnamt", param.get("totdelivcpnamt"));
        context.setVariable("paytotprice", param.get("paytotprice"));

        context.setVariable("reservetotamt", param.get("reservetotamt"));
        context.setVariable("epointtotamt", param.get("epointtotamt"));
        context.setVariable("empreservetotamt", param.get("empreservetotamt"));
        context.setVariable("paywaytype", param.get("paywaytype"));
        context.setVariable("paywaytypename", param.get("paywaytypename"));
        context.setVariable("trsbank", param.get("trsbank"));
        context.setVariable("cardcompanyname", param.get("cardcompanyname"));
        context.setVariable("paytotprice", param.get("paytotprice"));
        context.setVariable("virbank", param.get("virbank"));
        context.setVariable("accntnumber", param.get("accntnumber"));
        context.setVariable("vircusname", param.get("vircusname"));
        context.setVariable("virdueday", DateTimeUtil.getDateKrFormat(param.getStr("virdueday")));
        context.setVariable("rpaytotprice", param.get("rpaytotprice"));
        context.setVariable("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));
        context.setVariable("termurl", getFullPath("/termservice.html"));
        context.setVariable("drawurl", getFullPath("/withdrawSubscription.html"));

        String html = templateEngine.process("OrderDetail", context);
        param.remove("template");

        SOMap target = new SOMap();
        target.put("email", param.get("ordemail"));
        emailTargetList.add(target);

        param.put("content", html);
        SOMap resultEmail = sendEmail(param, emailTargetList);
        result.put("emailResult", resultEmail);

        return result;
    }

    @Override
    public SOMap sendInquiryrReception(SOMap param) {
        String templateText = "[D.PLOT] #{반품/교환} 접수 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "고객님의 #{반품/교환} 접수가 완료 되었습니다. \n" +
                " \n" +
                "■ #{반품/교환}상품: #{주문상품개수}\n" +
                "\n" +
                "고객님께 더 나은 서비스를 제공하도록 노력하겠습니다.\n" +
                "\n" +
                "▶ #{반품/교환} 내역 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        String orderGoodsText = param.getStr("goodsname");
        if(param.getInt("ordcnt") > 0){
            orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
        }

        String sendUrl = ("반품".equals(param.getStr("claimtypename"))) ? String.format("/mypage/claim/return-detail/%s", param.getStr("clmno")) : String.format("/mypage/claim/exchange-detail/%s", param.getStr("clmno"));

        templateText = templateText.replaceAll("#\\{반품/교환}", param.getStr("claimtypename"))
                .replace("#{주문상품개수}", orderGoodsText)
                .replace("#{URL}", getFullPath(sendUrl))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        String subject = String.format("[D.PLOT] %s 접수 안내", param.getStr("claimtypename"));
        param.put("subject", subject);
        param.put("template", "A_ordercancell04");
        param.put("url", getFullPath(sendUrl));

        return sendKakaoMessage(param, targetList);
    }

	@Override
	public SOMap sendRefundComplete(SOMap param) {
		String templateText = 
				"[D-PLOT] 환불 안내\n"
				+ "\n"
				+ "안녕하세요. D-PLOT입니다.\n"
				+ "고객님의 주문이 환불 처리 되었습니다. \n"
				+ "\n"
				+ "■ 주문번호 : #{주문번호} \n"
				+ "■ 주문일자 : #{주문일자}\n"
				+ "■ 반품상품 : #{상품명}\n"
				+ "■ 환불금액 : 총 #{총환불금액}원\n"
				+ "#{환불금액내역}\n"
				+ "\n"
				+ "▶ 고객센터 : #{고객센터번호}";

		String orderGoodsText = param.getStr("goodsname");
		if(param.getInt("clmcnt") > 0){
			orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("clmcnt")));
		}

		String payWayTypeName = param.getStr("refundwaytype");
		String ordAmtList = "";
		if(param.getInt("refundamt") > 0) {
			ordAmtList += payWayTypeName + " " + NumberFormat.getInstance().format(param.getInt("refundamt")) + "원";
		}
		if(param.getInt("rtnresamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "적립금 " + NumberFormat.getInstance().format(param.getInt("rtnresamt")) + "원";
		}
		if(param.getInt("rtnempresamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "임직원적립금 " + NumberFormat.getInstance().format(param.getInt("rtnempresamt")) + "원";
		}

		int totAmt = param.getInt("refundamt") + param.getInt("reservetotamt") + param.getInt("empreservetotamt");
		templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
				.replace("#{주문일자}", param.getStr("orderdate"))
				.replace("#{상품명}", orderGoodsText)
				.replace("#{총환불금액}", NumberFormat.getInstance().format(totAmt))
				.replace("#{환불금액내역}", ordAmtList)
				.replace("#{고객센터번호}", "1666-3642");

		List<SOMap> targetList = new ArrayList<>();
		SOMap map = new SOMap();
		map.put("msg", templateText);
		map.put("number", param.get("number"));
		targetList.add(map);
		
		// 버튼추가사항
		SOMap attachment = new SOMap();
		List<SOMap> button = new ArrayList<>();
		SOMap btnInfo = new SOMap();
		btnInfo.put("name", "환불 내역 조회");
		btnInfo.put("type", "WL");
		btnInfo.put("url_pc", "https://www.dplot.co.kr/mypage/claim");
		btnInfo.put("url_mobile", "https://m.dplot.co.kr/mypage/claim");
		button.add(btnInfo);
		attachment.put("button", button);
		
		param.put("attachment", attachment);
		param.put("subject", "[D-PLOT] 환불 안내");
		param.put("template", "Refundcomplete2");
		param.put("url", getFullPath(String.format("/mypage/claim/return-detail/%s", param.getStr("clmno"))));

		return sendKakaoMessage(param, targetList);
	}

    @Override
    public SOMap sendOutstandingDepositCancel(SOMap param) {
        String templateText = "[D-PLOT] 주문취소 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "고객님의 주문이 미입금으로 인해 취소되었습니다. \n" +
                "\n" +
                "■ 주문번호 : #{주문번호} \n" +
                "■ 주문일자 : #{주문일자}\n" +
                "■ 취소상품 : #{주문상품개수}\n" +
                "\n" +
                "▶ 취소내역 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        String orderGoodsText = param.getStr("goodsname");
        if(param.getInt("ordcnt") > 0){
            orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
        }

        templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
                .replace("#{주문일자}", param.getStr("orderdate"))
                .replace("#{주문상품개수}", orderGoodsText)
                .replace("#{URL}", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D-PLOT] 주문취소 안내");
        param.put("template", "A_ordercancell02");
        param.put("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));

        return sendKakaoMessage(param, targetList);
    }

    @Override
    public SOMap sendOutstandingDepositCancel2(SOMap param) {
        String templateText = "[D.PLOT] 주문취소 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "고객님의 주문이 취소되었습니다. \n" +
                "\n" +
                "■ 주문번호 : #{주문번호} \n" +
                "■ 주문일자 : #{주문일자}\n" +
                "■ 취소상품 : #{주문상품개수}\n" +
                "\n" +
                "▶ 취소내역 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        String orderGoodsText = param.getStr("goodsname");
        if(param.getInt("ordcnt") > 0){
            orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
        }

        templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
                .replace("#{주문일자}", param.getStr("orderdate"))
                .replace("#{주문상품개수}", orderGoodsText)
                .replace("#{URL}", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] 주문취소 안내");
        param.put("template", "A_ordercancell06");
        param.put("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));

        return sendKakaoMessage(param, targetList);
    }

	@Override
	public SOMap sendManagerOrderCancel(SOMap param) {
		String templateText =
				"[D-PLOT] 주문취소 안내\n"
				+ "\n"
				+ "안녕하세요. D-PLOT입니다.\n"
				+ "고객님께서 요청하신 주문이 취소 처리 되었습니다. \n"
				+ "\n"
				+ "■ 주문번호 : #{주문번호} \n"
				+ "■ 주문일자 : #{주문일자}\n"
				+ "■ 취소상품 : #{취소상품}\n"
				+ "■ 취소금액 : 총 #{총취소금액}원\n"
				+ "#{취소금액내역}\n"
				+ "\n"
				+ "※ 최종환불은 결제 수단에 따라 영업일 기준 2~5일 정도 소요되며, 사용하신 적립금 혹은 포인트는 즉시 환급됩니다.\n"
				+ "\n"
				+ "▶ 고객센터 : #{고객센터번호}";
		
		String orderGoodsText = param.getStr("goodsname");
		if(param.getInt("ordcnt") > 0){
			orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
		}
		
		String payWayTypeName = param.getStr("cancelwaytype");
		String ordAmtList = "";
		if(param.getInt("rtnpayamt") > 0) {
			ordAmtList += payWayTypeName + " " + NumberFormat.getInstance().format(param.getInt("rtnpayamt")) + "원";
		}
		if(param.getInt("rtnresamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "적립금 " + NumberFormat.getInstance().format(param.getInt("rtnresamt")) + "원";
		}
		if(param.getInt("rtnempresamt") > 0) {
			if(ordAmtList != "") {
				ordAmtList += " / ";
			}
			ordAmtList += "임직원적립금 " + NumberFormat.getInstance().format(param.getInt("rtnempresamt")) + "원";
		}
		
		int totalAmt = param.getInt("rtnpayamt") + param.getInt("rtnresamt") + param.getInt("rtnempresamt");
		templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
				.replace("#{주문일자}", param.getStr("orderdate"))
				.replace("#{취소상품}", orderGoodsText)
				.replace("#{총취소금액}", NumberFormat.getInstance().format(totalAmt))
				.replace("#{취소금액내역}", ordAmtList)
				.replace("#{고객센터번호}", "1666-3642");

		List<SOMap> targetList = new ArrayList<>();
		SOMap map = new SOMap();
		map.put("msg", templateText);
		map.put("number", param.get("number"));
		targetList.add(map);
		
		// 버튼추가사항
		SOMap attachment = new SOMap();
		List<SOMap> button = new ArrayList<>();
		SOMap btnInfo = new SOMap();
		btnInfo.put("name", "취소 내역 확인");
		btnInfo.put("type", "WL");
		btnInfo.put("url_pc", "https://www.dplot.co.kr/mypage/claim");
		btnInfo.put("url_mobile", "https://m.dplot.co.kr/mypage/claim");
		button.add(btnInfo);
		attachment.put("button", button);
		
		param.put("attachment", attachment);
		param.put("subject", "[D-PLOT] 주문취소 안내");
		param.put("template", "ordercancel3");
		param.put("url", getFullPath(String.format("/mypage/claim/cancel-detail/%s", param.getStr("clmno"))));

		return sendKakaoMessage(param, targetList);
	}

    @Override
    public SOMap sendDeliveryComplete(SOMap param) {
        String templateText = "[D.PLOT] 배송 완료 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "주문하신 상품의 배송이 완료되었습니다.\n" +
                "\n" +
                "■ 주문번호 : #{주문번호} \n" +
                "■ 주문일자 : #{주문일자}\n" +
                "■ 주문상품 : #{주문상품개수}\n" +
                "\n" +
                "▶배송정보 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        String orderGoodsText = param.getStr("goodsname");
        if(param.getInt("ordcnt") > 0){
            orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
        }

        templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
                .replace("#{주문일자}", param.getStr("orderdate"))
                .replace("#{주문상품개수}", orderGoodsText)
                .replace("#{URL}", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] 배송 완료 안내");
        param.put("template", "A_delivery02_2");
        param.put("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));

        return sendKakaoMessage(param, targetList);
    }

    @Override
    public SOMap sendRestockInfo(SOMap param) {
        SOMap result = new SOMap();
        String templateText = "[D.PLOT] 재입고 알림\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "고객님께서 재입고 알림 신청하신 상품이 입고 되었습니다. \n" +
                "입고 후에도 조기에 품절 될 수 있으니 가급적 서둘러 구매하시기 바랍니다.\n" +
                "\n" +
                "■ 상품명 : #{상품명}\n" +
                "\n" +
                "▶ 재입고 상품 확인\n" +
                " #{URL}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        List<SOMap> targetList = new ArrayList<>();
        param.put("siteid", "base");
        List<SOMap> restockList = rewareMapper.selectRewareTargetList(param);

        for(SOMap row : restockList){
            String text = templateText.replace("#{상품명}", row.getStr("goodsname"))
                    .replace("#{URL}", getFullPath(String.format("/shop/detail/%s", row.getStr("goodscode"))))
                    .replace("#{고객센터번호}", "1666-3642");

            row.put("msg", text);
            row.put("number", row.get("mobile"));
            row.put("linkUrl", getFullPath(String.format("/shop/detail/%s", row.getStr("goodscode"))));
            targetList.add(row);
        }

        param.put("subject", "[D.PLOT] 재입고 알림");
        param.put("template", "A_restocking");

        SOMap kakaoResult = sendKakaoMessage(param, targetList);

        if("200".equals(kakaoResult.getStr("httpcode"))){
            List<String> idxArr = targetList.stream().map(a-> a.getStr("rewidx")).collect(Collectors.toList());
            SOMap notiParam = new SOMap();
            notiParam.put("list", idxArr);
            rewareMapper.updateRestockNotiByIdx(notiParam);
        }

        return result;
    }

    @Override
    public SOMap sendReserveExtinct(SOMap param) {
        String templateText = "[D.PLOT] 적립금 소멸 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다. \n" +
                "회원님의 이번달 소멸예정 적립금을 안내드립니다.\n" +
                "\n" +
                "■ 소멸예정 적립금 : #{소멸예정적립금} 원\n" +
                "■ 소멸예정 일 : #{소멸예정일} 까지 \n" +
                "\n" +
                "*해당 적립금은 상품 구매 및 리뷰 작성 등의 보상으로 지급되었습니다.\n" +
                "*자세한 내용은 D.PLOT 사이트 및 모바일 앱에서 확인해주세요.\n" +
                "*소멸된 적립금은 복원되지 않습니다.\n" +
                "\n" +
                "▶적립금 확인\n" +
                "#{URL}\n" +
                "\n" +
                "▶고객센터 : 1666-3642";

        templateText = templateText.replace("#{URL}", getFullPath("/mypage/reward"));

        List<SOMap> targetList = reserveMapper.selectReserveExpireList(param);
        for(SOMap row : targetList){
            String msg = templateText.replace("#{소멸예정적립금}", row.getStr("rpoint")).replace("#{소멸예정일}", row.getStr("extinctdate"));
            row.put("msg", msg);
            row.put("number", row.getStr("mobile"));
        }

        param.put("subject", "[D.PLOT] 적립금 소멸 안내");
        param.put("template", "A_mileageloss");
        param.put("url", getFullPath("/mypage/reward"));

        return sendKakaoMessage(param, targetList);
    }

    @Override
    public SOMap sendDirectDelivery(SOMap param) {


        String templateText = "[D.PLOT] 배송 안내\n" +
                "\n" +
                "안녕하세요. D.PLOT입니다.  \n" +
                "고객님의 상품이 배송 될 예정입니다.\n" +
                "\n" +
                "■ 주문번호 : #{주문번호}\n" +
                "■ 주문일자 : #{주문일자}\n" +
                "■ 배송형태 : 직접배송\n" +
                "■ 주문상품 : #{상품주문개수}\n" +
                "■ 배송지: #{기본주소}\n" +
                "\n" +
                "* 직접배송의 경우 별도 배송 차량을 통해 배송기사님께서 직접 방문드립니다. \n" +
                "* 기사님과 통화 지연시 배송이 지연될 수 있으므로, 모르는 번호도 꼭 수신 부탁드립니다. \n" +
                "* 수도권의 경우 주말 제외 #{수도권배송일}, 지방의 경우 최대 #{지방배송일} 소요되며 일부지역은 배달이 불가할 수 있습니다. (상품상세 참고)\n" +
                "* 주문량 증가 및 입고 지연으로 일부 지연 상품이 있을 수 있습니다. (상품상세 참고)\n" +
                "\n" +
                "▶ 주문 내역 확인\n" +
                "#{url}\n" +
                "\n" +
                "▶ 고객센터 : #{고객센터번호}";

        String orderGoodsText = param.getStr("goodsname");
        if(param.getInt("ordcnt") > 0){
            orderGoodsText = String.format("%s 외 %s건", param.getStr("goodsname"), NumberFormat.getInstance().format(param.getInt("ordcnt")));
        }

        templateText = templateText.replace("#{주문번호}", param.getStr("ordno"))
                .replace("#{주문일자}", param.getStr("orderdate"))
                .replace("#{상품주문개수}", orderGoodsText)
                .replace("#{기본주소}", param.getStr("addr"))
                .replace("#{수도권배송일}", "7일")
                .replace("#{지방배송일}", "14일")
                .replace("#{url}", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))))
                .replace("#{고객센터번호}", "1666-3642");

        List<SOMap> targetList = new ArrayList<>();
        SOMap map = new SOMap();
        map.put("msg", templateText);
        map.put("number", param.get("number"));
        targetList.add(map);

        param.put("subject", "[D.PLOT] 직접배송안내");
        param.put("template", "A_directdeliver");
        param.put("url", getFullPath(String.format("/mypage/order/detail/%s", param.getStr("ordno"))));


        return sendKakaoMessage(param, targetList);
    }

    @Override
    public SOMap sendPartnersDelayInfo(SOMap param) {

        List<SOMap> targetList = new ArrayList<>();
        List<SOMap> targetPartners = dealerMapper.selectPartnersDelaySendTargetList(param);

        String url = String.format("%s/%s", prop.getProperty("partner.domain"), "main");

        for(SOMap row : targetPartners){
            SOMap map = new SOMap();

            SOMap order = comOrderMapper.selectOrderDashBoard(row);
            SOMap claim = claimMapper.selectClaimDashBoard(row);
            SOMap delay = comOrderMapper.selectOrderClaimDelayDashBoard(row);
            SOMap inquiry = inquiryMapper.selectInquiryDashBoard(row);

            int orderCnt = order.getInt("ods004");
            int claimCnt = claim.getInt("clm001") + claim.getInt("clm002") + claim.getInt("clm003");
            int delayCnt = delay.getInt("orddelay") + delay.getInt("delivdelay") + delay.getInt("cncdelay") + delay.getInt("rtndelay") + delay.getInt("excdelay");
            int inquiryCnt = inquiry.getInt("inquiry") + inquiry.getInt("goodsqna") + inquiry.getInt("asinquiry");

            String templateText = "[D.PLOT] 금일 현황 안내\n" +
                    "\n" +
                    "안녕하세요. D.PLOT입니다.\n" +
                    "금일 현황을 보내드리니 신속한 업무처리 부탁드립니다.\n" +
                    "감사합니다.\n" +
                    "\n" +
                    "■ 상품준비중 (#{상품준비중건수})\n" +
                    "■ 클레임 (#{클레임건수})\n" +
                    "■ 처리지연 (#{처리지연건수})\n" +
                    "■ 미답변 (#{미답변건수})\n" +
                    "\n" +
                    "▶ 현황 확인\n" +
                    "#{URL}";

            templateText = templateText.replace("#{상품준비중건수}", NumberFormat.getInstance().format(orderCnt))
                    .replace("#{클레임건수}", NumberFormat.getInstance().format(claimCnt))
                    .replace("#{처리지연건수}", NumberFormat.getInstance().format(delayCnt))
                    .replace("#{미답변건수}", NumberFormat.getInstance().format(inquiryCnt))
                    .replace("#{URL}", url);


            map.put("msg", templateText);
            map.put("number", row.get("chargemobile"));
            targetList.add(map);
        }

        param.put("subject", "[D.PLOT] 금일 현황 안내");
        param.put("template", "A_partner");

        return sendKakaoMessage(param, targetList);
    }

    public Map<String, Object> getEmailHeader(){
        Map<String, Object> result = new HashMap<>();
        result.put("Content-Type", "application/json;charset=utf-8");
        result.put("Cache-Control", "no-cache");
        result.put("Accept-type", "application/json");
        return result;
    }

    public Map<String, Object> getKakaoHeader(){
        Map<String, Object> result = new HashMap<>();
        String id = prop.getProperty("cj.message.kakao.alarm.talk.id");
        String pw = prop.getProperty("cj.message.pw");
        String auth = String.format("%s;%s", id, pw);
        result.put("Content-Type", "application/json;charset=utf-8");
        result.put("Authorization", auth);

        return result;
    }

    public Context getEmailCommonContext(){
        Context context = new Context();
        String domain = prop.getProperty("front.domain");
        String partnersDomain = prop.getProperty("partner.domain");

        context.setVariable("mainUrl", domain);
//        context.setVariable("topLogo", String.format("%s/%s", domain, "assets/message/img/icon-logo-black-h28px.svg"));
//        context.setVariable("footerLogo", String.format("%s/%s", domain, "assets/message/img/icon-logo-gray.svg"));
        context.setVariable("topLogo", String.format("%s/%s", partnersDomain, "assets/message/img/b_log.png"));
        context.setVariable("footerLogo", String.format("%s/%s", partnersDomain, "assets/message/img/b_log.png"));

        Map<String, Object> map = new HashMap<>();
        List<String> fontList = new ArrayList<>();
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-Medium.woff"));
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-Bold.woff"));
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-Book.woff"));
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-ExtraBold.woff"));
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-Medium.woff2"));
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-Bold.woff2"));
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-Book.woff2"));
        fontList.add(String.format("%s/%s", domain, "assets/message/font/AttenNew-ExtraBold.woff2"));

        map.put("fontList", fontList);
        context.setVariables(map);

        return context;
    }

    public Map<String, Object> getFailbackData(SOMap param, Map<String, Object> row){
        Map<String, Object> failback = new HashMap<>();
        failback.put("failback_type", "LMS");
        failback.put("failback_title", param.get("subject"));
        failback.put("failback_msg", row.get("msg"));
        failback.put("failback_id", "ddmncAPIdb01");
        return failback;
    }

    public String getFullPath(String path){
        String domain = prop.getProperty("front.domain");
        return String.format("%s%s", domain, path);
    }

	/**
	 * 알림톡 테스트를 위한 테스트 함수
	 * */
	@Override
	public SOMap sendTest(SOMap param) {
		SOMap result = new SOMap();

		List<SOMap> targetList = new ArrayList<>();
		
		SOMap map = new SOMap();
		map.put("msg", param.get("msg"));
		map.put("number", param.get("number"));
		targetList.add(map);

		param.put("subject", param.get("subject"));
		param.put("template", param.get("template"));
		param.put("attachment", param.get("attachment"));

		logger.info("============================== kakaoat 알림톡 발송 TEST ======================== ");
		logger.info("============================== kakaoat msg : " + param.get("msg"));
		logger.info("============================== kakaoat number : " + param.get("number"));
		logger.info("============================== kakaoat subject : " + param.get("subject"));
		logger.info("============================== kakaoat template : " + param.get("template"));
		logger.info("============================== kakaoat attachment : " + param.get("attachment"));
		SOMap resultKakao = sendKakaoMessage(param, targetList);
		logger.info("============================== kakaoat 알림톡 발송 결과 : " + resultKakao);
		result.put("kakaoResult", resultKakao);
		
		return result;
	}


}
