import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/reparaciones/');
}

const create = data => {
    return httpClient.post("/api/reparaciones/", data);
}

const get = id => {
    return httpClient.get(`/api/reparaciones/${id}`);
}

const update = data => {
    return httpClient.put('/api/reparaciones/', data);
}

const remove = id => {
    return httpClient.delete(`/api/reparaciones/${id}`);
}
export default { getAll, create, get, update, remove };