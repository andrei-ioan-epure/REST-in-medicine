package com.Appointment.Appointment.repository.interfaces;

import com.Appointment.Appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAppointmentJPARepository extends JpaRepository<Appointment,String> {
    Optional<List<Appointment>>  findByPatientId(String patientId) ;
    void deleteByPatientId(String patientId) ;
    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.physicianId = :physicianId " +
            "AND YEAR(a.date) = YEAR(:date) " +
            "AND MONTH(a.date) = MONTH(:date) " +
            "AND DAY(a.date) = DAY(:date) " +
            "AND ABS(TIMESTAMPDIFF(MINUTE, a.date, :date)) < 15")
    boolean existsByPhysicianIdAndDate(@Param("physicianId") Integer physicianId, @Param("date") LocalDateTime date);
    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.physicianId = :physicianId " +
            "AND a.patientId = :patientId " +
            "AND YEAR(a.date) = YEAR(:date) " +
            "AND MONTH(a.date) = MONTH(:date) " +
            "AND DAY(a.date) = DAY(:date) " )
    boolean alreadyExistsAppointmentOnTheDay(@Param("physicianId") Integer physicianId, @Param("patientId") String patientId, @Param("date") LocalDateTime date);

}

