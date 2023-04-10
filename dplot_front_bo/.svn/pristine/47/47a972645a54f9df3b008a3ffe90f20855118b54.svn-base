<template>
  <!-- 파트너사 선택 컴포넌트 -->
  <div id="modal-wrap" class="modal" style="display: block; z-index: 1000">
    <div class="modal-content" style="width: 400px;">
      <div class="pop-header">
        <h2>파트너사 선택</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div>
          <input type="search" style="width: 239px;" v-model="searchInput" placeholder="검색어를 입력하세요.">
          <button type="button" class="btn-search" style="margin-right: 4px;">검색</button>
          <label for="input-file" class="btn green-line">엑셀일괄등록</label>
          <input type="file" multiple="multiple" id="input-file" style="display: none" @change="readFile" ref="files"
                 accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <div class="scroll-y" style="max-height: 300px">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <caption>검색결과</caption>
            <colgroup>
              <col width="12%"/><!-- checkbox -->
              <col width="44%"/><!-- 아이디 -->
              <col width="44%"/><!-- 업체명 -->
              <col width="0%"/><!-- 유저 번호 -->
            </colgroup>
            <thead>
            <tr>
              <th><input type="checkbox" id="chkall" v-model="isCheckAll" @click="onCheckAll($event.target.checked)"/>
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
            </tr>
            </thead>
            <tbody>
            <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
            <tr v-for="(row, i) in searchListData" :key="i">
              <td><input type="checkbox" v-model="checkListData" :value="row | numberFilter" :id="'chk'+i" @change="onCheck"/></td>
              <td>{{ row.userid }}</td>
              <td>{{ row.name }}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="btn-group">
          <button type="button" class="btn blue" @click="onSelectSubmit">적용</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /파트너사 선택 컴포넌트 -->
</template>

<script>
import XLSX from 'xlsx'

export default {
  name: "PartnersListPopup",
  props: {
    checkPartenrs: Array,
    boardIdx: {
        type: Number,
        default: 0,
    },
  },
  comments: {},
  filters: {
    numberFilter(value) {
      value.no = value.no.toString();
      return value;
    }
  },
  data() {
    return {
      // 전체 파트너사 저장 객체
      totalListData: [],
      // 체크된 파트너사 저장 객체
      checkListData: [],
      // db에 저장된 파트너사 저장 객체
      dbCheckList: [],
      // 전체 선택 상태
      isCheckAll: false,
      // 검색 데이터
      searchInput: "",
      // 파일
      file: null,
      // 소트 데이터
      sortData: {
        userid: 'userid_desc',          // 작성자
        name: 'name_desc',    // 시작일시
      },
      searchData: {},
      idx: "",
    }
  },
  computed: {
    searchListData: function () {
      let thisObj = this;
      return this.totalListData.filter(m => m.name.includes(thisObj.searchInput) || m.userid.includes(thisObj.searchInput));
    }
  },
  mounted() {
    this.$set(this,'checkListData', this.checkPartenrs);
    if(this.boardIdx !== 0) {
      this.searchCheckList(this.boardIdx);
    }
    this.onInit();
  },
  methods: {

    // 엑셀 파일 읽기
    readFile(event) {
      if (event.target.files.length === 0) {
        return;
      }

      const file = event.target.files[0];
      let reader = new FileReader();
      let tmpResult = {};
      reader.onload = (e) => {
        let data = reader.result;
        let workbook = XLSX.read(data, {type: 'binary'});
        workbook.SheetNames.forEach(sheetName => {
          const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
          tmpResult = roa;
        });
        // excelJsonData = tmpResult;
        this.excelData(tmpResult);
      };
      reader.readAsArrayBuffer(file);

      this.$refs.files.value = null;
      this.file = null;
    },

    // 엑셀일괄 업로드
    excelData(excelJsonData) {
      let excelObj = [];
      let thisObj = this;

      // 받아온 excel Data 파싱해서 원하는 데이터로 변경
      excelJsonData.forEach(obj => {
        let temp = {};
        if (Object.prototype.hasOwnProperty.call(obj, 'userid')) {
          temp.userid = obj.userid;
        }

        if (Object.prototype.hasOwnProperty.call(obj, 'name')) {
          temp.name = obj.name;
        }

        if (Object.prototype.hasOwnProperty.call(obj, '아이디')) {
          temp.userid = obj.아이디;
        }

        if (Object.prototype.hasOwnProperty.call(obj, '업체명')) {
          temp.name = obj.업체명;
        }

        excelObj.push(temp);
      })

      // 객체 비교를 위한 str
      let listArr = JSON.parse(JSON.stringify(this.checkListData));

      // excelObj를 돌면서 checkList에 없는 데이터만 판별해서 push
      let notExistPartners = [];
      for (let cel of excelObj) {
        let check = true;
        listArr.forEach(item => {
          if(item.userid === cel.userid) {
            check = false;
          }
        })
        if (check) {
          notExistPartners.push(cel.userid);
        }
      }

      this.totalListData.forEach(item => {
        notExistPartners.forEach(temp => {
          if(item.userid === temp) {
            this.checkListData.push(item);
          }
        })
      })
    },

    // 전체 체크
    onCheckAll(checked) {
      if (checked) { // 전체 체크
        this.isCheckAll = checked;
        this.checkListData = this.totalListData;
      } else { // 전체 체크 해제
        this.isCheckAll = checked
        this.checkListData = [];
      }
    },

    onCheck() {
      if(this.totalListData.length > this.checkListData.length) {
        this.isCheckAll = false;
      } else {
        this.isCheckAll = true;
      }
    },

    // 닫기
    onClose() {
      this.$parent.goClosePartnersPopup();
      this.checkListData = [];
      this.dbCheckList = [];
      this.isCheckAll = false;
      this.idx = "";
    },

    // 초기화
    onInit() {
      let params = this.searchData;
      this.$http.post('/admin/partners/popup/select/search', params)
          .then(result => {
            this.totalListData = result.data.list;
            this.totalListData.forEach(item => {
              delete item.commrate;
            })
          })
          .catch(error => {
            this.$util.debug(error);
          })
    },

    // 적용 버튼
    onSelectSubmit() {

      let deleteList = [];
      let addList = [];

      // 삭제
      for (let i = 0; i < this.dbCheckList.length; i++) {
        let db = JSON.stringify(this.dbCheckList[i]);
        let isSame = false;
        for (let j = 0; j < this.checkListData.length; j++) {
          let ck = JSON.stringify(this.checkListData[j]);
          if (db === ck) {
            isSame = true;
            break;
          }
        }

        if (!isSame) {
          deleteList.push(JSON.parse(db));
        }
      }

      // 삭제
      for (let i = 0; i < this.checkListData.length; i++) {
        let ck = JSON.stringify(this.checkListData[i]);
        let isSame = false;
        for (let j = 0; j < this.dbCheckList.length; j++) {
          let db = JSON.stringify(this.dbCheckList[j]);
          if (db === ck) {
            isSame = true;
            break;
          }
        }

        if (!isSame) {
          addList.push(JSON.parse(ck));
        }
      }

      let crudList = {
        'delete': deleteList,
        'add': addList,
        'check': this.checkListData
      }

      this.$emit("getPartnersList", crudList);
      this.checkListData = [];
      this.dbCheckList = [];
      this.isCheckAll = false;
      this.idx = "";
      this.$emit("closePartnersPopup");
      // this.$parent.goClosePartnersPopup();
    },

    // 체크 데이터 조회
    searchCheckList(boardPostIdx) {
      if (this.idx === "") {
        this.idx = boardPostIdx;
      }

      let params = {
        boardidx: this.idx
      };

      this.$http.post('/admin/partners/popup/select/check', params)
          .then(result => {
            let data = result.data;

            // noList에 0인 데이터가 있으면 없는 것
            if (data.nolist[0] === 0) {
              return;
            }

            // 데이터가 있다면 dbCheckList에 저장
            for (let i = 0; i < data.nolist.length; i++) {
              let list = {
                no: data.nolist[i],
                name: data.namelist[i],
                userid: data.useridlist[i]
              }

              this.dbCheckList.push(list);
            } 

            let thisObj = this;
            this.checkListData.forEach(item => {
              let isExist = thisObj.totalListData.find(element => element.no === item.no);
              if(typeof isExist === 'undefined') {
                let jsonStr = JSON.stringify(item);
                let json = JSON.parse(jsonStr);
                thisObj.totalListData.push(json);
              }
            });
          })
          .catch(error => {
            this.$util.debug(error);
          })
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

      this.onInit();
    },
    /////////////////////////////////////////////////////////////////
  }
}
</script>
