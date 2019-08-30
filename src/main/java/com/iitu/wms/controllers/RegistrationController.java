package com.iitu.wms.controllers;

import com.iitu.wms.entities.Role;
import com.iitu.wms.entities.User;
import com.iitu.wms.repositories.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("isUserPresent")
    public ResponseEntity<?> isUserPresent(@RequestParam String username,
                                           @RequestParam String password) {
        User user = userRepo.findByUsernameAndPassword(username, password).orElse(null);
        if (user == null) return ResponseEntity.ok("false");
        return ResponseEntity.ok("true");
    }
}
