<template>
  <div>
    <b-container fluid="true">
          <b-card class="card col-xl-4 col-lg-5 col-md-6 col-sm-auto mx-auto text-center">

            <!-- Title -->
            <h3><strong>Login</strong></h3>

            <!-- Errors -->
            <b-alert v-if="this.hasErrors" v-model="this.hasErrors" variant="danger" dismissible>
              {{ this.error }}
            </b-alert>

            <!-- Login Form -->
            <UserForm
                button-text="Login"
                :editable="true"
                event-name="login"
                @login="tryLogin"
            />
          </b-card>
    </b-container>
  </div>
</template>

<script>
import API from "@/API";
import UserForm from "@/components/forms/UserForm";

export default {
  name: "Login",
  components: {
    UserForm,
  },

  // Variables
  data() {
    return {
      hasErrors: false,
      error: null,
    }
  },

  // Events
  created() {
    document.title = "PAM&PAW | Login Page";
  },

  // Functions
  methods: {
    tryLogin(data){
      API.login(data.username, data.password, this.successLogin, this.failedLogin);
    },

    successLogin(response){
      /*
      const date = new Date();
      date.setMinutes(date.getMinutes() + 15);
      localStorage.setItem("expiration-time", date.getTime().toString());
      */

      localStorage.setItem("token", response.data["token"]);
      localStorage.removeItem("error")
      this.$router.replace({name: "Users"});
    },

    failedLogin(error){
      this.hasErrors = true;
      this.error = error.response?.data["message"] || "(connection refused)";
      localStorage.setItem("error", this.error)
    }
  }
}
</script>



<style scoped>
  .card {
    border-width: 3px;
  }
</style>