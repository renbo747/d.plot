const path = require('path');
const { env } = require('process');
const webpack = require('webpack')
const jQueryPath = 'jquery/dist/jquery.js';
//const fs = require('fs')

module.exports = {
    productionSourceMap : false,
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
    filenameHashing: true,
    configureWebpack: {
        mode: process.env.NODE_ENV == 'prod' ? 'production' : 'development',
        plugins: [
            new webpack.ProvidePlugin({
                jQuery: jQueryPath,
                $: jQueryPath,
                'window.jQuery': jQueryPath,
            }),
           // new VueLoaderPlugin()
        ],
        output: {
            //[hash]"모든 빌드에 대해 생성된 고유 해시"입니다.
            //[chunkhash]"각 청크의 내용을 기반으로"
            //[contenthash]"추출된 콘텐츠에 대해 생성됨"
            
            //운영에서 수정한 파일판 파일명 변경하고 싶을 경우 처리
            filename :  process.env.NODE_ENV == 'prod'? "js/[name].[chunkhash].js" : "js/[name]_[hash].js" ,
            chunkFilename : process.env.NODE_ENV == 'prod' ? "js/[name].[chunkhash].js" : "js/[name]_[hash].js"
            // filename :  process.env.production ? "js/[name].[chunkhash].js" : "js/[name]_[hash].js" ,
            // chunkFilename : process.env.production ? "js/[name].[chunkhash].js" : "js/[name]_[hash].js"

 
            // filename :  "js/[name].[hash].js" ,
            // chunkFilename :  "js/[name].[hash].js"
        },
        resolve: {
            alias: {
                '@root': path.join(__dirname, 'src'),
                '@router': path.join(__dirname, 'src/router'),
                '@js': path.join(__dirname, 'src/js'),
                '@views': path.join(__dirname, 'src/views'),
                '@views.front': path.join(__dirname, 'src/views/front'),
                '@views.common': path.join(__dirname, "src/views/front/common"),
                '@views.mobile': path.join(__dirname, 'src/views/front/mobile'),
                '@views.pc': path.join(__dirname, 'src/views/front/pc'),
                '@router.mobile': path.join(__dirname, 'src/router/front/mobile'),
                '@router.pc': path.join(__dirname, 'src/router/front/pc'),
                '@assets.common': path.join(__dirname, 'src/assets/common'),
                '@assets.mobile': path.join(__dirname, 'src/assets/mobile'),
                '@assets.pc': path.join(__dirname, 'src/assets/pc'),
                '@assets.admin': path.join(__dirname, 'src/assets'),
            }
        },
        entry: ['babel-polyfill', './src/main.js'],

    },
    devServer: {
        port: 80,
        //port: 83,
        //port: 443,
        // https: {
        //     key: fs.readFileSync('d:/mobilefactory.key'),
        //     cert: fs.readFileSync('d:/mobilefactory.crt'),
        //     ca: fs.readFileSync('d:/rootca.crt'),
        // },
        historyApiFallback: true,
        disableHostCheck: true,
        overlay: false,
        proxy: {
            '/api/v1/nid/me' : {
                target: 'https://openapi.naver.com',
                pathRewrite:{
                    '^/api' : ''
                }
            },
            // 프록시 요청을 보낼 api의 시작 부분
            '/api/search' : {
                //target: 'http://3.35.165.108:7800',
                target: process.env.VUE_APP_SEARCH_URL,
                pathRewrite:{
                    '^/api' : ''
                }
            },
            '/api': {
                // 프록시 요청을 보낼 서버의 주소
                target: process.env.VUE_APP_SERVER_URL
            },
        }
    },
}
