<template>
  <div class="text-left justify-content-center">
    <b-form @submit.prevent="submitForm">

      <!-- User -->
      <strong>
        <b-form-group id="input-group-1" label="User:" label-for="input-1">
          <div>
            <b-form-select v-model="userSelected" :options="userOptions" required></b-form-select>
          </div>
        </b-form-group>
      </strong>
      <div class="mt-3">Selected user: <strong>{{ userSelected }}</strong></div><hr>

      <!-- File -->
      <strong>
        <b-form-group id="input-group-2" label="File:" label-for="file-1">
          <b-form-file
              accept="image/*, application/pdf"
              id="file-1"
              v-model="file"
              :state="Boolean(file)"
              placeholder="Choose a file or drop it here..."
              drop-placeholder="Drop file here..."
              required
          ></b-form-file>
        </b-form-group>
      </strong>
      <div class="mt-3">Selected file: {{ file ? file.name : '' }}</div><hr>

      <!-- Button -->
      <b-button v-if="$props.editable" type="submit" variant="outline-primary" :disabled="this.disableButton">{{this.$props.buttonText}}</b-button>
    </b-form>

  </div>
</template>

<script>
import API from "@/API";

export default {
  name: "InvoicesForm",

  // Args
  props: {
    eventName: String,
    buttonText: String,
    defaultInvoice: Object,
    editable: Boolean,
    id: Number,
    users: Array,
  },

  // Vars
  data() {
    return {
      token: null,
      file: null,
      userSelected: null,
      userOptions: [],
      disableButton: false
    }
  },

  // States
  mounted() {
    this.token = localStorage.getItem("token");
    this.loadUsers();
  },

  // Methods
  methods: {

    loadUsers(){
      API.getAllUsers(this.token, this.loadUsersSuccess, this.loadUsersFailure);
    },

    loadUsersSuccess(response){
      const users = response.data["users"];
      users.forEach(item => {
        const value = item.id;
        const text = item.username;
        this.userOptions.push({value: value, text: text});
      });
      this.disableButton = false;
    },

    loadUsersFailure(error){
      console.log(error);
      this.userOptions = [{value: -1, text: "Error loading users..."}];
      this.disableButton = true;
    },

    submitForm(){
      this.$emit(this.$props.eventName, {
        file: this.file,
        userID: this.userSelected,
        id: this.$props.id,
      });
    }
  }

}
</script>