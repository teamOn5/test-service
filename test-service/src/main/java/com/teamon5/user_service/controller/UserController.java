package com.teamon5.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User Service API", description = "사용자 서비스 및 미들웨어 DB 테스트용 API")
@RestController
public class UserController {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Operation(summary = "ALB 타겟 그룹 헬스체크", description = "ALB에서 호출하는 기본 루트 헬스체크 엔드포인트")
    @GetMapping("/")
    public Map<String, Object> rootHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "user-service");
        response.put("message", "teamOn5 backend service is running successfully!");
        return response;
    }

    @Operation(summary = "유저 서비스 상태 확인", description = "마이크로서비스 상태 확인 API")
    @GetMapping("/api/users/health")
    public Map<String, Object> userHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "user-service");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @Operation(summary = "Vercel 프론트엔드 연동 테스트", description = "Vercel -> ALB -> Backend EC2 기본 연동 확인용 API")
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

    @Operation(summary = "미들웨어 DB middle 테이블 목업 데이터 조회", description = "DB/미들웨어 EC2의 user_db.middle 테이블에 적재된 목업 데이터를 조회합니다.")
    @GetMapping("/api/users/db/middle")
    public Map<String, Object> getMiddleMockData() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("service", "user-service");
        response.put("table", "middle (DB EC2: 172.31.39.86:3306/user_db)");
        if (jdbcTemplate != null) {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT id, service_name, mock_key, mock_value, status, created_at FROM middle");
            response.put("data", rows);
            response.put("count", rows.size());
            response.put("status", "SUCCESS");
        } else {
            response.put("status", "JDBC_TEMPLATE_NOT_CONFIGURED");
        }
        return response;
    }

    @Operation(summary = "미들웨어 DB users 테이블 목업 데이터 조회", description = "DB/미들웨어 EC2의 user_db.users 테이블에 적재된 목업 데이터를 조회합니다.")
    @GetMapping("/api/users/db/users")
    public Map<String, Object> getUsersMockData() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("service", "user-service");
        response.put("table", "users (DB EC2: 172.31.39.86:3306/user_db)");
        if (jdbcTemplate != null) {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT id, username, email, role, created_at FROM users");
            response.put("data", rows);
            response.put("count", rows.size());
            response.put("status", "SUCCESS");
        } else {
            response.put("status", "JDBC_TEMPLATE_NOT_CONFIGURED");
        }
        return response;
    }
}
