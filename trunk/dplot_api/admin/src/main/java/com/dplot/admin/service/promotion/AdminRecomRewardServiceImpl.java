package com.dplot.admin.service.promotion;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.EPointMapper;
import com.dplot.mapper.RecomRewardLogMapper;
import com.dplot.mapper.RecomRewardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminRecomRewardServiceImpl extends MallBaseService implements AdminRecomRewardService {
    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RecomRewardMapper recomRewardMapper;

    @Autowired
    private RecomRewardLogMapper recomRewardLogMapper;

    @Autowired
    private EPointMapper epointMapper;


    /**
     * 추천리워드 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectRecomReward(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        result.put("info", recomRewardMapper.selectRecomReward(params));
        // 쿠폰조회 추가필요
        result.put("couponList", couponMapper.selectPromotionCouponList(params));

        return result;
    }

    /**
     * 추천리워드로그 목록 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectRecomRewardLogList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> list = recomRewardLogMapper.selectRecomRewardLogList(params);

        result.put("list", list);
        result.put("listCount", recomRewardLogMapper.selectRecomRewardLogCnt(params));

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap saveRecomReward(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        params.put("userid", cs.getStr("authuserid"));

        try {
        	if("RCT002".equals(params.getStr("recomtype")) && "F".equals(params.getStr("istrash"))) {
            	params.put("epopayday", params.getStr("recomstday"));
            	params.put("epovalidday", params.getStr("recomedday"));
        		int duplCnt = epointMapper.checkDuplEpoint(params).getInt("check");
    			if (duplCnt > 0) {
    	            throw new CustomException("기존에 지급된 D포인트의 유효기간이 만료되지 않아 신규 수동지급이 불가합니다.");
    			}
        	}
        	
        	if ("RCT001".equals(params.getStr("recomtype")) || "RCT002".equals(params.getStr("recomtype"))) {
                // 적립금, e포인트인 경우 쿠폰번호 NULL로 세팅
                params.put("revcpidx", null);
                params.put("recjoincpidx", null);
                params.put("reccfmcpidx", null);
            } else if("RCT003".equals(params.getStr("recomtype"))){
                // 쿠폰인 경우 포인트 NULL로 세팅
                params.put("revpoint", null);
                params.put("recjoinpoint", null);
                params.put("reccfmpoint", null);
            } else {
            	params.put("revcpidx", null);
                params.put("recjoincpidx", null);
                params.put("reccfmcpidx", null); 
                params.put("revpoint", null);
                params.put("recjoinpoint", null);
                params.put("reccfmpoint", null);
            }

            // e포인트가 아닌경우 중복지급여부 NULL로 세팅
            if (!"RCT002".equals(params.getStr("recomtype"))) {
                params.put("isepointdup", null);
            }

            // 추천리워드 로그 추가
            recomRewardLogMapper.insertRecomRewardLog(params);

            if ("T".equals(params.getStr("isfirst"))) {
                // 처음 저장하는 경우 추천리워드 생성
                recomRewardMapper.insertRecomReward(params);
            } else {
                // 이후 추천리워드 수정
                recomRewardMapper.updateRecomReward(params);
            }
        } catch(Exception e) {
        	throw new CustomException("추천리워드 저장중 오류가 발생했습니다. 관리자에게 문의해주세요.");
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> selectRecomRewardExcelList(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        return recomRewardLogMapper.selectRecomRewardLogExcel(params);
    }
}
