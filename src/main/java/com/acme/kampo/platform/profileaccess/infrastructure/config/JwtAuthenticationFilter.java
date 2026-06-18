package com.acme.kampo.platform.profileaccess.infrastructure.config;

import com.acme.kampo.platform.profileaccess.application.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT authentication filter — runs once per request.
 *
 * <p>Extracts the Bearer token from the {@code Authorization} header,
 * validates it using {@link JwtTokenProvider}, and sets the authentication
 * in the {@link SecurityContextHolder} so Spring Security allows the request.</p>
 *
 * <p>If no token is present or the token is invalid, the filter chain continues
 * without authentication — the endpoint's access rules then decide if the request
 * is rejected (401) or allowed (public endpoints).</p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        var token = extractToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            var email = jwtTokenProvider.getUserEmailFromToken(token);
            var authentication = new UsernamePasswordAuthenticationToken(
                    email, null, List.of());
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer "))
            return header.substring(7);
        return null;
    }
}