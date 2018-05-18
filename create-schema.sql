PRAGMA foreign_keys=OFF;
DROP TABLE IF EXISTS raw_materials;
DROP TABLE IF EXISTS cookies;
DROP TABLE IF EXISTS raw_cookie_material;
DROP TABLE IF EXISTS pallets;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS ordered_cookie;



CREATE TABLE raw_materials(
rm_name TEXT PRIMARY KEY,
rm_amount INTEGER
);

CREATE TABLE cookies(
cookie_name TEXT PRIMARY KEY
);

CREATE TABLE raw_cookie_material(
cookie_name TEXT,
rm_name TEXT,
raw_cookie_amount INTEGER,

PRIMARY KEY (cookie_name, rm_name),
FOREIGN KEY (cookie_name) REFERENCES cookies(cookie_name),
FOREIGN KEY (rm_name) REFERENCES  raw_materials(rm_name)
);

CREATE TABLE pallets(
label INTEGER PRIMARY KEY,
cookie_name TEXT,
order_id INTEGER,
timestamp Date,
blocked_status BOOLEAN,
arrival_date date,
FOREIGN KEY (cookie_name) REFERENCES cookies(cookie_name),
FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

CREATE TABLE orders(
order_id INT PRIMARY KEY,
customer_name TEXT,
FOREIGN KEY (customer_name) REFERENCES customers(customer_name)
);

CREATE TABLE customers(
customer_name TEXT PRIMARY KEY,
adress TEXT
);

CREATE TABLE ordered_cookie(
cookie_name TEXT,
order_id INT,
ordered_amount INT,

PRIMARY KEY (cookie_name, order_id),
FOREIGN KEY (cookie_name) REFERENCES cookies(cookie_name),
FOREIGN KEY (order_id) REFERENCES orders(order_id)
);




