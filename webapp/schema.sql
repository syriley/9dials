--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: address; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE address (
    id bigint NOT NULL,
    locality character varying(255),
    postalcode character varying(255),
    streetaddress character varying(255)
);


ALTER TABLE public.address OWNER TO nine;

--
-- Name: auser; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE auser (
    id bigint NOT NULL,
    bio character varying(255),
    email character varying(255),
    imageurl character varying(255),
    isemailverified boolean NOT NULL,
    name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.auser OWNER TO nine;

--
-- Name: auser_instrument; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE auser_instrument (
    users_id bigint NOT NULL,
    instruments_id bigint NOT NULL
);


ALTER TABLE public.auser_instrument OWNER TO nine;

--
-- Name: collective; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE collective (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.collective OWNER TO nine;

--
-- Name: drumlesson; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE drumlesson (
    id bigint NOT NULL,
    name character varying(255),
    pdflocation character varying(255),
    youtubelocation character varying(255),
    drumlessongroup_id bigint,
    user_id bigint
);


ALTER TABLE public.drumlesson OWNER TO nine;

--
-- Name: drumlessongroup; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE drumlessongroup (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.drumlessongroup OWNER TO nine;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: nine
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO nine;

--
-- Name: instrument; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE instrument (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.instrument OWNER TO nine;

--
-- Name: location; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE location (
    id bigint NOT NULL,
    city character varying(255),
    country character varying(255),
    dateprocessed timestamp without time zone
);


ALTER TABLE public.location OWNER TO nine;

--
-- Name: mailinglistrecipient; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE mailinglistrecipient (
    id bigint NOT NULL,
    email character varying(255)
);


ALTER TABLE public.mailinglistrecipient OWNER TO nine;

--
-- Name: picture; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE picture (
    id bigint NOT NULL,
    image character varying(255)
);


ALTER TABLE public.picture OWNER TO nine;

--
-- Name: school; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE school (
    id bigint NOT NULL,
    email character varying(255),
    haslinked boolean NOT NULL,
    hasreplied boolean NOT NULL,
    haveemailed boolean NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    name character varying(255),
    website character varying(255),
    description character varying(255),
    address character varying(255),
    logourl character varying(255),
    address_id bigint,
    updated timestamp without time zone,
    facebook character varying(255),
    twitter character varying(255)
);


ALTER TABLE public.school OWNER TO nine;

--
-- Name: school_phonenumbers; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE school_phonenumbers (
    school_id bigint NOT NULL,
    phonenumbers character varying(255)
);


ALTER TABLE public.school_phonenumbers OWNER TO nine;

--
-- Name: school_photourls; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE school_photourls (
    school_id bigint NOT NULL,
    photourls character varying(255)
);


ALTER TABLE public.school_photourls OWNER TO nine;

--
-- Name: school_possibleemails; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE school_possibleemails (
    school_id bigint NOT NULL,
    possibleemails character varying(255)
);


ALTER TABLE public.school_possibleemails OWNER TO nine;

--
-- Name: session; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE session (
    id bigint NOT NULL,
    access character varying(255),
    data text,
    description character varying(255),
    modified timestamp without time zone,
    name character varying(255)
);


ALTER TABLE public.session OWNER TO nine;

--
-- Name: sessiondetail; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE sessiondetail (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.sessiondetail OWNER TO nine;

--
-- Name: source; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE source (
    id bigint NOT NULL,
    filename character varying(255),
    s3key character varying(255)
);


ALTER TABLE public.source OWNER TO nine;

--
-- Name: track; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE track (
    id bigint NOT NULL,
    audiourl character varying(255),
    provider character varying(255)
);


ALTER TABLE public.track OWNER TO nine;

--
-- Name: useraction; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE useraction (
    id bigint NOT NULL,
    action character varying(255),
    actiontime timestamp without time zone,
    user_id bigint
);


ALTER TABLE public.useraction OWNER TO nine;

--
-- Name: usersession; Type: TABLE; Schema: public; Owner: nine; Tablespace: 
--

CREATE TABLE usersession (
    id bigint NOT NULL,
    role character varying(255),
    session_id bigint,
    user_id bigint
);


ALTER TABLE public.usersession OWNER TO nine;

--
-- Name: address_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: auser_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY auser
    ADD CONSTRAINT auser_pkey PRIMARY KEY (id);


--
-- Name: collective_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY collective
    ADD CONSTRAINT collective_pkey PRIMARY KEY (id);


--
-- Name: drumlesson_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY drumlesson
    ADD CONSTRAINT drumlesson_pkey PRIMARY KEY (id);


--
-- Name: drumlessongroup_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY drumlessongroup
    ADD CONSTRAINT drumlessongroup_pkey PRIMARY KEY (id);


--
-- Name: instrument_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY instrument
    ADD CONSTRAINT instrument_pkey PRIMARY KEY (id);


--
-- Name: location_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- Name: mailinglistrecipient_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY mailinglistrecipient
    ADD CONSTRAINT mailinglistrecipient_pkey PRIMARY KEY (id);


--
-- Name: picture_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY picture
    ADD CONSTRAINT picture_pkey PRIMARY KEY (id);


--
-- Name: school_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY school
    ADD CONSTRAINT school_pkey PRIMARY KEY (id);


--
-- Name: session_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY session
    ADD CONSTRAINT session_pkey PRIMARY KEY (id);


--
-- Name: sessiondetail_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY sessiondetail
    ADD CONSTRAINT sessiondetail_pkey PRIMARY KEY (id);


--
-- Name: source_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY source
    ADD CONSTRAINT source_pkey PRIMARY KEY (id);


--
-- Name: track_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY track
    ADD CONSTRAINT track_pkey PRIMARY KEY (id);


--
-- Name: useraction_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY useraction
    ADD CONSTRAINT useraction_pkey PRIMARY KEY (id);


--
-- Name: usersession_pkey; Type: CONSTRAINT; Schema: public; Owner: nine; Tablespace: 
--

ALTER TABLE ONLY usersession
    ADD CONSTRAINT usersession_pkey PRIMARY KEY (id);


--
-- Name: school_website_idx; Type: INDEX; Schema: public; Owner: nine; Tablespace: 
--

CREATE UNIQUE INDEX school_website_idx ON school USING btree (name, website);


--
-- Name: fk6befa3c7c1ddc95e; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY school_phonenumbers
    ADD CONSTRAINT fk6befa3c7c1ddc95e FOREIGN KEY (school_id) REFERENCES school(id);


--
-- Name: fk7161d09e47140efe; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY drumlesson
    ADD CONSTRAINT fk7161d09e47140efe FOREIGN KEY (user_id) REFERENCES auser(id);


--
-- Name: fk7161d09edcb15e36; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY drumlesson
    ADD CONSTRAINT fk7161d09edcb15e36 FOREIGN KEY (drumlessongroup_id) REFERENCES drumlessongroup(id);


--
-- Name: fk91d8fe7a4004e7a1; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY auser_instrument
    ADD CONSTRAINT fk91d8fe7a4004e7a1 FOREIGN KEY (users_id) REFERENCES auser(id);


--
-- Name: fk91d8fe7a70a4fed9; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY auser_instrument
    ADD CONSTRAINT fk91d8fe7a70a4fed9 FOREIGN KEY (instruments_id) REFERENCES instrument(id);


--
-- Name: fk9346479418cc8596; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY school
    ADD CONSTRAINT fk9346479418cc8596 FOREIGN KEY (address_id) REFERENCES address(id);


--
-- Name: fka2b77133c1ddc95e; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY school_possibleemails
    ADD CONSTRAINT fka2b77133c1ddc95e FOREIGN KEY (school_id) REFERENCES school(id);


--
-- Name: fkaa470b4bc1ddc95e; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY school_photourls
    ADD CONSTRAINT fkaa470b4bc1ddc95e FOREIGN KEY (school_id) REFERENCES school(id);


--
-- Name: fkb611ee0147140efe; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY useraction
    ADD CONSTRAINT fkb611ee0147140efe FOREIGN KEY (user_id) REFERENCES auser(id);


--
-- Name: fkc7bc0c2b47140efe; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY usersession
    ADD CONSTRAINT fkc7bc0c2b47140efe FOREIGN KEY (user_id) REFERENCES auser(id);


--
-- Name: fkc7bc0c2be6a46a56; Type: FK CONSTRAINT; Schema: public; Owner: nine
--

ALTER TABLE ONLY usersession
    ADD CONSTRAINT fkc7bc0c2be6a46a56 FOREIGN KEY (session_id) REFERENCES session(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

