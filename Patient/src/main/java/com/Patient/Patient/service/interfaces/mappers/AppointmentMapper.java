package com.Patient.Patient.service.interfaces.mappers;

import com.Patient.Patient.model.Appointment;
import com.Patient.Patient.dto.AppointmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "patientId", target = "patientId")
    @Mapping(source = "physicianId", target = "physicianId")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "status", target = "status")
    AppointmentDTO mapEntityToDto(Appointment entity);

    @Mapping(source = "patientId", target = "patientId")
    @Mapping(source = "physicianId", target = "physicianId")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "status", target = "status")
    Appointment mapDtoToEntity(AppointmentDTO dto);
}