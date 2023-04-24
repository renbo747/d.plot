import Vue from "vue";
import Logo from "@views.common/components/Logo";
import MagazineCard from "@views.common/components/main/MagazineCard";
import MagazineProduct from "@views.common/components/main/MagazineProduct";
import InstagramCard from "@views.common/components/main/InstagramCard";
import Product from "@views.common/components/shop/Product";
import Gift from "@views.common/components/shop/Gift";
import ShopCard from "@views.common/components/shop/ShopCard";
import BrandCard from "@views.common/components/shop/BrandCard";
import MdCard from "@views.common/components/shop/MdCard";
import BaseCheckbox from "@views.common/components/ui/BaseCheckbox";
import BaseRadio from "@views.common/components/ui/BaseRadio";
import BaseToggle from "@views.common/components/ui/BaseToggle";
import BaseInput from "@views.common/components/ui/BaseInput";
import BaseInputHorizon from "@views.common/components/ui/BaseInputHorizon";
import BaseTextarea from "@views.common/components/ui/BaseTextarea";
import BaseSelect from "@views.common/components/ui/BaseSelect";
import BaseLike from "@views.common/components/ui/BaseLike";
import BaseWish from "@views.common/components/shop/BaseWish";
import MasonryProduct from "@views.common/components/shop/MasonryProduct";
import ProductThumbnail from "@views.common/components/shop/ProductThumbnail";
import ProductCart from "@views.common/components/shop/ProductCart";
import ProductOrder from "@views.common/components/shop/ProductOrder";
import ProductInquiry from "@views.common/components/shop/ProductInquiry";
import OptionItem from "@views.common/components/shop/OptionItem";
import BasePagination from "@views.common/components/ui/BasePagination";
import BaseDropdown from "@views.common/components/ui/BaseDropdown";
import OrderCoupon from "@views.common/components/order/OrderCoupon";
import BaseProgress from "@views.common/components/ui/BaseProgress";
import BaseSearch from "@views.common/components/ui/BaseSearch";
import SearchRelation from "@views.common/components/ui/SearchRelation";
import MainPopup from "@views.common/components/main/MainPopup";
import EventBanner from "@views.common/components/shop/EventBanner";
import ReviewThumbnail from "@views.common/components/reviewThumbnail";

import {
  ValidationObserver,
  ValidationProvider,
  extend,
  localize,
} from "vee-validate";
import ko from "vee-validate/dist/locale/ko.json";
import * as rules from "vee-validate/dist/rules";
import BaseCounter from "@views.common/components/ui/BaseCounter";
import ImageUpload from "@views.common/components/ImageUpload";
import VideoThumbnail from "@views.common/components/VideoThumbnail";
import Recopick from "@views.common/components/Recopick";

import TermsContents from "@views.common/components/ui/modal/TermsContents";
import SnsContents from "@views.common/components/ui/modal/SnsContents";
import ShippingList from "@views.common/components/order/ShippingList";


// 토스트 모달
import VueToast from "vue-toast-notification";
Vue.use(VueToast, {
  position: "bottom",
  queue:false
});

// install rules and localization
Object.keys(rules).forEach((rule) => {
  extend(rule, rules[rule]);
});

localize("ko", ko);

// Install components globally
Vue.component("validation-observer", ValidationObserver);
Vue.component("validation-provider", ValidationProvider);

Vue.component("logo", Logo);
Vue.component("magazine-card", MagazineCard);
Vue.component("magazine-product", MagazineProduct);
Vue.component("instagram-card", InstagramCard);
Vue.component("product", Product);
Vue.component("gift", Gift);
Vue.component("shop-card", ShopCard);
Vue.component("brand-card", BrandCard);
Vue.component("md-card", MdCard);
Vue.component("base-checkbox", BaseCheckbox);
Vue.component("base-radio", BaseRadio);
Vue.component("base-toggle", BaseToggle);
Vue.component("base-input", BaseInput);
Vue.component("base-input-horizon", BaseInputHorizon);
Vue.component("base-textarea", BaseTextarea);
Vue.component("base-select", BaseSelect);
Vue.component("base-counter", BaseCounter);
Vue.component("base-like", BaseLike);
Vue.component("base-wish", BaseWish);
Vue.component("masonry-product", MasonryProduct);
Vue.component("product-thumbnail", ProductThumbnail);
Vue.component("product-cart", ProductCart);
Vue.component("product-order", ProductOrder);
Vue.component("product-inquiry", ProductInquiry);
Vue.component("option-item", OptionItem);
Vue.component("base-pagination", BasePagination);
Vue.component("base-dropdown", BaseDropdown);
Vue.component("image-upload", ImageUpload);
Vue.component("video-thumbnail", VideoThumbnail);
Vue.component("recopick", Recopick);

Vue.component("order-coupon", OrderCoupon);
Vue.component("base-progress", BaseProgress);
Vue.component("base-search", BaseSearch);
Vue.component("search-relation", SearchRelation);
Vue.component("event-banner", EventBanner);
// 공용 모달 컨텐츠
Vue.component("terms-contents", TermsContents);
Vue.component("sns-contents", SnsContents);
Vue.component("shipping-list", ShippingList);
// 메인팝업
Vue.component("main-popup", MainPopup);
Vue.component("review-thumbnail", ReviewThumbnail);