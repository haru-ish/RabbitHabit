<template>
  <div class="home">
    <b-modal
      header-class="custom-color"
      ref="input-modal"
      hide-footer
      :title="showSelectedDate"
      header-close-variant="white"
      :no-close-on-backdrop=true
    >
      <div class="d-block text-center">
      <div style="color:red;" class="style_3">{{ errorMsg }}</div>
      <b-form-group label="💚 What was your mood?" v-slot="{ ariaDescribedby }">
        <b-form-radio-group
          id="radio-group-0"
          v-model="writeLog.mood"
          :options="optionsMood"
          :aria-describedby="ariaDescribedby"
          buttons
          button-variant="success"
          size="lg"
          name="buttons-0"
        ></b-form-radio-group>
      </b-form-group>
      <img src="../assets/images/line.svg">
      <b-form-group label="💻 Did you do programming?" v-slot="{ ariaDescribedby }" class="style_2">
        <b-form-radio-group
          id="radio-group-1"
          v-model="writeLog.selectedOne"
          :options="optionsYesNo"
          :aria-describedby="ariaDescribedby"
          size="lg"
          name="radio-options_1"
        ></b-form-radio-group>
      </b-form-group>
      <img src="../assets/images/line.svg">
      <b-form-group label="🇬🇧 Did you take English lessons?" v-slot="{ ariaDescribedby }" class="style_2">
        <b-form-radio-group
          id="radio-group-2"
          v-model="writeLog.selectedTwo"
          :options="optionsYesNo"
          :aria-describedby="ariaDescribedby"
          size="lg"
          name="radio-options_2"
        ></b-form-radio-group>
      </b-form-group>
      <img src="../assets/images/line.svg">
      <b-form-group label="😴 Did you have a nap?" v-slot="{ ariaDescribedby }" class="style_2">
        <b-form-radio-group
          id="radio-group-3"
          v-model="writeLog.selectedThree"
          :options="optionsYesNo"
          :aria-describedby="ariaDescribedby"
          size="lg"
          name="radio-options_3"
          false
        ></b-form-radio-group>
      </b-form-group>
      <img src="../assets/images/line.svg">
      <div class="style_2">
      <div style="color:red; text-align: center;" v-if="v$.writeLog.memo.maxLength.$invalid">Enter up to 100 characters.</div>
      ✍ Memo
      <b-form-input v-model="writeLog.memo" size="sm"></b-form-input>
      </div>
      <b-button :disabled="v$.$invalid" block pill variant="success" class="style_2" @click="clickAddLog">Send</b-button>
      </div>
    </b-modal>
  </div>
</template>

<script>
import useVuelidate from '@vuelidate/core'
import { maxLength } from '@vuelidate/validators'
import axios from 'axios'

import { CalendarView, CalendarViewHeader } from 'vue-simple-calendar'
require('vue-simple-calendar/static/css/default.css')

export default {
  name: 'InputModal',
  setup () {
    return { v$: useVuelidate() }
  },
  data () {
    return {
      errorMsg: '',
      optionsMood: [
        { text: '😀', value: 1 },
        { text: '🙂', value: 2 },
        { text: '😐', value: 3 },
        { text: '🙁', value: 4 },
        { text: '😢', value: 5 }
      ],
      optionsYesNo: [
        { text: 'Yes', value: 1 },
        { text: 'No', value: 0 }
      ]
    }
  },
  props: {
    showSelectedDate: String,
    selectedDate: Date,
    writeLog: Object,
    emojiMap: Map
  },
  components: {
    CalendarView,
    CalendarViewHeader
  },
  validations () {
    return {
      writeLog: {
        memo: { maxLength: maxLength(100) }
      }
    }
  },
  methods: {
    showModal () {
      this.$refs['input-modal'].show()
    },
    clickAddLog () {
      // Validation check
      if (this.writeLog.mood === 0) {
        this.errorMsg = 'Choose your mood today.'
        return true
      }
      if (this.writeLog.memo.length > 100) {
        this.errorMsg = 'Enter up to 100 characters.'
        return true
      }
      // Modify for DB
      this.writeLog.date = new Date(this.selectedDate - this.selectedDate.getTimezoneOffset() * 60000)

      const d = new URLSearchParams()
      Object.keys(this.writeLog).forEach(key => {
        d.append(key, this.writeLog[key])
      })
      // Insert a new log in DB
      if (this.writeLog.id === undefined) {
        axios.post('http://localhost:8080/api/create', d, {
          headers: {
            'Authorization': 'Bearer ' + this.$store.getters.getToken
          }
        }).then(res => {
          // Result for validation errors from back-end
          if (res.data === 0) {
            this.errorMsg = 'There is an input error.'
            return true
          }
          // Auto-generated value as id from DB
          this.writeLog.id = res.data
        })
        // Back to Home.vue
        this.$emit('add', this.writeLog)
        // Hide the modal
        this.$refs['input-modal'].hide()
        return true
      }
      // Update the log
      axios.post('http://localhost:8080/api/edit', d, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getToken
        }
      }).then(res => {
        // Result for validation errors from back-end
        if (!res.data) {
          this.errorMsg = 'There is an input error.'
          return true
        }
      })
      // Back to Home.vue
      this.$emit('update', this.writeLog)
      // Hide the modal
      this.$refs['input-modal'].hide()
    }
  }
}
</script>
