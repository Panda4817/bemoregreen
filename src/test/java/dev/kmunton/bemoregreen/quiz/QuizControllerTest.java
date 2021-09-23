package dev.kmunton.bemoregreen.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@MicronautTest
public class QuizControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testGetQuiz() {
        HttpRequest<String> request = HttpRequest.GET("/api/questions");
        String body = client.toBlocking().retrieve(request);
        Quiz quiz = new Quiz();
        String expected = quiz.getQuestions();
        assertNotNull(body);
        assertEquals(expected, body);
    }

    @Test
    public void testGetScore() {
        HttpRequest<String> request = HttpRequest.POST("/api/score", "{\"data\": [0,0,0,0,0,0,0,0]}");
        String body = client.toBlocking().retrieve(request);
        Quiz quiz = new Quiz();
        int[] data = { 0, 0, 0, 0, 0, 0, 0, 0 };
        String expected = quiz.getGreenScore(data);
        assertNotNull(body);
        assertEquals(expected, body);
    }
}
