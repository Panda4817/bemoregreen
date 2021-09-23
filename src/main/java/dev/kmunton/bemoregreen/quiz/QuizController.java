package dev.kmunton.bemoregreen.quiz;

import java.util.Map;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;

@Controller("/api")
public class QuizController {
    @Get("/questions")
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuiz() {
        Quiz quiz = new Quiz();
        return quiz.getQuestions();
    }

    @Post("/score")
    @Produces(MediaType.APPLICATION_JSON)
    public String getScore(@Body Map<String, int[]> data) {
        Quiz quiz = new Quiz();
        return quiz.getGreenScore(data.get("data"));
    }

}
