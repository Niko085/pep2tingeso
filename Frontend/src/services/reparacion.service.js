import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/historialreparaciones/reparacion/');
}

const create = data => {
    return httpClient.post("/historialreparaciones/reparacion/", data);
}

const get = id => {
    return httpClient.get(`/historialreparaciones/reparacion/${id}`);
}

const update = data => {
    return httpClient.put('/historialreparaciones/reparacion/', data);
}

const remove = id => {
    return httpClient.delete(`/historialreparaciones/reparacion/${id}`);
}
export default { getAll, create, get, update, remove };