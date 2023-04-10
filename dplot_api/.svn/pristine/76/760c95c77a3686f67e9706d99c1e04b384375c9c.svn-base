package com.dplot.admin.controller.member;

import com.dplot.admin.service.member.AdminMemberService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;
import com.dplot.common.service.CommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/admin/member", "/partners/member"})
public class AdminMemberController extends ExcelDownController {

    @Autowired
    private AdminMemberService adminMemberService;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/total/list")
    public Response memberTotalList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectMemberList(params));
    }

    @RequestMapping("/total/list/excel")
    public ResponseEntity<ByteArrayResource> memberTotalListExcelDownload(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminMemberService.selectMemberExcelList(params);
        String[] header = {"아이디", "이름", "유형", "등급", "휴대폰번호", "이메일", "성별", "생년월일", "보유쿠폰", "적립금", "포인트", "임직원포인트", "최근접속일시", "최근구매일시", "첫구매일자", "가입일", "추천인ID"};
        String[] column = {"userid","username","dadamembertypename","memlvtypename","mobile","email","gendername","birthdate","havecoupon", "paypoint","epoint","emppoint","lastlogindate","lastbuydate","frstbuydate","regdate", "recuserid"};
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat ("yyyyMMddhhmmss");
        String excelName = String.format("%s_%s.xlsx", "전체회원목록", sf.format(timestamp));
        int[] columnSize = {14*256, 14*256, 14*256, 14*256, 18*256, 30*256, 14*256, 14*256, 14*256, 14*256, 14*256, 14*256, 25*256, 25*256, 14*256, 14*256, 14*256};
        return makeExcelFile(header, column, columnSize, excelList, excelName);
    }

    @RequestMapping("/dormancy/list")
    public Response memberDormancyList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectDormancyMemberList(params));
    }

    @RequestMapping("/sleep/restore")
    public Response memberRestore(@RequestBody SOMap params) throws Exception {
        return new Response(commonService.memberSleepRestore(params));
    }

    @RequestMapping("/resign/restore")
    public Response memberResignRestore(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.memberResignRestore(params));
    }

    @RequestMapping("/resign/list")
    public Response memberResignList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectResignMemberList(params));
    }

    @RequestMapping("/sleep/resign")
    public Response memberSleepResign(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.memberSleepResign(params));
    }

    @RequestMapping("/update/blackMemo")
    public Response memberUpdateBlackAndMemo(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.updateBlackAndMemo(params));
    }

    @RequestMapping("/update/withdrawMemo")
    public Response memberResign(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.memberResign(params));
    }

    @RequestMapping("/detail")
    public Response selectMemberDetail(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();

        //1:1문의 리스트 3개만 가져오기 위한 파라미터
        params.put("simple", 3);

        result.put("info", adminMemberService.selectMemberDetail(params));
        result.put("inquiry", adminMemberService.selectMyInquiryList(params));
        result.put("cmoneyinfo", adminMemberService.selectMemberCmoney(params));
        result.put("couponinfo", adminMemberService.selectMyCouponInfo(params));
        result.put("orderlist", adminMemberService.selectMemberOrder(params));

        return new Response(result);
    }

    @RequestMapping("/black/update")
    public Response memberUpdateBlack(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.updateBlack(params));
    }

    @RequestMapping("/info")
    public Response selectMemberInfoForEditAndSimple(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("info", adminMemberService.selectMemberDetail(params));
        return new Response(result);
    }

    @RequestMapping("/info/update")
    public Response memberInfoUpdate(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.updateMemberInfo(params));
    }

    @RequestMapping("/init/password")
    public Response memberInitPassword(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.updateMemberPasswordTemp(params));
    }

    @RequestMapping("/memo/list")
    public Response selectMemberMemoList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectMemberMemoList(params));
    }

    @RequestMapping("/memo/insert")
    public Response insertMemberMemo(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.insertMemberMemo(params));
    }

    @RequestMapping("/memo/delete")
    public Response deleteMemberMemo(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.deleteMemberMemo(params));
    }

    @RequestMapping("/reserve/list")
    public Response memberReserveList(@RequestBody SOMap params) throws Exception {

//        SOMap result = adminReserveService.selectReserveList(params);
//        SOMap cMoneyInfo = adminMemberService.getMemberCMoneyInfo(params);
//
//        result.put("pay", (cMoneyInfo != null) ? cMoneyInfo.get("pay") : 0);
//        result.put("used", (cMoneyInfo != null) ? cMoneyInfo.get("used") : 0);
//        result.put("pos", (cMoneyInfo != null) ? cMoneyInfo.get("pos") : 0);
//        result.put("extinct", (cMoneyInfo != null) ? cMoneyInfo.get("extinct") : 0);
//        result.put("expected", (cMoneyInfo != null) ? cMoneyInfo.get("expected") : 0);
//        result.put("tomorrow", (cMoneyInfo != null) ? cMoneyInfo.get("tomorrow") : 0);

        return new Response(adminMemberService.selectMemberReserveInfo(params));
    }

    /**
     * 적립금 목록 조회(엑셀용)
     *
     * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/reserve/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> memberReserveListForExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminMemberService.selectMemberReserveListForExcel(params);

        for (int i=0; i<excelList.size(); i++) {
            excelList.get(i).put("no", i+1);
        }

        String[] header = {"No", "포인트", "자동/수동", "종류", "상세구분", "수동지급/차감 사유", "주문번호", "적립/차감/소멸일자"};
        String[] column = {"no", "point", "isauto", "typekindname", "typename", "reason", "ordercode", "regdate"};
        int[] columnSize = {4*256, 10*256, 10*256, 10*256, 20*256, 50*256, 15*256, 15*256};

        return makeExcelFile(header, column, columnSize, excelList, "적립금_적립/차감/소멸내역.xlsx");
    }

    @RequestMapping("/epoint/list")
    public Response memberEPointList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectMemberEPointInfo(params));
    }

    /**
     * 적립금 목록 조회(엑셀용)
     *
     * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/epoint/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> memberEPointListForExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> excelList = adminMemberService.selectMemberEPointListForExcel(params);

        for (int i=0; i<excelList.size(); i++) {
            excelList.get(i).put("no", i+1);
        }

        String[] header = {"No", "포인트", "자동/수동", "종류", "상세구분", "수동지급사유", "주문번호", "적립/차감/소멸일자"};
        String[] column = {"no", "point", "isauto", "typekindname", "typename", "reason", "ordercode", "regdate"};
        int[] columnSize = {4*256, 10*256, 10*256, 10*256, 20*256, 50*256, 15*256, 15*256};

        return makeExcelFile(header, column, columnSize, excelList, "D포인트_적립/차감/소멸내역.xlsx");
    }

    @RequestMapping(value="/wish/list")
    public Response selectMemberWishList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectMemberWishList(params));
    }

    @RequestMapping(value="/cart/list")
    public Response selectMemberCartList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectMemberCartList(params));
    }

    @RequestMapping(value="/coupon/list")
    public Response selectMemberCouponList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectMemberCouponList(params));
    }

    @RequestMapping(value="/review/list")
    public Response selectMemberReviewList(@RequestBody SOMap params) throws Exception {
        return new Response(adminMemberService.selectMemberReviewList(params));
    }
}
