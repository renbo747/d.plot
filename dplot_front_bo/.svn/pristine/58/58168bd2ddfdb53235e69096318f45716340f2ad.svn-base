<template>

    <!-- 상품추가 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 60%;">
            <div class="pop-header">
                <h2>응모자조회</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="entry_list">
                <ul>
                    <li class="list_header">
                        <span>번호</span>
                        <span style="width: 15%;">성명</span>
                        <span style="width: 18%;">USER ID</span>
                        <span style="width: 20%;">휴대폰 번호 뒷자리</span>
                        <span style="width: 20%;">응모일</span>
                        <span style="width: 5%;">당첨여부</span>
                    </li>
                    <li v-for="(entry, idx) in entrylist" :key="idx">
                        <span>{{entry.rownum}}</span>
                        <span style="width: 18%;">{{entry.name}}</span>
                        <span style="width: 20%;">{{entry.userid}}</span>
                        <span style="width: 20%;">{{entry.phone}}</span>
                        <span style="width: 23%;">{{entry.regdate}}</span>
                        <span style="width: 5%;">{{entry.issucc}}</span>
                    </li>
                </ul>
            </div>
            </div>
        </div>
    </div> 
</template>

<script>
    export default {
        props: {
            params: Object,
        },
        data() {
            return {
                raffleData: {
                    eventidx: 0,
                },
                entrylist: [
                    /*{
                        name: '홍길',
                        phone: '3040'
                    },
                    {
                        name: '홍길동',
                        phone: '3040'
                    },
                    {
                        name: '홍길동전',
                        phone: '4404'
                    },
                    {
                        name: '김수한무거북이와두루미',
                        phone: '5958'
                    },
                    {
                        name: 'Ashley',
                        phone: '5958'
                    },
                    {
                        name: 'Caroline Brown',
                        phone: '5958'
                    },*/
                ]
            }
        },        
        created() {
            this.raffleData.eventidx = this.params.eventidx;
            this.onSearch();
        },
        mounted() {

        },        
        methods: {
            checkNull : function (t){
                return !!(typeof t == "undefined" || t == null || t == "");
            },
            
            securityTextMasking(t) {
                let originStr = t;
                let maskingStr;
                let strLength;
                
                if(this.checkNull(originStr)) return originStr;                
                strLength = originStr.length;
                
                if(strLength < 3){
                    maskingStr = originStr.replace(/(?<=.{1})./gi, "*");
                }else {
                    let midWord = originStr.substring(1,originStr.length - 1).replace(/(.)/g,'*');
                    midWord < 5 ? midWord : midWord = midWord.substring(0,4);
                    
                    maskingStr = originStr.substring(0,1) + midWord + originStr.substring(originStr.length - 1)
                }
                
                return maskingStr;
            },
            onSearch() {
                let params = {
                    eventidx : this.raffleData.eventidx,
                }
                this.$http.post("/admin/operation/shopping/promotion/raffleentrylist", params)
                    .then(result => {
                        if (result.statusCode === 200) {
                        let data = result.data;


                        // 프로모션 상세 정보
                        this.entrylist = data.entrylist;

                        }
                        else if (result.statusCode === 201) {

                            //alert("당첨자 추첨 전입니다.");
                            $emit('close');
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                });
            }
        },

    }
</script>

<style scoped>
    .entry_list {max-width: 100%; width: 100%; border:1px solid #ddd; padding:10px;}
    .entry_list ul {padding:0;}
    .entry_list ul .list_header {font-weight: 700;}
    .entry_list ul li {list-style: none; font-size: 14px; color: #666; display: flex; padding:5px 0;}
    .entry_list ul li + li {border-top:1px dashed #ddd;}
    .entry_list ul li span {min-width:100px;}
    .entry_list ul li span + span {margin-left: 10px;}
</style>