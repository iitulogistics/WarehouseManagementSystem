package com.example.wms.wms.controllers;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ContainerEntity;
import com.example.wms.wms.entities.StillageEntity;
import com.example.wms.wms.entities.TaskEntity;
import com.example.wms.wms.repositories.ContainerRepository;
import com.example.wms.wms.repositories.StillageRepository;
import com.example.wms.wms.repositories.TaskRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = {"Хранение"}, description = "API контроля товара на складе")
@RequestMapping("/store")
@RestController
public class StoreProductsController {
    private final ContainerRepository containerRepository;
    private final StillageRepository stillageRepository;
    private final TaskRepository taskRepository;

    public StoreProductsController(ContainerRepository containerRepository,
                                   StillageRepository stillageRepository,
                                   TaskRepository taskRepository) {
        this.containerRepository = containerRepository;
        this.stillageRepository = stillageRepository;
        this.taskRepository = taskRepository;
    }

    @ApiOperation("Вывод списка товаров находяшихся на этапе распределения")
    @GetMapping("/getListContOnDist")
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

        if (container.getStillageId().equals(id_stillage)) {
            containerRepository.updateLifeCyrcleById(id_container, BaseType.LifeCycle.storage);
            return ResponseEntity.ok("Товар на полке.");
        } else {
            return ResponseEntity.ok("Эта полка не для этого товара");
        }
    }

    @ApiOperation("Оптимизация товара на складе")
    @PostMapping("/optimization")
    public ResponseEntity<?> makeOptimization() {
        StringBuilder info = new StringBuilder();
        List<StillageEntity> stillageEntities = stillageRepository.findAll();

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCreated(new Date());
        taskEntity.setPriority(BaseType.PriorityOfExecution.midle);

        for (int i = 0; i < stillageEntities.size() - 1; i++) {
            List<ContainerEntity> containerEntities = containerRepository
                    .getContainersByStillageId(stillageEntities.get(i).getId());

            if (containerEntities.size() != stillageEntities.get(i).getMax_count_object() && !containerEntities.isEmpty()) {
                for (int j = i + 1; j < stillageEntities.size(); j++) {
                    List<ContainerEntity> containerEntities2 = containerRepository
                            .getContainersByStillageId(stillageEntities.get(j).getId());

                    if (containerEntities2.isEmpty()) continue;
                    if (!containerEntities2.get(0).getProduct_id().equals(containerEntities.get(0).getProduct_id())
                            || !containerEntities2.get(0).getTypeContainer().equals(containerEntities.get(0).getTypeContainer()))
                        continue;

                    int dif = stillageEntities.get(i).getMax_count_object() -
                            (containerEntities.size() + containerEntities2.size());

                    if (dif >= 0) {
                        if (containerEntities.size() >= containerEntities2.size()) {
                            for (ContainerEntity containerEntity : containerEntities2) {

                                taskEntity.setTask(taskEntity.getTask() + "Контейнер с id = " +
                                        containerEntity.getId() + "Переместить из стеллажа с номером " + stillageEntities.get(j).getStillage_index() +
                                        " и ячейки " + stillageEntities.get(j).getShelf_index() +
                                        " в стеллаж " + stillageEntities.get(i).getStillage_index() +
                                        " и ячейку " + stillageEntities.get(i).getShelf_index() + "\n");

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
                                        stillageEntities.get(i).getStillage_index() +
                                        " и ячейки " + stillageEntities.get(i).getShelf_index() +
                                        " в стеллаж " + stillageEntities.get(j).getStillage_index() +
                                        " и ячейку " + stillageEntities.get(j).getShelf_index() + "\n");

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
}

