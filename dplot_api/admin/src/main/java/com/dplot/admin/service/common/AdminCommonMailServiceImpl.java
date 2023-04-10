package com.dplot.admin.service.common;

import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author : ywm2004
 * @discription : 메일 보내기 공통 팝업 ServiceImpl
 * @fileName : AdminCommonMailServiceImpl.java
 * @date : 2022-02-09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-02-09	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCommonMailServiceImpl extends MallBaseService implements AdminCommonMailService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    CJMessageService cjMessageService;

    @Resource(name="propertiesFactory")
    private Properties prop;

    /**
     * 유저 정보 셋팅
     *
     * @param params
     * @return
     */
    @Override
    public SOMap selectUserInfo(SOMap params) throws Exception {
        return memberMapper.selectMemberInfoByUserNo(params);
    }

    /**
     * 관리자에서 메일 보내기
     */
    @Override
    public SOMap sendMail(SOMap params) throws Exception {

//        List<SOMap> receiverList = new ArrayList<>();
//        SOMap receiver = new SOMap();
//        receiver.put("name", params.get("name"));
//        receiver.put("email", params.get("email"));
//        receiver.put("content", params.get("content"));
//        receiver.put("note1", params.get("content"));
//
//        receiverList.add(receiver);

        return cjMessageService.sendMemberEmail(params);
    }
}
