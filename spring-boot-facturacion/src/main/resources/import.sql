/*Populate table negocios*/
insert into negocios(nombre, nombre_corto, iva, logo, direccion, telefono) values('TecnoPhone Portillo', 'TecnoPhone', 13, 'logo_01.jpeg', 'Brro. El Carmen, Agua Caliente, Chalatenango', '2309-56254');


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

/*Populate table unidades*/
INSERT INTO unidades(nombre, nombre_corto) VALUES('Unidad', 'U.');

/* Populate table productos */
insert into productos (unidad_id, nombre, precio, costo, existencia, create_at, inventariable, exento) values (1, 'Panasonic Pantalla LCD', 150.99, 100, 89, now(), true, false);
insert into productos (unidad_id, nombre, precio, costo, existencia, create_at, inventariable, exento) values (1, 'Sony Camara difital DSC-W320B', 360.99, 300, 150, now(), true, false);
insert into productos (unidad_id, nombre, precio, costo, existencia, create_at, inventariable, exento) values (1, 'Apple iPod shuffle', 99.90, 50, 25, now(), true, false);
insert into productos (unidad_id, nombre, precio, costo, existencia, create_at, inventariable, exento) values (1, 'Sony Notbook Z110', 489.68, 400, 10, now(), true, false);
insert into productos (unidad_id, nombre, precio, costo, existencia, create_at, inventariable, exento) values (1, 'Hewlett Packard Multifuncional F2280', 130.54, 100, 13, now(), true, false);
insert into productos (unidad_id, nombre, precio, costo, existencia, create_at, inventariable, exento) values (1, 'Bianchi Bicicleta Aro 26', 189.00, 100, 5, now(), true, false);
insert into productos (unidad_id, nombre, precio, costo, existencia, create_at, inventariable, exento) values (1, 'Mica Comoda 5 Cajones', 35.25, 30, 8, now(), true, false);

insert into tipos_movimiento_inv(nombre, accion) values('Inventario inicial', 'I');
insert into tipos_movimiento_inv(nombre, accion) values('Entra por compra', 'I');
insert into tipos_movimiento_inv(nombre, accion) values('Salida por venta', 'D');
insert into tipos_movimiento_inv(nombre, accion) values('Ajuste de inventario (Entrada)', 'I');
insert into tipos_movimiento_inv(nombre, accion) values('Ajuste de inventario (Salida)', 'D');

insert into kardex(tipo_movimiento_id, producto_id, saldo, costo_saldo, total_saldo) values(1, 1, 89, 100, 89*100);
insert into kardex(tipo_movimiento_id, producto_id, saldo, costo_saldo, total_saldo) values(1, 2, 150, 300, 150*300);
insert into kardex(tipo_movimiento_id, producto_id, saldo, costo_saldo, total_saldo) values(1, 3, 25, 50, 25*50);
insert into kardex(tipo_movimiento_id, producto_id, saldo, costo_saldo, total_saldo) values(1, 4, 10, 400, 10*400);
insert into kardex(tipo_movimiento_id, producto_id, saldo, costo_saldo, total_saldo) values(1, 5, 13, 100, 13*100);
insert into kardex(tipo_movimiento_id, producto_id, saldo, costo_saldo, total_saldo) values(1, 6, 8, 35.25, 8*35.25);

/* Populate tipos documentos */
insert into tipos_documento(nombre) values('Recibo');
insert into tipos_documento(nombre) values('Factura');
insert into tipos_documento(nombre) values('Crédito Fiscal');

insert into documentos_inicial(tipo_documento_id, serie, desde, hasta, activo, create_at) values(2,'',101,5000,true,now());
insert into documentos_inicial(tipo_documento_id, serie, desde, hasta, activo, create_at) values(3,'',1,5000,true,now());

/* Populate table facturas */

/*insert into facturas(descripcion,  cliente_id, create_at) values('Factura Equipos Oficina', 1,now());*/
/*insert into factura_items (cantidad, factura_id, producto_id) values(1, 1, 1);*/
/*insert into factura_items (cantidad, factura_id, producto_id) values(2, 1, 4);*/
/*insert into factura_items (cantidad, factura_id, producto_id) values(1, 1, 5);*/
/*insert into factura_items (cantidad, factura_id, producto_id) values(1, 1, 7);*/

/*insert into facturas(descripcion, cliente_id, create_at) values('Factura Bicicleta',1,now());/*
/*insert into factura_items (cantidad, factura_id, producto_id) values(3, 2, 6);/*

/*Populate usuarios*/
insert into users(username, nombre, apellido, password, enabled) values('admin', 'Administrador', 'Administrador','$2a$10$i7EaHT/WV4ZldSQqU86/AeqsLgwL6s13dl2feLJb3iPDyNigyOwHi', true);

insert into roles(nombre) values('DEVELOPER');
insert into roles(nombre) values('ADMINISTRADOR');
insert into roles(nombre) values('USUARIO');
insert into users_roles(user_id, role_id) values(1,1);
insert into users_roles(user_id, role_id) values(1,2);
insert into users_roles(user_id, role_id) values(1,3);

/*Populate reportes*/
insert into reports(nombre, report) values('Listado de Usuarios','rpt_users');
insert into reports(nombre, report) values('Ingresos','rpt_ventas');
insert into parametros(report_id, nombre, tipo, lista, valor) values(1, 'Mostrar', 'L', 'select 0 as codigo, cast('' Todos'' as varchar) as nombre union all select 1 as codigo, cast(''Activos'' as varchar) as nombre union all select 2 as codigo, cast(''Inactivo'' as varchar) as nombre', '');
insert into parametros(report_id, nombre, tipo, lista, valor) values(2, 'Del', 'F', '', '');
insert into parametros(report_id, nombre, tipo, lista, valor) values(2, 'Al', 'F', '', '');
insert into reports_roles(report_id, role_id) values(1,1)
insert into reports_roles(report_id, role_id) values(1,2)
insert into reports_roles(report_id, role_id) values(2,1)
insert into reports_roles(report_id, role_id) values(2,2)

