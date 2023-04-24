export default {
    name: 'SearchOriginalCodePopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        this.$refs.sword.focus();
        this.searchData.goodsno = this.params.goodsno;
        this.searchData.goodsname = this.params.goodsname;
        this.searchData.optioncode = this.params.optioncode;
        this.linkedList = this.params.linkedList;
        this.searchErpList();
        if (!this.params.isread && this.linkedList.length == 0) {
            this.searchLinkedList();
        } else {
            this.linkedList.forEach(item => {
                item.ischecked = false;
            });
        }
    },
    data :function(){
        return {
            searchData: {
                skey: 'orgcode',
                sword: '',
                orgcode: '',
                orgname: '',
                psort: 'org_item_code-asc',
                goodsno: '',
                goodsname: '',
                optioncode: ''
            },
            sortData: {
                org_item_code : 'org_item_code-asc',
                erpoptcode: 'erpoptcode-asc'
            },
            isallchkerp: false,
            isallchklink: false,
            erpCodeList: [],
            linkedList: []
        }
    },
    methods : {
        // 오리지널 코드목록 조회
        searchErpList: function() {
            if (this.searchData.skey==='orgcode') {
                this.searchData.orgcode = this.searchData.sword;
                this.searchData.orgname = '';
            } else if (this.searchData.skey==='orgname') {
                this.searchData.orgcode = '';
                this.searchData.orgname = this.searchData.sword;
            }
            this.$http.post('/admin/common/erp/goods', this.searchData)
                .then(result => {
                    this.$util.debug(result);
                    this.erpCodeList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 연결상품목록 조회
        searchLinkedList: function() {
            this.$http.post('/admin/goods/regist/linked/list', this.searchData)
                .then(result => {
                    this.$util.debug(result);
                    this.linkedList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 테이블 정렬
        sortToggle: function(target, key){
            let arr = key.split("-");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '-' + sortOrder;
        
            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;
        
            // 오리지널코드목록 조회
            if (target == 'erp') {
                this.erpCodeList.sort((a, b) => {
                    a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                    b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                    if (a[sortKey] < b[sortKey]) {
                        return sortOrder == 'asc'? -1: 1;
                    } else if (a[sortKey] > b[sortKey]) {
                        return sortOrder == 'asc'? 1: -1;
                    }
                    return 0;
                });
            }
            // 연결상품목록 조회
            else if (target == 'linked') {
                this.linkedList.sort((a, b) => {
                    a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                    b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                    if (a[sortKey] < b[sortKey]) {
                        return sortOrder == 'asc'? -1: 1;
                    } else if (a[sortKey] > b[sortKey]) {
                        return sortOrder == 'asc'? 1: -1;
                    }
                    return 0;
                });
            }
        },
        // // 오리지널코드조회 체크 세팅
        // setIsCheckedErp: function(obj) {
        //     this.erpCodeList.forEach(item => {
        //         item.ischecked = false;
        //     });
        //     obj.ischecked = true;
        // },
        // // 오리지널코드조회 체크 세팅
        // setIsCheckedLink: function(obj) {
        //     this.linkedList.forEach(item => {
        //         item.ischecked = false;
        //     });
        //     obj.ischecked = true;
        // },
        
        // 오리지널코드목록 전체체크
        checkAllErpCodeList: function(value) {
            this.erpCodeList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 오리지널코드목록 개별체크
        checkErpCodeList: function() {
            let checkedList = this.erpCodeList.filter(item => {
                return item.ischecked == true;
            });
            if (this.erpCodeList.length > checkedList.length){
                this.isallchkerp = false;
            } else {
                this.isallchkerp = true;
            }
        },
        // 연결상품목록 전체체크
        checkAllLinkedList: function(value) {
            this.linkedList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 연결상품목록 개별체크
        checkLinkedList: function() {
            let checkedList = this.linkedList.filter(item => {
                return item.ischecked == true;
            });
            if (this.linkedList.length > checkedList.length){
                this.isallchklink = false;
            } else {
                this.isallchklink = true;
            }
        },
        // 연결상품 추가
        addLinked: function() {
            let checkedList = this.erpCodeList.filter(item => {
                return item.ischecked == true;
            });
            if (checkedList.length == 0) {
                alert("추가할 상품을 선택해주세요.");
                return;
            }
            // for (let i=0; i<this.linkedList.length; i++) {
            //     if (this.linkedList[i].erpoptcode == checkedList[0].erpoptcode) {
            //         alert("이미 추가된 상품입니다.");
            //         return;
            //     }
            // }
            // 이미 추가되어 있는 상품 제외
            for (let i=0; i<checkedList.length; i++) {
                let pushObj = Object.assign({}, checkedList[i]);
                pushObj.ischecked = false;
                let existCnt = this.linkedList.filter(item => {
                    return item.erpoptcode == checkedList[i].org_item_code;
                }).length;
                if (existCnt == 0) {
                    pushObj.erpoptcode = pushObj.org_item_code;
                    pushObj.erpoptname = pushObj.org_item_name;
                    this.linkedList.push(pushObj);
                }
            }
            this.isallchkerp = false;
            this.erpCodeList.forEach(item => {
                item.ischecked = false;
            });
        },
        // 연결상품 삭제
        removeLinked: function() {
            let checkedList = this.linkedList.filter(item => {
                return item.ischecked == true;
            });
            if (checkedList.length == 0) {
                alert("삭제할 상품을 선택해주세요.");
                return;
            }
            checkedList.forEach(item => {
                let findIndex = this.linkedList.indexOf(item);
                this.linkedList.splice(findIndex, 1);
            });
            if (this.linkedList.length == 0) {
                this.isallchklink = false;
            }
        },
        // 전체적용
        sendAllList: function() {
            if(this.linkedList.length == 0) {
                alert("적용할 내역이 존재하지 않습니다.");
                return;
            }
            for(let i=0; i<this.linkedList.length; i++) {
                if (this.$util.isNull(this.linkedList[i].cnt) || this.linkedList[i].cnt === 0) {
                    alert('적용할 연결상품의 수량을 입력해주세요.');
                    return;
                }
            }
            if(confirm("전체적용 하시겠습니까?")) {
                this.linkedList.forEach(item => {
                    item.originalprice = this.$util.isNull(item.originalprice)? Number(item.buy_cost) * Number(item.cnt) : item.originalprice;
                })
                // 팝업닫기 - 파라미터 전달
                this.closePopup(this.linkedList);
            }
        },
        // 선택적용
        sendSelectedList: function() {
            let targetList = this.linkedList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (targetList.length == 0) {
                alert("적용할 항목을 선택해 주세요.");
                return;
            }
            for (let i=0; i<targetList.length; i++) {
                if (this.$util.isNull(targetList[i].cnt) || targetList[i].cnt === 0) {
                    alert('적용할 연결상품의 수량을 입력해주세요.');
                    return;
                }
            }
            if(confirm("선택대상을 적용하시겠습니까?")) {
                targetList.forEach(item => {
                    item.originalprice = this.$util.isNull(item.originalprice)? Number(item.buy_cost) * Number(item.cnt) : item.originalprice;
                })
                // 팝업닫기 - 파라미터 전달
                this.closePopup(targetList);
            }
        },
        // 팝업닫기
        closePopup: function(targetList) {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn({list: targetList});
            }
            this.$modal.hide('commonModal');
        }
    }
}