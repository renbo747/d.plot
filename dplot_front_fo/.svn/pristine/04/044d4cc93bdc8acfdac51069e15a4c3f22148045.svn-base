<template>
    <div>
        <!-- 옵션 선택 모달 -->
        <b-modal
            id="optionSelectModal"
            modal-class="dp-modal pop-over option-select-modal"
            hide-footer
            centered
            @show="show"
        >
            <template #modal-header="{ cancel }">
            <h5 class="modal-title">옵션선택</h5>
            <i class="modal-close" @click="cancel()">
                <img
                src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                alt="닫기"
                />
            </i>
            </template>
            <!-- Popover Body -->
            <div class="popover-layer__body">
            <div class="option-select__container">
                <div class="option-select-modal__area">
                <div class="option-select__product-info">
                    <p class="mb-0 atten-new seller-name">{{ product.brandname }}</p>
                    <p class="mb-0 product-name">{{ product.goodsname }}</p>
                </div>

                <slot>
                    <div class="option-dropdown-area d-flex">
                    <base-dropdown 
                    v-for="(option, index) in itemOption" :key="index"
                    v-model="option.selected"
                    :placeholder="option.optionname" 
                    :options="option.detail"
                    @change="selectOption2(option, index, 'F', ...arguments)" />
                    </div>
                    <div class="add-option-dropdown-area" v-if="addItem.length > 0">
                    <p class="dp-p-sm font-weight-500 dp-mb-10 add-option__title">
                        추가 구성상품
                    </p>
                    <base-dropdown 
                        placeholder="선택해주세요"
                        v-model="selectedAddItem"
                        :options="addItem"
                        @change="selectAddItem2" />
                        <base-dropdown 
                        v-for="(option, index) in addItemOption" :key="index"
                        v-model="option.selected"
                        :placeholder="option.optionname" 
                        :options="option.detail"
                        @change="selectOption2(option, index, 'T', ...arguments)" />
                    </div>
                </slot>
                <div class="option-item-area">
                    <slot>
                    <ul class="bottom-sheet__option-ul list-style-none">
                        <li v-for="(item, index) in selectedItem" :key="index">
                        <option-item
                            :item="item"
                            :is-mobile="false"
                            @delete="handleRemove"
                            @restockClick="openRestock(item)"
                            v-if="item.istrash == 'F'"
                        />
                    </li>
                    </ul>
                    </slot>
                </div>
                <div class="product-price__area d-flex justify-content-end">
                    <span class="product-price__title">상품금액</span>
                    <span class="atten-new product-price"> {{$util.maskComma(getTotalOptionPrice)}} </span
                    ><span class="price-unit">원</span>
                </div>
                </div>
            </div>
            </div>
            <div class="popover-layer__footer">
            <div class="footer__btn-area d-flex justify-content-center">
                <b-button
                class="dp-btn btn-h48 cancel-btn"
                variant="outline-gray-800 type02"
                squared
                @click="cancel()"
                >
                <span>취소</span>
                </b-button>
                <b-button
                class="dp-btn text-white btn-h48 change-btn"
                variant="gray-800"
                squared
                @click="change()"
                >
                <span>변경하기</span>
                </b-button>
            </div>
            </div>
            <!-- // Popover Body -->
        </b-modal>
        <!-- // 배송지 목록 모달 -->
        <component 
            :is="modalInfo.comp" 
            :param="modalInfo.param"
            :fnConfirm="modalInfo.fnConfirm" 
            :fnCancel="modalInfo.fnCancel"
            :key="skey" />
        <!-- // 옵션 선택 모달 -->
    </div>
</template>

<script>
import BottomCommon from "@views.mobile/components/BottomCommon.js";
import RestockAlarm from "@views.pc/cart/popup/RestockAlarm.vue"

export default {
    mixins : [BottomCommon],
    props: {
        param: { type: Object },
        fnConfirm : {type : Function},
        fnCancel : {type : Function},
    },
    data() {
        return {
            modalInfo : {
                comp : null,
                param : null,
                fnConfirm : () => {},
                fnCancel : () => {}
            },
            skey : 0
        }
    },
    mounted() {
    },
    methods : {
        cancel(){
            this.$bvModal.hide('optionSelectModal');
        },
        change() {
            if(!this.$front.checkCartItem(this, this.selectedItem)){
                return;
            }
            this.fnConfirm();
            this.$bvModal.hide('optionSelectModal');
        },
        show() {
            this.product = this.param.productInfo;
            this.selectedItem = this.param.items;
            this.getOptionList(this.product.goodsno, 'F');
            this.getAddItemsList();
            this.initSelectedOption();
        },
        //재입고 모달 열기
        openRestock(item){
            if(this.$store.state.isLogin) {
                this.skey = Date.now();
                this.modalInfo.comp = RestockAlarm;
                this.modalInfo.param = item;
                
                this.$nextTick(function () {
                    this.$bvModal.show('restockAlarmModal');
                })
                //this.$eventBus.$emit('showModal', RestockAlarm, 'restockAlarmModal', item);
            } else {
                this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
                    this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, params : this.$route.params});
                    this.$router.push({name : 'member-login'});
                });
            }
        }
    }
}
</script>