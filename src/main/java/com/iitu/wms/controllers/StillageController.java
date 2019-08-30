package com.iitu.wms.controllers;

import com.iitu.wms.base.BaseType;
import com.iitu.wms.entities.CellEntity;
import com.iitu.wms.entities.User;
import com.iitu.wms.repositories.CellRepository;
import com.iitu.wms.repositories.ContainerRepository;
import com.iitu.wms.repositories.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Api(tags = {"Стеллажи"}, description = "API для Стилажей на складе")
@RestController
@RequestMapping(value = "/stillage")
public class StillageController {

    private final CellRepository repository;
    private final ProductRepository productRepository;
    private final ContainerRepository containerRepository;

    @Value("${standard.pallet.width}")
    double pallet_width;
    @Value("${standard.pallet.length}")
    double pallet_length;

    @Autowired
    public StillageController(CellRepository repository,
                              ProductRepository productRepository,
                              ContainerRepository containerRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.containerRepository = containerRepository;
    }

    @GetMapping("")
    public ModelAndView main(@AuthenticationPrincipal User user) {
        List<CellEntity> stillageEntities = repository.findAll();
        stillageEntities.sort(new Comparator<CellEntity>() {
            @Override
            public int compare(CellEntity o1, CellEntity o2) {
                return (o1.getStillage() * 10000 + o1.getShelf() * 100 + o1.getCell()) -
                        (o2.getStillage() * 10000 + o2.getShelf() * 100 + o2.getCell());
            }
        });

        Map<String, Object> root = new TreeMap<>();


        root.put("stillages", stillageEntities);
        root.put("containers", containerRepository.findAll());
        root.put("user", user);

        return new ModelAndView("stillage", root);
    }

    @ApiOperation("Добавить стеллаж")
    @PostMapping("/add")
    public ResponseEntity<?> addStillage(@RequestBody CellEntity stillageEntity) {
        repository.save(stillageEntity);
        return ResponseEntity.ok("Стеллаж добавлен в дазу");
    }

    @ApiOperation("Добавить стеллаж")
    @PostMapping("/addStillageByRequests")
    public ResponseEntity<?> addStillage(@RequestParam int stillage_index,
                                         @RequestParam int shelf_index,
                                         @RequestParam int cell_index,
                                         @RequestParam double width,
                                         @RequestParam double height,
                                         @RequestParam double length,
                                         @RequestParam double max_weight,
                                         @RequestParam int max_count_object,
                                         @RequestParam Collection<BaseType.TypeProduct> products) {
        CellEntity stillageEntity = new CellEntity();
        stillageEntity.setWidth(width);
        stillageEntity.setStillage(stillage_index);
        stillageEntity.setShelf(shelf_index);
        stillageEntity.setCell(cell_index);
        stillageEntity.setHeight(height);
        stillageEntity.setLength(length);
        stillageEntity.setMax_weight(max_weight);
        stillageEntity.setTypeProduct(products);
        stillageEntity.setMax_count_object(max_count_object);
        repository.save(stillageEntity);
        return ResponseEntity.ok("Стеллаж добавлен в базу");
    }

    @ApiOperation("Удалить стеллаж")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStillage(@RequestParam(name = "id") Long id) {
        repository.delete(repository.getOne(id));
        return ResponseEntity.ok("Стеллаж удален из базы");
    }

    @ApiOperation("Показать список стеллаж")
    @GetMapping("/all")
    public List<CellEntity> getAll() {
        return repository.findAll();
    }

    @ApiOperation("Список пустых и не доконца заполненных стеллажей")
    @PostMapping("/getLooseStillage")
    public List<CellEntity> getLooseStillage() {
        return repository.getLooseStillage();
    }

    @PostMapping("getCellByBarcode")
    public CellEntity getCellByBarcode(@RequestParam String barcode){
        return repository.getCellByBarCode(barcode);
    }
}
