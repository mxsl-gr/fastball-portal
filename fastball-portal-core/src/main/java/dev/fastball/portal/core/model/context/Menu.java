package dev.fastball.portal.core.model.context;

import java.util.Map;

public interface Menu extends IdModel {
    Long getParentId();

    String getCode();

    String getName();

    String getPath();

    String getDescription();

    Object getParams();
}
