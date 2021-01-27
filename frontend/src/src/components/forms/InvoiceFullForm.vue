<template>
  <div class="text-left">
    <b-form @submit.prevent="submitForm">

      <!-- First Row -->
      <b-row>

        <!-- Filename -->
        <b-col>
          <strong>
            <b-form-group id="input-group-1" label="Filename:" label-for="input-1">
              <b-form-input v-model="invoice.filename" placeholder="Invoice filename" :disabled="!this.editable"></b-form-input>
            </b-form-group>
          </strong>
        </b-col>

        <!-- Extension -->
        <b-col>
          <strong>
            <b-form-group id="input-group-1" label="Extension:" label-for="input-1">
              <b-form-input v-model="invoice.extension" placeholder="Invoice extension" :disabled="!this.editable"></b-form-input>
            </b-form-group>
          </strong>
        </b-col>

        <!-- Status -->
        <b-col>
          <strong>
            <b-form-group id="input-group-1" label="Processed:" label-for="input-1">
              <b-form-input v-model="invoice.processed" placeholder="Invoice status" :disabled="!this.editable"></b-form-input>
            </b-form-group>
          </strong>
        </b-col>
      </b-row>

      <!-- Second Row -->
      <b-row>

        <!-- User -->
        <b-col>
          <strong>
            <b-form-group id="input-group-1" label="User:" label-for="input-1">
              <b-form-input v-model="invoice.userID" placeholder="" :disabled="!this.editable"></b-form-input></b-form-group>
          </strong>
        </b-col>

        <!-- Date -->
        <b-col>
          <strong>
            <b-form-group id="input-group-1" label="Type:" label-for="input-1">
              <b-form-input v-model="invoice.type" placeholder="" :disabled="!this.editable"></b-form-input>
            </b-form-group>
          </strong>
        </b-col>
      </b-row>

      <!-- Third Row -->
      <b-row>

        <!-- Data -->
        <b-col>
          <strong>
            <b-form-group id="input-group-1" label="Data:" label-for="input-1">
              <b-form-textarea
                  id="textarea"
                  v-model="invoice.fullText"
                  placeholder="No data..."
                  rows="25"
                  max-rows="6"
                  :disabled="!this.editable">
              ></b-form-textarea>
            </b-form-group>
          </strong>
        </b-col>
      </b-row>

    </b-form>

  </div>
</template>

<script>
export default {
  name: "InvoiceFullForm",

  // Args
  props: {
    eventName: String,
    buttonText: String,
    defaultInvoice: Object,
    editable: Boolean,
    id: Number,
    users: Object,
  },

  // Vars
  data() {
    return {
      token: null,
      invoice: {},
    }
  },

  // States
  mounted() {
    this.token = localStorage.getItem("token");
    this.loadInvoice();
  },

  // Methods
  methods: {

    loadInvoice(){
      this.invoice = this.$props.defaultInvoice.actions["invoice"];
      this.invoice.userID = this.$props.users[this.invoice.userID];
      this.invoice.processed = this.invoice.processed.toString();
    },

  }

}
</script>