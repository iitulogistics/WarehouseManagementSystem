package com.iitu.wms.entities;

import com.iitu.wms.base.BaseType;

import javax.persistence.*;

@Entity
public class ContainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    @Column(name = "product_count")
    private int amount;

    @JoinColumn(name = "cell_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CellEntity cellId;

    @Column(name = "product_width")
    private double width;
    @Column(name = "product_height")
    private double height;
    @Column(name = "product_length")
    private double length;
    @Column(name = "weight")
    private double weight;

    @Enumerated(EnumType.STRING)
    private BaseType.TypeContainerProduct typeContainer;

    @Enumerated(EnumType.STRING)
    private BaseType.LifeCycle lifeCycle;

    private String bar_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CellEntity getCellId() {
        return cellId;
    }

    public void setCellId(CellEntity cellId) {
        this.cellId = cellId;
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

    public BaseType.TypeContainerProduct getTypeContainer() {
        return typeContainer;
    }

    public void setTypeContainer(BaseType.TypeContainerProduct typeContainer) {
        this.typeContainer = typeContainer;
    }

    public BaseType.LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(BaseType.LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }
}
