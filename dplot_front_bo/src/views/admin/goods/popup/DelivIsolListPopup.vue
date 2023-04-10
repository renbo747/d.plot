<template>
    <!-- 제주/도서산간지역 조회 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 600px; height: 462px;">
            <div class="pop-header">
                <h2>제주/도서산간 지역 조회</h2>
                <button type="button" class="pop-close" @click="$modal.hide('delivIsolListPopup');"></button>
            </div>
            <div class="pop-body">
                <div>
                    <input type="search" style="width: calc(100% - 30px);" placeholder="우편번호/주소지 입력"
                        v-model="searchData.sword" maxlength="100" @keyup.enter="searchDelivIsolList">
                    <button type="button" class="btn-search" @click="searchDelivIsolList">검색</button>
                </div>
                <div class="scroll-y" style="max-height: 300px;" @scroll.passive="onScroll" ref="scrollTarget">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <caption>검색결과</caption>
                        <colgroup>
                            <col width="8%" /><!-- No -->
                            <col width="12%" /><!-- 우편번호 -->
                            <col width="" /><!-- 주소지 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>우편번호</th>
                                <th>주소지</th>
                            </tr>
                        </thead>
                        <tbody v-if="delivIsolList.length > 0">
                            <tr v-for="(item, index) in delivIsolList" :key="index">
                                <td>{{ index+1 }}</td>
                                <td>{{ item.post }}</td>
                                <td class="left">{{ item.addr }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="3">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn darkgray" @click="$modal.hide('delivIsolListPopup');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /제주/도서산간지역 조회 팝업 -->
</template>

<script src="./DelivIsolListPopup.js"></script>