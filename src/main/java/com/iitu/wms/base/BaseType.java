package com.iitu.wms.base;

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
        defective,
        //Поступление
        receipt,

        //Распределение
        distribution,

        //Хранение
        storage,

        //Отправка
        shipping
    }

    public enum PriorityOfExecution {
        low,

        middle,

        high
    }

    public enum StageOfExecution{
        inProgress,
        done
    }

    public enum Unit{
        thing, kg
    }
}
