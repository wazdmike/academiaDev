package io.github.wazdmike.infrastructure.persistence;

import io.github.wazdmike.application.repositories.CourseRepository;
import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.enums.DifficultyLevel;
import io.github.wazdmike.domain.exceptions.BusinessException;

import java.util.*;
import java.util.stream.Collectors;

public class CourseRepositoryInMemory implements CourseRepository {
    private final Map<String, Course> storage = new LinkedHashMap<>();

    @Override
    public void save(Course course) {
        if (storage.containsKey(course.getTitle()) &&
                !storage.get(course.getTitle()).getId().equals(course.getId())) {
            throw new BusinessException("Já existe um curso com o título \"" + course.getTitle() + "\".");
        }
        storage.put(course.getTitle(), course);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(storage.get(title));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Course> findByDifficulty(DifficultyLevel level) {
        return storage
                .values()
                .stream()
                .filter(c -> c.getDifficultyLevel() == level)
                .collect(Collectors.toList());
    }
}