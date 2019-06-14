package com.example.wms.wms.base;

public class BaseType {
    public enum TypeProduct {
        fragile,
        food,
        technics
    }

    public enum TypeContainerProduct {
        pallet,
        box
    }

    public enum LifeCycle {
        //Поступление
        receipt,

        //Распределение
        distribution,

        //Хранение
        storage,

        //Отправка
        shipping
    }
}
