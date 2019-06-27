package com.example.wms.wms.entities;

import com.example.wms.wms.base.BaseType;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long Id;
	@Column(name = "product_name")
	String product_name;
	@Column(name = "product_count_in_warehouse")
	int count_on_warehouse;
	@Column(name = "product_count_on_shipping")
	int count_on_shipping;
	@Column(name = "product_count_expected")
	int count_expected;
	@Column(name = "product_width")
	double width;
	@Column(name = "product_height")
	double height;
	@Column(name = "product_length")
	double length;
	@Column(name = "product_weight")
	double weight;
	@Column(name = "shelf_life")
	Date date;
	@Column(name = "price")
	double price;

	@Enumerated(EnumType.STRING)
	BaseType.TypeProduct type_product;

	@Column(name = "product_qr_code")
	String qr_code;

	public ProductEntity(){
	}

	public ProductEntity(Long id, String name, int count, double price){
		this.Id = id;
		this.product_name = name;
		this.count_on_shipping = count;
		this.price = price;
	}

	public String toString(){
		return "Имя товара: " + product_name + "\n" +
				"Ожидаемое время прибытия " + new SimpleDateFormat("dd.MM.yyyy").format(date) + "\n" +
				"Суммарная цена товара " + price + " тг\n" +
				"Кол-во " + count_expected+" шт.";
	}
}
