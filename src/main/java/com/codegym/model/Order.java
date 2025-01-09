package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "order")
@Data

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double total;
    private String paymentMethod;
    private String status;
}

package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
>>>>>>> 57f7fda445cb61cd7042ca40aa230d1f4cab130f

import java.util.Date;

@Entity
<<<<<<< HEAD
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;
//    private Date date;


    @Getter
    @Setter
    private String paymentMethod;
    @Getter
    @Setter
    private String status;
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

=======
@Table(name = "order")
@Data

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double total;
    private String paymentMethod;
    private String status;
>>>>>>> 57f7fda445cb61cd7042ca40aa230d1f4cab130f
}
