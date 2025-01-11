package com.eroskoller.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "contact_message")
public class ContactMessageEntity {
    @Id
    @Size(max = 50)
    @Column(name = "contact_id", nullable = false, length = 50)
    private String contactId;

    @Size(max = 50)
    @NotNull
    @Column(name = "contact_name", nullable = false, length = 50)
    private String contactName;

    @Size(max = 100)
    @NotNull
    @Column(name = "contact_email", nullable = false, length = 100)
    private String contactEmail;

    @Size(max = 500)
    @NotNull
    @Column(name = "subject", nullable = false, length = 500)
    private String subject;

    @Size(max = 2000)
    @NotNull
    @Column(name = "message", nullable = false, length = 2000)
    private String message;

    @Column(name = "create_dt")
    private LocalDate createDt;

}