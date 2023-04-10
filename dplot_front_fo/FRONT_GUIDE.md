# 프론트엔드 개발할때 참고 사이트 정리
# 링크는 컨트롤 + 클릭으로 이동가능

# [스와이퍼]
https://github.surmon.me/vue-awesome-swiper/ (실사용 예제)
https://github.com/surmon-china/vue-awesome-swiper (뷰)
https://swiperjs.com/swiper-api (퍼블)

# [스크롤페이징]
https://peachscript.github.io/vue-infinite-loading/guide/#installation

# [모달]
https://euvl.github.io/vue-js-modal/

# [TODO]
https://..........

# [VUEX]
글로벌 게터
this.$store.getters.NAV;
this.$store.commit('isNav',param); 
this.$store.dispatch('isNav', param);

# [layzy]
화면이 보일시에 페이지 로드
https://www.npmjs.com/package/vue-lazyload
기본사용(일반이미지)
<img data-src="@assets.mobile/icon/icon_plus.svg">
동적이미지(비동기 이미지)
<img v-lazy="fullpath">
컴포넌트 로드시(페이지 스크롤링에 의하여 데이터가 로드될 경우)
<lazy-component @show="isInitView = true;"></lazy-component>
<div>
    ... 비동기 상황에 로드되야 하는 화면
</div>
 * lazy-component컴포넌트로 감싸게 되면, 하위요소의 비동기 데이터가 로드 및 갱신이 되지 않음

토스트
this.$toasted.show("로딩됨");



상품 모듈
this.$store.getters['goods/GET_GOODS'];
this.$store.commit('goods/setBreadList', [1,2,3]);
this.$store.dispatch('goods/setBreadList', [1,2,3]);

# 퍼블파일 적용 후 url 수정사항(퍼블 수정요청 사항 적용될때까지!)
1. #productDetail .btn-product-detail (상품상세) 
>> 기존 : background-image: url(/assets/icon/icon_download.svg);
>> 변경 : background-image: url(../icon/icon_download.svg); 



단위 테스트
https://vue-test-utils.vuejs.org/guides/#testing-vuex-in-components
npm run test:unit --watch


nodemon 사용( 수정시 자동으로 다시 검사)
    npm i nodemon -g
    npx nodemon -e js,vue --exec npm run test:unit --watch


동작방식
    jest.setup.js
        main.js 의 vue 기본 라이브러리 임포트 (안할시에 오류가 발생함)
    
    jest.config
        단위 테스트 환경변수 설정
   
test 
파일
    기본형태
        test/unit/ 에 정의하여 사용
         - *.spec.js 로 파일명 정의
        동작방식,
            describe - 통합테스트
                블럭단위로 테스트를 진행할 때에 사용
                    (대부분 파일 단위로 정의하여 사용)
                describe("단위 테스트 이름", 테스트 진행 함수) 로 정의하여 사용
            test - 단일 테스트
                한개의 단일 테스트시 사용 (내부 api 테스트 포함)
                 - 비동기 함수를 지원
                test("단일테스트 이름", (done) => {
                    expect(typeof vm.getGoods).toBe("function"); // 검사 항목
                    ... 테스트 항목
                    done(); // 테스트 종료
                })
            it - 단일 테스트
                한개의 단일 테스트시 사용 (test와 차이점은, 내부 코드가 동작하지 않음)
                it("단일 테스트 이름", ()=>{
                    expect(typeof vm.getGoods).toBe("function"); // 검사 항목
                    ... 테스트 항목
                });
            beforeEach - 테스트 진행전, 호출되는 처리 함수
                초기값 설정에 사용
                test 진행전, vue 의 컴포넌트를 빌드하기 위하여 사용됨
                ```
                let wrapper, vm; // wrapper : 컴포넌트 , vm : 구성된 뷰 (component)
                beforeEach(() => {
                    wrapper = shallowMount(component, { // 단일 페이지 구성 (shallowMount 은, 해당 페이지만 구성하며 하위 - router-view 는 렌더링 하지 않음)
                    localVue, // 초기 지정(jest.setup.js 파일렌더)
                    mocks : { // 파라메타 설정
                        $route : { // 라우트 쿼리 구성에 필요함
                        query : { // 쿼리값 삽입 (렌더링)
                            goodsno : result_data.goodsno
                        }
                        }
                    }
                    });
                    vm = wrapper.vm
                });
                ```


테스트 예시
 FAIL  tests/unit/goods.detail.spec.js
  ● Console

    console.log tests/unit/goods.detail.spec.js:7
      GoodsDetail] -  파라메타 undefined

  ● GoodsDetail 파일 유닛테스트 › 구매정보 테스트

    expect(received).toBe(expected) // Object.is equality

    Expected: "구매정보"
    Received: "판매자정보"

      193 |    test("구매정보 테스트", ()=>{
      194 |     vm.contentsmap.ispbgoods = "T";
    > 195 |     expect(vm.getBuyInfoTabText).toBe("구매정보");
          |                                  ^
      196 |     vm.contentsmap.ispbgoods = "F";
      197 |     expect(vm.getBuyInfoTabText).toBe("판매자정보");
      198 |    });

      at Object.<anonymous> (tests/unit/goods.detail.spec.js:195:34)
