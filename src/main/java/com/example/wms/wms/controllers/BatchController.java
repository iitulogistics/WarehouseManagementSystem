package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.BatchEntity;
import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.repositories.BatchRepository;
import com.example.wms.wms.repositories.ContainerRepository;
import com.example.wms.wms.repositories.ProductRepository;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Api(tags = {"Заказ"}, description = "API для заказа товара со складе")
@RestController
@RequestMapping(value = "batch")
public class BatchController {
    final ProductRepository productRepository;
    final ContainerRepository palletRepository;
    final BatchRepository batchRepository;

    public BatchController(ProductRepository productRepository, ContainerRepository palletRepository, BatchRepository batchRepository) {
        this.productRepository = productRepository;
        this.palletRepository = palletRepository;
        this.batchRepository = batchRepository;
    }

    @PostMapping("/addBatch")
    public ResponseEntity<?> addBatch(@RequestParam Long id_product,
                                      @RequestParam int count) {
        BatchEntity batchEntity = new BatchEntity();
        batchEntity.setProduct_count(count);
        batchRepository.save(batchEntity);

        ProductEntity productEntity = productRepository.getOne(id_product);

        List<ContainerEntity> list = palletRepository.getPalletsByProductId(id_product);
        list.sort(new Comparator<ContainerEntity>() {
            @Override
            public int compare(ContainerEntity o1, ContainerEntity o2) {
                return o2.getCount_product() - o1.getCount_product();
            }
        });

        for (ContainerEntity entity : list) {
            if (entity.getCount_product() <= count && entity.getBatch_id() == null) {
                count -= entity.getCount_product();

                int count_shipping = productEntity.getCount_on_shipping() + entity.getCount_product();
                int count_warehouse = productEntity.getCount_on_warehouse() - entity.getCount_product();
                productRepository.updateById(id_product,count_shipping,count_warehouse);
                palletRepository.updateById(entity.getId(), batchEntity.getId());
            }
        }
        return ResponseEntity.ok("Партия сформирована и готова к отправке " + batchEntity.getId());
    }
}
