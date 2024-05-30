import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/historialreparaciones/datosbonos/');
}

const create = data => {
    return httpClient.post("/historialreparaciones/datosbonos/", data);
}

const get = id => {
    return httpClient.get(`/historialreparaciones/datosbonos/${id}`);
}

const update = data => {
    return httpClient.put('/historialreparaciones/datosbonos/', data);
}

const remove = id => {
    return httpClient.delete(`/historialreparaciones/datosbonos/${id}`);
}
export default { getAll, create, get, update, remove };