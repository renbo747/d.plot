<template>
    <!-- 상품정보 변경이력 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header">
                <h2>상품정보 변경이력</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="caption-group clearfix">
                    <div class="total-group fl">
                        <span class="total">{{ info.goodscode }}<strong class="left-bar txt-black">{{ info.goodsname }}</strong></span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                        <colgroup>
                            <col width="4%" /><!-- No -->
                            <col width="8%" /><!-- 구분 -->
                            <col width="%" /><!-- 변경내용 -->
                            <col width="22%" /><!-- 변경일시 -->
                            <col width="12%" /><!-- 처리자ID -->
                            <col width="14%" /><!-- 처리자명 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>구분</th>
                                <th>변경내용</th>
                                <th>변경일시
                                    <button type="button" :value="sortData.regdate" class="sort"
                                        :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                        @click="sortToggle(sortData.regdate)"></button>
                                </th>
                                <th>처리자ID</th>
                                <th>처리자명</th>
                            </tr>
                        </thead>
                        <tbody v-if="info.goodshistlist.length>0">
                            <tr v-for="(item, index) in info.goodshistlist" :key="index">
                                <td>{{ index+1 }}</td>
                                <td>{{ item.kindname }}</td>
                                <td class="left">
                                    <ul class="dot-list">
                                        <li>{{ item.summary }}</li>
                                    </ul>
                                </td>
                                <td>{{ item.regdate }}</td>
                                <td>{{ item.userid }}</td>
                                <td>{{ item.username }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="6">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /상품정보 변경이력 팝업 -->
</template>

<script src="./GoodsHistoryPopup.js"></script>