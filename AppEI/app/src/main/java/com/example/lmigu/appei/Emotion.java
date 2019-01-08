package com.example.lmigu.appei;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lmigu on 04/12/2018.
 */

public class Emotion
{


    @SerializedName("anger")
    @Expose
    private float anger;
    @SerializedName("contempt")
    @Expose
    private float contempt;
    @SerializedName("disgust")
    @Expose
    private float disgust;
    @SerializedName("fear")
    @Expose
    private float fear;
    @SerializedName("happiness")
    @Expose
    private float happiness;
    @SerializedName("neutral")
    @Expose
    private float neutral;
    @SerializedName("sadness")
    @Expose
    private float sadness;
    @SerializedName("surprise")
    @Expose
    private float surprise;
    private final static long serialVersionUID = -6926764518388819396L;

    public float getAnger() {
        return anger;
    }

    public void setAnger(float anger) {
        this.anger = anger;
    }

    public Emotion withAnger(float anger) {
        this.anger = anger;
        return this;
    }

    public float getContempt() {
        return contempt;
    }

    public void setContempt(float contempt) {
        this.contempt = contempt;
    }

    public Emotion withContempt(float contempt) {
        this.contempt = contempt;
        return this;
    }

    public float getDisgust() {
        return disgust;
    }

    public void setDisgust(float disgust) {
        this.disgust = disgust;
    }

    public Emotion withDisgust(float disgust) {
        this.disgust = disgust;
        return this;
    }

    public float getFear() {
        return fear;
    }

    public void setFear(float fear) {
        this.fear = fear;
    }

    public Emotion withFear(float fear) {
        this.fear = fear;
        return this;
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }

    public Emotion withHappiness(float happiness) {
        this.happiness = happiness;
        return this;
    }

    public float getNeutral() {
        return neutral;
    }

    public void setNeutral(float neutral) {
        this.neutral = neutral;
    }

    public Emotion withNeutral(float neutral) {
        this.neutral = neutral;
        return this;
    }

    public float getSadness() {
        return sadness;
    }

    public void setSadness(float sadness) {
        this.sadness = sadness;
    }

    public Emotion withSadness(float sadness) {
        this.sadness = sadness;
        return this;
    }

    public float getSurprise() {
        return surprise;
    }

    public void setSurprise(float surprise) {
        this.surprise = surprise;
    }

    public Emotion withSurprise(float surprise) {
        this.surprise = surprise;
        return this;
    }


}