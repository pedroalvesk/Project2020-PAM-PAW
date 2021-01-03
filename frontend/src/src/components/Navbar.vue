<template>
  <b-navbar type="dark" variant="dark">

    <!-- Align Left -->
    <b-navbar-nav>
      <b-nav-item href="#"><strong>OCR</strong></b-nav-item>
    </b-navbar-nav>

    <!-- Align Center -->
    <b-navbar-nav class="ml-auto">

      <b-nav-item>
        <router-link to="/users"><strong>Users</strong></router-link>
      </b-nav-item>

      <b-nav-item>
        <router-link to="/invoices"><strong>Invoices</strong> </router-link>
      </b-nav-item>

    </b-navbar-nav>

    <!-- Align Right -->
    <b-navbar-nav class="ml-auto">
      <b-nav-item href="#">Logout</b-nav-item>
    </b-navbar-nav>

  </b-navbar>
</template>

<script>
export default {
name: "Navbar"
}
</script>

<style scoped>

</style>