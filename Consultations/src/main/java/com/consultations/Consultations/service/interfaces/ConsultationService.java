package com.consultations.Consultations.service.interfaces;

import com.consultations.Consultations.dto.ConsultationDTO;
import com.consultations.Consultations.dto.InvestigationDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationService {
    List<ConsultationDTO> findAll(Integer page, Integer itemsPerPage);
    ConsultationDTO findById(String id);

    ConsultationDTO insert(ConsultationDTO consultationDTO);

    void deleteById(String id);

    ConsultationDTO updateConsultation(String id, ConsultationDTO newConsultationDTO);

    List<ConsultationDTO> findByIdDoctor(Integer id_doctor);
    List<ConsultationDTO> findByIdPatient(String id_pacient);

    List<ConsultationDTO> findByAppointment(String id_pacient, Integer id_doctor, String date);

    void deleteByIdDoctor(Integer id_doctor);


    List<InvestigationDTO> findInvestigationByConsultationId(String id_doctor);
    ConsultationDTO addInvestigationToConsultation(String consultationId,InvestigationDTO investigationDTO);
    void deleteInvestigationFromConsultation(String investigationId, String consultationId);

    ConsultationDTO editInvestigationToConsultation(String investigationId,String consultationId, InvestigationDTO investigationDTO);

    void validateHeader(String authorizationHeader,String requestedRole);

    InvestigationDTO findInvestigationByConsultationIdAndInvestigationId(String consultationId, String investigationId);
    // void validateHeader(String authorizationHeader,String method,String id);
}
