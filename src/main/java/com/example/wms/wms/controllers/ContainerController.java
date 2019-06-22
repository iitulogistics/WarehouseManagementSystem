package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.repositories.ContainerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Контейнеры"}, description = "API для котейнеров на складе")
@RestController
@RequestMapping(value = "/container")
public class ContainerController {

    final ContainerRepository palletRepository;

    public ContainerController(ContainerRepository palletRepository) {
        this.palletRepository = palletRepository;
    }

    @ApiOperation("Показать список контейнеров")
    @PostMapping("all")
    public List<ContainerEntity> getAll(){
        return palletRepository.findAll();
    }
}
