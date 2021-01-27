<template>
  <div>
    <b-container :fluid="true">

      <!-- Title -->
      <h1>{{ this.table.title }}</h1>

      <!-- Errors -->
      <b-alert v-if="this.hasErrors" v-model="this.hasErrors" variant="danger" dismissible>
        {{ this.error }}
      </b-alert>

      <!-- Success -->
      <b-alert v-if="this.hasSuccess" v-model="this.hasSuccess" variant="success" dismissible>
        {{ this.success }}
      </b-alert>

      <!-- "Create new invoice" button -->
      <b-button class="form-new" variant="success" @click="toggleAddForm">Add new Invoice</b-button>

      <!-- "Create new invoice" form -->
      <b-card class="card-new" v-if="showAddForm">
        <InvoicesForm
            button-text="Create new invoice"
            :editable="true"
            event-name="newInvoiceBtn"
            @newInvoiceBtn="createNewInvoice"
        />
      </b-card>

      <!-- Invoice table -->
      <InvoicesTable
          :key="this.table.hasUpdates"
          :empty='this.table.empty'
          :data-key="this.table.dataKey"
          :fields="this.table.items"
      />

    </b-container>
  </div>
</template>

<script>
import API from "@/API";
import InvoicesTable from "@/components/tables/InvoicesTable";
import InvoicesForm from "@/components/forms/InvoicesForm";

export default {
  name: "InvoicesPage",
  components: {
    InvoicesForm,
    InvoicesTable,
  },

  // Vars
  data() {
    return {
      hasSuccess: false,
      success: "",
      hasErrors: false,
      error: "",
      showAddForm: false,
      table: {
        title: "Invoices List",
        hasUpdates: 0,
        empty: "No invoices on record!",
        dataKey: "invoices",
        fields: [
          {
            key: 'id',
            label: 'Invoice ID',
            sortable: true,
          },
          {
            key: 'userID',
            label: 'User ID',
            sortable: true,
          },
          {
            key: 'processed',
            label: 'Processed?',
            sortable: true,
          },
          {
            key: 'filename',
            label: 'Filename',
            sortable: true,
          },
          {
            key: 'extension',
            label: 'Ext',
            sortable: true,
          },
          {
            key: 'data',
            label: 'Data',
            sortable: false,
          }
        ]
      },
    }
  },

  // Events
  created() {
    document.title = "PAM&PAW | Invoices Page";
  },

  // Functions
  methods: {
    toggleAddForm(){
      this.showAddForm = !this.showAddForm;
    },

    createNewInvoice(invoice){
      API.createNewInvoice(invoice.userID, invoice.file, this.token, this.createNewInvoiceSuccess, this.createNewInvoiceFailure)
    },

    createNewInvoiceSuccess(){
      this.table.hasUpdates++;
      this.hasSuccess = true;
      this.success = "Invoice created!";
      this.toggleAddForm();
    },

    createNewInvoiceFailure(error){
      this.error = error.response?.data["message"]
      this.hasErrors = true;
      localStorage.setItem("error", this.error)
    },

  }
}
</script>