-- Conectar a la base de datos `postgres`
\c postgres;

-- Crear la nueva base de datos
CREATE DATABASE "dbmsvehiculos";

-- Conectar a la nueva base de datos
\c "dbmsvehiculos";
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
-- Name: automoviles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.automoviles (
    anio_fabricacion integer NOT NULL,
    cant_asientos integer NOT NULL,
    kilometraje integer NOT NULL,
    id bigint NOT NULL,
    marca character varying(255),
    modelo character varying(255),
    motor character varying(255),
    patente character varying(255),
    tipo character varying(255)
);


ALTER TABLE public.automoviles OWNER TO postgres;

--
-- Name: automoviles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.automoviles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.automoviles_id_seq OWNER TO postgres;

--
-- Name: automoviles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.automoviles_id_seq OWNED BY public.automoviles.id;


--
-- Name: automoviles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.automoviles ALTER COLUMN id SET DEFAULT nextval('public.automoviles_id_seq'::regclass);


--
-- Data for Name: automoviles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.automoviles (anio_fabricacion, cant_asientos, kilometraje, id, marca, modelo, motor, patente, tipo) FROM stdin;
2010	5	15000	1	Hyundai	Getz	Gasolina	CFYF55	Sedan
2021	5	4000	2	Ford	Grand i10	Gasolina	TW6977	Hatchback
2015	5	10000	3	Toyota	Corolla	Hibrido	FHKJ23	Sedan
2018	5	35000	4	Ford	Ranger	Diesel	NMQP98	Pickup
2019	4	50000	5	Chevrolet	Spark	Gasolina	KDJW65	Hatchback
\.


--
-- Name: automoviles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.automoviles_id_seq', 5, true);


--
-- Name: automoviles automoviles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.automoviles
    ADD CONSTRAINT automoviles_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

