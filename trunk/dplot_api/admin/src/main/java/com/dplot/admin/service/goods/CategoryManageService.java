package com.dplot.admin.service.goods;

import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author : JSK
 * @discription : 상품등록 Service
 * @fileName : CategoryManageService.java
 * @date : 2021.11.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.09	JSK			최초생성
 * -----------------------------------------------------------------
 */
public interface CategoryManageService {

    /**
     * 기본정보 - 카테고리 전체 조회
     *
     * @return List<SOMap>
     * @throws Exception
     */
    List<Map<String, Object>> selectCategoryList(SOMap params) throws Exception;

    /**
     * 카테고리 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectCategoryDetail(SOMap params) throws Exception;

    /**
     * 카테고리 트리구조 변경 저장
     *
     * @param params
     */
    void updateCategoryTree(SOMap params) throws Exception;

    /**
     * 카테고리 추가
     *
     * @param params
     * @param uploadFile 
     */
    void insertCategory(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

    /**
     * 카테고리 수정
     *
     * @param params
     * @param uploadFile 
     * @throws Exception
     */
    void updateCategory(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

    /**
     * 카테고리 삭제
     *
     * @param params
     * @throws Exception
     */
    int deleteCategory(SOMap params) throws Exception;
}
