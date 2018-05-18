

INSERT INTO cookies (cookie_name)
VALUES ('kanelbulle');

INSERT INTO cookies (cookie_name)
VALUES ('vaniljbulle');

INSERT INTO cookies (cookie_name)
VALUES ('chokladboll');


INSERT INTO raw_materials (rm_name, rm_amount)
VALUES ('bulle', 10000);

INSERT INTO raw_materials (rm_name, rm_amount)
VALUES ('boll', 10000);


INSERT INTO raw_materials (rm_name, rm_amount)
VALUES ('choklad', 10000);


INSERT INTO raw_materials (rm_name, rm_amount)
VALUES ('vanilj', 10000);


INSERT INTO raw_materials (rm_name, rm_amount)
VALUES ('kanel', 10000);




INSERT INTO raw_cookie_material (cookie_name, rm_name, raw_cookie_amount)
VALUES ('kanelbulle', 'kanel', 200);


INSERT INTO raw_cookie_material (cookie_name, rm_name, raw_cookie_amount)
VALUES ('kanelbulle', 'bulle', 100);

INSERT INTO raw_cookie_material (cookie_name, rm_name, raw_cookie_amount)
VALUES ('vaniljbulle', 'vanilj', 200);

INSERT INTO raw_cookie_material (cookie_name, rm_name, raw_cookie_amount)
VALUES ('vaniljbulle', 'bulle', 100);


INSERT INTO raw_cookie_material (cookie_name, rm_name, raw_cookie_amount)
VALUES ('chokladboll', 'choklad', 200);

INSERT INTO raw_cookie_material (cookie_name, rm_name, raw_cookie_amount)
VALUES ('chokladboll', 'boll', 100);

-- Customers

INSERT INTO customers (customer_name,adress)
VALUES ('Karl XVI Gustav',' Slottet');

INSERT INTO customers (customer_name,adress)
VALUES ('Christian Soderberg',' LTH');

-- Orders

INSERT INTO orders (order_id,customer_name)
VALUES (111,'Christian Soderberg');


INSERT INTO orders (order_id,customer_name)
VALUES (222,'Karl XVI Gustav');

-- orderd_cookie

INSERT INTO ordered_cookie (cookie_name, order_id, ordered_amount)
VALUES ('vaniljbulle',222, 4);

INSERT INTO ordered_cookie (cookie_name, order_id, ordered_amount)
VALUES ('kanelbulle',111, 2);

INSERT INTO ordered_cookie (cookie_name, order_id, ordered_amount)
VALUES ('chokladboll',111, 40);




--- Pallar  



INSERT INTO pallets (label, cookie_name, order_id, timestamp, blocked_status, arrival_date)
VALUES (1,'chokladboll', null, '2018-04-15', TRUE, null);



INSERT INTO pallets (label, cookie_name, order_id, timestamp, blocked_status, arrival_date)
VALUES (2,'chokladboll', 111, '2018-04-13', FALSE, '2018-04-15');


INSERT INTO pallets (label, cookie_name, order_id, timestamp, blocked_status, arrival_date)
VALUES (3,'kanelbulle', 111, '2018-04-13', FALSE, '2018-04-16');


INSERT INTO pallets (label, cookie_name, order_id, timestamp, blocked_status, arrival_date)
VALUES (4,'vaniljbulle', 222, '2018-04-15', FALSE, null);




