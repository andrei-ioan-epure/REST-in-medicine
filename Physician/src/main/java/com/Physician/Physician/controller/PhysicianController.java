package com.Physician.Physician.controller;


import com.Physician.Physician.dto.PatientDTO;
import com.Physician.Physician.dto.PhysicianDTO;
import com.Physician.Physician.hateoas.PhysicianHateoas;
import com.Physician.Physician.service.interfaces.IPhysicianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/api/medical_office/physicians", headers = "Authorization")
public class PhysicianController {

    private final IPhysicianService physicianService;

    @Autowired
    PhysicianController(IPhysicianService physicianService)
    {
        this.physicianService=physicianService;
    }
//    @GetMapping
//    public ResponseEntity<List<PhysicianDTO>> getAllPhysicians(){
//        List<PhysicianDTO> physicians=physicianService.getAllPhysicians();
//        return new ResponseEntity<>(physicians, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<?> getAllPhysicians(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "items_per_page", required = false, defaultValue = "10") Integer itemsPerPage, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        physicianService.validateHeader(authorizationHeader,"admin,patient,physician");
        List<PhysicianDTO> physicians = physicianService.getAllPhysiciansPage(page, itemsPerPage);
        return new ResponseEntity<>(physicians, HttpStatus.OK);

    }

    @GetMapping("/{physicianId}")
    public ResponseEntity<?> getPhysicianById(@PathVariable Integer physicianId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        physicianService.validateHeader(authorizationHeader,"admin,patient,physician");
            PhysicianDTO physician = physicianService.getPhysicianById(physicianId);

            if (physician != null) {
                return new ResponseEntity<>(new PhysicianHateoas().toModel(physician), HttpStatus.OK);
            } else {
                Map<String, Object> links = new HashMap<>();
                links.put("message", "No doctor found with ID: " + physicianId);
                ArrayList<Link> arrayLinks = new ArrayList<>();
                Link parentLink = linkTo(methodOn(PhysicianController.class).getAllPhysicians(1, 1,null)).withRel("parent");
                arrayLinks.add(parentLink);
                links.put("_links", new ArrayList<>(arrayLinks));
                return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
            }
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getPhysicianByUserId(@PathVariable Integer user_id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        physicianService.validateHeader(authorizationHeader,"admin,patient,physician");
        PhysicianDTO physician = physicianService.getPhysicianByUserId(user_id);

        if(physician!=null)
        {
            return new ResponseEntity<>(new PhysicianHateoas().toModel(physician),HttpStatus.OK);
        }
        else{
            Map<String, Object> links = new HashMap<>();
            links.put("message", "No doctor found with ID: " + user_id);
            ArrayList<Link> arrayLinks = new ArrayList<>();
            Link parentLink = linkTo(methodOn(PhysicianController.class).getAllPhysicians(1, 1,null)).withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<>(arrayLinks));
            return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<?> createPhysician(@RequestBody @Valid PhysicianDTO physician, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

            physicianService.validateHeader(authorizationHeader,"admin");
            PhysicianDTO newPhysician = physicianService.createPhysician(physician);
            return new ResponseEntity<>(newPhysician, HttpStatus.CREATED);
    }

    @PutMapping("/{physicianId}")
    public ResponseEntity<?> updatePhysicianById(@PathVariable Integer physicianId, @RequestBody PhysicianDTO physicianDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        physicianService.validateHeader(authorizationHeader,"admin,physician");

            if(physicianService.getPhysicianById(physicianId)!=null) {
                physicianService.updatePhysician(physicianId, physicianDTO);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                PhysicianDTO updatedPhysician = physicianService.createPhysician(physicianDTO);
                return new ResponseEntity<>(updatedPhysician, HttpStatus.CREATED);
            }
    }

    @DeleteMapping("/{physicianId}")
    public ResponseEntity<?> deletePhysician(@PathVariable Integer physicianId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        physicianService.validateHeader(authorizationHeader,"admin");
        physicianService.deletePhysicianById(physicianId);
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{physicianId}/patients")
    public ResponseEntity<?> getPatientsForPhysician(@PathVariable Integer physicianId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        physicianService.validateHeader(authorizationHeader,"physician");
        List<PatientDTO> patients = physicianService.getPatientsForPhysician(physicianId);
        return (patients!=null)?  new ResponseEntity<>(patients, HttpStatus.OK)
            :new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }
    @GetMapping("/names")
    public ResponseEntity<List<PhysicianDTO>> searchPhysiciansByName(@RequestParam(name = "name") String name, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        physicianService.validateHeader(authorizationHeader,"admin,patient,physician");
        List<PhysicianDTO> physicians = physicianService.searchPhysiciansByName(name);
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<PhysicianDTO>> searchPhysiciansBySpecialization(@RequestParam(name = "specialization") String specialization, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        physicianService.validateHeader(authorizationHeader,"admin,patient,physician");
        List<PhysicianDTO> physicians = physicianService.searchPhysiciansBySpecialization(specialization);
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

}
