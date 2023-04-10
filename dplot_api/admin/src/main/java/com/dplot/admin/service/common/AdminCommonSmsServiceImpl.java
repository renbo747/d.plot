package com.dplot.admin.service.common;

import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCommonSmsServiceImpl extends MallBaseService implements AdminCommonSmsService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    CJMessageService cjMessageService;

    /**
     * 유저 정보 조회
     *
     * @param params
     * @return
     */
    @Override
    public SOMap selectUserInfo(SOMap params) throws Exception {
        SOMap result = new SOMap();
        result.put("userinfo", memberMapper.selectMemberInfoByUserNo(params));
        return result;
    }

    @Override
    public SOMap sendMessage(SOMap params) throws Exception {
        List<SOMap> list = new ArrayList<>();
        list.add(params);
        return cjMessageService.sendMessage(params, list);
    }
}
