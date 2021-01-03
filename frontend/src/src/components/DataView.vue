<template>
  <div>
    <h3>{{this.$props.title}}</h3>
    <b-table :items="users" :busy="isBusy" :fields="fields" class="mt-3" outlined hover head-variant="light" show-empty>

      <!-- ID -->
      <template #cell(id)="id">
        <b class="text">{{ id.value }}</b>
      </template>

      <!-- Username -->
      <template #cell(username)="username">
        <b class="text-info">{{ username.value }}</b>
      </template>

      <!-- Password -->
      <template #cell(password)="password">
        {{ password.value }}
      </template>

      <!-- Busy State -->
      <template #table-busy>
        <div class="text-center text-danger my-2">
          <b-spinner class="align-middle"></b-spinner>
          <strong> A carregar ...</strong>
        </div>
      </template>

      <!-- When empty -->
      <template #empty="">
        <h5><strong>{{ this.$props.empty }}</strong></h5>
      </template>


    </b-table>
  </div>
</template>

<script>
export default {
  name: "DataView",
  props: {
    title: String,
    url: String,
    empty: String,
  },

  // Variables
  data() {
    return {
      // errors
      hasErrors: false,
      error: "",

      // table
      isBusy: true,
      users: [],
      fields: [
        {
          key: 'id',
          label: 'ID',
          sortable: true,
        },
        {
          key: 'username',
          sortable: true,
          // Variant applies to the whole column, including the header and footer
          //variant: 'light'
        },
        {
          key: 'password',
          sortable: false,
        }
      ],
    }
  },

  // Page States
  mounted() {
    this.getUsers()
  },

  // Functions
  methods: {
    toggleBusy() {
      this.isBusy = !this.isBusy
    },

    getUsers() {
      const axios = require('axios');
      const config = {
        method: 'get',
        url: this.$props.url
      };

      axios(config)
          .then(response => {
            this.users = response.data["users"]
            this.toggleBusy()
          })
          .catch(error => {
            this.hasErrors = true
            this.error = error.response?.data["message"]
            localStorage.setItem("errors",this.error)
          });
    }
  }
}
</script>

<style scoped>

</style>