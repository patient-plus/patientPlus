DROP DATABASE patient_db;

USE patient_db;
select * from emergency_contacts;

select * from users;

select * from prescriptions;

SELECT * from surgery;

SELECT * from insurance;

select * from medication;

select * from pharmacies;

select * from doctor_patient;

select * from appointments;

insert into doctor_patient (doctor_id, patient_id)
VALUES  (2, 1);



