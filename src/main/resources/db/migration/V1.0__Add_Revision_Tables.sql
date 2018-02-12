CREATE TABLE public.revinfo
(
  rev integer NOT NULL,
  revtstmp bigint,
  CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
);

ALTER TABLE public.revinfo
  OWNER TO postgres;

CREATE TABLE public.userrevisionentity
(
  id integer NOT NULL,
  "timestamp" bigint NOT NULL,
  username character varying(255),
  CONSTRAINT userrevisionentity_pkey PRIMARY KEY (id)
);

ALTER TABLE public.userrevisionentity
  OWNER TO postgres;
