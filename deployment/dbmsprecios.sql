-- Conectar a la base de datos `postgres`
\c postgres;

-- Crear la nueva base de datos
CREATE DATABASE "dbmsprecios";

-- Conectar a la nueva base de datos
\c "dbmsprecios";
--
-- PostgreSQL database dump
--


-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: valorreparaciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.valorreparaciones (
    id bigint NOT NULL,
    descripcion character varying(255),
    monto integer NOT NULL,
    numero_reparacion integer NOT NULL,
    tipo_motor character varying(255),
    tipo_reparacion character varying(255)
);


ALTER TABLE public.valorreparaciones OWNER TO postgres;

--
-- Name: valorreparaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.valorreparaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.valorreparaciones_id_seq OWNER TO postgres;

--
-- Name: valorreparaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.valorreparaciones_id_seq OWNED BY public.valorreparaciones.id;


--
-- Name: valorreparaciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.valorreparaciones ALTER COLUMN id SET DEFAULT nextval('public.valorreparaciones_id_seq'::regclass);


--
-- Data for Name: valorreparaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.valorreparaciones (id, descripcion, monto, numero_reparacion, tipo_motor, tipo_reparacion) FROM stdin;
1	Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y Reparacioon o reemplazo del cilindro maestro de frenos.	120000	1	Gasolina	Reparaciones del Sistema de Frenos
2	Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y Reparacioon o reemplazo del cilindro maestro de frenos.	120000	1	Diesel	Reparaciones del Sistema de Frenos
3	Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y Reparacioon o reemplazo del cilindro maestro de frenos.	180000	1	Hibrido	Reparaciones del Sistema de Frenos
4	Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y Reparacioon o reemplazo del cilindro maestro de frenos.	220000	1	Electrico	Reparaciones del Sistema de Frenos
5	Reparacioon o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.	130000	2	Gasolina	Servicio del Sistema de Refrigeración
6	Reparacioon o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.	130000	2	Diesel	Servicio del Sistema de Refrigeración
7	Reparacioon o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.	190000	2	Hibrido	Servicio del Sistema de Refrigeración
8	Reparacioon o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.	230000	2	Electrico	Servicio del Sistema de Refrigeración
9	Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la Reparacioon de la junta de la culata.	350000	3	Gasolina	Reparaciones del Motor
10	Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la Reparacioon de la junta de la culata.	450000	3	Diesel	Reparaciones del Motor
11	Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la Reparacioon de la junta de la culata.	700000	3	Hibrido	Reparaciones del Motor
12	Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la Reparacioon de la junta de la culata.	800000	3	Electrico	Reparaciones del Motor
13	Incluyen la Reparacioon o reemplazo de componentes de la transmisión manual o automática, cambios de líquido y solución de problemas de cambios de marcha.	210000	4	Gasolina	Reparaciones de la Transmisión
14	Incluyen la Reparacioon o reemplazo de componentes de la transmisión manual o automática, cambios de líquido y solución de problemas de cambios de marcha.	210000	4	Diesel	Reparaciones de la Transmisión
15	Incluyen la Reparacioon o reemplazo de componentes de la transmisión manual o automática, cambios de líquido y solución de problemas de cambios de marcha.	300000	4	Hibrido	Reparaciones de la Transmisión
16	Incluyen la Reparacioon o reemplazo de componentes de la transmisión manual o automática, cambios de líquido y solución de problemas de cambios de marcha.	300000	4	Electrico	Reparaciones de la Transmisión
17	Solución de problemas y Reparacioon de alternadores, arrancadores, baterías y sistemas de cableado, así como la Reparacioon de componentes Electricos como faros, intermitentes y sistemas de entretenimiento.	150000	5	Gasolina	Reparaciones del Sistema Electrico
18	Solución de problemas y Reparacioon de alternadores, arrancadores, baterías y sistemas de cableado, así como la Reparacioon de componentes Electricos como faros, intermitentes y sistemas de entretenimiento.	150000	5	Diesel	Reparaciones del Sistema Electrico
19	Solución de problemas y Reparacioon de alternadores, arrancadores, baterías y sistemas de cableado, así como la Reparacioon de componentes Electricos como faros, intermitentes y sistemas de entretenimiento.	200000	5	Hibrido	Reparaciones del Sistema Electrico
20	Solución de problemas y Reparacioon de alternadores, arrancadores, baterías y sistemas de cableado, así como la Reparacioon de componentes Electricos como faros, intermitentes y sistemas de entretenimiento.	250000	5	Electrico	Reparaciones del Sistema Electrico
21	Incluye el reemplazo del silenciador, tubos de escape, catalizador y la solución de problemas relacionados con las emisiones.	100000	6	Gasolina	Reparaciones del Sistema de Escape
22	Incluye el reemplazo del silenciador, tubos de escape, catalizador y la solución de problemas relacionados con las emisiones.	120000	6	Diesel	Reparaciones del Sistema de Escape
23	Incluye el reemplazo del silenciador, tubos de escape, catalizador y la solución de problemas relacionados con las emisiones.	450000	6	Hibrido	Reparaciones del Sistema de Escape
24	Incluye el reemplazo del silenciador, tubos de escape, catalizador y la solución de problemas relacionados con las emisiones.	0	6	Electrico	Reparaciones del Sistema de Escape
25	Reparacioon de pinchazos, reemplazo de neumáticos, alineación y balanceo de ruedas.	100000	7	Gasolina	Reparacioon de Neumáticos y Ruedas
26	Reparacioon de pinchazos, reemplazo de neumáticos, alineación y balanceo de ruedas.	100000	7	Diesel	Reparacioon de Neumáticos y Ruedas
27	Reparacioon de pinchazos, reemplazo de neumáticos, alineación y balanceo de ruedas.	100000	7	Hibrido	Reparacioon de Neumáticos y Ruedas
28	Reparacioon de pinchazos, reemplazo de neumáticos, alineación y balanceo de ruedas.	100000	7	Electrico	Reparacioon de Neumáticos y Ruedas
29	Reemplazo de amortiguadores, brazos de control, rótulas y Reparacioon del sistema de dirección asistida.	180000	8	Gasolina	Reparaciones de la Suspensión y la Dirección
30	Reemplazo de amortiguadores, brazos de control, rótulas y Reparacioon del sistema de dirección asistida.	180000	8	Diesel	Reparaciones de la Suspensión y la Dirección
31	Reemplazo de amortiguadores, brazos de control, rótulas y Reparacioon del sistema de dirección asistida.	210000	8	Hibrido	Reparaciones de la Suspensión y la Dirección
32	Reemplazo de amortiguadores, brazos de control, rótulas y Reparacioon del sistema de dirección asistida.	250000	8	Electrico	Reparaciones de la Suspensión y la Dirección
33	Incluye la recarga de refrigerante, Reparacioon o reemplazo del compresor, y solución de problemas del sistema de calefacción.	150000	9	Gasolina	Reparacioon del Sistema de Aire Acondicionado y Calefacción
34	Incluye la recarga de refrigerante, Reparacioon o reemplazo del compresor, y solución de problemas del sistema de calefacción.	150000	9	Diesel	Reparacioon del Sistema de Aire Acondicionado y Calefacción
35	Incluye la recarga de refrigerante, Reparacioon o reemplazo del compresor, y solución de problemas del sistema de calefacción.	180000	9	Hibrido	Reparacioon del Sistema de Aire Acondicionado y Calefacción
36	Incluye la recarga de refrigerante, Reparacioon o reemplazo del compresor, y solución de problemas del sistema de calefacción.	180000	9	Electrico	Reparacioon del Sistema de Aire Acondicionado y Calefacción
37	Limpieza o reemplazo de inyectores de combustible, Reparacioon o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.	130000	10	Gasolina	Reparaciones del Sistema de Combustible
38	Limpieza o reemplazo de inyectores de combustible, Reparacioon o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.	140000	10	Diesel	Reparaciones del Sistema de Combustible
39	Limpieza o reemplazo de inyectores de combustible, Reparacioon o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.	220000	10	Hibrido	Reparaciones del Sistema de Combustible
40	Limpieza o reemplazo de inyectores de combustible, Reparacioon o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.	0	10	Electrico	Reparaciones del Sistema de Combustible
41	Reparacion de pequeñas grietas en el parabrisas o reemplazo completo de parabrisas y ventanas dañadas.	80000	11	Gasolina	Reparacioon y Reemplazo del Parabrisas y Cristales
42	Reparacion de pequeñas grietas en el parabrisas o reemplazo completo de parabrisas y ventanas dañadas.	80000	11	Diesel	Reparacioon y Reemplazo del Parabrisas y Cristales
43	Reparacion de pequeñas grietas en el parabrisas o reemplazo completo de parabrisas y ventanas dañadas.	80000	11	Hibrido	Reparacioon y Reemplazo del Parabrisas y Cristales
44	Reparacion de pequeñas grietas en el parabrisas o reemplazo completo de parabrisas y ventanas dañadas.	80000	11	Electrico	Reparacioon y Reemplazo del Parabrisas y Cristales
\.


--
-- Name: valorreparaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.valorreparaciones_id_seq', 44, true);


--
-- Name: valorreparaciones valorreparaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.valorreparaciones
    ADD CONSTRAINT valorreparaciones_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

