<template>
  <div>
      <p>kmcResult화면</p>
  </div>
</template>

<script>
export default {
    data(){
        return {
            kmcInfoReq : {
                rec_cert: "",
                certNum:""
            },
            kmcInfo: {},
            memberInfo: {},
            failPath:"",
            plusInfo:[],
            plusInfoObj:{}
        }
    },
    methods: {
        /*****************************
         * 복호화 처리
         * ***************************/
        kmcCertDecry(){
            this.$http.post("common/kmcCertDecry", this.kmcInfoReq).then((result) =>{
                if (result.statusCode ==  200) {
                    this.kmcInfo = result.data.kmcinfo;  //kmc 정보
                    this.memberInfo = result.data.memberinfo; //회원정보
                    let str = this.kmcInfo.plusinfo;
                    this.plusInfo = str.split(',');
                    
                    //queryString을 Object 변환
                    if (!this.$util.isNull(this.plusInfo[2])) {
                       //kmc의 plusInfo에 "/","|","&" 사용하지 못하여 '#'으로 저장되어 있어 '#'->'&'치환처리
                        const urlParams = new URLSearchParams(this.plusInfo[2].replaceAll('#','&'));
                        const params = Object.fromEntries(urlParams);
                       //const params = this.$util.toParam(this.plusInfo[2].replaceAll('#','&'));
                       this.plusInfoObj = params;
                    }
                    this.$util.debug("kmcInfo::"+JSON.stringify(this.kmcInfo));
                    this.$util.debug("memberInfo::"+JSON.stringify(this.memberInfo));
                    this.$util.debug("plusInfo::"+JSON.stringify(this.plusInfo));
                    this.$util.debug("plusInfoObj::"+JSON.stringify(this.plusInfoObj));

                    this.$router.push({name:this.plusInfo[0], params:{kmcInfo:this.kmcInfo, memberInfo:this.memberInfo, plusInfo: this.plusInfoObj}});
                } else {
                    if (!this.$util.isNull(this.plusInfo[1])) {
                        this.$router.push({name: this.plusInfo[1]});
                    }else {
                        let  system = window.sessionStorage.getItem('system');
                        if (system == 'PARTNER') {
                            this.$router.push({name:'partners.main'});
                        }else if (system == 'ADMIN') {
                            this.$router.push({name:'admin.main.dashboard'});
                        }else {
                            this.$router.push({name:'main'});
                        }

                    }
                }
            });
        }
    },
    mounted() {
        this.kmcInfoReq.rec_cert = this.$route.query.rec_cert;
        this.kmcInfoReq.certNum = this.$storage.getSessionStorage('CERTNUM');
        this.failPath = this.$storage.getSessionStorage('FAILPATH');
        this.kmcCertDecry();
    }
}
</script>

<style>
</style>