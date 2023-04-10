package com.dplot.admin.controller.stats;

import com.dplot.admin.service.stats.AdminStatsService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/stats")
public class AdminStatsController extends ExcelDownController {
    @Autowired
    private AdminStatsService adminStatsService;

    @RequestMapping("/member/list")
    public Response statsMemberList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectNewMemberStats(params));
    }

    @RequestMapping("/member/list/excel")
    public ResponseEntity<ByteArrayResource> statsMemberListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectNewMemberStatsExcel(params);

        String[] header = {"일자", "전체", "PC", "모바일", "모바일APP"};
        String[] column = {"joindate","total","pc", "mobile","app"};
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "신규회원목록", sf.format(timestamp));
        int[] columnSize = {28*256, 14*256, 14*256, 14*256, 14*256};

        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping(value="/cart/list")
    public Response statsCartList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectCartStatsList(params));
    }

    @RequestMapping("/cart/list/excel")
    public ResponseEntity<ByteArrayResource> statsCartListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectCartStatsListExcel(params);

        String[] header = {"순위", "공급사코드", "공급사명", "브랜드코드", "브랜드명", "상품코드", "단품코드", "상품명", "옵션명", "장바구니 담긴 수", "회원수", "판매상태"};
        String[] column = {"ranking","dealerno","dealername", "brcode","brandname", "goodsno", "optioncode", "goodsname", "optionname", "ordcnt", "cnt", "goodsselltypename"};
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "장바구니_상품분석", sf.format(timestamp));
        int[] columnSize = {14*256, 14*256, 20*256, 14*256, 20*256, 14*256, 14*256, 25*256, 40*256, 14*256, 14*256, 14*256};

        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping(value ="/product/list")
    public Response productStatList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectProductStatList(params));
    }

    @RequestMapping(value = "/product/list/excel")
    public ResponseEntity<ByteArrayResource> statsProductListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectProductStatListExcel(params);
        String[] header;
        String[] column;
        int[] columnSize;
        if("OPTION".equals(params.getStr("gubun"))){
            header = new String[]{"순위", "상품코드", "단품코드", "상품명", "옵션명", "순 주문수량", "환불수량", "판매수량", "취급고 합계", "매출 합계", "환불 합계", "영업이익", "영업이익률(%)", "상품쿠폰할인"};
            column = new String[]{"ranking","goodscode", "optioncode", "goodsname", "optionname", "frstordcnt","clmcnt", "recnt", "billing", "price", "rtnamt", "margin", "marginper", "goodscpnamt"};
            columnSize = new int[]{14 * 256, 20 * 256, 20 * 256, 20 * 256, 20 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256};
        } else {
            header = new String[]{"순위", "상품코드", "상품명", "순 주문수량", "환불수량", "판매수량", "취급고 합계", "매출 합계", "환불 합계", "영업이익", "영업이익률(%)", "상품쿠폰할인"};
            column = new String[]{"ranking","goodscode","goodsname", "frstordcnt","clmcnt", "recnt", "billing", "price", "rtnamt", "margin", "marginper", "goodscpnamt"};
            columnSize = new int[]{14*256, 20*256, 20*256, 14*256, 14*256, 14*256, 14*256, 14*256, 14*256, 14*256, 14*256, 14*256};
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "판매상품_순위", sf.format(timestamp));


        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping(value ="/cate/list")
    public Response categoryStatList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectCategoryStatList(params));
    }

    @RequestMapping(value = "/cate/list/excel")
    public ResponseEntity<ByteArrayResource> categoryStatListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectCategoryStatListExcel(params);
        String[] header = {"순위", "대분류", "중분류", "소분류", "세분류", "순 주문수량", "환불수량", "판매수량", "취급고 합계", "매출 합계", "환불 합계", "영업이익", "영업이익률(%)", "상품쿠폰할인"};
        String[] column = {"ranking","name1","name2", "name3","name4", "frstordcnt","clmcnt", "recnt", "billing", "price", "rtnamt", "margin", "marginper", "goodscpnamt"};
        int[] columnSize = new int[]{14 * 256, 20 * 256, 20 * 256, 20 * 256, 20 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256};
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "판매분류_순위", sf.format(timestamp));


        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping(value = "/claim/list")
    public Response claimStatList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectClaimStatList(params));
    }

    @RequestMapping(value = "/claim/list/excel")
    public ResponseEntity<ByteArrayResource> statsClaimListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectClaimStatListExcel(params);
        String[] header;
        String[] column;
        int[] columnSize;
        if("OPTION".equals(params.getStr("gubun"))){
            header = new String[]{"순위", "상품코드", "단품코드", "상품명", "옵션명", "순 주문수량", "환불수량", "판매수량", "클레임금액 합계"};
            column = new String[]{"ranking","goodscode", "optioncode", "goodsname", "optionname", "frstordcnt","clmcnt", "cnt", "clmamt"};
            columnSize = new int[]{14 * 256, 14 * 256, 14 * 256, 20 * 256, 20 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256};
        } else {
            header = new String[]{"순위", "상품코드", "상품명", "순 주문수량", "환불수량", "판매수량", "클레임금액 합계"};
            column = new String[]{"ranking","goodscode","goodsname", "frstordcnt","clmcnt", "cnt", "clmamt"};
            columnSize = new int[]{14*256, 14*256, 20*256, 14*256, 14*256, 14*256, 14*256};
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "클레임_순위", sf.format(timestamp));


        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping(value ="/sale/date/list")
    public Response saleDateStatList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectSaleDateStatList(params));
    }

    @RequestMapping(value = "/sale/date/list/excel")
    public ResponseEntity<ByteArrayResource> saleDateStatListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectSaleDateStatListExcel(params);
        String[] header = {"일자", "순 주문건수", "순 주문수량", "최초판매가", "프로모션할인", "쿠폰할인", "적립금", "D포인트", "임직원포인트", "배송비", "순 주문금액", "실 결제금액", "영업이익", "매출", "환불합계"};
        String[] column = {"target","ordercnt","salecnt", "stamt","salepromoamt", "goodscpnamt","reserveamt", "epointamt", "empreserveamt", "delivamt", "pure", "realamt", "margin", "price", "rtnamt"};
        int[] columnSize = new int[]{14 * 256, 20 * 256, 20 * 256, 20 * 256, 20 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256};
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "일별_주별_월별_매출", sf.format(timestamp));

        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping(value ="/age/list")
    public Response ageAndGenderStatList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectAgeStatList(params));
    }

    @RequestMapping(value = "/age/list/excel")
    public ResponseEntity<ByteArrayResource> ageAndGenderStatListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectAgeStatListExcel(params);
        String[] header = {"분류", "주문자수", "총 주문건수", "순 주문건수", "순 주문금액", "객단가", "취소건수", "교환건수", "환불건수", "반품건수", "신규회원수", "SMS수신동의수", "탈퇴회원수", "휴면회원전환수"};
        String[] column = {"name","humancnt","ordercnt", "pordercnt", "price", "unitamt", "cnccnt", "exccnt", "ramtcnt", "rtncnt", "regcnt", "smscnt", "rescnt", "dorcnt"};
        int[] columnSize = new int[]{30 * 256, 14 * 256, 14 * 256, 14 * 256, 20 * 256, 20 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256 };
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "연령별_성별_주문분석", sf.format(timestamp));

        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping(value ="/date/list")
    public Response selectDateStatList(@RequestBody SOMap params) throws Exception {
        return new Response(adminStatsService.selectDateStatList(params));
    }

    @RequestMapping(value = "/date/list/excel")
    public ResponseEntity<ByteArrayResource> selectDateStatListExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminStatsService.selectDateStatListExcel(params);
        String[] header = {"일자", "총 주문건수", "순 주문건수", "순 주문금액", "객단가", "취소건수", "교환건수", "환불건수", "반품건수", "신규회원수", "SMS수신동의수", "탈퇴회원수", "휴면회원전환수"};
        String[] column = {"target","ordercnt", "pordercnt", "price", "unitamt", "cnccnt", "exccnt", "ramtcnt", "rtncnt", "regcnt", "smscnt", "rescnt", "dorcnt"};
        int[] columnSize = new int[]{30 * 256, 14 * 256, 14 * 256, 20 * 256, 20 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256, 14 * 256};
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "일별_주별_월별_분석", sf.format(timestamp));

        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }
}
