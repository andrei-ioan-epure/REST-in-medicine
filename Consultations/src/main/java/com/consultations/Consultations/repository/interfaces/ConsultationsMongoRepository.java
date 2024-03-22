package com.consultations.Consultations.repository.interfaces;

import com.consultations.Consultations.model.Consultation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationsMongoRepository extends MongoRepository<Consultation, ObjectId> {
    @Query(value="{ 'id_doctor' : ?0 }")
    List<Consultation> findByIdDoctor(Integer id_doctor);
    @Query(value="{ 'id_doctor' : ?0 }", delete = true)
    void deleteByIdDoctor(Integer id_doctor);
    @Query(value="{ 'id_pacient' : ?0 }")
    List<Consultation> findByIdPatient(String id_patient);
    @Query(value="{ $and: [{'id_pacient' : ?0}, {'id_doctor' : ?1}, {'data' : ?2}] }")
    List<Consultation> findByAppointment(String id_pacient, Integer id_doctor, LocalDateTime date);

}
