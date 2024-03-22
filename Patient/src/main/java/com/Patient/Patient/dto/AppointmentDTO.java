package com.Patient.Patient.dto;

import com.Patient.Patient.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private String patientId;
    private Integer physicianId;
    private LocalDateTime date;
    private AppointmentStatus status;
}
