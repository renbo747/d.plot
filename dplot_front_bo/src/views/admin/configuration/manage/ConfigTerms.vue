<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <ConfigTermsDetail v-if="showDetail" v-bind:idx="idx" v-on:closeDetail="closeDetail"></ConfigTermsDetail>
        <ConfigTermsModify v-if="showModify" v-bind:termsidx="termsidx" v-on:closeModify="closeModify"></ConfigTermsModify>
        <div class="inner">
            <div class="caption-group mt10 clearfix">
                <div class="fl">
                    <div class="dpib">
                        <select v-if="isRead" v-model="searchData.termstype" style="width: 350px;" @change="onSearch(1)">
                            <option value="">전체</option>
                            <option v-for="(code,i) in commonCode.termstype" :key="i" :value="code.cmcode">{{code.codename}}</option>
                        </select>
                    </div>
                    <div class="dpib">
                        <div class="radio_wrap" v-if="isRead">
                            <input type="radio" v-model="searchData.iscurrent" name="group000" id="group001" value="T" checked><label for="group001">약관 별 최신 버전만 보기</label>
                            <input type="radio" v-model="searchData.iscurrent" name="group000" id="group002" value="F"><label for="group002">전체보기</label>
                        </div>
                    </div>
                </div>
                <div class="btn-group fr">
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>약관 목록</caption>
                <colgroup>
                    <col width="5%" /><!-- No -->
                    <col width="10%" /><!-- 구분 -->
                    <col width="" /><!-- 약관명 -->
                    <col width="8%" /><!-- 등록자 -->
                    <col width="8%" /><!-- 버전 -->
                    <col width="8%" /><!-- 필수여부 -->
                    <col width="12%" /><!-- 업데이트일시 -->
                    <col width="80px" /><!-- 관리 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>구분</th>
                        <th>약관명</th>
                        <th>등록자</th>
                        <th>버전</th>
                        <th>필수여부</th>
                        <th>업데이트일시</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody v-if="this.dataList.length > 0">
                    <tr v-for="(row, index) in this.dataList" :key="index">
                        <td>{{row.no}}</td>
                        <td>{{row.iscusterms}}</td>
                        <td class="left"><a class="link" @click="openDetail(row.idx)">{{row.termsname}}</a></td>
                        <td>{{row.regusername}}</td>
                        <td>{{row.version}}</td>
                        <td>{{row.isessen}}</td>
                        <td>{{row.moddate}}</td>
                        <td><button type="button" class="btn blue-line" v-if="isWrite" @click="openModify(row.termsidx)">수정</button></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="8">대상 약관이 없습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData"
                               v-on:setPagingData="setPagingData"></CommonPageNavigator>
                </div>
            </div>
        </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator'
import ConfigTermsDetail from './ConfigTermsDetail.vue'
import ConfigTermsModify from './ConfigTermsModify.vue'
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
export default {
    name: 'admin.configuration.manage.terms',
    components: {
        CommonNavigator,
        ConfigTermsDetail,
        ConfigTermsModify,
        CommonPageNavigator
    },
    data() {
        return {
            searchData: {
                termstype: '',
                iscurrent: 'T',
            },
            pagingData: {
                pageCount: 20,
                page: 1,
                listCount: 0,
            },
            commonCode: {
                termstype: [],
            },
            dataList: [],
            idx: '',
            termsidx: '',
            showModify: false,
            showDetail: false,
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead) {
                this.getCommonCodeList();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        onSearch(page) {
            let params = this.searchData;
            params.pageCount = this.pagingData.pageCount;
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.listCount = this.pagingData.listCount;

            this.$http.post('/admin/configuration/manage/terms/search', params)
            .then(result => {
                if(result.statusCode === 200) {
                    this.dataList = result.data.list;
                    this.pagingData.listCount = result.data.totalcnt;
                    this.selectIndex = 0;
                }
            }).catch(error => {
                this.$util.debug(error);
            });
        },
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['TERMSTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
            .then(result =>{
                let data = result.data;
                for (const [key] of Object.entries(data)) {
                    this.commonCode[key] = data[key];
                }

                this.onSearch();
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 페이징 콜백
        setPagingData(param){
            this.pagingData = param;
            this.onSearch();
        },
        openModify(termsidx) {
            this.termsidx = termsidx;
            this.showModify = true;
        },
        closeModify(isreload) {
            this.showModify = false;
            if(isreload) {
                this.onSearch();
            }
        },
        openDetail(idx) {
            this.idx = idx;
            this.showDetail = true;
        },
        closeDetail() {
            this.showDetail = false;
        }
    },
    watch: {
        'searchData.iscurrent': function(val) {
            this.onSearch(1);
        }
    }
}
</script>

<style>

</style>