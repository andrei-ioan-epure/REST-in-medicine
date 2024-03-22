package com.Physician.Physician.service.interfaces.mappers;

import com.Physician.Physician.model.Physician;
import com.Physician.Physician.dto.PhysicianDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface PhysicianMapper {

    @Mappings({
            @Mapping(source = "idDoctor", target = "id_doctor"),
            @Mapping(source = "id_user", target = "id_user"),
            @Mapping(source = "nume", target = "nume"),
            @Mapping(source = "prenume", target = "prenume"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "telefon", target = "telefon"),
            @Mapping(source = "specializare", target = "specializare")
    })
    PhysicianDTO mapEntityToDto(Physician entity);
    @Mappings({
            @Mapping(source = "id_doctor", target = "idDoctor"),
            @Mapping(source = "id_user", target = "id_user"),
            @Mapping(source = "nume", target = "nume"),
            @Mapping(source = "prenume", target = "prenume"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "telefon", target = "telefon"),
            @Mapping(source = "specializare", target = "specializare")
    })
    Physician mapDtoToEntity(PhysicianDTO dto);
}