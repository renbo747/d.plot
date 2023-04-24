<template>
    <b-modal
        id="id-find"
        modal-class="dp-modal page-layer id-find"
        scrollable
        :hide-footer="true"
        >
        <!-- Id-find Modal Header -->
        <template #modal-header="{cancel}">
            <h5 class="modal-title">아이디 찾기</h5>
            <i class="modal-close" @click="cancel">
            <img
                src="@assets.mobile/img/icon/icon-close-black-20px.svg"
                alt="닫기"
            />
            </i>
        </template>

        <!-- Id-find Modal Body -->
        <div class="page-layer__body">
            <div class="dp-panel pt-0">
            <p class="id-find__description">
                아이디가 기억나지 않으시면 다음과 같은 <br />
                방법으로 아이디를 찾아주세요.
            </p>
            <p class="id-find__title">본인인증으로 찾기</p>
            <b-button
                class="dp-btn text-white" variant="gray-800"
                squared @click="kmcCertReq()"
            >
                <span>본인인증하기</span>
            </b-button>
            </div>
            <hr class="dp-hr justify-margin" />
            <div class="dp-panel">
            <p class="id-find__title mb-3">회원 정보로 찾기</p>
            <form>
                <div class="form-group">
                <base-input
                    label="이름"
                    placeholder="이름을 입력해주세요."
                    :is-label="true"
                    v-model="name"
                >
                </base-input>
                </div>
                <div class="id-find__email-phone">
                <p class="id-find__label">이메일 또는 휴대폰번호</p>
                <div class="email-phone-find">
                    <base-select
                    class="base-select"
                    placeholder="선택해주세요."
                    :isPlaceholder="false"
                    v-model="selectType"
                    :options="selectOptions"
                    @input="changeType"
                    />
                    <base-input
                    :type="inputType"
                    class="base-input"
                    v-model="emailhp"
                    :placeholder="selectType == 'MOBILE' ? '- 없이 입력해주세요.' : '이메일을 입력해주세요.'"
                    >
                    </base-input>
                </div>
                </div>
                <b-button
                class="dp-btn text-white"
                variant="gray-800"
                squared
                @click="findId()"
                >
                <span>회원 정보 찾기</span>
                </b-button>
                <!-- <b-button
                class="dp-btn text-white"
                variant="gray-800"
                squared
                >
                <span>SNS찾기 결과 (삭제 예정)</span>
                </b-button>
                <b-button
                class="dp-btn text-white"
                variant="gray-800"
                squared
                >
                <span>회원조회 실패 (삭제 예정)</span>
                </b-button> -->
            </form>
            </div>
            <KmcCertReq ref="kmcCertReq"></KmcCertReq>
        </div>
        </b-modal>
</template>

<script src="./IdFind.js">
</script>
