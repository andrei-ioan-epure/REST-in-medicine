package com.consultations.Consultations.controller;

import com.consultations.Consultations.dto.ConsultationDTO;
import com.consultations.Consultations.dto.InvestigationDTO;
import com.consultations.Consultations.service.interfaces.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/medical_office/consultations",headers = "Authorization")
public class ConsultationsController {

    private final ConsultationService consultationService;

    @Autowired
    public ConsultationsController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "items_per_page", required = false, defaultValue = "10") Integer itemsPerPage,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"physician");

        List<ConsultationDTO> consultations=this.consultationService.findAll(page, itemsPerPage);
        return new ResponseEntity<>(consultations,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ConsultationDTO consultationDTO,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {

        this.consultationService.validateHeader(authorizationHeader,"physician");
        ConsultationDTO consultations = this.consultationService.insert(consultationDTO);
        return new ResponseEntity<>(consultations, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {

        this.consultationService.validateHeader(authorizationHeader,"physician,patient");

        ConsultationDTO consultation=this.consultationService.findById(id);
        return new ResponseEntity<>(consultation,HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {

        this.consultationService.validateHeader(authorizationHeader,"physician");

        this.consultationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody ConsultationDTO newConsultation,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {

        this.consultationService.validateHeader(authorizationHeader,"physician");

        if (consultationService.findById(id) != null) {
            consultationService.updateConsultation(id, newConsultation);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ConsultationDTO consultation = this.consultationService.insert(newConsultation);
        return new ResponseEntity<>(consultation, HttpStatus.CREATED);

    }

    @GetMapping("/physicians/{id_doctor}")
    public ResponseEntity<?> findByIdDoctor(@PathVariable Integer id_doctor ,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"patient,physician");

        List<ConsultationDTO> consultation=this.consultationService.findByIdDoctor(id_doctor);
            return new ResponseEntity<>(consultation,HttpStatus.OK);

    }
    @GetMapping("/patients/{id_pacient}")
    public ResponseEntity<?> findByIdPatient(@PathVariable String id_pacient,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"patient,physician");

        List<ConsultationDTO> consultation=this.consultationService.findByIdPatient(id_pacient);
        return new ResponseEntity<>(consultation,HttpStatus.OK);

    }
    @GetMapping("/appointments")
    public ResponseEntity<?> findByAppointment(@RequestParam String id_pacient,@RequestParam Integer id_doctor,@RequestParam String date ,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"patient,physician");

        List<ConsultationDTO> consultation=this.consultationService.findByAppointment(id_pacient,id_doctor,date);
        return new ResponseEntity<>(consultation,HttpStatus.OK);

    }

    @DeleteMapping("/physicians/{id_doctor}")
    public ResponseEntity<?> deleteByIdDoctor(@PathVariable Integer id_doctor,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"physician");

        this.consultationService.deleteByIdDoctor(id_doctor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/{consultationId}/investigations")
    public ResponseEntity< Object> findInvestigationByConsultationId(@PathVariable String consultationId ,
                                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"patient,physician");


        List<InvestigationDTO> investigations = this.consultationService.findInvestigationByConsultationId(consultationId);
        return new ResponseEntity<>(investigations, HttpStatus.OK);

    }

    @GetMapping("/{consultationId}/investigations/{investigationId}")
    public ResponseEntity< Object> findInvestigationByConsultationIdAndInvestigationId(@PathVariable String consultationId,@PathVariable String investigationId,
                                                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"patient,physician");

        InvestigationDTO investigations = this.consultationService.findInvestigationByConsultationIdAndInvestigationId(consultationId,investigationId);
        return new ResponseEntity<>(investigations, HttpStatus.OK);

    }
    @PutMapping ("/{consultationId}/investigations")
    public ResponseEntity< Object> addInvestigationToConsultation(@PathVariable String consultationId,@RequestBody InvestigationDTO investigationDTO,
                                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"physician");

        this.consultationService.addInvestigationToConsultation(consultationId, investigationDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping ("/{consultationId}/investigations/{id}")
    public ResponseEntity<Object> editInvestigationToConsultation(@PathVariable String id,@PathVariable String consultationId,@RequestBody InvestigationDTO investigationDTO ,
                                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"physician");

        if (this.consultationService.findInvestigationByConsultationIdAndInvestigationId(consultationId,id)!=null) {
            this.consultationService.editInvestigationToConsultation(id, consultationId, investigationDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ConsultationDTO newConsultation = this.consultationService.addInvestigationToConsultation(consultationId, investigationDTO);
        return new ResponseEntity<>(newConsultation, HttpStatus.CREATED);
    }

    @DeleteMapping ("/{consultationId}/investigations/{id}")
    public ResponseEntity<Object> deleteInvestigationFromConsultation(@PathVariable String id, @PathVariable String consultationId,
                                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.consultationService.validateHeader(authorizationHeader,"physician");

        this.consultationService.deleteInvestigationFromConsultation(id, consultationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
