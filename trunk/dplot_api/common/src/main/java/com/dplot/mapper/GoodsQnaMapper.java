package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @author : ywm2004
 * @discription : 상품_QA 테이블 Mapper (t_goods_qna)
 * @fileName : GoodsQnaMapper.java
 * @date : 2021-11-16
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-16	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsQnaMapper {

    /**
     * 상품 QA 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectQnaList(SOMap params);

    /**
     * 상품 QA 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectQnaDtl(SOMap params);

    /**
     * 상품 QA 대기, 완료 갯수
     *
     * @param params
     * @return
     */
    SOMap selectQnaListState(SOMap params);

    /**
     * 상품 QA 수정
     *
     * @param params
     */
    void updateQna(SOMap params);
    
	/**
	 * 상품 Q&A 메인
	 * @param params
	 * @return
	 */
	List<SOMap> selectInquiryQna(SOMap params);
	
	/**
	 * 상품 Q&A 카운트
	 * @param params
	 * @return
	 */
	int selectInquiryQnaCount(SOMap params);
	

	/**
	 * 상품 삭제 전 내 문의 인지 확인
	 * @param params
	 * @return
	 */
	SOMap selectMyQna(SOMap params);
	
	/**
	 * 상품별 내 문의 삭제
	 * @param params
	 * @return
	 */
	int deleteMyQna(SOMap params);	
	
	/**
	 * 상품 Q&A 수정
	 * @param params
	 * @return
	 */
	int updateMyQna(SOMap params);
	
	/**
	 * 상품별 문의 전체
	 * @param params
	 * @return
	 */
	List<SOMap> selectQnaGoods(SOMap params);
	
	
	/**
	* 상품별 문의 전체 카운트
	* @param params
	* @return
	*/
	int selectQnaGoodsCount(SOMap params);
	
	/**
	 * 상품별 문의 등록
	 * @param params
	 * @return
	 */
	int insertGoodsQna(SOMap params);
	
	
	/**상품별 문의 수정
	 * @param params
	 * @return
	 */
	int updateGoodsQna(SOMap params);

	/**
	 * 상품 Q&A Admin 상세조회
	 * @param params
	 * @return
	 */
	SOMap selectAdminQnaDtl(SOMap params);

	
	
	
    
}
