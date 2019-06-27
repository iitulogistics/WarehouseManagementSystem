package com.example.wms.wms.controllers;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.entities.StillageEntity;
import com.example.wms.wms.entities.TaskEntity;
import com.example.wms.wms.qr_code.QRCodeReader;
import com.example.wms.wms.repositories.ContainerRepository;
import com.example.wms.wms.repositories.ProductRepository;
import com.example.wms.wms.repositories.StillageRepository;
import com.example.wms.wms.repositories.TaskRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
                productEntity.getCount_on_warehouse() + count);

        containerRepository.save(box);
        return ResponseEntity.ok("Коробка добавлен в базу");
    }

//    @ApiOperation("Добавить паллет")
//    @PostMapping("/addPallet")
//    public ResponseEntity<?> addPallet(@RequestParam long id_product,
//                                       @RequestParam int count_product,
//                                       @RequestParam double height) {
//        ProductEntity productEntity = productRepository.getOne(id_product);
//
//        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
//                productEntity.getCount_on_warehouse() + count_product);
//        return makePallet(id_product, count_product, height);
//    }

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
        pallet.setHeight(height);
//        pallet.setHeight(height + standard_height);
        pallet.setWeight(productEntity.getWeight() * count_product);
        pallet.setLifeCycle(BaseType.LifeCycle.receipt);

        //Стандартный размер палеты 1000мм на 1200мм
        pallet.setLength(standard_length);
        pallet.setWidth(standard_width);
        //Распределение палета
        containerRepository.save(pallet);

        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
                productEntity.getCount_on_warehouse() + count_product);

        return ResponseEntity.ok("Паллет добавлен в базу");
    }

//    private boolean distribution(ContainerEntity container, long product_id) {
//        List<StillageEntity> stillages = stillageRepository.findAll();
//
//        for (StillageEntity stillage : stillages) {
//            List<ContainerEntity> palletEntities = containerRepository.getContainersByStillageId(stillage.getId());
//
//            if (palletEntities.size() < stillage.getMax_count_object() && !palletEntities.isEmpty()) {
//                if (palletEntities.get(0).getProduct_id() == product_id) {
//                    container.setStillageId(stillage.getId());
//                    container.setLifeCycle(BaseType.LifeCycle.distribution);
//                    containerRepository.save(container);
//                    return true;
//                }
//            }
//        }
//
//        for (StillageEntity stillage : stillages) {
//            List<ContainerEntity> palletEntities = containerRepository.getContainersByStillageId(stillage.getId());
//
//            if (palletEntities.isEmpty()) {
//                if (stillage.getWidth() >= container.getWidth() &&
//                        stillage.getHeight() >= container.getHeight() &&
//                        stillage.getLength() >= container.getLength() &&
//                        stillage.getMax_weight() > container.getWeight()) {
//                    int max_count_object = Math.min((int) (stillage.getWidth() / container.getWidth()) *
//                                    (int) (stillage.getHeight() / container.getHeight()) *
//                                    (int) (stillage.getLength() / container.getLength()),
//                            (int) (stillage.getMax_weight() / container.getWeight()));
//
//                    stillageRepository.updateMaxCountObject(stillage.getId(), max_count_object);
//                    container.setLifeCycle(BaseType.LifeCycle.distribution);
//                    container.setStillageId(stillage.getId());
//                    containerRepository.save(container);
//                    return true;
//                }
//            }
//        }
//        containerRepository.save(container);
//        return false;
//    }

    @ApiOperation("Выбор стилажа для контейнера")
    @PostMapping("/distribution")
    public ResponseEntity<?> distribution(Long id_container, Long id_stillage) {
        containerRepository.updateLifeCyrcleAndStillageById(id_container, BaseType.LifeCycle.distribution, id_stillage);
        return ResponseEntity.ok("Палету присвоен стилаж");
    }

    @ApiOperation("Список стилажей для хранения в нет контейнера")
    @PostMapping("/getLooseStillage")
    public List<StillageEntity> getLooseStillage() {
        return stillageRepository.getLooseStillage();
    }

    @ApiOperation("генерировать qr code")
    @PostMapping("/generationQRCode")
    public ResponseEntity<?> generationQRCode(String s) {
        return ResponseEntity.ok(QRCodeReader.getQRCodeImage(s, 200, 200));
    }


    @ApiOperation("раскодировать qr code")
    @PostMapping("/decodeQRCode")
    public ResponseEntity<?> decodeQRCode(@RequestParam MultipartFile file) {
        try {
            return ResponseEntity.ok(QRCodeReader.decodeQRCode(file));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
