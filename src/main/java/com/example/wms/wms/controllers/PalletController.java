package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.PalletEntity;
import com.example.wms.wms.repositories.PalletRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Паллеты"}, description = "API для паллетов на складе")
@RestController
@RequestMapping(value = "pallet")
public class PalletController {

    final PalletRepository palletRepository;

    public PalletController(PalletRepository palletRepository) {
        this.palletRepository = palletRepository;
    }

    @PostMapping("all")
    public List<PalletEntity> getAll(){
        return palletRepository.findAll();
    }
}
