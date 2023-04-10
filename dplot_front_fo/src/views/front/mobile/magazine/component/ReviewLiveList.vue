<template>
  <div class="review-live">
    <h2 class="review-title">리뷰LIVE</h2>
    <div class="review-live__body">
      <ul class="review-live__ul list-style-none">
        <li v-for="(list, index) in liveReviewList" :key="index">
          <figure class="review-img"  @click="openModal('photoReviewModal', {photoList:liveFileTopList,index:index, reviewidx:list.reviewidx})" :height="index==0?'222':'109'">
            <img :src="list.files[0].filetype == 'FLT001'?list.files[0].fullpath:$util.changeFileType(list.files[0].fullpath, '.jpg')" alt="리뷰이미지" >
            <i
              v-if="list.files[0].filetype === 'FLT002'"
              class="dp-icon icon-video-play h30"
            ></i>
          </figure>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import PhotoReview from "@views.mobile/shop/popup/PhotoReview.vue";
import PhotoReviewPc from "@views.pc/shop/popup/PhotoReviewPc.vue";

export default {
  data() {
    return {
      pagingData: {
        currentPage: 1, // 현재 페이지
        listTotal: 0, // 조회목록 전체 수
        listCnt: 5, // 한페이지에 노출할 목록수
      },
      liveReviewList: [],
      liveFileTopList: [],
    };
  },
  methods: {
    getReviewLiveList() {
      let param = {
        issearch: "T", //조회 조건이있는지
        isfile: "T", //파일첨부가있는지
        point: 3, //리뷰 점수
      };
      param = Object.assign(param, this.pagingData);
      this.$http.post("/review/mzLiveReview", param).then((result) => {
        if (result.statusCode === 200) {
          this.$util.scrollToTop();

          this.liveReviewList = result.data.reviewlist;
          this.liveFileTopList = result.data.fstfilelist;
          this.pagingData.listTotal = result.data.totalcnt;
        }
      });
    },
    /*****************************
     *모달 팝업 오픈
     ****************************/
    openModal(modalId, param) {
      if (window.sessionStorage.getItem('platform') == "MAC001") {
        this.$eventBus.$emit('showModal', PhotoReviewPc, modalId +"Pc", param);
      }else{
        this.$eventBus.$emit('showModal', PhotoReview, modalId, param);
      }
      
    }
  },
  mounted() {
    this.getReviewLiveList();
  },
};
</script>
<style></style>