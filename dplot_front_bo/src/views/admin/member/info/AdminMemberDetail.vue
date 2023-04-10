<template>
  <!-- 회원정보상세 - 기본정보 팝업 -->
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">기본정보</div>
      <div class="fr">
        <button type="button" class="btn blue-line" @click="memberEdit">정보수정</button>
      </div>
    </div>
    <div class="form-area">
      <table cellpadding="0" cellspacing="0" class="gray-tb">
        <colgroup>
          <col width="170px">
          <col width="464px">
          <col width="170px">
          <col width="324px">
        </colgroup>
        <tbody>
        <tr>
          <th>이름</th>
          <td><i :class="info.isblkmember === 'T'"></i>{{ info.name }} <button type="button" v-if="info.isblkmember === 'T'" class="btn blue-line ml3"  @click="deleteBlackMember">블랙해제</button></td>
          <th>생년월일</th>
          <td>{{ info.birthdate }}</td>
        </tr>
        <tr>
          <th>아이디</th>
          <td>{{ info.userid }}</td>
          <th>비밀번호</th>
          <td><button type="button" class="btn blue-line" @click="initPassword">임시비밀번호 발급</button></td>
        </tr>
        <tr>
          <th>휴대폰번호</th>
          <td>{{ $util.phoneNumberFilter(info.mobile) }}</td>
          <th>이메일</th>
          <td>{{ info.email }}</td>
        </tr>
        <tr>
          <th>성별</th>
          <td>{{ info.gendername }}</td>
          <th>내/외국인</th>
          <td>{{ info.isdomasticname }}</td>
        </tr>
        <tr>
          <th>가입채널/경로</th>
          <td><span v-if="info.ismigration === 'T'">[이관회원] </span>{{ info.joinchtypename }} / {{info.jointypename}}</td>
          <th>CI</th>
          <td>{{ info.isconninfo }}</td>
        </tr>
        <tr>
          <th>회원유형</th>
          <td>
            <span v-if="employeesCode.includes(info.dadamembertype) === false">{{ info.dadamembertypename }}</span>
            <span v-if="employeesCode.includes(info.dadamembertype) === true">{{ info.dadamembertypename }}</span>
            <span v-if="employeesCode.includes(info.dadamembertype) === true" class="left-bar">소속회사 : {{ info.companytypename }}</span>
            &nbsp;<button type="button" class="btn blue-line ml3" @click="historyPopupOpen('TYPE')">이력</button>
          </td>
          <th>회원등급</th>
          <td>{{ info.memlvtypename }} <button type="button" class="btn blue-line ml3" @click="historyPopupOpen('LV')">이력</button></td>
        </tr>
        <tr>
          <th>마케팅 수신 동의여부</th>
          <td>
            <span>이메일 : {{ info.isadmailing === 'T' ? '동의' : '거부' }} <button type="button" class="btn blue-line ml3" @click="historyPopupOpen('ADEMAIL')">이력</button></span>
            <span class="left-bar">SMS : {{ info.isadsms === 'T' ? '동의' : '거부' }} <button type="button" class="btn blue-line ml3" @click="historyPopupOpen('ADSMS')">이력</button></span>
            <span class="left-bar">PUSH : {{ info.isadpush === 'T' ? '동의' : '거부' }} <button type="button" class="btn blue-line ml3" @click="historyPopupOpen('ADPUSH')">이력</button></span>
          </td>
          <th>정보성 메시지 수신여부</th>
          <td>
            <span>이메일 : {{ info.isifmailing === 'T' ? '동의' : '거부' }}</span>
            <span class="left-bar">SMS : {{ info.isifsms === 'T' ? '동의' : '거부' }}</span>
            <span class="left-bar">PUSH : {{ info.isifpush === 'T' ? '동의' : '거부' }}</span>
          </td>
        </tr>
        <tr>
          <th>주소(배송지)</th>
          <td colspan="3">
            {{ info.addr }} {{info.addrdetail}}<br>
            <span v-if="info.addrroad != null &&info.addrroad !== ''">[도로명]</span> {{ info.addrroad }} {{info.addrdetailroad}}
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="col2 pd">
      <div class="left">
        <div class="clearfix">
          <div class="bar-title small fl">적립금/D포인트/임직원적립금</div>
          <div class="fr"><a class="link-arrow" style="cursor: pointer;" @click="directMovePage('CMONEY')">바로가기</a></div>
        </div>
        <table class="vertical-tb">
          <colgroup>
            <col width="33.33%">
            <col width="33.33%">
            <col width="33.34%">
          </colgroup>
          <thead>
          <tr>
            <th>보유 적립금</th>
            <th>보유 D포인트</th>
            <th>보유 임직원적립금</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>{{ $util.maskComma(cmoneyInfo.cmoney)}}p</td>
            <td>{{ $util.maskComma(cmoneyInfo.epoint)}}p</td>
            <td>{{ $util.maskComma(cmoneyInfo.empcmoney)}}p</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="right">
        <div class="clearfix">
          <div class="bar-title small fl">쿠폰</div>
          <div class="fr"><a class="link-arrow" style="cursor: pointer;" @click="directMovePage('COUPON')">바로가기</a></div>
        </div>
        <div class="box-with-tb">
          <div class="box">
            <span>총 보유쿠폰</span>
            <p><strong>{{ couponInfo.total }}</strong>개</p>
            </div>
            <div class="side-tb">
              <dl v-if="couponInfo.issue != null">
                <dt>최근 발급쿠폰</dt>
                <dd class="ellipsis">{{ couponInfo.issue.cpnname }}</dd>
                <dd>{{ couponInfo.issue.lastdate }}</dd>
              </dl>
              <dl v-else>
                <dt>최근 발급쿠폰</dt>
                <dd class="ellipsis">최근 발급쿠폰이 존재하지 않습니다.</dd>
                <dd></dd>
              </dl>
              <dl v-if="couponInfo.use != null">
                <dt>최근 사용쿠폰</dt>
                <dd class="ellipsis">{{ couponInfo.use.cpnname }}</dd>
                <dd>{{ couponInfo.use.lastdate }}</dd>
              </dl>
              <dl v-else>
                <dt>최근 사용쿠폰</dt>
                <dd class="ellipsis">최근 사용쿠폰이 존재하지 않습니다.</dd>
                <dd></dd>
              </dl>
            </div>
          </div>
        </div>
      </div>
      <div class="col2 pd mt10">
        <div class="left">
          <div class="clearfix">
            <div class="bar-title small fl">최근주문</div>
            <div class="fr"><a class="link-arrow" style="cursor: pointer;" @click="directMovePage('ORDER')">바로가기</a></div>
          </div>
          <table class="even-tb mb0">
            <colgroup>
              <col width="*">
              <col width="100px">
            </colgroup>
            <tbody v-if="orderList.length > 0">
            <tr v-for="(item, index) in orderList" :key="index">
              <td class="ellipsis">[{{item.ordno}}] {{item.goodsname}}</td>
              <td class="date">{{ item.orderdate }}</td>
            </tr>
            </tbody>
            <tbody v-else>
            <tr>
              <td colspan="2" class="empty-info">최근 주문 내역이 없습니다.</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="right">
          <div class="clearfix">
            <div class="bar-title small fl">1:1문의</div>
            <div class="fr"><a class="link-arrow" style="cursor: pointer;" @click="directMovePage('QUESTION')">바로가기</a></div>
          </div>
          <table class="even-tb mb0">
            <colgroup>
              <col width="*">
              <col width="100px">
            </colgroup>
            <tbody v-if="inquiryList.length > 0">
            <tr v-for="item in inquiryList" :key="item.idx">
              <td class="ellipsis" v-html="item.subject"></td>
              <td class="date">{{ item.regdate }}</td>
            </tr>
            </tbody>
            <tbody v-else>
              <tr>
                <td colspan="2" class="empty-info">1:1 문의 내역이 없습니다.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
</template>

<script>
import CommonMemberInfoHistory from "@views.admin/common/popup/CommonMemberInfoHistory";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";

export default {
  name: "AdminMemberDetail",
  comments: {CommonMemberInfoHistory, CommonDatePicker},
  props: ['activeUserNo'],
  data() {
    return {
      info: {},
      inquiryList : [],
      orderList : [],
      cmoneyInfo : {},
      couponInfo : {},
      employeesCode : ['DMT003', 'DMT004', 'DMT005'],
      showPopup: false,
      popupType: '',
    }
  },
  mounted() {
    this.$http.post('/admin/member/detail', {userno : this.activeUserNo}).then(result => {
      this.info = result.data.info;
      this.inquiryList = result.data.inquiry;
      this.cmoneyInfo = result.data.cmoneyinfo;
      this.couponInfo = result.data.couponinfo;
      this.orderList = result.data.orderlist;
      this.$util.debug(result);
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {
    initPassword(){
      let msg = this.info.name + ' 고객님의 등록된 휴대폰으로 임시 비밀번호가\n문자 발송 됩니다. 발송된 임시 비밀번호로 로그인 후\n신규 비밀번호를 입력하셔야 합니다.'

      if(confirm(msg)){
        this.$http.post('/admin/member/init/password', { userno : this.activeUserNo, mobile : this.info.mobile}).then(result => {
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
    historyPopupOpen(type) {
      let param = { userno: this.activeUserNo, type : type };
      this.$eventBus.$emit('modalShow', CommonMemberInfoHistory, param);
    },
    memberEdit(){
      this.$emit('goMemberEdit', true);
    },
    directMovePage(type){
      this.$emit('goDirectPage', type);
    },
    deleteBlackMember(){
      if(confirm('블랙해제처리를 하시겠습니까?')) {
        this.$http.post('/admin/member/black/update', {isblkmember : 'F', userno : this.activeUserNo}).then(result => {
          if(result.data.updatecnt > 0){
            this.info.isblkmember = 'F';
            alert('블랙회원이 해제 되었습니다.');
          }
        }).catch(error => {
          this.$util.debug(error);
        })
      }
    }
  }
}
</script>
<style scoped/>