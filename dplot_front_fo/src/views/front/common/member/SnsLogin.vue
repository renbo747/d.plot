<template>
    <KmcCertReq ref="kmcCertReq"></KmcCertReq>
</template>
<script>
import KmcCertReq from "@views/common/components/KmcCertReq.vue"
import SnsConnect from '@views.mobile/member/popup/SnsConnect.vue'
import NaverSignup from '@views.mobile/member/popup/NaverSignup.vue'
import KakaoSignupSuccess from '@views.mobile/member/popup/KakaoSignupSuccess.vue'

export default {
    components : {KmcCertReq},
    beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
        title: "로그인",
        searchIcon: false,
        cartIcon: false,
        homeIcon: false,
        });
    },
    data(){
        return {
            platform : null,
            logininfo: {}
        }
    },
    mounted() {
        this.platform = window.sessionStorage.getItem('platform');
        //this.$eventBus.$emit('loading', true);
    },
    methods : {
        snsLogin(params) {
            // this.$http.post('/member/sns/login', params).then(result => {
            //     if (result.statusCode == 200) {
            //         if(!this.$util.isEmpty(result.data.membersns)) {
            //             //SNS 회원정보가 있을경우
            //             this.$front.otherLogin(this, result.data.membersns.userid, 'sns', result.data.membersns.snsmemberno, result.data.membersns.snstype);
            //         } else if(!this.$util.isEmpty(result.data.member)) {
            //             //기존 회원정보가 있을경우
            //             this.logininfo = Object.assign(params, result.data);
            //             if(this.platform == 'MAC001') {
            //                 this.$router.replace({name : 'member-sns-connect', params : result.data});
            //             } else {
            //                 this.$eventBus.$emit('showModal', SnsConnect, 'sns-connect', result.data);
            //                 this.$router.replace({name : 'member-login'});
            //             }
            //         } else {
            //             params.issns = 'T';
            //             params.dadamembertype = "DMT001";

            //             //회원정보가 없을경우
            //             if(params.snstype == 'ULT001') {
            //                 //네이버
            //                 let successParam = Object.assign({}, result.data);
            //                 successParam.issns = 'T';
            //                 this.$eventBus.$emit('showModal', NaverSignup, 'naver-signup', successParam);
            //                 //this.$router.replace('/member/login');
            //             } else if(params.snstype == 'ULT002') {
            //                 //카카오
            //                 this.$http.post('/member/signup/act', params).then(result => {
            //                     if(result.statusCode == 200) {
            //                         this.$eventBus.$emit('alert', '회원가입', '회원가입이 완료되었습니다.', ()=>{
            //                             let successParam = Object.assign({}, result.data);
            //                             successParam.issns = 'T';

            //                             this.$eventBus.$emit('showModal', KakaoSignupSuccess, 'kakao-signup-success', successParam);
            //                             //this.$front.otherLogin(this, result.data.userid, 'sns', result.data.snsmemberno, result.data.snstype);
            //                         });
            //                     } else {
            //                         this.$eventBus.$emit('alert', '알림', result.message);
            //                         //this.$router.replcae('/main');
            //                     }
            //                 });
            //             } else {
            //                 //애플 회원가입 처리를 위하여 login페이지로 이동
            //                 params.isApple = true;
            //                 //this.$router.replace({'name':'member-login', 'params': params});
            //                 this.$parent.appleSignup(params);
            //             }
            //         }
            //     }
            // });
        }
    },
    beforeDestroy() {
        this.$eventBus.$emit('loading', false);
    },
}
</script>
