package dev.fastball.portal.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.fastball.core.annotation.Field;
import dev.fastball.meta.basic.DisplayType;
import dev.fastball.orm.jpa.JpaBaseEntity;
import dev.fastball.portal.core.dict.UserStatus;
import dev.fastball.portal.core.model.entity.UserEntity;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fb_user")
@EqualsAndHashCode(callSuper = true)
public class JpaUserEntity extends JpaBaseEntity implements UserEntity {

    @Field(title = "手机号")
    private String mobile;

    @Field(title = "用户名")
    private String username;

    @Field(display = DisplayType.Disabled)
    private String password;

    @Field(title = "昵称")
    private String nickname;

    @Field(title = "用户状态")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    // FIXME need abs third login
    @Field(title = "微信 OpenID", display = DisplayType.Hidden)
    private String wxOpenId;

    @JsonIgnore
    @ManyToMany
    @Field(display = DisplayType.Hidden)
    @JoinTable(name = "fb_user_role_rel", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<JpaRoleEntity> roles;
}
