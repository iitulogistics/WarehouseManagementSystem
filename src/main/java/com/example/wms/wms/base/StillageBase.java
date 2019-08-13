package com.example.wms.wms.base;

import lombok.Data;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.util.Set;

@Data
public abstract class StillageBase {
    double weigh;
    double length, height, width;
    int stillageNum, level, cell;
    Set<BaseType.TypeProduct> typeProducts;

    public abstract int disProduct();
    public abstract Batch takeProduct();

}
