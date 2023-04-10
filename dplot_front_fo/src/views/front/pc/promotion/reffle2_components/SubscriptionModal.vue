<template>
    <b-modal
        id="raffleSubscription"
        modal-class="dp-modal page-layer raffle-modal-container"
        scrollable
        centered
        :hide-footer="true"
    >
    <!-- Page Modal Header -->
        <template #modal-header="{ cancel }">
            <!--h5 class="modal-title">{{param.month}}월 {{param.week}}주차 래플 응모하기</h5 -->
            <h5 class="modal-title">래플 응모하기</h5>
            <i class="modal-close" @click="cancel()">
                <img
                    src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                    alt="닫기"
                />
            </i>
        </template>

        <!-- Page Modal Body -->
        <div class="page-layer__body raffle_modal">
            <div class="prd_img">
                <div class="prd_brand">
                    <img :src="param.item.brandlogoimg" alt="">
                </div>
                <div class="prd_big_thumb">
                    <img :src="param.item.itemimg" alt="">
                </div> 
            </div>
            <h5><span class="ponit_color">{{param.item.name}}</span> 래플에 응모하시겠습니까?</h5>
            <ul>
                <li>-래플은 선착순이 아닌 추첨제로 진행됩니다.</li>
                <li>-래플 이벤트를 카카오톡으로 공유하면 당첨 확률이 올라갑니다.</li>
                <li>-당첨자 본인만 구매가 가능합니다.</li>
                <li>-래플 상품은 한정 상품으로 구매 후 교환 및 환불이 불가능합니다.</li>
                <li>-반드시 정해진 구매시간 내에 구매하셔야 하며 미 구매 시 당첨이 취소됩니다.</li>
            </ul>
            <base-checkbox
                label="안내사항을 확인 하였으며 이에 동의합니다."
                id = "baseAgree"
                v-model="baseAgree"
            />
            <base-checkbox
                label="해당 이벤트를 참여하신 경우 마케팅 수신 활용에 동의하신 것으로 간주됩니다."
                id = "marketingAgree"
                v-model="marketingAgree"
            />
            <b-button
                class="dp-btn not-hover modify-btn"
                variant="outline-gray-800 type02"
                @click="subscriptionRaffle"
                squared
            >
                <span>응모하기</span>
            </b-button>
        </div>
    </b-modal>
</template>

<script>
	import RaffleModalVue from './RaffleModal.vue';
    export default {
        props: {
            param: Object,
        },        
        data() {
            return {
                raffleData: this.param,
                baseAgree: false,
                marketingAgree: false,
            }
        },
        methods: {
			callRaffleResultModal(){
				this.$eventBus.$emit('showModal', RaffleModalVue, "raffle-modal");
			},            
            subscriptionRaffle() {
                if(this.baseAgree && this.marketingAgree){
                    console.log('응모 진행 eventidx:'+this.raffleData.eventidx);
                    let params = {
					eventidx : this.raffleData.eventidx,
                    }
                    if(this.raffleData.eventidx==0){
                        alert("진행중인 이벤트가 없습니다.");
                        $bvModal.hide('raffleSubscription');
                    }
                    this.$http.post("/event/raffleApply", params)
					.then(result => {
						if (result.statusCode === 200) {
						let data = result.data;

						alert("응모 되었습니다.");
                        
                        this.callRaffleResultModal();
                                           
                        $bvModal.hide('raffleSubscription');
						}
                        else if (result.statusCode === 201) {
						let data = result.data;
                        
						alert(result.message);

                        this.callRaffleResultModal();

                        this.$bvModal.hide('raffleSubscription');
						}                        
					})
					.catch(error => {
						this.$util.debug(error);
                        this.$bvModal.hide('raffleSubscription');

                    });
                }else{
                    alert('모든 안내사항에 동의해주세요.');
                }
            }
        }
    }
</script>

<style scoped>
    .raffle_modal {max-width:100%; display: flex; justify-content: center; align-items: center; flex-direction: column;}
    .raffle_modal .prd_img {position: relative; max-width:300px; margin-bottom: 20px;}
    .raffle_modal .prd_img img {max-width:100%;}
    .raffle_modal .prd_img .prd_brand{text-align:left; max-width:100px;}
	.raffle_modal .prd_img .prd_big_thumb{position:relative; border:1px solid #ddd;}
    .raffle_modal h5 {margin-bottom: 20px; word-break: keep-all; text-align: center;}
    .raffle_modal ul {padding:0; max-width:90%;}
    .raffle_modal ul li {list-style: none; font-size: 14px;}
    .raffle_modal .ponit_color {color:#eb2929;}
    .base-checkbox {width:90%; font-size: 13px; text-align: left; letter-spacing: -1px; word-break: keep-all;}
    .base-checkbox + .base-checkbox {margin-top: 10px;}
    .raffle_modal button {margin-top: 20px; max-width:90%;color: #222;}
</style>