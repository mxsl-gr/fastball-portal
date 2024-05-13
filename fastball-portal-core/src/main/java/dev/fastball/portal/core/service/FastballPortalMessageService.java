package dev.fastball.portal.core.service;

import dev.fastball.core.component.DataResult;
import dev.fastball.portal.core.model.Message;
import dev.fastball.portal.core.model.context.User;

public interface FastballPortalMessageService {
    boolean hasUnreadMessage(User currentUser);

    DataResult<Message> loadMessages(User currentUser, Long currentPage);

    boolean readMessage(User currentUser, String messageId);
}
