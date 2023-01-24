import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

/* Bootstrap */
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import firebaseConfig from './firebaseConfig'

import firebase from 'firebase'

// Initialize Firebase
firebase.initializeApp(firebaseConfig)

Vue.use(BootstrapVue)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
