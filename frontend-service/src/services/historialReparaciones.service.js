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

const getAuto = patente => {
    return httpClient.get(`/historialreparaciones/patente/${patente}`);
}

const getNoPagadoByPatente = patente => {
    return httpClient.get(`/historialreparaciones/no-pagado/${patente}`);
}

const calcular = patente => {
    return httpClient.get(`/historialreparaciones/calculate?patente=${patente}`);
}


export default { getAll, create, get, update, remove, getAuto, getNoPagadoByPatente, calcular};