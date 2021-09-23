package dev.kmunton.bemoregreen.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@MicronautTest
public class IndexControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testIndex() {
        HttpRequest<String> request = HttpRequest.GET("/");
        String body = client.toBlocking().retrieve(request);
        assertNotNull(body);
        assertEquals("Server is running", body);
    }

    @Test
    public void testApiIndex() {
        HttpRequest<String> request = HttpRequest.GET("/api");
        String body = client.toBlocking().retrieve(request);
        assertNotNull(body);
        assertEquals("API is running", body);
    }
}