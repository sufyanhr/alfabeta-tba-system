package com.alfabeta.model.system;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "system_error_log")
public class SystemErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String errorCode;
    private String message;
    private String exceptionType;
    private String endpoint;
    private String userEmail;
    private String requestMethod;
    private String clientIp;
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(length = 4000)
    private String stackTrace;
}
