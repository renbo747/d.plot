package com.dplot.admin.controller.promotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.common.AdminCommonService;
import com.dplot.admin.service.promotion.AdminPromotionCheckEventService;
import com.dplot.admin.service.promotion.AdminPromotionEventService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : ywm2004
 * @discription : 프로모션 출석체크 이벤트 Controller
 * @fileName : AdminCheckEventController.java
 * @date : 2021-12-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-22	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/checkevent")
public class AdminCheckEventController extends ExcelDownController {

    @Autowired
    AdminPromotionCheckEventService adminPromotionCheckEventService;

    @Autowired
    AdminPromotionEventService adminPromotionEventService;

    @Autowired
    AdminCommonService adminCommonService;

    /**
     * 출석체크 이벤트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEventService.selectEventList(params));
    }

    /**
     * 출석체크 이벤트 상세
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionCheckEventService.selectCheckEventDetail(params));
    }

    /**
     * 출석체크 이벤트 등록
     *
     * @param params
     * @param fileParams
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
        Response result = null;

        try {
            Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
            adminPromotionCheckEventService.insertCheckEvent(params, uploadFile);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * 출석체크 이벤트 수정
     *
     * @param params
     * @param fileParams
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
        Response result = null;

        try {
            Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
            adminPromotionCheckEventService.updateCheckEvent(params, uploadFile);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * 엑셀 다운로드 - 리스트
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = params.getArrayList("list");

        // 헤더 이름
        ArrayList<String> header = new ArrayList<>(Arrays.asList(
                "이벤트명", "개인정보동의", "적용채널", "유형",
                "등급", "조회", "댓글", "출석인정기준",
                "출석수", "혜택지급반복", "시작일자", "종료일자",
                "등록일자", "사용여부", "노출여부", "진행상태"
        ));

        // 컬럼 이름
        ArrayList<String> column = new ArrayList<>(Arrays.asList(
                "subject", "isagree", "muappcode", "mumembercode",
                "mumemlvcode", "hits", "commentcount", "iscontattend",
                "winattendcnt", "benefitcount", "evsttime", "evedtime",
                "regdate", "istrash", "disptype", "iscomplete"
        ));

        // 컬럼 사이즈
        ArrayList<Integer> columnSize = new ArrayList<>(Arrays.asList(
                (10 * 256), (10 * 256), (35 * 256), (10 * 256),
                (10 * 256), (10 * 256), (35 * 256), (10 * 256),
                (10 * 256), (10 * 256), (35 * 256), (10 * 256),
                (10 * 256), (10 * 256), (35 * 256), (10 * 256)
        ));

        return makeExcelFile(header.toArray(new String[0]), column.toArray(new String[0]), columnSize.stream().mapToInt(Integer::intValue).toArray(), list, "출석체크_이벤트.xlsx");
    }

    /**
     * 엑셀 다운로드 - 출석체크 현황
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/atten", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelAttenDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = adminPromotionCheckEventService.selectCheckAttendExcelDownload(params);

        // 헤더 이름
        ArrayList<String> header = new ArrayList<>(Arrays.asList(
                "아이디", "이름", "출석인정일/출석일", "혜택지급"
        ));
        ArrayList<String> headerList = params.getArrayList("headerlist");
        header.addAll(headerList);

        // 컬럼 이름
        ArrayList<String> column = new ArrayList<>(Arrays.asList(
                "userid", "name", "totalcount", "benefitpaycount"
        ));
        ArrayList<String> columnlist = params.getArrayList("columnlist");
        column.addAll(columnlist);

        // 컬럼 사이즈
        ArrayList<Integer> columnSize = new ArrayList<>(Arrays.asList(
                (10 * 256), (10 * 256), (35 * 256), (10 * 256)
        ));
        for (int i = 0; i < headerList.size(); i++) {
            columnSize.add((15 * 256));
        }

        return makeExcelFile(header.toArray(new String[0]), column.toArray(new String[0]), columnSize.stream().mapToInt(Integer::intValue).toArray(), list, "출석체크_현황.xlsx");
    }

    /**
     * 페이지에 필요한 코드값
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public Response searchCode(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionCheckEventService.selectCodeList());
    }

    /**
     * 쿠폰 리스트
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/coupon", method = RequestMethod.POST)
    public Response searchCoupon(@RequestBody SOMap params) throws Exception {
        return new Response(adminCommonService.selectUseCouponList(params));
    }
}
