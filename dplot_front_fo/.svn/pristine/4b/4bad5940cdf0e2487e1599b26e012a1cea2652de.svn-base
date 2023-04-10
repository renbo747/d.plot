<template>
    <!-- 구매사은품 Modal -->
    <b-modal
    id="giftModal"
    modal-class="dp-modal pop-over gift-modal"
    hide-footer
    centered
    >
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">구매 사은품</h5>
        <i class="modal-close" @click="cancel()">
        <img src="@assets.mobile/img/icon/icon-close-black-20px.svg" alt="닫기" />
        </i>
    </template>

    <p class="modal-text01">
      사은품은 당사 사정에 따라 변경되거나 조기 종료 될 수
      있습니다. 최종 사은품 지급은 구매 페이지에서 확인하실 수
      있습니다.
    </p>
    <ul class="row list-style-none modal-product__list"
        :class="{ four: param.length >= 4 }"
    >
        <li
        :class="{
            'col-6 grid-items': param.length >= 4,
            'col-4 horizontal-items': param.length < 4,
        }"
        v-for="(item, index) in param"
        :key="index"
        >
        <gift :gift-info="item" />
        </li>
    </ul>
    <div class="btn-group" v-if="url != ''">
        <b-button
        class="dp-btn text-white btn-h38"
        variant="gray-800"
        squared
        @click="goUrl"
        >
        <span>사은품 프로모션 더보기</span>
        </b-button>
    </div>
    </b-modal>
    <!-- 구매사은품 Modal -->
</template>

<script>
export default {
    props :{
        param : Array
    },
    data() {
        return {
            url : "",
        }
    },
    mounted() {
        this.$http.post('/common/code/list', { cmclass: "CONFTYPE", isloading: false }).then(result => {
          for (var i = 0; i < result.data.list.length; i++) {
            if(result.data.list[i].cmcode == 'GIFTURL') {
                this.url = result.data.list[i].detail;
                break;
            }
          }
        });
    },
    methods : {
        goUrl() {
            document.location.href = this.url;
        }
    }
}
</script>

<style>

</style>