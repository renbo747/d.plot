package com.dplot.admin.service.configuration;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.exception.CustomException;
import com.dplot.mapper.AdminMapper;
import com.dplot.mapper.AdminMenuMapper;
import com.dplot.mapper.AdminPermissionMapper;
import com.dplot.mapper.ConfigUserMapper;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.GiftMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.mapper.UserTotalMapper;
import com.dplot.util.CryptHash;
import com.dplot.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collector;
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

@Service
public class AdminOperatorServiceImpl extends MallBaseService implements AdminOperatorService {
	private static final Logger logger = LoggerFactory.getLogger(AdminCodeManageServiceImpl.class);
	
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private AdminMenuMapper adminMenuMapper;
    
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;
    
    @Autowired
	private ConfigUserMapper configUserMapper;
    
    /**
     * 운영자 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectOperatorList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        result.put("list", adminMapper.selectOperatorList(params));
        result.put("statelist", adminMapper.selectOperatorState(params));
        result.put("permissionlist", selectOperatorAuth(params));
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap updateOperatorUse(SOMap params) throws Exception{
    	SOMap result = new SOMap();
    	
    	result.put("resCnt", adminMapper.updateOperatorUse(params));
    	
    	return result;
    }
    
    @Override
    public List<Map<String, Object>> selectOperatorExcelList(SOMap params) throws Exception{
    	params.put("siteid", cs.getStr("siteid"));
    	return adminMapper.selectOperatorExcelList(params);
    }
    
    public List<SOMap> selectOperatorAuth(SOMap params) throws Exception{
    	if(params.containsKey("userno")) {
    		params.put("authuserno", params.get("userno"));
    	}
    	SOMap result = new SOMap();
    	
    	List<SOMap> list = adminMenuMapper.selectAdminMenuAuth(params);
    	
    	List<SOMap> resultList = new ArrayList<>();
    	
    	// 각 1depth의 전체체크용 row추가
    	for(SOMap map : list) {
    		if("1".equals(map.getStr("depth"))) {
    			SOMap temp = new SOMap();
    			temp.put("code", map.get("code"));
    			temp.put("name", map.get("name"));
    			temp.put("depth", 0);
    			temp.put("dep1span", map.get("dep1span"));
    			temp.put("dep2span", 0);
    			temp.put("allread", map.get("allread"));
    			temp.put("allwrite", map.get("allwrite"));
    			resultList.add(temp);
    			resultList.add(map);
    		} else {
    			resultList.add(map);
    		}
    	}
    	
    	return resultList;
    }
    
    @Override
    public SOMap checkIdDupl(SOMap params) throws Exception {
    	SOMap result = new SOMap();
    	
    	boolean checkUserId = false;

		String cfgDefaultBlockID = "," + cs.getStr("cfgdefaultblockid").toLowerCase() + ",";
		String checkId = "," + params.getDbStr("userid").toLowerCase() + ",";

		//기본 차단 ID 확인
		if (cfgDefaultBlockID.indexOf(checkId) > -1) {
			checkUserId = true;
		}
		
		//선택 차단 ID 확인
		params.put("siteid", cs.getStr("siteid"));
//		SOMap configUser  = configUserMapper.selectConfigUserBlockId(params);
//		
//		if (Util.isNotEmpty(configUser)) {
//			String blockID = "," + configUser.getDbStr("blockId").toLowerCase() + ",";
//			if (blockID.indexOf(checkId) > -1) {
//				checkUserId = true;
//			}
//		}

		//사용자 ID 확인
		int userCount = userMapper.selectUserCount(params);

		if (userCount > 0) {
			checkUserId = true;
		}
    	
    	result.put("check", checkUserId);
    	
    	return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap insertOperator(SOMap params) throws Exception{
    	SOMap result = new SOMap();
    	
    	try {
    		params.put("pwchange",'T');
    		this.validSignUp(params);
    		
    		// 비밀번호 암호화
			String encPw = CryptHash.hash(params.getDbStr("userpw"));
			params.put("userpw", encPw);
			
			//사용자정보 등록
			params.put("usertype", CMConst.USER_TYPE_ADMIN);
	    	params.put("reguserid", cs.getStr("authuserid"));
			userMapper.insertUser(params);
			
			//사용자번호 세팅
			params.put("userno", params.getDbInt("no"));
			
			// depth의 전체선택용 depth0을 포함한 모든 메뉴리스트에서 depth 0 메뉴를 제거
			List<Map<String, Object>> allPermissionlist = params.getArrayList("permissionlist");
			List<Map<String, Object>> permissionlist = allPermissionlist.stream().filter(menu -> !"0".equals(menu.get("depth").toString())).collect(Collectors.toList());
			params.put("permissionlist", permissionlist);
			
			//사용자종합 등록
    		result.put("resAdminCnt", adminMapper.insertAdmin(params));
    		result.put("resPermissionCnt", adminPermissionMapper.insertPermission(params));
    	} catch(Exception e){
    		logger.error(e.getMessage());
    		throw new CustomException("운영자 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
    	
    	return result;
    }
    
    @Override
    public SOMap selectOperator(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        result.put("info", adminMapper.selectOperator(params));
        result.put("permissionlist", selectOperatorAuth(params));
        
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap modifyOperator(SOMap params) throws Exception {
    	SOMap result = new SOMap();
    	
    	try{
    		this.validSignUp(params);
    		
    		if(params.containsKey("pwchange")){
    			// 비밀번호 암호화
    			String encPw = CryptHash.hash(params.getDbStr("userpw"));
    			params.put("userpw", encPw);
    		} else {
    			params.put("userpw", "");
    		}
    		
			//사용자정보 등록
	    	params.put("authuserid", cs.getStr("authuserid"));
			userMapper.updateUser(params);
			
			// depth의 전체선택용 depth0을 포함한 모든 메뉴리스트에서 depth 0 메뉴를 제거
			List<Map<String, Object>> allPermissionlist = params.getArrayList("permissionlist");
			List<Map<String, Object>> permissionlist = allPermissionlist.stream().filter(menu -> !"0".equals(menu.get("depth").toString())).collect(Collectors.toList());
			params.put("permissionlist", permissionlist);
			
			//사용자종합 등록
    		result.put("resAdminCnt", adminMapper.updateAdmin(params));
    		result.put("resPermissionCnt", adminPermissionMapper.insertOrUpdatePermission(params));
    	} catch(Exception e){
			logger.error(e.getMessage());
			throw new CustomException("운영자 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
		}
    	return result;
    }
    
    /**
	 * 회원가입 필드체크
	 * @param param
	 * @throws Exception
	 */
	private void validSignUp(SOMap params) throws Exception {
		if (Util.isEmpty(params.getDbStr("userid"))) {
			throw new CustomException("아이디를 입력해 주세요.");
		}

		// 등록, 수정시에만 비밀번호 체크하도록 수정
		if(params.containsKey("pwchange")){
			if (Util.isEmpty(params.getDbStr("userpw"))) {
				throw new CustomException("비밀번호를 입력해 주세요.");
			}
			
			if (Util.isEmpty(params.getDbStr("userpw2"))) {
				throw new CustomException("비밀번호확인을 입력해 주세요.");
			}
			
			if(!isPassword(params.getDbStr("userpw"))) {
				throw new CustomException("영문/숫자/특수문자 3가지 이상 조합(최소 8자 이상)으로 해주세요.");
			}
			
			if(!params.getDbStr("userpw").equals(params.getDbStr("userpw2"))){
				throw new CustomException("비밀번호와 비밀번호 확인이 일치하지 않습니다");
			}
		}
		
		if (Util.isEmpty(params.getDbStr("name"))) {
			throw new CustomException("이름을 입력해 주세요.");
		}
		
		//아이디 유효성 확인
		if (!Util.checkInvalidId(params.getDbStr("userid"), "6", "12")) {
			throw new CustomException("아이디는 6~12자의 영문, 숫자만 사용 할 수 있습니다.");
		}
	
		//아이디 중복확인
		if ((boolean) this.checkIdDupl(params).get("check")) {
			throw new CustomException("사용 불가능한 아이디입니다. 다른 아이디를 사용해 주세요.");
		}
	}
	
	/**
	 * 입력한 값이 패스워드 형태인지 체크한다.
	 * 프론트에서 체크하는 것과 같다.
	 * https://regex101.com/ 에서 자바스크립트 정규식을 자바 정규식으로 Code Generator로 바꿀수있음
	 * @param value 최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 조합
	 * @return true, false
	 */
	private boolean isPassword(String value) {
        if (Util.isEmpty(value)) {
            return false;
        }
        
        // 최소 8 자, 하나 이상의 문자, 하나의 숫자, 하나의 특수 문자 조합
        // /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");//. represents single character  
        boolean reg1Valid = p.matcher(value).matches();  

        return reg1Valid;
	}
}
