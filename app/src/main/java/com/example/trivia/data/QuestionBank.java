package com.example.trivia.data;

import android.util.Log;

import androidx.annotation.LongDef;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import static controller.AppController.TAG;
//import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

public class QuestionBank {

    ArrayList<Question> questionArrayList = new ArrayList<>();

        private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements.json";

        public List<Question>getQuestions(final AnswerListAsyncResponse callBack)
        {
            JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                       for(int i=0;i< response.length();i++)
                       {
                           Question question= new Question();
                           question.setAnswer(response.getJSONArray(i).get(0).toString());
                           question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                           questionArrayList.add(question);

                       }
                    }
                    catch(Exception e){
                        Log.d("onjson", "onResponse: "+e.getMessage());

                    }
                    if(null!=callBack)callBack.processFinished(questionArrayList);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);



            return questionArrayList;

        }












}
