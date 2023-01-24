<template>
  <div class="home">
    <div class="text-center">
      <b-modal
        header-class="custom-color"
        ref="detail-modal"
        hide-footer
        :title="showSelectedDate"
        header-close-variant="white"
        :no-close-on-backdrop=true
      >
        <div class="d-block text-center">
          <div class="style_2"></div>
          <div class="rcorners1">💚 Your Mood was ...  <span class="emphasis">{{ emojiMap.get(logsOfTheDay.mood) }}</span></div>
          <div class="style_2"></div>
          <div class="rcorners2">💻 Did programming ... <span class="emphasis">{{ answerMap.get(logsOfTheDay.selectedOne) }}</span></div>
          <div class="style_2"></div>
          <div class="rcorners3">🇬🇧 Had English lessons ... <span class="emphasis">{{ answerMap.get(logsOfTheDay.selectedTwo) }}</span></div>
          <div class="style_2"></div>
          <div class="rcorners4">😴 Took a nap ... <span class="emphasis">{{ answerMap.get(logsOfTheDay.selectedThree) }}</span></div>
          <div class="style_2"></div>
          <div class="rcorners5">✍ Memo ... <span style="color:#212529;">{{ logsOfTheDay.memo }}</span></div>
          <div class="style_2"></div>
        </div>
         <footer id="__BVID__47___BV_modal_footer_" class="modal-footer">
          <button type="button" class="btn btn-primary" @click="deleteLog">Delete</button>
          <button type="button" class="btn btn-info" @click="editLog">Edit</button>
        </footer>
      </b-modal>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

import { CalendarView, CalendarViewHeader } from 'vue-simple-calendar'
require('vue-simple-calendar/static/css/default.css')

export default {
  name: 'DetailModal',
  data () {
    return {
      answerMap: new Map([
        [0, 'No'],
        [1, 'Yes']
      ])
    }
  },
  props: {
    showSelectedDate: String,
    selectedDate: Date,
    emojiMap: Map,
    logsOfTheDay: Object
  },
  components: {
    CalendarView,
    CalendarViewHeader
  },
  methods: {
    showModal () {
      this.$refs['detail-modal'].show()
    },
    editLog () {
      // Hide the modal
      this.$refs['detail-modal'].hide()
      // Back to Home.vue
      this.$emit('edit', this.logsOfTheDay)
    },
    deleteLog () {
      // Delete the log in DB
      const d = new URLSearchParams()
      d.append('id', this.logsOfTheDay.id)
      axios.post('/api/delete', d, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getToken
        }
      }).then(res => {
        // Hide the modal
        this.$refs['detail-modal'].hide()
        // Back to Home.vue
        this.$emit('delete')
      })
    }
  }
}
</script>
