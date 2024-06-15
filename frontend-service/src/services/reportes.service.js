import httpClient from "../http-common";

const getReporteReparacionesVsTipoAuto = ({ mes, ano }) => {
    return httpClient.get(`/reportes/reporte/reparaciones-vs-tipo-autos/${mes}/${ano}`);
}

const getReporteCompararMeses = ({ mes, ano }) => {
    return httpClient.get(`/reportes/reporte/compararMeses/${mes}/${ano}`);
}

export default { getReporteReparacionesVsTipoAuto, getReporteCompararMeses };