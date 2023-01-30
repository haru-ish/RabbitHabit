--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5 (Ubuntu 14.5-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.5 (Ubuntu 14.5-0ubuntu0.22.04.1)

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
-- Name: log_info; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.log_info (
    id integer NOT NULL,
    user_uid text NOT NULL,
    mood integer NOT NULL,
    selected_one integer NOT NULL,
    selected_two integer NOT NULL,
    selected_three integer NOT NULL,
    memo character varying(100),
    date timestamp with time zone NOT NULL,
    delete_flg integer NOT NULL,
    user_id integer
);


--
-- Name: log_info_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.log_info_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: log_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.log_info_id_seq OWNED BY public.log_info.id;


--
-- Name: log_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.log_user (
    id integer NOT NULL,
    user_uid text NOT NULL
);


--
-- Name: log_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.log_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: log_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.log_user_id_seq OWNED BY public.log_user.id;


--
-- PostgreSQL database dump complete
--

