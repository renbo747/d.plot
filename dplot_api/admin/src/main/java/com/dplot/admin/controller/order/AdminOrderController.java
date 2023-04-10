package com.dplot.admin.controller.order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.order.AdminOrderService;
import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;
import com.dplot.common.service.CommonOrderService;
import com.dplot.common.service.CommonService;
import com.dplot.util.Util;

/**
 * @discription	: 주문관리 Controller
 * @fileName	: AdminOrderController.java
 * @author		: JSK
 * @date		: 2022.02.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.02.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = {"/admin/order/manage", "/partners/order/manage"})
public class AdminOrderController extends ExcelDownController{

    @Autowired
    AdminOrderService adminOrderService;
    @Autowired
    CommonOrderService commonOrderService;
    @Autowired
    CommonService commonService;

    /**
     * 전체 주문, 클레임 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/listall", method = RequestMethod.POST)
    public Response orderClaimList(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectOrderClaimList(params));
    }

    /**
     * 전체 주문, 클레임 목록 조회(엑셀용)
     * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/all/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> orderClaimListForExcel(@RequestBody SOMap params) throws Exception {
    	// 엑셀출력 내용 조회
    	LocalDate now = LocalDate.now();         // 포맷 정의
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    	String today = now.format(formatter);

        if (params.get("ispartner") != null && Util.equal(params.getStr("ispartner"), "true")) {
        	List<Map<String, Object>> list = adminOrderService.selectOrderClaimListForExcel(params);
        	String[] header = {"주문일", "결제일", "주문번호", "상품명", "실결제금액", "결제수단", "미배송", "배송중", "배송완료", "구매확정", "취소", "반품", "교환"};
			String[] column = {"orderday", "paymentday", "ordno", "goodsname", "rpaytotprice", "paywaytypename", "undelivcnt", "indelivcnt", "compdelivcnt", "cfmcnt", "canclecnt", "returncnt", "exchangecnt"};
			int[] columnSize = {20*256, 20*256, 20*256, 30*256, 15*256, 15*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256};
			return makeExcelFile(header, column, columnSize, list, "전체주문목록.xlsx");
        } else {
        	List<Map<String, Object>> list = adminOrderService.selectOrderClaimListForExcelNew(params);
        	String[] header = {"주문경로", "주문일", "결제일", "주문번호", "첫구매", "주문자명", "주문자ID", "주문자연락처", "회원구분", "회원유형",
					"회원등급", "판매구분", "파트너사명", "상품코드", "단품코드", "상품순번", "상품명",
    				"옵션명", "주문수량", "판매단가", "판매금액", "대카테고리", "상품수수료율(%)", "수수료", "가격프로모션 자사부담", "가격프로모션 파트너부담",
    				"상품쿠폰 자사부담", "상품쿠폰 파트너부담", "장바구니쿠폰", "임직원 적립금", "일반 적립금", "PG결제금액", "배송비", "배송비쿠폰",
    				"주문상태", "결제수단", "추가상품여부", "배송유형", "배송조건", "수령인명", "수령인연락처", "수령인주소", "배송수량", "택배사", "송장번호", "구매확정"};
    		String[] column = {"ordpathtypename", "orderday", "paymentday", "ordno", "isfrstorder", "ordname", "ordid", "ordtel", "isnonmembername", "membertypename"
					, "memlvtypename", "ispbgoodsname", "dealername", "goodscode", "optioncode", "goodsturn",
					"goodsname",
    				"fulloptionnm", "ordcnt", "price", "saleamt", "depth1name", "commrate", "commamt", "proratioamt", "partproratioamt",
    				"cpnratioamt", "partcpnrratioamt", "basketdivamt", "empreserveamt", "reserveamt", "realgoodsamt", "delivamt", "delivcpnamt",
    				"ordstatusname", "paywaytypename", "isaddgoods", "iscombdelivname", "delivfaretext", "rcvname", "rcvtel1", "rcvaddr", "delivcnt", "logistypename", "invoiceno", "cfmconts"};
    		int[] columnSize = {10*256, 20*256, 20*256, 20*256, 10*256, 15*256, 15*256, 15*256, 10*256, 10*256
    				, 10*256, 30*256, 15*256, 15*256, 10*256, 10*256, 10*256
    				, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256
    				, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256
    				, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 20*256, 10*256, 10*256, 10*256
    				};
    		return makeExcelFile(header, column, columnSize, list, "전체주문목록(상세)_"+today+".xlsx");
        }
    }

    /**
     * 상태별 주문 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response orderList(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectOrderList(params));
    }

    /**
     * 주문 목록 조회(엑셀용)
     * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> orderListForExcel(@RequestBody SOMap params) throws Exception {
    	// 엑셀출력 내용 조회
    	List<Map<String, Object>> list = adminOrderService.selectOrderListForExcel(params);


        if (params.get("ispartner") != null && Util.equal(params.getStr("ispartner"), "true")) {
        	// 입금대기, 상품준비중
        	if (params.getStr("ordstatusarr").indexOf(CMConst.ORDER_WAITING_DEPOSIT) > -1
        			|| params.getStr("ordstatusarr").indexOf(CMConst.ORDER_PREPARING_GOODS) > -1) {
        		String[] header = {"주문일", "주문번호", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
                			     , "판매금액", "결제수단", "배송유형", "배송조건", "배송비", "주문상태"};
        		String[] column = {"orderday", "ordno", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
                				 , "totprice", "paywaytypename", "iscombdelivname", "delivfaretypename", "delivamt", "ordstatusname"};
        		int[] columnSize = {20*256, 20*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
                				  , 15*256, 15*256, 10*256, 10*256, 15*256, 10*256};
            	return makeExcelFile(header, column, columnSize, list, params.getStr("filename"));
        	}
        	// 배송준비중
        	else if (Util.equal(params.getStr("ordstatus"), CMConst.ORDER_PREPARING_DELIV)) {
        		String[] header = {"주문일", "결제일", "주문번호", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
        						 , "판매금액", "결제수단", "배송유형", "배송조건", "배송비", "수령인명", "수령인연락처", "수령인주소", "배송수량", "주문상태", "배송요청사항"};
        		String[] column = {"orderday", "paymentday", "ordno", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
        						 , "totprice", "paywaytypename", "iscombdelivname", "delivfaretypename", "delivamt", "rcvname", "rcvtel", "rcvfulladdr", "delivcnt", "ordstatusname", "rcvreqdetail"};
        		int[] columnSize = {20*256, 20*256, 20*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
        						  , 15*256, 15*256, 10*256, 10*256, 15*256, 15*256, 15*256, 25*256, 10*256, 10*256, 15*256};
            	return makeExcelFile(header, column, columnSize, list, params.getStr("filename"));
        	}
        	// 배송중, 배송완료
        	else {
        		String fileName = StringUtil.isEmpty(params.getStr("filename"))? "주문목록.xlsx" : params.getStr("filename");
        		String[] header = {"주문일", "결제일", "주문번호", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
		        				 , "판매금액", "결제수단", "배송유형", "배송조건", "배송비", "수령인명", "수령인연락처", "수령인주소", "택배사", "송장번호"
		        				 , "배송수량", "주문상태"};
        		String[] column = {"orderday", "paymentday", "ordno", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
								 , "totprice", "paywaytypename", "iscombdelivname", "delivfaretypename", "delivamt", "rcvname", "rcvtel", "rcvaddr", "logistypename", "invoiceno"
								 , "delivcnt", "ordstatusname"};
        		int[] columnSize = {20*256, 20*256, 20*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
								  , 15*256, 15*256, 10*256, 10*256, 15*256, 15*256, 15*256, 25*256, 10*256, 15*256
								  , 10*256, 10*256};
            	return makeExcelFile(header, column, columnSize, list, fileName);
        	}
        } else {
        	// 입금대기
        	if (params.getStr("ordstatusarr").indexOf(CMConst.ORDER_WAITING_DEPOSIT) > -1) {
        		String[] header = {"주문경로", "주문일", "주문번호", "첫구매", "주문자명", "주문자ID", "주문자연락처", "회원구분", "회원유형"
		        				 , "회원등급", "판매구분", "파트너사명", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
		        				 , "판매금액", "입금자", "결제수단", "추가상품여부", "배송유형", "배송조건", "배송비", "주문상태"};
        		String[] column = {"ordpathtypename", "orderday", "ordno", "isfrstorder", "ordname", "ordid", "ordtel", "isnonmembername", "membertypename"
		        				 , "memlvtypename", "ispbgoodsname", "dealername", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
		        				 , "totprice", "vircusname", "paywaytypename", "isaddgoods", "iscombdelivname", "delivfaretypename", "delivamt", "ordstatusname"};
        		int[] columnSize = {10*256, 20*256, 20*256, 10*256, 15*256, 15*256, 15*256, 10*256, 10*256
		        				  , 10*256, 10*256, 15*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
		        				  , 15*256, 15*256, 15*256, 10*256, 10*256, 10*256, 15*256, 10*256};
            	return makeExcelFile(header, column, columnSize, list, params.getStr("filename"));
        	}
        	// 상품준비중
        	else if (params.getStr("ordstatusarr").indexOf(CMConst.ORDER_PREPARING_GOODS) > -1) {
        		String[] header = {"주문경로", "주문일", "결제일", "주문번호", "첫구매", "주문자명", "주문자ID", "주문자연락처", "회원구분", "회원유형"
		        				 , "회원등급", "판매구분", "파트너사명", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
		        				 , "판매금액", "결제수단", "추가상품여부", "배송유형", "배송조건", "배송비", "주문상태"};
        		String[] column = {"ordpathtypename", "orderday", "paymentday", "ordno", "isfrstorder", "ordname", "ordid", "ordtel", "isnonmembername", "membertypename"
		        				 , "memlvtypename", "ispbgoodsname", "dealername", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
		        				 , "totprice", "paywaytypename", "isaddgoods", "iscombdelivname", "delivfaretypename", "delivamt", "ordstatusname"};
        		int[] columnSize = {10*256, 20*256, 20*256, 20*256, 10*256, 15*256, 15*256, 15*256, 10*256, 10*256
		        				  , 10*256, 10*256, 15*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
		        				  , 15*256, 15*256, 10*256, 10*256, 10*256, 15*256, 10*256};
            	return makeExcelFile(header, column, columnSize, list, params.getStr("filename"));
        	}
        	// 배송준비중
        	else if (Util.equal(params.getStr("ordstatus"), CMConst.ORDER_PREPARING_DELIV)) {
        		String[] header = {"주문경로", "주문일", "결제일", "주문번호", "첫구매", "주문자명", "주문자ID", "주문자연락처", "회원구분", "회원유형"
		        				 , "회원등급", "판매구분", "파트너사명", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
		        				 , "판매금액", "결제수단", "추가상품여부", "배송유형", "배송조건", "배송비", "수령인명", "수령인연락처", "수령인주소", "배송수량"
		        				 , "주문상태", "배송요청사항" };
        		String[] column = {"ordpathtypename", "orderday", "paymentday", "ordno", "isfrstorder", "ordname", "ordid", "ordtel", "isnonmembername", "membertypename"
		        				 , "memlvtypename", "ispbgoodsname", "dealername", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
		        				 , "totprice", "paywaytypename", "isaddgoods", "iscombdelivname", "delivfaretypename", "delivamt", "rcvname", "rcvtel", "rcvfulladdr", "delivcnt"
		        				 , "ordstatusname", "rcvreqdetail" };
        		int[] columnSize = {10*256, 20*256, 20*256, 20*256, 10*256, 15*256, 15*256, 15*256, 10*256, 10*256
		        				  , 10*256, 10*256, 15*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
		        				  , 15*256, 15*256, 10*256, 10*256, 10*256, 15*256, 15*256, 15*256, 25*256, 10*256
		        				  , 10*256, 25*256};
            	return makeExcelFile(header, column, columnSize, list, params.getStr("filename"));
        	}
        	// 배송중
        	else if (Util.equal(params.getStr("ordstatus"), CMConst.ORDER_IN_DELIVERY)) {
        		String[] header = {"주문경로", "주문일", "결제일", "주문번호", "첫구매", "주문자명", "주문자ID", "주문자연락처", "회원구분", "회원유형"
		        				 , "회원등급", "판매구분", "파트너사명", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
		        				 , "판매금액", "결제수단", "추가상품여부", "배송유형", "배송조건", "배송비", "수령인명", "수령인연락처", "수령인주소", "택배사"
		        				 , "송장번호", "배송수량", "주문상태"};
        		String[] column = {"ordpathtypename", "orderday", "paymentday", "ordno", "isfrstorder", "ordname", "ordid", "ordtel", "isnonmembername", "membertypename"
		        				 , "memlvtypename", "ispbgoodsname", "dealername", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
		        				 , "totprice", "paywaytypename", "isaddgoods", "iscombdelivname", "delivfaretypename", "delivamt", "rcvname", "rcvtel", "rcvaddr", "logistypename"
		        				 , "invoiceno", "delivcnt", "ordstatusname"};
        		int[] columnSize = {10*256, 20*256, 20*256, 20*256, 10*256, 15*256, 15*256, 15*256, 10*256, 10*256
		        				  , 10*256, 10*256, 15*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
		        				  , 15*256, 15*256, 10*256, 10*256, 10*256, 15*256, 15*256, 15*256, 25*256, 10*256
		        				  , 15*256, 10*256, 10*256};
            	return makeExcelFile(header, column, columnSize, list, params.getStr("filename"));
        	}
        	// 배송완료
        	else if (params.getStr("ordstatusarr").indexOf(CMConst.ORDER_COMPLETE_DELIV) > -1) {
        		String[] header = {"주문경로", "주문일", "결제일", "주문번호", "첫구매", "주문자명", "주문자ID", "주문자연락처", "회원구분", "회원유형"
		        				 , "회원등급", "판매구분", "파트너사명", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
		        				 , "판매금액", "결제수단", "추가상품여부", "배송유형", "배송조건", "배송비", "수령인명", "수령인연락처", "수령인주소", "택배사"
		        				 , "송장번호", "구매확정", "배송수량", "주문상태"};
    			String[] column = {"ordpathtypename", "orderday", "paymentday", "ordno", "isfrstorder", "ordname", "ordid", "ordtel", "isnonmembername", "membertypename"
		    					 , "memlvtypename", "ispbgoodsname", "dealername", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
		    					 , "totprice", "paywaytypename", "isaddgoods", "iscombdelivname", "delivfaretypename", "delivamt", "rcvname", "rcvtel", "rcvaddr", "logistypename"
		    					 , "invoiceno", "cfmconts", "delivcnt", "ordstatusname"};
    			int[] columnSize = {10*256, 20*256, 20*256, 20*256, 10*256, 15*256, 15*256, 15*256, 10*256, 10*256
		    					  , 10*256, 10*256, 15*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
		    					  , 15*256, 15*256, 10*256, 10*256, 10*256, 15*256, 15*256, 15*256, 25*256, 10*256
		    					  , 15*256, 25*256, 10*256, 10*256};
            	return makeExcelFile(header, column, columnSize, list, params.getStr("filename"));
        	}
        	// 이외
        	else {
        		String[] header = {"주문경로", "주문일", "결제일", "주문번호", "첫구매", "주문자명", "주문자ID", "주문자연락처", "회원구분", "회원유형"
		        				 , "회원등급", "판매구분", "파트너사명", "상품코드", "단품코드", "상품순번", "상품명", "옵션", "주문수량", "판매단가"
		        				 , "판매금액", "입금자", "결제수단", "추가상품여부", "배송유형", "배송조건", "배송비", "수령인명", "수령인연락처", "수령인주소"
		        				 , "택배사", "송장번호", "구매확정", "배송수량", "주문상태"};
    			String[] column = {"ordpathtypename", "orderday", "paymentday", "ordno", "isfrstorder", "ordname", "ordid", "ordtel", "isnonmembername", "membertypename"
		    					 , "memlvtypename", "ispbgoodsname", "dealername", "goodscode", "optioncode", "goodsturn", "goodsname", "optionconts", "ordcnt", "price"
		    					 , "totprice", "vircusname", "paywaytypename", "isaddgoods", "iscombdelivname", "delivfaretypename", "delivamt", "rcvname", "rcvtel", "rcvaddr"
		    					 , "logistypename", "invoiceno", "cfmconts", "delivcnt", "ordstatusname"};
    			int[] columnSize = {10*256, 20*256, 20*256, 20*256, 10*256, 15*256, 15*256, 15*256, 10*256, 10*256
		    					  , 10*256, 10*256, 15*256, 15*256, 10*256, 10*256, 30*256, 20*256, 10*256, 15*256
		    					  , 15*256, 15*256, 15*256, 10*256, 10*256, 10*256, 15*256, 15*256, 15*256, 25*256
		    					  , 10*256, 15*256, 25*256, 10*256, 10*256};
    			return makeExcelFile(header, column, columnSize, list, "주문목록.xlsx");
        	}
        }
    }

    /**
     * 주문상태변경이력 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/status/list", method = RequestMethod.POST)
    public Response orderStatusList(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectOrderStatusHistList(params));
    }

    /**
     * 주문상태 갱신
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/status/update", method = RequestMethod.POST)
    public Response orderStatusUpdate(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.updateOrderStatus(params));
        return new Response(result);
    }

    /**
     * 주문배송정보 목록조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/invoice/list", method = RequestMethod.POST)
    public Response orderInvoiceList(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectOrderInvoiceList(params));
    }

    /**
     * 일괄출고 (배송추적요청)
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/invoice/save/all", method = RequestMethod.POST)
    public Response saveOrderInvoiceAll(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.saveOrderInvoiceAll(params));
        return new Response(result);
    }

    /**
     * 송장 일괄등록(엑셀)
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/invoice/saveall", method = RequestMethod.POST)
    public Response saveAllOrderInvoice(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.saveOrderInvoiceByExcel(params));
        return new Response(result);
    }

    /**
     * 주문상품배송정보 추가
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/invoice/add", method = RequestMethod.POST)
    public Response addOrderInvoice(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.insertComOrderGoodsDeliv(params));
        return new Response(result);
    }

    /**
     * 주문상품배송정보 삭제 (사용여부 갱신)
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/invoice/remove", method = RequestMethod.POST)
    public Response removeOrderInvoice(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.updateComOrderGoodsDelivUse(params));
        return new Response(result);
    }

    /**
     * 주문상품목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.POST)
    public Response orderGoodsList(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectOrderGoodsList(params));
    }

    /**
     * 주문기본정보 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Response orderInfo(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectOrderInfo(params));
    }

    /**
     * 주문상세정보 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response orderDetail(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectOrderDetail(params));
    }

    /**
     * 취소내역 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/cncinfo", method = RequestMethod.POST)
    public Response cncInfo(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectCsInfo(params));
    }

    /**
     * 교환내역 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/excinfo", method = RequestMethod.POST)
    public Response excInfo(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectCsInfo(params));
    }

    /**
     * 주문상세 클레임내역 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/claim/info", method = RequestMethod.POST)
    public Response rtnInfo(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectClaimInfo(params));
    }

    /**
     * 주문CS 처리내역 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/csinfo", method = RequestMethod.POST)
    public Response csInfo(@RequestBody SOMap params) throws Exception {
        return new Response(adminOrderService.selectCsInfo(params));
    }

    /**
     * 배송지정보 수정
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/rcv/update", method = RequestMethod.POST)
    public Response updateRcvInfo(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", commonOrderService.updateOrderRcvInfo(params));
        return new Response(result);
    }

    /**
     * 관리자메모 추가
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/memo/add", method = RequestMethod.POST)
    public Response addOrderMemo(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.insertOrderMemo(params));
        return new Response(result);
    }

    /**
     * 관리자메모 삭제
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/memo/remove", method = RequestMethod.POST)
    public Response removeOrderMemo(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.updateOrderMemoUse(params));
        return new Response(result);
    }

    /**
     * 직배송 안내문구 저장
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/save/dirdelivmsg", method = RequestMethod.POST)
    public Response saveDirDelivMsg(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.updateDirDelivMsg(params));
        return new Response(result);
    }

    /**
     * 배송완료 처리시 유효성 체크
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/compdeliv/valid", method = RequestMethod.POST)
    public Response checkValidCompdeliv(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminOrderService.selectSameInvoiceCount(params));
        return new Response(result);
    }
}
