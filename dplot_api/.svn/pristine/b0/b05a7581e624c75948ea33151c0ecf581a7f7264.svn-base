package com.dplot.admin.service.promotion;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BoardPostMapper;
import com.dplot.mapper.DadaEventEnterMapper;
import com.dplot.mapper.DadaEventMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.util.DateTime;
import com.dplot.util.ServletRequestInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : ywm2004
 * @discription : 이벤트 당첨자 발표 ServiceImpl
 * @fileName : AdminPromotionAnnounceServiceImpl.java
 * @date : 2021-12-21
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-21	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPromotionAnnounceServiceImpl extends MallBaseService implements AdminPromotionAnnounceService {

    @Autowired
    BoardPostMapper boardPostMapper;

    @Autowired
    DadaEventMapper dadaEventMapper;

    @Autowired
    DadaEventEnterMapper dadaEventEnterMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 이벤트 당첨자 게시물 등록
     *
     * @param params
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public void insertAnnounce(SOMap params) throws Exception {
        params.put("boardtype", CMConst.BOARD_TYPE_EVENT_ANNOUNCE);
		params.put("userno", cs.getStr("authuserno"));
		params.put("siteid", cs.getStr("siteid"));

        // ip
        params.put("ip", ServletRequestInfoUtil.getRequestIp());
        
        try{
        	// 게시물 저장
        	boardPostMapper.insertBoardPost(params);
        	
        	// [재호] 당첨자 유저 저장
			dadaEventEnterMapper.insertAdminEventEnterArray(params);
        	
        	// 게시물 idx를 event 테이블에 저장
        	SOMap eventMap = new SOMap();
        	eventMap.put("postidx", params.getStr("idx"));
        	eventMap.put("eventidx", params.getStr("eventidx"));
        	dadaEventMapper.updateEvent(eventMap);
        } catch(Exception e) {
        	throw new CustomException("당첨자발표 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
    }

    /**
     * 이벤트 당첨자 게시물 수정
     *
     * @param params
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public void updateAnnounce(SOMap params) throws Exception {
        String oldEventIdx = params.getStr("oldeventidx");
        String currentEventIdx = params.getStr("eventidx");
        params.put("moduserid", cs.getStr("authuserid"));
        params.put("siteid", cs.getStr("siteid"));

        this.isCanSaveCheck(params);
        try{
        	// 저장 가능 여부
        	
        	if("".equals(oldEventIdx)) {
        		if(params.containsKey("removelist")) {
        			SOMap removeMap = new SOMap();
        			removeMap.put("eventidx", currentEventIdx);
        			removeMap.put("istrash", "T");
        			removeMap.put("list", params.get("removelist"));
        			dadaEventEnterMapper.updateAdminEventEnterArray(removeMap);
        		}
        	} else {
        		// 기존 프로모션과 수정 프로모션이 다른 경우
        		// 기존 연결된 이벤트 해제
        		SOMap eventClearMap = new SOMap();
        		eventClearMap.put("postidxdelete", 'T');
        		eventClearMap.put("eventidx", oldEventIdx);
        		dadaEventMapper.updateEvent(eventClearMap);
        		
        		// 새로운 이벤트 연결
        		SOMap eventUpdateMap = new SOMap();
        		eventUpdateMap.put("eventidx", currentEventIdx);
        		eventUpdateMap.put("postidx", params.getStr("idx"));
        		dadaEventMapper.updateEvent(eventUpdateMap);
        		
        		if(params.containsKey("removelist")) {
        			SOMap removeMap = new SOMap();
        			removeMap.put("eventidx", oldEventIdx);
        			removeMap.put("istrash", "T");
        			dadaEventEnterMapper.updateAdminEventEnterArray(removeMap);
        		}
        	}

    		if(params.containsKey("insertlist")) {
    			SOMap insertMap = new SOMap();
    			insertMap.put("eventidx", currentEventIdx);
    			insertMap.put("enterlist", params.get("insertlist"));
    			dadaEventEnterMapper.insertAdminEventEnterArray(insertMap);
    		}
        	
        	// 이벤트 발표 게시물 업데이트
        	boardPostMapper.updateBoardPost(params);
        } catch(Exception e) {
        	throw new CustomException("당첨자발표 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
    }

    /**
     * 종료 이벤트 리스트 조회
     *
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEndEventList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 종료 이벤트 리스트 조회
        SOMap eventEndMap = new SOMap();
        eventEndMap.put("eventtype", CMConst.EVENT_TYPE_NOMAL);
        eventEndMap.put("eventidx", params.getStr("eventidx"));
        eventEndMap.put("siteid", cs.getStr("siteid"));

        result.put("list", dadaEventMapper.selectAdminEndEventList(eventEndMap));
        return result;
    }

    /**
     * 이벤트 당첨자 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEventAnnounceList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        params.put("boardtype", CMConst.BOARD_TYPE_EVENT_ANNOUNCE);

        // String 날짜 변환
        Date startDate = DateTime._stringToDate(params.get("startdate").toString());
        Date endDate = DateTime._stringToDate(params.get("enddate").toString());

        // 끝 날짜가 시작 날짜보다 작다면 서로 교체
        if (endDate.before(startDate)) {
            params.put("startdate", DateTime._getDateString(endDate));
            params.put("enddate", DateTime._getDateString(startDate));
        }

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
		params.put("siteid", cs.getStr("siteid"));

        result.put("list", boardPostMapper.selectEventAnnounceList(params));
        result.put("listcount", boardPostMapper.selectEventAnnounceListStateCount(params));

        return result;
    }

    /**
     * 이벤트 당첨자 발표 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEventAnnounceDetail(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        params.put("boardtype", CMConst.BOARD_TYPE_EVENT_ANNOUNCE);
//        boardPostMapper.updateBoardHits(params.getStr("idx"));

        // 이벤트 상세 정보
        SOMap announceDetailMap = boardPostMapper.selectEventAnnounceDetail(params);
        // [재호] 당첨자 관리 컬럼 추가되면 로직 수정해야함
        SOMap enterParams = new SOMap();
        enterParams.put("eventidx", announceDetailMap.getStr("eventidx"));
        List<SOMap> enterList = dadaEventEnterMapper.selectAdminDadaEventEnterByUserInfoList(enterParams);

        // 종료 이벤트 리스트 조회
        SOMap eventEndParams = new SOMap();
        eventEndParams.put("eventidx", announceDetailMap.getStr("eventidx"));

        result.put("boardinfo", announceDetailMap);
        result.put("eventlist", this.selectEndEventList(eventEndParams).get("list"));
        result.put("enterlist", enterList);
        return result;
    }

    /**
     * 응모 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEnterList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("list", dadaEventEnterMapper.selectAdminDadaEventEnterByUserInfoList(params));
        return result;
    }

    /**
     * 수정 여부 판별
     *
     * @param params
     * @return
     * @throws Exception
     */
    public void isCanSaveCheck(SOMap params) throws Exception {
        boolean check = boardPostMapper.isAdminCanSaveCheckEventAnnounce(params).getInt("issave") == 1;
        if (!check) {
            throw new CustomException("즉시 등록 혹은 예약 지정 시간이 지났을 경우 저장할 수 없습니다.");
        }
    }
}
