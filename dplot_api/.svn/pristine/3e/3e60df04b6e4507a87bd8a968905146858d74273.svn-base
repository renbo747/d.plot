package com.dplot.admin.service.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BrandMapper;
import com.dplot.mapper.CategoryMapper;
import com.dplot.mapper.ChargemdMapper;
import com.dplot.mapper.CommonCodeMapper;
import com.dplot.mapper.DealerInfoMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.DelivTemplateMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.GoodsApprMapper;
import com.dplot.mapper.GoodsCategoryMapper;
import com.dplot.mapper.GoodsContentMapper;
import com.dplot.mapper.GoodsHistoryMapper;
import com.dplot.mapper.GoodsKccertMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsNotifyMapper;
import com.dplot.mapper.GoodsOptionDetailMapper;
import com.dplot.mapper.IFLogMapper;
import com.dplot.mapper.NotifyTplMapper;
import com.dplot.util.Util;

/**
 * @discription	: 상품관리 ServiceImpl
 * @fileName	: GoodsManageServiceImpl.java
 * @author		: JSK
 * @date		: 2021.12.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.08	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class GoodsManageServiceImpl extends MallBaseService implements GoodsManageService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsManageServiceImpl.class);

	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private DealerMapper dealerMapper;
	@Autowired
	private DealerInfoMapper dealerInfoMapper;
	@Autowired
	private GoodsHistoryMapper goodsHistoryMapper;
	@Autowired
	private GoodsApprMapper goodsApprMapper;
	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;
	@Autowired
	private GoodsContentMapper goodsContentMapper;
	@Autowired
	private GoodsNotifyMapper goodsNotifyMapper;
	@Autowired
	private GoodsKccertMapper goodsKccertMapper;
	@Autowired
	private GoodsOptionDetailMapper goodsOptionDetailMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private DelivTemplateMapper delivTemplateMapper;
	@Autowired
	private NotifyTplMapper notifyTplMapper;
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private ChargemdMapper chargemdMapper;
	@Autowired
	private CommonCodeMapper commonCodeMapper;
	@Autowired
	private IFLogMapper ifLogMapper;
	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private FileService fileService;

	/**
	 * 조회조건 - 파트너사 목록 조회
	 * @return List<SOMap>
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectPartnerList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();		
		params.put("siteid", cs.getStr("siteid"));
		resultList = dealerMapper.selectPartnersList(params);
		return resultList;
	}
	
	/**
	 * 전체상품목록 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectAllGoodsList(SOMap params) throws Exception {
		SOMap result = new SOMap();

        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        params.put("siteid", cs.getStr("siteid"));
		params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S); 	//이미지구분_상품_PC이미지_소

        List<SOMap> goodsList = goodsMapper.selectAdminGoodsList(params);
        SOMap selltypeCount = goodsMapper.selectAdminGoodsSellTypeCount(params);
        
        boolean isEditDisplay = false;
        if (!StringUtil.isEmpty(params.getStr("ispartner")) && (boolean) params.get("ispartner") == true) {
            int editDisplayCnt = 0;
        	params.put("addauth", CMConst.EDIT_AUTH_ISDISPLAY);
        	editDisplayCnt = dealerInfoMapper.selectDealerAddAuth(params);
        	isEditDisplay = editDisplayCnt > 0? true : false ;
        }
        
        result.put("iseditdisplay", isEditDisplay);
        result.put("list", goodsList);
        result.put("count", selltypeCount);
		return result;
	}
	
	/**
	 * 전체상품목록(엑셀다운로드용) 조회
	 * @param param
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> selectAllGoodsListForExcel(SOMap params) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        params.put("siteid", cs.getStr("siteid"));
		resultList = goodsMapper.selectAdminGoodsListForExcel(params);
		return resultList;
	}

    /**
     * 상품 전시상태 변경
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int updateGoodsDisplay(SOMap params) throws Exception {
		int resultCnt = 0;
        try {
        	if (Util.equal(params.getStr("isdisplay"), "T")) {
        		SOMap stateCount = dealerMapper.selectPartnersDealerstCountByGoodsno(params);
        		if (stateCount.getInt("stop_cnt") > 0) {
        			throw new CustomException("일시중단 상태의 파트너사 상품이 존재합니다.\n확인후 진행해주세요.");
        		} else if (stateCount.getInt("exit_cnt") > 0) {
        			throw new CustomException("휴점 상태의 파트너사 상품이 존재합니다.\n확인후 진행해주세요.");
        		}
        	}
            params.put("siteid", cs.getStr("siteid"));
        	resultCnt = goodsMapper.updateGoodsDisplay(params);
			
	        // ERP LOG INSERT
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_GOODS_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			ifLogMap.put("orgnolist", params.getArrayList("goodsnolist"));
			ifLogMapper.insertIfLogERPData(ifLogMap);
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("상품정보 전시상태 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        return resultCnt;
    }

    /**
     * 상품 판매상태 변경
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int updateGoodsSellType(SOMap params) throws Exception {
		int resultCnt = 0;
        try {
            params.put("siteid", cs.getStr("siteid"));
    		int cnt = goodsMapper.selectTempSaveGoodsCountByGoodsno(params);
    		if (cnt > 0) {
    			throw new CustomException("임시저장 상품은 판매상태 변경이 불가능합니다. \n확인후 진행해주세요.");
    		}
        	resultCnt = goodsMapper.updateGoodsSellType(params);
			
	        // ERP LOG INSERT
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_GOODS_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			ifLogMap.put("orgnolist", params.getArrayList("goodsnolist"));
			ifLogMapper.insertIfLogERPData(ifLogMap);
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("상품정보 판매상태 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        return resultCnt;
    }

    /**
     * 상품삭제
     * - 판매상태 영구종료 변경
     * - 상품 미전시로 변경
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public int updateGoodsTerminate(SOMap params) throws Exception {
		int resultCnt = 0;
        try {
            params.put("siteid", cs.getStr("siteid"));
        	resultCnt = goodsMapper.updateGoodsSellType(params);
        	goodsMapper.updateGoodsDisplay(params);
			
	        // ERP LOG INSERT
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_GOODS_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			ifLogMap.put("orgnolist", params.getArrayList("goodsnolist"));
			ifLogMapper.insertIfLogERPData(ifLogMap);
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("상품삭제시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        return resultCnt;
    }

    /**
     * 상품 승인상태 변경
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int updateGoodsApprType(SOMap params) throws Exception {
		int resultCnt = 0;
        try {
            params.put("siteid", cs.getStr("siteid"));
        	if (Util.isEmpty(params.get("dealerno"))) {
    			params.put("authuserid", getAdminId());
        	} else {
        		params.put("authuserid", getDealerId());
        	}
        	resultCnt = goodsMapper.updateGoodsApprType(params);
        	goodsApprMapper.insertGoodsApprHist(params);
			
	        // ERP LOG INSERT(승인완료 이후부터)
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_GOODS_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_ADD);
			ifLogMap.put("orgnolist", params.getArrayList("goodsnolist"));
			ifLogMap.put("orgno", params.getArrayList("goodsno"));
			if (Util.equal(params.getStr("goodsapprtype"), CMConst.GOODS_STATUS_APPROVAL)) {
				ifLogMapper.insertIfLogERPData(ifLogMap);
			}
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("상품 승인상태 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        return resultCnt;
    }
	
	/**
	 * 상품 처리(승인)이력 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectGoodsApprHistList(SOMap params) throws Exception {
		SOMap result = new SOMap();

		params.put("siteid", cs.getStr("siteid"));
        result.put("list", goodsApprMapper.selectGoodsApprHistList(params));
        result.put("info", goodsMapper.selectAdminGoodsDetail(params));
        
		return result;
	}
	
	/**
	 * 상품이력 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectGoodsHistList(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		result = goodsMapper.selectAdminGoodsDetail(params);
        result.put("goodshistlist", goodsHistoryMapper.selectGoodsHistory(params));
		return result;
	}

    /**
     * 상품정보 일괄수정
     * @param params
     * @param uploadFiles
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int updateGoodsAll(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception {
		int resultCnt = 0;
		SOMap goodsInfo = new SOMap();	// 이력저장용
        try {
			params.put("siteid", cs.getStr("siteid"));
			params.put("authuserid", cs.getStr("authuserid"));
			
			// 상품정보 수정(이미지 일괄변경시 제외)
			if (!Util.equal(params.getStr("ckey"), "image")) {
	        	resultCnt = goodsMapper.updateGoodsAll(params);
			}
        	
			// 기본정보
			if (Util.equal(params.getStr("ckey"), "basic")) {
				// 카테고리 수정
				if(Util.equal(params.getStr("iscategorychange"), "T")) {
					goodsCategoryMapper.deleteGoodsCategory(params);
					List<Integer> goodsNoList = params.getArrayList("goodsnolist");
					if (goodsNoList != null && goodsNoList.size() > 0) {
						goodsInfo.put("chgCate", "T");
						for (int goodsno : goodsNoList) {
							SOMap cateParamMap = new SOMap();
							List<HashMap<String, Object>> goodsCategoryList = params.getArrayList("goodscategorylist");
							cateParamMap.put("goodsno", goodsno);
							cateParamMap.put("goodscategorylist", goodsCategoryList);
							if (goodsCategoryList != null && goodsCategoryList.size() > 0) {
								goodsCategoryMapper.insertGoodsCategory(cateParamMap);
							}
						}
					}
				}
			}
			// 판매정보
			else if (Util.equal(params.getStr("ckey"), "sell")) {
				// 판매가격 변경시 옵션 대표상품 가격 변경
				if(Util.equal(params.getStr("ispricechange"), "T")) {
					goodsOptionDetailMapper.updateOptionDetailMainGoodsPrice(params);
				}
				
			}
			// 이미지정보
			else if (Util.equal(params.getStr("ckey"), "image")) {
				uploadFiles.remove("params");
				List<Integer> goodsNoList = params.getArrayList("goodsnolist");
				if (goodsNoList != null && goodsNoList.size() > 0) {
					// 피씨 대표이미지 수정
					if(Util.equal(params.getStr("ispcreprechange"), "T")) {
						goodsInfo.put("chgPcImg", "T");
						for (int goodsno : goodsNoList) {
							params.put("orgidx", goodsno);
							params.put("filetype", CMConst.FILE_TYPE_IMG);
							params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_B);
							// 파일삭제
							List<SOMap> fileList = fileMapper.selectFileList(params);
							if(fileList != null && fileList.size() > 0) {
								fileService.deleteGoodsImage(fileList.get(0).getInt("idx"));
							}
							// 파일추가
					        for (String key : uploadFiles.keySet()) {
					            MultipartFile file = uploadFiles.get(key);
					            logger.info("File : {}", file);
					        	if (Util.equal(key, CMConst.IMG_TYPE_GOODS_IMG_PC_B)) {
					                logger.debug("File Upload : " + fileService.uploadGoodsImage(file, goodsno, CMConst.IMG_TYPE_GOODS_IMG_PC_B));
					        	}
					        }
						}
					}
					// 모바일 대표이미지 수정
					if(Util.equal(params.getStr("ismoreprechange"), "T")) {
						goodsInfo.put("chgMoImg", "T");
						for (int goodsno : goodsNoList) {
							params.put("orgidx", goodsno);
							params.put("filetype", CMConst.FILE_TYPE_IMG);
							params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_MO_B);
							// 파일삭제
							List<SOMap> fileList = fileMapper.selectFileList(params);
							if(fileList != null && fileList.size() > 0) {
								fileService.deleteGoodsImage(fileList.get(0).getInt("idx"));
							}
							// 파일추가
					        for (String key : uploadFiles.keySet()) {
					            MultipartFile file = uploadFiles.get(key);
					            logger.info("File : {}", file);
					        	if (Util.equal(key, CMConst.IMG_TYPE_GOODS_IMG_MO_B)) {
					                logger.debug("File Upload : " + fileService.uploadGoodsImage(file, goodsno, CMConst.IMG_TYPE_GOODS_IMG_MO_B));
					        	}
					        }
						}
					}
					// 추가이미지 수정
					if(Util.equal(params.getStr("isaddchange"), "T")) {
						goodsInfo.put("chgAddImg", "T");
						String imgtypes[] = {"IGT032", "IGT035", "IGT038", "IGT041", "IGT044", "IGT047", "IGT050", "IGT053", "IGT056", "IGT059"};
						for (int goodsno : goodsNoList) {
							params.put("orgidx", goodsno);
							params.put("filetype", CMConst.FILE_TYPE_IMG);
							params.put("imgtypes", imgtypes);
							// 파일삭제
							List<SOMap> fileList = fileMapper.selectFileList(params);
							if(fileList != null && fileList.size() > 0) {
								for (SOMap fileMap : fileList) {
									fileService.deleteGoodsImage(fileMap.getInt("idx"));
								}
							}
							// 파일추가
					        for (String key : uploadFiles.keySet()) {
					            MultipartFile file = uploadFiles.get(key);
					            logger.info("File : {}", file);
					        	if (ArrayUtils.indexOf(imgtypes, key) > -1) {
					                logger.debug("File Upload : " + fileService.uploadGoodsImage(file, goodsno, key));
					        	}
					        }
						}
					}
					
					// 공지이미지
					if (Util.equal(params.getStr("isnoticechange"), "T")) {
						if (params.get("noticecontent") != null && params.get("noticecontent") != "") {
				        	String content = "";
				        	if ("T".equals(params.get("isusenotice"))) {
				                content = StringEscapeUtils.unescapeHtml(params.get("noticecontent").toString()); // 특수문자 html 태그로 변경
				        	}
				            params.put("noticecontent", content);
				        }
					}
					// 인트로이미지
					if (Util.equal(params.getStr("isintrochange"), "T")) {
				        if (params.get("introcontent") != null && params.get("introcontent") != "") {
				        	String content = "";
				        	if ("T".equals(params.get("isuseintro"))) {
				        		content = StringEscapeUtils.unescapeHtml(params.get("introcontent").toString()); // 특수문자 html 태그로 변경
				        	}
				            params.put("introcontent", content);
				        }
					}
					// PC 상세설명
					if (Util.equal(params.getStr("ispcdetailchange"), "T")) {
				        if (params.get("pccontent") != null && params.get("pccontent") != "") {
				            String content = StringEscapeUtils.unescapeHtml(params.get("pccontent").toString()); // 특수문자 html 태그로 변경
				            params.put("pccontent", content);
				        }
					}
					// 모바일 상세설명
					if (Util.equal(params.getStr("ismodetailchange"), "T")) {
				        if (params.get("mobilecontent") != null && params.get("mobilecontent") != "") {
				            String content = StringEscapeUtils.unescapeHtml(params.get("mobilecontent").toString()); // 특수문자 html 태그로 변경
				            params.put("mobilecontent", content);
				        }
					}
			        // 상품내용 일괄 저장
			        goodsContentMapper.updateAllGoodsContent(params);
				}
			}
			// 상품정보고시
			else if (Util.equal(params.getStr("ckey"), "notify")) {
				List<Integer> goodsNoList = params.getArrayList("goodsnolist");
				// 상품정보고시 수정
				if (Util.equal(params.getStr("isnotifychange"), "T")) {
					goodsInfo.put("chgNotify", "T");
					goodsNotifyMapper.deleteGoodsNotify(params);
					if (goodsNoList != null && goodsNoList.size() > 0) {
						for (int goodsno : goodsNoList) {
							SOMap notifyParamMap = new SOMap();
							List<HashMap<String, Object>> goodsNotifyList = params.getArrayList("notifytempitemlist");
							notifyParamMap.put("goodsno", goodsno);
							notifyParamMap.put("goodsnotifylist", goodsNotifyList);
							if (goodsNotifyList != null && goodsNotifyList.size() > 0) {
								goodsNotifyMapper.insertGoodsNotify(notifyParamMap);
							}
						}
					}
				}
				
				// KC 인증대상 수정(인증목록, 첨부파일 삭제)
				if (Util.equal(params.getStr("iskcdivtypechange"), "T")) {
					goodsInfo.put("chgKccert", "T");
					goodsInfo.put("chgKcAttach", "T");
					goodsKccertMapper.deleteGoodsKccert(params);
					for (int goodsno : goodsNoList) {
						params.put("orgidx", goodsno);
						params.put("filetype", CMConst.FILE_TYPE_ATTACH);
						params.put("imgtype", CMConst.IMG_TYPE_GOODS_KC_CERT);
						// 파일삭제
						List<SOMap> fileList = fileMapper.selectFileList(params);
						if(fileList != null && fileList.size() > 0) {
							for (SOMap file : fileList) {
								fileService.delete(file.getInt("idx"));
							}
							
						}
					}
				}
				// - KC인증 대상인 경우 KC 인증목록 추가
				if (Util.equal(params.getStr("iskccertchange"), "T") && Util.equal(params.getStr("kcdivtype"), CMConst.KC_DIV_TYPE_TARGET)) {
					if (goodsNoList != null && goodsNoList.size() > 0) {
						goodsInfo.put("chgNotify", "T");
						for (int goodsno : goodsNoList) {
							SOMap kcParamMap = new SOMap();
							List<HashMap<String, Object>> kccertList = params.getArrayList("kccertlist");
							kcParamMap.put("goodsno", goodsno);
							kcParamMap.put("authuserid", params.get("authuserid"));
							kcParamMap.put("kccertlist", kccertList);
							if (kccertList != null && kccertList.size() > 0) {
								goodsKccertMapper.insertGoodsKccert(kcParamMap);
							}
						}
					}
				}
				// - KC인증 대상인 경우 KC 첨부파일 추가
				if (Util.equal(params.getStr("iskcfilechange"), "T") && Util.equal(params.getStr("kcdivtype"), CMConst.KC_DIV_TYPE_TARGET)) {
					for (int goodsno : goodsNoList) {
						params.put("orgidx", goodsno);
						params.put("imgtype", CMConst.IMG_TYPE_GOODS_KC_CERT);
						// 파일추가
				        for (String key : uploadFiles.keySet()) {
				            MultipartFile file = uploadFiles.get(key);
				            logger.info("File : {}", file);
				        	if (key.contains(CMConst.IMG_TYPE_GOODS_KC_CERT)) {
				                logger.debug("File Upload : " + fileService.uploadAttach(file, goodsno, CMConst.IMG_TYPE_GOODS_KC_CERT));
				        	}
				        }
					}
				}
			}
			
			// 상품정보수정 이력저장
			List<Integer> goodsNoList = params.getArrayList("goodsnolist");
			if (goodsNoList != null && goodsNoList.size() > 0) {
				for (int goodsno : goodsNoList) {
					goodsInfo.put("goodsno", goodsno);
					//historyService.insertGoodsHistory(goodsInfo);
				}
			}
			
	        // ERP LOG INSERT
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_GOODS_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			ifLogMap.put("orgnolist", goodsNoList);
			ifLogMapper.insertIfLogERPData(ifLogMap);
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("상품정보 일괄수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        return resultCnt;
    }
}
