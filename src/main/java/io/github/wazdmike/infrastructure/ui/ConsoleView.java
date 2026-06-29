package io.github.wazdmike.infrastructure.ui;

import io.github.wazdmike.domain.entities.Course;
import io.github.wazdmike.domain.entities.Enrollment;
import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.entities.SupportTicket;
import io.github.wazdmike.domain.enums.CourseStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsoleView {
    private static final String LINE = "─ ".repeat(40);
    private static final String DLINE = "═ ".repeat(40);

    public void printTitle(String title) {
        System.out.printf("%n%s%n", DLINE);
        System.out.printf(" %s%n", title.toUpperCase());
        System.out.printf("%s%n", DLINE);
    }

    public void printSection(String section) {
        System.out.printf("%n%s%n", LINE);
        System.out.printf(" %s%n", section);
        System.out.printf("%s%n", LINE);
    }

    public void printMessage(String message) {
        System.out.printf(" %s%n", message);
    }

    public void printSuccess(String message) {
        System.out.printf(" SUCESSO: %s%n", message);
    }

    public void printError(String message) {
        System.out.printf(" ERRO: %s%n", message);
    }


    public void printMenu(String title, String[] options) {
        printSection(title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, options[i]);
        }
        System.out.printf("  [0] Voltar / Sair%n");
        System.out.print("\n  Opção: ");
    }

    public void printCourseList(List<Course> courses) {
        if (courses.isEmpty()) {
            printMessage("Nenhum curso encontrado.");
            return;
        }
        System.out.println();
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            System.out.printf("  %2d. [%s] %s%n", i + 1, c.getStatus(), c.getTitle());
            System.out.printf("      Instrutor: %s | %sh | %s%n",
                    c.getInstructorName(), c.getDurationInHours(), c.getDifficultyLevel());
        }
    }

    public void printActiveCourseList(List<Course> courses) {
        List<Course> active = courses.stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .toList();
        printCourseList(active);
    }

    public void printEnrollmentList(List<Enrollment> enrollments) {
        if (enrollments.isEmpty()) {
            printMessage("Nenhuma matrícula encontrada.");
            return;
        }
        System.out.println();
        for (int i = 0; i < enrollments.size(); i++) {
            System.out.printf("  %2d. %s%n", i + 1, enrollments.get(i));
        }
    }

    public void printTicket(SupportTicket ticket) {
        System.out.println();
        System.out.println(ticket);
    }

    public void printInstructors(Set<String> instructors) {
        System.out.println();
        instructors.forEach(name -> System.out.println("  • " + name));
    }

    public void printStudentsByPlan(Map<String, List<Student>> grouped) {
        grouped.forEach((plan, students) -> {
            System.out.printf("%n  Plano %s (%d aluno(s)):%n", plan, students.size());
            students.forEach(s ->
                    System.out.printf("    - %s <%s>%n", s.getName(), s.getEmail()));
        });
    }

    public void printCsv(String csv) {
        printSection("EXPORTAÇÃO CSV");
        System.out.println(csv);
    }

}
