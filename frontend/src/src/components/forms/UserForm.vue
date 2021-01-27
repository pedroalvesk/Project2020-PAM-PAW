<template>
  <div class="login-layout text-left justify-content-center">
    <b-form @submit.prevent="submitForm">

      <!-- Username -->
      <strong>
        <b-form-group id="input-group-1" label="Username:" label-for="input-1">
          <b-form-input id="input-1" v-model="username" type="text" required :disabled="!$props.editable"></b-form-input>
        </b-form-group>
      </strong>

      <!-- Password -->
      <strong>
        <b-form-group id="input-group-2" label="Password:" label-for="input-2">
          <b-form-input id="input-2" v-if="$props.showPassword" v-model="password" type="text" :disabled="!$props.editable" required></b-form-input>
          <b-form-input id="input-2" v-else v-model="password" type="password" :disabled="!$props.editable" required></b-form-input>
        </b-form-group>
      </strong>

      <!-- Button -->
      <b-button v-if="$props.editable" type="submit" variant="outline-primary">{{this.$props.buttonText}}</b-button>
    </b-form>

  </div>
</template>

<script>
export default {
  name: "UserForm",

  // Args
  props: {
    eventName: String,
    buttonText: String,
    defaultUsername: String,
    defaultPassword: String,
    editable: Boolean,
    showPassword: Boolean,
    id: Number,
  },

  // Vars
  data() {
    return {
      username: "",
      password: "",
      oldUsername: "",
      oldPassword: "",
    }
  },

  // States
  mounted() {
    this.username = this.$props.defaultUsername;
    this.password = this.$props.defaultPassword;
    this.oldUsername = this.username;
    this.oldPassword = this.password;
  },

  // Methods
  methods: {
    submitForm(){
      this.$emit(this.$props.eventName, {
        "username": this.username,
        "password": this.password,
        "oldUsername": this.oldUsername,
        "oldPassword": this.oldPassword,
        "id": this.$props.id,
      });
    }
  }

}
</script>

<style scoped>

</style>