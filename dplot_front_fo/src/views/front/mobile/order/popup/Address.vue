<template>
    <div>
        <!-- 배송지 등록 모달 -->
        <b-modal
        id="addressModal"
        :modal-class="{'dp-modal pop-over shipping-registration-modal': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer shipping-registration-modal': $store.state.platform != 'MAC001'}"
        hide-footer
        scrollable
        centered
        >
        <!-- Page Modal Header -->
        <template #modal-header="{ cancel }">
            <h5 class="modal-title">{{title}}</h5>
            <i class="modal-close" @click="cancel()">
            <img
                src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                alt="닫기"
            />
            </i>
        </template>

        <!-- Page Modal Body -->
        <div class="page-layer__body">
            <form class="form-area">
            <div class="form-group">
                <base-input-horizon
                label="받는사람"
                v-model="addressInfo.title"
                :is-label="true"
                placeholder="이름을 입력해주세요"
                :max-length="20"
                />
            </div>
            <div class="form-group">
                <base-input-horizon
                label="연락처"
                :type="'number'"
                v-model="addressInfo.mobile"
                :is-label="true"
                placeholder="휴대전화 번호 - 없이 입력"
                :max-length="11"
                />
            </div>
            <div class="form-group">
                <div class="d-flex justify-content-between mb-10">
                <base-input-horizon
                    label="주소"
                    v-model="addressInfo.post"
                    :is-label="true"
                    :is-readonly="true"
                    placeholder=""
                />
                <b-button
                    class="dp-btn text-white post-find-btn"
                    variant="gray-800"
                    squared
                    @click="execDaumPostModal"
                >
                    <span>우편번호 찾기</span>
                </b-button>
                </div>
                <base-input placeholder="" :is-readonly="true" class="mb-10" v-model="addressInfo.addrroad" />
                <base-input placeholder="상세주소 입력" v-model="addressInfo.addrdetailroad"/>
            </div>
            </form>
            <base-checkbox 
                label="기본 배송지로 설정" 
                class="reception-agree"
                v-model="isdefault" 
                :id="'address_' + addressInfo.idx"
                @change="changeDefault()"/>
            <div class="btn-area">
            <b-button class="dp-btn text-white" variant="gray-800" squared @click="saveAddress">
                <span>저장하기</span>
            </b-button>
            </div>
        </div>
        </b-modal>
        <!-- // 다음주소검색 모달 -->
        <component
            :key="skey"
            :is="modalInfo.comp" 
            :param="modalInfo.param"
            :fnConfirm="modalInfo.fnConfirm" 
            :fnCancel="modalInfo.fnCancel" />
    </div>
</template>

<script>
import Post from "@views.common/components/ui/modal/Post.vue"
export default {
    data() {
        return {
            skey : 0,
            title : '',
            isdefault:false,
            addressInfo:{
                idx   : null,           //일련번호
                title : null,           //주소명(VAR)
                tel   : null,           //전화번호(VAR)
                mobile: null,           //휴대폰번호(VAR)
                post  : null,           //우편번호(VAR)
                addr  : null,           //주소(VAR)
                addrdetail: null,       //주소_상세(VAR)
                isdefault : 'F',        //기본주소여부(CHAR) T,F
                addrroad  : null,       //주소_도로명(VAR)
                addrdetailroad: null,   //주소_상세도로명(VAR)
                sigungucode : null,
                buildingcode : null,
                roadnamecode : null
            },
            tempInfo:{},
            modalInfo : {
                comp : null,
                param : null,
                fnConfirm : () => {},
                fnCancel : () => {}
            },
        }
    },
    props : {
        param : Object,
        fnConfirm : Function
    },
    mounted(){
        this.title = this.param.type == 'add' ? "배송지 등록" : "배송지 수정";
        if (this.param.type == 'edit') {
            this.addressInfo.idx = this.param.idx;
            this.getAddressList();
        }else {
            this.addressInfo.idx = null;
            this.tempInfo = this.$util.deepClone(this.addressInfo);
        }
    },
    methods: {
        /*************************
        * 배송지 목록 조회
        *************************/
        getAddressList(){
          this.$http.post('mypage/address/detail',{idx: this.addressInfo.idx}).then(result => {
            if (result.statusCode == 200) {
              this.addressInfo = result.data.address;
              this.tempInfo = this.$util.deepClone(this.addressInfo);
            }
          });
        },
        /*************************
        * 배송지 저장
        *************************/
        saveAddress(){
          //지번상세주소와 도로명상세주소는 같음
          this.addressInfo.addrdetail = this.addressInfo.addrdetailroad;

          this.$http.post('mypage/address/save',this.addressInfo).then(result => {
            if (result.statusCode == 200) {
              this.fnConfirm(result.data.address);
              this.$bvModal.hide('addressModal');
            }else {
               this.$eventBus.$emit('alert', '알림', result.message);
            }
          });
        },
        /*************************
        * 다음 주소 API 리턴 값 바인딩 처리
        *************************/
        setBindAddr(address){
            this.addressInfo.addrroad = this.$util.isBlank(address.roadAddress) ? address.autoRoadAddress: address.roadAddress;      //도로명주소
            this.addressInfo.addr     = this.$util.isBlank(address.jibunAddress) ? address.autoJibunAddress : address.jibunAddress; //지번주소
            this.addressInfo.post     = address.zonecode;     //우편번호
            this.addressInfo.sigungucode = address.sigunguCode   //시군구코드
            this.addressInfo.buildingcode = address.buildingCode //빌딩코드
            this.addressInfo.roadnamecode = address.roadnameCode //도로명코드
        },
        /*********************
        * 다음 주소찾기 API 호출
        *********************/
        execDaumPostModal(){
            this.skey = Date.now()
            this.modalInfo.comp = Post;
            this.modalInfo.param = {}
            this.modalInfo.fnConfirm = this.setBindAddr;
            
            this.$nextTick(function () {
                this.$bvModal.show('postModal');
            })
        },
        changeDefault(){
            this.addressInfo.isdefault = this.isdefault ? "T":"F"; 
        }
    },
    
}
</script>

<style>

</style>