--
-- PostgreSQL database dump
--

\restrict k9oefUjAxulTUWnq3wsggFBuynfZXiJRN7CbpBsqrpOCLwhS44yUQAjxjWsk5S3

-- Dumped from database version 14.24
-- Dumped by pg_dump version 14.24

-- Started on 2026-08-24 12:55:05

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

--
-- TOC entry 3522 (class 1262 OID 16394)
-- Name: loja_virtual_3m; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE loja_virtual_3m WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Portuguese_Brazil.1252';


ALTER DATABASE loja_virtual_3m OWNER TO postgres;

\unrestrict k9oefUjAxulTUWnq3wsggFBuynfZXiJRN7CbpBsqrpOCLwhS44yUQAjxjWsk5S3
\connect loja_virtual_3m
\restrict k9oefUjAxulTUWnq3wsggFBuynfZXiJRN7CbpBsqrpOCLwhS44yUQAjxjWsk5S3

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

--
-- TOC entry 249 (class 1255 OID 16926)
-- Name: validachavepessoa(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validachavepessoa() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare existe integer;

BEGIN

 existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_id);
 if(existe <=0) then
 	existe =(select count(1) from pessoa_juridica where id = NEW.pessoa_id);
	 if(existe <= 0) then
	   RAISE EXCEPTION 'Não foi encontrado o ID OU PK da pessoa para realizar a associação';
	 end if;
 end if;
 return new;
END;
$$;


ALTER FUNCTION public.validachavepessoa() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 211 (class 1259 OID 16407)
-- Name: acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.acesso (
    id bigint NOT NULL,
    acesso_desc character varying(255) NOT NULL
);


ALTER TABLE public.acesso OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 16929)
-- Name: avaliacao_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.avaliacao_produto (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    nota integer NOT NULL,
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.avaliacao_produto OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16412)
-- Name: categoria_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria_produto (
    id bigint NOT NULL,
    nome_descricao character varying(255) NOT NULL
);


ALTER TABLE public.categoria_produto OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16486)
-- Name: contas_pagar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contas_pagar (
    id bigint NOT NULL,
    data_pagamento date,
    valor_desconto numeric(38,2),
    valor_total numeric(38,2),
    pessoa_id bigint NOT NULL,
    pessoa_fornecedor_id bigint NOT NULL,
    data_vencimento date NOT NULL,
    descricao character varying(255) NOT NULL,
    status_conta_pagar character varying(255) NOT NULL,
    CONSTRAINT contas_pagar_status_conta_pagar_check CHECK (((status_conta_pagar)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying, 'ALUGUEL'::character varying, 'FUNCIONARIO'::character varying, 'NEGOCIADA'::character varying])::text[])))
);


ALTER TABLE public.contas_pagar OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16494)
-- Name: contas_receber; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contas_receber (
    id bigint NOT NULL,
    data_pagamento date,
    valor_desconto numeric(38,2),
    pessoa_id bigint NOT NULL,
    data_vencimento date NOT NULL,
    descricao character varying(255) NOT NULL,
    status_conta_receber character varying(255) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    CONSTRAINT contas_receber_status_conta_receber_check CHECK (((status_conta_receber)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying])::text[])))
);


ALTER TABLE public.contas_receber OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 16746)
-- Name: cumpom_desconto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cumpom_desconto (
    id bigint NOT NULL,
    codigo_descricao character varying(255) NOT NULL,
    data_validade_cumpom date NOT NULL,
    valor_porcentagem_desconto numeric(38,2),
    valor_real_desconto numeric(38,2)
);


ALTER TABLE public.cumpom_desconto OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 16706)
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.endereco (
    id bigint NOT NULL,
    bairro character varying(255) NOT NULL,
    cep character varying(255) NOT NULL,
    cidade character varying(255) NOT NULL,
    complemento character varying(255),
    numero character varying(255) NOT NULL,
    rua_logradouro character varying(255) NOT NULL,
    tipo_endereco character varying(255) NOT NULL,
    uf character varying(255) NOT NULL,
    pessoa_id bigint NOT NULL,
    CONSTRAINT endereco_tipo_endereco_check CHECK (((tipo_endereco)::text = ANY ((ARRAY['COBRANCA'::character varying, 'ENTREGA'::character varying])::text[])))
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 16724)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.forma_pagamento OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 16729)
-- Name: imagem_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.imagem_produto (
    id bigint NOT NULL,
    img_miniatura text NOT NULL,
    img_original text NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.imagem_produto OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 16688)
-- Name: item_venda_loja; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item_venda_loja (
    id bigint NOT NULL,
    produto_id bigint NOT NULL,
    venda_id bigint NOT NULL,
    quantidade double precision NOT NULL
);


ALTER TABLE public.item_venda_loja OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16401)
-- Name: marca_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.marca_produto (
    id bigint NOT NULL,
    nome_desc character varying(255),
    descricao_marca character varying(255) NOT NULL
);


ALTER TABLE public.marca_produto OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 16756)
-- Name: nota_fiscal_compra; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nota_fiscal_compra (
    id bigint NOT NULL,
    data_compra date NOT NULL,
    descricao_obs character varying(255) NOT NULL,
    numero_nota character varying(255) NOT NULL,
    serie_nota character varying(255) NOT NULL,
    valot_desconto numeric(38,2),
    valot_icms numeric(38,2) NOT NULL,
    valot_total numeric(38,2) NOT NULL,
    conta_pagar_id bigint NOT NULL,
    pessoa_id bigint NOT NULL
);


ALTER TABLE public.nota_fiscal_compra OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 16838)
-- Name: nota_fiscal_venda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nota_fiscal_venda (
    id bigint NOT NULL,
    numero character varying(255) NOT NULL,
    pdf text NOT NULL,
    serie character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    xml text NOT NULL,
    venda_compra_loja_virtual_id bigint NOT NULL
);


ALTER TABLE public.nota_fiscal_venda OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16556)
-- Name: nota_item_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nota_item_produto (
    id bigint NOT NULL,
    quantidade double precision NOT NULL,
    nota_fical_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.nota_item_produto OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16421)
-- Name: pessoa_fisica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa_fisica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    cpf character varying(255) NOT NULL,
    data_nascimento date NOT NULL
);


ALTER TABLE public.pessoa_fisica OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16428)
-- Name: pessoa_juridica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa_juridica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    categoria character varying(255),
    cnpj character varying(255) NOT NULL,
    inscricao_estadual character varying(255) NOT NULL,
    inscricao_municipal character varying(255),
    nome_fantasia character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL
);


ALTER TABLE public.pessoa_juridica OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16510)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    id bigint NOT NULL,
    alerta_qtde_estoque boolean,
    altura double precision NOT NULL,
    ativo boolean NOT NULL,
    descricao_produto text NOT NULL,
    largura double precision NOT NULL,
    link_youtube character varying(255),
    nome_produto character varying(255) NOT NULL,
    peso double precision NOT NULL,
    profundidade double precision NOT NULL,
    qtde_clique integer,
    quantidade_alerta_estoque integer,
    quantidade_estoque integer NOT NULL,
    tipounidade character varying(255) NOT NULL,
    valor_venda numeric(38,2) NOT NULL
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16419)
-- Name: seq_acesso; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_acesso
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_acesso OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 16677)
-- Name: seq_avaliacao_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_avaliacao_produto OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16420)
-- Name: seq_categoria_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_categoria_produto OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16502)
-- Name: seq_contas_pagar; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_contas_pagar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_contas_pagar OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16478)
-- Name: seq_contas_receber; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_contas_receber
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_contas_receber OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16509)
-- Name: seq_cumpom_desconto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_cumpom_desconto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_cumpom_desconto OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16443)
-- Name: seq_endereco; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_endereco OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16484)
-- Name: seq_forma_pagamento; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_forma_pagamento OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16525)
-- Name: seq_imagem_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_imagem_produto OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 16693)
-- Name: seq_item_venda_loja; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_item_venda_loja OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16406)
-- Name: seq_marca_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_marca_produto OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16550)
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_nota_fiscal_compra OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16593)
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_nota_fiscal_venda OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16561)
-- Name: seq_nota_item_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_nota_item_produto OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16435)
-- Name: seq_pessoa; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_pessoa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_pessoa OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16517)
-- Name: seq_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_produto OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16594)
-- Name: seq_status_rastreio; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_status_rastreio OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16452)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_usuario OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 16600)
-- Name: seq_venda_compra_loja_virtual; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_venda_compra_loja_virtual
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_venda_compra_loja_virtual OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16579)
-- Name: status_rastreio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.status_rastreio (
    id bigint NOT NULL,
    cento_distrubicao character varying(255),
    cidade character varying(255),
    estado character varying(255),
    status character varying(255),
    venda_compra_loja_virtual_id bigint NOT NULL
);


ALTER TABLE public.status_rastreio OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 16857)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    data_atual_senha date NOT NULL,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    pessoa_id bigint NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16453)
-- Name: usuario_acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_acesso (
    usuario_id bigint NOT NULL,
    acesso_id bigint NOT NULL
);


ALTER TABLE public.usuario_acesso OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 16864)
-- Name: venda_compra_loja_virtual; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.venda_compra_loja_virtual (
    id bigint NOT NULL,
    data_entrega date NOT NULL,
    data_venda date NOT NULL,
    dias_entrega integer NOT NULL,
    valor_desconto numeric(38,2),
    valor_frete numeric(38,2) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    cumpom_desconto_id bigint,
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    nota_fiscal_venda_id bigint NOT NULL
);


ALTER TABLE public.venda_compra_loja_virtual OWNER TO postgres;

--
-- TOC entry 3479 (class 0 OID 16407)
-- Dependencies: 211
-- Data for Name: acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3516 (class 0 OID 16929)
-- Dependencies: 248
-- Data for Name: avaliacao_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3480 (class 0 OID 16412)
-- Dependencies: 212
-- Data for Name: categoria_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3491 (class 0 OID 16486)
-- Dependencies: 223
-- Data for Name: contas_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3492 (class 0 OID 16494)
-- Dependencies: 224
-- Data for Name: contas_receber; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3511 (class 0 OID 16746)
-- Dependencies: 243
-- Data for Name: cumpom_desconto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3508 (class 0 OID 16706)
-- Dependencies: 240
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3509 (class 0 OID 16724)
-- Dependencies: 241
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3510 (class 0 OID 16729)
-- Dependencies: 242
-- Data for Name: imagem_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3506 (class 0 OID 16688)
-- Dependencies: 238
-- Data for Name: item_venda_loja; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3477 (class 0 OID 16401)
-- Dependencies: 209
-- Data for Name: marca_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3512 (class 0 OID 16756)
-- Dependencies: 244
-- Data for Name: nota_fiscal_compra; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3513 (class 0 OID 16838)
-- Dependencies: 245
-- Data for Name: nota_fiscal_venda; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3499 (class 0 OID 16556)
-- Dependencies: 231
-- Data for Name: nota_item_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3483 (class 0 OID 16421)
-- Dependencies: 215
-- Data for Name: pessoa_fisica; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento) VALUES (1, 'uhauahuahu', 'paulo', '18998169183', '5454', '1990-06-02');


--
-- TOC entry 3484 (class 0 OID 16428)
-- Dependencies: 216
-- Data for Name: pessoa_juridica; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3495 (class 0 OID 16510)
-- Dependencies: 227
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produto (id, alerta_qtde_estoque, altura, ativo, descricao_produto, largura, link_youtube, nome_produto, peso, profundidade, qtde_clique, quantidade_alerta_estoque, quantidade_estoque, tipounidade, valor_venda) VALUES (1, true, 10, true, 'produto teste', 50.2, 'okoko', 'nome produto teste', 5.2, 20.5, 10, 1, 20, 'un', 50.80);


--
-- TOC entry 3501 (class 0 OID 16579)
-- Dependencies: 233
-- Data for Name: status_rastreio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3514 (class 0 OID 16857)
-- Dependencies: 246
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3488 (class 0 OID 16453)
-- Dependencies: 220
-- Data for Name: usuario_acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3515 (class 0 OID 16864)
-- Dependencies: 247
-- Data for Name: venda_compra_loja_virtual; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3523 (class 0 OID 0)
-- Dependencies: 213
-- Name: seq_acesso; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_acesso', 1, false);


--
-- TOC entry 3524 (class 0 OID 0)
-- Dependencies: 237
-- Name: seq_avaliacao_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_avaliacao_produto', 1, false);


--
-- TOC entry 3525 (class 0 OID 0)
-- Dependencies: 214
-- Name: seq_categoria_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_categoria_produto', 1, false);


--
-- TOC entry 3526 (class 0 OID 0)
-- Dependencies: 225
-- Name: seq_contas_pagar; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_contas_pagar', 1, false);


--
-- TOC entry 3527 (class 0 OID 0)
-- Dependencies: 221
-- Name: seq_contas_receber; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_contas_receber', 1, false);


--
-- TOC entry 3528 (class 0 OID 0)
-- Dependencies: 226
-- Name: seq_cumpom_desconto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_cumpom_desconto', 1, false);


--
-- TOC entry 3529 (class 0 OID 0)
-- Dependencies: 218
-- Name: seq_endereco; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_endereco', 1, false);


--
-- TOC entry 3530 (class 0 OID 0)
-- Dependencies: 222
-- Name: seq_forma_pagamento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_forma_pagamento', 1, false);


--
-- TOC entry 3531 (class 0 OID 0)
-- Dependencies: 229
-- Name: seq_imagem_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_imagem_produto', 1, false);


--
-- TOC entry 3532 (class 0 OID 0)
-- Dependencies: 239
-- Name: seq_item_venda_loja; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_item_venda_loja', 1, false);


--
-- TOC entry 3533 (class 0 OID 0)
-- Dependencies: 210
-- Name: seq_marca_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_marca_produto', 1, false);


--
-- TOC entry 3534 (class 0 OID 0)
-- Dependencies: 230
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_compra', 1, false);


--
-- TOC entry 3535 (class 0 OID 0)
-- Dependencies: 234
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_venda', 1, false);


--
-- TOC entry 3536 (class 0 OID 0)
-- Dependencies: 232
-- Name: seq_nota_item_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_item_produto', 1, false);


--
-- TOC entry 3537 (class 0 OID 0)
-- Dependencies: 217
-- Name: seq_pessoa; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_pessoa', 1, false);


--
-- TOC entry 3538 (class 0 OID 0)
-- Dependencies: 228
-- Name: seq_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_produto', 1, false);


--
-- TOC entry 3539 (class 0 OID 0)
-- Dependencies: 235
-- Name: seq_status_rastreio; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_status_rastreio', 1, false);


--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 219
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_usuario', 1, false);


--
-- TOC entry 3541 (class 0 OID 0)
-- Dependencies: 236
-- Name: seq_venda_compra_loja_virtual; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_venda_compra_loja_virtual', 1, false);


--
-- TOC entry 3269 (class 2606 OID 16411)
-- Name: acesso acesso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acesso
    ADD CONSTRAINT acesso_pkey PRIMARY KEY (id);


--
-- TOC entry 3311 (class 2606 OID 16933)
-- Name: avaliacao_produto avaliacao_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT avaliacao_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3271 (class 2606 OID 16416)
-- Name: categoria_produto categoria_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT categoria_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3279 (class 2606 OID 16493)
-- Name: contas_pagar contas_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contas_pagar
    ADD CONSTRAINT contas_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3281 (class 2606 OID 16501)
-- Name: contas_receber contas_receber_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contas_receber
    ADD CONSTRAINT contas_receber_pkey PRIMARY KEY (id);


--
-- TOC entry 3297 (class 2606 OID 16750)
-- Name: cumpom_desconto cumpom_desconto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cumpom_desconto
    ADD CONSTRAINT cumpom_desconto_pkey PRIMARY KEY (id);


--
-- TOC entry 3291 (class 2606 OID 16713)
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 3293 (class 2606 OID 16728)
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 3295 (class 2606 OID 16735)
-- Name: imagem_produto imagem_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT imagem_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3289 (class 2606 OID 16692)
-- Name: item_venda_loja item_venda_loja_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT item_venda_loja_pkey PRIMARY KEY (id);


--
-- TOC entry 3267 (class 2606 OID 16405)
-- Name: marca_produto marca_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT marca_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3299 (class 2606 OID 16762)
-- Name: nota_fiscal_compra nota_fiscal_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT nota_fiscal_compra_pkey PRIMARY KEY (id);


--
-- TOC entry 3301 (class 2606 OID 16844)
-- Name: nota_fiscal_venda nota_fiscal_venda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT nota_fiscal_venda_pkey PRIMARY KEY (id);


--
-- TOC entry 3307 (class 2606 OID 16917)
-- Name: venda_compra_loja_virtual nota_fiscal_venda_uk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT nota_fiscal_venda_uk UNIQUE (nota_fiscal_venda_id);


--
-- TOC entry 3285 (class 2606 OID 16560)
-- Name: nota_item_produto nota_item_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_item_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3273 (class 2606 OID 16427)
-- Name: pessoa_fisica pessoa_fisica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id);


--
-- TOC entry 3275 (class 2606 OID 16434)
-- Name: pessoa_juridica pessoa_juridica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id);


--
-- TOC entry 3283 (class 2606 OID 16516)
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3287 (class 2606 OID 16585)
-- Name: status_rastreio status_rastreio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT status_rastreio_pkey PRIMARY KEY (id);


--
-- TOC entry 3277 (class 2606 OID 16457)
-- Name: usuario_acesso unique_acesso_usuario; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT unique_acesso_usuario UNIQUE (usuario_id, acesso_id);


--
-- TOC entry 3305 (class 2606 OID 16863)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3309 (class 2606 OID 16868)
-- Name: venda_compra_loja_virtual venda_compra_loja_virtual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT venda_compra_loja_virtual_pkey PRIMARY KEY (id);


--
-- TOC entry 3303 (class 2606 OID 16846)
-- Name: nota_fiscal_venda venda_compra_loja_virtual_uk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT venda_compra_loja_virtual_uk UNIQUE (venda_compra_loja_virtual_id);


--
-- TOC entry 3330 (class 2620 OID 16944)
-- Name: nota_fiscal_compra insertvalidachavepessoa_nota_fiscal_compra; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertvalidachavepessoa_nota_fiscal_compra BEFORE INSERT ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3332 (class 2620 OID 16946)
-- Name: usuario insertvalidachavepessoa_usuario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertvalidachavepessoa_usuario BEFORE INSERT ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3335 (class 2620 OID 16948)
-- Name: venda_compra_loja_virtual insertvalidachavepessoa_venda; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertvalidachavepessoa_venda BEFORE INSERT ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3336 (class 2620 OID 16940)
-- Name: avaliacao_produto insertvalidachavepessoaavaliacaoproduto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertvalidachavepessoaavaliacaoproduto BEFORE INSERT ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3328 (class 2620 OID 16942)
-- Name: contas_pagar insertvalidachavepessoacontaspagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insertvalidachavepessoacontaspagar BEFORE INSERT ON public.contas_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3331 (class 2620 OID 16943)
-- Name: nota_fiscal_compra validachavepessoa_nota_fiscal_compra; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoa_nota_fiscal_compra BEFORE UPDATE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3333 (class 2620 OID 16945)
-- Name: usuario validachavepessoa_usuario; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoa_usuario BEFORE UPDATE ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3334 (class 2620 OID 16947)
-- Name: venda_compra_loja_virtual validachavepessoa_venda; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoa_venda BEFORE UPDATE ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3337 (class 2620 OID 16939)
-- Name: avaliacao_produto validachavepessoaavaliacaoproduto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaavaliacaoproduto BEFORE UPDATE ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3329 (class 2620 OID 16941)
-- Name: contas_pagar validachavepessoacontaspagar; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontaspagar BEFORE UPDATE ON public.contas_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3312 (class 2606 OID 16460)
-- Name: usuario_acesso acesso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.acesso(id);


--
-- TOC entry 3320 (class 2606 OID 16763)
-- Name: nota_fiscal_compra conta_pagar_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT conta_pagar_fk FOREIGN KEY (conta_pagar_id) REFERENCES public.contas_pagar(id);


--
-- TOC entry 3322 (class 2606 OID 16891)
-- Name: venda_compra_loja_virtual cumpom_desconto_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT cumpom_desconto_id FOREIGN KEY (cumpom_desconto_id) REFERENCES public.cumpom_desconto(id);


--
-- TOC entry 3323 (class 2606 OID 16896)
-- Name: venda_compra_loja_virtual endereco_cobranca__fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_cobranca__fk FOREIGN KEY (endereco_cobranca_id) REFERENCES public.endereco(id);


--
-- TOC entry 3324 (class 2606 OID 16901)
-- Name: venda_compra_loja_virtual endereco_entrega__fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_entrega__fk FOREIGN KEY (endereco_entrega_id) REFERENCES public.endereco(id);


--
-- TOC entry 3325 (class 2606 OID 16906)
-- Name: venda_compra_loja_virtual forma_pagamento__fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT forma_pagamento__fk FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id);


--
-- TOC entry 3315 (class 2606 OID 16768)
-- Name: nota_item_produto nota_fiscal_compra_pk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_fiscal_compra_pk FOREIGN KEY (nota_fical_compra_id) REFERENCES public.nota_fiscal_compra(id);


--
-- TOC entry 3326 (class 2606 OID 16918)
-- Name: venda_compra_loja_virtual nota_fiscal_venda__fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT nota_fiscal_venda__fk FOREIGN KEY (nota_fiscal_venda_id) REFERENCES public.nota_fiscal_venda(id);


--
-- TOC entry 3327 (class 2606 OID 16934)
-- Name: avaliacao_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3319 (class 2606 OID 16736)
-- Name: imagem_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3317 (class 2606 OID 16694)
-- Name: item_venda_loja produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3314 (class 2606 OID 16567)
-- Name: nota_item_produto produto_pk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT produto_pk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3313 (class 2606 OID 16886)
-- Name: usuario_acesso usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3321 (class 2606 OID 16876)
-- Name: nota_fiscal_venda venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


--
-- TOC entry 3316 (class 2606 OID 16881)
-- Name: status_rastreio venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


--
-- TOC entry 3318 (class 2606 OID 16871)
-- Name: item_venda_loja venda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT venda_fk FOREIGN KEY (venda_id) REFERENCES public.venda_compra_loja_virtual(id);


-- Completed on 2026-08-24 12:55:06

--
-- PostgreSQL database dump complete
--

\unrestrict k9oefUjAxulTUWnq3wsggFBuynfZXiJRN7CbpBsqrpOCLwhS44yUQAjxjWsk5S3

