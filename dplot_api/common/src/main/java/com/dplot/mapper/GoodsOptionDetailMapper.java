package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품옵션상세 Mapper (T_GOODS_OPTION_DETAIL)
 * @fileName	: GoodsContentMapper.java
 * @author		: JSK
 * @date		: 2021.12.01
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.01	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsOptionDetailMapper {
    
    /**
     * 상품옵션상세 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
	List<SOMap> selectGoodsOptionDetailList(SOMap param) throws Exception;
	
	/**
	 * 옵션코드 채번
	 * @param params
	 * @return String
	 */
	String selectOptionCode(SOMap params);
	
	/**
	 * 상품옵션상세 추가
	 * @param params
	 * @return int
	 */
	int insertGoodsOptionDetail(SOMap params);
	
	/**
	 * 상품옵션상세 수정
	 * @param params
	 * @return int
	 */
	int updateGoodsOptionDetail(SOMap params);
	
	/**
	 * 상품옵션상세 삭제
	 * @param params
	 * @return int
	 */
	int updateGoodsOptionDetailUse(SOMap params);
	
	/**
	 * 상품옵션상세 그룹바이하여 조회(인덱스는 1부터시작)
	 * 1 -> OPTIONNO1
     * 2 -> OPTIONNO2
     * 3 -> OPTIONNO3
     * 4 -> OPTIONNO4
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectGoodsOptionDetailByIndex(SOMap param) throws Exception;

	/**
	 * ERP 전송 상품 옵션 상세 조회
	 */
	List<SOMap> selectGoodsOptionDetailListERP(SOMap param) throws Exception;

	/**
	 * 모든 컬러 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectColorList(SOMap param);
	
	/**
	 * FRONT 옵션 코드 조회
	 * @param param
	 * @return
	 */
	SOMap selectGetOptionCode(SOMap param);
	
	/**
	 * 상품번호 및 옵션 코드로 조회 
	 * @param param
	 * @return
	 */
	SOMap selectDetailOptionCode(SOMap param);
	
	/**
	 * 교환출고상품 조회
	 * @param param
	 * @return List<SOMap>
	 */
	List<SOMap> selectExcGoodsList(SOMap param);
	
	/**
	 * 재고수정
	 * @param param
	 * @return
	 */
	int updateGoodsOptionDetailStockCnt(SOMap param);

	/**
	 * 직매입 ERP 연동 재고 수정
	 */
	int updateGoodsOptionDetailStockCntLoop(SOMap param);

	/**
	 * 검색 필터 색상 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFilterColorList(SOMap param);
	
	/**
	 * 대표상품 정상가, 판매가 수정
	 * @param param
	 * @return
	 */
	int updateOptionDetailMainGoodsPrice(SOMap param);
}
