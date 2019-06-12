package com.example.wms.wms.controllers;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.entities.StillageEntity;
import com.example.wms.wms.repositories.ContainerRepository;
import com.example.wms.wms.repositories.ProductRepository;
import com.example.wms.wms.repositories.StillageRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Поступление товара"}, description = "API для Поступившего товара")
@RestController
@RequestMapping("receipt")
public class ReceiptProductController {

    final ProductRepository productRepository;
    final ContainerRepository palletRepository;
    final StillageRepository stillageRepository;

    @Value("${standard.pallet.length}")
    double standard_length;
    @Value("${standard.pallet.weight}")
    double standard_weight;
    @Value("${standard.pallet.width}")
    double standard_width;
    @Value("${standard.pallet.height}")
    double standard_height;


    @Autowired
    public ReceiptProductController(ProductRepository productRepository, ContainerRepository palletRepository, StillageRepository stillageRepository) {
        this.productRepository = productRepository;
        this.palletRepository = palletRepository;
        this.stillageRepository = stillageRepository;
    }

    @PostMapping("/addOneProductForPallet")
    public void addOneProductForPallet(@RequestParam(name = "id_product") long id_product) {
        ProductEntity productEntity = productRepository.getOne(id_product);
        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
                productEntity.getCount_on_warehouse() + 1);
    }

    @PostMapping("/addBox")
    public void addBox(@RequestParam(name = "id_product") long id_product,
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
//        box.setTypeContainer(BaseType.TypeContainerProduct.box);

        distribution(box, id_product);

        productRepository.updateById(id_product, productEntity.getCount_on_shipping(),
                productEntity.getCount_on_warehouse() + count);
    }

    @PostMapping("/addPallet")
    public void addPallet(@RequestParam long id_product,
                          @RequestParam int count_product,
                          @RequestParam double height) {
        ProductEntity productEntity = productRepository.getOne(id_product);
        productEntity.setCount_on_warehouse(productEntity.getCount_on_warehouse() + count_product);

        makePallet(id_product, count_product, height);
    }


    @PostMapping("/makePallet")
    public void makePallet(@RequestParam long id_product,
                           @RequestParam int count_product,
                           @RequestParam double height) {
        ProductEntity productEntity = productRepository.getOne(id_product);

        ContainerEntity pallet = new ContainerEntity();
        pallet.setCount_product(count_product);
        pallet.setProduct_id(id_product);
//        pallet.setTypeContainer(BaseType.TypeContainerProduct.pallet);
        pallet.setHeight(height + standard_height);
        pallet.setWeight(productEntity.getWeight() * count_product);

        //Стандартный размер палеты 1000мм на 1200мм
        pallet.setLength(standard_length);
        pallet.setWidth(standard_width);
        //Распределение палета
        distribution(pallet, id_product);
    }

    private void distribution(ContainerEntity container, long product_id) {
        List<StillageEntity> stillages = stillageRepository.findAll();

        for (StillageEntity stillage : stillages) {
            List<ContainerEntity> palletEntities = palletRepository.getPalletsByStillageId(stillage.getId());

            if (palletEntities.size() < stillage.getMax_count_object() && !palletEntities.isEmpty()) {
                if (palletEntities.get(0).getProduct_id() == product_id) {
                    container.setStillageId(stillage.getId());
                    palletRepository.save(container);
                    return;
                }
            }
        }

        for (StillageEntity stillage : stillages) {
            List<ContainerEntity> palletEntities = palletRepository.getPalletsByStillageId(stillage.getId());

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

                    container.setStillageId(stillage.getId());
                    palletRepository.save(container);
                    return;
                }
            }
        }
    }
}
