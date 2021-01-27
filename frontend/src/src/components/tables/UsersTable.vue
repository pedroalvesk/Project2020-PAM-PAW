<template>
  <div>

    <!-- Table -->
    <b-table :key="this.hasUpdates" :items="items" :busy="isBusy" :fields="this.$props.fields" class="mt-3" outlined hover head-variant="light" show-empty>

      <!-- Password Field -->
      <template #cell(password)="">
        <b-icon font-scale="0.75" icon="circle-fill" variant="dark"></b-icon>
        <b-icon font-scale="0.75" icon="circle-fill" variant="dark"></b-icon>
        <b-icon font-scale="0.75" icon="circle-fill" variant="dark"></b-icon>
        <b-icon font-scale="0.75" icon="circle-fill" variant="dark"></b-icon>
        <b-icon font-scale="0.75" icon="circle-fill" variant="dark"></b-icon>
        <b-icon font-scale="0.75" icon="circle-fill" variant="dark"></b-icon>
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
        <h5><strong>{{ this.$props.emptyMessage }}</strong></h5>
      </template>

      <!-- Actions Field -->
      <template #cell(actions)="row">
        <div>
          <b-button variant="outline-info" title="View" @click="actionView(row)">
            <b-icon icon="eye" aria-hidden="true"></b-icon>
          </b-button> |

          <b-button variant="outline-warning" title="Edit" @click="actionEdit(row)">
            <b-icon icon="menu-up" aria-hidden="true"></b-icon>
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
            <UserForm v-if="row.item.actions.view"
                class="form"
                :editable="false"
                :default-username="row.item.username"
                :default-password="row.item.password"
                :show-password="true"
            />

          <!-- Edit Form -->
          <UserForm v-if="row.item.actions.edit"
                    class="form"
                    :editable="true"
                    :default-username="row.item.username"
                    :default-password="row.item.password"
                    :id="row.item.id"
                    :show-password="true"
                    button-text="Edit"
                    event-name="editForm"
                    @editForm="updateUser"
          />

          <!-- Delete -->
          <div v-if="row.item.actions.delete">
            <b-alert :show="true" variant="danger">
              <p>{{$props.deleteMessage || "Are you sure you want to delete: "}} <strong>{{row.item.username}}</strong> ?</p>
              <b-button variant="danger" @click="deleteUser(row)">Delete</b-button>
            </b-alert>
          </div>

        </b-card>
      </template>


    </b-table>
  </div>
</template>

<script>
import UserForm from "@/components/forms/UserForm";
import API from "@/API";

export default {
  name: "UsersTable",
  components: {
    UserForm,
  },

  // Args
  props: {
    fields: Array,
    dataKey: String,
    emptyMessage: String,
    deleteMessage: String,
  },

  // Data
  data() {
    return {
      token: null,
      hasUpdates: 0,
      hasErrors: false,
      error: null,
      isBusy: true,
      items: [],
    }
  },

  // Page States
  mounted() {
    this.token = localStorage.getItem("token");
    this.getAllUsers();
  },

  // Functions
  methods: {
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

    actionEdit(row){
      this.actionAllFalse(row);
      row.item.actions.edit = !row.detailsShowing;
    },

    actionDelete(row){
      this.actionAllFalse(row);
      row.item.actions.delete = !row.detailsShowing;
    },

    deleteUser(row){
      API.deleteUser(row.item.id, this.token, ()=>{this.getAllUsers();this.toggleBusy();}, ()=>{});
    },

    updateUser(data) {
      API.editUser(data.id, data.username, data.password, this.token, ()=>{this.getAllUsers();this.toggleBusy();}, () => {});
    },

    toggleBusy() {
      this.isBusy = !this.isBusy
    },

    getAllUsers() {
      API.getAllUsers(this.token, this.getAllUsersSuccess, this.getAllUsersFailure);
    },

    getAllUsersSuccess(response){
      this.items = response.data[this.$props.dataKey]
      this.items.forEach(item => {
        item.actions = []
      })
      this.toggleBusy()
    },

    getAllUsersFailure(error){
      this.hasErrors = true
      this.error = error.response?.data["message"]
      localStorage.setItem("errors",this.error)
      this.toggleBusy()
    },
  }

}
</script>