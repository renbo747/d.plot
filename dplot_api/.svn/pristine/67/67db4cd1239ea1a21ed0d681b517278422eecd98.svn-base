package com.dplot.front.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.ConfigTermHistoryMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.RecomRewardLogMapper;
import com.dplot.mapper.SubscribeMapper;


@Service
public class EtcServiceImpl extends MallBaseService implements EtcService {

	@Autowired
	private SubscribeMapper subscribeMapper;

	@Autowired
	private CJMessageService cJMessageService;
	
	
	@Autowired
	private ConfigTermHistoryMapper configTermHistoryMapper;
	
	@Autowired
	private DealerMapper dealerMapper;
	
	@Autowired
	private RecomRewardLogMapper recomRewardLogMapper;

	/**
	 * 구독 신청하기
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap sendSubscribe(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("siteid", cs.getStr("siteid"));

		if (cs.getStr("authmemberid") != "") {
			param.put("subuserid", cs.getStr("authmemberid"));
			param.put("moduserid", cs.getStr("authmemberid"));
		}

		int resultCnt = 0;
//		if (param.getStr("subuserid") == "") {
//			throw new BizException("로그인이 필요한 서비스입니다.");
//		}
		if (param.getStr("email") == "") {
			throw new BizException("이메일을 입력해주세요.");
		}
		SOMap subInfo = subscribeMapper.selectSubscribe(param);
		if (subInfo != null) {
			if (subInfo.getStr("iscancel").equals("T")) {
				Date today = new Date();
				param.put("subregdate", today);
				param.put("subidx", subInfo.get("subidx"));
				param.put("iscancel", "F");
				resultCnt = subscribeMapper.updateSubscribe(param);
			} else {
				throw new BizException("이미 구독된 회원입니다.");
			}
		} else {
			resultCnt = subscribeMapper.insertSubcribe(param);
		}
		SOMap messageParam = new SOMap();
		messageParam.put("email", param.getStr("email"));
		String email = param.getStr("email");
		messageParam.put("url", "/etc/news-letter?email="+param.getStr("email"));
		
		//messageParam.put("encemail", encemail);
		cJMessageService.sendNewsletterApply(messageParam);

		if (resultCnt <= 0) {
			throw new BizException("구독신청에 실패했습니다.");
		}
		return resultMap;
	}

	/**
	 * 구독취소처리
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap subscribeCancel(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		int resultCnt = 0;
		param.put("siteid", cs.getStr("siteid"));
		param.put("subuserid", cs.getStr("authmemberid"));
		param.put("moduserid", cs.getStr("authmemberid"));

//		if (param.getStr("subuserid") == "") {
//			throw new BizException("로그인이 필요한 서비스입니다.");
//		}
//		
		SOMap subInfo = subscribeMapper.selectSubscribe(param);
		if (subInfo != null) {
			if (subInfo.getStr("iscancel").equals("T")) {
				throw new BizException("이미 구독이 취소된 이메일 주소입니다.");
			}
		} else {
			throw new BizException("미구독한 이메일입니다.");
		}
		param.put("iscancel", "T");
		param.put("subidx", subInfo.get("subidx"));
		resultCnt = subscribeMapper.updateSubscribe(param);
		if (resultCnt <= 0) {
			throw new BizException("구독취소에 실패했습니다.");
		}

		return resultMap;
	}

	
	/**
	 * 이용약관 이력 조회
	 */
	@Override
	public SOMap useTerms(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		if (param.getStr("termstype").equals("")) {
			param.put("termstype", "TRT013");
		}
		List<SOMap> termsList = configTermHistoryMapper.selectTermsHistoryList(param);
		result.put("termslist", termsList);
		return result;
	}

	/**
	 * 판매업체 목록 조회
	 */
	@Override
	public SOMap dealerList(SOMap param) throws Exception {
		SOMap result = new SOMap();
		List<SOMap> saleList = dealerMapper.selectDealerList(param);
		result.put("salelist", saleList);
		return result;
	}

	/**
	 * 친구초대 보상정보 조회
	 */
	@Override
	public SOMap selectRewardInfo(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		SOMap rewardInfo = recomRewardLogMapper.selectSignupRewardLog(param);
		resultMap.put("rewardinfo", rewardInfo);
		return resultMap;
	}

}
