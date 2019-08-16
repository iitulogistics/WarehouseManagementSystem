package com.iitu.wms.entities;

import com.iitu.wms.base.BaseType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ContainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    ProductEntity product;

    @Column(name = "product_count")
    int amount;

    @JoinColumn(name = "cell_id")
    @ManyToOne(fetch = FetchType.LAZY)
    CellEntity cellId;

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
