export default {
    beforeCreate() {
      this.$store.commit("onSubHeaderOption", {
        title: "MAGAZINE",
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
      });
    },
    mounted() {
        this.getTrendList();
    },
    data() {
      return {
        // 트렌드 라디오 버튼
        trendTabType: [],
        filterData: [],
        // 내용
        contentsData: [],
        selectedCategory: "",
      };
    },
    methods: {
        getTrendList() {
            this.$http.post('/trend/list', {})
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.trendTabType = data.trendcategory; //상단 카테고리
                    this.contentsData = data.trendlist;  // 목록
                    if(this.$route.query.catename == "ALL") {
                      this.filterData = this.contentsData;
                    } else {
                      this.filterData = this.contentsData.filter(m => m.label === this.$route.query.catename);
                    }
                }
            })
        },
        // 상단 카테고리 클릭시 필터링 함수
        handleInquiry(index) {
            if(index === 0) {
              this.$router.push({query: {catename: 'ALL'}});
            } else {
              this.$router.push({query: {catename: this.trendTabType[index].subject}});
            }
        }
    },
  };