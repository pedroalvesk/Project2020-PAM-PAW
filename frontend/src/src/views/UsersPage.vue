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

      <!-- "Create new user" button -->
      <b-button class="form-new" variant="success" @click="toggleAddForm">Add new User</b-button>

      <!-- "Create new user" form -->
      <b-card class="card-new" v-if="showAddForm">
        <UserForm
            event-name="newUserBtn"
            button-text="Create new user"
            :editable="true"
            :show-password="true"
            @newUserBtn="createNewUser"
        />
      </b-card>

      <!-- Users table -->
      <UsersTable
          :key="this.table.hasUpdates"
          :empty='this.table.empty'
          :data-key="this.table.dataKey"
          :fields="this.table.items"
      />

    </b-container>
  </div>
</template>

<script>
import UsersTable from "@/components/tables/UsersTable";
import API from "@/API";
import UserForm from "@/components/forms/UserForm";

export default {
  name: "Users",
  components: {
    UserForm,
    UsersTable,
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
        title: "Users List",
        hasUpdates: 0,
        empty: "No users on record!",
        dataKey: "users",
        fields: [
          {
            key: 'actions',
            sortable: false
          },
          {
            key: 'id',
            label: 'ID',
            sortable: true,
          },
          {
            key: 'username',
            sortable: true,
          },
          {
            key: 'password',
            sortable: false,
          }
        ]
      },
    }
  },

  // Events
  created() {
    document.title = "PAM&PAW | Users Page";
  },

  // Functions
  methods: {
    toggleAddForm(){
      this.showAddForm = !this.showAddForm;
    },

    createNewUser(user){
      API.createNewUser(user.username, user.password, this.createNewUserSuccess, this.createNewUserFailure);
    },

    createNewUserSuccess(){
      this.table.hasUpdates++;
      this.hasSuccess = true;
      this.success = "User created!";
      this.toggleAddForm();
    },

    createNewUserFailure(error){
      this.error = error.response?.data["message"]
      this.hasErrors = true;
      localStorage.setItem("error", this.error)
    },

  }
}
</script>