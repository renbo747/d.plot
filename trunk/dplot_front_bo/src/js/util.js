/**
 * 공통으로 사용되는 util 함수
 */
import storage from  './storage.js';
import store from  './store.js';

export default {
    name: 'Util',

    /**
     * 넘어온 값이 빈값인지 체크한다.
     * @param value
     * @returns boolean
     */
    isNull: function(value) {
        if (typeof value === "undefined" || value === null || value === "" || value === "undefined") {
            return true;
        } else {
            return false;
        }
    },

    /**
     * 입력값에 스페이스 이외의 의미있는 값이 있는지 체크한다.
     * @param value (String만 체크)
     * @returns boolean
     */
    isBlank: function(value) {
        if (this.isNull(value) || (typeof value == "string" && this.isNull(value.trim()))) {
            return true;
        } else {
            return false;
        }
    },

    /**
     * 객체가 비어있는지 체크
     * @param {*} param 
     * @returns 
     */
    isEmpty: function(param) {
        if(typeof param == 'undefined' || param == null) {
            return true;
        }
        return Object.keys(param).length === 0;
    },

    /**
     * 입력한 값이 숫자인지 체크한다.
     * @param value
     * @returns boolean
     */
    isNumber: function(value) {
        let regExp = /^[0-9]*$/;
        return regExp.test(value);
    },

    /**
     * 입력한 값이 핸드폰 형태인지 체크한다.
     * 하이픈이 없음
     * @param value (01022221111 or 0102221111)
     * @returns boolean
     */
    isPhone: function(value) {
        let regExp = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/;
        return regExp.test(String(value).toLowerCase());
    },

    /**
     * 입력한 값이 이메일 형태인지 체크한다.
     * @param value (숫자영문@숫자영문.숫자영문)
     * @returns boolean
     */
    isEmail: function(value) {
        let regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        return regExp.test(String(value).toLowerCase());
    },

    /**
     * 입력한 값이 날짜 형태인지 체크한다.
     * @param value 8자리(yyyymmdd) 또는 구분자를 포함한 10자리(yyyy.mm.dd, yyyy-mm-dd...) 인 경우만 체크한다.
     * @returns boolean
     */
    isDate: function(strDate) {
        if (this.isNull(strDate)) {
            return false;
        }

        let yyyy = "";
        let mm = "";
        let dd = "";
        if (strDate.length == 8) {
            yyyy = strDate.substring(0, 4);
            mm = strDate.substring(4, 6);
            dd = strDate.substring(6, 8);
        } else if (strDate.length == 10) {
            yyyy = strDate.substring(0, 4);
            mm = strDate.substring(5, 7);
            dd = strDate.substring(8, 10);
        }
        let regExp = /^\d{4}\d{2}\d{2}$/;
        if (!regExp.test(yyyy + mm + dd)) {
            return false;
        }

        let date = new Date(yyyy, mm - 1, dd);
        if (date.getFullYear() == yyyy && date.getMonth() == mm - 1 && date.getDate() == dd) {
            return true;
        } else {
            return false;
        }
    },

    /**
     * 입력한 값이 패스워드 형태인지 체크한다.
     * @param value 최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 또는 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합
     * @returns boolean
     */
    isPassword: function(value) {
        if (this.isNull(value)) {
            return false;
        }
        // (8~10자 영문, 숫자 조합 -> 필요에 따라 정규식 변경)
        // let regExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,10}$/;

        // 최소 8 자, 하나 이상의 문자, 하나의 숫자, 하나의 특수 문자 조합
        let regExp1 = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
        let reg1Valid = regExp1.test(value);

        // 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합
        let reg2Valid = true;
        let num = value.search(/[0-9]/g); // 숫자체크
        let eng = value.search(/[a-z]/ig); // 영문체크
        let spe = value.search(/[~!@#$%^&*()_+|<>?:{}]/gi); // 특수문자체크
        let len = value.length; // 최소 길이 체크
        if (len < 10 || (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ) {
            reg2Valid = false;
        }
        return reg1Valid || reg2Valid;
    },

    /**
     * 입력한 값에 영문이 포함되어있는지 확인
     * @param value
     * @returns boolean
     */
    isExistEng: function(value) {
        let regExp = /[a-zA-Z]/;
        return regExp.test(value);
    },

    /**
     * 입력한 값에 숫자가 포함되어있는지 확인
     * @param value
     * @returns boolean
     */
    isExistNum: function(value) {
        let regExp = /[0-9]/;
        return regExp.test(value);
    },

    /**
     * 입력한 값에 한글이 포함되어있는지 확인
     * @param value
     * @returns boolean
     */
    isExistKor: function(value) {
        let regExp = /[ㄱ-ㅎ가-힣]/;
        return regExp.test(value);
    },

    /**
     * 입력한 값에 특수문자가 포함되어있는지 확인
     * @param value
     * @returns boolean
     */
    isExistSpecialChar: function(value) {
        let regExp = /[~!@#$%^&*()_+|<>?:{}]/;
        return regExp.test(value);
    },

    /**
     * 입력값에 특정 문자가 포함되어있는지 체크한다.
     * @param value, char
     * @returns boolean
     */
    isContainsChar: function(value, char) {
        if (this.isNull(value) || this.isNull(char)) {
            return false;
        }

        for (let i = 0; i < value.length; i++) {
            if (char.indexOf(value.charAt(i)) > -1) {
                return true;
            }
        }
        return false;
    },

    /**
     * 원래의 문자열의 좌측에 입력한 자릿수만큼 특정 문자를 채워서 반환한다.
     * @param value, padLen, padChar
     * @returns
     */
    lpad: function(value, padLen, padChar) {
        value = this.isNull(value) ? "" : value;

        if (value.length > padLen) {
            return value;
        }

        while (value.length < padLen) {
            value = padChar + value;
        }
        return value;
    },

    /**
     * 원래의 문자열의 우측에 입력한 자릿수만큼 특정 문자를 채워서 반환한다.
     * @param value, padLen, padChar
     * @returns
     */
    rpad: function(value, padLen, padChar) {
        value = this.isNull(value) ? "" : value;

        if (value.length > padLen) {
            return value;
        }

        while (value.length < padLen) {
            value = value + padChar;
        }
        return value;
    },

    /**
     * 한영 및 특수문자 글자수를 셈
     * @reutrn integer
     */
    getStringLengthByAsc(str){
        let sum = 0;
        const a = (i, o = 1) => {
            while((i >>= 8) > 0) o++;
            return o;
        };
        for (let i in str) sum += a(str.charCodeAt(i));
        return sum;
    },


    /* 날짜 관련 공통함수 */
    /**
     * 오늘날짜를 가져온다. (12자리)
     * @returns yyyymmddhhii
     */
    getDate12NoSeparator: function() {
        let date = this.getDate('');
        let time = this.getTime('');
        let result = ''.concat(date, time).substring(0,12);
        return result;
    },

    /**
     * 오늘날짜를 가져온다.
     * @param separator (ex. '.')
     * @returns yyyy.mm.dd
     */
    getDate: function(separator) {
        separator = this.isNull(separator) ? "" : separator;
        let now = new Date();
        let yyyy = now.getFullYear();
        let mm = this.addZero(now.getMonth() + 1);
        let dd = this.addZero(now.getDate());
        return yyyy + separator + mm + separator + dd;
    },

    /**
     * 현재시간를 가져온다.
     * @param separator (ex. ':')
     * @returns hh:mi:ss
     */
    getTime: function(separator) {
        separator = this.isNull(separator) ? "" : separator;
        let now = new Date();
        let hh = this.addZero(now.getHours());
        let mi = this.addZero(now.getMinutes());
        let ss = this.addZero(now.getSeconds());
        return hh + separator + mi + separator + ss;
    },

    /**
     * 현재 년을 가져온다.
     * @returns yyyy
     */
    getYear: function() {
        let now = new Date();
        return now.getFullYear();
    },

    /**
     * 현재 월을 가져온다.
     * @returns mm
     */
    getMonth: function() {
        let now = new Date();
        return this.addZero(now.getMonth() + 1);
    },

    /**
     * 현재 일을 가져온다.
     * @returns dd
     */
    getDay: function() {
        let now = new Date();
        return this.addZero(now.getDate());
    },

    /**
     * 오늘날짜 년월을 가져온다.
     * @param separator (ex. '.')
     * @returns yyyy.mm
     */
    getYearMonth: function(separator) {
        separator = this.isNull(separator) ? "" : separator;
        let now = new Date();
        let yyyy = this.addZero(now.getFullYear());
        let mm = this.addZero(now.getMonth() + 1);
        return yyyy + separator + mm;
    },

    /**
     * 8자리 String형태의 날짜를 Date 형태로 변환한다.
     * @param sDate (ex. '20210101')
     * @returns Sun Nov 21 2021 00:00:00 GMT+0900 (한국 표준시)
     */
    convertStringToDate: function(sDate) {
        let yyyy = sDate.substring(0, 4);
        let mm = sDate.substring(4, 6) - 1;
        let dd = sDate.substring(6, 8);
        return new Date(yyyy, mm, dd);
    },

    /**
     * Date 날짜를 8자리 String으로 변환한다.
     * @param Sun Nov 21 2021 00:00:00 GMT+0900 (한국 표준시)
     * @returns stringDate (ex. '20210101')
     */
    convertDateToString: function(date, separator) {
        separator = this.isNull(separator) ? "" : separator;
        let yyyy = date.getFullYear();
        let mm = this.addZero(date.getMonth() + 1);
        let dd = this.addZero(date.getDate());
        return yyyy + separator + mm + separator + dd;
    },

    /**
     * String 날짜를 8자리 구분자를 포함한 String으로 변환한다.
     * @param sDate, separator(ex.yyyymmdd, '.')
     * @returns stringDate (ex. 'yyyy.mm.dd')
     */
    getFormatDate: function(sDate, separator) {
        separator = this.isNull(separator) ? "" : separator;
        let yyyy = sDate.substring(0, 4);
        let mm = sDate.substring(4, 6);
        let dd = sDate.substring(6, 8);
        return yyyy + separator + mm + separator + dd;
    },

    /**
     * String 문자열을 포매팅 함
     * @param date {Date} Date타입 or 정수형 or 문자열 타입 (auto 캐스틍)
     * @param foramt "yy년MM월dd일" or "yyyy년MM월dd일 E요일 hh:mm:ss (a/p)"
     * @returns stringDate (ex. "yy년MM월dd일" , "yyyy년MM월dd일 E요일 hh:mm:ss (오전)")
     */
    getDateFormat : function(date, format = "yyyy.MM.dd") {
        let d;
        if (isNaN(date)){// 숫자일 / Date
            d = new Date();
            d.setTime(date);
        }else {
            d = new Date(date);
        }
        if (!date.valueOf()) return " ";
        const zf = (v,len) => typeof v === "string" ? Array(len - v.length+1).join("0") + v : zf(v.toString(), len);// 제귀함수
        const weekName = ["일", "월", "화", "수", "목", "금", "토"];
        return format.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
            switch ($1) {
                case "yyyy": return d.getFullYear();
                case "yy": return zf(d.getFullYear() % 1000, 2);
                case "MM": return zf(d.getMonth() + 1, 2);
                case "dd": return zf(d.getDate(),2);
                case "E": return weekName[d.getDay()];
                case "HH": return zf(d.getHours(),2);
                //case "hh": return zf((((h = d.getHours()) % 12) ? h : 12),2);
                case "hh": return zf(d.getHours(),2);
                case "mm": return zf(d.getMinutes(),2);
                case "ss": return zf(d.getSeconds(),2);
                case "a/p": return d.getHours() < 12 ? "오전" : "오후";
                default: return $1;
            }
        });
    },

    /**
     * String 날짜를 자릿수에 따라 Format을 변경한다.
     * @param strDate (ex.20210101090000)
     * @returns stringDate (yyyy-mm-dd hh:mi:ss)
     */
    getFormatDate2: function(sDate) {
        let returnValue = "";
        let yyyy = "";
        let mm = "";
        let dd = "";
        let hh = "";
        let mi = "";
        let ss = "";

        if (sDate.length == 8) {
            yyyy = sDate.substring(0, 4);
            mm = sDate.substring(4, 6);
            dd = sDate.substring(6, 8);
            returnValue = yyyy + "-" + mm + "-" + dd;
        } else if (sDate.length == 12) {
            yyyy = sDate.substring(0, 4);
            mm = sDate.substring(4, 6);
            dd = sDate.substring(6, 8);
            hh = sDate.substring(8, 10);
            mi = sDate.substring(10, 12);
            returnValue = yyyy + "-" + mm + "-" + dd + " " + hh + ":" + mi;
        } else if (sDate.length == 14) {
            yyyy = sDate.substring(0, 4);
            mm = sDate.substring(4, 6);
            dd = sDate.substring(6, 8);
            hh = sDate.substring(8, 10);
            mi = sDate.substring(10, 12);
            ss = sDate.substring(12, 14);
            returnValue = yyyy + "-" + mm + "-" + dd + " " + hh + ":" + mi + ":" + ss;
        }
        return returnValue;
    },

    /**
     * String 날짜를 자릿수에 따라 Format을 변경한다.
     * @param strDate (ex.20210101090000)
     * @returns stringDate (yyyy-mm-dd hh:mi:ss)
     */
    getFormatDate3: function(sDate, separator) {
    let returnValue = "";
    let yyyy = "";
    let mm = "";
    let dd = "";
    let hh = "";
    let mi = "";
    let ss = "";
    let sep = separator == "" ? "-":separator;

    if (sDate.length == 8) {
        yyyy = sDate.substring(0, 4);
        mm = sDate.substring(4, 6);
        dd = sDate.substring(6, 8);
        returnValue = yyyy + sep + mm + sep + dd;
    } else if (sDate.length == 12) {
        yyyy = sDate.substring(0, 4);
        mm = sDate.substring(4, 6);
        dd = sDate.substring(6, 8);
        hh = sDate.substring(8, 10);
        mi = sDate.substring(10, 12);
        returnValue = yyyy + sep + mm + sep + dd + " " + hh + ":" + mi;
    } else if (sDate.length == 14) {
        yyyy = sDate.substring(0, 4);
        mm = sDate.substring(4, 6);
        dd = sDate.substring(6, 8);
        hh = sDate.substring(8, 10);
        mi = sDate.substring(10, 12);
        ss = sDate.substring(12, 14);
        returnValue = yyyy + sep + mm + sep + dd + " " + hh + ":" + mi + ":" + ss;
    }
    return returnValue;
},

    /**
     * 입력한 날짜의 요일을 가져온다.
     * @param 8자리 String형태의 날짜 (ex.20210101)
     * @returns 요일
     */
    getDayOfWeek: function(sDate) {
        const week = ['일', '월', '화', '수', '목', '금', '토'];
        let date = this.convertStringToDate(sDate);
        return week[date.getDay()];
    },

    /**
     * 입력한 날짜의 년도를 계산하여 날짜를 반환한다.
     * @params yyyymmdd, yyyy.mm.dd
     * @params sDate, n, separator
     * @returns yyyymmdd
     */
    getAddYear: function(sDate, n, separator) {
        separator = this.isNull(separator) ? "" : separator;
        if (sDate.length == 10) {
            let yyyy = sDate.substring(0, 4);
            let mm = sDate.substring(5, 7);
            let dd = sDate.substring(8, 10);
            sDate = yyyy+mm+dd;
        }

        let date = this.convertStringToDate(sDate);
        date.setFullYear(date.getFullYear() + n);
        return this.convertDateToString(date, separator);
    },

    /**
     * 입력한 날짜의 월을 계산하여 날짜를 반환한다.
     * @params yyyymmdd, yyyy.mm.dd
     * @returns yyyymmdd
     */
    getAddMonth: function(sDate, n, separator) {
        separator = this.isNull(separator) ? "" : separator;
        if (sDate.length == 10) {
            let yyyy = sDate.substring(0, 4);
            let mm = sDate.substring(5, 7);
            let dd = sDate.substring(8, 10);
            sDate = yyyy+mm+dd;
        }

        let date = this.convertStringToDate(sDate);
        // n달 후의 1일
        let addMonthFirstDate = new Date(date.getFullYear(), date.getMonth() + n, 1);
        // n달 후의 말일
        let addMonthLastDate = new Date(addMonthFirstDate.getFullYear(), addMonthFirstDate.getMonth() + 1, 0);
        let result = addMonthFirstDate;

        if (date.getDate() > addMonthLastDate.getDate()) {
            result.setDate(addMonthLastDate.getDate());
        } else {
            result.setDate(date.getDate());
        }
        return this.convertDateToString(result, separator);
    },

    /**
     * 입력한 날짜의 일을 계산하여 날짜를 반환한다.
     * @params yyyymmdd, yyyy.mm.dd
     * @returns yyyymmdd
     */
    getAddDate: function(sDate, n, separator) {
        separator = this.isNull(separator) ? "" : separator;
        if (sDate.length == 10) {
            let yyyy = sDate.substring(0, 4);
            let mm = sDate.substring(5, 7);
            let dd = sDate.substring(8, 10);
            sDate = yyyy+mm+dd;
        }

        let date = this.convertStringToDate(sDate);
        date.setDate(date.getDate() + n);
        return this.convertDateToString(date, separator);
    },

    /**
     * 입력한 두 날짜의 기간(일수)을 반환한다.
     * @params
     * @returns yyyymmdd
     */
    getDateDiff: function(sDate1, sDate2) {
        let startDate = this.convertStringToDate(sDate1);
        let endDate = this.convertStringToDate(sDate2);
        let diffTime = endDate.getTime() - startDate.getTime();
        return Math.floor(diffTime / (1000 * 60 * 60 * 24));
    },

    /**
     * 전체 월을 가져온다.
     * @returns [{code: 01, value: 01월}, ...{code: 12, value: 12월}]
     */
    getAllMonth: function() {
        let array = new Array();
        for (let i = 1; i <= 12; i++) {
            array.push({ "code": String(this.addZero(i)), "value": this.addZero(i) + "월" });
        }
        return array;
    },

    /**
     * 입력한 년월의 전체 일을 가져온다.
     * @param yyyymm, yyyymmdd
     * @returns [{code: 01, value: 01일}, ...{code: 31, value: 31월}]
     */
    getAllDay: function(sYearMonth) {
        let yyyy = sYearMonth.substring(0, 4);
        let mm = sYearMonth.substring(4, 6);
        let date = new Date(yyyy, mm, 0);
        let array = new Array();
        for (let i = 1; i <= date.getDate(); i++) {
            array.push({ "code": String(this.addZero(i)), "value": this.addZero(i) + "일" });
        }
        return array;
    },

    /**
     * 입력한 날짜의 1일을 가져온다.
     * @param yyyymm, yyyymmdd
     * @return yyyymm01
     */
    getMonthFirstDate: function(sYearMonth, separator) {
        separator = this.isNull(separator) ? "" : separator;
        let yyyy = sYearMonth.substring(0, 4);
        let mm = sYearMonth.substring(4, 6) - 1;
        let date = new Date(yyyy, mm, 1);
        return this.convertDateToString(date, separator);
    },

    /**
     * 입력한 달의 마지막 날짜를 가져온다.
     * @param yyyymm, yyyymmdd
     * @return yyyymm31
     */
    getMonthLastDate: function(sYearMonth, separator) {
        separator = this.isNull(separator) ? "" : separator;
        let yyyy = sYearMonth.substring(0, 4);
        let mm = sYearMonth.substring(4, 6) - 1;
        let date = new Date(yyyy, mm + 1, 0);
        return this.convertDateToString(date, separator);
    },

    /**
     * 입력한 숫자가 한자리인 경우 앞에 0을 붙여서 반환한다.
     * @param i (ex. 1)
     * @returns (ex. 01)
     */
    addZero: function(i) {
        if (i < 10) {
            i = "0" + i;
        } else {
            i = "" + i;
        }
        return i;
    },


    /* UI 관련 공통함수 */
    /**
     * Scroll을 Top으로 이동한다.
     */
    scrollToTop: function() {
        window.scrollTo(0, 0);
    },

    /**
     *이스케이프처리
     */
    toEscape(value) {
        let ret = "";
        //ret = Converter.toStr(value);
        ret = value != null ? String(value) : "";
        ret = ret.replaceAll("&#39;", "'");
        ret = ret.replaceAll("&quot;", "\"");
        ret = ret.replaceAll("&gt;", ">");
        ret = ret.replaceAll("&lt;", "<");
        ret = ret.replaceAll("&amp;", "&");
        ret = ret.replaceAll("&nbsp;", " ");
        return ret;
    },

    /**
     *이스케이프처리
     */
    toUnescape(value) {
        let ret = "";
        //ret = Converter.toStr(value);
        ret = value != null ? String(value) : "";
        ret = ret.replaceAll("'", "&#39;");
        ret = ret.replaceAll("\"", "&quot;");
        ret = ret.replaceAll(">", "&gt;");
        ret = ret.replaceAll("<", "&lt;");
        ret = ret.replaceAll("&", "&amp;");
        ret = ret.replaceAll(" ", "&nbsp;");
        return ret;
    },

    /**
     * 브라우저 체크
     */
    getBrowser() {
        var agent = navigator.userAgent.toLowerCase();

        if ((navigator.appName == 'Netscape' &&
                navigator.userAgent.search('Trident') != -1 ||
                (agent.indexOf("msie") != -1))) {
            return 'ie';
        } else if (agent.indexOf("edg") != -1) {
            return 'edge';
        } else if (agent.indexOf("chrome") != -1) {
            return 'chrome';
        } else if (agent.indexOf("safari") != -1) {
            return 'safari';
        } else if (agent.indexOf("firefox") != -1) {
            return 'firefox';
        } else {
            return 'other';
        }
    },

    /**
     *로그 출력
     */
    debug(msg) {
        if (process.env.VUE_APP_LOG_LEVEL == "DEBUG" || process.env.VUE_APP_LOG_LEVEL == "INFO") {
            let callFile = '';
            if (this.getBrowser() != 'ie') {
                callFile = (new Error()).stack.split("\n")[2].split("/").slice(-1)[0].split(":")[0].split("?")[0];
                callFile = '(' + callFile + ') '
            }
            if (typeof msg === 'string' || msg instanceof String) {
                console.log('[DEBUG] ' + callFile + msg);
            } else {
                console.log('[DEBUG] ' + callFile + ' : ');
                console.log(msg);
            }
        }
    },

    info(msg) {
        if (process.env.VUE_APP_LOG_LEVEL == "INFO") {
            let callFile = '';
            if (this.getBrowser() != 'ie') {
                callFile = (new Error()).stack.split("\n")[2].split("/").slice(-1)[0].split(":")[0].split("?")[0];
                callFile = '(' + callFile + ') '
            }
            if (typeof msg === 'string' || msg instanceof String) {
                console.log('[INFO] ' + callFile + msg);
            } else {
                console.log('[INFO] ' + callFile + ' : ');
                console.log(msg);
            }
        }
    },

    error(msg) {
        if (process.env.VUE_APP_LOG_LEVEL == "DEBUG" || process.env.VUE_APP_LOG_LEVEL == "INFO") {
            let callFile = '';
            if (this.getBrowser() != 'ie') {
                callFile = (new Error()).stack.split("\n")[2].split("/").slice(-1)[0].split(":")[0].split("?")[0];
                callFile = '(' + callFile + ') '
            }
            if (typeof msg === 'string' || msg instanceof String) {
                console.error('[ERROR] ' + callFile + msg);
            } else {
                console.error('[ERROR] ' + callFile + ' : ');
                console.error(msg);
            }
        }
    },

    nameFilter(value){
        if(value == null || typeof value === 'undefined'){
            return '';
        }

        return value.slice(0, 1) + value.slice(1).replace(/./g, "*");
    },

    emailFilter(value){
        if(value == null || typeof value === 'undefined'){
            return '';
        }

        let strLength =  value.toString().split('@')[0].length - 2;
        return value.toString().replace(new RegExp('.(?=.{0,' + strLength + '}@)', 'g'), '*');
    },

    phoneNumberFilter: function(value, isFilter) {
        if(value == null || typeof value === 'undefined'){
            return '';
        }

        value = value.replace(/-/gi, '');

        if(typeof isFilter !== 'undefined' && isFilter){
            if (value.length < 11) {
                return value.replace(/[^0-9]/g, '').replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
            } else {
                return value.replace(/[^0-9]/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
            }
        } else {
            if (value.length < 11) {
                return value.replace(/[^0-9]/g, '').replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
            } else {
                return value.replace(/[^0-9]/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            }
        }
    },

    bizNumberFilter: function(value, index) {
        if (typeof value === 'undefined' || value === '') {
            return value;
        } else {
            value = value.replace(/-/gi, '');
            if (index === 1) {
                return value.substring(0, 3);
            } else if (index === 2) {
                return value.substring(3, 5);
            } else if (index === 3) {
                return value.substring(5);
            } else {
                return value.replace(/[^0-9]/g, '').replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-*****');
            }
        }
    },

    /**
     * 로컬스토리지에 로그인 정보 있는지 확인
     * @param param : MEMBER_USER(디폴트), ADMIN_USER, PARTNER_USER}
     * @returns true, 없으면 false
     */
    isAuthorized(param) {
        const type = param || "MEMBER_USER";

        let object = {};
        if (type === "MEMBER_USER") {
            object = storage.getLocalStorage(store.getters.CONSTANTS.MEMBER_USER);
        } else if (type === "ADMIN_USER") {
            object = storage.getLocalStorage(store.getters.CONSTANTS.ADMIN_USER);
        } else if (type === "PARTNER_USER") {
            object = storage.getLocalStorage(store.getters.CONSTANTS.PARTNER_USER);
        } else if (type === 'MANAGE_USER_SESSION') {
            object = storage.getLocalStorage(store.getters.CONSTANTS.MANAGER_SESSION);
        }
        return !this.isNull(object) && !this.isEmpty(object);
    },

    /**
     * 로컬스토리지에서 로그인 정보 가져옴
     * @param param : MEMBER_USER(디폴트), ADMIN_USER, PARTNER_USER}
     * @returns object, 없으면 null
     */
    getUser(param) {
        const type = param || "MEMBER_USER";
        if (!this.isAuthorized(param)) {
            return null;
        } else {
            if (type == "MEMBER_USER") {
                return storage.getLocalStorage(store.getters.CONSTANTS.MEMBER_USER);
            } else if (type == "ADMIN_USER") {
                return storage.getLocalStorage(store.getters.CONSTANTS.ADMIN_USER);
            } else if (type == "PARTNER_USER") {
                return storage.getLocalStorage(store.getters.CONSTANTS.PARTNER_USER);
            } else if (type === 'MANAGE_USER_SESSION') {
                return storage.getLocalStorage(store.getters.CONSTANTS.MANAGER_SESSION);
            } else {
                return null;
            }
        }
    },

    dataSetSearchParam(vueObj){
        vueObj.$storage.setSessionStorage("searchParams", {searchData: vueObj.searchData, pagingData: vueObj.pagingData});
    },

    componentSetSearchParam(vueObj) {
        let searchParams = vueObj.$storage.getSessionStorage('searchParams');
        if (!vueObj.$util.isNull(searchParams)) {
            for (const [key, value] of Object.entries(searchParams.searchData)) {
                vueObj.searchData[key] = value;
            }
            if (!this.isNull(searchParams.pagingData)) {
                for (const [key, value] of Object.entries(searchParams.pagingData)) {
                    vueObj.pagingData[key] = value;
                }
            }
        }
    },

    /**
     * 초를 hh:mm:ss로 변경
     * ex) 10 -> 00:10
     * ex) 600 -> 10:00
     * ex) 3600 -> 10:00:00
     * @param sec
     */
    toHHMMSS (secs) {
        var sec_num = parseInt(secs, 10)
        var hours   = Math.floor(sec_num / 3600) || 0
        var minutes = Math.floor(sec_num / 60) % 60 || 0
        var seconds = sec_num % 60 || 0
        return [hours,minutes,seconds].map(v => v < 10 ? "0" + v : v).filter((v,i) => v !== "00" || i > 0).join(":");
    },

    /**
     * 초를 hh:mm:ss로 변경
     * ex) 10 -> 00:10
     * ex) 600 -> 10:00
     * ex) 3600 -> 10:00:00
     * @param sec
     */
     toDDHHMMSS (secs) {
        var sec_num = parseInt(secs, 10)
        var days    = Math.floor(sec_num / 86400) || 0
        var hours   = Math.floor(sec_num / 3600)%24 || 0
        var minutes = Math.floor(sec_num / 60) % 60 || 0
        var seconds = sec_num % 60 || 0
        return [days, hours,minutes,seconds].map(v => v < 10 ? "0" + v : v).filter((v,i) => v !== "00" || i > 0).join(":");
    },

    /**
     * 조사(이, 가, 은, 는, 을, 를)를 선택
     * ex) josaStr('아이디', '을') => 아이디를
     * @param {*} txt
     * @param {*} josa
     * @returns
     */
    josaStr(txt, josa) {
        var code = txt.charCodeAt(txt.length-1) - 44032;

        // 원본 문구가 없을때는 빈 문자열 반환
        if (txt.length == 0) return '';

        // 한글이 아닐때
        if (code < 0 || code > 11171) return txt;

        if (code % 28 == 0) return txt + this._josaStr(josa, false);
        else return txt + this._josaStr(josa, true);
    },
    _josaStr(josa, jong) {
        // jong : true면 받침있음, false면 받침없음

        if (josa == '을' || josa == '를') return (jong?'을':'를');
        if (josa == '이' || josa == '가') return (jong?'이':'가');
        if (josa == '은' || josa == '는') return (jong?'은':'는');
        if (josa == '와' || josa == '과') return (jong?'와':'과');

        // 알 수 없는 조사
        return '**';
    },


    /**
     * Data Valid체크 후 메시지 리턴
     * null일경우 정상
     * ex )
     * var data = {
     *     mobile : '',
     *     userInfo = {
     *         userid : '',
     *         userpw : ''
     *     }
     * }
     * var valid = [
     *     {field : 'mbile', name :'휴대폰번호', type : 'I', required : true},
     *     {field : 'userInfo.userid', name :'아이디', type : 'I', required : true, minLength:4, maxLength : 12},
     *     {field : 'userInfo.userpw', name : '비밀번호', type : 'I', required : true, minLength:8}
     * ]
     *
     * this.$util.validMsg(data, valid) => 아이디를 입력해주세요.
     * required : 필수입력
     * type : (I : 입력, S : 선택, C : 체크)
     * minLength : 최소길이
     * maxLength :최대길이
     * @param {*} data
     * @param {*} validArr
     * @returns
     */
    validMsg(data, validArr) {
        try {
            for(var i = 0 ; i < validArr.length ; i++) {
                var fieldName = validArr[i].name;
                var temp = Object.assign({}, data);
                var val = this._getDescendantProp(temp, validArr[i].field);
                if(val === 'undefined' || val === undefined) return '[' + validArr[i].field + '] 필드가 존재하지 않습니다.';
                if(validArr[i].required) {
                    if(this.isNull(val)) {
                        var txt = "입력";
                        if(validArr[i].type == "S") {
                            txt = "선택"
                        } else if (validArr[i].type == "C"){
                            txt = "체크"
                        }
                        return this.josaStr(fieldName, '를') + ' ' + txt +'해주세요.';
                    }
                } else if(validArr[i].minLength > val.length) {
                    return this.josaStr(fieldName, '는') + ' ' + validArr[i].minLength +'자 이상이어야 합니다.';
                } else if(validArr[i].maxLength < val.length) {
                    return this.josaStr(fieldName, '는') + ' ' + validArr[i].maxLength +'자 이하이어야 합니다.';
                }
            }
            return null;
        } catch (e) {
            this.error('Error validate : ' + e);
            return false;
        }

    },
    _getDescendantProp(obj, desc) {
        var arr = desc.split(".");
        while(arr.length && (obj = obj[arr.shift()]));
        // console.log(obj);
        return obj;
    },
    getParam(sname) {
        var sval = "";
        var params = location.search.substr(location.search.indexOf("?") + 1);
        if(this.isEmpty(params)){
            return sval;
        }

        params = params.split("&");
        // console.log(params);
        for (var i = 0; i < params.length; i++) {
            var temp = params[i].split("=");
            if ([temp[0]] == sname) { sval = temp[1]; }
        }
        return sval;
    },
    isMobile() {
        var check = false;
        var UserAgent = navigator.userAgent;
        if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
            || UserAgent.match(/LG|SAMSUNG|Samsung/) != null) {
            check = true;
        }
        
        // var uAgent = navigator.userAgent.toLowerCase(); // 아래는 모바일 장치들의 모바일 페이지 접속을위한 스크립트
        // var mobilePhones = new Array('iphone', 'ipod', 'ipad', 'android', 'blackberry', 'windows ce','nokia', 'webos', 'opera mini', 'sonyericsson', 'opera mobi', 'iemobile');
        // for (var i = 0; i < mobilePhones.length; i++){
        //     if (uAgent.indexOf(mobilePhones[i]) != -1){
        //         check = true;
        //     }
        // }
        return check;
    },
    browser() {
        if((navigator.userAgent.indexOf("Opera") || navigator.userAgent.indexOf('OPR')) != -1 ) {
            return 'Opera';
        } else if(navigator.userAgent.indexOf("Edg") != -1 ) {
            return 'Edge';
        } else if(navigator.userAgent.indexOf("Chrome") != -1 ) {
            return 'Chrome';
        } else if(navigator.userAgent.indexOf("Safari") != -1) {
            return 'Safari';
        } else if(navigator.userAgent.indexOf("Firefox") != -1 ) {
            return 'Firefox';
        } else if((navigator.userAgent.indexOf("MSIE") != -1 ) || (!!document.documentMode == true )) {
            //IF IE > 10
            return 'IE'; 
        } else {
            return 'unknown';
        }
    },
    /**
     * 기본 배열 특정 값 하나 삭제
     *
     * @param arr 삭제할 배열
     * @param value 특정 값
     */
    removeArrOnce(arr, value) {
        let index = arr.indexOf(value);
        if (index > -1) {
            arr.splice(index, 1);
        }
    },

    /**
     * 기본 배열 특정 값 전부 삭제
     *
     * @param arr 삭제할 배열
     * @param value 특정 값
     */
    removeArrAll(arr, value) {
        let i = 0;
        while (i < arr.length) {
            if (arr[i] === value) {
                arr.splice(i, 1);
            } else {
                ++i;
            }
        }
    },
    /**
     * 깊은 복사
     */
    deepClone(obj) {
        if (obj === null || typeof obj !== "object") {
            return obj;
        }
        const result = Array.isArray(obj) ? [] : {};

        for(let key of Object.keys(obj)) {
            result[key] = this.deepClone(obj[key]);
        }
        return result;
    },
    /******************************
     * mask처리 함수
     ******************************/
    /**
     * 유저id mask처리
     * @param {*} value
     * @returns
     */
    maskUserId(value) {
        let maskId;
        if (this.isNull(value)) {
            return value;
        }
        if (value.length < 3) {
            maskId = value.slice(0, 1) + value.slice(1).replace(/./g, "*");
        }else {
            maskId = value.slice(0, 3) + value.slice(3).replace(/./g, "*");
        }

        return maskId;
    },
    /**
     * 사용자 이름 mask처리
     * @param {*} value
     * @returns
     */
    maskUserName(value) {
        let resultValue;
        if (this.isNull(value)) {
            return value;
        }
        let valueLen = value.length;
        if (valueLen < 3) {
            resultValue = value.slice(0, 1) + value.slice(1).replace(/./g, "*");
        } else {
            resultValue = value.slice(0, 1) + value.slice(1, valueLen-1).replace(/./g, "*") + value.slice(valueLen-1, valueLen);
        }
        return resultValue;
    },
    /**
     * 사용자 연락처 mask처리
     * @param {*} value
     * @returns
     */
    maskUserTel(value) {
        let resultValue;
        if (this.isNull(value)) {
            return value;
        }
        resultValue = String(value).replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        if(/-[0-9]{3}-/.test(resultValue)) {
            resultValue = resultValue.replace(/-[0-9]{3}-/g, "-***-");
        } else if(/-[0-9]{4}-/.test(resultValue)) {
            resultValue = resultValue.replace(/-[0-9]{4}-/g, "-****-");
        }

        return resultValue;
    },
    /**
     * 소수점 제거 처리(별점에서 소수점 책정x)
     * @param {*} value
     * @returns
     */
    maskFloor(value) {
        if (this.isNull(value)) {
            return '';
        }
        return Math.floor(value);
    },
    /**
     * 금액에 , 처리
     * @param {*} value
     * @returns
     */
    maskComma(value) {
        if (this.isNull(value)) {
            return 0;
        }
        return String(value).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    /**
     * 전화번호 - 표시
     * @param {*} value
     * @returns
     */
    maskTel(value) {
        if(this.isNull(value)) {
            return '';
        }
        return String(value).replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
    },
    /**
     * 브랜드명 => [브랜드명]로 변환처리
     * @param {*} value 
     * @returns 
     */
    maskBrandName(value) {
        if (this.isNull(value)) {
            return '';
        }
        return "[" + value + "]";
    },
    /**
     * 할인율 계산
     * @param {*} value1 원금액
     * @param {*} value2 할인금액
     * @returns 
     */
    saleRate(value1, value2) {
        return 100 - Math.round(((value2/value1) * 100));
    },
    /**
     * object 를 쿼리스트링 형태로 변환(단 '&' 대신 '#'으로) 이유 
     * kmc인증 전달 데이터로 사용되는데, "/","|","&" 사용 불가능 
     * @param {object} obj 
     * @returns 
     */
    toQueryStr(obj) {
        if (typeof obj !== "object") {
            return '';
        }else {
            return Object.entries(obj).map(e => e.join('=')).join('#');
        }
    },
    /**
     * List를 N개씩 분할 처리
     * ex)list = [{idx:1,name:'사과'},{idx:1,name:'파인애플'},{idx:1,name:'복숭아'},{idx:1,name:'바나나'}]
     *    n    = 2
     *  return = [[{idx:1,name:'사과'},{idx:1,name:'파인애플'}],[{idx:1,name:'복숭아'},{idx:1,name:'바나나'}]]
     * @param {*} list 
     * @param {*} n 
     * @returns 
     */
    division(list, n) {
        var arr = list;
        var len = arr.length;
        var cnt = Math.floor(len/n) + (Math.floor(len%n) > 0 ? 1: 0);
        var temp = [];

        for (var i= 0; i< cnt; i++) {
            temp.push(arr.splice(0,n));
        }
        return temp;
    },
    /***
     * 동영상 확장자 변경
     */
     changeFileType(url, type) {
        let fileLen = url.length;
        let fileDot = url.lastIndexOf(".");
        let fileType = url.substring(fileDot+1, fileLen);
        return url.replace("."+fileType, type);

    },

    /***
     * v-html로 미리보는 이미지 크기 조정
     */
    showImageResize(html) {
        if(this.isNull(html)) {
            return ;
        }

        let resizeHtml = html.replaceAll("<img", "<img style='max-width: 100%; height: auto;'");

        return resizeHtml;
    },
}
