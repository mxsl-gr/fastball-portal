package dev.fastball.portal.jpa.model;

import dev.fastball.core.annotation.Field;
import lombok.Data;

@Data
public class UserQueryModel {


    @Field(title = "用户名")
    private String username;

    @Field(title = "昵称")
    private String nickname;

}
