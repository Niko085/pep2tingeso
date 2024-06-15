import axios from "axios";

const autofixBackendServer = import.meta.env.VITE_AUTOFIX_BACKEND_SERVER;
const autofixBackendPort = import.meta.env.VITE_AUTOFIX_BACKEND_PORT;

console.log(autofixBackendServer)
console.log(autofixBackendPort)

export default axios.create({
    baseURL: `http://${autofixBackendServer}:${autofixBackendPort}`,
    headers: {
        'Content-Type': 'application/json'
    }
});