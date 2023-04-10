<template>
  <ul class="base-pagination" v-if="listTotal > 0 && setMaxPage > 1">
    <li @click="changePage((currentPage - 1) > 1 ? currentPage - 1 : 1)">
      <i class="dp-icon icon-pagination-prev"></i>
    </li>
    <li v-for="num in setPagingList" :key="num">
      <span class="page-item" :class="{ active: currentPage === num }" @click="changePage(num)">
        <span>{{ num }}</span>
      </span>
    </li>
    <li @click="changePage((currentPage + 1) < setMaxPage ? currentPage + 1 :  setMaxPage)">
      <i class="dp-icon icon-pagination-next"></i>
    </li>
  </ul>
</template>

<script>
export default {
  props: {
    currentPage: { //현재 페이지
      type: Number,
      default: 0,
    },
    listTotal: {   //조회 목록 전체 수
      type: Number,
      default: 0,
    },
    listCnt: {     //페이지 목록 수
      type: Number,
      default: 10
    },
    totalPage: {   // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)
      type: Number,
      default: 5
    }
  },
  methods: {
    /**********************
     * 페이지 변경
     *********************/
    changePage(page) {
      this.$util.debug("changePage()...::" +page);
       this.$util.debug("setMaxPage()...::" +this.setMaxPage);
      if (this.currentPage !== page) {
        this.$emit('changePage', page);  
      }
    },
  },
  mounted() {
    this.$util.debug("this.currentPage::"+this.currentPage);
    this.$util.debug("this.listTotal::"+this.listTotal);
    this.$util.debug("this.listCnt::"+this.listCnt);
    this.$util.debug("this.totalPage::"+this.totalPage);
  },
  computed: {
    /********************
     * 전체 페이지 구하기
     ********************/
    setMaxPage() {
      return Math.ceil(this.listTotal / this.listCnt);
    },
    /********************
     * 시작 페이지 구하기
     ********************/
    setStartPage() {
      return (Math.trunc((this.currentPage - 1) / this.totalPage) * this.totalPage) + 1;
    },
    /********************
     * 종료 페이지 구하기
     ********************/
    setEndPage() { // 페이지 끝 번호
      let end = this.setStartPage + this.totalPage - 1
      return end < this.setMaxPage ? end : this.setMaxPage
    },
    /********************
     * 페이지 배열 구하기
     ********************/
    setPagingList() {
      this.$util.debug("setPagingList()");
      this.$util.debug("this.setStartPage::" +this.setStartPage);
      this.$util.debug("this.setEndPage::"   +this.setEndPage);
      
      let list = []
      for(let num = this.setStartPage; num <= this.setEndPage; num++) {
        list.push(num)
      }
      return list;
    },
  }
};
</script>
