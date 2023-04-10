<template>
    <!-- 배송템플릿 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1200px;">
            <div class="pop-header">
                <h2>배송템플릿</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <caption>배송템플릿</caption>
                        <colgroup>
                            <col width="4%" /><!-- No -->
                            <col width="6%" /><!-- 관리코드 -->
                            <col width="%" /><!-- 배송정보명 -->
                            <col width="7%" /><!-- 배송방법 -->
                            <col width="9%" /><!-- 택배사 -->
                            <col width="24%" /><!-- 배송비 -->
                            <col width="8%" /><!-- 배송가능지역 -->
                            <col width="9%" /><!-- 교환 배송비 -->
                            <col width="9%" /><!-- 반품 배송비 -->
                            <col width="6%" /><!-- 사용여부 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>관리코드</th>
                                <th>배송정보명</th>
                                <th>배송방법</th>
                                <th>택배사</th>
                                <th>배송비</th>
                                <th>배송가능지역</th>
                                <th>교환 배송비(편도)</th>
                                <th>반품 배송비(편도)</th>
                                <th>사용여부</th>
                            </tr>
                        </thead>
                        <tbody v-if="delivTempList.length > 0">
                            <tr v-for="(item, index) in delivTempList" :key="item.delividx">
                                <td>{{ index+1 }}</td>
                                <td>{{ item.delividx }}</td>
                                <td class="left"><a href="javascript:void(0);" class="link" @click="openDelivTempDetailPopup(item)">[{{ item.iscombdelivname }}]{{ item.delivbname }}</a></td>
                                <td>{{ item.delivtypename }}</td>
                                <td>{{ item.logistypename }}</td>
                                <td class="left">{{ item.delivfaretypename }} 
                                    {{item.delivfaretype == constants.DELIV_FARE_DCT002? '(' + item.delivfare  + '원)': ''}} <!-- 유료 -->
                                    {{item.delivfaretype == constants.DELIV_FARE_DCT003? item.delivfare + '원 ('+item.delivfreefare+'원 이상 구매 시)': ''}} <!-- 조건부무료 -->
                                </td>
                                <td>{{ item.nationdelivconts }}</td>
                                <td>{{ $util.isNull(item.exowfare)? '': item.exowfare + '원' }}</td>
                                <td>{{ $util.isNull(item.rfowfare)? '': item.rfowfare + '원' }}</td>
                                <td>{{ item.useyn }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="10">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="openNewDelivTempPopup">신규등록</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /배송템플릿 팝업 -->
</template>

<script src="./DelivTempListPopup.js"></script>