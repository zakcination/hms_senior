package com.hms.controller.v2;

import com.hms.config.ApiVersionConfig;
import com.hms.controller.BaseController;
import com.hms.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Map;

@RestController
@RequestMapping(ApiVersionConfig.API_V2 + "/health")
@Tag(name = "Health Check V2", description = "Enhanced API health check endpoints")
@Component("healthCheckControllerV2")
public class HealthCheckController extends BaseController {

        @Value("${spring.application.name:HMS API}")
        private String applicationName;

        @Operation(summary = "Check API health with detailed metrics", description = "Returns detailed health status including memory usage and system metrics")
        @GetMapping
        public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
                MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
                Runtime runtime = Runtime.getRuntime();

                Map<String, Object> health = Map.of(
                                "status", "UP",
                                "version", "2.0",
                                "timestamp", System.currentTimeMillis(),
                                "application", applicationName,
                                "metrics", Map.of(
                                                "heap", Map.of(
                                                                "used", memoryBean.getHeapMemoryUsage().getUsed(),
                                                                "max", memoryBean.getHeapMemoryUsage().getMax()),
                                                "processors", runtime.availableProcessors(),
                                                "uptime", ManagementFactory.getRuntimeMXBean().getUptime()));
                return ok("System is healthy with detailed metrics", health);
        }
}