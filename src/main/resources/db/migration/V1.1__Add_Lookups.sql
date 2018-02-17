CREATE TABLE public.lookups
(
  id uuid NOT NULL,
  datemodified timestamp with time zone,
  clientid character varying(255),
  lookuptype character varying(255) NOT NULL,
  metadata text,
  value character varying(255) NOT NULL,
  parent_id uuid,
  CONSTRAINT lookups_pkey PRIMARY KEY (id),
  CONSTRAINT lookups_parent_fk FOREIGN KEY (parent_id)
  REFERENCES public.lookups (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_clientID UNIQUE (clientid)
);

ALTER TABLE public.lookups
  OWNER TO postgres;

CREATE TABLE public.lookups_aud
(
  id uuid NOT NULL,
  rev integer NOT NULL,
  revtype smallint,
  clientid character varying(255),
  lookuptype character varying(255),
  metadata text,
  value character varying(255),
  parent_id uuid,
  CONSTRAINT lookups_aud_pkey PRIMARY KEY (id, rev),
  CONSTRAINT lookups_revinfo_fk FOREIGN KEY (rev)
  REFERENCES public.revinfo (rev) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT lookups_rev_entity_fk FOREIGN KEY (rev)
  REFERENCES public.userrevisionentity (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE public.lookups_aud
  OWNER TO postgres;

