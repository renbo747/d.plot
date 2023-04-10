package com.dplot.admin.service.cs;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.DealerChargeMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.PartnerInquiryMapper;
import com.dplot.util.DateTime;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : ywm2004
 * @discription : CS관리 파트너사 문의 ServiceImpl
 * @fileName : AdminCSPartnersServiceImpl.java
 * @date : 2021-11-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-22	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCSPartnersServiceImpl extends MallBaseService implements AdminCSPartnersService {

    @Autowired
    DealerMapper dealerMapper;

    @Autowired
    PartnerInquiryMapper partnerInquiryMapper;

    @Autowired
    DealerChargeMapper dealerChargeMapper;

    @Override
    public SOMap selectPartnersList(SOMap params) throws Exception {
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

        params.put("siteid", cs.getStr("siteid"));

        result.put("list", partnerInquiryMapper.selectPartnerInquiryList(params));
        result.put("liststate", partnerInquiryMapper.selectPartnerInquiryListState(params));
        return result;
    }

    @Override
    public SOMap selectPartnersDtl(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        result.put("list", partnerInquiryMapper.selectPartnerInquiryDtl(params));
        return result;
    }

    /**
     * (셀렉박스) 파트너사 목록 조회
     *
     * @param params
     * @return
     */
    @Override
    public SOMap selectBoxPartnersList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        result.put("list", dealerMapper.selectPartnersList(params));
        return result;
    }

    /**
     * (셀렉박스) 담당자 목록 조회
     *
     * @param params
     * @return
     */
    @Override
    public SOMap selectBoxChargeList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("list", dealerChargeMapper.selectDealerChargeList(params));
        return result;
    }

    /**
     * 파트너사 문의 질문
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void insertPartners(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        params.put("isreply", "F");
        params.put("istrash", "F");

        // 담당자 idx가 비어있다면 null로 초기화
        if (StringUtils.isBlank(params.getStr("partchargeidx"))) {
            params.put("partchargeidx", null);
        }
        partnerInquiryMapper.insertPartnerInquiry(params);
    }

    /**
     * 파트너사 문의 수정
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void updatePartners(SOMap params) throws Exception {
        // [재호] 파트너사, 관리자 로그인 여부에 따른 완료 로직 필요함 -> 완료
        if(!Boolean.parseBoolean(params.getStr("isadmin"))) {
            params.put("repuserno", cs.getInt("authuserno"));
        }
        partnerInquiryMapper.updatePartnerInquiry(params);
    }
}

