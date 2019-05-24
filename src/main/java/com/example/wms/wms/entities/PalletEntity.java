package com.example.wms.wms.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PalletEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(name = "product_id")
	Long product_id;
	@Column(name = "product_count")
	int count_product;
	@Column(name = "product_stillage_id")
	Long stillageId;
	@Column(name = "product_width")
	double width;
	@Column(name = "product_height")
	double height;
	@Column(name = "product_length")
	double length;
	@Column(name = "batch_id")
	Long batch_id;
}
