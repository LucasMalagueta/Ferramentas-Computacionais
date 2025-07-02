--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6
-- Dumped by pg_dump version 16.6

-- Started on 2025-05-08 20:29:34

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
-- TOC entry 215 (class 1259 OID 16399)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    cat_id integer NOT NULL,
    cat_nome character varying(15) NOT NULL
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16402)
-- Name: categoria_cat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_cat_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_cat_id_seq OWNER TO postgres;

--
-- TOC entry 4882 (class 0 OID 0)
-- Dependencies: 216
-- Name: categoria_cat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_cat_id_seq OWNED BY public.categoria.cat_id;


--
-- TOC entry 217 (class 1259 OID 16403)
-- Name: comanda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comanda (
    com_id integer NOT NULL,
    com_numero numeric(15,0) NOT NULL,
    com_data date,
    com_desc character varying(255) NOT NULL,
    com_valor numeric(8,2),
    com_status character(1),
    tpg_id integer
);


ALTER TABLE public.comanda OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16406)
-- Name: comanda_com_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comanda_com_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.comanda_com_id_seq OWNER TO postgres;

--
-- TOC entry 4883 (class 0 OID 0)
-- Dependencies: 218
-- Name: comanda_com_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comanda_com_id_seq OWNED BY public.comanda.com_id;


--
-- TOC entry 219 (class 1259 OID 16413)
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item (
    com_id integer NOT NULL,
    prod_id integer NOT NULL,
    it_quantidade integer NOT NULL
);


ALTER TABLE public.item OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16416)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    prod_id integer NOT NULL,
    cat_id integer NOT NULL,
    prod_nome character varying(30) NOT NULL,
    prod_preco numeric(8,2) NOT NULL,
    prod_descr character varying(100)
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16419)
-- Name: produto_prod_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_prod_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produto_prod_id_seq OWNER TO postgres;

--
-- TOC entry 4884 (class 0 OID 0)
-- Dependencies: 221
-- Name: produto_prod_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_prod_id_seq OWNED BY public.produto.prod_id;


--
-- TOC entry 222 (class 1259 OID 16466)
-- Name: tipo_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_pagamento (
    tpg_id integer NOT NULL,
    tpg_descr character varying(20)
);


ALTER TABLE public.tipo_pagamento OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16469)
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_pagamento_tpg_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tipo_pagamento_tpg_id_seq OWNER TO postgres;

--
-- TOC entry 4885 (class 0 OID 0)
-- Dependencies: 223
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_pagamento_tpg_id_seq OWNED BY public.tipo_pagamento.tpg_id;


--
-- TOC entry 4707 (class 2604 OID 16424)
-- Name: categoria cat_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN cat_id SET DEFAULT nextval('public.categoria_cat_id_seq'::regclass);


--
-- TOC entry 4708 (class 2604 OID 16425)
-- Name: comanda com_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda ALTER COLUMN com_id SET DEFAULT nextval('public.comanda_com_id_seq'::regclass);


--
-- TOC entry 4709 (class 2604 OID 16427)
-- Name: produto prod_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN prod_id SET DEFAULT nextval('public.produto_prod_id_seq'::regclass);


--
-- TOC entry 4710 (class 2604 OID 16470)
-- Name: tipo_pagamento tpg_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento ALTER COLUMN tpg_id SET DEFAULT nextval('public.tipo_pagamento_tpg_id_seq'::regclass);


--
-- TOC entry 4868 (class 0 OID 16399)
-- Dependencies: 215
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoria VALUES (1, 'Bebida');
INSERT INTO public.categoria VALUES (3, 'Sobremesa');
INSERT INTO public.categoria VALUES (2, 'Prato');
INSERT INTO public.categoria VALUES (4, 'Porção');


--
-- TOC entry 4870 (class 0 OID 16403)
-- Dependencies: 217
-- Data for Name: comanda; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.comanda VALUES (1, 123, '2024-05-05', 'mesa do salao 2', 100.00, 'F', 1);


--
-- TOC entry 4872 (class 0 OID 16413)
-- Dependencies: 219
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.item VALUES (1, 1, 10);
INSERT INTO public.item VALUES (1, 2, 3);


--
-- TOC entry 4873 (class 0 OID 16416)
-- Dependencies: 220
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produto VALUES (1, 1, 'Vinho taça', 9.99, 'Importado');
INSERT INTO public.produto VALUES (2, 1, 'Chopp copo', 5.80, 'chopp artesanal');
INSERT INTO public.produto VALUES (3, 1, 'vodka dose', 8.00, 'importada');
INSERT INTO public.produto VALUES (4, 1, 'suco de laranja 500ml', 5.60, 'natural');
INSERT INTO public.produto VALUES (5, 1, 'suco de uva 500ml', 4.50, 'natural');
INSERT INTO public.produto VALUES (6, 1, 'cerveja heineken longneck', 12.00, 'super gelada');
INSERT INTO public.produto VALUES (7, 2, 'Batata frita porção média', 18.00, 'porção grande, acompanha molho');


--
-- TOC entry 4875 (class 0 OID 16466)
-- Dependencies: 222
-- Data for Name: tipo_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_pagamento VALUES (1, 'PIX');
INSERT INTO public.tipo_pagamento VALUES (2, 'Cartão Crédito');
INSERT INTO public.tipo_pagamento VALUES (3, 'Cartão Débito');


--
-- TOC entry 4886 (class 0 OID 0)
-- Dependencies: 216
-- Name: categoria_cat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_cat_id_seq', 4, true);


--
-- TOC entry 4887 (class 0 OID 0)
-- Dependencies: 218
-- Name: comanda_com_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comanda_com_id_seq', 1, true);


--
-- TOC entry 4888 (class 0 OID 0)
-- Dependencies: 221
-- Name: produto_prod_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_prod_id_seq', 7, true);


--
-- TOC entry 4889 (class 0 OID 0)
-- Dependencies: 223
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_pagamento_tpg_id_seq', 3, true);


--
-- TOC entry 4716 (class 2606 OID 16430)
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (com_id, prod_id);


--
-- TOC entry 4712 (class 2606 OID 16432)
-- Name: categoria pk_categoria; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT pk_categoria PRIMARY KEY (cat_id);


--
-- TOC entry 4714 (class 2606 OID 16434)
-- Name: comanda pk_comanda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT pk_comanda PRIMARY KEY (com_id);


--
-- TOC entry 4718 (class 2606 OID 16438)
-- Name: produto pk_produto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT pk_produto PRIMARY KEY (prod_id);


--
-- TOC entry 4720 (class 2606 OID 16475)
-- Name: tipo_pagamento tipo_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento
    ADD CONSTRAINT tipo_pagamento_pkey PRIMARY KEY (tpg_id);


--
-- TOC entry 4721 (class 2606 OID 16476)
-- Name: comanda comanda_tpg_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT comanda_tpg_id_fkey FOREIGN KEY (tpg_id) REFERENCES public.tipo_pagamento(tpg_id) NOT VALID;


--
-- TOC entry 4722 (class 2606 OID 16446)
-- Name: item fk_itemcomd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT fk_itemcomd FOREIGN KEY (com_id) REFERENCES public.comanda(com_id);


--
-- TOC entry 4723 (class 2606 OID 16451)
-- Name: item fk_itemprod; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT fk_itemprod FOREIGN KEY (prod_id) REFERENCES public.produto(prod_id);


--
-- TOC entry 4724 (class 2606 OID 16456)
-- Name: produto fk_prodcat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_prodcat FOREIGN KEY (cat_id) REFERENCES public.categoria(cat_id);


-- Completed on 2025-05-08 20:29:34

--
-- PostgreSQL database dump complete
--

