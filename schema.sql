-- Empty database schema for the Order Management System.
-- Create a PostgreSQL database named orders_management before running this script.

CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200),
    email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    stock INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS "Order" (
    id SERIAL PRIMARY KEY,
    clientid INTEGER NOT NULL REFERENCES client(id) ON DELETE CASCADE,
    productid INTEGER NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS log (
    id SERIAL PRIMARY KEY,
    orderid INTEGER NOT NULL,
    clientname VARCHAR(100),
    totalprice DOUBLE PRECISION
);
