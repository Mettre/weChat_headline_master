package com.chaychan.news.model.entity;

public class AccountClassification {

    private Integer id;
    private String classificationTitle;
    private Integer classificationType;
    private Boolean hasChoice = false;

    public Boolean getHasChoice() {
        return hasChoice;
    }

    public void setHasChoice(Boolean hasChoice) {
        this.hasChoice = hasChoice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassificationTitle() {
        return classificationTitle;
    }

    public void setClassificationTitle(String classificationTitle) {
        this.classificationTitle = classificationTitle;
    }

    public Integer getClassificationType() {
        return classificationType;
    }

    public void setClassificationType(Integer classificationType) {
        this.classificationType = classificationType;
    }
}
