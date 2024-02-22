import axios from "axios";

const api = axios.create({
    // baseURL: "http://localhost:8080/api/"
    // baseURL: "http://54.225.15.170:8080/api/"
    baseURL: "https://54.225.15.170:8443/api/"
})

export default api;
