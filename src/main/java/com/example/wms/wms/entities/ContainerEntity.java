package com.example.wms.wms.entities;

import com.example.wms.wms.base.BaseType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ContainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "product_id")
    Long product_id;
    @Column(name = "product_count")
    int count_product;
    @Column(name = "product_stillage_id")
    Long stillageId;
    @Column(name = "product_width")
    double width;
    @Column(name = "product_height")
    double height;
    @Column(name = "product_length")
    double length;
    @Column(name = "weight")
    double weight;

    @Enumerated(EnumType.STRING)
    BaseType.TypeContainerProduct typeContainer;

    @Enumerated(EnumType.STRING)
    BaseType.LifeCycle lifeCycle;
}
