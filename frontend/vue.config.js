module.exports = {
  productionSourceMap: false,
  devServer: {
    port: 3000,
    open: true,
    progress: false,
    proxy: {
      [process.env.VUE_APP_BASE_API]: {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: true,
      },
    },
  },
};
