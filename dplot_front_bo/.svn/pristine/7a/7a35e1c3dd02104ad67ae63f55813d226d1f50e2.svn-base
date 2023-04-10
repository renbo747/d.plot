<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="boxing">
                <div class="form-area">
                    <dl>
                        <dt>수정권한설정</dt>
                        <dd class="full">
                            <div class="check-field">
                                <ul class="clearfix">
                                    <li v-for="(item,index) in this.commonCode.muaddauthtype" :key="index">
                                        <input type="checkbox" v-model="muaddauthtypearr" :id="'chk_'+index" true-value="[]" :value="item.cmcode"><label :for="'chk_'+index">{{item.codename}}</label>
                                    </li>
                                </ul>
                            </div>
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isWrite" @click="onSave">저장</button>
            </div>
        </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator'
export default {
    name: 'admin.configuration.manage.goodsmodifyauth',
    components: {
        CommonNavigator,
    },
    data() {
        return {
            muaddauthtype: '',
            muaddauthtypearr: [],
            commonCode: {
                muaddauthtype: [],
            },
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
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['MUADDAUTHTYPE'];
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
        onSearch() {
            let param = {};

            this.$http.post('/admin/configuration/manage/goodsauth/search', param)
            .then(result => {
                if(result.statusCode === 200) {
                    this.muaddauthtype = result.data.muaddauthtype;
                    if(this.muaddauthtype !== '') {
                        let authTypeArray = this.muaddauthtype.split(",");
                        for (let type of authTypeArray) {
                            this.muaddauthtypearr.push(type);
                        }
                    }

                    if(result.data.first) {
                        for (let type of this.commonCode.muaddauthtype) {
                            this.muaddauthtypearr.push(type.cmcode);
                        }
                    }
                }
            })
        },
        onSave() {
            let param = {
                muaddauthtype: this.muaddauthtype
            }

            if(confirm("저장하시겠습니까?")){
                this.$http.post('/admin/configuration/manage/goodsauth/save', param)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("저장되었습니다.");
                    }
                })
            }
        }
    },
    watch: {
        'muaddauthtypearr': function(newValue, oldValue){
            let optionAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_OPTION;
            let marketPriceAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_MARKET_PRICE;
            let priceAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_PRICE;
            let stockCntAuthCode = this.$store.getters['ADMIN'].EDIT_AUTH_STOCK_CNT;
            // 옵션 체크시 정상가, 판매가, 재고 체크
            if (oldValue.indexOf(optionAuthCode) < 0 && newValue.indexOf(optionAuthCode) > -1) {
                if (this.muaddauthtypearr.indexOf(marketPriceAuthCode) < 0) {
                    this.muaddauthtypearr.push(marketPriceAuthCode);
                }
                if (this.muaddauthtypearr.indexOf(priceAuthCode) < 0) {
                    this.muaddauthtypearr.push(priceAuthCode);
                }
                if (this.muaddauthtypearr.indexOf(stockCntAuthCode) < 0) {
                    this.muaddauthtypearr.push(stockCntAuthCode);
                }
            }
            // 정상가, 판매가, 재고 체크해제시 옵션체크 해제
            if ((oldValue.indexOf(marketPriceAuthCode) > -1 && newValue.indexOf(marketPriceAuthCode) < 0)
            || (oldValue.indexOf(priceAuthCode) > -1 && newValue.indexOf(priceAuthCode) < 0)
            || (oldValue.indexOf(stockCntAuthCode) > -1 && newValue.indexOf(stockCntAuthCode) < 0)) {
                let findIdx = this.muaddauthtypearr.indexOf(optionAuthCode);
                if (findIdx > 0) {
                    this.muaddauthtypearr.splice(this.muaddauthtypearr.indexOf(optionAuthCode), 1);
                }
            }
            this.muaddauthtype = this.muaddauthtypearr.join();
        }
    }
}
</script>

<style>

</style>