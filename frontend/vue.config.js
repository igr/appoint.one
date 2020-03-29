module.exports = {
  productionSourceMap: false,
  devServer: {
    port: 3000,
    open: true,
    progress: false,
    proxy: {
      // change /api/login => /api/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      [`^${process.env.VUE_APP_BASE_API}`]: {
        target: 'http://localhost:8080',
        changeOrigin: true, // needed for virtual hosted sites
        ws: true, // proxy websockets
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: '/',
          '^/api': '/',
        },
      },
    },
  },
};
