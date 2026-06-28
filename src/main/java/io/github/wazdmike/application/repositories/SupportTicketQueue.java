package io.github.wazdmike.application.repositories;

import io.github.wazdmike.domain.entities.SupportTicket;

public interface SupportTicketQueue {
    void addTicket(SupportTicket ticket);
    int size();
    boolean isEmpty();
    SupportTicket nextTicket();
}
