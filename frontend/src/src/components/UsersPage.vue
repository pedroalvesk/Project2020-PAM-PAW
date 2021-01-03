<template>
  <b-container fluid="">
    <b-col>
      <DataView
        title="Lista de Usuários"
        :url='this.table.url'
        empty="Não há usuários registados!"
      />
    </b-col>
  </b-container>
</template>

<script>
import api from "@/api";
import DataView from "@/components/DataView";

export default {
  name: "UsersPage",
  components: {DataView},

  data() {
    return {
      "table": {
        "url": api.USERS,
      },
    }
  },

}
</script>

<style scoped>

</style>
