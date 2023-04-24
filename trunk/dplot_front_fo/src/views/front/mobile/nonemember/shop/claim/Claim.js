/**
 * 클레임 목록 공통
 */
export default {
    data() {
        return {
            claimType : 'CLM001', // CLM001 : 취소 , CLM002 : 반품 , CLM003 : 교환
            claimName : '',
            claimList: [],
        }
    },
    mounted() {
        if(this.$route.name.indexOf('cancel') > -1) {
            this.claimType = 'CLM001';
            this.claimName = '취소';
        } else if(this.$route.name.indexOf('return') > -1) {
            this.claimType = 'CLM002';
            this.claimName = '반품';
        } else if(this.$route.name.indexOf('exchange') > -1) {
            this.claimType = 'CLM003';
            this.claimName = '교환';
        } 
    },
    methods: {
        beforeClaimList(list){
            for(let i = 0 ; i < list.length; i++) {
                //Common.js 호출(클레임상품 화면구조변경)
                list[i] = this.setClaimItem(list[i]);
                
                let idx = this.$front.containIdx(this.claimList, 'clmidx', list[i].clmidx);
                if(idx == -1) {
                    this.claimList.push({
                        clmidx : list[i].clmidx,
                        clmno : list[i].clmno,
                        clmreqdate : list[i].clmreqdate,
                        clmtype : list[i].clmtype,
                        ordno : list[i].ordno,
                        items : []
                    })
                }
            }
    
            for(let i = 0 ; i < this.claimList.length ; i++) {
                for(let j = 0 ; j < list.length ; j++) {
                    if(this.claimList[i].clmidx == list[j].clmidx) {
                        this.claimList[i].items.push(list[j]);
                    }
                }
            }
        },
        goToDetail(claim) {
            if(claim.clmtype == 'CLM001') {
                if(this.$util.isBlank(claim.clmno)) {
                    this.$router.push({name:'nonemember-order-detail', params : {ordno : claim.ordno}});
                } else {
                    this.$router.push({name:'nonemember-claim-cancel-detail', params : {clmno : claim.clmno}});
                }
            } else if(claim.clmtype == 'CLM002') {
                this.$router.push({name:'nonemember-claim-return-detail', params : {clmno : claim.clmno}});
            } else {
                this.$router.push({name:'nonemember-claim-exchange-detail', params : {clmno : claim.clmno}});
            }
        },
    },
}