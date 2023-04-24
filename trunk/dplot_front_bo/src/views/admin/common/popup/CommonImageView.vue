<template>
  <!-- 첨부사진 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 600px;">
      <div class="pop-header">
        <h2>첨부 사진</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="slide-area mt20">
          <div class="slider" style="width: 100%;">
            <ul>
              <li><img :src="imgPath" style="width: 100%;"/></li>
            </ul>
          </div>
        </div>
        <div class="btn-group mt20">
          <button type="button" class="btn darkgray" @click="$emit('close')">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /첨부사진 팝업 -->
</template>

<script>
export default {
  name: "PictureViewPopup",
  props: {
    imgPath: String
  }
}
</script>

<style scoped>

</style>
