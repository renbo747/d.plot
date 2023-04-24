<template>
    <!-- 딜 구성상품추가 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>딜 구성상품 추가</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body" style="height: calc(80vh - 80px);">
                <div class="boxing search-area">
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
                            <select v-model="searchData.skey">
                                <option value="">전체</option>
                                <option value="goodsname">상품명</option>
                                <option value="goodscode">상품코드</option>
                                <option value="dealername">파트너사명</option>
                            </select>
                            <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>판매구분</dt>
                        <dd>
                            <div class="radio_wrap">
                                <input type="radio" name="ispbgoods" id="ispbgoodsAll" v-model="searchData.ispbgoods" value="" @click="searchData.dealerno=''"/>
                                <label for="ispbgoodsAll">전체</label>
                                <input type="radio" name="ispbgoods" id="ispbgoodsT" v-model="searchData.ispbgoods" value="T" @click="searchData.dealerno=''"/>
                                <label for="ispbgoodsT">직매입</label>
                                <input type="radio" name="ispbgoods" id="ispbgoodsF" v-model="searchData.ispbgoods" value="F"/>
                                <label for="ispbgoodsF">위탁</label>
                            </div>
                            <select style="width: 250px;" :disabled="searchData.ispbgoods!='F'" v-model="searchData.dealerno">
                                <option value="">파트너사 전체</option>
                                <option v-for="item in partnersList" :key="item.no" :value="item.no">{{ item.name }} </option>
                            </select>
                        </dd>
                    </dl>
                    <dl>
                        <dt>조회기간</dt>
                        <dd>
                            <select v-model="searchData.sdate">
                                <option value="start">전시시작일</option>
                                <option value="end">전시종료일</option>
                            </select>
                            <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
                            <span>-</span>
                            <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
                            <div class="radio_wrap">
                                <input type="radio" v-model="searchData.period" id="periodCP_aday_1" value='aday_1'/><label for="periodCP_aday_1">어제</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_aday_0" value='aday_0'/><label for="periodCP_aday_0">오늘</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_days_7" value='days_7'/><label for="periodCP_days_7">일주일</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_months_1" value='months_1'/><label for="periodCP_months_1">1개월</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_months_3" value='months_3'/><label for="periodCP_months_3">3개월</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_months_6" value='months_6'/><label for="periodCP_months_6">6개월</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>판매상태</dt>
                        <dd>
                            <div class="check-wrap">
                                <input type="checkbox" id="chkAllSellType" v-model="searchData.isallselltype" true-value="T" false-value="F" @change="checkAllSellType">
                                <label for="chkAllSellType">전체</label>
                            </div>
                            <div class="check-wrap" v-for="item in commonCode.goodsselltype" :key="item.cmcode">
                                <input type="checkbox" :id="'goodsselltype_'+item.cmcode" v-model="searchData.goodsselltypeArr" :true-value="[]" :value="item.cmcode"/>
                                <label :for="'goodsselltype_'+item.cmcode">{{ item.codename }}</label>
                            </div>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="searchList">검색</button>
                    <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{ constGoodsList.length }}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                        <colgroup>
                            <col width="2%" /><!-- checkbox -->
                            <col width="3%" /><!-- No -->
                            <col width="5%" /><!-- 판매구분 -->
                            <col width="6%" /><!-- 파트너사 -->
                            <col width="7%" /><!-- 상품코드 -->
                            <col width="62px" /><!-- 이미지 -->
                            <col width="" /><!-- 상품명 -->
                            <col width="5%" /><!-- 상품유형 -->
                            <col width="7%" /><!-- 판매가 -->
                            <col width="5%" /><!-- 판매상태 -->
                            <col width="7%" /><!-- 전시시작일 -->
                            <col width="7%" /><!-- 전시종료일 -->
                            <col width="8%" /><!-- 배송정보명 -->
                            <col width="8%" /><!-- 배송비 -->
                            <col width="6%" /><!-- 배송유형 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllGoodsList($event.target.checked)" true-value="T" false-value="F" /></th>
                                <th>No</th>
                                <th>판매구분</th>
                                <th>파트너사</th>
                                <th>상품코드</th>
                                <th>이미지</th>
                                <th>상품명</th>
                                <th>상품유형</th>
                                <th>판매가</th>
                                <th>판매상태</th>
                                <th>전시시작일</th>
                                <th>전시종료일</th>
                                <th>배송정보명</th>
                                <th>배송비</th>
                                <th>배송유형</th>
                            </tr>
                        </thead>
                        <tbody v-if="constGoodsList.length > 0">
                            <tr v-for="(item, index) in constGoodsList" :key="item.goodsno">
                                <td><input type="checkbox" :id="'goods_'+item.goodsno" v-model="item.ischecked" @change="checkGoodsList($event.target.checked)"/></td>
                                <td>{{ index+1 }}</td>
                                <td>{{ item.ispbgoodsname }}</td>
                                <td>{{ item.dealername }}</td>
                                <td>{{ item.goodscode }}</td>
                                <td>
                                    <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                        <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                    </div>
                                </td>
                                <td class="left">{{ item.goodsname }}</td>
                                <td>{{ item.goodsdivtypename }}</td>
                                <td class="right">{{ item.price }}</td>
                                <td>{{ item.goodsselltypename }}</td>
                                <td>{{ item.disstdate }}</td>
                                <td>{{ item.diseddate }}</td>
                                <td>{{ item.delivbname }}</td>
                                <td>{{ item.delivfaretext }}</td>
                                <td>{{ item.iscombdelivname }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="15">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="sendAllList">전체 적용</button>
                    <button type="button" class="btn big blue" @click="sendSelectedList">선택 대상 적용</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /딜 구성상품추가 팝업 -->
</template>

<script src="./AddConstGoodsListPopup.js"></script>