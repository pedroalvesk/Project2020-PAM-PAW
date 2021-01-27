<template>
  <div>
    <!-- Table -->
    <b-table :key="this.hasUpdates" :items="items" :busy="isBusy" :fields="this.$props.fields" class="mt-3" outlined hover head-variant="light" show-empty>

      <!-- Invoice UserID -->
      <template #cell(user)="row">
        {{row.value + " (" + users[row.value] + ")" }}
      </template>

      <!-- Invoice Processed -->
      <template #cell(processed)="row" class="text-center justify-content-between">
        <div v-if="row.value" class="circle-green">YES</div>
        <div v-else class="circle-red"> NO</div>
      </template>

      <!-- Busy State -->
      <template #table-busy>
        <div class="text-center text-danger my-2">
          <b-spinner class="align-middle"></b-spinner>
          <strong> Loading ...</strong>
        </div>
      </template>

      <!-- When empty -->
      <template #empty="">
        <h5><strong>{{ this.$props.empty }}</strong></h5>
      </template>

      <!-- Actions -->
      <template #cell(actions)="row">
        <div>
          <b-button variant="outline-info" title="View" @click="actionView(row)">
            <b-icon icon="eye" aria-hidden="true"></b-icon>
          </b-button> |

          <b-button variant="outline-danger" title="Delete" @click="actionDelete(row)">
            <b-icon icon="x-circle-fill" aria-hidden="true"></b-icon>
          </b-button>
        </div>
      </template>

      <!-- Row Details -->
      <template #row-details="row">
        <b-card>
          <!-- View Form -->
          <div v-if="row.item.actions.view">
            VIEW FORM
            <InvoiceFullForm
              :default-invoice="row.item"
              :users="users"
              :editable="false"
            />
          </div>

          <!-- Delete -->
          <div v-if="row.item.actions.delete">
            <b-alert :show="true" variant="danger">
              <p>{{$props.deleteMessage || "Are you sure you want to delete: "}} <strong>{{row.item.filename}}</strong> ?</p>
              <b-button variant="danger" @click="deleteInvoice(row)">Delete</b-button>
            </b-alert>
          </div>

        </b-card>
      </template>

    </b-table>
  </div>
</template>

<script>
import API from "@/API";
import InvoiceFullForm from "@/components/forms/InvoiceFullForm";

export default {
  name: "InvoicesTable",
  components: {InvoiceFullForm},
  // Arguments
  props: {
    fields: Array,
    dataKey: String,
    emptyMessage: String,
    deleteMessage: String,
  },

  // Variables
  data() {
    return {
      token: null,
      hasUpdates: 0,
      hasErrors: false,
      error: null,
      isBusy: true,
      items: [],
      invoices: null,
      users: {},
    }
  },

  // Page States
  async mounted() {
    this.token = localStorage.getItem("token");
    await this.getAllUsers();
  },

  // Functions
  methods: {

    /*
     * TABLE ACTIONS
     */
    actionAllFalse(row){
      row.item.actions.view = false;
      row.item.actions.edit = false;
      row.item.actions.delete = false;
      row.toggleDetails();
    },

    actionView(row){
      this.actionAllFalse(row);
      row.item.actions.view = !row.detailsShowing;
    },

    actionDelete(row){
      this.actionAllFalse(row);
      row.item.actions.delete = !row.detailsShowing;
    },

    toggleBusy() {
      this.isBusy = !this.isBusy
    },

    /*
     * DATA FETCH
     */
    getAllInvoices() {
      API.getAllInvoices(this.token, this.getAllInvoicesSuccess, this.fetchFailure);
    },

    getAllInvoicesSuccess(response){
      this.invoices = response.data[this.$props.dataKey]
      this.items = [];

      this.invoices.forEach(item => {
        const actions = [];
        actions["invoice"]= item;
        this.items.push({
          id: item.id,
          user: item.userID,
          filename: item.filename,
          processed: item.processed,
          extension: item.extension,
          type: item.type,
          actions: actions,
        })
      })
      this.toggleBusy()
    },

    getAllUsers() {
      API.getAllUsers(this.token, this.getAllUsersSuccess, this.fetchFailure);
    },

    getAllUsersSuccess(response) {
      response.data["users"].forEach(item => {
        this.users[item.id] = item.username;
      });
      this.getAllInvoices();
    },

    fetchFailure(error){
      console.log(error);
      this.hasErrors = true
      this.error = error.response?.data["message"]
      localStorage.setItem("errors",this.error)
    },

    /*
     * FORMS ACTIONS
     */
    deleteInvoice(row){
      API.deleteInvoice(row.item.id, this.token, ()=>{this.getAllInvoices(); this.toggleBusy();}, ()=>{});
    },


  }
}
</script>

<style scoped>
  .circle-red {
    height: 20px;
    background-color: red;
  }

  .circle-green {
    height: 20px;
    background-color: greenyellow;
  }
</style>