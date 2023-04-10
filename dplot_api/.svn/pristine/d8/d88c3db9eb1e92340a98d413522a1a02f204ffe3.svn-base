package com.dplot.admin.service.partners;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.BoardAgreeMapper;
import com.dplot.mapper.BoardPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * 설명 : 파트너사 미동의, 동의 목록 서비스
 * 생성 : 정재호
 * 일지 : 2021-11-08
 */
@Service
public class AdminPartnersAgreeListPopupServiceImpl extends MallBaseService implements AdminPartnersAgreeListPopupService {

    @Autowired
    BoardAgreeMapper boardAgreeMapper;

    @Autowired
    BoardPostMapper boardPostMapper;

    @Override
    public Map<String, Object> selectAgreeList(Map<String, Object> params) throws Exception {
        Map<String, Object> result = new HashMap<>();

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pageCount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startPage", startPage);
        params.put("endPage", pageCount);
        params.put("siteid", cs.getStr("siteid"));
        params.put("boardtype", "NTT002");

        result.put("list", boardAgreeMapper.selectAgreePagingList(params));
        result.put("listCount", boardAgreeMapper.selectAgreeListCount(params));

        return result;
    }
}
