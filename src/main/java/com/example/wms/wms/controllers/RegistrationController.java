package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.Role;
import com.example.wms.wms.entities.User;
import com.example.wms.wms.repositories.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/registration")
@Api(tags = {"Регистрация"}, description = "API для регистрации работника")
public class RegistrationController {
    private final UserRepository userRepo;

    public RegistrationController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("")
    public ModelAndView registration() {
        return new ModelAndView("registration");
    }

    @PostMapping("")
    public ModelAndView addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return new ModelAndView("registration");
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return new ModelAndView("redirect:/login");
    }
}
