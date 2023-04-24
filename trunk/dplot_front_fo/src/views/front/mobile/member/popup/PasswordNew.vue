<template>
    <b-modal
    id="password-new"
    modal-class="dp-modal page-layer password-find__new-password"
    scrollable
    :hide-footer="true"
    >
    <!-- Password-find New-password Modal Header -->
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">새 비밀번호 설정</h5>
        <i class="modal-close" @click="cancel()">
        <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
        />
        </i>
    </template>

    <!-- Password-find New-password Modal Body -->
    <div class="page-layer__body">
        <p class="password-find__description">
        새로운 비밀번호를 설정해주세요.
        </p>
        <form>
        <div class="form-group">
            <base-input
            :type="'password'"
            label="새 비밀번호"
            placeholder="비밀번호를 입력해 주세요."
            :is-label="true"
            v-model="newpw"
            />
        </div>
        <div class="form-group new-password-repeat">
            <base-input
            :type="'password'"
            label="새 비밀번호 재입력"
            placeholder="비밀번호를 다시 입력해주세요."
            :is-label="true"
            v-model="confirmpw"
            />
        </div>
        <b-button
            class="dp-btn text-white"
            variant="gray-800"
            squared
            @click="changeNewPw()"
        >
            <span>비밀번호 설정하기</span>
        </b-button>
        </form>
    </div>
    </b-modal>
</template>

<script>
export default {
    data() {
        return {
            newpw: "", // 새로운 비밀번호
            confirmpw: "", // 새로운 비밀번호 확인 (새로운 비밀번호랑 일치여부)
        }
    },
    props: {
        param: { type: Object }
    },
    mounted() {
    },
    methods : {
        changeNewPw() {
            this.$front.changePw(this, ()=>{
                this.$bvModal.hide('password-new');
            })
            // // 유효성 3개 다 success 여야함
            // if (this.newpw == "") {
            //     this.$eventBus.$emit('alert', '알림', "새 비밀번호를 입력해주세요.");
            //     return false;
            // } 
            // if (this.confirmpw == "") {
            //     this.$eventBus.$emit('alert', '알림', "비밀번호 확인을 입력해주세요.");
            //     return false;
            // }

            // if(this.newpw != this.confirmpw) {
            //     this.$eventBus.$emit('alert', '알림', "비밀번호 확인이 일치하지 않습니다.");
            //     return false;
            // }

            // if(!this.$util.isPassword(this.newpw)) {
            //     this.$eventBus.$emit('alert', '알림', "최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 또는 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합으로 입력해야합니다.");
            //     return false;
            // }
            // const param = {
            //     userno: this.param.userno,
            //     newpw: this.newpw,
            //     confirmpw: this.confirmpw,
            // };

            // this.$http.post("/member/pw/change", param).then((result) => {
            //     if (result.statusCode == 200) {
            //         this.$eventBus.$emit('alert', '알림', "비밀번호 변경이 처리되었습니다.", ()=>{
            //             this.$bvModal.hide('password-new');
            //         });
            //     }
            // });
        }
    }
}
</script>