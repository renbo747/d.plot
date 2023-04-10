<template>
    <b-modal
    id="id-find-success"
    modal-class="dp-modal page-layer id-find__success01"
    scrollable
    :hide-footer="true"
    >
    <!-- Id-find Success01 Modal Header -->
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">아이디 찾기 결과</h5>
        <i class="modal-close" @click="cancel()">
        <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
        />
        </i>
    </template>

    <!-- Id-find Success01 Modal Body -->
    <div class="page-layer__body">
        <p class="id-find__description">
        고객님이 가입하신 아이디입니다.
        </p>
        <div class="id-find__box">
        <p class="id-find__id">{{param.useridaster}}</p>
        <p class="id-find__date"><span>{{param.regdtstr}}</span> 가입</p>
        </div>
        <div class="id-find__email">
        <p class="id-find__email-text">{{param.emailaster}}</p>
        <b-button
            class="dp-btn pl-0 pr-0"
            variant="outline-gray-800"
            squared
            @click="sendId('EMAIL')"
        >
            <span>메일로 받기</span>
        </b-button>
        </div>
        <hr />
        <div class="id-find__phone">
        <p class="id-find__phone-text">{{param.mobileaster}}</p>
        <b-button
            class="dp-btn pl-0 pr-0"
            variant="outline-gray-800"
            squared
            @click="sendId('MOBILE')"
        >
            <span>메시지로 받기</span>
        </b-button>
        </div>
        <p class="id-find__send-info">
        회원정보에 등록된 이메일 / 휴대폰번호로 전체 아이디가
        발송됩니다.
        </p>
        <div class="id-find__success-buttons">
        <b-button
            class="dp-btn pl-0 pr-0 btn-mr10"
            v-if="param.joinchtype !== 'UCT001'"
            variant="outline-gray-800"
            squared
            @click="newPw()"
        >
            <span>비밀번호 재설정</span>
        </b-button>
        <b-button
            class="dp-btn pl-0 pr-0 text-white"
            variant="gray-800"
            squared
            @click="$bvModal.hide('id-find-success')"
        >
            <span>로그인</span>
        </b-button>
        </div>
    </div>
    </b-modal>
</template>
<script>
import PasswordNew from "@views.mobile/member/popup/PasswordNew.vue"

export default {
    data() {
        return {
            signupdt : null
        }
    },
    props: {
        param: { type: Object }
    },
    mounted() {
        // const temp = this.$util.convertStringToDate(this.param.regdt);
        // this.signupdt = this.$util.getDateFormat(temp, 'yyyy.MM.dd');
        // /this.signupdt = this.$util.getFormatDate3(this.param.regdate, '.');
    },
    methods : {
        // 메일로 아이디 보내기
        sendId(type) {
            const param = this.param;
            param.type = type;
            this.$http.post('/member/sendId', param).then(result => {
                if (result.statusCode == 200) {
                    const data = result.data;
                    if(type == 'EMAIL') {
                        this.$eventBus.$emit('alert', '알림', "아이디를 이메일로 전송했습니다.");
                    } else {
                        this.$eventBus.$emit('alert', '알림', "아이디를 핸드폰번호로 전송했습니다.");
                    }
                } 
            });
        },
        newPw() {
            this.$bvModal.hide('id-find-success');
            this.$eventBus.$emit('showModal', PasswordNew, 'password-new', this.param);
        }
    }
}
</script>