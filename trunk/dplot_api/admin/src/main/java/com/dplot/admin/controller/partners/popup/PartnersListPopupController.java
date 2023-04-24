package com.dplot.admin.controller.partners.popup;

import com.dplot.admin.controller.partners.PartnersNoticeController;
import com.dplot.admin.service.partners.AdminPartnersListPopupService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 파트너사 선택 리스트 팝업 Controller
 * @fileName : PartnersListPopupController.java
 * @date : 2021-11-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value={"/admin/partners/popup/select", "/partners/partners/popup/select"})
public class PartnersListPopupController {
    private static final Logger logger = LoggerFactory.getLogger(PartnersNoticeController.class);

    @Autowired
    private AdminPartnersListPopupService adminPartnersListPopupService;

    /**
     * 파트너사 리스트 팝업 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody HashMap<String, Object> params) throws Exception {
        return new Response(adminPartnersListPopupService.selectPartnersList(params));
    }


    /**
     * 넘어온 boardPostIdx로 체크 리스트 조회
     * ( boardPostIdx 필요 )
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Response searchCheckList(@RequestBody Map<String, Object> params) throws Exception {
        return new Response(adminPartnersListPopupService.selectCheckList(params));
    }
}
