package com.consultations.Consultations.dto;

import com.consultations.Consultations.model.enums.DiagnosisEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ConsultationDTO {
    private String id;
    private String id_pacient;
    private Integer id_doctor;
    private LocalDateTime data;
    private DiagnosisEnum diagnostic;
    private List<InvestigationDTO> investigatii;
}
