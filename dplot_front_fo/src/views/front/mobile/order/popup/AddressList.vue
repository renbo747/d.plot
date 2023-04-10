<template>
    <div>
        <!-- 배송지 목록 모달 -->
        <b-modal
        id="addressListModal"
        :modal-class="{'dp-modal pop-over yes-shipping-list-modal': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer yes-shipping-list-modal': $store.state.platform != 'MAC001'}"
        hide-footer
        scrollable
        centered
        >
        <!-- Popover Modal Header -->
        <template #modal-header="{ cancel }">
            <h5 class="modal-title">배송지 목록</h5>
            <i class="modal-close" @click="cancel()">
            <img
                src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                alt="닫기"
            />
            </i>
        </template>

        <!-- PopoverModal Body -->
        <div class="page-layer__body">
            <div class="shipping-list__area">
            <div class="no-shipping-list__body" v-if="addressList.length == 0">
                <div class="icon-pick-area">
                <i class="dp-icon icon-pick xl02"></i>
                </div>
                <p class="mb-0 dp-p-sm no-shipping-list__text">
                등록된 배송지가 없어요
                </p>
            </div>
            <ul class="list-style-none shipping-list" v-else>
                <li v-for="(list, index) in addressList" :key="index">
                <shipping-list 
                :address="list" 
                @selected="selectAddr"
                @modify="modifyAddr"
                @remove="removeAddr"
                />
                </li>
            </ul>
            </div>
            <b-button
            class="dp-btn text-white dp-btn-icon"
            variant="gray-800"
            squared
            @click="address('add')"
            v-b-modal.shippingRegistrationModal
            >
            <span>배송지 추가</span>
            <i class="dp-icon icon-plus"></i>
            </b-button>
        </div>
        </b-modal>
        <!-- // 배송지 목록 모달 -->
        <component 
            :is="modalInfo.comp" 
            :param="modalInfo.param"
            :fnConfirm="modalInfo.fnConfirm" 
            :fnCancel="modalInfo.fnCancel"
            :key="skey" />
    </div>
</template>

<script>
import Address from "./Address.vue";
export default {
    name : 'Address',
    data() {
        return {
            skey : 0,
            addressList: [],
            modalInfo : {
                comp : null,
                param : null,
                fnConfirm : () => {},
                fnCancel : () => {}
            },
        };
    },
    props : {
      fnConfirm : Function
    },
    mounted(){
        this.getAddressList();
    },
    methods: {
        /*************************
        * 배송지 목록 조회
        *************************/
        getAddressList(){
            this.$http.post('mypage/address/list',{}).then(result => {
              
            if (result.statusCode == 200) {
                this.addressList = result.data.addresslist;
            }
          });
        },
        /**
         * 배송지 수정
         */
        modifyAddr(idx) {
            this.address('edit', idx);
        },
        /**
         * 배송지 삭제
         */
        removeAddr(idx) {
            this.$eventBus.$emit('confirm','확인','선택하신 배송지를 삭제하시겠습니까?',()=>{
                this.$http.post('/mypage/address/del', {idx : idx}).then(result => {
                    if (result.statusCode == 200) {
                        this.$toast.clear();
                        this.$toast.open('선택하신 배송지가 삭제되었어요');
                        this.getAddressList();
                    }
                });
            });
        },
        /*************************
        * 배송지 등록 페이지 이동
        * type:'add':추가,'edit':수정
        * idx: 선택 배송지 idx
        *************************/
        address(type, idx){
            this.skey = Date.now();
            this.modalInfo.comp = Address;
            this.modalInfo.param = {
                type : type,
                idx : idx,
            };
            this.modalInfo.fnConfirm = this.selectAddr;
            
            this.$nextTick(function () {
                this.$bvModal.show('addressModal');
            })
        },
        /*************************
        * 배송지 선택
        *************************/
        selectAddr(item) {
            this.fnConfirm(item);
            this.$bvModal.hide('addressListModal');
        }
    },
}
</script>