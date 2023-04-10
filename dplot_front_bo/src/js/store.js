import Vue from 'vue'
import Vuex from 'vuex'
import constants from "./constants.js"

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        constants: constants,
        // 서브 헤더 옵션 관리
        subHeaderOption: {
            title: "페이지 제목",
            searchIcon: true,
            cartIcon: true,
            homeIcon: true,
        },
        platform : '',
        isCart :false,
        isMypage :false,
        isLogin: false,
        cartList : []
    },
    mutations: {
        onSubHeaderOption: (state, payload) => {
            state.subHeaderOption = payload;
        },
        platform : (state, payload) =>{
            state.platform = payload;
        },
        isCart: (state, payload) => {
            state.isCart = payload;
        },
        isMypage: (state, payload) => {
            state.isMypage = payload;
        },
        isLogin: (state, payload) => {
            state.isLogin = payload;
        },
        cartList:(state, payload) => {
            state.cartList = payload;
        }
    },
    getters: {
        // this.store.getters.TODO
        CONSTANTS: state => {
            return state.constants
        },
        ADMIN: state => {
            return state.constants.ADMIN;
        }
    }
});