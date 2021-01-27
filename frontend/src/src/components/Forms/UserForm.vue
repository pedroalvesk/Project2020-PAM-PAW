<template>
  <div class="login-layout text-left">
    <b-form @submit="submitForm">

      <!-- Username -->
      <b-form-group id="input-group-1" label="Username:" label-for="input-1">
        <b-form-input id="input-1" v-model="usernameField" type="text" required></b-form-input>
      </b-form-group>

      <!-- Password -->
      <b-form-group id="input-group-2" label="Password:" label-for="input-2">
        <b-form-input id="input-2" v-model="passwordField" type="password" required></b-form-input> <!--  v-b-toggle:disabled="this.$props.editable"  -->
      </b-form-group>

      <!-- Button -->
      <b-button type="submit" variant="outline-primary">{{this.$props.buttonText}}</b-button>
    </b-form>

  </div>
</template>

<script>
export default {
  name: "UserForm",

  // Arguments
  props: {
    editable: Boolean,
    eventName: String,
    buttonText: String,
    user: {
      username: String,
      password: String,
    }
  },

  // States
  mounted() {
    // this.usernameField = this.$props.user.username | "";
    // this.passwordField = this.$props.user.password | "";
  },


  // Variables
  data() {
    return {
      usernameField: this.$props.user.username | "",
      passwordField: this.$props.user.password | "",
    }
  },

  // Functions
  methods: {
    submitForm(event){
      event.preventDefault()
      if(this.$props.editable){
        this.$emit(this.$props.eventName, {
          "username": this.usernameField,
          "password": this.passwordField,
        })
      }
    }
  }

}
</script>

<style scoped>

</style>