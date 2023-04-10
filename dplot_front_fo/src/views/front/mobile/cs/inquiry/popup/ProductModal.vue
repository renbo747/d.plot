
<template>

    <!-- 주문상품선택 Modal -->
    <b-modal
      id="ProductModal"
      modal-class="dp-modal page-layer select-product-modal"
      scrollable
      :hide-footer="true"
    >
      <!-- 주문상품선택 Modal Header -->
      <template #modal-header="{ }">
        <h5 class="modal-title">주문상품선택</h5>
        <i class="modal-close" @click="closeProductModal()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <div class="select-product-header">
        <!-- scss index scss 안 select box 참조 -->
        <div class="cs-product-qna dp-panel pt-0">
          <div class="cs-product-qna__header__select row">
            <div class="col-6">
              <base-select
                placeholder="최근 1개월"
                class="inquiry-select"
                v-model="popMonthSelect"
                :options="popMonthOption"
                @input="popMonthCheck"
              />
            </div>
            <div class="col-6">
              <base-select
                placeholder="전체"
                class="inquiry-select"
                v-model="popInquirySelect"
                :options="popInquiryOption"
                @input="popInquiryCheck"
              />
            </div>
          </div>
        </div>
      </div>

      <!-- 주문상품선택 Modal Body -->
      <div class="select-product-body list" v-if="orderList.length > 0">
        <section class="select-product-area dp-panel pt-0"
            v-for="(list, index) in orderList"
            :key="index"        
        >
          <div>
            <div class="select-product-item-info pt-0">
              <div class="select-product-item">
                <div class="select-product-date__list">
                  <div class="select-product-date__list__data">
                  <span class="dp-p-sm text-gray-700 font-weight-400"
                  >{{list.orderdate}}</span
                  >
                    <span class="dp-bar select-product-date-bar"></span>
                    <span class="dp-p-sm text-gray-800 font-weight-400"
                    >{{list.ordno}}</span
                    >
                  </div>
                </div>
              </div>
            </div>
            <ul class="select-product-item-ul list-style-none dp-panel">
              <li class="select-product-item-li"
                v-for="(item, index2) in list.goods"
                :key="index2"                            
              >
                <div>
                  <product-inquiry
                    :is-header="true"
                    :is-footer="false"
                    :is-status="false"
                    :is-option="false"
                    :is-icon="false"
                    :is-title="false"
                    :product-info="item"
                    @change="checkChange(item)"
                    :key="skey"
                  />
                </div>
              </li>
            </ul>
          </div>
          <hr class="dp-hr" />
        </section>
      </div>
      <div v-else class="row list__empty-items">
        <div class="list__icon">
          <i class="empty-list-icon"></i>
        </div>
        <p class="empty-list__text">
          주문상품목록이 없습니다.<br />
        </p>
          <!-- <p class="empty-list__text">
          검색조건에 맞는 상품이 없습니다.<br />
          필터를 초기화 해주세요.
        </p> -->
        <!-- <b-button
          class="dp-btn dp-btn-icon not-hover shop-list__empty__btn"
          variant="outline-gray-800"
          squared
            @click="getFilterInit()"
        >
          <span class="empyt__btn__text">초기화</span>
          <i class="dp-icon icon-reset md"></i>
        </b-button> -->
      </div>

      <section class="select-footer-section">
        <div class="footer-section-btn btn-group row">
          <b-button
            class="dp-btn text-gray-800 login-btn__close col-6"
            variant="outline-gray-800 not-hover"
            block
            @click="closeProductModal()"
            >취소하기</b-button
          >
          <b-button
            class="dp-btn text-white login-btn__confirm col-6"
            variant="gray-800" @click="btnOrderSelect()">선택하기</b-button
          >
        </div>
      </section>      
    </b-modal>
    <!-- // 주문상품선택 Modal -->        
    
</template>

<style scoped>
  .select-footer-section {
    bottom:15px;
  }
  .select-product-body.list {
    height:65vh;overflow:scroll;overflow-x:hidden;margin-bottom:30px;
  }
</style>

<script src="@views.mobile/cs/inquiry/popup/ProductModal.js"/>
