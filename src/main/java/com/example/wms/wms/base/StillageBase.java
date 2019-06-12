package com.example.wms.wms.base;

import lombok.Data;

@Data
public abstract class StillageBase {
    double weigh;
    double length, height, width;
    double stillageNum, level, cell;
    BaseType.TypeProduct typeProduct;
    BaseType.TypeStillage typeStillage;

    public abstract int disProduct();
}
