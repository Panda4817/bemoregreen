package dev.kmunton.bemoregreen;

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
        for (int i = 0; i < answers.length; i = i + 1) {
            score = score + answers[i];
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("score", Integer.toString(score));
        ArrayList<String> colour = colours.get(score);
        result.put("background", colour.get(0));
        result.put("text", colour.get(1));
        return getJson(result);
    }

    public String getQuestions() {
        return getJson(questions);
    }

    public String getJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
