<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header">
                <h2>파트너사 목록 조회</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="boxing search-area">
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
                            <select v-model="searchData.skey">
                                <option value="">전체</option>
                                <option value="id">아이디</option>
                                <option value="name">업체명</option>
                                <option value="bizno">사업자등록번호</option>
                            </select>
                            <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList"/>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="searchList">검색</button>
                    <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{ list.length }}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 300px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <caption>검색결과</caption>
                        <colgroup>
                            <col width="4%"/><!-- No -->
                            <col width="15%"/><!-- 파트너사코드 -->
                            <col width="18%"/><!-- 아이디 -->
                            <col width=""/><!-- 업체명 -->
                            <col width="25%"/><!-- 사업자등록번호 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>파트너사코드</th>
                                <th>아이디</th>
                                <th>업체명</th>
                                <th>사업자등록번호</th>
                            </tr>
                        </thead>
                        <tbody v-if="list.length > 0">
                            <tr v-for="(item, index) in list" :key="index">
                                <td>{{ index+1 }}</td>
                                <td>{{ item.dealercode }}</td>
                                <td>{{ item.userid }}</td>
                                <td>{{ item.name }}</td>
                                <td>{{ $util.bizNumberFilter(item.bizno, 0) }}</td>
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
    <!-- /제주/도서산간지역 조회 팝업 -->
</template>

<script>
export default {
    name: 'partnersListPopup',
    mounted() {
        // 목록 조회
        this.searchList();
    },
    data :function(){
        return {
            searchData: {
                skey: '',
                sword: ''
            },
            list: [],
            listcnt: 0
        }
    },
    methods : {
        // 검색조건 초기화
        initSearchData: function() {
            this.searchData = this.$options.data().searchData;
        },
        // 목록 조회
        searchList: function() {
            let params = Object.assign(this.searchData);
            this.$http.post('/admin/goods/manage/partner/list', params).then(result => {
                this.$util.debug(result);
                this.list = result.data.list;
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
    }
}
</script>