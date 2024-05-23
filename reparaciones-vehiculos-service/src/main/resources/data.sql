--Insertar Bonos
INSERT INTO public.datos_bonos(marca_automovil, cantidad_bonos, monto_bono)
VALUES ('Toyota', 5, 70000),
       ('Ford', 2, 50000),
       ('Hyundai', 1, 30000),
       ('Honda', 7, 40000);





--Insertar historial
INSERT INTO public.historial_reparaciones(patente,fecha_ingreso_taller,hora_ingreso_taller, monto_total_pagar, fecha_salida_taller, hora_salida_taller, fecha_cliente_se_lleva_vehiculo, hora_cliente_se_lleva_vehiculo, descuentos, recargos,iva, pagado)
VALUES ('CFYF55','2022-01-18','10:30:00', 337008,'2022-01-20','10:30:00', '2022-01-21','15:25:00',43500,56700,53808, true),
       ('TW6977','2023-12-15','12:55:00', 0,'2023-12-19','13:30:00', '2023-12-20','13:27:00',0,0,0, false),
       ('CFYF55','2024-03-18','10:30:00', 0,'2024-03-19','10:30:00', '2024-03-21','15:25:00',0,0,0,false);




--Insertar reparaciones
INSERT INTO public.reparaciones(patente, descripcion, tipo_reparacion, id_historial_reparaciones)
VALUES ('CFYF55','Reparacion sistema electrico', 5,1),
       ('CFYF55','Reparacion sistema de frenos', 1,1),
       ('TW6977','Reparacion sistema de frenos', 1, 2),
       ('TW6977','Reparacion sistema de combustible', 10,2),
       ('CFYF55','Reparacion sistema electrico', 5,3),
       ('CFYF55','Reparacion sistema de frenos', 1,3),
       ('KDJW65','Reparacion sistema de refrigeracion', 2,4);




