package com.iitu.wms.controllers;

import com.iitu.wms.base.BaseType;
import com.iitu.wms.entities.*;
import com.iitu.wms.repositories.ContainerRepository;
import com.iitu.wms.repositories.ProductRepository;
import com.iitu.wms.repositories.CellRepository;
import com.iitu.wms.repositories.TaskRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.*;
import java.util.List;

@Api(tags = {"Хранение"}, description = "API контроля товара на складе")
@RequestMapping("/store")
@RestController
public class StoreProductsController {
    private final ContainerRepository containerRepository;
    private final CellRepository cellRepository;
    private final TaskRepository taskRepository;
    private final ProductRepository productRepository;

    public StoreProductsController(ContainerRepository containerRepository,
                                   CellRepository stillageRepository,
                                   TaskRepository taskRepository,
                                   ProductRepository productRepository) {
        this.containerRepository = containerRepository;
        this.cellRepository = stillageRepository;
        this.taskRepository = taskRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public ModelAndView main(@AuthenticationPrincipal User user) {
        Map<String, Object> root = new TreeMap<>();
        List<ContainerEntity> containerEntities = getListContOnDist();
        root.put("count_containers", containerEntities.size());

        List<Object[]> containersInfo = new ArrayList<>();

        for (ContainerEntity container : containerEntities) {
            containersInfo.add(new Object[]{container.getId(),
                    container.getProduct().getProduct_name() + " " + container.getAmount()
                            + " шт., размеры " + container.getWidth() + "/" + container.getHeight() +
                            "/" + container.getLength() + " " + container.getWeight() + " кг."});
        }

        root.put("containers", containersInfo);

        List<Object[]> stillagesInfo = new ArrayList<>();

        for (CellEntity stillageEntity : cellRepository.getLooseStillage()) {
            stillagesInfo.add(new Object[]{stillageEntity.getId(), "Номер стеллажа " + stillageEntity.getStillage() +
                    " , номер ячейки " + stillageEntity.getShelf() +
                    " ,размеры ячейки " + stillageEntity.getWidth() + "/" + stillageEntity.getHeight() + "/" + stillageEntity.getLength() +
                    " ,максимальный вес " + stillageEntity.getMax_weight()});
        }

        root.put("stillages", stillagesInfo);
        root.put("user", user);

        return new ModelAndView("store", root);
    }

    @ApiOperation("Вывод списка товаров находяшихся на этапе распределения")
    @PostMapping("/getListContOnDist")
    public List<ContainerEntity> getListContOnDist() {
        return containerRepository.getContainerByLifeCycle(BaseType.LifeCycle.distribution);
    }

    @ApiOperation("Вывод списка товаров находяшихся на этапе хранения")
    @PostMapping("/getListContOnStorage")
    public List<ContainerEntity> getListContOnStore() {
        return containerRepository.getContainerByLifeCycle(BaseType.LifeCycle.storage);
    }

    @ApiOperation("Поставить на полку")
    @PostMapping("/putOnShelf")
    public ResponseEntity<?> putOnShelf(@RequestParam Long id_container,
                                        @RequestParam Long id_stillage) {
        ContainerEntity container = containerRepository.getOne(id_container);

        if (container.getCellId().equals(id_stillage)) {
            containerRepository.updateLifeCyrcleById(id_container, BaseType.LifeCycle.storage);
            return ResponseEntity.ok("Товар на полке.");
        } else {
            return ResponseEntity.ok("Эта полка не для этого товара");
        }
    }

    @ApiOperation("Поставить на полку")
    @PostMapping("/putOnShelfByIndexes")
    public ResponseEntity<?> putOnShelfByIndexes(@RequestParam Long id_container,
                                                 @RequestParam int stillage_index,
                                                 @RequestParam int shelf_index,
                                                 @RequestParam int cell_index) {
        return putOnShelf(id_container, cellRepository.getCellByIndexes(stillage_index, shelf_index, cell_index).getId());
    }

    @ApiOperation("Оптимизация товара на складе")
    @PostMapping("/optimization")
    public ResponseEntity<?> makeOptimization() {
        StringBuilder info = new StringBuilder();
        List<CellEntity> stillageEntities = cellRepository.findAll();

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCreated(new Date());
        taskEntity.setPriority(BaseType.PriorityOfExecution.midle);

        for (int i = 0; i < stillageEntities.size() - 1; i++) {
            List<ContainerEntity> containerEntities = containerRepository
                    .getContainersByCellId(stillageEntities.get(i).getId());

            if (containerEntities.size() != stillageEntities.get(i).getMax_count_object() && !containerEntities.isEmpty()) {
                for (int j = i + 1; j < stillageEntities.size(); j++) {
                    List<ContainerEntity> containerEntities2 = containerRepository
                            .getContainersByCellId(stillageEntities.get(j).getId());

                    if (containerEntities2.isEmpty()) continue;
                    if (containerEntities2.get(0).getProduct() != (containerEntities.get(0).getProduct())
                            || !containerEntities2.get(0).getTypeContainer().equals(containerEntities.get(0).getTypeContainer()))
                        continue;

                    int dif = stillageEntities.get(i).getMax_count_object() -
                            (containerEntities.size() + containerEntities2.size());

                    if (dif >= 0) {
                        if (containerEntities.size() >= containerEntities2.size()) {
                            for (ContainerEntity containerEntity : containerEntities2) {

                                taskEntity.setTask(taskEntity.getTask() + "Контейнер с id = " +
                                        containerEntity.getId() + "Переместить из стеллажа с номером " + stillageEntities.get(j).getStillage() +
                                        " и ячейки " + stillageEntities.get(j).getShelf() +
                                        " в стеллаж " + stillageEntities.get(i).getStillage() +
                                        " и ячейку " + stillageEntities.get(i).getShelf() + "\n");

                                containerRepository.updateLifeCyrcleAndStillageById(containerEntity.getId(),
                                        BaseType.LifeCycle.distribution, stillageEntities.get(i).getId());
                                info.append("Контейнер с id = ").append(containerEntity.getId()).append(" был перемещен\n");
                            }
                        } else {
                            for (ContainerEntity containerEntity : containerEntities) {
                                containerRepository.updateLifeCyrcleAndStillageById(containerEntity.getId(),
                                        BaseType.LifeCycle.distribution, stillageEntities.get(j).getId());

                                taskEntity.setTask(taskEntity.getTask() + "Контейнер с id = " +
                                        containerEntity.getId() + "Переместить из стеллажа с номером " +
                                        stillageEntities.get(i).getStillage() +
                                        " и ячейки " + stillageEntities.get(i).getShelf() +
                                        " в стеллаж " + stillageEntities.get(j).getStillage() +
                                        " и ячейку " + stillageEntities.get(j).getShelf() + "\n");

                                info.append("Контейнер с id = ").append(containerEntity.getId()).append(" был перемещен\n");
                            }
                        }
                    }
                }

            }
        }
        if (info.length() == 0) {
            return ResponseEntity.ok("Склад уже оптимизирован");
        } else {
            taskRepository.save(taskEntity);
            return ResponseEntity.ok(info);
        }
    }

    @PostMapping("/inventory")
    public ResponseEntity<?> makeInventory() {
        return ResponseEntity.ok("Функция еще не реализована");
    }

    @PostMapping("/getCellBalance")
    public ResponseEntity<?> getCellBalance(@RequestParam String bar_code) {
        return ResponseEntity.ok(containerRepository.getContainersByCellId(cellRepository.getCellByBarCode(bar_code).getId()));
    }

    @PostMapping("/getCellProduct")
    public ResponseEntity<?> getCellProduct(@RequestParam String bar_code) {
        return ResponseEntity.ok(productRepository.getProductByBarCode(bar_code));
    }

    @PostMapping()
    public ResponseEntity<?> findCellsByProduct(@RequestParam String bar_code) {
        ProductEntity productEntity = productRepository.getProductByBarCode(bar_code);
        List<ContainerEntity> containerEntities = containerRepository.getContainersByProduct(productEntity);
        return ResponseEntity.ok(containerEntities);
    }
}

