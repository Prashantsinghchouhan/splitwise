package com.example.splitwise.auth;

import com.example.splitwise.exception.authorisation.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("headerAuthContext")
public class HeaderAuthContext implements AuthContext{
    private final HttpServletRequest request;

    public HeaderAuthContext(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getCurrentUserId() {
        String userId = request.getHeader("X-User-Id");
        if(Objects.isNull(userId) || userId.isBlank()){
            throw UnauthorizedException.missingUserHeader();
        }
        return userId;
    }
}
