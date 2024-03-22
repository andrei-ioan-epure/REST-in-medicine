package com.Physician.Physician.repository.impl;

import com.Physician.Physician.model.Patient;
import com.Physician.Physician.model.Physician;
import com.Physician.Physician.repository.interfaces.IPhysicianJPARepository;
import com.Physician.Physician.repository.interfaces.IPhysiciansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class PhysicianRepository implements IPhysiciansRepository {

    private final IPhysicianJPARepository physicianRepository;

    @Autowired
    PhysicianRepository(IPhysicianJPARepository physicianRepository)
    {
            this.physicianRepository=physicianRepository;
    }

    @Override
    public List<Physician> getAllPhysicians() {
        return physicianRepository.findAll();
    }

    @Override
    public Page<Physician> getAllPhysiciansPage(Pageable pageable) {
        return physicianRepository.findAll(pageable);
    }

    @Override
    public Boolean emailIsAlreadyRegistered(String email, Integer id) {
        return physicianRepository.emailIsAlreadyRegistered(email,id);
    }

    @Override
    public Boolean phoneNumberIsAlreadyRegistered(String telefon, Integer id) {
        return physicianRepository.phoneNumberIsAlreadyRegistered(telefon,id);
    }

    @Override
    public Optional<Physician> getPhysicianById(Integer id) {
       return  physicianRepository.findByIdDoctor(id);
    }

    @Override
    public Optional<Physician> getPhysicianByUserId(Integer id_user) {
        return physicianRepository.findByUserId(id_user);
    }


    @Override
    public Physician createPhysician(Physician physician) {
        return physicianRepository.save(physician);

    }

    @Override
    public Physician updatePhysician(Integer id, Physician physician) {
        Optional<Physician> existingPhysician = physicianRepository.findByIdDoctor(id);

        if (existingPhysician.isPresent()) {


            Physician updatedPhysician = existingPhysician.get();
            updatedPhysician.setId_user(physician.getId_user());
            updatedPhysician.setNume(physician.getNume());
            updatedPhysician.setPrenume(physician.getPrenume());
            updatedPhysician.setEmail(physician.getEmail());
            updatedPhysician.setTelefon(physician.getTelefon());
            updatedPhysician.setSpecializare(physician.getSpecializare());;

            return physicianRepository.save(updatedPhysician);
        } else {
            return physicianRepository.save(physician);
        }
    }

    @Override
    public void deletePhysicianById(Integer id) {
        Optional<Physician> physician = physicianRepository.findByIdDoctor(id);
        physician.ifPresent(physicianRepository::delete);
    }

    @Override
    public List<Patient> getPatientsForPhysician(Integer id) {
        Optional<Physician> physician = physicianRepository.findByIdDoctor(id);
        if (physician.isPresent()) {
            Set<Patient> patients = physician.get().getPatients();
            return new ArrayList<>(patients);
        } else {
            return null;
        }
    }
}
