package org.wikipedia.citation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.wikipedia.R;

public class CitationActivity extends AppCompatActivity {

    private RadioGroup citationStyleGroup;
    private RadioButton citationStyleBtn;

    private RadioButton citationStyleBtn_APA;
    private RadioButton citationStyleBtn_MLA;
    private RadioButton citationStyleBtn_IEEE;
    private RadioButton citationStyleBtn_LATEX;

    private Button citationLaTeXBtn;

    private Button copyClip;

    private String URLText;
    private String TitleText;
    private TextView citation_box;
    private CitationGenerator generator;
    private String citation;
    private CitationStyle citationStyle;

    private final String logCitation = "citation";
    //private RadioGroup.OnCheckedChangeListener radio_group_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.page_toolbar);
        setSupportActionBar(toolbar);

        citationStyleGroup = (RadioGroup) findViewById(R.id.citation_radiogroup_btn);
        citationStyleBtn_APA = citationStyleGroup.findViewById(R.id.button_apa);
        citationStyleBtn_MLA = citationStyleGroup.findViewById(R.id.button_mla);
        citationStyleBtn_IEEE = citationStyleGroup.findViewById(R.id.button_ieee);
        citationStyleBtn_LATEX = citationStyleGroup.findViewById(R.id.button_latex);
        copyClip = findViewById(R.id.button_copy_citation);
        setCitationStyleBtnBG(R.id.button_apa);

        // Get Page Information
        Intent intent = getIntent();
        this.URLText = intent.getStringExtra("item_MobileURI");
        this.TitleText = intent.getStringExtra("item_Title");

        this.citation_box = (TextView) findViewById(R.id.citation_box_text);

        // !!the following order is very important!!
        generator =  new CitationGenerator(URLText, TitleText);
        citationStyle = CitationStyle.APA;
        addListenerOnRadioGroupButton();
        citationCooker(citationStyle);
        addListenerOnClipBoardBtn();
    }

    public void addListenerOnRadioGroupButton() {
        citationStyleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                citationStyleBtn = group.findViewById(checkedId);
                Log.e(logCitation,"real id "+checkedId);
                if (citationStyleBtn != null && checkedId != -1) {
                    setCitationStyleBtnBG(checkedId);
                    if(checkedId == R.id.button_apa) {
                        Log.e(logCitation,"check  APA");
                        citationStyle = CitationStyle.APA;
                    }
                    if(checkedId == R.id.button_mla) {
                        Log.e(logCitation,"check  MLA");
                        citationStyle = CitationStyle.MLA;
                    }
                    if(checkedId == R.id.button_ieee) {
                        Log.e(logCitation,"check  IEEE");
                        citationStyle = CitationStyle.IEEE;
                    }
                    if(checkedId == R.id.button_latex) {
                        Log.e(logCitation,"check  LaTeX");
                        citationStyle = CitationStyle.LATEX;
                    }

                    citationCooker(citationStyle);
                    Toast.makeText(CitationActivity.this, citationStyleBtn.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setCitationStyleBtnBG(int checkedId){
        if(checkedId == R.id.button_apa) {
            citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_LATEX.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_APA.setTextColor(R.color.color_state_black);
            citationStyleBtn_MLA.setTextColor(R.color.color_state_white);
            citationStyleBtn_IEEE.setTextColor(R.color.color_state_white);
            citationStyleBtn_LATEX.setTextColor(R.color.color_state_white);

        }
        if(checkedId == R.id.button_mla) {
            citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_LATEX.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_APA.setTextColor(R.color.color_state_white);
            citationStyleBtn_MLA.setTextColor(R.color.color_state_black);
            citationStyleBtn_IEEE.setTextColor(R.color.color_state_white);
            citationStyleBtn_LATEX.setTextColor(R.color.color_state_white);
        }
        if(checkedId == R.id.button_ieee) {
            citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationStyleBtn_LATEX.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_APA.setTextColor(R.color.color_state_white);
            citationStyleBtn_MLA.setTextColor(R.color.color_state_white);
            citationStyleBtn_IEEE.setTextColor(R.color.color_state_black);
            citationStyleBtn_LATEX.setTextColor(R.color.color_state_white);
        }
        if(checkedId == R.id.button_latex) {
            citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_LATEX.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationStyleBtn_APA.setTextColor(R.color.color_state_white);
            citationStyleBtn_MLA.setTextColor(R.color.color_state_white);
            citationStyleBtn_IEEE.setTextColor(R.color.color_state_white);
            citationStyleBtn_LATEX.setTextColor(R.color.color_state_black);
        }
    }

    //this is for the button style change
    public void uncheckStyleBtnGroupBG(){
        citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_unselected);
        citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_unselected);
        citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_unselected);
        citationStyleBtn_APA.setTextColor(R.color.color_state_white);
        citationStyleBtn_MLA.setTextColor(R.color.color_state_white);
        citationStyleBtn_IEEE.setTextColor(R.color.color_state_white);
    }

    public void addListenerOnClipBoardBtn(){
        copyClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("citation",citation);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(CitationActivity.this, "Copy Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void citationCooker(CitationStyle citationStyle)
    {
        Log.e(logCitation,"string cook");
        citation = generator.citationCook(citationStyle);
        citation_box.setText(citation);
    }

}
