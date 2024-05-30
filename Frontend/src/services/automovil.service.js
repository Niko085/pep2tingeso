import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/automoviles/');
}

const create = data => {
    return httpClient.post("/automoviles/", data);
}

const get = id => {
    return httpClient.get(`/automoviles/${id}`);
}

const update = data => {
    return httpClient.put('/automoviles/', data);
}

const remove = id => {
    return httpClient.delete(`/automoviles/${id}`);
}
export default { getAll, create, get, update, remove };