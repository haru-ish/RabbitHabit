<template>
  <div class="home">
    <div v-if="loading" class="spinner-border text-info" role="status">
      <span class="sr-only">Loading...</span>
    </div>
    <div v-else class="text-center">
      <b-navbar toggleable="sm" type="dark" class="custom-color">
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-button variant="outline-light" size="sm" class="mb-2" type="submit" @click="signOut">
              Log Out
            </b-button>
          </b-navbar-nav>
          <b-navbar-nav class="collapse navbar-collapse justify-content-center">
            <b-nav-text>Hello haru-ish</b-nav-text>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <div class="calendar-parent">
        <calendar-view
          :items="items"
          :show-date="showDate"
          :starting-day-of-week="startingDayOfWeek"
          @click-date="onClickDate"
          class="theme-default"
        >
          <calendar-view-header
            slot="header"
            slot-scope="t"
            :header-props="t.headerProps"
            @input="setShowDate" />
        </calendar-view>
          <InputModal
            ref="input"
            v-show="showInputModal"
            :selectedDate='selectedDate'
            :showSelectedDate='showSelectedDate'
            :emojiMap='emojiMap'
            :writeLog='writeLog'
            @add="addLog"
            @update="updateLog"
          ></InputModal>
          <DetailModal
            ref="detail"
            v-show="showDetailModal"
            :selectedDate='selectedDate'
            :showSelectedDate='showSelectedDate'
            :emojiMap="emojiMap"
            :logsOfTheDay='logsOfTheDay'
            @delete="deleteLog"
            @edit="editLog"
          ></DetailModal>
      </div>
    </div>
  </div>
</template>

<script>
import firebase from 'firebase'
import axios from 'axios'

import InputModal from '../components/InputModal.vue'
import DetailModal from '../components/DetailModal.vue'

import { CalendarView, CalendarViewHeader } from 'vue-simple-calendar'
require('vue-simple-calendar/static/css/default.css')

export default {
  name: 'Home',
  data () {
    return {
      showInputModal: false,
      showDetailModal: false,
      displayName: '',
      loading: true,
      showDate: new Date(),
      startingDayOfWeek: 1,
      selectedDate: '',
      showSelectedDate: '',
      emojiMap: new Map([
        [1, '😀'],
        [2, '🙂'],
        [3, '😐'],
        [4, '🙁'],
        [5, '😢']
      ]),
      // Use for Calendar function
      items: [],
      // From input-modal
      writeLog: {},
      // From DB, All Data of Logs
      allLogs: '',
      // From DB, Data only for the day
      logsOfTheDay: ''
    }
  },
  components: {
    CalendarView,
    CalendarViewHeader,
    InputModal,
    DetailModal
  },
  created () {
    let parent = this
    // Check Login User
    firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
        user.getIdToken().then(function (idToken) {
          parent.$store.dispatch('saveToken', idToken)
          const headers = {
            headers: {
              'Authorization': 'Bearer ' + parent.$store.getters.getToken
            }
          }
          axios.get('http://localhost:8080/api/checkLoginUser', headers).then(res => {
            parent.loading = false
            parent.displayName = user.displayName
          })
        })
          .catch(e => {
            console.log(e)
            alert('ERROR: Login required')
            parent.$router.push('/login')
          })
      } else {
        parent.$router.push('/login')
      }
    })
    // Get all of data from DB
    this.$watch('displayName', () => {
      this.getAllLog()
    })
  },
  methods: {
    // Logout
    signOut (e) {
      this.$store.dispatch('removeToken')
      e.stopPropagation()
      firebase.auth().signOut()
      this.$router.push({
        name: 'Login'})
    },
    setShowDate (d) {
      this.showDate = d
    },
    onClickDate (d, calendarItems) {
      this.selectedDate = d
      var options = { year: 'numeric', month: '2-digit', day: '2-digit' }
      this.showSelectedDate = d.toLocaleString(undefined, options)
      // Show detail-modal
      if (calendarItems.length !== 0) {
        this.setLogOfTheDay(d)
        this.showDetailModal = true
        this.$refs.detail.showModal()
        return true
      }
      // Show input-modal
      // Initialize
      this.writeLog = {mood: 0, selectedOne: 0, selectedTwo: 0, selectedThree: 0, memo: '', errorMsg: ''}
      this.showInputModal = true
      this.$refs.input.showModal()
    },
    // From detail-modal
    editLog (log) {
      this.writeLog = log
      this.$refs.input.showModal()
      this.showInputModal = true
    },
    // From detail-modal
    deleteLog () {
      const index = this.items.findIndex(obj => {
        return obj.startDate === this.logsOfTheDay.date
      })
      this.items.splice(index, 1)
    },
    // From input-modal
    addLog (log) {
      log.date = this.formatDate(log.date)
      // Push into logs
      this.items.push({
        startDate: log.date,
        title: this.emojiMap.get(log.mood),
        id:
        'e' +
        Math.random()
          .toString(36)
          .substr(2, 10)
      })
      this.allLogs.push(log)
    },
    // From input-modal
    updateLog (log) {
      log.date = this.formatDate(log.date)
      // Modify title of the log in logs
      this.items.some((object) => {
        if (object.startDate.startsWith(this.formatDate(log.date))) {
          object.title = this.emojiMap.get(log.mood)
          console.log(object.startDate)
          return true
        }
      })
    },
    // From DB, Get All Data of Logs
    getAllLog: function () {
      axios.get('http://localhost:8080/api/getAllLog', {
        headers: {'Authorization': 'Bearer ' + this.$store.getters.getToken}
      }).then(res => {
        this.allLogs = res.data
        this.items = this.allLogs.map((obj, index) => ({
          id: index + 1,
          startDate: obj.date,
          title: this.emojiMap.get(obj.mood)
        }))
      })
    },
    // Get one log from allLogs by date
    setLogOfTheDay: function (d) {
      let day = this.formatDate(d)
      this.allLogs.some((object) => {
        if (object.date.startsWith(day)) {
          this.logsOfTheDay = object
          return true
        }
      })
    },
    // Change Date Format to yyyy-mm-dd
    formatDate: function (dt) {
      var y = dt.getFullYear()
      var m = ('00' + (dt.getMonth() + 1)).slice(-2)
      var d = ('00' + dt.getDate()).slice(-2)
      return (y + '-' + m + '-' + d)
    }
  }
}
</script>
