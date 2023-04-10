package com.dplot.common.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.CommonOrderService;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.DeliveryTrackingService;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.TossService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.AdminMapper;
import com.dplot.mapper.ClaimMapper;
import com.dplot.mapper.CodeMapper;
import com.dplot.mapper.ComOrderGoodsDelivMapper;
import com.dplot.mapper.ComOrderGoodsGiftMapper;
import com.dplot.mapper.ComOrderGoodsMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.ConfigCompanyMapper;
import com.dplot.mapper.DealerInfoMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.EpointPayMapper;
import com.dplot.mapper.EpointUseMapper;
import com.dplot.mapper.ExIfLogMapper;
import com.dplot.mapper.GiftMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsOptionDetailMapper;
import com.dplot.mapper.IFLogMapper;
import com.dplot.mapper.IsolationFareMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.MemberSleepMapper;
import com.dplot.mapper.MemberSnsMapper;
import com.dplot.mapper.ReservePayMapper;
import com.dplot.mapper.ReserveUseMapper;
import com.dplot.mapper.SplashImgMapper;
import com.dplot.mapper.TossreceiptMapper;
import com.dplot.mapper.UserLogMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.ServletRequestInfoUtil;
import com.dplot.util.Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

@Service
public class CommonServiceImpl extends MallBaseService implements CommonService {

	private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	private CodeMapper codeMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MemberSnsMapper memberSnsMapper;

	@Autowired
	private ComOrderMapper comOrderMapper;

	@Autowired
	private ComOrderGoodsMapper comOrderGoodsMapper;

	@Autowired
	private ComOrderGoodsDelivMapper comOrderGoodsDelivMapper;

	@Autowired
	private ComOrderGoodsGiftMapper comOrderGoodsGiftMapper;

	@Autowired
	private TossreceiptMapper tossreceiptMapper;

	@Autowired
	private CJMessageService cjMessageService;

	@Autowired
	private SplashImgMapper splashImgMapper;

    @Autowired
    private ReservePayMapper reservePayMapper;

    @Autowired
    private ReserveUseMapper reserveUseMapper;

    @Autowired
    private EpointPayMapper ePointPayMapper;

    @Autowired
    private EpointUseMapper ePointUseMapper;

    @Autowired
    private MemberSleepMapper memberSleepMapper;

    @Autowired
    private IsolationFareMapper isolationFareMapper;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
	private DealerInfoMapper dealerInfoMapper;

	@Autowired
	private ConfigCompanyMapper configCompanyMapper;

    @Autowired
	private IFLogMapper iFLogMapper;

	@Autowired
	private ExIfLogMapper exIfLogMapper;

	@Autowired
	private ERPService erpService;

	@Autowired
	private DeliveryTrackingService deliveryTrackingService;

	@Autowired
	private TossService tossService;

	@Autowired
	private CommonOrderService commonOrderService;

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private GiftMapper giftMapper;

	@Autowired
	private GoodsOptionDetailMapper goodsOptionDetailMapper;

	@Autowired
	private DealerMapper dealerMapper;

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private FileService fileService;

    @Autowired
    private UserLogMapper userLogMapper;

    @Resource(name="propertiesFactory")
	private Properties prop;

	@Override
	public ArrayList<SOMap> getCodeList(SOMap p) throws Exception {
		return codeMapper.selectCodeDtlList(p);
	}

	@Override
	public SOMap getCodeMapList(SOMap param) throws Exception {
		SOMap result = new SOMap();
		List<String> list = (List<String>) param.get("cmclass");
		String[] cmClassArr = list.toArray(new String[0]);

		param.put("cmclass", cmClassArr);
		List<SOMap> allCodeDtlList = codeMapper.selectCodeDtlMapList(param);

		Map<String, List<Map<String, Object>>> allEntryMap = allCodeDtlList.stream().collect(Collectors.groupingBy(a -> a.get("cmclass").toString()));

		for(String key : allEntryMap.keySet()){
			result.put(key, allEntryMap.get(key));
		}

		return result;
	}

	public Response kmcRequest(SOMap param) throws Exception {
		Response result = new Response();
		result.setErrorShow(false);
		/**************************************************
		 * 변수 선언
		 **************************************************/
		String tr_cert = "";
		String cpId = param.getStr("cpid"); // 회원사ID
		String urlCode = param.getStr("urlcode"); // URL코드
		String certNum = param.getStr("certnum"); // 요청번호 ( 본인인증 요청시 중복되지 않게 생성해야함. (예-시퀀스번호) )
		String date = param.getStr("date"); // 요청일시
		String certMet = param.getStr("certmet"); // 본인인증방법
		String name = param.getStr("name"); // 성명
		String phoneNo = param.getStr("phoneno"); // 휴대폰번호
		String phoneCorp = param.getStr("phonecorp"); // 이동통신사
		if (phoneCorp == null)
			phoneCorp = "";
		String birthDay = param.getStr("birthday"); // 생년월일
		String gender = param.getStr("gender"); // 성별
		if (gender == null)
			gender = "";
		String nation = param.getStr("nation"); // 내외국인 구분
		String plusInfo = param.getStr("plusinfo"); // 추가DATA정보
		int extendVar = 0000000000000000; // 확장변수
		// End-tr_cert 데이터 변수 선언
		// ---------------------------------------------------------------
		String tr_url = param.getStr("tr_url"); // 본인인증서비스 결과수신 POPUP URL
		String tr_add = param.getStr("tr_add"); // IFrame사용여부
		/**************************************************
		 * 날짜생성
		 **************************************************/
		// 날짜 생성
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = sdf.format(today.getTime());

		java.util.Random ran = new Random();
		// 랜덤 문자 길이
		int numLength = 6;
		String randomStr = "";

		for (int i = 0; i < numLength; i++) {
			// 0 ~ 9 랜덤 숫자 생성
			randomStr += ran.nextInt(10);
		}

		// reqNum은 최대 40byte 까지 사용 가능
		String reqNum = day + randomStr;
		certNum = reqNum;
		date = day;
		/**************************************************
		 * 암화값 생성
		 **************************************************/
		// 01. 한국모바일인증(주) 암호화 모듈 선언
		com.icert.comm.secu.IcertSecuManager seed = new com.icert.comm.secu.IcertSecuManager();
		// 02. 1차 암호화 (tr_cert 데이터변수 조합 후 암호화)
		String enc_tr_cert = "";
		tr_cert = cpId + "/" + urlCode + "/" + certNum + "/" + date + "/" + certMet + "/" + birthDay + "/" + gender
				+ "/" + name + "/" + phoneNo + "/" + phoneCorp + "/" + nation + "/" + plusInfo + "/" + extendVar;
		// tr_cert = cpId +"/"+ urlCode +"/"+ certNum +"/"+ date +"/"+ certMet
		// +"/"+ plusInfo +"/"+ extendVar;
		enc_tr_cert = seed.getEnc(tr_cert, "");

		// 03. 1차 암호화 데이터에 대한 위변조 검증값 생성 (HMAC)
		String hmacMsg = "";
		hmacMsg = seed.getMsg(enc_tr_cert);

		// 04. 2차 암호화 (1차 암호화 데이터, HMAC 데이터, extendVar 조합 후 암호화)
		tr_cert = seed.getEnc(enc_tr_cert + "/" + hmacMsg + "/" + extendVar, "");

		/**************************************************
		 * 파라미터 유효성 검증
		 **************************************************/
		String regex = "";
		String regex1 = "";

		// 회원사ID
		regex = "[A-Z]*";
		regex1 = "[0-9]*";
		if (cpId.length() == 8) {
			String engcpId = cpId.substring(0, 4);
			String numcpId = cpId.substring(4, 8);
			if (!Util.paramChk(regex, engcpId) || !Util.paramChk(regex1, numcpId)) {
				result.setStatusCode(Status.BAD_REQUEST.getKey());
				result.setMessage("회원사ID 비정상");
				return result;
			}
		} else {
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("회원사ID 비정상");
			return result;
		}

		// URL코드
		regex = "[0-9]*";
		if (urlCode.length() != 6 || !Util.paramChk(regex, urlCode)) {
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("URL코드 비정상");
			return result;
		}

		// 요청번호
		if (certNum.length() == 0 || certNum.length() > 40) {
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("요청번호를 입력해주세요.");
			return result;
		}

		// //요청일시
		// regex = "[0-9]*";
		// if( date.length() != 14 || !Util.paramChk(regex, date) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("요청일시 비정상");
		// return result;
		// }
		//
		// //본인인증방법
		// regex = "[A-Z]*";
		// if( certMet.length() != 1 || !Util.paramChk(regex, certMet) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("본인인증방법 비정상");
		// return result;
		// }
		//
		// //휴대폰번호
		// regex = "[0-9]*";
		// if( phoneNo.length() != 0 ){
		// if( (phoneNo.length() != 10 && phoneNo.length() != 11) ||
		// !Util.paramChk(regex, phoneNo) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("휴대폰번호 비정상");
		// return result;
		// }
		// }
		//
		// //이동통신사
		// regex = "[A-Z]*";
		// if( phoneCorp.length() != 0 ){
		// if( phoneCorp.length() != 3 || !Util.paramChk(regex, phoneCorp) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("이동통신사 비정상");
		// return result;
		// }
		// }
		//
		// //생년월일
		// regex = "[0-9]*";
		// if( birthDay.length() != 0 ){
		// if( birthDay.length() != 8 || !Util.paramChk(regex, birthDay) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("생년월일 비정상");
		// return result;
		// }
		// }
		//
		// //성별
		// regex = "[0-9]*";
		// if( gender.length() != 0 ){
		// if( gender.length() != 1 || !Util.paramChk(regex, gender) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("성별 비정상");
		// return result;
		// }
		// }
		//
		// //내/외국인
		// regex = "[0-9]*";
		// if( nation.length() != 0 ){
		// if( nation.length() != 1 || !Util.paramChk(regex, nation) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("내/외국인 비정상");
		// return result;
		// }
		// }
		//
		// //성명
		// regex = "[\\sA-Za-z가-?.,-]*";
		// if( name.length() != 0 ){
		// if( name.length() > 60 || !Util.paramChk(regex, name) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("성명 비정상");
		// return result;
		// }
		// }
		//
		// //결과수신url
		// if( tr_url.length() == 0 ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("결과수신 URL 비정상");
		// return result;
		// }
		//
		// //iframe사용여부
		// regex = "[A-Z]*";
		// if( tr_add.length() != 0 ){
		// if( tr_add.length() != 1 || !Util.paramChk(regex, tr_add) ){
		// result.setStatusCode(Status.BAD_REQUEST.getKey());
		// result.setMessage("IFrame사용여부 비정상");
		// return result;
		// }
		// }

		param.put("date", day);
		param.put("certNum", certNum);
		param.put("tr_cert", tr_cert);
		result.setData(param);

		return result;
	}

	/**
	 * KMC 본인인증 복호화 처리
	 */
	@Override
	public Response kmcCertDecry(SOMap param , HttpServletRequest request, HttpServletResponse response) throws Exception{
		Response results = new Response();
		results.setErrorShow(false);

		/**************************************************
		 * 변수 선언
		 **************************************************/
		String rec_cert = ""; // 결과수신DATA

		String k_certNum = ""; // 파라미터로 수신한 요청번호
		String certNum = ""; // 요청번호
		String date = ""; // 요청일시
		String CI = ""; // 연계정보(CI)
		String enCryCI = ""; // 연계정보암호화(CI)
		String DI = ""; // 중복가입확인정보(DI)
		String phoneNo = ""; // 휴대폰번호
		String phoneCorp = ""; // 이동통신사
		String birthDay = ""; // 생년월일
		String gender = ""; // 성별
		String nation = ""; // 내국인
		String name = ""; // 성명
		String M_name = ""; // 미성년자 성명
		String M_birthDay = ""; // 미성년자 생년월일
		String M_Gender = ""; // 미성년자 성별
		String M_nation = ""; // 미성년자 내외국인
		String result = ""; // 결과값
		String certMet = ""; // 인증방법
		String ip = ""; // ip주소
		String plusInfo = "";
		String encPara = "";
		String encMsg1 = "";
		String encMsg2 = "";
		String msgChk = "";
		/******************************************************
		 * 복호화 처리
		 ******************************************************/
		rec_cert = param.getStr("rec_cert");
		k_certNum = param.getStr("certnum");
		logger.debug("==================================================================");
		logger.debug("rec_cert : " + rec_cert);
		logger.debug("k_certNum : " + k_certNum);
		logger.debug("==================================================================");

		// [01. 암호화 모듈 (jar) Loading]
		com.icert.comm.secu.IcertSecuManager seed = new com.icert.comm.secu.IcertSecuManager();

		// [02. 1차 복호화]
		// 수신된 certNum를 이용하여 복호화
		rec_cert = seed.getDec(rec_cert, k_certNum);

		// [03. 1차 파싱]
		int inf1 = rec_cert.indexOf("/", 0);
		int inf2 = rec_cert.indexOf("/", inf1 + 1);
		encPara = rec_cert.substring(0, inf1); // 암호화된 통합 파라미터
		encMsg1 = rec_cert.substring(inf1 + 1, inf2); // 암호화된 통합 파라미터의 Hash값

		// [04. 위변조 검증]
		encMsg2 = seed.getMsg(encPara);
		if (encMsg2.equals(encMsg1)) {
			msgChk = "Y";
		}
		if (msgChk.equals("N")) {
			throw new BizException("비정상적인 접근입니다.!!(" + msgChk + ")");
		}

		// [05. 2차 복호화]
		rec_cert = seed.getDec(encPara, k_certNum);

		// [06. 2차 파싱]
        int info1  = rec_cert.indexOf("/",0);
        int info2  = rec_cert.indexOf("/",info1+1);
        int info3  = rec_cert.indexOf("/",info2+1);
        int info4  = rec_cert.indexOf("/",info3+1);
        int info5  = rec_cert.indexOf("/",info4+1);
        int info6  = rec_cert.indexOf("/",info5+1);
        int info7  = rec_cert.indexOf("/",info6+1);
        int info8  = rec_cert.indexOf("/",info7+1);
		int info9  = rec_cert.indexOf("/",info8+1);
		int info10 = rec_cert.indexOf("/",info9+1);
		int info11 = rec_cert.indexOf("/",info10+1);
		int info12 = rec_cert.indexOf("/",info11+1);
		int info13 = rec_cert.indexOf("/",info12+1);
		int info14 = rec_cert.indexOf("/",info13+1);
		int info15 = rec_cert.indexOf("/",info14+1);
		int info16 = rec_cert.indexOf("/",info15+1);
		int info17 = rec_cert.indexOf("/",info16+1);
		int info18 = rec_cert.indexOf("/",info17+1);

        certNum		= rec_cert.substring(0,info1);
        date		= rec_cert.substring(info1+1,info2);
        enCryCI	    = rec_cert.substring(info2+1,info3);
        phoneNo		= rec_cert.substring(info3+1,info4);
        phoneCorp	= rec_cert.substring(info4+1,info5);
        birthDay	= rec_cert.substring(info5+1,info6);
        gender		= rec_cert.substring(info6+1,info7);
        nation		= rec_cert.substring(info7+1,info8);
		name		= rec_cert.substring(info8+1,info9);
		result		= rec_cert.substring(info9+1,info10);
		certMet		= rec_cert.substring(info10+1,info11);
		ip			= rec_cert.substring(info11+1,info12);
		M_name		= rec_cert.substring(info12+1,info13);
		M_birthDay	= rec_cert.substring(info13+1,info14);
		M_Gender	= rec_cert.substring(info14+1,info15);
		M_nation	= rec_cert.substring(info15+1,info16);
		plusInfo	= rec_cert.substring(info16+1,info17);
		DI      	= rec_cert.substring(info17+1,info18);

		// [07. CI, DI 복호화]
        CI  = seed.getDec(enCryCI, k_certNum);
        DI  = seed.getDec(DI, k_certNum);
        /******************************************************
		 * 파라미터 유효성 검증
		 ******************************************************/

        String regex = "";
		String regex1 = "";



		if( certNum.length() == 0 || certNum.length() > 40){
			throw new BizException("요청번호 비정상.");
		}

		regex = "[0-9]*";
		if( date.length() != 14 || !Util.paramChk(regex, date)){
			throw new BizException("요청일시 비정상.");
		}

		regex = "[A-Z]*";
		if( certMet.length() != 1 ||  !Util.paramChk(regex, certMet) ){
			throw new BizException("본인인증방법 비정상" + certMet);
		}


		regex = "[0-9]*";
		if( (phoneNo.length() != 10 && phoneNo.length() != 11) ||  !Util.paramChk(regex, phoneNo) ){
			throw new BizException("휴대폰번호 비정상");
		}

		regex = "[A-Z]*";
		if( phoneCorp.length() != 3 ||  !Util.paramChk(regex, phoneCorp) ){
			throw new BizException("이동통신사 비정상");
		}

		regex = "[0-9]*";
		if( birthDay.length() != 8 ||  !Util.paramChk(regex, birthDay) ){
			throw new BizException("생년월일 비정상");
		}

		regex = "[0-9]*";
		if( gender.length() != 1 ||  !Util.paramChk(regex, gender) ){
			throw new BizException("성별 비정상");
		}

		regex = "[0-9]*";
		if( nation.length() != 1 ||  !Util.paramChk(regex, nation) ){
			throw new BizException("내/외국인 비정상");
		}

//		regex = "[\\sA-Za-z가-?.,-]*";
//		if( name.length() > 60 ||  !Util.paramChk(regex, name) ){
//			throw new BizException("성명 비정상");
//		}

		regex = "[A-Z]*";
		if( result.length() != 1 ||  !Util.paramChk(regex, result) ){
			throw new BizException("결과값 비정상");
		}

		regex = "[\\sA-Za-z가-?.,-]*";
		if( M_name.length() != 0 ){
			if( M_name.length() > 60 ||  !Util.paramChk(regex, M_name) ){
				throw new BizException("미성년자 성명 비정상");
			}
		}

		regex = "[0-9]*";
		if( M_birthDay.length() != 0 ){
			if( M_birthDay.length() != 8 ||  !Util.paramChk(regex, M_birthDay) ){
				throw new BizException("미성년자 생년월일 비정상");
			}
		}

		regex = "[0-9]*";
		if( M_Gender.length() != 0 ){
			if( M_Gender.length() != 1 ||  !Util.paramChk(regex, M_Gender) ){
				throw new BizException("미성년자 성별 비정상");
			}
		}

		regex = "[0-9]*";
		if( M_nation.length() != 0 ){
			if( M_nation.length() != 1 ||  !Util.paramChk(regex, M_nation) ){
				throw new BizException("미성년자 내/외국인 비정상");
			}
		}
		/**********************************
		 * 복호화된 데이터
		 **********************************/
		//kmc 결과 정보
		SOMap kmcinfo = new SOMap();
		kmcinfo.put("certnum", certNum);
		kmcinfo.put("date", date);
		kmcinfo.put("ci", CI);
		kmcinfo.put("phoneno", phoneNo);
		kmcinfo.put("phonecorp", phoneCorp);
		kmcinfo.put("birthday", birthDay);
		kmcinfo.put("gender", gender);
		kmcinfo.put("nation", nation);
		kmcinfo.put("name", name);
		kmcinfo.put("result", result);
		kmcinfo.put("certmet", certMet);
		kmcinfo.put("ip", ip);
		kmcinfo.put("m_name", M_name);
		kmcinfo.put("m_birthday", M_birthDay);
		kmcinfo.put("m_gender", M_Gender);
		kmcinfo.put("m_nation", M_nation);
		kmcinfo.put("plusinfo", plusInfo);
		kmcinfo.put("di", DI);

		//회원정보
		SOMap dbparam = new SOMap();
		dbparam.put("ci",CI);
		dbparam.put("di",DI);
		dbparam.put("mobile", phoneNo);
		dbparam.put("name", name);
		dbparam.put("siteid", cs.getStr("siteid"));
		dbparam.put("state", CMConst.CM_STATE_REAL);

		//사용자 정보 조회
		//CI값비교
		SOMap memberInfo = memberSnsMapper.selectMemberInfo(dbparam);
		if(memberInfo == null) {
			//이름 핸드폰번호비교
			dbparam.remove("ci");
			memberInfo = memberSnsMapper.selectMemberInfo(dbparam);
		}

		//기가입 있을경우
		if(memberInfo != null) {
			if (Util.isNotEmpty(memberInfo.get("userid"))) {
				String tempMemberId = memberInfo.get("userid").toString();
				tempMemberId = Util.asterisk(tempMemberId, 2, 0);
				memberInfo.put("useridaster", tempMemberId);
			}
			if (Util.isNotEmpty(memberInfo.get("email"))) {
				String emailAster = memberInfo.get("email").toString().trim();
				String[] emailArr = emailAster.split("@");
				if (emailArr.length > 1) {
					emailAster = emailArr[0].substring(0, 2) + "****@" + emailArr[1];
					memberInfo.put("emailaster", emailAster);
				}
			}
			if (Util.isNotEmpty(memberInfo.get("mobile"))) {
				String mobile = memberInfo.get("mobile").toString().trim();
				mobile = Util.phoneFormat(mobile);
				String[] mobileArr = Util.getTelPhoneSpliter(mobile);
				if (mobileArr.length > 2) {
					mobileArr[1] = Util.asterisk(mobileArr[1], 0, 0);
					memberInfo.put("mobileaster", mobileArr[0]+"-"+mobileArr[1]+"-"+mobileArr[2]);
				}
			}
		}

		SOMap dbparam2 = new SOMap();
		dbparam2.put("ci",CI);
		dbparam2.put("di",DI);
		dbparam.put("mobile", phoneNo);
		dbparam.put("siteid", cs.getStr("siteid"));
		
		/**
		 * 해당 로직을 회원가입, 휴면회원 전환에 같이 사용하므로 2개 프로세스를 다 만족하기 위해 아래와 같이 수정
		 * withdrawCnt는 해당 회원 데이터 카운트로 판단. 탈퇴회원/휴면회원은 T_MEMBER.STATE 값으로 판단.
		 * MST002 : 휴면회원, MST003 : 탈퇴회원
		 */
		SOMap member = memberSleepMapper.selectMemberSleepWithdraw(dbparam);
		
		SOMap resultMap = new SOMap();
		resultMap.put("kmcinfo", kmcinfo);
		resultMap.put("memberinfo", memberInfo);
		
		int witdrawcnt = 0;
		boolean isSleep = false;
		
		if(member != null) {
			if(member.getDbStr("state").equals(CMConst.CM_STATE_SLEEP)) {	// T_MEMBER_SLEEP 에 있는 회원 정보가 휴면회원인 경우
				isSleep = true;
				resultMap.put("sleepmember", member);
			} else {		// 탈퇴 회원인 경우
				witdrawcnt = 1;
			}
		} else {
			dbparam2.remove("ci");
			dbparam2.remove("di");
			SOMap member2 = memberSleepMapper.selectMemberSleepWithdraw(dbparam);
			
			if(member2 != null) {
				if(member2.getDbStr("state").equals(CMConst.CM_STATE_SLEEP)) {	// T_MEMBER_SLEEP 에 있는 회원 정보가 휴면회원인 경우
					isSleep = true;
					resultMap.put("sleepmember", member2);
				} else {		// 탈퇴 회원인 경우
					witdrawcnt = 1;
				}
			}
		}
		resultMap.put("witdrawcnt", witdrawcnt);
		resultMap.put("issleep", isSleep);

		results.setData(resultMap);

		/******************************************************
		 * 수신내역 유효성 검증(사설망의 사설 IP로 인해 미사용, 공용망의 경우 확인 후 사용)
		 ******************************************************/
		// [1. date 값 검증]
//		SimpleDateFormat formatter	= new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREAN); // 현재 서버 시각 구하기
//		String strCurrentTime	= formatter.format(new Date());
//		Date toDate				= formatter.parse(strCurrentTime);
//		Date fromDate			= formatter.parse(date);
//		long timediff			= toDate.getTime()-fromDate.getTime();
//		if ( timediff < -30*60*1000 || 30*60*100 < timediff  ){
//			throw new BizException("비정상적인 접근입니다. (요청시간경과)");
//		}
//		// [2. ip 값 검증]
//		String client_ip = request.getHeader("HTTP_X_FORWARDED_FOR"); // 사용자IP 구하기
//		if ( client_ip != null ){
//			if( client_ip.indexOf(",") != -1 )
//				client_ip = client_ip.substring(0,client_ip.indexOf(","));
//		}
//		if ( client_ip==null || client_ip.length()==0 ){
//			client_ip = request.getRemoteAddr();
//		}
//
//		if( !client_ip.equals(ip) ){
//			throw new BizException("비정상적인 접근입니다. (IP불일치)");
//		}

		return results;
	}

	@Override
	public void tossVirtualAccount(SOMap param) throws Exception{
		param.put("siteid", cs.getStr("siteid"));

		String exlog = "{status : '" + param.getDbStr("status") + "', orderId : '" + param.getDbStr("orderid")
				+ "', secret : '" + param.getDbStr("secret") +
				"', createAt : '" + param.getDbStr("createat") + "'}";
		param.put("exlogtype", "EXL002");
		param.put("iflog", exlog);
		param.put("orgno", param.getDbStr("orderid"));
		exIfLogMapper.insertExIfLog(param);

		//주문내역조회
		SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
		param.put("orderidx", orderInfo.getDbInt("orderidx"));

		if(orderInfo != null) {
			//결재정보 조회
			SOMap tossInfo = tossreceiptMapper.selectTossreceipt(param);
			if(tossInfo.getDbStr("virsecret").equals(param.getDbStr("secret"))) {
				if("DONE".equals(param.getDbStr("status"))) {
					List<SOMap> orderGoods = comOrderGoodsMapper.selectComOrderGoodsList(orderInfo);

					//주문결제일수정
					param.put("paymentdate", DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR14));
					param.put("paywaytype", orderInfo.getDbStr("paywaytype"));
					comOrderMapper.updateOrderPayment(param);

					//상품준비중 상태변경
					param.put("ordstatus", CMConst.ORDER_PREPARING_GOODS);//상품준비중
					comOrderGoodsDelivMapper.updateDeliveryStatusByOrderIdx(param);

					//현금영수증발급
					if(!Util.isEmpty(orderInfo.getDbStr("cashreceipttype")) && !"미발행".equals(orderInfo.getDbStr("cashreceipttype"))) {
						int amount = orderInfo.getDbInt("empreservetotamt");

						amount = amount + orderInfo.getDbInt("rpaytotprice");

						String orderName = orderGoods.get(0).getDbStr("goodsname");
						if(orderGoods.size() > 1) {
							orderName = orderName + " 외 " + (orderGoods.size() - 1) + "건";
						}

						SOMap payinfo = new SOMap();
						payinfo.put("cashreceipttype", orderInfo.getDbStr("cashreceipttype"));
						payinfo.put("registrationnumber", orderInfo.getDbStr("registrationnumber"));
						payinfo.put("orderid", orderInfo.getDbStr("ordno"));
						payinfo.put("ordername",  orderName);
						payinfo.put("amount", amount);
						try {
							SOMap resultTossInfo = tossService.tossCashReceipts(payinfo);
							resultTossInfo.put("orderidx", param.getDbInt("orderidx"));

							//결제정보 수정
							comOrderMapper.updateCashReceipts(resultTossInfo);
						} catch (Exception e) {
							logger.error("", e);
						}
					}
					//주문완료 메시지발송
					this.sendMessageOrderComplete(param);

					SOMap log = new SOMap();
					log.put("siteid", cs.getStr("siteid"));
					log.put("orgno", param.get("orderidx"));
					erpService.insertOrderERPData(log);

				} else if("CANCELED".equals(param.getDbStr("status"))) {

				} else if("WAITING_FOR_DEPOSIT".equals(param.getDbStr("status"))) {

				}
			}
		}
	}

	@Override
	public void sendMessageOrderComplete(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
		if(orderInfo == null) {
			throw new BizException("주문내역이 없습니다.");
		}

		orderInfo.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		List<SOMap> orderGoods = comOrderGoodsMapper.selectComOrderGoodsList(orderInfo);

		//판매자별 그룹핑
		List<SOMap> dealerInfo = new ArrayList<SOMap>();
		for(int i = 0 ; i < orderGoods.size() ; i++) {
			orderGoods.get(i).put("imgurl", orderGoods.get(i).getDbStr("fullpath"));

			boolean isExists = false;
			for(int j = 0 ; j < dealerInfo.size() ; j++) {
				if(orderGoods.get(i).getDbInt("dealerno") == dealerInfo.get(j).getDbInt("dealerno")){
					isExists = true;
					break;
				}
			}
			if(!isExists) {
				SOMap dealer = new SOMap();
				dealer.put("dealerno", orderGoods.get(i).getDbInt("dealerno"));
				dealer.put("dealername", orderGoods.get(i).getStr("dealernm"));
				dealer.put("delivamt", 0);
				dealer.put("ordergoods", new ArrayList<SOMap>());
				dealerInfo.add(dealer);
			}
		}
		for(int i = 0 ; i < dealerInfo.size() ; i++) {
			for(int j = 0 ; j < orderGoods.size() ; j++) {
				if(dealerInfo.get(i).getDbInt("dealerno") == orderGoods.get(j).getDbInt("dealerno")){
					dealerInfo.get(i).put("delivamt", dealerInfo.get(i).getDbInt("delivamt") + orderGoods.get(j).getDbInt("delivamt") - orderGoods.get(j).getDbInt("delivcpnamt"));
					dealerInfo.get(i).getArrayList("ordergoods").add(orderGoods.get(j));
				}
			}
		}

		param.put("imgtype", "IGT013");
		List<SOMap> giftList = comOrderGoodsGiftMapper.selectComOrderGoodsGiftList(param);

		List<SOMap> promoList = new ArrayList<SOMap>();

		if(giftList.size() > 0) {
			Map<String, List<SOMap>> giftMap = giftList.stream().collect(Collectors.groupingBy(a -> a.get("giftpromoidx").toString()));
			for (String key : giftMap.keySet()) {
				List<SOMap> promoGiftList = giftMap.get(key);
				SOMap promo = promoGiftList.get(0);

				int idx = 1;
				for (SOMap gift : promoGiftList) {
					gift.put("imgurl", gift.getDbStr("fullpath"));
					gift.put("giftcnt", gift.getDbInt("rcvamt"));
					if("GFT004".equals(gift.getDbStr("gifttermtype"))) {
						gift.put("giftname", "[증정] " + gift.getDbStr("giftname"));
					} else {
						gift.put("giftname", "[선택 " + idx + "] " + gift.getDbStr("giftname"));
					}
					idx++;
				}
				promo.put("promogift", promoGiftList);
				promoList.add(promo);
			}
		}

		SOMap messageParam = new SOMap();
		messageParam.putAll(orderInfo);
		messageParam.put("goodsname", orderGoods.get(0).getDbStr("goodsname"));
		messageParam.put("ordcnt", orderGoods.size() - 1);
		messageParam.put("number", orderInfo.getDbStr("ordtel"));
		messageParam.put("amt", orderInfo.getDbStr("rpaytotprice"));
		messageParam.put("addr", orderInfo.getDbStr("rcvaddrroad"));
//		messageParam.put("addr", orderInfo.getDbStr("rcvaddrroad") + " " + orderInfo.getDbStr("rcvaddrdetailroad"));
		messageParam.put("url", prop.getProperty("front.domain") + "");

		messageParam.put("dealerinfo", dealerInfo);
		messageParam.put("promotion", promoList);
		//카톡/이메일 발송
		cjMessageService.sendPayComplete(messageParam);

	}

	@Override
	public void sendMessageOrderComplete2(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
		if(orderInfo == null) {
			throw new BizException("주문내역이 없습니다.");
		}

		orderInfo.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		List<SOMap> orderGoods = comOrderGoodsMapper.selectComOrderGoodsList(orderInfo);

		//판매자별 그룹핑
		List<SOMap> dealerInfo = new ArrayList<SOMap>();
		for(int i = 0 ; i < orderGoods.size() ; i++) {
			orderGoods.get(i).put("imgurl", orderGoods.get(i).getDbStr("fullpath"));

			boolean isExists = false;
			for(int j = 0 ; j < dealerInfo.size() ; j++) {
				if(orderGoods.get(i).getDbInt("dealerno") == dealerInfo.get(j).getDbInt("dealerno")){
					isExists = true;
					break;
				}
			}
			if(!isExists) {
				SOMap dealer = new SOMap();
				dealer.put("dealerno", orderGoods.get(i).getDbInt("dealerno"));
				dealer.put("dealername", orderGoods.get(i).getStr("dealernm"));
				dealer.put("delivamt", 0);
				dealer.put("ordergoods", new ArrayList<SOMap>());
				dealerInfo.add(dealer);
			}
		}
		for(int i = 0 ; i < dealerInfo.size() ; i++) {
			for(int j = 0 ; j < orderGoods.size() ; j++) {
				if(dealerInfo.get(i).getDbInt("dealerno") == orderGoods.get(j).getDbInt("dealerno")){
					dealerInfo.get(i).put("delivamt", dealerInfo.get(i).getDbInt("delivamt") + orderGoods.get(j).getDbInt("delivamt") - orderGoods.get(j).getDbInt("delivcpnamt"));
					dealerInfo.get(i).getArrayList("ordergoods").add(orderGoods.get(j));
				}
			}
		}

		param.put("imgtype", "IGT013");
		List<SOMap> giftList = comOrderGoodsGiftMapper.selectComOrderGoodsGiftList(param);

		List<SOMap> promoList = new ArrayList<SOMap>();

		if(giftList.size() > 0) {
			Map<String, List<SOMap>> giftMap = giftList.stream().collect(Collectors.groupingBy(a -> a.get("giftpromoidx").toString()));
			for (String key : giftMap.keySet()) {
				List<SOMap> promoGiftList = giftMap.get(key);
				SOMap promo = promoGiftList.get(0);

				int idx = 1;
				for (SOMap gift : promoGiftList) {
					gift.put("imgurl", gift.getDbStr("fullpath"));
					gift.put("giftcnt", gift.getDbInt("rcvamt"));
					if("GFT004".equals(gift.getDbStr("gifttermtype"))) {
						gift.put("giftname", "[증정] " + gift.getDbStr("giftname"));
					} else {
						gift.put("giftname", "[선택 " + idx + "] " + gift.getDbStr("giftname"));
					}
					idx++;
				}
				promo.put("promogift", promoGiftList);
				promoList.add(promo);
			}
		}


		SOMap messageParam = new SOMap();
		messageParam.putAll(param);
		messageParam.putAll(orderInfo);
		messageParam.put("goodsname", orderGoods.get(0).getDbStr("goodsname"));
		messageParam.put("ordcnt", orderGoods.size() - 1);
		messageParam.put("number", orderInfo.getDbStr("ordtel"));
		messageParam.put("amt", orderInfo.getDbStr("rpaytotprice"));
		messageParam.put("addr", orderInfo.getDbStr("rcvaddrroad"));
//		messageParam.put("addr", orderInfo.getDbStr("rcvaddrroad") + " " + orderInfo.getDbStr("rcvaddrdetailroad"));
		messageParam.put("url", prop.getProperty("front.domain") + "");

		messageParam.put("dealerinfo", dealerInfo);
		messageParam.put("promotion", promoList);
		//카톡/이메일 발송
		cjMessageService.sendDepositWithOutBankBook(messageParam);
	}

	/***********************
	 * 스플래쉬 이미지 목록 조회
	 ***********************/
	@Override
	public SOMap getsplashimgList(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		SOMap splashList = splashImgMapper.selectFrontSplashImgList(param);
		return splashList;
	}

	/**
	 * 적립금지급
	 * @param param
	 * - 수동지급인 경우 ismemtype, reserveidx 필수
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int paymentReserve(SOMap param) throws Exception {
		int resultCnt = 0;

		try {
			// 적립금 지급
			resultCnt = reservePayMapper.insertReservePayAll(param);

	        // ERP LOG INSERT
			SOMap iFLogMap = new SOMap();
			iFLogMap.put("siteid", cs.getStr("siteid"));
			iFLogMap.put("type", CMConst.ERP_RESERVE_TYPE);
			iFLogMap.put("aud", CMConst.IF_LOG_ADD);
			if (param.containsKey("ismemtype") && !Util.isEmpty(param.getStr("ismemtype"))) {
				// reserveidx 필수
				iFLogMap.put("reserveidx", param.get("reserveidx"));
				iFLogMapper.insertIfLogERPDataForReserveAll(iFLogMap);
			} else {
				iFLogMap.put("orgno", param.get("respayidx"));
				iFLogMapper.insertIfLogERPData(iFLogMap);
			}
		} catch(Exception e){
            logger.error(e.getMessage());
            throw new BizException("적립금 지급시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }

		return resultCnt;
	}

	/**
	 * 이포인트지급
	 * @param param
	 * - 수동지급인 경우 payarr, epoidx 필수
	 * @throws Exception
	 */
	@Override
	public int paymentEpotint(SOMap param) throws Exception {
		int resultCnt = 0;
		try {
			// 이포인트지급
			resultCnt = ePointPayMapper.insertEpointPay(param);

	        // ERP LOG INSERT
			SOMap iFLogMap = new SOMap();
			iFLogMap.put("siteid", cs.getStr("siteid"));
			iFLogMap.put("type", CMConst.ERP_EPOINT_TYPE);
			iFLogMap.put("aud", CMConst.IF_LOG_ADD);
			// orgno, orgnolist 세팅
			if (param.containsKey("payarr") && param.getArrayList("payarr") != null && param.getArrayList("payarr").size()>0) {
				List<Integer> orgnoList = ePointPayMapper.selectEpopayidxListByIdx(param);
				iFLogMap.put("orgnolist", orgnoList);
			} else {
				iFLogMap.put("orgno", param.get("epopayidx"));
			}
			iFLogMapper.insertIfLogERPData(iFLogMap);
		} catch(Exception e){
            logger.error(e.getMessage());
            throw new BizException("D포인트 지급시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
		return resultCnt;
	}

	/**
	 * 적립금 EPOINT 사용,복원처리
	 * isuse T : 사용 F : 복원
	 * 사용 : reservetotamt(적립금), epointtotamt(EPOINT), empreservetotamt(임직원)
	 * 복원 : rtnresamt(적립금), rtnepoamt(EPOINT), rtnempresamt(임직원)
	 * 주문번호 : ordercode
	 * @param param
	 * @throws Exception
	 */
	public void procUseReserveEPoint(SOMap param) throws Exception {

		if(Util.flag2Bool(param.getDbStr("isuse"))) {
			SOMap memberEtcInfo = memberMapper.selectMemberEtcInfo(param);
			/**
			 * 사용처리
			 */
			param.put("resusetype", "RUT001"); //주문/결제
			//적립금
			if(param.getDbInt("reservetotamt") > 0) {
				if(memberEtcInfo.getDbInt("respoint") < param.getDbInt("reservetotamt")) {
					throw new BizException("적립금 잔액이 부족합니다.");
				}
				param.put("isempreserve", "F");
				param.put("usepoint", param.getDbInt("reservetotamt"));
				this.procUseReserve(param);
			}

			//EPOINT
			if(param.getDbInt("epointtotamt") > 0) {
				List<SOMap> list = ePointPayMapper.selectEpointPayListByUser(param);
				if(list == null || list.size() == 0) {
					throw new BizException("D포인트 잔액이 부족합니다.");
				}

				SOMap epoint = list.get(0);
				int remain = epoint.getDbInt("paypoint") - epoint.getDbInt("usedpoint");
				int point = param.getDbInt("epointtotamt");
				if(remain <  point) {
					throw new BizException("D포인트 잔액이 부족합니다.");
				}

				param.put("usepoint", point);
				//적립금사용내역 저장
				ePointUseMapper.insertEpointUse(param);

				//적립금지급내역 저장
				param.put("epopayidx", epoint.getDbInt("epopayidx"));
				param.put("usedpoint", point + epoint.getDbInt("usedpoint"));

				param.put("usepoint", param.getDbInt("usedpoint"));
				ePointUseMapper.insertEpointUsepay(param);
				ePointPayMapper.updateEpointPayUsedPoint(param);
			}

			//임직원적립금
			if(param.getDbInt("empreservetotamt") > 0) {
				if(memberEtcInfo.getDbInt("emppoint") < param.getDbInt("empreservetotamt")) {
					throw new BizException("임직원적립금 잔액이 부족합니다.");
				}

				param.put("isempreserve", "T");
				param.put("usepoint", param.getDbInt("empreservetotamt"));
				this.procUseReserve(param);
			}

		} else {
			/**
			 * 적립금 사용취소
			 */
			param.put("resusetype", "RUT003"); //주문취소적립
			//적립금
			if(param.getDbInt("rtnresamt") > 0) {
				param.put("isempreserve", "F");
				param.put("usepoint", param.getDbInt("rtnresamt"));

				this.procReturnReserve(param);
			}

			//E포인트
			if(param.getDbInt("rtnepoamt") > 0) {
				param.put("usepoint", param.getDbInt("rtnepoamt"));

				SOMap epoint = ePointUseMapper.selectEpointUseByOrder(param);
				int point = param.getDbInt("usepoint");

				//E포인트사용내역 저장
				ePointUseMapper.insertEpointUse(param);

				int usedpoint = epoint.getDbInt("epoint");

				//적립금지급내역 저장
				param.put("epopayidx", epoint.getDbInt("epopayidx"));
				param.put("usedpoint", usedpoint - point);

				ePointUseMapper.insertEpointUsepay(param);
				ePointPayMapper.updateEpointPayUsedPoint(param);
			}

			//임직원
			if(param.getDbInt("rtnempresamt") > 0) {
				param.put("isempreserve", "T");
				param.put("usepoint", param.getDbInt("rtnempresamt"));

				this.procReturnReserve(param);
			}
		}
	}

	/**
	 * 적립금 사용처리
	 * @param param
	 * @throws Exception
	 */
	private void procUseReserve(SOMap param) throws Exception {
		List<SOMap> list = reservePayMapper.selectReservePayListByUser(param);
		int point = param.getDbInt("usepoint");

		//적립금사용내역 저장
		reserveUseMapper.insertReserveUse(param);

		//적립금지급내역 저장
		for (SOMap map : list) {
			param.put("respayidx", map.getDbInt("respayidx"));

			int remain = map.getDbInt("paypoint") - map.getDbInt("usedpoint");

			if(remain >= point) {
				param.put("usedpoint", point + map.getDbInt("usedpoint"));
				param.put("usepoint", point);
				point = 0;
			} else {
				param.put("usedpoint", remain + map.getDbInt("usedpoint"));
				param.put("usepoint", remain);
				point = point - remain;
			}
			reserveUseMapper.insertReserveUsepay(param);
			reservePayMapper.updateReservePayUsedPoint(param);

			// ERP LOG INSERT
			SOMap iFLogMap = new SOMap();
			iFLogMap.put("siteid", cs.getStr("siteid"));
			iFLogMap.put("type", CMConst.ERP_RESERVE_TYPE);
			iFLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			iFLogMap.put("orgno", param.get("respayidx"));

			iFLogMapper.insertIfLogERPData(iFLogMap);
			if(point == 0) break;
		}
	}

	/**
	 * 적립금 복원처리
	 * @param param
	 * @throws Exception
	 */
	private void procReturnReserve(SOMap param) throws Exception {
		List<SOMap> list = reserveUseMapper.selectReserveUseByOrder(param);
		int point = param.getDbInt("usepoint");

		//적립금사용내역 저장
		reserveUseMapper.insertReserveUse(param);

		//적립금지급내역 저장
		for (SOMap map : list) {
			param.put("respayidx", map.getDbInt("respayidx"));

			int usedpoint = map.getDbInt("usedpoint");

			if(usedpoint >= point) {
				param.put("usedpoint", usedpoint - point);
				point = 0;
			} else {
				param.put("usedpoint", 0);
				point = point - usedpoint;
			}
			param.put("usepoint", param.getDbInt("usedpoint"));
			reserveUseMapper.insertReserveUsepay(param);
			reservePayMapper.updateReservePayUsedPoint(param);

			// ERP LOG INSERT
			SOMap iFLogMap = new SOMap();
			iFLogMap.put("siteid", cs.getStr("siteid"));
			iFLogMap.put("type", CMConst.ERP_RESERVE_TYPE);
			iFLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			iFLogMap.put("orgno", param.get("respayidx"));

			if(point == 0) break;
		}
	}

	/**
	 * 주문배송조회
	 */
	@Override
	public SOMap selectDeliveryTracking(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
		if("1".equals(param.getDbStr("track"))) {
			//주문배송조회
			SOMap order = comOrderMapper.selectComOrderInfo(param);
			List<SOMap> delivList = comOrderGoodsDelivMapper.selectOrderInvoiceList(param);
			param.put("invoiceno", delivList.get(0).getDbStr("invoiceno"));
			param.put("logistype", delivList.get(0).getDbStr("logiscode"));
			resultMap.put("istracking", delivList.get(0).getDbStr("istracking"));

			resultMap.put("result", order);
			resultMap.put("list", delivList);
		} else {
			SOMap claim = claimMapper.selectClaimInfo(param);
			if("2".equals(param.getDbStr("track"))) {
				//교환배송조회
				param.put("invoiceno", claim.getDbStr("excdlvinvoiceno"));
				param.put("logistype", claim.getDbStr("exclogiscode"));
				resultMap.put("istracking", claim.getDbStr("isexctracking"));
			} else {
				//반품,교환회수조회
				param.put("invoiceno", claim.getDbStr("recinvoiceno"));
				param.put("logistype", claim.getDbStr("reclogiscode"));
				resultMap.put("istracking", claim.getDbStr("isrectracking"));
			}

			resultMap.put("result", claim);
		}

		SOMap tracking = new SOMap();
		if(Util.flag2Bool(resultMap.getDbStr("istracking"))) {
			tracking = deliveryTrackingService.getDeliveryTrackingData(param);
		}
		resultMap.put("tracking", tracking);

		// 판매자, AS정보
		SOMap dbparams = new SOMap();
		int dealerno = param.getDbInt("dealerno");
		if (dealerno > 0) {
			dbparams.put("dealerno", dealerno);
			resultMap.put("dealer", dealerInfoMapper.selectDealerAsInfo(dbparams));
		} else {
			dbparams.put("siteid", cs.getStr("siteid"));
			resultMap.put("dealer", configCompanyMapper.selectCompanyAsInfo(dbparams));
		}

		return resultMap;
	}

	/**
	 * 주소 제주/산간여부 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectAddrIsolatetype(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		String isolatetype = isolationFareMapper.selectIsolationType(param);
		if(Util.isEmpty(isolatetype)) {
			resultMap.put("isolatetype", "N");
		} else {
			resultMap.put("isolatetype", isolatetype);
		}

		return resultMap;
	}

	@Override
	public SOMap niceBankAccountCheck(SOMap param) throws Exception {
		SOMap result = new SOMap();
		result.put("exist", false);
		result.put("msg", "인증 요청에 실패 하였습니다.");

		String tokenUrl = prop.getProperty("nice.account.token.url");
		String accountUrl = prop.getProperty("nice.account.url");
		String clientId = prop.getProperty("nice.account.client.id");
		String clientSecret = prop.getProperty("nice.account.client.secret");
		String productId = prop.getProperty("nice.account.product.id");

		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(tokenUrl);

		String key = String.format("%s:%s", clientId, clientSecret);

		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(key.getBytes()));

		ObjectMapper mapper = new ObjectMapper();

		HttpResponse response = client.execute(postRequest);
		if (response.getStatusLine().getStatusCode() == 200) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			SOMap tokenResult = mapper.readValue(body,  new TypeReference<SOMap>(){});
			SOMap dataHeader = tokenResult.getSOMap("dataheader");
			SOMap dataBody = tokenResult.getSOMap("databody");
			if("1200".equals(dataHeader.getStr("GW_RSLT_CD"))){
				String accessToken = dataBody.getStr("access_token");
				String accountKey = String.format("%s:%s:%s", accessToken, new Date().getTime()/1000, clientId);

				HttpPost postAccountRequest = new HttpPost(accountUrl);
				postAccountRequest.setHeader("Authorization", String.format("bearer %s" , Base64.getEncoder().encodeToString(accountKey.getBytes(StandardCharsets.UTF_8))));
				postAccountRequest.setHeader("productID", productId);
				postAccountRequest.setHeader("client_id", clientId);
				postAccountRequest.setHeader("CNTY_CD", "ko");
				postAccountRequest.setHeader("Content-Type", "application/json");

				Map<String, Object> sendData = new HashMap<>();
				Map<String, Object> dataRow = new HashMap<>();
				dataRow.put("acct_gb", param.getStr("accounttype"));
				dataRow.put("bnk_cd", param.getStr("bank").replaceAll("-", ""));
//				dataRow.put("birthday", param.getStr("birthday").replaceAll("-", ""));
				dataRow.put("acct_no", param.getStr("account").replaceAll("-", ""));
				dataRow.put("name", param.getStr("name"));

				sendData.put("dataBody", dataRow);
				JSONObject json = new JSONObject();
				json.putAll(sendData);

				postAccountRequest.setEntity(new StringEntity(json.toString(), StandardCharsets.UTF_8));

				HttpResponse accountResponse = client.execute(postAccountRequest);
				if (accountResponse.getStatusLine().getStatusCode() == 200) {
					ResponseHandler<String> accountHandler = new BasicResponseHandler();
					String accountBody = accountHandler.handleResponse(accountResponse);
					SOMap accountResult = mapper.readValue(accountBody,  new TypeReference<SOMap>(){});

					SOMap accountHeader = accountResult.getSOMap("dataheader");
					SOMap accountData = accountResult.getSOMap("databody");
					//계좌소유주 확인 성공
					if("1200".equals(accountHeader.getStr("GW_RSLT_CD")) && "0000".equals(accountData.getStr("result_cd"))){
						result.put("exist", true);
						result.put("msg", "인증 완료");
					} else if("1800".equals(accountHeader.getStr("GW_RSLT_CD"))){
						//토큰만료 발생시 폐기 처리
						niceAccountAuthExpireAndApprovalToken(accessToken, clientId);
						result.put("msg", "토큰이 만료되었습니다. 다시 시도해 주세요.");
					} else {
						result.put("exist", false);
						String resultCode = accountData.getStr("result_cd");
						if("DB01".equals(resultCode)){
							result.put("msg", "소유주확인시 타행일 경우 데이터 미존재");
						} else if("D900".equals((resultCode))){
							result.put("msg", "조회은행코드오류");
						} else if("D103".equals((resultCode))){
							result.put("msg", "조회 계좌번호 오류");
						} else if("D105".equals((resultCode))){
							result.put("msg", "계좌연동 포멧 오류");
						} else if("TIME".equals((resultCode))){
							result.put("msg", "TIMEOUT(응답지연)");
						} else if("DSYS".equals((resultCode))){
							result.put("msg", "시스템장애");
						} else if("OVER".equals((resultCode))){
							result.put("msg", "동시 접속자수 초과");
						} else if("D888".equals((resultCode))){
							result.put("msg", "당행서비스가 불가능함");
						} else if("D999".equals((resultCode))){
							result.put("msg", "서비스 시간 아님.");
						} else if("B004".equals((resultCode))){
							result.put("msg", "계좌성명-계좌불일치");
						} else if("B101".equals((resultCode))){
							result.put("msg", "타행(공동망) or 은행시스템 오류");
						} else if("B102".equals((resultCode))){
							result.put("msg", "계좌오류");
						} else if("B103".equals((resultCode))){
							result.put("msg", "주민번호 또는 사업자번호 상이");
						} else if("B104".equals((resultCode))){
							result.put("msg", "입력성명과 계좌성명이 다름");
						} else if("B199".equals((resultCode))){
							result.put("msg", "은행 기타 오류");
						} else if("S606".equals((resultCode))){
							result.put("msg", "소유주확인시 타행일 경우 당사 성명 불일치");
						} else {
							result.put("msg", "알수 없는 에러");
						}
					}
				}
			}
		}

		return result;
	}

	@Override
	public void batchOrderCompleteAndCancel(SOMap param) throws Exception {
		String idxEndArrStr = param.getStr("idx_end_arr");
		if(!"".equals(idxEndArrStr)){
			String[] idxArr = idxEndArrStr.split(",");

			if(idxArr.length > 0){
				for(String value : idxArr){
					SOMap subParam = new SOMap();
					subParam.put("ordgdidx", value);
					subParam.put("ordcfmtype", CMConst.ORD_CFM_BATCH);
					subParam.put("userid", "system");
					subParam.put("siteid", param.getStr("siteid"));

					commonOrderService.confirmOrder(subParam);
				}
			}
		}
		String idxCancelArrStr = param.getStr("idx_cancel_arr");
		if(!"".equals(idxCancelArrStr)){
			String[] idxArr = idxCancelArrStr.split(",");
			for(String value : idxArr){
				SOMap subParam = new SOMap();
				subParam.put("orderidx", value);
				subParam.put("userid", "system");
				subParam.put("siteid", param.getStr("siteid"));
				subParam.put("isbatch", "T");
				commonOrderService.confirmCancel(subParam);
			}
		}
	}

	@Override
	public void updateGoodsAndGiftStock(SOMap param) throws Exception {

		List<SOMap> goodsList = goodsMapper.selectPBGoodsList(param);
		List<SOMap> goodsStockUpdateList = new ArrayList<>();
		for(SOMap map : goodsList){
			map.put("isgift", "F");
			SOMap result = erpService.erpGetGoodsAndGiftStock(map);
			if("T".equals(result.getStr("exist"))){
				goodsStockUpdateList.add(result);
			}
		}

		List<SOMap> giftList = giftMapper.selectGiftStockUpdateList(param);
		List<SOMap> giftStockUpdateList = new ArrayList<>();
		for(SOMap map : giftList){
			map.put("isgift", "T");
			SOMap result = erpService.erpGetGoodsAndGiftStock(map);
			if("T".equals(result.getStr("exist"))){
				giftStockUpdateList.add(result);
			}
		}

		if(goodsStockUpdateList.size() > 0){
			SOMap loopParam = new SOMap();
			loopParam.put("goods", goodsStockUpdateList);
			goodsOptionDetailMapper.updateGoodsOptionDetailStockCntLoop(loopParam);
		}

		if(giftStockUpdateList.size() > 0){
			SOMap loopParam = new SOMap();
			loopParam.put("gift", goodsStockUpdateList);
			giftMapper.updateGiftStockCntLoop(loopParam);
		}
	}

	public void niceAccountAuthExpireAndApprovalToken(String accessToken, String clientId) throws Exception {
		String expireUrl = prop.getProperty("nice.account.expire.token.url");

		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(expireUrl);

		String accountKey = String.format("%s:%s:%s", accessToken, new Date().getTime()/1000, clientId);

		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(accountKey.getBytes()));

		ObjectMapper mapper = new ObjectMapper();

		HttpResponse response = client.execute(postRequest);
		if (response.getStatusLine().getStatusCode() == 200) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			SOMap expireTokenResult = mapper.readValue(body,  new TypeReference<SOMap>(){});
			SOMap dataHeader = expireTokenResult.getSOMap("dataheader");

			if("1200".equals(dataHeader.getStr("GW_RSLT_CD"))){
				logger.info("NICE 계좌 실명인증 TOKEN 폐기 완료");
			}
		}
	}

    @Override
    public SOMap createNaverEp(SOMap param) throws Exception{
    	SOMap resultMap = new SOMap();

		StringBuilder sb = new StringBuilder();
		String updateTime = DateTimeUtil.getNowFullStr();
    	String epKind = param.getStr("kind");
        String pclink = String.format("%s%s", prop.getProperty("front.domain"), "/shop/detail/");
        String molink = String.format("%s%s", prop.getProperty("front.mobile.domain"), "/shop/detail/");

		// HEADER 세팅 (첫번째 행)
		Map<String, String> headerMap = this._getEpGoodsHeaderMap(epKind);
		headerMap.forEach((key, value) -> {
			sb.append(key + "\t");
		});
    	sb.deleteCharAt(sb.length()-1);	// HEADER 마지막 탭 제거
		sb.append("\n");

    	// EP 데이터 세팅
    	List<SOMap> epGoodsList = goodsMapper.selectNaverEpGoodsList(param);
    	if (epGoodsList != null && epGoodsList.size() > 0) {
            // 카드할인정보 세팅
    		SOMap cardPromition = tossService.tossCardPromotion(param);
    		// 카드즉시할인정보
    		List<SOMap> discountCardList = Util.convertToListSOMap(cardPromition.getArrayList("discountcards"));
    		for (SOMap discountCard : discountCardList) {
    			// 할인종료일자 확인
    			if (DateTimeUtil.getDiffDays(DateTimeUtil.getNowDatePartStr(), discountCard.getDateStr("dueDate")) < 0) {
    				discountCardList.remove(discountCard);
    				continue;
    			}
    			// 예산금액 확인
    			if (discountCard.getInt("balance") <= 0) {
    				discountCardList.remove(discountCard);
    				continue;
    			}
    			// 카드명 세팅
    			discountCard.put("cardname", String.format("%s%s", discountCard.getStr("cardCompany"), "카드"));
    		}

    		// 카드무이자정보
    		List<SOMap> interestFreeCardList = Util.convertToListSOMap(cardPromition.getArrayList("interestfreecards"));
    		for (SOMap interestFreeCard : interestFreeCardList) {
    			// 할인종료일자 확인
    			if (DateTimeUtil.getDiffDays(DateTimeUtil.getNowDatePartStr(), interestFreeCard.getDateStr("dueDate")) < 0) {
    				discountCardList.remove(interestFreeCard);
    				continue;
    			}
    			// 카드명 세팅
    			interestFreeCard.put("cardname", String.format("%s%s", interestFreeCard.getStr("cardCompany"), "카드"));
    			// 할부개월 세팅
    			List<String> freeMonthList = interestFreeCard.getArrayList("installmentFreeMonths");
    			if (freeMonthList == null || freeMonthList.size() == 0) {
    				discountCardList.remove(interestFreeCard);
    				continue;
    			}
    			interestFreeCard.put("freemonth", String.format("%s~%s", freeMonthList.get(0), freeMonthList.get(freeMonthList.size()-1)));
    		}

    		// BODY 세팅
    		for (int i=0; i<epGoodsList.size(); i++) {
    			SOMap goodsMap = epGoodsList.get(i);
    			String brandName = goodsMap.getStr("brandnm");
    			String goodsName = Util.isEmpty(brandName)? goodsMap.getStr("goodsname") : String.format("[%s] %s", brandName, goodsMap.getStr("goodsname"));

    			sb.append(goodsMap.getStr("goodscode") + "\t");
    			sb.append(goodsName + "\t");
    			sb.append(goodsMap.getStr("pcprice") + "\t");
    			sb.append(goodsMap.getStr("moprice") + "\t");
    			sb.append(goodsMap.getStr("marketprice") + "\t");
    			sb.append(pclink + goodsMap.getStr("goodscode") + "\t");
    			sb.append(molink + goodsMap.getStr("goodscode") + "\t");
    			sb.append(goodsMap.getStr("imgurl") + "\t");
    			sb.append(goodsMap.getStr("addimgurls") + "\t");
    			sb.append(goodsMap.getStr("depth1name") + "\t");
    			sb.append(goodsMap.getStr("depth2name") + "\t");
    			sb.append(goodsMap.getStr("depth3name") + "\t");
    			sb.append(goodsMap.getStr("depth4name") + "\t");
    			sb.append(goodsMap.getStr("goodsdivtypenm") + "\t");
    			sb.append(goodsMap.getStr("isimport") + "\t");
    			sb.append(goodsMap.getStr("iscncappr") + "\t");
    			sb.append(brandName + "\t");
    			sb.append(goodsMap.getStr("coupondetail") + "\t");
    			sb.append(goodsMap.getStr("isdownload") + "\t");
    			sb.append(goodsMap.getStr("pointdetail") + "\t");

    			String cardevent = discountCardList.stream().filter(m -> goodsMap.getInt("marketprice") >= m.getInt("minimumpaymentamount"))
						.map(m-> String.format("%s^%s", m.getStr("cardname"), m.getStr("discountAmount")))
						.collect(Collectors.joining("|"));
    			sb.append(cardevent + "\t");

    			String interestfreeevent = interestFreeCardList.stream().filter(m -> goodsMap.getInt("marketprice") >= m.getInt("minimumpaymentamount"))
	    				.map(m-> String.format("%s^%s", m.getStr("cardname"), m.getStr("freemonth")))
	    				.collect(Collectors.joining("|"));
    			sb.append(interestfreeevent + "\t");

    			sb.append(goodsMap.getStr("keyword").replaceAll(",", "|") + "\t");
    			sb.append(goodsMap.getStr("minordcnt") + "\t");
    			sb.append(goodsMap.getStr("reviewcnt") + "\t");
    			sb.append(goodsMap.getStr("delivfare") + "\t");
    			sb.append(goodsMap.getStr("keyword").replaceAll(",", "^") + "\t");
    			sb.append(goodsMap.getStr("optiondetail") + "\t");
    			sb.append(goodsMap.getStr("dealerid") + "\t");
    			sb.append(goodsMap.getStr("delivdetail"));

	    		String className = "";
    	    	if (Util.equal(epKind, CMConst.EP_KIND_BRIEF)) {
    	    		sb.append("\t");
    	    		if (Util.equal(goodsMap.getStr("isepif"), "T")
    	    			&& Util.equal(goodsMap.getStr("goodsselltype"), CMConst.GOODS_SELL_TYPE_SALE)
    	    			&& Util.equal(goodsMap.getStr("isdisplay"), "T")) {
						if ( DateTimeUtil.getDiffDays(goodsMap.getStr("regdate"), goodsMap.getStr("moddate")) <= 1 ) {
							// 신규상품
							className = "I";
						} else {
							// 업데이트상품
							className = "U";
						}
					} else {
						// 품절상품
						className = "D";
					}
    	    		sb.append(className + "\t");
    	    		sb.append(updateTime);
    	    	}
    	    	if (i < epGoodsList.size()-1) {
    	    		sb.append("\n");
    	    	}
    		}
        	logger.debug(sb.toString());
    	} else {
        	sb.deleteCharAt(sb.length()-1);	// HEADER 개행 제거
    	}

    	// tsv EP파일 생성
    	String fileName = "epGoodsAllList.tsv";
    	if (Util.equal(epKind, CMConst.EP_KIND_BRIEF)) {
        	fileName = "epGoodsBriefList.tsv";
    	}
		String epRootPath = prop.getProperty("base.upload.naver.ep.path");

		fileService.uploadEpFile(sb.toString(), fileName, epRootPath);
		//this._writeTxtFile(fileName, sb.toString());

		resultMap.put("epGoodsCnt", epGoodsList.size());
		resultMap.put("epText", sb.toString());

        return resultMap;
    }

    private Map<String, String> _getEpGoodsHeaderMap(String epKind) {
    	if (Util.isEmpty(epKind)) {
    		epKind = CMConst.EP_KIND_ALL;
    	}

    	Map<String, String> headerMap = new LinkedHashMap<String, String>();
    	headerMap.put("id", "goodscode");
    	headerMap.put("title", "goodsname");
    	headerMap.put("price_pc", "pcprice");
    	headerMap.put("price_mobile", "moprice");
    	headerMap.put("normal_price", "marketprice");
    	headerMap.put("link", "pclink");
    	headerMap.put("mobile_link", "molink");
    	headerMap.put("image_link", "imgurl");
    	headerMap.put("add_image_link", "addimgurls");
    	headerMap.put("category_name1", "depth1name");
    	headerMap.put("category_name2", "depth2name");
    	headerMap.put("category_name3", "depth3name");
    	headerMap.put("category_name4", "depth4name");
    	headerMap.put("condition", "goodsdivtypenm");
    	headerMap.put("import_flag", "isimport");
    	headerMap.put("order_made", "iscncappr");
    	headerMap.put("brand", "brandnm");
    	headerMap.put("coupon", "coupondetail");
    	headerMap.put("point", "pointdetail");
    	headerMap.put("partner_coupon_download", "isdownload");
    	headerMap.put("card_event", "cardevent");
    	headerMap.put("interest_free_event", "interestfreeevent");
    	headerMap.put("search_tag", "keyword");
    	headerMap.put("minimum_purchase_quantity", "minordcnt");
    	headerMap.put("review_count", "reviewcnt");
    	headerMap.put("shipping", "delivfare");
    	headerMap.put("attribute", "keyword");
    	headerMap.put("option_detail", "optiondetail");
    	headerMap.put("seller_id", "dealerid");
    	headerMap.put("shipping_settings", "delivdetail");

    	if (Util.equal(epKind, CMConst.EP_KIND_BRIEF)) {
        	headerMap.put("class", "classname");
        	headerMap.put("update_time", "updatetime");
    	}

    	return headerMap;
    }
	
	@Override
	public SOMap createMetaEp(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
		
		StringBuilder sb = new StringBuilder();
		String pclink = String.format("%s%s", prop.getProperty("front.domain"), "/shop/detail/");
		String epRootPath = prop.getProperty("base.upload.meta.ep.path");
		
		// HEADER 세팅 (첫번째 행)
		Map<String, String> headerMap = this._getMetaEpGoodsHeaderMap();
		headerMap.forEach((key, value) -> {
			sb.append(key + "\t");
		});
		sb.deleteCharAt(sb.length()-1);	// HEADER 마지막 탭 제거
		sb.append("\n");
		
		// EP 데이터 세팅
		List<SOMap> epGoodsList = goodsMapper.selectCommonEpGoodsList(param);
		if (epGoodsList != null && epGoodsList.size() > 0) {
			
			// BODY 세팅
			for (int i=0; i<epGoodsList.size(); i++) {
				SOMap goodsMap = epGoodsList.get(i);
				
				sb.append(goodsMap.getStr("goodscode") + "\t");
				sb.append(goodsMap.getStr("goodsname") + "\t");
				sb.append(goodsMap.getStr("summary")+ "\t");
				sb.append("in stock" + "\t");
				sb.append("new" + "\t");
				sb.append(goodsMap.getStr("marketprice") + " KRW\t");
				sb.append(pclink + goodsMap.getStr("goodscode") + "\t");
				sb.append(goodsMap.getStr("imgurl") + "\t");
				sb.append(goodsMap.getStr("brandnm") + "\t");
				sb.append(goodsMap.getStr("pcprice") + " KRW\t");
				sb.append("KR:::" + goodsMap.getStr("delivfare") + " KRW\t");
				sb.append(goodsMap.getStr("epname") + "\t");
				sb.append(goodsMap.getStr("brandeng") + "\t");
				sb.append(goodsMap.getStr("fullcategoryname"));
				
				if (i < epGoodsList.size()-1) {
					sb.append("\n");
				}
			}
			logger.debug(sb.toString());
		} else {
			sb.deleteCharAt(sb.length()-1);	// HEADER 개행 제거
		}
		
		// tsv EP파일 생성
		String fileName = "epGoodsAllList.tsv";
		fileService.uploadEpFile(sb.toString(), fileName, epRootPath);
		
		resultMap.put("epGoodsCnt", epGoodsList.size());
		resultMap.put("epText", sb.toString());
		
		return resultMap;
	}

	private Map<String, String> _getMetaEpGoodsHeaderMap() {
		Map<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("id", "goodscode");
		headerMap.put("title", "goodsname");
		headerMap.put("description", "summary");
		headerMap.put("availability", "availability");
		headerMap.put("condition", "condition");
		headerMap.put("price", "marketprice");
		headerMap.put("link", "pclink");
		headerMap.put("image_link", "imgurl");
		headerMap.put("brand", "brandnm");
		headerMap.put("sale_price", "pcprice");
		headerMap.put("shipping", "delivfare");
		headerMap.put("google_product_category", "epname");
		headerMap.put("brand_eng", "brandeng");
		headerMap.put("category", "fullcategoryname");

		return headerMap;
	}
	
	@Override
	public SOMap createGoogleEp(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
		
		StringBuilder sb = new StringBuilder();
		String pclink = String.format("%s%s", prop.getProperty("front.domain"), "/shop/detail/");
		String epRootPath = prop.getProperty("base.upload.google.ep.path");
		
		// HEADER 세팅 (첫번째 행)
		Map<String, String> headerMap = this._getGoogleEpGoodsHeaderMap();
		headerMap.forEach((key, value) -> {
			sb.append(key + "\t");
		});
		sb.deleteCharAt(sb.length()-1);	// HEADER 마지막 탭 제거
		sb.append("\n");
		
		// EP 데이터 세팅
		List<SOMap> epGoodsList = goodsMapper.selectCommonEpGoodsList(param);
		if (epGoodsList != null && epGoodsList.size() > 0) {
			
			// BODY 세팅
			for (int i=0; i<epGoodsList.size(); i++) {
				SOMap goodsMap = epGoodsList.get(i);
				
				sb.append(goodsMap.getStr("goodscode") + "\t");
				sb.append(goodsMap.getStr("goodsname") + "\t");
				sb.append(goodsMap.getStr("summary")+ "\t");
				sb.append(pclink + goodsMap.getStr("goodscode") + "\t");
				sb.append("new" + "\t");
				sb.append(goodsMap.getStr("marketprice") + " KRW\t");
				sb.append("in stock" + "\t");
				sb.append(goodsMap.getStr("imgurl") + "\t");
				sb.append("" + "\t");
				sb.append("" + "\t");
				sb.append(goodsMap.getStr("brandnm") + "\t");
				sb.append(goodsMap.getStr("epname") + "\t");
				sb.append(goodsMap.getStr("pcprice") + " KRW\t");
				sb.append("KR:::" + goodsMap.getStr("delivfare") + " KRW\t");
				sb.append(goodsMap.getStr("brandeng") + "\t");
				sb.append(goodsMap.getStr("fullcategoryname"));
				
				if (i < epGoodsList.size()-1) {
					sb.append("\n");
				}
			}
			logger.debug(sb.toString());
		} else {
			sb.deleteCharAt(sb.length()-1);	// HEADER 개행 제거
		}
		
		// tsv EP파일 생성
		String fileName = "epGoodsAllList.tsv";
		
		fileService.uploadEpFile(sb.toString(), fileName, epRootPath);
		
		resultMap.put("epGoodsCnt", epGoodsList.size());
		resultMap.put("epText", sb.toString());
		
		return resultMap;
	}
	
	private Map<String, String> _getGoogleEpGoodsHeaderMap() {
		Map<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("id", "goodscode");
		headerMap.put("제목", "goodsname");
		headerMap.put("설명", "summary");
		headerMap.put("링크", "pclink");
		headerMap.put("상태", "condition");
		headerMap.put("가격", "marketprice");
		headerMap.put("재고수량", "availability");
		headerMap.put("이미지 링크", "imgurl");
		headerMap.put("gtin", "gtin");
		headerMap.put("mpn", "mpn");
		headerMap.put("상표", "brandnm");
		headerMap.put("google 상품 카테고리", "epname");
		headerMap.put("할인가", "pcprice");
		headerMap.put("배송", "delivfare");
		headerMap.put("brand_eng", "brandeng");
		headerMap.put("original category", "fullcategoryname");
		
		return headerMap;
	}

	/**
	 * 파일 생성
	 * @param fileName(확장자 포함)
	 * @param txtData
	 * @throws Exception
	 */
	 private void _writeTxtFile(String fileName, String txtData) throws Exception {
    	String epRootPath = prop.getProperty("base.upload.naver.ep.path");

		try{
			// EP 경로 없으면 디렉토리 생성
			File epDir = new File(epRootPath);
			if (!epDir.exists()) {
				epDir.mkdir();
			}

			String filePath = epRootPath + fileName;
			File file = new File(filePath);

			// 이전파일 존재시 백업파일 생성
			if (file.exists()) {
				// 날짜별 디렉토리 생성
				File newDir = new File(String.format("%s%s", epRootPath, DateTimeUtil.getNowDatePartShortStr()));
				if (!newDir.exists()) {
					newDir.mkdir();
				}
				String newFilePath = String.format("%s/%s_%s", newDir, fileName, DateTimeUtil.getNowFullShortStr());
				File newFile = new File(newFilePath);
				file.renameTo(newFile);
				file.delete();
			}

			BufferedWriter fw = new BufferedWriter(new FileWriter(filePath, true));
			fw.write(txtData);
			fw.flush();
			fw.close();
		} catch(Exception e){
            logger.error(e.getMessage());
		}
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap memberSleepRestore(SOMap params) throws Exception {
        SOMap result = new SOMap();
        String[] userNoArr = params.getStr("user_no_arr_str").split(",");
        params.put("siteid", cs.getStr("siteid"));
        
        if(userNoArr != null && userNoArr.length > 0)
        	params.put("user_no_arr", userNoArr);

        params.put("ip", ServletRequestInfoUtil.getRequestIp());

        int restoreCnt = memberSleepMapper.memberSleepRestore(params);
        int deleteCnt = memberSleepMapper.deleteMemberSleepArray(params);
        int insertUserLogCnt = userLogMapper.insertUserLogArray(params);

        for(String userNo : userNoArr){
            SOMap erpParam = new SOMap();
            erpParam.put("siteid", cs.getStr("siteid"));
            erpParam.put("userno", userNo);
            erpParam.put("aud", "U");
            erpService.insertMemberERPData(erpParam);
        }

        result.put("restore", restoreCnt);
        result.put("delete", deleteCnt);
        result.put("log", insertUserLogCnt);
        return result;
    }
}