import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/datosbonos/');
}

const create = data => {
    return httpClient.post("/api/datosbonos/", data);
}

const get = id => {
    return httpClient.get(`/api/datosbonos/${id}`);
}

const update = data => {
    return httpClient.put('/api/datosbonos/', data);
}

const remove = id => {
    return httpClient.delete(`/api/datosbonos/${id}`);
}
export default { getAll, create, get, update, remove };