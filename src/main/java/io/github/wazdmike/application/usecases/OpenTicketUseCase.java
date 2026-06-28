package io.github.wazdmike.application.usecases;

import io.github.wazdmike.application.repositories.SupportTicketQueue;
import io.github.wazdmike.domain.entities.SupportTicket;
import io.github.wazdmike.domain.entities.User;

import java.util.UUID;

public class OpenTicketUseCase {
    private final SupportTicketQueue supportTicketQueue;

    public OpenTicketUseCase(SupportTicketQueue supportTicketQueue) {
        this.supportTicketQueue = supportTicketQueue;
    }

    public SupportTicket execute(User user, String title, String message) {
        SupportTicket ticket = new SupportTicket(
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase(),
                title, message, user);
        supportTicketQueue.addTicket(ticket);
        return ticket;
    }
}
