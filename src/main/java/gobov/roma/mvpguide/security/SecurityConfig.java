package gobov.roma.mvpguide.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll() // Для мониторинга
                        .requestMatchers("/api/poi/nearby").permitAll() // Публичный эндпоинт
                        .requestMatchers("/api/**").authenticated() // Все остальные API требуют аутентификации
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(); // Используем базовую аутентификацию

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();
        var regularUser = User
                .withUsername("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, regularUser);
    }
}