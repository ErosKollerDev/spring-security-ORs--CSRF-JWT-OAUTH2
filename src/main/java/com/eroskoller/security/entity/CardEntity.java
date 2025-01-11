package com.eroskoller.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Integer id;

    @Size(max = 500)
    @NotNull
    @Column(name = "card_number", nullable = false, length = 500)
    private String cardNumber;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private CustomerEntity customer;

    @NotNull
    @Column(name = "customer_id")
    private Integer customerId;

    @Size(max = 100)
    @NotNull
    @Column(name = "card_type", nullable = false, length = 100)
    private String cardType;

    @NotNull
    @Column(name = "total_limit", nullable = false)
    private Integer totalLimit;

    @NotNull
    @Column(name = "amount_used", nullable = false)
    private Integer amountUsed;

    @NotNull
    @Column(name = "available_amount", nullable = false)
    private Integer availableAmount;

    @Column(name = "create_dt")
    private LocalDate createDt;

}