package com.Patient.Patient.service.interfaces.mappers;

import com.Patient.Patient.model.Physician;
import com.Patient.Patient.dto.PhysicianDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface PhysicianMapper {

    @Mappings({
            @Mapping(source = "id_doctor", target = "id_doctor"),
            @Mapping(source = "id_user", target = "id_user"),
            @Mapping(source = "nume", target = "nume"),
            @Mapping(source = "prenume", target = "prenume"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "telefon", target = "telefon"),
            @Mapping(source = "specializare", target = "specializare")
    })
    PhysicianDTO mapEntityToDto(Physician entity);
    @Mappings({
            @Mapping(source = "id_doctor", target = "id_doctor"),
            @Mapping(source = "id_user", target = "id_user"),
            @Mapping(source = "nume", target = "nume"),
            @Mapping(source = "prenume", target = "prenume"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "telefon", target = "telefon"),
            @Mapping(source = "specializare", target = "specializare")
    })
    PhysicianDTO mapDtoToEntity(Physician dto);
}