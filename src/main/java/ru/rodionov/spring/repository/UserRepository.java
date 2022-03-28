package ru.rodionov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.rodionov.spring.DTO.UserDTO;
import ru.rodionov.spring.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByLogin(String login);
        List<User> findByCreatorId(Long creatorId);
        Optional<User> findByCreatorIdAndId(Long creatorId, Long id);
        @Modifying
        @Query("DELETE FROM User u WHERE u.creatorId = ?1 and u.id = ?2")
        void deleteUserByCreatorIdAndId(Long creatorId, Long id);
}
