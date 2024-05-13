package dev.fastball.portal.jpa.ui;

import dev.fastball.core.annotation.RecordAction;
import dev.fastball.core.annotation.UIComponent;
import dev.fastball.core.exception.BusinessException;
import dev.fastball.portal.core.service.FastballPortalService;
import dev.fastball.portal.jpa.model.UserResetPassword;
import dev.fastball.ui.components.form.Form;
import lombok.RequiredArgsConstructor;

@UIComponent
@RequiredArgsConstructor
public class ResetPasswordForm implements Form<UserResetPassword> {

    private final FastballPortalService portalService;

    @RecordAction(name = "提交")
    public void submit(UserResetPassword userResetPassword) {
        if (!portalService.resetPasswordByUserId(userResetPassword.getId(), userResetPassword.getPassword())) {
            throw new BusinessException("重置密码失败");
        }
    }

}
