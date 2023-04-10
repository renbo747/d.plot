<template>
  <!-- 배송조회 모달 -->
  <b-modal
    id="trackingModal"
    :modal-class="{
      'dp-modal pop-over tracking-modal': $store.state.platform == 'MAC001',
      'dp-modal page-layer tracking-modal': $store.state.platform != 'MAC001',
    }"
    hide-footer
    scrollable
    centered
  >
    <!-- 배송조회 모달 헤더 -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">배송조회</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <!-- 배송조회 모달 바디 -->
    <div class="page-layer__body" v-if="isend">
      <template v-if="isdirectDeli">
        <div class="directly-delivery__section">
          <div>
            <!--관리자 테스트 삭제요청(날짜) -->
            <!--<p class="info-text font-weight-500">3월 31일 (목)</p>-->
            <p class="info-text" v-if="param.item.track == '3'">본 상품은 직접회수됩니다.</p>
            <p class="info-text" v-else>본 상품은 직접배송으로 출고됩니다.</p>
            <p class="text-primary text-center tracking-text-etc" v-if="invoiceList.length > 0">
              {{ invoiceList[0].dirdelivmsg }}
            </p>
            <!-- <p class="text-primary text-center tracking-text-etc">
              주문제작 후 발송되는 상품입니다.
            </p> -->
          </div>
          <div class="box-container">
            <p>배송관련 사항은 협력사 고객센터로 문의 바랍니다.</p>
          </div>
        </div>
        <hr class="dp-hr justify-margin" />
      </template>
      <div class="tracking-status__section" v-if="!isdirectDeli">
        <h2 class="tracking-status__h2" v-if="invoiceInfo.lastDlvStatType < 50">
          상품이 배송지에 도착했습니다.
        </h2>
        <h2
          class="tracking-status__h2"
          v-else-if="invoiceInfo.lastDlvStatType < 70"
        >
          상품이 배송중입니다.
        </h2>
        <h2
          class="tracking-status__h2"
          v-else-if="invoiceInfo.lastDlvStatType >= 70"
        >
          상품이 배송완료 되었습니다
        </h2>
        <h2 class="tracking-status__h2" v-else>상품을 발송하였습니다.</h2>
        <ul class="tracking-stepper justify-margin list-style-none">
          <li>
            <div class="stepper-item pass">
              <span class="stepper-bullet"></span>
              <p class="stepper-text">상품발송</p>
            </div>
          </li>
          <li>
            <div
              class="stepper-item active"
              :class="{
                active: invoiceInfo.lastDlvStatType >= 50,
                pass: invoiceInfo.lastDlvStatType >= 25,
              }"
            >
              <span class="stepper-bullet"></span>
              <p class="stepper-text">배송지도착</p>
            </div>
          </li>
          <li>
            <div
              class="stepper-item"
              :class="{
                active: invoiceInfo.lastDlvStatType >= 70,
                pass: invoiceInfo.lastDlvStatType >= 50,
              }"
            >
              <span class="stepper-bullet"></span>
              <p class="stepper-text">상품배송중</p>
            </div>
          </li>
          <li>
            <div
              class="stepper-item"
              :class="{ active: invoiceInfo.lastDlvStatType >= 70 }"
            >
              <span class="stepper-bullet"></span>
              <p class="stepper-text">배송완료</p>
            </div>
          </li>
        </ul>
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="tracking__section">
        <product-order
          :is-badge="false"
          :is-footer="false"
          :is-price="false"
          :is-status="false"
          :product-info="param.item"
        />
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="tracking__section" v-if="!isdirectDeli">
        <ul class="info__ul list-style-none">
          <li>
            <div class="info__text">
              <p v-if="param.item.track == '1'">주문자명</p>
              <p v-if="param.item.track == '1'">{{ info.ordname }}</p>
              <p v-if="param.item.track == '2'">교환수령인</p>
              <p v-if="param.item.track == '2'">{{ info.excdlvname }}</p>
              <p v-if="param.item.track == '3'">회수자명</p>
              <p v-if="param.item.track == '3'">{{ info.recname }}</p>
            </div>
          </li>
          <li>
            <div class="info__text">
              <p>송장번호</p>
              <p>{{ invoiceInfo.invoiceNo }}</p>
            </div>
          </li>
          <li>
            <div class="info__text">
              <p>배송업체</p>
              <p>{{ invoiceInfo.logisticsName }}</p>
              <!-- 2022-08-05 배송업체 번호 넘겨받는게 없어서 제거하기로함-->
              <!--
              <b-button
                class="dp-btn dp-btn-icon not-hover btn-h30 call-btn"
                variant="outline-gray-400"
                pill
              >
                
                <i class="dp-icon icon-phone sm02"></i>
                <span class="text-black">1588-1255</span>
              </b-button>
              -->
            </div>
          </li>
          <li>
            <div class="info__text">
              <p>요청사항</p>
              <p v-if="param.item.track == '1'">{{info.rcvreqtype == 'RVT006' ? info.rcvreqdetail : info.rcvreqtypenm}}</p>
              <p v-if="param.item.track == '2'">{{info.excreqtypenm}}</p>
              <p v-if="param.item.track == '3'">{{info.rcvreqtypenm}}</p>
            </div>
          </li>
        </ul>
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="tracking-status-text__section" v-if="!isdirectDeli">
        <div class="tracking-location">
          <ul class="tracking-location__ul list-style-none">
            <li
              v-for="(tracking, index) in invoiceInfo.details"
              :key="index"
              class="active"
            >
              <div class="location-item">
                <p class="mb-0">
                  {{ tracking.dlvStatName }} / {{ tracking.fromBranName }}
                </p>
                <p class="mb-0">{{ tracking.procDateTime }}</p>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <template>
        <hr class="dp-hr justify-margin" />
        <div class="directly-delivery__section-footer">
          <div class="center-info" v-if="param.item.dealerno > 0">협력사 고객센터안내</div>
          <div class="center-info" v-else>고객센터안내</div>
          <div class="info-span">
            <span class="brand-name">{{dealer.compname}}</span>
            <span class="brand-number atten-new">{{$util.maskTel(dealer.tel)}}</span>
          </div>
        </div>
      </template>
    </div>
  </b-modal>
  <!-- // 배송조회 모달 -->
</template>

<script>
export default {
  props: {
    param: { type: Object },
    fnConfirm: { type: Function },
    fnCancel: { type: Function },
  },
  data() {
    return {
      info : null,
      dealer : null,
      invoiceList: [],
      invoiceInfo: {
        tracking: [],
      },
      isdirectDeli: false,
      isend:false
    };
  },
  created() {
    this.getTracking();
  },
  methods: {
    getTracking() {
      this.isend = false;
      let param = this.param.item;
      param.isloading = false;
      
      this.$http.post("/common/order/tracking", param).then((result) => {
        this.info = result.data.result;
        this.invoiceInfo = result.data.tracking;
        this.dealer = result.data.dealer;

        if(this.param.item.track == '1') {
          this.invoiceList = result.data.list;
          if (this.invoiceList.length > 0) {
            this.isdirectDeli = this.invoiceList[0].istracking == "T" ? false : true;
          }
        } else if (this.param.item.track == '2'){
          this.isdirectDeli = this.info.isexctracking == "T" ? false : true;
        } else {
          this.isdirectDeli = this.info.isrectracking == "T" ? false : true;
        }
        this.isend = true;
      });
    },
  }
};
</script>
<style scoped>
</style>