package com.dplot.admin.controller.adjust;

import com.dplot.admin.service.adjust.AdminPartnersAdjustService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;
import com.dplot.util.DateTimeUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping(value = {"/admin/adjust", "/partners/adjust"})
public class AdminPartnersAdjustController extends ExcelDownController {

    @Autowired
    private AdminPartnersAdjustService adminPartnersAdjustService;

    @Resource(name="propertiesFactory")
    private Properties prop;

    @RequestMapping(value="/estimate/list", method = RequestMethod.POST)
    public Response adminPartnersAdjustEstimateList(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.selectAdjustList(param));
    }

    @RequestMapping(value="/state/update", method = RequestMethod.POST)
    public Response adminPartnersAdjustStateUpdate(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.updateCalcStatus(param));
    }

    @RequestMapping(value="/recalculate", method = RequestMethod.POST)
    public Response adminPartnersAdjustReCalculate(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.reCalculate(param));
    }

    @RequestMapping(value="/cs/list", method = RequestMethod.POST)
    public Response adjustCsList(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.selectAdjustCsList(param));
    }

    @RequestMapping(value="/detail/list", method = RequestMethod.POST)
    public Response adjustDetailList(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.selectAdjustDetailList(param));
    }

    @RequestMapping(value="/payment/complete", method = RequestMethod.POST)
    public Response adjustPaymentComplete(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.updatePaymentDate(param));
    }

    @RequestMapping(value="/pay/compare", method = RequestMethod.POST)
    public Response adjustPayCompare(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.selectPayCompareList(param));
    }

    @RequestMapping("/calc/excel")
    public ResponseEntity<ByteArrayResource> calcExcelDownload(@RequestBody SOMap params, HttpServletRequest request) throws Exception {
        String excelPath = prop.getProperty("base.excel.template.path");
        String fileName = "CalcResult.xls";
        String filePath = String.format("%s%s", excelPath, fileName);

        try{
            List<SOMap> list = adminPartnersAdjustService.calcExcelDownList(params);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders httpHeader = new HttpHeaders();

            String downFileName = String.format("%s_%s.xls", "세금계산서", DateTimeUtil.getNowFormatStr("yyyyMMddHHmmss"));
            downFileName = URLEncoder.encode(downFileName, String.valueOf(StandardCharsets.UTF_8));

            httpHeader.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + downFileName);
            httpHeader.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            FileInputStream fileInputStream = new FileInputStream(filePath);

            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = sheet.getRow(6);

            int listCnt = 7;
            for(SOMap map : list){
                HSSFRow newRow = sheet.createRow(listCnt);

                int cellCnt = 0;
                for(Cell cell : row){
                    HSSFCell newCell = newRow.createCell(cellCnt);
                    if(cell.getCellStyle() != null ) {
                        newCell.setCellStyle(cell.getCellStyle());
                    }
                    newCell.setCellType(cell.getCellType());
                    cellCnt++;
                }
                //전자계산서 종류 - 01 : 일반, 02 : 영세율
                newRow.getCell(0).setCellValue("01");
                //작성일자
                newRow.getCell(1).setCellValue(map.getStr("calcmonth"));
                //파트너사 사업자 번호
                newRow.getCell(2).setCellValue(map.getStr("bizno"));
                //파트너사 이름
                newRow.getCell(4).setCellValue(map.getStr("name"));
                //파트너사 대표자 이름
                newRow.getCell(5).setCellValue(map.getStr("reprename"));
                //세금담당자 이메일 주소
                newRow.getCell(9).setCellValue(map.getStr("email"));
                //공급가액
                newRow.getCell(11).setCellValue(map.getStr("applyamt"));
                //세액
                newRow.getCell(12).setCellValue(map.getStr("vatamt"));
                //일자1 말일
                newRow.getCell(14).setCellValue(map.getStr("calcdate"));
                //품목1 (품목명)
                newRow.getCell(15).setCellValue(map.getStr("productname"));
                //공급가액
                newRow.getCell(19).setCellValue(map.getStr("applyamt"));
                //세액
                newRow.getCell(20).setCellValue(map.getStr("vatamt"));
                //영수(01), 청구(02)
                newRow.getCell(50).setCellValue("01");
                listCnt++;
            }

            //기존 양식 참조용 ROW 삭제
            int ls=sheet.getLastRowNum();
            sheet.shiftRows(7, ls, -1);
            Row removeRow=sheet.getRow(ls);
            sheet.removeRow(removeRow);

            workbook.write(stream);
            stream.close();

            adminPartnersAdjustService.updateCalcStatus(params);

            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    httpHeader, HttpStatus.CREATED);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value="/cfm/update", method = RequestMethod.POST)
    public Response adminPartnersAdjustCfmUpdate(@RequestBody SOMap param) throws Exception {
        return new Response(adminPartnersAdjustService.updateCalcCfm(param));
    }

}
