#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
---CREACION DE USUARIO Y BASE DE DATOS
CREATE USER user_stock_db WITH ENCRYPTED PASSWORD 'admin';

---CREATE DATABASE vaccination_stock;

GRANT USAGE ON SCHEMA public TO Postgres;

GRANT all PRIVILEGES ON DATABASE vaccination_stock to user_stock_db;
ALTER DATABASE vaccination_stock OWNER TO user_stock_db;
---USE vaccination_stock;

--CREACION DE TABLAS
EOSQL

psql -v ON_ERROR_STOP=1 --username "user_stock_db"  --dbname "$POSTGRES_DB" <<-EOSQL

CREATE TABLE ROLES(
    ID BIGSERIAL PRIMARY KEY,
    ROL VARCHAR(255) NOT NULL,
    ENABLE BOOLEAN NOT NULL
);

CREATE TABLE USERS(
    ID BIGSERIAL PRIMARY KEY,
    USERNAME VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL,
    ROL BIGINT NOT NULL,
    DELETE
        BOOLEAN NOT NULL,
        CREATE_BY BIGINT NOT NULL,
        CREATE_AT TIMESTAMP NOT NULL,
        UPDATE_BY BIGINT NULL,
        UPDATE_AT TIMESTAMP NULL,
        DELETE_BY BIGINT NULL,
        DELETE_AT TIMESTAMP NULL
);

CREATE UNIQUE INDEX USERS_USER_NAME ON USERS (USERNAME);

ALTER TABLE
    USERS
ADD
    CONSTRAINT ROLES_WITH_USERS FOREIGN KEY (ROL) REFERENCES ROLES(ID);

CREATE TABLE EMPLOYEES(
    ID BIGSERIAL PRIMARY KEY,
    DOCUMENT_NUMBER VARCHAR(10) NOT NULL UNIQUE,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    BIRTHDAY DATE NOT NULL,
    ADDRESS VARCHAR(300) NOT NULL,
    PHONE VARCHAR(15) NOT NULL,
    VACCINATED BOOLEAN NOT NULL,
    DELETE
        BOOLEAN NOT NULL,
        USER_ACCESS BIGINT NOT NULL,
        CREATE_BY BIGINT NOT NULL,
        CREATE_AT TIMESTAMP NOT NULL,
        UPDATE_BY BIGINT NULL,
        UPDATE_AT TIMESTAMP NULL,
        DELETE_BY BIGINT NULL,
        DELETE_AT TIMESTAMP NULL
);

ALTER TABLE
    EMPLOYEES
ADD
    CONSTRAINT FK_EMPLOYEES_WITH_USERS FOREIGN KEY (USER_ACCESS) REFERENCES USERS(ID);

CREATE UNIQUE INDEX EMPLOYEES_DOCUMENT_NUMBER_INDEX ON EMPLOYEES (DOCUMENT_NUMBER);

CREATE TABLE VACCINATION_STOCK(
    ID BIGSERIAL PRIMARY KEY,
    TYPE VARCHAR(255) NOT NULL,
    DATE DATE NOT NULL,
    DOSE SMALLINT NOT NULL,
    ID_EMPLOYEE BIGINT NOT NULL,
    DELETE
        BOOLEAN NOT NULL,
        CREATE_BY BIGINT NOT NULL,
        CREATE_AT TIMESTAMP NOT NULL,
        UPDATE_BY BIGINT NULL,
        UPDATE_AT TIMESTAMP NULL,
        DELETE_BY BIGINT NULL,
        DELETE_AT TIMESTAMP NULL
);


ALTER TABLE
    VACCINATION_STOCK
ADD
    CONSTRAINT FK_EMPLOYEES_WITH_VACCINATION_STOCK FOREIGN KEY (ID_EMPLOYEE) REFERENCES EMPLOYEES(ID);


INSERT INTO public.roles(id,rol,"enable")VALUES(1,'ADMIN',true);
INSERT INTO public.roles(id,rol,"enable")VALUES(2,'EMPLOYEE',true);

INSERT INTO public .users (username,password,rol,"delete",create_by,create_at)VALUES('admin','\$2a\$10\$VmDtoARCVU9v3AT5rB9GqewvD/76kPbBFrIYb4tZhTTXZldtiRLEu',1,false,1,'2021-11-06 23:25:10.413');
EOSQL