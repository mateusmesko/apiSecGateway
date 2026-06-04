package br.com.mesko.config;

import br.com.mesko.enums.TypeRun;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppConfig(TypeRun typeRun) {
}