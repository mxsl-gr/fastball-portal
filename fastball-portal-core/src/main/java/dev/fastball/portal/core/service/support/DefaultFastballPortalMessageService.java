package dev.fastball.portal.core.service.support;

import dev.fastball.core.component.DataResult;
import dev.fastball.portal.core.model.Message;
import dev.fastball.portal.core.model.context.User;
import dev.fastball.portal.core.service.FastballPortalMessageService;

public class DefaultFastballPortalMessageService implements FastballPortalMessageService {
    @Override
    public boolean hasUnreadMessage(User currentUser) {
        return false;
    }

    @Override
    public DataResult<Message> loadMessages(User currentUser, Long currentPage) {
        return DataResult.empty();
    }

    @Override
    public boolean readMessage(User currentUser, String messageId) {
        return false;
    }
}
