package ru.rodionov.spring.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.enums.UserRole;
import ru.rodionov.spring.model.User;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping()
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO, Authentication auth) {
        return userService.create(userDTO, auth.getName());
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @PostMapping("/createWorker")
    public UserDTO createWorker(@RequestBody UserDTO userDTO, Authentication auth) {
        return userService.create(userDTO, auth.getName());
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @GetMapping("/getAllMy")
    public List<UserDTO> getAllMyEntity(Authentication auth) {
        return userService.getAllMy(auth.getName());
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @GetMapping("/getById/{id}")
    public UserDTO oneOfMy(@PathVariable Long id, Authentication auth) {
        return userService.getMineById(auth.getName(), id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @DeleteMapping("/deleteMyUser/{id}")
    public void deleteMyUser(@PathVariable Long id, Authentication auth) {
        userService.deleteMyUser(auth.getName(), id); //add exception
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    public UserDTO one(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public UserDTO replaceUser(@RequestBody UserDTO newUser, @PathVariable Long id) {
        return userService.update(newUser, id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @PutMapping("/updateMyUser/{id}")
    public UserDTO replaceMyUser(@RequestBody UserDTO newUser, @PathVariable Long id, Authentication auth){
        return userService.updateMy(newUser, id, auth.getName());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    //put -> id + new User
    //delete -> id
    //clients + histor transaction
    //http://localhost:8080/swagger-ui/index.html

    //Добавить сервисы
    //отдельный проект авторизация - > логаут (одна сущность)
    //расписать ролевую модель (кто что может делать? добавить проверки ролевой модели)


}
