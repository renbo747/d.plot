<template>
    <!-- 처리이력 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header">
                <h2>처리이력</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="caption-group clearfix">
                    <div class="total-group fl">
                        <span class="total">{{ dealername }}</span>
                        <span>{{ goodscode }}<strong class="left-bar txt-black">{{ goodsname }}</strong></span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                        <colgroup>
                            <col width="5%" /><!-- No -->
                            <col width="10%" /><!-- 처리구분 -->
                            <col width="22%" /><!-- 처리일시 -->
                            <col width="15%" /><!-- 담당자 -->
                            <col width="%" /><!-- 내용 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>처리구분</th>
                                <th>처리일시</th>
                                <th>담당자</th>
                                <th>내용</th>
                            </tr>
                        </thead>
                        <tbody v-if="apprhistList.length > 0">
                            <tr v-for="(item, index) in apprhistList" :key="index">
                                <td>{{ index+1 }}</td>
                                <td>{{ item.goodsapprtypename }}</td>
                                <td>{{ item.regdate }}</td>
                                <td>{{ item.regusername }}</td>
                                <td class="left">{{ item.contents }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /처리이력 팝업 -->
</template>

<script src="./GoodsApprHistoryPopup.js"></script>