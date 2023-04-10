package com.dplot.admin.controller.common;

import com.dplot.admin.service.common.AdminCommonDashBoardService;
import com.dplot.admin.service.common.AdminCommonService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {"/admin/dashboard", "/partners/dashboard"})
@RestController
public class AdminCommonDashBoardController {

    @Autowired
    private AdminCommonDashBoardService adminCommonDashBoardService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response adminDashBoardInfo(@RequestBody SOMap param) throws Exception {
        return new Response(adminCommonDashBoardService.selectDashBoardData(param));
    }

    @RequestMapping(value = "/search/dealer", method =RequestMethod.POST)
    public Response partnersDashBoardInfo(@RequestBody SOMap param) throws Exception {
        return new Response(adminCommonDashBoardService.selectPartnerDashBoardData(param));
    }

}
