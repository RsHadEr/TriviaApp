package com.example.trivia.data;

import java.util.ArrayList;

import com.example.trivia.model.Question;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
