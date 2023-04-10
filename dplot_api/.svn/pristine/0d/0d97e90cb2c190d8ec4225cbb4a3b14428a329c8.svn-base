package com.dplot.admin.service.promotion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.ConfigMapper;
import com.dplot.mapper.ReserveConfigMapper;
import com.dplot.mapper.ReserveMapper;
import com.dplot.mapper.ReserveUserMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;

/**
 * @discription	: 프로모션 적립금 ServiceImpl
 * @fileName	: AdminReserveServiceImpl.java
 * @author		: JSK
 * @date		: 2021.12.20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.20	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminReserveServiceImpl extends MallBaseService implements AdminReserveService {
    private static final Logger logger = LoggerFactory.getLogger(AdminReserveServiceImpl.class);

    @Autowired
    private ReserveMapper reserveMapper;
    @Autowired
    private ReserveUserMapper reserveUserMapper;
    @Autowired
    private ReserveConfigMapper reserveConfigMapper;
    @Autowired
    private ConfigMapper configMapper;
	@Autowired
	private CommonService commonService;

    /**
     * 적립금 목록 조회
     *
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectReserveList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> resultList = reserveMapper.selectReserveList(params);
        SOMap count = reserveMapper.selectReserveListCount(params);

        result.put("list", resultList);
        result.put("totalcnt", count.getInt("totalcnt"));
        result.put("paycnt", count.getInt("paycnt"));
        result.put("usecnt", count.getInt("usecnt"));
        result.put("extinctcnt", count.getInt("extinctcnt"));
        return result;
    }
    
    /**
     * 적립금 목록 조회(엑셀용)
     *
     * @param params
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectReserveListForExcel(SOMap params) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        params.put("siteid", cs.getStr("siteid"));
    	params.put("startpage", 0);
    	params.put("endpage", CMConst.EXCEL_LIMIT_ROWS);
		resultList = reserveMapper.selectReserveListForExcel(params);
		return resultList;
    }

    /**
     * 적립금 저장
     *  
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {CustomException.class, Exception.class})
    public int saveReserve(SOMap params) throws CustomException {
    	int resultCnt = 0;
		
        try {
			// 1. 파라미터 세팅
			params.put("siteid", cs.getStr("siteid"));
			params.put("authuserid", cs.getStr("authuserid"));
			String today = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
			
			// 1-1. 적립금 지급일
			//      즉시지급인 경우 현재날짜로 세팅
			if (Util.equal(params.getStr("isnowpay"), "T")) {
				params.put("respayday", today);
			}
			// 1-2. 적립금유효시작일
			params.put("resstday", params.getStr("respayday"));
			// 1-3. 적립금유효종료일
			//      임직원적립금은 유효기간 없이 999912312359
			//      일반적립금은 발급일 기준 1년 (일자기준 23시59분)
			if (Util.equal(params.getStr("isempreserve"), "T")) {
				params.put("resedday", "999912312359");
			} else if (Util.equal(params.getStr("isempreserve"), "F")) {
				params.put("resedday", DateTimeUtil.getPointEndDate(params.getStr("resstday"))); 
			}

			// 2. 적립금 저장
			resultCnt = reserveMapper.insertReserve(params);
			
        	// 3-1. 즉시지급인 경우
			if (Util.equal(params.getStr("isnowpay"), "T")) {
				// 지급내역 저장
				params.put("respaytype", CMConst.RES_PAY_TYPE_MANUAL);
				int payCnt = commonService.paymentReserve(params);
				if (payCnt == 0) {
		            throw new CustomException("적립금지급 대상이 존재하지 않습니다.");
	        	}
			}
        	// 3-2. 즉시지급이 아닌 경우
			else if (Util.equal(params.getStr("isnowpay"), "F")) {
				SimpleDateFormat format = new SimpleDateFormat(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
				Date dateToday = format.parse(today);
				Date dateRespayday = format.parse(params.getStr("respayday"));
				if (dateToday.compareTo(dateRespayday) >= 0) {
		            throw new CustomException("지급일은 현재일 이후로 입력이 가능합니다.");
				}
	        	// 특정회원인 경우 예약대상회원 저장
				if (Util.equal(params.getStr("ismemtype"), "F")) {
					List<Map<String, Object>> targetMemberList = params.getArrayList("targetmemberlist");
					if (targetMemberList != null && targetMemberList.size() > 0) {
						reserveUserMapper.insertReserveUserMember(params);
					}
				}
			}
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("적립금 수동지급시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }

    /**
     * 적립금차감 저장
     *  
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap saveReserveDeduction(SOMap params) throws CustomException {
    	SOMap result = new SOMap();
    	int failCnt = 0;
    	List<SOMap> adjustList = new ArrayList<SOMap>();

        try {
	    	List<SOMap> targetMemberList = Util.convertToListSOMap(params.getArrayList("memberlist"));
	    	for (SOMap member : targetMemberList) {
	    		SOMap dbParam = new SOMap();
	    		dbParam.put("userid", member.get("userid"));
	    		dbParam.put("username", member.get("username"));
	    		dbParam.put("isempreserve", params.get("isempreserve"));
	    		dbParam.put("userno", member.getInt("userno"));
	    		dbParam.put("usepoint", params.getInt("point"));
	    		dbParam.put("resusereason", params.get("reason"));
	    		dbParam.put("regid", getAdminId());
	    		reserveMapper.spInsertManualReserveuse(dbParam);
	            logger.info(dbParam.toString());
	    		if (dbParam.getInt("code") < 0) {
	    			failCnt += 1;
	    		}
	    		if (dbParam.getInt("usepoint") != dbParam.getInt("adjustpoint")) {
	    			adjustList.add(dbParam);
	    		}
	    	}
        } catch(Exception e){
            logger.error(e.getMessage());
        }
        
        result.put("failcnt", failCnt);
        result.put("adjustlist", adjustList);
        
        return result;
    }
    
    /**
     * 적립금 자동지급설정 조회
     *
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectReserveConfig() throws Exception {
    	SOMap result = new SOMap();
    	
        SOMap params = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        result = configMapper.selectConfigForReserveConfig(params);
        
        List<SOMap> configlist = reserveConfigMapper.selectReserveConfig(params);
        result.put("configlist", configlist);
        
        return result;
    }

    /**
     * 적립금 자동지급설정 저장
     *  
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int saveReserveConfig(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
			// 1. 파라미터 세팅
        	params.put("siteid", cs.getStr("siteid"));
        	params.put("authuserid", cs.getStr("authuserid"));
			
			// 2. 적립금 지급설정 저장
			List<Map<String, Object>> configlist = params.getArrayList("configlist");
			for (Map<String, Object> config: configlist) {
				SOMap paramMap = new SOMap();
				paramMap.putAll(config);
				paramMap.put("siteid", params.get("siteid"));
				paramMap.put("authuserid", params.get("authuserid"));
				resultCnt += reserveConfigMapper.mergeReserveConfig(paramMap);
			}
        	
        	// 3. 적립금 추가설정 저장
        	configMapper.updateConfig(params);
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("적립금 자동지급설정 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
}
