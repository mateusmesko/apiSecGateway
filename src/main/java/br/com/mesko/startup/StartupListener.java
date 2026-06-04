package br.com.mesko.startup;

import br.com.mesko.config.RunMode;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupListener {

    private final RunMode runMode;

    public StartupListener(RunMode runMode) {
        this.runMode = runMode;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {

        if (runMode.isDev()) {
            System.out.println("Aplicação iniciada em DEV");
        }

        if (runMode.isProd()) {
            System.out.println("Aplicação iniciada em PROD");
        }
    }
}