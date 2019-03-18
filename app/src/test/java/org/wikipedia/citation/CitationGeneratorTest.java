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

public class CitationGeneratorTest {
    //Initialize Varibles
    Context context;
    Intent intent;
    DateFormat date;
    DateFormat year;
    private String title;
    private String URL;
    private Calendar calendar;


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
    public void IEEECitationGenerator () {
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);

        assertThat(generator.IEEECitationGenerator(), containsString(date));
        assertThat(generator.IEEECitationGenerator(), containsString(year));
        assertThat(generator.IEEECitationGenerator(), containsString(title));
        assertThat(generator.IEEECitationGenerator(), containsString(URL));
    }

    @Test
    public void LatexCitationGenerator () {
        String date = "21 March 2018";
        String year = "2018";
        CitationGenerator generator = new CitationGenerator(title, URL, date, year);

        assertThat(generator.LatexCitationGenerator(), containsString(date));
        assertThat(generator.LatexCitationGenerator(), containsString(year));
        assertThat(generator.LatexCitationGenerator(), containsString(title));
        assertThat(generator.LatexCitationGenerator(), containsString(URL));
    }
}