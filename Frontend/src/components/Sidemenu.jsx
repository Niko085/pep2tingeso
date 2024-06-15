import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import PaidIcon from "@mui/icons-material/Paid";
import CalculateIcon from "@mui/icons-material/Calculate";
import AnalyticsIcon from "@mui/icons-material/Analytics";
import DiscountIcon from "@mui/icons-material/Discount";
import HailIcon from "@mui/icons-material/Hail";
import MedicationLiquidIcon from "@mui/icons-material/MedicationLiquid";
import MoreTimeIcon from "@mui/icons-material/MoreTime";
import HomeIcon from "@mui/icons-material/Home";
import HistoryIcon from "@mui/icons-material/History";
import TimelineIcon from "@mui/icons-material/Timeline";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import { useNavigate } from "react-router-dom";
import SummarizeIcon from '@mui/icons-material/Summarize';
import BuildIcon from '@mui/icons-material/Build';
import FeedIcon from '@mui/icons-material/Feed';
import ConstructionIcon from '@mui/icons-material/Construction';
import NoCrashIcon from '@mui/icons-material/NoCrash';

export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const listOptions = () => (
    <Box
      role="presentation"
      onClick={toggleDrawer(false)}
      sx={{ width: 240 }} // Cambia el ancho del Drawer
    >
      <List>
        <ListItemButton onClick={() => navigate("/home")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Home" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/ingresoTaller")}>
          <ListItemIcon>
          <BuildIcon/>
          </ListItemIcon>
          <ListItemText primary="Ingresar al taller" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/retiroTaller")}>
          <ListItemIcon>
          <NoCrashIcon />
          </ListItemIcon>
          <ListItemText primary="Retirar del Taller" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/automovil/list")}>
          <ListItemIcon>
          <DirectionsCarIcon />
          </ListItemIcon>
          <ListItemText primary="Automoviles" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/historialReparaciones/list")}>
          <ListItemIcon>
            <HistoryIcon />
          </ListItemIcon>
          <ListItemText primary="Listado de Registros" />
        </ListItemButton>

        {/*<ListItemButton onClick={() => navigate("/agregarReparacion/list")}>
          <ListItemIcon>
            <HistoryIcon />
          </ListItemIcon>
          <ListItemText primary="Ingresar reparaciones" />
  </ListItemButton>*/}


        <ListItemButton onClick={() => navigate("/reparaciones/list")}>
          <ListItemIcon>
            <ConstructionIcon />
          </ListItemIcon>
          <ListItemText primary="Listado de Reparaciones" />
        </ListItemButton>
      </List>

      <Divider />

      <List>
        <ListItemButton onClick={() => navigate("/reportes/ReporteReparacionesVsTiposAutos")}>
          <ListItemIcon>
            <FeedIcon />
          </ListItemIcon>
          <ListItemText primary="Reporte reparaciones Vs tipo de automovil" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/reportes/ReporteCompararMeses")}>
          <ListItemIcon>
            <FeedIcon />
          </ListItemIcon>
          <ListItemText primary="Reporte de comparación de meses" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/bonos/list")}>
          <ListItemIcon>
            <SummarizeIcon />
          </ListItemIcon>
          <ListItemText primary="Datos de descuento por bonos" />
        </ListItemButton>

      </List>
    </Box>
  );

  return (
    <div>
      <Drawer anchor={"left"} open={open} onClose={toggleDrawer(false)}>
        {listOptions()}
      </Drawer>
    </div>
  );
}
