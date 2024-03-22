package com.Physician.Physician.hateoas;

import com.Physician.Physician.controller.PhysicianController;
import com.Physician.Physician.dto.PhysicianDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PhysicianHateoas implements RepresentationModelAssembler<PhysicianDTO, EntityModel<PhysicianDTO>> {

    @Override
    public EntityModel<PhysicianDTO> toModel(PhysicianDTO physicianDTO) {
        return EntityModel.of(physicianDTO,
                linkTo(methodOn(PhysicianController.class).getAllPhysicians(1, 10,null)).withSelfRel(),
                linkTo(methodOn(PhysicianController.class).getPhysicianById(physicianDTO.getId_doctor() + 1,null)).withRel("parent"),
                linkTo(methodOn(PhysicianController.class).updatePhysicianById(physicianDTO.getId_doctor(), physicianDTO,null)).withRel("update"),
                linkTo(methodOn(PhysicianController.class).deletePhysician(physicianDTO.getId_doctor(),null)).withRel("delete")
        );
    }
}
