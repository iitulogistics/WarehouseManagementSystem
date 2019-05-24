package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.StillageEntity;
import com.example.wms.wms.repositories.StillageRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/add")
	public void addStillage(@RequestBody StillageEntity stillageEntity){
		stillageEntity.setMax_count_pallet((int) ((stillageEntity.getLength() / pallet_length) *
				(int)(stillageEntity.getWidth() / pallet_width)));
		repository.save(stillageEntity);
	}

	@PostMapping("/delete")
	public void deleteStillage(@RequestParam(name = "id") Long id){
		repository.delete(repository.getOne(id));
	}

	@PostMapping("/all")
	public List<StillageEntity> getAll(){
		return repository.findAll();
	}
}
