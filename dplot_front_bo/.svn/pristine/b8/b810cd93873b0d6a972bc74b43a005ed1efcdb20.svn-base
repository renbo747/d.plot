<template>
    <!-- 브랜드 조회 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 500px; height: 472px;">
            <div class="pop-header">
                <h2>브랜드 조회</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div>
                    <input type="search" style="width: calc(100% - 30px);" placeholder="브랜드명을 검색하세요!" :maxlength='200'
                        v-model="searchData.sword" @keyup.enter="searchBrandList" ref="sword">
                    <button type="button" class="btn-search" @click="searchBrandList">검색</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{ brandListCnt }}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 300px;" @scroll.passive="onScroll" ref="scrollTarget">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <caption>검색결과</caption>
                        <colgroup>
                            <col width="7%" /><!-- checkbox -->
                            <col width="8%" /><!-- No -->
                            <col width="20%" /><!-- 브랜드코드 -->
                            <col width="30%" /><!-- 브랜드명 -->
                            <col width="*" /><!-- 브랜드영문명 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>선택</th>
                                <th>No</th>
                                <th>브랜드코드
                                    <button type="button" :value="sortData.code" class="sort"
                                        :class="[{up : sortData.code === 'code_asc'}, {down : sortData.code === 'code_desc'}]"
                                        @click="sortToggle(sortData.code)"></button>
                                </th>
                                <th>브랜드명
                                    <button type="button" :value="sortData.brandname" class="sort"
                                        :class="[{up : sortData.brandname === 'brandname_asc'}, {down : sortData.brandname === 'brandname_desc'}]"
                                        @click="sortToggle(sortData.brandname)"></button>
                                </th>
                                <th>브랜드영문명
                                    <button type="button" :value="sortData.enname" class="sort"
                                        :class="[{up : sortData.enname === 'enname_asc'}, {down : sortData.enname === 'enname_desc'}]"
                                        @click="sortToggle(sortData.enname)"></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody v-if="brandList.length > 0">
                            <tr v-for="(item, index) in brandList" :key="index">
                                <td>
                                    <input type="radio" name="brandidx" class="circle" :id="'brandidx' + $util.lpad(index, 2, '0')" @click="sendData(item)">
                                </td>
                                <td>{{ index+1 }}</td>
                                <td>{{ item.brcode }}</td>
                                <td>{{ item.brandname }}</td>
                                <td>{{ item.enname }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- /브랜드 조회 팝업 -->
</template>

<script src="./SearchBrandListPopup.js"></script>