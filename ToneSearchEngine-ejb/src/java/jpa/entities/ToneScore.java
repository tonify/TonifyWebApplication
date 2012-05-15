/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;

/**
 * A POJO that stores a document's tone score with respect to a specific
 * tone, as well as the keywords that influenced the result
 * @author Diego Perez Botero
 */
public class ToneScore implements Serializable {
    
    private String tone;
    
    private float score;
    
    private String keywords;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    } 
    
}
