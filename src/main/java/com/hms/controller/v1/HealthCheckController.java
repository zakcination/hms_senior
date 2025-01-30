package com.hms.controller.v1;

import com.hms.config.ApiVersionConfig;
import com.hms.controller.BaseController;
import com.hms.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(ApiVersionConfig.API_V1 + "/health")
@Tag(name = "Health Check", description = "API health check endpoints")
@Component("healthCheckControllerV1")
public class HealthCheckController extends BaseController {

    @Operation(summary = "Check API health", description = "Returns the health status of the API and its components")
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> health = Map.of(
                "status", "UP",
                "version", "1.0",
                "timestamp", System.currentTimeMillis());
        return ok("System is healthy", health);
    }
}