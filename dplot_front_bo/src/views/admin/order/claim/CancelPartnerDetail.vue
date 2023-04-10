<template>
    <!-- 파트너 취소상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1600px;">
            <div class="pop-header">
                <h2>주문취소상세{{ ' - ' + claimInfo.cncstatusnm }}</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0">
                    <div class="clearfix">
                        <div class="fl">[{{ orderInfo.ordpathtypename }}] 주문번호 : <strong>{{ orderInfo.ordno }}</strong></div>
                        <div class="fr txt-gray">
                            <span>주문일 : {{ orderInfo.orderdate }}</span>
                            <span class="left-bar">취소신청일 : {{ orderInfo.clmreqdate }}</span>
                        </div>
                    </div>
                </div>
                <div class="clearfix mt20">
                    <div class="bar-title small fl">주문취소상품 {{ '[부분취소' +orderInfo.clmturn+ '차]' }}</div>
                </div>
                <template v-for="row in claimGoodsList">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0" :key="'list_'+row.ordgdidx">
                        <caption>취소상품 목록</caption>
                        <colgroup>
                            <col width="6.5%" /><!-- 상품코드 -->
                            <col width="4%" /><!-- 단품코드 -->
                            <col width="62px" /><!-- 상품이미지 -->
                            <col width="" /><!-- 상품명 -->
                            <col width="10%" /><!-- 옵션 -->
                            <col width="6%" /><!-- 주문수량 -->
                            <col width="6%" /><!-- 취소수량 -->
                            <col width="7%" /><!-- 판매단가 -->
                            <col width="7%" /><!-- 판매금액 -->
                            <col width="7%" /><!-- 취소금액 -->
                            <col width="12%" /><!-- 취소상태 -->
                            <col width="8%" /><!-- 취소번호 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>상품코드</th>
                                <th>단품코드</th>
                                <th colspan="2">상품명</th>
                                <th>옵션</th>
                                <th>주문수량</th>
                                <th>취소수량</th>
                                <th>판매단가</th>
                                <th>판매금액</th>
                                <th>취소금액</th>
                                <th>취소상태</th>
                                <th>취소번호</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{{ row.goodscode }}</td>
                                <td>{{ row.optioncode }}</td>
                                <td>
                                    <div class="img-thumb size60 link" @click="goFrontGoodsDetail(row.goodscode)" :class="{'no-image': $util.isNull(row.fullpath)}">
                                        <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                                    </div>
                                </td>
                                <td class="left no-left">
                                    <a href="javascript:void(0);" class="link" @click="goGoodsDetail(row.goodsno)">{{ row.goodsname }}</a>
                                </td>
                                <td style="white-space: pre-wrap">{{ row.optionname }}</td>
                                <td>{{ $util.maskComma(row.bfordcnt) }}</td>
                                <td>{{ $util.maskComma(row.clmcnt) }}</td>
                                <td class="right">{{ $util.maskComma(row.price) }}</td>
                                <td class="right">{{ $util.maskComma(Number(row.price) * Number(row.bfordcnt)) }}</td>
                                <td class="right">{{ $util.maskComma(Number(row.price) * Number(row.clmcnt)) }}</td>
                                <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(row.clmgdidx)">{{ row.boclmstatusnm }}</a></td>
                                <td>{{ row.clmno }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellpadding="0" cellspacing="0" class="gray-tb" :key="'reason_'+row.ordgdidx" >
                        <colgroup>
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>취소사유<i class="essential"></i></th>
                                <td>
                                    <select v-model="claimInfo.cnctype" disabled>
                                        <option value="">구분선택</option>
                                        <option v-for="item in commonCode.cnctype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                    </select>
                                    <input type="text" class="ml3" style="width: calc(100% - 156px);" v-model="claimInfo.clmdetail" maxlength="500" disabled/>
                                </td>
                            </tr>
                            <tr v-if="row.clmstatus!==$store.getters['ADMIN'].CNC_REJECT && $util.isNull(row.rejsubreason) && !$util.isNull(row.rejreason)">
                                <th>반려사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="반려사유입력" v-model="row.rejreason" maxlength="200" disabled/></td>
                            </tr>
                            <tr v-if="row.clmstatus===$store.getters['ADMIN'].CNC_WITHDRAW && !$util.isNull(row.dropreason)">
                                <th>철회사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="철회사유입력" v-model="row.dropreason" maxlength="200" disabled/></td>
                            </tr>
                            <tr v-if="isShowRejsubreason || row.clmstatus === $store.getters['ADMIN'].CNC_REQ_REJECT || !$util.isNull(row.rejsubreason)">
                                <th>반려승인요청사유<i class="essential"></i></th>
                                <td>
                                    <input type="text" :style="isEdit? 'width: calc(100% - 57px);' : 'width: 100%;'" placeholder="반려승인요청사유입력" v-model="row.rejsubreason" maxlength="200" :disabled="!isEdit"/>
                                    <button type="button" class="btn blue ml3" v-if="isEdit" @click="saveGoodsCncStatus($store.getters['ADMIN'].CNC_REQ_REJECT)">저장</button>
                                </td>
                            </tr>
                            <tr v-if="row.clmstatus===$store.getters['ADMIN'].CNC_REJECT && !$util.isNull(row.rejsubreason) && !$util.isNull(row.rejreason)">
                                <th>반려승인사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="반려승인사유입력" v-model="row.rejreason" maxlength="200" disabled/></td>
                            </tr>
                            <tr v-if="(row.clmstatus===$store.getters['ADMIN'].CNC_COMP_APPRV || row.clmstatus===$store.getters['ADMIN'].CNC_PRCCOMP) && !$util.isNull(row.rejdenyreason)">
                                <th>반려승인거부사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="반려승인거부사유입력" v-model="row.rejdenyreason" maxlength="200" disabled/></td>
                            </tr>
                        </tbody>
                    </table>
                </template>
                <div class="btn-group" v-if="isWrite">
                    <button type="button" class="btn big blue" v-if="isEdit" @click="saveGoodsCncStatus($store.getters['ADMIN'].CNC_COMP_APPRV)">승인완료</button>
                    <button type="button" class="btn big red" v-if="isEdit" @click="isShowRejsubreason=true">반려승인요청</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
    </div>
    <!-- /파트너 취소상세 팝업 -->
</template>

<script>
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import ClaimStatusHistoryPopup from '@views.admin/order/popup/ClaimStatusHistoryPopup.vue';

export default {
    name: 'admin.order.claim.cancel.detail',
    props: ['activeClmParam', 'activeOrderInfo'],
    components:{
        GoodsDetail,
    },
    data() {
        return {
            orderInfo: {},          // 주문정보
            claimInfo: {},          // 클레임정보
            claimGoodsList: [],     // 클레임상품목록
            clmInfo: {
                bforder: {},            // 이전클레임 정보
                bfpromotion: [],        // 이전프로모션
                bfgoodscoupon: [],      // 이전상품쿠폰
                bfdelivcoupon: [],      // 이전배송비쿠폰
                bfbasketcoupon: [],     // 이전장바구니쿠폰
                calinfo: {},            // 재계산정보
                aforder: {},            // 재계산정보
                afpromotion: [],        // 재계산프로모션
                afgoodscoupon: [],      // 재계산상품쿠폰
                afdelivcoupon: [],      // 재계산배송비쿠폰
                afbasketcoupon: [],     // 재계산장바구니쿠폰
                dispitems: [],          // 클레임신청(노출)
                items: [],              // 클레임상품
                afitems: [],            // 주문후상품
            },
            commonCode: {
                cncstatus: [],      // 취소상태
                cnctype: []         // 취소사유
            },
            activeUserNo: '',
            activeGoodsNo : false,
            isGoodsDetailShow: false,   // 상품상세 팝업 노출여부
            isShowRejsubreason: false,  // 반려승인요청사유 노출여부
            isRead : false,
            isWrite : false,
            isEdit: false,
        }
    },
    mounted() {
        this.orderInfo = this.activeOrderInfo;
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 공통코드 조회
                    this.getCommonCodeList();
                } else {
                    alert('페이지 접근 권한이 없습니다.');
                    this.onClose();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    methods: {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['CNCTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 취소정보 조회
                    this.searchOrdCancelInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 취소정보 조회
        searchOrdCancelInfo: function() {
            let params = { 
                orderidx: this.activeClmParam.orderidx,
                clmidx: this.activeClmParam.clmidx,
                clmgdidx: this.activeClmParam.clmgdidx, 
                clmtype: this.$store.getters['ADMIN'].CLM_CANCEL 
            };
            this.$http.post('/admin/order/claim/detail/info', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.claimInfo = data.claiminfo;
                    this.claimGoodsList = data.claimgoodslist;
                    for (const [key] of Object.entries(data)) {
                        this.clmInfo[key] = data[key];
                    }
                    let clmGoods = this.claimGoodsList[0];
                    if (clmGoods.clmstatus === this.$store.getters['ADMIN'].CNC_WAITING_APPRV) {
                        this.isEdit = true;
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 클레임상품 취소상태 저장
        saveGoodsCncStatus: function(reqStatus) {
            let obj = this.claimGoodsList[0];
            if (reqStatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT && (this.$util.isNull(obj.rejsubreason) || obj.rejsubreason.trim()==='')) {
                alert('반려승인요청사유는 필수입력사항 입니다.');
                return;
            }
            let cncstatusnm = '';
            if (reqStatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT) {
                cncstatusnm = '반려승인요청';
            } else if (reqStatus === this.$store.getters['ADMIN'].CNC_COMP_APPRV) {
                cncstatusnm = '승인';
            }
            if (confirm(cncstatusnm + ' 하시겠습니까?')) {
                let params = { 
                    orderidx: this.claimInfo.orderidx,
                    clmtype: this.claimInfo.clmtype,
                    clmidx: obj.clmidx,
                    clmgdidx: obj.clmgdidx,
                    cncstatus: reqStatus,
                    rejsubreason: reqStatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT? obj.rejsubreason : null,
                };
                this.$http.post('/admin/order/claim/save/status', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode === 200) {
                        alert(cncstatusnm  + '이 완료되었습니다.');
                        this.onClose(true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 재적용사은품 선택
        addReapplyGiftList: function() {
            let param = { reapplyGiftList: this.reapplyGiftList };
            this.$eventBus.$emit('modalShow', AddReapplyGiftPopup, param, 
                (result) => {
                    this.chkReapplyGiftList = result.list;
                    // // 팝업에서 가져온 결과 재적용사은품에 적용(이미 추가되어 있는 사은품 제외)
                    // let resultList = result.list;
                    // for (let i=0; i<resultList.length; i++) {
                    //     resultList[i].ischecked = false;
                    //     let existCnt = this.chkReapplyGiftList.filter(gift => {
                    //         return gift.giftidx == resultList[i].giftidx;
                    //     }).length;
                    //     if (existCnt == 0) {
                    //         this.chkReapplyGiftList.push(resultList[i]);
                    //     }
                    // }
                }
            );
        },
        // 재적용사은품 선택
        closeAddGiftPopup: function() {
            this.isShowAddGift = false;
        },
        // 클래임상태변경이력 팝업 오픈
        goClaimHistory: function(value) {
            let param = { clmgdidx: value };
            this.$eventBus.$emit('modalShow', ClaimStatusHistoryPopup, param, null);
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        },
        // 회원상세 팝업 오픈
        goMemDetail: function(value) {
            this.isShowMemDetail = true;
            this.activeUserNo = value;
        },
        // 회원상세 팝업 닫기
        closeMemDetail: function() {
            this.isShowMemDetail = false;
        },
        // 상품상세 팝업 오픈
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        // 상품상세 팝업 닫기
        closeGoodsDetail: function () {
            this.isGoodsDetailShow = false;
        },
        // 팝업닫기
        onClose(isreload) {
            if(typeof(isreload) === 'boolean' && isreload) {
                this.$emit('closeDetail', true);
            } else {
                this.$emit('closeDetail');
            }
        }
    }
}
</script>