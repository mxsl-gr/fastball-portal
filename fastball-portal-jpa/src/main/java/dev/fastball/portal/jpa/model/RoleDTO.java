package dev.fastball.portal.jpa.model;

import dev.fastball.core.annotation.Field;
import dev.fastball.core.annotation.TreeLookup;
import dev.fastball.meta.basic.DisplayType;
import dev.fastball.meta.basic.ValueType;
import dev.fastball.portal.jpa.ui.action.PermissionTreeAction;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleDTO {
    @Field(display = DisplayType.Hidden)
    private Long id;

    @Field(title = "编码")
    @NotNull(message = "角色编码不可为空")
    private String code;

    @Field(title = "名称")
    @NotNull(message = "角色名称不可为空")
    private String name;

    @Field(title = "授权", entireRow = true)
    @TreeLookup(value = PermissionTreeAction.class, valueField = "id", labelField = "name", childrenField = "subPermissions")
    private List<Long> permissions;

    @Field(title = "备注", type = ValueType.TEXTAREA, entireRow = true)
    private String description;
}
