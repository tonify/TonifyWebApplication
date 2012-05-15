/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.util.List;

/**
 * A POJO that stores a single search result
 * @author Diego Perez Botero
 */
public class SearchResult implements Serializable {
    
    private int rank;
    
    private String URL;
    
    private String title;
    
    private List<ToneScore> toneScores;
    
    private float baseScore;
    
    private float toneScore;
    
    private float finalScore;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ToneScore> getToneScores() {
        return toneScores;
    }

    public void setToneScores(List<ToneScore> toneScores) {
        this.toneScores = toneScores;
    }

    public float getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(float baseScore) {
        this.baseScore = baseScore;
    }

    public float getToneScore() {
        return toneScore;
    }

    public void setToneScore(float toneScore) {
        this.toneScore = toneScore;
    }

    public float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        this.finalScore = finalScore;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }   
    
}
