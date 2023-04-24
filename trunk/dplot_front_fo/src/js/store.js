import Vue from 'vue'
import Vuex from 'vuex'
import constants from "./constants.js"

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        constants: constants,
        // 서브 헤더 옵션 관리
        subHeaderOption: {
            title: "",
            searchIcon: true,
            cartIcon: true,
            homeIcon: true,
        },
        platform : '',
        isCart :false,
        isMypage :false,
        isLogin: false,
        membertype:"",
        name:"",
        joinchtype:"",
        cartList : [],
        filterinfo:{
            setCateidx:null,
            setBrandList:[],
            setColorList:[],
            setBenefitList:[],
            setPriceList:[],
            setRatingList:[],
            setFilterList:[],
            setGiconList:[],
            setRecomList:[]
        },
        brandfilterinfo:{
            setBrandList:[],
            setFilterList:[],
            fBrandList:[]
        },
        kmcInfo:{},
        savedPosition: {},
        fBrandList:[],
        giconList:[],
        recomList:[],
        parentInfo:{},
        cateList:[],
        colorList:[],
        goodsList:[],
        priceList:[],
        ratingList:[],
        benefitList:[],
        setPriceValue:{
            currentValue: [0, 1000000], //현재 금액
            min: 0, //최소 금액
            max: 100000000, //최대 금액
            interval: 100000, //금액 증가량
        },
        isBio:false,
        isKakao:false,
        isNaver:false,
        isApple:false,
        cartCount:0,
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
        membertype: (state, payload) => {
            state.membertype = payload;
        },
        name: (state, payload) => {
            state.name = payload;
        },
        joinchtype: (state, payload) => {
            state.joinchtype = payload;
        },
        cartList:(state, payload) => {
            state.cartList = payload;
        },
        filterinfo:(state, payload)=>{
            state.filterinfo = payload;
        },
        brandfilterinfo:(state, payload)=>{
            state.brandfilterinfo = payload;
        },
        redirectUrl:(state, payload)=>{
            state.redirectUrl = payload;
        },
        savedPosition:(state, payload)=>{
            state.savedPosition = payload;
        },
        _this:(state, payload)=>{
            state._this = payload;
        },
        parentInfo:(state, payload)=>{
            state.parentInfo = payload;
        },
        cateList:(state, payload)=>{
            state.cateList = payload;
        },
        fBrandList:(state, payload)=> {
            state.fBrandList = payload;
        },
        giconList:(state, payload)=> {
            state.giconList = payload;
        },
        recomList:(state, payload)=> {
            state.recomList = payload;
        },
        colorList:(state, payload)=> {
            state.colorList = payload;
        },
        benefitList:(state, payload)=> {
            state.benefitList = payload;
        },
        ratingList:(state, payload)=> {
            state.ratingList = payload;
        },
        goodsList:(state, payload)=> {
            state.goodsList = payload;
        },
        priceList:(state, payload)=> {
            state.priceList = payload;
        },
        setPriceValue:(state, payload)=>{
            state.setPriceValue = payload;
        },
        isBio:(state, payload)=>{
            state.isBio = payload;
        },
        isKakao:(state, payload)=>{
            state.isKakao = payload;
        },
        isNaver:(state, payload)=>{
            state.isNaver = payload;
        },
        isApple:(state, payload)=>{
            state.isApple = payload;
        },
        cartCount:(state, payload)=>{
            state.cartCount = payload;
        },
    },
    getters: {
        // this.store.getters.TODO
        CONSTANTS: state => {
            return state.constants
        },
        ADMIN: state => {
            return state.constants.ADMIN;
        },
        MEMBER: state => {
            return state.constants.MEMBER;
        },
    }
});