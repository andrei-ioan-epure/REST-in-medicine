package com.Patient.Patient.hateoas;

import com.Patient.Patient.controller.PatientController;
import com.Patient.Patient.dto.PatientDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PatientHateoas implements RepresentationModelAssembler<PatientDTO, EntityModel<PatientDTO>> {

    @Override
    public EntityModel<PatientDTO> toModel(PatientDTO patientDTO) {
        return EntityModel.of(patientDTO,
                linkTo(methodOn(PatientController.class).getAllPatients(1,10,null)).withSelfRel(),
              //  linkTo(methodOn(PatientController.class).getPatientByCNP(patientDTO.getCnp())).withRel("parent"),
                linkTo(methodOn(PatientController.class).updatePatientByCNP(patientDTO.getCnp(), patientDTO,null)).withRel("update"),
                linkTo(methodOn(PatientController.class).deletePatient(patientDTO.getCnp(),null)).withRel("delete")

        );
    }
}
