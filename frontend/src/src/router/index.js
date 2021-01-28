import Vue from "vue";
import VueRouter from 'vue-router'
import Login from "@/views/LoginPage";
import Invoices from "@/views/InvoicesPage";
import Users from "@/views/UsersPage";
import NotFound from "@/views/NotFound";

Vue.use(VueRouter);

const routes = [
    {
        path: "",
        name: "Login",
        component: Login,
        beforeEnter: redirectIfLoggedIn,
    },
    {
        path: "/invoices/",
        name: "Invoices",
        component: Invoices,
        beforeEnter: needsPermission,
    },
    {
        path: "/users/",
        name: "Users",
        component: Users,
        beforeEnter: needsPermission,
    },
    {
        path: "*",
        name: "NotFound",
        component: NotFound,
    },
];

// Auth Middleware
function needsPermission(to, from, next) {
    const token = window.localStorage.getItem("token");

    if(token === null)
        next("/");
    else
        next();
}

// Auto Login
function redirectIfLoggedIn(to, from, next) {
    const token = window.localStorage.getItem("token");

    if(token === null)
        next();
    else
        next("/users");
}

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;