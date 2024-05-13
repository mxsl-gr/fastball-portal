package dev.fastball.portal.jpa.ui.action;

import dev.fastball.core.annotation.UIComponent;
import dev.fastball.core.component.LookupAction;
import dev.fastball.core.component.LookupActionParam;
import dev.fastball.portal.core.model.context.Role;
import dev.fastball.portal.core.service.FastballPortalService;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@UIComponent
@RequiredArgsConstructor
public class RoleAction implements LookupAction<Role, Object> {
    private final FastballPortalService portalService;

    @Override
    public Collection<Role> loadLookupItems(LookupActionParam<Object> param) {
        return portalService.getAllRole();
    }
}
