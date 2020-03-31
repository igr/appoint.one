module.exports = {
  productionSourceMap: false,
  devServer: {
    port: 3000,
    open: true,
    progress: false,
    proxy: {
      // change /api/login => /api/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      ['^/api']: {
        target: 'http://localhost:8080',
        changeOrigin: true, // needed for virtual hosted sites
        ws: true, // proxy websockets
        pathRewrite: {
          '/api': '/'
        },
      },
    },
  },
  transpileDependencies: [
    'vuetify',
  ],
};
