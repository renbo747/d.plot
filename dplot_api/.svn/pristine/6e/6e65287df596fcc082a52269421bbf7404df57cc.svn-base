package com.dplot.admin.service.partners;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BoardAgreeMapper;
import com.dplot.mapper.BoardPostMapper;
import com.dplot.mapper.CodeMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.util.DateTime;
import com.dplot.util.ServletRequestInfoUtil;
import com.dplot.util.Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;

/**
 * @author : ywm2004
 * @discription : 파트너사 동의현황 게시판 ServiceImpl
 * @fileName : AdminConsentServiceImpl.java
 * @date : 2021-11-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPartnersConsentServiceImpl extends MallBaseService implements AdminPartnersConsentService {

    private static final Logger logger = LoggerFactory.getLogger(AdminPartnersConsentServiceImpl.class);

    @Autowired
    BoardPostMapper boardPostMapper;

    @Autowired
    BoardAgreeMapper boardAgreeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    FileService fileService;

    @Override
    public SOMap selectConsentList(SOMap params) throws Exception {

        SOMap result = new SOMap();

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
        params.put("boardtype", CMConst.BOARD_TYPE_CONSENT);
        params.put("dealerno", cs.getStr("authuserno"));

        result.put("list", boardPostMapper.selectConsentPagingList(params));
        result.put("statelist", boardPostMapper.selectConsentListStateCount(params));

        return result;
    }

    @Override
    public SOMap selectConsentDetail(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        SOMap detailList = boardPostMapper.selectConsentDetail(params);

        if (detailList.containsKey("nolist")) {
            String[] noList = detailList.get("nolist").toString().split(",");
            detailList.put("nolist", noList);
        }

        if (detailList.containsKey("namelist")) {
            String[] nameList = detailList.get("namelist").toString().split(",");
            detailList.put("namelist", nameList);
        }

        if (detailList.containsKey("useridlist")) {
            String[] idList = detailList.get("useridlist").toString().split(",");
            detailList.put("useridlist", idList);
        }

        // 첨부파일 조회
        SOMap fileParams = new SOMap();
        fileParams.put("orgidx", params.get("idx"));
        fileParams.put("imgtype", CMConst.IMG_TYPE_BOARD);
        SOMap fileMap = fileService.selectFileList(fileParams);

        detailList.putAll(fileMap);
        return detailList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateConsent(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        // 팝업 여부
        if (params.containsKey("ispapopnotice")) {
            params.put("ispapopnotice", Util.bool2Flag((Boolean) params.get("ispapopnotice")));
        }

        // 특수문자 -> html 태그로 변경
        if (params.containsKey("content")) {
            String content = StringEscapeUtils.unescapeHtml(params.get("content").toString()); // 특수문자 html 태그로 변경
            params.put("content", content);
        }

        boardPostMapper.updateBoardPost(params); // 동의사항 게시판 업데이트

        if ("T".equals(params.get("isallagree"))) { // 파트너사 전체 체크 (t_board_agree를 idx 기준으로 삭제후 재등록)
            // 전체 파트너사 가져오기
            SOMap popupParams = new SOMap();
            popupParams.put("siteid", cs.getStr("siteid"));
            List<SOMap> partnerList = userMapper.selectPartnersList(popupParams);
            List<SOMap> agreeList = new ArrayList<>();

            partnerList.forEach(obj -> {
                SOMap agree = new SOMap();
                agree.put("userno", Integer.parseInt(obj.get("no").toString()));
                agree.put("postidx", params.get("idx"));
                agree.put("isagree", "F");

                // 동의 리스트에 저장
                agreeList.add(agree);
            });

            boardAgreeMapper.deleteBoardAgreeAllByPostIdx(params.get("idx").toString());
            boardAgreeMapper.insertBoardAgree(agreeList);

        } else { // 파트너사 선택 체크
            // 파트너사 삭제 리스트
            Map<String, Object> deleteNoMap = new HashMap<>();
            if (params.containsKey("deletenolist")) {
                List<String> deleteNoList = (List<String>) params.get("deletenolist");
                deleteNoMap.put("deleteNoList", deleteNoList);
                deleteNoMap.put("postidx", params.get("idx"));

                if (deleteNoList.size() != 0) {
                    boardAgreeMapper.deleteBoardAgreeByUserNo(deleteNoMap);
                }
            }

            // 파트너사 추가 되는 리스트
            if (params.containsKey("addlist")) {
                List<SOMap> addList = (List<SOMap>) params.get("addlist");
                if (addList.size() != 0) {
                    boardAgreeMapper.insertBoardAgree(addList);
                }
            }
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
    }

    @Override
    public void updateTrash(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        boardPostMapper.updateBoardPost(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertConsent(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
        // 특수문자 -> html 태그로 변환
        String content = StringEscapeUtils.unescapeHtml(params.get("content").toString());
        params.put("content", content);

        // true -> T, faslse -> F
        params.put("ispapopnotice", Util.bool2Flag((Boolean) params.get("ispapopnotice")));

        // boardtype
        params.put("boardtype", CMConst.BOARD_TYPE_CONSENT);

        // ip
        params.put("ip", ServletRequestInfoUtil.getRequestIp());

        // t_board_post에 저장
        boardPostMapper.insertBoardPost(params);

        // 첨부 파일
        MultipartHttpServletRequest uploadFile = (MultipartHttpServletRequest) params.get("uploadfile");
        Map<String, MultipartFile> fileMap = uploadFile.getFileMap();
        fileMap.remove("params");

        // 파일 저장
        for (String key : fileMap.keySet()) {
            MultipartFile file = fileMap.get(key);
            logger.debug("File Upload : " + fileService.uploadAttach(file, params.getDbInt("idx"), CMConst.IMG_TYPE_BOARD));
        }

        // 저장된 게시물의 idx 값
        Integer boardPostIdx = Integer.parseInt(params.get("idx").toString());

        // 입점사 동의 내역 업로드를 위한 리스트 객체 생성
        List<SOMap> agreeList = new ArrayList<>();

        // 파트너사 전체 선택 여부
        if ("T".equals(params.get("isallagree"))) {
            // 전체 파트너사 가져오기
            SOMap popupParams = new SOMap();
            popupParams.put("siteid", cs.getStr("siteid"));

            List<SOMap> partnerList = userMapper.selectPartnersList(popupParams);

            // agree 객체 생성
            partnerList.forEach(obj -> {
                SOMap agree = new SOMap();
                agree.put("userno", Integer.parseInt(obj.get("no").toString()));
                agree.put("postidx", boardPostIdx);
                agree.put("isagree", "F");

                // 동의 리스트에 저장
                agreeList.add(agree);
            });
        } else { // 선택
            if (params.containsKey("partnernolist")) {
                // ,을 기준으로 묶인 유저 번호를 잘라서 셋팅
                List<String> partnerNoList = Arrays.asList(params.get("partnernolist").toString().split(","));
                if (StringUtils.isNotBlank(partnerNoList.get(0))) {
                    partnerNoList.forEach(obj -> {
                        SOMap agree = new SOMap();
                        agree.put("userno", Integer.parseInt(obj));
                        agree.put("postidx", boardPostIdx);
                        agree.put("isagree", "F");

                        // 동의 리스트에 저장
                        agreeList.add(agree);
                    });
                }
            }
        }

        // 저장 데이터가 있다면
        if (agreeList.size() > 0) {
            boardAgreeMapper.insertBoardAgree(agreeList);
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap updateAgree(SOMap params) throws Exception {
    	SOMap result = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));
    	params.put("userno", cs.getStr("authuserno"));
    	
    	try{
    		boardAgreeMapper.updateAgree(params);
    	} catch(Exception e){
        	throw new CustomException("동의현황 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    	return result;
    }
}
