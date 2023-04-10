export default {
    beforeCreate() {
      this.$store.commit("onSubHeaderOption", {
        title: "BRAND",
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
      });
    },
    data() {
      return {
        currentTab: 0,
        tempTab: 0,
        tabTitle: [],
        selectCateIdx : 0,
        selectBrand : 'ALL',
        alphaExistETC : false, // ETC 여부
        letterExistETC: false, // ETC 여부
        enRadio: {
          id: "enRadio",
          label: "ABC",
        },
        koRadio: {
          id: "koRadio",
          label: "가나다",
        },
        selectRadioData: "ABC",
        //전체 체크 데이터
        allCheckData: {
          id: "all",
          label: "ALL",
        },
        isAlphabet: true,
        // 영어체크 데이터
        alphabetData: [],
        //한글체크 데이터
        letterData: [],
        // 브랜드탭 데이터
        brandData: [],
      };
    },
    created() {
      if(this.$route.params.init) {
        this.$storage.removeSessionStorage('param-' + this.$route.name);
      } else {
        const param = this.$storage.getSessionStorage('param-' + this.$route.name);
        if(!this.$util.isEmpty(param)) {
            this.selectCateIdx = param.selectcateidx;
            this.selectBrand = param.selectbrand;
            this.selectRadioData = param.selectradiodata;
            this.tempTab = param.currenttab;
        }
      }
    },
    mounted() {
      let param = {
        cateidx : this.selectCateIdx,
      }
      this.$http.post('/brand/mzlist',param)
      .then(result => {
          if(result.statusCode === 200) {
              let data = result.data;
              this.tabTitle = data.catelist;
              this.brandData = data.brandlist;
              this.alphabetData = data.alphabetdata;
              this.letterData = data.letterdata;
              this.$nextTick(() => {
                this.currentTab = this.tempTab;
              });
              this.initFilter();
          }
      });
    },
    updated() {
      this.$nextTick(() => {
          if (this.brandData.length > 0) {
              this.$eventBus.$emit('scrollTo', this.$route.name, (flag) => {
                  if (!flag) {
                      window.scrollTo(0, 0);
                  }
              });
          } else {
              window.scrollTo(0, 0);
          }
      })
    },
    methods: {
      // 라디오 버튼 값
      changeRadioData() {
        this.selectBrand = 'ALL';
        this.saveFilter();
      },
  
      handleAllCheck(label) {
        this.selectBrand = label;
        this.saveFilter();
      },
      saveFilter() {
        this.$store.commit("savedPosition", {});
        let param = {
          selectcateidx: this.selectCateIdx,
          selectbrand: this.selectBrand,
          selectradiodata: this.selectRadioData,
          currenttab: this.currentTab
        }
        this.$storage.setSessionStorage('param-' + this.$route.name, param);
      },
      changeCategory(index) {
        this.$store.commit("savedPosition", {});
        let cateidx = this.tabTitle[index].idx;
        this.selectCateIdx = cateidx;
        this.selectBrand = 'ALL';
        this.currentTab = index;

        let param = {
          cateidx: cateidx,
          selectcateidx: this.selectCateIdx,
          selectbrand: this.selectBrand,
          selectradiodata: this.selectRadioData,
          currenttab: this.currentTab,
          iskeep: true,
          reqname: this.$route.name
        }

        this.$http.post('/brand/mzlist', param)
        .then(result => {
            if(result.statusCode === 200) {
                let data = result.data;
                this.brandData = data.brandlist;
                this.alphabetData = data.alphabetdata;
                this.letterData = data.letterdata;
                this.selectRadioData = "ABC";
                this.initFilter();
            }
        });

      },
      initFilter() {
        this.alphaExistETC = false;
        this.letterExistETC = false;
        if (window.sessionStorage.getItem('platform') === "MAC001") {
          if(this.alphabetData.length === 1 && this.letterData.length > 0) {
            if(this.alphabetData[0].label === 'ETC') {
              this.letterData.push(this.alphabetData[0]);
              this.alphabetData.splice(0,1);
            }
          }
        }

        this.alphabetData.sort((a,b) => {
          if(a.label === 'ETC') return 1;
          if(b.label === 'ETC') return -1;
          return b.label > a.label ? -1 : 1;
        });

        this.letterData.sort((a,b) => {
          if(a.label === 'ETC') return 1;
          if(b.label === 'ETC') return -1;
          return b.label > a.label ? -1 : 1;
        });

        
        if (window.sessionStorage.getItem('platform') !== "MAC001") {
          if(this.alphabetData.length > 0) {
            if(this.alphabetData[this.alphabetData.length -1].label === 'ETC') {
              this.letterData.push(this.alphabetData[this.alphabetData.length -1]);
            }
          }
        }

        if(this.alphabetData.length > 0) {
          if(this.alphabetData[this.alphabetData.length -1].label === 'ETC') {
            this.alphaExistETC = true;
          }
        }
        if(this.letterData.length > 0) {
          if(this.letterData[this.letterData.length -1].label === 'ETC') {
            this.letterExistETC = true;
          }
        }
      }
    },
  };