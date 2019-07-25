package com.example.wms.wms.controllers;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.entities.User;
import com.example.wms.wms.repositories.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Api(tags = {"Товары"}, description = "API для товара на складе")
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductRepository repository;

    @GetMapping("")
    public ModelAndView index(@AuthenticationPrincipal User user) {
        Map<String, Object> root = new TreeMap<>();

        root.put("products", showAll());
        root.put("last_search", "");
        root.put("user", user);
        return new ModelAndView("product", root);
    }

    @PostMapping("")
    public ModelAndView getProdLikeName(@AuthenticationPrincipal User user,
                                        @RequestParam(name = "name") String name) {
        Map<String, Object> root = new TreeMap<>();

        root.put("products", getProductLikeName(name));
        root.put("last_search", name);
        root.put("user", user);
        return new ModelAndView("product", root);
    }

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @ApiOperation("Добавить продукт")
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductEntity productEntity) {
        repository.save(productEntity);
        return ResponseEntity.ok("Товар добавлен");
    }

    @ApiOperation("Добавить продукт")
    @PostMapping("/addProductByParam")
    public ResponseEntity<?> addProductByParam(@RequestParam(name = "product_name") String productName,
                                               @RequestParam(name = "length") double length,
                                               @RequestParam(name = "width") double width,
                                               @RequestParam(name = "height") double height,
                                               @RequestParam(name = "weight") double weight,
                                               @RequestParam(name = "price") double price,
                                               @RequestParam(name = "type_product") BaseType.TypeProduct product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProduct_name(productName);
        productEntity.setLength(length);
        productEntity.setWidth(width);
        productEntity.setHeight(height);
        productEntity.setWeight(weight);
        productEntity.setPrice(price);
        productEntity.setType_product(product);
        return addProduct(productEntity);
    }


    @ApiOperation("Показать все продукты")
    @PostMapping("/all")
    public List<ProductEntity> showAll() {
        return repository.findAll();
    }

    @ApiOperation("Показать продукт по имени")
    @PostMapping("/getProductByName")
    public List<ProductEntity> getProductByName(@RequestParam String name) {
        return repository.getProductByName(name);
    }

    @ApiOperation("Показать продукт по имени")
    @PostMapping("/getProductLikeName")
    public List<ProductEntity> getProductLikeName(@RequestParam String name) {
        return repository.getProductLikeName(name);
    }

    @ApiOperation("Удалить продукт")
    @DeleteMapping("/deleteById")
    public void deleteProduct(@RequestParam(name = "id") Long id) {
        repository.delete(repository.getOne(id));
    }
}
