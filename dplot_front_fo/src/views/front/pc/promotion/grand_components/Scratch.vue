<template>
    <b-modal
        id="scratchModal"
        modal-class="dp-modal page-layer raffle-modal-container"
        scrollable
        centered
        :hide-footer="true"
    >
        <template #modal-header="{ cancel }">
            <h5 class="modal-title">스크래치 이벤트</h5>
            <i class="modal-close" @click="cancel()">
                <img
                    src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                    alt="닫기"
                />
            </i>
        </template>
        <div class="page-layer__body raffle_modal">
            <!-- 응모하기 이전 -->
            <div class="scratch_container" v-if="!subscript">
                <div class="wrapper" style="background:#000">
                    <img
                        :src="require(`@assets.pc/img/promotion/grandopen/scratch${dateFormatter(ranNumber+1)}.jpg`)"
                        alt=""
                        style="opacity: 0.2;"
                    >
                    <div class="grand_btn" @click="goSubscript">응모하기</div>
                </div>
            </div>

            <!-- 응모하기 이후 실제 이벤트 발생 -->
            <div class="scratch_container" :key="forRefresh" v-else>
                <scratchable
                    v-if="cover"
                    v-slot="{ init }"
                    :brushOptions="brush"
                    :hideOptions="hide"
                    getPercentageCleared
                    @percentage-update="updatePoints"
                    :percentageStride="1000"
                >
                    <div class="wrapper">
                        <img
                        :src="require(`@assets.pc/img/promotion/grandopen/scratch${dateFormatter(ranNumber+1)}.jpg`)"
                        @load="init()"
                        alt=""
                        >
                    </div>
                </scratchable>
                <div class="wrapper" style="background:#000" v-else>
                    <img
                    :src="require(`@assets.pc/img/promotion/grandopen/scratch${dateFormatter(ranNumber+1)}.jpg`)"
                    alt=""
                    :style="{opacity:todaytryyn == 'N' ? '0.2' : '1'}"
                    >
                    <div class="msg_box">
                        <div class="main_block" v-html="resultMsg"></div>
                        <div class="btn_wrap">
                            <div class="btn" @click="$bvModal.hide('scratchModal')">닫기</div>
                            <div class="btn" v-if="todaytryyn == 'Y'" @click="getRanNumber">재 응모하기</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
    import VueScratchable from 'vue-scratchable';

    export default {
        components: {
            'scratchable' : VueScratchable,
        },
        data() {
            return {
                //isLogin: "T", // [D]프론트 테스트용 로그인 코드입니다.

                rewardamount : 1000000,//당첨금액,
                todayjoincount : 0,//금일 시도횟수,
                rewardsuccessyn : "N",//당첨여부,
                todaytryyn : "Y",//금일 참여가능여부,

                resultMsg: '',
                forRefresh: 0,
                subscript: false,
                cover: true,
                ranNumber: 0,
                percentage: 0,
                hide: {
                    type: 'color',
                    value: 'rgba(1, 0, 0, 0.8)'
                },
                brush: {
                    size: 100,
                    shape: 'round',
                },
            }
        },
        methods: {
            goSubscript() {
				let params = {
					promoidx: 9
				}
                
                /*if(this.isLogin == "T"){
                    if(this.todaytryyn == "Y"){
                        this.subscript = true;
                    }else{
                        this.subscript = true;
                        this.resultMsg = '오늘 응모 가능한 횟수가<br>모두 소진되었습니다.<br>(ID당 하루 5번 까지 가능)'
                        this.resultOpen();
                    }
                }else{
                    alert('로그인 후 가능합니다.')
                }*/

				if (!this.$store.state.isLogin) {
					this.$eventBus.$emit('confirm', '로그인', '로그인이 필요합니다. 로그인 하시겠습니까?',()=>{
						this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
						this.$router.push({name : 'member-login'});
					});
					return;
				}

				this.$http.post("/event/getScratchEventJoinCount", params)
					.then(result => {
						if (result.statusCode === 200) {
							let data = result.data;
	
							// 스크래치 이벤트 결과 VALUE
							this.todayjoincount = data.todayjoincount;
							this.todaytryyn = data.todaytryyn;
                            
                            // [D]로그인 체크 후 로그인 상태일 경우 아래 실행
                            if(this.todaytryyn == "Y"){
                                this.subscript = true;
                            }else{
                                this.subscript = true;
                                this.resultMsg = '오늘 응모 가능한 횟수가<br>모두 소진되었습니다.<br>(ID당 하루 5번 까지 가능)';
                                this.resultOpen();
                            }
						}
					})
					.catch(error => {
						this.$util.debug(error);
				});

            },
            updatePoints(curPercentage) {
                if(!this.cover) return;
                if(curPercentage > 50){
                    this.resultOpen();
                    return;
                }
                
                if(this.percentage !== curPercentage) this.percentage = curPercentage; 
            },
            dateFormatter(t){return t < 10 ? `0${t}` : t},
            resultOpen(){
                this.cover = false;
                
                if(this.todaytryyn == "N"){
                    return;
                }
                
				let params = {
					promoidx: 9
				}
				this.$http.post("/event/joinScratchEvent", params)
					.then(result => {
						if (result.statusCode === 200) {

							let data = result.data;
	
							// 스크래치 이벤트 결과 VALUE
							this.rewardamount = data.rewardamount;
							this.todayjoincount = data.todayjoincount;
							this.rewardsuccessyn = data.rewardsuccessyn;
							this.todaytryyn = data.todaytryyn;
                            
                            // [D]로그인 체크 후 로그인 상태일 경우 아래 실행
                            if(this.todaytryyn == "Y"){
                                this.subscript = true;
                            }else{
                                this.subscript = true;
                                this.resultMsg = '오늘 응모 가능한 횟수가<br>모두 소진되었습니다.<br>(ID당 하루 5번 까지 가능)';
                                this.resultOpen();
                            }
						}

                        if(this.$store.state.isLogin && this.todaytryyn == "Y") {
                            if(this.rewardsuccessyn == "Y"){
                                // caseA 당첨
                                this.resultMsg = `즉시 사용 가능한 <strong>${this.numberWithCommas(this.rewardamount)}원</strong>을<br> 발견하였습니다.<br><span style='font-size:14px; color:#666;'}>오늘 남은 응모횟수: ${5-this.todayjoincount}번</span>`;
                            }else{
                                // caseB 낙첨
                                this.resultMsg = `아쉽게도 당첨되지 않으셨네요.<br><span style='font-size:14px; color:#666;'}>오늘 남은 응모횟수: ${5-this.todayjoincount}번</span>`;
                            }
                        }
					})
					.catch(error => {
						this.$util.debug(error);
				});

            },
            getRanNumber() {
                this.resultMsg = '';
                
                let ranNum = Math.floor(Math.random() * 10);
                while (ranNum === this.ranNumber) {
                    ranNum = Math.floor(Math.random() * 10);
                }
                
                if(ranNum !== this.ranNumber){
                    this.ranNumber = ranNum;
                    this.forRefresh ++;
                    this.cover = true;
                }

                if(this.todaytryyn == "Y"){
                    this.goSubscript();
                    return;
                }
            },
            // 가격 콤마처리
			numberWithCommas(x) {
				return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			},
        },
        mounted() {
            alert("이벤트가 종료되었습니다. 감사합니다.");
            cancel();
        }
    }
</script>
<style scoped>
    .scratch_container {max-width:100%; margin:0 auto;}
    .scratch_container img {max-width:100%;}
    .wrapper {position: relative;}
    .msg_box {position: absolute; top: 50%; left: 50%; transform: translate3d(-50%,-50%,0);}
    .msg_box .main_block {background:#ffccff; color:#000; font-size: 18px; padding:30px; z-index: 3; cursor:pointer; font-weight: 400; text-align: center; box-shadow: 3px 3px 9px rgba(0,0,0,0.5); margin-bottom: 20px;}
    .msg_box .btn_wrap {display:flex; justify-content: center;}
    .msg_box .btn {background:#ffccff; color:#000; font-size: 18px; box-shadow: 3px 3px 9px rgba(0,0,0,0.5); border-radius:0; margin:0 10px;}
    .grand_btn {position: absolute; background:#ffccff; color:#000; font-size: 24px; padding:10px 30px; z-index: 3; top: 50%; left: 50%; transform: translate3d(-50%,-50%,0); cursor:pointer; font-weight: 600; box-shadow: 3px 3px 9px rgba(0,0,0,0.5);}

    @media (max-width: 500px){
        .msg_box {position: absolute; top: 50%; left: 50%; transform: translate3d(-50%,-50%,0); width: 80%;}
        .msg_box .main_block {background:#ffccff; color:#000; font-size: 14px; padding:30px; z-index: 3; cursor:pointer; font-weight: 400; text-align: center; box-shadow: 3px 3px 9px rgba(0,0,0,0.5); margin-bottom: 20px;}
        .msg_box .btn_wrap {display:flex; justify-content: center;}
        .msg_box .btn {background:#ffccff; color:#000; font-size: 14px; box-shadow: 3px 3px 9px rgba(0,0,0,0.5); border-radius:0; margin:0 10px;}
        .grand_btn {position: absolute; background:#ffccff; color:#000; font-size: 14px; padding:10px 30px; z-index: 3; top: 50%; left: 50%; transform: translate3d(-50%,-50%,0); cursor:pointer; font-weight: 600; box-shadow: 3px 3px 9px rgba(0,0,0,0.5);}
    }
</style>