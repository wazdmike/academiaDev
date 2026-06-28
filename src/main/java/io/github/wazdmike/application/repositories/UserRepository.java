package io.github.wazdmike.application.repositories;

import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    List<User> findAll();
    List<Student> findAllStudents();
    Optional<User> findByEmail(String email);
}
