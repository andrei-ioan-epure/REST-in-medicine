package com.Patient.Patient.repository.interfaces;

import com.Patient.Patient.model.Appointment;
import com.Patient.Patient.model.Patient;
import com.Patient.Patient.model.Physician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPatientRepository {
    List<Patient> getAllPatients();
    Optional<Patient> getPatientByCNP(String cnp);
    Optional<Patient> getPatientByUserId(Integer id_user);
    Patient createPatient(Patient patient);
    void deleteByCNP(String cnp);
    Patient updatePatient(String cnp,Patient patient);
    List<Physician> getPhysiciansForPatient(String cnp);
    List<Appointment> getAppointmentsForPatient(String cnp);
    Page<Patient> getAllPatientsPage(Pageable pageable);
    Boolean emailIsAlreadyRegistered(String email,Integer id_user);
    Boolean phoneNumberIsAlreadyRegistered(String telefon, Integer id_user);

}
