package com.consultations.Consultations.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InvestigationDTO {
    private String id;
    private String denumire;
    private Integer durateDeProcesare;
    private String rezultat;
}