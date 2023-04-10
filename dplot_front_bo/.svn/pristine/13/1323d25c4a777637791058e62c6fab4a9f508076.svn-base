<template>
  <div class="paging" v-if="childPagingData.listCount > 0">
    <a href="javascript:void(0);" @click="goPage(childPagingData.page - 1)" class="arrow left"></a>
    <a href="javascript:void(0);" v-for="page in pageData" v-bind:key="page.page" @click="goPage(page.page)" :class="{active: page.isActive}">{{page.page}}</a>
    <a href="javascript:void(0);" @click="goPage(childPagingData.page + 1)" class="arrow right"></a>
  </div>
</template>

<script>
export default {
  name: 'CommonPageNavigator',
  props: {
    pagingData : Object
  },
  data() {
    return {
      childPagingData : this.pagingData,
      pageData : [],
    }
  },
  methods: {
    goPage : function(page){
      if(page < 1) {
        page = 1;
      }
      
      let lastPage = Math.ceil(this.childPagingData.listCount / this.childPagingData.pageCount);
      if(page > lastPage) {
        page = lastPage;
      }

      this.childPagingData.page = page;
      this.changePageData();
      this.$emit('setPagingData', this.childPagingData);
    },
    changePageData : function() {
      // 41 / 10 = 4.1 이므로 for 문에서 "나머지가 있을 경우 자동으로 +1 만큼" 반복됨
      // 단, lastPage가 소수점으로 입력되므로 자리 올림 필요
      let pageCnt = this.childPagingData.listCount / this.childPagingData.pageCount;
      // 10개 단위로 페이징 자르기
      if(this.childPagingData.page <= 0){
        this.childPagingData.page = 1;
      }

      let splitPage = Math.floor((this.childPagingData.page -1 ) / 10);

      // 현재 위치가 마지막 분할범위인 경우 pageCnt까지만 보여주기
      if(Math.ceil(pageCnt) > (splitPage + 1) * 10) {
        pageCnt = (splitPage + 1) * 10;
      }

      let data = [];
      for(let i= splitPage * 10; i<pageCnt; i++){
        data.push({page : i+1, isActive : (this.childPagingData.page === (i+1)) });
      }
      this.pageData = data;
      // this.lastPage = Math.ceil(pageCnt);
    },
    pagingActiveChnage: function(){
      let maxListCount = parseInt(this.childPagingData.listCount) + parseInt(this.childPagingData.pageCount);
      let pageMaxCount = parseInt(this.childPagingData.pageCount) * parseInt(this.childPagingData.page);

      if(pageMaxCount >= maxListCount){
        this.childPagingData.page = Math.ceil(parseInt(this.childPagingData.listCount) / parseInt(this.childPagingData.pageCount));
      }
      this.$emit('setPagingData', this.childPagingData);
    }
  },
  watch: {
    'childPagingData.listCount': function () {
      // 검색 조건에 의해 수량이 변경 되었을 때 현재 페이지 값이2 일 경우(변경된 리스트의 최대 페이징 횟수 보다 클 경우) 리스트 갯수에 의한 오류 발생하여 초기화 해줌
      // * 검색을 했는데 이전과 수량이 동일할 경우 초기화가 되지않음 수정 필요 *
      this.changePageData();
      this.pagingActiveChnage();
    },
    'childPagingData.pageCount': function () {
      // 총 페이지 수량(10개씩 보기, 20개씩 보기)을 변경할 때 현재 페이지 값이2 일 경우(변경된 리스트의 최대 페이징 횟수 보다 클 경우) 리스트 갯수에 의한 오류 발생하여 초기화 해줌
      // 총 페이지 수량 변경시 재 검색 추가
      // this.childPagingData.page = 1;
      this.changePageData();
      this.pagingActiveChnage();
    },
    'childPagingData.page': function () {
      this.changePageData();
    }
  }
}
</script>
