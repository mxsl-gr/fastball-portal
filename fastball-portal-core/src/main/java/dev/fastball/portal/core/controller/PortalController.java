package dev.fastball.portal.core.controller;

import dev.fastball.core.Result;
import dev.fastball.core.component.DataResult;
import dev.fastball.portal.core.context.PortalContext;
import dev.fastball.portal.core.model.ChangePasswordModel;
import dev.fastball.portal.core.model.CurrentUser;
import dev.fastball.portal.core.model.Message;
import dev.fastball.portal.core.model.context.User;
import dev.fastball.portal.core.service.FastballPortalMessageService;
import dev.fastball.portal.core.service.FastballPortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal")
public class PortalController {

    private final FastballPortalService portalService;
    private final FastballPortalMessageService messageService;

    @GetMapping("/currentUser")
    public Result<CurrentUser> getCurrentUser() {
        User user = PortalContext.currentUser();
        CurrentUser currentUser = new CurrentUser();
        currentUser.setNickname(user.getNickname());
        currentUser.setUsername(user.getUsername());
        currentUser.setMenus(portalService.getUserMenu(PortalContext.currentUser().getId()));
//        currentUser.setAvatar(user.getA());
        return Result.success(currentUser);
    }

    @PostMapping("/changePassword")
    public Result<?> changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
        portalService.changePassword(PortalContext.currentUser().getId(), changePasswordModel.getPassword(), changePasswordModel.getNewPassword());
        return Result.success();
    }

    @GetMapping("/hasUnreadMessage")
    public Result<Boolean> hasUnreadMessage() {
        return Result.success(messageService.hasUnreadMessage(PortalContext.currentUser()));
    }

    @GetMapping("/loadMessage")
    public Result<DataResult<Message>> loadMessage(@RequestParam Long current) {
        return Result.success(messageService.loadMessages(PortalContext.currentUser(), current));
    }

    @PostMapping("/readMessage/{messageId}")
    public Result<Boolean> hasUnreadMessage(@PathVariable String messageId) {
        return Result.success(messageService.readMessage(PortalContext.currentUser(), messageId));
    }
}
