package com.Physician.Physician.service.interfaces;

import com.Physician.Physician.dto.PatientDTO;
import com.Physician.Physician.dto.PhysicianDTO;

import java.util.List;

public interface IPhysicianService {

    List<PhysicianDTO> getAllPhysicians();
    PhysicianDTO getPhysicianById(Integer id);
    PhysicianDTO getPhysicianByUserId(Integer id_user);
    PhysicianDTO createPhysician(PhysicianDTO physicianDTO);
    void deletePhysicianById(Integer id);
    PhysicianDTO updatePhysician(Integer id, PhysicianDTO patientDTO);
    List<PatientDTO> getPatientsForPhysician(Integer id);
    List<PhysicianDTO> searchPhysiciansByName(String name);
    List<PhysicianDTO> searchPhysiciansBySpecialization(String specialization);
    List<PhysicianDTO> getAllPhysiciansPage(Integer page, Integer itemsPerPage);
    void validateHeader(String authorizationHeader,String requestedRole);

}
