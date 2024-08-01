package dev.fastball.portal.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.fastball.orm.jpa.JpaBaseEntity;
import dev.fastball.portal.core.dict.PermissionType;
import dev.fastball.portal.core.model.entity.PermissionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "fb_permission",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"type", "code"})
        }
)
@EqualsAndHashCode(callSuper = true)
public class JpaPermissionEntity extends JpaBaseEntity implements PermissionEntity {

    private Long parentId;
    private String code;
    private String name;
    private String target;
    @Enumerated(EnumType.STRING)
    private PermissionType type;
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private List<JpaRoleEntity> roles;
}
