export default {
    beforeCreate() {
            this.$store.commit("onSubHeaderOption", {
            title: "비회원 주문조회",
            searchIcon: false,
            cartIcon: false,
            homeIcon: false,
        });
    },
    data() {
        return {
            ordname: '',
            ordno: '',
        }
    },
    methods: {
        searchOrder() {
            if(this.$util.isBlank(this.ordname)) {
                this.$eventBus.$emit('alert','알림','주문자명을 입력해주세요.');
                return;
            } else if(this.$util.isBlank(this.ordno)) {
                this.$eventBus.$emit('alert','알림','주문번호를 입력해주세요.');
                return;
            }
            
            const param = {
                ordname : this.ordname,
                ordno : this.ordno,
                mode : 'guest'
            }
            this.$http.post('/member/login_act', param).then(result => {
                if (result.statusCode == 200) {
                    this.$router.push('/nonemember');
                } else {
                    this.$eventBus.$emit('alert', '알림', result.message);
                }
            });
        }
    }
};