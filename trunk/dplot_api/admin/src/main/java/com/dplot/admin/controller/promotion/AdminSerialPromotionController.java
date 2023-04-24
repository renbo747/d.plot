package com.dplot.admin.controller.promotion;

import com.dplot.admin.service.promotion.AdminSerialPromotionService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.controller.ExcelDownController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 시리얼프로모션 Controller
 * @fileName : AdminCerealPromotionController.java
 * @date : 2022-01-07
 *
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-07	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RequestMapping("/admin/promotion/promotion/serial")
@RestController
public class AdminSerialPromotionController extends ExcelDownController {
    private static final Logger logger = LoggerFactory.getLogger(AdminSerialPromotionController.class);

    @Autowired
    AdminSerialPromotionService adminSerialPromotionService;

    /**
     * 리스트 검색
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminSerialPromotionService.selectSerialPromotionList(params));
    }

    /**
     * 상세 검색
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminSerialPromotionService.selectSerialPromotionDetail(params));
    }

    /**
     * S/N 발급 내역 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail/snlist", method = RequestMethod.POST)
    public Response detailSerialNoList(@RequestBody SOMap params) throws Exception {
        return new Response(adminSerialPromotionService.selectSerialNoList(params));
    }

    /**
     * 저장
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminSerialPromotionService.insertSerialPromotion(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 업데이트
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminSerialPromotionService.updateSerialPromotion(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }

    /**
     * 시리얼 번호 중복 체크 ( 중복됐는지 체크 )
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check/dup", method = RequestMethod.POST)
    public Response checkDuplicationSerialNo(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            result = new Response(Status.OK, adminSerialPromotionService.isAdminCheckDuplicationSerialNo(params));
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL, e.getMessage());
        }

        return result;
    }


    /**
     * 중복된 시리얼 번호 조회 ( 중복된 시리얼 번호 조회 )
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check/dup/serial", method = RequestMethod.POST)
    public Response checkDuplicationSerialNoXlsx(@RequestBody SOMap params) throws Exception {
        return new Response(adminSerialPromotionService.selectDuplicationSerialNoList(params));
    }

//    /**
//     * 시리얼프로모션 저장 가능 여부 판별
//     *
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/check/save", method = RequestMethod.POST)
//    public Response checkSave(@RequestBody SOMap params) throws Exception {
//        return new Response(adminSerialPromotionService.isAdminCanSaveCheck(params));
//    }

    /**
     * 엑셀 다운로드 - 시리얼 프로모션 리스트
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/list", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> listExcelDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = params.getArrayList("list");

        // 헤더 이름
        String[] header = {
                "관리제목", "혜택구분", "S/N 생성방법", "S/N 발급기준",
                "등록", "사용가능", "지급혜택", "소멸일자(D포인트)",
                "중복사용여부(D포인트)", "사용여부", "시작일자", "종료일자",
                "등록자", "등록일자", "진행상태"
        };

        // 컬럼 이름
        String[] column = {
                "title", "srptype", "srctype", "isequalserial",
                "ncount", "availablecount", "srppoint", "epoedday",
                "isepointdup", "istrash", "srpstday", "srpedday",
                "regname", "regdate", "bptype"
        };

        // 컬럼 너비 지정
        int[] columnSize = {
                (35 * 256), (15 * 256), (20 * 256), (35 * 256),
                (17 * 256), (17 * 256), (30 * 256), (20 * 256),
                (15 * 256), (20 * 256), (20 * 256)
        };

        return makeExcelFile(header, column, columnSize, list, "시리얼프로모션.xlsx");
    }

    /**
     * 엑셀 다운로드 - S/N 등록 리스트
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/snlist", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> SNListExcelDownload(@RequestBody SOMap params) throws Exception {

        List<Map<String, Object>> list = (List<Map<String, Object>>) adminSerialPromotionService.serialNoExcelDownload(params).get("list");

        // 헤더 이름
        String[] header = {
                "시리얼번호", "아이디", "이름", "유형",
                "등급", "S/N 등록일자"
        };

        // 컬럼 이름
        String[] column = {
                "serialno", "userid", "name", "dadamembertype",
                "memlvtype", "regdate"
        };

        // 컬럼 너비 지정
        int[] columnSize = {
                (40 * 256),(20 * 256),(20 * 256),(20 * 256)
                ,(20 * 256),(20 * 256)
        };

        return makeExcelFile(header, column, columnSize, list, "시리얼번호_등록_리스트.xlsx");
    }
}
