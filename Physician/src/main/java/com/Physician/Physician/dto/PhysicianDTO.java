package com.Physician.Physician.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhysicianDTO {
    private Integer id_doctor;
    private Integer id_user;
    private String nume;
    private String prenume;
    private String email;
    private String telefon;
    private String specializare;
}