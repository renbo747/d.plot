<template>
  <div
    class="base-input-search search-relation"
    :class="{ 'is-success': valid }"
  >
    <div class="position-relative">
      <input
        :type="type"
        class="form-control"
        :placeholder="placeholder"
        v-model.trim="newValue"
        :disabled="isDisabled"
        :readonly="isReadonly"
        @keyup="handleKeyword($event)"
        maxLength="20"
        ref="search"
        @focusin="onFocus"
      />
      <i
        v-if="newValue !== ''"
        class="dp-icon icon-input-remove"
        @click="clearValue"
      ></i>
      <i
        v-if="isMobile"
        class="search__input__icon dp-icon sm02 icon-search"
        @click="searchStart(newValue)"
      ></i>
      <i
        v-else
        class="search__input__icon dp-icon h28 icon-search"
        @click="searchStart(newValue)"
      ></i>
    </div>

    <!-- 모바일 -->
    <!-- 최근검색어, 인기검색어 -->
    <div
      v-if="isMobile && !isMobileBrand && isNotResult && !(this.$route.path.indexOf('/search/result') > -1)"
      class="mobile-search-box"
    >
      <div>
        <section class="recent-search__section dp-mb-30">
          <div
            class="
              header-title
              d-flex
              align-items-center
              justify-content-between
            "
          >
            <h2 class="title">최근검색어</h2>
            <p
              class="all-remove__button"
              v-if="recentList.length > 0"
              @click="delRecentSearchAll()"
            >
              전체삭제
            </p>
          </div>

          <div class="recent-search" v-if="recentList.length > 0">
            <ul class="recent-search__ul list-style-none">
              <li v-for="(list, index) in recentList" :key="index">
                <span class="dp-filter" @click.self="setSearch(list.content)">
                  {{ list.content }}
                  <i
                    class="dp-icon icon-close"
                    @click.self="delRecentSearch(list.content)"
                  ></i>
                </span>
              </li>
            </ul>
          </div>
          <p class="no-recent-search__text" v-else>
            최근 검색한 내역이 없습니다.
          </p>
        </section>
        <section class="best-search__section">
          <div class="header-title">
            <h2 class="title">인기검색어</h2>
          </div>
          <div class="d-flex align-items-center">
            <div class="best-search">
              <ul class="list-style-none">
                <li
                  v-for="(list, index) in bestList1"
                  :key="index"
                  @click="setSearch(list.content)"
                >
                  <span class="rank-number">{{ list.id }}</span>
                  <span class="rank-text">{{ list.content }}</span>
                </li>
              </ul>
            </div>
            <div class="best-search">
              <ul class="list-style-none">
                <li
                  v-for="(list2, index) in bestList2"
                  :key="index"
                  @click="setSearch(list2.content)"
                >
                  <span class="rank-number">{{ list2.id }}</span>
                  <span class="rank-text">{{ list2.content }}</span>
                </li>
              </ul>
            </div>
          </div>
        </section>
      </div>
    </div>
    <!-- // 최근검색어, 인기검색어 -->

    <!-- 모바일 브랜드 검색 -->
    <div v-if="isMobileBrand && isMobile" class="search-live no-scrollbar">
      <!-- 자동완성 검색어 목록 -->
      <div class="brand-header" v-for="(list, index) in brandList" :key="index">
        <div class="brand" @click="$router.push('/magazine/brand/detail/'+list.idx)">
          <figure class="brand-logo__img" v-if="!$util.isNull(list.linkurl)">
            <img :src="list.linkurl" :alt="list.linkname" />
          </figure>
          <p class="brand-name">{{ list.linkname }}</p>
        </div>
        <div class="brand-shop-button">
          <b-button
            class="dp-btn dp-btn-icon not-hover btn-h30"
            variant="outline-gray-600"
            @click="$router.push('/magazine/brand/detail/'+list.idx)"
            squared
          >
            <span class="text-black">브랜드샵</span>
            <i class="dp-icon icon-arrow-right sm"></i>
          </b-button>
        </div>
      </div>
      <!-- 자동완성 검색어 목록 -->
      <div class="search__list">
        <ul class="list-style-none">
          <li
            v-for="(list, index) in autoList"
            :key="index"
            v-html="list.hkeyword"
            @click="setSearch(list.keyword)"
          >
            <!--<span class="search-word">뱅앤올룹슨</span> 베오사운드 엣지-->
          </li>
        </ul>
      </div>
      <!-- // 브랜드 검색결과 -->
    </div>
    <!-- // 모바일 브랜드 검색 -->

    <!-- // 모바일 -->

    <!-- PC -->
    <div v-if="!isMobile" class="pc-search-box">
      <!-- 인기, 최근 탭 메뉴 나옴 -->
      <template v-if="showTab">
        <b-tabs v-if="!isPcBrand">
          <b-tab title="최근검색어">
            <template v-if="recentList.length > 0">
              <div class="recent-search__container">
                <div class="recent-search">
                  <ul class="recent-search__ul list-style-none">
                    <li
                      v-for="(list, index) in recentList"
                      class="d-flex align-items-center justify-content-between"
                      :key="index"
                    >
                      <span
                        class="text-gray-600"
                        @click.self="setSearch(list.content)"
                      >
                        {{ list.content }}</span
                      >
                      <i
                        class="dp-icon icon-close"
                        @click.self="delRecentSearch(list.content)"
                      ></i>
                    </li>
                  </ul>
                </div>
                <p class="all-remove__btn" @click="delRecentSearchAll()">
                  <span>전체삭제</span>
                </p>
              </div>
            </template>
            <template v-else>
              <div class="recent-search__container">
                <div class="no-recent-search">
                  <p class="no-recent-search_p">최근 검색한 내역이 없습니다.</p>
                </div>
              </div>
            </template>
          </b-tab>
          <b-tab title="인기검색어">
            <div class="d-flex align-items-center best-search__container">
              <div class="best-search">
                <ul class="list-style-none">
                  <li
                    v-for="(list, index) in bestList1"
                    :key="index"
                    @click="setSearch(list.content)"
                  >
                    <span class="rank-number">{{ list.id }}</span>
                    <span class="rank-text">{{ list.content }}</span>
                  </li>
                </ul>
              </div>
              <div class="best-search">
                <ul class="list-style-none">
                  <li
                    v-for="(list, index) in bestList2"
                    :key="index"
                    @click="setSearch(list.content)"
                  >
                    <span class="rank-number">{{ list.id }}</span>
                    <span class="rank-text">{{ list.content }}</span>
                  </li>
                </ul>
              </div>
            </div>
          </b-tab>
        </b-tabs>
        <div v-if="isPcBrand" class="brand-search">
          <!-- 브랜드 검색결과 -->
          <div
            class="brand-header"
            v-for="(list, index) in brandList"
            :key="index"
          >
            <div class="brand">
              <figure
                class="brand-logo__img"
                v-if="!$util.isNull(list.linkurl)"
              >
                <img :src="list.linkurl" :alt="list.linkname" />
              </figure>
              <p class="brand-name">{{ list.linkname }}</p>
            </div>
            <div class="brand-shop-button">
              <b-button
                class="dp-btn dp-btn-icon not-hover btn-h30"
                variant="outline-gray-600"
                 @click="$router.push('/magazine/brand/detail/'+ list.idx)"
                squared
              >
                <span class="text-black">브랜드샵</span>
                <i class="dp-icon icon-arrow-right sm"></i>
              </b-button>
            </div>
          </div>
          <hr class="dp-hr justify-margin dp-mb-20" v-if="brandList.length > 0" />
          <div class="search__list">
            <ul class="list-style-none">
              <li
                v-for="(list, index) in autoList"
                :key="index"
                v-html="list.hkeyword"
                @click="setSearch(list.keyword)"
              >
                <!--<span class="search-word">뱅앤올룹슨</span> 베오사운드 엣지-->
              </li>
            </ul>
          </div>
          <!-- // 브랜드 검색결과 -->
        </div>
      </template>
    </div>
    <!-- // PC -->

    <div class="search-backdrop" @click="clickBackdrop" v-if="showTab"></div>
  </div>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      default: "text",
    },
    label: {
      type: String,
      default: "라벨명",
    },
    isLabel: {
      type: Boolean,
      default: false,
    },
    value: {
      default: "",
    },
    placeholder: {
      type: String,
      default: "placeholder",
    },
    isDisabled: {
      type: Boolean,
      default: false,
    },
    isReadonly: {
      type: Boolean,
      default: false,
    },
    valid: {
      // 성공일 때 조건값 가져오기
      type: Boolean,
      default: false,
    },
    invalid: {
      // 실패일 때 조건값 가져오기
      type: Boolean,
      default: false,
    },
    //모바일에서 isMobile
    isMobile: {
      type: Boolean,
      default: true,
    },
  },
  computed: {
    newValue: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit("input", val);
      },
    },
  },
  data() {
    return {
      showTab: false,
      isMobileBrand: false,
      isPcBrand: false,
      isNotResult: true,
      recentList: [], //최근검색어 목록
      bestList1: [], //인기검색어1 목록
      bestList2: [], //인기검색어2 목록
      brandList: [], //브랜드 목록
      autoList: [], //자동완성 목록
    };
  },
  created() {
    if (this.$route.path === "/search/result") {
      this.isNotResult = false;
    } else if (this.$route.path === "/search") {
      this.isNotResult = true;
      document.body.style.overflow = "hidden";
    }
  },
  mounted() {
    this.recentList = this.$front.getRecentSearch(this);
    this.getBestSearchList();
    if (this.$route.path === "/search/result") {
      this.newValue = this.$route.query.content;
    }
  },
  watch: {
    $route() {
      if (this.$route.path === "/search/result") {
        this.isNotResult = false;
        this.newValue = this.$route.query.content;
      } else if (this.$route.path === "/search") {
        this.isNotResult = true;
        this.isMobileBrand = false;
        document.body.style.overflow = "hidden";
      }
    },
  },
  methods: {
    /**********************
     * 인기검색어 조회
     **********************/
    getBestSearchList() {
      //목록 조회
      this.$http.get("/search/popword/popword.jsp?range=W").then((result) => {
          let list = result.Data.Query;
          //인기검색어 5개씩 분할
          for (let i = 0; i < list.length; i++) {
            if (i < 5) {
              this.bestList1.push(list[i]);
            } else {
              this.bestList2.push(list[i]);
            }
          }
          this.$util.debug(this.bestList1);
          this.$util.debug(this.bestList2);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    /******************************
     * 자동완성 목록 조회처리
     ******************************/
    getAutoSearch(query) {
      let param = {
        query: query, //검색어
        target: "common", //통합검색(고객의 요건에 따라 자동완성 대상값은 추가할 수 있음)
        convert: "fw", //전후방일치값 구분(fw:전방일치, rw:후방일치)
      };
       let str = "query=" + encodeURIComponent(`${param.query}`) + "&target=" + `${param.target}` + "&convert=" + `${param.convert}`;
      this.$http.get("/search/ark/ark_trans.jsp?" +str).then((result) => {
          let list1 =
            result.result[0].totalcount > 0 ? result.result[0].items : []; //convert가fw일경우 전방
          let list2 =
            result.result[1].totalcount > 0 ? result.result[1].items : []; //convert가fw일경우 후방

          //전방
          this.autoList = list1;
          this.brandList = [];
          for (let i = 0; i < this.autoList.length; i++) {
            //자동완성 목록중 브랜드인 목록 추출
            if (this.autoList[i].type == 3) {
              if (!this.$util.isNull(this.autoList[i].linkurl)) {
                let index = this.autoList[i].linkurl.indexOf(";");
                this.autoList[i].idx = this.autoList[i].linkurl.substring(0, index);
                this.autoList[i].linkurl = this.autoList[i].linkurl.substr(index + 1);
              }
              this.brandList.push(this.autoList[i]);
            }
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
    clickBackdrop() {
      this.showTab = false;
    },
    /******************************
     * 검색어 조회 처리
     ******************************/
    searchStart(content) {
      if (this.$util.isNull(content)) {
        this.$eventBus.$emit("alert", "알림", "검색어를 입력해주세요.");
        return;
      }
      this.setRecentSearch(content);
      this.showTab = false;
      // 패이지 중복 이동 방지
      this.isNotResult = false;
      this.$router.push({ name: "search-result", query: { content: content }, params:{init:true} });
      this.newValue = "";
      this.isNotResult = false;
      this.isMobileBrand = false;
      document.body.style.overflow = "auto";
    },
    /******************************
     * 최근 검색어 저장 처리
     ******************************/
    setRecentSearch(content) {
      //최근본상품 저장
      let item = {
        content: content,
      };
      this.$front.setRecentSearch(this, item);
      this.recentList = this.$front.getRecentSearch(this);
    },
    /******************************
     * 최근 검색어 삭제 처리
     ******************************/
    delRecentSearch(content) {
      let item = {
        content: content,
      };
      this.$front.delRecentSearch(this, item);
      this.recentList = this.$front.getRecentSearch(this);
    },
    /******************************
     * 최근 검색어 전체삭제 처리
     ******************************/
    delRecentSearchAll(content) {
      this.$storage.removeLocalStorage("RECENT_SEARCH");
      this.recentList = this.$front.getRecentSearch(this);
    },
    /******************************
     * 검색어 keyup 이벤트 처리
     ******************************/
    handleKeyword(event) {
      let query = this.$refs.search.value;
      this.showTab = true;
      if (query === "") {
        this.isMobileBrand = false;
        this.isPcBrand = false;
        if (this.isMobileBrand) {
          document.body.style.overflow = "auto";
        }
        return;
      }
      //백스페이스가 아닐경우 처리
      if (event.inputType !== "deleteContentBackward") {
        this.isMobileBrand = true;
        this.isPceBrand = true;
        if (this.isMobile) {
          document.body.style.overflow = "hidden";
        }
      }
      //enter일 경우 처리
      if (event.keyCode === 13) {
        this.searchStart(this.value);
        this.$refs.search.blur();
      }
      // pc 탭메뉴에서 브랜드 검색 메뉴로 변경
      this.isPcBrand = true;
      this.getAutoSearch(query);
    },
    onFocus() {
      this.showTab = true;
    },
    clearValue() {
      this.$emit("input", "");
      this.isMobileBrand = false;
      this.isPcBrand = false;
      if (!this.isNotResult) {
        document.body.style.overflow = "auto";
      }
    },
    /***********************
     * 선택한 검색어 리턴
     **************************/
    setSearch(content) {
      if (window.sessionStorage.getItem("platform") == "MAC001") {
        this.searchStart(content);
      } else {
        this.$emit("select", content);
      }
    },
  },
  beforeDestroy() {
    document.body.style.overflow = "auto";
  },
};
</script>
