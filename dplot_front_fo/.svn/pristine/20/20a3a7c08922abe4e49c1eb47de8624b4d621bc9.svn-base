<template>
<!-- store-inquiry Modal -->
          <b-modal
            id="StoreInquiryModal"
            modal-class="dp-modal page-layer store-inquiry-modal"
            scrollable
            :hide-footer="true"
          >
            <!-- store-inquiry Modal Header -->
            <template #modal-header="{ cancel }">
              <h5 class="modal-title">입점/제휴문의</h5>
              <i class="modal-close" @click="cancel()">
                <img
                  src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                  alt="닫기"
                />
              </i>
            </template>

            <!-- store-inquiry Modal Body -->
            <div class="store-inquiry__body">
              <div>
                <header class="dp-panel pt-0">
                  <h1 class="dp-p text-black font-weight-400 mb-0">
                    D.PLOT에 입점/제휴를 위해 아래 정보를 입력해주세요. 입력하신
                    정보는 내부 검토 후 별도의 안내를 드립니다.
                  </h1>
                </header>
                <!--  form  -->
                <div class="store-inquiry-form">
                  <form>
                    <ul class="list-style-none">
                      <li v-for="(list, index) in inputListData" :key="index">
                        <div>
                          <div class="form-group">
                            <p>{{ list.label }}</p>
                            <base-input
                              :placeholder="list.placeholder"
                              v-model="list.value"
                              :ref="list.ref"
                            />
                          </div>
                        </div>
                      </li>
                      <li>
                        <div>
                          <div class="dp-panel pt-0">
                            <p class=" dp-p store-inquiry-form-p">담당자 연락처</p>
                            <base-input
                              placeholder="전화번호를 입력해주세요"
                              v-model="mobileNo"
                              :type="'tel'"
                              :max-length=13
                              ref="tel"
                              :keyup="getPhoneMask(mobileNo)"
                            />
                          </div>
                        </div>
                      </li>                      
                    </ul>
                    <div>
                      <div>
                        <p>기타 문의사항</p>
                        <base-textarea
                          v-model="textareaData"
                          placeholder="내용을 입력해주세요. (최대 500자)"
                          :maxCount=500
                        ></base-textarea>
                      </div>
                    </div>
                  </form>
                </div>
                <!-- 사업자정보제공 안내  -->
                <div class="store-inquiry-info dp-panel">
                  <div class="store-inquiry-info-header mb-20">
                    <h2 class="dp-title02 text-black font-weight-700 mb-0">
                      사업자정보제공 안내
                    </h2>
                  </div>
                  <div>
                    <ul class="store-inquiry-info__list list-style-none mb-20">
                      <li>
                        <div class="store-inquiry-info__list__item">
                          <div class="store-inquiry-info__list__title">
                            <h3 class="mb-0 text-black font-weight-500 dp-p">
                              수집 및 이용목적
                            </h3>
                          </div>
                          <div class="store-inquiry-info__list__text">
                            <p class="info__list__text__p mb-0">
                              입점/제휴 상담신청을 위한 담당자 연락처 정보 확보
                            </p>
                          </div>
                        </div>
                      </li>
                      <li>
                        <div class="store-inquiry-info__list__item">
                          <div class="store-inquiry-info__list__title">
                            <h3 class="mb-0 text-black font-weight-500 dp-p">
                              제공받는자
                            </h3>
                          </div>
                          <div class="store-inquiry-info__list__text">
                            <p class="info__list__text__p mb-0">
                              (주)다다엠앤씨
                            </p>
                          </div>
                        </div>
                      </li>
                      <li>
                        <div class="store-inquiry-info__list__item">
                          <div class="store-inquiry-info__list__title">
                            <h3 class="mb-0 text-black font-weight-500 dp-p">
                              수집 및 이용목적
                            </h3>
                          </div>
                          <div class="store-inquiry-info__list__text">
                            <p class="info__list__text__p mb-0">
                              사업자등록번호, 협력업체 담당자의 인적사항 (이름,
                              전화번호, 휴대폰번호, 이메일주소) 등
                            </p>
                          </div>
                        </div>
                      </li>
                      <li>
                        <div class="store-inquiry-info__list__item">
                          <div class="store-inquiry-info__list__title">
                            <h3 class="mb-0 text-black font-weight-500 dp-p">
                              보유 및 이용기간
                            </h3>
                          </div>
                          <div class="store-inquiry-info__list__text">
                            <p
                              class="mb-0 dp-p-sm text-gray-700 font-weight-400"
                            >
                              원칙적으로 개인정보의 수집목적 또는 제공받은
                              목적이 달성되면 지체없이 파기합니다. 다만,
                              관계법률에 의해 보존할 필요가 있는 경우에는
                              일정기간 보존합니다. 이 경우, 당사는 보관하는
                              정보를 그 보관 목적으로만 이용합니다.
                            </p>
                          </div>
                        </div>
                      </li>
                    </ul>
                    <hr class="dp-hr h1" />
                    <div class="dp-panel">
                      <div class="form-group">
                        <base-checkbox
                          label="위의 안내 사항을 모두 확인하였으며, 이에 동의합니다."
                          v-model="StoreChkBox.checked"
                          :id="StoreChkBox.id"
                          name="StoreChkBox"
                          :is-checked="StoreChkBox.checked"
                          @change="sendChk"
                        />
                      </div>
                      <div class="store-inquiry-btn">
                        <b-button
                          class="dp-btn text-white"
                          variant="gray-800"
                          squared
                        >
                          <span @click="registerStore()">등록하기</span>
                        </b-button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </b-modal>
          <!-- // store-inquiry Modal -->    
</template>

<script>
export default {
    beforeCreate() {
      this.$store.commit("onSubHeaderOption", {
        title: "고객센터",
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
      });
    },
    mounted() {
    },      
    data() {
      return {
        mobileNo: "",
        textareaData: "",
        inputListData: [
          {
            id: "input01",
            label: "업체명",
            placeholder: "업체명을 입력해주세요",
            ref:"busname",
            value: "",
          },
          {
            id: "input02",
            label: "사업자등록번호",
            placeholder: "사업자등록번호를 입력해주세요",
            ref:"busnum",
            value: "",
          },
          {
            id: "input03",
            label: "홈페이지 URL",
            placeholder: "URL을 입력해주세요",
            ref:"url",
            value: "",
          },
          {
            id: "input04",
            label: "제안상품명(주요상품)",
            placeholder: "내용을 입력해주세요",
            ref:"goods",
            value: "",
          },
          {
            id: "input05",
            label: "브랜드 자사몰 / 판매처 URL",
            placeholder: "URL을 입력해주세요",
            ref:"brand",
            value: "",
          },
          {
            id: "input06",
            label: "담당자명/직급",
            placeholder: "담당자정보를 입력해주세요",
            ref:"name",
            value: "",
          },
          {
            id: "input07",
            label: "담당자 메일 주소",
            placeholder: "이메일주소를 입력해주세요",
            ref:"email",
            value: "",
          },
        ],
        StoreChkBox: {
          id: "sendChk",
          checked: false,
        },
      };
    },
    methods: {
      sendChk(){
        this.$util.debug("click");         
      },
      registerStore() {
        if(this.inputListData[0].value == ""){
          this.$eventBus.$emit('alert', '알림', "업체명을 입력해주세요.");
          this.$refs.busname[0].$refs.binput.focus();
          return;
        }
        if(this.inputListData[1].value == ""){
          this.$eventBus.$emit('alert', '알림', "사업자번호를 입력해주세요.");
          this.$refs.busnum[0].$refs.binput.focus();
          return;
        }
        // if(this.inputListData[2].value == ""){
        //   this.inputListData[2].value = "-";
        // }
        if(this.inputListData[3].value == ""){
          this.$eventBus.$emit('alert', '알림', "제안상품 정보를 입력해주세요.");
          this.$refs.goods[0].$refs.binput.focus();
          return;
        }
        // if(this.inputListData[4].value == ""){
        //   this.inputListData[4].value = "-";
        // }
        if(this.inputListData[5].value == ""){
          this.$eventBus.$emit('alert', '알림', "담당자명/직급을 입력해주세요.");
          this.$refs.name[0].$refs.binput.focus();
          return;
        }
        if(this.inputListData[6].value == ""){
          this.$eventBus.$emit('alert', '알림', "담당자 메일주소를 입력해주세요.");
          this.$refs.email[0].$refs.binput.focus();
          return;
        }
        if(this.mobileNo == ""){
          this.$eventBus.$emit('alert', '알림', "담당자 연락처를 입력해주세요.");
           this.$refs.tel.$refs.binput.focus();
          return;
        }
        // if(this.textareaData == ""){
        //   this.textareaData = "-";
        // }
        if(!this.StoreChkBox.checked){
          this.$eventBus.$emit('alert', '알림', "사업자정보 수집 및 이용에 동의해주세요.");
          return;
        }

        //입력값없을시 -을 한이유는 모르겠지만 되어있으니 null 대신 '-'으로 저장....
        //메시지 때문인가?.......
        var param = {
          companyname: this.inputListData[0].value,
          businessnumber: this.inputListData[1].value,
          homepageurl: this.$util.isNull(this.inputListData[2].value)?"-":this.inputListData[2].value,
          mainproduct: this.inputListData[3].value,
          brandurl: this.$util.isNull(this.inputListData[4].value)?"-":this.inputListData[4].value,
          manager: this.inputListData[5].value,
          manageremail: this.inputListData[6].value,
          managerphone: this.mobileNo,
          etc: this.$util.isNull(this.textareaData)?"-":this.textareaData,
          sendchk: this.StoreChkBox.checked,
        }

        this.$util.debug(param);         
        this.$http.post('/cscenter/store',param).then(result => {
          if(result.statusCode == 200){
            if(result.data.httpcode == "401"){
                this.$eventBus.$emit('alert', '알림', result.data.httpmessage);
            }else{
                this.$eventBus.$emit('alert', '알림', "입점/제휴 문의가 등록되었습니다.", () => {
                  this.$bvModal.hide('StoreInquiryModal');
                  this.$router.push({name:'shop', params:{init:true}});    
                });
            }
          }
        })
  

      },
      getPhoneMask(val) {
        let res = this.$util.getMask(val);
        this.mobileNo = res;
      },      
    },
  };
</script>
