<template>
    <b-modal
        id="raffleWinner"
        modal-class="dp-modal page-layer raffle-modal-container_winner"
        scrollable
        centered
        :hide-footer="true"
    >
    <!-- Page Modal Header -->
        <template #modal-header="{ cancel }">
            <h5 class="modal-title">{{param.month}}월 {{param.week}}주차 릴레이 래플 당첨 대상자</h5>
            <i class="modal-close" @click="cancel()">
                <img
                    src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                    alt="닫기"
                />
            </i>
        </template>

        <!-- Page Modal Body -->
        <div class="page-layer__body">
            <div class="winner_list">
                <ul>
                    <li class="list_header">
                        <span>성명</span>
                        <span>휴대폰 번호 뒷자리</span>
                    </li>
                    <li v-for="(winner, idx) in winnerlist" :key="idx">
                        <span>{{securityTextMasking(winner.name)}}</span>
                        <span>{{securityTextMasking(winner.phone)}}</span>
                    </li>
                </ul>
            </div>
        </div>
    </b-modal>
</template>

<script>
    export default {
        props: {
            param: Object,
        },
        created() {
            this.onSearch();
        },
        methods: {
            checkNull : function (t){
                return !!(typeof t == "undefined" || t == null || t == "");
            },
            
            securityTextMasking(t) {
                let originStr = t;
                let maskingStr;
                let strLength;
                
                if(this.checkNull(originStr)) return originStr;
                strLength = originStr.length;
                
                if(strLength < 3){
                    maskingStr = originStr.replace(originStr[originStr.length-1], '*')
                }else {
                    let midWord = originStr.substring(1,originStr.length - 1);
                    midWord < 5 ? midWord : midWord = midWord.substring(0,4);
                    midWord = '*'.repeat(midWord.length);
                    
                    maskingStr = originStr.substring(0,1) + midWord + originStr.substring(originStr.length - 1)
                }
                
                return maskingStr;
            },
            onSearch() {

                let params = {
                    eventidx : this.raffleData.eventidx,
                }
                this.$http.post("/event/raffleWinList", params)
                    .then(result => {
                        if (result.statusCode === 200) {
                        let data = result.data;

                        // 프로모션 상세 정보
                        this.winnerlist = data.winnerlist;
                        }
                        else if (result.statusCode === 201) {
                            alert("당첨자 추첨 전입니다.");
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                });
            }
        },
        data() {
            return {
                raffleData: this.param,
                winnerlist: []
            }
        }
    }
</script>

<style scoped>
    .winner_list {max-width: 100%; width: 100%; border:1px solid #ddd; padding:10px;}
    .winner_list ul {padding:0;}
    .winner_list ul .list_header {font-weight: 700;}
    .winner_list ul li {list-style: none; font-size: 14px; color: #666; display: flex; padding:5px 0;}
    .winner_list ul li + li {border-top:1px dashed #ddd;}
    .winner_list ul li span {min-width:100px;}
    .winner_list ul li span + span {margin-left: 10px;}
</style>