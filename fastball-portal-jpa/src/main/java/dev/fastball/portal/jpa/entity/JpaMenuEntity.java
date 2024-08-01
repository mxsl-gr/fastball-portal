package dev.fastball.portal.jpa.entity;

import dev.fastball.orm.jpa.JpaBaseEntity;
import dev.fastball.orm.jpa.converter.MapJsonConverter;
import dev.fastball.portal.core.model.entity.MenuEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "fb_menu")
@EqualsAndHashCode(callSuper = true)
public class JpaMenuEntity extends JpaBaseEntity implements MenuEntity {

    private Long parentId;

    @Column(unique = true)
    private String code;
    private String name;
    private String path;
    @Convert(converter = MapJsonConverter.class)
    private Object params;
    private String description;
}
