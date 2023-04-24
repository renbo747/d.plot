package com.dplot.admin.controller.promotion;

import com.dplot.admin.service.promotion.AdminPromotionEventService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.controller.ExcelDownController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 프로모션 이벤트 Controller
 * @fileName : EventController.java
 * @date : 2021-12-02
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-02	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/event")
public class AdminEventController extends ExcelDownController {

    @Autowired
    AdminPromotionEventService adminPromotionEventService;

    /**
     * 이벤트 리스트 조회
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
     * 이벤트 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEventService.selectEventDetail(params));
    }

    /**
     * 이벤트 댓글 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Response searchComment(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEventService.selectCommentList(params));
    }

    /**
     * 이벤트 저장
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
            adminPromotionEventService.insertEvent(params, uploadFile);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 이벤트 상세 내역 수정
     *
     * @param params
     * @param fileParams
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/detail", method = RequestMethod.POST)
    public Response updateDetail(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
        Response result = null;

        try {
            Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
            adminPromotionEventService.updateEventDetail(params, uploadFile);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 사용, 미사용 변경
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/use", method = RequestMethod.POST)
    public Response updateUse(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionEventService.updateEventUse(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 댓글 블라인드 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/comment/blind", method = RequestMethod.POST)
    public Response updateCommentBlind(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionEventService.updateEventCommentBlind(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 댓글 삭제
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/comment/delete", method = RequestMethod.POST)
    public Response updateComment(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionEventService.deleteEventComment(params);
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
                "이벤트명", "개인정보동의", "적용채널", "유형",
                "등급", "조회", "댓글", "응모",
                "시작일자", "종료일자", "발표일자", "등록일자",
                "사용여부", "노출여부", "진행상태", "이벤트 발표"
        };

        // 컬럼 이름
        String[] column = {
                "subject", "isagree", "muappcode", "mumembercode",
                "mumemlvcode", "hits", "commentcount", "entercount",
                "evsttime", "evedtime", "pubtime", "regdate",
                "istrash", "disptype", "iscomplete", "isannounce"
        };

        // 컬럼 너비 지정
        int[] columnSize = {
                (35 * 256), (17 * 256), (35 * 256), (35 * 256),
                (35 * 256), (10 * 256), (10 * 256), (10 * 256),
                (15 * 256), (15 * 256), (15 * 256), (15 * 256),
                (15 * 256), (15 * 256), (15 * 256), (15 * 256)
        };

        return makeExcelFile(header, column, columnSize, list, "이벤트목록.xlsx");
    }

    /**
     * 엑셀 다운로드 - 댓글
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/comment", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> commentExcelDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = adminPromotionEventService.selectEventCommentExcelDownload(params);

        // 헤더 이름
        ArrayList<String> header = new ArrayList<>(Arrays.asList(
                "No", "블라인드", "댓글", "좋아요",
                "첨부", "댓글구분", "답글수", "아이디",
                "이름", "신고", "등록/수정일자"
        ));
        ArrayList<String> headerList = params.getArrayList("headerlist");
        header.addAll(headerList);

        // 컬럼 이름
        ArrayList<String> column = new ArrayList<>(Arrays.asList(
                "rn", "isblind", "comment", "likecount",
                "fullpath", "issecret", "commentcount", "userid",
                "username", "reportcount", "regdate"
        ));
        ArrayList<String> columnlist = params.getArrayList("columnlist");
        column.addAll(columnlist);

        // 컬럼 사이즈
        ArrayList<Integer> columnSize = new ArrayList<>(Arrays.asList(
                (10 * 256), (10 * 256), (35 * 256), (10 * 256),
                (35 * 256), (10 * 256), (10 * 256), (10 * 256),
                (15 * 256), (10 * 256), (15 * 256)
        ));
        for (int i = 0; i < headerList.size(); i++) {
            columnSize.add((15 * 256));
        }

        return makeExcelFile(header.toArray(new String[0]), column.toArray(new String[0]), columnSize.stream().mapToInt(Integer::intValue).toArray(), list, "이벤트_댓글.xlsx");
    }

    /**
     * 엑셀 다운로드 - 응모
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/enter", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> enterExcelDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = adminPromotionEventService.selectEventEnterExcelDownload(params);

        // 헤더 이름
        ArrayList<String> header = new ArrayList<>(Arrays.asList(
                "아이디", "이름", "총 응모 수"
        ));
        ArrayList<String> headerList = params.getArrayList("headerlist");
        header.addAll(headerList);

        // 컬럼 이름
        ArrayList<String> column = new ArrayList<>(Arrays.asList(
                "userid", "name", "count"
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

        return makeExcelFile(header.toArray(new String[0]), column.toArray(new String[0]), columnSize.stream().mapToInt(Integer::intValue).toArray(), list, "이벤트_응모.xlsx");
    }
}
