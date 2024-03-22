package com.Patient.Patient.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private String cnp;
    private Integer userId;
    private String nume;
    private String prenume;
    private String email;
    private String telefon;
    private LocalDateTime dataNasterii;
    private Boolean is_active;
}