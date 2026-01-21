package com.ademlo.reto_kairos_tienda;

import com.ademlo.reto_kairos_tienda.prices.infra.rest.TestPriceRepositoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestPriceRepositoryConfig.class)
class RetoKairosTiendaApplicationTests {

    @Test
    void contextLoads() {
    }

}
