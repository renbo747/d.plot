<template>
    <b-modal id="raffle-modal" modal-class="dp-modal" centered :no-close-on-backdrop="true">
        <template>
            <h5 class="modal-title" style="text-align: center;">래플 이벤트 공유</h5>                      
        </template>
        <template #modal-footer="{}">
        <div class="btn-group">
            <b-button 
            variant="gray-800 btn-h38" 
            @click="copyUrl()"
            >URL 복사</b-button
            >
            <b-button 
            variant="outline-gray-800"
            @click="kakaoShare()"
            >카카오톡 공유하기</b-button
            >
            <input
            type="text"
            ref="urlinput"
            v-model="url"
            style="position: absolute; top: -9999em"
            />            
        </div>
        </template>
    </b-modal>
</template>

<script>
export default {
    mounted() {
        if (!Kakao.isInitialized()) {
            Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
        }
    },
    data() {
        return {
            url: document.location.href,
            platform: window.sessionStorage.getItem("platform"),
            serverCallbackArgs:{
                id: '',
                key: '',
            },
            param: {
                kakao: {
                    objectType: "feed",
                    content: {
                        title: "[D.PLOT] 래플 이벤트",
                        description: '래플이벤트 공유하기!!',
                        imageUrl: '',
                        link: {
                            mobileWebUrl: window.location.href,
                            webUrl: window.location.href
                        },
                    },
                },
                meta: {
                    title: "D.PLOT",
                    summary: "상세내용",
                    img: ''
                }
            }        
        };
    },    
    methods : {
        copyUrl() {
            this.$refs.urlinput.select();
            let isCopy = document.execCommand("copy");
            this.$refs.urlinput.blur();
            if (isCopy) {
                // this.$toast.clear();
                // this.$toast.open(
                // "주소가 복사되었습니다. </br> 입력하실 곳에 붙여넣기 해주세요."
                //);
                alert("주소가 복사되었습니다.");
            }
            console.log('copy url');
            this.$bvModal.hide('raffle-modal');
        },
        kakaoShare() {
            //this.serverCallbackArgs.id = "testid";
            // this.serverCallbackArgs.key = "testkey";
            
            this.param.kakao.serverCallbackArgs = this.serverCallbackArgs;
            Kakao.Link.sendDefault(this.param.kakao);
            console.log('kakao share')
            this.$bvModal.hide('raffle-modal');
        }
    }
}
</script>

<style>
.modal-backdrop {
    z-index:2000 !important;
}
.modal {
    z-index:2001 !important;
}
</style>