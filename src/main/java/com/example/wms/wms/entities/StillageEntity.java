package com.example.wms.wms.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class StillageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(name = "row_position")
	int row;
	@Column(name = "column_position")
	int column;
	@Column(name = "shelf_position")
	int shelf;
	@Column(name = "stillige_width")
	double width;
	@Column(name = "stillage_height")
	double height;
	@Column(name = "stillage_length")
	double length;
	@Column(name = "stillage_max_weight")
	double 	max_weight;
	@Column(name = "stillage_max_count_object")
	int max_count_object;
}
