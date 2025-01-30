package com.hms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Response status code", example = "200")
    private int status;

    @Schema(description = "Response message", example = "Success")
    private String message;

    @Schema(description = "Response data")
    private T data;

    @Schema(description = "Error details, if any")
    private String error;

    @Schema(description = "Timestamp of the response", example = "2024-02-20T10:30:00")
    private LocalDateTime timestamp = LocalDateTime.now();

    private ApiResponse(int status, String message, T data, String error, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data, null, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data, null, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(int status, String message, String error) {
        return new ApiResponse<>(status, message, null, error, LocalDateTime.now());
    }
}