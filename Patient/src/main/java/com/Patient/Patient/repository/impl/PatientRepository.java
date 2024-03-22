package com.Patient.Patient.repository.impl;


import com.Patient.Patient.model.Appointment;
import com.Patient.Patient.model.Patient;
import com.Patient.Patient.model.Physician;
import com.Patient.Patient.repository.interfaces.IPatientJPARepository;
import com.Patient.Patient.repository.interfaces.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class PatientRepository implements IPatientRepository {

    private final IPatientJPARepository patientRepository;

    @Autowired
    public PatientRepository(IPatientJPARepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Page<Patient> getAllPatientsPage(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public Boolean emailIsAlreadyRegistered(String email, Integer id_user) {
        return patientRepository.emailIsAlreadyRegistered(email,id_user);
    }

    @Override
    public Boolean phoneNumberIsAlreadyRegistered(String telefon, Integer id_user) {
        return patientRepository.phoneNumberIsAlreadyRegistered(telefon,id_user);
    }

    @Override
    public Optional<Patient> getPatientByCNP(String cnp) {
        return patientRepository.findByCNP(cnp);
    }


    @Override
    public Optional<Patient> getPatientByUserId(Integer id_user) {
        return patientRepository.findByUserId(id_user);
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deleteByCNP(String cnp) {
        Optional<Patient> patient = patientRepository.findByCNP(cnp);
        patient.ifPresent(patientRepository::delete);
    }
    @Override

    public Patient updatePatient(String cnp,Patient patient) {

        Optional<Patient> existingPatient = patientRepository.findByCNP(cnp);

        if (existingPatient.isPresent()) {


            Patient updatedPatient = existingPatient.get();
            updatedPatient.setId_user(patient.getId_user());
            updatedPatient.setNume(patient.getNume());
            updatedPatient.setPrenume(patient.getPrenume());
            updatedPatient.setEmail(patient.getEmail());
            updatedPatient.setTelefon(patient.getTelefon());
            updatedPatient.setData_nasterii(patient.getData_nasterii());
            updatedPatient.setIs_active(patient.getIs_active());

            return patientRepository.save(updatedPatient);
        } else {
            return patientRepository.save(patient);
        }
    }


    @Override
    public List<Physician> getPhysiciansForPatient(String cnp) {
        Optional<Patient> patient = patientRepository.findByCNP(cnp);
        if (patient.isPresent()) {
            Set<Physician> physicians = patient.get().getPhysicians();
            return new ArrayList<>(physicians);
        } else {
            return null;
        }
    }
    @Override
    public List<Appointment> getAppointmentsForPatient(String cnp) {
        Optional<Patient> patient = patientRepository.findByCNP(cnp);
        if (patient.isPresent()) {
            Set<Appointment> appointments = patient.get().getAppointments();
            return new ArrayList<>(appointments);
        } else {
            return null;
        }
    }
}
