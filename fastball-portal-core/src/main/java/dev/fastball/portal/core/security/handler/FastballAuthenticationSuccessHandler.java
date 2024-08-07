package dev.fastball.portal.core.security.handler;

import dev.fastball.core.Result;
import dev.fastball.portal.core.model.AccessToken;
import dev.fastball.portal.core.security.utils.JwtUtils;
import dev.fastball.portal.core.security.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class FastballAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ResponseUtils responseUtils;
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String token = jwtUtils.generateToken(authentication);
        AccessToken accessToken = new AccessToken();
        accessToken.setToken(token);
        accessToken.setExpiration(jwtUtils.getExpiredDateFromToken(token).getTime());
        Result<Object> result = Result.success("登录成功 ", accessToken);
        responseUtils.writeResult(response, result);
    }
}