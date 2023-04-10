package com.dplot.admin.controller.goods;

import com.dplot.admin.service.goods.CategoryManageService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/goods/category")
public class CategoryManageController {

    @Autowired
    private CategoryManageService categoryMangeService;

    /**
     * 카테고리 전체 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response search(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> list = categoryMangeService.selectCategoryList(params);

        SOMap result = new SOMap();
        result.put("text", "DADA M&C MALL");
        result.put("children", list);
        result.put("idx", 0);
        result.put("depth", 0);

        return new Response(result);
    }

    /**
     * 카테고리 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(categoryMangeService.selectCategoryDetail(params));
    }

    /**
     * 카테고리 트리 저장
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public Response treeSave(@RequestBody SOMap params) throws Exception {
        Response result;

        try {
            categoryMangeService.updateCategoryTree(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * 카테고리 추가, 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response Save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
        Response result = null;
        
        try {
        	Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
            if (params.getStr("issave").equals("T")) {
                categoryMangeService.insertCategory(params, uploadFile);
            } else if (params.getStr("issave").equals("F")) {
                categoryMangeService.updateCategory(params, uploadFile);
            }
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * 카테고리 삭제
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response Delete(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            int count = categoryMangeService.deleteCategory(params);
            if(count == 1) {
                result = new Response(Status.OK);
            } else {
                result = new Response(Status.BAD_REQUEST);
                result.setMessage("하위 카테고리가 있거나, 해당 카테고리에 상품이 있을 경우 삭제 할 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
}


}
