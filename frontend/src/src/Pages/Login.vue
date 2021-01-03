<template>
<div>
  <b-container fluid="">

    <b-row>
      <b-col></b-col>
      <b-col >

        <h3 class="text-center">Login</h3>

        <!-- Errors -->
        <b-alert class="text-center" v-model="this.hasErrors" variant="danger" dismissible>
          {{ this.error }}
        </b-alert>

        <!-- Login Form -->
        <UserForm
            button-text="Login"
            @submit="tryLogin"
        />

      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</div>
</template>

<script>

import api from "@/api";
import UserForm from "@/components/Forms/UserForm";
export default {
  name: "Login",
  components: {
    UserForm
  },

  // Data (variables)
  data() {
      return {
        usernameField: "admin",
        passwordField: "password",
        token: "",
        hasErrors: false,
        error: ""
      }
  },

  // Page States
  beforeMount() {
    this.token = localStorage.getItem("token")
  },

  mounted() {
    this.token = localStorage.getItem("token")
  },

  // Functions
  methods: {
    tryLogin(data) {
      this.usernameField = data[0]
      this.passwordField = data[1]

      const axios = require('axios');
      const config = {
        method: 'post',
        url: api.LOGIN,
        headers: {
          'Content-Type': 'application/json'
        },
        data: JSON.stringify({
          "username": this.usernameField,
          "password": this.passwordField
        })
      };

      axios(config)
          .then(response => {
            this.token = response.data["token"]
            console.log(this.token)
            localStorage.setItem("token", this.token)
          })
          .catch(error => {
            this.hasErrors = true
            this.error = error.response?.data["message"]
            localStorage.setItem("errors",this.error)
          });
    }
  }


}
</script>

<style scoped>
  .error {
    color: red;
    text-align-all: center;
    border-radius: 5px;
    border-color: firebrick;
    border-style: solid;
    border-width: 3px 3px;
  }
</style>