package dev.fastball.portal.core.model.entity;

import dev.fastball.portal.core.model.context.Menu;

import java.util.Map;

public interface MenuEntity extends Menu {

    void setParentId(Long parentId);

    void setCode(String code);

    void setName(String name);

    void setPath(String path);

    void setDescription(String description);

    void setParams(Object params);
}
