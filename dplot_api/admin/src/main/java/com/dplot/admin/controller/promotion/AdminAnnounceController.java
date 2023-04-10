package com.dplot.admin.controller.promotion;

import com.dplot.admin.service.promotion.AdminPromotionAnnounceService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 이벤트 발표 관리 Controller
 * @fileName : AdminAnnounceController.java
 * @date : 2021-12-21
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-21	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/event/announce")
public class AdminAnnounceController extends ExcelDownController {

    @Autowired
    AdminPromotionAnnounceService adminPromotionAnnounceService;

    /**
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionAnnounceService.selectEventAnnounceList(params));
    }

    /**
     * 종료, 선택할 이벤트 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/event", method = RequestMethod.POST)
    public Response searchEvent(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionAnnounceService.selectEndEventList(params));
    }

    /**
     * 응모 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/enter", method = RequestMethod.POST)
    public Response searchEnter(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionAnnounceService.selectEnterList(params));
    }

    /**
     * 이벤트 당첨자 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionAnnounceService.selectEventAnnounceDetail(params));
    }

    /**
     * 이벤트 당첨자 게시물 저장
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionAnnounceService.insertAnnounce(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 이벤트 당첨자 게시물 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionAnnounceService.updateAnnounce(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 엑셀 다운로드 - 이벤트 발표
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
                "No", "당첨자발표제목", "이벤트명", "당첨자 수",
                "조회", "작성자명", "예약여부", "게시일자",
                "등록일자"
        ));

        // 컬럼 이름
        ArrayList<String> column = new ArrayList<>(Arrays.asList(
                "rn", "announcesubject", "eventsubject", "count",
                "hits", "writer", "isrightnow", "poststtime",
                "regdate"
        ));

        // 컬럼 사이즈
        ArrayList<Integer> columnSize = new ArrayList<>(Arrays.asList(
                (10 * 256), (35 * 256), (35 * 256), (10 * 256),
                (10 * 256), (15 * 256), (10 * 256), (15 * 256),
                (15 * 256)
        ));

        return makeExcelFile(header.toArray(new String[0]), column.toArray(new String[0]), columnSize.stream().mapToInt(Integer::intValue).toArray(), list, "이벤트_발표.xlsx");
    }

}
