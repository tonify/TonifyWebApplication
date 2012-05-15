/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import jpa.entities.SearchResult;
import jpa.entities.ToneScore;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Diego
 */
@Stateless
@LocalBean
public class SearchActions {
    
    private static StandardAnalyzer analyzer;
    private static Directory index;
    
    public SearchActions ()
    {
        try {
            index = new SimpleFSDirectory(new File("C:\\Users\\Diego\\Desktop\\index"));
            analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<SearchResult> findResults (String query, List<String> tones)
    {
        List<SearchResult> results = new ArrayList<SearchResult>();
        
        
        try
        {
            // The "content" arg specifies the default field to use
            // when no field is explicitly specified in the query.
            Query q = new QueryParser(Version.LUCENE_CURRENT, "contents", analyzer)
            .parse(query);
            // int hitsPerPage = 10;

            IndexSearcher searcher = new IndexSearcher(index, true);
            TopScoreDocCollector collector = TopScoreDocCollector.create(searcher.maxDoc()+1, true);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            SearchResult[] partialResults = new SearchResult[hits.length];
            // Display the results
            // System.out.println("\nFound " + hits.length + " hits.");
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                float baseScore = hits[i].score;
                
                Document d = searcher.doc(docId);
                
                SearchResult page = new SearchResult();
                page.setTitle(d.get("title"));
                page.setURL(d.get("path"));
                page.setBaseScore(baseScore);
                
                List<ToneScore> toneScores = new ArrayList<ToneScore>();
                for (String tone : tones)
                {
                    ToneScore temp = new ToneScore();
                    temp.setTone(tone);
                    
                    String[] fields = d.get(tone).split(":");                    
                    temp.setScore(Float.parseFloat(fields[0]));
                    temp.setKeywords(fields.length > 1 ? fields[1] : "N/A");
                    toneScores.add(temp);
                }
                page.setToneScores(toneScores);
                
                float toneScore = 0;
                for (ToneScore s : toneScores)
                   toneScore += s.getScore() / 100.0f;
                
                if (toneScores.size() > 0
                        && d.get("contents").split("\\s+").length > 200)
                    toneScore /= toneScores.size();
                else if (toneScores.size() > 0)
                    toneScore = 0.1f;
                else                    
                    toneScore = 1;
                
                page.setToneScore(toneScore);
                page.setFinalScore(toneScore*baseScore);
                
                partialResults[i] = page;
                //System.out.println("\t" + d.get("positive"));
            }
            
            Arrays.sort(partialResults, new Comparator<SearchResult>()
            {

                @Override
                public int compare(SearchResult o1, SearchResult o2) {
                    float finalScore1 = o1.getFinalScore();
                    float finalScore2 = o2.getFinalScore();
                    
                    if (finalScore1 < finalScore2)
                        return 1;
                    else if (finalScore1 > finalScore2)
                        return -1;
                    else
                        return 0;
                }
                
            });
            
            for (int i = 0; i < 30; i++)
            {
                results.add(partialResults[i]);
                partialResults[i].setRank(i+1);
            }

            // Searcher can only be closed when there
            // is no need to access the documents any more.
            searcher.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return results;
    }
    
}
