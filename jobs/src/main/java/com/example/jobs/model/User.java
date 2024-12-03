package com.example.jobs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String fullName;
        private String email;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String password;

        private USER_ROLE role=USER_ROLE.ROLE_USER;
}
