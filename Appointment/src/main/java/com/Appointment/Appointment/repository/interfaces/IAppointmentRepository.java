package com.Appointment.Appointment.repository.interfaces;

import com.Appointment.Appointment.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAppointmentRepository {

     List<Appointment> findAll();
     Appointment createAppointment(Appointment appointment);
     void deleteByPatientId(String id) ;
     Optional<List<Appointment>> findByPatientId(String cnp);
     Page<Appointment> getAppointmentsPage(Pageable pageable);

}
