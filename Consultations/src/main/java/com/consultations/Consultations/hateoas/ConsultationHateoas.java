package com.consultations.Consultations.hateoas;

import com.consultations.Consultations.controller.ConsultationsController;
import com.consultations.Consultations.dto.ConsultationDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ConsultationHateoas implements RepresentationModelAssembler<ConsultationDTO, EntityModel<ConsultationDTO>> {

    @Override
    public EntityModel<ConsultationDTO> toModel(ConsultationDTO consultationDTO) {
        return EntityModel.of(consultationDTO,
                linkTo(methodOn(ConsultationsController.class).findAll(1, 10,null)).withSelfRel(),
               // linkTo(methodOn(ConsultationsController.class).findById(consultationDTO.getId())).withRel("parent"),
                linkTo(methodOn(ConsultationsController.class).updateById(consultationDTO.getId(), consultationDTO,null)).withRel("update"),
                linkTo(methodOn(ConsultationsController.class).deleteById(consultationDTO.getId(),null)).withRel("delete")
        );
    }
}
