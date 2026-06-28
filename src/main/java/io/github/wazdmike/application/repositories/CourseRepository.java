package io.github.wazdmike.application.repositories;

import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.enums.DifficultyLevel;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void save(Course course);
    List<Course> findAll();
    List<Course> findByDifficulty(DifficultyLevel difficultyLevel);
    Optional<Course> findByTitle(String title);
}
