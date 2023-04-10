package com.dplot.admin.controller.partners;

import com.dplot.admin.service.partners.AdminPartnersApplyService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.DeliveryTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/admin/partners/apply", "/partners/partners/apply"})
public class PartnersApplyController {
    private static final Logger logger = LoggerFactory.getLogger(PartnersApplyController.class);

    @Autowired
    private AdminPartnersApplyService adminPartnersService;

    @Autowired
    private DeliveryTrackingService deliveryTrackingService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response partnersApplyList(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersService.selectPartnersList(params));
    }

    @RequestMapping(value="/state/update", method = RequestMethod.POST)
    public Response partnersStateUpdate(@RequestBody SOMap params) throws Exception{
        SOMap result = new SOMap();
        params.put("user_no_arr", params.get("user_no_arr_str").toString().split(","));
        int resultCnt = adminPartnersService.updatePartnersState(params);
        result.put("resultCnt", resultCnt);
        return new Response(result);
    }

    @RequestMapping(value="/detail", method = RequestMethod.POST)
    public Response partnersDetail(@RequestBody SOMap params) throws Exception {
        SOMap result = adminPartnersService.selectPartnsersDetail(params);
        return new Response(result);
    }

    @RequestMapping(value="/password/init", method = RequestMethod.POST)
    public Response partnerPasswordInit(@RequestBody SOMap param){
        SOMap soMap = new SOMap();
        soMap.putAll(param);
        SOMap result = adminPartnersService.initPassword(soMap);
        return new Response(result);
    }

    @RequestMapping(value="/otp/code", method = RequestMethod.POST)
    public Response partnersApplyDeliveryService(@RequestBody SOMap param) throws Exception {
        return new Response(deliveryTrackingService.getPartnersOTPCode(param));
    }

    @RequestMapping(value="/charger/delete", method = RequestMethod.POST)
    public Response partnersDetailChargerDelete(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersService.deletePartnersCharger(param));
    }

    @RequestMapping(value = "/goodsflow/refresh", method = RequestMethod.POST)
    public Response partnersGoodsflowApplyRefresh(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        try {
            result = deliveryTrackingService.requestApplyServiceUsageResult(param);
        } catch(Exception e){
            logger.error(e.getMessage());
        }
        return new Response(result);
    }

}
