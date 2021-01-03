<template>
  <b-container>
    <hr>
    <b-row>
      <b-col>
        <h3>Invoice #{{ this.invoice.id }}</h3>
      </b-col>
    </b-row>

    <!-- Nome do Ficheiro -->
    <b-row>
      <b-col>
        <b-form-group label="Nome do Ficheiro:" class="text-left">
          <b-form-input v-model="this.invoice.filename" :state="notNull" trim disabled></b-form-input>
        </b-form-group>
      </b-col>

      <!-- Extensão Ficheiro -->
      <b-col cols="3">
        <b-form-group label="Extensão:" class="text-left">
          <b-form-input v-model="this.invoice.extension" :state="notNull" trim disabled></b-form-input>
        </b-form-group>
      </b-col>

      <!-- Usuário -->
      <b-col cols="3">
        <b-form-group label="Usuário:" class="text-left">
          <b-form-input v-model="this.invoice.userID" :state="notNull" trim disabled></b-form-input>
        </b-form-group>
      </b-col>
    </b-row>


    <b-row>
      <!-- Processado -->
      <b-col>
        <b-form-group label="Processada?" class="text-left">
          <b-form-select v-model="this.invoice.processed" :options="options" :state="this.invoice.processed" disabled></b-form-select>
        </b-form-group>
      </b-col>

      <!-- Tipo -->
      <b-col cols="3">
        <b-form-group label="Tipo:" class="text-left">
          <b-form-input v-model="this.invoice.type"  :state="this.invoice.type" trim disabled></b-form-input>
        </b-form-group>
      </b-col>

    </b-row>

    <b-row>
      <b-col>

        <!-- Dados OCR -->
        <b-form-group label="Dados OCR:" class="text-left">
          <b-form-textarea
              v-model="this.invoice.data"
              rows="10"
              max-rows="25"
              disabled
          ></b-form-textarea>
        </b-form-group>

      </b-col>
    </b-row>



  </b-container>

</template>

<script>
export default {
  name: "Layout",

  // Arguments
  props: {
    invoice: Object,
  },


  // Variables
  data() {
    return {
      options: [
          { value: 'false', text: 'Ainda não foi processada!', disabled: true },
          { value: 'true', text: 'Já foi processada!', disabled: true },
      ]
    }
  },

  // Computed
  computed: {
    state() {
      return this.name.length >= 4
    },

    notNull(data){
      return data !== null
    },

    invalidFeedback() {
      if (this.name.length > 0) {
        return 'Enter at least 4 characters.'
      }
      return 'Please enter something.'
    }
  },

}
</script>

<style scoped>

</style>