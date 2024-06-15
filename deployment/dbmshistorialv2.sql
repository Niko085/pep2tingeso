-- Conectar a la base de datos `postgres`
\c postgres;

-- Crear la nueva base de datos
CREATE DATABASE "dbmshistorialv2";

-- Conectar a la nueva base de datos
\c "dbmshistorialv2";
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
-- Name: datos_bonos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.datos_bonos (
    cantidad_bonos integer NOT NULL,
    monto_bono integer NOT NULL,
    id bigint NOT NULL,
    marca_automovil character varying(255)
);


ALTER TABLE public.datos_bonos OWNER TO postgres;

--
-- Name: datos_bonos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.datos_bonos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.datos_bonos_id_seq OWNER TO postgres;

--
-- Name: datos_bonos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.datos_bonos_id_seq OWNED BY public.datos_bonos.id;


--
-- Name: historial_reparaciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historial_reparaciones (
    descuentos double precision NOT NULL,
    fecha_cliente_se_lleva_vehiculo date,
    fecha_ingreso_taller date,
    fecha_salida_taller date,
    hora_cliente_se_lleva_vehiculo time(6) without time zone,
    hora_ingreso_taller time(6) without time zone,
    hora_salida_taller time(6) without time zone,
    iva double precision NOT NULL,
    monto_sin_iva double precision NOT NULL,
    monto_total_pagar double precision NOT NULL,
    monto_total_reparaciones double precision NOT NULL,
    pagado boolean NOT NULL,
    recargos double precision NOT NULL,
    id bigint NOT NULL,
    patente character varying(255)
);


ALTER TABLE public.historial_reparaciones OWNER TO postgres;

--
-- Name: historial_reparaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.historial_reparaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.historial_reparaciones_id_seq OWNER TO postgres;

--
-- Name: historial_reparaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.historial_reparaciones_id_seq OWNED BY public.historial_reparaciones.id;


--
-- Name: reparaciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reparaciones (
    fecha_reparacion date,
    hora_reparacion time(6) without time zone,
    monto_reparacion double precision NOT NULL,
    tipo_reparacion integer NOT NULL,
    id bigint NOT NULL,
    id_historial_reparaciones bigint,
    descripcion character varying(255),
    patente character varying(255)
);


ALTER TABLE public.reparaciones OWNER TO postgres;

--
-- Name: reparaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reparaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reparaciones_id_seq OWNER TO postgres;

--
-- Name: reparaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reparaciones_id_seq OWNED BY public.reparaciones.id;


--
-- Name: datos_bonos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos_bonos ALTER COLUMN id SET DEFAULT nextval('public.datos_bonos_id_seq'::regclass);


--
-- Name: historial_reparaciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_reparaciones ALTER COLUMN id SET DEFAULT nextval('public.historial_reparaciones_id_seq'::regclass);


--
-- Name: reparaciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reparaciones ALTER COLUMN id SET DEFAULT nextval('public.reparaciones_id_seq'::regclass);


--
-- Data for Name: datos_bonos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.datos_bonos (cantidad_bonos, monto_bono, id, marca_automovil) FROM stdin;
5	70000	1	Toyota
2	50000	2	Ford
1	30000	3	Hyundai
7	40000	4	Honda
\.


--
-- Data for Name: historial_reparaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.historial_reparaciones (descuentos, fecha_cliente_se_lleva_vehiculo, fecha_ingreso_taller, fecha_salida_taller, hora_cliente_se_lleva_vehiculo, hora_ingreso_taller, hora_salida_taller, iva, monto_sin_iva, monto_total_pagar, monto_total_reparaciones, pagado, recargos, id, patente) FROM stdin;
43500	2022-01-21	2022-01-18	2022-01-20	15:25:00	10:30:00	10:30:00	53808	240327	337008	270000	t	56700	1	CFYF55
0	2023-12-20	2023-12-15	2023-12-19	13:27:00	12:55:00	13:30:00	0	0	0	0	f	0	2	TW6977
\.


--
-- Data for Name: reparaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reparaciones (fecha_reparacion, hora_reparacion, monto_reparacion, tipo_reparacion, id, id_historial_reparaciones, descripcion, patente) FROM stdin;
2022-01-18	10:30:00	120000	5	1	1	Reparación sistema eléctrico	CFYF55
2022-01-18	10:30:00	150000	1	2	1	Reparación sistema de frenos	CFYF55
2023-12-15	12:55:00	120000	1	3	2	Reparación sistema de frenos	TW6977
2023-12-15	12:55:00	130000	10	4	2	Reparación sistema de combustible	TW6977
\.


--
-- Name: datos_bonos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.datos_bonos_id_seq', 4, true);


--
-- Name: historial_reparaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.historial_reparaciones_id_seq', 2, true);


--
-- Name: reparaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reparaciones_id_seq', 4, true);


--
-- Name: datos_bonos datos_bonos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos_bonos
    ADD CONSTRAINT datos_bonos_pkey PRIMARY KEY (id);


--
-- Name: historial_reparaciones historial_reparaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_reparaciones
    ADD CONSTRAINT historial_reparaciones_pkey PRIMARY KEY (id);


--
-- Name: reparaciones reparaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reparaciones
    ADD CONSTRAINT reparaciones_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

