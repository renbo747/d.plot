<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">관리자 메모</div>
    </div>

    <div class="memo-write">
      <textarea v-model="memo" placeholder="메모를 작성해 주세요!"></textarea>
      <button type="button" class="btn big blue" @click="insertMemberMemo">메모<br>저장</button>
    </div>
    <div class="scroll-y" style="width: 100%; max-height: 550px; margin-bottom: 0;">
      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
        <colgroup>
          <col width="5%" /><!-- No -->
          <col width="15%" /><!-- 작성일시 -->
          <col width="13%" /><!-- 작성자 -->
          <col width="" /><!-- 상품명 -->
          <col width="40px" /><!-- 삭제 -->
        </colgroup>
        <thead>
        <tr>
          <th>No</th>
          <th>작성일시
            <button type="button"
                    :value="sortData.regdate"
                    class="sort"
                    :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                    @click="sortToggle(sortData.regdate)">
            </button>
          </th>
          <th>작성자</th>
          <th colspan="2">내용</th>
        </tr>
        </thead>
        <tbody v-if="memoList.length > 0">
        <tr v-for="(item, index) in memoList" :key="item.idx">
          <td>{{ index + 1}}</td>
          <td>{{ item.regdate }}</td>
          <td>{{ item.regname }}</td>
          <td class="left">{{ item.memo }}</td>
          <td class="no-left"><button type="button" class="del mg0" @click="deleteMemberMemo(item)"></button></td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="5" class="empty-info">메모 작성 내역이 없습니다.</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminMemberMemoList",
  props: ['activeUserNo'],
  data() {
    return {
      memo: '',
      memoList: [],
      sortData: {
        regdate: 'regdate_desc',
      }
    }
  },
  mounted() {
    this.onSearch();
  },
  methods : {
    onSearch(){
      this.memo = '';

      this.$http.post('/admin/member/memo/list', {userno : this.activeUserNo}).then(result => {
        this.$util.debug(result);
        this.memoList = result.data.list;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    insertMemberMemo(){
      if(this.$util.isNull(this.memo)) {
        alert("메모를 입력해주세요.");
        return;
      }
      this.$http.post('/admin/member/memo/insert', {userno : this.activeUserNo, memo : this.memo}).then(result => {
        if(result.data.code === 'success') {
          this.onSearch();
        } else {
          alert('처리중 에러가 발생 하였습니다.\n관리자에게 문의 바랍니다.');
        }
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    deleteMemberMemo(obj){
      this.$http.post('/admin/member/memo/delete', {idx : obj.idx}).then(result => {
        if(result.data.code === 'success') {
          this.onSearch();
        } else {
          alert('처리중 에러가 발생 하였습니다.\n관리자에게 문의 바랍니다.');
        }
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    // 정렬조건으로 검색
    sortToggle (key) {
        let arr = key.split('_');
        let sortKey = arr[0];
        let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
        let sortName = sortKey + '_' + sortOrder;

        this.sortData = this.$options.data().sortData;

        this.sortData[sortKey] = sortName;

        this.memoList.sort((a,b) =>{
          if(sortOrder === 'asc') {
            if(a[sortKey] > b[sortKey]) {
              return 1;
            } else {
              return -1;
            } 
          } else {
            if(a[sortKey] > b[sortKey]) {
              return -1;
            } else { 
              return 1;
            }
          }
        })
    },
  }
}
</script>

<style scoped>

</style>