package com.dplot.admin.controller.partners.popup;

import com.dplot.admin.controller.partners.PartnersNoticeController;
import com.dplot.admin.service.partners.AdminPartnersAgreeListPopupService;
import com.dplot.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 설명 : 파트너사 미동의, 동의 목록 컨트롤러
 * 생성 : 정재호
 * 일지 : 2021-11-08
 */

/**
 * @author : ywm2004
 * @discription : 파트너사 미동의, 동의 목록 Controller
 * @fileName : PartnersAgreeListPopupController.java
 * @date : 2021-11-08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-08	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/partners/popup/agree")
public class PartnersAgreeListPopupController {
    private static final Logger logger = LoggerFactory.getLogger(PartnersNoticeController.class);

    @Autowired
    AdminPartnersAgreeListPopupService adminPartnersAgreeListPopupService;

    /**
     * 파트너사 동의현황 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody Map<String, Object> params) throws Exception {
        return new Response(adminPartnersAgreeListPopupService.selectAgreeList(params));
    }
}
