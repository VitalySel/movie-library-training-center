create sequence hibernate_sequence start 1 increment 1

CREATE TABLE actor
(
    idactors integer NOT NULL,
    country character varying(100000) COLLATE pg_catalog."default",
    date_birth character varying(25) COLLATE pg_catalog."default",
    name character varying(25) COLLATE pg_catalog."default" NOT NULL,
    photo character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT actor_pkey PRIMARY KEY (idactors)
)

CREATE TABLE actors_genres
(
    actor_idactors integer NOT NULL,
    genre_idgenres integer NOT NULL,
    CONSTRAINT fkbb1uj6wfd3geep6m6uf04rw17 FOREIGN KEY (actor_idactors)
        REFERENCES actor (idactors) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkt5xfqxdh51k2sx85n1uekpip0 FOREIGN KEY (genre_idgenres)
        REFERENCES genres (idgenres) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE genres
(
    idgenres integer NOT NULL,
    genre_name character varying(25) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT genres_pkey PRIMARY KEY (idgenres),
    CONSTRAINT uk_11hq5auj6f9viwt8hr86j714d UNIQUE (genre_name)

)

CREATE TABLE movie
(
    idmovies integer NOT NULL,
    budget character varying(255) COLLATE pg_catalog."default",
    description character varying(100000) COLLATE pg_catalog."default",
    duration character varying(255) COLLATE pg_catalog."default",
    movie_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    poster character varying(255) COLLATE pg_catalog."default",
    release_date character varying(255) COLLATE pg_catalog."default",
    movies integer NOT NULL,
    CONSTRAINT movie_pkey PRIMARY KEY (idmovies),
    CONSTRAINT fkjgh4bv2cqrvy41c7okinjhkwb FOREIGN KEY (movies)
        REFERENCES producer (idproducers) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE movie_actors
(
    movie_idmovies integer NOT NULL,
    actor_idactors integer NOT NULL,
    CONSTRAINT movie_actors_pkey PRIMARY KEY (movie_idmovies, actor_idactors),
    CONSTRAINT fkjyofiwy05tbyiquhbk14oi0yh FOREIGN KEY (movie_idmovies)
        REFERENCES movie (idmovies) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmmvjwtf7h6k996b51vdpxfivt FOREIGN KEY (actor_idactors)
        REFERENCES actor (idactors) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE movie_genres
(
    movie_idmovies integer NOT NULL,
    genres_idgenres integer NOT NULL,
    CONSTRAINT fk5o4gr96w9tl88sjfysohmmcv5 FOREIGN KEY (genres_idgenres)
        REFERENCES genres (idgenres) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fklads2ho5h5fhffr8lgl59wdht FOREIGN KEY (movie_idmovies)
        REFERENCES movie (idmovies) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE producer
(
    idproducers integer NOT NULL,
    producer_country character varying(255) COLLATE pg_catalog."default",
    producer_date character varying(255) COLLATE pg_catalog."default",
    producer_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    producer_photo character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT producer_pkey PRIMARY KEY (idproducers)
)

CREATE TABLE producers_genres
(
    producer_idproducers integer NOT NULL,
    genres_idgenres integer NOT NULL,
    CONSTRAINT fk2iy5jwvfufpeftq5rcg9vl4w4 FOREIGN KEY (producer_idproducers)
        REFERENCES producer (idproducers) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk7475xjlslqma5w3bu7blf0e5l FOREIGN KEY (genres_idgenres)
        REFERENCES genres (idgenres) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE usr
(
    id bigint NOT NULL,
    activationcode character varying(255) COLLATE pg_catalog."default",
    active boolean NOT NULL,
    avatar character varying(255) COLLATE pg_catalog."default",
    mail character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    realname character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT usr_pkey PRIMARY KEY (id)
)

CREATE TABLE usr_role
(
    usr_id bigint NOT NULL,
    roles character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT fk9ffk6ts9njcytrt8ft17fvr3p FOREIGN KEY (usr_id)
        REFERENCES usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)