package com.consultations.Consultations.repository.impl;

import com.consultations.Consultations.exception.NotFoundException;
import com.consultations.Consultations.repository.interfaces.ConsultationsMongoRepository;
import com.consultations.Consultations.repository.interfaces.ConsultationsRepository;
import com.consultations.Consultations.model.Consultation;
import com.consultations.Consultations.model.Investigation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ConsultationsRepositoryImpl implements ConsultationsRepository {

    @Autowired
    ConsultationsMongoRepository consultationsMongoRepository;

    @Override
    public Page<Consultation> findAll(Pageable pageable)
    {
        return consultationsMongoRepository.findAll( pageable);
    }
    @Override
    public Consultation insert(Consultation consultation)
    {
        return consultationsMongoRepository.insert(consultation);
    }

    @Override
    public Consultation findById(ObjectId id) {
        Optional<Consultation>consultation=consultationsMongoRepository.findById(id);
        return consultation.orElseThrow( ()->new NotFoundException("Consultation not found"));
    }

    @Override
    public void deleteById(ObjectId id) {
        this.consultationsMongoRepository.deleteById(id);
    }

    @Override
    public List<Consultation> findByIdDoctor(Integer id_doctor) {
        return  consultationsMongoRepository.findByIdDoctor(id_doctor);
    }
    @Override
    public List<Consultation> findByIdPatient(String id_pacient) {
        return  consultationsMongoRepository.findByIdPatient(id_pacient);
    }
    @Override
    public List<Consultation> findByAppointment(String id_pacient, Integer id_doctor, LocalDateTime date) {
        return  consultationsMongoRepository.findByAppointment(id_pacient,id_doctor,date);
    }

    @Override
    public void deleteByIdDoctor(Integer id_doctor) {
        this.consultationsMongoRepository.deleteByIdDoctor(id_doctor);
    }

    @Override
    public Consultation updateConsultation(ObjectId id , Consultation newConsultation) {
        Optional<Consultation> oldConsultation= consultationsMongoRepository.findById(id);
        if(oldConsultation.isPresent())
        {
            oldConsultation.get().setData(newConsultation.getData());
            oldConsultation.get().setDiagnostic(newConsultation.getDiagnostic());
            oldConsultation.get().setId_doctor(newConsultation.getId_doctor());
            oldConsultation.get().setId_pacient(newConsultation.getId_pacient());
            oldConsultation.get().setInvestigatii(newConsultation.getInvestigatii());
            return consultationsMongoRepository.save(oldConsultation.get());
        }
        return consultationsMongoRepository.insert(newConsultation);
    }

    @Override
    public List<Investigation> findInvestigationByConsultationId(ObjectId consultationId) {
        Optional<Consultation> consultation= consultationsMongoRepository.findById(consultationId);
        return consultation.map(Consultation::getInvestigatii).orElseThrow( ()->new NotFoundException("Investigation not found"));
    }

    @Override
    public Investigation findInvestigationByConsultationIdAndInvestigationId(ObjectId consultationId, ObjectId investigationId) {
        Optional<Consultation> consultation= consultationsMongoRepository.findById(consultationId);
        if(consultation.isPresent()) {
            Investigation investigation = consultation.get().getInvestigatii().stream()
                    .filter(it -> it.get_id().equals(investigationId))
                    .findFirst()
                    .orElse(null);
            if(investigation ==null)
                throw new NotFoundException("Investigation not found");
            return investigation;
        }
       throw new NotFoundException("Consultation not found");
    }

    @Override
    public Consultation addInvestigationToConsultation(ObjectId consultationId, Investigation investigation) {
        Consultation consultation = consultationsMongoRepository.findById(consultationId)
                .orElseThrow(() -> new NotFoundException("Consultation not found"));

        consultation.getInvestigatii().add(investigation);

        return consultationsMongoRepository.save(consultation);
    }

    @Override
    public Consultation editInvestigationToConsultation(ObjectId investigationId, ObjectId consultationId, Investigation investigation) {
        Consultation consultation = consultationsMongoRepository.findById(consultationId)
                .orElseThrow(() -> new NotFoundException("Consultation not found"));

        for (Investigation item:consultation.getInvestigatii()) {
            if(item.get_id().equals(investigationId))
            {
                item.setDenumire(investigation.getDenumire());
                item.setDurateDeProcesare(investigation.getDurateDeProcesare());
                item.setRezultat(investigation.getRezultat());
                break;
            }

        }
        return consultationsMongoRepository.save(consultation);
    }

    @Override
    public void deleteInvestigationFromConsultation(ObjectId investigationId, ObjectId consultationId) {
        Consultation consultation = consultationsMongoRepository.findById(consultationId)
                .orElseThrow(() -> new NotFoundException("Consultation not found"));

        List<Investigation> investigations = consultation.getInvestigatii();
        Investigation investigationToRemove = investigations.stream()
                .filter(inv -> inv.get_id().equals(investigationId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Investigation not found"));

        investigations.remove(investigationToRemove);

        consultationsMongoRepository.save(consultation);
    }



}
