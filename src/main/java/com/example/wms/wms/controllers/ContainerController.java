package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.repositories.ContainerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Контейнеры"}, description = "API для котейнеров на складе")
@RestController
@RequestMapping(value = "/container")
public class ContainerController {

    private final ContainerRepository containerRepository;

    public ContainerController(ContainerRepository palletRepository) {
        this.containerRepository = palletRepository;
    }

    @ApiOperation("Показать список контейнеров")
    @GetMapping("all")
    public List<ContainerEntity> getAll() {
        return containerRepository.findAll();
    }

    @ApiOperation("Взять товар")
    public ResponseEntity<?> takeProduct(Long container_id, int amount) {
        ContainerEntity containerEntity = containerRepository.getOne(container_id);
        if (containerEntity.getAmount() < amount) {
            return ResponseEntity.ok("Товара не хватает");
        } else {
            containerRepository.updateCountProducts(container_id, containerEntity.getAmount() - amount);
        }
        return ResponseEntity.ok("Товар взят");
    }
}
