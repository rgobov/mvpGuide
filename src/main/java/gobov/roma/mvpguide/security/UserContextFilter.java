package gobov.roma.mvpguide.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        // Логика определения пользователя (по cookie/session/token)
        // Может работать с обоими типами пользователей
        try {
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            throw new RuntimeException("Что то пошло не так в filterChain.doFilter");
        } catch (ServletException e) {
            throw new RuntimeException("Что то пошло не так в filterChain.doFilter");
        }
    }
}