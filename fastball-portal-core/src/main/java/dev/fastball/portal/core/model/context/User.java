package dev.fastball.portal.core.model.context;

import dev.fastball.portal.core.dict.UserStatus;


public interface User extends IdModel {

    String getUsername();

    String getNickname();

    String getMobile();

    UserStatus getStatus();

    String getWxOpenId();
}
