package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.comment.service.CommentAdminService;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final CommentAdminService commentAdminService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler
    ) throws IOException {
        //어드민 권한 여부 확인
        String userRole = (String) request.getAttribute("userRole");

        if(!UserRole.ADMIN.name().equals(userRole)) {
            throw new AuthException("인증되지 않은 사용자입니다.");
        }

        log.info("요청 시각: {}", LocalDateTime.now().toString());
        log.info("요청 URL: {}", request.getRequestURI());

        return true;
    }
}
