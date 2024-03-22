package com.Appointment.Appointment.model;

import com.Appointment.Appointment.model.enums.AppointmentStatus;
import com.Appointment.Appointment.model.ids.AppointmentId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name="appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AppointmentId.class)
public class Appointment {
    @Id
    @Column(name = "patient_id")
    private String patientId;

    @Id
    @Column(name = "physician_id")
    private Integer physicianId;

    @Id
    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;
}
