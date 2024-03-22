package com.Patient.Patient.repository.ids;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentId implements Serializable {
    private String patientId;
    private Integer physicianId;
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentId that = (AppointmentId) o;
        return Objects.equals(patientId, that.patientId) &&
                Objects.equals(physicianId, that.physicianId) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, physicianId, date);
    }
}