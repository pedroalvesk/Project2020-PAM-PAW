<template>
  <div id="app">
    <!-- Nav Bar -->
    <div id="nav">
      <router-link class="box" v-if="!isLoggedIn()" to="/">Home</router-link>
      <router-link class="box" v-if="isLoggedIn()" to="/users"> Users </router-link>
      <router-link class="box" v-if="isLoggedIn()" to="/invoices">Invoices</router-link>
      <a class="box" v-if="isLoggedIn()" @click.prevent="logOut">Log out</a>
    </div>

    <!-- Image -->
    <img alt="Vue logo" src="./assets/logo.png">

    <!-- Content -->
    <router-view />

  </div>
</template>

<script>

export default {
  name: 'App',

  methods:{
    isLoggedIn(){
      return localStorage.getItem("token") !== null;

    },

    logOut(){
      localStorage.removeItem("token");
      localStorage.removeItem("expiration-time");
      this.$router.replace({name: "Login"});
    }
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
}

img {
  width: 5%;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

.box {
  padding: 10px 10px 10px 10px;
  border-left: 1px solid #41b883;
  border-right: 1px solid #41b883;
}

</style>
