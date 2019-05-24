package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.BatchEntity;
import com.example.wms.wms.entities.PalletEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.repositories.BatchRepository;
import com.example.wms.wms.repositories.PalletRepository;
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
    final PalletRepository palletRepository;
    final BatchRepository batchRepository;

    public BatchController(ProductRepository productRepository, PalletRepository palletRepository, BatchRepository batchRepository) {
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

        List<PalletEntity> list = palletRepository.getPalletsByProductId(id_product);
        list.sort(new Comparator<PalletEntity>() {
            @Override
            public int compare(PalletEntity o1, PalletEntity o2) {
                return o2.getCount_product() - o1.getCount_product();
            }
        });

        for (PalletEntity entity : list) {
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
