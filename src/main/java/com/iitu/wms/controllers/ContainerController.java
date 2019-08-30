package com.iitu.wms.controllers;

import com.iitu.wms.entities.ContainerEntity;
import com.iitu.wms.repositories.ContainerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("takeProduct")
    public ResponseEntity<?> takeProduct(@RequestParam String barcode, @RequestParam int amount) {
        ContainerEntity containerEntity = containerRepository.getContainersByBarCode(barcode).orElse(null);
        assert containerEntity != null;
        if (containerEntity.getAmount() < amount) {
            return ResponseEntity.ok("Товара не хватает");
        } else {
            containerRepository.updateCountProducts(containerEntity.getId(), containerEntity.getAmount() - amount);
        }
        return ResponseEntity.ok("Товар взят");
    }

    @ApiOperation("Контейнер по бар коду")
    @PostMapping("getContainerByBarcode")
    public ResponseEntity<?> getContainerByBarcode(String barcode) {
        ContainerEntity containerEntity = containerRepository.getContainersByBarCode(barcode).orElse(null);
        if (containerEntity == null) return ResponseEntity.ok("null");
        return ResponseEntity.ok(containerEntity);
    }
}
