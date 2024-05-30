import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/historialreparaciones/');
}

const create = data => {
    return httpClient.post("/api/historialreparaciones/", data);
}

const get = id => {
    return httpClient.get(`/api/historialreparaciones/${id}`);
}

const update = data => {
    return httpClient.put('/api/historialreparaciones/', data);
}

const remove = id => {
    return httpClient.delete(`/api/historialreparaciones/${id}`);
}

export default { getAll, create, get, update, remove};