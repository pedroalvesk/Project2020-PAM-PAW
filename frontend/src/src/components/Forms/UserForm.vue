<template>
  <div class="login-layout text-left">
    <b-form @submit="onSubmit">

      <!-- Username -->
      <b-form-group id="input-group-1" label="Username:" label-for="input-1">
        <b-form-input id="input-1" v-model="usernameField" type="text" required></b-form-input>
      </b-form-group>

      <!-- Password -->
      <b-form-group id="input-group-2" label="Password:" label-for="input-2">
        <b-form-input id="input-2" v-model="passwordField" type="password" required></b-form-input>
      </b-form-group>

      <!-- Button -->
      <b-button type="submit" variant="outline-primary">{{this.$props.buttonText}}</b-button>
    </b-form>

  </div>
</template>

<script>
export default {
  name: "Form",

  props: {
    buttonText: String,
  },

  // Data (variables)
  data() {
    return {
      usernameField: "",
      passwordField: "",
    }
  },

  // Functions
  methods: {
    onSubmit(event){
      event.preventDefault()
      this.$emit("submit", [this.usernameField, this.passwordField])
    }
  }
}
</script>

<style scoped>

</style>