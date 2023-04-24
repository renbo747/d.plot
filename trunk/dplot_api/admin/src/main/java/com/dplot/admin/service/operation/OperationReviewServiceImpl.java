package com.dplot.admin.service.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.admin.service.promotion.AdminCouponServiceImpl;
import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BoardPostMapper;
import com.dplot.mapper.ComOrderGoodsMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsReviewMapper;
import com.dplot.mapper.ReviewNotiMapper;
import com.dplot.util.ServletRequestInfoUtil;

/**
 * @discription	: 리뷰 ServiceImpl
 * @fileName	: OperationReviewServiceImpl.java
 * @author		: LKW
 * @date		: 2022.03.03
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.03	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class OperationReviewServiceImpl extends MallBaseService implements OperationReviewService {
    private static final Logger logger = LoggerFactory.getLogger(OperationReviewServiceImpl.class);
	
    @Autowired
    private GoodsReviewMapper goodsReviewMapper;
    
    @Autowired
    private ReviewNotiMapper reviewNotiMapper;

    @Autowired
    private GoodsMapper goodsMapper;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private ComOrderGoodsMapper comOrderGoodsMapper;
    
	@Override
	public SOMap selectReviewList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();
        
        result.put("list", goodsReviewMapper.selectOperationReviewList(params));
        result.put("count", goodsReviewMapper.selectOperationReviewCnt(params));
        
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectReviewExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return goodsReviewMapper.selectOperationReviewExcelList(params);
	}
	
//	@Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
//	public SOMap saveReview(SOMap params) throws Exception {
//		SOMap result = new SOMap();
//		params.put("userno", cs.getStr("authuserno"));
//
//		try {
//			goodsReviewMapper.insertGoodsReivew(params);
//		} catch(Exception e){
//	        logger.error(e.getMessage());
//	        throw new CustomException("운영관리 리뷰 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
//	    }
//		
//		return result;
//	}	
	
	@Override
	public SOMap selectReviewDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap reviewInfo = goodsReviewMapper.selectReviewDetail(params);
		
		params.put("goodsno", reviewInfo.getStr("goodsno"));
		params.put("imgtype", "IGT001");
		
		SOMap goodsInfo = goodsMapper.selectAdminGoodsDetailAll(params);
		goodsInfo.put("disdate", reviewInfo.getStr("disdate"));
		goodsInfo.put("disdaterange", reviewInfo.getStr("disdaterange"));
		reviewInfo.put("notilist", reviewNotiMapper.selectReviewNotiList(params));
		
		params.put("orderidx", reviewInfo.getStr("orderidx"));
		params.put("ordgdidx", reviewInfo.getStr("ordgdidx"));
		params.put("separator", " / ");
		List<SOMap> orderInfo = comOrderGoodsMapper.selectAdminOrderGoodsList(params);

    	SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_REVIEW_IMG,CMConst.IMG_TYPE_REVIEW_MOV};
		fileParams.put("orgidx", params.get("reviewidx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		List<Map<String,Object>> reviewImgFile = new ArrayList<>();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_REVIEW_IMG.equals(m.get("imgtype"))){
				reviewImgFile.add(m);
			}else if(CMConst.IMG_TYPE_REVIEW_MOV.equals(m.get("imgtype"))){
				if(CMConst.FILE_TYPE_IMG.equals(m.get("filetype"))) {
					files.put("videoimgfile", m);
				} else {
					files.put("reviewvideofile", m);
				}
				
			}
		});
		files.put("reviewimgfile", reviewImgFile);
		result.put("orderinfo", orderInfo.get(0));
    	result.put("files", files);
		result.put("info", reviewInfo);
		result.put("goodsinfo", goodsInfo);
			
		// 리뷰용 파일 조회 추가 필요
		
		
		return result;
	}
	
	@Override
	public SOMap selectReviewNotiList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();

        result.put("list", reviewNotiMapper.selectReviewNotiList(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifyReview(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("moduserid", cs.getStr("authuserid"));
    	params.put("siteid", cs.getStr("siteid"));
		try {
			goodsReviewMapper.modifyReview(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("운영관리 리뷰 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap selectReviewCheck(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap result = new SOMap();
		
		result.put("msg", "저장 하시겠습니까?");
		if("T".equals(params.getStr("isbest")) && "T".equals(params.getStr("isdisplay"))) {
			params.put("maxcheck", "Y");
			int check =  goodsReviewMapper.selectBestCnt(params);
			if(check >= 5) {
				result.put("msg", "동일 기간에 노출중인 베스트 리뷰가 5건 이상 존재합니다. 진행 하시겠습니까?");
			}
		} else {
			int check =  goodsReviewMapper.selectBestCnt(params);
			if(check == 0) {
				result.put("msg", "수정 처리 시, 노출되는 베스트 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
	}
}
