<template>
    <!-- 주문상품 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1200px;">
            <div class="pop-header">
                <h2>주문상품</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">
                        <span>주문번호 : {{ ordno }}</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="7.5%" v-if="!isPartner" /><!-- 판매구분 -->
                            <col width="9%" v-if="!isPartner" /><!-- 파트너사 -->
                            <col width="10%" /><!-- 상품코드 -->
                            <col width="7%" /><!-- 단품코드 -->
                            <col width="7%" /><!-- 상품순번 -->
                            <col width="62px" /><!-- 상품이미지 -->
                            <col width="" /><!-- 상품명 -->
                            <col width="11%" /><!-- 옵션 -->
                            <col width="7%" /><!-- 주문수량 -->
                            <col width="8%" /><!-- 판매단가 -->
                            <col width="8%" /><!-- 판매금액 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th v-if="!isPartner" >판매구분
                                    <button type="button" class="sort" :value="sortData.ispbgoods"
                                        :class="[{up : sortData.ispbgoods === 'ispbgoods_asc'}, {down : sortData.ispbgoods === 'ispbgoods_desc'}]"
                                        @click="sortToggle(sortData.ispbgoods)"></button>
                                </th>
                                <th v-if="!isPartner" >파트너사
                                    <button type="button" class="sort" :value="sortData.dealername"
                                        :class="[{up : sortData.dealername === 'dealername_asc'}, {down : sortData.dealername === 'dealername_desc'}]"
                                        @click="sortToggle(sortData.dealername)"></button>
                                </th>
                                <th>상품코드
                                    <button type="button" class="sort" :value="sortData.goodscode"
                                        :class="[{up : sortData.goodscode === 'goodscode_asc'}, {down : sortData.goodscode === 'goodscode_desc'}]"
                                        @click="sortToggle(sortData.goodscode)"></button>
                                </th>
                                <th>단품코드
                                    <button type="button" class="sort" :value="sortData.optioncode"
                                        :class="[{up : sortData.optioncode === 'optioncode_asc'}, {down : sortData.optioncode === 'optioncode_desc'}]"
                                        @click="sortToggle(sortData.optioncode)"></button>
                                </th>
                                <th>상품순번
                                    <button type="button" class="sort" :value="sortData.goodsturn"
                                        :class="[{up : sortData.goodsturn === 'goodsturn_asc'}, {down : sortData.goodsturn === 'goodsturn_desc'}]"
                                        @click="sortToggle(sortData.goodsturn)"></button>
                                </th><th colspan="2">상품명
                                    <button type="button" class="sort" :value="sortData.goodsname"
                                        :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                                        @click="sortToggle(sortData.goodsname)"></button>
                                </th>
                                <th>옵션
                                    <button type="button" class="sort" :value="sortData.optionconts"
                                        :class="[{up : sortData.optionconts === 'optionconts_asc'}, {down : sortData.optionconts === 'optionconts_desc'}]"
                                        @click="sortToggle(sortData.optionconts)"></button>
                                </th>
                                <th>주문수량
                                    <button type="button" class="sort" :value="sortData.ordcnt"
                                        :class="[{up : sortData.ordcnt === 'ordcnt_asc'}, {down : sortData.ordcnt === 'ordcnt_desc'}]"
                                        @click="sortToggle(sortData.ordcnt)"></button>
                                </th>
                                <th>판매단가
                                    <button type="button" class="sort" :value="sortData.price"
                                        :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                                        @click="sortToggle(sortData.price)"></button>
                                </th>
                                <th>판매금액
                                    <button type="button" class="sort" :value="sortData.totprice"
                                        :class="[{up : sortData.totprice === 'totprice_asc'}, {down : sortData.totprice === 'totprice_desc'}]"
                                        @click="sortToggle(sortData.totprice)"></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody v-if="list.length > 0">
                            <tr v-for="item in list" :key="item.ordgdidx" >
                                <td v-if="!isPartner" >{{ item.ispbgoodsname }}</td>
                                <td v-if="!isPartner" >{{ item.dealername }}</td>
                                <td>{{ item.goodscode }}</td>
                                <td>{{ item.optioncode }}</td>
                                <td>{{ item.goodsturn }}</td>
                                <td>
                                    <div class="img-thumb size60"  :class="{'no-image': $util.isNull(item.fullpath)}">
                                        <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                    </div>
                                </td>
                                <td class="left no-left">{{ item.goodsname }}</td>
                                <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                                <td>{{ $util.maskComma(item.ordcnt) }}</td>
                                <td class="right">{{ $util.maskComma(item.price) }}</td>
                                <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td :colspan="isPartner? 9:11">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn darkgray" @click="closePopup">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /주문상품 팝업 -->
</template>

<script>

export default {
    name: 'OrderGoodsPopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            isPartner: '',
            dealerno: '',
            ordno: '',  // 주문번호
            sortData: {
                ispbgoods: 'ispbgoods_asc',     // 판매구분
                dealername: 'dealername_asc',   // 판매사명
                goodscode: 'goodscode_asc',     // 상품코드
                optioncode: 'optioncode_asc',   // 단품코드
                goodsturn: 'goodsturn_asc',     // 상품순번
                goodsname: 'goodsname_asc',     // 상품명
                optionconts: 'optionconts_asc', // 옵션내용
                ordcnt: 'ordcnt_asc',           // 주문수량
                price: 'price_asc',             // 판매단가
                totprice: 'totprice_asc'        // 판매가
            },
            list: []    // 주문상품목록
        }
    },
    mounted() {
        this.isPartner = this.params.isPartner;
        this.dealerno = this.params.dealerno;
        this.ordno = this.params.ordno;
        this.selectOrderGoodsList();
    },
    methods: {
        // 주문상품목록 조회
        selectOrderGoodsList: function() {
            let params = {
                ordno: this.ordno,
                dealerno: this.dealerno
            };
            this.$http.post('/admin/order/manage/goods/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.list = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 정렬
        sortToggle(key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.sortData[sortKey] = sortName;

            this.list.sort((a, b) => {
                a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                
                if (a[sortKey] < b[sortKey]) {
                    return sortOrder == 'asc'? -1: 1;
                } else if (a[sortKey] > b[sortKey]) {
                    return sortOrder == 'asc'? 1: -1;
                }
                return 0;
            });
        },
        // 팝업닫기
        closePopup(params){
            if(typeof(this.callbackFn) === 'function') {
                this.callbackFn(params);
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>