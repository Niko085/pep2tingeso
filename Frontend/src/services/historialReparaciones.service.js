import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/historialreparaciones/');
}

const create = data => {
    return httpClient.post("/historialreparaciones/", data);
}

const get = id => {
    return httpClient.get(`/historialreparaciones/${id}`);
}

const update = data => {
    return httpClient.put('/historialreparaciones/', data);
}

const remove = id => {
    return httpClient.delete(`/historialreparaciones/${id}`);
}

export default { getAll, create, get, update, remove};