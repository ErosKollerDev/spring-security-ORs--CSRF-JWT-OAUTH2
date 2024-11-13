package com.eazybytes.springsecsection1.entity;

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
@Table(name = "notice_details")
public class NoticeDetailEntity {
    @Id
    @Column(name = "notice_id", nullable = false)
    private Integer noticeId;

    @Size(max = 200)
    @NotNull
    @Column(name = "notice_summary", nullable = false, length = 200)
    private String noticeSummary;

    @Size(max = 500)
    @NotNull
    @Column(name = "notice_details", nullable = false, length = 500)
    private String noticeDetails;

    @NotNull
    @Column(name = "notic_beg_dt", nullable = false)
    private LocalDate noticBegDt;

    @Column(name = "notic_end_dt")
    private LocalDate noticEndDt;

    @Column(name = "create_dt")
    private LocalDate createDt;

    @Column(name = "update_dt")
    private LocalDate updateDt;

}