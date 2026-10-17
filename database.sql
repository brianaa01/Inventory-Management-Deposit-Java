

create table categorie
(
    id_categorie integer generated always as identity
        primary key,
    nume         varchar(50) not null
        unique
);

alter table categorie
    owner to postgres;

create table produs
(
    id_produs    integer generated always as identity
        primary key,
    nume         varchar(100) not null
        constraint nume_produs_unic
            unique,
    id_categorie integer      not null
        constraint fk_produse_categorie
            references categorie
            on update cascade on delete restrict,
    stoc_minim   integer      not null
        constraint produs_stoc_minim_check
            check (stoc_minim >= 0),
    stoc_curent  integer      not null
        constraint produs_stoc_curent_check
            check (stoc_curent >= 0)
);

alter table produs
    owner to postgres;

create table electronice
(
    id_produs     integer not null
        primary key
        constraint fk_electronice_produs
            references produs
            on delete cascade,
    garantie_luni integer not null,
    tensiune      integer not null
);

alter table electronice
    owner to postgres;

create table mobila
(
    id_produs  integer not null
        primary key
        constraint fk_mobila_produs
            references produs
            on delete cascade,
    material   varchar(50),
    dimensiuni varchar(100)
);

alter table mobila
    owner to postgres;

create table furnizor
(
    id_furnizor integer generated always as identity
        primary key,
    nume        varchar(50),
    contact     varchar(11)
);

alter table furnizor
    owner to postgres;

create table produs_furnizor
(
    id_produs   integer not null
        constraint fk_pf_produs
            references produs
            on update cascade on delete restrict,
    id_furnizor integer not null
        constraint fk_pf_furnizor
            references furnizor
            on update cascade on delete restrict,
    primary key (id_produs, id_furnizor)
);

alter table produs_furnizor
    owner to postgres;

create table tranzactie
(
    id_tranzactie   integer generated always as identity
        primary key,
    id_produs       integer    not null
        constraint fk_tranzactie_produs
            references produs
            on update cascade on delete restrict,
    tip             varchar(3) not null
        constraint tranzactie_tip_check
            check ((tip)::text = ANY ((ARRAY ['IN'::character varying, 'OUT'::character varying])::text[])),
    cantitate       integer    not null
        constraint tranzactie_cantitate_check
            check (cantitate > 0),
    data_tranzactie timestamp default CURRENT_TIMESTAMP
);

alter table tranzactie
    owner to postgres;

create function update_stoc() returns trigger
    language plpgsql
as
$$
BEGIN
    IF NEW.tip = 'IN' THEN
        UPDATE produs
        SET stoc_curent = stoc_curent + NEW.cantitate
        WHERE id_produs = NEW.id_produs;
    ELSIF NEW.tip = 'OUT' THEN
        UPDATE produs
        SET stoc_curent = stoc_curent - NEW.cantitate
        WHERE id_produs = NEW.id_produs;
    END IF;

    RETURN NEW;
END;
$$;

alter function update_stoc() owner to postgres;

create function actualizeaza_stoc() returns trigger
    language plpgsql
as
$$
BEGIN
    IF NEW.tip = 'IN' THEN
        UPDATE produs
        SET stoc_curent = stoc_curent + NEW.cantitate
        WHERE id_produs = NEW.id_produs;
    ELSIF NEW.tip = 'OUT' THEN
        UPDATE produs
        SET stoc_curent = stoc_curent - NEW.cantitate
        WHERE id_produs = NEW.id_produs;
    END IF;
    RETURN NEW;
END;
$$;

alter function actualizeaza_stoc() owner to postgres;

