<template>
  <div class="container">
    <div class="mb-4 main-popup01">
      <b-button
        class="dp-btn text-white main-popup-btn"
        variant="gray-800"
        v-b-modal.mainPopUpModal01
        >Popup main 01</b-button
      >

      <!-- Main PopUp Modal01 Modal -->
      <b-modal
        id="mainPopUpModal01"
        modal-class="dp-modal pop-over none-title main-popup-modal01"
        centered
        hide-header
        hide-footer
      >
        <div class="modal-body-popup">
          <div class="modal-popup-header">
            <i
              class="dp-icon modal-close"
              block
              @click="$bvModal.hide('mainPopUpModal01')"
            >
              <img
                src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                alt="닫기"
              />
            </i>
          </div>
          <swiper class="swiper" :option="modalOption">
            <swiper-slide v-for="(list, index) in thumbnailData" :key="index">
              <figure class="popup-thumbnail mb-0">
                <img
                  class="popup-thumbnail__image"
                  :src="list.fullpath"
                  :alt="list.subject"
                />
              </figure>
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
          <footer class="popup-modal-footer">
            <base-checkbox
              label="오늘 하루 보지 않기"
              v-model="sampleCheckbox.id"
              :id="sampleCheckbox.id"
              name="sampleCheckbox"
              :is-checked="sampleCheckbox.checked"
              @change="sampleCheckbox.checked = !sampleCheckbox.checked"
            />
          </footer>
        </div>
      </b-modal>
      <!-- // Main PopUp Modal01 Modal -->
    </div>
  </div>
</template>

<script srv="./PopUp.js"></script>
