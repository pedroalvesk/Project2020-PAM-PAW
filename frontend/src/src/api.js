const URL_LOGIN = "http://localhost:8090/api/v1/auth/login"

const URL_USERS = "http://localhost:8090/api/v1/backoffice/users"
const URL_USERS_CREATE = "http://localhost:8090/api/v1/auth/register"

const URL_INVOICES = "http://localhost:8090/api/v1/backoffice/invoices"

export default {
    LOGIN: URL_LOGIN,

    USERS: URL_USERS,
    USERS_CREATE: URL_USERS_CREATE,

    INVOICES: URL_INVOICES,
}

function makeRequest(type, url, key, data){
    const axios = require('axios');
    const config = {
        method: type,
        url: url,
        headers: {
            'Content-Type': 'application/json'
        },
        data: data
    };

    this.response = {
        data: null,
        hasErrors: false,
        error: "",
    };

    axios(config)
        .then(response => {
            this.response.data = response.data[key]
        })
        .catch(error => {
            this.response.hasErrors = true
            this.response.error = error.response?.data["message"]
        });

    return this.response
}

export function tryLogin(username, password){
    return makeRequest("post", this.LOGIN, "token", JSON.stringify({
        "username": username,
        "password": password
    }))
}

export function getAllUsers(){
    return makeRequest("post", this.USERS, "users", JSON.stringify({}))
}

export function getAllInvoices(){
    return makeRequest("post", this.USERS, "invoices", JSON.stringify({}))
}

