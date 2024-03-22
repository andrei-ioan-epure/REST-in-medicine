package com.Physician.Physician.repository.interfaces;

import com.Physician.Physician.model.Physician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPhysicianJPARepository extends JpaRepository<Physician,Integer> {
    Optional<Physician> findByIdDoctor(Integer idDoctor);
    Page<Physician> findAll(Pageable pageable);
    @Query("SELECT p FROM Physician  p WHERE p.id_user = :id_user")
    Optional<Physician> findByUserId(@Param("id_user") Integer id_user);

    @Query("SELECT COUNT(p) > 0 FROM Physician p WHERE (p.idDoctor <> :id_doctor) AND (p.email = :email)")
    Boolean emailIsAlreadyRegistered(@Param("email") String email, @Param("id_doctor") Integer id_doctor);

    @Query("SELECT COUNT(p) > 0 FROM Physician p WHERE (p.idDoctor <> :id_doctor) AND (p.telefon = :telefon)")
    Boolean phoneNumberIsAlreadyRegistered(@Param("telefon") String telefon, @Param("id_doctor") Integer id_doctor);
}