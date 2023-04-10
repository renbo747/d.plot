package com.dplot.admin.controller.promotion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.promotion.AdminReserveService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @discription	: 프로모션 적립금 Controller
 * @fileName	: AdminReserveController.java
 * @author		: JSK
 * @date		: 2021.12.20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.20	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/reserve")
public class AdminReserveController extends ExcelDownController{
	
    @Autowired
    AdminReserveService adminReserveService;

    /**
     * 적립금 목록 조회
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response reserveList(@RequestBody SOMap params) throws Exception {
        return new Response(adminReserveService.selectReserveList(params));
    }
    
    /**
     * 적립금 목록 조회(엑셀용)
     *
     * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> reserveListForExcel(@RequestBody SOMap params) throws Exception {
    	List<Map<String, Object>> list = adminReserveService.selectReserveListForExcel(params);
    	for (int i=0; i<list.size(); i++) {
    		list.get(i).put("no", i+1);
    	}
    	
        String[] header = {"No", "구분", "아이디", "이름", "유형", "등급", "포인트", "종류", "수동처리여부", "상세구분", "수동지급/차감사유", "주문번호", "적립/차감/소멸일자"};
        String[] column = {"no", "isempreservename", "userid", "username", "membertypename", "memlvtypename", "formatpoint", "typename", "isautoname", "respaytypename", "resreason", "ordercode", "regdate"};
        int[] columnSize = {4*256, 20*256, 20*256, 15*256, 15*256, 15*256, 10*256, 10*256, 10*256, 20*256, 30*256, 15*256, 15*256};
    	
    	return makeExcelFile(header, column, columnSize, list, "적립/차감/소멸내역.xlsx");
    }

    /**
     * 적립금 저장
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response saveReserve(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminReserveService.saveReserve(params));
        return new Response(result);
    }

    /**
     * 적립금차감 저장
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/deduction", method = RequestMethod.POST)
    public Response saveReserveDeduction(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
        return new Response(adminReserveService.saveReserveDeduction(params));
    }
    
    /**
     * 적립금 자동지급설정 조회
     *
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public Response autoPaymentConfig() throws Exception {
        return new Response(adminReserveService.selectReserveConfig());
    }

    /**
     * 적립금 자동지급설정 저장
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/config/save", method = RequestMethod.POST)
    public Response saveReserveConfig(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminReserveService.saveReserveConfig(params));
        return new Response(result);
    }
}
