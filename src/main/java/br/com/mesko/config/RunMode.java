package br.com.mesko.config;

import br.com.mesko.enums.TypeRun;
import org.springframework.stereotype.Component;

@Component
public class RunMode {

    private final AppConfig config;

    public RunMode(AppConfig config) {
        this.config = config;
    }

    public boolean isDev() {
        return config.typeRun() == TypeRun.DEV;
    }

    public boolean isProd() {
        return config.typeRun() == TypeRun.PROD;
    }

    public TypeRun getTypeRun() {
        return config.typeRun();
    }
}