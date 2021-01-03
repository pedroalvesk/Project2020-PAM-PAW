<template>
  <div id="app">
    <router-link to="/">Login </router-link>
    <router-link to="/users">Users </router-link>
    <router-link to="/invoices">Invoices </router-link>
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

#content {
  text-align: center;
  padding-top: 50px;
}


</style>
