package suai.vladislav.onboardingapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless-сессии
            .authenticationProvider(authenticationProvider) // Настройка провайдера аутентификации
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT-фильтр
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/track/**",
                    "/api/v1/module/**",
                    "/api/v1/page/**",
                    "/api/v1/knowledgebase/**",
                    "api/v1/survey"
                ).hasAnyAuthority("MANAGER")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/v1/track/**",
                    "/api/v1/module/**",
                    "/api/v1/page/**",
                    "api/v1/scoreboard",
                    "api/v1/survey",
                    "/api/v1/knowledgebase/**"
                ).hasAnyAuthority("MANAGER")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/v1/track/**",
                    "/api/v1/module/**",
                    "/api/v1/page/**",
                    "api/v1/survey",
                    "api/v1/scoreboard",
                    "/api/v1/knowledgebase/**",
                    "api/v1/user-progress-in-module",
                    "api/v1/surveys/"
                ).hasAnyAuthority("MANAGER")

                .requestMatchers(
                    "/health",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html",
                    "/actuator/**",
                    "/api/v1/auth/**"
                ).permitAll() // Разрешаем доступ без аутентификации
                .anyRequest().authenticated() // Остальные запросы требуют аутентификации
            )
            .build();
    }
}

