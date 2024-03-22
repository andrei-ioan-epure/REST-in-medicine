package com.consultations.Consultations.repository.interfaces;

import com.consultations.Consultations.model.Consultation;
import com.consultations.Consultations.model.Investigation;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationsRepository {

    Page<Consultation> findAll(Pageable pageable);
    Consultation insert(Consultation consultation);
    Consultation findById(ObjectId id);
    void deleteById(ObjectId id);
    List<Consultation> findByIdDoctor(Integer id_doctor);
    List<Consultation> findByIdPatient(String id_pacient);
    List<Consultation> findByAppointment(String id_pacient, Integer id_doctor, LocalDateTime date);
    void deleteByIdDoctor(Integer id_doctor);
    Consultation updateConsultation(ObjectId id , Consultation newConsultation);
    List<Investigation> findInvestigationByConsultationId(ObjectId consultationId);
    Consultation addInvestigationToConsultation(ObjectId consultationId, Investigation investigation);
    Consultation editInvestigationToConsultation(ObjectId investigationId,ObjectId consultationId, Investigation investigation);
    void deleteInvestigationFromConsultation(ObjectId investigationId,ObjectId consultationId);

    Investigation findInvestigationByConsultationIdAndInvestigationId(ObjectId consultationId, ObjectId investigationId);
}
