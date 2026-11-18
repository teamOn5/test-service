package com.teamon5.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    // ALB 타겟 그룹 헬스 체크용 루트 엔드포인트
    @GetMapping("/")
    public Map<String, Object> rootHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "user-service");
        response.put("message", "teamOn5 backend service is running successfully!");
        return response;
    }

    // 마이크로서비스 상태 확인 API
    @GetMapping("/api/users/health")
    public Map<String, Object> userHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "user-service");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    // Vercel 프론트엔드 연동 테스트용 API
    @GetMapping("/api/users/test")
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Hello teamOn5! Connected from Vercel -> ALB -> Backend EC2!");
        response.put("data", Map.of(
            "id", 1,
            "username", "teamOn5_admin",
            "role", "DEVELOPER"
        ));
        return response;
    }
}
