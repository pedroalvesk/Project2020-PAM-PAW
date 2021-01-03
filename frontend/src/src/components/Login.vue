<template>
  <b-container>
    <b-row>
      <b-col></b-col>
    <b-col >
      <div class="login-layout text-left">
        <b-form @submit="tryLogin">

          <b-alert variant="success">Success Login</b-alert>
          <!-- Errors -->
          <b-alert class="text-center" v-model="this.hasErrors" variant="danger" dismissible>
            {{ this.error }}
          </b-alert>

          <!-- Username -->
          <b-form-group id="input-group-1" label="Username:" label-for="input-1">
            <b-form-input id="input-1" v-model="usernameField" type="text" required></b-form-input>
          </b-form-group>

          <!-- Password -->
          <b-form-group id="input-group-2" label="Password:" label-for="input-2">
            <b-form-input id="input-2" v-model="passwordField" type="password" required></b-form-input>
          </b-form-group>

          <!-- Button -->
          <b-button type="submit" variant="outline-primary">Submit</b-button>
        </b-form>

        </div>
    </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>

import api from "@/api";

export default {
  name: "Login",

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
    tryLogin(event) {
      event.preventDefault()

      const axios = require('axios');
      const config = {
        method: 'post',
        url: api.LOGIN,
        headers: {
          'Content-Type': 'application/json'
        },
        data: JSON.stringify({
          "username": "admin",
          "password": "password"
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