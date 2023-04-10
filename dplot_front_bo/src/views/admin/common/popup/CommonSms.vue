<template>
  <!-- SMS 발송 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 600px;">
      <div class="pop-header">
        <h2 v-if="isLongSentence === 'F'">SMS 발송</h2>
        <h2 v-if="isLongSentence === 'T'">LMS 발송</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="tab-group mt0">
          <ul><!-- 활성화탭 li에 class="active" 추가 -->
            <li :class="isLongSentence === 'F' ? 'active' : ''"><a @click="onChangeSentence('F')">단문</a></li>
            <li :class="isLongSentence === 'T' ? 'active' : ''"><a @click="onChangeSentence('T')">장문</a></li>
          </ul>
        </div>
        <div class="tab-area">
          <div class="col2">
            <div class="left">
              <div class="sms-input">
                <div class="input-area">
                  <textarea placeholder="보낼 문자를 입력하세요." :value="content" @input="inputContent"></textarea>
                  <!--                  <textarea placeholder="보낼 문자를 입력하세요." v-model="content"></textarea>-->
                </div>
                <div class="byte-cnt" v-if="isLongSentence === 'F'">단문 : <strong>{{ content.length }}</strong> / 90</div>
                <div class="byte-cnt" v-if="isLongSentence === 'T'">장문 : <strong>{{ content.length }}</strong> / 1200</div>
              </div>
            </div>
            <div class="right">
              <table cellpadding="0" cellspacing="0" class="gray-tb mt20">
                <colgroup>
                  <col width="90px">
                  <col width="">
                </colgroup>
                <tbody>
                <tr>
                  <th>수신번호</th>
                  <td>{{ userInfo.mobile }}</td>
                </tr>
                <tr>
                  <th>고객명</th>
                  <td>{{ userInfo.fullname }}</td>
                </tr>
                </tbody>
              </table>
              <div>
                <div class="small mt20">특수문자</div>
                <div class="btn-char-group">
                  <button type="button" @click="onClickCharacter('☆')">☆</button>
                  <button type="button" @click="onClickCharacter('★')">★</button>
                  <button type="button" @click="onClickCharacter('♡')">♡</button>
                  <button type="button" @click="onClickCharacter('♥')">♥</button>
                  <button type="button" @click="onClickCharacter('♧')">♧</button>
                  <button type="button" @click="onClickCharacter('♣')">♣</button>
                  <button type="button" @click="onClickCharacter('◁')">◁</button>
                  <button type="button" @click="onClickCharacter('◀')">◀</button>
                  <button type="button" @click="onClickCharacter('▷')">▷</button>
                  <button type="button" @click="onClickCharacter('▶')">▶</button>
                  <button type="button" @click="onClickCharacter('♤')">♤</button>
                  <button type="button" @click="onClickCharacter('♠')">♠</button>
                  <button type="button" @click="onClickCharacter('♧')">♧</button>
                  <button type="button" @click="onClickCharacter('♣')">♣</button>
                  <button type="button" @click="onClickCharacter('⊙')">⊙</button>
                  <button type="button" @click="onClickCharacter('○')">○</button>
                  <button type="button" @click="onClickCharacter('●')">●</button>
                  <button type="button" @click="onClickCharacter('◎')">◎</button>
                  <button type="button" @click="onClickCharacter('◇')">◇</button>
                  <button type="button" @click="onClickCharacter('◆')">◆</button>
                  <button type="button" @click="onClickCharacter('⇔')">⇔</button>
                  <button type="button" @click="onClickCharacter('△')">△</button>
                  <button type="button" @click="onClickCharacter('▲')">▲</button>
                  <button type="button" @click="onClickCharacter('▽')">▽</button>
                  <button type="button" @click="onClickCharacter('▼')">▼</button>
                  <button type="button" @click="onClickCharacter('▒')">▒</button>
                  <button type="button" @click="onClickCharacter('▤')">▤</button>
                  <button type="button" @click="onClickCharacter('▥')">▥</button>
                  <button type="button" @click="onClickCharacter('▦')">▦</button>
                  <button type="button" @click="onClickCharacter('▩')">▩</button>
                  <button type="button" @click="onClickCharacter('◈')">◈</button>
                  <button type="button" @click="onClickCharacter('▣')">▣</button>
                  <button type="button" @click="onClickCharacter('◐')">◐</button>
                  <button type="button" @click="onClickCharacter('◑')">◑</button>
                  <button type="button" @click="onClickCharacter('♨')">♨</button>
                  <button type="button" @click="onClickCharacter('☏')">☏</button>
                  <button type="button" @click="onClickCharacter('☎')">☎</button>
                  <button type="button" @click="onClickCharacter('☜')">☜</button>
                  <button type="button" @click="onClickCharacter('☞')">☞</button>
                  <button type="button" @click="onClickCharacter('♭')">♭</button>
                  <button type="button" @click="onClickCharacter('♩')">♩</button>
                  <button type="button" @click="onClickCharacter('♪')">♪</button>
                  <button type="button" @click="onClickCharacter('♬')">♬</button>
                  <button type="button" @click="onClickCharacter('㉿')">㉿</button>
                  <button type="button" @click="onClickCharacter('㈜')">㈜</button>
                  <button type="button" @click="onClickCharacter('℡')">℡</button>
                  <button type="button" @click="onClickCharacter('㏇')">㏇</button>
                  <button type="button" @click="onClickCharacter('±')">±</button>
                  <button type="button" @click="onClickCharacter('㏂')">㏂</button>
                  <button type="button" @click="onClickCharacter('㏘')">㏘</button>
                  <button type="button" @click="onClickCharacter('€')">€</button>
                  <button type="button" @click="onClickCharacter('®')">®</button>
                  <button type="button" @click="onClickCharacter('↗')">↗</button>
                  <button type="button" @click="onClickCharacter('↙')">↙</button>
                  <button type="button" @click="onClickCharacter('↖')">↖</button>
                  <button type="button" @click="onClickCharacter('↘')">↘</button>
                  <button type="button" @click="onClickCharacter('↕')">↕</button>
                  <button type="button" @click="onClickCharacter('↔')">↔</button>
                  <button type="button" @click="onClickCharacter('↑')">↑</button>
                  <button type="button" @click="onClickCharacter('↓')">↓</button>
                  <button type="button" @click="onClickCharacter('∀')">∀</button>
                  <button type="button" @click="onClickCharacter('∃')">∃</button>
                  <button type="button" @click="onClickCharacter('∮')">∮</button>
                  <button type="button" @click="onClickCharacter('∑')">∑</button>
                  <button type="button" @click="onClickCharacter('∏')">∏</button>
                  <button type="button" @click="onClickCharacter('℉')">℉</button>
                  <button type="button" @click="onClickCharacter('‰')">‰</button>
                  <button type="button" @click="onClickCharacter('￥')">￥</button>
                  <button type="button" @click="onClickCharacter('￡')">￡</button>
                  <button type="button" @click="onClickCharacter('￠')">￠</button>
                  <button type="button" @click="onClickCharacter('Å')">Å</button>
                  <button type="button" @click="onClickCharacter('℃')">℃</button>
                  <button type="button" @click="onClickCharacter('♂')">♂</button>
                  <button type="button" @click="onClickCharacter('♀')">♀</button>
                  <button type="button" @click="onClickCharacter('∴')">∴</button>
                  <button type="button" @click="onClickCharacter('《')">《</button>
                  <button type="button" @click="onClickCharacter('》')">》</button>
                  <button type="button" @click="onClickCharacter('『')">『</button>
                  <button type="button" @click="onClickCharacter('』')">』</button>
                  <button type="button" @click="onClickCharacter('【')">【</button>
                  <button type="button" @click="onClickCharacter('】')">】</button>
                  <button type="button" @click="onClickCharacter('±')">±</button>
                  <button type="button" @click="onClickCharacter('×')">×</button>
                  <button type="button" @click="onClickCharacter('÷')">÷</button>
                  <button type="button" @click="onClickCharacter('∥')" style="font-family: dotum;">∥</button>
                  <button type="button" @click="onClickCharacter('＼')">＼</button>
                  <button type="button" @click="onClickCharacter('√')">√</button>
                  <button type="button" @click="onClickCharacter('∽')">∽</button>
                  <button type="button" @click="onClickCharacter('∵')">∵</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="pop-footer mt20">
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="sendMessage">발송</button>
          <button type="button" class="btn big darkgray" @click="$emit('close')">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /SMS 발송 팝업 -->
</template>

<script>
export default {
  name: "CommonSms",
  props: {
    userNo: Number
  },
  data() {
    return {
      userInfo: {
        name: '',
        userno: '',
        mobile: '',
        userid: '',
        fullname: '',
      },
      content: '',
      isLongSentence: 'F', // 장문, 단문
    }
  },
  mounted() {
    this.onInit();
  },
  methods: {
    onInit() {
      this.setUserInfo();
    },

    // 유저 정보 조회
    setUserInfo() {
      this.$http.post("/sms/init", {userNo: this.userNo})
          .then(result => {
            if (result.statusCode === 200) {
              this.userInfo = result.data.userinfo;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 장문 단문 변경
    onChangeSentence(isLong) {
      this.isLongSentence = isLong;
      this.content = '';
    },

    // 특수 문자 클릭
    onClickCharacter(char) {
      this.content += char;
    },

    // 입력 이벤트
    inputContent(e) {
      this.content = e.target.value;
    },

    // 보내기
    sendMessage() {
      let params = {
        userno : this.userInfo.userno,
        sender_number: this.userInfo.mobile.replace(/-/g, ''),
        receiver_number: this.userInfo.mobile.replace(/-/g, ''),
        msg_type: (this.isLongSentence === 'F') ? 'SMS' : 'LMS',
        msg: this.content,
        isLongSentence: this.isLongSentence,
      }
      this.$http.post("/sms/send", params)
          .then(result => {
            if (result.statusCode === 200 && result.data.httpcode === 200 && result.data.success === 1) {
              alert("메세지 전송에 성공했습니다.");
              this.$emit('close');
            } else {
              alert(result.data.httpmessage);
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
  },
  watch: {
    content(val, oldVal) {
      let maxLength = (this.isLongSentence === 'F') ? 90 : 1200;
      if (val.length > maxLength) {
        alert(maxLength + "자 이상을 넘을 수 없습니다.");
        this.content = oldVal;
      }
    },
  }
}
</script>
