package com.Patient.Patient.service.interfaces;

import com.Patient.Patient.dto.AppointmentDTO;
import com.Patient.Patient.dto.PatientDTO;
import com.Patient.Patient.dto.PhysicianDTO;
import com.Patient.Patient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<PatientDTO> getAllPatients();
    List<PatientDTO> getAllPatientsPage(Integer page, Integer itemsPerPage);
    PatientDTO getPatientByCNP(String cnp);
    PatientDTO getPatientByUserId(Integer id_user);
    PatientDTO createPatient(PatientDTO patientDTO);
    void deletePatientByCNP(String cnp);
    PatientDTO updatePatient(String cnp, PatientDTO patientDTO);
    List<PhysicianDTO> getPhysiciansForPatient(String cnp);
    List<AppointmentDTO> getAppointmentsForPatient(String cnp);
    List<AppointmentDTO> getAppointmentsForPatient(String id, String date, String type);
    void validateHeader(String authorizationHeader,String requestedRole);

}
