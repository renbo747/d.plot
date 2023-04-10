package com.dplot.admin.controller.operation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.operation.ShopExTwoService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 쇼핑전시영역2 Controller
 * @fileName : ShopExTwoController.java
 * @date : 2022-04-06
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-06 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/shopping/shopextwo")
public class ShopExTwoController extends ExcelDownController {
	@Autowired
	private ShopExTwoService shopExTwoService;

	/**
	 * 쇼핑전시영역2 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(shopExTwoService.selectShopExTwoList(params));
	}

	/**
	 * 쇼핑전시영역2 사용여부, 전시여부  수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(shopExTwoService.updateShopExTwo(params));
	}
	
	/**
	 * 쇼핑전시영역2 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = shopExTwoService.selectShopExTwoExcelList(params);

		String[] header = { "노출순서", "브랜드", "상품코드", "상품명", "담당MD", "노출상태", "등록자", "등록일자" };
		int[] columnSize = { 10 * 256, 10 * 256, 10 * 256, 15 * 256, 20 * 256, 10 * 256, 10 * 256, 10 * 256, 12 * 256 };
		String[] columns = { "sortno", "brandname", "goodscode", "goodsname", "mdname",
				"isdisplay", "regusername", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "쇼핑전시영역2 목록.xlsx");
	}
	
	 /**
	 * 쇼핑전시영역2 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestBody SOMap params) throws Exception{
		 return new Response(shopExTwoService.saveShopExTwo(params));
	 }
	 
	 /**
	 * 쇼핑전시영역2 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response check(@RequestBody SOMap params) throws Exception {
		return new Response(shopExTwoService.checkShopExTwo(params));
	}
}
