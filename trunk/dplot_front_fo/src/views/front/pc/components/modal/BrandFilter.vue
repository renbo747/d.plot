<template>
  <div class="brand-more-contents">
    <section class="letter-filter-section">
      <div class="d-flex letter-filter-list">
        <!-- 전체선택버튼 -->
        <div class="letter__all__check">
          <label :for="'letter' + allCheckData.id" class="all__label mb-0">
            <input
              :id="'letter' + allCheckData.id"
              v-model="selectLetter"
              type="radio"
              name="letter"
              value="ALL"
              hidden
            />
            <span class="all__label__item" @click="letterChange('ALL')">{{
              allCheckData.label
            }}</span>
          </label>
        </div>
        <!-- 글자선택버튼 -->
        <div class="letter__check">
          <!-- 알파벳 -->
          <div class="letter__check__alphabet">
            <ul class="list-style-none filter__ul alphabet__ul">
              <li
                v-for="(list, index) in alphabetData"
                :key="index"
                @click="letterChange(list.label)"
                :style="index === alphabetData.length -1 && list.label !== 'ETC' ? 'width:40px;' : ''"
              >
                <label :for="'letter' + list.id" class="letter__label mb-0">
                  <input
                    :id="'letter' + list.id"
                    v-model="selectLetter"
                    type="radio"
                    name="letter"
                    :value="list.label"
                    hidden
                  />
                  <span class="label__item">{{ list.label }}</span>
                </label>
              </li>
            </ul>
          </div>
          <!-- 한글 -->
          <div class="letter__check__ko">
            <ul class="list-style-none filter__ul letter__ul">
              <li
                v-for="(list, index) in letterData"
                :key="index"
                @click="letterChange(list.label)"
              >
                <label :for="'letter' + list.id" class="letter__label mb-0">
                  <input
                    :id="'letter' + list.id"
                    v-model="selectLetter"
                    type="radio"
                    name="letter"
                    :value="list.label"
                    hidden
                  />
                  <span class="label__item">{{ list.label }}</span>
                </label>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>
    <!-- 브랜드 선택 -->
    <section class="brand-filter-section">
      <div class="more-contents__brand">
        <ul class="list-style-none brand__ul">
          <template v-for="(list, index) in brandData">
            <li
              v-show="
                selectLetter === 'ALL' ||
                list.filterfrstname === selectLetter ||
                list.filterfrstename === selectLetter
              "
              :key="index"
            >
              <label :for="'b' + list.idx" class="brand__label mb-0">
                <input
                  :id="'b' + list.idx"
                  type="checkbox"
                  name="brandfilter"
                  v-model="setBrandList"
                  :value="list.idx"
                  @change="checkLength(list)"
                  hidden
                />
                <div class="label__item">
                  <div v-if="!$util.isNull(list.logopath)">
                    <img
                      :src="list.logopath"
                      alt="브랜드 로고"
                      style="max-width: 160px; max-height: 23px"
                    />
                  </div>
                  <div v-else>
                    <p class="product-name-ko">{{ list.name }}</p>
                  </div>
                </div>
              </label>
            </li>
          </template>
        </ul>
      </div>
    </section>
    <!-- 버튼 -->
    <section class="more-contents__btn">
      <div class="btn-group">
        <b-button
          class="dp-btn dp-btn-icon not-hover"
          variant="outline-gray-800"
          @click="initdata()"
          squared
        >
          <span>초기화</span>
          <i class="dp-icon icon-reset md"></i>
        </b-button>
        <b-button
          class="dp-btn text-white"
          variant="gray-800"
          squared
          @click="ok()"
        >
          <span>선택보기</span>
        </b-button>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  props: {
    param: { type: Object },
    fnConfirm: { type: Function },
  },
  data() {
    return {
      //전체 체크 데이터
      allCheckData: {
        id: "all",
        label: "ALL",
        checked: true,
      },
      // 영어체크 데이터
      alphabetData: [],
      //한글체크 데이터
      letterData: [],
      //브랜드 섹션 데이터
      brandData: [],
      tabTitle: [],
      selectLetter: "ALL",
      filterinfo: {},
      setFilterList: [],
      setBrandList: [],
      fBrandList: [],
      searchData: {},
    };
  },
  mounted() {
    if (!this.$util.isBlank(this.$route.query.search)) {
      const searchStr = decodeURIComponent(this.$route.query.search);
      this.searchData = JSON.parse(searchStr);
      this.setBrandList = this.$util.isEmpty(this.searchData.fBrandList)
        ? []
        : this.searchData.fBrandList;
      this.setFilterList = this.$util.isEmpty(this.searchData.setFilterList)
        ? []
        : this.searchData.setFilterList;
    }
    let param = {
      isloading: false,
      brandlist: this.param.fBrandList,
    };

    this.$http.post("/brand/filter", param).then((result) => {
      if (result.statusCode === 200) {
        let data = result.data;
        this.brandData = data.brandlist;
        this.alphabetData = data.alphabetdata;
        this.letterData = data.letterdata;
      }
    });
  },
  methods: {
    letterChange(label) {
      this.selectLetter = label;
    },
    initdata() {
      this.selectLetter = "ALL";
      this.setBrandList = [];
      this.setFilterList = [];
    },
    checkLength(list) {
      if (this.setBrandList.length > 10) {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "브랜드는 최대 10개까지 선택 가능합니다.",
          () => {
              this.setBrandList.pop();
          }
        );
      } else {
        this.setFilter("setBrandList", list);
      }
    },
    ok() {
      this.searchData.fBrandList = this.setBrandList;
      this.searchData.setFilterList = this.setFilterList;
      const searchStr = encodeURIComponent(JSON.stringify(this.searchData));
      if (this.$route.path.indexOf("result")>-1) {
        this.$router.push({name: this.$route.name, query: {content:this.$route.query.content, search: searchStr }});
      }else{
        this.$router.push({name: this.$route.name, query: { search: searchStr }});
      }
      
      this.$root.$emit("bv::hide::modal", "brandPlusModal");
    },
    /************************
     * 필터 리스트 추가
     *************************/
    setFilter(target, value) {
      let name = "";
      this.brandData.forEach((element) => {
        if (element.idx == value.idx) {
          name = element.enname;
        }
      });
      if (this.setFilterList.filter((x) => x.value == name).length == 0) {
        this.setFilterList.unshift({
          target: target,
          value: name,
          id: value.idx,
        });
      } else {
        this.setFilterList = this.setFilterList.filter((x) => x.value != name);
      }
    },
    changefBrandList(list) {
      if (this.fBrandList.filter((x) => x.idx == list.idx).length == 0) {
        this.fBrandList.unshift(list);
      }
    },
  },
};
</script>

<style lang="scss">
.brand-more-contents {
  // 글자선택 scss
  .letter-filter-section {
    padding-bottom: 50px;
    // 전체선택
    .letter__all__check {
      margin-right: 10px;
    }
    .all__label {
      width: 80px;
      height: 40px;
      border: 1px solid $gray-400;
      input:checked {
        border: 0;
      }
    }
    .all__label__item {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      //text-transform: uppercase;
      font-size: 16px;
      letter-spacing: -0.64px;
      color: $gray-800;
      cursor: pointer;
    }

    input:checked + .all__label__item {
      background-color: $gray-800;
      color: #ffffff;
    }
    // 영어 선택
    .alphabet__ul {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      li {
        width: 40px;
        height: 40px;
        border: 1px solid $gray-400;
        margin-bottom: 10px;
        margin-right: 6px;

        &:last-child {
          width: 80px;
        }
      }
    }
    .letter__ul {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      li {
        width: 40px;
        height: 40px;
        border: 1px solid $gray-400;
        margin-bottom: 10px;
      }

      li + li {
        margin-left: 6px;
      }
    }
    .letter__label {
      display: block;
      width: 100%;
      cursor: pointer;
      .label__item {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 40px;
        padding: 0;
        font-size: 16px;
        letter-spacing: -0.64px;
        font-weight: 400;
        color: $black;
        cursor: pointer;
        img {
          height: 16px;
        }
      }
      input:checked + .label__item {
        position: relative;
        background-color: $gray-800;
        color: #ffffff;
        font-weight: 500;
        &:before {
          position: absolute;
          top: -1px;
          left: -1px;
          display: block;
          content: "";
          width: calc(100% + 2px);
          height: calc(100% + 1px);
          border: 1px solid $black;
        }
      }
    }
  }
  .brand-filter-section {
    padding: 0 93px;

    .brand__ul {
      display: flex;
      flex-wrap: wrap;

      li {
        width: 19%;
        margin-bottom: 10px;
        padding: 0;

        &:first-child {
          margin-right: 1.25%;
        }

        &:nth-last-child(-n + 5) {
          margin-bottom: 0;
        }

        &:nth-child(5n + 0) {
          margin-right: 0;
        }
      }

      li + li {
        margin-right: 1.25%;
      }
      .brand__label {
        display: block;
        width: 100%;

        .label__item {
          display: flex;
          align-items: center;
          justify-content: center;
          width: 100%;
          height: 54px;
          padding: 0;
          background-color: $gray-100;
          cursor: pointer;
          img {
            width: 100%;
            height: 80%;
          }
        }

        input:checked + .label__item {
          position: relative;
          border: 1px solid $gray-100;

          &:before {
            position: absolute;
            top: -1px;
            left: -1px;
            display: block;
            content: "";
            width: calc(100% + 2px);
            height: calc(100% + 2px);
            border: 1px solid $black;
          }
        }
      }
    }
  }

  .more-contents__btn {
    padding-top: 50px;
    width: 100%;
    .btn-group {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .icon-reset {
      background-image: url("~@/assets/common/icon/icon-reset-black-24px.svg");
    }
    .dp-btn {
      max-width: 255px;
      span {
        font-size: 16px;
        letter-spacing: -0.64px;
      }
    }
  }
}
</style>
