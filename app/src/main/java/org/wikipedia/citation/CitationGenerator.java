package org.wikipedia.citation;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.wikipedia.citation.CitationStyle;
import org.wikipedia.page.PageBackStackItem;

import java.util.Calendar;
public class CitationGenerator {
    private String outPutString;
    private final String _WIKIPEDIA = "Wikipedia";
    private String citationDate;
    private String link;
    private String mobileURL;
    private CitationStyle citStyle;
    private String textFormat;
    private String currentDate;

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

    public CitationGenerator (String URL){
        mobileURL = URL;
        setDateTime();
    }

    public void setDateTime() {
        DateFormat df = new SimpleDateFormat("d MMM yyyy");
        this.currentDate = df.format(Calendar.getInstance().getTime());
    }

    public String IEEECitationGenerator(String ArticleTitle){
        String citeBuilder = "";
        citeBuilder += "\"" + ArticleTitle + ",\"";
        citeBuilder += " Wikipedia,";
        citeBuilder += currentDate;
        citeBuilder += ". [Online]. Available: ";
        citeBuilder += mobileURL;
        citeBuilder += ". [Accessed: " + currentDate + "].";

        return citeBuilder;
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
