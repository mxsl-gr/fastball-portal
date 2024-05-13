package dev.fastball.portal.core.security.matcher;

import dev.fastball.core.component.runtime.ComponentRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class DynamicAnonymousPathRequestMatcher implements RequestMatcher {
    private static final String COMPONENT_PATH = "/api/fastball/component/{componentKey}/**";
    private final ComponentRegistry componentRegistry;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean matches(HttpServletRequest request) {
        String requestPath = request.getServletPath();
        if (pathMatcher.match(COMPONENT_PATH, requestPath)) {
            String componentKey = extractComponentKey(requestPath);
            if (componentRegistry.getAnonymousAccessComponentBeans().contains(componentKey)) {
                return true;
            }
        }
        return false;
    }

    private String extractComponentKey(String requestPath) {
        return pathMatcher.extractUriTemplateVariables(COMPONENT_PATH, requestPath).get("componentKey");
    }
}
