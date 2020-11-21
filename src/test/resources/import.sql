INSERT INTO dummy_cities (id, name) VALUES (1, 'Mcity');

INSERT INTO dummy_addresses (id, street, number, city_id) VALUES (1, 'Fake street', 123, 1);

INSERT INTO dummies (name, surname, customer, birthday, address_id) VALUES ('Joe', 'Simons', true, '1978-05-03', null);
INSERT INTO dummies (name, surname, customer, birthday, address_id) VALUES ('John', 'Doe', true, '1980-07-12', 1);
INSERT INTO dummies (name, surname, customer, birthday, address_id) VALUES('Linus', 'Torvalds', false, '1972-03-05', null);
INSERT INTO dummies (name, surname, customer, birthday, address_id) VALUES('Jane', 'Doe', null, '1982-11-28', null);