package dev.fastball.portal.core.security.service;

import dev.fastball.portal.core.dict.UserStatus;
import dev.fastball.portal.core.exception.UserDisabledException;
import dev.fastball.portal.core.model.entity.UserEntity;
import dev.fastball.portal.core.service.FastballPortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final FastballPortalService portalService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = portalService.loadAccountByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (user.getStatus() == UserStatus.Disabled) {
            throw new UserDisabledException();
        }
        return User.withUsername(username).password(user.getPassword()).authorities(new ArrayList<>()).build();
    }
}
