<template>
  <!-- 신고하기 Modal -->
  <b-modal
    id="postReportModal"
    modal-class="dp-modal page-layer post-report-modal"
    centered
    scrollable
    :hide-footer="true"
  >
    <!-- Post Report Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">해당댓글 신고하기</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Post Report Modal Body -->
    <template>
      <div class="page-layer__body">
        <div class="dp-mb-30">
          <p class="post-report__title dp-p font-weight-500 dp-mb-10">
            신고사유 (택1)
          </p>
          <form>
            <ul class="post-report__radio list-style-none">
              <li
                v-for="(list, index) in commonCodeList"
                class="dp-mb-10"
                :key="index"
              >
                <base-radio
                  name="post-report"
                  :id="list.cmcode"
                  :label="list.codename"
                  v-model="reportRadio"
                  :val="list.cmcode"
                  @change="changePostReportData(list.cmcode)"
                />
              </li>
            </ul>
          </form>
        </div>
        <div class="dp-mb-30" v-if="reportRadio === 'CRT008'">
          <p class="dp-p font-weight-500 dp-mb-10">신고내용</p>
          <form>
            <base-textarea
              class="dp-mb-30 textarea-h-140"
              v-model="etcreason"
              placeholder="자세한 신고내용을 적어주세요. 최소 10자 이상 적어주세요."
            />
          </form>
        </div>
        <div class="btn-group">
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            squared
            @click="saveReport()"
            >등록 완료</b-button
          >
        </div>
      </div>
    </template>
  </b-modal>
  <!-- // 신고하기 Modal -->
</template>

<script src="./PostReport.js"></script>
<style></style>