package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 사은품 Mapper (T_GIFT)
 * @fileName	: GiftMapper.java
 * @author		: LKW
 * @date		: 2021.12.20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.20	LKW			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GiftMapper {
    
    /**
     * 사은품 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectGiftList(SOMap param) throws Exception;
    
    /**
     * 사은품 상태별 수량 조회
     * @param param
     * @return
     * @throws Exception
     */
    SOMap selectGiftState(SOMap param) throws Exception;
	
	/**
	 * 사은품 사용여부 수정
	 * @param params
	 * @return int
	 */
	int updateGiftUse(SOMap params);
	
	/**
	 * 사은품 엑셀 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectGiftExcelList(SOMap param) throws Exception;

	/**
	 *  사은품 추가
	 * @param params
	 * @return
	 */
	int insertGift(SOMap params);

	/**
	 * 사은품 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectGiftDetail(SOMap params);

	/**
	 * 사은품 수정
	 * @param params
	 * @return
	 */
	int modifyGift(SOMap params);
	
	/**
	 * 상품별 프로모션 사은품 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectGiftListByGoods(SOMap param) throws Exception;

	/**
	 * 사은품 ERP 전송 조회
	 */
	List<SOMap> selectGiftERPData(SOMap param) throws Exception;

	/**
	 * 사은품 재고 확인 리스트 조회
	 */
	List<SOMap> selectGiftStockUpdateList(SOMap param) throws Exception;

	/**
	 * 사은품 ERP 재고 업데이트
	 */
	int updateGiftStockCntLoop(SOMap param);
}
