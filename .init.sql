-- Crear un usuario llamado 'data' con contraseña 'datapassword'
CREATE USER admin WITH SUPERUSER PASSWORD 'admin';

-- Cambiar al usuario 'data' para ejecutar los comandos siguientes
--SET ROLE admin;

-- Crear una base de datos llamada 'spring_security'
CREATE DATABASE spring_security;
--CREATE DATABASE spring_security2 WITH OWNER = admin;

-- Cambiar a la nueva base de datos
\connect spring_security;

-- Crear un esquema llamado 'data'
CREATE SCHEMA data;

-- Cambiar al esquema 'data'
SET search_path TO data;

-- Puedes agregar aquí más comandos SQL para crear tablas, índices, etc.
-- Por ejemplo:
CREATE TABLE data_table (
     id SERIAL PRIMARY KEY,
     name VARCHAR(255)
);

-- Volver al esquema público (por defecto)
SET search_path TO public;

-- Otros comandos SQL si es necesario
