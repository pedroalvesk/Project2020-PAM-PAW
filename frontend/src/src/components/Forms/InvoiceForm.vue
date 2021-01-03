<template>
  <div class="login-layout text-left">
    <b-form @submit="onSubmit">

      <!-- User -->
      <b-form-group id="input-group-1" label="Usuário:" label-for="input-1">
        <b-form-input id="input-1" v-model="user" type="text" required></b-form-input>
      </b-form-group>

      <!-- File -->
      <b-form-group id="input-group-2" label="Ficheiro:" label-for="input-2">
        <b-form-file
            id="input-2"
            v-model="file"
            :state="Boolean(file)"
            placeholder="Escolha um ficheiro ou arraste um para aqui..."
            drop-placeholder="Arraste um ficheiro para aqui..."
            accept="image/*, .pdf"
            required
        ></b-form-file>
        <div class="mt-3">Ficheiro selecionado: {{ file ? file.name : '' }}</div>
      </b-form-group>

      <!-- Button -->
      <b-button type="submit" variant="outline-primary">{{this.$props.buttonText}}</b-button>
    </b-form>

  </div>
</template>

<script>
export default {
  name: "InvoiceForm",

  props: {
    buttonText: String,
  },

  // Data (variables)
  data() {
    return {
      file: null,
      user: null,
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