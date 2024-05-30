import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/automoviles/');
}

const create = data => {
    return httpClient.post("/api/automoviles/", data);
}

const get = id => {
    return httpClient.get(`/api/automoviles/${id}`);
}

const update = data => {
    return httpClient.put('/api/automoviles/', data);
}

const remove = id => {
    return httpClient.delete(`/api/automoviles/${id}`);
}
export default { getAll, create, get, update, remove };