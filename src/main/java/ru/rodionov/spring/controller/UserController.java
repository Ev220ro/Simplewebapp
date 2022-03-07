package ru.rodionov.spring.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.service.UserService;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO, Authentication auth) {
        return userService.create(userDTO, auth.getName());
    }

    @GetMapping("{id}")
    public UserDTO one(@PathVariable Long id) {
        return userService.getById(id);
    }


    @PutMapping("{id}")
    public UserDTO replaceUser(@RequestBody UserDTO newUser, @PathVariable Long id) {
        return userService.update(newUser, id);
    }


    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    //put -> id + new User
    //delete -> id
    //clients + histor transaction
    //http://localhost:8080/swagger-ui/index.html

    //Добавить сервисы
    //git
    //отдельный проект авторизация - > логаут (одна сущность)
    //расписать ролевую модель (кто что может делать? добавить проверки ролевой модели)



}
