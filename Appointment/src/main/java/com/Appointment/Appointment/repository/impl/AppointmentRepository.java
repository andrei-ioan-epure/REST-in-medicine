package com.Appointment.Appointment.repository.impl;


import com.Appointment.Appointment.model.Appointment;
import com.Appointment.Appointment.repository.interfaces.IAppointmentJPARepository;
import com.Appointment.Appointment.repository.interfaces.IAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepository  implements IAppointmentRepository {


    private final IAppointmentJPARepository appointmentRepository;

    @Autowired
    public AppointmentRepository(IAppointmentJPARepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<List<Appointment>> findByPatientId(String cnp) {
        return appointmentRepository.findByPatientId(cnp);
    }

    @Override
    public Page<Appointment> getAppointmentsPage(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        if(!appointmentRepository.existsByPhysicianIdAndDate(appointment.getPhysicianId(),appointment.getDate())
        && !appointmentRepository.alreadyExistsAppointmentOnTheDay(appointment.getPhysicianId(),appointment.getPatientId(),appointment.getDate()))
             return appointmentRepository.save(appointment);
        return null;
    }


    @Override
    public void deleteByPatientId(String id) {
        Optional<List<Appointment>> appointments = appointmentRepository.findByPatientId(id);

        appointments.ifPresent(appointmentRepository::deleteAll);
    }
}
