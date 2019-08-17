package com.iitu.wms.base;

import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.util.Set;

public abstract class StillageBase {
    double weigh;
    double length, height, width;
    int stillageNum, level, cell;
    Set<BaseType.TypeProduct> typeProducts;

    public abstract int disProduct();
    public abstract Batch takeProduct();

}
