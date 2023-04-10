/**
 * @jest-environment jsdom
 */
 import { shallowMount, createLocalVue } from '@vue/test-utils'

 import component from '@views.mobile/goods/GoodsDetail.vue'
 const log = (...args) => console.log(`${component.name}] - `, ...args)
 
 
 const localVue = createLocalVue();
 // 응답 셈플 데이터
 const result_data = {
  goodsno : "10588",
  "swiperlist": [
      {
          "filetype": "FLT001",
          "imgforiname": "7fe27e13c27a4378a30fd068e8f6fb14.jpg",
          "imgpath": "img/item/88/",
          "reguserid": "test",
          "sort": 1,
          "imgtype": "IGT002",
          "istrash": "F",
          "parentidx": 922,
          "fullpath": "https://d2yxem29c3b5r4.cloudfront.net/img/item/88/10588PM_1643254087257.jpg",
          "host": "https://d2yxem29c3b5r4.cloudfront.net/",
          "imgfname": "10588PM_1643254087257.jpg",
          "regdate": 1643254085000,
          "orgidx": 10588,
          "idx": 923
      },
      {
          "filetype": "FLT001",
          "imgforiname": "@bg_event1.jpg",
          "imgpath": "img/item/88/",
          "reguserid": "test",
          "sort": 1,
          "imgtype": "IGT033",
          "istrash": "F",
          "parentidx": 909,
          "fullpath": "https://d2yxem29c3b5r4.cloudfront.net/img/item/88/10588AM_1643253213054.jpg",
          "host": "https://d2yxem29c3b5r4.cloudfront.net/",
          "imgfname": "10588AM_1643253213054.jpg",
          "regdate": 1643253211000,
          "orgidx": 10588,
          "idx": 910
      },
      {
          "filetype": "FLT001",
          "imgforiname": "@bg_index.jpg",
          "imgpath": "img/item/88/",
          "reguserid": "test",
          "sort": 1,
          "imgtype": "IGT036",
          "istrash": "F",
          "parentidx": 927,
          "fullpath": "https://d2yxem29c3b5r4.cloudfront.net/img/item/88/10588BM_1643254087829.jpg",
          "host": "https://d2yxem29c3b5r4.cloudfront.net/",
          "imgfname": "10588BM_1643254087829.jpg",
          "regdate": 1643254086000,
          "orgidx": 10588,
          "idx": 928
      }
  ],
  "goodsnotifylist": [],
  "asinfo": {
      "bizno": "101-86-64524",
      "ispbgoods": "T",
      "siteid": "base",
      "tel": "02-555-1344",
      "comsellno": "제1234-서울강남-1234호",
      "fulladdr": "06531서울 서초구 신반포로49길 12 (잠원동, 하늘정원빌딩)4층 ",
      "compname": "(주)DADA M&C",
      "fax": "02-552-2202",
      "brandname": "(주)DADA M&C",
      "reprename": "홍길동",
      "email": "plan@ncgn.co.kr"
  },
  "breadcrumblist": [
      {
          "parent": 0,
          "depth": 1,
          "name": "의류",
          "idx": 1617
      },
      {
          "parent": 1617,
          "depth": 2,
          "name": "상의",
          "idx": 1618
      },
      {
          "parent": 1618,
          "depth": 3,
          "name": "원피스",
          "idx": 1662
      }
  ],
  "contentsmap": {
      "notice_content": "",
      "isdisplay": "T",
      "qnasize": 0,
      "maxordcnt": 999,
      "deliveryfee": 0,
      "mobile_content": "<style>\n    .event{\n      width: 100%;\n    }\n\n    .event img{\n      width: 100%;\n    }\n    .event video {\n        position: absolute;\n        left: 0;\n        top: 0;\n        width: 20%;\n    }\n    .event .block{\n      display: block;\n      position: relative;\n      width: auto;\n    }\n\n    .event .item2 video{\n      left: 45%;\n      top: 15%;\n    }\n\n    .event .item3 video{\n      left: 10%;\n      top: 11%;\n    }\n\n    .event .item4 video{\n      left: 55%;\n      top: 22.5%;\n    }\n\n    .event .item5 video{\n      left: 62%;\n      top: 7%;\n    }\n</style>\n<section class=\"event\">\n    <p class=\"info\"> <span>단품 상품</span></p>\n    <h3>새해의 기원 : 아바타</h3>\n    <span>24,000 로열 크리스탈</span>\n    <p>\n        새해의 기원 아바타 선택 상자 사용 시, 디자인 4종 중 1종을 선택해 클래스에 맞는 머리, 상의, 하의, 얼굴2 아바타를 획득할 수 있습니다.\n        얼굴2 파츠를 제외한 새해의 기원 아바타는 염색 가능하며 디자인에 따라 염색 범위가 상이할 수 있습니다. (특정 머리 아바타는 디자인 특성 상\n        착용 시 머리카락, 귀 일부가 돌출되어 보일 수 있습니다.)\n    </p>\n    <section class=\"block item1\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi01.jpg\" alt=\"\">\n    </section>\n    <section class=\"block item2\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi02.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n          <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video2.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n    <section class=\"block item3\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi03.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n        <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video3.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n    <section class=\"block item4\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi04.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n        <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video4.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n    <section class=\"block item5\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi05.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n        <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video5.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n</section>",
      "price": 0,
      "goodsdivtype": "GDT001",
      "catecnt": 1,
      "keyword": "",
      "imgautochk": "T",
      "margin": 0,
      "deliverylimit": 0,
      "goodsselltype": "GST002",
      "muappchtype": "MAC001,MAC002,MAC003",
      "isdeal": "F",
      "discountprice": 0,
      "frontgoodsname": "한복",
      "cateidx": 1662,
      "daymaxordcnt": 999,
      "ispbgoods": "T",
      "isopenreview": "T",
      "mumembertype": "DMT001,DMT002,DMT003,DMT004,DMT005",
      "goodsno": 10588,
      "mumemlvtype": "",
      "iscncappr": "F",
      "goodsname": "한복",
      "dealno": 0,
      "content": "<style>\n    .event{\n      width: 100%;\n    }\n\n    .event img{\n      width: 100%;\n    }\n    .event video {\n        position: absolute;\n        left: 0;\n        top: 0;\n        width: 20%;\n    }\n    .event .block{\n      display: block;\n      position: relative;\n      width: auto;\n    }\n\n    .event .item2 video{\n      left: 45%;\n      top: 15%;\n    }\n\n    .event .item3 video{\n      left: 10%;\n      top: 11%;\n    }\n\n    .event .item4 video{\n      left: 55%;\n      top: 22.5%;\n    }\n\n    .event .item5 video{\n      left: 62%;\n      top: 7%;\n    }\n</style>\n<section class=\"event\">\n    <p class=\"info\"> <span>단품 상품</span></p>\n    <h3>새해의 기원 : 아바타</h3>\n    <span>24,000 로열 크리스탈</span>\n    <p>\n        새해의 기원 아바타 선택 상자 사용 시, 디자인 4종 중 1종을 선택해 클래스에 맞는 머리, 상의, 하의, 얼굴2 아바타를 획득할 수 있습니다.\n        얼굴2 파츠를 제외한 새해의 기원 아바타는 염색 가능하며 디자인에 따라 염색 범위가 상이할 수 있습니다. (특정 머리 아바타는 디자인 특성 상\n        착용 시 머리카락, 귀 일부가 돌출되어 보일 수 있습니다.)\n    </p>\n    <section class=\"block item1\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi01.jpg\" alt=\"\">\n    </section>\n    <section class=\"block item2\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi02.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n          <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video2.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n    <section class=\"block item3\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi03.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n        <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video3.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n    <section class=\"block item4\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi04.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n        <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video4.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n    <section class=\"block item5\">\n      <img src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/@bg_coordi05.jpg\" alt=\"\">\n      <video loop=\"\" muted=\"\" autoplay=\"\">\n        <source src=\"https://cdn-lostark.game.onstove.com/2022/event/220126_package_VBcofWv1CZaj/images/pc/video5.mp4?v=2\" type=\"video/mp4\">\n      </video>\n    </section>\n</section>",
      "disstdate": 1643209200000,
      "reviewsize": 0,
      "reviewvalue": 0,
      "diseddate": 32503604399000,
      "summary": "한복 테스트",
      "istempsave": "F",
      "goodscode": "0000000062",
      "iswished": "F",
      "isuseaddition": "F",
      "icons": 0,
      "isfrstsale": "T",
      "istaxfree": "F",
      "minordcnt": 1,
      "kcdivtype": "KDT001",
      "orgno": 0,
      "goodsdivtypename": "새상품",
      "iscombdeliv": "F",
      "goodsapprtype": "GAT004",
      "intro_content": "",
      "marketprice": 0
  }
}
 describe(`${component.name} 파일 유닛테스트`, () => {
   log("파라메타", component.probs);
 
   let wrapper, vm; // 화면 구성
  
   // 화면을 구성함
   beforeEach(() => {
     wrapper = shallowMount(component, {
       localVue,
       mocks : {
        $route : { // 라우트 쿼리 구성에 필요함
          query : {
            goodsno : result_data.goodsno
          }
         }
       }
     });
     vm = wrapper.vm
   });
 
   test(`${result_data.goodsno}상품의 데이터 테스트`, done=>{
     expect(typeof vm.getGoods).toBe("function");
     setTimeout(() => {
      expect(JSON.stringify(vm.breadcrumblist)).toBe(JSON.stringify(result_data.breadcrumblist));
      expect(JSON.stringify(vm.swiperlist)).toBe(JSON.stringify(result_data.swiperlist));
      expect(JSON.stringify(vm.asinfo)).toBe(JSON.stringify(result_data.asinfo));
      expect(JSON.stringify(vm.goodsnotifylist)).toBe(JSON.stringify(result_data.goodsnotifylist));
      expect(JSON.stringify(vm.contentsmap)).toBe(JSON.stringify(result_data.contentsmap));
      done()
    }, 1000) // 1초
   });

   test("999개가 넘는 수 테스트", ()=>{
    vm.contentsmap.reviewsize = 20000;
    expect(vm.getReviewSize).toBe("999+");
    vm.contentsmap.reviewsize = 800;
    expect(vm.getReviewSize).toBe(800);
    vm.contentsmap.qnasize = 20000;
    expect(vm.getQnaSize).toBe("999+");
    vm.contentsmap.qnasize = 800;
    expect(vm.getQnaSize).toBe(800);
   });

   test("구매정보 테스트", ()=>{
    vm.contentsmap.ispbgoods = "T";
    expect(vm.getBuyInfoTabText).toBe("구매정보");
    vm.contentsmap.ispbgoods = "F";
    expect(vm.getBuyInfoTabText).toBe("판매자정보");
   });

   test("할인율 테스트", ()=>{
     vm.contentsmap.price = 200000; // 20만

    vm.contentsmap.marketprice = 0;
    expect(vm.getGoodsDiscountPer).toBe(0);

    vm.contentsmap.marketprice = 50;
    expect(vm.getGoodsDiscountPer).toBe(0);
   });
 });
 
w