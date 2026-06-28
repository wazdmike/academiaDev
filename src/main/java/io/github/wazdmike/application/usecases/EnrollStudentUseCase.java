package io.github.wazdmike.application.usecases;

import io.github.wazdmike.application.repositories.CourseRepository;
import io.github.wazdmike.application.repositories.EnrollmentRepository;
import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.entities.Enrollment;
import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.exceptions.BusinessException;
import io.github.wazdmike.domain.exceptions.EnrollmentException;

import java.util.UUID;

public class EnrollStudentUseCase {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollStudentUseCase(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository){
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment execute(Student student, String titleCourse) {
        Course course = courseRepository.findByTitle(titleCourse)
                .orElseThrow(() -> new BusinessException("Curso não encontrado: " + titleCourse));

        if (!course.isActive()) {
            throw new EnrollmentException("O curso \"" + course.getTitle() + "\" está INATIVO e não aceita matrículas.");
        }

        enrollmentRepository.findByStudentAndCourse(student, course).ifPresent(e -> {
            throw new EnrollmentException("Você já está matriculado em \"" + course.getTitle() + "\".");
        });

        int active = enrollmentRepository.countActiveByStudent(student);
        if (!student.canEnroll(active)) {
            throw new EnrollmentException("Limite de matrículas atingido para o plano " +
                            student.getSubscriptionPlan() + ". Atualize para o plano PREMIUM.");
        }

        Enrollment enrollment = new Enrollment(UUID.randomUUID().toString(), student, course);
        enrollmentRepository.save(enrollment);
        return enrollment;
    }
}
