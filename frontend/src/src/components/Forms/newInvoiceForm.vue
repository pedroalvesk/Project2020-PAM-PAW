<template>
  <div class="login-layout text-left">
    <b-form @submit="submitForm">

      <!-- User -->
      <b-form-group id="input-group-1" label="User:" label-for="input-1">
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
      <b-button type="submit" variant="outline-primary">Criar</b-button>
    </b-form>

  </div>
</template>

<script>
export default {
  name: "newInvoiceForm",

  // Props
  props: {
    eventName: String,
  },

  // Variables
  data() {
    return {
      file: null,
      user: null,
    }
  },

  // Functions
  methods: {
    submitForm(event){
      event.preventDefault()
      this.$emit(this.$props.eventName, {
        "file": this.file,
        "user": this.user
      })
    }
  }

}
</script>

<style scoped>

</style>