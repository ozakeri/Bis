package com.gap.bis_inspection.db.objectmodel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "FORM_ITEM_ANSWER".
 */
public class FormItemAnswer {

    private Long id;
    private String question;
    private Integer answerTypeEn;
    private Integer answerInt;
    private String answerStr;
    private Long serverAnswerId;
    private String inputValuesDefault;
    private long formAnswerId;
    private long formQuestionId;
    private long formQuestionGroupId;

    public FormItemAnswer() {
    }

    public FormItemAnswer(Long id) {
        this.id = id;
    }

    public FormItemAnswer(Long id, String question, Integer answerTypeEn, Integer answerInt, String answerStr, Long serverAnswerId, String inputValuesDefault, Long formAnswerId, long formQuestionId, long formQuestionGroupId) {
        this.id = id;
        this.question = question;
        this.answerTypeEn = answerTypeEn;
        this.answerInt = answerInt;
        this.answerStr = answerStr;
        this.serverAnswerId = serverAnswerId;
        this.inputValuesDefault = inputValuesDefault;
        this.formAnswerId = formAnswerId;
        this.formQuestionId = formQuestionId;
        this.formQuestionGroupId = formQuestionGroupId;
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

    public String getInputValuesDefault() {
        return inputValuesDefault;
    }

    public void setInputValuesDefault(String inputValuesDefault) {
        this.inputValuesDefault = inputValuesDefault;
    }

    public Long getFormAnswerId() {
        return formAnswerId;
    }

    public void setFormAnswerId(Long formAnswerId) {
        this.formAnswerId = formAnswerId;
    }

    public long getFormQuestionId() {
        return formQuestionId;
    }

    public void setFormQuestionId(long formQuestionId) {
        this.formQuestionId = formQuestionId;
    }

    public long getFormQuestionGroupId() {
        return formQuestionGroupId;
    }

    public void setFormQuestionGroupId(long formQuestionGroupId) {
        this.formQuestionGroupId = formQuestionGroupId;
    }

}