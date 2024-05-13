package dev.fastball.portal.core.model.entity;

import dev.fastball.portal.core.dict.PermissionType;
import dev.fastball.portal.core.model.context.Permission;

public interface PermissionEntity extends Permission {

    void setParentId(Long parentId);

    void setCode(String code);

    void setName(String name);

    void setType(PermissionType type);

    void setTarget(String target);

    void setDescription(String description);
}
