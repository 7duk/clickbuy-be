package dev.sideproject.ndx2.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResourceServerConfig {
    static String prefixPath = "/api/v1/";
    static String[] allowedURLS = Stream.of(
            "health-check",
            "/actuator/**",
            "/oauth2/**",
            "/auth/**"
    ).map(str -> str.startsWith("/") ? str : prefixPath + str).toArray(String[]::new);


    JwtDecoder jwtDecoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler jwtAccessDeniedHandler,
                                                   AuthenticationEntryPointHandler jwtAuthenticationEntryPointHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .securityMatcher("/**")
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(allowedURLS).permitAll()
                                .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServer -> {
                    resourceServer.jwt(jwt -> jwt.decoder(jwtDecoder));
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler)
                                .authenticationEntryPoint(jwtAuthenticationEntryPointHandler))
                .build();
    }

}



