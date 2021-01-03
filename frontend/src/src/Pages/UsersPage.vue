<template>
  <b-container id="page">

    <!-- Title -->
    <h3>{{this.table.title}}</h3>

    <!-- Form toggle -->
    <b-row id="pad-b-5">
      <b-col>
        <b-button @click="this.toggleForm" variant="success">Adicionar novo usuário</b-button>
      </b-col>
    </b-row>

    <!-- Form -->
    <b-row>
      <b-col>

        <!-- Success Alert -->
        <b-alert class="text-center" v-model="this.form.success" variant="success" dismissible>
          User adicionado com sucesso!
        </b-alert>

        <!-- Error Alert -->
        <b-alert class="text-center" v-model="this.form.hasErrors" variant="danger" dismissible>
          {{ this.form.error }}
        </b-alert>

        <!-- Form -->
        <UserForm
            v-if="this.form.visible"
            button-text="Criar"
            @submit="this.onFormSubmit"/>
      </b-col>
    </b-row>
    <b-row></b-row>

    <!-- Table -->
    <b-row>
      <b-col>
        <Table :key="this.table.forceUpdate"
            :url='this.table.url'
            :empty='this.table.empty'
            :data-key="this.table.dataKey"
            :fields="this.table.items"
        />
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
import api from "@/api";
import Table from "@/components/DataViews/Table";
import UserForm from "@/components/Forms/UserForm";

export default {
  name: "UsersPage",
  components: {
    Table,
    UserForm
  },

  // Variables
  data() {
    return {
      form: {
        visible: false,
        success: false,
        hasErrors: false,
        error: ""
      },

      table: {
        forceUpdate: 0,
        title: "Lista de Usuários",
        url: api.USERS,
        empty: "Não há usuários registados!",
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

  // Functions
  methods: {

    toggleForm(){
      this.form.visible = !this.form.visible
    },

    onFormSubmit(data){
      let username = data[0]
      let password = data[1]

      const axios = require('axios');
      const config = {
        method: 'post',
        url: api.USERS_CREATE,
        headers: {
          'Content-Type': 'application/json'
        },
        data: JSON.stringify({
          "username": username,
          "password": password
        })
      };

      axios(config)
          .then(response => {
            console.log(response.data)
            this.table.forceUpdate++
            this.form.success = true
            this.form.hasErrors = false
          })
          .catch(error => {
            this.form.success = false
            this.form.hasErrors = true
            this.form.error = error.response?.data["message"]
          });
    }
  }

}
</script>

<style scoped>
  #page {
    padding: 5px;
  }

  #pad-b-5 {
    padding-bottom: 5px
  }
</style>
