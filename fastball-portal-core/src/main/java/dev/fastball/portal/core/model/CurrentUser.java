package dev.fastball.portal.core.model;

import dev.fastball.portal.core.dict.UserStatus;
import dev.fastball.portal.core.model.context.Menu;
import dev.fastball.portal.core.model.context.User;
import lombok.Data;

import java.util.List;

@Data
public class CurrentUser implements User {

    private Long id;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    private UserStatus status;
    private String wxOpenId;
    private List<Menu> menus;
}
