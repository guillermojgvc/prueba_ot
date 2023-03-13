-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler version: 1.0.1
-- PostgreSQL version: 15.0
-- Project Site: pgmodeler.io
-- Model Author: ---

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 
-- object: wallet_db | type: DATABASE --
-- DROP DATABASE IF EXISTS wallet_db;
-- CREATE DATABASE wallet_db
-- 	ENCODING = 'UTF8'
-- 	LC_COLLATE = 'en_US.utf8'
-- 	LC_CTYPE = 'en_US.utf8'
-- 	TABLESPACE = pg_default
-- 	OWNER = postgres;
-- ddl-end --


-- object: ontop | type: SCHEMA --
-- DROP SCHEMA IF EXISTS ontop CASCADE;
CREATE SCHEMA ontop;
-- ddl-end --
ALTER SCHEMA ontop OWNER TO postgres;
-- ddl-end --

-- object: provider | type: SCHEMA --
-- DROP SCHEMA IF EXISTS provider CASCADE;
CREATE SCHEMA provider;
-- ddl-end --
ALTER SCHEMA provider OWNER TO postgres;
-- ddl-end --

SET search_path TO pg_catalog,public,ontop,provider;
-- ddl-end --

-- object: ontop.tbl_user_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS ontop.tbl_user_id_seq CASCADE;
CREATE SEQUENCE ontop.tbl_user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE ontop.tbl_user_id_seq OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_user | type: TABLE --
-- DROP TABLE IF EXISTS ontop.tbl_user CASCADE;
CREATE TABLE ontop.tbl_user (
	id serial NOT NULL,
	name character varying(255) NOT NULL,
	identification character varying(50),
	CONSTRAINT tbl_user_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ontop.tbl_user OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_recipient_bank_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS ontop.tbl_recipient_bank_id_seq CASCADE;
CREATE SEQUENCE ontop.tbl_recipient_bank_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE ontop.tbl_recipient_bank_id_seq OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_recipient_bank | type: TABLE --
-- DROP TABLE IF EXISTS ontop.tbl_recipient_bank CASCADE;
CREATE TABLE ontop.tbl_recipient_bank (
	id serial NOT NULL,
	bank_name character varying NOT NULL,
	account_number character varying(255),
	first_name character varying(100) NOT NULL,
	last_name character varying(100) NOT NULL,
	routing_number character varying(255) NOT NULL,
	dni_number character varying(20),
	prefered boolean NOT NULL DEFAULT false,
	id_tbl_user integer,
	CONSTRAINT tbl_recipient_bank_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ontop.tbl_recipient_bank OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_wallet_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS ontop.tbl_wallet_id_seq CASCADE;
CREATE SEQUENCE ontop.tbl_wallet_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE ontop.tbl_wallet_id_seq OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_wallet | type: TABLE --
-- DROP TABLE IF EXISTS ontop.tbl_wallet CASCADE;
CREATE TABLE ontop.tbl_wallet (
	id serial NOT NULL,
	balance numeric(13,2) NOT NULL,
	account_number character varying(255) NOT NULL,
	routing_number character varying(255) NOT NULL,
	prefered boolean NOT NULL DEFAULT false,
	id_tbl_user integer,
	id_tbl_currency integer,
	CONSTRAINT tbl_wallet_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ontop.tbl_wallet OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_transaction_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS ontop.tbl_transaction_id_seq CASCADE;
CREATE SEQUENCE ontop.tbl_transaction_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE ontop.tbl_transaction_id_seq OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_transaction | type: TABLE --
-- DROP TABLE IF EXISTS ontop.tbl_transaction CASCADE;
CREATE TABLE ontop.tbl_transaction (
	id serial NOT NULL,
	transaction_id uuid,
	fee numeric(13,2),
	current_balance numeric(13,2),
	amount numeric(13,2),
	payment boolean NOT NULL DEFAULT false,
	src_currency integer,
	des_currency integer,
	src_account character varying(255),
	des_account character varying(255),
	src_routing_number character varying(255),
	des_routing_number character varying(255),
	created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	id_tbl_status integer,
	id_tbl_recipient_bank integer,
	id_tbl_wallet integer,
	CONSTRAINT tbl_transaction_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ontop.tbl_transaction OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_currency_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS ontop.tbl_currency_id_seq CASCADE;
CREATE SEQUENCE ontop.tbl_currency_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE ontop.tbl_currency_id_seq OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_currency | type: TABLE --
-- DROP TABLE IF EXISTS ontop.tbl_currency CASCADE;
CREATE TABLE ontop.tbl_currency (
	id serial NOT NULL,
	currency_name character varying(200),
	currency_abbreviate character varying(4) NOT NULL,
	CONSTRAINT tbl_currency_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ontop.tbl_currency OWNER TO postgres;
-- ddl-end --

-- object: ontop.tbl_status | type: TABLE --
-- DROP TABLE IF EXISTS ontop.tbl_status CASCADE;
CREATE TABLE ontop.tbl_status (
	id integer NOT NULL,
	status_name character varying(150) NOT NULL,
	CONSTRAINT tbl_status_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ontop.tbl_status OWNER TO postgres;
-- ddl-end --

-- object: provider.tbl_provider_transaction | type: TABLE --
-- DROP TABLE IF EXISTS provider.tbl_provider_transaction CASCADE;
CREATE TABLE provider.tbl_provider_transaction (
	id uuid NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status varchar(20) NOT NULL,
	CONSTRAINT tbl_provider_transaction_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE provider.tbl_provider_transaction OWNER TO postgres;
-- ddl-end --

-- object: tbl_status_fk | type: CONSTRAINT --
-- ALTER TABLE ontop.tbl_transaction DROP CONSTRAINT IF EXISTS tbl_status_fk CASCADE;
ALTER TABLE ontop.tbl_transaction ADD CONSTRAINT tbl_status_fk FOREIGN KEY (id_tbl_status)
REFERENCES ontop.tbl_status (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tbl_user_fk | type: CONSTRAINT --
-- ALTER TABLE ontop.tbl_wallet DROP CONSTRAINT IF EXISTS tbl_user_fk CASCADE;
ALTER TABLE ontop.tbl_wallet ADD CONSTRAINT tbl_user_fk FOREIGN KEY (id_tbl_user)
REFERENCES ontop.tbl_user (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tbl_currency_fk | type: CONSTRAINT --
-- ALTER TABLE ontop.tbl_wallet DROP CONSTRAINT IF EXISTS tbl_currency_fk CASCADE;
ALTER TABLE ontop.tbl_wallet ADD CONSTRAINT tbl_currency_fk FOREIGN KEY (id_tbl_currency)
REFERENCES ontop.tbl_currency (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tbl_recipient_bank_fk | type: CONSTRAINT --
-- ALTER TABLE ontop.tbl_transaction DROP CONSTRAINT IF EXISTS tbl_recipient_bank_fk CASCADE;
ALTER TABLE ontop.tbl_transaction ADD CONSTRAINT tbl_recipient_bank_fk FOREIGN KEY (id_tbl_recipient_bank)
REFERENCES ontop.tbl_recipient_bank (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tbl_wallet_fk | type: CONSTRAINT --
-- ALTER TABLE ontop.tbl_transaction DROP CONSTRAINT IF EXISTS tbl_wallet_fk CASCADE;
ALTER TABLE ontop.tbl_transaction ADD CONSTRAINT tbl_wallet_fk FOREIGN KEY (id_tbl_wallet)
REFERENCES ontop.tbl_wallet (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tbl_user_fk | type: CONSTRAINT --
-- ALTER TABLE ontop.tbl_recipient_bank DROP CONSTRAINT IF EXISTS tbl_user_fk CASCADE;
ALTER TABLE ontop.tbl_recipient_bank ADD CONSTRAINT tbl_user_fk FOREIGN KEY (id_tbl_user)
REFERENCES ontop.tbl_user (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


