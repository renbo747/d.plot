package com.dplot.admin.controller.promotion.popup;

import com.dplot.admin.service.promotion.AdminPromotionCheckEventService;
import com.dplot.admin.service.promotion.AdminPromotionEventService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ywm2004
 * @discription : 프로모션 이벤트 팝업 Controller
 * @fileName : AdminEventPopupController.java
 * @date : 2021-12-27
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-27	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/event/popup")
public class AdminEventPopupController {

    @Autowired
    AdminPromotionEventService adminPromotionEventService;

    @Autowired
    AdminPromotionCheckEventService adminPromotionCheckEventService;

    /**
     * 이벤트 댓글 신고 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/report", method = RequestMethod.POST)
    public Response searchReport(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEventService.selectEventReportList(params));
    }

    /**
     * 이벤트 응모 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/enter", method = RequestMethod.POST)
    public Response searchEnter(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionEventService.selectEventEnterList(params));
    }

    /**
     * 댓글 추가, 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify/comment", method = RequestMethod.POST)
    public Response saveAndUpdateComment(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPromotionEventService.insertAndUpdateEventComment(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    @RequestMapping(value = "/atten", method = RequestMethod.POST)
    public Response atten(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionCheckEventService.selectAttendDetail(params));
    }

}
