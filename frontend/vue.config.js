const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  // devServer: {
  //   disableHostCheck: true
  // },
  transpileDependencies: [
    'vuetify'
  ]
})
module.exports = {
  devServer: {
    allowedHosts: "all",
  },
};