package dev.fastball.portal.jpa.entity;

import dev.fastball.orm.jpa.JpaBaseEntity;
import dev.fastball.orm.jpa.converter.MapJsonConverter;
import dev.fastball.portal.core.model.entity.MenuEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

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
