package com.dplot.front.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.CertMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.CommonCodeMapper;
import com.dplot.mapper.ConfigMapper;
import com.dplot.mapper.ConfigTermMapper;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.CouponMemissueMapper;
import com.dplot.mapper.MemberAddressMapper;
import com.dplot.mapper.MemberHistoryMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.MemberMemoMapper;
import com.dplot.mapper.MemberRecomMapper;
import com.dplot.mapper.MemberSleepMapper;
import com.dplot.mapper.MemberSnsMapper;
import com.dplot.mapper.RecomRewardLogMapper;
import com.dplot.mapper.RecomRewardMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.util.CryptHash;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;

import ucar.ma2.ArrayDouble.D3.IF;

/**
 * The Class FrontMemberServiceImpl.
 */
@Service
public class FrontMemberServiceImpl extends MallBaseService implements FrontMemberService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(FrontMemberServiceImpl.class);

	/** The MemberMapper. */
	@Autowired
	private MemberMapper memberMapper;

	/** The MemberSnsMapper. */
	@Autowired
	private MemberSnsMapper memberSnsMapper;

	/** The MemberSleepMapper. */
	@Autowired
	private MemberSleepMapper memberSleepMapper;

	/** The MemberMemoMapper. */
	@Autowired
	private MemberMemoMapper memberMemoMapper;

	/** The CouponMapper`. */
	@Autowired
	private CouponMapper couponMapper;

	/** The CouponMemissueMapper. */
	@Autowired
	private CouponMemissueMapper couponMemissueMapper;

	/** The RecomRewardMapper. */
	@Autowired
	private RecomRewardLogMapper recomRewardLogMapper;

	@Autowired
	private MemberRecomMapper memberRecomMapper;

	@Autowired
	private RecomRewardMapper recomRewardMapper;

	/** The UserMapper. */
	@Autowired
	private UserMapper userMapper;

	/** The ConfigTermMapper. */
	@Autowired
	private ConfigTermMapper configTermMapper;

	/** The CommonCodeMapper. */
	@Autowired
	private CommonCodeMapper commonCodeMapper;

	/** The CertMapper. */
	@Autowired
	private CertMapper certMapper;

	@Autowired
	private CJMessageService cjMessageService;

	@Autowired
	private MemberAddressMapper memberAddressMapper;

	@Autowired
	private ERPService eRPService;

	@Autowired
	private MemberHistoryMapper memberHistoryMapper;

	@Autowired
	private ConfigMapper configMapper;

	@Autowired
	private CommonService commonservice;

	@Autowired
	private ComOrderMapper comOrderMapper;

	@Resource(name = "propertiesFactory")
	private Properties prop;

	/**
	 * 이름, 이메일 또는 핸드폰번호로 아이디 찾기
	 *
	 * @param name    이름
	 * @param emailhp 이메일 또는 핸드폰번호
	 * 
	 */
	@Override
	public SOMap findId(SOMap param) throws Exception {
		// 별표처리한 값만 나와야함
		param.put("astar", "T");

		param.put("state", CMConst.CM_STATE_REAL);
		param.put("siteid", cs.getStr("siteid"));

		if (Util.isEmpty(param.get("name"))) {
			throw new BizException("입력해주세요.");
		}

		if (Util.isEmpty(param.get("emailhp"))) {
			throw new BizException("이메일 또는 핸드폰번호를 입력해주세요.");
		}

		SOMap member = memberMapper.selectIdByNameEmailHp(param);

		SOMap result = new SOMap();
		if (member == null) {
			result.put("isMember", "NO");
			return result;
		}

		result.putAll(member);

		if (!CMConst.CM_JOINCHTYPE_DADAPICK.equals(member.getDbStr("joinchtype"))) {
			result.put("isMember", "SNS");
			return result;
		}

		result.put("isMember", "OK");
		if (Util.isNotEmpty(member.get("userid"))) {
			String tempMemberId = member.get("userid").toString();
			tempMemberId = Util.asterisk(tempMemberId, 2, 0);
			result.put("useridaster", tempMemberId);
		}
		if (Util.isNotEmpty(member.get("email"))) {
			String emailAster = member.get("email").toString().trim();
			String[] emailArr = emailAster.split("@");
			if (emailArr.length > 1) {
				emailAster = emailArr[0].substring(0, 2) + "****@" + emailArr[1];
				result.put("emailaster", emailAster);
			}
		}
		if (Util.isNotEmpty(member.get("mobile"))) {
			String mobile = member.get("mobile").toString().trim();
			mobile = Util.phoneFormat(mobile);
			String[] mobileArr = Util.getTelPhoneSpliter(mobile);
			if (mobileArr.length > 2) {
				mobileArr[1] = Util.asterisk(mobileArr[1], 0, 0);
				result.put("mobileaster", mobileArr[0] + "-" + mobileArr[1] + "-" + mobileArr[2]);
			}
		}

		// 아스타만 보여야할 경우 원본데이터 맵에서 삭제
		if (param.get("astaronly") != null && "T".equals(param.get("astaronly"))) {
			result.remove("memberid");
			result.remove("mobile");
			result.remove("email");
		}
		return result;
	}

	@Override
	public void sendId(SOMap param) throws Exception {
		if (Util.isEmpty(param.get("type"))) {
			new BizException("이메일/모바일 타입이 지정되지 않았습니다.");
		}

		param.put("state", CMConst.CM_STATE_REAL);
		param.put("siteid", cs.getStr("siteid"));

		SOMap member = memberMapper.selectMember(param);

		if (member == null) {
			throw new BizException("일치하는 정보가 없습니다");
		}
		if ("EMAIL".equals(param.getDbStr("type"))) {

		} else {
			// 문자인증
			SOMap messageParam = new SOMap();
			messageParam.put("msg", String.format("[D.PLOT] 회원님의 아이디는\n[%s]입니다.", member.getDbStr("userid")));
			messageParam.put("msg_type", "SMS");
			messageParam.put("receiver_number", member.getDbStr("mobile"));
			cjMessageService.sendMessage(messageParam);
		}
		// am.add(userEmail, userName, new String[][] {{"MEMBER_ID", userId},
		// {"MEMBER_NAME", userName}});
		// am.send();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap sendAuthNum(SOMap param) throws Exception {
		SOMap result = new SOMap();

		if (Util.isEmpty(param.get("userid"))) {
			throw new BizException("아이디를 입력해주세요.");
		}

		if (Util.isEmpty(param.get("name"))) {
			throw new BizException("이름을 입력해주세요.");
		}

		if (Util.isEmpty(param.get("emailhp"))) {
			throw new BizException("이메일 또는 핸드폰번호를 입력해주세요.");
		}

		param.put("state", CMConst.CM_STATE_REAL);
		param.put("siteid", cs.getStr("siteid"));
		SOMap member = memberMapper.selectIdByNameEmailHp(param);

		if (member == null) {
			throw new BizException("일치하는 사용자 정보가 없습니다.");
		}

		result.putAll(member);

		if (!CMConst.CM_JOINCHTYPE_DADAPICK.equals(member.getDbStr("joinchtype"))) {
			result.put("isMember", "SNS");
			return result;
		}

		result.put("isMember", "OK");
		// 타입 email, hp
		String type = param.get("type").toString();

		// 인증만료시간
		// 이메일이면 10분 , 핸드폰은 3분
		int timeout = "EMAIL".equals(type) ? 600 : 180;

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, timeout);

		// Date -> DB Date로 변환
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String aulimitdateStr = sdf.format(cal.getTime());

		Map<String, Object> dbparams = new HashMap<>();
		dbparams.put("aulimitdate", aulimitdateStr);

		// 인증번호
		String authnum = RandomStringUtils.randomNumeric(6);
		dbparams.put("authnum", authnum);

		// 유저번호
		String no = member.get("userno").toString();
		dbparams.put("no", no);

		// 유저번호에 인증번호와 만료시간 업데이트
		int updateCnt = userMapper.updateUserAuthNumAndAuLimitDate(dbparams);

		// 업데이트 안되면 실패
		if (updateCnt != 1) {
			throw new BizException("인증번호 전송에 실패했습니다.");
		}

		// 화면에 사용할 timeout
		result.put("timeout", timeout);

		// 참고용 만료시간
		result.put("aulimitdate", aulimitdateStr);
		SOMap messageParam = new SOMap();
		if ("EMAIL".equals(type)) {
			messageParam.put("title", "비밀번호 찾기");
			messageParam.put("number", authnum);
			messageParam.put("email", param.get("emailhp"));
			cjMessageService.sendEmpAuthNumber(messageParam);
		} else {
			messageParam.put("msg", String.format("[D.PLOT]인증번호는 %s입니다.", authnum));
			messageParam.put("msg_type", "SMS");
			messageParam.put("receiver_number", param.get("emailhp"));
			cjMessageService.sendMessage(messageParam);
		}
		return result;

	}

	@Override
	public SOMap confirmAuthNum(SOMap param) throws Exception {
		SOMap result = new SOMap();

		if (Util.isEmpty(param.get("userid"))) {
			throw new BizException("아이디를 입력해주세요.");
		}

		if (Util.isEmpty(param.get("name"))) {
			throw new BizException("이름을 입력해주세요.");
		}

		if (Util.isEmpty(param.get("emailhp"))) {
			throw new BizException("이메일 또는 핸드폰번호를 입력해주세요.");
		}

		if (Util.isEmpty(param.get("authnum"))) {
			throw new BizException("인증번호를 입력해주세요.");
		}

		param.put("state", CMConst.CM_STATE_REAL);
		param.put("siteid", cs.getStr("siteid"));
		SOMap member = memberMapper.selectIdByNameEmailHp(param);

		if (member == null) {
			throw new BizException("일치하는 사용자 정보가 없습니다.");
		}
		param.put("no", member.getDbInt("userno"));

		SOMap auth = userMapper.selectUserAll(param);
		if (auth == null || Util.isEmpty(auth.getDateStr("authnum"))) {
			throw new BizException("인증요청정보가 없습니다.");
		}

		// 인증번호 확인 여부
		boolean authorized = false;
		boolean matched = false; // 인증번호 일치여부 체크 -> false면 유효하지않음
		boolean timeout = false; // 타임아웃 체크 -> false면 인증시간초과

		// DB에서 조회한 인증번호
		String targetAuthnum = auth.getDbStr("authnum");

		// 사용자가 입력한 인증번호
		String inputAuthnum = param.getStrTrim("authnum");

		// 시간초과 했는지 체크
		String authLimitDate = auth.getDateStr("aulimitdate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date();
		Date limit = sdf.parse(authLimitDate);
		if (now.getTime() <= limit.getTime()) {
			timeout = true;
		}

		// 시간초과 안했고, 입력한 인증정보랑 같은지 체크
		if (targetAuthnum.equals(inputAuthnum)) {
			matched = true;
		}

		// 시간초과안했고, 입력한 인증번호랑 맞으면 true 아니면 false
		authorized = timeout && matched ? true : false;

		result.put("matched", matched);
		result.put("timeout", timeout);
		result.put("authorized", authorized);
		result.put("userno", auth.get("no"));

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap changePw(SOMap param) throws Exception {

		SOMap result = new SOMap();

		// 비밀번호 변경알림에서 왔을 경우, 비밀번호 확인을 입력하지 않음
		String isOldPw = Util.toFlag(Util.getStr(param.getStr("isoldpw"), "F"));

		if (Util.isEmpty(param.get("userno"))) {
			throw new BizException("잘못된 접근입니다.");
		}

		if ("T".equals(isOldPw) && Util.isEmpty(param.get("oldpw"))) {
			throw new BizException("현재 비밀번호를 입력해주세요.");
		}

		if (Util.isEmpty(param.get("newpw"))) {
			throw new BizException("새 비밀번호를 입력해주세요.");
		}

		if (Util.isEmpty(param.get("confirmpw"))) {
			throw new BizException("비밀번호 확인을 입력해주세요.");
		}

		if (!param.get("newpw").equals(param.get("confirmpw"))) {
			throw new BizException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
		}

		if (!Util.isPassword(param.getDbStr("newpw"))) {
			throw new BizException("최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 또는 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합으로 입력해야합니다.");
		}

		SOMap dbparams = new SOMap();
		dbparams.put("no", param.getDbInt("userno"));

		SOMap oldPwMap = userMapper.selectUserAll(dbparams);
		if (oldPwMap == null) {
			throw new BizException("사용자 정보가 없습니다.");
		}

		String oldPw = oldPwMap.getDbStr("userpw");

		if ("T".equals(isOldPw)) {
			// 이전 비밀번호 입력 암호화
			String inputOldPw = param.getDbStr("oldpw");
			String encInputOldPw = CryptHash.hash(inputOldPw);

			if (Util.isEmpty(oldPwMap.get("userpw"))) {
				throw new BizException("현재 비밀번호 정보가 없습니다.");
			}

			if (!oldPw.equals(encInputOldPw)) {
				throw new BizException("현재 비밀번호가 일치하지 않습니다.");
			}
		}

		// 신규 비밀번호 암호화
		String encNewPw = CryptHash.hash(param.getDbStr("newpw"));

		// 신규비밀번호랑 기존 비밀번호랑 같은지 체크
		if (encNewPw.equals(oldPw)) {
			throw new BizException("신규비밀번호가 이전 비밀번호와 같습니다. 다른 비밀번호를 입력해주세요.");
		}

		dbparams.put("userpw", encNewPw);

		int cnt = userMapper.updateUser(dbparams);

		if (cnt != 1) {
			throw new BizException("비밀번호 변경에 실패했습니다.");
		}

		// 로그인 실패 횟수 초기화
		userMapper.resetPwFailCnt(dbparams);

		return result;
	}

	@Override
	public void updatePwChangeDateNowByNo(SOMap param) throws Exception {
		userMapper.updatePwChangeDateNowByNo(param);
	}

	@Override
	public boolean checkUserId(SOMap param) throws Exception {
		boolean checkUserId = false;

		String cfgDefaultBlockID = "," + cs.getStr("cfgdefaultblockid").toLowerCase() + ",";
		String checkId = "," + param.getDbStr("userid").toLowerCase() + ",";

		// 기본 차단 ID 확인
		if (cfgDefaultBlockID.indexOf(checkId) > -1) {
			checkUserId = true;
		}

		param.put("siteid", cs.getStr("siteid"));

		// 선택 차단 ID 확인
//		param.put("siteid", cs.getStr("siteid"));
//		SOMap configUser = configUserMapper.selectConfigUserBlockId(param);

//		if (Util.isNotEmpty(configUser)) {
//			String blockID = "," + configUser.getDbStr("blockId").toLowerCase() + ",";
//			if (blockID.indexOf(checkId) > -1) {
//				checkUserId = true;
//			}
//		}

		// 사용자 ID 확인
		int userCount = userMapper.selectUserCount(param);

		if (userCount > 0) {
			checkUserId = true;
		}

		return checkUserId;
	}

	/*******************************
	 * 이메일 check
	 *******************************/
	@Override
	public boolean checkEmail(SOMap param) throws Exception {
		boolean checkEmail = false;
		int emailCount = memberMapper.selectMemberCntByEmail(param);
		if (emailCount > 0) {
			checkEmail = true;
		}
		return checkEmail;
	}

	/*******************************
	 * 30일 탈퇴회원 이메일 check
	 *******************************/
	@Override
	public boolean checkSleepMemberEmail(SOMap param) throws Exception {
		boolean checkEmail = false;
		int emailCount = memberSleepMapper.selectSleepEmailCheck(param);
		if (emailCount > 0) {
			checkEmail = true;
		}
		return checkEmail;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap sendAuthEmail(SOMap param) throws Exception {

		if (Util.isEmpty(param.getDbStr("email"))) {
			throw new BizException("이메일을 입력해주세요.");
		}

		param.put("cmclass", "EMPDOMAIN");
		param.put("istrash", "F");
		List<SOMap> employDomain = commonCodeMapper.selectCodeList(param);

		boolean isEmplyDomain = false;
		for (SOMap domain : employDomain) {
			if (param.getDbStr("email").endsWith(domain.getDbStr("codename"))) {
				isEmplyDomain = true;
				break;
			}
		}

		if (!isEmplyDomain) {
			throw new BizException("임직원용 이메일이 아닙니다.");
		}

		// 인증시간 이메일 10분
		int timeout = 60;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, timeout);

		// Date -> DB Date로 변환
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String aulimitdateStr = sdf.format(cal.getTime());
		param.put("aulimitdate", aulimitdateStr);

		// 인증번호
		String authnum = RandomStringUtils.randomNumeric(6);
		param.put("authnum", authnum);

		// 인증번호 저장
		param.put("isemail", "T");
		param.put("emailhp", param.get("email"));
		param.put("certno", authnum);
		certMapper.insertCert(param);

		// 이메일발송
		SOMap messageParam = new SOMap();
		messageParam.put("title", "임직원");
		messageParam.put("number", authnum);
		messageParam.put("email", param.get("emailhp"));
		cjMessageService.sendEmpAuthNumber(messageParam);

		SOMap result = new SOMap();
		result.put("timeout", timeout);
		result.put("aulimitdate", aulimitdateStr);

		return result;
	}

	@Override
	public void confirmAuthEmail(SOMap param) throws Exception {
		if (Util.isEmpty(param.getDbStr("email"))) {
			throw new BizException("이메일을 입력해주세요.");
		}
		if (Util.isEmpty(param.getDbStr("authnum"))) {
			throw new BizException("인증번호를 입력해주세요.");
		}

		// 인증번호 조회
		param.put("emailhp", param.getDbStr("email"));
		String autnNum = certMapper.selectCert(param);
		// 인증번호 비교
		if (!autnNum.equals(param.getDbStr("authnum"))) {
			throw new BizException("인증번호가 일치하지 않습니다.");
		}
	}

	@Override
	public List<SOMap> selectSignUpTerm(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		if (param.getStr("termslist").equals("")) {
			//param.put("termslist", new String[] { "TRT001", "TRT002", "TRT003", "TRT004", "TRT005", "TRT006" });
			param.put("termslist", new String[] { "TRT001", "TRT002", "TRT003", "TRT004"});
		}

		return configTermMapper.selectConfigTerm(param);
	}

	@Override
	public SOMap snsLogin(SOMap param) throws Exception {
		SOMap result = new SOMap();
		result.put("snsInfo", param);

		param.put("siteid", cs.getStr("siteid"));
		// SNS 로그인정보(snsmemberno,snstype)
		if (!param.getStr("mobile").equals("")) {
			param.put("mobile", param.getStr("mobile").replace("-", ""));
		}

		SOMap dbparam = new SOMap();
		dbparam.put("siteid", cs.getStr("siteid"));
		dbparam.put("mobile", param.getStr("mobile"));
		dbparam.put("email", param.getStr("email"));
		dbparam.put("ci", param.getStr("conninfo"));
		/*******************************
		 * 기 탈퇴회원 체크
		 ********************************/
		int witdrawCnt = memberSleepMapper.selectWithdrawMemberCnt(dbparam);
		if (witdrawCnt <= 0) {
			dbparam.remove("ci");
			witdrawCnt = memberSleepMapper.selectWithdrawMemberCnt(dbparam);
		}else {
			result.put("witdrawCnt", witdrawCnt);
			return result;
		}
		/*****************************************************
		 * SNS 회원번호 중복 체크 - O:SNS 로그인 연결처리 - X:일반 로그인 연결처리 - CI는 KMC, NAVER, KAKAO 채널 모두
		 * 같음-주민번호 같은거
		 *****************************************************/
		/*************
		 * 1.SNSMEMBERNO CHECK
		 *************/
		List<SOMap> memberList = memberSnsMapper.selectMemberSns(param);
		if (memberList.size() > 0) {
			result.put("memberSns", memberList);
			return result;
		}
		/*****************************************************
		 * 기가입 중복 체크 - CI, HP, EMAIL CHECK
		 *****************************************************/
		/*************
		 * 2.CI CHECK
		 *************/
		SOMap memberParam = new SOMap();
		memberParam.put("siteid", cs.getStr("siteid"));
		memberParam.put("conninfo", param.getStr("conninfo"));

		// 현재 ci값을 못받고 있어서 처리
		if (!param.getStr("conninfo").equals("")) {
			memberList = memberSnsMapper.selectMemberbyCi(memberParam);
			for (int i = 0; i < memberList.size(); i++) {
				memberList.get(i).put("isuser", "T");
			}
		}
		/*************
		 * 3.HP, EMAIL CHECK 참고)현재 카카오 로그인후 네이버 로그인시 ci값을 못받고 있어서 로그인으로 발생할거 이유=> ci
		 * 불일치, email,mobile 불일치
		 *************/
		if (memberList.size() <= 0) {
			memberParam.remove("conninfo");
			memberParam.put("email", param.getStr("email"));
			memberParam.put("mobile", param.getStr("mobile"));
			memberList = memberSnsMapper.selectMemberbyEmail(memberParam);

			/******************************
			 * 5.HP, EMAIL 둘다 중복일경우 => 회원일치
			 *******************************/
			for (int i = 0; i < memberList.size(); i++) {
				if (memberList.get(i).get("email").equals(memberParam.get("email"))
						&& memberList.get(i).get("mobile").equals(memberParam.get("mobile"))) {
					memberList.get(i).put("isuser", "T");
					// List<SOMap> temp = new ArrayList<>();
					// temp.add(memberList.get(i));
					// result.put("member", temp);
					// return result;
				} else {
					memberList.get(i).put("isuser", "F");
				}
			}
		}
		if (memberList.size() > 0) {
			/*************
			 * 4.기존 회원이 있을경우 masking처리
			 *************/
			for (int i = 0; i < memberList.size(); i++) {
				if (Util.isNotEmpty(memberList.get(i).get("userid"))) {
					String tempMemberId = memberList.get(i).get("userid").toString();
					tempMemberId = Util.asterisk(tempMemberId, 2, 0);
					memberList.get(i).put("useridaster", tempMemberId);
				}
				if (Util.isNotEmpty(memberList.get(i).get("email"))) {
					String emailAster = memberList.get(i).get("email").toString().trim();
					String[] emailArr = emailAster.split("@");
					if (emailArr.length > 1) {
						emailAster = emailArr[0].substring(0, 2) + "****@" + emailArr[1];
						memberList.get(i).put("emailaster", emailAster);
					}
				}
				if (Util.isNotEmpty(memberList.get(i).get("mobile"))) {
					String mobile = memberList.get(i).get("mobile").toString().trim();
					mobile = Util.phoneFormat(mobile);
					String[] mobileArr = Util.getTelPhoneSpliter(mobile);
					if (mobileArr.length > 2) {
						mobileArr[1] = Util.asterisk(mobileArr[1], 0, 0);
						memberList.get(i).put("mobileaster", mobileArr[0] + "-" + mobileArr[1] + "-" + mobileArr[2]);
					}
				}
			}
			result.put("member", memberList);
		}
		return result;
	}

	/**
	 * sns연결처리
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap snsConnect(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		/************************************
		 * 기가입 여부 한번 더  체크
		 ************************************/
		SOMap memberPram = new SOMap();
		memberPram.put("siteid", cs.getStr("siteid"));
		memberPram.put("conninfo", param.getStr("conninfo"));
		
		List<SOMap> memberList = new ArrayList<>(); 
		
		if (!memberPram.getStr("conninfo").equals("")) {
			memberList = memberSnsMapper.selectMemberbyCi(memberPram);
		}
		
		if (memberList.size() <= 0) {
			memberPram.remove("ci");
			memberPram.put("email", param.getStr("email"));
			memberPram.put("mobile", param.getStr("mobile"));
			memberList = memberSnsMapper.selectMemberbyEmail(memberPram);
		}
		if (memberList.size() <= 0) {
			throw new BizException("회원가입 정보가 없습니다.");
		}
		List<SOMap> snsInfoList = memberSnsMapper.selectMemberSns(param);
		if (snsInfoList.size() > 0) {
			throw new BizException("이미 SNS 연결이된 회원입니다.");
		}
		memberSnsMapper.insertMemberSns(param);
		
		SOMap dbParam = new SOMap();
		dbParam.put("userno", param.get("userno"));
		SOMap meminfno = memberMapper.selectMember(dbParam);
		// 광고성 변경 여부
		if (!param.getStr("isadsms").equals("") || !param.getStr("isadmailing").equals("")) {
			boolean isAd = false;
			// 기존 sms 와 email 광고성 수신여부 비교
			if (!meminfno.getStr("isadsms").equals(param.getStr("isadsms"))) {
				dbParam.put("isadsms", param.getStr("isadsms"));
				isAd = true;
			}
			if (!meminfno.getStr("isadmailing").equals(param.getStr("isadmailing"))) {
				dbParam.put("isadmailing", param.getStr("isadmailing"));
				isAd = true;
			}
			if (isAd) {
				memberMapper.updateMember(dbParam);
				saveMemberHistory(dbParam);
				/**********************************
				 * ERP 회원정보 수정
				 **********************************/
				SOMap erpData = new SOMap();
				erpData.put("siteid", cs.getStr("siteid"));
				erpData.put("userno", dbParam.get("userno"));
				erpData.put("aud", "U");
				eRPService.insertMemberERPData(erpData);
			}	
		}

		SOMap snsInfo = new SOMap();
		if (snsInfoList.size() > 0) {
			snsInfo = snsInfoList.get(0);
		}
		snsInfo.put("userid", memberList.get(0).get("userid"));
		snsInfo.put("snstype", param.get("snstype"));
		snsInfo.put("snsmemberno", param.get("snsmemberno"));
		return snsInfo;
	}
	
	/**
	 * APPLE SNS 연결 처리
	 */
	@Override
	public SOMap snsAppleConnect(SOMap param) throws Exception {
		SOMap snsInfo = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		if (param.getInt("userno") == 0) {
			throw new BizException("회원번호 정보가 없습니다.");
		}
		
		List<SOMap> snsInfoList = memberSnsMapper.selectMemberSns(param);
		if (snsInfoList.size() > 0) {
			throw new BizException("이미 SNS 연결이된 회원입니다.");
		}
		memberSnsMapper.insertMemberSns(param);
		
		if (snsInfoList.size() > 0) {
			snsInfo = snsInfoList.get(0);
		}
		snsInfo.put("snstype", param.get("snstype"));
		snsInfo.put("snsmemberno", param.get("snsmemberno"));
		return snsInfo;
	}

	/**
	 * 회원가입 처리
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public Response saveSignup(SOMap param) throws Exception {
		Response result = new Response();
		result.setErrorShow(false);
		param.put("siteid", cs.getStr("siteid"));
		/******************************
		 * 회원등록전 기존 회원인지 체크
		 *******************************/
		SOMap memberinfo = memberSnsMapper.selectMemberInfo(param);
		if (memberinfo != null) {
			throw new BizException("이미 가입한 회원입니다.");
		}
		/********************************
		 * 타당성 검사 및 회원가입 필요 데이터 셋팅
		 *******************************/
		param.put("joinchtype", CMConst.CM_JOINCHTYPE_DADAPICK);

		if (param.getStr("snstype").equals(CMConst.CM_SNSTYPE_NAVER)) {
			param.put("joinchtype", CMConst.CM_JOINCHTYPE_NAVER);
			param.put("userid", getSnsUserId("N"));
		} else if (param.getStr("snstype").equals(CMConst.CM_SNSTYPE_KAKAO)) {
			param.put("joinchtype", CMConst.CM_JOINCHTYPE_KAKAO);
			param.put("userid", getSnsUserId("K"));
		} else {
			if (param.getStr("snstype").equals(CMConst.CM_SNSTYPE_APPLE)) {
				param.put("joinchtype", CMConst.CM_JOINCHTYPE_APPLE);
			}
			/******************************
			 * 타당성검사
			 ******************************/
			this.validSignUp(param);
			/******************************
			 * 비밀번호 암호화
			 ******************************/
			String encPw = CryptHash.hash(param.getDbStr("userpw"));
			param.put("userpw", encPw);
		}

//		if (param.getStr("email").contains("cj")) {
//			param.put("company", "cj");
//			param.put("COMPANYTYPE", "CPT001");
//		}

		// TO-DO 가입여부 확인(본인인증, 소셜)
		/******************************
		 * 사용자 추가(user)
		 *******************************/
		param.put("jointype", cs.getStr("platform").replace("MAC", "UJT"));
		param.put("usertype", CMConst.USER_TYPE_MEMBER);
		userMapper.insertUser(param);
		/******************************
		 * 사용자종합 등록
		 *******************************/
		param.put("userno", param.getDbInt("no"));// 사용자번호 세팅
		// userTotalMapper.insertUserTotal(param);
		/******************************
		 * 회원등록 정보 셋팅
		 *******************************/
		param.put("membertype", "1"); // 회원유형 임시
		param.put("levelidx", 0); // 회원등급_일련번호 임시
		param.put("levelmanual", 0); // 회원등급_수동설정여부 임시
		param.put("memlvtype", CMConst.MEMLVTYPE_BRONZE); // 회원등급
		param.put("state", CMConst.CM_STATE_REAL); // 회원상태

		// 생일 replace 처리
		String birthdate = param.getStr("birthdate");
		if (birthdate != "") {
			param.put("birthdate", birthdate.replace("-", ""));
		}
		// 전화번호 replace 처리
		String mobile = param.getStr("mobile");
		if (mobile != "") {
			param.put("mobile", mobile.replace("-", ""));
		}
		/********************************
		 * 정보성 수신동의 T로 넣기로함(2022-05-17)
		 *********************************/
		param.put("isifpush", "T"); // 정보성정보수신여부(PUSH)
		param.put("isifmailing", "T"); // 정보성정보수신여부(이메일)
		param.put("isifsms", "T"); // 정보성정보수신여부(SMS)
		/********************************
		 * 광고성 수신동의 미선택시 F로 넣기로함(2022-05-17)
		 *********************************/
		if (param.getStr("isadpush") == "") {
			param.put("isadpush", "F");
		}
		if (param.getStr("isadsms") == "") {
			param.put("isadsms", "F");
		}
		if (param.getStr("isadmailing") == "") {
			param.put("isadmailing", "F");
		}
		// 내국인여부
		if (Util.isEmpty(param.getDbStr("isdomastic"))) {
			param.put("isdomastic", "T");
		}
		/******************************
		 * 회원등록(member)
		 *******************************/
		memberMapper.insertMember(param);
		/******************************
		 * 회원등록 이력(member)
		 *******************************/
		SOMap historyInfo = new SOMap();
		historyInfo.put("userno", param.get("userno"));
		historyInfo.put("mhistype", "MHT001");
		historyInfo.put("dadamembertype", param.getDbStr("dadamembertype"));
		historyInfo.put("memlvtype", param.getDbStr("memlvtype"));
		historyInfo.put("preval", null);
		historyInfo.put("aftval", param.getDbStr("dadamembertype"));
		historyInfo.put("reguserid", param.get("userid"));
		memberHistoryMapper.insertMemberHistory(historyInfo);
		historyInfo.put("mhistype", "MHT002");
		historyInfo.put("aftval", param.getDbStr("memlvtype"));
		memberHistoryMapper.insertMemberHistory(historyInfo);

		historyInfo.put("mhistype", "MHT003");
		historyInfo.put("aftval", param.get("isadmailing"));
		memberHistoryMapper.insertMemberHistory(historyInfo);

		historyInfo.put("mhistype", "MHT004");
		historyInfo.put("aftval", param.get("isadsms"));
		memberHistoryMapper.insertMemberHistory(historyInfo);

		historyInfo.put("mhistype", "MHT005");
		historyInfo.put("aftval", param.get("isadpush"));
		memberHistoryMapper.insertMemberHistory(historyInfo);
		/**********************************
		 * ERP 회원정보 수정
		 **********************************/
		SOMap erpData = new SOMap();
		erpData.put("siteid", cs.getStr("siteid"));
		erpData.put("userno", param.get("userno"));
		erpData.put("aud", "A");
		eRPService.insertMemberERPData(erpData);
		/******************************
		 * 회원sns등록(member)
		 *******************************/
		if (Util.flag2Bool(param.getDbStr("issns", "F"))) {
			memberSnsMapper.insertMemberSns(param);
		}
		/******************************
		 * 가입시 기본적립금 및 추천인 지급 처리
		 *******************************/
		List<String> content = new ArrayList<String>();
		if (param.getStr("recommendid") != "") {
			saveReward(param, content);
		}
		/******************************
		 * 가입시 회원가입 쿠폰 지급
		 *******************************/
		// 회원가입_평생회원제외
		param.put("islife", "F");
		param.put("muappchtype", cs.getStr("platform"));
		param.put("mumemlvtype", param.getStr("memlvtype"));
		param.put("mumembertype", param.getStr("dadamembertype"));
		List<SOMap> signupCoupon = couponMapper.selectSignupCoupon(param);

		param.put("comcpnno", prop.getProperty("life.member.comcpnno"));
		if (!(param.getStr("comcpnno").equals("") && param.getStr("comcpnno") == null)) {
			if (param.getDbStr("dadamembertype").equals("DMT002")) {
				param.put("islife", "T");
				List<SOMap> signupCoupon2 = couponMapper.selectSignupCoupon(param);
				signupCoupon.addAll(signupCoupon2);
			}
		}
	

		for (SOMap soMap : signupCoupon) {
			soMap.put("userno", param.getDbInt("userno")); // 회원번호
			soMap.put("issuemembertype", param.getDbStr("dadamembertype"));// 발급시회원유형
			soMap.put("issuememlvtype", param.getDbStr("memlvtype"));// 발급시회원등급
			soMap.put("isdownload", soMap.getDbStr("isautopay"));// 자동발급여부
			soMap.put("issuedate", DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR14));// 발급일자
			soMap.put("userid", param.getDbStr("userid"));
			couponMemissueMapper.insertCouponMemissue(soMap);

			// 평생회원 쿠폰 지급시 처리
			if (soMap.getStr("comcpnno").equals(prop.getProperty("life.member.comcpnno"))) {
				SOMap memSomap = new SOMap();
				memSomap.put("userno", param.getInt("userno"));
				memSomap.put("isfulllife", "T");
				memberMapper.updateMember(memSomap);
				/**********************************
				 * ERP 회원정보 수정
				 **********************************/
				erpData.put("siteid", cs.getStr("siteid"));
				erpData.put("userno", param.get("userno"));
				erpData.put("aud", "U");
				eRPService.insertMemberERPData(erpData);
			}

			if ("CIT002".equals(soMap.getDbStr("cpnissuetype"))) {
				// 신규가입쿠폰
				content.add("신규회원가입 쿠폰지급");
			}
		}

		// TO-DO. 자동메일발송 및 SMS발송
		SOMap messageParam = new SOMap();
		messageParam.put("number", param.get("mobile"));
		messageParam.put("email", param.get("email"));
		messageParam.put("username", param.get("name"));
		messageParam.put("userid", param.get("userid"));
		messageParam.put("userno", param.get("userno"));
		messageParam.put("regdate", DateTimeUtil.getNowFormatStr("yyyy년 MM월 dd일"));
		messageParam.put("content", String.join(" / ", content));
		cjMessageService.sendJoinMember(messageParam);
		/******************************
		 * SMS 처리
		 *******************************/
		// TO_DO
		/******************************
		 * 최종 리턴 param 값 셋팅
		 *******************************/
		param.remove("userpw");
		param.remove("userpw2");
		result.setData(param);
		return result;
	}

	/**
	 * 회원 휴먼처리
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void cancelSleepMember(SOMap param) throws Exception {
		if (Util.isEmpty(param.getDbStr("userno"))) {
			throw new BizException("회원번호가 없습니다.");
		}

		int cnt = memberSleepMapper.updateMemberSleepCancel(param);

		if (cnt == 0) {
			throw new BizException("휴면회원 취소처리중 에러가 발생하였습니다.");
		}

		memberSleepMapper.deleteMemberSleep(param);
	}

	/**
	 * 회원 탈퇴 처리
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void withdraw(SOMap param) throws Exception {
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인후 이용해 주세요.");
		}

		if (Util.isEmpty(param.getDbStr("withdrawreason"))) {
			throw new BizException("탈퇴사유는 필수항목입니다.");
		}

		SOMap memberInfo = memberMapper.selectMember(param);
		if (memberInfo == null) {
			throw new BizException("회원정보가 없습니다.");
		}
		if (!memberInfo.getStr("withdrawdate").equals("")) {
			throw new BizException("이미 탈퇴한 계정입니다.");
		}
		int ordercnt = comOrderMapper.selectUserOrderIngCnt(param);

		if (ordercnt > 0) {
			throw new BizException("진행중인 주문/교환/반품 내역이 있는 경우, 탈퇴가 불가합니다. 해당건이 종료된 후 탈퇴해주세요.");
		}

		// 회원탈퇴
		param.putDb("usernoarr", new Integer[] { param.getDbInt("userno") });
		param.putDb("withdrawtype", CMConst.CM_WITHDRAW_TYPE_MEMBER);
		memberSleepMapper.insertMemberToSleep(param);
		memberMapper.updateMemberWithdraw(param);
		// memberSnsMapper.deleteMemberSns(param);

		// 회원메모 등록
		param.putDb("memo", param.getDbStr("withdrawreason"));
		memberMemoMapper.insertMemberMemoArr(param);

		/**********************************
		 * ERP 회원탈퇴 등록
		 **********************************/
		SOMap erpData = new SOMap();
		erpData.put("siteid", cs.getStr("siteid"));
		erpData.put("userno", param.get("userno"));
		erpData.put("aud", "A");
		eRPService.insertResignMemberERPData(erpData);

		// throw new BizException("탈퇴완료");
	}

	/**
	 * 회원가입 필드체크
	 * 
	 * @param param
	 * @throws Exception
	 */
	private void validSignUp(SOMap param) throws Exception {
		if (Util.isEmpty(param.getDbStr("userid"))) {
			throw new BizException("아이디를 입력해 주세요.");
		}

		if (Util.isEmpty(param.getDbStr("userpw"))) {
			throw new BizException("비밀번호를 입력해 주세요.");
		}

		if (Util.isEmpty(param.getDbStr("userpw2"))) {
			throw new BizException("비밀번호확인을 입력해 주세요.");
		}

		if (Util.isEmpty(param.getDbStr("name"))) {
			throw new BizException("이름을 입력해 주세요.");
		}

		// 아이디 유효성 확인
		if (!Util.checkInvalidId(param.getDbStr("userid"), "6", "12")) {
			throw new BizException("아이디는 6~12자의 영문, 숫자만 사용 할 수 있습니다.");
		}

		// 아이디 중복확인
		if (this.checkUserId(param)) {
			throw new BizException("사용 불가능한 아이디입니다. 다른 아이디를 사용해 주세요.");
		}

		if (!Util.isPassword(param.getDbStr("userpw"))) {
			throw new BizException("영문/숫자/특수문자 3가지 이상 조합(최소 8자 이상) 또는 영문/숫자/특수문자 2가지 이상 조합(최소 10자 이상)으로 해주세요.");
		}

		if (!param.getDbStr("userpw").equals(param.getDbStr("userpw2"))) {
			throw new BizException("비밀번호와 비밀번호 확인이 일치하지 않습니다");
		}
	}

	/**
	 * sns 가입계정 아이디 생성
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private String getSnsUserId(String isSns) throws Exception {
		String userid = "";

		String randStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// ID 생성 Rule: (kakao:K, naver:N) + 년월 + 영문/숫자 4자리 난수
		/********************
		 * 1번째 문자 생성
		 *******************/
		if (Util.isEmpty(isSns)) {
			return "";
		}
		if (isSns == "K" || isSns == "N") {
			userid += isSns;
		} else {
			return "";
		}

		/********************
		 * 2번째 이메일 @이전 아이디 or 년월(YYMM) 생성
		 *******************/
		String year = DateTimeUtil.getNowFormatStr("yy");
		int index = 22 - Integer.parseInt(year);
		if (index < 0) {
			index = 0;
		}
		userid += randStr.substring(index, index + 1) + DateTimeUtil.getNowFormatStr("MMdd");

		/********************
		 * 나머지 SEQ 4자리 생성
		 ********************/
		userid += userMapper.selectUserIdSeq();

		return userid.toLowerCase();
	}

	/**
	 * 회원가입한 회원정보 조회
	 */
	@Override
	public SOMap selectMemberInfo(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		// 사용자 정보조회
		SOMap memberInfo = memberMapper.selectMember(param);
		result.put("membersinfo", memberInfo);
		param.put("userno", memberInfo.getInt("userno"));
		param.put("muappchtype", memberInfo.getStr("muappchtype"));
		param.put("mumemlvtype", memberInfo.getStr("mumemlvtype"));
		param.put("mumembertype", memberInfo.getStr("mumembertype"));

		// 회원가입쿠폰정보(지금 어드민 솔루션상 N개 지급이 가능함으로 LIST로 받음)
		List<SOMap> couponinfo = couponMemissueMapper.selectSignUpCoupon(param);
		result.put("couponinfo", couponinfo);

		// 추천인 가입 보상정보
		SOMap rewardinfo = recomRewardMapper.selectRewardPayInfo(param);
		result.put("rewardinfo", rewardinfo);
		
		SOMap rewardInfo = recomRewardLogMapper.selectSignupRewardLog(param);
		result.put("reward", rewardInfo);
		return result;
	}

	/******************
	 * sns 추천인 보상 지급
	 ******************/
	@Override
	public void saveRecommend(SOMap param) throws Exception {
		List<String> content = new ArrayList<String>();
		saveReward(param, content);
	}

	/***************************************
	 * sns 추천인 보상 지급
	 * 
	 * @param param
	 * @throws Exception
	 *****************************************/
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	private void saveReward(SOMap param, List<String> content) throws Exception {

		SOMap dbParam = new SOMap();
		String startDay = "";
		String endDay = "";
		param.put("siteid", cs.getStr("siteid"));
		dbParam.put("siteid", cs.getStr("siteid"));
		dbParam.put("userid", param.getStr("recommendid"));
		SOMap userInfo = memberMapper.selectMemberInfoByUserNo(param);
		if (userInfo == null) {
			throw new BizException("회원 정보를 가져오는데 실패했습니다.");
		}
		/*************************************
		 * 추천인 아이디 체크
		 *************************************/
		SOMap recomUserInfo = memberMapper.selectMemberInfoByUserNo(dbParam);
		if (recomUserInfo == null) {
			throw new BizException("추천인 아이디를 확인해주세요.");
		}

		SOMap rewardInfo = recomRewardLogMapper.selectSignupRewardLog(param);
		/*************************************
		 * 추천인 리워드가 없을경우 그냥 회원가입처리 => 추후 정책에 따라 수정
		 *************************************/
		if (rewardInfo == null) {
			return;
		}
		/*************************************
		 * 추천인 인원수 제한 체크
		 *************************************/
		if (recomUserInfo.getInt("recusercnt") > rewardInfo.getInt("reclimitcnt")) {
			throw new BizException("추천 인원수가 초과하였습니다.");
		}
		/*************************************
		 * 회원 추천인 변경
		 *************************************/
		SOMap userParam = new SOMap();
		userParam.put("userno", userInfo.getInt("userno"));
		userParam.put("recuserno", recomUserInfo.getInt("userno"));
		memberMapper.updateMember(userParam);
		/*************************************
		 * 추천인 리워드 지급
		 *************************************/
		if (rewardInfo != null) {
			/*********************
			 * 추천리워드 보상::적립금
			 *********************/
			if (rewardInfo.getDbStr("recomtype").equals("RCT001")) {// 적립금
				dbParam.clear();
				startDay = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
				endDay = DateTimeUtil.getPointEndDate(startDay);

				// 참고(피추천인:: 회원가입하는 사람, 회원가입::회원가입을 추천한 사람)
				// 피추천인 적립금 지급
				dbParam.put("userno", param.getDbInt("userno"));
				dbParam.put("isempreserve", "F"); // 임직원적립금여부
				dbParam.put("resstday", startDay); // 적립금유효시작일
				dbParam.put("resedday", endDay); // 적립금유효종료일
				dbParam.put("respaytype", "RPT009"); // 적립금지급구분
				dbParam.put("paymembertype", userInfo.getDbStr("dadamembertype")); // 지급시회원유형
				dbParam.put("paymemlvtype", userInfo.getDbStr("memlvtype")); // 지급시회원등급
				dbParam.put("paypoint", rewardInfo.getInt("revpoint"));
				dbParam.put("authuserid", userInfo.getStr("userid"));
				// reservePayMapper.insertReservePayAll(dbParam);
				commonservice.paymentReserve(dbParam);

				// 추천인 적립금 지급
				dbParam.put("userno", recomUserInfo.getStr("userno"));
				dbParam.put("respaytype", "RPT013"); // 적립금지급구분
				dbParam.put("paymembertype", recomUserInfo.getStr("dadamembertype"));
				dbParam.put("paymemlvtype", recomUserInfo.getStr("memlvtype"));
				dbParam.put("paypoint", rewardInfo.getInt("recjoinpoint"));
				dbParam.put("authuserid", recomUserInfo.getStr("userid"));
				// reservePayMapper.insertReservePayAll(dbParam);
				commonservice.paymentReserve(dbParam);

				content.add("추천리워드 적립금 " + rewardInfo.getInt("recjoinpoint") + "P");
			}
			/*********************
			 * 추천리워드 보상::epoint
			 *********************/
			if (rewardInfo.getDbStr("recomtype").equals("RCT002")) {
				startDay = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
				endDay = String.format("%s%s", DateTimeUtil.addDays(String.format("%s%s", startDay, "00"), 30,
						DateTimeUtil.MALL_DATE_FORMAT_DATE_SHORT), "2359");

				// 피추천인 epoint 지급
				dbParam.put("userno", param.getDbInt("userno"));
				dbParam.put("epostday", startDay);
				dbParam.put("epoedday", endDay);
				dbParam.put("epopaytype", "EPT002");
				dbParam.put("epopayreason", "피추천인가입보상");
				dbParam.put("paymembertype", userInfo.getDbStr("dadamembertype"));
				dbParam.put("paymemlvtype", userInfo.getDbStr("memlvtype"));
				dbParam.put("paypoint", rewardInfo.getInt("revpoint"));
				dbParam.put("authuserid", userInfo.getStr("userid"));
				// epointpaymapper.insertEpointPay(dbParam);
				commonservice.paymentEpotint(dbParam);

				// 추천인 epoint 지급
				dbParam.put("userno", recomUserInfo.getStr("userno"));
				dbParam.put("epopaytype", "EPT003");
				dbParam.put("paymembertype", recomUserInfo.getStr("dadamembertype"));
				dbParam.put("paymemlvtype", recomUserInfo.getStr("memlvtype"));
				dbParam.put("epopayreason", "추천인가입보상");
				dbParam.put("paypoint", rewardInfo.getInt("recjoinpoint"));
				dbParam.put("authuserid", recomUserInfo.getStr("userid"));
				// epointpaymapper.insertEpointPay(dbParam);
				commonservice.paymentEpotint(dbParam);

				content.add("추천리워드 E-POINT " + rewardInfo.getInt("recjoinpoint") + "P");
			}
			/*********************
			 * 추천리워드 보상::쿠폰
			 *********************/
			if (rewardInfo.getDbStr("recomtype").equals("RCT003")) {
				dbParam.clear();
				startDay = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);

				// 피추천인 쿠폰지급
				dbParam.put("siteid", cs.getStr("siteid"));
				dbParam.put("comcpnidx", rewardInfo.getStr("revcpidx"));
				SOMap couponInfo = couponMapper.selectCouponByComcpnidx(dbParam);

				dbParam.put("cpninfoidx", couponInfo.getInt("cpninfoidx")); // 쿠폰발급정보idx
				dbParam.put("userno", userInfo.getDbInt("userno")); // 회원번호
				dbParam.put("cpnusestday", couponInfo.getStr("cpnusestday"));// 쿠폰사용시작일
				dbParam.put("cpnuseedday", couponInfo.getStr("cpnuseedday"));// 쿠폰사용종료일
				dbParam.put("issuemembertype", userInfo.getDbStr("dadamembertype"));// 발급시회원유형
				dbParam.put("issuememlvtype", userInfo.getDbStr("memlvtype"));// 발급시회원등급
				dbParam.put("isdownload", 'F');// 다운로드여부
				dbParam.put("issuedate", startDay);
				dbParam.put("userid", userInfo.getStr("userid"));
				couponMemissueMapper.insertCouponMemissue(dbParam);

				// 추천인 쿠폰지급
				dbParam.put("comcpnidx", rewardInfo.getStr("recjoincpidx"));
				couponInfo = couponMapper.selectCouponByComcpnidx(dbParam);

				dbParam.put("cpninfoidx", couponInfo.getInt("cpninfoidx")); // 쿠폰발급정보idx
				dbParam.put("userno", recomUserInfo.getStr("userno"));
				dbParam.put("cpnusestday", couponInfo.getStr("cpnusestday"));// 쿠폰사용시작일
				dbParam.put("cpnuseedday", couponInfo.getStr("cpnuseedday"));// 쿠폰사용종료일
				dbParam.put("issuemembertype", recomUserInfo.getStr("dadamembertype"));// 발급시회원유형
				dbParam.put("issuememlvtype", recomUserInfo.getStr("memlvtype"));// 발급시회원등급
				dbParam.put("userid", recomUserInfo.getStr("userid"));
				couponMemissueMapper.insertCouponMemissue(dbParam);

				content.add("추천리워드 쿠폰지급");
			}
			SOMap memberParam = new SOMap();
			memberParam.put("revuserno", userInfo.getDbInt("userno"));// 피추천인
			memberParam.put("recuserno", recomUserInfo.getStr("userno"));// 추천인
			memberParam.put("relogidx", rewardInfo.getInt("relogidx"));
			memberRecomMapper.insertMemberRecom(memberParam);
		}
	}

	/**
	 * 카카오 이름 변경
	 */
	@Override
	public void saveKakaoName(SOMap param) throws Exception {
		if (param.getInt("userno") == 0) {
			throw new BizException("회원번호가 없습니다.");
		}
		if (param.getStr("name") == "") {
			throw new BizException("변경할 이름을 작성해주새요.");
		}
		memberMapper.updateMember(param);
	}

	@Override
	public SOMap selectMember(SOMap param) throws Exception {
		SOMap result = new SOMap();
		if (cs.getInt("authmemberno") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}
		param.put("userno", cs.getInt("authmemberno"));
		result.put("members", memberMapper.selectMember(param));
		return result;
	}

	/**
	 * 회원정보 확인페이지 비밀번호 확인
	 */
	@Override
	public void selectConfirmCheck(SOMap param) throws Exception {
		param.put("no", cs.getInt("authmemberno"));
		if (param.getInt("no") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}
		if (param.getStr("id") == "") {
			throw new BizException("아이디를 확인해주세요.");
		}
		if (param.getStr("pw") == "") {
			throw new BizException("비밀번호를 입력해주세요.");
		}

		SOMap pwMap = userMapper.selectUserAll(param);
		String dbpw = pwMap.getDbStr("userpw");
		String encpw = param.getStr("pw");
		encpw = CryptHash.hash(encpw);
		if (!dbpw.equals(encpw)) {
			throw new BizException("비밀번호가 일치하지 않습니다.");
		}
	}

	/**
	 * 회원정보 수정처리
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void saveInfoModify(SOMap param) throws Exception {
		int resultCnt = 0;
		param.put("userno", cs.getInt("authmemberno"));
		param.put("authuserid", cs.getStr("authmemberid"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}
		if (Util.isEmpty(param.getStr("mobile"))) {
			throw new BizException("휴대폰번호를 입력해주세요.");
		}
		if (Util.isEmpty(param.getStr("email"))) {
			throw new BizException("이메일을 입력해주세요.");
		}
//		if (Util.isEmpty(param.getStr("post")) || Util.isEmpty(param.getStr("addr"))) {
//			throw new BizException("주소를 입력해주세요.");
//		}
		/**********************************
		 * 비밀번호 변경이 있을경우 처리
		 **********************************/
		if (!Util.isEmpty(param.getStr("userpw")) && !Util.isEmpty(param.getStr("userpw2"))) {
			if (!Util.isPassword(param.getDbStr("userpw"))) {
				throw new BizException("영문/숫자/특수문자 3가지 이상 조합(최소 8자 이상) 또는 영문/숫자/특수문자 2가지 이상 조합(최소 10자 이상)으로 해주세요.");
			}
			if (!param.getStr("userpw").equals(param.getStr("userpw2"))) {
				throw new BizException("비밀번호가 일치하지 않습니다.");
			}
			/******************************
			 * 비밀번호 암호화 및 변경처리
			 ******************************/
			SOMap dbPram = new SOMap();
			String encPw = CryptHash.hash(param.getDbStr("userpw"));
			dbPram.put("userpw", encPw);
			dbPram.put("no", param.getInt("userno"));
			dbPram.put("authuserid", param.getStr("userid"));
			resultCnt = userMapper.updateUser(dbPram);
			if (resultCnt <= 0) {
				throw new BizException("회원정보 수정에 실패했습니다.");
			}
		}
		/**********************************
		 * 주소작성내용이 있으면 주소록에 해당 회원의 기본주소가 있는지 체크 배송지가 없으면 추가
		 **********************************/
		if (!Util.isEmpty(param.getStr("post"))) {
			int adrCnt = memberAddressMapper.selectAddressCnt(param);
			if (adrCnt == 0) {
				SOMap adrParam = new SOMap();
				adrParam.putAll(param);
				adrParam.put("title", param.get("name"));
				adrParam.put("isdefault", "T");
				resultCnt = memberAddressMapper.insertAddress(adrParam);
				if (resultCnt <= 0) {
					throw new BizException("회원정보 수정에 실패했습니다.");
				}
			}
		}
		/**********************************
		 * [일반회원, 평생회원]-평생회원 처리 전환처리
		 **********************************/
		if (param.getStr("dadamembertype").equals("DMT001") || param.getStr("dadamembertype").equals("DMT002")) {
			if (param.getStr("islife").equals("T")) {
				param.put("dadamembertype", "DMT002");
				int lifetimeCnt = memberMapper.selectLifetimeCnt(param);
				// 평생회원 보상 지급을 받은적이 없을시 처리
				if (lifetimeCnt > 0) {
					param.put("comcpnno", prop.getProperty("life.member.comcpnno"));
					List<SOMap> lifeCouponList = couponMapper.selectLifeCoupon(param);
					if (lifeCouponList.size() > 0) {
						SOMap soMap = new SOMap();
						soMap.put("userno", param.getInt("userno")); // 회원번호
						soMap.put("issuemembertype", cs.getStr("authmembertype"));// 발급시회원유형
						soMap.put("issuememlvtype", cs.getStr("authmemlvtype"));// 발급시회원등급
						soMap.put("cpninfoidx", lifeCouponList.get(0).get("cpninfoidx")); // 쿠폰발급정보idx
						soMap.put("cpnusestday", lifeCouponList.get(0).get("cpnusestday"));
						soMap.put("cpnuseedday", lifeCouponList.get(0).get("cpnuseedday"));
						soMap.put("isdownload", "T");// 자동발급여부
						soMap.put("issuedate", DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR14));// 발급일자
						soMap.put("userid", param.getStr("authuserid"));
						int cnt = couponMemissueMapper.insertCouponMemissue(soMap);
						if (cnt > 0) {
							param.put("isfulllife", "T");
						}

					}
				}
			} else {
				param.put("dadamembertype", "DMT001");
			}
		}

		/**********************************
		 * 회원 변경이력 처리
		 **********************************/
		saveMemberHistory(param);
		/**********************************
		 * 회원정보 수정처리
		 **********************************/
		resultCnt = memberMapper.updateMember(param);
		if (resultCnt <= 0) {
			throw new BizException("회원정보 수정에 실패했습니다.");
		}
		/**********************************
		 * ERP 회원정보 수정
		 **********************************/
		SOMap erpData = new SOMap();
		erpData.put("siteid", cs.getStr("siteid"));
		erpData.put("userno", param.get("userno"));
		erpData.put("aud", "U");
		eRPService.insertMemberERPData(erpData);
	}

	/**
	 * SNS 연결 해제 처리
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap snsDisconnect(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		int resultCnt = 0;
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}
		if (param.getStr("snstype") == "") {
			throw new BizException("SNS 타입정보가 없습니다.");
		}
		resultCnt = memberSnsMapper.deleteMemberSns(param);
		if (resultCnt <= 0) {
			throw new Exception("SNS 연결 해지에 실패하였습니다.");
		}

		return resultMap;
	}

	/**
	 * 회원 이력 처리
	 * 
	 * @param param
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	private void saveMemberHistory(SOMap param) throws Exception {
		SOMap memberInfo = memberMapper.selectMember(param);

		SOMap historyInfo = new SOMap();

		if ((!Util.isEmpty(param.getStr("dadamembertype")))
				&& !memberInfo.getStr("dadamembertype").equals(param.getStr("dadamembertype"))) {
			historyInfo.put("userno", memberInfo.get("userno"));
			historyInfo.put("mhistype", "MHT001");
			historyInfo.put("dadamembertype", memberInfo.get("dadamembertype"));
			historyInfo.put("memlvtype", memberInfo.get("memlvtype"));
			historyInfo.put("preval", memberInfo.get("dadamembertype"));
			historyInfo.put("aftval", param.get("dadamembertype"));
			historyInfo.put("reguserid", memberInfo.get("userid"));

			memberHistoryMapper.insertMemberHistory(historyInfo);
		}

		if ((!Util.isEmpty(param.getStr("memlvtype")))
				&& !memberInfo.getStr("memlvtype").equals(param.getStr("memlvtype"))) {
			historyInfo.put("userno", memberInfo.get("userno"));
			historyInfo.put("mhistype", "MHT002");
			historyInfo.put("dadamembertype", memberInfo.get("dadamembertype"));
			historyInfo.put("memlvtype", memberInfo.get("memlvtype"));
			historyInfo.put("preval", memberInfo.get("memlvtype"));
			historyInfo.put("aftval", param.get("memlvtype"));
			historyInfo.put("reguserid", memberInfo.get("userid"));

			memberHistoryMapper.insertMemberHistory(historyInfo);
		}

		if ((!Util.isEmpty(param.getStr("isadmailing")))
				&& !memberInfo.getStr("isadmailing").equals(param.getStr("isadmailing"))) {
			historyInfo.put("userno", memberInfo.get("userno"));
			historyInfo.put("mhistype", "MHT003");
			historyInfo.put("dadamembertype", memberInfo.get("dadamembertype"));
			historyInfo.put("memlvtype", memberInfo.get("memlvtype"));
			historyInfo.put("preval", memberInfo.get("isadmailing"));
			historyInfo.put("aftval", param.get("isadmailing"));
			historyInfo.put("reguserid", memberInfo.get("userid"));

			memberHistoryMapper.insertMemberHistory(historyInfo);
		}

		if ((!Util.isEmpty(param.getStr("isadsms"))) && !memberInfo.getStr("isadsms").equals(param.getStr("isadsms"))) {
			historyInfo.put("userno", memberInfo.get("userno"));
			historyInfo.put("mhistype", "MHT004");
			historyInfo.put("dadamembertype", memberInfo.get("dadamembertype"));
			historyInfo.put("memlvtype", memberInfo.get("memlvtype"));
			historyInfo.put("preval", memberInfo.get("isadsms"));
			historyInfo.put("aftval", param.get("isadsms"));
			historyInfo.put("reguserid", memberInfo.get("userid"));

			memberHistoryMapper.insertMemberHistory(historyInfo);
		}

		if ((!Util.isEmpty(param.getStr("isadpush")))
				&& !memberInfo.getStr("isadpush").equals(param.getStr("isadpush"))) {
			historyInfo.put("userno", memberInfo.get("userno"));
			historyInfo.put("mhistype", "MHT005");
			historyInfo.put("dadamembertype", memberInfo.get("dadamembertype"));
			historyInfo.put("memlvtype", memberInfo.get("memlvtype"));
			historyInfo.put("preval", memberInfo.get("isadpush"));
			historyInfo.put("aftval", param.get("isadpush"));
			historyInfo.put("reguserid", memberInfo.get("userid"));

			memberHistoryMapper.insertMemberHistory(historyInfo);
		}
	}

	/**
	 * 회원 광고성수신동의
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	@Override
	public void saveIsAdPush(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}

		this.saveMemberHistory(param);

		memberMapper.updateMember(param);
	}

	/**
	 * 바이오Token조회
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public SOMap getEncBio(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}
		SOMap member = memberMapper.selectMember(param);

		SOMap result = new SOMap();
		String encBio = CryptHash.hash(member.getDbStr("userno") + "_" + member.getDbStr("userid"));
		result.put("encbio", encBio);

		return result;
	}

	/**
	 * 바이오Token저장
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveEncBio(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}

		memberMapper.updateMemberEncBio(param);
	}

	/**
	 * 회원정보 조회
	 */
	@Override
	public SOMap memberEmailPhone(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));

		SOMap memberEmailPhone = memberMapper.memberEmailPhone(param);

		resultMap.put("memberemail", memberEmailPhone);
		return resultMap;
	}

	/**
	 * 회원정보 조회
	 */
	@Override
	public SOMap getMemberInfo(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		param.put("siteid", cs.getStr("siteid"));

		SOMap memberinfo = memberMapper.selectMember(param);
		SOMap configinfo = configMapper.selectConfigAppVersion(param);
		SOMap members = new SOMap();
		if (memberinfo == null) {
			members.put("isbio", false);
			members.put("islogin", false);
			members.put("isalarm", false);
			members.put("name", null);
			members.put("userid", null);
		} else {
			members.put("isbio", memberinfo.getStr("isbio").equals("T") ? true : false);
			members.put("islogin", true);
			members.put("isalarm", memberinfo.getStr("isadpush").equals("T") ? true : false);
			members.put("name", memberinfo.getStr("name"));
			members.put("userid", memberinfo.getStr("userid"));
		}
		if (configinfo == null) {
			members.put("appverion", "");
		} else {
			members.put("appverion", configinfo.getStr("appversion"));
		}

		result.put("members", members);

		return result;
	}
}
