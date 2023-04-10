package com.dplot.partners.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dplot.common.controller.ExcelDownController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.common.AdminCommonService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.partners.service.PartnersCommonService;
import com.dplot.util.Util;

@RestController
@RequestMapping(value = "/partners/common")
public class PartnersCommonController extends ExcelDownController {

    @Autowired
    private PartnersCommonService partnersCommonService;
    @Autowired
    private AdminCommonService adminCommonService;
    @Resource(name="propertiesFactory")
    private Properties prop;

    /**
     * 사이드메뉴 조회 (파트너사)
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/side-menu", method = RequestMethod.POST)
    public Response partnersSideMenuList(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("sideMenu", partnersCommonService.selectPartnersSubMenu(params));

        return new Response(result);
    }

    /**
     * 네비게이터에 노출할 메뉴 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/menu-nav", method = RequestMethod.POST)
    public Response partnersMenuNavigator(@RequestBody SOMap params) throws Exception {
        String url = params.containsKey("path") ? params.get("path").toString() : "";

//        if(!"".equals(url)){
//            int startIndex = url.indexOf("partners.") + 9;
//            params.put("path", String.format("%s%s", "admin.", url.substring(startIndex)));
//        }
        params.put("path", url);
        params.put("ismaster", "F");
        SOMap result = adminCommonService.selectAdminMenuNav(params);
        String[] includes = {"pageurl", "depth1url", "depth2url", "depth3url"};

        for( String key : result.keySet() ){
            if(Arrays.asList(includes).contains(key)){
                result.put(key, ("".equals(result.get(key))) ? "" : result.getStr(key).replace("admin.", "partners."));
            }
        }
        return new Response(result);
    }

    /**
     * 파트너사 페이지 읽기/쓰기권한 조회
     * (관리자랑 같이 쓰기때문에 파트너사일 경우 권한 관리가 따로 없기 때문에 모두 T : TRUE 로 리턴)
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/pageAuth/check", method = RequestMethod.POST)
    public Response adminMenuPageAuthCheck(@RequestBody SOMap params){
        SOMap result = new SOMap();
        result.put("url", params.get("url"));
        result.put("isread", "T");
        result.put("iswrite", "T");
        return new Response(result);
    }

    /**
     * 상품목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/goods/list", method = RequestMethod.POST)
    public Response goodsList(@RequestBody SOMap params) throws Exception {
        return new Response(adminCommonService.selectGoodsList(params));
    }

    /**
     * 사은품목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/gift/list", method = RequestMethod.POST)
    public Response giftList(@RequestBody SOMap params) throws Exception {
        return new Response(adminCommonService.selectGiftList(params));
    }

    /**
     * 엑셀양식 다운로드
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/excel/download", method = RequestMethod.POST)
    public Response excelDownload(@RequestBody SOMap params, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Response result = new Response();
    	String rootpath = request.getSession().getServletContext().getRealPath("/");
    	String excelpath = prop.getProperty("base.excel.template.path");
        String filename = params.getStr("filename");
        File file = null;
        if (Util.equal(prop.getProperty("Globals.Profile"), "local")) {
            file = new File(rootpath + excelpath + filename);
        } else {
            file = new File(excelpath + filename);
        }
        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        try {
            String downloadname = null;
            String browser = request.getHeader("User-Agent");
            //파일 인코딩
            if (browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
            	downloadname = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
            } else {
            	downloadname = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-Disposition","attachment;filename=" + downloadname);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Transfer-Encoding", "binary;");

            fileInputStream = new FileInputStream(file);
            servletOutputStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int readsize;
            while ((readsize=(fileInputStream.read(buffer, 0, buffer.length))) != -1) {
                servletOutputStream.write(buffer, 0, readsize);
            }
            servletOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatusCode(Status.BAD_REQUEST.getKey());
            result.setMessage("엑셀 다운로드중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
        } finally {
            if (servletOutputStream != null) {
            	servletOutputStream.close();
            }
            if (fileInputStream != null) {
            	fileInputStream.close();
            }
        }

        return result;
    }




    @RequestMapping(value ="/sale/list")
    public Response selectPartnersSaleList(@RequestBody SOMap params) throws Exception {
        return new Response(partnersCommonService.selectPartnersSaleList(params));
    }

    @RequestMapping(value = "/sale/list/excel")
    public ResponseEntity<ByteArrayResource> selectPartnersSaleListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = partnersCommonService.selectPartnersSaleListExcel(params);
        String[] header = {"일자", "순 주문건수", "순 주문수량", "상품구매금액", "배송비", "할인분담금", "쿠폰분담금", "실 결제금액", "매출", "환불합계"};
        String[] column = {"target","ordercnt", "frstordcnt", "ordamt", "delivamt","promoshareamt", "cpnshareamt", "realamt", "price", "rtnamt"};
        int[] columnSize = new int[]{30 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256};
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "매출현황", sf.format(timestamp));

        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }


}
