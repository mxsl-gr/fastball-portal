package dev.fastball.portal.jpa.model;

import dev.fastball.core.annotation.Field;
import dev.fastball.core.annotation.Lookup;
import dev.fastball.core.info.basic.DisplayType;
import dev.fastball.portal.jpa.ui.action.RoleAction;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleModel {

    @Field(display = DisplayType.Hidden)
    private Long userId;
    @Field(title = "角色", entireRow = true)
    @Lookup(value = RoleAction.class, labelField = "name")
    private List<Long> roles;
}
