package io.github.wazdmike.application.usecases;

import io.github.wazdmike.application.repositories.CourseRepository;
import io.github.wazdmike.application.repositories.EnrollmentRepository;
import io.github.wazdmike.application.repositories.UserRepository;
import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.entities.Enrollment;
import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.enums.CourseStatus;
import io.github.wazdmike.domain.enums.DifficultyLevel;

import java.util.*;
import java.util.stream.Collectors;

public class ReportUseCase {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public ReportUseCase(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public List<Course> coursesByLevel(DifficultyLevel level) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getDifficultyLevel() == level)
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public Set<String> activeInstructors() {
        return courseRepository.findAll().stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }

    public Map<String, List<Student>> studentsByPlan() {
        return userRepository.findAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getSubscriptionPlan));
    }

    public double averageProgress() {
        OptionalDouble avg = enrollmentRepository.findAll().stream()
                .mapToInt(Enrollment::getProgress)
                .average();
        return avg.orElse(0.0);
    }

    public Optional<Student> studentWithMostEnrollments() {
        return userRepository.findAllStudents().stream()
                .max(Comparator.comparingInt(
                        enrollmentRepository::countActiveByStudent));
    }

    public List<Student> allStudents() {
        return userRepository.findAllStudents();
    }

    public List<Enrollment> allEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Course> allCourses() {
        return courseRepository.findAll();
    }
}
