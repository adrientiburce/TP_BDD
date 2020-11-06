
CREATE SEQUENCE public.produit_pk_seq;

CREATE TABLE public.produit (
                pk INTEGER NOT NULL DEFAULT nextval('public.produit_pk_seq'),
                nom VARCHAR NOT NULL,
                CONSTRAINT produit_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.produit_pk_seq OWNED BY public.produit.pk;

CREATE SEQUENCE public.quai_produit_pk_seq;

CREATE TABLE public.quai_produit (
                pk INTEGER NOT NULL DEFAULT nextval('public.quai_produit_pk_seq'),
                quantite INTEGER NOT NULL,
                produit_id INTEGER NOT NULL,
                CONSTRAINT quai_produit_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.quai_produit_pk_seq OWNED BY public.quai_produit.pk;

CREATE INDEX quai_produit_idx
 ON public.quai_produit
 ( produit_id );

CREATE SEQUENCE public.quai_pk_seq;

CREATE TABLE public.quai (
                pk INTEGER NOT NULL DEFAULT nextval('public.quai_pk_seq'),
                adresse VARCHAR NOT NULL,
                ville VARCHAR NOT NULL,
                CONSTRAINT quai_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.quai_pk_seq OWNED BY public.quai.pk;

CREATE SEQUENCE public.entreprise_pk_seq;

CREATE TABLE public.entreprise (
                pk INTEGER NOT NULL DEFAULT nextval('public.entreprise_pk_seq'),
                nom VARCHAR NOT NULL,
                adresse VARCHAR NOT NULL,
                CONSTRAINT entreprise_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.entreprise_pk_seq OWNED BY public.entreprise.pk;

CREATE SEQUENCE public.depot_pk_seq;

CREATE TABLE public.depot (
                pk INTEGER NOT NULL DEFAULT nextval('public.depot_pk_seq'),
                adresse VARCHAR NOT NULL,
                entreprise_id INTEGER NOT NULL,
                CONSTRAINT depot_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.depot_pk_seq OWNED BY public.depot.pk;

CREATE SEQUENCE public.commande_pk_seq;

CREATE TABLE public.commande (
                pk INTEGER NOT NULL DEFAULT nextval('public.commande_pk_seq'),
                produit VARCHAR NOT NULL,
                quantite INTEGER NOT NULL,
                date DATE NOT NULL,
                depot_id INTEGER NOT NULL,
                CONSTRAINT commande_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.commande_pk_seq OWNED BY public.commande.pk;

CREATE SEQUENCE public.camion_pk_seq;

CREATE TABLE public.camion (
                pk INTEGER NOT NULL DEFAULT nextval('public.camion_pk_seq'),
                immat VARCHAR NOT NULL,
                remarque VARCHAR,
                CONSTRAINT camion_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.camion_pk_seq OWNED BY public.camion.pk;

CREATE SEQUENCE public.chauffeur_pk_seq;

CREATE TABLE public.chauffeur (
                pk INTEGER NOT NULL DEFAULT nextval('public.chauffeur_pk_seq'),
                nom VARCHAR NOT NULL,
                camion_id INTEGER NOT NULL,
                CONSTRAINT chauffeur_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.chauffeur_pk_seq OWNED BY public.chauffeur.pk;

CREATE SEQUENCE public.livraison_pk_seq;

CREATE TABLE public.livraison (
                pk INTEGER NOT NULL DEFAULT nextval('public.livraison_pk_seq'),
                chauffeur_id INTEGER NOT NULL,
                date_chargement TIME NOT NULL,
                quai_id INTEGER NOT NULL,
                CONSTRAINT livraison_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.livraison_pk_seq OWNED BY public.livraison.pk;

CREATE SEQUENCE public.livraison_commande_pk_seq;

CREATE TABLE public.livraison_commande (
                pk INTEGER NOT NULL DEFAULT nextval('public.livraison_commande_pk_seq'),
                date_livraison TIME NOT NULL,
                livraison_id INTEGER NOT NULL,
                commande_id INTEGER NOT NULL,
                CONSTRAINT livraison_commande_pk PRIMARY KEY (pk)
);


ALTER SEQUENCE public.livraison_commande_pk_seq OWNED BY public.livraison_commande.pk;

ALTER TABLE public.quai_produit ADD CONSTRAINT produit_quai_produit_fk
FOREIGN KEY (produit_id)
REFERENCES public.produit (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.livraison ADD CONSTRAINT quai_livraison_fk
FOREIGN KEY (quai_id)
REFERENCES public.quai (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.depot ADD CONSTRAINT entreprise_depot_fk
FOREIGN KEY (entreprise_id)
REFERENCES public.entreprise (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.commande ADD CONSTRAINT depot_commande_fk
FOREIGN KEY (depot_id)
REFERENCES public.depot (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.livraison_commande ADD CONSTRAINT commande_livraison_commande_fk
FOREIGN KEY (commande_id)
REFERENCES public.commande (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.chauffeur ADD CONSTRAINT camion_chauffeur_fk
FOREIGN KEY (camion_id)
REFERENCES public.camion (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.livraison ADD CONSTRAINT chauffeur_livraison_fk
FOREIGN KEY (chauffeur_id)
REFERENCES public.chauffeur (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.livraison_commande ADD CONSTRAINT livraison_livraison_commande_fk
FOREIGN KEY (livraison_id)
REFERENCES public.livraison (pk)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
