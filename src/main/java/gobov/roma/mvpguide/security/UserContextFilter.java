package gobov.roma.mvpguide.security;

import gobov.roma.mvpguide.model.User;
import gobov.roma.mvpguide.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserContextFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext = new UserContext();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            // Аутентифицированный пользователь
            String username = authentication.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));
            userContext.setUser(user);
            userContext.setAuthenticated(true);
        } else {
            // Анонимный пользователь (гость)
            User guestUser = createGuestUser();
            userContext.setUser(guestUser);
            userContext.setAuthenticated(false);
        }

        // Устанавливаем контекст пользователя в ThreadLocal
        UserContextHolder.setContext(userContext);

        try {
            filterChain.doFilter(request, response);
        } finally {
            // Очищаем контекст после обработки запроса
            UserContextHolder.clearContext();
        }
    }

    private User createGuestUser() {
        String guestUsername = "guest_" + System.currentTimeMillis();
        return userService.createGuestUser(guestUsername); // UserService уже устанавливает роль UNAUTHORIZED
    }
}