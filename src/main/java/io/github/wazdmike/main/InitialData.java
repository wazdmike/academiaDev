package io.github.wazdmike.main;

import io.github.wazdmike.application.repositories.CourseRepository;
import io.github.wazdmike.application.repositories.UserRepository;
import io.github.wazdmike.domain.entities.Admin;
import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.entities.User;
import io.github.wazdmike.domain.enums.DifficultyLevel;

import java.util.List;

public class InitialData {

    public static void populate(CourseRepository courseRepo, UserRepository userRepo) {

        List<Course> courses = List.of(
                new Course("C001", "Java Essencial com Projetos Reais",
                        "Domine Java do básico ao avançado com projetos reais.",
                        "Prof. Gabriel Martins", 80, DifficultyLevel.BEGINNER),

                new Course("C002", "APIs com Spring Boot e Clean Architecture",
                        "Construa APIs RESTful seguindo os princípios da Clean Architecture.",
                        "Prof. Juliana Almeida", 60, DifficultyLevel.INTERMEDIATE),

                new Course("C003", "Microserviços com Docker & Kubernetes na Prática",
                        "Orquestre microserviços em produção com containers.",
                        "Prof. Roberto Lima", 100, DifficultyLevel.ADVANCED),

                new Course("C004", "Estruturas de Dados e Algoritmos para Entrevistas",
                        "Fundamentos essenciais para entrevistas técnicas.",
                        "Prof. Beatriz Souza", 40, DifficultyLevel.INTERMEDIATE),

                new Course("C005", "Python Básico para Iniciantes",
                        "Primeiros passos com programação usando Python.",
                        "Prof. Lucas Ferreira", 30, DifficultyLevel.BEGINNER),

                new Course("C006", "Machine Learning Aplicado",
                        "Modelos preditivos com scikit-learn e TensorFlow.",
                        "Prof. Camila Rocha", 120, DifficultyLevel.ADVANCED),

                new Course("C007", "Manutenção de Sistemas Legados com jQuery",
                        "Manutenção de sistemas legados com jQuery.",
                        "Prof. Eduardo Santos", 20, DifficultyLevel.BEGINNER)
        );

        courses.getLast().deactivate();

        courses.forEach(courseRepo::save);

        List<User> users = List.of(
                new Admin("U001", "Admin Master", "admin@academiadev.com"),

                new Student("U002", "Miguel Souza", "miguel@email.com", "BASIC"),
                new Student("U003", "Andrea Menezes", "andrea@email.com", "PREMIUM"),
                new Student("U004", "Joao Pedro", "joaop@email.com", "PREMIUM")
        );

        users.forEach(userRepo::save);

        System.out.println("[InitialData] " +
                courses.size() + " cursos e " +
                users.size() + " usuários carregados.");
    }
}