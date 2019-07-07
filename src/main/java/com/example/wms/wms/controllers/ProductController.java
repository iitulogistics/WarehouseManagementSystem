package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.repositories.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Товары"}, description = "API для товара на складе")
@RestController
@RequestMapping(value = "/product")
public class ProductController {

	private final ProductRepository repository;

	@Autowired
	public ProductController(ProductRepository repository) {
		this.repository = repository;
	}

	@ApiOperation("Добавить продукт")
	@PostMapping("/add")
	public void addProduct(@RequestBody ProductEntity productEntity){
		repository.save(productEntity);
	}

	@ApiOperation("Показать все продукты")
	@PostMapping("/all")
	public List<ProductEntity> showAll(){
		return repository.findAll();
	}

	@ApiOperation("Показать продукт по имени")
	@PostMapping("/getProductByName")
	public List<ProductEntity> getProductByName(@RequestParam String name){
		return repository.getProductByName(name);
	}

	@ApiOperation("Показать продукт по имени")
	@PostMapping("/getProductLikeName")
	public List<ProductEntity> getProductLikeName(@RequestParam String name){
		return repository.getProductLikeName(name);
	}

	@ApiOperation("Удалить продукт")
	@DeleteMapping("/deleteById")
	public void deleteProduct(@RequestParam(name = "id") Long id){
		repository.delete(repository.getOne(id));
	}
}
