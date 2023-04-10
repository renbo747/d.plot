<!-- 파트너사 동의현황 팝업 -->
<template>
  <!-- 동의완료 파트너사목록 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 500px;">
      <div class="pop-header">
        <h2 v-if="isAgree === 'T'">동의 완료 파트너사 목록</h2>
        <h2 v-else>미 동의 완료 파트너사 목록</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="bar-title">{{ subject }}</div>
        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
          <caption v-if="isAgree === 'T'">동의 완료 파트너사 목록</caption>
          <caption v-else>미 동의 완료 파트너사 목록</caption>
          <colgroup>
            <col width="10%"/><!-- No -->
            <col width="25%"/><!-- 아이디 -->
            <col width="25%"/><!-- 업체명 -->
            <col v-if="isAgree === 'T'" width="40%"/><!-- 동의일시 -->
          </colgroup>
          <thead>
          <tr>
            <th>No</th>
            <th>아이디
              <button type="button"
                      :value="sortData.userid"
                      class="sort"
                      :class="[{up : sortData.userid === 'userid_asc'}, {down : sortData.userid === 'userid_desc'}]"
                      @click="sortToggle(sortData.userid)">
              </button>
            </th>
            <th>업체명
              <button type="button"
                      :value="sortData.name"
                      class="sort"
                      :class="[{up : sortData.name === 'name_asc'}, {down : sortData.writer === 'name_desc'}]"
                      @click="sortToggle(sortData.name)">
              </button>
            </th>
            <th v-if="isAgree === 'T'">동의일시</th>
          </tr>
          </thead>
          <tbody v-for="(row,  i) in boardList" :key="i">
          <tr>
            <td>{{ row.rn }}</td>
            <td>{{ row.userid }}</td>
            <td>{{ row.name }}</td>
            <td v-if="isAgree === 'T'">{{ row.agreedate }}</td>
          </tr>
          </tbody>
        </table>
        <div class="bottom-group">
          <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
        </div>
      </div>
    </div>
  </div>
  <!-- /동의완료 파트너사목록 팝업-->
</template>

<script>


import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";

export default {
  name: "PartnersAgreeListPopup",
  components: {
    CommonPageNavigator
  },
  data() {
    return {
      subject: "",
      isAgree: "",
      idx: "",
      boardList: {},
      searchData: {},
      pagingData: {
        pageCount: 10,                  // 페이징 옵션 (최대수)
        page: 1,                        // 현재 페이지
        listCount: 0                    // 총 페이지
      },
      sortData: {
        userid: 'userid_desc',          // 작성자
        name: 'name_desc',    // 시작일시
      },
    }
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    onOpen(idx, isagree) {
      this.pagingData.listCount = 0;
      this.pagingData.page = 1;
      this.pagingData.pageCount = 10;
      this.searchData = {};
      this.sortData = this.$options.data().sortData;
      this.isAgree = isagree;
      this.idx = idx;
      this.onSearch();
    },

    onSearch() {
      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;
      param.idx = this.idx;
      param.isagree = this.isAgree;

      // 데이터 통신
      this.$http.post("/admin/partners/popup/agree/search", param)
          .then(result => {
            let data = result.data;
            this.boardList = data.list;
            this.pagingData.listCount = data.listCount;
            this.subject = this.boardList[0].subject;
          })
          .catch(error => {
            this.$util.debug(error);
          })
    },

    onClose() {
      this.$parent.goCloseAgreePopup();
      this.boardList = {};
    },

    // 테이블 소트
    sortToggle(key) {
      let arr = key.split("_");
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData = this.$options.data().sortData;

      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.onSearch();
    },
    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    // 페이징 콜백 함수
    setPagingData(param) {
      this.pagingData = param;
      this.onSearch();
    },
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    /////////////////////////////////////////////////////////////////

  },
  mounted() {
  }

}
</script>
