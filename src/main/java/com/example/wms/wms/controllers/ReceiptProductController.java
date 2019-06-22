package com.example.wms.wms.controllers;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.entities.StillageEntity;
import com.example.wms.wms.entities.TaskEntity;
import com.example.wms.wms.repositories.ContainerRepository;
import com.example.wms.wms.repositories.ProductRepository;
import com.example.wms.wms.repositories.StillageRepository;
import com.example.wms.wms.repositories.TaskRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Поступление товара"}, description = "API для Поступившего товара")
@RestController
@RequestMapping("receipt")
public class ReceiptProductController {

    private final ProductRepository productRepository;
    private final ContainerRepository containerRepository;
    private final StillageRepository stillageRepository;
    private final TaskRepository taskRepository;

    @Value("${standard.pallet.length}")
    double standard_length;
    @Value("${standard.pallet.weight}")
    double standard_weight;
    @Value("${standard.pallet.width}")
    double standard_width;
    @Value("${standard.pallet.height}")
    double standard_height;


    @Autowired
    public ReceiptProductController(ProductRepository productRepository,
                                    ContainerRepository containerRepository,
                                    StillageRepository stillageRepository,
                                    TaskRepository taskRepository) {
        this.productRepository = productRepository;
        this.containerRepository = containerRepository;
        this.stillageRepository = stillageRepository;
        this.taskRepository = taskRepository;
    }

    @ApiOperation("Добавить продукт для дальнейшей упаковки в паллет")
    @PostMapping("/addOneProductForPallet")
    public ResponseEntity<?> addOneProductForPallet(@RequestParam(name = "id_product") long id_product) {
        ProductEntity productEntity = productRepository.getOne(id_product);
        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
                productEntity.getCount_on_warehouse() + 1);
        return ResponseEntity.ok("Продукт добавлен в базу без контейнера.");
    }

//    @ApiOperation("Бракованый товар")
//    @PostMapping("/addDefectiveProduct")
//    public ResponseEntity<?> addDefectiveProduct() {
//        return ResponseEntity.ok("Продукт добавлен в базу без контейнера.");
//    }

    @ApiOperation("Добавить коробку")
    @PostMapping("/addBox")
    public ResponseEntity<?> addBox(@RequestParam(name = "id_product") long id_product,
                                    @RequestParam(name = "height") double height,
                                    @RequestParam(name = "width") double width,
                                    @RequestParam(name = "length") double length,
                                    @RequestParam(name = "count") int count) {
        ProductEntity productEntity = productRepository.getOne(id_product);

        ContainerEntity box = new ContainerEntity();
        box.setWeight(productEntity.getWeight() * count);
        box.setHeight(height);
        box.setWidth(width);
        box.setLength(length);
        box.setProduct_id(id_product);
        box.setCount_product(count);
        box.setCount_product(count);
        box.setLifeCycle(BaseType.LifeCycle.receipt);
        box.setTypeContainer(BaseType.TypeContainerProduct.box);

        boolean isDistribution = distribution(box, id_product);

        StillageEntity stillageEntity = stillageRepository.getOne(box.getStillageId());

        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
                productEntity.getCount_on_warehouse() + count);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setPriority(BaseType.PriorityOfExecution.midle);

        if (isDistribution) {
            taskEntity.setTask("Переместить коробку с id = " + box.getId() +
                    " из зоны отгрузки в ячейку хранения (номер стиллажа - " + stillageEntity.getStillage_index() +
                    " номер ячейки " + stillageEntity.getShelf_index() + ")");
            taskRepository.save(taskEntity);
            return ResponseEntity.ok("Коробка добавлена в базу.\nБыло найдено место для хранения");
        } else {
            return ResponseEntity.ok("Коробка добавлена в базу.\nНе было найдено место для хранения");
        }
    }

    @ApiOperation("Добавить паллет")
    @PostMapping("/addPallet")
    public ResponseEntity<?> addPallet(@RequestParam long id_product,
                                       @RequestParam int count_product,
                                       @RequestParam double height) {
        ProductEntity productEntity = productRepository.getOne(id_product);

        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
                productEntity.getCount_on_warehouse() + count_product);

        return makePallet(id_product, count_product, height);
    }

    @ApiOperation("Упаковать продукты в паллет")
    @PostMapping("/makePallet")
    public ResponseEntity<?> makePallet(@RequestParam long id_product,
                                        @RequestParam int count_product,
                                        @RequestParam double height) {
        ProductEntity productEntity = productRepository.getOne(id_product);

        ContainerEntity pallet = new ContainerEntity();
        pallet.setCount_product(count_product);
        pallet.setProduct_id(id_product);
        pallet.setTypeContainer(BaseType.TypeContainerProduct.pallet);
        pallet.setHeight(height + standard_height);
        pallet.setWeight(productEntity.getWeight() * count_product);
        pallet.setLifeCycle(BaseType.LifeCycle.receipt);

        //Стандартный размер палеты 1000мм на 1200мм
        pallet.setLength(standard_length);
        pallet.setWidth(standard_width);
        //Распределение палета
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setPriority(BaseType.PriorityOfExecution.midle);

        if (distribution(pallet, id_product)) {
            StillageEntity stillageEntity = stillageRepository.getOne(pallet.getStillageId());
            taskEntity.setTask("Переместить паллет с id = " + pallet.getId() +
                    " из зоны отгрузки в ячейку хранения (номер стиллажа " + stillageEntity.getStillage_index() +
                    " номер ячейки " + stillageEntity.getShelf_index() + ")");
            taskRepository.save(taskEntity);
            return ResponseEntity.ok("Паллет добавлена в базу.\nБыло найдено место для хранения");
        } else {
            return ResponseEntity.ok("Паллет добавлена в базу.\nНе было найдено место для хранения.\n");
        }
    }

    private boolean distribution(ContainerEntity container, long product_id) {
        List<StillageEntity> stillages = stillageRepository.findAll();

        for (StillageEntity stillage : stillages) {
            List<ContainerEntity> palletEntities = containerRepository.getContainersByStillageId(stillage.getId());

            if (palletEntities.size() < stillage.getMax_count_object() && !palletEntities.isEmpty()) {
                if (palletEntities.get(0).getProduct_id() == product_id) {
                    container.setStillageId(stillage.getId());
                    container.setLifeCycle(BaseType.LifeCycle.distribution);
                    containerRepository.save(container);
                    return true;
                }
            }
        }

        for (StillageEntity stillage : stillages) {
            List<ContainerEntity> palletEntities = containerRepository.getContainersByStillageId(stillage.getId());

            if (palletEntities.isEmpty()) {
                if (stillage.getWidth() >= container.getWidth() &&
                        stillage.getHeight() >= container.getHeight() &&
                        stillage.getLength() >= container.getLength() &&
                        stillage.getMax_weight() > container.getWeight()) {
                    int max_count_object = Math.min((int) (stillage.getWidth() / container.getWidth()) *
                                    (int) (stillage.getHeight() / container.getHeight()) *
                                    (int) (stillage.getLength() / container.getLength()),
                            (int) (stillage.getMax_weight() / container.getWeight()));

                    stillageRepository.updateMaxCountObject(stillage.getId(), max_count_object);
                    container.setLifeCycle(BaseType.LifeCycle.distribution);
                    container.setStillageId(stillage.getId());
                    containerRepository.save(container);
                    return true;
                }
            }
        }
        containerRepository.save(container);
        return false;
    }
}
