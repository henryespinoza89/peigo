package com.peigo.msvcaccounts.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peigo.msvcaccounts.common.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdPayment;

    @Column(nullable=false, updatable=false)
    private String dateTransaction = Utils.getTime();

    @Column(nullable = false, length = 20)
    private String accountOrigin;

    @Column()
    private BigDecimal balanceAccountOrigin;

    @Column(nullable = false, length = 20)
    private String accountDestination;

    @Column()
    private BigDecimal balanceAccountDestination;

    @Column()
    private BigDecimal balanceTransfer;

    @Column()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operationNumber;
}
