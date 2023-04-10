package com.dplot.admin.controller.partners;

import com.dplot.admin.service.partners.AdminPartnersNoticeService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 파트너사 공지사항 게시판 Controller
 * @fileName : PartnersNoticeController.java
 * @date : 2021-11-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = {"/admin/partners/notice", "/partners/partners/notice"})
public class PartnersNoticeController {
    private static final Logger logger = LoggerFactory.getLogger(PartnersNoticeController.class);

    @Autowired
    AdminPartnersNoticeService adminPartnersNoticeService;

    /**
     * 파트너사 공지사항 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(MultipartHttpServletRequest files, @RequestPart("params") SOMap params) throws Exception {
        Response response;
        try {
            params.put("uploadfile", files);
            adminPartnersNoticeService.updateBoardPost(params);
            response = new Response(Status.OK);
        } catch (Exception e) {
            response = new Response(Status.FAIL);
        }

        return response;
    }

    /**
     * 파트너사 공지사항 사용 여부 변경
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/istrash", method = RequestMethod.POST)
    public Response updateIsTrash(@RequestBody SOMap params) throws Exception {
        Response result;

        try {
            adminPartnersNoticeService.updateBoardPostIsTrash(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * 파트너사 공지사항 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersNoticeService.selectNoticeList(params));
    }

    /**
     * 파트너사 공지사항 게시
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(MultipartHttpServletRequest files, @RequestPart("params") SOMap params) throws Exception {
        Response response;
        try {
            params.put("uploadfile", files);
            adminPartnersNoticeService.insertNotice(params);
            response = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Status.FAIL);
        }
        return response;
    }

    /**
     * 파트너사 공지사항 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersNoticeService.selectNoticeDetail(params));
    }
}
