package dev.fastball.portal.jpa.ui;

import dev.fastball.core.annotation.RecordAction;
import dev.fastball.core.annotation.UIComponent;
import dev.fastball.portal.core.model.RegisterUser;
import dev.fastball.portal.core.service.FastballPortalService;
import dev.fastball.ui.components.form.Form;
import lombok.RequiredArgsConstructor;

@UIComponent
@RequiredArgsConstructor
public class UserForm implements Form<RegisterUser> {

    private final FastballPortalService portalService;

    @RecordAction(name = "提交")
    public void submit(RegisterUser user) {
        portalService.registerUser(user);
    }
}
