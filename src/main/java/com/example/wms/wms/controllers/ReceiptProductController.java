package com.example.wms.wms.controllers;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.helpers.QRCodeHelper;
import com.example.wms.wms.repositories.ContainerRepository;
import com.example.wms.wms.repositories.ProductRepository;
import com.example.wms.wms.repositories.CellRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"Поступление товара"}, description = "API для Поступившего товара")
@RestController
@RequestMapping("receipt")
public class ReceiptProductController {

    private final ProductRepository productRepository;
    private final ContainerRepository containerRepository;
    private final CellRepository stillageRepository;
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
                                    CellRepository stillageRepository,
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

//    @ApiOperation("Дефектную коробку товара")
//    @PostMapping("/addDefectiveProductBox")
//    public ResponseEntity<?> addDefectiveProductBox(int count) {
//        ContainerEntity productEntity = new ContainerEntity();
//        productEntity.setLifeCycle(BaseType.LifeCycle.defective);
//        containerRepository.save(productEntity);
//        return ResponseEntity.ok("Продукт добавлен как дефектный.");
//    }

    @ApiOperation("Добавить коробку")
    @PostMapping("/addBox")
    public ResponseEntity<?> addBox(@RequestParam(name = "id_product") ProductEntity productEntity,
                                    @RequestParam(name = "height") double height,
                                    @RequestParam(name = "width") double width,
                                    @RequestParam(name = "length") double length,
                                    @RequestParam(name = "count") int count) {
        ContainerEntity box = new ContainerEntity();
        box.setWeight(productEntity.getWeight() * count);
        box.setHeight(height);
        box.setWidth(width);
        box.setLength(length);
        box.setProduct(productEntity);
        box.setAmount(count);
        box.setLifeCycle(BaseType.LifeCycle.receipt);
        box.setTypeContainer(BaseType.TypeContainerProduct.box);

        productRepository.updateById(productEntity.getId(), productEntity.getCount_on_shipping(),
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
    public ResponseEntity<?> makePallet(@RequestParam ProductEntity product,
                                        @RequestParam int count_product,
                                        @RequestParam double height) {


        ContainerEntity pallet = new ContainerEntity();
        pallet.setAmount(count_product);
        pallet.setProduct(product);
        pallet.setTypeContainer(BaseType.TypeContainerProduct.pallet);
        pallet.setHeight(height);
//        pallet.setHeight(height + standard_height);
        pallet.setWeight(product.getWeight() * count_product);
        pallet.setLifeCycle(BaseType.LifeCycle.receipt);

        pallet.setLength(standard_length);
        pallet.setWidth(standard_width);
        //Распределение палета
        containerRepository.save(pallet);

        productRepository.updateById(product.getId(), product.getCount_on_shipping(),
                product.getCount_on_warehouse() + count_product);

        return ResponseEntity.ok("Паллет добавлен в базу");
    }

//    private boolean distribution(ContainerEntity container, long product_id) {
//        List<CellEntity> stillages = stillageRepository.findAll();
//
//        for (CellEntity stillage : stillages) {
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
//        for (CellEntity stillage : stillages) {
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
        stillageRepository.updateCountObject(id_stillage, stillageRepository.getOne(id_stillage).getCount_object() + 1);
        containerRepository.updateLifeCyrcleAndStillageById(id_container, BaseType.LifeCycle.distribution, id_stillage);
        return ResponseEntity.ok("Палету присвоен стилаж");
    }



    @ApiOperation("генерировать qr code")
    @PostMapping("/generationQRCode")
    public ResponseEntity<?> generationQRCode(String s) {
        return ResponseEntity.ok(QRCodeHelper.getQRCodeImage(s, 200, 200));
    }


    @ApiOperation("раскодировать qr code")
    @PostMapping("/decodeQRCode")
    public ResponseEntity<?> decodeQRCode(@RequestParam MultipartFile file) {
        try {
            return ResponseEntity.ok(QRCodeHelper.decodeQRCode(file));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(e.getMessage());
        }
    }
}