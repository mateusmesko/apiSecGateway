package br.com.mesko.filter;

import br.com.mesko.config.JwtValidationService;
import br.com.mesko.model.RequestInfo;
import br.com.mesko.service.RequestAuditService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RequestTrackingFilter implements GlobalFilter {

    private static final List<String> PUBLIC_ROUTES =
            List.of(
                    "/auth/login",
                    "/auth/register",
                    "/health"
            );

    private final RequestAuditService requestAuditService;
    private final JwtValidationService jwtValidationService;

    public RequestTrackingFilter(
            RequestAuditService requestAuditService,
            JwtValidationService jwtValidationService
    ) {
        this.requestAuditService = requestAuditService;
        this.jwtValidationService = jwtValidationService;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        String requestId = UUID.randomUUID().toString();

        String ip = exchange
                .getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();

        String userAgent = exchange
                .getRequest()
                .getHeaders()
                .getFirst("User-Agent");

        String path = exchange
                .getRequest()
                .getURI()
                .getPath();

        RequestInfo requestInfo = new RequestInfo(
                requestId,
                ip,
                userAgent,
                exchange.getRequest().getMethod().name(),
                path,
                LocalDateTime.now()
        );

        requestAuditService.register(requestInfo);

        boolean isPublicRoute =
                PUBLIC_ROUTES.stream()
                        .anyMatch(path::startsWith);

        if (isPublicRoute) {
            return chain.filter(exchange);
        }

        String authorization =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("Authorization");

        if (authorization == null
                || !authorization.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse()
                    .setComplete();
        }

        String token =
                authorization.replace("Bearer ", "");

        if (!jwtValidationService.validate(token)) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse()
                    .setComplete();
        }

        return chain.filter(exchange);
    }
}
