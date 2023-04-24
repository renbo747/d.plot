import Vue from 'vue'
import util from './util.js'

/**
 * 숫자 세자리마다 콤마(,) 찍기
 */
Vue.filter("comma", val => {
    if (val == null) {
        return 0;
    }
    return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
})

/**
 * 전화번호 하이픈 및 가운데 4자리 아스타(*)처리
 */
Vue.filter("phoneNumber", val => {
    if(util.isEmpty(val)){
        return '';
    }
    val = val.replace(/-/gi, '');

    if (val < 11) {
        return val.replace(/[^0-9]/g, '').replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
    } else {
        return val.replace(/[^0-9]/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
    }
})

/**
 * String 문자열을 포매팅 함
 * @param date {Date} Date타입 or 정수형 or 문자열 타입 (auto 캐스틍)
 * @param foramt "yy년MM월dd일" or "yyyy년MM월dd일 E요일 hh:mm:ss (a/p)"
 * @returns stringDate (ex. "yy년MM월dd일" , "yyyy년MM월dd일 E요일 hh:mm:ss (오전)")
 * {{getGoodsDiscountPer | date('yy.MM.dd')}}
 * {{getGoodsDiscountPer | date}}
 */
Vue.filter("date", (val, format)=> {
    return util.getDateFromat(val, format);
})

/**
 * 소수점 버림 처리 
 */
Vue.filter('floor', val => {
    if(util.isNull(val)){
        return '';
    }
    return Math.floor(val); 
}) 

/**
 * 이스케이프문자를 HTML로 변환
 */
Vue.filter('unescapeHtml', val => {
    return val.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, "\"");
})

/**
 * ID 4글자 부터 *로 마스킹 처리
 */
Vue.filter('userid', id => {
    let originId = id;
    let maskId;
    let strLen;

    if (util.isNull(originId)) {
        return originId;
    }

    strLen = originId.length;

    if(strLen < 3){
        // maskId = originId.replace(/(?<=.{1})./gi, "*");
        maskId = originId.slice(0, 1) + value.slice(1).replace(/./g, "*");
    }else { 
        // maskId = originId.replace(/(?<=.{2})./gi, "*");
        maskId = originId.slice(0, 3) + value.slice(3).replace(/./g, "*");
    } 
    return maskId;
})