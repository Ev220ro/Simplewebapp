package ru.rodionov.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodionov.spring.DTO.ActionDTO;
import ru.rodionov.spring.exceptions.ActionNotFoundException;
import ru.rodionov.spring.model.Action;
import ru.rodionov.spring.model.User;
import ru.rodionov.spring.repository.ActionRepository;
import ru.rodionov.spring.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ActionService {
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository, UserRepository userRepository) {
        this.actionRepository = actionRepository;
        this.userRepository = userRepository;
    }

    public List<ActionDTO> getAll(String login) {
        return actionRepository.findAllByUser_Login(login)
                .stream().map(this::getActionDTO)
                .collect(Collectors.toList());
    }

    public ActionDTO create(ActionDTO actionDTO, String login) {
        User user = userRepository.findByLogin(login).get();
        Action action = new Action()
                .setDateTime(actionDTO.getDateTime())
                .setDetails(actionDTO.getDetails())
                .setType(actionDTO.getType())
                .setUser(user);
        actionRepository.save(action);
        return actionDTO.setId(action.getId());
    }

    public ActionDTO getById(Long id) {
        return actionRepository.findById(id)
                .map(this::getActionDTO)
                .orElseThrow(() -> new ActionNotFoundException("Not found " + id));
    }


    public ActionDTO updateAction(ActionDTO newActionDTO, Long id, String login) {
        return actionRepository.findByIdAndUser_Login(id, login)
                .map(action -> getAction(newActionDTO, action))
                .orElseThrow(() -> new ActionNotFoundException("Not found " + id));
    }

    public ActionDTO getAction(ActionDTO newAction, Action action) {
        action.setDetails(newAction.getDetails());
        action.setType(newAction.getType());
        action.setDateTime(newAction.getDateTime());
        actionRepository.save(action);
        return newAction.setId(action.getId());
    }


    public ActionDTO getActionDTO(Action action) {
        return new ActionDTO().setId(action.getId())
                .setDateTime(action.getDateTime())
                .setDetails(action.getDetails())
                .setType(action.getType());
    }
    @Transactional
    public void deleteAction(Long id){
        actionRepository.deleteById(id);
    }
}
