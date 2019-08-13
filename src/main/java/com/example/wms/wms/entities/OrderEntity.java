package com.example.wms.wms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long order_number;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    ProductEntity product;

    Integer amount;

    Date date;
    String address;
}
