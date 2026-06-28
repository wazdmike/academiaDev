package io.github.wazdmike.application.repositories;

import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.entities.Enrollment;
import io.github.wazdmike.domain.entities.Student;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    void delete(Enrollment enrollment);
    int countActiveByStudent(Student student);
    List<Enrollment> findAll();
    List<Enrollment> findByStudent(Student student);
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
}
