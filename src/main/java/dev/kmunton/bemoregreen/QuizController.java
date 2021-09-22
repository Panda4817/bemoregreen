package dev.kmunton.bemoregreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;

@Controller("/quiz")
public class QuizController {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuiz() {
        Quiz quiz = new Quiz();
        return quiz.getQuestions();
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public String getScore(@Body Map<String, int[]> data) {
        Quiz quiz = new Quiz();
        return quiz.getGreenScore(data.get("data"));
    }

}
