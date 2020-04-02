'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
  dev: {

    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/api': {
        target: 'http://101ycy.com:10001',
        //target: 'http://localhost:8070',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/'
        }
      },
      '/test': {
        // target: 'http://101ycy.com:10001',
        target: 'http://localhost:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/test': '/',
        }
      },
        '/user': {
          // target: 'http://101ycy.com:10001',
          target: 'http://192.168.247.1:8085',
          changeOrigin: true,
          pathRewrite: {
            '^/user': '/user',
          }
      },
      '/role': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/role': '/role',
        }
      },
      '/resource': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/resource': '/resource',
        }
      },
      '/authorization': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/authorization': '/authorization',
        }
      },
      '/permission': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/permission': '/permission',
        }
      },
      '/project': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/project': '/project',
        }
      },
      '/projectGroup': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/projectGroup': '/projectGroup',
        }
      },
      '/apiDocument': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/apiDocument': '/apiDocument',
        }
      },
      '/message': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/message': '/message',
        }
      },
      '/solr': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/solr': '/solr',
        }
      },
      '/group': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/group': '/group',
        }
      },
      '/interface': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/interface': '/',
        }
      },
      '/projectApi': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/projectApi': '/projectApi',
        }
      },
      '/picture/user': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/picture/user': '/picture/user',
        }
      },
      '/picture/project': {
        // target: 'http://101ycy.com:10001',
        target: 'http://192.168.247.1:8085',
        changeOrigin: true,
        pathRewrite: {
          '^/picture/project': '/picture/project',
        }
      }
    },

    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 6002, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../dist/index.html'),

    // Paths
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',

    /**
     * Source Maps
     */

    productionSourceMap: false,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}