package com.dplot.admin.service.goods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;

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
import com.dplot.common.service.util.HistoryService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BrandMapper;
import com.dplot.mapper.CateTemplateMapper;
import com.dplot.mapper.CategoryMapper;
import com.dplot.mapper.ChargemdMapper;
import com.dplot.mapper.ConfigCompanyMapper;
import com.dplot.mapper.DealGoodsMapper;
import com.dplot.mapper.DealerDelivMapper;
import com.dplot.mapper.DealerInfoMapper;
import com.dplot.mapper.DelivTemplateMapper;
import com.dplot.mapper.GoodsAdditionMapper;
import com.dplot.mapper.GoodsApprMapper;
import com.dplot.mapper.GoodsCategoryMapper;
import com.dplot.mapper.GoodsContentMapper;
import com.dplot.mapper.GoodsGrpMapper;
import com.dplot.mapper.GoodsKccertMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsMemoMapper;
import com.dplot.mapper.GoodsNotifyMapper;
import com.dplot.mapper.GoodsOptionDetailMapper;
import com.dplot.mapper.GoodsOptionMapper;
import com.dplot.mapper.IFLogMapper;
import com.dplot.mapper.IsolationFareMapper;
import com.dplot.mapper.MemberGoodsMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.NotifyTplItemMapper;
import com.dplot.mapper.NotifyTplMapper;
import com.dplot.mapper.OptionErpMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.util.HttpConnectionUtil;
import com.dplot.util.Util;

/**
 * @discription	: 상품등록 ServiceImpl
 * @fileName	: GoodsRegistServiceImpl.java
 * @author		: JSK
 * @date		: 2021.11.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.09	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class GoodsRegistServiceImpl extends MallBaseService implements GoodsRegistService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsRegistServiceImpl.class);

	@Autowired
	private HistoryService historyService;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsApprMapper goodsApprMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;
	@Autowired
	private CateTemplateMapper cateTemplateMapper;
	@Autowired
	private ChargemdMapper chargemdMapper;
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private MemberGoodsMapper memberGoodsMapper;
	@Autowired
	private DelivTemplateMapper delivTemplateMapper;
	@Autowired
	private DealerDelivMapper dealerDelivMapper;
	@Autowired
	private IsolationFareMapper delivIsolationMapper;
	@Autowired
	private ConfigCompanyMapper configCompanyMapper;
	@Autowired
	private DealerInfoMapper dealerInfoMapper;
	@Autowired
	private NotifyTplMapper notifyTplMapper;
	@Autowired
	private NotifyTplItemMapper notifyTplItemMapper;
	@Autowired
	private GoodsNotifyMapper goodsNotifyMapper;
	@Autowired
	private GoodsMemoMapper goodsMemoMapper;
	@Autowired
	private GoodsAdditionMapper goodsAdditionMapper;
	@Autowired
	private GoodsKccertMapper goodsKccertMapper;
	@Autowired
	private GoodsGrpMapper goodsGrpMapper;
	@Autowired
	private GoodsContentMapper goodsContentMapper;
	@Autowired
	private GoodsOptionMapper goodsOptionMapper;
	@Autowired
	private GoodsOptionDetailMapper goodsOptionDetailMapper;
	@Autowired
	private OptionErpMapper optionErpMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private DealGoodsMapper dealGoodsMapper;
	@Autowired
	private IFLogMapper ifLogMapper;
	
	@Autowired
	private FileService fileService;
	
	@Resource(name="propertiesFactory")
	private Properties prop;

	/**
	 * 기본정보 - 파트너사 목록 조회
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectPartnerList() throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		SOMap params = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		resultList = userMapper.selectPartnersList(params);
		return resultList;
	}

	/**
	 * 기본정보 - 카테고리명 목록 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectCategorynameList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		
		params.put("siteid", cs.getStr("siteid"));
		resultList = categoryMapper.selectCategorynameList(params);
		return resultList;
	}

	/**
	 * 기본정보 - 카테고리 템플릿 목록 조회
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectCateTemplateList() throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		SOMap params = new SOMap();

		params.put("siteid", cs.getStr("siteid"));
		params.put("userno", cs.getInt("authuserno"));
		resultList = cateTemplateMapper.selectCateTemplateList(params);
		return resultList;
	}

	/**
	 * 기본정보 - 카테고리 템플릿 저장
	 * @param params
	 * @return int
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {CustomException.class, Exception.class})
	public int saveCategoryTemplate(SOMap params) throws CustomException {
		int resultCnt = 0;

        try {
			params.put("userno", cs.getInt("authuserno"));
			// isstar="T" 템플릿 삭제
			if ("T".equals(params.get("isstar"))) {
				resultCnt = cateTemplateMapper.deleteCateTemplate(params);
			}
			// isstar="F" 템플릿 추가
			else if ("F".equals(params.get("isstar"))) {
				int dupCnt = cateTemplateMapper.selectCateTamplateDupCnt(params);
				if (dupCnt > 0) {
					throw new CustomException("중복된 카테고리 템플릿이 존재합니다.");
				}
				resultCnt = cateTemplateMapper.insertCateTemplate(params);
			}
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("카테고리 템플릿 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
		return resultCnt;
	}

	/**
	 * 기본정보 - 카테고리 목록 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectCategoryList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		
		params.put("siteid", cs.getStr("siteid"));
		resultList = categoryMapper.selectCategoryList(params);
		return resultList;
	}

	/**
	 * 기본정보 - 담당MD 목록 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectChargemdList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		resultList = chargemdMapper.selectChargemdList(params);
		return resultList;
	}

	/**
	 * 기본정보 - 브랜드목록 목록 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectBrandList(SOMap params) throws Exception {
		SOMap result = new SOMap();

        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        params.put("siteid", cs.getStr("siteid"));

        result.put("list", brandMapper.selectBrandList(params));
        result.put("listcount", brandMapper.selectBrandListCount(params));
		return result;
	}

	/**
	 * 판매정보 - 회원정보 조회
	 * @param param
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectUserInfoList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		params.put("siteid", cs.getStr("siteid"));
		List<String> useridlist = Util.convertToListSOMap(params.getArrayList("useridlist")).stream().map(m->m.getStr("userid")).collect(Collectors.toList());
		params.put("useridlist", useridlist);
		resultList = memberMapper.selectAdminMemberList(params);
		return resultList;
	}

	/**
	 * 옵션정보 - 연결상품목록 조회
	 * @param param
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectOptionErpList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		resultList = optionErpMapper.selectOptionErpList(params);
		return resultList;
	}
	
	/**
	 * 배송정보 - 배송템플릿목록 조회
	 * @param param
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectDelivTemplateList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();

        params.put("siteid", cs.getStr("siteid"));
		resultList = delivTemplateMapper.selectDelivTemplateList(params);
		return resultList;
	}

	/**
	 * 배송정보 - 배송템플릿상세 조회
	 * @param param
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectDelivTemplateDetail(SOMap params) throws Exception {
		SOMap resultMap = new SOMap();
		resultMap = delivTemplateMapper.selectDelivTemplateDetail(params);
		return resultMap;
	}

	/**
	 * 배송정보 - 배송템플릿 저장
	 * @param params
	 * @return int
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public int saveDelivTemplate(SOMap params) throws Exception {
		int resultCnt = 0;
		
		try {
			params.put("authuserid", cs.getStr("authuserid"));
			resultCnt = delivTemplateMapper.mergeDelivTemplate(params);
		} catch (Exception e) {
            logger.error(e.getMessage());
			throw new CustomException("배송템플릿 저장중 오류가 발생하였습니다. 관리자에게 문의해주시기 바랍니다.");
		}
		return resultCnt;
	}

	/**
	 * 배송정보 - 반품택배사 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectRtnLogisList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		
        params.put("siteid", cs.getStr("siteid"));
		resultList = dealerDelivMapper.selectRtnLogisList(params);
		return resultList;
	}
	
	/**
	 * 배송정보 - 개별반품택배사 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectRtnManLogisList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		
		params.put("siteid", cs.getStr("siteid"));
		resultList = dealerDelivMapper.selectRtnManLogisList(params);
		return resultList;
	}

	/**
	 * 배송정보 - 제주/도서산간지역 목록 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectIsolationList(SOMap params) throws Exception {
		SOMap result = new SOMap();

        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        result.put("list", delivIsolationMapper.selectIsolationList(params));
        result.put("listcount", delivIsolationMapper.selectIsolationListCount(params));
		return result;
	}

	/**
	 * 배송정보 - 사업자주소 조회
	 * @param param
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectBizAddr(SOMap params) throws Exception {
		SOMap resultMap = new SOMap();

		// 업체번호가 있는경우 해당 업체의 주소, 없는경우 사업자(base)주소
		if (StringUtil.isEmpty(params.getStr("dealerno"))) {
	        params.put("siteid", cs.getStr("siteid"));
			resultMap = configCompanyMapper.selectCompanyAddr(params);
		} else {
			resultMap = dealerInfoMapper.selectPartnerAddr(params);
		}
		return resultMap;
	}

	/**
	 * 상품정보고시 - 정보고시템플릿목록 조회
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectNotifyTplList() throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		
		SOMap params = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
		resultList = notifyTplMapper.selectNotifyTplList(params);
		return resultList;
	}
	
	/**
	 * 상품정보고시 - 정보고시템플릿항목 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectNotifyTplItemList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();
		resultList = notifyTplItemMapper.selectNotifyTplItemList(params);
		return resultList;
	}

	/**
	 * 상품정보 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectGoodsDetail(SOMap params) throws Exception {
		SOMap resultMap = new SOMap();

		params.put("siteid", cs.getStr("siteid"));
		params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S); 	//이미지구분_상품_PC이미지_소(추가상품, 리뷰묶어보기상품 조회시 적용)
		// 상품정보 조회
		SOMap goodsInfo = goodsMapper.selectAdminGoodsDetail(params);
		String[] basicColumn= {"isdeal", "istempsave", "ispbgoods", "dealerno", "goodsno", "repregoodsno", "goodscode", "goodsname"
							 , "summary", "keyword", "brandidx", "brandname", "isnobrand", "mdifidx", "mdsabun", "empname", "muappchtype"
							 , "isdealname", "regdate", "moddate", "goodsapprtype", "goodsapprtypename", "dealername", "delivinfo"};
		String[] sellColumn = {"istaxfree", "goodsselltype", "goodsselltypename", "disstdate", "dissthour", "disstmin", "diseddate", "disedhour", "disedmin", "marketprice"
							 , "price", "minordcnt", "maxordcnt", "daymaxordcnt", "permaxordcnt", "isdisplay", "isfrstsale", "iscncappr", "goodsdivtype", "selltarget", "mumemlvtype"};
		String[] imageColumn = {"mugicontype"};
		String[] delivColumn = {"delividx", "iscombdeliv"};
		String[] notifyColumn = {"kcdivtype"};
		String[] etcColumn = {"isopenreview", "mumembertype", "aswarmonth", "asnotice", "isepif"};
		SOMap basicInfo = new SOMap();
		SOMap sellInfo = new SOMap();
		SOMap imageInfo = new SOMap();
		SOMap delivInfo = new SOMap();
		SOMap notifyInfo = new SOMap();
		SOMap etcInfo = new SOMap();
		for (String key : goodsInfo.keySet()) {
			if (ArrayUtils.indexOf(basicColumn, key) > -1) {
				basicInfo.put(key, goodsInfo.get(key));
			} else if (ArrayUtils.indexOf(sellColumn, key) > -1) {
				sellInfo.put(key, goodsInfo.get(key));
			} else if (ArrayUtils.indexOf(imageColumn, key) > -1) {
				imageInfo.put(key, goodsInfo.get(key));
			} else if (ArrayUtils.indexOf(delivColumn, key) > -1) {
				delivInfo.put(key, goodsInfo.get(key));
			} else if (ArrayUtils.indexOf(notifyColumn, key) > -1) {
				notifyInfo.put(key, goodsInfo.get(key));
			} else if (ArrayUtils.indexOf(etcColumn, key) > -1) {
				etcInfo.put(key, goodsInfo.get(key));
			}
		}
		resultMap.put("basicInfo", basicInfo);
		resultMap.put("sellInfo",  sellInfo);
		resultMap.put("imageInfo",  imageInfo);
		resultMap.put("delivInfo",  delivInfo);
		resultMap.put("notifyInfo",  notifyInfo);
		resultMap.put("etcInfo",  etcInfo);
		resultMap.put("editauth",  goodsInfo.get("editauth"));
		// 파트너사 목록 조회
		List<SOMap> partnerlist = userMapper.selectPartnersList(params);
		if ("F".equals(basicInfo.getStr("ispbgoods"))) {
			boolean noneMatch = partnerlist.stream().noneMatch(m -> basicInfo.getStr("dealerno").equals(m.getStr("no")));
			if (noneMatch) {
				SOMap dealerMap = new SOMap();
				dealerMap.put("no", basicInfo.getStr("dealerno"));
				dealerMap.put("name", basicInfo.getStr("dealername"));
				partnerlist.add(dealerMap);
			}
		}
		resultMap.put("partnerlist", partnerlist);
		// 상품별 카테고리 목록 조회
		resultMap.put("goodscategorylist", goodsCategoryMapper.selectGoodsCategoryList(params));
		// 상품별 특정회원 목록 조회
		resultMap.put("goodsmemberlist", memberGoodsMapper.selectGoodsMemberList(params));
		// 상품별 옵션정보 조회
		resultMap.put("optionitemlist", goodsOptionMapper.selectGoodsOptionList(params));
		List<SOMap> optionlist = goodsOptionDetailMapper.selectGoodsOptionDetailList(params);
		if (optionlist != null && optionlist.size() > 0) {
			for (SOMap optionMap : optionlist) {
				optionMap.put("linkedlist", optionErpMapper.selectOptionErpList(optionMap));
				if (Util.equal(basicInfo.getStr("ispbgoods"), "T")) {
					double price = optionMap.getDouble("price");
					double originalprice = optionMap.getDouble("originalprice");
					double marginrate = 0;
					
					if (!Util.isEmpty("price") && !Util.isEmpty("originalprice") && price > 0) {
						marginrate = (double)Math.round((price - (originalprice * 1.1))/price * 100 * 10)/10;
					}
					optionMap.put("marginrate", marginrate);
				}
			}
		}
		resultMap.put("optionlist", optionlist);
		params.put("dealerno", goodsInfo.get("dealerno"));
		// 배송템플릿 목록 조회
		resultMap.put("delivtemplist", delivTemplateMapper.selectDelivTemplateList(params));
		// 상품별 KC인증목록 조회
		resultMap.put("kccertlist", goodsKccertMapper.selectGoodsKccertList(params));
		// 정보고시템플릿목록 조회
		resultMap.put("notifytemplist", notifyTplMapper.selectNotifyTplList(params));
		// 정보고시템플릿항목 조회
		resultMap.put("notifytempitemlist", goodsNotifyMapper.selectGoodsNotifyList(params));
		// 리뷰묶어보기 상품 조회
		resultMap.put("goodsgrplist", goodsGrpMapper.selectGoodsGrpList(params));
		// 추가상품목록 조회
		resultMap.put("goodsadditionlist", goodsAdditionMapper.selectGoodsAdditionList(params));
		// 상품별메모목록 조회
		resultMap.put("goodsmemolist", goodsMemoMapper.selectGoodsMemoList(params));
		// 업로드파일목록 조회
		resultMap.put("files", selectUploadFileList(params));
		// 상품내용 조회
		resultMap.put("goodscontent",  goodsContentMapper.selectGoodsContent(params));
		
		return resultMap;
	}

	/**
	 * 업로드파일 목록 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public SOMap selectUploadFileList(SOMap params) throws Exception {
		SOMap resultMap = new SOMap();
		
		String[] pcimgtypes = new String[] {
			CMConst.IMG_TYPE_GOODS_IMG_PC_B	// 이미지구분_상품_PC이미지_대
		};
		String[] moimgtypes = new String[] {
			CMConst.IMG_TYPE_GOODS_IMG_MO_B	// 이미지구분_상품_Mobile이미지_대
		};
		String[] addimgtypes = new String[] {
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B1,  // 이미지구분_상품 추가 이미지_대1
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B2,  // 이미지구분_상품 추가 이미지_대2
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B3,  // 이미지구분_상품 추가 이미지_대3
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B4,  // 이미지구분_상품 추가 이미지_대4
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B5,  // 이미지구분_상품 추가 이미지_대5
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B6,  // 이미지구분_상품 추가 이미지_대6
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B7,  // 이미지구분_상품 추가 이미지_대7
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B8,  // 이미지구분_상품 추가 이미지_대8
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B9,  // 이미지구분_상품 추가 이미지_대9
			CMConst.IMG_TYPE_GOODS_IMG_ADD_B10  // 이미지구분_상품 추가 이미지_대10
		};
		String[] kccerttypes = new String[] {
			CMConst.IMG_TYPE_GOODS_KC_CERT		// 이미지구분_상품 KC 인증서
		};
		
		List<String> imgtypes = new ArrayList<String>();
		imgtypes.addAll(Arrays.asList(pcimgtypes));
		imgtypes.addAll(Arrays.asList(moimgtypes));
		imgtypes.addAll(Arrays.asList(addimgtypes));
		imgtypes.addAll(Arrays.asList(kccerttypes));
		params.put("imgtypes", imgtypes.toArray());
		params.put("orgidx", params.get("goodsno"));
		SOMap fileMap = fileService.selectFileList(params);
		
		List<SOMap> fileList = fileMap.getArrayList("uploadedfile");
		List<SOMap> addimgfilelist = new ArrayList<SOMap>();
		List<SOMap> kcfilelist = new ArrayList<SOMap>();
		for (SOMap file : fileList) {
			if (Arrays.asList(pcimgtypes).contains(file.getStr("imgtype"))) {
				resultMap.put("pcrepreimgfile", file);
			} else if (Arrays.asList(moimgtypes).contains(file.getStr("imgtype"))) {
				resultMap.put("morepreimgfile", file);
			} else if (Arrays.asList(addimgtypes).contains(file.getStr("imgtype"))) {
				addimgfilelist.add(file);
			} else if (Arrays.asList(kccerttypes).contains(file.getStr("imgtype"))) {
				String[] imagyExtentionArr = {"jpg", "jpeg", "png"};
				String fileExtention = file.getStr("imgforiname").substring(file.getStr("imgforiname").indexOf(".")+1);
				file.put("isimage", false);
				if (ArrayUtils.indexOf(imagyExtentionArr, fileExtention) > -1) {
					file.put("isimage", true);
				}
				kcfilelist.add(file);
			}
		}
		resultMap.put("addimgfilelist", addimgfilelist);
		resultMap.put("kcfilelist", kcfilelist);
		
		return resultMap;
	}
	
	/**
	 * 상품 저장
	 * @param params
	 * @param uploadFiles
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap saveGoods(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception {
		SOMap resultMap = new SOMap();
		int resultCnt = 0;

		try {
			// 0. 파라미터 세팅
			params.put("siteid", cs.getStr("siteid"));
        	if (isAdmin()) {
    			params.put("authuserid", getAdminId());
    			params.put("authuserno", getAdminNo());
        	} else if (isDealer()) {
        		params.put("authuserid", getDealerId());
        		params.put("authuserno", getDealerNo());
        	}
	        boolean iscopy = Util.equal(params.getStr("iscopy"), "T")? true : false;
	        // ERP LOG 파라미터
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_GOODS_TYPE);
			
			// 1. 상품번호 없으면 상품 신규등록
			if (StringUtil.isEmpty(params.getStr("goodsno"))) {
				// 1-1. 상품코드 채번
				params.put("goodscode", goodsMapper.selectGoodsCode());
				// 1-2. 상품테이블 저장
				resultCnt = goodsMapper.insertGoods(params);
				// 1-3. 상품이력 저장
				historyService.insertGoodsHistory(params);
				// 1-4. 승인상태 히스토리 저장
				goodsApprMapper.insertGoodsApprHist(params);
				// ERP LOG 파라미터 세팅 (A)
				ifLogMap.put("orgno", params.get("goodsno"));
				ifLogMap.put("aud", CMConst.IF_LOG_ADD);
	 			// ERP LOG INSERT 승인완료 이후만 로그 INSERT
	 			if (Util.equal(params.getStr("goodsapprtype"), CMConst.GOODS_STATUS_APPROVAL)) {
	 				ifLogMapper.insertIfLogERPData(ifLogMap);
	 			}
			}
			// 2. 상품번호 있으면 상품정보 갱신
			else {
				// 2-1. 상품이력 저장
				historyService.insertGoodsHistory(params);
				// 2-2. 상품테이블 갱신
				resultCnt = goodsMapper.updateGoods(params);
				if (!params.get("orggoodsapprtype").equals(params.get("goodsapprtype"))) {
					// 2-3. 승인상태 히스토리 저장
					goodsApprMapper.insertGoodsApprHist(params);
				}
				// ERP LOG 파라미터 세팅(처음 승인완료시 A, 아니면 U)
				ifLogMap.put("orgno", params.get("goodsno"));
				// ERP LOG INSERT 
	 			if (Util.equal(params.getStr("goodsapprtype"), CMConst.GOODS_STATUS_APPROVAL)) {
	 				if (!Util.equal(params.getStr("orggoodsapprtype"), CMConst.GOODS_STATUS_APPROVAL)) {
						ifLogMap.put("aud", CMConst.IF_LOG_ADD);
	 				} else {
						ifLogMap.put("aud", CMConst.IF_LOG_UPDATE);
	 				}
	 				ifLogMapper.insertIfLogERPData(ifLogMap);
	 			}
			}
 			
			// 3. 카테고리 저장
			List<HashMap<String, Object>> goodsCategoryList = params.getArrayList("goodscategorylist");
			// 3-1. 기존 카테고리 삭제
			goodsCategoryMapper.deleteGoodsCategory(params);
			// 3-2. 카테고리 추가
			if (goodsCategoryList != null && goodsCategoryList.size() > 0) {
				for (int i=0; i<goodsCategoryList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(goodsCategoryList.get(i));
					paramMap.put("sort", i);
					paramMap.put("goodsno", params.get("goodsno"));
					goodsCategoryMapper.insertGoodsCategory(paramMap);
				}
			}
			
			// 4. 특정회원 저장
			// 4-1. 기존 특정회원목록 삭제
			memberGoodsMapper.deleteMemberGoods(params);
			// 4-2. 판매대상이 특정회원인 경우 추가
			if (Util.equal("SP", params.getStr("selltarget"))) {
				List<HashMap<String, Object>> memberGoodsList = params.getArrayList("goodsmemberlist");
				if (memberGoodsList != null && memberGoodsList.size() > 0) {
					for (int i=0; i<memberGoodsList.size(); i++) {
						SOMap paramMap = new SOMap();
						paramMap.putAll(memberGoodsList.get(i));
						paramMap.put("goodsno", params.get("goodsno"));
						paramMap.put("authuserid", params.get("authuserid"));
						memberGoodsMapper.insertMemberGoods(paramMap);
					}
				}
			}
			
			// 5. 상품정보고시 저장 
			// 5-1. 기존 상품정보고시목록 삭제
			goodsNotifyMapper.deleteGoodsNotify(params);
			// 5-2. 상품정보고시 추가
			List<HashMap<String, Object>> goodsNotifyList = params.getArrayList("notifytempitemlist");
			if (goodsNotifyList != null && goodsNotifyList.size() > 0) {
				for (int i=0; i<goodsNotifyList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(goodsNotifyList.get(i));
					paramMap.put("goodsno", params.get("goodsno"));
					goodsNotifyMapper.insertGoodsNotify(paramMap);
				}
			}
			
			// 6. KC인증목록 저장
			// 6-1. 기존 KC인증목록 삭제
			goodsKccertMapper.deleteGoodsKccert(params);
			// 6-2. KC인증 대상인 경우 추가
			if (Util.equal(params.getStr("kcdivtype"), CMConst.KC_DIV_TYPE_TARGET)) {
				List<HashMap<String, Object>> kccertList = params.getArrayList("kccertlist");
				if (kccertList != null && kccertList.size() > 0) {
					for (int i=0; i<kccertList.size(); i++) {
						SOMap paramMap = new SOMap();
						paramMap.putAll(kccertList.get(i));
						paramMap.put("goodsno", params.get("goodsno"));
						paramMap.put("authuserid", params.get("authuserid"));
						goodsKccertMapper.insertGoodsKccert(paramMap);
					}
				}
			}
	
			// 7. 리뷰묶어보기 상품 저장
			// 7-1. 기존 리뷰묶어보기 상품목록 삭제
			goodsGrpMapper.deleteGoodsGrp(params);
			// 7-2. 리뷰묶어보기 상품 추가
			List<HashMap<String, Object>> goodsGrpList = params.getArrayList("goodsgrplist");
			if (goodsGrpList != null && goodsGrpList.size() > 0) {
				for (int i=0; i<goodsGrpList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(goodsGrpList.get(i));
					paramMap.put("maingoodsno", params.get("goodsno"));
					paramMap.put("subgoodsno", paramMap.get("goodsno"));
					goodsGrpMapper.insertGoodsGrp(paramMap);
				}
			}
	
			// 8. 추가상품목록 저장
			// 8-1. 기존 추가상품목록 삭제
			goodsAdditionMapper.deleteGoodsAddition(params);
			// 8-2. 추가상품목록 추가
			List<HashMap<String, Object>> goodsAdditionList = params.getArrayList("goodsadditionlist");
			if (goodsAdditionList != null && goodsAdditionList.size() > 0) {
				for (int i=0; i<goodsAdditionList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(goodsAdditionList.get(i));
					paramMap.put("maingoodsno", params.get("goodsno"));
					paramMap.put("subgoodsno", paramMap.get("goodsno"));
					goodsAdditionMapper.insertGoodsAddition(paramMap);
				}
			}
	
			// 9. 상품별메모 저장
			List<HashMap<String, Object>> goodsMemoList = params.getArrayList("goodsmemolist");
			if (goodsMemoList != null && goodsMemoList.size() > 0) {
				for (int i=0; i<goodsMemoList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(goodsMemoList.get(i));
					paramMap.put("goodsno", params.get("goodsno"));
					paramMap.put("authuserno", params.get("authuserno"));
					goodsMemoMapper.mergeGoodsMemo(paramMap);
				}
			}
			
			// 10. 파일처리
			// 10-1. 파일 업로드
			uploadFiles.remove("params");
			if (iscopy) {
				String[] addimgtypes = {
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B1,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B2,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B3,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B4,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B5,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B6,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B7,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B8,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B9,
					CMConst.IMG_TYPE_GOODS_IMG_ADD_B10
				};
				List<SOMap> copyfilelist = Util.convertToListSOMap(params.getArrayList("copyfilelist"));
				boolean isPcImgChange = true;
				boolean isMoImgChange = true;
				if (copyfilelist != null && copyfilelist.size() > 0) {
					for (SOMap copyfileMap: copyfilelist) {
						String fileKey = copyfileMap.getStr("key");
						if (Util.equal(fileKey, CMConst.IMG_TYPE_GOODS_IMG_PC_B)) {
							isPcImgChange = false;
							fileService.copyGoodsImage(copyfileMap.getInt("fileidx"), params.getInt("goodsno"), fileKey);
						} else if (Util.equal(fileKey, CMConst.IMG_TYPE_GOODS_IMG_MO_B)) {
							isMoImgChange = false;
							fileService.copyGoodsImage(copyfileMap.getInt("fileidx"), params.getInt("goodsno"), fileKey);
						} else if (ArrayUtils.indexOf(addimgtypes, fileKey) > -1) {
							fileService.copyGoodsImage(copyfileMap.getInt("fileidx"), params.getInt("goodsno"), fileKey);
						} else if (fileKey.indexOf(CMConst.IMG_TYPE_GOODS_KC_CERT) > -1) {
							fileService.copyAttach(copyfileMap.getInt("fileidx"), params.getInt("goodsno"), CMConst.IMG_TYPE_GOODS_KC_CERT);
						}
					}
				}
				if (uploadFiles != null && !uploadFiles.isEmpty()) {
			        for (String key : uploadFiles.keySet()) {
			            MultipartFile file = uploadFiles.get(key);
						if (key.contains(CMConst.IMG_TYPE_GOODS_KC_CERT)) {
			                logger.debug("File Upload : " + fileService.uploadAttach(file, params.getInt("goodsno"), CMConst.IMG_TYPE_GOODS_KC_CERT));
						} else {
							if ((Util.equal(key, CMConst.IMG_TYPE_GOODS_IMG_PC_B) && isPcImgChange)
							 || (Util.equal(key, CMConst.IMG_TYPE_GOODS_IMG_MO_B) && isMoImgChange)
							 || ArrayUtils.indexOf(addimgtypes, key) > -1) {
								logger.debug("File Upload : " + fileService.uploadGoodsImage(file, params.getInt("goodsno"), key));
							}
						}
			        }
				}
			} else {
		        for (String key : uploadFiles.keySet()) {
		            MultipartFile file = uploadFiles.get(key);
		            logger.info("File : {}", file);
		        	if (key.contains(CMConst.IMG_TYPE_GOODS_KC_CERT)) {
		                logger.debug("File Upload : " + fileService.uploadAttach(file, params.getInt("goodsno"), CMConst.IMG_TYPE_GOODS_KC_CERT));
		        	} else {
		                logger.debug("File Upload : " + fileService.uploadGoodsImage(file, params.getInt("goodsno"), key));
		        	}
		        }
		        // PC이미지와 동일(PC이미지가 이미 저장되어 있는 경우)
		        if ((boolean) params.get("issamepcimg") && !StringUtil.isEmpty(params.getStr("sameimgidx"))) {
		        	fileService.copyGoodsImage(params.getInt("sameimgidx"), params.getInt("goodsno"), CMConst.IMG_TYPE_GOODS_IMG_MO_B);
		        }
		        
		        // 10-2. 파일삭제
		        List<HashMap<String, Object>> deletefilelist = (List<HashMap<String, Object>>) params.get("deletefilelist");
		        for (HashMap<String, Object> deletefile : deletefilelist) {
		        	fileService.deleteGoodsImage(Integer.parseInt(deletefile.get("idx").toString()));
		        }
			}
	        
	        // 11. 상품내용 저장
	        // 11-1. 에디터 상품내용 세팅
	        if (params.get("noticecontent") != null && params.get("noticecontent") != "") {
	        	String content = "";
	        	if ("T".equals(params.get("isusenotice"))) {
	                content = StringEscapeUtils.unescapeHtml(params.get("noticecontent").toString()); // 특수문자 html 태그로 변경
	        	}
	            params.put("noticecontent", content);
	        }
	        if (params.get("introcontent") != null && params.get("introcontent") != "") {
	        	String content = "";
	        	if ("T".equals(params.get("isuseintro"))) {
	        		content = StringEscapeUtils.unescapeHtml(params.get("introcontent").toString()); // 특수문자 html 태그로 변경
	        	}
	            params.put("introcontent", content);
	        }
	        if (params.get("pccontent") != null && params.get("pccontent") != "") {
	            String content = StringEscapeUtils.unescapeHtml(params.get("pccontent").toString()); // 특수문자 html 태그로 변경
	            params.put("pccontent", content);
	        }
	        if (params.get("mobilecontent") != null && params.get("mobilecontent") != "") {
	            String content = StringEscapeUtils.unescapeHtml(params.get("mobilecontent").toString()); // 특수문자 html 태그로 변경
	            params.put("mobilecontent", content);
	        }
	        // 11-2. 상품내용 저장
	        goodsContentMapper.mergeGoodsContent(params);
	        
			// 12. 옵션 저장
	        // 12-1. 기존 옵션항목/옵션상세 삭제
	        if (!iscopy) {
	 			List<HashMap<String, Object>> delOptionnoList = params.getArrayList("deloptionnolist");
	 			if (delOptionnoList != null && delOptionnoList.size() > 0) {
	 				goodsOptionMapper.updateGoodsOptionUse(params);
	 			}
	 			List<HashMap<String, Object>> delOptioncodeList = params.getArrayList("deloptioncodelist");
	 			if (delOptioncodeList != null && delOptioncodeList.size() > 0) {
	 				// 삭제할 옵션상세내용의 연결된 ERP MAP 삭제
	 				optionErpMapper.deleteOptionErp(params);
	 				goodsOptionDetailMapper.updateGoodsOptionDetailUse(params);
	 			}
	        }
 			// 12-2. 옵션항목 추가
 			SOMap optionNoMap = new SOMap();
 			List<HashMap<String, Object>> optionItemList = params.getArrayList("optionitemlist");
 			// SORT 순으로 오름차순
 			optionItemList = optionItemList.stream()
	            .sorted((o1, o2) -> o1.get("sort").toString().compareTo(o2.get("sort").toString()))
	            .collect(Collectors.toList());
 			if (optionItemList != null && optionItemList.size() > 0) {
 				for (int i=0; i<optionItemList.size(); i++) {
 					SOMap paramMap = new SOMap();
 					paramMap.putAll(optionItemList.get(i));
 					paramMap.put("optionno", iscopy? null:paramMap.get("optionno"));
 					paramMap.put("goodsno", params.get("goodsno"));
 					paramMap.put("authuserid", params.get("authuserid"));
 					goodsOptionMapper.mergeGoodsOption(paramMap);
 					optionNoMap.put("optionno"+(i+1), paramMap.get("optionno"));
 				}
 			}
 			// 12-2. 옵션상세 추가
 			List<HashMap<String, Object>> optionList = params.getArrayList("optionlist");
 			if (optionList != null && optionList.size() > 0) {
 				for (int i=0; i<optionList.size(); i++) {
 					SOMap paramMap = new SOMap();
 					paramMap.putAll(optionList.get(i));
 					paramMap.put("goodsno", params.get("goodsno"));
 					for (String key : optionNoMap.keySet()) {
 						paramMap.put(key, optionNoMap.get(key));
 					}
 					paramMap.put("ispbgoods", params.get("ispbgoods"));
 					paramMap.put("authuserid", params.get("authuserid"));
 	
 					if (!Util.equal(params.getStr("ispbgoods"), "T")) {
 	 					int originalprice = 0;
 	 					int price = paramMap.getInt("price");
 	 					double commrate = paramMap.getDouble("commrate");
 	 					originalprice = (int) Math.round(price - commrate/100 * price);
 	 					paramMap.put("originalprice", originalprice);
 					} else {
 	 					// 옵션상세내용의 연결된 ERP MAP 삭제 후 재등록
 	 					optionErpMapper.deleteOptionErp(paramMap);
 	 					List<SOMap> linkedList = Util.convertToListSOMap(paramMap.getArrayList("linkedlist"));
 	 					paramMap.put("erpoptlist", linkedList);
 					}
 					
 					if (iscopy || StringUtil.isEmpty(paramMap.getStr("optioncode"))) {
 						paramMap.put("optioncode", goodsOptionDetailMapper.selectOptionCode(paramMap));
 						goodsOptionDetailMapper.insertGoodsOptionDetail(paramMap);
 					} else {
 						goodsOptionDetailMapper.updateGoodsOptionDetail(paramMap);
 					}
 					List<SOMap> erpoptList = Util.convertToListSOMap(paramMap.getArrayList("erpoptlist"));
 					if (erpoptList != null && erpoptList.size()>0) {
 						optionErpMapper.insertOptionErp(paramMap);
 					}
 				}
 			}
		} catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("상품 등록 및 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
		
		resultMap.put("resultCnt", resultCnt);
		resultMap.put("goodsno", params.get("goodsno"));
		return resultMap;
	}

	/**
	 * 상품 KC인증여부 확인 (단순 KC인증번호가 유효한지 만을 체크함)
	 * @param param
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap goodsKcCertification(SOMap param){
		String kcUrl = prop.getProperty("kc.certification.url");
		String authKey = prop.getProperty("kc.certification.auth.key");

		String url = String.format("%s?conditionKey=certNum&conditionValue=%s", kcUrl, param.get("kccertno"));
		Map<String, Object> header = new HashMap<>();
		header.put("Content-Type", "application/x-www-form-urlencoded");
		header.put("authKey", authKey);
		SOMap result = new SOMap();

		Map<String, Object> httpResultMap;
		try {
			httpResultMap = HttpConnectionUtil.httpRequest(url, "GET", null, header);
			if(httpResultMap != null && "200".equals(httpResultMap.get("httpCode").toString())){
				List<Map<String, String>> resultList = (List<Map<String, String>>) httpResultMap.get("resultData");
				result.put("code", (resultList.size() > 0) ? "success" : "fail");
			} else {
				result.put("code", "fail");
			}

		} catch(Exception e){
			result.put("code", "error");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 딜상품등록 - 구성상품목록 조회
	 * @param param
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectConstGoodsList(SOMap params) throws Exception {
		List<SOMap> resultList = new ArrayList<SOMap>();

        params.put("siteid", cs.getStr("siteid"));
		resultList = goodsMapper.selectAdminConstGoodsList(params);
		return resultList;
	}
	
	/**
	 * 딜상품등록 - 상품별 구성상품목록 조회(엑셀다운로드용)
	 * @param param
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> selectGoodsConstGoodsListForExcel(SOMap params) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        params.put("siteid", cs.getStr("siteid"));
		resultList = dealGoodsMapper.selectGoodsConstGoodsListForExcel(params);
		return resultList;
	}

	/**
	 * 딜상품정보 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectDealGoodsDetail(SOMap params) throws Exception {
		SOMap resultMap = new SOMap();

		params.put("siteid", cs.getStr("siteid"));
		params.put("goodsno", params.get("dealno"));
		params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S); 	//이미지구분_상품_PC이미지_소
		// 상품정보 조회
		SOMap goodsInfo = goodsMapper.selectAdminGoodsDetail(params);
		String[] basicColumn= {"isdeal", "istempsave", "goodsno", "goodscode", "goodsname", "keyword"
							 , "disstdate", "dissthour", "disstmin", "diseddate", "disedhour", "disedmin"
							 , "isdisplay", "goodsselltype", "goodsselltypename", "mdifidx", "mdsabun", "empname", "muappchtype"
							 , "isdealname", "regdate", "moddate", "goodsapprtype", "goodsapprtypename"};
		String[] etcColumn = {"isopenreview", "mumembertype"};
		SOMap basicInfo = new SOMap();
		SOMap etcInfo = new SOMap();
		for (String key : goodsInfo.keySet()) {
			if (ArrayUtils.indexOf(basicColumn, key) > -1) {
				basicInfo.put(key, goodsInfo.get(key));
			} else if (ArrayUtils.indexOf(etcColumn, key) > -1) {
				etcInfo.put(key, goodsInfo.get(key));
			}
		}
		resultMap.put("basicInfo", basicInfo);
		resultMap.put("etcInfo",  etcInfo);
		// 상품별 카테고리 목록 조회
		resultMap.put("goodscategorylist", goodsCategoryMapper.selectGoodsCategoryList(params));
		// 상품별 구성상품목록 조회
		resultMap.put("constgoodslist", dealGoodsMapper.selectGoodsConstGoodsList(params));
		// 상품별메모목록 조회
		resultMap.put("goodsmemolist", goodsMemoMapper.selectGoodsMemoList(params));
		// 업로드파일목록 조회
		resultMap.put("files", selectUploadFileList(params));
		// 상품내용 조회
		resultMap.put("goodscontent",  goodsContentMapper.selectGoodsContent(params));
		
		return resultMap;
	}

	
	/**
	 * 딜상품 저장
	 * @param params
	 * @param uploadFiles
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap saveDealGoods(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception {
		SOMap resultMap = new SOMap();
		int resultCnt = 0;

		try {
			// 0. 파라미터 세팅
			params.put("siteid", cs.getStr("siteid"));
			params.put("goodsno", params.get("dealno"));
        	if (isAdmin()) {
    			params.put("authuserid", getAdminId());
    			params.put("authuserno", getAdminNo());
        	} else if (isDealer()) {
        		params.put("authuserid", getDealerId());
        		params.put("authuserno", getDealerNo());
        	}
			// 1. 상품번호 없으면 상품 신규등록
			if (StringUtil.isEmpty(params.getStr("goodsno"))) {
				// 1-1. 상품코드 채번
				params.put("goodscode", goodsMapper.selectGoodsCode());
				// 1-2. 상품테이블 저장
				resultCnt = goodsMapper.insertGoods(params);
				// 1-3. 상품이력 저장
				historyService.insertGoodsHistory(params);
				// 1-4. 승인상태 히스토리 저장
				goodsApprMapper.insertGoodsApprHist(params);
			}
			// 2. 상품번호 있으면 상품정보 갱신
			else {
				// 2-1. 상품이력 저장
				historyService.insertGoodsHistory(params);
				// 2-3. 상품테이블 갱신
				resultCnt = goodsMapper.updateGoods(params);
				if (!params.get("orggoodsapprtype").equals(params.get("goodsapprtype"))) {
					// 2-3. 승인상태 히스토리 저장
					goodsApprMapper.insertGoodsApprHist(params);
				}
			}
			
			// 3. 카테고리 저장
			List<HashMap<String, Object>> goodsCategoryList = params.getArrayList("goodscategorylist");
			// 3-1. 기존 카테고리 삭제
			goodsCategoryMapper.deleteGoodsCategory(params);
			// 3-2. 카테고리 추가
			if (goodsCategoryList != null && goodsCategoryList.size() > 0) {
				for (int i=0; i<goodsCategoryList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(goodsCategoryList.get(i));
					paramMap.put("sort", i);
					paramMap.put("goodsno", params.get("goodsno"));
					goodsCategoryMapper.insertGoodsCategory(paramMap);
				}
			}
			
			// 4. 구성상품 목록 저장
			params.put("dealno", params.get("goodsno"));
			List<HashMap<String, Object>> constGoodsList = params.getArrayList("constgoodslist");
			// 3-1. 기존 구성상품 삭제
			dealGoodsMapper.deleteConstGoods(params);
			if (constGoodsList != null && constGoodsList.size() > 0) {
				for (int i=0; i<constGoodsList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(constGoodsList.get(i));
					paramMap.put("dealno", params.get("dealno"));
					dealGoodsMapper.insertConstGoods(paramMap);
				}
			}
			
			// 5. 상품별메모 저장
			List<HashMap<String, Object>> goodsMemoList = params.getArrayList("goodsmemolist");
			if (goodsMemoList != null && goodsMemoList.size() > 0) {
				for (int i=0; i<goodsMemoList.size(); i++) {
					SOMap paramMap = new SOMap();
					paramMap.putAll(goodsMemoList.get(i));
					paramMap.put("goodsno", params.get("goodsno"));
					paramMap.put("authuserno", params.get("authuserno"));
					goodsMemoMapper.mergeGoodsMemo(paramMap);
				}
			}
			
			// 6. 파일처리
			// 6-1. 파일 업로드
			uploadFiles.remove("params");
	        for (String key : uploadFiles.keySet()) {
	            MultipartFile file = uploadFiles.get(key);
	            logger.info("File : {}", file);
	            logger.debug("File Upload : " + fileService.uploadGoodsImage(file, params.getInt("goodsno"), key));
	        }
	        // 대표상품 이미지와 동일
	        if (Util.equal(params.getStr("ismaingoodsimage"), "T") && !StringUtil.isEmpty(params.getStr("sameimgidx"))) {        	
	        	SOMap fileParams = new SOMap();
	        	fileParams.put("idx", params.getInt("sameimgidx"));
	    		SOMap pcFile = fileService.selectFile(fileParams);
	    		if(pcFile != null) {
	    			SOMap file = (SOMap) pcFile.get("file");
	            	fileService.copyGoodsImage(file.getInt("parentidx"), params.getInt("goodsno"), CMConst.IMG_TYPE_GOODS_IMG_PC_B);
	    		}
	        }
	        // 6-2. 파일삭제
	        List<HashMap<String, Object>> deletefilelist = (List<HashMap<String, Object>>) params.get("deletefilelist");
	        for (HashMap<String, Object> deletefile : deletefilelist) {
	        	fileService.deleteGoodsImage(Integer.parseInt(deletefile.get("idx").toString()));
	        }
	        
	        // 7. 상품내용 저장
	        // 7-1. 에디터 상품내용 세팅
	        if (params.get("noticecontent") != null && params.get("noticecontent") != "") {
	        	String content = "";
	        	if ("T".equals(params.get("isusenotice"))) {
	                content = StringEscapeUtils.unescapeHtml(params.get("noticecontent").toString()); // 특수문자 html 태그로 변경
	        	}
	            params.put("noticecontent", content);
	        }
	        if (params.get("introcontent") != null && params.get("introcontent") != "") {
	        	String content = "";
	        	if ("T".equals(params.get("isuseintro"))) {
	        		content = StringEscapeUtils.unescapeHtml(params.get("introcontent").toString()); // 특수문자 html 태그로 변경
	        	}
	            params.put("introcontent", content);
	        }
	        if (params.get("pccontent") != null && params.get("pccontent") != "") {
	            String content = StringEscapeUtils.unescapeHtml(params.get("pccontent").toString()); // 특수문자 html 태그로 변경
	            params.put("content", content);
	        }
	        if (params.get("mobilecontent") != null && params.get("mobilecontent") != "") {
	            String content = StringEscapeUtils.unescapeHtml(params.get("mobilecontent").toString()); // 특수문자 html 태그로 변경
	            params.put("mobilecontent", content);
	        }
	        // 7-2. 상품내용 저장
	        goodsContentMapper.mergeGoodsContent(params);
		} catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("딜상품 등록 및 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
		
		resultMap.put("resultCnt", resultCnt);
		resultMap.put("goodsno", params.get("goodsno"));
		return resultMap;
	}
}
