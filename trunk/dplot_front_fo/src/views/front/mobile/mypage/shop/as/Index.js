import Post from "@views.common/components/ui/modal/Post.vue"
export default {
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "AS전용 문의",
            searchIcon: true,
            cartIcon: true,
            homeIcon: false,
        });
    },
    mounted() {
        //as-신청으로 접근
        if(this.$route.name == 'apply-as'){
            var param = {
                ordgdidx: this.$route.params.pid
            }
            this.onInit(param);
        }
        //as-수정으로 접근
        if(this.$route.name =='apply-as-revise'){
            this.$util.debug(this.$route);
            var rParam = {asidx: this.$route.params.pid};
            this.onRevise(rParam);
        }        
    },    
    data() {
      return {
        skey: 0,
        files:[],
        deletefilelist: [],
        product: {},
        btntext: "문의완료",
        wirter: "",
        tel: "",
        content: "",
        address: {
            addr: "",
            addrroad: "",
            post: "",
            detail: "",
            sigungucode: "",
            buildingcode: "",
            roadnamecode: ""
        },
        //수량
        selectOption: [],
        select: "",
      };
    },
    methods: {
        /**************************
         * 신청 진입
         ****************************/
        onInit(param){
            this.$util.debug("onInit.......");
            //1:1 문의 데이터 로드
            this.$http.post('/mypage/as/apply', param).then(result => {
                if (result.statusCode == 200) {
                    if(result.data.auth == 'success'){
                        this.$util.debug("`onInit success.......`");
                        this.$util.debug(result.data.list);
                        this.product = result.data.list;
                        this.product.delivcnt = result.data.list.ordcnt;
                        this.product.asview = true;
                        if(this.product.delivcnt === 1) {
                            this.select = 1;
                        }
                        for(let i=1; i<=Number(this.product.delivcnt); i++){
                            var temp = {};
                            temp.label = i;
                            temp.value = i;
                            this.selectOption.push(temp);
                        }
                        this.product.selectOption = this.selectOption;
                        this.product.select = this.select;
                        
                        this.wirter = this.product.rcvname;
                        this.tel = this.product.rcvtel1;
                        this.address.addr = this.product.rcvaddr;
                        this.address.addrroad = this.product.rcvaddrroad;
                        this.address.post = this.product.rcvpost;
                        this.address.sigungucode = this.product.rcvsigungucode;
                        this.address.buildingcode = this.product.rcvbuildingcode;
                        this.address.roadnamecode = this.product.rcvroadnamecode;
                        if(this.product.rcvaddrdetail != "" && this.product.rcvaddrdetail != null){
                            this.address.detail = this.product.rcvaddrdetail;
                        }else{
                            this.address.detail = this.product.rcvaddrdetailroad;
                        }
                    }else{
                        this.$eventBus.$emit('alert', '알림' ,"접근 권한이 없습니다.");
                        this.$router.go(-1);
                    }
                }
            })
        },
        /********************
         * 수정 접근
         ********************/
        onRevise(param) {
            this.$util.debug("onRevise .......");
            //1:1 문의 데이터 로드
            this.$http.post('/mypage/as/applydetail', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("onRevise success.......");
                    this.product = result.data.list;
                    this.product.ordcnt = result.data.list.ascnt;
                    this.product.delivcnt = result.data.list.ascnt;

                    this.select = result.data.list.ascnt;
                    this.product.asview = true;
                    for(let i=1; i<=Number(result.data.list.ascnt); i++){
                        var temp = {};
                        temp.label = i;
                        temp.value = i;
                        this.selectOption.push(temp);
                    }
                    this.product.selectOption = this.selectOption;
                    this.product.select = this.select;


                    this.files = result.data.files;
                    this.wirter = this.product.writer;
                    this.tel = this.product.tel;
                    this.address.addr = this.product.addr;
                    this.address.addrroad = this.product.addrroad;
                    this.address.post = this.product.post;
                    this.address.detail = this.product.addrdetail;
                    this.address.sigungucode = this.product.sigungucode;
                    this.address.buildingcode = this.product.buildingcode;
                    this.address.roadnamecode = this.product.roadnamecode;
                    //this.content = this.product.content;
                    this.content = this.product.content.replace(/(<br>|<br\/>|<br \/>)/g, '\r\n');
                    this.btntext = "수정완료";
                    this.$refs.imageupload.bindFile(this.files);
                    this.product.ascounter = true;

                }
            })            
        },          
        /**************************
         * 상세주소 입력
         ****************************/
         addressInput2(val) {
            this.address.detail = val;
            this.$util.debug(this.address.detail);
        },
        /**************************
         * 신청완료 버튼 클릭 
         ****************************/
        applyComplt() {
            if(this.product.select == ""){
                this.$eventBus.$emit('alert', '확인' ,"상품 수량을 확인해주세요.");
                return;
            }
            if(this.wirter == ""){
                this.$eventBus.$emit('alert', '확인' ,"접수자를 입력해주세요.");
                return;
            }
            if(this.tel == ""){
                this.$eventBus.$emit('alert', '확인' ,"연락처를 입력해주세요.");
                return;
            }
            if(this.address.post == ""){
                this.$eventBus.$emit('alert', '확인' ,"우편번호를 검색해주세요.");
                return;
            }
            if(this.content == ""){
                this.$eventBus.$emit('alert', '확인' ,"AS 접수내용을 입력해주세요.");
                return;
            }

            this.$refs.imageupload.emitFileList();
            var param = {
                files: this.files,
                deletefilelist: this.deletefilelist,
                ascnt: this.product.select,
                subject: this.product.goodsname,
                content: this.content,
                goodsno: this.product.goodsno,
                optioncode: this.product.optioncode,
                writer: this.wirter,
                tel: this.tel,
                post: this.address.post,
                addr: this.address.addr,
                addrroad: this.address.addrroad,
                addrdetail: this.address.detail,
                sigungucode: this.address.sigungucode,
                buildingcode: this.address.buildingcode,
                roadnamecode: this.address.roadnamecode,
                orderidx: this.product.orderidx,
            }
            this.$util.debug("applyComplt file list >>>>>>>>>>>>>>>>>>>>>>>>>>>");
            this.$util.debug(JSON.stringify(this.files));
    
            this.$util.debug(JSON.stringify(param));
            if(this.$route.name == 'apply-as'){
                this.saveAsApply(param);
            }
            if(this.$route.name =='apply-as-revise'){
                param.asidx = this.product.asidx;
                this.saveRevise(param);
            }


        },
        /*********************
        * AS 신청 완료 통신
        *********************/
        saveAsApply(param) {
            this.$http.post('/mypage/as/applysave',param).then(result => {
                if(result.statusCode == 200){
                    if(result.data.msg == 'success'){
                        var paramMap = {
                            asidx : result.data.asidx,
                            ordno : result.data.ordno,
                            number : param.tel,
                        }
                        this.sendKakao(paramMap);
                        this.$root.$emit("bv::show::modal", "asApplySuccess");
                    }
                }
            })
        },
        /*********************
        * AS 수정 완료 통신
        *********************/
        saveRevise(param){
            this.$http.post('/mypage/as/applyrevise',param).then(result => {
                if(result.statusCode == 200){
                    if(result.data.msg == 'success'){
                        this.$eventBus.$emit('alert', '확인' ,"수정이 완료되었습니다.");
                        this.$router.push({ name: 'mypage-as-detail', params:{pid:this.$route.params.pid}});
                    }
                }
            })
        },
        /*********************
        * AS 신청 완료 시 알림톡
        *********************/
        sendKakao(param) {
            this.$http.post('/mypage/as/sendapply',param).then(result => {
            })
        },
        goListPage(){
            this.$router.push({ name: 'mypage-as-apply-list'});
            this.$root.$emit("bv::hide::modal", "asApplySuccess");
        },
        /*********************************
         * 첨부파일 변경시 처리
         *********************************/
        changeFile(files, deletelist) {
            this.files = files;
            this.deletefilelist = deletelist;
        },
        /**************************
         * 부모에서 전달받은 파일목록 매핑
         ****************************/
        bindFile(files){
            this.$util.debug("bindFile....");
            this.$util.debug(files);

            //저장된 파일리스트에 id값 부여
            for (let index = 0; index < files.length; index++) {
            files[index].id = 'files'+index;
            files[index].url = files[index].fullpath;
            }
            this.filelist = files;
        },
        /*********************
        * 다음 주소찾기 API 호출
        *********************/
         execDaumPostModal() {
            this.$eventBus.$emit('showModal', Post, 'postModal', null, (address) => {
                this.$util.debug(address);
                this.skey = Date.now();
                this.address.addrroad = this.$util.isBlank(address.roadAddress) ? address.autoRoadAddress: address.roadAddress;      //도로명주소
                this.address.addr = this.$util.isBlank(address.jibunAddress) ? address.autoJibunAddress : address.jibunAddress;         //구주소
                this.address.post = address.zonecode;             //우편번호
                this.address.sigungucode = address.sigunguCode;   //시군구코드
                this.address.buildingcode = address.buildingCode; //빌딩코드
                this.address.roadnamecode = address.roadnameCode; //도로명코드
            });
        },
        /*********************
        * 전화걸기 버튼 클릭 시 전화연결
        *********************/
        sendTel(){
            location.href = 'tel:' + encodeURIComponent(this.product.dealertel);
        },
        /*********************
        * 뒤로가기 클릭
        *********************/
        goBack(){
            this.$util.debug("goBack");
            this.$router.go(-1);
        },
        searchChangeASFunc(){
            this.content = this.content.split('  ').join(' ');
        },

    },
  };