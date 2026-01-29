-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Three clinic owners, with password "clinic_owner"
INSERT INTO authorities(id,authority) VALUES (2,'CLINIC_OWNER');
INSERT INTO appusers(id,username,password,authority) VALUES (2,'clinicOwner1','$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi',2);
INSERT INTO appusers(id,username,password,authority) VALUES (3,'clinicOwner2','$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi',2);

INSERT INTO clinic_owners(id,first_name,last_name,user_id) VALUES (1, 'John', 'Doe', 2);
INSERT INTO clinic_owners(id,first_name,last_name,user_id) VALUES (2, 'Jane', 'Doe', 3);

INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (1, 'Clinic 1', 'Av. Palmera, 26', '955684230', 'PLATINUM', 1);
INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (2, 'Clinic 2', 'Av. Torneo, 52', '955634232', 'GOLD', 2);
INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (3, 'Clinic 3', 'Av. Reina Mercedes, 70', '955382238', 'BASIC', 2);

-- Ten owner user, named owner1 with password 0wn3r
INSERT INTO authorities(id,authority) VALUES (3,'OWNER');
INSERT INTO appusers(id,username,password,authority) VALUES (4,'owner1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'owner2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'owner3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'owner4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'owner5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'owner6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'owner7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'owner8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'owner9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'owner10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
-- One vet user, named vet1 with passwor v3t
/*INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'vet1','veterinarian');*/
INSERT INTO authorities(id,authority) VALUES (4,'VET');
INSERT INTO appusers(id,username,password,authority) VALUES (14,'vet1','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (15,'vet2','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (16,'vet3','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (17,'vet4','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (18,'vet5','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (19,'vet6','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);

INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (1, 'James', 'Carter','Sevilla', 1, 14);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (2, 'Helen', 'Leary','Sevilla', 1, 15);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (3, 'Linda', 'Douglas','Sevilla', 2, 16);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (4, 'Rafael', 'Ortega','Badajoz', 2, 17);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (5, 'Henry', 'Stevens','Badajoz', 3, 18);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (6, 'Sharon', 'Jenkins','Cádiz', 3, 19);

INSERT INTO specialties(id,name) VALUES (1, 'radiology');
INSERT INTO specialties(id,name) VALUES (2, 'surgery');
INSERT INTO specialties(id,name) VALUES (3, 'dentistry');

INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (2, 1);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 3);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (4, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (5, 1);

INSERT INTO types(id,name)  VALUES (1, 'cat');
INSERT INTO types(id,name)  VALUES (2, 'dog');
INSERT INTO types(id,name)  VALUES (3, 'lizard');
INSERT INTO types(id,name)  VALUES (4, 'snake');
INSERT INTO types(id,name)  VALUES (5, 'bird');
INSERT INTO types(id,name)  VALUES (6, 'hamster');
INSERT INTO types(id,name)  VALUES (7, 'turtle');

INSERT INTO	owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Sevilla', '608555103', 4, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sevilla', '608555174', 5, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'Sevilla', '608558763', 6, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Sevilla', '608555319', 7, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Sevilla', '608555765', 8, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Badajoz', '608555264', 9, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Badajoz', '608555538', 10, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Badajoz', '608557683', 11, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail','Cádiz', '685559435', 12, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Cádiz', '685555487', 13, 1);

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (1, 7, '2013-01-01 13:00', 'rabies shot', 4);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (2, 8, '2013-01-02 15:30', 'rabies shot', 5);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (3, 8, '2013-01-03 9:45', 'neutered', 5);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (4, 7, '2013-01-04 17:30', 'spayed', 4);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (5, 1, '2013-01-01 13:00', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (6, 1, '2020-01-02 15:30', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (7, 1, '2020-01-02 15:30', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (8, 2, '2013-01-03 9:45', 'neutered', 2);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (9, 3, '2013-01-04 17:30', 'spayed', 3);

INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (1, 'Consultation about vaccines', 0, 'ANSWERED', 1, 1, '2023-01-04 17:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (2, 'My dog gets really nervous', 0, 'PENDING', 1, 1, '2022-01-02 19:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (3, 'My cat does not eat', 0, 'PENDING', 2, 2, '2023-04-11 11:20');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (4, 'My lovebird does not sing', 0, 'CLOSED', 2, 2, '2023-02-24 10:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (5, 'My snake has layed eggs', 0, 'PENDING', 10, 12, '2023-04-11 11:20');

INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (1, 'What vaccine should my dog receive?', '2023-01-04 17:32', 4, 1);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (2, 'Rabies'' one.', '2023-01-04 17:36', 14, 1);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (3, 'My dog gets really nervous during football matches. What should I do?', '2022-01-02 19:31', 4, 2);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (4, 'It also happens with tennis matches.', '2022-01-02 19:33', 4, 2);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (5, 'My cat han''t been eating his fodder.', '2023-04-11 11:30', 5, 3);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (6, 'Try to give him some tuna to check if he eats that.', '2023-04-11 15:20', 15, 3);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (7, 'My lovebird doesn''t sing as my neighbour''s one.', '2023-02-24 12:30', 5, 4);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (8, 'Lovebirds do not sing.', '2023-02-24 18:30', 16, 4);

/*INSERT INTO cartas(id,imagen,tipo) VALUES (1,'/ImagenesCartas/CartaDatos.jpg','DATOS');
INSERT INTO cartas(id,imagen,tipo) VALUES (2,'/ImagenesCartas/MiradaFija.jpg','MIRADA_FIJA');
INSERT INTO cartas(id,imagen,tipo) VALUES (3,'/ImagenesCartas/Carta1.jpg','PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (4, '/ImagenesCartas/Carta2.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (5, '/ImagenesCartas/Carta3.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (6, '/ImagenesCartas/Carta4.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (7, '/ImagenesCartas/Carta5.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (8, '/ImagenesCartas/Carta6.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (9, '/ImagenesCartas/Carta7.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (10, '/ImagenesCartas/Carta8.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (11, '/ImagenesCartas/Carta9.jpg', 'PUNTERIA');
INSERT INTO cartas (id, imagen, tipo) VALUES (12, '/ImagenesCartas/Carta10.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (13, '/ImagenesCartas/Carta11.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (14, '/ImagenesCartas/Carta12.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (15, '/ImagenesCartas/Carta13.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (16, '/ImagenesCartas/Carta14.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (17, '/ImagenesCartas/Carta15.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (18, '/ImagenesCartas/Carta16.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (19, '/ImagenesCartas/Carta17.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (20, '/ImagenesCartas/Carta18.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (21, '/ImagenesCartas/Carta19.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (22, '/ImagenesCartas/Carta20.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (23, '/ImagenesCartas/Carta21.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (24, '/ImagenesCartas/Carta22.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (25, '/ImagenesCartas/Carta23.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (26, '/ImagenesCartas/Carta24.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (27, '/ImagenesCartas/Carta25.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (28, '/ImagenesCartas/Carta26.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (29, '/ImagenesCartas/Carta27.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (30, '/ImagenesCartas/Carta28.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (31, '/ImagenesCartas/Carta29.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (32, '/ImagenesCartas/Carta30.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (33, '/ImagenesCartas/Carta31.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (34, '/ImagenesCartas/Carta32.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (35, '/ImagenesCartas/Carta33.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (36, '/ImagenesCartas/Carta34.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (37, '/ImagenesCartas/Carta35.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (38, '/ImagenesCartas/Carta36.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (39, '/ImagenesCartas/Carta37.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (40, '/ImagenesCartas/Carta38.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (41, '/ImagenesCartas/Carta39.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (42, '/ImagenesCartas/Carta40.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (43, '/ImagenesCartas/Carta41.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (44, '/ImagenesCartas/Carta42.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (45, '/ImagenesCartas/Carta43.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (46, '/ImagenesCartas/Carta44.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (47, '/ImagenesCartas/Carta45.jpg', 'INTIMIDACION');
INSERT INTO cartas (id, imagen, tipo) VALUES (48, '/ImagenesCartas/Carta46.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (49, '/ImagenesCartas/Carta47.jpg', 'DISPARO');
INSERT INTO cartas (id, imagen, tipo) VALUES (50, '/ImagenesCartas/Carta48.jpg', 'FINTA');
INSERT INTO cartas (id, imagen, tipo) VALUES (51, '/ImagenesCartas/Carta49.jpg', 'CINTURON_DE_ARMAS');
INSERT INTO cartas (id, imagen, tipo) VALUES (52, '/ImagenesCartas/Carta50.jpg', 'DISPARO');*/

