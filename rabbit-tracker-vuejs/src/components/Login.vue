<template>
  <div class="login">
    <div class="text-center">
      <div class="background">
      <img src="../assets/images/rabbit.svg" class="img-fluid" width="150" height="150" alt="Image" style="margin:50px 20px 10px;">
      <h1 style="color: rgb(9, 104, 37);">Rabbit Habit</h1>
      <p >We help build and keep positive <span style="color:#FF7F50; font-weight: 600;">Mood</span> and <span style="color:rgb(69 176 244); font-weight: 600;">Habits</span></p>
      <div class="style_1">
        <section id="firebaseui-auth-container"></section>
      </div>
      </div>
      <div class="style_2"></div>
      <div>
      <span style="font-size:16px; color:#DC143C;">ABOUT</span>
      <div class="style_3"></div>
      <h2 style="font-size: 1.6rem;">✍ <span class="rabbit" style="color: rgb(9, 104, 37);">Rabbit Habit </span>is...</h2>
      <div class="style_3"></div>
      <p>an app for recording daily mood and habits.</p>
      <p>Recording your mood on a daily basis can help you to understand your current mental state.</p>
      <p>And we will support you create habits that will keep you in good mental shape.</p>
      <p>Record whether you have done the good habits along with how you feel today!</p>
      <div class="style_2">
        <video playsinline autoplay loop muted>
          <source src="../assets/videos/RabbitHabit_input.mp4" type="video/mp4">
        </video>
      </div>
      <p>We are glad<span class="rabbit" style="color: rgb(9, 104, 37);"> Rabbit Habit </span>will be useful</p>
      <p>you take a moment to reflect on how you felt today and what you did to make yourself happy. 💚</p>
      <div class="style_2">
        <video playsinline autoplay loop muted>
          <source src="../assets/videos/RabbitHabit_detail.mp4" type="video/mp4">
        </video>
      </div>
      </div>
      <footer><div class="footer-item">©️ 2022 haru-ish
        <a class="footer-item" href="https://github.com/taka61/chiikoma"><b-icon-github></b-icon-github></a></div>
      </footer>
    </div>
  </div>
</template>

<script>
import firebase from 'firebase'
import * as firebaseui from 'firebaseui'
import 'firebaseui/dist/firebaseui.css'
import { BIcon, BIconGithub } from 'bootstrap-vue'

export default {
  name: 'Login',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  components: {
    BIcon,
    BIconGithub
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
