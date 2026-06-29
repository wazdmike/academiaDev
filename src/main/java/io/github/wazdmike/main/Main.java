package io.github.wazdmike.main;

import io.github.wazdmike.application.repositories.CourseRepository;
import io.github.wazdmike.application.repositories.EnrollmentRepository;
import io.github.wazdmike.application.repositories.SupportTicketQueue;
import io.github.wazdmike.application.repositories.UserRepository;
import io.github.wazdmike.application.usecases.*;
import io.github.wazdmike.infrastructure.persistence.CourseRepositoryInMemory;
import io.github.wazdmike.infrastructure.persistence.EnrollmentRepositoryInMemory;
import io.github.wazdmike.infrastructure.persistence.SupportTicketQueueInMemory;
import io.github.wazdmike.infrastructure.persistence.UserRepositoryInMemory;
import io.github.wazdmike.infrastructure.ui.ConsoleController;
import io.github.wazdmike.infrastructure.ui.ConsoleView;
import io.github.wazdmike.infrastructure.utils.GenericCsvExporter;

import java.util.Scanner;

public class Main {
    static void main() {
        System.out.println("AcademiaDev — Iniciando...\n");

        CourseRepository courseRepo = new CourseRepositoryInMemory();
        UserRepository userRepo = new UserRepositoryInMemory();
        EnrollmentRepository enrollRepo = new EnrollmentRepositoryInMemory();
        SupportTicketQueue ticketQueue = new SupportTicketQueueInMemory();

        InitialData.populate(courseRepo, userRepo);

        // Casos de Uso
        EnrollStudentUseCase enrollUseCase = new EnrollStudentUseCase(courseRepo, enrollRepo);
        CancelEnrollmentUseCase cancelUseCase = new CancelEnrollmentUseCase(courseRepo, enrollRepo);
        UpdateProgressUseCase progressUseCase = new UpdateProgressUseCase(courseRepo, enrollRepo);
        OpenTicketUseCase openTicket = new OpenTicketUseCase(ticketQueue);
        ProcessTicketUseCase processTicket = new ProcessTicketUseCase(ticketQueue);
        ChangeCourseStatusUseCase courseStatus = new ChangeCourseStatusUseCase(courseRepo);
        ChangeStudentPlanUseCase studentPlan = new ChangeStudentPlanUseCase(userRepo);
        ReportUseCase reportUseCase = new ReportUseCase(courseRepo, enrollRepo, userRepo);

        Scanner scanner = new Scanner(System.in);
        ConsoleView view = new ConsoleView();
        GenericCsvExporter csvExporter = new GenericCsvExporter();

        ConsoleController controller = new ConsoleController(
                scanner, view, userRepo, csvExporter,
                enrollUseCase, cancelUseCase, progressUseCase,
                openTicket, processTicket, courseStatus,
                studentPlan, reportUseCase
        );

        controller.run();

        scanner.close();
    }
}
