package ru.rodionov.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodionov.spring.DTO.ClientDTO;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.exceptions.UserNotFoundException;
import ru.rodionov.spring.model.Client;
import ru.rodionov.spring.model.User;
import ru.rodionov.spring.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserDTO> getAll(){
            return userRepository.findAll().stream()
                    .map(this::getUserDTO)
                    .collect(Collectors.toList());
    }
    public UserDTO getUserDTO(User user){
        return new UserDTO().setId(user.getId())
                .setName(user.getName())
                .setCreatorId(user.getCreatorId())
                .setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .setRole(user.getRole());
    }
    public UserDTO create(UserDTO userDTO){
        User user = new User().setCreatorId(userDTO.getCreatorId())
                .setName(userDTO.getName())
                .setLogin(userDTO.getLogin())
                .setPassword(userDTO.getPassword())
                .setRole(userDTO.getRole());
        userRepository.save(user);
        return userDTO.setId(user.getId());
    }
    public UserDTO getById(Long id){
        return userRepository.findById(id)
                .map(this::getUserDTO)
                .orElseThrow(()-> new UserNotFoundException("User not found " + id));
    }

    public UserDTO update(UserDTO newUser, Long id){
        return userRepository.findById(id)
                .map(user -> getUser(newUser, user))
                .orElseThrow(()-> new UserNotFoundException("User not found " + id));
    }
    private UserDTO getUser(UserDTO newUser, User user) {
        user.setName(user.getName());
        user.setCreatorId(user.getCreatorId());
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        user.setRole(user.getRole());
        userRepository.save(user);
        return newUser.setId(user.getId());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
