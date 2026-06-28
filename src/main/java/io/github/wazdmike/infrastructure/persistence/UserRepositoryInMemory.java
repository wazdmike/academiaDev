package io.github.wazdmike.infrastructure.persistence;

import io.github.wazdmike.application.repositories.UserRepository;
import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.entities.User;
import io.github.wazdmike.domain.exceptions.BusinessException;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepositoryInMemory implements UserRepository {
    private final Map<String, User> storage = new LinkedHashMap<>();

    @Override
    public void save(User user) {
        if (storage.containsKey(user.getEmail()) &&
                !storage.get(user.getEmail()).getId().equals(user.getId())) {
            throw new BusinessException("Já existe um usuário com o e-mail \"" + user.getEmail() + "\".");
        }
        storage.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(storage.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Student> findAllStudents() {
        return storage
                .values()
                .stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());
    }
}
