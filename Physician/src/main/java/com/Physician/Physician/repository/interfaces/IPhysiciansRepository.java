package com.Physician.Physician.repository.interfaces;



import com.Physician.Physician.dto.PhysicianDTO;
import com.Physician.Physician.model.Patient;
import com.Physician.Physician.model.Physician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPhysiciansRepository {
    List<Physician> getAllPhysicians();
    Optional<Physician> getPhysicianById(Integer id);
    Optional<Physician> getPhysicianByUserId(Integer id_user);
    Physician createPhysician(Physician physician);
    void deletePhysicianById(Integer id);
    Physician updatePhysician(Integer id, Physician physician) ;
    List<Patient> getPatientsForPhysician(Integer id);
    Page<Physician> getAllPhysiciansPage(Pageable pageable);
    Boolean emailIsAlreadyRegistered(String email,Integer id);
    Boolean phoneNumberIsAlreadyRegistered(String telefon, Integer id);

}
