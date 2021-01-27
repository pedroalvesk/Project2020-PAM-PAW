import Vue from "vue"
import Router from "vue-router"
import Login from "@/Pages/Login";
import InvoicePage from "@/Pages/InvoicePage";
import UsersPage from "@/Pages/UsersPage";

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: "/",
            name: "login",
            component: Login
        },
        {
            path: "/users",
            name: "users",
            component: UsersPage
        },
        {
            path: "/invoices",
            name: "invoices",
            component: InvoicePage
        },
    ]

})