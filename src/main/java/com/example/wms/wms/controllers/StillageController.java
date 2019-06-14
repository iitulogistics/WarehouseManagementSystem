package com.example.wms.wms.controllers;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.StillageEntity;
import com.example.wms.wms.repositories.StillageRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

//										Стандартные размеры стилажей
//					Высота									Длина							Глубина
//1000; 1500; 1800; 2000; 2200; 2500; 3000; 3500	||	700; 1000; 1200; 1500 	||	300; 400; 500; 600; 800; 1000

@Api(tags = {"Стилажи"}, description = "API для Стилажей на складе")
@RestController
@RequestMapping(value = "stillage")
public class StillageController {

	private final StillageRepository repository;

	@Value("${standard.pallet.width}")
	double pallet_width;
	@Value("${standard.pallet.length}")
	double pallet_length;

	@Autowired
	public StillageController(StillageRepository repository){
		this.repository = repository;
	}

	@ApiOperation("Добавить стилаж")
	@PostMapping("/add")
	public ResponseEntity<?> addStillage(@RequestBody StillageEntity stillageEntity){
		repository.save(stillageEntity);
		return ResponseEntity.ok("Стилаж добавлен в дазу");
	}

	@ApiOperation("Добавить стилаж")
	@PostMapping("/addStillageByRequests")
	public ResponseEntity<?> addStillage(@RequestParam int column,
										 @RequestParam int row,
										 @RequestParam int shelf,
										 @RequestParam double width,
										 @RequestParam double height,
										 @RequestParam double length,
										 @RequestParam double max_weight,
										 @RequestParam Collection<BaseType.TypeProduct> products){
		StillageEntity stillageEntity = new StillageEntity();
		stillageEntity.setColumn(column);
		stillageEntity.setRow(row);
		stillageEntity.setShelf(shelf);
		stillageEntity.setWidth(width);
		stillageEntity.setHeight(height);
		stillageEntity.setLength(length);
		stillageEntity.setMax_weight(max_weight);
		stillageEntity.setTypeProduct(products);
		repository.save(stillageEntity);
		return ResponseEntity.ok("Стилаж добавлен в дазу");
	}

	@ApiOperation("Удалить стилаж")
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteStillage(@RequestParam(name = "id") Long id){
		repository.delete(repository.getOne(id));
		return ResponseEntity.ok("Стилаж удален из дазы");
	}

	@ApiOperation("Показать список стилажей")
	@PostMapping("/all")
	public List<StillageEntity> getAll(){
		return repository.findAll();
	}
}
