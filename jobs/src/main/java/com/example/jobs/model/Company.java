package com.example.jobs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;
    private String logoUrl;

    @Lob
    private String about;

    @Lob
    private String mission;

    @Lob
    private String vision;

    @Lob
    private String whyWork;
}
