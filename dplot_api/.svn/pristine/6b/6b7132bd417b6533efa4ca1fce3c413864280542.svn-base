package com.dplot.admin.service.cs;

import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.GoodsQnaMapper;
import com.dplot.mapper.ReqTemplateMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.util.DateTime;

/**
 * @author : ywm2004
 * @discription : CS관리 상품 문의 ServiceImpl
 * @fileName : AdminCSProductServiceImpl.java
 * @date : 2021-11-16
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-16	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCSProductServiceImpl extends MallBaseService implements AdminCSProductService {

    @Autowired
    GoodsQnaMapper goodsQnaMapper;

    @Autowired
    DealerMapper dealerMapper;
    
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    ReqTemplateMapper reqTemplateMapper;
    
    @Autowired
    CJMessageService cjMessageService;
    
    @Resource(name="propertiesFactory")
    private Properties prop;

    @Override
    public SOMap selectProductList(SOMap params) throws Exception {
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

        result.put("partnerslist", dealerMapper.selectPartnersList(params));
        result.put("list", goodsQnaMapper.selectQnaList(params));
        result.put("statelist", goodsQnaMapper.selectQnaListState(params));
        return result;
    }

    @Override
    public SOMap selectProductDtl(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 파일 조회
        SOMap fileParams = new SOMap();
        fileParams.put("orgidx", params.get("idx"));
        fileParams.put("imgtype", CMConst.IMG_TYPE_QNA);

        // 상세 조회
        result.put("list", goodsQnaMapper.selectAdminQnaDtl(params));
        result.put("template", reqTemplateMapper.selectAllUseTemplateList(params));
        return result;
    }

    @Override
    public void updateProduct(SOMap params) throws Exception {
        // 특수문자 html 태그로 변경
        String content = StringEscapeUtils.unescapeHtml(params.get("repcontent").toString());
        params.put("repcontent", content);
        params.put("repuserno", cs.getStr("authuserno"));

        // 답변 상태 완료
        if(params.containsKey("isreply")) {
            params.put("isreply", "T");
        }

        goodsQnaMapper.updateQna(params);

        String domain = prop.getProperty("front.mobile.domain");
        SOMap tempMap = new SOMap();
        tempMap.put("typename", "상품Q&A");
        tempMap.put("idx", params.getStr("idx"));
//        tempMap.put("url", String.format("%s%s", domain, "/cs/inquiry/index/qna"));
        tempMap.put("number", params.getStr("mobile"));
        tempMap.put("userno", params.getStr("userno"));
        
        cjMessageService.sendCustomerQuestionsAndAnswer(tempMap);
    }
}
