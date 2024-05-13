package dev.fastball.portal.core.model.context;

import dev.fastball.portal.core.dict.PermissionType;

public interface Permission extends IdModel {

    Long getParentId();

    String getCode();

    String getName();

    PermissionType getType();

    String getTarget();

    String getDescription();
}
