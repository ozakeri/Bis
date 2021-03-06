package com.gap.bis_inspection.db.objectmodel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SURVEY_FORM_QUESTION_TEMP".
 */
public class SurveyFormQuestionTemp {

    private Long id;
    private String question;
    private Integer answerTypeEn;
    private Integer answerInt;
    private String answerStr;
    private Long serverAnswerId;
    private Long formQuestionGroupId;
    private String inputValuesDefault;
    private long surveyFormId;
    private long surveyFormQuestionId;

    public SurveyFormQuestionTemp() {
    }

    public SurveyFormQuestionTemp(Long id) {
        this.id = id;
    }

    public SurveyFormQuestionTemp(Long id, String question, Integer answerTypeEn, Integer answerInt, String answerStr, Long serverAnswerId, Long formQuestionGroupId, String inputValuesDefault, long surveyFormId, long surveyFormQuestionId) {
        this.id = id;
        this.question = question;
        this.answerTypeEn = answerTypeEn;
        this.answerInt = answerInt;
        this.answerStr = answerStr;
        this.serverAnswerId = serverAnswerId;
        this.formQuestionGroupId = formQuestionGroupId;
        this.inputValuesDefault = inputValuesDefault;
        this.surveyFormId = surveyFormId;
        this.surveyFormQuestionId = surveyFormQuestionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getAnswerTypeEn() {
        return answerTypeEn;
    }

    public void setAnswerTypeEn(Integer answerTypeEn) {
        this.answerTypeEn = answerTypeEn;
    }

    public Integer getAnswerInt() {
        return answerInt;
    }

    public void setAnswerInt(Integer answerInt) {
        this.answerInt = answerInt;
    }

    public String getAnswerStr() {
        return answerStr;
    }

    public void setAnswerStr(String answerStr) {
        this.answerStr = answerStr;
    }

    public Long getServerAnswerId() {
        return serverAnswerId;
    }

    public void setServerAnswerId(Long serverAnswerId) {
        this.serverAnswerId = serverAnswerId;
    }

    public Long getFormQuestionGroupId() {
        return formQuestionGroupId;
    }

    public void setFormQuestionGroupId(Long formQuestionGroupId) {
        this.formQuestionGroupId = formQuestionGroupId;
    }

    public String getInputValuesDefault() {
        return inputValuesDefault;
    }

    public void setInputValuesDefault(String inputValuesDefault) {
        this.inputValuesDefault = inputValuesDefault;
    }

    public long getSurveyFormId() {
        return surveyFormId;
    }

    public void setSurveyFormId(long surveyFormId) {
        this.surveyFormId = surveyFormId;
    }

    public long getSurveyFormQuestionId() {
        return surveyFormQuestionId;
    }

    public void setSurveyFormQuestionId(long surveyFormQuestionId) {
        this.surveyFormQuestionId = surveyFormQuestionId;
    }

}
