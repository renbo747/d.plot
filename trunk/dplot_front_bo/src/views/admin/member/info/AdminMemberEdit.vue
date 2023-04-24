<template>
  <div style="margin-top: 20px;">
    <div class="tab-area" style="height: calc(90vh - 266px);">
      <div class="clearfix">
        <div class="bar-title small fl">기본정보 수정</div>
      </div>
      <div class="form-area">
        <table cellpadding="0" cellspacing="0" class="gray-tb">
          <colgroup>
            <col width="170px">
            <col width="394px">
            <col width="170px">
            <col width="394px">
          </colgroup>
          <tbody>
          <tr>
            <th>이름</th>
            <td><input type="text" v-model="editInfo.name" /></td>
            <th>생년월일</th>
            <td>
              <CommonDatePicker :value="editInfo.birthdate" @change="onChangeBirthDate"/>
            </td>
          </tr>
          <tr>
            <th>아이디</th>
            <td>{{ editInfo.userid }}</td>
            <th>비밀번호</th>
            <td><button type="button" class="btn blue-line" @click="initPassword">임시비밀번호 발급</button></td>
          </tr>
          <tr>
            <th>휴대폰번호</th>
            <td>
              <input type="text" v-model="editInfo.mobile">
            </td>
            <th>이메일</th>
            <td><input type="text" v-model="editInfo.email" /></td>
          </tr>
          <tr>
            <th>성별</th>
            <td>
              <div class="radio_wrap wide">
                <input type="radio" name="group00" id="rd01" v-model="editInfo.gender" value="M"/><label for="rd01">남</label>
                <input type="radio" name="group00" id="rd02" v-model="editInfo.gender" value="F"/><label for="rd02">여</label>
              </div>
            </td>
            <th>내/외국인</th>
            <td>
              <div class="radio_wrap wide">
                <input type="radio" name="group01" id="rd11" v-model="editInfo.isdomastic" value="T"/><label for="rd11">내국인</label>
                <input type="radio" name="group01" id="rd12" v-model="editInfo.isdomastic" value="F"/><label for="rd12">외국인</label>
              </div>
            </td>
          </tr>
          <tr>
            <th>회원유형</th>
            <td>
              <select v-model="editInfo.dadamembertype" style="width: 120px;">
                <option v-for="item in commonCode.DADAMEMBERTYPE" v-bind:key="item.cmcode" :value="item.cmcode">{{ item.codename }} </option>
              </select>
              <select v-if="editInfo.dadamembertype === 'DMT003' || editInfo.dadamembertype === 'DMT004' || editInfo.dadamembertype === 'DMT005' " v-model="editInfo.companytype" style="width: 240px;">
                <option v-for="item in commonCode.COMPANYTYPE" v-bind:key="item.cmcode" :value="item.cmcode">{{ item.codename }} </option>
              </select>
            </td>
            <th>회원등급</th>
            <td>
              <select v-model="editInfo.memlvtype">
                <option v-for="item in commonCode.MEMLVTYPE" v-bind:key="item.cmcode" :value="item.cmcode">{{ item.codename }} </option>
              </select>
            </td>
          </tr>
          <tr>
            <th>마케팅 수신 동의여부</th>
            <td>
              <div class="dpb">
                <span class="dpib" style="width: 40px;">이메일</span>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group02" id="rd21" v-model="editInfo.isadmailing" value="T"/><label for="rd21">동의</label>
                  <input type="radio" name="group02" id="rd22" v-model="editInfo.isadmailing" value="F"/><label for="rd22">미동의</label>
                </div>
              </div>
              <div class="dpb">
                <span class="dpib" style="width: 40px;">SMS</span>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group03" id="rd31" v-model="editInfo.isadsms" value="T"/><label for="rd31">동의</label>
                  <input type="radio" name="group03" id="rd32" v-model="editInfo.isadsms" value="F"/><label for="rd32">미동의</label>
                </div>
              </div>
              <div class="dpb">
                <span class="dpib" style="width: 40px;">PUSH</span>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group07" id="rd71" v-model="editInfo.isadpush" value="T"/><label for="rd71">동의</label>
                  <input type="radio" name="group07" id="rd72" v-model="editInfo.isadpush" value="F"/><label for="rd72">미동의</label>
                </div>
              </div>
            </td>
            <th>정보성 메시지 수신여부</th>
            <td>
              <div class="dpb">
                <span class="dpib" style="width: 40px;">이메일</span>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group04" id="rd41" v-model="editInfo.isifmailing" value="T"/><label for="rd41">허용</label>
                  <input type="radio" name="group04" id="rd42" v-model="editInfo.isifmailing" value="F"/><label for="rd42">거부</label>
                </div>
              </div>
              <div class="dpb">
                <span class="dpib" style="width: 40px;">SMS</span>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group05" id="rd51" v-model="editInfo.isifsms" value="T"/><label for="rd51">허용</label>
                  <input type="radio" name="group05" id="rd52" v-model="editInfo.isifsms" value="F"/><label for="rd52">거부</label>
                </div>
              </div>
              <div class="dpb">
                <span class="dpib" style="width: 40px;">PUSH</span>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group06" id="rd61" v-model="editInfo.isifpush" value="T"/><label for="rd61">허용</label>
                  <input type="radio" name="group06" id="rd62" v-model="editInfo.isifpush" value="F"/><label for="rd62">거부</label>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <th>주소(배송지)</th>
            <td colspan="3">
              <button type="button" class="btn blue-line" @click="searchAddress">주소검색</button>
              <input type="text" class="ml3" style="width: 420px;" v-model="editInfo.addr">
              <input type="text" class="ml3" style="width: 420px;" v-model="editInfo.addrdetail">
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="btn-group">
      <button type="button" class="btn big blue" @click="memberInfoUpdate">저장</button>
      <button type="button" class="btn big darkgray" @click="onClose">취소</button>
    </div>

  </div>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker.vue";

export default {
  name: "AdminMemberEdit",
  components: { CommonDatePicker },
  props: ['activeUserNo'],
  data() {
    return {
      editInfo : {},
      originInfo : {},
      commonCode: {
        DADAMEMBERTYPE: [], // 다다픽회원유형
        MEMLVTYPE: [],       // 회원등급
        COMPANYTYPE: [],
      },
      ISADSMS: '',
      ISADMAILING: '',
      ISADPUSH: '',
      ISIFSMS: '',
      ISIFMAILING: '',
      ISIFPUSH: ''
    }
  },
  mounted() {
    const cmclassArr = ['DADAMEMBERTYPE', 'MEMLVTYPE', 'COMPANYTYPE'];
    this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false}).then(result =>{
      this.commonCode.DADAMEMBERTYPE = result.data.dadamembertype;
      this.commonCode.MEMLVTYPE = result.data.memlvtype;
      this.commonCode.COMPANYTYPE = result.data.companytype;

      this.$http.post('/admin/member/info', {userno : this.activeUserNo, isloading : false}).then(result => {
        let jsonStr = JSON.stringify(result.data.info);
        let origin = JSON.parse(jsonStr);

        this.editInfo = result.data.info;
        this.originInfo = origin;

        this.ISADSMS = result.data.info.isadsms;
        this.ISADMAILING = result.data.info.isadmailing;
        this.ISADPUSH = result.data.info.isadpush;
        this.ISIFSMS = result.data.info.isifsms;
        this.ISIFMAILING = result.data.info.isifmailing;
        this.ISIFPUSH = result.data.info.isifpush;

      }).catch(error => {
        this.$util.debug(error);
      })
    }).catch(error => {
      this.$util.debug(error);
    })


  },
  methods : {
    initPassword(){
      let msg = this.editInfo.name + ' 고객님의 등록된 휴대폰으로 임시 비밀번호가\n문자 발송 됩니다. 발송된 임시 비밀번호로 로그인 후\n신규 비밀번호를 입력하셔야 합니다.'
      if(confirm(msg)){
        this.$http.post('/admin/member/init/password', { userno : this.activeUserNo, mobile : this.editInfo.mobile}).then(result => {
          if(result.data.code === 'success') {
            alert('임시 비밀번호가 정상적으로 발송 되었습니다.');
          } else {
            alert('임시 비밀번호 발송에 실패 하였습니다.');
          }
        }).catch(error => {
          this.$util.debug(error);
        })
      }
    },
    onClose(){
      this.$emit('goMemberEdit', false);
    },
    memberInfoUpdate(){

      if(!this.$util.isPhone(this.editInfo.mobile)){
        alert('휴대폰형식이 올바르지 않습니다.');
        return false;
      }

      if(!this.$util.isEmail(this.editInfo.email)){
        alert('이메일 형식이 올바르지 않습니다.');
        return false;
      }

      let msg = '회원 정보를 수정 하시겠습니까?';
      let param = this.editInfo;
      if(confirm(msg)){
        if(this.editInfo.name === this.originInfo.name) this.editInfo.name = '';
        if(this.editInfo.birthdate === this.originInfo.birthdate) this.editInfo.birthdate = '';
        if(this.editInfo.mobile === this.originInfo.mobile) this.editInfo.mobile = '';
        if(this.editInfo.email === this.originInfo.email) this.editInfo.email = '';
        if(this.editInfo.gender === this.originInfo.gender) this.editInfo.gender = '';
        if(this.editInfo.isdomastic === this.originInfo.isdomastic) this.editInfo.isdomastic = '';
        if(this.editInfo.dadamembertype === this.originInfo.dadamembertype) this.editInfo.dadamembertype = '';
        if(this.editInfo.memlvtype === this.originInfo.memlvtype) this.editInfo.memlvtype = '';

        if(this.editInfo.isadsms === this.originInfo.isadsms) this.editInfo.isadsms = '';
        if(this.editInfo.isadmailing === this.originInfo.isadmailing) this.editInfo.isadmailing = '';
        if(this.editInfo.isadpush === this.originInfo.isadpush) this.editInfo.isadpush = '';
        if(this.editInfo.isifsms === this.originInfo.isifsms) this.editInfo.isifsms = '';
        if(this.editInfo.isifmailing === this.originInfo.isifmailing) this.editInfo.isifmailing = '';
        if(this.editInfo.isifpush === this.originInfo.isifpush) this.editInfo.isifpush = '';

        this.$http.post('/admin/member/info/update', param).then(result =>{
          if(result.data.code === 'success'){
            alert('회원 정보 수정이 정상적으로 처리 되었습니다.');
            this.onClose();
          } else {
            alert('회원 정보 수정 처리 중 에러가 발생 하였습니다.\n관리자에게 문의 바랍니다.');
          }
        }).catch(error =>{
          this.$util.debug(error);
        })
      }
    },
    searchAddress() {
      let thisObj = this;
      new window.daum.Postcode({
        oncomplete: (data) => {
          let jibun = data.jibunAddress;
          let road = data.roadAddress;
          thisObj.$set(thisObj.editInfo, 'post', data.zonecode);
          thisObj.$set(thisObj.editInfo, 'addr', (jibun === '') ? data.autoJibunAddress : jibun);
          thisObj.$set(thisObj.editInfo, 'addrroad', (road === '') ? data.autoRoadAddress : road);
          thisObj.$set(thisObj.editInfo, 'sigungucode', data.sigunguCode);
          thisObj.$set(thisObj.editInfo, 'buildingcode', data.buildingCode);
          thisObj.$set(thisObj.editInfo, 'roadnamecode', data.roadnameCode);
        }
      }).open();
    },
    onChangeBirthDate(val){
      this.editInfo.birthdate = val;
    }
  }
}
</script>

<style scoped>

</style>