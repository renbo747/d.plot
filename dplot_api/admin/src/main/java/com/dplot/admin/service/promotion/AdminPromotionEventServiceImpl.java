package com.dplot.admin.service.promotion;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.*;
import com.dplot.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : ywm2004
 * @discription : 프로모션 이벤트 ServiceImpl
 * @fileName : AdminPromotionEventServiceImpl.java
 * @date : 2021-12-21
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-21	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPromotionEventServiceImpl extends MallBaseService implements AdminPromotionEventService {

    @Autowired
    BoardPostMapper boardPostMapper;

    @Autowired
    DadaEventMapper dadaEventMapper;

    @Autowired
    DadaEventCommentMapper dadaEventCommentMapper;

    @Autowired
    DadaEventEnterMapper dadaEventEnterMapper;

    @Autowired
    DadaEventGoodsMapper dadaEventGoodsMapper;

    @Autowired
    EvtCommentReportMapper evtCommentReportMapper;

    @Autowired
    FileService fileService;

    @Autowired
    CommonService commonService;

    /**
     * 이벤트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEventList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        params.put("eventtype", CMConst.EVENT_TYPE_NOMAL);

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

        result.put("list", dadaEventMapper.selectAdminEventList(params));
        result.put("stateList", dadaEventMapper.selectAdminEventStateList(params));
        return result;
    }

    /**
     * 이벤트 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEventDetail(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 이벤트 상세 조회
        SOMap eventDtl = dadaEventMapper.selectAdminEventDtl(params);

        // 이벤트 정보 파일 조회
        SOMap fileParams = new SOMap();
        String[] types = {CMConst.IMG_TYPE_EVENT_IMG_MAIN_PC, CMConst.IMG_TYPE_EVENT_IMG_MAIN_MO};
        fileParams.put("orgidx", params.get("eventidx"));
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

        // 연관 상품 조회 : 2021-12-17 완료
        List<SOMap> productList = dadaEventGoodsMapper.selectAdminDadaEventGoods(params);

        // 응모 조회 : 2021-12-17 완료
        List<SOMap> enterList = new ArrayList<>();
        String isEnter = eventDtl.getSOMap("eventdtlinfo").get("isenter").toString();
        if ("T".equals(isEnter)) {
            enterList = dadaEventEnterMapper.selectAdminDadaEventEnterList(params);
        }

        // 댓글 조회 : 2021-12-17 완료
        ArrayList<Object> commentList = this.selectCommentList(params).getArrayList("list");

//        fileParams.clear();
//        String[] commentTypes = {CMConst.IMG_TYPE_EVENT_IMG_REPLY, CMConst.IMG_TYPE_EVENT_REPLY_VIDEO};
//        fileParams.put("imgtypes", commentTypes);
//        ArrayList<Map<String, Object>> commentFileList = fileService.selectFileList(fileParams).getArrayList("uploadedfile");
//        List<SOMap> commentList = new ArrayList<>();
//        String isComment = eventDtl.getSOMap("eventdtlinfo").get("iscomment").toString();
//        if ("T".equals(isComment)) {
//            commentList = dadaEventCommentMapper.selectAdminDadaEventCommentList(params);
//
//            // depth 댓글 순서 조정
//            List<SOMap> resultCommentList = new ArrayList<>();
//            List<SOMap> root = commentList.stream().filter(obj -> "0".equals(obj.getStr("depth"))).collect(Collectors.toList());
//            List<SOMap> child = commentList.stream().filter(obj -> !"0".equals(obj.getStr("depth"))).collect(Collectors.toList());
//
//            root.forEach(rt -> {
//                // depth0 idx 값
//                String commentIdx = rt.getStr("commentidx");
//                // depth0 idx 값과 같은 child upcommentidx 추출
//                List<SOMap> rootChildList = child.stream().filter(obj -> commentIdx.equals(obj.getStr("upcommentidx"))).collect(Collectors.toList());
//                // 이미지 파일 검사
//                Optional<Map<String, Object>> commentFile = commentFileList.stream().filter(file -> commentIdx.equals(file.get("orgidx").toString())).findFirst();
//                if (commentFile.isPresent()) { // null 체크
//                    rt.put("imgpath", commentFile.get().get("fullpath"));
//                    if (CMConst.IMG_TYPE_EVENT_REPLY_VIDEO.equals(commentFile.get().get("imgtype").toString())) { // 사진 타입
//                        rt.put("icon", "icon-file-video");
//                    } else if (CMConst.IMG_TYPE_EVENT_IMG_REPLY.equals(commentFile.get().get("imgtype").toString())) { // 영상 타입
//                        rt.put("icon", "icon-file-photo");
//                    }
//                }
//                // 새로운 result list에 넣어줌
//                resultCommentList.add(rt);
//                if (rootChildList.size() > 0) {
//                    resultCommentList.addAll(rootChildList);
//                }
//            });
//
//            commentList = resultCommentList;
//        }

        // result 객체에 저장
        result.put("eventdtl", eventDtl); // 이벤트 기본 상세 정보
        result.put("productlist", productList); // 이벤트 연관 상품 정보
        result.put("files", files); // 사진 파일
        result.put("enterlist", enterList); // 응모 리스트
        result.put("commentlist", commentList); // 댓글 리스트

        return result;
    }

    /**
     * 이벤트 댓글 신고 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEventReportList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("list", evtCommentReportMapper.selectAdminEvtCommentReportList(params));
        return result;
    }

    /**
     * 이벤트 응모 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEventEnterList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("list", dadaEventEnterMapper.selectAdminDadaEventEnterListPopup(params));
        return result;
    }

    /**
     * 이벤트 등록
     *
     * @param params
     * @param files
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
    public void insertEvent(SOMap params, Map<String, MultipartFile> files) throws Exception {
        // 사이트맵
        params.put("siteid", cs.getStr("siteid"));
        params.put("eventtype", CMConst.EVENT_TYPE_NOMAL);

        // 이벤트 게시물 추가
        dadaEventMapper.insertEvent(params);

        int eventIdx = params.getInt("eventidx");

        // 이벤트 상품 추가 (dadaevent_goods)
        ArrayList<HashMap<String, Object>> productList = params.getArrayList("productlist");
        if (productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {
                productList.get(i).put("sortnum", i);
                productList.get(i).put("reguserid", params.getStr("reguserid"));
                productList.get(i).put("eventidx", eventIdx);
            }
//            dadaEventGoodsMapper.insertArrayDadaEventGoods(productList);
        }

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
     * 이벤트 상세 수정
     *
     * @param params
     * @param files
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
    public void updateEventDetail(SOMap params, Map<String, MultipartFile> files) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        params.put("moduserid", cs.getStr("authuserid"));

        this.isCanSaveCheck(params);

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

        // 상품 삭제
        if (params.containsKey("deletelist")) {
            ArrayList<Object> deleteList = params.getArrayList("deletelist");
            if (deleteList.size() > 0) {
                SOMap deleteProductMap = new SOMap();
                deleteProductMap.put("goodsnolist", deleteList);
                deleteProductMap.put("eventidx", eventIdx);
                dadaEventGoodsMapper.deleteDadaEventGoods(deleteProductMap);
            }
        }

        // 상품, 추가수정
        if (params.containsKey("productlist")) {
            ArrayList<Object> productList = params.getArrayList("productlist");
            if (productList.size() > 0) {
                SOMap insertAndUpdateProductMap = new SOMap();
                insertAndUpdateProductMap.put("productlist", productList);
                insertAndUpdateProductMap.put("dbproductlist", params.getArrayList("dbproductlist"));
                insertAndUpdateProductMap.put("reguserid", params.getStr("moduserid"));
                insertAndUpdateProductMap.put("eventidx", params.getStr("eventidx"));

//                dadaEventGoodsMapper.insertAndUpdateDadaEventGoods(insertAndUpdateProductMap);
            }
        }
    }

    /**
     * 이벤트 사용 여부 수정
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void updateEventUse(SOMap params) throws Exception {
        dadaEventMapper.updateEvent(params);
    }

    /**
     * 이벤트 블라인드 수정
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void updateEventCommentBlind(SOMap params) throws Exception {
        dadaEventCommentMapper.updateDadaEventComment(params);
    }

    /**
     * 이벤트 댓글 추가, 수정
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void insertAndUpdateEventComment(SOMap params) throws Exception {
        // 추가
        if ("T".equals(params.getStr("isinsert"))) {
            dadaEventCommentMapper.insertDadaEventComment(params);
        } else {
            dadaEventCommentMapper.updateDadaEventComment(params);
        }
    }

    /**
     * 이벤트 댓글 삭제
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void deleteEventComment(SOMap params) throws Exception {
        dadaEventCommentMapper.deleteDadaEventComment(params);
    }

    /**
     * 저장 여부 판별
     *
     * @param params
     * @return
     * @throws Exception
     */
    public void isCanSaveCheck(SOMap params) throws Exception {
        boolean check = dadaEventMapper.isAdminCanSaveCheckEventAndCheckEvent(params).getInt("check") == 1;
        if (!check) {
            throw new CustomException("진행전 상태에서만 수정가능합니다.");
        }
    }

    /**
     * 이벤트 상세 댓글 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectCommentList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        SOMap fileParams = new SOMap();
        String[] commentTypes = {CMConst.IMG_TYPE_EVENT_IMG_REPLY, CMConst.IMG_TYPE_EVENT_REPLY_VIDEO};
        fileParams.put("imgtypes", commentTypes);
        ArrayList<Map<String, Object>> commentFileList = fileService.selectFileList(fileParams).getArrayList("uploadedfile");

        List<SOMap> commentList = dadaEventCommentMapper.selectAdminDadaEventCommentList(params);

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

        result.put("list", commentList);
        return result;
    }

    /**
     * 이벤트 댓글 엑셀 다운
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectEventCommentExcelDownload(SOMap params) throws Exception {
        List<Map<String, Object>> commentList = dadaEventCommentMapper.selectAdminCommentExcelDownload(params);

        // depth 댓글 순서 조정
        List<Map<String, Object>> resultCommentList = new ArrayList<>();
        List<Map<String, Object>> root = commentList.stream().filter(obj -> "0".equals(obj.get("depth").toString())).collect(Collectors.toList());
        List<Map<String, Object>> child = commentList.stream().filter(obj -> !"0".equals(obj.get("depth").toString())).collect(Collectors.toList());

        root.forEach(rt -> {
            // depth0 idx 값
            String commentIdx = rt.get("commentidx").toString();
            // depth0 idx 값과 같은 child upcommentidx 추출
            List<Map<String, Object>> rootChildList = child.stream().filter(obj -> commentIdx.equals(obj.get("upcommentidx").toString())).collect(Collectors.toList());
            // 새로운 result list에 넣어줌
            resultCommentList.add(rt);
            if (rootChildList.size() > 0) {
                resultCommentList.addAll(rootChildList);
            }
        });

        commentList = resultCommentList;
        return commentList;
    }

    /**
     * 이벤트 응모 엑셀 다운
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectEventEnterExcelDownload(SOMap params) throws Exception {
        return dadaEventEnterMapper.selectAdminEnterExcelDownload(params);
    }
}
