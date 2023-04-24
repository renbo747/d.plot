package com.dplot.admin.service.goods;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.CategoryMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.UserMapper;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : JSK
 * @discription : 카테고리관리 ServiceImpl
 * @fileName : CategoryManageServiceImpl.java
 * @date : 2021.11.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.09	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class CategoryManageServiceImpl extends MallBaseService implements CategoryManageService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ERPService erpService;

    /**
     * 기본정보 - 카테고리 전체 조회
     *
     * @return List<SOMap>
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectCategoryList(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        params.put("showall", "t");
        params.put("chkcate", "T");
        List<SOMap> resultList = categoryMapper.selectCategoryList(params);

        // 최상위 부모 Entry Map
        Map<String, List<Map<String, Object>>> allEntryMap = resultList.stream().collect(Collectors.groupingBy(a -> a.get("parent").toString()));

        // 각 댑스별 ListMap
        List<Map<String, Object>> depthThreeList = resultList.stream().filter(a -> "3".equals(a.get("depth").toString())).collect(Collectors.toList());

        List<Map<String, Object>> depthTwoList = resultList.stream().filter(a -> "2".equals(a.get("depth").toString())).collect(Collectors.toList());

        List<Map<String, Object>> depthOneList = resultList.stream().filter(a -> "1".equals(a.get("depth").toString())).collect(Collectors.toList());

        // 3depth 순회하여 부모 idx와 같은 데이터는 children에 저장
        for (Map<String, Object> three : depthThreeList) {
            List<Map<String, Object>> tempList;
            if (allEntryMap.get(three.get("idx").toString()) != null) {
                tempList = allEntryMap.get(three.get("idx").toString());
            } else {
                tempList = new ArrayList<>();
            }
            tempList.add(makeAddButton(three.get("idx").toString()));
            three.put("children", tempList.stream().sorted((m1, m2) -> 
            	"a".equals(m1.get("sort").toString()) ? 1 : "a".equals(m2.get("sort").toString()) ? -1 : 
                Integer.parseInt(m1.get("sort").toString()) - Integer.parseInt(m2.get("sort").toString())).collect(Collectors.toList()));
        }
        Map<String, List<Map<String, Object>>> threeEntryMap = depthThreeList.stream().collect(Collectors.groupingBy(a -> a.get("parent").toString()));

        // 2depth 순회하여 부모 idx와 같은 데이터는 children에 저장
        for (Map<String, Object> two : depthTwoList) {
            List<Map<String, Object>> tempList;
            if (threeEntryMap.get(two.get("idx").toString()) != null) {
                tempList = threeEntryMap.get(two.get("idx").toString());
            } else {
                tempList = new ArrayList<>();
            }
            tempList.add(makeAddButton(two.get("idx").toString()));
            two.put("children", tempList.stream().sorted((m1, m2) -> 
            	"a".equals(m1.get("sort").toString()) ? 1 : "a".equals(m2.get("sort").toString()) ? -1 : 
                Integer.parseInt(m1.get("sort").toString()) - Integer.parseInt(m2.get("sort").toString())).collect(Collectors.toList()));
        }
        Map<String, List<Map<String, Object>>> twoEntryMap = depthTwoList.stream().collect(Collectors.groupingBy(a -> a.get("parent").toString()));

        // 1depth 순회하여 부모 idx와 같은 데이터는 children에 저장
        for (Map<String, Object> one : depthOneList) {
            List<Map<String, Object>> tempList;
            if (twoEntryMap.get(one.get("idx").toString()) != null) {
                tempList = twoEntryMap.get(one.get("idx").toString());
            } else {
                tempList = new ArrayList<>();
            }
            tempList.add(makeAddButton(one.get("idx").toString()));
            one.put("children", tempList.stream().sorted((m1, m2) -> 
            	"a".equals(m1.get("sort").toString()) ? 1 : "a".equals(m2.get("sort").toString()) ? -1 : 
                Integer.parseInt(m1.get("sort").toString()) - Integer.parseInt(m2.get("sort").toString())).collect(Collectors.toList()));
        }

        // 순회하여 저장된 ListMap을 depthOneList에 저장
        depthOneList.add(makeAddButton("0"));
        depthOneList = depthOneList.stream().sorted((m1, m2) -> 
        "a".equals(m1.get("sort").toString()) ? 1 : "a".equals(m2.get("sort").toString()) ? -1 : 
        Integer.parseInt(m1.get("sort").toString()) - Integer.parseInt(m2.get("sort").toString())).collect(Collectors.toList());
        
        return depthOneList;
    }

    @Override
    public SOMap selectCategoryDetail(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        
        SOMap map = new SOMap();
        map.put("detail",categoryMapper.selectCategoryDetail(params));
        
        SOMap fileParams = new SOMap();
		String[] imgtypes = {"IGT092","IGT093"};
		fileParams.put("orgidx", params.get("idx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if("IGT092".equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if("IGT093".equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
		
		if(!files.containsKey("pcimgfile")){
			files.put("pcimgfile", "");
		}
		if(!files.containsKey("mobileimgfile")){
			files.put("mobileimgfile", "");
		}

		map.put("files", files);
        
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateCategoryTree(SOMap params) throws Exception {
		try{
	        categoryMapper.updateCategoryTree(params);
            SOMap logParam = new SOMap();
            logParam.put("siteid", cs.getStr("siteid"));
            logParam.put("aud", "U");
            
            List<Map<String, Object>> list = params.getArrayList("changelist");
            for(Map map: list) {
            	if(map.get("parent").toString().equals(map.get("oriparent").toString())) continue;
            	logParam.put("idx", map.get("idx").toString());
            	erpService.insertCategoryERPData(logParam);
            }
	    }catch(Exception e){
	    	throw new CustomException("카테고리 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertCategory(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        try{
        	categoryMapper.updateCategorySort(params);
        	categoryMapper.insertCategory(params);
        	int idx = params.getInt("idx");
        	
        	if(uploadFile.containsKey("pcimgfile")){
        		MultipartFile pcimgFile = uploadFile.get("pcimgfile");
        		SOMap s3Result = fileService.uploadImage(pcimgFile, idx, CMConst.IMG_TYPE_CATEGORY_IMG_PC);
        	}
        	if(uploadFile.containsKey("mobileimgfile")){
        		MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
        		SOMap s3Result = fileService.uploadImage(mobileimgFile, idx, CMConst.IMG_TYPE_CATEGORY_IMG_MO);
        	}

    		// IFLOG ADD 기록
            SOMap logParam = new SOMap();
            logParam.put("idx", idx);
            logParam.put("siteid", cs.getStr("siteid"));
            logParam.put("aud", "A");
            erpService.insertCategoryERPData(logParam);
	    }catch(Exception e){
	    	throw new CustomException("카테고리 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateCategory(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
    	try{
    		List<Integer> removefile = params.getArrayList("removefile");
    		for (int idx : removefile) {
    			fileService.delete(idx);
    		}
    		
    		int categoryidx = params.getInt("idx");
    		
    		if(uploadFile.containsKey("pcimgfile")){
    			MultipartFile pcimgFile = uploadFile.get("pcimgfile");
    			SOMap s3Result = fileService.uploadImage(pcimgFile, categoryidx, CMConst.IMG_TYPE_CATEGORY_IMG_PC);
    		}
    		if(uploadFile.containsKey("mobileimgfile")){
    			MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
    			SOMap s3Result = fileService.uploadImage(mobileimgFile, categoryidx, CMConst.IMG_TYPE_CATEGORY_IMG_MO);
    		}
    		
    		// pc이미지 변경없이 mobile이미지만 변경했을 시 copy필요
    		if(params.containsKey("copycheck")){
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
    			
    			SOMap s3Result = fileService.uploadImage(multipartFile, categoryidx, CMConst.IMG_TYPE_CATEGORY_IMG_MO);
    		}
    		params.put("siteid", cs.getStr("siteid"));

        	categoryMapper.updateCategorySort(params);
    		categoryMapper.updateCategory(params);
    		
    		// IFLOG UPDATE 기록
            SOMap logParam = new SOMap();
            logParam.put("idx", params.getStr("idx"));
            logParam.put("siteid", cs.getStr("siteid"));
            logParam.put("aud", "U");
            erpService.insertCategoryERPData(logParam);
            
    	}catch(Exception e){
	    	throw new CustomException("카테고리 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int deleteCategory(SOMap params) throws Exception {
        int result = 0;
        
        try{
        	// 하위 노드가 없고, 카테고리에 상품이 없어야 삭제 가능 
            if (!categoryMapper.isSubNodesAndGoodsExists(params)) {
                params.put("istrash", "T");
                result = categoryMapper.updateCategory(params);
            }
            
    		// IFLOG DELETE 기록
            SOMap logParam = new SOMap();
            logParam.put("idx", params.getStr("idx"));
            logParam.put("siteid", cs.getStr("siteid"));
            logParam.put("aud", "D");
            erpService.insertCategoryERPData(logParam);
	    }catch(Exception e){
	    	throw new CustomException("카테고리 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
        return result;
    }

    public Map<String, Object> makeAddButton(String parentIdx) {
        Map<String, Object> map = new HashMap<>();
        map.put("idx", -1);
        map.put("parent", parentIdx);
        map.put("text", "추가");
        map.put("dropDisabled", true);
        map.put("dragDisabled", true);
        map.put("sort", "a");
        map.put("count", "");

        return map;
    }
}
