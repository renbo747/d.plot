import CommonNavigator from '@/views/admin/common/CommonNavigator'
import VJstree from 'vue-jstree/dist/vue-jstree.js'
import util from "@js/util";

// "의류<span style='color: red'> (123)</span>"
export default {
  name: 'admin.goods.category.categoryList',
  components: {
    CommonNavigator,
    VJstree
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if (this.isRead) {
        this.onSearch();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  data() {
    return {
      data: [],
      detailData: {
        sort: '',
        path: '',
        name: '',
        engname: '',
        idx: '',
        linkurl: '',
        depth: '',
        hidden: '',
        goodsno: '',
        parent: '',
        issave: '',
      },
      copyimgcheck: false,        // pc이미지 복사여부
      files: {
        pcimgfile: '',          // pc이미지
        mobileimgfile: '',      // mobile이미지
      },
      imgPreview: {
        pcimgfile: '',          // pc이미지 미리보기
        mobileimgfile: '',      // mobile이미지 미리보기
      },
      addfile: {
        pcimgfile: '',
        mobileimgfile: '',
      },
      removefile: [],
      changeList: {},  // 변경된 트리의 내용 저장
      changeCheck: false,
      isRead: false,
      isWrite: false,
    }
  },
  methods: {
    // 첨부파일(탐색기 열기)
    fileAttach: function(fileTypeKey) {
        if (Array.isArray(this.$refs[fileTypeKey])) {
            this.$refs[fileTypeKey][0].click();
        } else {
          this.$refs[fileTypeKey].click();
        }
    },
    // 가져온 파일 세팅
    handleFileUpload: function(fileTypeKey, target) {
        // PC, 모바일 대표이미지
        let file = this.$refs[fileTypeKey];
        if (this.$util.isNull(file)) {
            return;
        }

        if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
            this.removefile.push(this.files[fileTypeKey].idx);
        }

        let fileType = ['image/png','image/jpeg', 'image/png'];
        if(!fileType.includes(file.files[0].type)){
            alert('jpg, jpeg, png파일만 첨부 가능합니다.');
            file.value = null;
            this.files[fileTypeKey] = '';
            return false;
        }
        if(file.files[0].size > 1048576 * 10){
            alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
            file.value = null;
            this.files[fileTypeKey] = '';
            return false;
        }
        let fileObj = {
            file: file.files[0],
            iscreated: true
        }
        
        this.files[fileTypeKey] = fileObj;
        this.addfile[fileTypeKey] = fileObj;
        this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);
        
        if(fileTypeKey === 'pcimgfile' && this.copyimgcheck){
            this.setSameAsPcepreImg();
        }
        // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
        if (fileTypeKey === 'mobileimgfile') {
            this.copyimgcheck = false;
        }
    },
    removeFile(fileTypeKey, index) {
        if(confirm('파일을 삭제 하시겠습니까?')){
            if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
              this.removefile.push(this.files[fileTypeKey].idx);
            }

            this.files[fileTypeKey] = '';
            this.addfile[fileTypeKey] = ''
            this.imgPreview[fileTypeKey] = '';
            this.$refs[fileTypeKey].value = null;
            
            // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
            if (fileTypeKey === 'mobileimgfile') {
                this.copyimgcheck = false;
            }
        }
    },
    // PC 대표이미지와 동일하게 세팅
    setSameAsPcepreImg: function() {
        // 모바일 이미지 변경 or 삭제시 pc이미지 복사로 생성된 경우 idx가 있어서 해당 경우 체크
        if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'idx')){
            if(!this.removefile.includes(this.files.mobileimgfile.idx)) {
              this.removefile.push(this.files.mobileimgfile.idx);
            }
        }
        if (this.copyimgcheck) {
            this.files.mobileimgfile = this.files.pcimgfile;
            this.addfile.mobileimgfile = this.files.pcimgfile;
            this.imgPreview.mobileimgfile = this.imgPreview.pcimgfile;
        } else {
            this.files.mobileimgfile = '';
            this.addfile.mobileimgfile = '';
            this.imgPreview.mobileimgfile = '';
        }
    },
    // 검색
    onSearch() {
      let param = {};

      this.$http.post("/admin/goods/category/search", param)
          .then(result => {
            let data = result.data;

            this.data = [];
            this.changeList = {};
            this.changeCheck = false;
            this.data.push(data);
            this.copyimgcheck= false;        // pc이미지 복사여부
            this.files = {
              pcimgfile: '',          // pc이미지
              mobileimgfile: '',      // mobile이미지
            };
            this.imgPreview= {
              pcimgfile: '',          // pc이미지 미리보기
              mobileimgfile: '',      // mobile이미지 미리보기
            };
            this.addfile= {
              pcimgfile: '',
              mobileimgfile: '',
            };
            this.$refs.pcimgfile.value = null;
            this.$refs.mobileimgfile.value = null;
            this.removefile= [],
            this.detailData = this.$options.data().detailData;
            this.$refs.tree.initializeData(this.data);

            // this.collapseAll(this.data);
            this.categoryCount(this.data);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 카테고리 저장
    onSave() {
      let params = this.detailData;

      if (util.isNull(this.detailData.depth)) {
        alert("카테고리를 선택해주세요.");
        return;
      }

      if (util.isNull(this.detailData.name)) {
        alert("카테고리명(한글)을 입력해주세요.");
        this.$refs.name.focus();
        return;
      }

      if (util.isNull(this.detailData.engname)) {
        alert("카테고리명(영문)을 입력해주세요.");
        this.$refs.name.focus();
        return;
      }

      if(util.isNull(this.detailData.sort)) {
        alert("정렬순서를 입력해주세요.");
        return;
      }

      if(this.$util.isNull(this.files.pcimgfile)){
        alert("대표이미지(PC)를 첨부해주세요.");
        return false;
      }
      if(this.$util.isNull(this.files.mobileimgfile)){
          alert("대표이미지(모바일)를 첨부해주세요.");
          return false;
      }

      let files = [];

      if(!this.$util.isNull(this.addfile.pcimgfile)){
          files.push({key: 'pcimgfile', file: this.addfile.pcimgfile.file});
      }
      if(!this.$util.isNull(this.addfile.mobileimgfile)){
          // pc이미지 복사를 한 경우 file key가 없고 복사시 기존에 저장되어있는 pcimgfile을 저장해야하므로 pcimgfile의 idx를 보냄
          if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'file')){
              files.push({key: 'mobileimgfile', file: this.addfile.mobileimgfile.file});
          } else {
              params.copycheck = true;
              params.copyidx = this.files.pcimgfile.idx;
          }
      }
      params.files = files;
      params.removefile = new Set(this.removefile);

      this.$http.post("/admin/goods/category/save", params)
          .then(result => {
            if (result.statusCode === 200) {
              alert("저장이 완료되었습니다.");
              this.onSearch();
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 카테고리 삭제
    onDelete() {
      if (util.isNull(this.detailData.idx)) {
        return;
      }

      this.$http.post("/admin/goods/category/delete", this.detailData)
          .then(result => {
            if (result.statusCode === 200) {
              alert("삭제가 완료되었습니다.");
              this.onSearch();
            } else if (result.statusCode === 400) {
              alert(result.message);
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });

    },

    // 트리 전체 펼침
    collapseAll(data) {
      if (data === undefined) {
        data = this.data;
      }

      for (let i = 0; i < data.length; i++) {
        if (Object.prototype.hasOwnProperty.call(data[i], 'children')) {
          data[i].opened = true;
          this.collapseAll(data[i].children);
        }
      }
    },

    // 트리 전체 닫기
    expandAll(data) {
      if (data === undefined) {
        data = this.data;
      }

      for (let i = 0; i < data.length; i++) {
        if (Object.prototype.hasOwnProperty.call(data[i], 'children')) {
          data[i].opened = false;
          this.expandAll(data[i].children);
        }
      }
    },

    // 트리 클릭시 이벤트
    onClick(node, item, e) {
      this.copyimgcheck= false;        // pc이미지 복사여부
      this.addfile= {
        pcimgfile: '',
        mobileimgfile: '',
      };
      this.removefile= [],
      this.detailData = this.$options.data().detailData;
      
      // 루트 선택시
      if(item.idx === 0){
        return;
      }

      // 최상위 메뉴 추가 클릭시
      if (item.parent === "0") {
        this.detailData.path = '추가';
        this.detailData.depth = 1;
        this.detailData.parent = 0;
        this.detailData.hidden = 0;
        this.detailData.goodsno = 0;
        this.detailData.istrash = 'F';
        this.detailData.issave = 'T';
        if(this.files.pcimgfile !== ''){
          this.files.pcimgfile = '';
          this.imgPreview.pcimgfile = '';
        }
        if(this.files.mobileimgfile !== ''){
          this.files.mobileimgfile = '';
          this.imgPreview.mobileimgfile = '';
        }
        return;
      }

      let param = {
        idx: item.idx
      }

      if (item.idx === -1) {
        param.idx = item.parent;
      }

      this.$http.post("/admin/goods/category/search/detail", param)
          .then(result => {
            let data = result.data.detail;
            this.files = result.data.files;

            if(this.files.pcimgfile !== ''){
              this.imgPreview.pcimgfile = this.files.pcimgfile.fullpath;
            }
            if(this.files.mobileimgfile !== ''){
              this.imgPreview.mobileimgfile = this.files.mobileimgfile.fullpath;
            }

            // '추가'가 아닌 카테고리
            if (item.idx !== -1) {
              let pcDomain = process.env.VUE_APP_PC_DOMAIN;
              this.detailData = data;
              this.detailData.goodsno = item.count;
              this.detailData.linkurl = pcDomain + '/shop/list/' + data.idx;
              this.detailData.issave = 'F';
            } else {
              this.detailData.path = data.path + " > 추가";
              this.detailData.idx = '';
              this.detailData.goodsno = 0;
              this.detailData.parent = data.idx;
              this.detailData.depth = data.depth + 1;
              this.detailData.hidden = 0;
              this.detailData.sort = 1;
              this.detailData.istrash = 'F';
              this.detailData.issave = 'T';
              this.files = {
                pcimgfile: '',          // pc이미지
                mobileimgfile: '',      // mobile이미지
              };
              this.imgPreview= {
                pcimgfile: '',          // pc이미지 미리보기
                mobileimgfile: '',      // mobile이미지 미리보기
              };
              this.addfile= {
                pcimgfile: '',
                mobileimgfile: '',
              };
              this.$refs.pcimgfile.value = null;
              this.$refs.mobileimgfile.value = null;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          })
    },

    // tree Drag 끝났을 때
    onDragEnd(node, item, e) {
      this.$refs.tree.isDraggedCancel = false;
    },

    // tree Drag 완료 됐을 때
    onDrop(node, item, draggedItem, e) {
      draggedItem.parent = item.idx;
      draggedItem.depth = item.depth + 1;
      this.sortChild(item);
      this.categoryCount(this.data);
    },

    // tree Drag 시작했을 때
    onDragStart(node, item, e) {
      let depth = this.checkDepth(item, 1);
      this.changeDropDisabled(this.data[0], depth);
    },

    // tree Drag 이동했을 중간
    onDropBefore(node, item, draggedItem, e) {
      if (item.idx === draggedItem.parent) {
        draggedItem.sort = -1;
        this.sortChild(item);
      }
    },

    // tree Drag 마우스 땠을 때
    onDropMouseUp() {
      if (!confirm("선택한 위치로 카테고리를 이동하시겠습니까?")) {
        this.$refs.tree.isDraggedCancel = true;
      }
    },

    checkDepth(data, max) {
      let tempMax = max;
      if (Object.prototype.hasOwnProperty.call(data, 'children')) {
        for (let child in data.children) {
          let temp = this.checkDepth(data.children[child], max + 1);
          if (tempMax < temp) {
            tempMax = temp;
          }
        }
      }
      return tempMax;
    },

    changeDropDisabled(data, depth) {
      if (data.idx !== -1) {
        data.dropDisabled = data.depth + depth > 4 ? true : false;
      }
      if (Object.prototype.hasOwnProperty.call(data, 'children')) {
        for (let child in data.children) {
          this.changeDropDisabled(data.children[child], depth);
        }
      }
    },

    // 자식 정렬
    sortChild(item) {
      var sortBy = function (attr) {
        return function (a, b) {
          a = a[attr];
          b = b[attr];
          if (a === 'a') {
            return 1;
          }
          if (b === 'a') {
            return -1;
          }
          if (Number(a) < Number(b)) {
            return -1;
          }
          if (Number(a) > Number(b)) {
            return 1;
          }
          return 0;
        }
      }
      item.children.sort(sortBy('sort'))

      for (let i in item.children) {
        if (item.children[i].idx !== -1) {
          item.children[i].sort = i;
          let temp = {
            idx: item.children[i].idx,
            sort: item.children[i].sort,
            parent: item.children[i].parent,
            oriparent: item.children[i].oriparent,
            depth: item.children[i].depth
          }
          this.changeList[item.children[i].idx] = temp;
        }
      }
      this.changeCheck = true;
    },

    // 이동저장
    onTreeSave() {
      if (!this.changeCheck) {
        alert("이동한 카테고리가 없습니다.");
        return false;
      }
      let changeList = [];
      for (let key in this.changeList) {
        changeList.push(this.changeList[key]);
      }

      let params = {
        changeList: changeList,
      }
      if (confirm("이동한 카테고리를 저장하시겠습니까?")) {
        this.$http.post("/admin/goods/category/tree", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.onSearch();
              } else {
                alert("저장에 실패했습니다.");
              }
            })
            .catch(error => {
              this.$util.debug(error);
            })
      }
    },

    // 카테고리 포함 상품 갯수 로직
    categoryCount(data) {
      let oneDepth = data[0].children;

      for (let i = 0; i < oneDepth.length; i++) {
        let oneDepthIdx = oneDepth[i].idx;
        let oneDepthCount = oneDepth[i].oricount;
        let twoDepth = oneDepth[i].children;

        // '추가' 카테고리
        if (oneDepthIdx === -1) {
          return;
        }

        if (!this.$util.isNull(twoDepth)) {
          twoDepth = twoDepth.filter(function (element) {
            return element.parent === oneDepthIdx;
          });

          for (let j = 0; j < twoDepth.length; j++) {
            let twoDepthIdx = twoDepth[j].idx;
            let twoDepthCount = twoDepth[j].oricount;
            let threeDepth = twoDepth[j].children;
            oneDepthCount += twoDepthCount;

            threeDepth = threeDepth.filter(function (element) {
              return element.parent === twoDepthIdx;
            });

            for (let k = 0; k < threeDepth.length; k++) {
              let threeDepthIdx = threeDepth[k].idx;
              let threeDepthCount = threeDepth[k].oricount;
              let fourDepth = threeDepth[k].children;
              oneDepthCount += threeDepthCount;
              twoDepthCount += threeDepthCount;

              fourDepth = fourDepth.filter(function (element) {
                return element.parent === threeDepthIdx;
              });
              for (let l = 0; l < fourDepth.length; l++) {
                let fourDepthCount = fourDepth[l].oricount;
                if (!this.$util.isNull(fourDepthCount)) {
                  oneDepthCount += fourDepthCount;
                  twoDepthCount += fourDepthCount;
                  threeDepthCount += fourDepthCount;
                }
              }

              // 3 Depth Count Setting
              if (!this.$util.isNull(threeDepth[k].value)) {
                threeDepth[k].count = threeDepthCount;
                threeDepth[k].text = threeDepth[k].value + "<span style='color: red'>(" + threeDepthCount + ")</span>";
                if(threeDepth[k].hidden === 1) {
                  threeDepth[k].text = "<span style='background-color: #cccccc'>"+threeDepth[k].text+"</span>";
                }
              }
            }

            // 2 Depth Count Setting
            if (!this.$util.isNull(twoDepth[j].value)) {
              twoDepth[j].count = twoDepthCount;
              twoDepth[j].text = twoDepth[j].value + "<span style='color: red'>(" + twoDepthCount + ")</span>";
              if(twoDepth[j].hidden === 1) {
                twoDepth[j].text = "<span style='background-color: #cccccc'>"+twoDepth[j].text+"</span>";
              }
            }
          }
        }

        // 1 Depth Count Setting
        if (!this.$util.isNull(oneDepth[i].value)) {
          oneDepth[i].count = oneDepthCount;
          oneDepth[i].text = oneDepth[i].value + "<span style='color: red'>(" + oneDepthCount + ")</span>";
          if(oneDepth[i].hidden === 1) {
            oneDepth[i].text = "<span style='background-color: #cccccc'>"+oneDepth[i].text+"</span>";
          }
        }
      }
    },
  },
}
