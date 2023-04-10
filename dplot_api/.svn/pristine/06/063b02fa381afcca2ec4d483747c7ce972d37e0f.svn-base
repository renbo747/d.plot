package com.dplot.admin.service.partners;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.BoardAgreeMapper;
import com.dplot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 파트너사 선택 리스트 팝업 ServiceImpl
 * @fileName : AdminPartnersListPopupServiceImpl.java
 * @date : 2021-11-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPartnersListPopupServiceImpl extends MallBaseService implements AdminPartnersListPopupService {

    @Autowired
    BoardAgreeMapper boardAgreeMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Map<String, Object> selectPartnersList(Map<String, Object> params) throws Exception {
        Map<String, Object> result = new HashMap<>();

        SOMap soMap = new SOMap();
        soMap.put("siteid", cs.getStr("siteid"));
        soMap.put("psort", params.get("psort"));

        result.put("list", userMapper.selectPartnersList(soMap));
        return result;
    }

    @Override
    public Map<String, Object> selectCheckList(Map<String, Object> params) throws Exception {
        Map<String, Object> result = new HashMap<>();
        params.put("siteid", cs.getStr("siteid"));

        Map<String, Object> checkList = boardAgreeMapper.selectCheckList(params);
        String[] noList = checkList.get("nolist").toString().split(",");
        String[] nameList = checkList.get("namelist").toString().split(",");
        String[] idList = checkList.get("useridlist").toString().split(",");

        result.put("nolist", noList);
        result.put("namelist", nameList);
        result.put("useridlist", idList);

        return result;
    }
}
