package com.consultations.Consultations.model;

import com.consultations.Consultations.model.enums.DiagnosisEnum;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Document("consultation")
public class Consultation {
    @Id
    private ObjectId _id;
    private String id_pacient;
    private Integer id_doctor;
    private LocalDateTime data;
    private DiagnosisEnum diagnostic;
    private List<Investigation> investigatii;
}
