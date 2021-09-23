package dev.kmunton.bemoregreen.quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Quiz {
    private Map<String, ArrayList<Question>> questions = new HashMap<String, ArrayList<Question>>();
    private Map<Integer, ArrayList<String>> colours = new HashMap<Integer, ArrayList<String>>();
    private int totalQuestions = 0;
    private int totalScore = 0;
    private int maxScorePerQuestion = 3;
    private int minScorePerQuestion = 0;

    public Quiz() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File myQuestionsFile = new File(classLoader.getResource("data/quiz.txt").getFile());
            Scanner myQuestionsReader = new Scanner(myQuestionsFile);
            String question = "";
            ArrayList<String> answers = new ArrayList<String>();
            ArrayList<Question> allQuestions = new ArrayList<Question>();
            while (myQuestionsReader.hasNextLine()) {
                String data = myQuestionsReader.nextLine().strip();
                if (data == "") {
                    Question item = new Question(question, answers);
                    allQuestions.add(item);
                    answers = new ArrayList<String>();
                    totalQuestions = totalQuestions + 1;
                    totalScore = totalScore + maxScorePerQuestion;
                    continue;
                } else if (data.endsWith("?")) {
                    question = data;
                } else {
                    answers.add(data);
                }
            }
            Question item = new Question(question, answers);
            allQuestions.add(item);
            questions.put("data", allQuestions);
            myQuestionsReader.close();
            File myColoursFile = new File(classLoader.getResource("data/colours.txt").getFile());
            Scanner myColoursReader = new Scanner(myColoursFile);
            int score = 0;
            ArrayList<String> coloursList = new ArrayList<String>();
            while (myColoursReader.hasNextLine()) {
                String[] data = myColoursReader.nextLine().strip().split(",");
                for (String s : data) {
                    coloursList.add(s);
                }
                colours.put(score, coloursList);
                coloursList = new ArrayList<String>();
                score = score + 1;
            }
            myColoursReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getGreenScore(int[] answers) {
        int score = 0;
        String msg;
        for (int i = 0; i < answers.length; i = i + 1) {
            if (i > totalQuestions) {
                break;
            }
            if (answers[i] > maxScorePerQuestion || answers[i] < minScorePerQuestion) {
                continue;
            }
            score = score + answers[i];
        }
        if (score < minScorePerQuestion || score > totalScore) {
            score = 0;
            msg = "Stop hacking our website and try to be more environmentally friendly!";
        } else if (score <= 6) {
            msg = "We have one Earth. It needs your help to protect it. Please start being eco-friendly. Look at the fourth answer for each question for tips.";
        } else if (score > 6 && score < totalScore) {
            msg = "You are trying to be kind to the planet, which is great. You can be more eco-friendly by looking at the questions you answered with other choices instead of the fourth one.";
        } else {
            msg = "Fantastic! You are protecting the planet! Why not try to encourage others around you to do the same by sharing this quiz.";
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("score", Integer.toString(score));
        ArrayList<String> colour = colours.get(score);
        result.put("background", colour.get(0));
        result.put("text", colour.get(1));
        result.put("msg", msg);
        return getJson(result);
    }

    public String getQuestions() {
        return getJson(questions);
    }

    private String getJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
