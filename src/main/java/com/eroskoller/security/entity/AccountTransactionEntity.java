package com.eroskoller.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Data
@Table(name = "account_transactions")
public class AccountTransactionEntity {
    @Id
    @Size(max = 200)
    @Column(name = "transaction_id", nullable = false, length = 200)
    private String transactionId;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "account_number", nullable = false)
//    private AccountEntity accountNumber;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private Integer accountNumber;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private CustomerEntity customer;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @NotNull
    @Column(name = "transaction_dt", nullable = false)
    private LocalDate transactionDt;

    @Size(max = 200)
    @NotNull
    @Column(name = "transaction_summary", nullable = false, length = 200)
    private String transactionSummary;

    @Size(max = 100)
    @NotNull
    @Column(name = "transaction_type", nullable = false, length = 100)
    private String transactionType;

    @NotNull
    @Column(name = "transaction_amt", nullable = false)
    private Integer transactionAmt;

    @NotNull
    @Column(name = "closing_balance", nullable = false)
    private Integer closingBalance;

    @Column(name = "create_dt")
    private LocalDate createDt;

}