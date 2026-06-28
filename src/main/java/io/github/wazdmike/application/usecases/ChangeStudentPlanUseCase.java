package io.github.wazdmike.application.usecases;

import io.github.wazdmike.application.repositories.UserRepository;
import io.github.wazdmike.domain.entities.Student;
import io.github.wazdmike.domain.entities.User;
import io.github.wazdmike.domain.exceptions.BusinessException;

public class ChangeStudentPlanUseCase {
    private final UserRepository userRepository;

    public ChangeStudentPlanUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Student execute(String studentEmail, String newPlan) {
        if (!newPlan.equalsIgnoreCase("BASIC") && !newPlan.equalsIgnoreCase("PREMIUM")) {
            throw new BusinessException("Plano inválido. Use BASIC ou PREMIUM.");
        }

        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado: " + studentEmail));

        if (!(user instanceof Student student)) {
            throw new BusinessException("O usuário informado não é um aluno.");
        }

        student.setSubscriptionPlan(newPlan.toUpperCase());
        return student;
    }
}
