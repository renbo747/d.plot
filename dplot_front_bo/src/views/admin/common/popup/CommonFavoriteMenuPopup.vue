<template>
    <!-- 즐겨찾기 설정 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1000px;">
            <div class="pop-header">
                <h2>즐겨찾기 설정</h2>
                <button type="button" class="pop-close" v-on:click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="favorite-setting" v-if="menuList.length > 0">
                    <div v-for="i in menuRowNum" :key="i">
                        <dl v-for="(menu,num) in tableMenuList[i-1]" :key="num" :class="num % 5 === 0 && i === 1 ? 'left': (num % 5 === 4 || num === menuList.length -1) && i === 1 ? 'right':''">
                            <dt v-if="menu.name !== ''">{{menu.name}}</dt>
                            <dd v-for="(row,index) in menu.childmenulist" :key="index">
                                <input type="checkbox" v-model="checkedList" :id="'fav'+row.uppercode+index" true-value="[]" :value="row.code"><label :for="'fav'+row.uppercode+index">{{row.name}}</label>
                            </dd>
                        </dl>
                    </div>
                </div>
                <hr class="solid" />
                <div class="clearfix">
                    <div class="btn-group fl">
                        <button type="button" class="btn big gray" v-on:click="resetAll">전체초기화</button>
                    </div>
                    <div class="btn-group fr">
                        <button type="button" class="btn big blue" v-on:click="goSave">저장</button>
                        <button type="button" class="btn big darkgray" v-on:click="goCancel">취소</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /즐겨찾기 설정 팝업 -->
</template>

<script>
export default {
    name: 'CommonFavoriteMenuPopup',
    data :function(){
        return {
            menuList: [],       // 조회해온 메뉴리스트
            tableMenuList: [],  // 테이블 모양으로 5개씩 배열로 만들어놓은 메뉴리스트
            checkedList: [],    // 즐겨찾기 메뉴리스트
            menuRowNum: 0,
            lastColNum: 0,
        }
    } ,
    mounted() {
        let param = {};
        this.$http.post('/admin/common/favorite-menu', param)
        .then(result => {
            this.menuList = result.data.favoritemenu;
            this.menuList.forEach(menulist => {
                let bookmarklist = menulist.childmenulist.filter(menu => menu.isbookmark === 'T');
                bookmarklist.forEach(menu => {
                    this.checkedList.push(menu.code);
                });
            });

            if(this.menuList.length === 0) {
                this.menuRowNum = 0;
                this.lastColNum = 0;
            } else{
                this.menuRowNum = parseInt(this.menuList.length / 5) + (this.menuList.length % 5 === 0 ? 0 : 1);
                this.lastColNum = this.menuList.length % 5 === 0 ? 5 : this.menuList.length % 5;
            }
            for(let i = 0; i < this.menuRowNum; i++) {
                let tempList = [];
                let n = i === this.menuRowNum -1 ? this.lastColNum : 5;
                for(let j = 0; j < n; j++) {
                    let num = i * 5 + j;
                    tempList.push(this.menuList[num]);
                }
                this.tableMenuList.push(tempList);
            }

            let size = this.menuList.length;
            if(size >= 6 && size < 10) {
                for(let i = size; i < 10; i++) {
                    let tempMenu = {};
                    tempMenu.name = '';
                    tempMenu.childmenulist = [];
                    this.tableMenuList[1].push(tempMenu);
                }
            }
        })
        .catch(error => {
            this.$util.debug(error);
        })
    },
    methods : {
        resetAll() {
            this.checkedList = [];
        },
        goCancel() {
            this.$modal.hide('commonModal');
        },
        goSave() {
            if (confirm("저장하시겠습니까?")) {
                if(this.checkedList.length === 0){
                    this.checkedList.push('');
                }

                let param = {
                    checklist: this.checkedList
                };

                this.$http.post('/admin/common/favorite/save', param)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("저장되었습니다.");
                        this.goCancel();
                    }
                })
            }
        }
    }
}
</script>
