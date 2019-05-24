package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.PalletEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.entities.StillageEntity;
import com.example.wms.wms.repositories.PalletRepository;
import com.example.wms.wms.repositories.ProductRepository;
import com.example.wms.wms.repositories.StillageRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Поступление товара"}, description = "API для Поступившего товара")
@RestController
@RequestMapping("receipt")
public class ReceiptProductController {

    final ProductRepository productRepository;
    final PalletRepository palletRepository;
    final StillageRepository stillageRepository;

    @Value("${standard.pallet.length}")
    double standard_length;
    @Value("${standard.pallet.weight}")
    double standard_weight;
    @Value("${standard.pallet.width}")
    double standard_width;


    @Autowired
    public ReceiptProductController(ProductRepository productRepository, PalletRepository palletRepository, StillageRepository stillageRepository) {
        this.productRepository = productRepository;
        this.palletRepository = palletRepository;
        this.stillageRepository = stillageRepository;
    }

    @PostMapping("/addOneProduct")
    public void addOneProduct(@RequestParam(name = "id_product") long id_product) {
        ProductEntity productEntity = productRepository.getOne(id_product);
        productEntity.setCount_on_warehouse(productEntity.getCount_on_warehouse() + 1);
    }

    @PostMapping("/addPallet")
    public void addPallet(@RequestParam long id_product,
                          @RequestParam int count_product,
                          @RequestParam double height
    ) {
        ProductEntity productEntity = productRepository.getOne(id_product);
        productEntity.setCount_on_warehouse(productEntity.getCount_on_warehouse() + count_product);

        makePallet(id_product, count_product, height);
    }


    @PostMapping("/makePallet")
    public void makePallet(@RequestParam long id_product,
                           @RequestParam int count_product,
                           @RequestParam double height) {
        PalletEntity pallet = new PalletEntity();
        pallet.setHeight(height);
        pallet.setCount_product(count_product);
        pallet.setProduct_id(id_product);

        //Стандартный размер палеты 1000мм на 1200мм
        pallet.setLength(standard_length);
        pallet.setWidth(standard_width);

        List<StillageEntity> stillages = stillageRepository.findAll();
        for (StillageEntity stillage : stillages) {
            List<PalletEntity> palletEntities = palletRepository.getPalletsByStillageId(stillage.getId());

            if(palletEntities.isEmpty()){
                pallet.setStillageId(stillage.getId());
                palletRepository.save(pallet);
                return;
            }
            //if (palletEntities.size() < stillage.getMax_count_pallet()) {
            for (PalletEntity palletEntity : palletEntities) {
                if (palletEntity.getProduct_id() == id_product) {
                    pallet.setStillageId(stillage.getId());
                    palletRepository.save(pallet);
                    return;
                }
            }
            //}
        }

        for (StillageEntity stillage : stillages) {
            List<PalletEntity> palletEntities = palletRepository.getPalletsByStillageId(stillage.getId());

            //if (palletEntities.size() < stillage.getMax_count_pallet()) {
                pallet.setStillageId(stillage.getId());
                palletRepository.save(pallet);
                return;
            //}
        }
    }
}
