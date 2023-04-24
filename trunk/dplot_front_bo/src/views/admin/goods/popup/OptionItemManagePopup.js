export default {
    name: 'OptionItemManagePopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        this.searchData.goodsno = this.params.goodsno;
        this.optionItemList = this.params.optionItemList;
        // 첫행 추가
        if (this.optionItemList.length == 0) {
            let params = { 
                status: 'I',
                sort: 1
            };
            this.optionItemList.splice(0, 0, params);
        }
    },
    data :function(){
        return {
            searchData: {
                goodsno: ''
            },
            optionItemList: [],
            delOptionnoList: []
        }
    },
    methods : {
        // 항목추가
        addItem: function(index) {
            if (this.optionItemList.length < 4) {
                let params = { 
                    status: 'I',
                    sort: this.optionItemList[index].sort + 1
                };
                this.optionItemList.splice(index+1, 0, params);
                for(let i=index+2; i<this.optionItemList.length; i++) {
                    this.optionItemList[i].sort = this.optionItemList[i].sort+1;
                }
            }
        },
        // 항목삭제
        removeItem: function(index) {
            if (this.optionItemList[index].status === 'N') {
                this.delOptionnoList.push(this.optionItemList[index].optionno);
            } 
            this.optionItemList.splice(index, 1);
            if (this.optionItemList.length === 0) {
                let params = { 
                    status: 'I',
                    sort: 1
                };
                this.optionItemList.splice(0, 0, params);
            }
        },
        // 데이터 전달
        sendData: function() {
            // 항목명 입력체크
            let chkResult = true;
            for (let i=0; i<this.optionItemList.length; i++) {
                if (this.$util.isNull(this.optionItemList[i].optionname)) {
                    alert("항목명을 모두 입력해주세요");
                    chkResult = false;
                    break;
                }
            }
            if (chkResult) {
                if (confirm("적용하시겠습니까?")) {
                    if( typeof(this.callbackFn) == 'function') {
                        this.callbackFn({list: this.optionItemList, dellist: this.delOptionnoList});
                    }
                    this.$modal.hide('commonModal');
                }
            }
        }
    }
}