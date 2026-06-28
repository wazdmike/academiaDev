package io.github.wazdmike.application.usecases;

import io.github.wazdmike.application.repositories.SupportTicketQueue;
import io.github.wazdmike.domain.entities.SupportTicket;
import io.github.wazdmike.domain.exceptions.BusinessException;

public class ProcessTicketUseCase {
    private final SupportTicketQueue supportTicketQueue;

    public ProcessTicketUseCase(SupportTicketQueue supportTicketQueue) {
        this.supportTicketQueue = supportTicketQueue;
    }

    public SupportTicket execute() {
        if (supportTicketQueue.isEmpty()) {
            throw new BusinessException("Não há tickets na fila de suporte.");
        }
        return supportTicketQueue.nextTicket();
    }

    public int pendingCount() {
        return supportTicketQueue.size();
    }
}
