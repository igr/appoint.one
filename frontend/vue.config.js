// eslint-disable-next-line @typescript-eslint/no-var-requires
const path = require('path');

module.exports = {
  productionSourceMap: false,
  devServer: {
    contentBase: [
      path.join(process.cwd(), './public'),
      path.join(process.cwd(), './docs'),
    ],
    port: 3000,
    open: true,
    progress: false,
    proxy: {
      // change /api/login => /api/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      '^/api': {
        target: 'http://localhost:8080',
        changeOrigin: true, // needed for virtual hosted sites
        ws: true, // proxy websockets
        pathRewrite: {
          '/api': '/',
        },
      },
    },
  },
  transpileDependencies: [
    'vuetify',
  ],
  chainWebpack: (config) => {
    config
      .plugin('html')
      .tap((args) => {
        args[0].title = 'Podrška psihoterapeuta';
        return args;
      });
  },
};
