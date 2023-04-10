<template>
  <b-modal
    id="productFilterModal"
    modal-class="dp-modal page-layer product-filter-modal"
    scrollable
  >
    <!-- Product Filter Modal Header -->
    <template #modal-header="{ cancel }">
      <i class="modal-close" @click="cancel()" style="margin-left: auto;">
        <img
          src="@/assets/common/icon/icon-close-modal-30px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Product Filter Modal Body -->
    <template #default>
      <div class="page-layer__body">
        <!-- 카테고리 -->
        <div class="filter__box" v-if="false">
          <p class="filter__title">카테고리</p>
          <ul class="list-style-none filter__ul category__ul">
            <li v-for="(list, index) in cateList" :key="index">
              <label :for="list.cateidx" class="filter__label mb-0">
                <input
                  :id="list.cateidx"
                  type="radio"
                  name="category"
                  v-model="setCateidx"
                  :value="list.cateidx"
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
          <swiper :options="brandOption">
            <swiper-slide v-for="(list, index) in brandGroup" :key="index">
              <ul class="list-style-none filter__ul brand__ul">
                <li v-for="(item, itemIndex) in list" :key="itemIndex" class="col-6">
                  <label :for="item.idx" class="filter__label mb-0">
                    <input
                      :id="item.idx"
                      type="checkbox"
                      name="category"
                      v-model="setBrandList"
                      :value="item.idx"
                      hidden
                      @click="setFilter('setBrandList', item.idx)"
                    />
                    <div class="label__item atten-new">
                      <!-- <template v-if="!$util.isNull(item.logopath)">
                        <img :src="item.logopath" alt="브랜드 로고" />
                      </template> -->
                      <template>
                        {{ $util.isNull(item.krname)?item.name:item.krname }}
                      </template>
                    </div>
                  </label>
                </li>
              </ul>
            </swiper-slide>

            <div
              class="swiper-pagination"
              slot="pagination"
              v-show="brandGroup.length > 1"
            ></div>
          </swiper>
        </div>

        <!-- 추천 -->
        <div class="filter__box" v-if="recomList.length > 0">
          <p class="filter__title">추천</p>
          <ul class="filter__ul list-style-none typeBorder">
            <li v-for="(list, index) in recomList" :key="index">
              <label :for="list.id" class="filter__label mb-0">
                <input
                  :id="list.id"
                  type="checkbox"
                  name="recom"
                  v-model="setRecomList"
                  :value="list.id"
                  hidden
                  @click="setFilter('setRecomList', list.id)"
                />
                <span class="label__item">{{ list.name }}</span>
              </label>
            </li>
          </ul>
        </div>

        <!-- 상품 유형 -->
        <div class="filter__box" v-if="giconList.length > 0">
          <p class="filter__title">상품유형</p>
          <ul class="filter__ul list-style-none typeBorder">
            <li v-for="(list, index) in giconList" :key="index">
              <label :for="list.id" class="filter__label mb-0">
                <input
                  :id="list.id"
                  type="checkbox"
                  name="gicon"
                  v-model="setGiconList"
                  :value="list.id"
                  hidden
                  @click="setFilter('setGiconList', list.id)"
                />
                <span class="label__item">{{ list.name }}</span>
              </label>
            </li>
          </ul>
        </div>

        <!-- 컬러 -->
        <!-- <div class="filter__box">
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
                  hidden
                  @click="setFilter('setColorList', list.colortype)"
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
        <!-- <div class="filter__box"  v-if="false">
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
        </div> -->

        <!-- 가격 -->
        <div class="filter__box">
          <p class="filter__title">가격</p>
          <div>
            <p class="atten-new font-weight-500 dp-p-sm name-center">
              {{ $util.maskComma(setPriceValue.currentValue[0]) }} ~ {{ $util.maskComma(setPriceValue.currentValue[1]) }}
            </p>
            <div class="filter__range-slider">
              <!--임시-padding: 15px 0px; width: 320px; height: 4px; cursor: pointer; -->
              <vue-slider
                class="filter-slider"
                style="padding: 15px 0px; width: 90%; height: 4px; cursor: pointer;"
                ref="slider"
                v-model="setPriceValue.currentValue"
                :enable-cross="true"
                :tooltip="'none'"
                :min="setPriceValue.min"
                :max="setPriceValue.max"
                :interval="setPriceValue.interval"
                @drag-end="updateValue(setPriceValue.currentValue, 'drag')"
                :dragOnClick="true"
                :dot-size="[0, 30]"
                :adsorb="true"
              >
                <template v-slot:dot>
                  <span class="filter__dot">
                    <span class="dot-box atten-new"></span>
                  </span>
                </template>
              </vue-slider>
            </div>
            <ul class="filter__ul row list-style-none">
              <li v-for="(list, index) in priceList" :key="index" class="col-6">
                <label :for="list.id" class="filter__label mb-0">
                  <input
                    type="radio"
                    :id="list.id"
                    name="price"
                    v-model="setPriceList"
                    :value="list.id"
                    @change="setPrice(list)"
                    hidden
                  />
                  <span class="label__item">
                    <span v-if="list.startPrice >= 3000000"> {{ list.startPrice / 10000 }} 만원 초과</span><span v-if="list.endPrice < 3000000">{{ list.endPrice / 10000 }} 만원 이하</span>
                  </span>
                </label>
              </li>
              <!-- <li class="col-6">
                <label for="price1" class="filter__label mb-0">
                  <input type="radio" id="price1" name="price" hidden="hidden" value="price1" @change="setPrice({startPrice:setPriceValue.min, endPrice:100000})">
                  <span class="label__item">10만원 이하</span>
                </label>
              </li>
              <li class="col-6">
                <label for="price2" class="filter__label mb-0">
                  <input type="radio" id="price2" name="price" hidden="hidden" value="price2" @change="setPrice({startPrice:100001, endPrice:200000})">
                  <span class="label__item">20만원 이하</span>
                </label>
              </li>
              <li class="col-6">
                <label for="price3" class="filter__label mb-0">
                  <input type="radio" id="price3" name="price" hidden="hidden" value="price3" @change="setPrice({startPrice:200001, endPrice:500000})">
                  <span class="label__item">50만원 이하</span>
                </label>
              </li>
              <li class="col-6">
                <label for="price4" class="filter__label mb-0">
                  <input type="radio" id="price4" name="price" hidden="hidden" value="price4" @change="setPrice({startPrice:500001, endPrice:1000000})">
                  <span class="label__item">100만원 이하</span>
                </label>
              </li>
              <li class="col-6">
                <label for="price5" class="filter__label mb-0">
                  <input type="radio" id="price5" name="price" hidden="hidden" value="price5" @change="setPrice({startPrice:1000001, endPrice:3000000})">
                  <span class="label__item">300만원 이하</span>
                </label>
              </li>
              <li class="col-6">
                <label for="price6" class="filter__label mb-0">
                  <input type="radio" id="price6" name="price" hidden="hidden" value="price6" @change="setPrice({startPrice:3000001, endPrice: setPriceValue.max})">
                  <span class="label__item">300만원 초과</span>
                </label>
              </li> -->
              <!-- <li v-for="(list, index) in priceList" :key="index" class="col-6">
                <label :for="list.id" class="filter__label mb-0">
                  <input
                    type="radio"
                    :id="list.id"
                    name="price"
                    v-model="setPriceList"
                    :value="list.id"
                    @change="setPrice(list)"
                    hidden
                  />
                  <span class="label__item">
                    {{ $util.maskComma(list.startPrice) }} ~
                    {{ $util.maskComma(list.endPrice) }}
                  </span>
                </label>
              </li> -->
            </ul>
          </div>
        </div>

        <!-- 평점 -->
        <!-- <div class="filter__box" v-if="ratingList.length > 1"> -->
        <div class="filter__box" v-if="false">
          <p class="filter__title">평점</p>
          <ul class="filter__ul type02 row list-style-none">
            <li v-for="(list, index) in ratingList" :key="index" class="col-4">
              <label :for="'rating' + list.rating" class="filter__label mb-0">
                <input
                  type="checkbox"
                  :id="'rating' + list.rating"
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
    <template #modal-footer="{}">
      <!-- 버튼 그룹 -->
      <div class="btn-group d-flex justify-content-center align-items-center">
        <b-button
          style="border-right:none"
          variant="btn dp-btn btn-outline-gray-800 rounded-0 not-hover"
          @click="getFilterInit()"
        >
          <span>초기화</span>
          <i class="dp-icon reset-icon" :class="{ white: isClick }"></i>
        </b-button>
        <b-button
          variant="btn dp-btn rounded-0 not-hover"
          style="border-top:1px solid #ddd; border-left:1px solid #ddd;"
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
import "vue-slider-component/theme/default.css";
export default {
  components: {
    swiper,
    swiperSlide,
    VueSlider,
  },
  props: {
    param: {
      type: Object,
      default: () => ({
        idx: null,
        setCateList: [], //선택 카테고리 리스트
        setBrandList: [], //선택 브랜드 리스트
        setColorList: [], //선택 색상 리스트
        setBenefitList: [], //선택 혜택 리스트
        setPriceList: [], //선택 가격 리스트
        setRatingList: [], //선택 별점 리스트
        setGiconList: [], // 선택 아이콘설정 리스트
        setRecomList: [], // 선택 추천유형 리스트
      }),
    },
    fnConfirm: { type: Function },
  },
  data() {
    return {
      isChecked: true,
      cateList: [0],
      brandOption: {
        observeParents: true,
        observer: true,
        pagination: {
          el: ".swiper-pagination",
        },
      },
      sortActive: 'best',
      searchData: {
        idx: "", //카테고리
        platform: window.sessionStorage.getItem("platform"), //사용 플랫폼
        fBrandList: [], //브랜드목록
        colorList: [], //컬러목록
        benefitList: [], //혜택목록
        ratingList: [], //별점목록
        giconList: [], // 아이콘설정목록
        recomList: [], // 추천유형목록
        minprice: 0, //최소가격
        maxprice: 0, //최대가격
        sort: "best", //정렬기준
      },
      priceInfo: {},
      commonCodeList: [],
      brandGroup: [], //브랜드목록 10개씩 그룹
      brandList: [], //브랜드 전체
      colorList: [], //색상 목록
      priceList: [],
      ratingList: [],
      giconList: [],
      benefitList: [],
      recomList: [],
      setPriceValue: this.$store.state.setPriceValue,
      setFilterList: [], //선택 필터옵션
      setBrandList: [], //선택 브랜드 리스트
      setColorList: [], //선택 색상 리스트
      setBenefitList: [], //선택 혜택 리스트
      setPriceList: [], //선택 가격 리스트
      setRatingList: [], //선택 별점 리스트
      setGiconList: [], // 선택 아이콘설정 리스트
      setRecomList: [], // 선택 추천유형 리스트
      parentInfo: {},
      setCateidx: this.$route.params.idx,
      initMinPrice: 0,
      initMaxPrice: 0,
      brandShowMax: 10,
      isClick: false,
      pagingData: {
        currentPage: 1, // 현재 페이지
        listTotal: 0, // 조회목록 전체 수
        listCnt: 10, // 한페이지에 노출할 목록수
      },
    };
  },
  methods: {
    /**************************
     * 조회조건 리셋
     **************************/
    getFilterInit() {
      //this.$router.push(this.$route.path);
      this.setCateidx = this.$route.params.idx;
      this.setBrandList = [];
      this.setColorList = [];
      this.setBenefitList = [];
      this.setPriceList = [];
      this.setRatingList = [];
      this.setGiconList = [];
      this.setFilterList = [];
      this.setRecomList = [];
      this.searchData.maxprice=null;
      this.searchData.minprice=null;
      this.getFilterInfo();
    },
    /**************************
     * 필터정보 조회
     **************************/
    getFilterInfo() {
      this.$http
        .post("/shop/filter/list", {
          idx: this.setCateidx,
          isloading: true,
        })
        .then((result) => {
          if (result.statusCode == 200) {
            /***********************************
             * 카테고리 셋팅
             ***********************************/
            //1)상위 카테고리 정보
            this.parentInfo = result.data.parentinfo;
            //2)하위 카테고리 목록 - '전체' 추가
            let cate = {
              cateidx: this.parentInfo.idx,
              catename: "전체",
            };
            this.cateList = result.data.catelist;
            this.$nextTick(() => {
              this.cateList.unshift(cate);
            });
            /***********************************
             * 색상 셋팅
             ***********************************/
            //this.colorList = result.data.colorlist;
            /***********************************
             * 브랜드 목록 셋팅
             ***********************************/
            this.fBrandList = result.data.brandlist;
            let deepList = [...this.fBrandList];
            //리스트 N개씩 묶음처리
            this.brandGroup = this.$util.division(deepList, this.brandShowMax);
            /***********************************
             * 혜택 목록 셋팅
             ***********************************/
            this.benefitList = result.data.benefitlist;
            /***********************************
             * 평점 목록 셋팅
             ***********************************/
            this.ratingList = result.data.ratinglist.filter(
              (x) => x.rating > 0
            );
            /***********************************
             * 아이콘설정 목록 셋팅
             ***********************************/
            this.giconList = result.data.giconlist;
            /***********************************
             * 추천유형 목록 셋팅
             ***********************************/
            this.recomList = result.data.recomlist;
            /******************
             * 필터 가격 지정
             * ****************/
            this.priceInfo = result.data.priceinfo;
            this.initMinPrice = this.priceInfo.minamt;
            this.initMaxPrice = this.priceInfo.maxamt;
            this.setPriceValue.min = this.priceInfo.minamt;
            this.setPriceValue.max = this.priceInfo.maxamt;
            this.setPriceValue.interval = (this.setPriceValue.max - this.setPriceValue.min) / 10;
            if(this.setPriceValue.interval == 0) {
              this.setPriceValue.interval = 1;
            }
            if (this.$util.isNull(this.searchData.maxprice) || this.$util.isNull(this.searchData.minprice)) {
              this.setPriceValue.currentValue[0] = this.priceInfo.minamt;
              this.setPriceValue.currentValue[1] = this.priceInfo.maxamt;
            } else {
              this.setPriceValue.currentValue[0] = this.searchData.minprice;
              this.setPriceValue.currentValue[1] = this.searchData.maxprice;
            }
            //this.$refs.slider.setValue( this.setPriceValue.currentValue);
            // this.$nextTick(() => {
            //  this.$refs.slider.setValue( this.setPriceValue.currentValue);
            // });
            /*********************
             * 5분위 가격 목록 생성
             ********************/
            this.$front.getPriceList(this, 6);
          } else {
            this.$eventBus.$emit("alert", "알림", result.message);
          }
        });
    },
    /*************************
     * 카테고리 단일 선택 처리
     **************************/
    onChecked(idx) {
      this.$nextTick(() => {
        this.setCateList = [idx];
        this.getBrandList();
        this.setFilter("setCateList", idx);
      });
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
          if(list.endPrice > this.setPriceValue.max) {
            valueList[1] = this.setPriceValue.max;
          } else {
            valueList[1] = list.endPrice;
          }
        }
        this.$refs.slider.setValue(valueList);
        this.updateValue(this.setPriceValue.currentValue, 'btn');
      });
    },
    /*************************
     * 가격바 변경시 가격라디오에 해당하는 값
     * 없으면 라디오 리스트 비우기
     **************************/
    updateValue(priceList, value) {
      let isSame = false;
      let price = "";
      this.priceList.forEach((item) => {
        if (item.startPrice == priceList[0] && item.endPrice == priceList[1]) {
          isSame = true;
          price =
            this.$util.maskComma(item.startPrice) +
            "원 ~ " +
            this.$util.maskComma(item.endPrice) +
            "원";
        }
      });
      if (!isSame) {
        this.setPriceList = [];
        this.setFilter(
          "setPriceList",
          this.$util.maskComma(priceList[0]) +
            "원 ~ " +
            this.$util.maskComma(priceList[1]) +
            "원"
        );
      } else {
        this.setFilter("setPriceList", price);
      }

      if(value == 'drag') {
        $('input[name="price"]').removeAttr('checked');    //체크되어있는 항목 모두 해제
      }
    },
    /******************************
     * 조회하기 버튼 클릭시 처리
     *****************************/
    ok() {
      this.$front.setSearchData(this);
      const searchStr = encodeURIComponent(JSON.stringify(this.searchData));
      this.$router.push({
        name:'shop-list',
        params:{idx:this.setCateidx, init:true},
        query: { search: searchStr },
      });
      this.$bvModal.hide("productFilterModal");
    },
    /************************
     * 필터 리스트 추가
     *************************/
    setFilter(target, value) {
      this.$front.setFilter(this, target, value);
    },
  },
  mounted() {
    this.setCateidx = this.$route.params.idx;
    if (this.$util.isBlank(this.$route.query.search)) {
      this.$front.setSearchData(this);
    } else {
      this.$front.queryStrToParam(this);
    }
    this.getFilterInfo();
  },
  watch:{
    setCateidx(val){
      this.setBrandList = [];
      this.setColorList = [];
      this.setBenefitList = [];
      this.setPriceList = [];
      this.setRatingList = [];
      this.setGiconList = [];
      this.setRecomList = [];
      this.setFilterList = [];
      this.searchData.maxprice=null;
      this.searchData.minprice=null;
      this.getFilterInfo();
    }
  }
};
</script>

<style scoped>
  .dp-modal.page-layer .modal-header {justify-content: flex-end;}
</style>