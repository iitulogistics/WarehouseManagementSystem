package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.OrderEntity;
import com.example.wms.wms.entities.ProductEntity;
import com.example.wms.wms.entities.User;
import com.example.wms.wms.repositories.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Api(tags = {"Заказ"}, description = "API для заказа товара со склада")
@RestController
@RequestMapping(value = "/batch")
public class OrderController {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final TaskRepository taskRepository;

    public OrderController(ProductRepository productRepository,
                           OrderRepository batchRepository,
                           TaskRepository taskRepository) {
        this.productRepository = productRepository;
        this.orderRepository = batchRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("")
    public ModelAndView main(@AuthenticationPrincipal User user) {
        Map<String, Object> root = new TreeMap<>();

        root.put("products", productRepository.findAll());
        List<OrderEntity> orderEntities = orderRepository.findAll();


        root.put("batches", orderEntities);
        root.put("user", user);
        return new ModelAndView("batch", root);
    }

    @PostMapping("")
    public ModelAndView main(@AuthenticationPrincipal User user,
                             @RequestParam(name = "company_name") Long order_number) {
        Map<String, Object> root = new TreeMap<>();

        root.put("products", productRepository.findAll());

        root.put("batches", orderRepository.getBatchByOrderNumber(order_number));
        root.put("user", user);
        return new ModelAndView("batch", root);
    }

    @ApiOperation("Создать batch")
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestParam ProductEntity product,
                                      @RequestParam int count,
                                      @RequestParam String address) {
        if (product.getCount_on_warehouse() < count) {
            return ResponseEntity.ok("Недостаточно товара на складе");
        }

        OrderEntity order = new OrderEntity();
        order.setProduct(product);
        order.setDate(new Date());
        order.setAmount(count);
        order.setAddress(address);
        orderRepository.save(order);

        productRepository.updateById(product.getId(), product.getCount_on_shipping() + count,
                product.getCount_on_warehouse() - count);

        return ResponseEntity.ok("Партия сформирована и готова к отправке по адресу " + address);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderRepository.findAll());
    }
}