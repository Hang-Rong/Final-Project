package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "order_receipt")
@Data

public class OrderReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double total;
    private String paymentMethod;
    private String status;
}
