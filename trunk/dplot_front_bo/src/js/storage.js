/**
 * storage 관련 공통함수
 */
import util from  './util.js';

export default {
    name: 'Storage',

    /**
     * localStorage에 데이터를 저장한다.
     */
     setLocalStorage: function(key, data) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }
        localStorage.setItem(key, JSON.stringify(data));
    },

    /**
     * localStorage에 저장된 데이터를 가져온다.
     */
    getLocalStorage: function(key) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }
        var item = localStorage.getItem(key);
        if (util.isNull(item)) {
            return;
        }
        return JSON.parse(item);
    },

    /**
     * localStorage에 저장된 데이터를 지운다.
     */
    removeLocalStorage: function(key) {
        if (!window.localStorage || !window.JSON || !key) {
            return;
        }
        localStorage.removeItem(key);
    },

    /**
     * sessionStorage에 데이터를 저장한다.
     */
    setSessionStorage: function(key, data) {
        if (!window.sessionStorage || !window.JSON || !key) {
            return;
        }
        sessionStorage.setItem(key, JSON.stringify(data));
    },

    /**
     * sessionStorage에 저장된 데이터를 가져온다.
     */
    getSessionStorage: function(key) {
        if (!window.sessionStorage || !window.JSON || !key) {
            return;
        }
        let item = sessionStorage.getItem(key);
        if (!item) {
            return;
        }
        return JSON.parse(item);
    },

    /**
     * sessionStorage에 저장된 데이터를 지운다.
     */
    removeSessionStorage: function(key) {
        if (!window.sessionStorage || !window.JSON || !key) {
            return;
        }
        sessionStorage.removeItem(key);
    }
}