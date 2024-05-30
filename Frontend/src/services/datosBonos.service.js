import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/datosbonos/');
}

const create = data => {
    return httpClient.post("/datosbonos/", data);
}

const get = id => {
    return httpClient.get(`/datosbonos/${id}`);
}

const update = data => {
    return httpClient.put('/datosbonos/', data);
}

const remove = id => {
    return httpClient.delete(`/datosbonos/${id}`);
}
export default { getAll, create, get, update, remove };