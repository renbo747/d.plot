<template>
    <b-modal
        id="id-find-sns-success"
        modal-class="dp-modal page-layer id-find__success02"
        scrollable
        :hide-footer="true"
    >
    <!-- Id-find Success01 Modal Header -->
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">회원정보 확인결과</h5>
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
        고객님은 {{signupdt}}에 {{signch}}로 <br />
        가입하셨습니다. <br />
        {{signch}} 계정으로 로그인해주세요.
        </p>
        <b-button
        class="dp-btn pl-0 pr-0 text-white"
        variant="gray-800"
        squared
        @click="$bvModal.hide('id-find-sns-success')"
        >
        <span>로그인</span>
        </b-button>
    </div>
    </b-modal>
</template>
<script>
export default {
    data() {
        return {
            signupdt : null,
            signch : ''
        }
    },
    props: {
        param: { type: Object }
    },
    mounted() {
        const temp = this.$util.convertStringToDate(this.param.regdt);
        this.signupdt = this.$util.getDateFormat(temp, 'yyyy년 MM월 dd일');
        switch (this.param.joinchtype) {
            case 'UCT002':
                this.signch = '네이버';
                break;
            case 'UCT003':
                this.signch = '카카오';
                break;
            default:
                this.signch = '애플';
                break;
        }
    },
    methods : {
        login() {
            this.fnConfirm();
            this.$bvModal.hide('id-find-sns-succ');
        },
    }
}
</script>