package com.dplot.admin.service.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.ShopEx45GoodsMapper;
import com.dplot.mapper.ShopEx45Mapper;

/**
 * @discription	: 전시영역4 ServiceImpl
 * @fileName	: ShopExFourServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.06
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.06	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class ShopExFourServiceImpl extends MallBaseService implements ShopExFourService {
    private static final Logger logger = LoggerFactory.getLogger(ShopExFourServiceImpl.class);

    @Autowired
    private FileService fileService;
    
    @Autowired
    private ShopEx45Mapper shopEx45Mapper;
    
    @Autowired
    private ShopEx45GoodsMapper shopEx45GoodsMapper;
    
    @Autowired
    private FileMapper fileMapper;
    
    @Autowired
    private GoodsMapper goodsMapper;
    
	@Override
	public SOMap selectShopExFourList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN004");
		params.put("pcimgtype", CMConst.IMG_TYPE_SHOPPING_EXFOUR_PC);
		params.put("moimgtype", CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO);
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();

        result.put("list", shopEx45Mapper.selectShopEx45List(params));
        result.put("state", shopEx45Mapper.selectShopEx45Cnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveShopExFour(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN004");

		try {
			shopEx45Mapper.insertShopEx45(params);
			// 이미지 타입 변경 
			if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("ex45idx"), CMConst.IMG_TYPE_SHOPPING_EXFOUR_PC);
                result.put(CMConst.IMG_TYPE_SHOPPING_EXFOUR_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("ex45idx"), CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO);
                result.put(CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO, s3Result);
            }
            
            @SuppressWarnings("unchecked")
			HashMap<String, String> goodslist = (HashMap<String, String>)params.get("goodslist");
            
            if(goodslist != null && goodslist.size() > 0)
            	shopEx45GoodsMapper.insertShopEx45Goods(params);  
            
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("전시영역4 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectShopExFourExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN004");
		return shopEx45Mapper.selectShopEx45ExcelList(params);
	}
	
	@Override
	public SOMap selectShopExFourDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap reviewInfo = shopEx45Mapper.selectShopEx45Detail(params);

		// 이미지타입 변경 필요
    	SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_SHOPPING_EXFOUR_PC,CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO};
		fileParams.put("orgidx", params.get("ex45idx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_SHOPPING_EXFOUR_PC.equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if(CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO.equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
		
		List<SOMap> goodsAllList = shopEx45GoodsMapper.selectShopEx45GoodsList(params);
		SOMap tempParams = new SOMap();
		List<String> goodsList = goodsAllList.stream().map(m -> m.getStr("goodsno")).collect(Collectors.toList());
		tempParams.put("goodsnolist", goodsList);
		tempParams.put("siteid", cs.getStr("siteid"));
		tempParams.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S); 	//이미지구분_상품_PC이미지_소
		
		List<SOMap> goodsInfoList = goodsMapper.selectCommonGoodsList(tempParams);
		
		List<SOMap> sortGoodsList = new ArrayList<>();
		
		goodsAllList.forEach(m -> {
			goodsInfoList.forEach(m2 -> {
				if(m.getStr("goodsno").equals(m2.getStr("goodsno"))) {
					SOMap temp = new SOMap(m2);
					temp.put("keyword", m.getStr("keyword"));
					temp.put("sortnum", m.getStr("sortnum"));
					sortGoodsList.add(temp);
				} 
			});			
		});
		
		result.put("goodslist", sortGoodsList);
		
    	result.put("files", files);
		result.put("info", reviewInfo);
			
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifyShopExFour(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap resultMap = new SOMap();
		params.put("moduserid", cs.getStr("authuserid"));
		params.put("extype", "EXN004");
		try {
			// 파일 삭제
    		List<Integer> deletefile = params.getArrayList("deletefile");
            for (int i = 0; i < deletefile.size(); i++) {
                int idx = deletefile.get(i);
                fileService.delete(idx);
            }
            
            // 파일 저장
            if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("ex45idx"), CMConst.IMG_TYPE_SHOPPING_EXFOUR_PC);
                resultMap.put(CMConst.IMG_TYPE_SHOPPING_EXFOUR_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("ex45idx"), CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO);
                resultMap.put(CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO, s3Result);
            }
            
            // pc이미지 변경없이 mobile이미지만 변경했을 시 copy필요
            if(params.containsKey("copycheck")) {
            	SOMap temp = new SOMap();
            	int idx = params.getInt("copyidx");
            	temp.put("idx", idx);
            	SOMap result = fileMapper.selectFile(temp);
            	if(result == null) {
        			throw new  Exception("파일이 존재하지 않습니다");
        		}

        		byte[] byteImage = fileService.get(idx);
        		
        		if(byteImage == null || byteImage.length == 0) {
        			throw new  Exception("파일이 존재하지 않습니다");
        		}
        		
        		String contentType = new Tika().detect(byteImage);
        		MultipartFile multipartFile = new MockMultipartFile(result.getDbStr("imgfname"), result.getDbStr("imgforiname"), contentType, byteImage);

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("ex45idx"), CMConst.IMG_TYPE_SHOPPING_EXFOUR_MO);
            }
            shopEx45Mapper.updateShopEx45(params);
			
            @SuppressWarnings("unchecked")
			List<SOMap> goodslist = (List<SOMap>) params.get("goodslist");

            shopEx45GoodsMapper.deleteShopEx45Goods(params);
            if(goodslist != null && !goodslist.isEmpty()) {
				shopEx45GoodsMapper.insertShopEx45Goods(params);
            }
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("전시영역4 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		

		return resultMap;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateShopExFour(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("moduserid", cs.getStr("authuserid"));
		params.put("extype", "EXN004");
		
		try {
			shopEx45Mapper.updateShopEx45(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("전시영역4 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap selectShopExFourCheck(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN004");
		
		SOMap result = new SOMap();
		
		result.put("msg", params.getStr("overmsg"));
		if("T".equals(params.getStr("isdisplay"))) {
			if(params.containsKey("idxlist")) {
				int check = shopEx45Mapper.selectDisplayCheckCntList(params);
//				if(check > 1) {
//					result.put("msg", "동일 기간에 노출중인 영역이 1건 이상 존재합니다. 진행 하시겠습니까?");
//				}
			} else {
				params.put("maxcheck", "Y");
				int check =  shopEx45Mapper.selectDisplayCheckCnt(params).getInt("total");
//				if(check >= 1) {
//					result.put("msg", "동일 기간에 노출중인 영역이 1건 이상 존재합니다. 진행 하시겠습니까?");
//				}
			}
		} else {
			int total =  shopEx45Mapper.selectDisplayCheckCnt(params).getInt("total");
			if(total == 0) {
				result.put("msg", params.getStr("zeromsg")+" 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
	}
}
