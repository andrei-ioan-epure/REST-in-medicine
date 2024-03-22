package com.Physician.Physician.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @Column(name = "CNP", columnDefinition = "CHAR(13)")
    private String CNP;

    @Column(name = "id_user")
    private Integer id_user;

    @Column(name = "nume",length = 50)
    private String nume;

    @Column(name = "prenume",length = 50)
    private String prenume;

    @Email
    @Column(name = "email",length = 70)
    private String email;

    @Column(name = "telefon", columnDefinition = "CHAR(10)")
    private String telefon;

    @Column(name = "data_nasterii")
    private LocalDateTime data_nasterii;

    @Column(name = "is_active")
    private Boolean is_active;


    @ManyToMany(mappedBy = "patients")
    private Set<Physician> physicians;
}
