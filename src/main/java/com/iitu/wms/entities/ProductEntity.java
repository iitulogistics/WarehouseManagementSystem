package com.iitu.wms.entities;

import com.iitu.wms.base.BaseType;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "product_count_in_warehouse")
    private double count_on_warehouse;
    @Column(name = "product_count_on_shipping")
    private double count_on_shipping;
    @Column(name = "product_count_expected")
    private double count_expected;

    @Enumerated(EnumType.STRING)
    private BaseType.Unit unit;

    @Column(name = "product_width")
    private double width;
    @Column(name = "product_height")
    private double height;
    @Column(name = "product_length")
    private double length;
    @Column(name = "product_weight")
    private double weight;
    @Column(name = "shelf_life")
    private Date date;
    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    private BaseType.TypeProduct type_product;

    @Column(name = "product_qr_code")
    private String bar_code;

    public ProductEntity() {
    }

    public ProductEntity(Long id, String name, int count, double price) {
        this.Id = id;
        this.product_name = name;
        this.count_on_shipping = count;
        this.price = price;
    }

    public String toString() {
        return "Имя товара: " + product_name + "\n" +
                "Ожидаемое время прибытия " + new SimpleDateFormat("dd.MM.yyyy").format(date) + "\n" +
                "Суммарная цена товара " + price + " тг\n" +
                "Кол-во " + count_expected + " шт.";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getCount_on_warehouse() {
        return count_on_warehouse;
    }

    public void setCount_on_warehouse(double count_on_warehouse) {
        this.count_on_warehouse = count_on_warehouse;
    }

    public double getCount_on_shipping() {
        return count_on_shipping;
    }

    public void setCount_on_shipping(double count_on_shipping) {
        this.count_on_shipping = count_on_shipping;
    }

    public double getCount_expected() {
        return count_expected;
    }

    public void setCount_expected(double count_expected) {
        this.count_expected = count_expected;
    }

    public BaseType.Unit getUnit() {
        return unit;
    }

    public void setUnit(BaseType.Unit unit) {
        this.unit = unit;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BaseType.TypeProduct getType_product() {
        return type_product;
    }

    public void setType_product(BaseType.TypeProduct type_product) {
        this.type_product = type_product;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }
}
