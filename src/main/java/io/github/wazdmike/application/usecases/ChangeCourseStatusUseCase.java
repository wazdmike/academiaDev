package io.github.wazdmike.application.usecases;

import io.github.wazdmike.application.repositories.CourseRepository;
import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.exceptions.BusinessException;

public class ChangeCourseStatusUseCase {
    private final CourseRepository courseRepository;

    public ChangeCourseStatusUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course activate(String titleCourse){
        Course course = courseRepository.findByTitle(titleCourse)
                .orElseThrow(() -> new BusinessException("Curso não encontrado: " + titleCourse));
        course.activate();
        return course;
    }

    public Course deactivate(String titleCourse) {
        Course course = courseRepository.findByTitle(titleCourse)
                .orElseThrow(() -> new BusinessException("Curso não encontrado: " + titleCourse));
        course.deactivate();
        return course;
    }
}
