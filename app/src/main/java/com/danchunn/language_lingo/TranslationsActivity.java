package com.danchunn.language_lingo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TranslationsActivity extends AppCompatActivity {


    private LanguagePack languagePackObj;
    private int categoryIndex;
    private int phraseIndex;
    TextView[] textViews;
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        Intent intent = getIntent();
        String intentMessage = intent.getStringExtra(PhrasesActivity.EXTRA_MESSAGE);
        String intentMessageArray[] = intentMessage.split("\\s+");
        categoryIndex = Integer.parseInt(intentMessageArray[0]);
        phraseIndex = Integer.parseInt(intentMessageArray[1]);
        languagePackObj = Blackboard.getInstance().getCurrentLanguagePack();
        Log.d("TRNACT", "CategoryID : " + categoryIndex + " PhraseID : " +  phraseIndex + " of " + languagePackObj.getLanguage());
        linear = (LinearLayout) findViewById(R.id.translations);
        fillList();
    }

    protected void fillList(){
        Category category = languagePackObj.getCategory(categoryIndex);
        Phrase phrase = category.getPhrase(phraseIndex);
        textViews = new TextView[Phrase.PHRASE_SIZE];
        textViews[0] = new TextView(this);
        textViews[0].setText(phrase.getPhrase());
        textViews[0].setPadding(0,100,0,35);
        textViews[1] = new TextView(this);
        textViews[1].setText(phrase.getOriginalTranslation());
        textViews[1].setTextColor(Color.BLACK);
        textViews[2] = new TextView(this);
        textViews[2].setText(phrase.getRomanization());
        textViews[2].setTextColor(Color.BLACK);
        for(int i = 0; i < textViews.length; i++){
            textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            textViews[i].setGravity(Gravity.CENTER);
            linear.addView(textViews[i]);
        }
    }
}
