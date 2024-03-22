package com.Physician.Physician.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "physicians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Physician {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id_doctor")
    private Integer idDoctor;

    @Column(name = "id_user")
    private Integer id_user;

    @Column(name="nume",length = 50)
    private String nume;

    @Column(name="prenume",length = 50)
    private String prenume;

    @Email
    @Column(name="email",length = 70)
    private String email;

    @Column(name = "telefon", columnDefinition = "CHAR(10)")
    private String telefon;

    @Column(name="specializare")
    private String specializare;

    @ManyToMany
    @JoinTable(
            name = "appointments",
            joinColumns = @JoinColumn(name = "physician_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patients;

}
