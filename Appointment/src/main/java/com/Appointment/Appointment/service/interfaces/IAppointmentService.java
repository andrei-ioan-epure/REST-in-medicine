package com.Appointment.Appointment.service.interfaces;

import com.Appointment.Appointment.dto.AppointmentDTO;

import java.util.List;

public interface IAppointmentService {
    List<AppointmentDTO> getAppointments(Integer page, Integer itemsPerPage);
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    void deleteAppointmentByPatientCNP(String cnp);
    List<AppointmentDTO> getAppointmentsByPatientId(String cnp);
    void validateHeader(String authorizationHeader,String requestedRole);
}
