package com.example.wms.wms.entities;

import com.example.wms.wms.base.BaseType;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class StillageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "stillage_index")
    int stillage_index;
    @Column(name = "shelf_index")
    int shelf_index;
    @Column(name = "stillige_width")
    double width;
    @Column(name = "stillage_height")
    double height;
    @Column(name = "stillage_length")
    double length;
    @Column(name = "stillage_max_weight")
    double max_weight;
    @Column(name = "stillage_max_count_object")
    int max_count_object;
    @Column(name = "stillage_count_object")
    int count_object;

    @ElementCollection(targetClass = BaseType.TypeProduct.class)
    @Enumerated(EnumType.STRING)
    Collection<BaseType.TypeProduct> typeProduct;

    @ElementCollection(targetClass = ContainerEntity.class)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_stillage_id")
    Collection<ContainerEntity> containerEntities;
}
