package com.dplot.common.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.mapper.GoodsHistoryMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.util.Util;

@Service
public class HistoryService extends MallBaseService {
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsHistoryMapper goodsHistoryMapper;
	
	private SOMap columnMap;
	
	
	public HistoryService() {
		super();
		initMap();
	}
	
	/**
	 * 상품 변경이력 저장
	 * @param goodsno
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public List<SOMap> insertGoodsHistory(SOMap goodsInfo) throws Exception {
		List<SOMap> history = new ArrayList<SOMap>();
		
		SOMap params = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		params.put("goodsno", goodsInfo.get("goodsno"));
		params.put("userno", cs.getInt("authuserno"));
		params.put("kind", "");
		
		SOMap bfGoodsInfo = goodsMapper.selectAdminGoodsDetailForHist(params);
		
		Map<String, Object> fstInfo = goodsInfo.entrySet().stream()
	            .filter(entry -> entry.getKey().startsWith("fst") && "T".equals(entry.getValue()))
	            .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue()));

		//최초저장,최초임시저장 이력저장
		if (fstInfo != null && !fstInfo.isEmpty()) {
			for(String key : fstInfo.keySet()){
				String columnNm = columnMap.getDbStr(key);
				
				if(Util.isEmpty(columnNm)){
					continue;
				}
				SOMap hisParam = new SOMap(params);
				
				if(Util.equal("T", goodsInfo.getDbStr(key))) {
					hisParam.put("kind", CMConst.GOODS_HISTORY_INSERT);
					hisParam.put("summary", columnNm);
					goodsHistoryMapper.insertGoodsHistory(hisParam);
					history.add(hisParam);
					continue;
				}
			}
		} else {
			for(String key : goodsInfo.keySet()){
				String columnNm = columnMap.getDbStr(key);
				
				if(Util.isEmpty(columnNm)){
					continue;
				}
				SOMap hisParam = new SOMap(params);
				
				//상품관련 관리목록 수정여부 이력저장
				if (key.startsWith("chg")) {
					if(Util.equal("T", goodsInfo.getDbStr(key))) {
						hisParam.put("kind", CMConst.GOODS_HISTORY_UPDATE);
						hisParam.put("summary", columnNm + " 수정");
						goodsHistoryMapper.insertGoodsHistory(hisParam);
						history.add(hisParam);
						
						continue;
					}
				}
				
				//이전 값과 비교하여 변경되었을 경우 이력저장
				String bfData = bfGoodsInfo.getDbStr(key, "");
				String afData = goodsInfo.getDbStr(key, "");
				
				if(!Util.equal(bfData, afData)) {
					hisParam.put("kind", CMConst.GOODS_HISTORY_UPDATE);
					hisParam.put("summary", columnNm + " 수정");
					goodsHistoryMapper.insertGoodsHistory(hisParam);
					history.add(hisParam);
				}
			}
		}
				
		return history;
	}
	
	private void initMap() {
		columnMap = new SOMap();
		columnMap.put("ispbgoods", "판매구분");
		columnMap.put("dealerno", "파트너사");
		columnMap.put("goodsname", "상품명");
		columnMap.put("summary", "상품요약설명");
		columnMap.put("keyword", "검색키워드");
		columnMap.put("brandidx", "브랜드");
		columnMap.put("mdifidx", "담당MD");
		columnMap.put("muappchtype", "적용채널");
		columnMap.put("istaxfree", "과세여부");
		columnMap.put("goodsselltype", "판매상태");
		columnMap.put("disstdate", "전시시작일자");
		columnMap.put("dissthour", "전시시작시간");
		columnMap.put("disstmin", "전시시작분");
		columnMap.put("diseddate", "전시종료일자");
		columnMap.put("disedhour", "전시종료시간");
		columnMap.put("disedmin", "전시종료분");
		columnMap.put("marketprice", "정상가");
		columnMap.put("price", "판매가");
		columnMap.put("minordcnt", "최소주문수량");
		columnMap.put("maxordcnt", "최대주문수량");
		columnMap.put("daymaxordcnt", "1일최대주문수량");
		columnMap.put("permaxordcnt", "1인당최대구매수량");
		columnMap.put("isdisplay", "전시여부");
		columnMap.put("isfrstsale", "첫구매할인혜택여부");
		columnMap.put("iscncappr", "취소승인필요여부");
		columnMap.put("goodsdivtype", "상품상태");
		columnMap.put("selltarget", "판매대상");
		columnMap.put("mumemlvtype", "대상회원등급");
		columnMap.put("mugicontype", "아이콘설정");
		columnMap.put("noticecontent", "공지이미지");
		columnMap.put("introcontent", "인트로이미지");
		columnMap.put("pccontent", "PC용상품상세설명");
		columnMap.put("mobilecontent", "모바일용상품상세설명");
		columnMap.put("delividx", "배송정보");
		columnMap.put("iscombdeliv", "배송유형");
		columnMap.put("notifytplidx", "상품정보고시 상품군");
		columnMap.put("kcdivtype", "KC인증대상");
		columnMap.put("aswarmonth", "A/S보증기간");
		columnMap.put("asnotice", "A/S안내문구");
		columnMap.put("mumembertype", "대상회원유형");
		columnMap.put("isopenreview", "리뷰공개여부");
		columnMap.put("goodsapprtype", "상품승인상태");
		columnMap.put("chgCate", "카테고리");
		columnMap.put("chgSpUser", "대상특정회원");
		columnMap.put("chgOptItem", "옵션항목");
		columnMap.put("chgOpt", "옵션");
		columnMap.put("chgErpOpt", "ERP연결옵션");
		columnMap.put("chgNotify", "상품정보고시");
		columnMap.put("chgKccert", "KC인증내용");
		columnMap.put("chgGoodsGrp", "리뷰묶어보기상품");
		columnMap.put("chgGoodsAdd", "추가상품");
		columnMap.put("chgMemo", "관리자메모");
		columnMap.put("chgConstGoods", "구성상품");
		columnMap.put("chgPcImg", "PC대표이미지");
		columnMap.put("chgMoImg", "MO대표이미지");
		columnMap.put("chgAddImg", "추가이미지");
		columnMap.put("chgKcAttach", "KC첨부파일");
		columnMap.put("fstTempSave", "최초임시저장");
		columnMap.put("fstSave", "최초저장");
	}
}
