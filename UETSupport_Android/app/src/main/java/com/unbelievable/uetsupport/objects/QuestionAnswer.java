package com.unbelievable.uetsupport.objects;

import com.unbelievable.uetsupport.common.CommonUtils;

import org.json.JSONObject;

/**
 * Created by huylv on 21/11/2015.
 */
public class QuestionAnswer {
    public long answerId;
    public String question;
    public String answer;
    public String care;

    public QuestionAnswer(){}

    public static QuestionAnswer getQuestionAnswer(JSONObject jData) {
        try {
            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.answerId = jData.getLong("answerId");
            questionAnswer.question = CommonUtils.getValidString(jData.getString("question"));
            questionAnswer.answer = CommonUtils.getValidString(jData.getString("answer"));
            questionAnswer.care = CommonUtils.getValidString(jData.getString("care"));
            return questionAnswer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
