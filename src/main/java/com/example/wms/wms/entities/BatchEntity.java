package com.example.wms.wms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class BatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "company_name")
    String company_name;
//    @Column(name = "delivery_address")
//    String delivery_address;
    @Column(name = "delivery_date")
    Date delivery_date;
}
