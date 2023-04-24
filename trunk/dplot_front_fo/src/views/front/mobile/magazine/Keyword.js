export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "키워드",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  mounted() {
    this.getKeyword();
  },
  data() {
    return {
      subject: '',
      fullpath: '',
      // 키워드 리스트
      keywordList: [],
      keywordReady: false,
    };
  },
  methods: {
    getKeyword() {
        this.$http.post('/mz/keyword',{})
        .then(result => {
            if(result.statusCode === 200) {
                let data = result.data;
                this.keywordList = data.keywordlist;
                this.subject = data.subject;
                this.fullpath = data.fullpath;
                this.keywordReady = true;
            }
        })
    },
    goLink(list) {
        this.$util.debug(list);
        if (window.sessionStorage.getItem('platform') == "MAC001") {
            if (!this.$util.isNull(list.pclinkurl)) {
                if (list.ispcnwindow == "T") {
                    window.open(list.pclinkurl, "_blank");
                } else {
                    window.location.href = list.pclinkurl;
                }
            }
        } else {
            if (!this.$util.isNull(list.molinkurl)) {
                if (list.ismonwindow == "T") {
                    window.open(list.molinkurl, "_blank");
                } else {
                    window.location.href = list.molinkurl;
                }
            }
        }
    },
  },
};