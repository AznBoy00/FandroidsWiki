package org.wikipedia.citation;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CitationGeneratorTest {
    //Initialize Varibles
    Context context;
    Intent intent;
    DateFormat date;
    DateFormat year;
    private String title;
    private String URL;
    private Calendar calendar;
    private final CitationStyle cs_APA = CitationStyle.APA;
    private final CitationStyle cs_MLA = CitationStyle.MLA;
    private final CitationStyle cs_IEEE = CitationStyle.IEEE;
    private final CitationStyle cs_LaTeX = CitationStyle.LATEX;


    @Before
    public void SetUp(){
        context = PowerMockito.mock(Context.class);
        intent = mock(Intent.class);
        date = mock(SimpleDateFormat.class);
        year = mock(SimpleDateFormat.class);
        calendar = mock(Calendar.class);



        title = "Wikipedia Test";
        URL = "https://www.wikipedia.com/Wikipedia_Test";
    }

    @Test
    public void IEEECitationGeneratorTest () {
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);

        assertThat(generator.IEEECitationGenerator(), containsString(date));
        assertThat(generator.IEEECitationGenerator(), containsString(year));
        assertThat(generator.IEEECitationGenerator(), containsString(title));
        assertThat(generator.IEEECitationGenerator(), containsString(URL));
    }

    @Test
    public void LatexCitationGeneratorTest () {
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);

        assertThat(generator.LatexCitationGenerator(), containsString(date));
        assertThat(generator.LatexCitationGenerator(), containsString(year));
        assertThat(generator.LatexCitationGenerator(), containsString(title));
        assertThat(generator.LatexCitationGenerator(), containsString(URL));
    }

    @Test
    public void APACitationGeneratorTest(){
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);

        assertThat(generator.APACitationGenerator(), containsString(date));
        assertThat(generator.APACitationGenerator(), containsString(year));
        assertThat(generator.APACitationGenerator(), containsString(title));
        assertThat(generator.APACitationGenerator(), containsString(URL));
    }

    @Test
    public void MLACitationGeneratorTest(){
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);

        assertThat(generator.MLACitationGenerator(), containsString(date));
        assertThat(generator.MLACitationGenerator(), containsString(year));
        assertThat(generator.MLACitationGenerator(), containsString(title));
        assertThat(generator.MLACitationGenerator(), containsString(URL));
    }

    @Test
    public void citationCookAPASuccessTest(){}{
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);
        String str = generator.citationCook(cs_APA);

        assertThat(str,containsString(title + ". (n.d.). " + "In Wikipedia: The free encyclopedia. Retrieved "));
    }

    @Test
    public void citationCookMLASuccessTest(){}{
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);
        String str = generator.citationCook(cs_MLA);

        assertThat(str,containsString('"' + title + "," + '"' + " Wikipedia: The Free Encyclopedia. Web. "));
    }

    @Test
    public void citationCookIEEESuccessTest(){}{
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);
        String str = generator.citationCook(cs_IEEE);

        assertThat(str,containsString('"' + title + "," + '"' + " Wikipedia,"));
    }

    @Test
    public void citationCookLATEXSuccessTest(){}{
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);
        String str = generator.citationCook(cs_LaTeX);

        assertThat(str,containsString("@misc{ wiki:###, \n"));
    }

}