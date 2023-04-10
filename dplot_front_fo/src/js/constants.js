export default {
    MEMBER_USER: 'MEMBER_USER',
    ADMIN_USER: 'ADMIN_USER',
    PARTNER_USER: 'PARTNER_USER',
    MEMBER: {
        MUAPPCHTYPE_MAC_PC: 'MAC001',    //PC
        MUAPPCHTYPE_MAC_MO: 'MAC002',    //모바일
        MUAPPCHTYPE_MAC_APP: 'MAC003',   //APP
        /* 시리얼프로모션 혜택 구분 */
        RECOMTYPE_RCT_SAVE: 'RCT001',     // 적립금
        RECOMTYPE_RCT_EPOINT: 'RCT002',   // D포인트
        RECOMTYPE_RCT_COUPON: 'RCT003',   // 쿠폰

        /* 시리얼프로모션 혜택 구분 */
        SRPTYPE_SRT_SAVE: 'SRT001',     // 적립금
        SRPTYPE_SRT_EPOINT: 'SRT002',   // D포인트
        SRPTYPE_SRT_COUPON: 'SRT003',   // 쿠폰

        /*적립금지급구분*/
        RESPAYTYPE_RPT_CONFIRM: 'RPT001',        //구매확정
        RESPAYTYPE_RPT_REVIEW_TXT: 'RPT002',     //리뷰작성(텍스트)
        RESPAYTYPE_RPT_REVIEW_PHOTO: 'RPT003',   //리뷰작성(포토)
        RESPAYTYPE_RPT_REVIEW_MOV: 'RPT004',     //리뷰작성(동영상) 
        RESPAYTYPE_RPT_BIRTH: 'RPT005',          //생일
        RESPAYTYPE_RPT_FST_CONFIRM: 'RPT006',    //첫구매확정
        RESPAYTYPE_RPT_CHECK: 'RPT007',          //출석체크이벤트
        RESPAYTYPE_RPT_PROMOTION: 'RPT008',      //프로모션
        RESPAYTYPE_RPT_REWARD_NOMINEE: 'RPT009', //추천리워드(피추천인)
        RESPAYTYPE_RPT_SERIAL: 'RPT010',         //시리얼프로모션
        RESPAYTYPE_RPT_MANUAL: 'RPT011',         //수동지급
        RESPAYTYPE_RPT_CANCEL: 'RPT012',         //취소적립
        RESPAYTYPE_RPT_REWARD_SIGNUP: 'RPT013',  //추천리워드(회원가입)
        RESPAYTYPE_RPT_REWARD_CONFIRM: 'RPT014', //추천리워드(구매확정)

        /*D포인트지급구분*/
        EPOPPAYTYPE_RPT_CONFIRM: 'EPT001',        //구매확정
        EPOPPAYTYPE_RPT_REVIEW_TXT: 'EPT002',     //리뷰작성(텍스트)
        EPOPPAYTYPE_RPT_REVIEW_PHOTO: 'EPT003',   //리뷰작성(포토)
        EPOPPAYTYPE_RPT_REVIEW_MOV: 'EPT004',     //리뷰작성(동영상) 
        EPOPPAYTYPE_RPT_BIRTH: 'EPT005',          //생일
        EPOPPAYTYPE_RPT_FST_CONFIRM: 'EPT006',    //첫구매확정
        EPOPPAYTYPE_RPT_CHECK: 'EPT007',          //출석체크이벤트
        EPOPPAYTYPE_RPT_PROMOTION: 'EPT008',      //프로모션
        EPOPPAYTYPE_RPT_REWARD_NOMINEE: 'EPT009', //추천리워드(피추천인)
        EPOPPAYTYPE_RPT_SERIAL: 'EPT010',         //시리얼프로모션
        EPOPPAYTYPE_RPT_MANUAL: 'EPT011',         //수동지급
        EPOPPAYTYPE_RPT_CANCEL: 'EPT012',         //취소적립
        EPOPPAYTYPE_RPT_REWARD_SIGNUP: 'EPT013',  //추천리워드(회원가입)
        EPOPPAYTYPE_RPT_REWARD_CONFIRM: 'EPT014', //추천리워드(구매확정)
    },
    ADMIN: {
        /* USER TYPE */
        USER_TYPE_ADMIN: 'URT001',  // 관리자
        USER_TYPE_PARTNER: 'URT003',  // 파트너사
        /* 배송비종류 */
        DELIV_FARE_DCT001: 'DCT001',    // 무료
        DELIV_FARE_DCT002: 'DCT002',    // 유료
        DELIV_FARE_DCT003: 'DCT003',    // 조건부무료
        /* 배송방법 */
        DELIV_TYPE_DLT001: 'DLT001',    // 택배배송
        /* 배송일정 */
        DELIV_SCH_TYPE_DSC001: 'DSC001',    // 당일배송
        DELIV_SCH_TYPE_DSC002: 'DSC002',    // 익일배송
        DELIV_SCH_TYPE_DSC003: 'DSC003',    // 주문제작
        DELIV_SCH_TYPE_DSC004: 'DSC004',    // (가구)설치/화물배송
        DELIV_SCH_TYPE_DSC005: 'DSC005',    // (가전)설치/화물배송
        /* 상품판매상태 */
        GOODS_SELL_TYPE_GST001: 'GST001',   // 판매대기
        GOODS_SELL_TYPE_GST002: 'GST002',   // 판매중
        GOODS_SELL_TYPE_GST003: 'GST003',   // 판매중지
        GOODS_SELL_TYPE_GST004: 'GST004',   // 자동품절
        GOODS_SELL_TYPE_GST005: 'GST005',   // 수기품절
        GOODS_SELL_TYPE_GST006: 'GST006',   // 영구종료
        /* 상품구분상태 */
        GOODS_DIV_TYPE_GDT001: 'GDT001',    // 새상품
        /* KC대상구분 */
        KC_DIV_TYPE_KDT001: 'KDT001',   // 미대상
        KC_DIV_TYPE_KDT002: 'KDT002',   // 대상
        /* 이미지구분 */
        IMG_TYPE_GOODS_IMG_PC_B: 'IGT001',    // 상품_PC이미지_대
        IMG_TYPE_GOODS_IMG_MO_B: 'IGT004',    // 상품_Mobile이미지_대
        IMG_TYPE_GOODS_IMG_ADD_B1: 'IGT032',  // 상품_추가이미지_대1
        IMG_TYPE_GOODS_IMG_ADD_B2: 'IGT035',  // 상품_추가이미지_대2
        IMG_TYPE_GOODS_IMG_ADD_B3: 'IGT038',  // 상품_추가이미지_대3
        IMG_TYPE_GOODS_IMG_ADD_B4: 'IGT041',  // 상품_추가이미지_대4
        IMG_TYPE_GOODS_IMG_ADD_B5: 'IGT044',  // 상품_추가이미지_대5
        IMG_TYPE_GOODS_IMG_ADD_B6: 'IGT047',  // 상품_추가이미지_대6
        IMG_TYPE_GOODS_IMG_ADD_B7: 'IGT050',  // 상품_추가이미지_대7
        IMG_TYPE_GOODS_IMG_ADD_B8: 'IGT053',  // 상품_추가이미지_대8
        IMG_TYPE_GOODS_IMG_ADD_B9: 'IGT056',  // 상품_추가이미지_대9
        IMG_TYPE_GOODS_IMG_ADD_B10: 'IGT059', // 상품_추가이미지_대10
        IMG_TYPE_GOODS_KC_CERT: 'IGT069',     // 상품KC인증서
        /* 상품승인상태 */
        GOODS_STATUS_TEMP: 'GAT001',          // 임시저장
        GOODS_STATUS_REQ: 'GAT002',           // 승인요청
        GOODS_STATUS_REJECT: 'GAT003',        // 반려
        GOODS_STATUS_APPROVAL: 'GAT004',      // 상품승인완료
        /* 프로모션구분 */
        PROMO_DIV_DISCOUNT: 'PDT001',   // 할인
        PROMO_DIV_GIFT: 'PDT002',       // 사은품
        PROMO_DIV_RESERVE: 'PDT003',    // 적립금/D포인트
        /* 대상상품범위 */
        GOODS_RANGE_ALL: 'GGT001',      // 전체상품
        GOODS_RANGE_INCLUDE: 'GGT002',  // 특정상품 추가
        GOODS_RANGE_EXCEPT:'GGT003',    // 특정상품 제외
        /* 사은품지급조건 */
        GIFT_TERM_PURCH: 'GFT001',      // 특정상품구매시지급
        GIFT_TERM_PRICE_MORE: 'GFT002', // 특정금액이상구매시지급
        GIFT_TERM_PRICE_RANGE:'GFT003', // 특정금액구간구매시지급
        GIFT_TERM_FRST_PURCH: 'GFT004', // 첫구매시지급
        /* 프로모션상태 */
        PROMO_ST_BEFORE: 'PST001',      // 진행전
        PROMO_ST_PROCEEDING: 'PST002',  // 진행중
        PROMO_ST_END: 'PST003',         // 종료
        /* 시리얼프로모션 혜택 구분 */
        SRPTYPE_SRT_SAVE: 'SRT001',     // 적립금
        SRPTYPE_SRT_EPOINT: 'SRT002',   // D포인트
        SRPTYPE_SRT_COUPON: 'SRT003',   // 쿠폰
        /* 시리얼프로모션 생성 타입 */
        SRCTYPE_SRC_AUTO: 'SRC001',     // 자동
        SRCTYPE_SRC_DIR: 'SRC002',      // 직접
        SRCTYPE_SRC_EXCEL: 'SRC003',    // 엑셀업로드
        /* 회원상태 */
        CM_STATE_REAL: 'MST001',        // 정상
        /* 적립금사용구분 */
        RES_USE_EXTINCT: 'RUT002',      // 소멸(유효기간만료)
        /* 쿠폰발급상태 */
        CPN_ISSUE_ST_BEFORE: 'CIS001',   // 발급전
        CPN_ISSUE_ST_ISSUE: 'CIS002',   // 발급중
        CPN_ISSUE_ST_STOP: 'CIS003',    // 발급중지
        /* 쿠폰사용기간종류 */
        CPN_USE_PERIOD: 'CUT001',       // 사용기간
        CPN_USE_DAY_CNT: 'CUT002',      // 발급일기준
        CPN_USE_LAST_DAY: 'CUT003',     // 발급당월말까지
        /* 쿠폰종류 */
        COM_CPN_GOODS: 'CCT001',        // 상품쿠폰
        COM_CPN_CART: 'CCT002',         // 장바구니쿠폰
        COM_CPN_DELIV: 'CCT003',        // 배송비쿠폰
        /* 쿠폰발급종류 */
        CPN_ISSUE_NOW_DISCOUNT: 'CIT001',   // 즉시할인
        CPN_ISSUE_NEW_JOIN: 'CIT002',       // 신규가입
        CPN_ISSUE_BIRTHDAY: 'CIT003',       // 생일
        CPN_ISSUE_REGULAR: 'CIT004',        // 정기발급
        CPN_ISSUE_PURCH_CONFIRM: 'CIT005',  // 구매확정보상
        CPN_ISSUE_MEM_PROMO: 'CIT006',      // 회원(프로모션)
        /* 쿠폰발급처리여부 */
        CPN_IS_START: 'CST001',     // 발급시작
        CPN_IS_STOP: 'CST002',      // 발급중지
        CPN_IS_RESTART: 'CST003',   // 발급재개
        /* 주문상태 */
        ORDER_WAITING_DEPOSIT: "ODS001",    // 입금대기
        ORDER_CANCEL_BF_DEPOSIT: "ODS002",  // 입금전취소
        ORDER_COMPLETE_PAYMENT: "ODS003",   // 결제완료
        ORDER_PREPARING_GOODS: "ODS004",    // 상품준비중
        ORDER_PREPARING_DELIV: "ODS006",    // 배송준비중
        ORDER_IN_DELIVERY: "ODS007",        // 배송중
        ORDER_COMPLETE_DELIV: "ODS008",     // 배송완료
        ORDER_PURCH_CONFIRM: "ODS009",      // 구매확정
        /* 결제수단타입 */
        PAY_CREDIT_CARD: "PWT001",      //신용카드
        PAY_VIRTURE_ACCOUNT: "PWT002",  //가상계좌
        PAY_ACCOUNT_TRANSFER: "PWT003", //계좌이체
        PAY_MOBILE: "PWT004",           //휴대폰
        PAY_NABER: "PWT005",            //네이버페이
        /* 배송요청타입 */
        RCV_REQ_INPUT: "RVT006",        // 직접입력
        /* 취소사유 */
        CNC_REASON_INPUT: "CNT005",     // 직접입력
	
        /* 클레임구분 */
        CLM_CANCEL: "CLM001",   // 취소
        CLM_RETURN: "CLM002",   // 반품
        CLM_EXCHANGE: "CLM003", // 교환
	
        /* 클레임상태 */
        CNC_WAITING_APPRV: "CNS001",    // 취소신청(승인대기)
        CNC_WAITING_PAYMENT: "CNS002",  // 취소처리(결제대기)
        CNC_REJECT: "CNS003",           // 취소완료(취소반려)
        CNC_WITHDRAW: "CNS004",         // 취소완료(취소철회)
        CNC_PRCCOMP: "CNS005",          // 취소완료(처리완료)
        RTN_PRCCOMP: "RTS010",          // 반품완료(처리완료)
        EXC_PRCCOMP: "EXS012",          // 교환완료(처리완료)

        /* AS상태 */
        AS_RECEIPT: "AST001",   // 접수완료
        AS_CANCEL: "AST002",    // 신청취소
        AS_COMP: "AST003",      // A/S완료
    },
    PARTNER: {

    },
    EMAILDOMAIN : [
        {value : '', label : '도메인을 선택'},
        {value : 'naver.com', label : 'naver.com'},
        {value : 'gmail.com', label : 'gmail.com'},
        {value : 'daum.net', label : 'daum.net'},
        {value : 'INPUT', label : '직접입력'}
    ],
    MANAGER_SESSION: 'MANAGE_USER_SESSION'
};