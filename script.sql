CREATE SEQUENCE public.measure_id_seq
    INCREMENT 50
    START 2101
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
ALTER SEQUENCE public.measure_id_seq
    OWNER TO <name>;
	
CREATE SEQUENCE public.note_id_seq
    INCREMENT 50
    START 601
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
ALTER SEQUENCE public.note_id_seq
    OWNER TO <name>;
	
CREATE TABLE public.measure
(
    id integer NOT NULL DEFAULT nextval('measure_id_seq'::regclass),
    sensor_id uuid NOT NULL,
    city_id uuid NOT NULL,
    pm10 double precision NOT NULL,
    co double precision NOT NULL,
    no2 double precision NOT NULL,
    "timestamp" timestamp without time zone,
    CONSTRAINT measure_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.measure
    OWNER to <name>;
	
CREATE TABLE public.note
(
    id integer NOT NULL,
    city_id uuid NOT NULL,
    topic character varying(100) COLLATE pg_catalog."default",
    date_add timestamp without time zone NOT NULL,
    date_mod timestamp without time zone NOT NULL,
    user_mod character varying(50) COLLATE pg_catalog."default" NOT NULL,
    text character varying(1000) COLLATE pg_catalog."default",
    uuid uuid NOT NULL,
    CONSTRAINT note_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.note
    OWNER to <name>;
	
create view average_cp as
 SELECT row_number() OVER (ORDER BY m1.city_id) AS id,
    concat(date_part('year'::text, m1."timestamp"),
        CASE
            WHEN date_part('month'::text, m1."timestamp") < '10'::double precision THEN '0'::text
            ELSE ''::text
        END, date_part('month'::text, m1."timestamp")) AS yrmon,
    m1.city_id AS cit,
    avg(m1.co) AS av_co,
    avg(m1.pm10) AS av_pm10
   FROM measure m1
  WHERE (( SELECT count(*) AS count
           FROM measure m2
          WHERE concat(date_part('year'::text, m1."timestamp"), date_part('month'::text, m1."timestamp")) = concat(date_part('year'::text, m2."timestamp"), date_part('month'::text, m2."timestamp")) AND m1.city_id = m2.city_id AND (date_part('day'::text, m2."timestamp") = 1::double precision OR date_part('day'::text, m2."timestamp") = (( SELECT date_part('day'::text, date_trunc('month'::text, to_date((date_part('year'::text, m1."timestamp") || '-'::text) || date_part('month'::text, m1."timestamp"), 'YYYY-MM'::text)::timestamp with time zone) + '1 mon -1 days'::interval) AS date_part))))) = 2
  GROUP BY m1.city_id, (concat(date_part('year'::text, m1."timestamp"),
        CASE
            WHEN date_part('month'::text, m1."timestamp") < '10'::double precision THEN '0'::text
            ELSE ''::text
        END, date_part('month'::text, m1."timestamp")))
  ORDER BY m1.city_id, (concat(date_part('year'::text, m1."timestamp"),
        CASE
            WHEN date_part('month'::text, m1."timestamp") < '10'::double precision THEN '0'::text
            ELSE ''::text
        END, date_part('month'::text, m1."timestamp")));
		
create view average_n AS
 SELECT row_number() OVER (ORDER BY m1.city_id) AS id,
    concat(date_part('year'::text, m1."timestamp"),
        CASE
            WHEN date_part('month'::text, m1."timestamp") < '10'::double precision THEN '0'::text
            ELSE ''::text
        END, date_part('month'::text, m1."timestamp")) AS yrmon,
    m1.city_id AS cit,
    avg(m1.no2) AS av_no2
   FROM measure m1
  GROUP BY m1.city_id, (concat(date_part('year'::text, m1."timestamp"),
        CASE
            WHEN date_part('month'::text, m1."timestamp") < '10'::double precision THEN '0'::text
            ELSE ''::text
        END, date_part('month'::text, m1."timestamp")))
  ORDER BY m1.city_id, (concat(date_part('year'::text, m1."timestamp"),
        CASE
            WHEN date_part('month'::text, m1."timestamp") < '10'::double precision THEN '0'::text
            ELSE ''::text
        END, date_part('month'::text, m1."timestamp")));
		
create view average_n_diff AS
 SELECT curr.id,
    curr.yrmon,
    curr.cit,
    curr.av_no2 AS av_no2curr,
    prev.av_no2 AS av_no2prev
   FROM average_n curr
     JOIN average_n prev ON curr.cit = prev.cit AND to_char(to_date(curr.yrmon, 'YYYYMM'::text) - '1 year'::interval, 'YYYYMM'::text) = prev.yrmon
  WHERE curr.av_no2 > prev.av_no2;