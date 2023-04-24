package com.dplot.admin.service.partners;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.BoardPostMapper;
import com.dplot.mapper.CodeMapper;
import com.dplot.util.DateTime;
import com.dplot.util.ServletRequestInfoUtil;
import com.dplot.util.Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 파트너사 공지사항 게시판 ServiceImpl
 * @fileName : AdminNoticeServiceImpl.java
 * @date : 2021-11-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPartnersNoticeServiceImpl extends MallBaseService implements AdminPartnersNoticeService {

    private static final Logger logger = LoggerFactory.getLogger(AdminPartnersNoticeServiceImpl.class);

    @Autowired
    BoardPostMapper boardPostMapper;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    FileService fileService;

    @Override
    public SOMap selectNoticeDetail(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));

        // 상세 조회
        SOMap noticeDetail = boardPostMapper.selectNoticeDetail(params);
        if("T".equals(params.getStr("isadmin"))) {
        	boardPostMapper.updateBoardHits(params.get("idx").toString());        	
        }

        // 첨부파일 조회
        // [재호] 첨부파일 imgtype 추가되면 변경 요망 -> 수정
        SOMap fileParams = new SOMap();
        fileParams.put("orgidx", params.get("idx"));
        fileParams.put("imgtype", CMConst.IMG_TYPE_BOARD);
        SOMap fileMap = fileService.selectFileList(fileParams);

        // Map 합치기
        noticeDetail.putAll(fileMap);
        return noticeDetail;
    }

    @Override
    public void updateBoardPost(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));

        // 특수문자 -> html 태그로 변경
        if (params.get("content") != null && params.get("content") != "") {
            String content = StringEscapeUtils.unescapeHtml(params.get("content").toString()); // 특수문자 html 태그로 변경
            params.put("content", content);
        }

        // true -> T, false -> F
        if (params.containsKey("isessnotice")) {
            params.put("isessnotice", Util.bool2Flag((Boolean) params.get("isessnotice")));
        }
        if (params.containsKey("ispapopnotice")) {
            params.put("ispapopnotice", Util.bool2Flag((Boolean) params.get("ispapopnotice")));
        }

        // 파일 저장
        MultipartHttpServletRequest uploadFile = (MultipartHttpServletRequest) params.get("uploadfile");
        Map<String, MultipartFile> fileMap = uploadFile.getFileMap();
        fileMap.remove("params");
        for (String key : fileMap.keySet()) {
            MultipartFile file = fileMap.get(key);
            logger.debug("File Upload : " + fileService.uploadAttach(file, params.getDbInt("idx"), CMConst.IMG_TYPE_BOARD));
        }

        // 파일 삭제
        List<SOMap> deletefile = (List<SOMap>) params.get("deletefile");
        for (int i = 0; i < deletefile.size(); i++) {
            HashMap<String, Object> file = deletefile.get(i);
            fileService.delete(Integer.parseInt(file.get("idx").toString()));
        }

        boardPostMapper.updateBoardPost(params);
    }

    @Override
    public void updateBoardPostIsTrash(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        boardPostMapper.updateBoardPost(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertNotice(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        // true -> T, false -> F
        params.put("isessnotice", Util.bool2Flag((Boolean) params.get("isessnotice")));
        params.put("ispapopnotice", Util.bool2Flag((Boolean) params.get("ispapopnotice")));

        // 특수문자 html 태그로 변경
        String url = StringEscapeUtils.unescapeHtml(params.get("content").toString());
        params.put("content", url);

        // BoardType
        params.put("boardtype", CMConst.BOARD_TYPE_NOTICE);

        // ip
        params.put("ip", ServletRequestInfoUtil.getRequestIp());

        // Board Post
        boardPostMapper.insertBoardPost(params);

        // 첨부 파일
        // [재호] 첨부파일 imgtype 추가되면 변경요망 -> 수정
        MultipartHttpServletRequest uploadFile = (MultipartHttpServletRequest) params.get("uploadfile");
        Map<String, MultipartFile> fileMap = uploadFile.getFileMap();
        fileMap.remove("params");

        // 파일 저장
        for (String key : fileMap.keySet()) {
            MultipartFile file = fileMap.get(key);
            logger.debug("File Upload : " + fileService.uploadAttach(file, params.getDbInt("idx"), CMConst.IMG_TYPE_BOARD));
        }
    }

    @Override
    public SOMap selectNoticeList(SOMap params) throws Exception {

        SOMap result = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));

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
        params.put("indexpage", startPage);
        params.put("limit", pageCount);

        params.put("boardtype", CMConst.BOARD_TYPE_NOTICE);
        result.put("list", boardPostMapper.selectNoticePagingList(params));
        result.put("listcount", boardPostMapper.selectNoticeListCount(params));

        return result;
    }
}
