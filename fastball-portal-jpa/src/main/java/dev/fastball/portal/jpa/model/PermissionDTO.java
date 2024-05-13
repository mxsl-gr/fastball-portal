package dev.fastball.portal.jpa.model;

import dev.fastball.portal.core.dict.PermissionType;
import lombok.Data;

import java.util.List;

@Data
public class PermissionDTO {
    private Long id;
    private String code;
    private String name;
    private String target;
    private PermissionType type;
    private String description;
    private List<PermissionDTO> subPermissions;
}
