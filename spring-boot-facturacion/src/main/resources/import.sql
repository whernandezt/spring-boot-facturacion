/* Populate table clientes */
INSERT INTO clientes (nombre, email, create_at) VALUES ('Walter Hernández', 'walhez.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('William Tobar', 'wtobar.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Henry Cardoz', 'hcardoza.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Luis Tobar', 'ltboar.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Noe Tejada', 'ntejada@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Nora Toronja', 'ntoronja@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Vladimir Putin', 'vputin@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Leonor Aguilar', 'laguilar.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Roberto Cañas', 'rcanas.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Edi Lopez', 'elopez.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('David Quezada', 'hquezada.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Yuri Quijada', 'jquijada.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Walter Hernández', 'walhez.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('William Tobar', 'wtobar.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Henry Cardoza', 'hcardoza.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Luis Tobar', 'ltboar.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Noe Tejada', 'ntejada@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Nora Toronja', 'ntoronja@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Vladimir Putin', 'vputin@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Leonor Aguilar', 'laguilar.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Roberto Cañas', 'rcanas.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Edi Lopez', 'elopez.t@gmail.com', '2019-03-05');
INSERT INTO clientes (nombre, email, create_at) VALUES ('David Quezada', 'hquezada.t@gmail.com', '2016-07-20');
INSERT INTO clientes (nombre, email, create_at) VALUES ('Yuri Quijada', 'jquijada.t@gmail.com', '2019-03-05');

/* Populate table productos */
insert into productos (nombre, precio, create_at) values ('Panasonic Pantalla LCD', 259990, now());
insert into productos (nombre, precio, create_at) values ('Sony Camara difital DSC-W320B', 123490, now());
insert into productos (nombre, precio, create_at) values ('Apple iPod shuffle', 1499990, now());
insert into productos (nombre, precio, create_at) values ('Sony Notbook Z110', 37990, now());
insert into productos (nombre, precio, create_at) values ('Hewlett Packard Multifuncional F2280', 69990, now());
insert into productos (nombre, precio, create_at) values ('Bianchi Bicicleta Aro 26', 259990, now());
insert into productos (nombre, precio, create_at) values ('Mica Comoda 5 Cajones', 299990, now());

/* Populate table facturas */
insert into facturas(descripcion, observacion, cliente_id, create_at) values('Factura Equipos Oficina', null,1,now());
insert into factura_items (cantidad, factura_id, producto_id) values(1, 1, 1);
insert into factura_items (cantidad, factura_id, producto_id) values(2, 1, 4);
insert into factura_items (cantidad, factura_id, producto_id) values(1, 1, 5);
insert into factura_items (cantidad, factura_id, producto_id) values(1, 1, 7);

insert into facturas(descripcion, observacion, cliente_id, create_at) values('Factura Bicicleta', 'Alguna nota importante',1,now());
insert into factura_items (cantidad, factura_id, producto_id) values(3, 2, 6);

/*Populate usuarios*/
insert into users(username, nombre, apellido, password, enabled) values('whernandez', 'Walter', 'Hernández','$2a$10$FyBiyNqvqbzTxMaBz6VeVuCe9LGnDTqDD8N4EO37uHhzqsNS.QS8u', true);
insert into users(username, nombre, apellido, password, enabled) values('admin', 'Administrador', 'Administrador','$2a$10$i7EaHT/WV4ZldSQqU86/AeqsLgwL6s13dl2feLJb3iPDyNigyOwHi', true);

insert into authorities(user_id, authority) values(1,'ROLE_USER');
insert into authorities(user_id, authority) values(2,'ROLE_USER');
insert into authorities(user_id, authority) values(2,'ROLE_ADMIN');