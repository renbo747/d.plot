<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="bar-title">일괄등록양식</div>
            <div class="boxing">
                <dl>
                    <dd>
                        <ul>
                            <li>반드시 지정된 엑셀양식으로만 일괄등록이 가능합니다. 아래 엑셀양식을 다운로드 후 작성하시기 바랍니다.</li>
                        </ul>
                        <button type="button" class="btn big blue mt10 ml10" @click="fnExcelTemplateDownload('AdminPbGoodsUpload.xlsx')" v-if="!isPartner" :disabled="!isWrite">직매입 엑셀양식 다운로드</button>
                        <button type="button" class="btn big blue mt10 ml10" @click="fnExcelTemplateDownload('AdminCoGoodsUpload.xlsx')" v-if="!isPartner" :disabled="!isWrite">위탁 엑셀양식 다운로드</button>
                        <button type="button" class="btn big blue mt10 ml10" @click="fnExcelTemplateDownload('PartnerGoodsUpload.xlsx')" v-if="isPartner" :disabled="!isWrite">엑셀양식 다운로드</button>
                    </dd>
                </dl>
                <dl class="mt10">
                    <dd>
                        <ul>
                            <li>엑셀작성 시 코드조회가 필요한 항목은 아래 자료를 참고하시어 입력하시면 됩니다.</li>
                        </ul>
                        <button type="button" class="btn green-line mt10 ml10" @click="fnExcelDownload('cate')" :disabled="!isWrite"><i class="icon-excel"></i>카테고리 코드</button>
                        <button type="button" class="btn green-line mt10" @click="fnExcelDownload('delivtemp')" :disabled="!isWrite"><i class="icon-excel"></i>배송템플릿 코드</button>
                        <button type="button" class="btn green-line mt10" @click="fnExcelDownload('notify')" :disabled="!isWrite"><i class="icon-excel"></i>상품정보고시 코드</button>
                        <button type="button" class="btn green-line mt10" @click="fnExcelDownload('brand')" :disabled="!isWrite"><i class="icon-excel"></i>브랜드 코드</button>
                        <button type="button" class="btn green-line mt10" @click="fnExcelDownload('md')" :disabled="!isWrite"><i class="icon-excel"></i>담당MD 코드</button>
                        <!-- <button type="button" class="btn green-line mt10" @click="fnExcelDownload('color')" :disabled="!isWrite"><i class="icon-excel"></i>검색컬러 코드</button> -->
                    </dd>
                </dl>
                <dl class="mt10">
                    <dd>
                        <ul>
                            <li>코드조회</li>
                        </ul>
                        <button type="button" class="btn blue-line mt10 ml10" @click="goGoodsListPopup" :disabled="!isWrite">상품코드조회</button>
                        <button type="button" class="btn blue-line mt10 ml10" @click="goOriginalCodePopup" :disabled="!isWrite" v-if="!isPartner">오리지널코드조회</button>
                        <button type="button" class="btn blue-line mt10 ml10" @click="goDealernoPopup" :disabled="!isWrite" v-if="!isPartner">파트너사코드조회</button>
                    </dd>
                </dl>
            </div>
            <div class="bar-title">엑셀파일업로드</div>
            <div class="boxing">
                <dl>
                    <dd>
                        <ul>
                            <li>엑셀은 한번에 1개 파일만 업로드가 가능하며, 파일당 최대 100개 상품 이내로 등록이 가능합니다.</li>
                        </ul>
                        <div class="mt10 ml10">
                            <input type="file" ref="excel" @change="handleFileUpload" hidden accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                            <input type="text" ref="excelFileName" readonly>
                            <button type="button" class="btn blue-line ml3" @click="fileAttach" :disabled="!isWrite">파일찾기</button>
                            <button type="button" class="btn blue"  @click="uploadExcelFile" :disabled="!isWrite">파일업로드</button>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="caption-group mt10 ml10 clearfix">
                <div class="total-group fl">
                    <span class="total">조회결과</span>
                </div>
                <div class="btn-group fr">
                    <!-- <button type="button" class="btn blue-line" @click="deleteResult" v-if="isWrite">삭제</button> -->
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <colgroup>
                    <!-- <col width="1%" /> -->
                    <col width="3%" /><!-- No -->
                    <col width="" /><!-- 파일명 -->
                    <col width="8%" /><!-- 성공 -->
                    <col width="8%" /><!-- 실패 -->
                    <col width="14%" /><!-- 업로드일시 -->
                    <col width="14%" /><!-- 처리일시 -->
                    <col width="10%" /><!-- 이미지업로드 상태 -->
                    <col width="12%" /><!-- 이미지업로드 -->
                </colgroup>
                <thead>
                    <tr>
                        <!-- <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllGoodsList($event.target.checked)"/></th> -->
                        <th>No</th>
                        <th>파일명
                            <button type="button" class="sort" :value="sortData.exfilename" v-if="isRead"
                                :class="[{up : sortData.exfilename=== 'exfilename_asc'}, {down : sortData.exfilename === 'exfilename_desc'}]"
                                @click="sortToggle(sortData.exfilename)"></button>
                        </th>
                        <th>성공
                            <button type="button" class="sort" :value="sortData.succcnt" v-if="isRead"
                                :class="[{up : sortData.succcnt=== 'succcnt_asc'}, {down : sortData.succcnt === 'succcnt_desc'}]"
                                @click="sortToggle(sortData.succcnt)"></button>
                        </th>
                        <th>실패
                            <button type="button" class="sort" :value="sortData.failcnt" v-if="isRead"
                                :class="[{up : sortData.failcnt=== 'failcnt_asc'}, {down : sortData.failcnt === 'failcnt_desc'}]"
                                @click="sortToggle(sortData.failcnt)"></button>
                        </th>
                        <th>업로드일시
                            <button type="button" class="sort" :value="sortData.uploaddate" v-if="isRead"
                                :class="[{up : sortData.uploaddate=== 'uploaddate_asc'}, {down : sortData.uploaddate === 'uploaddate_desc'}]"
                                @click="sortToggle(sortData.uploaddate)"></button>
                        </th>
                        <th>처리일시
                            <button type="button" class="sort" :value="sortData.prcdate" v-if="isRead"
                                :class="[{up : sortData.prcdate=== 'prcdate_asc'}, {down : sortData.prcdate === 'prcdate_desc'}]"
                                @click="sortToggle(sortData.prcdate)"></button>
                        </th>
                        <th>이미지업로드 상태</th>
                        <th>이미지업로드</th>
                    </tr>
                </thead>
                <tbody v-if="list.length > 0">
                    <tr v-for="(item, index) in list" :key="index">
                        <!-- <td><input type="checkbox" v-model="checkedList" :value="item.excelidx" @change="checkGoodsList($event.target.checked)"/></td> -->
                        <td>{{ loopNumberForPaging(index) }}</td>
                        <td>{{ item.exfilename }}</td>
                        <td>{{ item.succcnt }}</td>
                        <td v-if="item.failcnt > 0"><a class="link" @click="goUploadFailPopup(item)">{{ $util.maskComma(item.failcnt) }}</a></td>
                        <td v-else>{{ $util.maskComma(item.failcnt) }}</td>
                        <td>{{ item.uploaddate }}</td>
                        <td>{{ item.prcdate }}</td>
                        <td>{{ $util.isNull(item.prcdate)? '미완료':'완료' }}</td>
                        <td><button type="button" class="btn blue" @click="uploadImageZip(item)" v-if="$util.isNull(item.prcdate) && Number(item.succcnt) > 0">업로드</button></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="8">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
            </div>
        </div>
        <GoodsImageUpload v-if="isShowIamgeUpload" :uploadParams="uploadParams" @closeUpload="closeUpload"></GoodsImageUpload>
        <GoodsExcelFailPopup v-if="isShowUploadFail" :excelFailParams="excelFailParams" @closeUpload="closeUploadFailPopup"></GoodsExcelFailPopup>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator'
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator.vue';
import GoodsExcelFailPopup from '@views.admin/goods/popup/GoodsExcelFailPopup.vue';
import GoodsImageUpload from '@views.admin/goods/popup/GoodsImageUploadPopup.vue';
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';
import SearchOriginalCodePopup from '@views.admin/goods/popup/SearchOriginalCodePopup.vue';
import PartnersListPopup from '@views.admin/goods/popup/PartnersListPopup.vue';

export default {
    name: 'admin.goods.manage.goodsUploadAll',
    components: {
        CommonNavigator,
        CommonPageNavigator,
        GoodsImageUpload,
        GoodsExcelFailPopup
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
        this.dealerno = this.isPartner? this.user.no : null;
        // 권한 설정
        this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');
            if (this.isRead) {
                this.searchList();
            }
        }).catch(error => {
            this.$util.debug(error);
        })
    },
    data() {
        return {
            isPartner: false,
            dealerno : null,
            list: [],
            psort: '',
            checkedList: [],
            goodsExcelFile: null,
            excelFailParams: {},
            uploadParams: {},
            sortData: {
                exfilename: 'exfilename_asc',
                succcnt: 'succcnt_asc',
                failcnt: 'failcnt_asc',
                uploaddate: 'uploaddate_asc',
                prcdate: 'prcdate_asc'
            },
            pagingData: {
              pageCount: 20,        //페이징 옵션 (최대수)
              page: 1,              //현재 페이지
              listCount: 0          //총 페이지
            },
            isShowIamgeUpload: false,
            isShowUploadFail: false,
            isRead: false,
            isWrite: false
        }
    },
    methods: {
        // 엑셀양식다운로드
        fnExcelTemplateDownload: function(filename) {
            let params = { filename: filename };
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/common/excel/download', params, config);
        },
        // 코드조회 엑셀 다운로드
        fnExcelDownload: function(value) {
            let param = {
                type: value,
                dealerno: this.dealerno
            };
            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/goods/upload/codelist", param, postConfig).then(result => {
                this.$util.debug(result);
            })
        },
        // 엑셀업로드 결과 목록 조회
        searchList: function() {
            let params = { dealerno: this.dealerno, psort: this.psort };
            params = Object.assign(params, this.pagingData);
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;

            this.$http.post('/admin/goods/upload/list', params).then(result => {
                this.$util.debug(result);
                this.list = result.data.list;
                this.pagingData.listCount = result.data.totalcnt;
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function() {
            this.$refs.excel.click();
        },
        // 가져온 파일 세팅
        handleFileUpload: function() {
            let file = this.$refs.excel;
            if (this.$util.isNull(file.files[0])) {
                return;
            }
            let fileType = ['application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'];
            if(!fileType.includes(file.files[0].type)){
                alert('엑셀 파일만 첨부 가능합니다.');
                file.value = null;
                this.goodsExcelFile = null;
                this.$refs.excelFileName.value = '';
                return false;
            }
            
            // if(file.files[0].size > 10485760){
            //     alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
            //     file.value = null;
            //     return false;
            // }
            this.goodsExcelFile = file.files[0];
            this.$refs.excelFileName.value = file.files[0].name;
        },
        // 엑셀파일 업로드
        uploadExcelFile: function() {
            if (this.$util.isNull(this.goodsExcelFile)) {
                alert('파일을 선택해주세요.');
                return;
            }

            if (confirm('파일을 업로드하시겠습니까?')) {
                let files = [];
                files.push({key: 'excel', file: this.goodsExcelFile});
                let params = { 
                    dealerno: this.dealerno,
                    files: files
                };
                this.$http.post('/admin/goods/upload/excel', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        alert('상품(성공:' + result.data.succcnt + '건, 실패:'+ result.data.failcnt +'건)이 등록되었습니다.');
                        this.searchList();
                    }
                    this.goodsExcelFile = null;
                    this.$refs.excel.value = '';
                    this.$refs.excelFileName.value = '';
                })
                .catch(error => {
                    this.$util.debug(error);
                    this.goodsExcelFile = null;
                    this.$refs.excel.value = '';
                    this.$refs.excelFileName.value = '';
                });
            }
        },
        // 목록 전체체크
        checkAllGoodsList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    this.checkedList.push(item.excelidx);
                });
            }
        },
        // 목록 개별체크
        checkGoodsList: function() {
            if (this.list.length > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 목록 Number 세팅
        loopNumberForPaging(index) {
            if(this.pagingData.page > 1){
                let page = this.pagingData.page - 1;
                return (index+1) + (page * this.pagingData.pageCount);
            } else {
                return (index+1);
            }
        },
        // 정렬
        sortToggle(key){
            if (!this.isRead) return;
            
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;
            this.sortData[sortKey] = sortName;
            this.psort = sortName;

            this.searchList();
        },
        // 페이징 콜백 함수
        setPagingData(param) {
            this.pagingData = param;
            this.searchList();
        },
        // 이미지 업로드 팝업 오픈
        uploadImageZip: function(obj) {
            if (obj.succcnt == 0) {
                alert('이미지를 업로드할 상품이 존재하지 않습니다.');
                return;
            }
            obj.dealerno = this.dealerno;
            this.uploadParams = obj;
            this.isShowIamgeUpload = true;
        },
        // 이미지 업로드 팝업 닫기
        closeUpload: function(isreload) {
            this.isShowIamgeUpload = false;
            if (isreload) {
                this.searchList();
            }
        },
        // 업로드 실패 팝업 오픈
        goUploadFailPopup: function(obj) {
            this.excelFailParams = obj;
            this.isShowUploadFail = true;
        },
        // 업로드 실패 팝업 닫기
        closeUploadFailPopup: function() {
            this.isShowUploadFail = false;
        },
        // 상품코드조회 목록 팝업 오픈
        goGoodsListPopup: function() {
            let params = { isread: true };
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, params, null);
        },
        // 오리지널 코드조회 팝업 오픈
        goOriginalCodePopup: function() {
            let params = { isread: true };
            this.$eventBus.$emit('modalShow', SearchOriginalCodePopup, params, null);
        },
        // 파트너사 코드조회 팝업 오픈
        goDealernoPopup: function() {
            this.$eventBus.$emit('modalShow', PartnersListPopup, null, null);
        }
    }
}
</script>

<style scoped>
.boxing dl dd ul li {
    font-size: 14px;
    color: #333;
    line-height: 28px;
}
.boxing dl dd ul li:before {
    content: "";
    display: inline-block;
    width: 3px;
    height: 3px;
    border-radius: 50%;
    background: #333;
    margin-right: 10px;
    vertical-align: middle;
}
</style>