package com.Appointment.Appointment.hateoas;

import com.Appointment.Appointment.controller.AppointmentController;
import com.Appointment.Appointment.dto.AppointmentDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AppointmentHateoas implements RepresentationModelAssembler<AppointmentDTO, EntityModel<AppointmentDTO>> {

    @Override
    public EntityModel<AppointmentDTO> toModel(AppointmentDTO appointmentDTO) {
        return EntityModel.of(appointmentDTO,
                WebMvcLinkBuilder.linkTo(methodOn(AppointmentController.class).getAllAppointments(1,10,null)).withSelfRel(),
                linkTo(methodOn(AppointmentController.class).getAppointmentsById(appointmentDTO.getPatientId(),null)).withRel("parent"),
                linkTo(methodOn(AppointmentController.class).deleteAppointment(appointmentDTO.getPatientId(),null)).withRel("delete")

        );
    }
}
