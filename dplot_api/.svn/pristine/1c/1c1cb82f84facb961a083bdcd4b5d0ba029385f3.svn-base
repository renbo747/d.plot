package com.dplot.admin.service.promotion;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.common.service.util.NumberMakerService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.EPointMapper;
import com.dplot.mapper.SerialNoMapper;
import com.dplot.mapper.SerialPromoMapper;
import com.dplot.util.DateTime;
import com.dplot.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : ywm2004
 * @discription : 시리얼 프로모션 ServiceImpl
 * @fileName : AdminSerialPromotionServiceImpl.java
 * @date : 2022-01-07
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-07	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminSerialPromotionServiceImpl extends MallBaseService implements AdminSerialPromotionService {

    @Autowired
    SerialPromoMapper serialPromoMapper;

    @Autowired
    SerialNoMapper serialNoMapper;

    @Autowired
    EPointMapper ePointMapper;

    @Autowired
    NumberMakerService numberMakerService;

    /**
     * 시리얼 프로모션 저장
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertSerialPromotion(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        params.put("reguserid", cs.getStr("authuserid"));
        params.put("userid", cs.getStr("authuserid"));

        // 저장 가능 여부 판별
        this.isAdminCanSaveCheck(params);

        serialPromoMapper.insertSerialPromo(params);
        int srpIdx = params.getInt("srpidx");

        // 자동 등록
        if (CMConst.SRCTYPE_SRC_AUTO.equals(params.getStr("srctype"))) {
            SOMap serialNoParam = new SOMap();
            if ("T".equals(params.getStr("isequalserial"))) {
                // 한개
                serialNoParam.put("serialno", numberMakerService.getSerialNo());
                serialNoParam.put("srpidx", srpIdx);
                serialNoParam.put("usecnt", 0);
                serialNoParam.put("reguserid", params.get("userid"));
            } else {
                // 여러 개
                List<String> serialNoList = numberMakerService.getSerialNoList(params.getInt("issuecnt"));
                serialNoParam.put("serialnolist", makeSerialNoParam2(params, srpIdx, serialNoList));
            }
            serialNoMapper.insertSerialNo(serialNoParam);
        }

        // 직접 등록, 엑셀 등록
        else if (CMConst.SRCTYPE_SRC_DIR.equals(params.getStr("srctype")) || CMConst.SRCTYPE_SRC_EXCEL.equals(params.getStr("srctype"))) {
            List<Map<String, Object>> serialNoList = params.getArrayList("serialnolist");
            SOMap serialNoParams = new SOMap();
            serialNoParams.put("serialnolist", makeSerialNoParam(params, srpIdx, serialNoList));
            serialNoMapper.insertSerialNo(serialNoParams);
        }
    }

    /**
     * 시리얼 프로모션 수정
     *
     * @param params
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateSerialPromotion(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        params.put("moduserid", cs.getStr("authuserid"));
        params.put("userid", cs.getStr("authuserid"));

        // 저장 가능 여부 체크
        this.isAdminCanSaveCheck(params);

        serialPromoMapper.updateSerialPromo(params);

        // 시리얼 번호 삭제 여부
        if ("T".equals(params.getStr("isdelserial"))) {
            int srpIdx = params.getInt("srpidx");

            // 시리얼 번호 삭제
            SOMap deleteParams = new SOMap();
            deleteParams.put("srpidx", srpIdx);
            serialNoMapper.deleteSerialNo(deleteParams);

            // 자동 등록
            if (CMConst.SRCTYPE_SRC_AUTO.equals(params.getStr("srctype"))) {
                SOMap serialNoParam = new SOMap();
                if ("T".equals(params.getStr("isequalserial"))) {
                    // 한개
                    serialNoParam.put("serialno", numberMakerService.getSerialNo());
                    serialNoParam.put("srpidx", srpIdx);
                    serialNoParam.put("usecnt", 0);
                    serialNoParam.put("reguserid", params.get("userid"));
                } else {
                    // 여러 개
                    List<String> serialNoList = numberMakerService.getSerialNoList(params.getInt("issuecnt"));
                    serialNoParam.put("serialnolist", makeSerialNoParam2(params, srpIdx, serialNoList));
                }
                serialNoMapper.insertSerialNo(serialNoParam);
            }

            // 직접 등록, 엑셀 등록
            else if (CMConst.SRCTYPE_SRC_DIR.equals(params.getStr("srctype")) || CMConst.SRCTYPE_SRC_EXCEL.equals(params.getStr("srctype"))) {
                List<Map<String, Object>> serialNoList = params.getArrayList("serialnolist");
                if (!serialNoList.isEmpty()) {
                    SOMap serialNoParams = new SOMap();
                    serialNoParams.put("serialnolist", makeSerialNoParam(params, srpIdx, serialNoList));
                    serialNoMapper.insertSerialNo(serialNoParams);
                }
            }
        }
    }

    /**
     * 시리얼 프로모션 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectSerialPromotionList(SOMap params) throws Exception {
        SOMap result = new SOMap();

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

        String srcType = params.getStr("srctype");
        if (!srcType.isEmpty()) {
            params.put("srctypelist", Arrays.asList(srcType.split(",")));
        }

        String srpType = params.getStr("srptype");
        if (!srpType.isEmpty()) {
            params.put("srptypelist", Arrays.asList(srpType.split(",")));

        }

        String promost = params.getStr("promost");
        if (!promost.isEmpty()) {
            params.put("promostlist", Arrays.asList(promost.split(",")));

        }

        result.put("list", serialPromoMapper.selectAdminSerialPromotionList(params));
        result.put("state", serialPromoMapper.selectAdminSerialPromotionState(params));
        return result;
    }

    /**
     * 시리얼 프로모션 상세 조회
     *
     * @param params
     * @return
     */
    @Override
    public SOMap selectSerialPromotionDetail(SOMap params) {
        SOMap result = new SOMap();
        result.put("list", serialPromoMapper.selectAdminSerialPromotionDetail(params));
        result.put("snlist", serialNoMapper.selectAdminSerialNoUserInfoList(params));
        return result;
    }

    /**
     * S/N 발급 내역 조회
     *
     * @param params
     * @return
     */
    @Override
    public SOMap selectSerialNoList(SOMap params) {
        SOMap result = new SOMap();
        result.put("list", serialNoMapper.selectAdminSerialNoUserInfoList(params));
        return result;
    }

    /**
     * 시리얼 번호 중복 체크
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap isAdminCheckDuplicationSerialNo(SOMap params) throws Exception {
        SOMap result = new SOMap();
        boolean check = "1".equals(serialNoMapper.isCheckDuplicationSerialNo(params).getStr("check"));
        if (check) {
            throw new CustomException("시리얼 번호가 중복됩니다.");
        }
        result.put("check", false);
        return result;
    }

    /**
     * 중복된 시리얼 번호 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectDuplicationSerialNoList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("list", serialNoMapper.selectAdminSerialNoList(params));
        return result;
    }

    /**
     * 시리얼 번호 엑셀 다운로드
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap serialNoExcelDownload(SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("list", serialNoMapper.selectAdminSerialNoUserInfoList(params));
        return result;
    }


    /**
     * Serial Params 생성
     *
     * @param params
     * @param srpIdx
     * @param serialNoList
     * @return
     */
    private List<SOMap> makeSerialNoParam2(SOMap params, int srpIdx, List<String> serialNoList) {
        List<SOMap> result = new ArrayList<>();
        serialNoList.forEach(obj -> {
            SOMap temp = new SOMap();
            temp.put("serialno", obj);
            temp.put("srpidx", srpIdx);
            temp.put("usecnt", 0);
            temp.put("reguserid", params.get("userid"));
            result.add(temp);
        });

        return result;
    }

    private List<SOMap> makeSerialNoParam(SOMap params, int srpIdx, List<Map<String, Object>> serialNoList) {
        List<SOMap> result = new ArrayList<>();
        serialNoList.forEach(obj -> {
            SOMap temp = new SOMap();
            temp.put("serialno", obj.get("serialno"));
            temp.put("srpidx", srpIdx);
            temp.put("usecnt", 0);
            temp.put("reguserid", params.get("userid"));
            result.add(temp);
        });

        return result;
    }

    /**
     * 저장 가능 여부 판별
     *
     * @param params
     * @return
     * @throws Exception
     */
    public void isAdminCanSaveCheck(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));

        // 오늘 등록 갯수가 10개 이상인지 판별
        int count = serialPromoMapper.currentDateBySerialPromotionRegisterCount();
        if (count >= 10 && !"update".equals(params.getStr("type"))) {
            throw new CustomException("하루에 10번 이상 등록할 수 없습니다.\n" + count + "/10");
        }

        // 수정일 경우 진행전 상태에서만
        if ("update".equals(params.getStr("type"))) {
            boolean check = serialPromoMapper.isAdminCanUpdateCheck(params).getInt("check") == 1;
            if (!check) {
                throw new CustomException("진행전 상태에서만 수정가능합니다.");
            }
        }

        // 혜택 구분 : e포인트 일 경우
        if (CMConst.SRPTYPE_SRT_EPOINT.equals(params.getStr("srptype"))) {
        	params.put("epopayday", params.getStr("srpstday"));
			params.put("epovalidday", params.getStr("srpedday"));
            boolean check = ePointMapper.checkDuplEpoint(params).getInt("check") == 0;
            if (!check) {
                throw new CustomException("기존에 지급된 D포인트의 유효기간이 만료되지 않아 신규 수동지급이 불가 합니다");
            }
        }
    }
}
