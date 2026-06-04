package br.com.mesko.filter;

import br.com.mesko.model.RequestInfo;
import br.com.mesko.service.RequestAuditService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RequestTrackingFilter implements GlobalFilter {

    private final RequestAuditService requestAuditService;

    public RequestTrackingFilter(
            RequestAuditService requestAuditService) {
        this.requestAuditService = requestAuditService;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

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

        RequestInfo requestInfo = new RequestInfo(
                requestId,
                ip,
                userAgent,
                exchange.getRequest().getMethod().name(),
                exchange.getRequest().getURI().getPath(),
                LocalDateTime.now()
        );

        requestAuditService.register(requestInfo);

        return chain.filter(exchange);
    }
}