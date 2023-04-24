<template>
  <!-- 상품 필터 Modal -->
  <b-modal
    id="searchFilterModal"
    modal-class="dp-modal page-layer product-filter-modal"
    scrollable
  >
    <!-- Product Filter Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">필터</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <!-- Product Filter Modal Body -->
    <template #default>
      <div class="page-layer__body">
        <!-- 카테고리 -->
        <div class="filter__box" v-if="cateList.length > 0">
          <p class="filter__title">카테고리</p>
          <ul class="list-style-none filter__ul category__ul">
            <li v-for="(list, index) in cateList" :key="index">
              <label :for="'cate' + list.cateidx" class="filter__label mb-0">
                <input
                  :id="'cate' + list.cateidx"
                  type="checkbox"
                  name="category"
                  v-model="setCateList"
                  :value="list.cateidx"
                  @change="setFilter('setCateList', list.cateidx)"
                  hidden
                />
                <span class="label__item">{{ list.catename }}</span>
              </label>
            </li>
          </ul>
        </div>
        <!-- 브랜드 -->
        <div class="filter__box" v-if="brandGroup.length > 0 && brandGroup[0].length > 0">
          <p class="filter__title">브랜드</p>
          <swiper :options="modalBrandOption">
            <swiper-slide v-for="(list, index) in brandGroup" :key="index">
              <ul class="list-style-none filter__ul brand__ul">
                <li v-for="(item, itemIndex) in list" :key="itemIndex">
                  <label :for="item.idx" class="filter__label mb-0">
                    <input
                      :id="item.idx"
                      type="checkbox"
                      name="category"
                      v-model="setBrandList"
                      :value="item.idx"
                      @change="setFilter('setBrandList', item.idx)"
                      hidden
                    />
                    <div class="label__item">
                      <template v-if="!$util.isNull(item.logopath)">
                        <img :src="item.logopath" alt="브랜드 로고" />
                      </template>
                      <template v-else>
                        {{ $util.isNull(item.ename)?item.name:item.ename }}
                      </template>
                    </div>
                  </label>
                </li>
              </ul>
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
        </div>

        <!-- 컬러 -->
        <!-- <div class="filter__box" v-if="colorList.length > 0">
          <p class="filter__title">컬러</p>
          <ul class="color__ul list-style-none">
            <li v-for="(list, index) in colorList" :key="index">
              <label :for="list.colortype" class="color__label mb-0">
                <input
                  :id="list.colortype"
                  type="checkbox"
                  name="color"
                  v-model="setColorList"
                  :value="list.colortype"
                  @click="setFilter('setColorList', list.colortype)"
                  hidden
                />
                <span
                  class="label__item"
                  :style="{
                    backgroundColor: '#' + list.bg,
                    borderColor: '#' + list.bg,
                  }"
                ></span>
              </label>
            </li>
          </ul>
        </div> -->

        <!-- 혜택 -->
        <div class="filter__box" v-if="false">
          <p class="filter__title">혜택</p>
          <ul class="filter__ul benefit__ul list-style-none">
            <li v-for="(list, index) in benefitList" :key="index">
              <label :for="list.id" class="filter__label mb-0">
                <input
                  :id="list.id"
                  type="checkbox"
                  name="benefit"
                  v-model="setBenefitList"
                  :value="list.id"
                  hidden
                  @click="setFilter('setBenefitList', list.id)"
                />
                <span class="label__item">{{ list.name }}</span>
              </label>
            </li>
          </ul>
        </div>

        <!-- 가격 -->
        <div class="filter__box">
          <p class="filter__title">가격(원)</p>
          <div>
            <p class="atten-new font-weight-500 dp-p-sm text-center">
              {{ $util.maskComma(setPriceValue.currentValue[0]) }} ~
              {{ $util.maskComma(setPriceValue.currentValue[1]) }}
            </p>
            <div class="filter__range-slider">
              <vue-slider
                ref="slider"
                style="padding: 15px 0px; width: 90%; height: 4px; cursor: pointer;"
                v-model="setPriceValue.currentValue"
                :enable-cross="true"
                :tooltip="'none'"
                :min="setPriceValue.min"
                :max="setPriceValue.max"
                :interval="setPriceValue.interval"
                @drag-end="updateValue(setPriceValue.currentValue)"
                :dragOnClick="true"
                :dot-size="[0, 30]"
                :adsorb="true"
              >
                <template v-slot:dot>
                  <span class="filter__dot">
                    <span class="dot-box"></span>
                  </span>
                </template>
              </vue-slider>
            </div>

            <ul class="filter__ul type02 row list-style-none">
              <li v-for="(list, index) in priceList" :key="index" class="col-6">
                <label :for="list.id" class="filter__label mb-0">
                  <input
                    :id="list.id"
                    type="radio"
                    name="price"
                    v-model="setPriceList"
                    :value="list.id"
                    @click="setPrice(list)"
                    hidden
                  />
                  <span class="label__item">
                    {{ $util.maskComma(list.startPrice) }} ~
                    {{ $util.maskComma(list.endPrice) }}
                  </span>
                </label>
              </li>
            </ul>
          </div>
        </div>

        <!-- 평점 -->
        <div class="filter__box" v-if="false">
          <p class="filter__title">평점</p>
          <ul class="filter__ul type02 row list-style-none">
            <li v-for="(list, index) in ratingList" :key="index" class="col-4">
              <label :for="list.id" class="filter__label mb-0">
                <input
                  :id="list.id"
                  type="checkbox"
                  name="rating"
                  v-model="setRatingList"
                  :value="list.rating"
                  hidden
                  @click="setFilter('setRatingList', list.rating)"
                />
                <span class="label__item">
                  <img
                    v-for="(data, index) in list.rating"
                    :key="index"
                    src="@assets.admin/common/icon/icon-star-black-18px.svg"
                    :alt="`${data} 평점`"
                  />
                </span>
              </label>
            </li>
          </ul>
        </div>
      </div>
    </template>

    <template #modal-footer>
      <!-- 버튼 그룹 -->
      <div class="btn-group d-flex justify-content-center align-items-center">
        <b-button
          variant="btn dp-btn btn-outline-gray-800 rounded-0 not-hover"
          @click="getFilterInit()"
        >
          <span>초기화</span>
          <i class="dp-icon reset-icon"></i>
        </b-button>
        <b-button
          variant="btn dp-btn text-white btn-gray-800 rounded-0"
          @click="ok()"
          >선택보기</b-button
        >
      </div>
    </template>
  </b-modal>
</template>

<script>
import { swiper, swiperSlide } from "vue-awesome-swiper";
import VueSlider from "vue-slider-component";
export default {
  components: {
    swiper,
    swiperSlide,
    VueSlider,
  },
  props: {
    param: Object,
    fnConfirm: { type: Function },
  },
  data() {
    return {
      modalBrandOption: {
        observeParents: true,
        observer: true,
        pagination: {
          el: ".swiper-pagination",
        },
      },
      modalCateOption: {
        observeParents: true,
        observer: true,
        pagination: {
          el: ".swiper-pagination",
        },
      },
      benefitList: [],
      ratingList: [],
      setPriceValue: this.$store.state.setPriceValue,
      pagingData: {
        currentPage: 1, // 현재 페이지
        listTotal: 0, // 조회목록 전체 수
        listCnt: 10, // 한페이지에 노출할 목록수
      },
      cateGroup: [],
      cateList: [],
      fBrandList: [],
      colorList: [],
      priceList: [],
      brandGroup: [],
      giconList: [], // 아이콘설정목록
      recomList: [], // 추천유형목록
      cateShowMax: 4,
      brandShowMax: 6,
      priceInfo: {},
      setCateidx: 0,
      setColorList: [],
      setBrandList: [],
      setBenefitList: [],
      setPriceList: [],
      setRatingList: [],
      setFilterList: [],
      setGiconList: [], // 선택 아이콘설정 리스트
      setRecomList: [], // 선택 추천유형 리스트
      setCateList: [],
      initMinPrice: 0,
      initMaxPrice: 0,
      platform: window.sessionStorage.getItem("platform"),
      membertype: "DMT001", //회원구분(비로그인:일반회원,브론즈)
      memlvtype: "MDL001", //회원레벨(비로그인:일반회원,브론즈)
    };
  },
  methods: {
    /**************************
     * 조회조건 리셋
     **************************/
    getFilterInit() {
      //this.$router.push(this.$route.path);
      this.setCateList = [];
      this.setBrandList = [];
      this.setColorList = [];
      this.setBenefitList = [];
      this.setPriceList = [];
      this.setRatingList = [];
      this.setFilterList = [];
      this.setGiconList = [];
      this.setRecomList = [];
      this.searchData.maxprice=null;
      this.searchData.minprice=null;
      this.getFilterInfo();
    },
    /*************************
     * 가격 라디오 버튼 클릭에 따른 가격바 셋팅
     **************************/
    setPrice(list) {
      this.$nextTick(() => {
        let valueList = [];
        valueList[0] = list.startPrice;
        if (this.$util.isNull(list.endPrice)) {
          valueList[1] = list.startPrice;
        } else {
          valueList[1] = list.endPrice;
        }
        this.$refs.slider.setValue(valueList);
        this.updateValue(this.setPriceValue.currentValue);
      });
    },
    /*************************
     * 가격바 변경시 가격라디오에 해당하는 값
     * 없으면 라디오 리스트 비우기
     **************************/
    updateValue(priceList) {
      let isSame = false;
      let price = "";
      this.priceList.forEach((element) => {
        if (
          element.startPrice == priceList[0] &&
          element.endPrice == priceList[1]
        ) {
          isSame = true;
          this.setPriceList = [element.id];
          price = this.$util.maskComma(element.startPrice) + "원" + " ~ " + this.$util.maskComma(element.endPrice) + "원";
        }
      });
      if (!isSame) {
        this.setPriceList = [];
        this.setFilter(
          "setPriceList",
           this.$util.maskComma(priceList[0]) + "원" + " ~ " +  this.$util.maskComma(priceList[1]) + "원"
        );
      } else {
        this.setFilter("setPriceList", price);
      }
    },
    /****************************
     * 선택 옵션 리스트 셋팅 처리
     ****************************/
    setFilter(target, value) {
      this.$front.setFilter(this, target, value);
    },
    /******************************
     * 조회하기 버튼 클릭시 처리
     *****************************/
    ok() {
      this.$front.setSearchData(this);
      const searchStr = encodeURIComponent(JSON.stringify(this.searchData));
      this.$router.push({ name: this.$route.name, query: { content: this.$route.query.content, search: searchStr } , params:{init:true}});
      this.$bvModal.hide("searchFilterModal");
    },
    /************************
     * 카테고리 조회
     ***********************/
    getFilterInfo() {
      this.queryStr = this.$front.setFilterSearchUrl(this);
      this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
        if (result.ReturnMsg == "success") {
          /******************
           * 필터 가격 지정
           *****************/
          this.initMinPrice = parseInt(result.Data[0].MinPrice);
          this.initMaxPrice = parseInt(result.Data[0].MaxPrice);
          this.setPriceValue.min = parseInt(result.Data[0].MinPrice);
          this.setPriceValue.max = parseInt(result.Data[0].MaxPrice);
          this.setPriceValue.interval = (this.setPriceValue.max - this.setPriceValue.min) / 10;
          if (this.$util.isNull(this.searchData.maxprice) || this.$util.isNull(this.searchData.minprice)) {
            this.setPriceValue.currentValue[0] = parseInt(result.Data[0].MinPrice);
            this.setPriceValue.currentValue[1] = parseInt(result.Data[0].MaxPrice);
          } else {
            this.setPriceValue.currentValue[0] = this.searchData.minprice;
            this.setPriceValue.currentValue[1] = this.searchData.maxprice;
          }
          //this.$refs.slider.setValue( this.setPriceValue.currentValue);
          this.$nextTick(() => {
            this.$refs.slider.setValue( this.setPriceValue.currentValue);
          });
          this.$front.getPriceList(this, 5);

          /******************
           * 카테고리 목록  셋팅
           *****************/
          let cateList = result.Data[0].CategoryGroup[0].cateidx[0].Category;
          cateList.forEach((item) => {
            item.cateidx = Number(item.Name);
          });
          /******************
           * 브랜드 목록 셋팅
           *****************/
          let brandList = result.Data[0].CategoryGroup[1].brandidx[0].Category;
          brandList.forEach((item) => {
            item.idx = Number(item.Name);
          });
          /******************
           * 별점 목록 셋팅
           *****************/
          let reviewList =
            result.Data[0].CategoryGroup[2].reviewavg[0].Category;
          /**************************************
           * 리뷰 필터 셋팅
           ***************************************/
          reviewList = reviewList.sort(
            (a, b) => Number(a.Name) - Number(b.Name)
          );
          this.ratingList=[];
          reviewList.forEach((item) => {
            let rating = Number(item.Name);
            switch (rating) {
              case 0:
                //this.ratingList.push({'rating':rating, 'name':"☆☆☆☆☆"});
                break;
              case 1:
                this.ratingList.push({ rating: rating, name: "★☆☆☆☆" });
                break;
              case 2:
                this.ratingList.push({ rating: rating, name: "★★☆☆☆" });
                break;
              case 3:
                this.ratingList.push({ rating: rating, name: "★★★☆☆" });
                break;
              case 4:
                this.ratingList.push({ rating: rating, name: "★★★★☆" });
                break;
              case 5:
                this.ratingList.push({ rating: rating, name: "★★★★★" });
                break;
              default:
                break;
            }
          });
          /******************
           * 색상 목록 셋팅
           *****************/
          this.colorList=[];
          let colorList = result.Data[0].CategoryGroup[3].colortype[0].Category;
          colorList.forEach((item) => {
            item.colortype = item.Name;
          });
          /******************
           * 혜택 목록 셋팅
           *****************/
          this.benefitList=[];
          let isdelivList =
            result.Data[0].CategoryGroup[4].isdelivfree[0].Category;
          let ispccouponList =
            result.Data[0].CategoryGroup[5].ispccoupon[0].Category;
          let ismocouponList =
            result.Data[0].CategoryGroup[6].ismocoupon[0].Category;
          let isappcouponList =
            result.Data[0].CategoryGroup[7].isappcoupon[0].Category;
          let iscouponList =
            this.platform == "MAC001"
              ? ispccouponList
              : this.platform == "MAC002"
              ? ismocouponList
              : isappcouponList;

          if (isdelivList.filter((x) => x.Name == "T").length > 0) {
            this.benefitList.push({ id: "freeDeli", name: "무료배송" });
          }
          if (iscouponList.filter((x) => x.Name == "T").length > 0) {
            this.benefitList.push({ id: "goodscoupon", name: "상품쿠폰" });
          }
          /********************************
           * 카테고리, 브랜드, 색상 목록 셋팅
           ********************************/
          let param = {
            catelist: cateList,
            brandlist: brandList,
            colorlist: colorList,
          };
          this.$http.post("/shop/search/filter", param).then((result) => {
            if (result.statusCode == 200) {
              if (result.data.memberinfo.authType == "member") {
                this.membertype = result.data.memberinfo.membertype;
                this.memlvtype = result.data.memberinfo.memlvtype;
              } else {
                this.memlvtype = "MDL001";
                this.membertype = "DMT001";
              }
              this.cateList = result.data.catelist;
              // let deepList2 = [...this.cateList];
              // this.cateGroup = this.$util.division(deepList2,this.cateShowMax);
              this.fBrandList = result.data.brandlist;
              let deepList = [...this.fBrandList];
              //리스트 N개씩 묶음처리
              this.brandGroup = this.$util.division(
                deepList,
                this.brandShowMax
              );
              this.colorList = result.data.colorlist;
            } else {
              this.$eventBus.$emit(
                "alert",
                "알림",
                "검색어 조회에 실패했습니다."
              );
            }
          });
        } else {
          this.$eventBus.$emit("alert", "알림", "검색어 조회에 실패했습니다.");
        }
      });
    },
  },
  mounted() {
    if (this.$util.isBlank(this.$route.query.search)) {
      this.$front.setSearchData(this);
    } else {
      const searchStr = decodeURIComponent(this.$route.query.search);
      this.searchData = JSON.parse(searchStr);

      this.pagingData.currentPage = this.$util.isNull(this.searchData.page)
        ? this.pagingData.currentPage
        : this.searchData.page;
      this.sortActive = this.$util.isEmpty(this.searchData.sort)
        ? this.sortActive
        : this.searchData.sort;
      this.setMaxPrice = this.$util.isNull(this.searchData.maxprice)
        ? null
        : this.searchData.maxprice;
      this.setMinPrice = this.$util.isNull(this.searchData.minprice)
        ? null
        : this.searchData.minprice;
      this.setBenefitList = this.$util.isEmpty(this.searchData.benefitList)
        ? []
        : this.searchData.benefitList;
      this.setColorList = this.$util.isEmpty(this.searchData.colorList)
        ? []
        : this.searchData.colorList;
      this.setBrandList = this.$util.isEmpty(this.searchData.fBrandList)
        ? []
        : this.searchData.fBrandList;
      this.setRatingList = this.$util.isEmpty(this.searchData.ratingList)
        ? []
        : this.searchData.ratingList;
      this.setFilterList = this.$util.isEmpty(this.searchData.setFilterList)
        ? []
        : this.searchData.setFilterList;
      this.setPriceList = this.$util.isEmpty(this.searchData.setPriceList)
        ? []
        : this.searchData.setPriceList;
      this.setCateList = this.$util.isEmpty(this.searchData.setCateList)
        ? []
        : this.searchData.setCateList;
      this.setRecomList = this.$util.isEmpty(this.searchData.recomList)
        ? []
        : this.searchData.recomList;
      this.setGiconList = this.$util.isEmpty(this.searchData.giconList)
        ? []
        : this.searchData.giconList;
    }
    this.getFilterInfo();
  },
};
</script>

<style>
</style>