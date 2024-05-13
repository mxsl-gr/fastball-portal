package dev.fastball.portal.core.model.entity;

import dev.fastball.portal.core.dict.UserStatus;
import dev.fastball.portal.core.model.context.User;

import java.util.Map;

public interface UserEntity extends User {

    String getPassword();

    void setPassword(String password);

    void setUsername(String username);

    void setNickname(String nickname);

    void setMobile(String mobile);

    void setStatus(UserStatus status);

    void setWxOpenId(String wxOpenId);
}
