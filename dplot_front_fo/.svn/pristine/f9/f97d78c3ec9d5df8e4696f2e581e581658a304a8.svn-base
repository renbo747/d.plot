
export default {
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
          title: "공지사항",
          searchIcon: true,
          cartIcon: true,
          homeIcon: false,
        });
    },  
    mounted() {
      this.onInit();        
    },
    data() {
      return {
          Data : {},
      };
    },
    methods: {
      onInit: function() {
        let DetailParams = {
            idx: this.$route.params.idx,
        }
        this.$http.post('/cscenter/noticedetail',DetailParams).then(result => {
            this.Data = result.data.list;
        })
      }
    },
  };