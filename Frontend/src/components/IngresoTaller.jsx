import React from 'react';
import { Link } from 'react-router-dom';
import Button from '@mui/material/Button';
import DriveEtaIcon from '@mui/icons-material/DriveEta';

const IngresoTaller = () => {

    return (
        <div className="container">
            <h1>¿Primera vez en el taller?</h1>
            <Link
                to="/automovil/add"
                style={{ textDecoration: "none", marginTop: "1rem" }}
            >
                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<DriveEtaIcon />}
                >
                    Añadir Automóvil
                </Button>
            </Link>


            <Link
                to="/automovil/add"
                style={{ textDecoration: "none", marginTop: "1rem" }}
            >
                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<DriveEtaIcon />}
                >
                    Ya registrado
                </Button>
            </Link>
        </div>
    );
};

export default IngresoTaller;
