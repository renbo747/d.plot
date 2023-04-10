<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1200px;">
      <div class="pop-header">
        <h2>파트너사 상세정보</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="clearfix">
          <div class="btn-group fr" v-if="viewType === 'APPLY' && isAdmin">
            <button type="button" class="btn big blue" v-if="isWrite" @click="updateDetail('RDS002')">입점승인</button>
            <button type="button" class="btn big red" v-if="isWrite" @click="updateDetail('RDS003')">반려</button>
<!--            <button type="button" class="btn big blue">수정완료</button>-->
            <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
          </div>
          <div class="btn-group fr" v-if="viewType === 'SAVE'">
            <button type="button" class="btn big blue" v-if="isWrite" @click="updateDetail(info.reqdealst)">저장</button>
            <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
          </div>
        </div>
        <div class="boxing mt10">
          <div class="boxing-title" :class="{ closed : !tabs.tab1 }">기본 정보<i class="arrcodi" @click="toggleTab('tab1')"></i></div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab1 === true"><!-- 닫은 상태일때 display:none -->
            <dl>
              <dt>아이디</dt>
              <dd><p>{{ info.userid }}</p></dd>
            </dl>
            <dl>
              <dt>신청상태</dt>
              <dd><p>{{ info.reqdealstname }}</p></dd>
            </dl>
            <dl v-if="isWrite">
              <dt>비밀번호</dt>
              <dd>
                <button type="button" class="btn blue-line" @click="initPassword">비밀번호 초기화</button>
              </dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <div class="boxing-title" :class="{ closed : !tabs.tab2 }">사업자 정보<i class="arrcodi" @click="toggleTab('tab2')"></i></div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab2 === true"><!-- 닫은 상태일때 display:none -->
            <dl>
              <dt>사업자등록번호</dt>
              <dd>
                <input type="text" :value="$util.bizNumberFilter(info.bizno, 1)" style="width: 68px;" disabled><span>-</span>
                <input type="text" :value="$util.bizNumberFilter(info.bizno, 2)" style="width: 62px;" disabled><span>-</span>
                <input type="text" :value="$util.bizNumberFilter(info.bizno, 3)" style="width: 97px;" disabled>
                <button type="button" class="btn blue-line" @click="fileAttach('orgbiznofile')">사업자등록증 사본 파일첨부</button>
                <input type="file" ref="orgbiznofile" v-on:change="handleFileUpload('orgbiznofile')" v-show="false" accept="image/jpeg, image/png, .pdf" />
                <div>
                  <a href="#" v-if="info.biznotype === 'FLT001'" class="file-link" @click="imageView(info.pathbiznofile)">{{ info.orgbiznofile }}</a>
                  <a :href="info.pathbiznofile" v-else>{{ info.orgbiznofile }}</a>
                  <button type="button" class="file-del" v-if="info.orgbiznofile && isWrite" @click="removeFile('orgbiznofile')"></button>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>상호</dt>
              <dd><input type="text" :value="info.compname" disabled></dd>
            </dl>
            <dl>
              <dt>업체명</dt>
              <dd><input type="text" ref="name" v-model="info.name"></dd>
            </dl>
            <dl>
              <dt>대표자명</dt>
              <dd><input type="text" :value="info.reprename" disabled></dd>
            </dl>
            <dl>
              <dt>대표자 휴대폰</dt>
              <dd>
                <input type="text" ref="repmobile" v-model="info.repmobile">
              </dd>
            </dl>
            <dl>
              <dt>업태</dt>
              <dd><input type="text" ref="bizcondition" v-model="info.bizcondition"></dd>
            </dl>
            <dl>
              <dt>종목</dt>
              <dd><input type="text" ref="bizitem" v-model="info.bizitem"></dd>
            </dl>
            <dl>
              <dt>주소</dt>
              <dd>
                <button type="button" class="btn blue-line" @click="searchAddress">주소검색</button>
                <input type="text" v-model="info.addr" ref="addr" name="address1" :readonly="true" style="width: 300px;">
                  <input type="text" v-model="info.addrdetail" ref="addrdetail" name="address2" style="width: 300px;">
              </dd>
            </dl>
            <dl>
              <dt>판매상품유형</dt>
              <dd>
                <select style="width: 250px;" v-model="info.sellprodtype" ref="sellprodtype">
                  <option value="">대표카테고리 선택</option>
                  <option v-for="item in prodTypeList" v-bind:key="item.name" :value="item.name">{{ item.name }} </option>
                </select>

              </dd>
            </dl>
            <dl>
              <dt>통신판매 신고번호</dt>
              <dd>
                <input type="text" v-model="info.comsellno" ref="comsellno">
                <span class="txt-orange"><i class="icon-alert"></i>통신판매업 신고 후 가입을 진행해주시기 바랍니다.</span>
              </dd>
            </dl>
            <dl>
              <dt>주요취급브랜드</dt>
              <dd>
                <input type="text" v-model="info.handbrand" ref="handbrand">
              </dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <div class="boxing-title" :class="{ closed : !tabs.tab3 }">고객문의 연락처 정보<i class="arrcodi" @click="toggleTab('tab3')"></i></div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab3 === true"><!-- 닫은 상태일때 display:none -->
            <dl>
              <dt>고객문의 연락처</dt>
              <dd>
                <input type="text" ref="tel" v-model="info.tel">
              </dd>
            </dl>
            <dl>
              <dt>대표 이메일</dt>
              <dd>
                <input type="text" ref="email" v-model="info.email">
                <p class="txt-orange"><i class="icon-alert"></i>고객문의 연락처 및 이메일은 주문정보에 노출되니 정확한 정보를 입력해주시기 바랍니다.</p>
              </dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <div class="boxing-title" :class="{ closed : !tabs.tab4 }">담당자 정보<i class="arrcodi" @click="toggleTab('tab4')"></i></div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab4 === true"><!-- 닫은 상태일때 display:none -->
            <table cellpadding="0" cellspacing="0" class="form-tb-col">
              <colgroup>
                <col width="150px"/>
                <col width="20%"/>
                <col width="224px"/>
                <col width=""/>
              </colgroup>
              <thead>
              <tr>
                <th>담당자 구분</th>
                <th>담당자 이름</th>
                <th>담당자 휴대폰</th>
                <th>담당자 이메일</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(charger, index) in dealerChargeList" v-bind:key="charger.idx" ref="chargeRef">
                <td>
                  <select v-if="!charger.isCreated" v-model="charger.chargetype" disabled>
                    <option :value="charger.chargetype">{{ charger.chargetypename }} 담당자</option>
                  </select>
                  <select v-else v-model="charger.chargetype">
                    <option value="">선택</option>
                    <option v-for="type in chargeTypeList" :key="type.cmcode" :value="type.cmcode">{{ type.codename }} 담당자</option>
                  </select>
                </td>
                <td><input type="text" v-model="charger.chargename" style="width: 100%;" :readonly="charger.chargetype === 'CHA001'"></td>
                <td><input type="text" v-model="charger.chargemobile" :readonly="charger.chargetype === 'CHA001'"></td>
                <td>
                  <input type="text" v-model="charger.chargeemail" style="width: calc(100% - 113px);">
                  <button v-if="charger.chargetype === 'CHA001' && isWrite" type="button" class="btn blue-line ml3" @click="userAuthCheck">실명인증</button>
                  <button v-if="charger.chargetype !== 'CHA001' && charger.chargetype !== 'CHA002' && charger.chargetype !== 'CHA006'" type="button" class="btn red-line ml3" @click="removeCharger(charger, index)">삭제</button>
                  <button v-if="(index === (dealerChargeList.length - 1) && isWrite)" type="button" class="btn blue-line ml3" @click="addCharger">추가</button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="boxing">
          <div class="boxing-title" :class="{ closed : !tabs.tab5 }">정산 정보<i class="arrcodi" @click="toggleTab('tab5')"></i></div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab5 === true"><!-- 닫은 상태일때 display:none -->
            <dl>
              <dt>정산일</dt>
              <dd><p>전월 1일부터 말일까지의 구매확정건을 기준으로 영업일 기준 익월 말일 정산</p></dd>
            </dl>
            <dl>
              <dt>계좌정보</dt>
              <dd>
                <select v-model="info.remitbank" disabled>
                  <option value="002">산업은행</option>
                  <option value="003">기업은행</option>
                  <option value="004">국민은행</option>
                  <option value="007">수협은행/수협중앙회</option>
                  <option value="011">농협은행</option>
                  <option value="012">농협중앙회</option>
                  <option value="020">우리은행</option>
                  <option value="023">SC제일은행</option>
                  <option value="027">한국씨티은행</option>
                  <option value="031">대구은행</option>
                  <option value="032">부산은행</option>
                  <option value="034">광주은행</option>
                  <option value="035">제주은행</option>
                  <option value="037">전북은행</option>
                  <option value="039">경남은행</option>
                  <option value="045">새마을금고중앙회</option>
                  <option value="048">신협중앙회</option>
                  <option value="050">상호저축은행</option>
                  <option value="054">HSBC은행</option>
                  <option value="055">도이치은행</option>
                  <option value="057">제이피모간체이스은행</option>
                  <option value="060">BOA은행</option>
                  <option value="062">중국공상은행</option>
                  <option value="064">산림조합중앙회</option>
                  <option value="071">우체국</option>
                  <option value="081">KEB하나은행</option>
                  <option value="088">신한은행</option>
                  <option value="089">K뱅크</option>
                  <option value="090">카카오뱅크</option>
                  <option value="209">유안타증권</option>
                  <option value="218">KB증권</option>
                  <option value="238">미래에셋대우</option>
                  <option value="240">삼성증권</option>
                  <option value="243">한국투자증권</option>
                  <option value="247">NH투자증권</option>
                  <option value="261">교보증권</option>
                  <option value="262">하이투자증권</option>
                  <option value="263">현대차투자증권</option>
                  <option value="264">키움증권</option>
                  <option value="265">이베스트투자증권</option>
                  <option value="266">SK증권</option>
                  <option value="267">대신증권</option>
                  <option value="269">한화투자증권</option>
                  <option value="270">하나금융투자</option>
                  <option value="278">신한금융투자</option>
                  <option value="279">동부증권</option>
                  <option value="280">유진투자증권</option>
                  <option value="287">메리츠종합금융증권</option>
                  <option value="290">부국증권</option>
                  <option value="291">신영증권</option>
                  <option value="292">케이프투자증권</option>
                  <option value="103">SBI 저축은행</option>
                </select>
                <input type="text" :value="info.remitaccount" style="width: 200px;" disabled>
                <input type="text" :value="info.remitdepositor" disabled>
              </dd>
            </dl>
            <dl>
              <dt>기본수수료</dt>
              <dd v-if="isAdmin">
                <input type="text" class="ml3 right" style="width: 50px;" v-model="info.commrate" maxlength="5"/><span class="ml3">%</span>
              </dd>
              <dd v-if="viewType === 'SAVE' && !isAdmin">
                <span class="ml3">{{ info.commrate }}%</span>
              </dd>
            </dl>
            <dl>
              <dt>통장사본</dt>
              <dd>
                <button type="button" class="btn blue-line" @click="fileAttach('orgbcopyfile')">통장 사본 파일첨부</button>
                <input type="file" ref="orgbcopyfile" v-on:change="handleFileUpload('orgbcopyfile')" v-show="false" accept="image/jpeg, image/png, .pdf" />
                <div>
                  <a href="#" v-if="info.bcopytype === 'FLT001'" class="file-link" @click="imageView(info.pathbcopyfile)">{{ info.orgbcopyfile }}</a>
                  <a :href="info.pathbcopyfile" class="file-link" v-else>{{ info.orgbcopyfile }}</a>
                  <button type="button" class="file-del" v-if="info.orgbcopyfile && isWrite" @click="removeFile('orgbcopyfile')"></button>
                </div>
              </dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <div class="boxing-title" :class="{ closed : !tabs.tab6 }">굿스플로 자동반품 택배사 계약 정보
<!--             <button type="button" class="btn black-line" style="position: absolute; float: right; margin-right: 170px;" v-if="isWrite" @click="applyDeliveryService">반품택배사 이용 신청</button> -->
<!--             <button type="button" class="btn black-line" style="position: absolute; float: right; margin-right: 10px;" v-if="isWrite" @click="applyDeliveryServiceRefresh">반품택배사 이용 신청 갱신</button> -->
            <i class="arrcodi" @click="toggleTab('tab6')"></i>
          </div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab6 === true"><!-- 닫은 상태일때 display:none -->
            <dl v-for="(delivery, index) in deliveryList" v-bind:key="delivery.idx" v-show="index === 0 || index%2 === 0">
              <dt>{{ delivery.logistname }}</dt>
              <dd style="width: 406px;">{{ delivery.locontcode }}</dd>
              <dt>{{ (typeof deliveryList[index+1] !== 'undefined') ? deliveryList[index+1].logistname : '' }}</dt>
              <dd style="width: 406px;">{{ (typeof deliveryList[index+1] !== 'undefined') ? deliveryList[index+1].locontcode : '' }}</dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <div class="boxing-title" :class="{ closed : !tabs.tab7 }">첨부 서류<i class="arrcodi" @click="toggleTab('tab7')"></i></div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab7 === true"><!-- 닫은 상태일때 display:none -->
            <dl>
              <dt>통신판매업신고증</dt>
              <dd class="pt10">
                <button type="button" class="btn blue-line" @click="fileAttach('orgcsellfile')">통신판매업신고증 사본 파일첨부</button>
                <input type="file" ref="orgcsellfile" v-on:change="handleFileUpload('orgcsellfile')" v-show="false" accept="image/jpeg, image/png, .pdf" />
                <div>
                  <a href="#" v-if="info.cselltype === 'FLT001'" class="file-link" @click="imageView(info.pathcsellfile)">{{ info.orgcsellfile }}</a>
                  <a :href="info.pathcsellfile" class="file-link" v-else>{{ info.orgcsellfile }}</a>
                  <button type="button" class="file-del" v-if="info.orgcsellfile && isWrite" @click="removeFile('orgcsellfile')"></button>
                </div>
                <p class="txt-orange"><i class="icon-alert"></i>jpg, jpeg, png파일 또는 PDF 파일만 첨부 가능하며, 파일 최대 크기는 10MB를 초과
                  할 수 없습니다.</p>
              </dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <div class="boxing-title" :class="{ closed : !tabs.tab8 }">추가설정<i class="arrcodi" @click="toggleTab('tab8')"></i></div><!-- 닫은 상태일때 closed 추가 -->
          <div class="form-area" v-show="tabs.tab8 === true"><!-- 닫은 상태일때 display:none -->
            <dl>
              <dt>SMS 마케팅 수신</dt>
              <dd>
                <div class="radio_wrap wide">
                  <input type="radio" id="smsAgree" name="sms" v-model="info.isadsms" value="T" :disabled="!isWrite" /><label for="smsAgree">동의</label>
                  <input type="radio" id="smsDisAgree" name="sms" v-model="info.isadsms" value="F" :disabled="!isWrite" /><label for="smsDisAgree">동의안함</label>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>이메일 마케팅 수신</dt>
              <dd>
                <div class="radio_wrap wide">
                  <input type="radio" id="emailAgree" name="mail" v-model="info.isadmailing" value="T" :disabled="!isWrite" /><label for="emailAgree">동의</label>
                  <input type="radio" id="emailDisAgree" name="mail" v-model="info.isadmailing" value="F" :disabled="!isWrite" /><label for="emailDisAgree">동의안함</label>
                </div>
              </dd>
            </dl>
            <dl v-if="isAdmin">
              <dt>수정권한부여</dt>
              <dd class="clearfix">
                <div class="radio_wrap wide">
                  <input type="radio" name="editor" v-model="isBasicAuth" :value=true id="basicAuth" :disabled="!isWrite"  @change="setPartnersAuth()" /><label for="basicAuth">기본설정</label>
                  <input type="radio" name="editor" v-model="isBasicAuth" :value=false id="ownAuth" :disabled="!isWrite" @change="setPartnersAuth()" /><label for="ownAuth">개별설정</label>
                </div>
                <span class="txt-orange"><i class="icon-alert"></i>기본설정은 [파트너사승인환경설정]기준입니다.</span>
              </dd>
            </dl>
            <dl v-if="isAdmin">
              <dt></dt>
              <dd class="full">
                <button type="button" class="btn gray" style="position: absolute; right: 10px; top: -35px;" @click="initPartnersAuth">초기화</button>
                <div class="check-field">
                  <ul class="clearfix">
                    <li v-for="auth in authTypeList" v-bind:key="auth.cmcode">
                      <input type="checkbox" :id="auth.cmcode" :ref="auth.cmcode" v-model="authCheckArray" :true-value="[]" :value="auth.cmcode" :disabled="isBasicAuth"><label :for="auth.cmcode">{{ auth.codename }}</label>
                    </li>
                  </ul>
                </div>
              </dd>
            </dl>
          </div>
        </div>
        <div class="btn-group" v-if="viewType === 'APPLY' && isAdmin">
          <button type="button" class="btn big blue" v-if="isWrite" @click="updateDetail('RDS002')">입점승인</button>
          <button type="button" class="btn big red" v-if="isWrite" @click="updateDetail('RDS003')">반려</button>
<!--          <button type="button" class="btn big blue">수정완료</button>-->
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
        <div class="btn-group fr" v-if="viewType === 'SAVE'">
          <button type="button" class="btn big blue" v-if="isWrite" @click="updateDetail(info.reqdealst)">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import storage from "@/js/storage";
import store from "@/js/store";

export default {
  name: 'admin.partners.applyList.detail',
  components: {},
  props: ['activeUserNo'],
  data() {
    return {
      info: {},
      dealerChargeList: [],
      prodTypeList: [],
      chargeTypeList: [],
      bankTypeList: [],
      authTypeList: [],
      authCheckArray: [],
      deliveryList: [],
      configAuthTypeOrigin: '',
      orgbiznofile: {},
      orgbcopyfile: {},
      orgcsellfile: {},
      tabs:{
        tab1: true,
        tab2: true,
        tab3: true,
        tab4: true,
        tab5: true,
        tab6: true,
        tab7: true,
        tab8: true,
      },
      isBasicAuth: true,
      isRead: false,
      isWrite: false,
      certPopupObj : null,
      intervalObj : null,
      parentName : '',
      viewType : '', //SAVE : 정보수정용으로 접근한 경우, APPLY : 입정승인처리로 접근한 경우
      isAdmin : false,
    }
  },
  mounted() {
    this.parentName = this.$parent.$options.name;

    const session = storage.getLocalStorage(store.getters.CONSTANTS.MANAGER_SESSION);
    if(session.usertype !== this.$store.getters.CONSTANTS.ADMIN.USER_TYPE_PARTNER) {
      this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {

        this.isRead = (result.data.isread === 'T');
        this.isWrite = (result.data.iswrite === 'T');

        if(this.isRead && this.isWrite && this.parentName !== 'admin.partners.applyList'){
          this.isWrite = false;
        }
        this.isAdmin = true;
        this.initDetail();

      }).catch(error => {
        this.$util.debug(error);
      })
    } else if(session.usertype === this.$store.getters.CONSTANTS.ADMIN.USER_TYPE_PARTNER && session.no === this.activeUserNo){
        this.isRead = 'T';
        this.isWrite = 'T';

        if(this.parentName !== 'partners.info.auth'){
          this.isWrite = false;
        }

        this.initDetail();
    }
  },
  methods: {
    initDetail() {
      if (this.isRead) {
        let param = {userNo: this.activeUserNo}
        this.$http.post('/admin/partners/apply/detail', param)
            .then(result => {

              this.info = result.data.detailinfo;
              this.dealerChargeList = result.data.dealerchargelist;
              this.prodTypeList = result.data.prodtypelist;
              this.chargeTypeList = result.data.chargetypelist;
              this.bankTypeList = result.data.banktypelist;
              this.authTypeList = result.data.authtypelist;
              this.deliveryList = result.data.deliverylist;
              this.configAuthTypeOrigin = result.data.configauthtypeorigin;
              // this.deliveryList = result.data.deliverylist;

              this.setDataFiles(result.data.partnersfiles);

              if (this.dealerChargeList.length > 0) {

                this.dealerChargeList.forEach(charge => {
                  this.chargeTypeList.forEach((chargeType, index) => {
                    if (charge.chargetype === chargeType.cmcode) {
                      this.chargeTypeList.splice(index, 1);
                    }
                  })
                });
              } else {
                this.addCharger();
              }

              if (typeof this.info.muaddauthtype !== 'undefined') {
                let authTypeArray = this.info.muaddauthtype.split(",");
                for (let type of authTypeArray) {
                  this.authCheckArray.push(type);
                }
              }

              let configAuthArray = this.configAuthTypeOrigin.split(",");
              this.isBasicAuth = (this.authCheckArray.sort().join() === configAuthArray.sort().join());
              this.viewType = (this.info.reqdealst === 'RDS002') ? 'SAVE' : 'APPLY';
            }).catch(error => {
          this.$util.debug(error);
        })
      } else {
        alert('페이지 접근 권한이 없습니다.');
        this.info = this.$options.data().info
        this.$emit('closeDetail');
      }

      if (!this.isWrite) {
        let buttons = this.$el.getElementsByTagName('button');

        for (let button of buttons) {
          if (button.className !== 'pop-close') {
            button.style.display = 'none';
            button.disabled = true;
          }
        }
      }
    },
    setDataFiles(fileData) {

      if(typeof fileData['igt029'] !== 'undefined' && fileData['igt029'] !== '') {
        this.orgbiznofile = fileData['igt029'];
        this.$set(this.info, 'orgbiznofile', fileData['igt029'].imgforiname);
        this.$set(this.info, 'pathbiznofile', fileData['igt029'].fullpath);
        this.$set(this.info, 'biznotype', fileData['igt029'].filetype);
      }
      if(typeof fileData['igt030'] !== 'undefined' && fileData['igt030'] !== '') {
        this.orgbcopyfile = fileData['igt030'];
        this.$set(this.info, 'orgbcopyfile', fileData['igt030'].imgforiname);
        this.$set(this.info, 'pathbcopyfile', fileData['igt030'].fullpath);
        this.$set(this.info, 'bcopytype', fileData['igt030'].filetype);
      }
      if(typeof fileData['igt031'] !== 'undefined' && fileData['igt031'] !== '' ) {
        this.orgcsellfile = fileData['igt031'];
        this.$set(this.info, 'orgcsellfile', fileData['igt031'].imgforiname);
        this.$set(this.info, 'pathcsellfile', fileData['igt031'].fullpath);
        this.$set(this.info, 'cselltype', fileData['igt031'].filetype);
      }
    },
    onClose() {
      this.info = this.$options.data().info
      this.$emit('closeDetail');
    },
    searchAddress() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.info.post = data.zonecode;
          this.info.addr = data.address;
          this.info.sigungucode = data.sigunguCode;
          this.info.buildingcode = data.buildingCode;
          this.info.roadnamecode = data.roadnameCode;
        }
      }).open();
    },
    fileAttach(fileTypeKey) {
      this.$refs[fileTypeKey].click();
    },
    handleFileUpload(fileTypeKey) {

      let file = this.$refs[fileTypeKey];

      if(typeof file.files[0] === 'undefined'){
        return false;
      }

      let fileType = ['image/png','image/jpeg', 'image/png', 'application/pdf']
      if(!fileType.includes(file.files[0].type)){
        alert('jpg, jpeg, png파일 또는 PDF 파일만 첨부 가능 합니다.');
        file.value = null;
        this.info[fileTypeKey] = '';
        return false;
      }

      if(file.files[0].size > 10485760){
        alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
        file.value = null;
        this.info[fileTypeKey] = '';
        return false;
      }



      let params = {
        no : this.info.no,
        "files": []
      }

      if(typeof this[fileTypeKey] !== 'undefined'){
        params.deleteidx = this[fileTypeKey].idx;
      }
      params.files.push({key: fileTypeKey, file : file.files[0]});

      this.$http.post('/partnership/updatePartnershipFile', params).then(result => {
        this.setDataFiles(result.data);
      }).catch(error =>{
        this.$util.debug(error);
      });

    },
    removeFile(fileTypeKey) {
      let confirmMsg = confirm("파일을 삭제 하시겠습니까?");
      if(confirmMsg){

        let params = {deleteidx : this[fileTypeKey].idx};
        this.$http.post('/partnership/deletePartnershipFile', params).then(result => {
          if(result.data.code === 'success') {
            if(typeof this[fileTypeKey] !== 'undefined'){
              this[fileTypeKey] = {};
            }

            if(typeof this.info[fileTypeKey] !== 'undefined'){
              this.info[fileTypeKey] =  '';
            }
          }
        }).catch(error =>{
          this.$util.debug(error);
        });

      }



    },
    addCharger() {
      let charger = {
        chargeemail: '',
        chargetypename: '',
        userno: this.info.no,
        chargemobile: '',
        chargetype: '',
        chargename: '',
        isCreated: true
      }

      this.dealerChargeList.push(charger);
    },
    removeCharger(item, index) {
      if(!item.isCreated) {
        if (confirm('담당자를 삭제하시겠습니까?')) {
          let params = Object.assign({userno: this.userno}, item);
          this.$http.post('/admin/partners/apply/charger/delete', params).then(result => {
            this.dealerChargeList = result.data.list;
            this.chargeTypeList = result.data.chargetypelist;
            if (this.dealerChargeList.length > 0) {
              this.dealerChargeList.forEach(charge => {
                this.chargeTypeList.forEach((chargeType, index) => {
                  if (charge.chargetype === chargeType.cmcode) {
                    this.chargeTypeList.splice(index, 1);
                  }
                })
              });
            } else {
              this.addCharger();
            }
          }).catch(error => {
            this.$util.debug(error);
          })
        }
      } else {
        if(this.dealerChargeList[index] === item) {
          this.dealerChargeList.splice(index, 1)
        } else {
          let found = this.dealerChargeList.indexOf(item)
          this.dealerChargeList.splice(found, 1)
        }
      }
    },
    toggleTab(tab) {
      this.tabs[tab] =  !this.tabs[tab];
    },
    initPassword() {
      let confirmMsg = confirm("비밀번호를 초기화 하시겠습니까?");

      if(confirmMsg){
        let mobile = "";

        this.dealerChargeList.forEach(function(row){
          if(row.chargetype === 'CHA001'){
            mobile = row.chargemobile;
          }
        })

        let param = { userno : this.info.no, mobile: mobile }

        this.$http.post('/admin/partners/apply/password/init', param).then(result => {
          if(result.data > 0){ alert('해당 파트너사에게\n 임시 비밀번호가 발급 되었습니다.') }
          if(result.data.code === 'success') {
            alert('해당 파트너사 제휴담당자에게\n임시 비밀번호가 발급 되었습니다.')
          } else {
            alert('임시 비밀번호 발급 중 에러가 발생 되었습니다.')
          }
        }).catch(error => {
          this.$util.debug(error);
        })
      }

    },
    setPartnersAuth() {
      let authObj = (this.isBasicAuth) ? this.configAuthTypeOrigin : this.info.muaddauthtype;
      this.authCheckArray = [];
      if(typeof authObj !== 'undefined'){
        let authTypeArray = authObj.split(",");
        for(let type of authTypeArray){
          this.authCheckArray.push(type);
        }
      }
    },
    userAuthCheck: function(){
      let system = window.sessionStorage.getItem("system");
      let certKey = (system === 'ADMIN') ? process.env.VUE_APP_KMC_CERT_ADMIN : process.env.VUE_APP_KMC_CERT_PARTNERS;
      window.popupCallback = this.certClose;
      this.certPopupObj = window.open('/common/partnership/kmc/request?code='+certKey, "KMCISWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
    },
    certClose(certData){
      if(certData != null) {
        if (typeof certData.ci !== 'undefined') {
          this.info.repCI = certData.ci;
          this.dealerChargeList.forEach(function(row){
            if(row.chargetype === 'CHA001'){
              row.chargename = certData.name;
              row.chargemobile = certData.phoneno;
            }
          })

          window.popupCallback = null;
        }
      }
    },
    updateDetail(applyType) {
      //업체명, 대표자 휴대폰, 업태, 종목, 주소, 판매상품유형, 통신판매신고번호, 고객문의 연락처, 대표 이메일, 사업자등록정보파일명, 통장사본파일명, 통신판매업종신고증, 주요브랜드 NULL 체크
      let validArr = ['name', 'repmobile', 'bizcondition', 'bizitem', 'addr', 'addrdetail', 'sellprodtype', 'comsellno', 'tel', 'email', 'orgbiznofile', 'orgbcopyfile', 'handbrand']

      let isValidate = true;
      for (const [key, value] of Object.entries(this.info)) {
        if(validArr.includes(key)){
          if( (value === '' || value == null) && typeof this.$refs[key] !== 'undefined'){
            this.$refs[key].focus();
            this.$refs[key].scrollIntoView();
            isValidate = false;
            break;
          }
        }
      }

      if(!isValidate) {
        setTimeout(function () {
          alert('필수값 또는 필수파일이 입력/등록되지 않았습니다.');
        }.bind(this), 500);

        return false;
      }

      if(!this.$util.isPhone(this.info.repmobile)){
        alert('대표자 휴대폰형식이 올바르지 않습니다.');
        this.$refs.repmobile.focus();
        this.$refs.repmobile.scrollIntoView();
        return false;
      }

      if(!this.$util.isEmail(this.info.email)){
        alert('대표 이메일 형식이 올바르지 않습니다.');
        this.$refs.email.focus();
        this.$refs.email.scrollIntoView();
        return false;
      }

      //중복된 담당자 구분이 있는지 또는 필수항목들이 입력이 되어 있는지 확인.
      let result = {};
      this.dealerChargeList.forEach(charge => {
        if(charge.chargetype !== '') {
          result[charge.chargetype] = (result[charge.chargetype] || 0) + 1;
        }
      });

      let isDuplicateChargeType = false;
      let type;
      let msgType = {
        isDuplicate : "담당자 구분이 중복되었습니다.",
        isEmpty : "담당자 필수 항목이 입력되지 않았습니다.",
        isFormatError : "휴대폰/이메일 형식이 올바르지 않습니다.",
      }
      this.dealerChargeList.forEach(charge => {
        if(result[charge.chargetype] > 1 || charge.chargetype === ''){
          type = (result[charge.chargetype] > 1) ? 'isDuplicate' : 'isEmpty';
          isDuplicateChargeType = true;
          return false;
        } else if(charge.chargename === '' || charge.chargeemail === '' || charge.chargemobile === '') {
          isDuplicateChargeType = true;
          type = 'isEmpty';
          return false;
        } else if(!this.$util.isPhone(charge.chargemobile) || !this.$util.isEmail(charge.chargeemail)) {
          isDuplicateChargeType = true;
          type = 'isFormatError'
          return false;
        }
      });

      if(isDuplicateChargeType){
        alert(msgType[type]);
        return false;
      }

      this.info.muaddauthtype = this.authCheckArray.join();

      let params = {
        // "files": [],
        "dealerChargeInfo" : []
      }

      params.dealerChargeInfo = this.dealerChargeList;

      params = Object.assign({}, params, this.info);
      params.reqdealst = applyType;

      console.log(params);

      this.$http.post('/partnership/update', params).then(result => {
        if(result.data.updateresult){
          if(this.viewType === 'SAVE'){
            alert('정보수정 처리가 완료 되었습니다.');
          } else {
            let type = (applyType === 'RDS002') ? '입점승인' : '반려';
            alert(type + '처리가 완료 되었습니다.');
          }

          this.$emit('closeDetail');
        } else {
          if(this.viewType === 'SAVE'){
            alert('파트너사 정보수정 처리에 실패하였습니다.\n관리자에게 문의하시기 바랍니다.');
          } else {
            alert('파트너사 정보 수정 및 승인/반려처리에 실패하였습니다.\n관리자에게 문의하시기 바랍니다.');
          }
        }
        this.$util.debug(result);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    initPartnersAuth() {
      this.authCheckArray = [];
      if(typeof this.info.muaddauthtype !== 'undefined'){
        let authTypeArray = this.info.muaddauthtype.split(",");
        for(let type of authTypeArray){
          this.authCheckArray.push(type);
        }
      }

      this.isBasicAuth = (this.authCheckArray.sort().join() === this.configAuthTypeOrigin);

    },
    applyDeliveryService() {

      this.$http.post('/admin/partners/apply/otp/code', {userno : this.activeUserNo}).then(result => {
        if(result.data.status === 200){

          let applyUrl = result.data.url;

          if(this.deliveryList.length > 0){
            applyUrl = applyUrl.replace(/detail/gi, 'list');
          }

          window.open(applyUrl, '_blank');

        } else {
          alert(result.data.message);
        }

        this.$util.debug(result);
      }).catch(error => {
        this.$util.debug(error);
      });
    },
    imageView(url) {
      this.$viewerApi({
        images : [url]
      });

    },
    applyDeliveryServiceRefresh(){
      this.$http.post('/admin/partners/apply/goodsflow/refresh', {}).then(result => {
        if(result.statusCode === 200){
          alert('갱신 처리가 완료 되었습니다.');
        }
      }).catch(error =>{
        this.$util.debug(error);
      });
    }
  },
  watch : {
    'info.commrate': function(value) {
      value = value+'';
      if (this.$util.isNull(value)) {
        return this.info.commrate = '';
      } else {
        let pattern = /(^\d+$)|(^\d{1,5}.\d{0,2}$)/;
        if (!pattern.test(value)) {
          value = value.substr(0, value.length-1);
        }
        return this.info.commrate = value;
      }
    },
    'authCheckArray': function(newValue, oldValue){
      let optionAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_OPTION;
      let marketPriceAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_MARKET_PRICE;
      let priceAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_PRICE;
      let stockCntAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_STOCK_CNT;
      // 옵션 체크시 정상가, 판매가, 재고 체크
      if (oldValue.indexOf(optionAuthCode) < 0 && newValue.indexOf(optionAuthCode) > -1) {
        if (this.authCheckArray.indexOf(marketPriceAuthCode) < 0) {
         this.authCheckArray.push(marketPriceAuthCode);
        }
        if (this.authCheckArray.indexOf(priceAuthCode) < 0) {
          this.authCheckArray.push(priceAuthCode);
        }
        if (this.authCheckArray.indexOf(stockCntAuthCode) < 0) {
          this.authCheckArray.push(stockCntAuthCode);
        }
      }
      // 정상가, 판매가, 재고 체크해제시 옵션체크 해제
      if ((oldValue.indexOf(marketPriceAuthCode) > -1 && newValue.indexOf(marketPriceAuthCode) < 0)
        || (oldValue.indexOf(priceAuthCode) > -1 && newValue.indexOf(priceAuthCode) < 0)
        || (oldValue.indexOf(stockCntAuthCode) > -1 && newValue.indexOf(stockCntAuthCode) < 0)) {
          let findIdx = this.authCheckArray.indexOf(optionAuthCode);
          if (findIdx > 0) {
            this.authCheckArray.splice(this.authCheckArray.indexOf(optionAuthCode), 1);
          }
      }
    }
  }
}
</script>