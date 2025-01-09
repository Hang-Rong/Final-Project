package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;
//    private Date date;

//    private String paymentMethod;
//    private String status;
    @Getter
    @Setter
    private Date orderDate;
    @Getter
    @Setter
    private Double totalPrice;

    public Order() {
    }

    public Order(int id, Date orderDate, double totalPrice) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;

    }

}
