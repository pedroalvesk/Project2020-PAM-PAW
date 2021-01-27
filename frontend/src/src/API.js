const axios = require('axios');

// Login
const URL_LOGIN = "http://localhost:8090/api/v1/auth/login";

// User
const URL_USERS = "http://localhost:8090/api/v1/backoffice/users";
const URL_USERS_CREATE = "http://localhost:8090/api/v1/auth/register";

// Invoices
const URL_INVOICES = "http://localhost:8090/api/v1/backoffice/invoices";


export default {
    LOGIN: URL_LOGIN,
    USERS: URL_USERS,
    USERS_CREATE: URL_USERS_CREATE,
    INVOICES: URL_INVOICES,

    login: login,
    getAllUsers: getAllUsers,
    createNewUser: createNewUser,
    editUser: editUser,
    deleteUser: deleteUser,
    getAllInvoices: getAllInvoices,
    createNewInvoice: createNewInvoice,
    deleteInvoice: deleteInvoice,


}

/**************************************/

/*
 * Login
 */
export function login(username, password, onSuccess, onFailure){
    const config = getGenericConfig(username, password, URL_LOGIN, "POST");

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));
}

//////////////////////////////
// Users

/*
 * Get all users
 */
export function getAllUsers(token, onSuccess, onFailure){
    const config = getTokenConfig(token, URL_USERS, "GET");

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));
}

/*
 * Create new user
 */
export function createNewUser(username, password, onSuccess, onFailure){
    const config = getGenericConfig(username, password, URL_USERS_CREATE, "POST");

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));
}

/*
 * Edit user
 */
export function editUser(id, username, password, token, onSuccess, onFailure){
    const config = getGenericConfig(username, password, URL_USERS + "\\" + id, "PUT");

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));
}

/*
 * Delete user
 */
export function deleteUser(id, token, onSuccess, onFailure){
    const config = getGenericConfig("", "", URL_USERS + "\\" + id, "DELETE");

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));
}

//////////////////////////////
// Invoices

/*
 * Get all invoices
 */
export function getAllInvoices(token, onSuccess, onFailure){
    const config = getTokenConfig(token, URL_INVOICES, "GET");

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));
}

/*
 * Create new invoice
 */
export function createNewInvoice(userID, file, token, onSuccess, onFailure){
    const axios = require('axios');

    const formData = new FormData;
    formData.append("file", file);

    const config = {
        method: 'POST',
        url: URL_INVOICES + "/" + userID,
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': 'Bearer ' + token,
        },
        data : formData
    };

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));
    console.log(userID, file, token, onSuccess, onFailure);
}

/*
 * Delete invoice
 */
export function deleteInvoice(id, token, onSuccess, onFailure){
    const config = getGenericConfig("", "", URL_INVOICES + "\\" + id, "DELETE");

    axios(config)
        .then(response => onSuccess(response))
        .catch(error => onFailure(error));

}

/**************************************/

/*
 * Config for routes that DON'T NEED authorization
 */
export function getGenericConfig(username, password, url, method){
    return {
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/json',
        },
        data: JSON.stringify({
            "username": username,
            "password": password,
        })
    };
}

/*
 * Config for routes that NEED authorization
 */
export function getTokenConfig(token, url, method){
    return {
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + token,
        },
    };
}