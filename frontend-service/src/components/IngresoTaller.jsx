import React from 'react';
import { Link } from 'react-router-dom';
import Button from '@mui/material/Button';
import DriveEtaIcon from '@mui/icons-material/DriveEta';
import AddIcon from '@mui/icons-material/Add';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import AddBoxIcon from '@mui/icons-material/AddBox';
import QueueIcon from '@mui/icons-material/Queue';

const IngresoTaller = () => {
    return (
        <div className="container">
            <h1>¿Primera vez en el taller?</h1>
            <Link
                to="/automovil/add"
                style={{ textDecoration: "none" }}
            >
                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<QueueIcon />}
                    style={{ marginTop: "1.5rem", marginRight: "2.5rem", fontSize: "1.5rem"}} // Ajustando el margen superior del botón y a la derecha y tamaño de letra
                >
                    Añadir Automóvil
                </Button>
            </Link>

            <Link
                to="/historialreparaciones/add"
                style={{ textDecoration: "none" }}
            >
                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<DriveEtaIcon />}
                    style={{ marginTop: "1.5rem", marginLeft: "2.5rem", fontSize: "1.5rem" }} // Ajustando el margen superior del botón y a la izquierda
                    
                >
                    Ya registrado
                </Button>
            </Link>
        </div>
    );
};

export default IngresoTaller;
