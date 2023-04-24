package com.dplot.common.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.TossService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.ExIfLogMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

@Service
public class TossServiceImpl extends MallBaseService implements TossService {
	
	private static final Logger logger = LoggerFactory.getLogger(TossServiceImpl.class);
	
	@Autowired
	private ExIfLogMapper exIfLogMapper;
	
	@Resource(name="propertiesFactory")
    private Properties prop;
	
	@Override
	public SOMap tossPayment(SOMap param) throws Exception {
		String reqUrl = "https://api.tosspayments.com/v1/payments/" + param.getDbStr("paymentkey");
		
		SOMap result = new SOMap();
		String secretKey = prop.getProperty("toss.secret.key") + ":";
		
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(reqUrl); //POST 메소드 URL 새성 
		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(secretKey.getBytes()));
		
		ObjectMapper mapper = new ObjectMapper();

		JSONObject json = new JSONObject();
		json.put("amount", param.getDbInt("amount"));
		json.put("orderId", param.getDbStr("orderid"));
		
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8")); //json 메시지 입력 

		HttpResponse response = client.execute(postRequest);

		//Response 출력
		/**
		 * 중요 JSON -> SOMap : 1Depth는 소문자 키, 2Depth 객체는 대문자 키
		 */
		ResponseHandler<String> handler = new BasicResponseHandler();
		String body = handler.handleResponse(response);
		
		this.insertLog(body, param.getDbStr("orderid"));
		
		logger.debug("Toss Response : " + body);
		if (response.getStatusLine().getStatusCode() == 200) {
			SOMap payInfo = (SOMap)mapper.readValue(body,  SOMap.class);
			
			result.put("paymentkey", payInfo.getStr("paymentkey"));
			result.put("paymentdate", DateTimeUtil.convertIOS8601(payInfo.getStr("approvedat"), DateTimeUtil.MALL_DATE_FORMAT_VARCHAR14));
	        result.put("totalpayamount", payInfo.getInt("totalamount"));
			
			if(payInfo.getSOMap("card") != null) {
				//카드결제
				SOMap card = payInfo.getSOMap("card");
				
				result.put("paywaytype", CMConst.PAYWAY_TYPE_CARD);
				result.put("cardcompany", card.getStr("company"));
				result.put("cardnumber", card.getStr("number"));
				result.put("planmonth", card.getInt("installmentPlanMonths"));
				result.put("isinterfree", Util.bool2Flag(card.getStr("isInterestFree")));
				result.put("appoveno", card.getStr("approveNo"));
				result.put("isusecardpoint", Util.bool2Flag(card.getStr("useCardPoint")));
				result.put("cardtype", card.getStr("cardType"));
				result.put("ownertype", card.getStr("ownerType"));
				
			} else if(payInfo.getSOMap("virtualaccount") != null) {
				//무통장(가상계좌)
				SOMap viraccount = payInfo.getSOMap("virtualaccount");
				
				result.put("paywaytype", CMConst.PAYWAY_TYPE_VIRACCOUNT);
				result.put("accounttype", viraccount.getStr("accountType"));
				result.put("accntnumber", viraccount.getStr("accountNumber"));
				result.put("virbank", viraccount.getStr("bank"));
				result.put("vircusname", viraccount.getStr("customerName"));
				result.put("virdueday", DateTimeUtil.convertIOS8601(viraccount.getStr("dueDate"), DateTimeUtil.MALL_DATE_FORMAT_VARCHAR14));
				result.put("virrefundst", viraccount.getStr("refundStatus"));
				result.put("isvirexpire",  Util.bool2Flag(viraccount.getStr("expired")));
				result.put("virsecret", payInfo.getStr("secret"));

			} else if(payInfo.getSOMap("transfer") != null) {
				//계좌이체
				SOMap transfer = payInfo.getSOMap("transfer");
				
				result.put("paywaytype", CMConst.PAYWAY_TYPE_ACCOUNTTRANS);
				result.put("trsbank", transfer.getStr("bank"));
				
			} else if(payInfo.getSOMap("mobilephone") != null) {
				//모바일결제
				SOMap mobilephone = payInfo.getSOMap("mobilephone");
				
				result.put("paywaytype", CMConst.PAYWAY_TYPE_MOBILE);
				result.put("mopcarrier", mobilephone.getStr("carrier"));
				result.put("mopmobileno", mobilephone.getStr("customerMobilePhone"));
			}
			
			//간편결제
			if(payInfo.getSOMap("easypay") != null) {
				SOMap easypay = payInfo.getSOMap("easypay");
				if("토스페이".equals(easypay.getStr("provider")) || "TOSSPAY".equals(easypay.getStr("provider"))) {
					result.put("paywaytype", CMConst.PAYWAY_TYPE_TOSSPAY);
				} else if("페이코".equals(easypay.getStr("provider")) || "PAYCO".equals(easypay.getStr("provider"))){
					result.put("paywaytype", CMConst.PAYWAY_TYPE_PAYCO);
				} else if("카카오페이".equals(easypay.getStr("provider")) || "KAKAOPAY".equals(easypay.getStr("provider"))) {
					result.put("paywaytype", CMConst.PAYWAY_TYPE_KAKAO);
				}
				
				result.put("cardcompany", easypay.getStr("provider"));
			}
			
			//영수증URL
			if(payInfo.getSOMap("receipt") != null) {
				SOMap receipt = payInfo.getSOMap("receipt");
				result.put("receipturl", receipt.getStr("url"));
			}
			 
			//현금영수증
			if(payInfo.getSOMap("cashreceipt") != null) {
				SOMap cashreceipt = payInfo.getSOMap("cashreceipt");
				result.put("cashreceipttype", cashreceipt.getStr("type"));
				result.put("cashreceiptamount", cashreceipt.getStr("amount"));
				result.put("issuenumber", cashreceipt.getStr("issueNumber "));
				result.put("cashreceiptrul", cashreceipt.getStr("receiptUrl "));
			}

		} else if(response.getStatusLine().getStatusCode() == 401){
			logger.error("결제처리중 인증정보가 유효하지 않습니다.[" + body + "]");
			throw new BizException("결제처리중 인증정보가 유효하지 않습니다.");
		} else if(response.getStatusLine().getStatusCode() == 404){
			logger.error("결제정보가 존재하지 않습니다. 다시시도해 주세요.[" + body + "]");
			throw new BizException("결제정보가 존재하지 않습니다. 다시시도해 주세요.");
		} else {
			logger.error("결제처리중 에러가 발생하였습니다.[" + body + "]");
			throw new BizException("결제처리중 에러가 발생하였습니다.");
		} 
	
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SOMap tossPaymentCancel(SOMap param) throws Exception {
		String reqUrl = "https://api.tosspayments.com/v1/payments/" + param.getDbStr("paymentkey") + "/cancel";
		
		SOMap result = new SOMap();
		String secretKey = prop.getProperty("toss.secret.key") + ":";
		
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(reqUrl); //POST 메소드 URL 새성 
		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(secretKey.getBytes()));
		
		ObjectMapper mapper = new ObjectMapper();

		JSONObject json = new JSONObject();
		json.put("cancelAmount", param.getDbInt("amount"));
		json.put("cancelReason", param.getDbStr("reason"));
		if(!Util.isEmpty(param.getDbStr("bank")) && !Util.isEmpty(param.getDbStr("holdername")) && !Util.isEmpty(param.getDbStr("accountnumber"))){
			JSONObject refund = new JSONObject();
			refund.put("bank", param.getDbStr("bank"));
			refund.put("holderName", param.getDbStr("holdername"));
			refund.put("accountNumber", param.getDbStr("accountnumber"));
			json.put("refundReceiveAccount", refund);
		}
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8")); //json 메시지 입력 

		HttpResponse response = client.execute(postRequest);

		//Response 출력
		ResponseHandler<String> handler = new BasicResponseHandler();
		String body = handler.handleResponse(response);
		
		this.insertLog(body, param.getDbStr("clmno"));
		
		logger.debug("Toss Response : " + body);
		if (response.getStatusLine().getStatusCode() == 200) {
			SOMap payInfo = (SOMap)mapper.readValue(body,  SOMap.class);
			
			result.putAll((Map<String, Object>)payInfo.getArrayList("cancels").get(0));
			
		} else if(response.getStatusLine().getStatusCode() == 401){
			logger.error("결제취소중 인증정보가 유효하지 않습니다.[" + body + "]");
			throw new BizException("결제취소중 인증정보가 유효하지 않습니다.");
		} else if(response.getStatusLine().getStatusCode() == 404){
			logger.error("결제정보가 존재하지 않습니다. 다시시도해 주세요.[" + body + "]");
			throw new BizException("결제정보가 존재하지 않습니다. 다시시도해 주세요.");
		} else {
			logger.error("결제취소중 에러가 발생하였습니다.[" + body + "]");
			throw new BizException("결제취소중 에러가 발생하였습니다.");
		} 
	
		return result;
	}
	
	/**
	 * 토스 거래조회
	 * startdate : yyyyMMddHHmmss
	 * enddate : yyyyMMddHHmmss
	 */
	@Override
	public List<SOMap> tossPaymentList(SOMap param) throws Exception {
		String startdate = DateTimeUtil.getNowDatePartShortStr() + "000000";
		String enddate = DateTimeUtil.getNowDatePartShortStr() + "235959";
		
		if(!Util.isEmpty(param.getDbStr("startdate"))) {
			startdate = param.getDbStr("startdate");
		}
		if(!Util.isEmpty(param.getDbStr("enddate"))) {
			enddate = param.getDbStr("enddate");
		}
		
		startdate = DateTimeUtil.convertDateStrToIOS8601(startdate);
		enddate = DateTimeUtil.convertDateStrToIOS8601(enddate);
		
		String reqUrl = String.format("%s?startDate=%s&endDate=%s&limit=%d","https://api.tosspayments.com/v1/transactions", startdate, enddate, 10000);
		
		List<SOMap> result = new ArrayList<SOMap>();
		String secretKey = prop.getProperty("toss.secret.key") + ":";
		
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpGet getRequest = new HttpGet(reqUrl); //GET 메소드 URL 새성 
		getRequest.setHeader("Content-Type", "application/json");
		getRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(secretKey.getBytes()));
		
		ObjectMapper mapper = new ObjectMapper();

		
		HttpResponse response = client.execute(getRequest);
		ResponseHandler<String> handler = new BasicResponseHandler();
		String body = handler.handleResponse(response);
		logger.debug("Toss Response : " + body);
		if (response.getStatusLine().getStatusCode() == 200) {
			result = mapper.readValue(body,  new TypeReference<List<SOMap>>(){});
		} else {
			logger.error("토스 거래조회중 에러가 발생하였습니다.[" + body + "]");
			throw new BizException("토스 거래조회중 에러가 발생하였습니다.");
		} 
		
		return result;
	}
	
	/**
	 * 토스 거래조회
	 * startdate : yyyyMMddHHmmss
	 * enddate : yyyyMMddHHmmss
	 */
	@Override
	public SOMap tossCardPromotion(SOMap param) throws Exception {
		String reqUrl = "https://api.tosspayments.com/v1/promotions/card";
		
		SOMap result = new SOMap();
		String secretKey = prop.getProperty("toss.secret.key") + ":";
		
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpGet getRequest = new HttpGet(reqUrl); //GET 메소드 URL 새성 
		getRequest.setHeader("Content-Type", "application/json");
		getRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(secretKey.getBytes()));
		
		ObjectMapper mapper = new ObjectMapper();

		HttpResponse response = client.execute(getRequest);
		ResponseHandler<String> handler = new BasicResponseHandler();
		String body = handler.handleResponse(response);
		logger.debug("Toss Response : " + body);
		if (response.getStatusLine().getStatusCode() == 200) {
			result = mapper.readValue(body,  new TypeReference<SOMap>(){});
		} else {
			logger.error("Toss 카드정보 조회중 에러가 발생하셨습니다.[" + body + "]");
			logger.error("Toss 카드정보 조회중 에러가 발생하셨습니다.");
		} 
		
		return result;
	}
	
	@Override
	/**
	 * 현금영수증 발급
	 * amount : 발급 금액
	 * orderid : 주문번호
	 * ordername : 주문명(상품명)
	 * registrationnumber : 개인 식별 번호
	 * cashreceipttype : 현금영수증의 종류. 소득공제 / 지출증빙
	 */
	public SOMap tossCashReceipts(SOMap param) throws Exception {
		String reqUrl = "https://api.tosspayments.com/v1/cash-receipts";
		
		SOMap result = new SOMap();
		String secretKey = prop.getProperty("toss.secret.key") + ":";
		
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(new URI(reqUrl)); //POST 메소드 URL 새성 
		postRequest.setHeader("content-Type", "application/json");
		postRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(secretKey.getBytes()));
		
		ObjectMapper mapper = new ObjectMapper();

		JSONObject json = new JSONObject();
		json.put("amount", param.getDbInt("amount"));
		json.put("orderId", param.getDbStr("orderid"));
		json.put("orderName", param.getDbStr("ordername"));
		json.put("registrationNumber", param.getDbStr("registrationnumber").trim());
		json.put("type", param.getDbStr("cashreceipttype").trim());
		
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8")); //json 메시지 입력 

		HttpResponse response = client.execute(postRequest);
		
		//Response 출력
		/**
		 * 중요 JSON -> SOMap : 1Depth는 소문자 키, 2Depth 객체는 대문자 키
		 */
		ResponseHandler<String> handler = new BasicResponseHandler();
		String body = handler.handleResponse(response);
		
		this.insertLog(body, param.getDbStr("orderid"));
		
		logger.debug("Toss Response : " + body);
		if (response.getStatusLine().getStatusCode() == 200) {
			result = (SOMap)mapper.readValue(body,  SOMap.class);
		} else {
			throw new BizException("Toss현금영수증 발급중 에러가 발생하였습니다.[" + body +"]");
		}
		return result;
	}

	@Override
	/**
	 * 현금영수증 발급취소
	 * cashreceiptkey : 발급키
	 * amount : 취소금액
	 */
	public SOMap tossCashReceiptsCancel(SOMap param) throws Exception {
		String reqUrl = "https://api.tosspayments.com/v1/cash-receipts/" + param.getDbStr("cashreceiptkey") + "/cancel";
		
		SOMap result = new SOMap();
		String secretKey = prop.getProperty("toss.secret.key") + ":";
		
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
		HttpPost postRequest = new HttpPost(new URI(reqUrl)); //POST 메소드 URL 새성 
		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(secretKey.getBytes()));
		
		ObjectMapper mapper = new ObjectMapper();

		JSONObject json = new JSONObject();
		json.put("amount", param.getDbInt("amount"));
		
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8")); //json 메시지 입력 

		HttpResponse response = client.execute(postRequest);
		ResponseHandler<String> handler = new BasicResponseHandler();
		String body = handler.handleResponse(response);
		
		this.insertLog(body, param.getDbStr("clmno"));
		
		logger.debug("Toss Response : " + body);
		//Response 출력
		if (response.getStatusLine().getStatusCode() == 200) {
			result = (SOMap)mapper.readValue(body,  SOMap.class);
			
		} else {
			throw new BizException("현금영수증 발급취소중 에러가 발생하였습니다.");
		}
		return result;
	}
	
	/**
	 * Toss결제 로그이력
	 * @param log
	 * @param orgno
	 */
	private void insertLog(String log, String orgno) {
		SOMap param = new SOMap();
		param.put("exlogtype", "EXL002");
		param.put("iflog", log);
		param.put("orgno", orgno);
		try {
			exIfLogMapper.insertExIfLog(param);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
