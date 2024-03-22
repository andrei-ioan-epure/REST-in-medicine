package com.Patient.Patient.service.impl;


import com.Patient.Patient.dto.AppointmentDTO;
import com.Patient.Patient.dto.PatientDTO;
import com.Patient.Patient.dto.PhysicianDTO;
import com.Patient.Patient.exception.ConflictException;
import com.Patient.Patient.exception.ForbiddenException;
import com.Patient.Patient.exception.InvalidDataException;
import com.Patient.Patient.exception.UnauthorizedException;
import com.Patient.Patient.model.Appointment;
import com.Patient.Patient.model.Patient;
import com.Patient.Patient.model.Physician;
import com.Patient.Patient.repository.interfaces.IPatientRepository;
import com.Patient.Patient.service.interfaces.IPatientService;
import com.Patient.Patient.service.interfaces.mappers.AppointmentMapper;
import com.Patient.Patient.service.interfaces.mappers.PatientMapper;
import com.Patient.Patient.service.interfaces.mappers.PhysicianMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class PatientService implements IPatientService {


    private final IPatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final PhysicianMapper physicianMapper;
    private final AppointmentMapper appointmentMapper;
    private static final String SECRET_KEY = "3ff2e1h57aefb8bdf1542850d663n007d620e40520b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";
    Logger logger = LoggerFactory.getLogger(PatientService.class);


    @Autowired
    public PatientService(IPatientRepository patientRepository,
                          PatientMapper patientMapper,
                          PhysicianMapper physicianMapper,
                          AppointmentMapper appointmentMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.physicianMapper=physicianMapper;
        this.appointmentMapper=appointmentMapper;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.getAllPatients();
        return patients.stream().map(patientMapper::mapEntityToDto).toList();
    }
    @Override
    public List<PatientDTO> getAllPatientsPage(Integer page, Integer itemsPerPage) {
       // validatePagination(page,itemsPerPage);
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        Page<Patient> patientPage = patientRepository.getAllPatientsPage(pageable);
        return patientPage.getContent()
                .stream()
                .map(patientMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public PatientDTO getPatientByCNP(String cnp) {
        validateCNP(cnp);
        Optional<Patient> patientOptional = patientRepository.getPatientByCNP(cnp);
        return patientOptional.map(patientMapper::mapEntityToDto).orElse(null);
    }
    @Override
    public  PatientDTO getPatientByUserId(Integer id_user)
    {
       validateUserId(id_user);
        return  patientRepository.getPatientByUserId(id_user).map(patientMapper::mapEntityToDto).orElse(null);
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        validateCNP(patientDTO.getCnp());
        validateFields(patientDTO);
        verifyIsAdult(patientDTO.getDataNasterii());
        Patient patient = patientMapper.mapDtoToEntity(patientDTO);
        Patient savedPatient = patientRepository.createPatient(patient);
        return patientMapper.mapEntityToDto(savedPatient);
    }

    @Override
    public void deletePatientByCNP(String cnp) {
        validateCNP(cnp);
        Optional<Patient> patientOptional = patientRepository.getPatientByCNP(cnp);
        if(patientOptional.isPresent()) patientRepository.deleteByCNP(cnp);
    }

    @Override
    public PatientDTO updatePatient(String cnp, PatientDTO patientDTO) {
        validateCNP(cnp);
        validateFields(patientDTO);
        Patient updatedPatient = patientRepository.updatePatient( cnp,patientMapper.mapDtoToEntity(patientDTO));
        return patientMapper.mapEntityToDto(updatedPatient);

    }
    @Override
    public List<PhysicianDTO> getPhysiciansForPatient( String cnp) {
        validateCNP(cnp);
        List<Physician> physicians = patientRepository.getPhysiciansForPatient(cnp);
        return (physicians==null)? null: physicians.stream().map(physicianMapper::mapEntityToDto).toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsForPatient(String cnp) {
        validateCNP(cnp);
        List<Appointment> appointments = patientRepository.getAppointmentsForPatient(cnp);
        return (appointments == null)? null:  appointments.stream().map(appointmentMapper::mapEntityToDto).toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsForPatient(String cnp, String date, String type) {
        validateCNP(cnp);
        List<Appointment> appointments = patientRepository.getAppointmentsForPatient(cnp);
        if(appointments == null) return null;

        if (type != null) {

            int dateInteger = Optional.ofNullable(date)
                .map(Integer::parseInt)
                .orElseThrow(() -> new InvalidDataException("Invalid date"));

            switch (type) {
                case "month" -> appointments = appointments.stream()
                        .filter(appointment -> appointment.getDate().getMonthValue() == dateInteger)
                        .toList();
                case "day" -> appointments = appointments.stream()
                        .filter(appointment -> appointment.getDate().getDayOfMonth() == dateInteger)
                        .toList();
                default -> throw new InvalidDataException("Invalid type");

                }

        }
        else{
            validateDate(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate parsedDate = LocalDate.parse( date, formatter);
            appointments = appointments.stream()
                    .filter(appointment -> appointment.getDate().toLocalDate().isEqual(parsedDate))
                    .toList();
        }
        return appointments.stream()
                .map(appointmentMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public void validateHeader(String authorizationHeader,String requestedRole) {

        logger.info("Authorization Header: " + authorizationHeader);

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

    private void verifyIsAdult(LocalDateTime dateOfBirth) {
        LocalDateTime now = LocalDateTime.now();
        int age = Period.between(dateOfBirth.toLocalDate(), now.toLocalDate()).getYears();

        if (age < 18) throw  new InvalidDataException("Patients must be at least 18 years old");
    }

    private void validateDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            dateFormat.parse(dateStr);

            int day = Integer.parseInt(dateStr.substring(0, 2));
            int month = Integer.parseInt(dateStr.substring(3, 5));
            int year = Integer.parseInt(dateStr.substring(6));

            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1000) {
                throw new InvalidDataException("Invalid date");
            }


        } catch (Exception e) {
            throw new InvalidDataException("Invalid date format: must be a valid date in format \"dd-MM-yyyy\"");
        }

    }


    private void validateUserId(Integer user_id) {
        if (user_id == null) {
            throw new InvalidDataException("User Id cannot be null.");
        }

        if (user_id <= 0) {
            throw new InvalidDataException("Invalid User Id format: must be a positive number.");
        }
    }


    private void validateCNP(String cnp)
    {
        if (!Pattern.matches("^\\d{13}$", cnp)) {
            throw new InvalidDataException("Invalid CNP format: must be 13 digits");
        }

//        int[] digits = new int[13];
//        for (int i = 0; i < 13; i++) {
//            digits[i] = Character.getNumericValue(cnp.charAt(i));
//        }
//
//        int s = (2 * digits[0] + 7 * digits[1] + 9 * digits[2] + digits[3] + 4 * digits[4] + 6 * digits[5] +
//                3 * digits[6] + 5 * digits[7] + 8 * digits[8] + 2 * digits[9] + 7 * digits[10] + 9 * digits[11]) % 11;
//
//        if (!((s < 10 && s == digits[12]) || (s == 10 && digits[12] == 1))) {
//            throw new InvalidDataException("Invalid CNP");
//        }
    }


    private void validateFields(PatientDTO patientDTO) {
        if(patientDTO.getEmail().isBlank())
            throw new InvalidDataException("Email can not be empty");
        if(patientDTO.getTelefon().isBlank())
            throw new InvalidDataException("Phone number can not be empty");
        if(!patientDTO.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b"))
            throw new InvalidDataException("Invalid email");
        if (patientRepository.emailIsAlreadyRegistered(patientDTO.getEmail(), patientDTO.getUserId())) {
            throw new ConflictException("Email already registered");
        }
        if (patientRepository.phoneNumberIsAlreadyRegistered(patientDTO.getTelefon(), patientDTO.getUserId())) {
            throw new ConflictException("Phone number already registered");
        }
        if(!patientDTO.getTelefon().matches("^0[0-9]{9}$"))
            throw new InvalidDataException("Invalid phone number");
        if(patientDTO.getNume().length()>50)
            throw new InvalidDataException("Last name exceeds maximum limit of 50");
        if(patientDTO.getPrenume().length()>50)
            throw new InvalidDataException("First name exceeds maximum limit of 50");
        if(patientDTO.getEmail().length()>70)
            throw new InvalidDataException("Email exceeds maximum limit of 70");


    }

    private void validatePagination(Integer page, Integer itemsPerPage) {
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