package com.dplot.admin.controller.goods;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.goods.GoodsUploadService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.controller.ExcelDownController;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;

/**
 * @discription	: 상품관리 Controller
 * @fileName	: GoodsUploadController.java
 * @author		: JSK
 * @date		: 2021.12.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.08	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = {"/admin/goods/upload", "/partners/goods/upload"})
public class GoodsUploadController extends ExcelDownController{
    private static final Logger logger = LoggerFactory.getLogger(GoodsUploadController.class);

    @Autowired
    private GoodsUploadService goodsUploadService;
    
    @Resource(name="propertiesFactory")
    private Properties prop;

    /**
     * 상품일괄업로드 코드조회 엑셀 다운로드
     * @param params
     * @param fileParams
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/codelist", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> selectCodeListForExcel(@RequestBody SOMap params) throws Exception {
    	List<Map<String, Object>> list = goodsUploadService.selectCodeListForExcel(params);
    	String excelFileName = "코드목록조회.xlsx";
    	List<String> headerList = new ArrayList<String>();
    	List<String> columnList = new ArrayList<String>();
    	List<Integer> columnSizeList = new ArrayList<Integer>();
    	
    	switch (params.getStr("type")) {
			case "cate":
		    	excelFileName = "카테고리_코드목록.xlsx";
		    	headerList = Arrays.asList("카테고리코드", "대분류", "중분류", "소분류", "세분류");
		    	columnList = Arrays.asList("cateidx", "depth1name", "depth2name", "depth3name", "depth4name");
		    	columnSizeList = Arrays.asList(15*256, 30*256, 30*256, 30*256, 30*256);
				break;
			case "delivtemp":
		    	excelFileName = "배송템플릿_코드목록.xlsx";
		    	if (Util.isEmpty(params.get("dealerno"))) {
			    	headerList = Arrays.asList("관리코드", "파트너사코드", "파트너사명", "배송정보명", "배송방법", "택배사", "배송비", "배송가능지역", "교환배송비(편도)", "반품배송비(편도)", "배송유형");
			    	columnList = Arrays.asList("delividx", "dealercode", "dealername", "delivbname", "delivtypename", "logistypename", "delivfare", "nationdelivconts", "exowfare", "rfowfare", "iscombdeliv");
			    	columnSizeList = Arrays.asList(15*256, 20*256, 30*256, 40*256, 25*256, 25*256, 20*256, 25*256, 20*256, 20*256, 20*256);
		    	} else {
			    	headerList = Arrays.asList("관리코드", "배송정보명", "배송방법", "택배사", "배송비", "배송가능지역", "교환배송비(편도)", "반품배송비(편도)", "배송유형");
			    	columnList = Arrays.asList("delividx", "delivbname", "delivtypename", "logistypename", "delivfare", "nationdelivconts", "exowfare", "rfowfare", "iscombdeliv");
			    	columnSizeList = Arrays.asList(15*256, 40*256, 25*256, 25*256, 20*256, 25*256, 20*256, 20*256, 20*256);
		    	}
				break;
			case "notify":
		    	excelFileName = "상품정보고시_코드목록.xlsx";
		    	headerList = Arrays.asList("관리코드", "상품정보고시명");
		    	columnList = Arrays.asList("idx", "title");
		    	columnSizeList = Arrays.asList(15*256, 50*256);
				break;
			case "brand":
		    	excelFileName = "브랜드_코드목록.xlsx";
		    	headerList = Arrays.asList("브랜드코드", "카테고리", "한글명", "영문명", "헤드카피");
		    	columnList = Arrays.asList("brcode", "category", "name", "enname", "headcopy");
		    	columnSizeList = Arrays.asList(20*256, 30*256, 30*256, 40*256, 60*256);
				break;
			case "md":
		    	excelFileName = "담당MD_목록.xlsx";
		    	if (Util.isEmpty(params.get("dealerno"))) {
			    	headerList = Arrays.asList("MD정보 IDX", "MD사번", "사원이름", "MD코드", "MD명", "이메일", "직매입여부");
			    	columnList = Arrays.asList("mdifidx", "mdsabun", "empname", "mdcode", "mdname", "mdemail", "isdp");
			    	columnSizeList = Arrays.asList(10*256, 20*256, 20*256, 20*256, 30*256, 50*256, 15*256);
		    	} else {
			    	headerList = Arrays.asList("MD정보 IDX", "MD사번", "사원이름", "MD코드", "MD명", "이메일");
			    	columnList = Arrays.asList("mdifidx", "mdsabun", "empname", "mdcode", "mdname", "mdemail");
			    	columnSizeList = Arrays.asList(10*256, 20*256, 20*256, 20*256, 30*256, 50*256);
		    	}
				break;
			case "color":
		    	excelFileName = "검색컬러코드_목록.xlsx";
		    	headerList = Arrays.asList("컬러코드", "색상");
		    	columnList = Arrays.asList("cmcode", "codename");
		    	columnSizeList = Arrays.asList(20*256, 40*256);
				break;
	
			default:
				break;
		}
    	
    	String[] header = (String[]) headerList.toArray();
    	String[] column = (String[]) columnList.toArray();
    	int[] columnSize = columnSizeList.stream().mapToInt(Integer::intValue).toArray();
    	
		return makeExcelFile(header, column, columnSize, list, excelFileName);
    }

    /**
     * 상품일괄업로드 목록조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public Response goodsExcelList(@RequestBody SOMap params) throws Exception {
        return new Response(goodsUploadService.selectGoodsExcelList(params));
    }

    /**
     * 상품일괄업로드 엑셀 업로드
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/excel", method = RequestMethod.POST)
    public Response goodsExcelUpload(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
        return new Response(goodsUploadService.uploadGoodsExcel(params, uploadFile));
    }
    
    /**
     * 상품 일괄업로드 - 이미지 업로드
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/imagezip", method = RequestMethod.POST)
    public Response goodsUploadImageZip(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		System.out.println("##################################################");
		long after = 0;
		long mid = 0;
		long before = System.currentTimeMillis();
		System.out.println("Before start :: " + before);
		System.out.println("##################################################");
       	Response result = new Response();
    	String imagepath = prop.getProperty("base.temp.template.path");
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		
		String dirStr = imagepath + params.getInt("excelidx") + "_" + DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR14);
		params.put("dirstr", dirStr);
		
		ZipInputStream zis = null;
		try {
	        File rootdir = new File(imagepath);
			// 최초 업로드시 업로드 저장용 폴더를 생성
			if(!rootdir.exists()) {
				rootdir.mkdir();
			}
			
			MultipartFile imgFile = uploadFile.get("imgfile");
			
			zis = new ZipInputStream(imgFile.getInputStream());
			ZipEntry ze = zis.getNextEntry();
			while(ze != null) {
				File dir = new File(dirStr);
				// 압축해제용 폴더를 생성
				if(!dir.exists()) {
					dir.mkdir();
				}
				String fileName = ze.getName();
				File file = new File(dirStr, fileName);
				
				try (FileOutputStream fos = new FileOutputStream(file)) {
					byte[] buffer = new byte[1024];
					int size = 0;
					while((size = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, size);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();

			System.out.println("##################################################");
			mid = System.currentTimeMillis();
			System.out.println("ZIP decompress :: " + (mid - before)/1000);
			System.out.println("##################################################");
			try {
				// 이미지 저장
				goodsUploadService.uploadGoodsImage(params);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("이미지 업로드중 오류가 발생했습니다. 관리자에게 문의바랍니다.");
		} finally {
			if(zis != null) {
				try{
					zis.close();
				} catch (IOException e) {}
			}
			// 폴더, 파일 삭제
			File folder = new File(dirStr);
			FileUtils.deleteDirectory(folder);

			System.out.println("##################################################");
			after = System.currentTimeMillis();
			System.out.println("After end :: " + (after - mid)/1000);
			System.out.println("##################################################");
		}
		
        return result;
    }
}
