<template>
  <div>
    <!-- Delete Warning -->
    <b-alert
        :show="dismissCountDown"
        dismissible
        variant="info"
        @dismissed="dismissCountDown=0"
        @dismiss-count-down="countDownChanged"
    >
      <p>Tem a certeza que pretende apagar o utilizador: {{ this.selectedUser }} ?</p>
      <b-button variant="danger">Sim</b-button> |
      <b-button variant="info">Não</b-button>
      <p>{{ dismissCountDown }} segundos...</p>
      <b-progress
          variant="danger"
          :max="dismissSecs"
          :value="dismissCountDown"
          height="4px"
      ></b-progress>
    </b-alert>

    <!-- Table -->
    <b-table :items="items" :busy="isBusy" :fields="this.$props.fields" class="mt-3" outlined hover head-variant="light" show-empty>

      <!-- Invoice Processed -->
      <template #cell(processed)="data">
        <div v-if="data.value" class="circle-green"/>
        <div v-else class="circle-red"/>
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

      <!-- Actions -->
      <template #cell(actions)="data">
        <div>
          <b-button variant="outline-info" title="View" :key="data" @click="viewBtnEmit">
            <b-icon icon="eye" aria-hidden="true"></b-icon>
          </b-button> |

          <b-button variant="outline-warning" title="Edit" @click="editBtnEmit">
            <b-icon icon="menu-up" aria-hidden="true"></b-icon>
          </b-button> |

          <b-button variant="outline-danger" title="Delete" @click="deleteBtnEmit">
            <b-icon icon="x-circle-fill" aria-hidden="true"></b-icon>
          </b-button>
        </div>

      </template>

    </b-table>
  </div>
</template>

<script>
export default {
  name: "Table",
  props: {
    url: String,
    empty: String,
    fields: Array,
    dataKey: String
  },

  // Variables
  data() {
    return {
      // alert
      selectedUser: "Joao",
      dismissSecs: 7,
      dismissCountDown: 0,

      // errors
      hasErrors: false,
      error: "",

      // table
      isBusy: true,
      items: [],
      showViewBtn: true
    }
  },

  // Page States
  mounted() {
    if(this.$props.dataKey === "users"){
      this.showViewBtn = false
    }

    this.getData()
  },

  // Functions
  methods: {

    viewBtnEmit(event, data){
      event.preventDefault()
      this.$emit("viewBtnClick", data)
    },

    editBtnEmit(event, data){
      event.preventDefault()
      this.$emit("editBtnClick", data)
    },

    deleteBtnEmit(event, data){
      event.preventDefault()
      this.$emit("deleteBtnClick", data)
    },
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown
    },

    toggleBusy() {
        this.isBusy = !this.isBusy
    },

    getData() {
      const axios = require('axios');
      const config = {
        method: 'get',
        url: this.$props.url
      };

      axios(config)
          .then(response => {
            console.log(response.data[this.$props.dataKey])
            this.items = response.data[this.$props.dataKey]

            this.items.forEach(item => {
              item.actions = ""
            })

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
  .btn-pill {
    padding-left: 5px;
    padding-right: 5px;
  }

  .circle-red {
    height: 20px;
    width: 20px;
    background-color: red;
  }

  .circle-green {
    height: 20px;
    width: 20px;
    background-color: greenyellow;
  }
</style>