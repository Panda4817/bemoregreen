package dev.kmunton.bemoregreen;

import java.util.ArrayList;

public class Question {
    public String question;
    public ArrayList<String> answers = new ArrayList<String>();

    public Question(String questionText, ArrayList<String> answerList) {
        question = questionText;
        for (String ans : answerList) {
            answers.add(ans);
        }
    }
}
