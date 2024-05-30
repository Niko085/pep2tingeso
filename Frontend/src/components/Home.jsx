import React from "react";
import carImage from "../assets/car-image.jpg"; // Importa la imagen desde tu directorio de assets

const Home = () => {
  return (
    <div>
      <h1>SiGRA: Sistema de Gestión de Reparaciones de Automoviles</h1>
      <p>
        SiGRA es una aplicación web para la gestión de reparaciones de
        automoviles. Esta aplicación ha sido desarrollada usando tecnologías
        como{" "}
        <a href="https://spring.io/projects/spring-boot">Spring Boot</a> (para
        el desarrollo del backend), <a href="https://reactjs.org/">React</a>{" "}
        (para el desarrollo del Frontend) y{" "}
        <a href="https://www.postgresql.org/">PostgreSQL</a> (para la base de
        datos).
      </p>
      <img src={carImage} alt="Automovil" style={{ width: "500px" }} />
    </div>
  );
};

export default Home;
