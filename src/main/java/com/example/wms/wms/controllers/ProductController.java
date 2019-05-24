package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.repositories.ProductRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Товары"}, description = "API для товара на складе")
@RestController
@RequestMapping(value = "product")
public class ProductController {

	private final ProductRepository repository;

	@Autowired
	public ProductController(ProductRepository repository) {
		this.repository = repository;
	}

	@PostMapping("/add")
	public void addProduct(@RequestBody ProductEntity productEntity){
		repository.save(productEntity);
	}

	@PostMapping("/all")
	public List<ProductEntity> showAll(){
		return repository.findAll();
	}

	@PostMapping("/deleteById")
	public void deleteProduct(@RequestParam(name = "id") Long id){
		repository.delete(repository.getOne(id));
	}
}
