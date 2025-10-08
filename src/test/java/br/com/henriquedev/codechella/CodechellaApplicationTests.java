package br.com.henriquedev.codechella;

import br.com.henriquedev.codechella.controller.response.EventoResponse;
import br.com.henriquedev.codechella.enums.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodechellaApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

	@Test
	void cadastraNovoEvento() {
        EventoResponse eventoResponse = new EventoResponse(null, TipoEvento.SHOW, "Kiss",
                LocalDate.parse("2025-01-01"), "Show da melhor banda que existe");

        webTestClient.post().uri("/eventos").bodyValue(eventoResponse)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EventoResponse.class)
                .value(response -> {
                    assertNotNull(response.id());
                    assertEquals(eventoResponse.tipo(), response.tipo());
                    assertEquals(eventoResponse.nome(), response.nome());
                    assertEquals(eventoResponse.data(), response.data());
                    assertEquals(eventoResponse.descricao(), response.descricao());
                });

    }

}
