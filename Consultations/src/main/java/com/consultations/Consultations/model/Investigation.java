package com.consultations.Consultations.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Document("investigation")
public class Investigation {
    @Id
    private ObjectId _id;
    private String denumire;
    private Integer durateDeProcesare;
    private String rezultat;
}
