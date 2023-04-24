package com.dplot.admin.service.goods;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.HistoryService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BrandMapper;
import com.dplot.mapper.CategoryMapper;
import com.dplot.mapper.ChargemdMapper;
import com.dplot.mapper.CommonCodeMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.DelivTemplateMapper;
import com.dplot.mapper.GoodsAdditionMapper;
import com.dplot.mapper.GoodsApprMapper;
import com.dplot.mapper.GoodsCategoryMapper;
import com.dplot.mapper.GoodsContentMapper;
import com.dplot.mapper.GoodsExcelMapMapper;
import com.dplot.mapper.GoodsExcelMapper;
import com.dplot.mapper.GoodsGrpMapper;
import com.dplot.mapper.GoodsKccertMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsNotifyMapper;
import com.dplot.mapper.GoodsOptionDetailMapper;
import com.dplot.mapper.GoodsOptionMapper;
import com.dplot.mapper.IFLogMapper;
import com.dplot.mapper.MemberGoodsMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.NotifyTplItemMapper;
import com.dplot.mapper.NotifyTplMapper;
import com.dplot.mapper.OptionErpMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.JsonUtil;
import com.dplot.util.Util;

/**
 * @discription	: 상품 일괄등록 ServiceImpl
 * @fileName	: GoodsUploadServiceImpl.java
 * @author		: JSK
 * @date		: 2022.07.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.07.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class GoodsUploadServiceImpl extends MallBaseService implements GoodsUploadService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsUploadServiceImpl.class);
    
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsApprMapper goodsApprMapper;
	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;
	@Autowired
	private GoodsContentMapper goodsContentMapper;
	@Autowired
	private GoodsGrpMapper goodsGrpMapper;
	@Autowired
	private GoodsAdditionMapper goodsAdditionMapper;
	@Autowired
	private GoodsNotifyMapper goodsNotifyMapper;
	@Autowired
	private GoodsKccertMapper goodsKccertMapper;
	@Autowired
	private MemberGoodsMapper memberGoodsMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private DelivTemplateMapper delivTemplateMapper;
	@Autowired
	private NotifyTplMapper notifyTplMapper;
	@Autowired
	private NotifyTplItemMapper notifyTplItemMapper;
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private ChargemdMapper chargemdMapper;
	@Autowired
	private CommonCodeMapper commonCodeMapper;
	@Autowired
	private GoodsOptionMapper goodsOptionMapper;
	@Autowired
	private GoodsOptionDetailMapper goodsOptionDetailMapper;
	@Autowired
	private OptionErpMapper optionErpMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private GoodsExcelMapper goodsExcelMapper;
	@Autowired
	private GoodsExcelMapMapper goodsExcelMapMapper;
	@Autowired
	private DealerMapper dealerMapper;
	@Autowired
	private IFLogMapper ifLogMapper;

	@Autowired
	private CommonService commonService;
	@Autowired
	private FileService fileService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ERPService erpService;
	
    @Resource(name="propertiesFactory")
    private Properties prop;
	
	/**
	 * 상품 일괄업로드 코드조회
	 * @param params
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> selectCodeListForExcel(SOMap params) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		params.put("siteid", cs.getStr("siteid"));
		
		switch (params.getStr("type")) {
			case "cate":
				resultList = categoryMapper.selectCategorynameListForExcel(params);
				break;
			case "delivtemp":
				params.put("istrash", "F");
				resultList = delivTemplateMapper.selectDelivTemplateListForExcel(params);
				break;
			case "notify":
				resultList = notifyTplMapper.selectNotifyTplListForExcel(params);
				break;
			case "brand":
				resultList = brandMapper.selectBrandListForExcel(params);
				break;
			case "md":
				if (!Util.isEmpty(params.getStr("dealerno"))) {
					params.put("isdp", "F");
				}
				resultList = chargemdMapper.selectChargemdListForExcel(params);
				break;
			case "color":
				params.put("cmclass", "COLORTYPE");
				params.put("istrash", "F");
				resultList = commonCodeMapper.selectCodeExcelList(params);
				break;
	
			default:
				break;
		}
		return resultList;
	}
	
	/**
	 * 상품일괄업로드 목록조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectGoodsExcelList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
		params.put("siteid", cs.getStr("siteid"));
		if (Util.isEmpty(params.getStr("dealerno"))) {
			params.put("authuserid", getAdminId());
		} else {
			params.put("authuserid", getDealerId());
		}
        result.put("list", goodsExcelMapper.selectGoodsExcelList(params));
        result.put("totalcnt", goodsExcelMapper.selectGoodsExcelListCount(params));
		return result;
	}
	
	/**
	 * 상품일괄업로드 엑셀 업로드
     * @param params
     * @param uploadFiles
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap uploadGoodsExcel(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		System.out.println("##################################################");
		long beforeTime = System.currentTimeMillis();
		System.out.println("excel uploade beforeTime :: " + beforeTime);
		System.out.println("##################################################");
		
		SOMap result = new SOMap();
		int succCnt = 0;
		int goodsCnt = 0;
		List<SOMap> failList = new ArrayList<SOMap>();
		List<SOMap> imgNameList = new ArrayList<SOMap>();
		
		try {
			uploadFiles.remove("params");
            if (uploadFiles == null) {
                throw new CustomException("파일이 존재하지 않습니다.");
            }
			MultipartFile excelFile = uploadFiles.get("excel");
			params.put("excelfilename", excelFile.getOriginalFilename());
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
			
			// 공통코드 세팅
            SOMap codeParams = new SOMap();
            String[] cmclassArr = {"MUAPPCHTYPE", "MUMEMLVTYPE", "MUMEMBERTYPE"};
            codeParams.put("cmclass", Arrays.asList(cmclassArr));
            SOMap commonCodeListMap = commonService.getCodeMapList(codeParams);
            SOMap commonCodeAllStrMap = new SOMap();
            for(String codeKey : commonCodeListMap.keySet()) {
            	List<SOMap> codeList = (List<SOMap>) commonCodeListMap.get(codeKey);
            	commonCodeAllStrMap.put(codeKey, codeList.stream().map(m->m.getStr("cmcode")).collect(Collectors.joining(",")));
    		}
            params.put("commonCodeAllStrMap", commonCodeAllStrMap);

			// 엑셀파일 세팅
        	XSSFSheet goodsSheet = workbook.getSheetAt(0);
			XSSFRow headerRow = goodsSheet.getRow(0);
			int rowSize = goodsSheet.getPhysicalNumberOfRows();
            int headerSize = headerRow != null? headerRow.getPhysicalNumberOfCells()-1 : 0;
			
    		// 관리자 직매입/위탁상품
            if(Util.isEmpty(params.get("dealerno"))) {
    			params.put("authuserid", getAdminId());
    			params.put("authuserno", getAdminNo());
    			
    			if (headerRow != null) {
    	        	DataFormatter formatter = new DataFormatter();
	                // 위탁상품
    				if (Util.equal(formatter.formatCellValue(headerRow.getCell(1)).replaceAll(" ", ""), "파트너사코드")) {
    	                if (headerSize != 57) {	// 관리자 위탁상품 총 컬럼 57개
    	        			throw new CustomException("엑셀양식이 올바르지 않습니다.");
    	                }
    	                // Body 세팅
	                    for (int rowidx = 4; rowidx < rowSize; rowidx++) {
	                    	SOMap rowResult = new SOMap();
	                    	try {
		                    	XSSFRow row = goodsSheet.getRow(rowidx);
		                    	rowResult = this._saveAdminExcelCoGoodsRow(row, params);
		                    	succCnt += rowResult.getInt("succcnt");
		                    	if (rowResult.getInt("succcnt") > 0) {
			                    	imgNameList.add(rowResult.getSOMap("rowimgnamemap"));
			                    	goodsCnt += 1;
		                    	}
	                    	} catch (Exception e){
	                			SOMap failMap = new SOMap();
	                			failMap.put("failmsg", e.getMessage());
	                			failMap.put("failrow", rowidx+1);
		                    	failList.add(failMap);
		                    	goodsCnt += 1;
	                    	}
	                    }
    				}
    				
    				// 직매입상품
    				else {
    	                if (headerSize != 57) {	// 관리자 직매입상품 총 컬럼 57개
    	        			throw new CustomException("엑셀양식이 올바르지 않습니다.");
    	                }
    	                // Body 세팅
	                    for (int rowidx = 4; rowidx < rowSize; rowidx++) {
	                    	SOMap rowResult = new SOMap();
	                    	try {
		                    	XSSFRow row = goodsSheet.getRow(rowidx);
		                    	rowResult = this._saveAdminExcelPbGoodsRow(row, params);
		                    	succCnt += rowResult.getInt("succcnt");
		                    	if (rowResult.getInt("succcnt") > 0) {
			                    	imgNameList.add(rowResult.getSOMap("rowimgnamemap"));
			                    	goodsCnt += 1;
		                    	}
	                    	} catch (Exception e){
	                			SOMap failMap = new SOMap();
	                			failMap.put("failmsg", e.getMessage());
	                			failMap.put("failrow", rowidx+1);
		                    	failList.add(failMap);
		                    	goodsCnt += 1;
	                    	}
	                    }
    				}
    			}
            }
            // 파트너사 위탁상품
            else {
    			if (headerRow != null) {
	                if (headerSize != 47) {	// 파트너사 상품 총 컬럼 47개
	        			throw new CustomException("엑셀양식이 올바르지 않습니다.");
	                }
	        		params.put("authuserid", getDealerId());
	        		params.put("authuserno", getDealerNo());
	                // Body 세팅
                    for (int rowidx = 4; rowidx < rowSize; rowidx++) {
                    	SOMap rowResult = new SOMap();
                    	try {
	                    	XSSFRow row = goodsSheet.getRow(rowidx);
	                    	rowResult = this._savePatnerExcelGoodsRow(row, params);
	                    	succCnt += rowResult.getInt("succcnt");
	                    	if (rowResult.getInt("succcnt") > 0) {
		                    	imgNameList.add(rowResult.getSOMap("rowimgnamemap"));
		                    	goodsCnt += 1;
	                    	}
                    	} catch (Exception e){
                			SOMap failMap = new SOMap();
                			failMap.put("failmsg", e.getMessage());
                			failMap.put("failrow", rowidx+1);
	                    	failList.add(failMap);
	                    	goodsCnt += 1;
                    	}
                    }
    			}
            }
            
            if (goodsCnt == 0) {
            	throw new CustomException("입력한 상품이 존재하지 않습니다.");
            }

            // 결과 세팅
            result.put("siteid", params.get("siteid"));
            result.put("authuserid", params.get("authuserid"));
            result.put("exfilename", params.get("excelfilename"));
            result.put("succcnt", succCnt);
            result.put("failcnt", failList.size());
            result.put("failrow", failList.stream().map(m->m.getStr("failrow")).collect(Collectors.joining(", ")));
            result.put("imgnamelist", imgNameList);
            
			// 상품엑셀업로드 저장
			goodsExcelMapper.insertGoodsExcel(result);
            
            // 상품엑셀 업로드 Map 저장
            if (result.getInt("succcnt") > 0) {
                goodsExcelMapMapper.insertGoodsExcelMap(result);
            }
        } catch(CustomException e) {
			logger.error(e.getMessage());
            throw e;
        } catch(Exception e) {
			logger.error(e.getMessage());
            throw new CustomException("상품 엑셀파일 업로드시 오류가 발생하였습니다.");
        }

		System.out.println("##################################################");
		long afterTime = System.currentTimeMillis();
		System.out.println("excel uploade afterTime :: " + afterTime);
		System.out.println((afterTime - beforeTime)/1000);
		System.out.println("##################################################");
		return result;
	}

	/**
	 * 직매입 상품업로드
	 * @param workSheet
	 * @return SOMap
	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap _saveAdminExcelPbGoodsRow(XSSFRow row, SOMap params) throws Exception {
		SOMap result = new SOMap();
		int succCnt = 0;

		try {
			SOMap commonCodeAllStrMap = params.getSOMap("commoncodeallstrmap");
        	DataFormatter formatter = new DataFormatter();
        	SOMap goodsMap = new SOMap();
			SOMap rowImgNameMap = new SOMap();

			if (row != null && !Util.isEmpty(formatter.formatCellValue(row.getCell(3)).replaceAll(" ", ""))) {
				goodsMap.put("siteid", params.getStr("siteid"));
				goodsMap.put("authuserid", params.get("authuserid"));
				goodsMap.put("authuserno", params.get("authuserno"));
				goodsMap.put("goodscode", goodsMapper.selectGoodsCode());
				goodsMap.put("ispbgoods", "T");
				goodsMap.put("goodsselltype", CMConst.GOODS_SELL_TYPE_WAIT);
				goodsMap.put("goodsapprtype", CMConst.GOODS_STATUS_TEMP);
				goodsMap.put("isdeal", "F");
				goodsMap.put("istempsave", "T");
				
				// 상품카테고리 저장
				List<SOMap> goodsCategoryList = new ArrayList<SOMap>();
                String repreCateIdx = formatter.formatCellValue(row.getCell(1)).trim();
                if (Util.isEmpty(repreCateIdx)) {
					throw new CustomException("대표카테고리는 필수 입력 항목입니다.");
                }
                String addCateIdxStr = formatter.formatCellValue(row.getCell(2)).replaceAll(" ", "");
                if (addCateIdxStr.contains(repreCateIdx)) {
					throw new CustomException("대표카테고리와 중복된 추가카테고리가 존재합니다.");
                }
                SOMap categoryMap = new SOMap();
                categoryMap.put("cateidx", repreCateIdx);
                categoryMap.put("isrepre", "T");
                goodsCategoryList.add(categoryMap);
                if (!Util.isEmpty(addCateIdxStr)) {
                    List<String> addCateIdxList = Arrays.asList(addCateIdxStr.split(","));
                    addCateIdxList = addCateIdxList.stream().distinct().collect(Collectors.toList());
                    for (String cateidx : addCateIdxList) {
                    	SOMap addCategoryMap = new SOMap();
                        addCategoryMap.put("cateidx", cateidx);
                        addCategoryMap.put("isrepre", "F");
                        goodsCategoryList.add(addCategoryMap);
                    }
                }
				goodsMap.put("goodscategorylist", goodsCategoryList);
				int categoryListCnt = categoryMapper.selectCategoryCount(goodsMap);
				if (categoryListCnt != goodsCategoryList.size()) {
					throw new CustomException("유효하지 않은 카테고리가 존재합니다.");
				}
				String goodsname = formatter.formatCellValue(row.getCell(3));
				if (Util.isEmpty(goodsname.replaceAll(" ", ""))) {
					throw new CustomException("상품명은 필수 입력 항목입니다.");
				} else if (goodsname.length() > 50) {
					throw new CustomException("상품명이 50자 이상입니다.");
				}
				goodsMap.put("goodsname", goodsname);
				String summary = formatter.formatCellValue(row.getCell(4));
				if (summary.length() > 1000) {
					throw new CustomException("상품요약설명이 1,000자 이상입니다.");
				}
				goodsMap.put("summary", summary);
				String keyword = formatter.formatCellValue(row.getCell(5));
				if (keyword.length() > 100) {
					throw new CustomException("검색키워드가 100자 이상입니다.");
				}
				goodsMap.put("keyword", keyword);
				String brcode = formatter.formatCellValue(row.getCell(6)).trim();
				if (!Util.isEmpty(brcode)) {
					SOMap brandParams = new SOMap();
					brandParams.put("siteid", goodsMap.get("siteid"));
					brandParams.put("brcode", brcode);
					SOMap brandMap = brandMapper.selectBrandDetail(brandParams);
					if (Util.isEmptyMap(brandMap)) {
						throw new CustomException("유효하지 않은 브랜드코드 입니다.");
					}
					goodsMap.put("brandidx", brandMap.get("idx"));
				}
				String mdifidx = formatter.formatCellValue(row.getCell(7)).trim();
				if (Util.isEmpty(mdifidx)) {
					throw new CustomException("담당 MD정보 IDX는 필수 입력 항목입니다.");
				} else {
					SOMap mdParams = new SOMap();
					mdParams.put("mdifidx", mdifidx);
					List<SOMap> chargemdList= chargemdMapper.selectChargemdList(mdParams);
					if (chargemdList == null || chargemdList.size() == 0) {
						throw new CustomException("유효하지 않은  MD코드 입니다.");
					}
					goodsMap.put("mdifidx", mdifidx);
				}
				goodsMap.put("muappchtype", Util.isEmpty(formatter.formatCellValue(row.getCell(8)))? commonCodeAllStrMap.getStr("muappchtype") : formatter.formatCellValue(row.getCell(8)).replaceAll(" ", ""));
				goodsMap.put("istaxfree", Util.isEmpty(formatter.formatCellValue(row.getCell(9)))? "F" : formatter.formatCellValue(row.getCell(9)));
				String disstdate = formatter.formatCellValue(row.getCell(10))+":00";
				if (!DateTimeUtil.isValid(disstdate)) {
					throw new CustomException("전시기간(시작일)이 유효하지 않은 날짜입니다.");
				}
				String diseddate = formatter.formatCellValue(row.getCell(11))+":00";
				if (!DateTimeUtil.isValid(diseddate)) {
					throw new CustomException("전시기간(종료일)이 유효하지 않은 날짜입니다.");
				}
				if (DateTimeUtil.diffOfSeconds(disstdate, diseddate) < 0) {
					throw new CustomException("전시종료일이 전시시작일보다 이전입니다.");
				}
				goodsMap.put("disstday", DateTimeUtil.getFormatStr(disstdate, DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12));
				goodsMap.put("disedday", DateTimeUtil.getFormatStr(diseddate, DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12));
				
				String minordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(12)).trim())? "1" : formatter.formatCellValue(row.getCell(12)).trim();
				if (!this._isNumber(minordcnt)) {
					throw new CustomException("최소주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("minordcnt", minordcnt);
				String maxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(13)).trim())? "1" : formatter.formatCellValue(row.getCell(13)).trim();
				if (!this._isNumber(maxordcnt)) {
					throw new CustomException("최대주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("maxordcnt", maxordcnt);
				String daymaxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(14)).trim())? "999" : formatter.formatCellValue(row.getCell(14)).trim();
				if (!this._isNumber(daymaxordcnt)) {
					throw new CustomException("1일 최대주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("daymaxordcnt", daymaxordcnt);
				String permaxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(15)).trim())? "999" : formatter.formatCellValue(row.getCell(15)).trim();
				if (!this._isNumber(permaxordcnt)) {
					throw new CustomException("1인당 최대구매수량이 숫자가 아닙니다.");
				}
				goodsMap.put("permaxordcnt", permaxordcnt);
				goodsMap.put("isdisplay", Util.isEmpty(formatter.formatCellValue(row.getCell(16)).trim())? "T" : formatter.formatCellValue(row.getCell(16)).trim());
				goodsMap.put("isfrstsale", Util.isEmpty(formatter.formatCellValue(row.getCell(17)).trim())? "T" : formatter.formatCellValue(row.getCell(17)).trim());
				goodsMap.put("iscncappr", Util.isEmpty(formatter.formatCellValue(row.getCell(18)).trim())? "F" : formatter.formatCellValue(row.getCell(18)).trim());
				goodsMap.put("goodsdivtype", Util.isEmpty(formatter.formatCellValue(row.getCell(19)).replaceAll(" ", ""))? CMConst.GOODS_DIV_NEW : formatter.formatCellValue(row.getCell(19)).replaceAll(" ", ""));
				String selltarget = Util.isEmpty(formatter.formatCellValue(row.getCell(20)).trim())? "1" : formatter.formatCellValue(row.getCell(20)).trim();
				if ("1".equals(selltarget)) {			// 전체
					goodsMap.put("mumemlvtype", commonCodeAllStrMap.getStr("mumemlvtype"));
				} else if ("2".equals(selltarget)) {	// 등급별
					goodsMap.put("mumemlvtype", Util.isEmpty(formatter.formatCellValue(row.getCell(21)).replaceAll(" ", ""))? commonCodeAllStrMap.getStr("mumemlvtype") : formatter.formatCellValue(row.getCell(21)).replaceAll(" ", ""));
				} else if ("3".equals(selltarget)) {	// 특정회원
					String memberidStr = formatter.formatCellValue(row.getCell(22)).replaceAll(" ", "");
					if (Util.isEmpty(memberidStr)) {
						throw new CustomException("판매대상이 특정회원이면 특정회원판매는 필수입력 항목입니다.");
					} else {
						String[] memberidArr = memberidStr.split(",");
						SOMap memberParams = new SOMap();
						memberParams.put("siteid", params.get("siteid"));
						memberParams.put("useridlist", Arrays.asList(memberidArr));
						List<SOMap> memberList = memberMapper.selectAdminMemberList(memberParams);
						if (memberidArr.length != memberList.size()) {
							throw new CustomException("판매대상 특정회원에 유효하지 않은 회원 ID가 있습니다.");
						}
						goodsMap.put("usernolist", memberList.stream().map(m->m.get("userno")).distinct().collect(Collectors.toList()));
					}
				} else {
					throw new CustomException("판매대상이 올바르지 않습니다.");
				}
				goodsMap.put("mugicontype", formatter.formatCellValue(row.getCell(38)).replaceAll(" ", ""));
//				goodsMap.put("iscombdeliv", Util.isEmpty(formatter.formatCellValue(row.getCell(46)).trim())? "F" : formatter.formatCellValue(row.getCell(46)).trim());
				String delividx = formatter.formatCellValue(row.getCell(47)).trim();
				if (Util.isEmpty(delividx)) {
					throw new CustomException("배송정보는 필수입력 항목입니다.");
				} else {
					SOMap delivParams = new SOMap();
					delivParams.put("istrash", "F");
					delivParams.put("delividx", delividx);
					List<SOMap> delivTemplateList= delivTemplateMapper.selectDelivTemplateList(delivParams);
					if (delivTemplateList == null || delivTemplateList.size() == 0) {
						throw new CustomException("유효한 배송정보가 아닙니다.");
					}
					goodsMap.put("delividx", delividx);
					goodsMap.put("iscombdeliv", delivTemplateList.get(0).get("iscombdeliv"));
				}
				String notifyTplIdx = formatter.formatCellValue(row.getCell(48)).trim();
				if (Util.isEmpty(notifyTplIdx)) {
					throw new CustomException("상품정보고시는 필수입력 항목입니다.");
				}
				SOMap notifyParams = new SOMap();
				notifyParams.put("notifyTplIdx", notifyTplIdx);
				List<SOMap> goodNotifyList = notifyTplItemMapper.selectNotifyTplItemList(notifyParams);
				if (goodNotifyList == null || goodNotifyList.size() == 0) {
					throw new CustomException("유효하지 않은 상품정보고시코드 입니다.");
				}
				goodNotifyList.forEach(m -> {
					m.put("notifydata", "상세설명참고");
				});
				goodsMap.put("goodsnotifylist", goodNotifyList);
				String kcdivtype = Util.isEmpty(formatter.formatCellValue(row.getCell(49)).trim())? CMConst.KC_DIV_TYPE_NON_TARGET : formatter.formatCellValue(row.getCell(49)).trim();
				goodsMap.put("kcdivtype", kcdivtype);
				if (Util.equal(kcdivtype, CMConst.KC_DIV_TYPE_TARGET)) {
					String certNumStr = formatter.formatCellValue(row.getCell(50)).replaceAll(" ", "");
					if (Util.isEmpty(certNumStr)) {
						throw new CustomException("KC 인증대상인 경우 KC인증번호는 필수입력 항목입니다.");
					}
					List<String> certnumList = Arrays.asList(certNumStr.split(","));
					List<SOMap> kccertlist = new ArrayList<SOMap>();
					certnumList.stream().distinct().forEach(certnum -> {
						SOMap kccert = new SOMap();
						kccert.put("kccertno", certnum);
						kccert.put("iscert", "F");
						kccertlist.add(kccert);
					});
					goodsMap.put("kccertlist", kccertlist);
				}

				String aswarmonth = Util.isEmpty(formatter.formatCellValue(row.getCell(51)).trim())? "0" : formatter.formatCellValue(row.getCell(51)).trim();
				if (!this._isNumber(aswarmonth)) {
					throw new CustomException("AS보증기간이 숫자가 아닙니다.");
				}
				goodsMap.put("aswarmonth", aswarmonth);
				String asnotice = formatter.formatCellValue(row.getCell(52));
				if (asnotice.length() > 1000) {
					throw new CustomException("AS 안내문구가 1,000자 이상입니다.");
				}
				goodsMap.put("asnotice", asnotice);
				goodsMap.put("mumembertype", Util.isEmpty(formatter.formatCellValue(row.getCell(53)).replaceAll(" ", ""))? commonCodeAllStrMap.getStr("mumembertype") : formatter.formatCellValue(row.getCell(53)).replaceAll(" ", ""));
				goodsMap.put("isopenreview", Util.isEmpty(formatter.formatCellValue(row.getCell(54)).trim())? "T" : formatter.formatCellValue(row.getCell(54)).trim());
				goodsMap.put("isepif", Util.isEmpty(formatter.formatCellValue(row.getCell(57)).trim())? "T" : formatter.formatCellValue(row.getCell(57)).trim());
				logger.debug("::::: goodsMap :::::" + JsonUtil.getJsonStringFromMap(goodsMap).toJSONString());
				
				// 상품정보 저장
				goodsMapper.insertGoods(goodsMap);
				
				// 특정대상회원 저장
				List<String> usernoList = goodsMap.getArrayList("usernolist");
				if (usernoList != null && usernoList.size() > 0) {
					memberGoodsMapper.insertMemberGoods(goodsMap);
				}
				
				// KC인증번호 저장
				List<SOMap> kccertList = Util.convertToListSOMap(goodsMap.getArrayList("kccertlist"));
				if (kccertList != null && kccertList.size() > 0) {
					goodsKccertMapper.insertGoodsKccert(goodsMap);
				}
				
				// 상품카테고리 저장
				if (goodsCategoryList != null && goodsCategoryList.size() > 0) {
					goodsCategoryMapper.insertGoodsCategory(goodsMap);
				}
                
                // 상품정보고시 저장
				if (goodNotifyList != null && goodNotifyList.size() > 0) {
					goodsNotifyMapper.insertGoodsNotify(goodsMap);
				}
				
				// 이미지, 상세설명 저장
				String pccontent = formatter.formatCellValue(row.getCell(44));
				if (Util.isEmpty(pccontent.trim())) {
					throw new CustomException("상품상세설명(PC용)은 필수 입력 항목입니다.");
				}
				goodsMap.put("pccontent", pccontent);
				String mobilecontent = formatter.formatCellValue(row.getCell(45));
				if (Util.isEmpty(mobilecontent.trim())) {
					throw new CustomException("상품상세설명(모바일용)은 필수 입력 항목입니다.");
				}
				goodsMap.put("mobilecontent", mobilecontent);
				goodsMap.put("noticecontent", formatter.formatCellValue(row.getCell(42)));
				goodsMap.put("introcontent", formatter.formatCellValue(row.getCell(43)));
				goodsContentMapper.mergeGoodsContent(goodsMap);
				
				// 리뷰묶어보기 저장
				SOMap goodsDbParams = new SOMap();
				goodsDbParams.put("siteid", params.get("siteid"));
				goodsDbParams.put("istempsave", "F");
				goodsDbParams.put("isdeal", "F");
				goodsDbParams.put("goodsselltype", CMConst.GOODS_SELL_TYPE_SALE);
				goodsDbParams.put("goodsapprtype", CMConst.GOODS_STATUS_APPROVAL);
				
				String grpGoodsCodeStr = formatter.formatCellValue(row.getCell(55)).replaceAll(" ", "");
				if (!Util.isEmpty(grpGoodsCodeStr)) {
					List<String> goodsCodeList = Arrays.asList(grpGoodsCodeStr.split(",")).stream().distinct().collect(Collectors.toList());
					goodsDbParams.put("goodscodelist", goodsCodeList);
					List<SOMap> validGoodsList = goodsMapper.selectCommonGoodsList(goodsDbParams);
					if (goodsCodeList.size() != validGoodsList.size()) {
						throw new CustomException("리뷰묶어보기 유효하지 않은 상품코드가 존재합니다.");
					}
					goodsMap.put("grpgoodsnolist", validGoodsList.stream().map(m -> m.getStr("goodsno")).collect(Collectors.toList()));
					goodsGrpMapper.insertGoodsGrp(goodsMap);
				}
				
				// 추가상품 저장
				goodsDbParams.put("goodscodelist", null);
				String addGoodsCodeStr = formatter.formatCellValue(row.getCell(56)).replaceAll(" ", "");
				if (!Util.isEmpty(addGoodsCodeStr)) {
					List<String> goodsCodeList = Arrays.asList(addGoodsCodeStr.split(",")).stream().distinct().collect(Collectors.toList());
					goodsDbParams.put("goodscodelist", goodsCodeList);
					List<SOMap> validGoodsList = goodsMapper.selectCommonGoodsList(goodsDbParams);
					if (goodsCodeList.size() != validGoodsList.size()) {
						throw new CustomException("추가상품 유효하지 않은 상품코드가 존재합니다.");
					}
					goodsMap.put("addgoodsnolist", validGoodsList.stream().map(m -> m.getStr("goodsno")).collect(Collectors.toList()));
					goodsAdditionMapper.insertGoodsAddition(goodsMap);
				}
				
				// 이미지 파일명 세팅
				String pcimgName = formatter.formatCellValue(row.getCell(39)).trim();
				if (Util.isEmpty(pcimgName)) {
					throw new CustomException("대표이미지(PC)는 필수 입력 항목입니다.");
				}
				String moimgName = formatter.formatCellValue(row.getCell(41)).trim();
				if (Util.isEmpty(moimgName)) {
					throw new CustomException("대표이미지(모바일)는 필수 입력 항목입니다.");
				}
				String addimgName = formatter.formatCellValue(row.getCell(40)).trim();
				if (addimgName.split(",").length > 7) {
					throw new CustomException("추가이미지 파일명이 7개 이상입니다.");
				}
				rowImgNameMap.put("goodsno", goodsMap.get("goodsno"));
				rowImgNameMap.put("pcimg", pcimgName);
				rowImgNameMap.put("moimg", moimgName);
				rowImgNameMap.put("addimg", addimgName);
				
				// 옵션항목 저장
				List<String> optionNoList = new ArrayList<String>();
				SOMap goodsOptionMap = new SOMap();
				goodsOptionMap.put("goodsno", goodsMap.get("goodsno"));
				goodsOptionMap.put("authuserid", goodsMap.get("authuserid"));
				String optionItem1Name = formatter.formatCellValue(row.getCell(27)).trim();
				if (Util.isEmpty(optionItem1Name)) {
					throw new CustomException("옵션항목명 1단계는 필수 입력 항목입니다.");
				}
				goodsOptionMap.put("optionname", optionItem1Name);
				goodsOptionMap.put("sort", 1);
				goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
				optionNoList.add(goodsOptionMap.getStr("optionno"));
				
				String optionItem2Name = formatter.formatCellValue(row.getCell(28)).trim();
				if (!Util.isEmpty(optionItem2Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem2Name);
					goodsOptionMap.put("sort", 2);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				String optionItem3Name = formatter.formatCellValue(row.getCell(29)).trim();
				if (!Util.isEmpty(optionItem3Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem3Name);
					goodsOptionMap.put("sort", 3);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				String optionItem4Name = formatter.formatCellValue(row.getCell(30)).trim();
				if (!Util.isEmpty(optionItem4Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem4Name);
					goodsOptionMap.put("sort", 4);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				
				// 옵션 목록 세팅
				String[] isMainGoodsArr = formatter.formatCellValue(row.getCell(23)).replaceAll(" ", "").split("\\n");
				String[] isOptDisplayArr = formatter.formatCellValue(row.getCell(24)).replaceAll(" ", "").split("\\n");
				String[] marketpriceArr = formatter.formatCellValue(row.getCell(25)).replaceAll(" ", "").split("\\n");
				String[] priceArr = formatter.formatCellValue(row.getCell(26)).replaceAll(" ", "").split("\\n");
				String[] option1NameArr = formatter.formatCellValue(row.getCell(31)).replaceAll(" ", "").split("\\n");
				String[] option2NameArr = formatter.formatCellValue(row.getCell(32)).replaceAll(" ", "").split("\\n");
				String[] option3NameArr = formatter.formatCellValue(row.getCell(33)).replaceAll(" ", "").split("\\n");
				String[] option4NameArr = formatter.formatCellValue(row.getCell(34)).replaceAll(" ", "").split("\\n");
				String[] safeStockArr = formatter.formatCellValue(row.getCell(35)).replaceAll(" ", "").split("\\n");
				String[] orignalCodeListArr = formatter.formatCellValue(row.getCell(36)).replaceAll(" ", "").split("\\n");
				String[] orignalCntListArr = formatter.formatCellValue(row.getCell(37)).replaceAll(" ", "").split("\\n");
				if (Util.isEmpty(option1NameArr)) {
					throw new CustomException("옵션항목명 1단계는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(isMainGoodsArr)) {
					throw new CustomException("옵션 대표상품여부는 필수 입력 항목입니다.");
				}
				int mainGoodsCnt = Arrays.asList(isMainGoodsArr).stream().distinct().filter(str->"T".equals(str)).collect(Collectors.toList()).size();
				if (mainGoodsCnt == 0) {
					throw new CustomException("옵션 한개의 대표상품은 필수입니다.");
				}
				if (mainGoodsCnt > 1) {
					throw new CustomException("옵션 대표상품이 여러개 있습니다.");
				}
				if (Util.isEmpty(isOptDisplayArr)) {
					throw new CustomException("옵션 노출상태는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(marketpriceArr)) {
					throw new CustomException("옵션 정상가는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(priceArr)) {
					throw new CustomException("옵션 판매가는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(safeStockArr)) {
					throw new CustomException("옵션 안전재고는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(orignalCodeListArr)) {
					throw new CustomException("옵션 오리지널 코드는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(orignalCntListArr)) {
					throw new CustomException("옵션 오리지널 단품수량은 필수 입력 항목입니다.");
				}
				if (option1NameArr.length != isMainGoodsArr.length
						|| option1NameArr.length != isOptDisplayArr.length 
						|| option1NameArr.length != marketpriceArr.length
						|| option1NameArr.length != priceArr.length 
						|| option1NameArr.length != safeStockArr.length) {
					throw new CustomException("옵션설정(대표상품, 옵션상태, 정상가, 판매가, 옵션항목명1단계, 안전재고) 개수가 일치하지 않습니다.");
				}
				if (option1NameArr.length != orignalCodeListArr.length) {
					throw new CustomException("오리지널 코드 목록이 옵션 개수와 일치하지 않습니다.");
				}
				if (option1NameArr.length != orignalCntListArr.length) {
					throw new CustomException("오리지널 단품수량 목록이 옵션 개수와 일치하지 않습니다.");
				}
				boolean isValidOpt = true;
				for (int i=0; i<option1NameArr.length; i++) {
					SOMap optionMap = new SOMap();
					double price = Double.parseDouble(priceArr[i]);
					optionMap.put("goodsno", goodsMap.get("goodsno"));
					optionMap.put("authuserid", params.get("authuserid"));
					optionMap.put("optioncode", goodsOptionDetailMapper.selectOptionCode(goodsMap));
					optionMap.put("marketprice", marketpriceArr[i]);
					optionMap.put("price", price);
					optionMap.put("originalprice", 0);
					optionMap.put("commrate", 0);
					optionMap.put("stockcnt", 0);
					optionMap.put("safestockcnt", safeStockArr[i]);
					optionMap.put("ismaingoods", isMainGoodsArr[i]);
					optionMap.put("isoptdisplay", isOptDisplayArr[i]);
					optionMap.put("optionno1", optionNoList.get(0));
					optionMap.put("optionno2", (optionNoList.size() > 1 && !Util.isEmpty(optionNoList.get(1)))? optionNoList.get(1):null);
					optionMap.put("optionno3", (optionNoList.size() > 2 && !Util.isEmpty(optionNoList.get(2)))? optionNoList.get(2):null);
					optionMap.put("optionno4", (optionNoList.size() > 3 && !Util.isEmpty(optionNoList.get(3)))? optionNoList.get(3):null);
					optionMap.put("optionnm1", option1NameArr[i]);
					optionMap.put("optionnm2", (option2NameArr.length > i && !Util.isEmpty(option2NameArr[i]))? option2NameArr[i]:null);
					optionMap.put("optionnm3", (option3NameArr.length > i && !Util.isEmpty(option3NameArr[i]))? option3NameArr[i]:null);
					optionMap.put("optionnm4", (option4NameArr.length > i && !Util.isEmpty(option4NameArr[i]))? option4NameArr[i]:null);
					if ("T".equals(isMainGoodsArr[i])) {
						goodsMap.put("marketprice", marketpriceArr[i]);
						goodsMap.put("price", priceArr[i]);
						goodsMapper.updateGoods(goodsMap);
					}
					List<SOMap> erpoptList = new ArrayList<SOMap>();
					List<String> orignalCodeList = Arrays.asList(orignalCodeListArr[i].split(","));
					List<String> orignalCntList = Arrays.asList(orignalCntListArr[i].split(","));
					for (int j=0; j<orignalCodeList.size(); j++) {
						SOMap erpoptMap = new SOMap();
						erpoptMap.put("goodsno", goodsMap.get("goodsno"));
						erpoptMap.put("erpoptcode", orignalCodeList.get(j));
						erpoptMap.put("cnt", orignalCntList.get(j));
						erpoptMap.put("authuserid", goodsMap.get("authuserid"));
						erpoptMap.put("orgcode", erpoptMap.get("erpoptcode"));
						SOMap erpResultMap = erpService.selectReceiveERPOrgGoodsList(erpoptMap);
			            List<SOMap> erpRsultList = Util.convertToListSOMap(erpResultMap.getArrayList("list"));
						if (erpRsultList == null || erpRsultList.size() == 0) {
							isValidOpt = false;
							break;
						} else {
							SOMap erpMap = erpRsultList.get(0);
							erpoptMap.put("originalprice", erpMap.get("buy_cost"));
							erpoptMap.put("erpoptname", erpMap.get("org_item_name"));
							erpoptList.add(erpoptMap);
						}
					}
					double originalprice = 0;
					for (int k=0; k<erpoptList.size(); k++) {
						SOMap erpMap = erpoptList.get(k);
						originalprice += erpMap.getDouble("originalprice") * erpMap.getDouble("cnt");
					}
					optionMap.put("originalprice", originalprice);
					goodsOptionDetailMapper.insertGoodsOptionDetail(optionMap);

					if (erpoptList != null && erpoptList.size() > 0) {
						optionMap.put("erpoptlist", erpoptList);
						optionErpMapper.insertOptionErp(optionMap);
					}
				}
				if (!isValidOpt) {
					throw new CustomException("유효하지 않은 오리지널코드가 존재합니다.");
				}

	            // 상품 히스토리 저장 (최초 임시저장)
				goodsMap.put("fstTempSave", "T");
	            historyService.insertGoodsHistory(goodsMap);
	            
	            // 승인 히스토리 저장 (임시저장)
	            goodsApprMapper.insertGoodsApprHist(goodsMap);
	            
				succCnt ++;
				result.put("goodsno", goodsMap.get("goodsno"));
				result.put("rowimgnamemap", rowImgNameMap);
			}
		} catch (CustomException e) {
			logger.error(e.getMessage());
			throw e;
        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
        }
		
		result.put("succcnt", succCnt);
		
		return result;
	}

	/**
	 * 관리자 위탁 상품업로드
	 * @param workSheet
	 * @return SOMap
	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {CustomException.class})
	public SOMap _saveAdminExcelCoGoodsRow(XSSFRow row, SOMap params) throws Exception {
		SOMap result = new SOMap();
		int succCnt = 0;

		try {
			SOMap commonCodeAllStrMap = params.getSOMap("commoncodeallstrmap");
        	DataFormatter formatter = new DataFormatter();
        	SOMap goodsMap = new SOMap();
			SOMap rowImgNameMap = new SOMap();

			if (row != null && !Util.isEmpty(formatter.formatCellValue(row.getCell(4)).replaceAll(" ", ""))) {
				goodsMap.put("siteid", params.getStr("siteid"));
				goodsMap.put("authuserid", params.get("authuserid"));
				goodsMap.put("authuserno", params.get("authuserno"));
				goodsMap.put("goodscode", goodsMapper.selectGoodsCode());
				goodsMap.put("ispbgoods", "F");
				goodsMap.put("goodsselltype", CMConst.GOODS_SELL_TYPE_WAIT);
				goodsMap.put("goodsapprtype", CMConst.GOODS_STATUS_TEMP);
				goodsMap.put("isdeal", "F");
				goodsMap.put("istempsave", "T");

                String dealercode = formatter.formatCellValue(row.getCell(1)).trim();
				if (Util.isEmpty(dealercode)) {
					throw new CustomException("파트너사코드는 필수 입력 항목입니다.");
				}
				SOMap dealerDbParams = new SOMap();
				dealerDbParams.put("dealercode", dealercode);
				SOMap dealerInfo = dealerMapper.selectPartnsersDetail(dealerDbParams);
				if (Util.isEmpty(dealerInfo)) {
					throw new CustomException("유효하지 않은 파트너사코드 입니다.");
				}
				goodsMap.put("dealerno", dealerInfo.get("no"));
				
				List<SOMap> goodsCategoryList = new ArrayList<SOMap>();
                String repreCateIdx = formatter.formatCellValue(row.getCell(2)).trim();
                if (Util.isEmpty(repreCateIdx)) {
					throw new CustomException("대표카테고리는 필수 입력 항목입니다.");
                }
                String addCateIdxStr = formatter.formatCellValue(row.getCell(3)).replaceAll(" ", "");
                if (addCateIdxStr.contains(repreCateIdx)) {
					throw new CustomException("대표카테고리와 중복된 추가카테고리가 존재합니다.");
                }
                SOMap categoryMap = new SOMap();
                categoryMap.put("cateidx", repreCateIdx);
                categoryMap.put("isrepre", "T");
                goodsCategoryList.add(categoryMap);
                if (!Util.isEmpty(addCateIdxStr)) {
                    List<String> addCateIdxList = Arrays.asList(addCateIdxStr.split(","));
                    addCateIdxList = addCateIdxList.stream().distinct().collect(Collectors.toList());
                    for (String cateidx : addCateIdxList) {
                    	SOMap addCategoryMap = new SOMap();
                        addCategoryMap.put("cateidx", cateidx);
                        addCategoryMap.put("isrepre", "F");
                        goodsCategoryList.add(addCategoryMap);
                    }
                }
				goodsMap.put("goodscategorylist", goodsCategoryList);
				int categoryListCnt = categoryMapper.selectCategoryCount(goodsMap);
				if (categoryListCnt != goodsCategoryList.size()) {
					throw new CustomException("유효하지 않은 카테고리가 존재합니다.");
				}
				String goodsname = formatter.formatCellValue(row.getCell(4));
				if (Util.isEmpty(goodsname.replaceAll(" ", ""))) {
					throw new CustomException("상품명은 필수 입력 항목입니다.");
				} else if (goodsname.length() > 50) {
					throw new CustomException("상품명이 50자 이상입니다.");
				}
				goodsMap.put("goodsname", goodsname);
				String summary = formatter.formatCellValue(row.getCell(5));
				if (summary.length() > 1000) {
					throw new CustomException("상품요약설명이 1,000자 이상입니다.");
				}
				goodsMap.put("summary", summary);
				String keyword = formatter.formatCellValue(row.getCell(6));
				if (keyword.length() > 100) {
					throw new CustomException("검색키워드가 100자 이상입니다.");
				}
				goodsMap.put("keyword", keyword);
				String brcode = formatter.formatCellValue(row.getCell(7)).trim();
				if (!Util.isEmpty(brcode)) {
					SOMap brandParams = new SOMap();
					brandParams.put("siteid", goodsMap.get("siteid"));
					brandParams.put("brcode", brcode);
					SOMap brandMap = brandMapper.selectBrandDetail(brandParams);
					if (Util.isEmptyMap(brandMap)) {
						throw new CustomException("유효하지 않은 브랜드코드 입니다.");
					}
					goodsMap.put("brandidx", brandMap.get("idx"));
				}
				String mdifidx = formatter.formatCellValue(row.getCell(8)).trim();
				if (Util.isEmpty(mdifidx)) {
					throw new CustomException("담당 MD정보 IDX는 필수 입력 항목입니다.");
				} else {
					SOMap mdParams = new SOMap();
					mdParams.put("mdifidx", mdifidx);
					mdParams.put("isdp", "F");
					List<SOMap> chargemdList= chargemdMapper.selectChargemdList(mdParams);
					if (chargemdList == null || chargemdList.size() == 0) {
						throw new CustomException("유효하지 않은  MD코드 입니다.");
					}
					goodsMap.put("mdifidx", mdifidx);
				}
				goodsMap.put("muappchtype", Util.isEmpty(formatter.formatCellValue(row.getCell(9)))? commonCodeAllStrMap.getStr("muappchtype") : formatter.formatCellValue(row.getCell(9)).replaceAll(" ", ""));
				goodsMap.put("istaxfree", Util.isEmpty(formatter.formatCellValue(row.getCell(10)))? "F" : formatter.formatCellValue(row.getCell(10)));
				String disstdate = formatter.formatCellValue(row.getCell(11))+":00";
				if (!DateTimeUtil.isValid(disstdate)) {
					throw new CustomException("전시기간(시작일)이 유효하지 않은 날짜입니다.");
				}
				String diseddate = formatter.formatCellValue(row.getCell(12))+":00";
				if (!DateTimeUtil.isValid(diseddate)) {
					throw new CustomException("전시기간(종료일)이 유효하지 않은 날짜입니다.");
				}
				if (DateTimeUtil.diffOfSeconds(disstdate, diseddate) < 0) {
					throw new CustomException("전시종료일이 전시시작일보다 이전입니다.");
				}
				goodsMap.put("disstday", DateTimeUtil.getFormatStr(disstdate, DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12));
				goodsMap.put("disedday", DateTimeUtil.getFormatStr(diseddate, DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12));
				
				String minordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(13)).trim())? "1" : formatter.formatCellValue(row.getCell(13)).trim();
				if (!this._isNumber(minordcnt)) {
					throw new CustomException("최소주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("minordcnt", minordcnt);
				String maxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(14)).trim())? "1" : formatter.formatCellValue(row.getCell(14)).trim();
				if (!this._isNumber(maxordcnt)) {
					throw new CustomException("최대주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("maxordcnt", maxordcnt);
				String daymaxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(15)).trim())? "999" : formatter.formatCellValue(row.getCell(15)).trim();
				if (!this._isNumber(daymaxordcnt)) {
					throw new CustomException("1일 최대주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("daymaxordcnt", daymaxordcnt);
				String permaxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(16)).trim())? "999" : formatter.formatCellValue(row.getCell(16)).trim();
				if (!this._isNumber(permaxordcnt)) {
					throw new CustomException("1인당 최대구매수량이 숫자가 아닙니다.");
				}
				goodsMap.put("permaxordcnt", permaxordcnt);
				goodsMap.put("isdisplay", Util.isEmpty(formatter.formatCellValue(row.getCell(17)).trim())? "T" : formatter.formatCellValue(row.getCell(17)).trim());
				goodsMap.put("isfrstsale", Util.isEmpty(formatter.formatCellValue(row.getCell(18)).trim())? "T" : formatter.formatCellValue(row.getCell(18)).trim());
				goodsMap.put("iscncappr", Util.isEmpty(formatter.formatCellValue(row.getCell(19)).trim())? "F" : formatter.formatCellValue(row.getCell(19)).trim());
				goodsMap.put("goodsdivtype", Util.isEmpty(formatter.formatCellValue(row.getCell(20)).replaceAll(" ", ""))? CMConst.GOODS_DIV_NEW : formatter.formatCellValue(row.getCell(20)).replaceAll(" ", ""));
				String selltarget = Util.isEmpty(formatter.formatCellValue(row.getCell(21)).trim())? "1" : formatter.formatCellValue(row.getCell(21)).trim();
				if ("1".equals(selltarget)) {			// 전체
					goodsMap.put("mumemlvtype", commonCodeAllStrMap.getStr("mumemlvtype"));
				} else if ("2".equals(selltarget)) {	// 등급별
					goodsMap.put("mumemlvtype", Util.isEmpty(formatter.formatCellValue(row.getCell(22)).replaceAll(" ", ""))? commonCodeAllStrMap.getStr("mumemlvtype") : formatter.formatCellValue(row.getCell(22)).replaceAll(" ", ""));
				} else if ("3".equals(selltarget)) {	// 특정회원
					String memberidStr = formatter.formatCellValue(row.getCell(23)).replaceAll(" ", "");
					if (Util.isEmpty(memberidStr)) {
						throw new CustomException("판매대상이 특정회원이면 특정회원판매는 필수입력 항목입니다.");
					} else {
						String[] memberidArr = memberidStr.split(",");
						SOMap memberParams = new SOMap();
						memberParams.put("siteid", params.get("siteid"));
						memberParams.put("useridlist", Arrays.asList(memberidArr));
						List<SOMap> memberList = memberMapper.selectAdminMemberList(memberParams);
						if (memberidArr.length != memberList.size()) {
							throw new CustomException("판매대상 특정회원에 유효하지 않은 회원 ID가 있습니다.");
						}
						goodsMap.put("usernolist", memberList.stream().map(m->m.get("userno")).distinct().collect(Collectors.toList()));
					}
				} else {
					throw new CustomException("판매대상이 올바르지 않습니다.");
				}
				goodsMap.put("mugicontype", formatter.formatCellValue(row.getCell(38)).replaceAll(" ", ""));
				goodsMap.put("iscombdeliv", Util.isEmpty(formatter.formatCellValue(row.getCell(46)).trim())? "F" : formatter.formatCellValue(row.getCell(46)).trim());
				String delividx = formatter.formatCellValue(row.getCell(47)).trim();
				if (Util.isEmpty(delividx)) {
					throw new CustomException("배송정보는 필수입력 항목입니다.");
				} else {
					SOMap delivParams = new SOMap();
					delivParams.put("istrash", "F");
					delivParams.put("delividx", delividx);
					delivParams.put("dealerno", dealerInfo.get("no"));
					List<SOMap> delivTemplateList= delivTemplateMapper.selectDelivTemplateList(delivParams);
					if (delivTemplateList == null || delivTemplateList.size() == 0) {
						throw new CustomException("유효한 배송정보가 아닙니다.");
					}
					goodsMap.put("delividx", delividx);
				}
				String notifyTplIdx = formatter.formatCellValue(row.getCell(48)).trim();
				if (Util.isEmpty(notifyTplIdx)) {
					throw new CustomException("상품정보고시는 필수입력 항목입니다.");
				}
				SOMap notifyParams = new SOMap();
				notifyParams.put("notifyTplIdx", notifyTplIdx);
				List<SOMap> goodNotifyList = notifyTplItemMapper.selectNotifyTplItemList(notifyParams);
				if (goodNotifyList == null || goodNotifyList.size() == 0) {
					throw new CustomException("유효하지 않은 상품정보고시코드 입니다.");
				}
				goodNotifyList.forEach(m -> {
					m.put("notifydata", "상세설명참고");
				});
				goodsMap.put("goodsnotifylist", goodNotifyList);
				String kcdivtype = Util.isEmpty(formatter.formatCellValue(row.getCell(49)).trim())? CMConst.KC_DIV_TYPE_NON_TARGET : formatter.formatCellValue(row.getCell(49)).trim();
				goodsMap.put("kcdivtype", kcdivtype);
				if (Util.equal(kcdivtype, CMConst.KC_DIV_TYPE_TARGET)) {
					String certNumStr = formatter.formatCellValue(row.getCell(50)).replaceAll(" ", "");
					if (Util.isEmpty(certNumStr)) {
						throw new CustomException("KC 인증대상인 경우 KC인증번호는 필수입력 항목입니다.");
					}
					List<String> certnumList = Arrays.asList(certNumStr.split(","));
					List<SOMap> kccertlist = new ArrayList<SOMap>();
					certnumList.stream().distinct().forEach(certnum -> {
						SOMap kccert = new SOMap();
						kccert.put("kccertno", certnum);
						kccert.put("iscert", "F");
						kccertlist.add(kccert);
					});
					goodsMap.put("kccertlist", kccertlist);
				}

				String aswarmonth = Util.isEmpty(formatter.formatCellValue(row.getCell(51)).trim())? "0" : formatter.formatCellValue(row.getCell(51)).trim();
				if (!this._isNumber(aswarmonth)) {
					throw new CustomException("AS보증기간이 숫자가 아닙니다.");
				}
				goodsMap.put("aswarmonth", aswarmonth);
				String asnotice = formatter.formatCellValue(row.getCell(52));
				if (asnotice.length() > 1000) {
					throw new CustomException("AS 안내문구가 1,000자 이상입니다.");
				}
				goodsMap.put("asnotice", asnotice);
				goodsMap.put("mumembertype", Util.isEmpty(formatter.formatCellValue(row.getCell(53)).replaceAll(" ", ""))? commonCodeAllStrMap.getStr("mumembertype") : formatter.formatCellValue(row.getCell(53)).replaceAll(" ", ""));
				goodsMap.put("isopenreview", Util.isEmpty(formatter.formatCellValue(row.getCell(54)).trim())? "T" : formatter.formatCellValue(row.getCell(54)).trim());
				goodsMap.put("isepif", Util.isEmpty(formatter.formatCellValue(row.getCell(57)).trim())? "T" : formatter.formatCellValue(row.getCell(57)).trim());
				logger.debug("::::: goodsMap :::::" + JsonUtil.getJsonStringFromMap(goodsMap).toJSONString());
				
				// 상품정보 저장
				goodsMapper.insertGoods(goodsMap);
				
				// 특정대상회원 저장
				List<String> usernoList = goodsMap.getArrayList("usernolist");
				if (usernoList != null && usernoList.size() > 0) {
					memberGoodsMapper.insertMemberGoods(goodsMap);
				}
				
				// KC인증번호 저장
				List<SOMap> kccertList = Util.convertToListSOMap(goodsMap.getArrayList("kccertlist"));
				if (kccertList != null && kccertList.size() > 0) {
					goodsKccertMapper.insertGoodsKccert(goodsMap);
				}
				
				// 상품카테고리 저장
				if (goodsCategoryList != null && goodsCategoryList.size() > 0) {
					goodsCategoryMapper.insertGoodsCategory(goodsMap);
				}
                
                // 상품정보고시 저장
				if (goodNotifyList != null && goodNotifyList.size() > 0) {
					goodsNotifyMapper.insertGoodsNotify(goodsMap);
				}
				
				// 이미지, 상세설명 저장
				String pccontent = formatter.formatCellValue(row.getCell(44));
				if (Util.isEmpty(pccontent.trim())) {
					throw new CustomException("상품상세설명(PC용)은 필수 입력 항목입니다.");
				}
				goodsMap.put("pccontent", pccontent);
				String mobilecontent = formatter.formatCellValue(row.getCell(45));
				if (Util.isEmpty(mobilecontent.trim())) {
					throw new CustomException("상품상세설명(모바일용)은 필수 입력 항목입니다.");
				}
				goodsMap.put("mobilecontent", mobilecontent);
				goodsMap.put("noticecontent", formatter.formatCellValue(row.getCell(42)));
				goodsMap.put("introcontent", formatter.formatCellValue(row.getCell(43)));
				goodsContentMapper.mergeGoodsContent(goodsMap);
				
				// 리뷰묶어보기 저장
				SOMap goodsDbParams = new SOMap();
				goodsDbParams.put("siteid", params.get("siteid"));
				goodsDbParams.put("istempsave", "F");
				goodsDbParams.put("isdeal", "F");
				goodsDbParams.put("goodsselltype", CMConst.GOODS_SELL_TYPE_SALE);
				goodsDbParams.put("goodsapprtype", CMConst.GOODS_STATUS_APPROVAL);
				String grpGoodsCodeStr = formatter.formatCellValue(row.getCell(55)).replaceAll(" ", "");
				if (!Util.isEmpty(grpGoodsCodeStr)) {
					List<String> goodsCodeList = Arrays.asList(grpGoodsCodeStr.split(",")).stream().distinct().collect(Collectors.toList());
					goodsDbParams.put("goodscodelist", goodsCodeList);
					List<SOMap> validGoodsList = goodsMapper.selectCommonGoodsList(goodsDbParams);
					if (goodsCodeList.size() != validGoodsList.size()) {
						throw new CustomException("리뷰묶어보기 유효하지 않은 상품코드가 존재합니다.");
					}
					goodsMap.put("grpgoodsnolist", validGoodsList.stream().map(m -> m.getStr("goodsno")).collect(Collectors.toList()));
					goodsGrpMapper.insertGoodsGrp(goodsMap);
				}
				
				// 추가상품 저장
				goodsDbParams.put("goodscodelist", null);
				String addGoodsCodeStr = formatter.formatCellValue(row.getCell(56)).replaceAll(" ", "");
				if (!Util.isEmpty(addGoodsCodeStr)) {
					List<String> goodsCodeList = Arrays.asList(addGoodsCodeStr.split(",")).stream().distinct().collect(Collectors.toList());
					goodsDbParams.put("goodscodelist", goodsCodeList);
					List<SOMap> validGoodsList = goodsMapper.selectCommonGoodsList(goodsDbParams);
					if (goodsCodeList.size() != validGoodsList.size()) {
						throw new CustomException("추가상품 유효하지 않은 상품코드가 존재합니다.");
					}
					goodsMap.put("addgoodsnolist", validGoodsList.stream().map(m -> m.getStr("goodsno")).collect(Collectors.toList()));
					goodsAdditionMapper.insertGoodsAddition(goodsMap);
				}
				
				// 이미지 파일명 세팅
				String pcimgName = formatter.formatCellValue(row.getCell(39)).trim();
				if (Util.isEmpty(pcimgName)) {
					throw new CustomException("대표이미지(PC)는 필수 입력 항목입니다.");
				}
				String moimgName = formatter.formatCellValue(row.getCell(41)).trim();
				if (Util.isEmpty(moimgName)) {
					throw new CustomException("대표이미지(모바일)는 필수 입력 항목입니다.");
				}
				String addimgName = formatter.formatCellValue(row.getCell(40)).trim();
				if (addimgName.split(",").length > 7) {
					throw new CustomException("추가이미지 파일명이 7개 이상입니다.");
				}
				rowImgNameMap.put("goodsno", goodsMap.get("goodsno"));
				rowImgNameMap.put("pcimg", pcimgName);
				rowImgNameMap.put("moimg", moimgName);
				rowImgNameMap.put("addimg", addimgName);
				
				// 옵션항목 저장
				List<String> optionNoList = new ArrayList<String>();
				SOMap goodsOptionMap = new SOMap();
				goodsOptionMap.put("goodsno", goodsMap.get("goodsno"));
				goodsOptionMap.put("authuserid", goodsMap.get("authuserid"));
				String optionItem1Name = formatter.formatCellValue(row.getCell(29)).trim();
				if (Util.isEmpty(optionItem1Name)) {
					throw new CustomException("옵션항목명 1단계는 필수 입력 항목입니다.");
				}
				goodsOptionMap.put("optionname", optionItem1Name);
				goodsOptionMap.put("sort", 1);
				goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
				optionNoList.add(goodsOptionMap.getStr("optionno"));
				
				String optionItem2Name = formatter.formatCellValue(row.getCell(30)).trim();
				if (!Util.isEmpty(optionItem2Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem2Name);
					goodsOptionMap.put("sort", 2);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				String optionItem3Name = formatter.formatCellValue(row.getCell(31)).trim();
				if (!Util.isEmpty(optionItem3Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem3Name);
					goodsOptionMap.put("sort", 3);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				String optionItem4Name = formatter.formatCellValue(row.getCell(32)).trim();
				if (!Util.isEmpty(optionItem4Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem4Name);
					goodsOptionMap.put("sort", 4);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				
				// 옵션 목록 저장
				String[] isMainGoodsArr = formatter.formatCellValue(row.getCell(24)).replaceAll(" ", "").split("\\n");
				String[] isOptDisplayArr = formatter.formatCellValue(row.getCell(25)).replaceAll(" ", "").split("\\n");
				String[] marketpriceArr = formatter.formatCellValue(row.getCell(26)).replaceAll(" ", "").split("\\n");
				String[] priceArr = formatter.formatCellValue(row.getCell(27)).replaceAll(" ", "").split("\\n");
				String[] commRateArr = formatter.formatCellValue(row.getCell(28)).replaceAll(" ", "").split("\\n");
				String[] option1NameArr = formatter.formatCellValue(row.getCell(33)).replaceAll(" ", "").split("\\n");
				String[] option2NameArr = formatter.formatCellValue(row.getCell(34)).replaceAll(" ", "").split("\\n");
				String[] option3NameArr = formatter.formatCellValue(row.getCell(35)).replaceAll(" ", "").split("\\n");
				String[] option4NameArr = formatter.formatCellValue(row.getCell(36)).replaceAll(" ", "").split("\\n");
				String[] stockArr = formatter.formatCellValue(row.getCell(37)).replaceAll(" ", "").split("\\n");
				if (Util.isEmpty(option1NameArr)) {
					throw new CustomException("옵션항목명 1단계는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(isMainGoodsArr)) {
					throw new CustomException("옵션 대표상품여부는 필수 입력 항목입니다.");
				}
				int mainGoodsCnt = Arrays.asList(isMainGoodsArr).stream().distinct().filter(str->"T".equals(str)).collect(Collectors.toList()).size();
				if (mainGoodsCnt == 0) {
					throw new CustomException("옵션 한개의 대표상품은 필수입니다.");
				}
				if (mainGoodsCnt > 1) {
					throw new CustomException("옵션 대표상품이 여러개 있습니다.");
				}
				if (Util.isEmpty(isOptDisplayArr)) {
					throw new CustomException("옵션 노출상태는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(marketpriceArr)) {
					throw new CustomException("옵션 정상가는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(priceArr)) {
					throw new CustomException("옵션 판매가는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(stockArr)) {
					throw new CustomException("옵션 재고는 필수 입력 항목입니다.");
				}
				if (option1NameArr.length != isMainGoodsArr.length
						|| option1NameArr.length != isOptDisplayArr.length 
						|| option1NameArr.length != marketpriceArr.length
						|| option1NameArr.length != priceArr.length 
						|| option1NameArr.length != stockArr.length) {
					throw new CustomException("옵션설정(대표상품, 옵션상태, 정상가, 판매가, 옵션항목명1단계, 재고) 개수가 일치하지 않습니다.");
				}
				for (int i=0; i<option1NameArr.length; i++) {
					SOMap optionMap = new SOMap();
					double price = Double.parseDouble(priceArr[i]);
					double commrate = (commRateArr.length > i && !Util.isEmpty(commRateArr[i])) ? Double.parseDouble(commRateArr[i]) : dealerInfo.getDouble("commrate");
					optionMap.put("goodsno", goodsMap.get("goodsno"));
					optionMap.put("authuserid", params.get("authuserid"));
					optionMap.put("optioncode", goodsOptionDetailMapper.selectOptionCode(goodsMap));
					optionMap.put("marketprice", marketpriceArr[i]);
					optionMap.put("price", price);
					optionMap.put("originalprice", Math.round(price - commrate/100 * price));
					optionMap.put("commrate", commrate);
					optionMap.put("stockcnt", stockArr[i]);
					optionMap.put("safestockcnt", 0);
					optionMap.put("ismaingoods", isMainGoodsArr[i]);
					optionMap.put("isoptdisplay", isOptDisplayArr[i]);
					optionMap.put("optionno1", optionNoList.get(0));
					optionMap.put("optionno2", (optionNoList.size() > 1 && !Util.isEmpty(optionNoList.get(1)))? optionNoList.get(1):null);
					optionMap.put("optionno3", (optionNoList.size() > 2 && !Util.isEmpty(optionNoList.get(2)))? optionNoList.get(2):null);
					optionMap.put("optionno4", (optionNoList.size() > 3 && !Util.isEmpty(optionNoList.get(3)))? optionNoList.get(3):null);
					optionMap.put("optionnm1", option1NameArr[i]);
					optionMap.put("optionnm2", (option2NameArr.length > i && !Util.isEmpty(option2NameArr[i]))? option2NameArr[i]:null);
					optionMap.put("optionnm3", (option3NameArr.length > i && !Util.isEmpty(option3NameArr[i]))? option3NameArr[i]:null);
					optionMap.put("optionnm4", (option4NameArr.length > i && !Util.isEmpty(option4NameArr[i]))? option4NameArr[i]:null);
					if ("T".equals(isMainGoodsArr[i])) {
						goodsMap.put("marketprice", marketpriceArr[i]);
						goodsMap.put("price", priceArr[i]);
						goodsMapper.updateGoods(goodsMap);
					}
					goodsOptionDetailMapper.insertGoodsOptionDetail(optionMap);
				}

	            // 상품 히스토리 저장 (최초 임시저장)
				goodsMap.put("fstTempSave", "T");
	            historyService.insertGoodsHistory(goodsMap);
	            
	            // 승인 히스토리 저장 (임시저장)
	            goodsApprMapper.insertGoodsApprHist(goodsMap);
	            
				succCnt ++;
				result.put("goodsno", goodsMap.get("goodsno"));
				result.put("rowimgnamemap", rowImgNameMap);
			}
		} catch (CustomException e) {
			logger.error(e.getMessage());
			throw e;
        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
        }

		result.put("succcnt", succCnt);
		
		return result;
	}
	
	/**
	 * 파트너사 상품업로드
	 * @param workSheet
	 * @return SOMap
	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap _savePatnerExcelGoodsRow(XSSFRow row, SOMap params) throws Exception {
    	SOMap result = new SOMap();
		int succCnt = 0;

		try {
			SOMap commonCodeAllStrMap = params.getSOMap("commoncodeallstrmap");
        	DataFormatter formatter = new DataFormatter();
        	SOMap goodsMap = new SOMap();
			SOMap rowImgNameMap = new SOMap();

			if (row != null && !Util.isEmpty(formatter.formatCellValue(row.getCell(3)).replaceAll(" ", ""))) {
				goodsMap.put("siteid", params.getStr("siteid"));
				goodsMap.put("authuserid", params.get("authuserid"));
				goodsMap.put("authuserno", params.get("authuserno"));
				goodsMap.put("goodscode", goodsMapper.selectGoodsCode());
				goodsMap.put("ispbgoods", "F");
				goodsMap.put("goodsselltype", CMConst.GOODS_SELL_TYPE_WAIT);
				goodsMap.put("goodsapprtype", CMConst.GOODS_STATUS_TEMP);
				goodsMap.put("isdeal", "F");
				goodsMap.put("istempsave", "T");
				goodsMap.put("dealerno", params.get("authuserno"));

				// 상품카테고리 세팅
				List<SOMap> goodsCategoryList = new ArrayList<SOMap>();
                String repreCateIdx = formatter.formatCellValue(row.getCell(1)).trim();
                if (Util.isEmpty(repreCateIdx)) {
					throw new CustomException("대표카테고리는 필수 입력 항목입니다.");
                }
                String addCateIdxStr = formatter.formatCellValue(row.getCell(2)).replaceAll(" ", "");
                if (addCateIdxStr.contains(repreCateIdx)) {
					throw new CustomException("대표카테고리와 중복된 추가카테고리가 존재합니다.");
                }
                SOMap categoryMap = new SOMap();
                categoryMap.put("cateidx", repreCateIdx);
                categoryMap.put("isrepre", "T");
                goodsCategoryList.add(categoryMap);
                if (!Util.isEmpty(addCateIdxStr)) {
                    List<String> addCateIdxList = Arrays.asList(addCateIdxStr.split(","));
                    addCateIdxList = addCateIdxList.stream().distinct().collect(Collectors.toList());
                    for (String cateidx : addCateIdxList) {
                    	SOMap addCategoryMap = new SOMap();
                        addCategoryMap.put("cateidx", cateidx);
                        addCategoryMap.put("isrepre", "F");
                        goodsCategoryList.add(addCategoryMap);
                    }
                }
				goodsMap.put("goodscategorylist", goodsCategoryList);
				int categoryListCnt = categoryMapper.selectCategoryCount(goodsMap);
				if (categoryListCnt != goodsCategoryList.size()) {
					throw new CustomException("유효하지 않은 카테고리가 존재합니다.");
				}
				String goodsname = formatter.formatCellValue(row.getCell(3));
				if (Util.isEmpty(goodsname.replaceAll(" ", ""))) {
					throw new CustomException("상품명은 필수 입력 항목입니다.");
				} else if (goodsname.length() > 50) {
					throw new CustomException("상품명이 50자 이상입니다.");
				}
				goodsMap.put("goodsname", goodsname);
				String summary = formatter.formatCellValue(row.getCell(4));
				if (summary.length() > 1000) {
					throw new CustomException("상품요약설명이 1,000자 이상입니다.");
				}
				goodsMap.put("summary", summary);
				String keyword = formatter.formatCellValue(row.getCell(5));
				if (keyword.length() > 100) {
					throw new CustomException("검색키워드가 100자 이상입니다.");
				}
				goodsMap.put("keyword", keyword);
				String brcode = formatter.formatCellValue(row.getCell(6)).trim();
				if (!Util.isEmpty(brcode)) {
					SOMap brandParams = new SOMap();
					brandParams.put("siteid", goodsMap.get("siteid"));
					brandParams.put("brcode", brcode);
					SOMap brandMap = brandMapper.selectBrandDetail(brandParams);
					if (Util.isEmptyMap(brandMap)) {
						throw new CustomException("유효하지 않은 브랜드코드 입니다.");
					}
					goodsMap.put("brandidx", brandMap.get("idx"));
				}
				String mdifidx = formatter.formatCellValue(row.getCell(7)).trim();
				if (Util.isEmpty(mdifidx)) {
					throw new CustomException("담당 MD정보 IDX는 필수 입력 항목입니다.");
				} else {
					SOMap mdParams = new SOMap();
					mdParams.put("mdifidx", mdifidx);
					mdParams.put("isdp", "F");
					List<SOMap> chargemdList= chargemdMapper.selectChargemdList(mdParams);
					if (chargemdList == null || chargemdList.size() == 0) {
						throw new CustomException("유효하지 않은  MD코드 입니다.");
					}
					goodsMap.put("mdifidx", mdifidx);
				}
				goodsMap.put("muappchtype", Util.isEmpty(formatter.formatCellValue(row.getCell(8)))? commonCodeAllStrMap.getStr("muappchtype") : formatter.formatCellValue(row.getCell(8)).replaceAll(" ", ""));
				goodsMap.put("istaxfree", Util.isEmpty(formatter.formatCellValue(row.getCell(9)))? "F" : formatter.formatCellValue(row.getCell(9)));
				String disstdate = formatter.formatCellValue(row.getCell(10))+":00";
				if (!DateTimeUtil.isValid(disstdate)) {
					throw new CustomException("전시기간(시작일)이 유효하지 않은 날짜입니다.");
				}
				String diseddate = formatter.formatCellValue(row.getCell(11))+":00";
				if (!DateTimeUtil.isValid(diseddate)) {
					throw new CustomException("전시기간(종료일)이 유효하지 않은 날짜입니다.");
				}
				if (DateTimeUtil.diffOfSeconds(disstdate, diseddate) < 0) {
					throw new CustomException("전시종료일이 전시시작일보다 이전입니다.");
				}
				goodsMap.put("disstday", DateTimeUtil.getFormatStr(disstdate, DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12));
				goodsMap.put("disedday", DateTimeUtil.getFormatStr(diseddate, DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12));
				
				String minordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(12)).trim())? "1" : formatter.formatCellValue(row.getCell(12)).trim();
				if (!this._isNumber(minordcnt)) {
					throw new CustomException("최소주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("minordcnt", minordcnt);
				String maxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(13)).trim())? "1" : formatter.formatCellValue(row.getCell(13)).trim();
				if (!this._isNumber(maxordcnt)) {
					throw new CustomException("최대주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("maxordcnt", maxordcnt);
				String daymaxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(14)).trim())? "999" : formatter.formatCellValue(row.getCell(14)).trim();
				if (!this._isNumber(daymaxordcnt)) {
					throw new CustomException("1일 최대주문수량이 숫자가 아닙니다.");
				}
				goodsMap.put("daymaxordcnt", daymaxordcnt);
				String permaxordcnt = Util.isEmpty(formatter.formatCellValue(row.getCell(15)).trim())? "999" : formatter.formatCellValue(row.getCell(15)).trim();
				if (!this._isNumber(permaxordcnt)) {
					throw new CustomException("1인당 최대구매수량이 숫자가 아닙니다.");
				}
				goodsMap.put("permaxordcnt", permaxordcnt);
				goodsMap.put("isdisplay", Util.isEmpty(formatter.formatCellValue(row.getCell(16)).trim())? "T" : formatter.formatCellValue(row.getCell(16)).trim());
				goodsMap.put("isfrstsale", "T");
				goodsMap.put("iscncappr", "F");
				goodsMap.put("goodsdivtype", Util.isEmpty(formatter.formatCellValue(row.getCell(17)).replaceAll(" ", ""))? CMConst.GOODS_DIV_NEW : formatter.formatCellValue(row.getCell(17)).replaceAll(" ", ""));
				goodsMap.put("mumemlvtype", commonCodeAllStrMap.getStr("mumemlvtype"));
				goodsMap.put("mugicontype", formatter.formatCellValue(row.getCell(31)).replaceAll(" ", ""));
//				goodsMap.put("iscombdeliv", Util.isEmpty(formatter.formatCellValue(row.getCell(39)).trim())? "F" : formatter.formatCellValue(row.getCell(39)).trim());
				String delividx = formatter.formatCellValue(row.getCell(40)).trim();
				if (Util.isEmpty(delividx)) {
					throw new CustomException("배송정보는 필수입력 항목입니다.");
				} else {
					SOMap delivParams = new SOMap();
					delivParams.put("istrash", "F");
					delivParams.put("delividx", delividx);
					delivParams.put("dealerno", goodsMap.get("dealerno"));
					List<SOMap> delivTemplateList= delivTemplateMapper.selectDelivTemplateList(delivParams);
					if (delivTemplateList == null || delivTemplateList.size() == 0) {
						throw new CustomException("유효한 배송정보가 아닙니다.");
					}
					goodsMap.put("iscombdeliv", delivTemplateList.get(0).get("iscombdeliv"));
					goodsMap.put("delividx", delividx);
				}
				String notifyTplIdx = formatter.formatCellValue(row.getCell(41)).trim();
				if (Util.isEmpty(notifyTplIdx)) {
					throw new CustomException("상품정보고시는 필수입력 항목입니다.");
				}
				SOMap notifyParams = new SOMap();
				notifyParams.put("notifyTplIdx", notifyTplIdx);
				List<SOMap> goodNotifyList = notifyTplItemMapper.selectNotifyTplItemList(notifyParams);
				if (goodNotifyList == null || goodNotifyList.size() == 0) {
					throw new CustomException("유효하지 않은 상품정보고시코드 입니다.");
				}
				goodNotifyList.forEach(m -> {
					m.put("notifydata", "상세설명참고");
				});
				goodsMap.put("goodsnotifylist", goodNotifyList);
				String kcdivtype = Util.isEmpty(formatter.formatCellValue(row.getCell(42)).trim())? CMConst.KC_DIV_TYPE_NON_TARGET : formatter.formatCellValue(row.getCell(42)).trim();
				goodsMap.put("kcdivtype", kcdivtype);
				if (Util.equal(kcdivtype, CMConst.KC_DIV_TYPE_TARGET)) {
					String certNumStr = formatter.formatCellValue(row.getCell(43)).replaceAll(" ", "");
					if (Util.isEmpty(certNumStr)) {
						throw new CustomException("KC 인증대상인 경우 KC인증번호는 필수입력 항목입니다.");
					}
					List<String> certnumList = Arrays.asList(certNumStr.split(","));
					List<SOMap> kccertlist = new ArrayList<SOMap>();
					certnumList.stream().distinct().forEach(certnum -> {
						SOMap kccert = new SOMap();
						kccert.put("kccertno", certnum);
						kccert.put("iscert", "F");
						kccertlist.add(kccert);
					});
					goodsMap.put("kccertlist", kccertlist);
				}
				String aswarmonth = Util.isEmpty(formatter.formatCellValue(row.getCell(44)).trim())? "0" : formatter.formatCellValue(row.getCell(44)).trim();
				if (!this._isNumber(aswarmonth)) {
					throw new CustomException("AS보증기간이 숫자가 아닙니다.");
				}
				goodsMap.put("aswarmonth", aswarmonth);
				String asnotice = formatter.formatCellValue(row.getCell(45));
				if (asnotice.length() > 1000) {
					throw new CustomException("AS 안내문구가 1,000자 이상입니다.");
				}
				goodsMap.put("asnotice", asnotice);
				goodsMap.put("mumembertype", commonCodeAllStrMap.getStr("mumembertype"));
				goodsMap.put("isopenreview", "T");
				goodsMap.put("isepif", Util.isEmpty(formatter.formatCellValue(row.getCell(47)).trim())? "T" : formatter.formatCellValue(row.getCell(47)).trim());
				logger.debug(":: " + JsonUtil.getJsonStringFromMap(goodsMap).toJSONString());
				
				// 상품정보 저장
				goodsMapper.insertGoods(goodsMap);
				
				// 특정대상회원 저장
				List<String> usernoList = goodsMap.getArrayList("usernolist");
				if (usernoList != null && usernoList.size() > 0) {
					memberGoodsMapper.insertMemberGoods(goodsMap);
				}
				
				// KC인증번호 저장
				List<SOMap> kccertList = Util.convertToListSOMap(goodsMap.getArrayList("kccertlist"));
				if (kccertList != null && kccertList.size() > 0) {
					goodsKccertMapper.insertGoodsKccert(goodsMap);
				}
				
				// 상품카테고리 저장
				if (goodsCategoryList != null && goodsCategoryList.size() > 0) {
					goodsCategoryMapper.insertGoodsCategory(goodsMap);
				}
                
                // 상품정보고시 저장
				if (goodNotifyList != null && goodNotifyList.size() > 0) {
					goodsNotifyMapper.insertGoodsNotify(goodsMap);
				}
				
				// 이미지, 상세설명 저장
				String pccontent = formatter.formatCellValue(row.getCell(37));
				if (Util.isEmpty(pccontent.trim())) {
					throw new CustomException("상품상세설명(PC용)은 필수 입력 항목입니다.");
				}
				goodsMap.put("pccontent", pccontent);
				String mobilecontent = formatter.formatCellValue(row.getCell(38));
				if (Util.isEmpty(mobilecontent.trim())) {
					throw new CustomException("상품상세설명(모바일용)은 필수 입력 항목입니다.");
				}
				goodsMap.put("mobilecontent", mobilecontent);
				goodsMap.put("noticecontent", formatter.formatCellValue(row.getCell(35)));
				goodsMap.put("introcontent", formatter.formatCellValue(row.getCell(36)));
				goodsContentMapper.mergeGoodsContent(goodsMap);
				
				// 추가상품 저장
				SOMap goodsDbParams = new SOMap();
				goodsDbParams.put("siteid", params.get("siteid"));
				goodsDbParams.put("istempsave", "F");
				goodsDbParams.put("isdeal", "F");
				goodsDbParams.put("dealerno", params.get("dealerno"));
				goodsDbParams.put("goodsselltype", CMConst.GOODS_SELL_TYPE_SALE);
				goodsDbParams.put("goodsapprtype", CMConst.GOODS_STATUS_APPROVAL);
				String addGoodsCodeStr = formatter.formatCellValue(row.getCell(46)).replaceAll(" ", "");
				if (!Util.isEmpty(addGoodsCodeStr)) {
					List<String> goodsCodeList = Arrays.asList(addGoodsCodeStr.split(",")).stream().distinct().collect(Collectors.toList());
					goodsDbParams.put("goodscodelist", goodsCodeList);
					List<SOMap> validGoodsList = goodsMapper.selectCommonGoodsList(goodsDbParams);
					if (goodsCodeList.size() != validGoodsList.size()) {
						throw new CustomException("추가상품 유효하지 않은 상품코드가 존재합니다.");
					}
					goodsMap.put("addgoodsnolist", validGoodsList.stream().map(m -> m.getStr("goodsno")).collect(Collectors.toList()));
					goodsAdditionMapper.insertGoodsAddition(goodsMap);
				}
				
				// 이미지 파일명 세팅
				String pcimgName = formatter.formatCellValue(row.getCell(32)).trim();
				if (Util.isEmpty(pcimgName)) {
					throw new CustomException("대표이미지(PC) 파일명은 필수 입력 항목입니다.");
				}
				String moimgName = formatter.formatCellValue(row.getCell(34)).trim();
				if (Util.isEmpty(moimgName)) {
					throw new CustomException("대표이미지(모바일) 파일명은 필수 입력 항목입니다.");
				}
				String addimgName = formatter.formatCellValue(row.getCell(33)).trim();
				if (addimgName.split(",").length > 7) {
					throw new CustomException("추가이미지 파일명이 7개 이상입니다.");
				}
				rowImgNameMap.put("goodsno", goodsMap.get("goodsno"));
				rowImgNameMap.put("pcimg", pcimgName);
				rowImgNameMap.put("moimg", moimgName);
				rowImgNameMap.put("addimg", addimgName);
				
				// 옵션항목 저장
				List<String> optionNoList = new ArrayList<String>();
				SOMap goodsOptionMap = new SOMap();
				goodsOptionMap.put("goodsno", goodsMap.get("goodsno"));
				goodsOptionMap.put("authuserid", goodsMap.get("authuserid"));
				String optionItem1Name = formatter.formatCellValue(row.getCell(22)).trim();
				if (Util.isEmpty(optionItem1Name)) {
					throw new CustomException("옵션항목명 1단계는 필수 입력 항목입니다.");
				}
				goodsOptionMap.put("optionname", optionItem1Name);
				goodsOptionMap.put("sort", 1);
				goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
				optionNoList.add(goodsOptionMap.getStr("optionno"));
				
				String optionItem2Name = formatter.formatCellValue(row.getCell(23)).trim();
				if (!Util.isEmpty(optionItem2Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem2Name);
					goodsOptionMap.put("sort", 2);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				String optionItem3Name = formatter.formatCellValue(row.getCell(24)).trim();
				if (!Util.isEmpty(optionItem3Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem3Name);
					goodsOptionMap.put("sort", 3);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				String optionItem4Name = formatter.formatCellValue(row.getCell(25)).trim();
				if (!Util.isEmpty(optionItem4Name)) {
					goodsOptionMap.put("optionno", null);
					goodsOptionMap.put("optionname", optionItem4Name);
					goodsOptionMap.put("sort", 4);
					goodsOptionMapper.mergeGoodsOption(goodsOptionMap);
					optionNoList.add(goodsOptionMap.getStr("optionno"));
				}
				
				// 옵션 목록 세팅
				String[] isMainGoodsArr = formatter.formatCellValue(row.getCell(18)).replaceAll(" ", "").split("\\n");
				String[] isOptDisplayArr = formatter.formatCellValue(row.getCell(19)).replaceAll(" ", "").split("\\n");
				String[] marketpriceArr = formatter.formatCellValue(row.getCell(20)).replaceAll(" ", "").split("\\n");
				String[] priceArr = formatter.formatCellValue(row.getCell(21)).replaceAll(" ", "").split("\\n");
				String[] option1NameArr = formatter.formatCellValue(row.getCell(26)).replaceAll(" ", "").split("\\n");
				String[] option2NameArr = formatter.formatCellValue(row.getCell(27)).replaceAll(" ", "").split("\\n");
				String[] option3NameArr = formatter.formatCellValue(row.getCell(28)).replaceAll(" ", "").split("\\n");
				String[] option4NameArr = formatter.formatCellValue(row.getCell(29)).replaceAll(" ", "").split("\\n");
				String[] stockArr = formatter.formatCellValue(row.getCell(30)).replaceAll(" ", "").split("\\n");
				if (Util.isEmpty(option1NameArr)) {
					throw new CustomException("옵션항목명 1단계는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(isMainGoodsArr)) {
					throw new CustomException("옵션 대표상품여부는 필수 입력 항목입니다.");
				}
				int mainGoodsCnt = Arrays.asList(isMainGoodsArr).stream().distinct().filter(str->"T".equals(str)).collect(Collectors.toList()).size();
				if (mainGoodsCnt == 0) {
					throw new CustomException("옵션 한개의 대표상품은 필수입니다.");
				}
				if (mainGoodsCnt > 1) {
					throw new CustomException("옵션 대표상품이 여러개 있습니다.");
				}
				if (Util.isEmpty(isOptDisplayArr)) {
					throw new CustomException("옵션 노출상태는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(marketpriceArr)) {
					throw new CustomException("옵션 정상가는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(priceArr)) {
					throw new CustomException("옵션 판매가는 필수 입력 항목입니다.");
				}
				if (Util.isEmpty(stockArr)) {
					throw new CustomException("옵션 재고는 필수 입력 항목입니다.");
				}
				if (option1NameArr.length != isMainGoodsArr.length
						|| option1NameArr.length != isOptDisplayArr.length 
						|| option1NameArr.length != marketpriceArr.length
						|| option1NameArr.length != priceArr.length 
						|| option1NameArr.length != stockArr.length) {
					throw new CustomException("옵션설정(대표상품, 옵션상태, 정상가, 판매가, 옵션항목명1단계, 재고) 개수가 일치하지 않습니다.");
				}
				SOMap dealerDbParams = new SOMap();
				dealerDbParams.put("userno", params.get("dealerno"));
				SOMap dealerInfo = dealerMapper.selectPartnsersDetail(dealerDbParams);
				double dealerCommrate = dealerInfo.getDouble("commrate");

				for (int i=0; i<option1NameArr.length; i++) {
					SOMap optionMap = new SOMap();
					double price = Double.parseDouble(priceArr[i]);
					optionMap.put("goodsno", goodsMap.get("goodsno"));
					optionMap.put("authuserid", params.get("authuserid"));
					optionMap.put("optioncode", goodsOptionDetailMapper.selectOptionCode(goodsMap));
					optionMap.put("marketprice", marketpriceArr[i]);
					optionMap.put("price", price);
					optionMap.put("originalprice", Math.round(price - dealerCommrate/100 * price));
					optionMap.put("commrate", dealerCommrate);
					optionMap.put("stockcnt", stockArr[i]);
					optionMap.put("safestockcnt", 0);
					optionMap.put("ismaingoods", isMainGoodsArr[i]);
					optionMap.put("isoptdisplay", isOptDisplayArr[i]);
					optionMap.put("optionno1", optionNoList.get(0));
					optionMap.put("optionno2", (optionNoList.size() > 1 && !Util.isEmpty(optionNoList.get(1)))? optionNoList.get(1):null);
					optionMap.put("optionno3", (optionNoList.size() > 2 && !Util.isEmpty(optionNoList.get(2)))? optionNoList.get(2):null);
					optionMap.put("optionno4", (optionNoList.size() > 3 && !Util.isEmpty(optionNoList.get(3)))? optionNoList.get(3):null);
					optionMap.put("optionnm1", option1NameArr[i]);
					optionMap.put("optionnm2", (option2NameArr.length > i && !Util.isEmpty(option2NameArr[i]))? option2NameArr[i]:null);
					optionMap.put("optionnm3", (option3NameArr.length > i && !Util.isEmpty(option3NameArr[i]))? option3NameArr[i]:null);
					optionMap.put("optionnm4", (option4NameArr.length > i && !Util.isEmpty(option4NameArr[i]))? option4NameArr[i]:null);
					if ("T".equals(isMainGoodsArr[i])) {
						goodsMap.put("marketprice", marketpriceArr[i]);
						goodsMap.put("price", priceArr[i]);
						goodsMapper.updateGoods(goodsMap);
					}
					goodsOptionDetailMapper.insertGoodsOptionDetail(optionMap);
				}

		        // 상품 히스토리 저장 (최초 임시저장)
				goodsMap.put("fstTempSave", "T");
		        historyService.insertGoodsHistory(goodsMap);
		        
		        // 승인 히스토리 저장 (임시저장)
		        goodsApprMapper.insertGoodsApprHist(goodsMap);
		        
				succCnt ++;
				result.put("goodsno", goodsMap.get("goodsno"));
				result.put("rowimgnamemap", rowImgNameMap);
			}
		} catch (CustomException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		result.put("succcnt", succCnt);
		
		return result;
	}

	/**
	 * 상품일괄업로드 엑셀 업로드
     * @param params
	 * @return int
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public int uploadGoodsImage(SOMap params) throws Exception {
		int resultCnt = 0;
		try {
			List<SOMap> goodsExcelMapList = goodsExcelMapMapper.selectGoodsExcelMapList(params);
			if (goodsExcelMapList == null || goodsExcelMapList.size() == 0) {
				throw new CustomException("상품과 매핑된 이미지가 없습니다. 관리자에게 문의바랍니다.");
			}
			List<String> orgnoList = new ArrayList<String>();
			for (SOMap goodsMap : goodsExcelMapList) {
				goodsMap.putAll(params);
				int orgidx = goodsMap.getInt("goodsno");
				String pcimgname = goodsMap.getStr("pcimg");
				if(!Util.isEmpty(pcimgname)) {
					String pcImgType = CMConst.IMG_TYPE_GOODS_IMG_PC_B;
					File file = this._getFile(goodsMap, pcimgname);
					if (file != null) {
						fileService.uploadGoodsImage(file, orgidx, pcImgType);
						goodsMap.put("chgpcimg", "T");
					}
				}
				String moimgname = goodsMap.getStr("moimg");
				if(!Util.isEmpty(moimgname)) {
					String moImgType = CMConst.IMG_TYPE_GOODS_IMG_MO_B;
					File file = this._getFile(goodsMap, moimgname);
					if (file != null) {
						fileService.uploadGoodsImage(file, orgidx, moImgType);
						goodsMap.put("chgmoimg", "T");
					}
				}
				String addImgStr = goodsMap.getStr("addimg");
				if(!Util.isEmpty(addImgStr)) {
					List<String> addImgList = Arrays.asList(addImgStr.split(","));
					for (int i=0; i<addImgList.size(); i++) {
						SOMap addImgTypeKey = new SOMap();
						addImgTypeKey.put("0", CMConst.IMG_TYPE_GOODS_IMG_ADD_B1);
						addImgTypeKey.put("1", CMConst.IMG_TYPE_GOODS_IMG_ADD_B2);
						addImgTypeKey.put("2", CMConst.IMG_TYPE_GOODS_IMG_ADD_B3);
						addImgTypeKey.put("3", CMConst.IMG_TYPE_GOODS_IMG_ADD_B4);
						addImgTypeKey.put("4", CMConst.IMG_TYPE_GOODS_IMG_ADD_B5);
						addImgTypeKey.put("5", CMConst.IMG_TYPE_GOODS_IMG_ADD_B6);
						addImgTypeKey.put("6", CMConst.IMG_TYPE_GOODS_IMG_ADD_B7);
	
						String addImgType = addImgTypeKey.getStr(i+"");
						File file = this._getFile(goodsMap, addImgList.get(i).trim());
						if (file != null) {
							fileService.uploadGoodsImage(file, orgidx, addImgType);
							goodsMap.put("chgaddimg", "T");
						}
					}
				}

	            if(Util.isEmpty(params.get("dealerno"))) {
	    			params.put("authuserid", getAdminId());
	    			params.put("authuserno", getAdminNo());
					goodsMap.put("goodsapprtype", CMConst.GOODS_STATUS_APPROVAL);
	            } else {
	    			params.put("authuserid", getDealerId());
	    			params.put("authuserno", getDealerNo());
					goodsMap.put("goodsapprtype", CMConst.GOODS_STATUS_REQ);
	            	goodsMap.put("contents", "승인요청합니다.");
	            }
				goodsExcelMapper.updateGoodsExcel(params);
	            
				if(Util.equal(goodsMap.getStr("chgpcimg"), "T") && Util.equal(goodsMap.getStr("chgmoimg"), "T")) {
					goodsMap.put("siteid", cs.getStr("siteid"));
					goodsMap.put("istempsave", "F");
					goodsMap.put("fstSave", "T");
					// 상품 승인상태, 판매상태 변경
					goodsMapper.updateStatusImageUpload(goodsMap);
					if (Util.equal(goodsMap.getStr("goodsapprtype"), CMConst.GOODS_STATUS_APPROVAL)) {
						orgnoList.add(goodsMap.getStr("goodsno"));
					}
				}

				// 상품 ERP 전송
				if (orgnoList != null && orgnoList.size() > 0) {
					SOMap ifLogMap = new SOMap();
					ifLogMap.put("siteid", goodsMap.get("siteid"));
					ifLogMap.put("type", CMConst.ERP_GOODS_TYPE);
					ifLogMap.put("orgnolist", orgnoList);
					ifLogMap.put("aud", CMConst.IF_LOG_ADD);
	 				ifLogMapper.insertIfLogERPData(ifLogMap);
				}

	            // 상품 히스토리 저장 (이미지 정상등록시 최초저장, 아니면 수정내용)
				goodsMap.put("authuserid", params.get("authuserid"));
	            historyService.insertGoodsHistory(goodsMap);
	            
	            // 승인 히스토리 저장 (승인완료 및 승인요청)
	            goodsApprMapper.insertGoodsApprHist(goodsMap);
			}
		} catch (CustomException e) {
			logger.error(e.getMessage());
			throw e;
        } catch(Exception e) {
			logger.error(e.getMessage());
            throw new CustomException("상품 이미지 업로드시 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
        }
		
		return resultCnt;
	}
	
	public boolean _isNumber(String value) {
		boolean result = true;
		try {
			int numValue = Integer.parseInt(value);
			result = (numValue % 1 == 0);
		} catch (Exception e){
			result = false;
		}
		return result;
	}
	
	public File _getFile(SOMap params, String imagename) throws Exception {
    	File file = null;
		try {
	    	String filePath = params.getStr("dirstr") + "/" + imagename;
	    	file = new File(filePath);
	    	if (!file.exists()) {
	    		file = null;
	    	}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return file;
	}
}
