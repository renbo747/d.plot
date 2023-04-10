const path = require('path');
const webpack = require('webpack')
const jQueryPath = 'jquery/dist/jquery.js';

module.exports = {
    css: {
        // Global SCSS Variables
        loaderOptions: {
            scss: {
            // 부트스트랩의 변수를 전역으로 사용하기 위해 선언
            additionalData: `
                @import "@/assets/common/scss/bootstrap/bootstrap.scss";
            `,
            },
        },
    },
    transpileDependencies: ['vue-daum-postcode', 'vue-toast-notification', 'vue-apple-login'],
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                jQuery: jQueryPath,
                $: jQueryPath,
                'window.jQuery': jQueryPath,
            }),
        ],
        resolve: {
            alias: {
                '@root': path.join(__dirname, 'src'),
                '@router': path.join(__dirname, 'src/router'),
                '@js': path.join(__dirname, 'src/js'),
                '@views': path.join(__dirname, 'src/views'),
                '@views.admin': path.join(__dirname, 'src/views/admin'),
                '@router.admin': path.join(__dirname, 'src/router/admin'),
                '@assets.common': path.join(__dirname, 'src/assets/common'),
                '@assets.admin': path.join(__dirname, 'src/assets'),
            }
        },
        entry: ['babel-polyfill', './src/main.js'],

    },
    devServer: {
        port: 80,
        //port: 83,
        //https: true,
        historyApiFallback: true,
        disableHostCheck: true,
        overlay: false,
        proxy: {
            // 프록시 요청을 보낼 api의 시작 부분
            '/api': {
                // 프록시 요청을 보낼 서버의 주소
                target: process.env.VUE_APP_SERVER_URL
            }
        }
    }
}