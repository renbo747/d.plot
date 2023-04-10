/**
 * 
 */
package com.dplot.front.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CommonOrderService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.ComCartMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsOptionDetailMapper;
import com.dplot.util.Util;

/**
 * @FileName : CartServiceImpl.java
 * @Project : datapick_api
 * @Date : 2021. 12. 8. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 8.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
@Service
public class CartServiceImpl extends MallBaseService implements CartService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	
	@Autowired
	ComCartMapper comCartMapper;
	
	@Autowired
	GoodsOptionDetailMapper goodsOptionDetailMapper;
	
	@Autowired
	GoodsMapper goodsMapper;
	
	@Autowired
	CommonOrderService commonOrderService;
	
	
	@Override
	public SOMap selectCartCnt(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		
		SOMap result = new SOMap();
		if(cs.getInt("authmemberno") == 0) {
			if(param.getArrayList("items") != null) {
				result.put("cnt", param.getArrayList("items").size());
			} else {
				result.put("cnt", 0);
			}
		} else {
			int cnt = comCartMapper.selectCartCnt(param);
			result.put("cnt", cnt);
		}
		
		return result;
	}
	
	@Override
	public SOMap selectCartList(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("platform", cs.getStr("platform"));
		
		param.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		
		List<SOMap> list = new ArrayList<SOMap>() ;
		SOMap result = new SOMap();
		if(cs.getInt("authmemberno") == 0) {
			if(param.getArrayList("items") != null && param.getArrayList("items").size() > 0) {
				list = comCartMapper.selectCartListByItems(param);
			}
		} else {
			list = comCartMapper.selectCartList(param);
		}
		
		commonOrderService.calDelivAmt(list, "N");
		
		result.put("list", list);
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap saveCart(SOMap param) throws Exception {
		logger.debug("장바구니 담기 시작 ==> {}", param.toString());
		
		List<Map<String,Object>> items = param.getArrayList("items");
		
		SOMap dbparams = new SOMap();
		
		dbparams.put("siteid", cs.getStr("siteid"));
		dbparams.put("sessid", "");
		dbparams.put("userno", cs.getInt("authmemberno"));
		dbparams.put("userid", getMemberId());
		dbparams.put("ordpathtype", cs.getStr("platform"));
		
		boolean iscart = Util.flag2Bool(param.getDbStr("iscart"));
		
		for (Map<String,Object> map : items) {
			
			dbparams.putAll(map);
			
			logger.debug(dbparams.toString());
			if(Util.flag2Bool(dbparams.getStr("istrash"))){
				if(dbparams.getInt("cartidx") > 0) {
					comCartMapper.deleteCartByCartIdx(dbparams);
				}
				continue;
			}
			
			//기존 장바구니에 동일한 상품옵션 조회
			int cnt = comCartMapper.selectCartByGoods(dbparams);
			if(cnt == 0) {
				comCartMapper.insertCart(dbparams);
			} else {
				if(iscart) {
					comCartMapper.updateCart(dbparams);
				} else {
					comCartMapper.updateCartByGoods(dbparams);
				}
			}
		}
		
		SOMap result = this.selectCartList(param);
		return result;
	}
	
	@Override
	public SOMap deleteCart(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		
		int cnt = comCartMapper.deleteCart(param);
		SOMap result = new SOMap();
		result.put("cnt", cnt);
		
		return result;
	}
}
