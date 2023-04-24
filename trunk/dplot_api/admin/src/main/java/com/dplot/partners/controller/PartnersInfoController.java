package com.dplot.partners.controller;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.partners.service.PartnersCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/partners/common")
public class PartnersInfoController {

    @Autowired
    private PartnersCommonService partnersCommonService;

    @RequestMapping(value = "/charger", method = RequestMethod.POST)
    public Response getChargerInfo(@RequestBody SOMap params) throws Exception {
        return new Response(partnersCommonService.getChargerInfoByType(params));
    }

    @RequestMapping(value = "/send/auth/message", method = RequestMethod.POST)
    public Response sendAuthMessage(@RequestBody SOMap params) throws Exception {
        return new Response(partnersCommonService.sendAuthMessage(params));
    }
}
