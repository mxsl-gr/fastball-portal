package dev.fastball.portal.core.model;

import lombok.Data;

@Data
public class AccessToken {

    private String token;

    private Long expiration;
}
