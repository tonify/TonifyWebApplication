/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejbs.SearchActions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.entities.SearchResult;

/**
 * ManagedBean that exposes the search engine's user options and invokes
 * the appropriate EJBs when a query is submitted
 * @author Diego Perez Botero
 */
@ManagedBean
@SessionScoped
public class SearchManagedBean {
    
    @EJB
    private SearchActions searchActions;
    
    private List<SearchResult> results;
    
    private String selectedTone1;    
    private Map<String,String> possibleTones1;  
    
    private String selectedTone2;    
    private Map<String,String> possibleTones2; 
    
    private String selectedTone3;    
    private Map<String,String> possibleTones3; 
    
    private String query;

    /**
     * Creates a new instance of SearchManagedBean
     */
    public SearchManagedBean() {
        results = new ArrayList<SearchResult>();
        
        possibleTones1 = new HashMap<String, String>();
        possibleTones1.put("ALL", "all");
        possibleTones1.put("Positive", "positive");
        possibleTones1.put("Negative", "negative");
        selectedTone1 = "all";
        
        possibleTones2 = new HashMap<String, String>();
        possibleTones2.put("ALL", "all");
        possibleTones2.put("Happy", "happy");
        possibleTones2.put("Upset", "upset");
        selectedTone2 = "all";
        
        possibleTones3 = new HashMap<String, String>();
        possibleTones3.put("ALL", "all");
        possibleTones3.put("Corporate", "Corporate");
        possibleTones3.put("Personal", "Personal");
        selectedTone3 = "all";
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    public void executeQuery()
    {
        List<String> selectedTones = new ArrayList<String>();
        
        if (!selectedTone1.equals("all"))
            selectedTones.add(selectedTone1);
        
        if (!selectedTone2.equals("all"))
            selectedTones.add(selectedTone2);
        
        if (!selectedTone3.equals("all"))
            selectedTones.add(selectedTone3);
        
        results = searchActions.findResults(query, selectedTones);
    }
    
    public List<SearchResult> getResults()
    {        
        return results;
    }

    public String getSelectedTone1() {
        return selectedTone1;
    }

    public void setSelectedTone1(String selectedTone1) {
        this.selectedTone1 = selectedTone1;
    }   

    public Map<String, String> getPossibleTones1() {
        return possibleTones1;
    }

    public Map<String, String> getPossibleTones2() {
        return possibleTones2;
    }

    public void setPossibleTones2(Map<String, String> possibleTones2) {
        this.possibleTones2 = possibleTones2;
    }

    public Map<String, String> getPossibleTones3() {
        return possibleTones3;
    }

    public void setPossibleTones3(Map<String, String> possibleTones3) {
        this.possibleTones3 = possibleTones3;
    }

    public String getSelectedTone2() {
        return selectedTone2;
    }

    public void setSelectedTone2(String selectedTone2) {
        this.selectedTone2 = selectedTone2;
    }

    public String getSelectedTone3() {
        return selectedTone3;
    }

    public void setSelectedTone3(String selectedTone3) {
        this.selectedTone3 = selectedTone3;
    }   
}
