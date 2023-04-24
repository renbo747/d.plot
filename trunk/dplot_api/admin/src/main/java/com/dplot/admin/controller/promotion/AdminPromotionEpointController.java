package com.dplot.admin.controller.promotion;

import com.dplot.admin.service.promotion.AdminPromotionEpointService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.controller.ExcelDownController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 프로모션 e포인트 관리 Conroller
 * @fileName : AdminPromotionEpointController.java
 * @date : 2021-12-30
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-30	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/promotion/epoint")
public class AdminPromotionEpointController extends ExcelDownController {

    @Autowired
    AdminPromotionEpointService adminPromotionEpointService;

    /**
     * epoint 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEpointService.selectEpointList(params));
    }

    /**
     * 회원 정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/member", method = RequestMethod.POST)
    public Response searchMember(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEpointService.selectMemberInfo(params));
    }

    /**
     * 적립/차감 내역 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/usage", method = RequestMethod.POST)
    public Response searchUsage(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEpointService.selectUsageDetailList(params));
    }

    /**
     * epoint 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEpointService.selectEPointDetail(params));
    }

    /**
     * epoint 저장
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionEpointService.insertEpoint(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * epoint 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionEpointService.updateEpoint(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 엑셀 다운로드
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = params.getArrayList("list");

        // 헤더 이름
        String[] header = {
                "관리제목", "지급대상범위", "지급대상수",
                "지급포인트", "소멸일자", "중복사용여부", "지급 사유", "지급일자"
        };

        // 컬럼 이름
        String[] column = {
                "eponame", "ismemtype", "count",
                "payepoint", "epovalidday", "isepointdup", "eporeason", "epopayday",
        };

        // 컬럼 너비 지정
        int[] columnSize = {
                (35 * 256), (17 * 256), (35 * 256),
                (35 * 256), (35 * 256), (35 * 256), (35 * 256), (35 * 256),
        };

        return makeExcelFile(header, column, columnSize, list, "D포인트.xlsx");
    }

    /**
     * 엑셀 다운로드
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/usage", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelUsageDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = params.getArrayList("list");

        // 헤더 이름
        String[] header = {
                "아이디", "이름", "유형", "등급",
                "D포인트", "자동/수동", "상세 구분", "수동지급 사유",
                "주문번호", "적립/차감일자"
        };

        // 컬럼 이름
        String[] column = {
                "userid", "name", "mumembertype", "mumemlvtype"
                , "paypoint", "type", "epopaytype", "epopayreason"
                , "ordercode", "regdate"
        };

        // 컬럼 너비 지정
        int[] columnSize = {
                (17 * 256), (17 * 256), (35 * 256), (35 * 256)
                , (15 * 256), (15 * 256), (15 * 256), (15 * 256)
                , (15 * 256), (15 * 256)
        };

        return makeExcelFile(header, column, columnSize, list, "적립/차감내역.xlsx");
    }

}
