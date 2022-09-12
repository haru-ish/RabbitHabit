<template>
  <div class="login">
    <div class="text-center">
      <img src="../assets/images/rabbit.svg" class="img-fluid" width="150" height="150" alt="Image" style="margin:20px 20px 20px;">
      <h1>Rabbit Habit</h1>
      <p >We help build and keep positive <span class="font" style="color:#FF7F50;">Mood</span> and <span class="font" style="color:rgb(69 176 244);">Habits</span></p>
      <div class="style_1">
        <section id="firebaseui-auth-container"></section>
      </div>
      <div class="style_2"></div>
      <span>ABOUT</span>
      <h2>What is Rabbit Habit?</h2>
      <p>The habits we make affect not only our physical health but also our mental health.</p>
      <p>Would you like to create good habits and be aware of your daily mental health?</p>
      <p>This app keeps track of your daily habits and moods.</p>
      <img src="../assets/images/calendar.png" class="img-fluid" width="150" height="150" alt="Image" style="margin:20px 20px 20px;">

    </div>
  </div>
</template>

<script>
import firebase from 'firebase'
import * as firebaseui from 'firebaseui'
import 'firebaseui/dist/firebaseui.css'

export default {
  name: 'Login',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  mounted () {
    var uiConfig = {
      signInSuccessUrl: '/home',
      tosUrl: '/tos',
      privacyPolicyUrl: '/privatePolicy',
      signInOptions: [firebase.auth.GoogleAuthProvider.PROVIDER_ID]
      // signInFlow: 'popup'
    }
    let ui = firebaseui.auth.AuthUI.getInstance()
    if (!ui) {
      ui = new firebaseui.auth.AuthUI(firebase.auth())
    }
    ui.start('#firebaseui-auth-container', uiConfig)
  }
}
</script>
