package ru.rodionov.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.service.UserService;

import java.util.List;

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

    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
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


}
