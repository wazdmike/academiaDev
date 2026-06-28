package io.github.wazdmike.application.usecases;

import io.github.wazdmike.application.repositories.CourseRepository;
import io.github.wazdmike.application.repositories.EnrollmentRepository;
import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.exceptions.BusinessException;
import io.github.wazdmike.domain.exceptions.EnrollmentException;

public class CancelEnrollmentUseCase {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CancelEnrollmentUseCase(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(Student student, String titleCourse) {
        Course course = courseRepository.findByTitle(titleCourse)
                .orElseThrow(() -> new BusinessException("Curso não encontrado: " + titleCourse));

        var enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new EnrollmentException("Você não está matriculado em \"" + course.getTitle() + "\"."));

        enrollmentRepository.delete(enrollment);
    }
}