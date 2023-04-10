<template>
  <main class="mypage-previous-order-pc">
    <div class="container">
      <div class="previous-order__title__area">
        <h1 class="mb-0">이전주문내역(다다픽)</h1>
      </div>
      <!-- 이전구매내역 있을 때-->
      <div v-if="orderList.length > 0">
        <template  v-for="(daylist, index) in orderList">
          <section
            class="order-list__wrap dp-section"
            :key="index"
          >
            <div class="order-date__area">
              <p class="mb-0 date__P atten-new">
                {{ $util.getFormatDate(daylist.orderday, ".") }}
              </p>
              <span class="dp-bar"></span>
              <p class="mb-0 order-number__p atten-new">
                {{ daylist.orderno }}
              </p>
            </div>
            <template v-for="(item, idx) in daylist.list">
              <hr class="dp-hr h1" :key="idx + 'hr'" />
              <div class="product-info__area d-flex" :key="idx">
                <div class="product-info__left">
                  <p class="product-title mb-0">
                     {{ item.goodsinfo }}
                  </p>
                </div>
                <div class="product-into__right">
                  <p class="mb-0 product-conunt">{{ item.ordercnt }}개</p>
                  <div>
                    <p class="mb-0 product-price__area">
                    <span class="product-price atten-new"> {{$util.maskComma(item.orderamt)}}</span>
                    <span class="price-unit">원</span>
                    </p>
                    <p class="dp-p-sm text-primary text-right mb-0">{{item.ordstatus}}</p>
                  </div>
                </div>
              </div>
            </template>
          </section>
          <hr class="dp-hr previous-order-hr" :key="index + 'main_hr'" v-if="index+1 != orderList.length"/>
        </template>
        <div>
          <div class="pagination__area d-flex justify-content-center">
            <base-pagination
              class="justify-content-center dp-mt-10"
              :currentPage="pagingData.currentPage"
              :listTotal="pagingData.listTotal"
              :listCnt="pagingData.listCnt"
              @changePage="changePage"
            />
          </div>
        </div>
      </div>
      <!-- 이전구매내역 없을 때-->
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
            <i class="dp-icon icon-previous-order h68"></i>
          </div>
          <div class="empty-text__area">
            <p class="mb-0 empty-text__p">이전주문 내역이 없어요</p>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      // 이전주문내역 유무
      isPreviousOrder: true,
      pagingData: {
        currentPage: 1, // 현재 페이지
        listTotal: 0, // 조회목록 전체 수
        listCnt: 10, // 한페이지에 노출할 목록수
      },
      orderList: [],
      isEnd:false
    };
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.isEnd =false;
      this.orderList = [];
      this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
      this.getPreOrder();
    },
    /**********************
     * 목록 조회
     ********************/
    getPreOrder() {
      let param = Object.assign(this.pagingData);
      this.isEnd = false;
      this.$http.post("/mypage/preorder", param).then((result) => {
        if (result.statusCode == 200) {
          if (result.data.preorderlist.length > 0) {
            this.pagingData.listTotal = result.data.listtotal;
            let dayList = [];
            let tempList = result.data.preorderlist;
            tempList.forEach((item) => {
              item.dayorder = item.orderday + item.orderno;
            });
            for (let i = 0; i < tempList.length; i++) {
              let idx = this.$front.containIdx(
                dayList,
                "dayorder",
                tempList[i].dayorder
              );
              if (idx == -1) {
                dayList.push({
                  dayorder: tempList[i].dayorder,
                  orderday: tempList[i].orderday,
                  orderno: tempList[i].orderno,
                  list: tempList.filter(
                    (x) => x.dayorder == tempList[i].dayorder
                  ),
                });
              }
            }

            //값 저장
            this.orderList = dayList;
            this.$util.scrollToTop();
          }
        }
        this.isEnd = true;
      });
    },
    /*****************************
     * 페이지 변경시
     ****************************/
    changePage(page) {
      this.pagingData.currentPage = page;
      this.orderList = [];
      this.getPreOrder();
    },
  },
  mounted() {
    this.initData();
  },
};
</script>
