<template>
  <!-- 상품문의 작성 Modal -->
  <b-modal
    id="QnaModal"
    modal-class="dp-modal page-layer product-inquiry-modal"
    scrollable
    centered
    :hide-footer="true"
  >
    <!-- Product inquiry Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">상품문의하기</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Product inquiry Modal Body -->
    <template #default="{}">
      <div class="page-layer__body">
        <div class="dp-mb-30">
          <p class="post-report__title dp-p font-weight-500 dp-mb-10">
            문의유형
          </p>
          <form>
            <base-select
              placeholder="선택해주세요."
              v-model="searchData.qnatype"
              :options="qnatypeOptions"
            />
          </form>
        </div>
        <div v-for="(list, index) in optionList" :key="index" class="dp-mb-30">
          <p v-if="index == 0" class="dp-p font-weight-500 dp-mb-10">
            상품옵션 (선택사항)
          </p>
          <form>
            <base-select
              :placeholder="list.optionname"
              v-model="list.selected"
              :options="list.detail"
              :key="skey"
              @change="selectOption(list, index, list.selected)"
            />
          </form>
        </div>
        <div class="dp-mb-30">
          <p class="dp-p font-weight-500 dp-mb-10">문의내용</p>
          <form>
            <base-textarea
              class="textarea-h-140"
              placeholder="자세한 후기는 다른 고객의 구매에 많은 도움이 되며, 건강기기 등의 효능이나 효과 등의 오해의 소지가 있는 내용 작성 시 검토 후 비공개 조치 될 수 있습니다.
반품/환불 문의는 1:1문의로 가능합니다."
              v-model="searchData.content"
            />
            <base-checkbox
              label="비공개 문의 작성"
              name="privateInquiryCheck"
              :id="privateInquiryCheckbox.id"
              trueValue="T"
              falseValue="F"
              v-model="searchData.issecret"
            />
          </form>
        </div>
        <div class="btn-group">
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            @click="QnaMobileRegister()"
            squared
            >{{ btnText }}</b-button
          >
        </div>
      </div>
    </template>
  </b-modal>
</template>            
            <!-- // 상품문의 작성 Modal -->

<script src="./QnaModal.js"></script>