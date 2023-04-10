package com.dplot.admin.service.promotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import com.dplot.mapper.EPointMapper;
import com.dplot.mapper.EpointPayMapper;
import com.dplot.mapper.EpointUseMapper;
import com.dplot.mapper.EpointUserMapper;
import com.dplot.mapper.IFLogMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.util.DateTime;

/**
 * @author : ywm2004
 * @discription : E포인트 관리 ServiceImpl
 * @fileName : AdminPromotionEpointServiceImpl.java
 * @date : 2022-01-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPromotionEpointServiceImpl extends MallBaseService implements AdminPromotionEpointService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminPromotionEpointServiceImpl.class);

    @Autowired
    private EPointMapper ePointMapper;

    @Autowired
    private EpointPayMapper epointPayMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private EpointPayMapper ePointPayMapper;

    @Autowired
    private EpointUseMapper epointUseMapper;

    @Autowired
    private EpointUserMapper epointUserMapper;

	@Autowired
	private CommonService commonService;

    /**
     * epoint 저장
     *
     * @param params
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertEpoint(SOMap params) throws Exception {
        // 저장 가능 여부 판별
        this.isCanSaveCheck(params);

        try {
	        ePointMapper.insertEpoint(params);
	
	        // [재호] e포인트 예약 지급 관련 -> 배치 처리
	        // 즉시 지급일 경우
	        if ("T".equals(params.getStr("isnowpay"))) {
	            List<SOMap> ePayList = new ArrayList<>();
	
	            // 특정 유형/등급 대상
	            if ("T".equals(params.getStr("ismemtype")) && params.containsKey("mumemlvtype") && params.containsKey("mumembertype")) {
	                ePayList = this.getSpecificTypeList(params, CMConst.EPO_PAY_MANUAL);
	            }
	
	            // 특정 회원 대상
	            if ("F".equals(params.getStr("ismemtype")) && params.containsKey("memberlist")) {
	                ePayList = this.getSpecificMemberList(params, CMConst.EPO_PAY_MANUAL);
	            }
	
	            // 지급 인원이 있을 경우
	            if (!ePayList.isEmpty()) {
	                SOMap payParams = new SOMap();
	                payParams.put("payarr", ePayList);
	                payParams.put("epoidx", params.get("epoidx"));
	                commonService.paymentEpotint(payParams);
	            }
	        } else {
	            // 예약지급,
	            SOMap epoUserParams = new SOMap();
	            List<SOMap> memberList = this.getEpointUserList(params.getStr("epoidx"), params.getStr("reguserid"), params.getArrayList("memberlist"));
	            
	            if(!memberList.isEmpty()) {
	            	epoUserParams.put("memberlist", memberList);
	            	epointUserMapper.insertEpointUser(epoUserParams);	            	
	            }
	        }
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("D포인트 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    }

    /**
     * e포인트 수정
     *
     * @param params
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateEpoint(SOMap params) throws Exception {
        params.put("reguserid", cs.getStr("authuserid"));
        params.put("moduserid", cs.getStr("authuserid"));

        this.isCanSaveCheck(params);


        try {
	        ePointMapper.updateEpoint(params);
	
	        String changeNowPay = params.getStr("ischangenowpay"); // 즉시지급 변경 여부
	        String changeValidDay = params.getStr("ischangevaliday"); // 지급일 변경 여부
	        String changePayDay = params.getStr("ischangepayday"); // 유효일 변경 여부
	
	        String changeMemType = params.getStr("ischangememtype"); // 대상 범위 변경 여부
	        String changeMuType = params.getStr("ischangemutype"); // 회원 유형 변경 여부
	        String changeMuLy = params.getStr("ischangemulv"); // 회원 등급 변경 여부
	
	
	        // 즉시 지급, 예약 지급 변경 여부
	        if ("T".equals(changeNowPay)) {
	            // - 예약 지급 -> 즉시 지급으로 변경된 경우
	            // 1) 이전 t_epoint_user 테이블 데이터 삭제
	            // 2) 새로운 조건(특정 유형/등급, 특정 회원)으로 t_epoint_pay에 데이터 생성
	            if ("T".equals(params.getStr("isnowpay"))) {
	                SOMap deleteEpointUserParamas = new SOMap();
	                deleteEpointUserParamas.put("epoidx", params.getStr("epoidx"));
	                epointUserMapper.deleteEpointUser(deleteEpointUserParamas);
	
	                List<SOMap> ePayList;
	                // 특정 유형/등급 대상
	                if ("T".equals(params.getStr("ismemtype"))) {
	                    ePayList = this.getSpecificTypeList(params, CMConst.EPO_PAY_MANUAL);
	                }
	
	                // 특정 회원 대상
	                else {
	                    ePayList = this.getSpecificMemberList(params, CMConst.EPO_PAY_MANUAL);
	                }
	
	                // 지급 인원이 있을 경우
	                if (!ePayList.isEmpty()) {
	                    SOMap payParams = new SOMap();
	                    payParams.put("payarr", ePayList);
	                    ePointPayMapper.insertEpointPay(payParams);
	                }
	            }
	
	            // - 즉시 지급 -> 예약 지급으로 변경된 경우
	            // 1) 지급된 t_epoint_pay 데이터 삭제
	            // 2) 특정 회원일 경우만 t_epoint_user에 저장
	            else {
	                SOMap deleteEpointPayParams = new SOMap();
	                deleteEpointPayParams.put("epoidx", params.getStr("epoidx"));
	                epointPayMapper.deleteEpointPay(deleteEpointPayParams);
	
	                // 특정 회원
	                if ("F".equals(params.getStr("ismemtype"))) {
	                    // 예약 회원 테이블 저장
	                    SOMap epoUserParams = new SOMap();
	                    epoUserParams.put("memberlist", this.getEpointUserList(params.getStr("epoidx"), params.getStr("reguserid"), params.getArrayList("memberlist")));
	                    epointUserMapper.insertEpointUser(epoUserParams);
	                }
	            }
	        }
	
	        // 대상범위가 변경된 경우
	        else if ("T".equals(changeMemType)) {
	            // - 즉시 지급일 경우
	            if ("T".equals(params.getStr("isnowpay"))) {
	                List<SOMap> ePayList;
	
	                // 기존 pay 데이터 삭제
	                SOMap deleteParams = new SOMap();
	                deleteParams.put("epoidx", params.getStr("epoidx"));
	                epointPayMapper.deleteEpointPay(deleteParams);
	
	                if ("T".equals(params.getStr("ismemtype"))) {
	                    // 특정 유형/등급 대상
	                    ePayList = this.getSpecificTypeList(params, CMConst.EPO_PAY_MANUAL);
	                } else {
	                    // 특정 회원 대상
	                    ePayList = this.getSpecificMemberList(params, CMConst.EPO_PAY_MANUAL);
	                }
	
	                // 새로운 pay 데이터 저장
	                if (!ePayList.isEmpty()) {
	                    SOMap payParams = new SOMap();
	                    payParams.put("payarr", ePayList);
	                    ePointPayMapper.insertEpointPay(payParams);
	                }
	            }
	            // - 예약 지급일 경우
	            else {
	                if ("F".equals(params.getStr("ismemtype"))) {
	                    // 유형/등급 -> 회원 대상일 경우
	                    SOMap epoUserParams = new SOMap();
	                    epoUserParams.put("memberlist", this.getEpointUserList(params.getStr("epoidx"), params.getStr("reguserid"), params.getArrayList("memberlist")));
	                    epointUserMapper.insertEpointUser(epoUserParams);
	                } else {
	                    // 회원 대상 -> 유형/등급
	                    SOMap deleteEpointUserParamas = new SOMap();
	                    deleteEpointUserParamas.put("epoidx", params.getStr("epoidx"));
	                    epointUserMapper.deleteEpointUser(deleteEpointUserParamas);
	                }
	            }
	        }
	
	        // 즉시지급 && 유형이나 등급이 변경된 경우
	        else if ("T".equals(params.getStr("isnowpay")) && ("T".equals(changeMuType) || "T".equals(changeMuLy))) {
	            // - 즉시 지급일 경우 원래 있던 t_epoint_pay 데이터를 삭제후 새로 지급
	            List<SOMap> ePayList;
	
	            // 기존 pay 데이터 삭제
	            SOMap deleteParams = new SOMap();
	            deleteParams.put("epoidx", params.getStr("epoidx"));
	            epointPayMapper.deleteEpointPay(deleteParams);
	
	            if ("T".equals(params.getStr("ismemtype"))) {
	                // 특정 유형/등급 대상
	                ePayList = this.getSpecificTypeList(params, CMConst.EPO_PAY_MANUAL);
	            } else {
	                // 특정 회원 대상
	                ePayList = this.getSpecificMemberList(params, CMConst.EPO_PAY_MANUAL);
	            }
	
	            // 새로운 pay 데이터 저장
	            if (!ePayList.isEmpty()) {
	                SOMap payParams = new SOMap();
	                payParams.put("payarr", ePayList);
	                ePointPayMapper.insertEpointPay(payParams);
	            }
	        }
	
	        // 특정 회원 대상 인원이 변경된 경우
	        else {
	            ArrayList<Map<String, Object>> deleteMember = params.getArrayList("deletemember");
	            ArrayList<Map<String, Object>> insertMember = params.getArrayList("insertmember");
	
	            // - 즉시 지급일 경우 원래 있던 t_epoint_pay 데이터에서 insert,delete
	            if ("T".equals(params.getStr("isnowpay"))) {
	                if (!deleteMember.isEmpty()) {
	                    List<String> userNoList = deleteMember.stream().map(obj -> obj.get("userno").toString()).collect(Collectors.toList());
	                    SOMap deleteParams = new SOMap();
	                    deleteParams.put("usernolist", userNoList);
	                    deleteParams.put("epoidx", params.getStr("epoidx"));
	                    epointPayMapper.deleteEpointPay(deleteParams);
	                }
	
	                if (!insertMember.isEmpty()) {
	                    List<SOMap> insertList = this.getSpecificMemberList(params, CMConst.EPO_PAY_MANUAL, insertMember);
	                    SOMap insertParams = new SOMap();
	                    insertParams.put("payarr", insertList);
	                    epointPayMapper.insertEpointPay(insertParams);
	                }
	            }
	
	            // - 예약 지급일 경우 원래 있던 t_epoint_user 데이터에서 insert, delete
	            else {
	                if (!deleteMember.isEmpty()) {
	                    List<String> userNoList = deleteMember.stream().map(obj -> obj.get("userno").toString()).collect(Collectors.toList());
	                    SOMap deleteParams = new SOMap();
	                    deleteParams.put("usernolist", userNoList);
	                    deleteParams.put("epoidx", params.getStr("epoidx"));
	                    epointUserMapper.deleteEpointUser(deleteParams);
	                }
	
	                if (!insertMember.isEmpty()) {
	                    List<SOMap> insertList = this.getEpointUserList(params.getStr("epoidx"), params.getStr("reguserid"), insertMember);
	                    SOMap insertParams = new SOMap();
	                    insertParams.put("memberlist", insertList);
	                    epointUserMapper.insertEpointUser(insertParams);
	                }
	            }
	        }
	
	        // 즉시지급 && e포인트 지급일이나 유효일이 변경된 경우
	        // - 즉시 지급일 경우 원래 있던 t_epoint_pay 데이터 지급,유효일 변경
	        if ("T".equals(params.getStr("isnowpay")) && ("T".equals(changePayDay) || "T".equals(changeValidDay))) {
	            SOMap payParams = new SOMap();
	            payParams.put("epoidx", params.getStr("epoidx"));
	            payParams.put("epostday", params.getStr("epostday"));
	            payParams.put("epoedday", params.getStr("epoedday"));
	            epointPayMapper.updateEpointPay(payParams);
	        }
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("D포인트 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }

    }

    /**
     * 저장 여부 판별
     *
     * @param params
     * @return
     * @throws Exception
     */
    public void isCanSaveCheck(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));

        // 업데이트시 체크
        if ("update".equals(params.getStr("type"))) {
            boolean check = ePointMapper.isAdminCanUpdateCheck(params).getInt("check") == 1;
            if (!check) {
                throw new CustomException("진행전 상태에서만 수정가능합니다.");
            }
        }

        // e포인트 기간 중복 체크
        boolean check = ePointMapper.checkDuplEpoint(params).getInt("check") == 0;
        if (!check) {
            throw new CustomException("기존에 지급된 D포인트의 유효기간이 만료되지 않아 신규 수동지급이 불가 합니다");
        }
    }

    /**
     * e포인트 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEpointList(SOMap params) throws Exception {
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

        result.put("list", ePointMapper.selectAdminEpointList(params));
        result.put("listcount", ePointMapper.selectAdminEpointStateList(params));
        return result;
    }

    /**
     * e포인트 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectEPointDetail(SOMap params) throws Exception {
        SOMap result = new SOMap();
        SOMap detailMap = ePointMapper.selectAdminEpointDetail(params);

        // 특정 회원 대상일 경우 회원 정보 조회
        if ("F".equals(detailMap.getStr("ismemtype"))) {
            List<String> userNoList = Arrays.asList(detailMap.getStr("userno").split(","));
            if (!userNoList.isEmpty()) {
                SOMap memberParams = new SOMap();
                memberParams.put("siteid", cs.getStr("siteid"));
                memberParams.put("usernolist", userNoList);
                List<SOMap> memberList = memberMapper.selectAdminMemberList(memberParams);
                result.put("memberlist", memberList);
            }
        }

        // 적립/차감 내역
        List<SOMap> usageDetailList = (List<SOMap>) this.selectUsageDetailList(params).get("list");

        result.put("list", detailMap);
        result.put("usagedetaillist", usageDetailList);
        return result;
    }

    /**
     * 유저 정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectMemberInfo(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        SOMap result = new SOMap();
        result.put("list", memberMapper.selectAdminMemberList(params));
        return result;
    }

    /**
     * 적립/차감 내역 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectUsageDetailList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        List<SOMap> totalList = new ArrayList<>();
        List<SOMap> payList = epointPayMapper.selectAdminEpointPayList(params);
        List<SOMap> useList = epointUseMapper.selectAdminEpointUseList(params);

        totalList.addAll(payList);
        totalList.addAll(useList);

        // 정렬 정보가 있다면 정렬
        if (params.containsKey("psort")) {
            String orderStr = params.getStr("psort").split("_")[0];
            String orderSort = params.getStr("psort").split("_")[1];

            if (orderSort.equals("desc")) { // 내림 차순
                totalList = totalList.stream()
                        .sorted(Comparator.comparing(o -> o.getStr(orderStr)))
                        .collect(Collectors.toList());
            } else if (orderSort.equals("asc")) { // 오름 차순
                totalList = totalList.stream()
                        .sorted((o1, o2) -> o2.getStr(orderStr).compareTo(o1.getStr(orderStr)))
                        .collect(Collectors.toList());
            }
        } else {
            // 정렬정보가 없으면 idx로 정렬
            totalList = totalList.stream()
                    .sorted(Comparator.comparing(o -> o.getStr("regdate"), Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        }

        result.put("list", totalList);
        return result;
    }

    /**
     * 특정 회원 대상
     *
     * @param params
     * @return
     * @throws Exception
     */
    private List<SOMap> getSpecificMemberList(SOMap params, String epoPayType) throws Exception {
        List<SOMap> result = new ArrayList<>();
        ArrayList<Map<String, Object>> memberList = params.getArrayList("memberlist");
        memberList.forEach(obj -> {
            SOMap pay = new SOMap();
            pay.put("epoidx", params.getStr("epoidx"));
            pay.put("userno", obj.get("userno"));
            pay.put("ordercode", "");
            pay.put("epostday", params.getStr("epostday"));
            pay.put("epoedday", params.getStr("epoedday"));
            pay.put("epopaytype", epoPayType);
            pay.put("epopayreason", params.getStr("eporeason"));
            pay.put("paymembertype", obj.get("dadamembertype"));
            pay.put("paymemlvtype", obj.get("memlvtype"));
            pay.put("paypoint", params.getStr("payepoint"));
            pay.put("isepointdup", params.getStr("isepointdup"));
            result.add(pay);
        });

        return result;
    }

    private List<SOMap> getSpecificMemberList(SOMap params, String epoPayType, ArrayList<Map<String, Object>> list) throws Exception {
        List<SOMap> result = new ArrayList<>();
        list.forEach(obj -> {
            SOMap pay = new SOMap();
            pay.put("epoidx", params.getStr("epoidx"));
            pay.put("userno", obj.get("userno"));
            pay.put("ordercode", "");
            pay.put("epostday", params.getStr("epostday"));
            pay.put("epoedday", params.getStr("epoedday"));
            pay.put("epopaytype", epoPayType);
            pay.put("epopayreason", params.getStr("eporeason"));
            pay.put("paymembertype", obj.get("dadamembertype"));
            pay.put("paymemlvtype", obj.get("memlvtype"));
            pay.put("paypoint", params.getStr("payepoint"));
            pay.put("isepointdup", params.getStr("isepointdup"));
            result.add(pay);
        });

        return result;
    }

    /**
     * 특정 유형/등급 대상
     *
     * @param params
     * @param epoPayType
     * @return
     * @throws Exception
     */
    private List<SOMap> getSpecificTypeList(SOMap params, String epoPayType) throws Exception {
        List<SOMap> result = new ArrayList<>();

        SOMap memberParams = new SOMap();
        List<String> muMemberTypeArr = Arrays.asList(params.getStr("mumembertype").split(","));
        List<String> muMemLvTypeArr = Arrays.asList(params.getStr("mumemlvtype").split(","));
        memberParams.put("mumembertypearr", muMemberTypeArr);
        memberParams.put("mumemlvtypearr", muMemLvTypeArr);
        // 맴버 유형/등급별 조회
        List<SOMap> memberList = memberMapper.selectActiveMemberList(memberParams);
        memberList.forEach(obj -> {
            SOMap pay = new SOMap();
            pay.put("epoidx", params.getStr("epoidx"));
            pay.put("userno", obj.get("userno"));
            pay.put("ordercode", "");
            pay.put("epostday", params.getStr("epostday"));
            pay.put("epoedday", params.getStr("epoedday"));
            pay.put("epopaytype", epoPayType);
            pay.put("epopayreason", params.getStr("eporeason"));
            pay.put("paymembertype", obj.get("dadamembertype"));
            pay.put("paymemlvtype", obj.get("memlvtype"));
            pay.put("paypoint", params.getStr("payepoint"));
            pay.put("isepointdup", params.getStr("isepointdup"));
            result.add(pay);
        });

        return result;
    }

    /**
     * e포인트 유저
     *
     * @param list
     * @return
     */
    private List<SOMap> getEpointUserList(String epoIdx, String regUserId, ArrayList<Map<String, Object>> list) {
        List<SOMap> result = new ArrayList<>();
        list.forEach(obj -> {
            SOMap temp = new SOMap();
            temp.put("epoidx", epoIdx);
            temp.put("userno", obj.get("userno"));
            temp.put("reguserid", regUserId);
            result.add(temp);
        });

        return result;
    }

}
