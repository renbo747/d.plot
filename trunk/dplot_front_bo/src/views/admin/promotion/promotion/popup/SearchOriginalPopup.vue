<template>
    <!-- ERP 오리지널 조회 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header">
                <h2 class="half">ERP 오리지널 조회</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="boxing search-area pd0">
                    <dl>
                        <dt style="width: 120px;">직접검색</dt>
                        <dd>
                            <select style="width: 150px;" v-model="searchData.skey">
                                <option value="">전체</option>
                                <option value="erpcode">ERP 오리지널코드</option>
                                <option value="goodsname">ERP 오리지널명</option>
                            </select>
                            <input type="text" v-model="searchData.sword" style="width: 405px;" ref="sword"/>
                            <button type="button" class="btn blue" @click="searchErpList">검색</button>
                        </dd>
                    </dl>
                </div>
                <div class="caption-group clearfix mt10">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{dataList.length}}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 400px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <caption>검색결과</caption>
                        <colgroup>
                            <col width="6%" /><!-- 선택 -->
                            <col width="8%" /><!-- No -->
                            <col width="20%" /><!-- ERP 오리지널코드 -->
                            <col width="" /><!-- ERP 오리지널명 -->
                            <col width="20%" /><!-- ERP 오리지널 재고 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>선택</th>
                                <th>No</th>
                                <th>ERP 오리지널 코드
                                    <button type="button"
                                            :value="sortData.erpcode" 
                                            class="sort"
                                            :class="[{up : sortData.erpcode === 'erpcode_asc'}, {down : sortData.erpcode === 'erpcode_desc'}]"
                                            @click="sortToggle(sortData.erpcode)">
                                    </button>    
                                </th>
                                <th>ERP 오리지널 명</th>
                                <th>ERP 오리지널 재고
                                    <button type="button"
                                            :value="sortData.stockcnt" 
                                            class="sort"
                                            :class="[{up : sortData.stockcnt === 'stockcnt_asc'}, {down : sortData.stockcnt === 'stockcnt_desc'}]"
                                            @click="sortToggle(sortData.stockcnt)">
                                    </button>   
                                </th>
                            </tr>
                        </thead>
                        <tbody v-if="this.dataList.length > 0">
                            <tr v-for="(row,index) in this.dataList" :key="index">
                                <td><input type="radio" class="circle" v-model="checkedData" :value="row"/></td>
                                <td>{{index + 1}}</td>
                                <td>{{row.erpoptcode}}</td>
                                <td class="left">{{row.goodsname}}</td>
                                <td>{{row.stockcnt}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="sendOriginal">확인</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /ERP 오리지널  조회 팝업 -->
</template>

<script>
export default {
    name: 'SearchOriginalPopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            searchData: {
                skey: '',
                sword: '',
            },
            sortData: {
                erpcode: 'erpcode_asc',
                stockcnt: 'stockcnt_asc'
            },
            dataList: [],
            checkedData: '',
        }
    },
    mounted() {
        this.$refs.sword.focus();
        this.searchErpList();
    },
    methods: {
        searchErpList() {
            this.dataList = [
                {erpoptcode: '00000001', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000002', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000003', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000004', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000005', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000006', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000007', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000008', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000009', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000010', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000011', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000012', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000013', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000014', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000015', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000016', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000017', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000018', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000019', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000020', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000021', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500},
                {erpoptcode: '00000022', goodsname: '아디다스 운동화 오리지널 상품', optname: '검정색', stockcnt: 500}
            ];

            // erp 조회 추가필요
        },
        // 정렬조건으로 검색
        sortToggle (key) {
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchErpList();
        },
        sendOriginal() {
            if(this.checkedData === '') {
                alert("적용할 항목을 선택해주세요.");
                return;
            }

            if(confirm("선택대상을 적용하시겠습니까?")){
                this.closePopup(this.checkedData);
            }
        },
        closePopup(target){
            if( typeof(this.callbackFn) === 'function') {
                this.callbackFn({data: target});
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>

<style>

</style>