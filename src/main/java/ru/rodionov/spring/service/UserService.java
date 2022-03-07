package ru.rodionov.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.exceptions.UserNotFoundException;
import ru.rodionov.spring.model.User;
import ru.rodionov.spring.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAll(){
            return userRepository.findAll().stream()
                    .map(this::getUserDTO)
                    .collect(Collectors.toList());
    }

    public UserDTO getUserDTO(User user){
        return new UserDTO().setId(user.getId())
                .setName(user.getName())
//                .setCreatorId(user.getCreatorId())
                .setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .setRole(user.getRole());
    }

    public UserDTO create(UserDTO userDTO, String login){
        User creator = userRepository.findByLogin(login).get();

        User user = new User()
                .setCreatorId(creator.getId())
                .setName(userDTO.getName())
                .setLogin(userDTO.getLogin())
                .setPassword(passwordEncoder.encode(userDTO.getPassword()))
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
                .map(user -> updateUser(newUser, user))
                .orElseThrow(()-> new UserNotFoundException("User not found " + id));
    }

    private UserDTO updateUser(UserDTO newUser, User user) {
        user.setName(newUser.getName());
        user.setLogin(newUser.getLogin());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(newUser.getRole());
        userRepository.save(user);
        return newUser.setId(newUser.getId());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
