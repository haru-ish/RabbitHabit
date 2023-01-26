import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

const store = new Vuex.Store({
  state () {
    return {
      // This is JWT that used to identify the user to a Firebase service
      token: ''
    }
  },
  mutations: {
    saveToken (state, token) {
      state.token = token
    },
    removeToken (state) {
      state.token = ''
    }
  },
  actions: {
    saveToken ({commit}, token) {
      commit('saveToken', token)
    },
    removeToken ({commit}) {
      commit('removeToken')
    }
  },
  getters: {
    getToken (state) {
      return state.token
    }
  },
  plugins: [createPersistedState(
    {
      key: 'rabbitTracker',
      Storage: window.sessionStorage
    }
  )]
})

export default store
