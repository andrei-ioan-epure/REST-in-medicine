package com.consultations.Consultations.service.impl;

import com.consultations.Consultations.dto.ConsultationDTO;
import com.consultations.Consultations.dto.InvestigationDTO;
import com.consultations.Consultations.exception.ForbiddenException;
import com.consultations.Consultations.exception.InvalidDataException;
import com.consultations.Consultations.exception.UnauthorizedException;
import com.consultations.Consultations.model.Consultation;
import com.consultations.Consultations.repository.interfaces.ConsultationsRepository;
import com.consultations.Consultations.service.interfaces.ConsultationService;
import com.consultations.Consultations.service.mappers.ConsultationMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationsRepository consultationsRepository;
    private final ConsultationMapper consultationMapper;


    @Autowired
    public ConsultationServiceImpl(ConsultationsRepository consultationsRepository, ConsultationMapper consultationMapper) {
        this.consultationsRepository = consultationsRepository;
        this.consultationMapper = consultationMapper;
    }

    @Override
    public List<ConsultationDTO> findAll(Integer page, Integer itemsPerPage) {
        validatePagination(page,itemsPerPage);
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        Page<Consultation> consultations = consultationsRepository.findAll(pageable);
        return consultations.getContent().stream()
                .map(consultationMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public ConsultationDTO findById(String id) {
        validateId(id);
        Consultation consultation = consultationsRepository.findById(new ObjectId(id));
        return consultationMapper.mapEntityToDto(consultation);
    }

    @Override
    public ConsultationDTO insert(ConsultationDTO consultationDTO) {
        validateFields(consultationDTO);
        consultationDTO.setId(new ObjectId().toHexString());

        if (consultationDTO.getInvestigatii() != null) {
            consultationDTO.getInvestigatii().forEach(investigationDTO -> investigationDTO.setId(new ObjectId().toHexString()));
        }
        Consultation consultation = consultationMapper.mapDtoToEntity(consultationDTO);
        Consultation savedConsultation = consultationsRepository.insert(consultation);
        return consultationMapper.mapEntityToDto(savedConsultation);
    }

    @Override
    public void deleteById(String id) {
        validateId(id);
        consultationsRepository.deleteById(new ObjectId(id));
    }

    @Override
    public ConsultationDTO updateConsultation(String id, ConsultationDTO newConsultationDTO) {
        validateId(id);
        validateCNP(newConsultationDTO.getId_pacient());
        validatePersonId(newConsultationDTO.getId_doctor(),"Physician");
        validateFields(newConsultationDTO);
        newConsultationDTO.setId(new ObjectId(id).toHexString());
        Consultation newConsultation = consultationMapper.mapDtoToEntity(newConsultationDTO);
        Consultation updatedConsultation = consultationsRepository.updateConsultation(new ObjectId(id), newConsultation);
        return consultationMapper.mapEntityToDto(updatedConsultation);
    }

    @Override
    public List<ConsultationDTO> findByIdDoctor(Integer id_doctor) {
        validatePersonId(id_doctor,"Physician");
        return consultationsRepository.findByIdDoctor(id_doctor)
                .stream()
                .map(consultationMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<ConsultationDTO> findByIdPatient(String id_pacient) {
        validateCNP(id_pacient);
        return consultationsRepository.findByIdPatient(id_pacient)
                .stream()
                .map(consultationMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<ConsultationDTO> findByAppointment(String id_patient, Integer id_doctor,String date) {
        validateCNP(id_patient);
        validatePersonId(id_doctor,"Physician");

        LocalDateTime dateTime=convertStringToLocalDateTime(date);

        return consultationsRepository.findByAppointment(id_patient, id_doctor,dateTime)
                .stream()
                .map(consultationMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByIdDoctor(Integer id_doctor) {
        validatePersonId(id_doctor,"Physician");
        consultationsRepository.deleteByIdDoctor(id_doctor);
    }

    @Override
    public List<InvestigationDTO> findInvestigationByConsultationId(String consultationId) {
        validateId(consultationId);
        return consultationMapper.mapInvestigationListToDTOList(consultationsRepository.findInvestigationByConsultationId(new ObjectId(consultationId)));
    }

    @Override
    public InvestigationDTO findInvestigationByConsultationIdAndInvestigationId(String consultationId, String investigationId) {
        validateId(consultationId);
        validateId(investigationId);

        return consultationMapper.mapInvestigationEntityToDTO(consultationsRepository.findInvestigationByConsultationIdAndInvestigationId(new ObjectId(consultationId),new ObjectId(investigationId)));
    }

    @Override
    public ConsultationDTO addInvestigationToConsultation(String consultationId, InvestigationDTO investigationDTO) {
        validateId(consultationId);
        validateInvestigation(investigationDTO);
        investigationDTO.setId(new ObjectId().toHexString());
        return consultationMapper.mapEntityToDto(consultationsRepository.addInvestigationToConsultation(new ObjectId(consultationId),
                consultationMapper.mapInvestigationDTOToEntity(investigationDTO)));
    }

    @Override
    public void deleteInvestigationFromConsultation(String investigationId, String consultationId) {
        validateId(consultationId);
        validateId(investigationId);
        consultationsRepository.deleteInvestigationFromConsultation(new ObjectId(investigationId),new ObjectId(consultationId));
    }

    @Override
    public ConsultationDTO editInvestigationToConsultation(String investigationId, String consultationId, InvestigationDTO investigationDTO) {

        validateId(consultationId);
        validateId(investigationId);
        validateInvestigation(investigationDTO);
        investigationDTO.setId(new ObjectId(investigationId).toHexString());
        return consultationMapper.mapEntityToDto(consultationsRepository.editInvestigationToConsultation(new ObjectId(investigationId),new ObjectId(consultationId),
                consultationMapper.mapInvestigationDTOToEntity(investigationDTO)));
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
            String sub = (String) claims.getClaim("sub");
            if(!listRoles.contains(role)) throw new ForbiddenException("Access forbidden for "+role);
        } catch (java.text.ParseException e) {
            throw new InvalidDataException("Invalid token");
        }
    }


//    @Override
//    public void validateHeader(String authorizationHeader,String method,String id) {
//
//
//        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
//            throw new UnauthorizedException("Authorization header is missing");
//        }
//        try {
//            String token = authorizationHeader.replace("Bearer ", "");
//            JWSObject jwsObject;
//            JWTClaimsSet claims;
//            jwsObject = JWSObject.parse(token);
//            claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
//            String role = (String) claims.getClaim("role");
//            String sub = (String) claims.getClaim("sub");
//
//            switch (method)
//            {
//                case("insert"), ("update"), ("delete") ->{
//                    if ("patient".equals(role)) {
//                        throw new ForbiddenException("Not allowed");
//                    }
//                }
//                case("findById")->{
//
//                        if ((!("patient".equals(role)) || !(sub.equals(id))) && !("physician".equals(role)))  {
//                            throw new ForbiddenException("Not allowed");
//                        }
//
//                }
//
//            }
//
//        } catch (java.text.ParseException e) {
//            throw new InvalidDataException("Invalid token");
//        }
//
//
//    }

    private LocalDateTime convertStringToLocalDateTime(String dateString) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid date format");
        }
    }

    private void validateId(String id)
    {
        if (id == null || !id.matches( "^[0-9a-fA-F]{24}$")) {
            throw new InvalidDataException("Invalid id format");
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


    private void validatePersonId(Integer id,String role) {
        if (id <= 0) {
            throw new InvalidDataException("Invalid " + role + " Id format: must be a positive number");
        }
    }


    private  void validateFields(ConsultationDTO consultation) {

        validateCNP(consultation.getId_pacient());
        if (consultation.getId_pacient() == null  ) {
            throw new InvalidDataException("Invalid patient id");
        }

        if (consultation.getId_doctor() == null || consultation.getId_doctor() <= 0) {
            throw new InvalidDataException("Invalid physician is");
        }

        LocalDateTime now = LocalDateTime.now();
        if (consultation.getData() == null || consultation.getData().isBefore(now)) {
            throw new InvalidDataException("Consultation date must be in the future");
        }

        if (consultation.getDiagnostic() == null) {
            throw new InvalidDataException("Diagnostic cannot be null");
        }
        for (InvestigationDTO investigation : consultation.getInvestigatii()) {
            validateInvestigation(investigation);
        }
    }
    private void  validateInvestigation(InvestigationDTO investigation) {


        if (investigation.getDenumire().isEmpty()) {
            throw new InvalidDataException("Investigation name cannot be empty");
        }

        if (investigation.getDurateDeProcesare() <= 0) {
            throw new InvalidDataException("Invalid investigation processing duration");
        }

        if (investigation.getRezultat().isEmpty()) {
            throw new InvalidDataException("Investigation result cannot be empty");
        }
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
