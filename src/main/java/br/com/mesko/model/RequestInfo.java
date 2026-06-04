package br.com.mesko.model;

import java.time.LocalDateTime;

public record RequestInfo(
        String requestId,
        String ip,
        String userAgent,
        String method,
        String path,
        LocalDateTime timestamp
) {
}