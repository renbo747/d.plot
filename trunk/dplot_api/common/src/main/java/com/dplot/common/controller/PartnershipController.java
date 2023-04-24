package com.dplot.common.controller;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.PartnershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/partnership")
public class PartnershipController {
    private static final Logger logger = LoggerFactory.getLogger(PartnershipController.class);

    @Autowired
    private PartnershipService partnershipService;

    @RequestMapping(value = "/id/check", method = RequestMethod.POST)
    public Response userIdCheck(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("isavailable", partnershipService.checkOverlapUserId(params.get("userid").toString()));
        return new Response(result);
    }

    @RequestMapping(value="/insert", method = RequestMethod.POST)
    public Response partnershipInsert(@RequestPart("params") SOMap param, MultipartHttpServletRequest file){
        SOMap result = new SOMap();
        Map<String, MultipartFile> files = file.getFileMap();
        boolean insertResult = partnershipService.insertPartnership(param, files);
        result.put("insertresult", insertResult);
        return new Response(result);
    }

    @RequestMapping(value="/init/data", method = RequestMethod.POST)
    public Response partnershipInitData(@RequestBody SOMap param) throws Exception {
        SOMap soMap = new SOMap();
        soMap.putAll(param);
        Map<String, Object> dataList = partnershipService.selectPartnershipJoinInitData(soMap);
        return new Response(dataList);
    }

    @RequestMapping(value="/biz/numberCheck", method = RequestMethod.POST)
    public Response bizNumberCheck(@RequestBody SOMap param) throws Exception {
        boolean result = partnershipService.bizNumberCheck(param);
        Map<String, Object> map = new HashMap<>();
        map.put("exist", result);
        return new Response(map);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response partnershipUpdate(@RequestBody SOMap param){
        SOMap result = new SOMap();
        boolean updateResult = partnershipService.updatePartnership(param);
        result.put("updateresult", updateResult);
        return new Response(result);
    }

    @RequestMapping(value="/updatePartnershipFile", method = RequestMethod.POST)
    public Response partnershipUpdateFile(@RequestPart("params") SOMap param, MultipartHttpServletRequest file){
        Map<String, MultipartFile> files = file.getFileMap();
        SOMap result = partnershipService.updatePartnershipFile(param, files);
        return new Response(result);
    }

    @RequestMapping(value="/deletePartnershipFile", method = RequestMethod.POST)
    public Response partnershipDeleteFile(@RequestBody SOMap param){
        SOMap result = partnershipService.deletePartnershipFile(param);
        return new Response(result);
    }

    @RequestMapping(value="/password/save", method = RequestMethod.POST)
    public Response partnersFindPasswordUpdate(@RequestBody SOMap param) throws Exception {
        return new Response(partnershipService.passwordUpdate(param));
    }

    @RequestMapping(value="/find/id", method = RequestMethod.POST)
    public Response partnersFindId(@RequestBody SOMap param) throws Exception {
        return new Response(partnershipService.findId(param));
    }

    @RequestMapping(value = "/terms", method = RequestMethod.POST)
    public Response partnersTerms(@RequestBody SOMap param) throws Exception {
        return new Response(partnershipService.selectPartnersTerms(param));
    }

}
