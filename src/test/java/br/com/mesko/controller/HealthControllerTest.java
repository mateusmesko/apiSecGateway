package br.com.mesko.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    void testReturnHealthRoute(){
        webClient.get().uri("/health").exchange().expectStatus().isOk();
    }
}
