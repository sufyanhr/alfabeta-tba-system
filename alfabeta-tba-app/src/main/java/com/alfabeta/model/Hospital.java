package com.alfabeta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "hospitals")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 120)
    @Column(name = "contact_email", length = 120)
    private String contactEmail;

    @Size(max = 50)
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}