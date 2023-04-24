<template>
    <!--  쿠폰 다운로드 모달 (추가쿠폰받기)-->
    <b-modal
      id="couponAddModal"
      :modal-class="{'dp-modal pop-over coupon-more-modal': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer coupon-more-modal': $store.state.platform != 'MAC001'}"
      scrollable
      centered
      :hide-footer="true"
    >
    <!-- Page Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">쿠폰 다운로드</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- Page Modal Body -->
      <div class="page-layer__body" v-if="couponList.length > 0">
        <section class="dp-coupon__area">
          <div class="coupon-list__area">
            <ul class="list-style-none coupon-list" :class="{'row': $store.state.platform == 'MAC001'}">
              <li v-for="(coupon, idx) in couponList" :key="idx" :class="{'col-6': $store.state.platform == 'MAC001'}">
                <order-coupon :coupon-info="coupon" @down="downloadCoupon(coupon);"/>
              </li>
            </ul>
          </div>
        </section>
        <hr class="dp-hr h1" />
        <section class="dp-coupon__area" v-if="couponAddList.length > 0">
          <div class="coupon-area__title dp-p">함께 사용 가능한 쿠폰</div>
          <div class="coupon-list__area">
            <ul class="list-style-none coupon-list" :class="{'row': $store.state.platform == 'MAC001'}">
              <li v-for="(coupon, idx) in couponAddList" :key="idx" :class="{'col-6': $store.state.platform == 'MAC001'}">
                <order-coupon :coupon-info="coupon" @down="downloadCoupon(coupon);"/>
              </li>
            </ul>
          </div>
        </section>
        <div class="pagination__area d-flex justify-content-center" v-if="$store.state.platform == 'MAC001'">
          <base-pagination 
            :currentPage="pagingData.currentPage"
            :listTotal="pagingData.listTotal"
            :listCnt="pagingData.listCnt"
            @changePage="changePage"/>
        </div>
        <b-button
          class="dp-btn text-white dp-btn-icon"
          variant="gray-800"
          squared
          @click="downloadAll"
        >
          <span>전체쿠폰받기</span>
          <div class="download-icon__area">
            <i class="dp-icon icon-download"></i>
          </div>
        </b-button>
      </div>
      <div v-if="couponList.length == 0">
        <section>
          <div class="no-result text-center">
            <i class="dp-mb-20">
              <img
                src="@assets.admin/common/icon/icon-exclamation-mark-128px.svg"
                alt="검색결과 없음"
              />
            </i>
            <p class="no-search__text">다운 가능한 쿠폰이 없습니다.
            </p>
            <br/>
            <br/>
            <br/>
          </div>
        </section>
      </div>
    </b-modal>
    <!-- // 쿠폰 다운로드 모달 (추가쿠폰받기)-->
</template>

<script>
export default {
    name : 'CouponDownload',
    data() {
        return {
            pagingData: {
              currentPage: 0,         // 현재 페이지
              listTotal: 0,           // 조회목록 전체 수
              listCnt: 4              // 한페이지에 노출할 목록수
            },
            list : [],
            couponList : [],
            couponAddList : [],
        };
    },
    props : {
        param : Object,
        fnConfirm : Function
    },
    mounted(){
      //this.couponList = this.param;
      //this.listTotal = this.couponList.length;
      //this.getCouponList();
      this.setCouponList();
    },
    methods: {
       async getCouponList() {
        let param = this.param;
        param.isloading = true;
         await this.$http.post('/coupon/order/list', this.param).then(result => {
          this.couponList = [];
          //this.couponAddList = [];

          for(let i = 0 ; i < result.data.list.length ; i++){
            let idx = this.$front.containIdx(this.couponList, "comcpnidx", result.data.list[i].comcpnidx);
            if(idx == -1) {
              //this.list.push(result.data.list[i]);              
              this.couponList.push(result.data.list[i]);
            }
          }
 
          if(this.$store.state.platform == 'MAC001') {
            //this.couponList = this.list;
            this.pagingData.listTotal = this.couponList.length;
            this.changePage(1);
          }
        });
      },
      setCouponList() {
        for(let i = 0 ; i < this.param.couponList.length ; i++){
          let idx = this.$front.containIdx(this.list, "comcpnidx", this.param.couponList[i].comcpnidx);
          if(idx == -1) {
            this.list.push(this.param.couponList[i]);
          }
        }

        if(this.$store.state.platform == 'MAC001') {
          this.pagingData.listTotal = this.list.length;
          this.changePage(1);
        } else {
          this.couponList = this.list;
        }
      },
      downloadCoupon(coupon) {
        if(coupon.isdownload == 'T') {
          return;
        } 
        const param = this.$util.deepClone(coupon);
        param.items = this.param.items;
        param.isoption = this.param.isoption;
        this.$http.post('/coupon/download', param).then(async result => {
          if(result.statusCode == 200) {
            coupon.cpnmisidx = result.data.cpnmisidx;
            coupon.isdownload = result.data.isdownload;
            this.$toast.clear();
            this.$toast.open("쿠폰을 다운로드하였습니다.");
          } else {
            coupon.cpnmisidx = result.data.cpnmisidx;
            coupon.isdownload = result.data.isdownload;
          }
          this.fnConfirm(result.data.list);
          await this.getCouponList(); 
          //this.setCouponList();

        });
      },
      downloadAll() {
        const param = {
          list : this.list,
          items : this.param.items,
          isoption : this.param.isoption
        }

        this.$http.post('/coupon/download/all', param).then(result => {
          if(result.statusCode == 200) {
            this.$toast.clear();
            this.$toast.open("전체쿠폰을 다운로드하였습니다.");
            this.fnConfirm(result.data.list);
            this.$bvModal.hide('couponAddModal');
          } 
        });
      },
      changePage(page) {
        if(this.pagingData.currentPage != page) {
          this.couponList = [];
          this.pagingData.currentPage = page;
          let startPage = (page > 1) ? ((page - 1 ) * this.pagingData.listCnt) : 0;
          let endPage = startPage + this.pagingData.listCnt;
          for(let i = startPage ; i < endPage ; i++) {
            if(i >= this.list.length) {
              break;
            }
            this.couponList.push(this.list[i]);
          }
        }
      }
    },
    
}
</script>