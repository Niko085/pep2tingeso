import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import AutomovilesList from './components/AutomovilesList';
import AddEditAutomovil from './components/AddEditAutomovil';
import NotFound from './components/NotFound';
import HistorialReparacionesList from './components/HistorialReparacionesList';
import AddEditHistorialReparaciones from './components/AddEditHistorialReparaciones';
import IngresarAutomovil from './components/IngresarAutomovil';
import ReparacionesList from './components/ReparacionesList';
import AddEditReparacion from './components/AddEditReparaciones';
import ReporteTiempoPromedio from './components/ReporteTiempoPromedio';
import ReporteReparacionesVsTiposAutos from './components/ReporteReparacionesVsTipoAutos';
import ReporteReparacionesVsTipoMotor from './components/ReporteReparacionesVsTipoMotor';
import ReparacionSelectionForm from './components/SeleccionarReparacion';
import BonosList from './components/DescuentoBonosList';
import AddEditBono from './components/AddEditDescuentoBonos';

function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/" element={<Home/>} />
              <Route path="/home" element={<Home/>} />
              <Route path="/automovil/list" element={<AutomovilesList/>} />
              <Route path="/automovil/add" element={<AddEditAutomovil/>} />
              <Route path="/automovil/edit/:id" element={<AddEditAutomovil/>} />

              <Route path="/historialReparaciones/list" element={<HistorialReparacionesList/>} />
              <Route path="/historialReparaciones/add" element={<AddEditHistorialReparaciones/>} />
              <Route path="/historialReparaciones/edit/:id" element={<AddEditHistorialReparaciones/>} />


              <Route path="/ingresarAutomovil/add" element={<IngresarAutomovil/>} />
              <Route path="/ingresarAutomovil/edit/:id" element={<IngresarAutomovil/>} />

              <Route path="/reparaciones/list" element={<ReparacionesList/>} />
              <Route path="/reparaciones/add" element={<AddEditReparacion/>} />
              <Route path="/reparaciones/edit/:id" element={<AddEditReparacion/>} />

              <Route path="/reportes/ReporteTiempoPromedio" element={<ReporteTiempoPromedio/>} />
              <Route path="/reportes/ReporteReparacionesVsTiposAutos" element={<ReporteReparacionesVsTiposAutos/>} />
              <Route path="/reportes/ReporteReparacionesVsTipoMotor" element={<ReporteReparacionesVsTipoMotor/>} />

              <Route path="/agregarReparacion/list" element={<ReparacionSelectionForm/>} />
              <Route path="/reparaciones/select/:idH/:patenteH" element={<ReparacionSelectionForm />} />

              <Route path="/bonos/list" element={<BonosList/>} />
              <Route path="/bonos/add" element={<AddEditBono/>} />
              <Route path="/bonos/edit/:id" element={<AddEditBono/>} />

              <Route path="*" element={<NotFound/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App
