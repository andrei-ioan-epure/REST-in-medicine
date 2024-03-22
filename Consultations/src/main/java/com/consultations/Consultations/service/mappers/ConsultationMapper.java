package com.consultations.Consultations.service.mappers;

import com.consultations.Consultations.dto.ConsultationDTO;
import com.consultations.Consultations.dto.InvestigationDTO;
import com.consultations.Consultations.model.Consultation;
import com.consultations.Consultations.model.Investigation;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    @Mappings({
            @Mapping(source = "_id", target = "id")
    })
    ConsultationDTO mapEntityToDto(Consultation entity);

    @Mappings({
            @Mapping(source = "id", target = "_id")
    })
    Consultation mapDtoToEntity(ConsultationDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "_id"),
    })
    Investigation mapInvestigationDTOToEntity(InvestigationDTO investigationDTO);

    @Mappings({
            @Mapping(source = "_id", target = "id"),
    })
    InvestigationDTO mapInvestigationEntityToDTO(Investigation investigation);

    List<InvestigationDTO> mapInvestigationListToDTOList(List<Investigation> investigationList);
    List<Investigation> mapInvestigationDTOListToEntityList(List<InvestigationDTO> investigationDTOList);

    default String mapObjectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }
    default ObjectId mapStringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }
}