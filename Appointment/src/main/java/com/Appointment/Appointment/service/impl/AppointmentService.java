package com.Appointment.Appointment.service.impl;

import com.Appointment.Appointment.exception.ConflictException;
import com.Appointment.Appointment.exception.ForbiddenException;
import com.Appointment.Appointment.exception.InvalidDataException;
import com.Appointment.Appointment.exception.UnauthorizedException;
import com.Appointment.Appointment.model.Appointment;
import com.Appointment.Appointment.model.enums.AppointmentStatus;
import com.Appointment.Appointment.repository.interfaces.IAppointmentRepository;
import com.Appointment.Appointment.dto.AppointmentDTO;
import com.Appointment.Appointment.service.interfaces.IAppointmentService;
import com.Appointment.Appointment.service.interfaces.mappers.AppointmentMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentService(IAppointmentRepository appointmentRepository,
                              AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper=appointmentMapper;
    }
    @Override
    public List<AppointmentDTO> getAppointments(Integer page, Integer itemsPerPage) {
        validatePagination(page,itemsPerPage);
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        Page<Appointment> appointmentPage = appointmentRepository.getAppointmentsPage(pageable);

        return   appointmentPage.getContent()
                .stream()
                .map(appointmentMapper::mapEntityToDto)
                .toList();

    }
    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(String cnp) {
        validateCNP(cnp);
        Optional<List<Appointment>> appointment = appointmentRepository.findByPatientId(cnp);
        return appointment.map(appointments -> appointments.stream().map(appointmentMapper::mapEntityToDto).toList()).orElse(null);

    }
    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {


        validateFields(appointmentDTO);
        validateCNP(appointmentDTO.getPatientId());
        validatePhysicianId(appointmentDTO.getPhysicianId());
        Appointment appointment = appointmentMapper.mapDtoToEntity(appointmentDTO);
        Appointment savedAppointment = appointmentRepository.createAppointment(appointment);
        if (savedAppointment == null) {
            throw new ConflictException("Programare suprapusa");
        }
        return appointmentMapper.mapEntityToDto(savedAppointment);

    }

    @Override
    public void deleteAppointmentByPatientCNP(String cnp) {
        validateCNP(cnp);
        Optional<List<Appointment>> patientOptional = appointmentRepository.findByPatientId(cnp);
        if(patientOptional.isPresent()) appointmentRepository.deleteByPatientId(cnp);

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
    private LocalDateTime convertStringToLocalDateTime(String dateString) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid date format");
        }
    }
    private void validateFields(AppointmentDTO physicianDTO) {
        if (physicianDTO.getPhysicianId() == null)
            throw new InvalidDataException("Physician Id can not be empty");
        if (physicianDTO.getPatientId() == null)
            throw new InvalidDataException("Patient Id can not be empty");
        if (physicianDTO.getDate() == null)
            throw new InvalidDataException("Date can not be empty");
        try {
            AppointmentStatus status = physicianDTO.getStatus();
            if (status == null)
                throw new InvalidDataException("Status can not be empty");
        } catch (Exception e) {
            throw new InvalidDataException("Invalid status value");
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

    private void validatePhysicianId(Integer id) {
        if (id <= 0) {
            throw new InvalidDataException("Invalid Physician Id format: must be a positive number.");
        }
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
