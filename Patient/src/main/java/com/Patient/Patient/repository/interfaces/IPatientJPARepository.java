package com.Patient.Patient.repository.interfaces;

import com.Patient.Patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPatientJPARepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByCNP(String cnp);
    @Query("SELECT p FROM Patient  p WHERE p.id_user = :id_user")
    Optional<Patient> findByUserId(@Param("id_user") Integer id_user);
    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE (p.id_user <> :id_user) AND (p.email = :email)")
    Boolean emailIsAlreadyRegistered(@Param("email") String email, @Param("id_user") Integer id_user);
    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE (p.id_user <> :id_user) AND (p.telefon = :telefon)")
    Boolean phoneNumberIsAlreadyRegistered(@Param("telefon") String telefon, @Param("id_user") Integer id_user);

}
