package com.example.wms.wms.entities;

import com.example.wms.wms.base.BaseType;
import lombok.Data;

import javax.persistence.*;
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

	//@ElementCollection(targetClass = BaseType.TypeProduct.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	//@Column(name = "type_product")
	BaseType.TypeProduct type_product;

	@Column(name = "product_qr_code")
	String qr_code;
}
