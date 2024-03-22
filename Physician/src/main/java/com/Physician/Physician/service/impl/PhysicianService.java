package com.Physician.Physician.service.impl;


import com.Physician.Physician.dto.PatientDTO;
import com.Physician.Physician.dto.PhysicianDTO;
import com.Physician.Physician.exception.ConflictException;
import com.Physician.Physician.exception.ForbiddenException;
import com.Physician.Physician.exception.InvalidDataException;
import com.Physician.Physician.exception.UnauthorizedException;
import com.Physician.Physician.model.Patient;
import com.Physician.Physician.model.Physician;
import com.Physician.Physician.repository.interfaces.IPhysiciansRepository;

import com.Physician.Physician.service.interfaces.IPhysicianService;
import com.Physician.Physician.service.interfaces.mappers.PatientMapper;
import com.Physician.Physician.service.interfaces.mappers.PhysicianMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PhysicianService implements IPhysicianService {


    private final IPhysiciansRepository physicianRepository;
    private final PatientMapper patientMapper;
    private final PhysicianMapper physicianMapper;

    @Autowired
    public PhysicianService(IPhysiciansRepository physicianRepository, PatientMapper patientMapper, PhysicianMapper physicianMapper) {
        this.physicianRepository = physicianRepository;
        this.patientMapper = patientMapper;
        this.physicianMapper=physicianMapper;
    }

    @Override
    public List<PhysicianDTO> getAllPhysicians() {
        List<Physician> physicians = physicianRepository.getAllPhysicians();
        return physicians.stream().map(physicianMapper::mapEntityToDto).toList();
    }

    @Override
    public PhysicianDTO getPhysicianById(Integer id) {
        validateId(id);
        Optional<Physician> physicianOptional = physicianRepository.getPhysicianById(id);
        return physicianOptional.map(physicianMapper::mapEntityToDto).orElse(null);
    }
    @Override
    public List<PhysicianDTO> getAllPhysiciansPage(Integer page, Integer itemsPerPage) {
        validatePagination(page,itemsPerPage);
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        Page<Physician> physicianPage = physicianRepository.getAllPhysiciansPage(pageable);
        return physicianPage.getContent()
                .stream()
                .map(physicianMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public  PhysicianDTO getPhysicianByUserId(Integer id_user)
    {
        validateId(id_user);
        return  physicianRepository.getPhysicianByUserId(id_user).map(physicianMapper::mapEntityToDto).orElse(null);
    }

    @Override
    public PhysicianDTO createPhysician(PhysicianDTO physicianDTO) {
        validateFields(physicianDTO);
        Physician physician = physicianMapper.mapDtoToEntity(physicianDTO);
        Physician savedPhysician = physicianRepository.createPhysician(physician);
        return physicianMapper.mapEntityToDto(savedPhysician);
    }

    @Override
    public void deletePhysicianById(Integer id) {
        validateId(id);
        Optional<Physician> physicianOptional = physicianRepository.getPhysicianById(id);
        if(physicianOptional.isPresent()) physicianRepository.deletePhysicianById(id);
    }

    @Override
    public PhysicianDTO updatePhysician(Integer id, PhysicianDTO physicianDTO) {
        validateId(id);
        validateFields(physicianDTO);
        Physician updatedPhysician = physicianRepository.updatePhysician(id, physicianMapper.mapDtoToEntity(physicianDTO));
        return physicianMapper.mapEntityToDto(updatedPhysician);

    }
    @Override
    public List<PatientDTO> getPatientsForPhysician(Integer id) {
        validateId(id);
        List<Patient> patients = physicianRepository.getPatientsForPhysician(id);
        return patients.stream().map(patientMapper::mapEntityToDto).toList();

    }
    @Override
    public List<PhysicianDTO> searchPhysiciansByName(String name) {
        return physicianRepository.getAllPhysicians()
                .stream()
                .map(physicianMapper::mapEntityToDto)
                .filter(physicianDTO -> physicianDTO.getNume().contains(name)).toList();
    }

    @Override
    public List<PhysicianDTO> searchPhysiciansBySpecialization(String specialization) {
        return physicianRepository.getAllPhysicians()
                .stream()
                .map(physicianMapper::mapEntityToDto)
                .filter(physicianDTO -> physicianDTO.getSpecializare().equals(specialization)).toList();
    }

    @Override
    public void validateHeader(String authorizationHeader,String requestedRole) {

        List<String> listRoles= List.of(requestedRole.split(","));
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            throw new UnauthorizedException("Authorization header is missing");
        }
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            JWSObject jwsObject;
            JWTClaimsSet claims;
            jwsObject = JWSObject.parse(token);
            claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            String role = (String) claims.getClaim("role");
            if(!listRoles.contains(role)) throw new ForbiddenException("Access forbidden for "+role);
        } catch (java.text.ParseException e) {
            throw new InvalidDataException("Invalid token");
        }
    }

    private void validateId(Integer id) {
        if (id == null) {
            throw new InvalidDataException("User Id cannot be null.");
        }

        if (id <= 0) {
            throw new InvalidDataException("Invalid User Id format. must be a positive number.");
        }
    }

    private void validateFields(PhysicianDTO physicianDTO) {
        if(physicianDTO.getEmail().isBlank())
            throw new InvalidDataException("Email can not be empty");
        if(physicianDTO.getTelefon().isBlank())
            throw new InvalidDataException("Phone number can not be empty");
        if(!physicianDTO.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b"))
            throw new InvalidDataException("Invalid email");
        if (physicianRepository.emailIsAlreadyRegistered(physicianDTO.getEmail(), physicianDTO.getId_doctor())) {
            throw new ConflictException("Email already registered");
        }
        if (physicianRepository.phoneNumberIsAlreadyRegistered(physicianDTO.getTelefon(), physicianDTO.getId_doctor())) {
            throw new ConflictException("Phone number already registered");
        }
        if(!physicianDTO.getTelefon().matches("^0[0-9]{9}$"))
            throw new InvalidDataException("Invalid phone number");
        if(physicianDTO.getNume().length()>50)
            throw new InvalidDataException("Last name exceeds maximum limit of 50");
        if(physicianDTO.getPrenume().length()>50)
            throw new InvalidDataException("First name exceeds maximum limit of 50");
        if(physicianDTO.getEmail().length()>70)
            throw new InvalidDataException("Email exceeds maximum limit of 70");


    }

    private void validatePagination(Integer page, Integer itemsPerPage){
        if (page == null || page < 1) {
            throw new InvalidDataException("Page must be positive");
        }

        if (itemsPerPage == null || itemsPerPage < 1) {
            throw new InvalidDataException("ItemsPerPage must be positive");
        }

        if (itemsPerPage > 25) {
            throw new InvalidDataException("ItemsPerPage exceeds maximum limit of 25");
        }
        if (page > 1000) {
            throw new InvalidDataException("Page exceeds maximum limit of 1000");
        }
    }


}