package org.wikipedia.citation;

import java.util.Date;

import org.wikipedia.citation.CitationStyle;

public class CitationGenerator {
    private String outPutString;
    private final String _WIKIPEDIA = "Wikipedia";
    private String citationDate;
    private String link;
    private CitationStyle citStyle;
    private String textFormat;

    public CitationGenerator(){
        outPutString = "";
        citationDate = "";
        link = "";
        textFormat = "";
        citStyle = CitationStyle.APA;
    }

    public CitationGenerator(String keyWord, String link )
    {
        outPutString = "";
        citationDate = "";
        this.link = "";
        textFormat = "";
        citStyle = CitationStyle.APA;
    }

    public void setCitationDate(String date)
    {
        this.setCitationDate(date, "","","");
    }

    public void setCitationDate(String date, String month, String day, String year)
    {
        citationDate = date;
    }




    public String getOutPutString(){
        return  outPutString;
    }


}
