<template>
    <li @click="goLink">
        <div class="product_img_box">
            <div class="rank_number atten-new">{{productInfo.index+1}}</div>
            <img :src="productInfo.fullpath" alt="">
        </div>
        <div class="product_text_box">
            <span class="brand_logo atten-new" v-if="productInfo.brandname">{{productInfo.brandname}}</span>
            <p class="product_name">{{productInfo.goodsname}}</p>
            <div class="product_price">
            <span class="price vollkorn">{{$util.maskComma(productInfo.saleamt - productInfo.goodscpnamt)}}</span>원
            <span class="sale_percent vollkorn" v-if="$util.saleRate(productInfo.marketprice, productInfo.saleamt - productInfo.goodscpnamt) > 0">{{$util.saleRate(productInfo.marketprice, productInfo.saleamt - productInfo.goodscpnamt)}}%</span>
            </div>
            <div class="origin_price" v-if="(productInfo.saleamt - productInfo.goodscpnamt) != productInfo.marketprice">
            <span class="vollkorn">{{$util.maskComma(productInfo.marketprice)}}</span>원
            </div>
            <i
                class="product__like"
                :class="{ on: product.iswished == 'T' }"
                @click.stop="changeWish()"
            ></i>
        </div>
    </li>
</template>

<script>
    export default {
        props: {
            isHorizontal: {
                type: Boolean,
                default: false,
            },
            productInfo: {
                type: Object,
            },
            height: {
                type: Number,
                default: 0,
            },
            isCart: {
                type: Boolean,
                default: false,
            },
        },
        data() {
            return {
                isLike: false,
                product: {
                    goodsno: 0,
                },
            }
        },
        mounted() {
            this.product = this.productInfo;
        },
        methods: {
            /**********************
             * 좋아요 버튼 처리
             ********************/
            changeWish() {
                this.$front.goodsChangeWish(this, this.product);
            },
            goLink() {
                if (
                    this.product.goodsselltype == "GST003" ||
                    this.product.goodsselltype == "GST006"
                ) {
                    this.$eventBus.$emit("alert", "알림", "판매종료 상품입니다.");
                } else {
                    this.$router.push({name:'shop-detail', params:{pid:this.productInfo.goodscode}});
                }
            },
        },
        watch: {
            productInfo() {
                this.product = this.productInfo;
            },
        },
    }
</script>