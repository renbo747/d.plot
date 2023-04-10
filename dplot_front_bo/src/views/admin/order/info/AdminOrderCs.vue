<template>
    <!-- 주문상세정보 - CS처리 팝업 -->
    <div class="tab-area" :style="isPartner? 'height: calc(90vh - 100px);':'height: calc(90vh - 290px);'">
        <div class="bar-title small">CS처리내역</div>
        <div class="scroll-y mt0" style="max-height: 270px;">
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>CS처리목록</caption>
                <colgroup>
                    <col width="4%" /><!-- No -->
                    <col width="10%" /><!-- 분류 -->
                    <col width="" /><!-- 제목 -->
                    <col width="12%" /><!-- 문의일시 -->
                    <col width="8%" /><!-- 답변자 -->
                    <col width="12%" /><!-- 답변일시 -->
                    <col width="8%" /><!-- 답변상태 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>분류</th>
                        <th>제목</th>
                        <th>문의일시</th>
                        <th>답변자</th>
                        <th>답변일시</th>
                        <th>답변상태</th>
                    </tr>
                </thead>
                <tbody v-if="csList.length > 0">
                    <tr v-for="(item, index) in csList" :key="item.idx">
                        <td>{{ index+1 }}</td>
                        <td>{{ item.inquirytypename }}</td>
                        <td class="left"><a href="javascript:void(0);" class="link" @click="goCsDetail(item.idx)">{{ item.content }}</a></td>
                        <td>{{ item.regdate }}</td>
                        <td>{{ item.repname }}</td>
                        <td>{{ item.repregdate }}</td>
                        <td>{{ item.isreply }}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="7">CS처리내역이 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
        </div>
        <div class="bar-title small">관리자 메모</div>
        <div class="memo-write" v-if="isWrite">
            <textarea placeholder="메모를 작성해 주세요!" v-model="memo.inputMemo"></textarea>
            <button type="button" class="btn big blue" @click="addMemo">메모<br>저장</button>
        </div>
        <div class="scroll-y" style="width: 100%; max-height: 550px; margin-bottom: 0;">
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <colgroup>
                    <col width="5%" /><!-- No -->
                    <col width="20%" /><!-- 작성일시 -->
                    <col width="8%" /><!-- 작성자 -->
                    <col width="" /><!-- 상품명 -->
                    <col width="40px" /><!-- 삭제 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>작성일시
                            <button type="button" class="sort" :value="memo.psort"
                                :class="[{up : memo.psort === 'regdate_asc'}, {down : memo.psort === 'regdate_desc'}]"
                                @click="memoSortToggle(memo.psort)"></button>
                        </th>
                        <th>작성자</th>
                        <th colspan="2">내용</th>
                    </tr>
                </thead>
                <tbody v-if="!$util.isNull(orderMemoList) && orderMemoList.length > 0">
                    <tr v-for="(item, index) in orderMemoList" :key="item.ordmemoidx">
                        <td>{{ index + 1 }}</td>
                        <td>{{ item.regdate }}</td>
                        <td>{{ item.regusername }}</td>
                        <td class="left">{{ item.memo }}</td>
                        <td class="no-left"><button type="button" class="del mg0" @click="removeMeno(item.ordmemoidx)" v-if="isWrite"></button></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
        </div>
        <!-- 상세 -->
        <OneOneDetail v-if="isCsDetailShow" :idx="activeIdx"></OneOneDetail>
    </div>
    <!-- /주문상세정보 - CS처리 팝업 -->
</template>

<script>
import OneOneDetail from "@views.admin/cs/oneone/OneOneDetail";
export default {
    name: "AdminOrderCs",
    props: {
        activeOrderIdx: Number,
        isRead: Boolean,
        isWrite: Boolean
    },
    components: {
        OneOneDetail
    },
    data() {
        return {
            isPartner: false,
            user: {},
            csList: [],                 // CS처리내역목록
            csSort: {
                psort: 'regdate_desc',
                inquirytypename: 'inquirytypename_asc',
                regdate: 'regdate_desc',
                repregdate: 'repregdate_asc'
            },
            orderMemoList: [],          // 관리자메모목록
            memo: {
                inputMemo: '',          // 입력된 메모내용
                psort: 'regdate_desc'   // 메모정렬
            },
            isCsDetailShow: false,      // 1:1문의 상세팝업 노출여부
            activeIdx: ''
        }
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
        this.getOrderCsInfo();
    },
    methods: {
        // CS 내역 조회
        getOrderCsInfo: function() {
            let params = {
                orderidx : this.activeOrderIdx, 
                psort: this.csSort.psort, 
                dealerno: this.isPartner? this.user.no : ''
            };
            this.$http.post('/admin/order/manage/csinfo', params)
                .then(result => {
                    this.$util.debug(result);
                    this.csList = result.data.cslist;
                    this.orderMemoList = result.data.ordermemolist;
                }).catch(error => {
                    this.$util.debug(error);
                })
        },
        // 메모 추가
        addMemo: function() {
            if (this.$util.isNull(this.memo.inputMemo.trim())){
                alert('메모 내용을 입력해주세요.');
                return;
            }
            let params = {
                orderidx: this.activeOrderIdx,
                memo: this.memo.inputMemo
            }
            this.$http.post('/admin/order/manage/memo/add', params)
                .then(result => {
                    this.$util.debug(result);
                    this.memo.inputMemo = '';
                    this.getOrderCsInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 메모 삭제
        removeMeno: function(value) {
            let params = { ordmemoidx: value };
            this.$http.post('/admin/order/manage/memo/remove', params)
                .then(result => {
                    this.$util.debug(result);
                    this.memo.inputMemo = '';
                    this.getOrderCsInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 메모정렬
        memoSortToggle(key){            
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.memo.psort = sortName;

            this.orderMemoList.sort((a, b) => {
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
        // cs처리내역 정렬
        csSortToggle(key){            
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.csSort.psort = sortName;
            this.csSort[sortKey] = sortName;

            this.csList.sort((a, b) => {
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
        // 1:1문의 상세팝업 오픈
        goCsDetail: function(value) {
            this.isCsDetailShow = true;
            this.activeIdx = value;
        },
        // 1:1문의 상세팝업 닫기
        goCloseDetailPopup: function() {
            this.isCsDetailShow = false;
        },
        onClose() {
            this.$emit('closeDetail');
        },
    }
}
</script>
