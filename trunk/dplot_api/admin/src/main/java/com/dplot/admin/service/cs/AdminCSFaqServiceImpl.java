package com.dplot.admin.service.cs;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.BoardPostMapper;
import com.dplot.mapper.CodeMapper;
import com.dplot.util.DateTime;
import com.dplot.util.ServletRequestInfoUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : ywm2004
 * @discription : CS관리 FAQ ServiceImpl
 * @fileName : AdminCSFaqServiceImpl.java
 * @date : 2021-11-10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-10	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCSFaqServiceImpl extends MallBaseService implements AdminCSFaqService {

    private static final Logger logger = LoggerFactory.getLogger(AdminCSFaqServiceImpl.class);

    @Autowired
    BoardPostMapper boardPostMapper;

    @Autowired
    CodeMapper codeMapper;

    @Override
    public SOMap selectFaqList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 날짜 데이터
        Date startDate = DateTime._stringToDate(params.get("startdate").toString());
        Date endDate = DateTime._stringToDate(params.get("enddate").toString());

        // 끝 날짜가 시작 날짜보다 작다면 서로 교체
        if (endDate.before(startDate)) {
            params.put("startdate", DateTime._getDateString(endDate));
            params.put("enddate", DateTime._getDateString(startDate));
        }

        // boardtype
        params.put("boardtype", CMConst.BOARD_TYPE_FAQ);


        result.put("list", boardPostMapper.selectFaqPagingList(params));
        result.put("stateList", boardPostMapper.selectFaqListStateCount(params));

        return result;
    }

    @Override
    public SOMap selectFaqDetail(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        SOMap dtlMap = boardPostMapper.selectBoardPostDtl(params);

        // memberType 데이터가 있다면
        if (StringUtils.isNotBlank(dtlMap.get("mumembertype").toString())) {
            List<String> muMemberType = Arrays.asList(dtlMap.get("mumembertype").toString().split(","));
            dtlMap.put("mumembertype", muMemberType);
        }

//        boardPostMapper.updateBoardHits(params.get("idx").toString());
        return dtlMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertFaq(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
    	params.put("userno", cs.getStr("authuserno"));
        // 특수 문자 -> Html 태그 변환
        String content = StringEscapeUtils.unescapeHtml(params.get("content").toString());

        params.put("content", content);

        // BoardType
        params.put("boardtype", CMConst.BOARD_TYPE_FAQ);

        // ip
        params.put("ip", ServletRequestInfoUtil.getRequestIp());
        boardPostMapper.insertBoardPost(params);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateFaq(SOMap params) throws Exception {
        params.put("boardtype", CMConst.BOARD_TYPE_FAQ);
        params.put("siteid", cs.getStr("siteid"));
        if (params.containsKey("numsave")) {
            boardPostMapper.updateBoardSortNum(params);
        } else {
            boardPostMapper.updateBoardPost(params);
        }
    }
    
    @Override
    public SOMap checkFaqMain(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        params.put("boardtype", CMConst.BOARD_TYPE_FAQ);
		
		SOMap result = new SOMap();
		
		int check =  boardPostMapper.selectFaqMainCnt(params);
		result.put("msg", "저장 하시겠습니까?");
		if("F".equals(params.getStr("istrash")) && "T".equals(params.getStr("isfaqmain"))) {
			if(check > 5) {
				result.put("msg", "동일 기간에 메인노출중인 게시물이 5건 이상 존재합니다. 진행 하시겠습니까?");
			}
		} else {
			if(check == 0) {
				result.put("msg", "저장 처리 시, 노출되는 메인 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
    }
}
