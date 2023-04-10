<template>
  <main class="mypage-previous-order">
    <div class="container">
      <!-- 이전구매내역 있을 때-->
      <div>
        <template v-for="(daylist, index) in orderList">
          <section class="order-list__wrap dp-section" :key="index">
            <div class="order-date__area">
              <p class="mb-0 date__P atten-new">
                {{ $util.getFormatDate(daylist.orderday, ".") }}
              </p>
            </div>
            <template v-for="(ordlist, ordindex) in daylist.list">
              <div
                class="order-number__area"
                :key="ordindex"
                v-if="
                  ordindex == 0
                    ? true
                    : daylist.list[ordindex].orderno !=
                      daylist.list[ordindex - 1].orderno
                "
              >
                <p class="mb-0 order-number__p">{{ ordlist.orderno }}</p>
              </div>
              <template v-for="(item, idx) in ordlist.list">
                <div class="product-info__area" :key="ordindex + idx + 'aa'">
                  <p class="product-title mb-0">
                    {{ item.goodsinfo }}
                  </p>
                  <div class="product-info__bottom">
                    <p class="mb-0 product-conunt">{{ item.ordercnt }}개</p>
                    <div>
                      <p class="mb-0 product-price__area">
                        <span class="product-price atten-new">{{
                          $util.maskComma(item.orderamt)
                        }}</span>
                        <span class="price-unit">원</span>
                      </p>
                      <p class="dp-p-sm text-primary text-right mb-0">
                        {{ item.ordstsatus }}
                      </p>
                    </div>
                  </div>
                </div>
                <hr
                  class="dp-hr h1"
                  :key="ordindex + idx + 'bb'"
                  v-if="idx != ordlist.list.length"
                />
              </template>
            </template>
          </section>
          <hr class="dp-hr hr-order" :key="index" v-if="orderList.length - 1 > index "/>
        </template>

        <infinite-loading
          :identifier="infiniteId"
          @infinite="getPreOrder"
          spinner="circles"
        >
          <div slot="no-more"></div>
          <div slot="no-results">
            <div class="empty__area" v-if="isEnd && orderList.length <= 0">
              <div class="empty__body d-flex flex-column align-items-center">
                <div
                  class="
                    empty-icon-area
                    d-flex
                    align-items-center
                    justify-content-center
                  "
                >
                  <i class="dp-icon icon-previous-order xl02"></i>
                </div>
                <div class="empty-text__area">
                  <p class="mb-0 empty-text__p">이전주문 내역이 없어요</p>
                </div>
              </div>
            </div>
          </div>
        </infinite-loading>
      </div>
      <!-- 이전구매내역 없을 때-->
      <!-- <div class="empty__area" v-else>
        <div class="empty__body d-flex flex-column align-items-center">
          <div
            class="
              empty-icon-area
              d-flex
              align-items-center
              justify-content-center
            "
          >
            <i class="dp-icon icon-previous-order xl02"></i>
          </div>
          <div class="empty-text__area">
            <p class="mb-0 empty-text__p">이전주문 내역이 없어요</p>
          </div>
        </div>
      </div> -->
    </div>
  </main>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "이전주문내역(다다픽)",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  components: {
    InfiniteLoading,
  },
  data() {
    return {
      // 이전주문내역 유무
      isPreviousOrder: true,
      isloading: true, //스크롤페이징 조회할지 여부
      infiniteId: +new Date(), //스크롤페이징 id
      pagingData: {
        currentPage: 1, // 현재 페이지
        listTotal: 0, // 조회목록 전체 수
        listCnt: 10, // 한페이지에 노출할 목록수
      },
      orderList: [],
      cntList: [],
      isEnd:false
    };
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.isEnd = false;
      this.orderList = [];
      this.infiniteId += 1;
      this.isloading = true;
      this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
    },
    getPreOrder($state) {
      if (this.isloading) {
        let param = Object.assign(this.pagingData);
        this.isEnd = false;
        this.$http.post("/mypage/preorder", param).then((result) => {
          if (result.statusCode == 200) {
            if (result.data.preorderlist.length > 0) {
              this.pagingData.listTotal = result.data.listtotal;
              let dayList = [];
              let tempList = result.data.preorderlist;
              this.cntList.push(...result.data.preorderlist);
              for (let i = 0; i < tempList.length; i++) {
                let idx = this.$front.containIdx(
                  dayList,
                  "orderday",
                  tempList[i].orderday
                );
                if (idx == -1) {
                  dayList.push({
                    orderday: tempList[i].orderday,
                    list: tempList.filter(
                      (x) => x.orderday == tempList[i].orderday
                    ),
                  });
                }
              }
              for (let i = 0; i < dayList.length; i++) {
                let ordList = [];
                for (let j = 0; j < dayList[i].list.length; j++) {
                  let idx = this.$front.containIdx(
                    ordList,
                    "orderno",
                    dayList[i].list[j].orderno
                  );
                  if (idx == -1) {
                    ordList.push({
                      orderno: dayList[i].list[j].orderno,
                      list: dayList[i].list.filter(
                        (x) => x.orderno == dayList[i].list[j].orderno
                      ),
                    });
                  }
                }
                dayList[i].list = ordList;
              }
              //값 저장
              this.orderList.push(...dayList);
              if (this.pagingData.listTotal <= this.cntList.length) {
                this.isloading = false;
                $state.complete();
              } else {
                this.pagingData.currentPage += 1;
                $state.loaded();
              }
            } else {
              this.isloading = false;
              this.pagingData.listTotal = result.data.listtotal;
              $state.complete();
            }
          }
          this.isEnd = true;
        });
      }
    },
  },
  mounted() {
    this.$util.scrollToTop();
    this.initData();
  },
};
</script>
