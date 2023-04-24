import Address from '@views.mobile/nonemember/shop/popup/Address.vue';
import TossPayment from '@views.mobile/mypage/shop/claim/TossPayment.js'

export default {
    mixins :[TossPayment],
    beforeCreate() {
      let title = "취소신청";
      if(this.$route.name.indexOf('return') > -1) {
        title = '반품신청';
      } else if(this.$route.name.indexOf('exchange') > -1) {
        title = '교환신청';
      } 
      this.$store.commit("onSubHeaderOption", {
        title: title,
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
      });
    },
    data() {
      return {
        userno : 0,
        claimType : 'CLM001', // CLM001 : 취소 , CLM002 : 반품 , CLM003 : 교환
        claimName : '취소',
        ordstatus : '',
        list : [],        // 클레임상품
        dispitems : [{
          excgoodslist : []
        }],   // 클레임상품 노출용
        bflist : [],      // 이전상품
        aflist : [],      // 이후상품
        rslist: [],       // 클레임사유목록
        rstype : '',      // 클레임사유
        clmdetail : '',   // 클레임상세사유
        isExcShow : false, // 교환옵션보이기
        recAddr : {       // 회수지
          recname: null,
          rectel: null,
          recaddr: null,
          recaddrdetail: null,
          recaddrroad: null,
          recaddrdetailroad: null,
          recpost: null,
          isolatetype: null,
          recsigungucode: null,
          recbuildingcode: null,
          rcvreqtype: null,
          rcvreqdetail: null,
          rcvroadnamecode : null,
        },     
        excAddr : {     // 반품배송지
          excdlvname: null,
          excdlvtel: null,
          excdlvaddr: null,
          excdlvaddrdetail: null,
          excdlvaddrroad: null,
          excdlvaddrdetailroad: null,
          excpost: null,
          isolatetype: null,
          excsigungucode: null,
          excbuildingcode: null,
          excreqtype : null,
          excreqdetail : null,
          excroadnamecode : null
        },     
        saveCart : false, // 클레임상품카트저장
        reqlist : [],     // 배송요청사항
        paywaylist : [],  // 결제수단
        cardlist : [],    // 카드종류
        cardPlan : [],    // 할부개월
        payInfo : {       // 결제정보
          paywaytype : 'PWT001', // 결재수단
          cardCompany : '',
          cardInstallmentPlan : '0',
          useEscrow : false, // 에스크로사용여부
        },
        bforder : {},
        aforder : {},
        calinfo : {},
        
        banklist : [],

        refInfo : {          //환불정보
          refaccnumber : '', // 환불계좌
          refbank : '',
          refcusname : '',
          refbirthday : '',
          confirm : false,
        },
        // 첨부파일
        files : [{
          filetype: null,
          imgforiname: null,
          imgpath: null,
          reguserid: null,
          sort: null,
          imgtype: null,
          istrash: null,
          fullpath: null,
          host: null,
          imgfname: null,
          regdate: null,
          orgidx: null,
          idx: null,
          status: null
        }],
        deletefilelist : [],
        // 추가결제 유뮤
        isAddPayment: true,
      }
    },
    mounted() {
      if(this.$route.name.indexOf('return') > -1) {
        this.claimType = 'CLM002';
        this.claimName = '반품';
      } else if(this.$route.name.indexOf('exchange') > -1) {
        this.claimType = 'CLM003';
        this.claimName = '교환';
      } 

      if(this.$util.isEmpty(this.$route.query.items)) {
        this.$eventBus.$emit('alert', '알림', this.claimName +'상품이 없습니다.', ()=>{
            this.$router.go(-1);
        });
      } else {
        let claimList = this.$front.parseClaimStr(this.$route.query.items);
        //  claimList = [
        //    {orderidx : 159, ordgdidx : 299, clmcnt : 1},
        //    {orderidx : 159, ordgdidx : 300, clmcnt : 1},
        //    {orderidx : 159, ordgdidx : 301, clmcnt : 1},
        //  ]
        this.getClaimList(claimList);
      }
    },
    methods: {
      getClaimList(claimList) {
        const param = {
          clmtype : this.claimType,
          orderidx : claimList[0].orderidx,
          claim : claimList
        }

        this.$http.post('/nonemember/claim/apply', param).then(result => {
          if (result.statusCode == 200) {
            //주문자명을 환불계좌명으로 디폴트설정
            this.refInfo.refcusname = result.data.bforder.rcvname;

            //회수지,교환배송지 디폴트설정
            let addr = {
              title : result.data.bforder.rcvname,
              mobile : result.data.bforder.rcvtel1,
              addrroad : result.data.bforder.rcvaddrroad,
              addrdetailroad : result.data.bforder.rcvaddrdetailroad,
              addr : result.data.bforder.rcvaddr,
              addrdetail : result.data.bforder.rcvaddrdetail,
              post : result.data.bforder.rcvpost,
              //2022-08-29:yiy수정(시군구코드, 빌딩코드에 수령인명이 바인딩)
              sigungucode : result.data.bforder.rcvsigungucode,
              buildingcode : result.data.bforder.rcvbuildingcode,
              roadnamecode : result.data.bforder.rcvroadnamecode,
              isolatetype : result.data.bforder.isolatetype
            }
            if(this.claimType == 'CLM002') {
              this.setAddr('REC', addr);
            } else if(this.claimType == 'CLM003') {
              this.setAddr('REC', addr);
              this.setAddr('EXC', addr);
            }

            //할부
            this.cardPlan.push({value : '0', label : '일시불'});
            for(let i = 2 ; i <= 12 ; i++) {
                this.cardPlan.push({value : i + '', label : i+'개월'});
            }

            //클레임사유
            this.rslist = result.data.rslist;

            //결제수단
            for(let i = 0 ; i < result.data.paywaylist.length ; i++) {
              if(result.data.paywaylist[i].cmcode != "PWT002" && result.data.paywaylist[i].cmcode != "PWT009") {
                this.paywaylist.push(result.data.paywaylist[i]);
              }
            }
            //카드종류
            this.cardlist = result.data.cardlist;
            //배송요청사항
            this.reqlist = result.data.reqlist;
            //은행구분
            for(let i = 0 ; i < result.data.banklist.length ; i++) {
              if(result.data.banklist[i].value == 'TOSSBANK') continue;

              result.data.banklist[i].value = result.data.banklist[i].detail;
              this.banklist.push(result.data.banklist[i]);
              if(result.data.banklist[i].cmcode == 'NONGHYEOP') {
                this.banklist.push({
                  value : '농협중앙회',
                  label : 'NH농협중앙회',
                  detail : '농협',
                  note : '012'
                })  
              }
            }

            //이전주문
            this.bforder = result.data.bforder;
            //이후주문
            this.aforder = result.data.aforder;
            //클레임계산
            this.calinfo = result.data.calinfo;

            //이전상품
            this.bflist = result.data.bfitems;
            //이후상품
            this.aflist = result.data.afitems;
            //클레임상품
            this.list = result.data.items;
            //주문상태
            this.ordstatus = this.list[0].ordstatus;

            //클레임상품 설정
            let list = result.data.dispitems;
            for(let i = 0 ; i < list.length ; i++) {
              list[i].chk = true;
              //옵션명 설정
              let optionnames = list[i].optionname.split('|');
              list[i].opthtml = '';
              if(!this.$util.isBlank(list[i].optionname) && optionnames.length > 0) {
                optionnames[0] = '옵션 : ' + optionnames[0];
                for(let j = 0 ; j < optionnames.length ; j++) {
                    optionnames[j] = '<span>' + optionnames[j] + '</span>';
                } 
                list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
              }
              
              //클레임상품이 이전클레임 상품일경우(교환의 교환, 교환의 반품등) 클레임가능수량은 클레임수량
              if(this.list[0].clmidx != null && this.list[0].clmidx != 0) {
                list[i].origincnt = list[i].clmcnt;
              } 
              
              list[i].ordcnt = list[i].origincnt;

              //수량선택
              list[i].selectOption = [];
              // if(this.claimType == 'CLM003') {
              //   list[i].origincnt = 1
              // } 
              for(let j = 1 ; j <= list[i].origincnt ; j++) {
                list[i].selectOption.push({
                  value : j,
                  label : j + ''
                });
              }

              //교환상품
              list[i].excoption = '';
              list[i].excgoodslist = [];
              for(let y = 0 ; y < result.data.excgoodslist.length ; y++) {
                result.data.excgoodslist[y].value = result.data.excgoodslist[y].optioncode;
                result.data.excgoodslist[y].label = result.data.excgoodslist[y].optionname;
                list[i].excgoodslist.push(result.data.excgoodslist[y]);
              }
            }
            this.dispitems = list;
          }
        });
      },
      //취소,반품,교환 재계산
      calClaimAmt(item) {
        let items = [];
        for(let i = 0 ; i < this.dispitems.length ; i++) {
          if(this.dispitems[i].chk) {
            items.push(this.dispitems[i]);
          }
        }
        
        if(items.length == 0) {
          this.$nextTick(()=>{
            this.$eventBus.$emit('alert', '알림', this.claimName + '신청할 상품을 선택해주세요.');
            item.chk = true;
          });
          return;
        }

        const param = {
          clmtype : this.claimType,
          orderidx : this.list[0].orderidx,
          rstype : this.rstype,
          claim : items
        }

        this.$http.post('/nonemember/claim/apply', param).then(result => {
          if (result.statusCode == 200) {
            //계산금액
            this.calinfo = result.data.calinfo;
            //이후상품
            this.aflist = result.data.afitems;
            //클레임상품
            this.list = result.data.items;
            //주문상태
            this.ordstatus = this.list[0].ordstatus;
          }
        });
      },
      //배송지변경 팝업
      changeAddr(type){
        let param = {
          title : this.recAddr.recname,
          mobile : this.recAddr.rectel,
          addr : this.recAddr.recaddr,
          addrdetail : this.recAddr.recaddrdetail,
          addrroad : this.recAddr.recaddrroad,
          addrdetailroad : this.recAddr.recaddrdetailroad,
          post : this.recAddr.recpost,
          type : 'change'
        }
        if(type != 'REC') {
          param = {
            title : this.excAddr.excdlvname,
            mobile : this.excAddr.excdlvtel,
            addr : this.excAddr.excdlvaddr,
            addrdetail : this.excAddr.excdlvaddrdetail,
            addrroad : this.excAddr.excdlvaddrroad,
            addrdetailroad : this.excAddr.excdlvaddrdetailroad,
            post : this.excAddr.excdlvpost,
            type : 'change'
          }
        }
        this.$eventBus.$emit('showModal', Address, 'addressModal', param, (address)=>{
          this.setAddr(type, address);
        });
      },
      //회수지 교환배송지 세팅
      setAddr(type, addr) {
        if(type == 'REC') {
          //회수지
          this.recAddr.recname = addr.title;
          this.recAddr.rectel = addr.mobile;
          this.recAddr.recaddr = addr.addr;
          this.recAddr.recaddrdetail = addr.addrdetail;
          this.recAddr.recaddrroad = addr.addrroad;
          this.recAddr.recaddrdetailroad = addr.addrdetailroad;
          this.recAddr.recpost = addr.post;
          this.recAddr.isolatetype = addr.isolatetype;
          this.recAddr.recsigungucode = addr.sigungucode;
          this.recAddr.recbuildingcode = addr.buildingcode;
          this.recAddr.recroadnamecode = addr.roadnamecode;
        } else {
          //교환배송지
          this.excAddr.excdlvname = addr.title;
          this.excAddr.excdlvtel = addr.mobile;
          this.excAddr.excdlvaddr = addr.addr;
          this.excAddr.excdlvaddrdetail = addr.addrdetail;
          this.excAddr.excdlvaddrroad = addr.addrroad;
          this.excAddr.excdlvaddrdetailroad = addr.addrdetailroad;
          this.excAddr.excpost = addr.post;
          this.excAddr.isolatetype = addr.isolatetype;
          this.excAddr.isdefault = addr.isdefault;
          this.excAddr.excsigungucode = addr.sigungucode;
          this.excAddr.excbuildingcode = addr.buildingcode;
          this.excAddr.excroadnamecode = addr.roadnamecode;
        }
      },
      //결제수단수정
      changePayWayType() {
        this.payInfo.cardCompany = '';
      },
      //환불계좌확인
      accountConfirm() {
        if(this.$util.isBlank(this.refInfo.refaccnumber) 
        || this.$util.isBlank(this.refInfo.refbank) 
        || this.$util.isBlank(this.refInfo.refcusname) 
        || this.$util.isBlank(this.refInfo.refbirthday)){
          this.$eventBus.$emit("alert", "알림", "환불정보를 입력해주세요.");
          return;
        }
        let bank = '';
        for(let i = 0 ; i < this.banklist.length ; i++) {
          if(this.refInfo.refbank == this.banklist[i].value) {
            bank = this.banklist[i].note;
            break;
          }
        }
        const param = {
          accounttype : '1',
          bank : bank,
          birthday : this.refInfo.refbirthday,
          account : this.refInfo.refaccnumber,
          name : this.refInfo.refcusname
        }
        this.$http.post('/common/bank/account', param).then(result => {
          if(result.statusCode == 200) {
            if(result.data.exist) {
              this.refInfo.confirm = true;
              this.$eventBus.$emit('alert', '알림', '환불계좌가 확인되었습니다.');
            } else {
              this.$eventBus.$emit('alert', '알림', '계좌인증이 실패하였습니다. 확인 후 다시시도해주세요.');
              //this.$eventBus.$emit('alert', '알림', result.data.msg);
            }
          }
        });
      },
      claimApply() {
        if(this.claimType == 'CLM003' && this.$util.isEmpty(this.dispitems[0].excoption)) {
          this.$eventBus.$emit("alert", "알림", "교환옵션을 선택해주세요.");
            return;
        }

        if(this.$util.isBlank(this.rstype)) {
          this.$eventBus.$emit("alert", "알림", this.claimName + "사유를 선택해주세요.");
          return;
        } 
        
        if(this.ordstatus != 'ODS001' && this.bforder.paywaytype == 'PWT002') {
          if(this.$util.isBlank(this.refInfo.refaccnumber) || this.$util.isBlank(this.refInfo.refbank) || this.$util.isBlank(this.refInfo.refcusname)){
            this.$eventBus.$emit("alert", "알림", "환불정보를 입력해주세요.");
            return;
          }
          if(!this.refInfo.confirm) {
            this.$eventBus.$emit("alert", "알림", "계좌확인 후 신청이가능합니다.");
            return;
          }
          if(this.refInfo.refbank == '농협중앙회'){
            this.refInfo.refbank = '농협';
          }
        }
        
        let msg = '주문을 취소하시겠습니까?';
        if(this.claimType != 'CLM001') {
          msg = this.claimName + '을 신청하겠습니까?';
        }
        this.$eventBus.$emit('confirm', this.claimName + '신청', msg, ()=>{
          this.saveClaimApply();
        });
      },
      saveClaimApply() {
        // gtmDataLayer 데이터 작업.
        let ecommerce = new Object();
        let items =  new Array();
        let transaction_id = new Object();
        let shipping = new Object();
        let value = new Object();
        let first_order = new Object();
        let numcnt = 0;
        for (let i = 0; i < this.list.length; i++) {
            let item1 = new Object();
            item1.item_id = this.list[i].goodscode;
            item1.id = this.list[i].goodscode;
            item1.item_name = this.list[i].goodsname;
            item1.price = this.list[i].realgoodsamt;
            item1.quantity = this.list[i].clmcnt;
            numcnt += this.list[i].clmcnt;
            item1.item_brand = this.list[i].brandname;
            item1.item_category = this.list[i].fullcategoryname;
            item1.item_variant = this.list[i].fulloptionnm;
            items.push(item1);
        }
        ecommerce.transaction_id = this.calinfo.ordno;
        ecommerce.value = this.calinfo.rtnamt;
        ecommerce.shipping = this.calinfo.totrtndelivamt;
        ecommerce.first_order = this.calinfo.isfrstorder;
        ecommerce.items = items;
        ecommerce.num_items = this.list.length;
        ecommerce.num_quantity = numcnt;
        this.$front.gtmDataLayer('refund', ecommerce);

        if(this.ordstatus == 'ODS001'){
          this.$http.post('/nonemember/order/cancel', this.bforder).then(result => {
            if (result.statusCode == 200) {
              this.$router.replace({name:'nonemember-order'});
            }
          });
          return;
        }

        this.$refs.imageupload.emitFileList();
        
        let param = {
          clmtype : this.claimType,   //클레임구분
          clmreqtype : 'CRQ001',      //클레임신청구분(고객)
          clmprctype : 'CPR002',      //클레임처리구분
          clmdetail : this.clmdetail, //CLMDETAIL
          claimitems : this.list,     //클레임상품
          items : this.aflist,        //계산후주문상품
          files: this.files,          //첨부파일
          savecart : this.saveCart ? 'T' : 'F', //클레임상품 장바구니담기
        }
        //크레임별 사유 및 상태
        if(param.clmtype == 'CLM001') {
          param.cnctype = this.rstype;
          param.cncstatus = this.calinfo.addrpaytotprice > 0 ? 'CNS002' : 'CNS005';
          for(let i = 0 ; i < param.claimitems.length ; i++) {
            param.claimitems[i].cncstatus = param.cncstatus;
          }
        } else if(param.clmtype == 'CLM002') {
          param.rtntype = this.rstype;
          param.rtnstatus = this.calinfo.addrpaytotprice > 0 ? 'RTS002' : 'RTS001';
          param.clmidx = this.dispitems[0].clmidx;  //이전클레임IDX(반품처리용)
        } else {
          param.exctype = this.rstype;
          param.excstatus = this.calinfo.addrpaytotprice > 0 ? 'EXS002' : 'EXS001';
          param.excoption = this.dispitems[0].excoption; //교환옵션설정
          param.clmidx = this.dispitems[0].clmidx;  //이전클레임IDX(교환처리용)
        }

        param = Object.assign({}, param, this.calinfo); //클레임금액
        param = Object.assign({}, param, this.recAddr); //회수지주소
        param = Object.assign({}, param, this.excAddr); //교환배송지주소

        if(this.ordstatus != 'ODS001' && this.bforder.paywaytype == 'PWT002'){
          param = Object.assign({}, param, this.refInfo); //환불계좌
        }
        
        this.$http.post('/nonemember/claim/save', param).then(result => {
          if (result.statusCode == 200) {
            if(this.calinfo.addrpaytotprice > 0) {
              this.paymentProc(result.data.clmno);
            } else {
              param.clmno = result.data.clmno;
              this.goDetail(param);
            }
          }
        });
      },
      /*********************************
       * 첨부파일 변경시 처리
       *********************************/
      changeFile(files, deletelist) {
        this.$util.debug("changeFile....");
        this.$util.debug(JSON.stringify(files));
        this.$util.debug(JSON.stringify(deletelist));
        this.files = files;
        this.deletefilelist = deletelist;
      },
      paymentProc(clmno) {
        const param = {
          clmno : clmno,
          clmtype : this.claimType,
          amount : this.calinfo.addrpaytotprice,
          ordname : this.bforder.ordname,
          goodsname : "[추가결제]" + this.list[0].goodsname,
        }

        this.payment(param);
      },
      showOption() {
        if(this.dispitems[0].excgoodslist.length > 0) {
          this.isExcShow = true;
        } else {
          this.$eventBus.$emit('alert', '알림', '교환가능한 옵션이 없습니다.');
        }
      },
      // 사은품 체크클릭 이벤트
      changeGiftChk(gift, goods) {
        let cnt = 0;
        for(let i = 0 ; i < gift.detail.length ; i++) {
            for(let j = 0 ; j < gift.detail[i].length ; j++) {
                if(gift.detail[i][j].chk) {
                    cnt++;
                }
            }
        }
        
        if(cnt > gift.giftselcnt) {
            this.$nextTick(()=>{
                goods.chk = false;
                alert("최대 " + gift.giftselcnt + "만 선택가능합니다.");
            });
        }
      },
    },
};