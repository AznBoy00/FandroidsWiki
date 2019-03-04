package org.wikipedia.citation;


import java.text.DateFormat;
import java.text.SimpleDateFormat;


import java.util.Calendar;
public class CitationGenerator {

    private String mobileURL;
    private String title;
    private String currentDate;
    private String currentYear;


    public CitationGenerator (String URL, String title){
        this.mobileURL = URL;
        this.title = title;
        setDateTime();
    }

    public void setDateTime() {
        DateFormat date = new SimpleDateFormat("d MMM yyyy");
        this.currentDate = date.format(Calendar.getInstance().getTime());
        DateFormat year = new SimpleDateFormat("yyyy");
        this.currentYear = year.format(Calendar.getInstance().getTime());
    }

    public String IEEECitationGenerator(){
        String citeBuilder = "";
        citeBuilder += "\"" + title + ",\"";
        citeBuilder += " Wikipedia,";
        citeBuilder += currentDate;
        citeBuilder += ". [Online]. Available: ";
        citeBuilder += mobileURL;
        citeBuilder += ". [Accessed: " + currentDate + "].";

        return citeBuilder;
    }

    public String LatexCitationGenerator(){
        String citeBuilder = "";
        citeBuilder += "@misc{ wiki:###, \n";
        citeBuilder += " \t author = \"{Wikipedia Contributors}\",\n";
        citeBuilder += " \t title = \"" + title + "\",\n";
        citeBuilder += " \t year = \"" + currentYear + "\",\n";
        citeBuilder += " \t url = \"" + mobileURL + "\",\n";
        citeBuilder += " \t note = \"[Online; accessed " + currentDate + "]\"\n";
        citeBuilder += "}";

        return citeBuilder;
    }

}
