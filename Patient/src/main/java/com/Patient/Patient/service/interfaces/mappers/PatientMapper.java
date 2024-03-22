package com.Patient.Patient.service.interfaces.mappers;

import com.Patient.Patient.model.Patient;
import com.Patient.Patient.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "CNP", target = "cnp")
    @Mapping(source = "id_user", target = "userId")
    @Mapping(source = "nume", target = "nume")
    @Mapping(source = "prenume", target = "prenume")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telefon", target = "telefon")
    @Mapping(source = "data_nasterii", target = "dataNasterii")
    @Mapping(source = "is_active", target = "is_active")
    PatientDTO mapEntityToDto(Patient entity);

    @Mapping(source = "cnp", target = "CNP")
    @Mapping(source = "userId", target = "id_user")
    @Mapping(source = "nume", target = "nume")
    @Mapping(source = "prenume", target = "prenume")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telefon", target = "telefon")
    @Mapping(source = "dataNasterii", target = "data_nasterii")
    @Mapping(source = "is_active", target = "is_active")
    Patient mapDtoToEntity(PatientDTO dto);
}