<template>
  <b-container>

    <!-- Title -->
    <h3>{{this.table.title}}</h3>

    <!-- Form toggle -->
    <b-row id="pad-b-5">
      <b-col>
        <b-button @click="this.toggleForm" variant="success">Adicionar novo invoice</b-button>
      </b-col>
    </b-row>

    <!-- Form -->
    <b-row>
      <b-col>

        <!-- Success Alert -->
        <b-alert class="text-center" v-model="this.createForm.success" variant="success" dismissible>
          User adicionado com sucesso!
        </b-alert>

        <!-- Error Alert -->
        <b-alert class="text-center" v-model="this.createForm.hasErrors" variant="danger" dismissible>
          {{ this.createForm.error }}
        </b-alert>

        <!-- Form -->
        <InvoiceForm
            v-if="this.createForm.visible"
            button-text="Criar invoice"
            @submit="this.onFormSubmit"/>
        <hr v-if="this.createForm.visible">
      </b-col>
    </b-row>

    <!-- Table -->
    <b-row>
      <b-col>
        <Table
            :url='this.table.url'
            :empty='this.table.empty'
            :data-key="this.table.dataKey"
            :fields="this.table.items"
            @viewBtnClick="this.showViewForm"
            @editBtnClick="this.showEditForm"
            @deleteBtnClick="this.showDeleteForm"
        />
      </b-col>
    </b-row>

    <!-- View Invoice -->
    <b-row>
      <b-col>
        <Layout
          v-if="this.invoiceViewer.visible"
          :invoice="this.invoiceViewer.selected"
        ></Layout>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
import api from "@/api";
import Table from "@/components/DataViews/Table";
import Layout from "@/components/DataViews/Layout";
import InvoiceForm from "@/components/Forms/InvoiceForm";
export default {
  name: "InvoicePage",
  components: {
    Table, InvoiceForm, Layout
  },

  data() {
    return {
      createForm: {
        visible: false,
        success: false,
        hasErrors: false,
        error: ""
      },
      invoiceViewer: {
        visible: true,
        selected: {
          actions: "",
          data: "",
          extension: ".jpeg",
          filename: "405d00b1-53bf-4cf6-a2b7-5025682db0b2.jpeg",
          id: 3,
          processed: false,
          type: "",
          userID: 1
        }
      },
      table: {
        title: "Lista de Invoices",
        url: api.INVOICES,
        empty: "Não há invoices registadas!",
        dataKey: "invoices",
        fields: [
          {
            key: 'emptyField',
            label: 'NEW',
            sortable: true,
          },
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

  methods: {
    toggleForm(){
      this.createForm.visible = !this.createForm.visible
    },

    onFormSubmit(){

    },

    showViewForm(data){
      console.log("View:", data)
    },

    showEditForm(data){
      console.log("Edit:", data)
    },

    showDeleteForm(data){
      console.log("Delete:", data)
    },
  }
}
</script>

<style scoped>

</style>
