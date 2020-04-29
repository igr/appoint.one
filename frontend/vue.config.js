// eslint-disable-next-line @typescript-eslint/no-var-requires
const path = require('path');

module.exports = {
  productionSourceMap: false,
  devServer: {
    contentBase: [
      path.join(process.cwd(), './public'),
    ],
    port: 3000,
    open: true,
    progress: false,
    https: false,
    proxy: {
      // change /api/login => /api/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      '^/api': {
        target: 'http://localhost:8080',
        changeOrigin: true, // needed for virtual hosted sites
        ws: true, // proxy websockets
        pathRewrite: {
          '/api': '',
        },
      },
    },
  },
  transpileDependencies: [
    'vuetify',
  ],
  pwa: {
    name: 'Podrška psihoterapeuta',
    themeColor: '#111111',
    msTileColor: '#000000',
    appleMobileWebAppCapable: 'yes',
    appleMobileWebAppStatusBarStyle: 'black',
  },
  chainWebpack: (config) => {
    config
      .plugin('html')
      .tap((args) => {
        args[0].title = 'Podrška psihoterapeuta';
        args[0].description = 'Besplatna psihoterapeutska pomoć';
        return args;
      });
  },
};
