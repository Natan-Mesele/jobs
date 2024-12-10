package com.example.jobs.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;

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

        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number")
        private String phoneNumber;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        @JsonManagedReference
        private UserProfile profile;

}
