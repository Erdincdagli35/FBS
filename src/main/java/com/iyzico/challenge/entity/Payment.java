package com.iyzico.challenge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    private String bankResponse;
}
