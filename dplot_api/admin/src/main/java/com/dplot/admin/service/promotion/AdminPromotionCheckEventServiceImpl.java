package com.dplot.admin.service.promotion;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.*;
import com.dplot.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : ywm2004
 * @discription : 프로모션 출석체크 이벤트 ServiceImpl
 * @fileName : AdminPromotionCheckEventServiceImpl.java
 * @date : 2021-12-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-22	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPromotionCheckEventServiceImpl extends MallBaseService implements AdminPromotionCheckEventService {

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    FileService fileService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DadaEventMapper dadaEventMapper;

    @Autowired
    AttendDetailMapper attendDetailMapper;


    @Autowired
    DadaEventCommentMapper dadaEventCommentMapper;

    @Autowired
    DadaEventAttendMapper dadaEventAttendMapper;

    /**
     * 프로모션 출석체크 이벤트 저장
     *
     * @param params
     * @param files
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertCheckEvent(SOMap params, Map<String, MultipartFile> files) throws Exception {
        params.put("siteid", cs.getStr("siteid"));

        // BoardType 구하기
        SOMap codeParams = new SOMap();
        codeParams.put("cmclass", "EVENTTYPE");
        codeParams.put("codename", "출석체크");
        ArrayList<SOMap> boardType = codeMapper.selectCodeDtlList(codeParams);

        // cmcode
        params.put("eventtype", boardType.get(0).get("cmcode"));

        dadaEventMapper.insertEvent(params);

        int eventIdx = params.getInt("eventidx");

        // 사진 파일 추가
        if (files.containsKey("pcimgFile")) {
            MultipartFile pcImgFile = files.get("pcimgFile");
            fileService.uploadImage(pcImgFile, eventIdx, CMConst.IMG_TYPE_EVENT_IMG_MAIN_PC);
        }

        if (files.containsKey("mobileimgFile")) {
            MultipartFile mobileImgFile = files.get("mobileimgFile");
            fileService.uploadImage(mobileImgFile, eventIdx, CMConst.IMG_TYPE_EVENT_IMG_MAIN_MO);
        }
    }

    /**
     * 프로모션 출석체크 이벤트 수정
     *
     * @param params
     * @param files
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateCheckEvent(SOMap params, Map<String, MultipartFile> files) throws Exception {
        int eventIdx = params.getInt("eventidx");
        dadaEventMapper.updateEvent(params);

        if (files.containsKey("pcimgFile")) {
            MultipartFile pcimgFile = files.get("pcimgFile");
            fileService.uploadImage(pcimgFile, eventIdx, CMConst.IMG_TYPE_EVENT_IMG_MAIN_PC);
        }
        if (files.containsKey("mobileimgFile")) {
            MultipartFile pcimgFile = files.get("mobileimgFile");
            fileService.uploadImage(pcimgFile, eventIdx, CMConst.IMG_TYPE_EVENT_IMG_MAIN_MO);
        }
    }

    /**
     * 프로모션 출석 체크 이벤트 조회
     *
     * @param params
     * @return
     */
    @Override
    public SOMap selectCheckEventDetail(SOMap params) throws Exception {
        SOMap result = new SOMap();
        String eventIdx = params.getStr("eventidx");
        params.put("siteid", cs.getStr("siteid"));

        // 출첵 이벤트 상세 조회
        SOMap checkEventDtl = dadaEventMapper.selectAdminEventDtl(params);

        // 출첵 이벤트 정보 파일 조회
        SOMap fileParams = new SOMap();
        String[] types = {CMConst.IMG_TYPE_EVENT_IMG_MAIN_PC, CMConst.IMG_TYPE_EVENT_IMG_MAIN_MO};
        fileParams.put("orgidx", eventIdx);
        fileParams.put("imgtypes", types);
        SOMap filesMap = fileService.selectFileList(fileParams);
        List<Map<String, Object>> filesList = filesMap.getArrayList("uploadedfile");
        SOMap files = new SOMap();
        filesList.forEach(obj -> {
            if (CMConst.IMG_TYPE_EVENT_IMG_MAIN_PC.equals(obj.get("imgtype"))) {
                files.put("pcimgfile", obj);
            } else {
                files.put("mobileimgfile", obj);
            }
        });

        // 댓글 첨부 파일 조회
        fileParams.clear();
        String[] commentTypes = {CMConst.IMG_TYPE_EVENT_IMG_REPLY, CMConst.IMG_TYPE_EVENT_REPLY_VIDEO};
        fileParams.put("imgtypes", commentTypes);
        ArrayList<Map<String, Object>> commentFileList = fileService.selectFileList(fileParams).getArrayList("uploadedfile");

        // 댓글 정보 조회
        List<SOMap> commentList = new ArrayList<>();
        String isComment = checkEventDtl.getSOMap("eventdtlinfo").get("iscomment").toString();
        if ("T".equals(isComment)) { // 댓글있는 이벤트 경우
            commentList = dadaEventCommentMapper.selectAdminDadaEventCommentList(params);

            // depth 댓글 순서 조정
            List<SOMap> resultCommentList = new ArrayList<>();
            List<SOMap> root = commentList.stream().filter(obj -> "0".equals(obj.getStr("depth"))).collect(Collectors.toList());
            List<SOMap> child = commentList.stream().filter(obj -> !"0".equals(obj.getStr("depth"))).collect(Collectors.toList());

            root.forEach(rt -> {
                // depth0 idx 값
                String commentIdx = rt.getStr("commentidx");
                // depth0 idx 값과 같은 child upcommentidx 추출
                List<SOMap> rootChildList = child.stream().filter(obj -> commentIdx.equals(obj.getStr("upcommentidx"))).collect(Collectors.toList());
                // 이미지 파일 검사
                Optional<Map<String, Object>> commentFile = commentFileList.stream().filter(file -> commentIdx.equals(file.get("orgidx").toString())).findFirst();
                if (commentFile.isPresent()) { // null 체크
                    rt.put("imgpath", commentFile.get().get("fullpath"));
                    if (CMConst.IMG_TYPE_EVENT_REPLY_VIDEO.equals(commentFile.get().get("imgtype").toString())) { // 사진 타입
                        rt.put("icon", "icon-file-video");
                    } else if (CMConst.IMG_TYPE_EVENT_IMG_REPLY.equals(commentFile.get().get("imgtype").toString())) { // 영상 타입
                        rt.put("icon", "icon-file-photo");
                    }
                }
                // 새로운 result list에 넣어줌
                resultCommentList.add(rt);
                if (rootChildList.size() > 0) {
                    resultCommentList.addAll(rootChildList);
                }
            });

            commentList = resultCommentList;
        }

        // 출석체크 리스트
        SOMap eventAttendMap = new SOMap();
        eventAttendMap.put("eventidx", eventIdx);
        List<SOMap> dadaEventAttendList = dadaEventAttendMapper.selectAdminDadaEventAttendList(eventAttendMap);

        // result 객체 저장
        result.put("checkeventdtl", checkEventDtl); // 출첵 이벤트 기본 상세 정보
        result.put("commentlist", commentList); // 댓글 리스트
        result.put("attendlist", dadaEventAttendList);
        result.put("files", files); // 사진 파일

        return result;
    }

    /**
     * 출석정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectAttendDetail(SOMap params) throws Exception {
        SOMap result = new SOMap();
        SOMap noAtten = new SOMap() {{
            put("isattend", "N");
            put("notatreason", "미인정(미출석)");
            put("attenddatehhii", "-");
            put("benefitpaycount", "-");
        }};

        List<SOMap> resultTime = new ArrayList<>();

        List<String> fromToDateList = DateTimeUtil.getFromToDateList(params.getStr("startdate"), params.getStr("enddate"), DateTimeUtil.MALL_DATE_FORMAT_DATE);
        fromToDateList.sort(Collections.reverseOrder());

        List<SOMap> attendList = attendDetailMapper.selectAdminAttendDetailPopupList(params);

        fromToDateList.forEach(obj -> {
            List<SOMap> temp = attendList.stream().filter(atten -> atten.getStr("attenddateyyyymmdd").equals(obj)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(temp)) { // null check
                noAtten.put("attenddateyyyymmdd", obj);
                resultTime.add(noAtten);
            } else {
                resultTime.add(temp.get(0));
            }
        });

        result.put("list", resultTime);
        return result;
    }

    /**
     * 페이지에 필요한 코드값
     *
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectCodeList() throws Exception {
        SOMap result = new SOMap();

        // 다중적용채널
        SOMap muappchTypeMap = new SOMap();
        muappchTypeMap.put("cmclass", "MUAPPCHTYPE");
        result.put("muappchtypecode", codeMapper.selectCodeDtlList(muappchTypeMap));

        // 대상회원 유형
        SOMap mumemberTypeMap = new SOMap();
        mumemberTypeMap.put("cmclass", "MUMEMBERTYPE");
        result.put("mumembertypecode", codeMapper.selectCodeDtlList(mumemberTypeMap));

        // 대상회원 등급
        SOMap mumemlvTypeMap = new SOMap();
        mumemlvTypeMap.put("cmclass", "MUMEMLVTYPE");
        result.put("mumemlvtypecode", codeMapper.selectCodeDtlList(mumemlvTypeMap));

        // 노출 여부
        SOMap dispTypeMap = new SOMap();
        dispTypeMap.put("cmclass", "DISPTYPE");
        result.put("disptypecode", codeMapper.selectCodeDtlList(dispTypeMap));

        // 혜택 구분
        SOMap betTypeMap = new SOMap();
        betTypeMap.put("cmclass", "BENEFITTYPE");
        result.put("bettypecode", codeMapper.selectCodeDtlList(betTypeMap));

        return result;
    }

    /**
     * 엑셀 다운로드 - 출첵 현황
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectCheckAttendExcelDownload(SOMap params) throws Exception {
        return dadaEventAttendMapper.selectAdminDadaEventAttendExcelDownload(params);
    }
}
