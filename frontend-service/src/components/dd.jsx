const [reparaciones_disponibles, setReparacionesDisponibles] = useState([]);
	const [reparaciones_seleccionadas, setReparacionesSeleccionadas] = useState([]);

    const manejarSeleccionarReparacion = (index) => {
        setReparacionesSeleccionadas([...reparaciones_seleccionadas, reparaciones_disponibles[index]].sort((a, b) => a.numero - b.numero));

        const reparaciones_disponibles_aux = reparaciones_disponibles.filter(function(_, i) {
            return i != index;
        });
        
        setReparacionesDisponibles(reparaciones_disponibles_aux);
    }

    const manejarEliminarReparacion = (index) => {
        setReparacionesDisponibles([...reparaciones_disponibles, reparaciones_seleccionadas[index]].sort((a, b) => a.numero - b.numero));

        const reparaciones_seleccionadas_aux = reparaciones_seleccionadas.filter(function(_, i) {
            return i != index;
        });
        
        setReparacionesSeleccionadas(reparaciones_seleccionadas_aux);
    }





<div>
                            <label for="patente" >Reparaciones disponibles</label>
                            <select id="patente" onChange={(e) => (e.target.value !== "" ? manejarSeleccionarReparacion(e.target.value) : null)} required>
                                <option value="">Elegir reparación</option>
                                {
                                    reparaciones_disponibles.map((reparacion, index) => (
                                        <option key={index} value={index}>
                                            {reparacion.value} - {reparacion.label}
                                        </option>
                                    ))    
                                }
                            </select>
                        </div>
                        <div>
                            <label for="patente" >Reparaciones seleccionadas</label>
                            <select id="patente"  onChange={(e) => (e.target.value !== "" ? manejarEliminarReparacion(e.target.value) : null)} required>
                                <option value="">Revisar selección</option>
                                {
                                    reparaciones_seleccionadas.map((reparacion, index) => (
                                        <option key={index} value={index}>
                                            {reparacion.value} - {reparacion.label}
                                        </option>
                                    ))
                                }
                            </select>
                        </div>



const reparaciones = [
    { value: 1, label: "Reparaciones del Sistema de Frenos" },
    { value: 2, label: "Servicio del Sistema de Refrigeración" },
    { value: 3, label: "Reparaciones del Motor" },
    { value: 4, label: "Reparaciones de la Transmisión" },
    { value: 5, label: "Reparación del Sistema Eléctrico" },
    { value: 6, label: "Reparaciones del Sistema de Escape" },
    { value: 7, label: "Reparación de Neumáticos y Ruedas" },
    { value: 8, label: "Reparaciones de la Suspensión y la Dirección" },
    { value: 9, label: "Reparación del Sistema de Aire Acondicionado y Calefacción" },
    { value: 10, label: "Reparaciones del Sistema de Combustible" },
    { value: 11, label: "Reparación y Reemplazo del Parabrisas y Cristales" }
];