package br.com.mesko.config;

import br.com.mesko.enums.TypeRun;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = "app.type-run=DEV")
class RunModeTest {

    @Autowired
    private RunMode runMode;

    @Test
    void shouldBeDev() {
        assertTrue(runMode.isDev());
    }

    @Test
    void shouldReturnTypeRunFromConfig() {

        TypeRun typeRun = runMode.getTypeRun();

        assertEquals(TypeRun.DEV, typeRun);
    }
}
@SpringBootTest
@TestPropertySource(properties = "app.type-run=PROD")
class RunModeProdTest {

    @Autowired
    private RunMode runMode;

    @Test
    void shouldBeProd() {
        assertTrue(runMode.isProd());
    }
}