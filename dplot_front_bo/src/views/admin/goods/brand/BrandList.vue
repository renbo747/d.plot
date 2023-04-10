<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <BrandRegist v-if="registShow" v-bind:cateList="cateList" v-on:closeRegist="closeRegist"></BrandRegist>
        <BrandDetail v-if="detailShow" v-bind:cateList="cateList" v-bind:idx="idx" v-on:closeDetail="closeDetail"></BrandDetail>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="brcode">브랜드코드</option>
                            <option value="brandname">브랜드명</option>
                        </select>
                        <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
                    </dd>
                </dl>
                <!-- <dl>
                    <dt>등록구분</dt>
                    <dd>
                        <div class="radio_wrap">
                            <input type="radio" name="dealer" id="rd01212" v-model="searchData.isdealer" value="" checked/><label for="rd01212">전체</label>
                            <input type="radio" name="dealer" id="rd02323" v-model="searchData.isdealer" value="F"/><label for="rd02323">직매입</label>
                            <input type="radio" name="dealer" id="rd03434" v-model="searchData.isdealer" value="T"/><label for="rd03434">위탁</label>
                        </div>
                        <select v-model="searchData.dealerno" :disabled="searchData.isdealer !== 'T'">
                            <option value="">파트너사 선택</option>
                            <option v-for="(row, i) in this.partnerList" :key="i" :value="row.no">{{row.name}}</option>
                        </select>
                    </dd>
                </dl> -->
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
                        <div class="radio_wrap">
                            <input type="radio" name="period" id="rd1" v-model="searchData.period" value='-1'/><label for="rd1">어제</label>
                            <input type="radio" name="period" id="rd2" v-model="searchData.period" value='0'/><label for="rd2">오늘</label>
                            <input type="radio" name="period" id="rd3" v-model="searchData.period" value='7'/><label for="rd3">일주일</label>
                            <input type="radio" name="period" id="rd4" v-model="searchData.period" value='1'/><label for="rd4">1개월</label>
                            <input type="radio" name="period" id="rd5" v-model="searchData.period" value='3' checked/><label for="rd5">3개월</label>
                            <input type="radio" name="period" id="rd6" v-model="searchData.period" value='6'/><label for="rd6">6개월</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>사용여부</dt>
                    <dd>
                        <div class="radio_wrap">
                            <input type="radio" name="istrash" id="rd11" v-model="searchData.istrash" value="" checked/><label for="rd11">전체</label>
                            <input type="radio" name="istrash" id="rd12" v-model="searchData.istrash" value="F"/><label for="rd12">사용</label>
                            <input type="radio" name="istrash" id="rd13" v-model="searchData.istrash" value="T"/><label for="rd13">미사용</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group" v-if="isRead">
                <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
                <button type="button" class="btn big gray" @click="onSearchDataReset">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{pagingData.listCount}}</strong>건</span>
                </div>
                <div class="btn-group fr" v-if="isRead">
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>브랜드 관리 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- No -->
                    <col width="8%" /><!-- 브랜드코드 -->
                    <col width="10%" /><!-- 카테고리 -->
                    <col width="10%" /><!-- 한글명 -->
                    <col width="10%" /><!-- 영문명 -->
                    <col width="" /><!-- 헤드카피 -->
                    <col width="5%" /><!-- 사용여부 -->
                    <col width="8%" /><!-- 프로모션 배너 -->
                    <col width="10%" /><!-- 등록일 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>No</th>                    
                        <th>브랜드코드<button type="button" v-if="isRead" :value="sortData.brcode" class="sort" :class="[{up : sortData.brcode === 'brcode_asc'}, {down : sortData.brcode === 'brcode_desc'}]" @click="sortToggle(sortData.brcode)"></button></th>
                        <th>카테고리</th>
                        <th>한글명<button type="button" v-if="isRead" :value="sortData.name" class="sort" :class="[{up : sortData.name === 'name_asc'}, {down : sortData.name === 'name_desc'}]" @click="sortToggle(sortData.name)"></button></th>
                        <th>영문명<button type="button" v-if="isRead" :value="sortData.enname" class="sort" :class="[{up : sortData.enname === 'enname_asc'}, {down : sortData.enname === 'enname_desc'}]" @click="sortToggle(sortData.enname)"></button></th>
                        <th>헤드카피<button type="button" v-if="isRead" :value="sortData.headcopy" class="sort" :class="[{up : sortData.headcopy === 'headcopy_asc'}, {down : sortData.headcopy === 'headcopy_desc'}]" @click="sortToggle(sortData.headcopy)"></button></th>
                        <th>사용여부<button type="button" v-if="isRead" :value="sortData.istrash" class="sort" :class="[{up : sortData.istrash === 'istrash_asc'}, {down : sortData.istrash === 'istrash_desc'}]" @click="sortToggle(sortData.istrash)"></button></th>
                        <th>브랜드 배너<button type="button" v-if="isRead" :value="sortData.bannercnt" class="sort" :class="[{up : sortData.bannercnt === 'bannercnt_asc'}, {down : sortData.bannercnt === 'bannercnt_desc'}]" @click="sortToggle(sortData.bannercnt)"></button></th>
                        <th>등록일<button type="button" v-if="isRead" :value="sortData.regdate" class="sort" :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]" @click="sortToggle(sortData.regdate)"></button></th>
                    </tr>
                </thead>
                <tbody v-if="this.listData.length > 0">
                    <tr v-for="(row, i) in this.listData" :key="i">
                        <td>{{row.no}}</td>
                        <td><a class="link" @click="goDetail(row.idx)">{{row.brcode}}</a></td>
                        <td>{{row.category}}</td>
                        <td class="left">{{row.name}}</td>
                        <td class="left">{{row.enname}}</td>
                        <td class="left">{{row.headcopy}}</td>
                        <td>{{row.istrash === 'F' ? '사용' : '미사용'}}</td>
                        <td><a class="link" @click="goBrandBanner(row.idx, row.name)">{{row.bannercnt}}</a></td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr>
                        <td colspan="9">조회 결과가 존재하지 않습니다.</td>
                    </tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <CommonPageNavigator v-bind:pagingData="this.pagingData" v-on:setPagingData="setPagingData"/>
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="openRegist">임시브랜드 등록</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@/views/admin/common/CommonNavigator'
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "../../common/CommonPageNavigator";
import BrandRegist  from './BrandRegist.vue';
import BrandDetail from './BrandDetail.vue';

export default {
    name: 'admin.goods.brand.brandList',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        BrandRegist,
        BrandDetail
    },
    data(){
        return{
            searchData:{
                startDate: '',  // 시작년도
                endDate: '',    // 종료년도
                skey: '',       // 검색조건
                sword: '',      // 검색단어
                period: 3,      // 조회기간
                istrash: '',    // 사용여부
            },
            pagingData: {
                pageCount: 20,  // 페이징 옵션(최대수)
                page: 1,        // 현재 페이지
                listCount: 0    // 전체 수
            },
            sortData: {
                brcode : 'brcode_desc',
                name : 'name_desc',
                enname: 'enname_desc',
                headcopy: 'headcopy_desc',
                istrash: 'istrash_desc',
                bannercnt: 'bannercnt_desc',
                regdate : 'regdate_desc'
            },
            idx: null,     // 상세 데이터
            listData: [],       // response 데이터 리스트
            cateList: [],
            registShow: false,  // 브랜드등록 열림 여부
            detailShow: false,  // 브랜드상세 열림 여부
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');
      
            if(this.isRead){
                this.init();
                this.$util.componentSetSearchParam(this);
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        init(){
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.onSearch();
        },
        onSearch(page){
            this.isChecked = false;
            let params = this.searchData;
            params.pageCount = this.pagingData.pageCount;
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.listCount = this.pagingData.listCount;

            this.$http.post("/admin/goods/brand/search", params)
                .then(result => {
                    let data = result.data;
                    this.listData = data.list;
                    this.pagingData.listCount = data.listcount;
                    this.cateList = data.categorylist;

                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        onSearchDataReset(){
            this.searchData = this.$options.data().searchData;
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
        },
        setPagingData(param){
            this.pagingData = param;
            this.onSearch();
        },
        sortToggle(key){
            if (!this.isRead) return;
            
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.onSearch();
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(val) {
            this.searchData.startDate = val;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(val) {
            this.searchData.endDate = val;
        },
        openRegist(){
            this.registShow = true;
        },
        closeRegist(isreload){
            this.registShow = false;
            if(isreload) {
                this.onSearch();
            }
        },
        goDetail(idx){
            this.idx = idx;
            this.detailShow = true;
        },
        closeDetail(isreload){
            this.detailShow = false;
            if(isreload) {
                this.onSearch();
            }
        },
        goBrandBanner(idx, name){
            this.$router.push({ name: 'admin.goods.brand.brandbannerlist', params: { brandidx: idx, brandname: name, childMenu: this.$route.params.childMenu }});
        },
    },
    watch: {
        'searchData.period': function(val){
            let dayType = ["-1", "0", "7"];
            let monthType = ["1", "3", "6"];
            let valueToInt = parseInt(val);

            if(dayType.includes(val)){
                if(valueToInt >= 0){
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), (-1 * valueToInt), '-');
                    this.searchData.endDate = this.$util.getDate('-');
                }else{
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                    this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                }
            }else if(monthType.includes(val)){
                this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), (-1 * valueToInt), '-');
                this.searchData.endDate = this.$util.getDate('-');
            }
        }
    }
}
</script>
