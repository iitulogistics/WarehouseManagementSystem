package com.iitu.wms.entities;

import com.iitu.wms.base.BaseType;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class CellEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "stillage_index")
    private int stillage;
    @Column(name = "shelf_index")
    private int shelf;
    @Column(name = "cell_index")
    private int cell;

    @Column(name = "stillige_width")
    private double width;
    @Column(name = "stillage_height")
    private double height;
    @Column(name = "stillage_length")
    private double length;

    @Column(name = "stillage_max_weight")
    private double max_weight;
    @Column(name = "stillage_max_count_object")
    private int max_count_object;
    @Column(name = "stillage_count_object")
    private int count_object;
    private String bar_code;

    @ElementCollection(targetClass = BaseType.TypeProduct.class)
    @Enumerated(EnumType.STRING)
    private Collection<BaseType.TypeProduct> typeProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStillage() {
        return stillage;
    }

    public void setStillage(int stillage) {
        this.stillage = stillage;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(double max_weight) {
        this.max_weight = max_weight;
    }

    public int getMax_count_object() {
        return max_count_object;
    }

    public void setMax_count_object(int max_count_object) {
        this.max_count_object = max_count_object;
    }

    public int getCount_object() {
        return count_object;
    }

    public void setCount_object(int count_object) {
        this.count_object = count_object;
    }

    public Collection<BaseType.TypeProduct> getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(Collection<BaseType.TypeProduct> typeProduct) {
        this.typeProduct = typeProduct;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }
}
