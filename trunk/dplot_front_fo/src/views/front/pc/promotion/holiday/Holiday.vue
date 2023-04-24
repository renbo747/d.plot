<template>
    <div class="promotion_container holiyday">
        <div class="section cover_section">
            <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_cover.jpg" alt="" />
            <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_mo_cover.jpg" alt="">
        </div>
        <div class="tab_wrap" id="prom_tab_menu">
            <div class="tab_container">
                <ul v-if="!mobileCheck">
                    <li v-for="(menu, idx) in menus" :class="menuclassList[idx]" :key="idx"><a @click="scrollToTargetAdjusted(`#prom0${idx+1}`)" v-html="menu"></a></li>
                </ul>
                <ul v-else>
                    <li v-for="(menu, idx) in menusmo" :class="menuclassList[idx]" :key="idx"><a @click="scrollToTargetAdjusted(`#prom0${idx+1}`)" v-html="menu"></a></li>
                </ul>
            </div>
        </div>
        <div class="section section01" id="prom01">
            <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_top.jpg" alt="" />
            <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_mo_top.jpg" alt="">
            <div class="stamp_wrap" v-if="!mobileCheck">
                <div class="stamp_item" v-for="(stamp, idx) in stampitem" :key="idx" @click="stampAction(stamp.id)">
                    <img :src="stamp.status ? stamp.disableimg : stamp.activeimg" alt="" />
                </div>
                <img src="~@/assets/common/img/promotion/holiyday/post_bg.png" alt="" />
            </div>
            <div class="stamp_wrap" v-else>
                <div class="stamp_item" v-for="(stamp, idx) in stampitem" :key="idx" @click="stampAction(stamp.id)">
                    <img :src="stamp.status ? stamp.disableimg : stamp.activeimg" alt="" />
                </div>
                <img src="~@/assets/common/img/promotion/holiyday/post_bg.png" alt="" />
            </div>
            <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_infor.jpg" alt="" />
            <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_mo_infor.jpg" alt="">
            <div class="buttons_container">
                <div class="button" @click="getUrlBtn()">
                    <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/urlButton.png" alt="" />
                    <img v-else src="~@/assets/common/img/promotion/holiyday/urlButton_mo.png" alt="" />
                </div>
                <div class="button" @click="shareKakao()">
                    <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/kakaoButton.png" alt="" />
                    <img v-else src="~@/assets/common/img/promotion/holiyday/kakaoButton_mo.png" alt="" />
                </div>
            </div>
        </div>
        <div class="section section02" id="prom02">
            <img v-if="(!mobileCheck && !isFinish)" src="~@/assets/common/img/promotion/holiyday/holiyday_03.jpg" alt="" />
            <img v-else-if="(!mobileCheck && isFinish)" src="~@/assets/common/img/promotion/holiyday/holiyday_03_end.jpg" alt="" />
            <img v-else-if="(mobileCheck && !isFinish)" src="~@/assets/common/img/promotion/holiyday/holiyday_mo_03.jpg" alt="">
            <img v-else-if="(mobileCheck && isFinish)" src="~@/assets/common/img/promotion/holiyday/holiyday_mo_03_end.jpg" alt="">
        </div>
        <div class="section section03" id="prom03">
            <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_04_01.jpg" alt="" />
            <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_mo_04_01.jpg" alt="">
            <a href="https://www.dplot.co.kr/shop/promotion/welcome" :style="!mobileCheck ? 'margin-bottom: 100px;' : 'margin-bottom:50px;'">
                <!-- <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_03.jpg" alt="" />
                <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_mo_03.jpg" alt=""> -->
                <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/coupon_img.png" alt="" />
                <img v-else src="~@/assets/common/img/promotion/holiyday/coupon_img_mo.png" alt="">
            </a>
            <a href="https://www.dplot.co.kr/member/login">
                <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/joinButton.png" alt="" />
                <img v-else src="~@/assets/common/img/promotion/holiyday/joinButton_mo.png" alt="">
            </a>
        </div>
        <div class="section section04" id="prom04">
            <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_05_01.jpg" alt="" />
            <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_mo_05_01.jpg" alt="">
            <div>
                <a href="/shop/promotion/detail/79">
                    <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_05_a.png" alt="" />
                    <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_05_a_mo.png" alt="" />
                </a>
                <a href="/shop/promotion/detail/81">
                    <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_05_b.png" alt="" />
                    <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_05_b_mo.png" alt="" />
                </a>
                <a href="/shop/promotion/detail/80">
                    <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_05_c.png" alt="" />
                    <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_05_c_mo.png" alt="" />
                </a>
                <a href="/shop/promotion/detail/83">
                    <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_05_d.png" alt="" />
                    <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_05_d_mo.png" alt="" />
                </a>
            </div>
        </div>
        <div class="section section_notice">
            <img v-if="!mobileCheck" src="~@/assets/common/img/promotion/holiyday/holiyday_notice.jpg" alt="" />
            <img v-else src="~@/assets/common/img/promotion/holiyday/holiyday_mo_notice.jpg" alt="">
        </div>
        <input
            type="text"
            ref="urlinput"
            v-model="url"
            style="position: absolute; top: -9999em"
            />

        <!-- 프로모션 상품 영역    -->
        <section class="promotion__product__section" style="margin-top:50px;" v-if="!mobileCheck">
            <div class="promotion__product-area">
                <div class="container" style="max-width:100%;">
                    <ul class="list-style-none row" style="margin:0 -30px 1rem -30px">
                    <li
                        class="col-3"
                        v-for="(item, index) in goodsList"
                        :key="index"
                    >
                        <product :product-info="item" product_type="promotion" :isBedge="true"/>
                    </li>
                    </ul>
                </div>
            </div>
        </section>
        <section class="promotion__product__section" style="margin-top:50px;" v-else>
            <div class="promotion__product-area">
                <div class="container">
                    <ul class="list-style-none row">
                    <li
                        class="col-6"
                        v-for="(item, index) in goodsList"
                        :key="index"
                    >
                        <product :product-info="item" product_type="promotion" :isBedge="true"/>
                    </li>
                    </ul>
                </div>
            </div>
        </section>
    </div>
</template>

<script src="./Holiday.js"></script>

<style scoped lang="scss">
    .promotion_container {
        width:100%;
        max-width:1280px;
        margin: 0 auto;
        .section {
            img {width:100%;}
        }
        &.holiyday {
            .section01 {
                background:#911711;
                padding-bottom:150px;
    
                .stamp_wrap {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    position:relative;
                    & > img {width: auto;}
    
                    .stamp_item {
                        position: absolute;
                        cursor:pointer;
                        top: 0;
                        &:hover {filter:brightness(1.08);}
                        &:nth-of-type(1){ top:29px; left:9%; z-index: 4;}
                        &:nth-of-type(2){ top:16px; left:30%; z-index: 4;}
                        &:nth-of-type(3){ top:23px; left:52%; z-index: 4;}
                        &:nth-of-type(4){ top:26px; left:73%; z-index: 4;}
                        &:nth-of-type(5){ top:0; left:18%; z-index: 3;}
                        &:nth-of-type(6){ top:27px; left:41.5%; z-index: 3;}
                        &:nth-of-type(7){ top:5px; left:62%; z-index: 3;}
                    }
                }
    
                .buttons_container {
                    display: flex;
                    justify-content: center;
                    align-items: center;
    
                    .button {
                        cursor:pointer;
                        margin: 0 10px;
                        &:hover {filter:brightness(1.08);}
                    }
                }
            }
            .section03 {
                background:#f6d092;
                display: flex;
                flex-direction: column;
                padding-bottom: 180px;
                a {
                    margin: 0 auto;
                    img {width: auto;}
                }
            }
            .section04 {
                background:#11511f;
                display: flex;
                flex-direction: column;
                padding-bottom: 170px;
                > div {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    flex-wrap: wrap;
                    a {
                        margin:8.5px;
                        &:hover {filter:brightness(1.08);}
                        img {width: auto;}
                    }
                }
            }
        }
    }

    .tab_wrap {
        height: 0;
        position: relative;
        top: -86px;
        &.active {
            z-index: 930;
            top:80px;
            position:fixed;
            width: 100%;
            max-width:1280px;
            margin-top: 0;
            padding-top: 0;
        }
        ul {
            display: flex;
            justify-content: space-between;
            padding: 0;
            margin: 0;
            position: relative;
            width: 100%;
            z-index: 1;
            max-width:1280px;
            li {
                border: 1px solid #000;
                width: 100%;
                padding: 0;
                margin: 0;
                list-style: none;
                border-bottom: 2px solid #000;
                text-align: center;
                border-radius: 20px 20px 0 0;
                background: #e7c796;
                color:#000004;

                a {
                    font-size: 30px;
                    font-weight: 700;
                    cursor:pointer;
                    display: block;
                    width: 100%;
                    height: 100%;
                    padding: 25px 0 15px;
                    letter-spacing: -3px;
                    color:#000004;
                }

                &.active {
                    background: #921710;
                    color:#fff;
                    border-bottom: 0;
                    a {color:inherit;}
                }
                &:first-child.active { background: #921710; color:#fff;}
                &:nth-of-type(2).active { background: #053410; color:#fff;}
                &:nth-of-type(3).active { background: #f6d092; color:#013908;}
                &:nth-of-type(4).active { background: #11511f; color:#fff;}
            }
        }
    }
    .tab_container {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;
        z-index: 1;


        &::after{
            content:'';
            width: 100%;
            height: 2px;
            position: absolute;
            bottom: 0;
            background:#000;
            z-index: 0;
        }
    }

    .promotion__product__section .promotion__product-area li {margin-bottom: 60px;}

    @media (max-width: 719px) {
        .promotion__product__section .promotion__product-area li {margin-bottom: 0;}
        .product-list li {padding-bottom: 40px;}
        .promotion_container {
            
            &.holiyday {
                max-width:100%;
                & > img {
                    width:100%;
                }

                .section01 {
                    padding-bottom: 70px;

                    .stamp_wrap {
                        width: 100%;
                        & > img {
                            width: 90%;
                        }
    
                        .stamp_item {
                            width: 17%;
                            img { width: 100%; }
                            &:nth-of-type(1){ top:8px; left:7.5%; z-index: 4;}
                            &:nth-of-type(2){ top:8px; left:29%; z-index: 4;}
                            &:nth-of-type(3){ top:8px; left:53%; z-index: 4;}
                            &:nth-of-type(4){ top:8px; left:74.5%; z-index: 4;}
                            &:nth-of-type(5){ top:0; left:17%; z-index: 3;}
                            &:nth-of-type(6){ top:10px; left:41.5%; z-index: 3;}
                            &:nth-of-type(7){ top:2px; left:62.6%; z-index: 3;}
                        }
                        
                    }
    
                    .buttons_container {
                        width: 90%;
                        margin:0 auto;
                        .button {
                            margin: 0 5px;
                            img { width: 100%; }
                        }
                    }
                }

                .section03 {
                    a {
                        max-width:90%;
                        img {max-width:100%;}

                        & + a {
                            max-width:50%;
                        }
                    }
                    padding-bottom: 60px;
                }

                .section04 {
                    padding-bottom: 60px;

                    & > div {
                        max-width:90%;
                        margin: 0 auto;
                        
                        a {
                            max-width:48%;
                            margin: 3px;
                            img {max-width: 100%;}
                        }
                    }
                }
            }
        }
        .tab_wrap {
            ul li a {
                line-height: 1.1;
            }
            top: -43px;
            &.active {
                top:50px;
                max-width:100%;
            }
            ul {
                max-width:100%;
                li {
                    border-radius: 10px 10px 0 0;
                    a { font-size: 14px;
                        font-weight: 700;
                        padding: 10px 0;
                        letter-spacing: -1px;
                        word-break: keep-all;
                        color: #000004;}
                }
            }
        }
    }
</style>