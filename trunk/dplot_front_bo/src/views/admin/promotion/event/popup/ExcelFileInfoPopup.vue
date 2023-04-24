<template>
  <!-- 엑셀파일 정보 구성 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 600px;">
      <div class="pop-header">
        <h2>엑셀파일 정보 구성(관리자 제외)</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="check-field col3">
          <ul class="clearfix">
            <li><input type="checkbox" id="chk001" value="mobile,휴대폰번호"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label for="chk001">휴대폰번호</label>
            </li>
            <li><input type="checkbox" id="chk002" value="couponcount,보유쿠폰수"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label for="chk002">보유쿠폰</label>
            </li>
            <li><input type="checkbox" id="chk003" value="lastlogindate,최근접속일시"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk003">최근접속일시</label>
            </li>
            <li><input type="checkbox" id="chk004" value="email,이메일"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk004">이메일</label>
            </li>
            <li><input type="checkbox" id="chk005" value="totpaypoint,총적립금"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk005">총 적립금</label>
            </li>
            <li><input type="checkbox" id="chk006" value="lastbuydate,최근구매일시"
                       @click="clickInput($event.target.value, $event.target.checked)"><label
                for="chk006">최근구매일시</label>
            </li>
            <li><input type="checkbox" id="chk007" value="gender,성별"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk007">성별</label>
            </li>
            <li><input type="checkbox" id="chk005" value="paypoint,적립금"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk005">적립금</label>
            </li>
            <li><input type="checkbox" id="chk009" value="frstbuydate,첫구매일자"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label for="chk009">첫 구매일자</label>
            </li>
            <li><input type="checkbox" id="chk010" value="birthdate,생년월일"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk010">생년월일</label>
            </li>
            <li><input type="checkbox" id="chk008" value="epoint,e포인트"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk008">D포인트</label>
            </li>
            <li><input type="checkbox" id="chk012" value="memregdate,가입일자"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk012">가입일자</label>
            </li>
            <li><input type="checkbox" id="chk013" value="address,배송지 주소"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk013">배송지 주소</label>
            </li>
            <li><input type="checkbox" id="chk011" value="payemploypoint,임직원적립금"
                       @click="clickInput($event.target.value, $event.target.checked)">
              <label
                  for="chk011">임직원적립금</label>
            </li>
          </ul>
        </div>
        <div class="btn-group mt10">
          <button type="button" class="btn big blue" @click="goDownExcel">선택 대상 적용</button>
          <button type="button" class="btn big darkgray" @click="$emit('close')">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /엑셀파일 정보 구성 팝업 -->
</template>

<script>
export default {
  name: "ExcelFileInfoPopup",
  props: {
    eventIdx: Number,
    path: String,
  },
  data() {
    return {
      columnList: [],
      headerList: [],
    }
  },
  methods: {
    goDownExcel() {
      let param = {
        columnlist: this.columnList,
        headerlist: this.headerList,
        eventidx: this.eventIdx
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post(this.path, param, postConfig)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    clickInput(value, isCheck) {
      let obj = value.split(',');
      if (isCheck) { // 체크
        this.columnList.push(obj[0]);
        this.headerList.push(obj[1]);
      } else { // 체크 해제
        this.$util.removeArrOnce(this.columnList, obj[0]);
        this.$util.removeArrOnce(this.headerList, obj[1]);
      }
    }
  }
}
</script>
