<template>
    <!-- 구매확정 모달 -->
    <b-modal
    id="confirmBuyModal"
    modal-class="dp-modal pop-over confirm-buy-modal"
    centered
    >
    <!-- 구매확정 모달 헤더 -->
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">구매확정</h5>
        <i class="modal-close" @click="cancel()">
        <img
            src="@/assets/common/icon/icon-close-modal-30px.svg"
            alt="닫기"
        />
        </i>
    </template>

    <!-- 구매확정 모달 바디 -->
    <div class="popover-layer__body">
        <div class="confirm-modal__text-area text-center">
        <p class="modal-text01" :class="{'dp-mb-10':$store.state.platform != 'MAC001'}">상품을 받으시고 만족하셨나요?</p>
        <p class="modal-text02" :class="{'dp-mb-20':$store.state.platform != 'MAC001'}" v-if="reservePoint > 0">
            지금 구매확정을 하시면<br />{{$util.maskComma(reservePoint)}}원이 적립됩니다.
        </p>
        </div>
        <div class="confirm-modal__item-area">
        <product-order
            :is-badge="false"
            :is-footer="false"
            :is-status="false"
            :product-info="product"
        />
        </div>
    </div>

    <!-- 구매확정 모달 푸터 -->
    <template #modal-footer="{ cancel }">
        <div class="btn-group">
        <b-button
            class="dp-btn not-hover squared"
            variant="white"
            @click="cancel()"
            >취소</b-button
        >
        <b-button
            class="dp-btn text-white apply-btn"
            variant="gray-800 btn-h44"
            style="margin-left:0px;"
            @click="confirm"
            >구매확정</b-button
        >
        </div>
    </template>
    </b-modal>
    <!-- // 구매확정 모달 -->
</template>

<script>
export default {
    props: {
        param: { type: Object },
        fnConfirm : {type : Function},
        fnCancel : {type : Function},
    },
    data() {
        return {
            product : {},
            reservePoint : 0,
            page : '/mypage'
        }
    },
    mounted(){
        this.product = this.param.param;
        if(!this.$store.state.isLogin){
            this.page = '/nonemember'
        } else {
            this.getReservePoint();
        }
        
    },
    methods: {
        getReservePoint() {
            const param = {
                orderidx : this.product.orderidx,
                ordgdidx : this.product.ordgdidx,
                isloading: false
            }
            this.$http.post('/mypage/order/reserve', param).then(result => {
                if (result.statusCode == 200) {
                    this.reservePoint = result.data.paypoint;
                }
            });
        },
        confirm() {
            const param = {
                ordgdidx : this.product.ordgdidx
            }
            this.$http.post(this.page + '/order/confirm', param).then(result => {
                if (result.statusCode == 200) {
                    this.$toast.clear();
                    this.$toast.open("구매확정이 완료되었습니다.");
                    this.fnConfirm();
                    this.$bvModal.hide('confirmBuyModal');
                }
            });
        }
    }
}
</script>

<style>
@media (min-width: 600px) {
  .confirm-buy-modal .product-info{width: calc(100% - 156px) !important;}
}

</style>