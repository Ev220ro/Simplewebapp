package ru.rodionov.spring.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rodionov.spring.DTO.ActionDTO;
import ru.rodionov.spring.service.ActionService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/action")
@RestController
public class ActionController {

    private final ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public List<ActionDTO> getAll(Authentication auth) {
        return actionService.getAll(auth.getName());
    }

    @GetMapping("{id}")
    public ActionDTO one(@PathVariable Long id) {
        return actionService.getById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        actionService.deleteAction(id);
    }

    @PostMapping
    public ActionDTO create(@RequestBody ActionDTO actionDTO, Authentication auth){
        return actionService.create(actionDTO, auth.getName());
    }

    @PutMapping("{id}")
    public ActionDTO replaceAction(@RequestBody ActionDTO actionDTO, @PathVariable Long id, Authentication auth){
        return actionService.updateAction(actionDTO, id, auth.getName());
    }

}
