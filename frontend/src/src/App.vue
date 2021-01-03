<template>
  <div id="app">
    <UsersPage/>
  </div>
</template>

<script>
import UsersPage from "@/components/UsersPage";

export default {
  name: 'App',
  components: {
    UsersPage,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
