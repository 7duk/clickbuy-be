package dev.sideproject.ndx.entity.auditor;

import dev.sideproject.ndx.security.CustomUserDetails;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class AuditorAwareImpl implements AuditorAware<Integer> {
    @NonNull
    @Override
    public Optional<Integer> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(CustomUserDetails.class::cast)
                .map(CustomUserDetails::getId);
    }
}
