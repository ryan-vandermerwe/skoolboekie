CREATE TABLE public.lookups
(
  id bytea NOT NULL,
  datemodified timestamp with time zone,
  clientid character varying(255),
  lookuptype character varying(255) NOT NULL,
  metadata text,
  value character varying(255) NOT NULL,
  parent_id bytea,
  CONSTRAINT lookups_pkey PRIMARY KEY (id),
  CONSTRAINT fkawfm2efpyyjhs6ev2rrlt9r8e FOREIGN KEY (parent_id)
  REFERENCES public.lookups (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_prhmjd5jdqtlwlwy5cvcxy7w9 UNIQUE (clientid)
);

ALTER TABLE public.lookups
  OWNER TO postgres;

CREATE TABLE public.lookups_aud
(
  id bytea NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  clientid character varying(255),
  lookuptype character varying(255),
  metadata text,
  value character varying(255),
  parent_id bytea,
  CONSTRAINT lookups_aud_pkey PRIMARY KEY (id, rev),
  CONSTRAINT fklpkt4ir5q32cwgtj8nsg1j3dx FOREIGN KEY (rev)
  REFERENCES public.revinfo (rev) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkmdox9xtdphb80f70krsfuqqsr FOREIGN KEY (rev)
  REFERENCES public.userrevisionentity (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE public.lookups_aud
  OWNER TO postgres;

