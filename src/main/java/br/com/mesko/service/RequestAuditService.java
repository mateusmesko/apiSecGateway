package br.com.mesko.service;

import br.com.mesko.model.RequestInfo;
import org.springframework.stereotype.Service;

@Service
public class RequestAuditService {

    public void register(RequestInfo requestInfo) {

        System.out.printf("""
                
                REQUEST_ID: %s
                IP: %s
                METHOD: %s
                PATH: %s
                USER_AGENT: %s
                TIMESTAMP: %s
                
                """,
                requestInfo.requestId(),
                requestInfo.ip(),
                requestInfo.method(),
                requestInfo.path(),
                requestInfo.userAgent(),
                requestInfo.timestamp()
        );
    }
}