package com.danchunn.language_lingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.R.attr.category;
import static android.R.interpolator.linear;
import static com.danchunn.language_lingo.R.id.categories;
import static com.danchunn.language_lingo.R.id.phrases;

//TODO: class is very similar to CategoriesActivity, could do some redesign and refactoring
public class PhrasesActivity extends AppCompatActivity {

    private LanguagePack languagePackObj;
    private int categoryIndex;
    Button[] buttons;
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        Intent intent = getIntent();
        String intentMessage = intent.getStringExtra(CategoriesActivity.EXTRA_MESSAGE);
        int categoryIndex = Integer.parseInt(intentMessage);
        languagePackObj = Blackboard.getInstance().getCurrentLanguagePack();
        Log.d("PHRACT", "CategoryID : " +  categoryIndex + " of " + languagePackObj.getLanguage());
        linear = (LinearLayout) findViewById(R.id.phrases);
        fillList();
    }

    protected void fillList(){

        Category category = languagePackObj.getCategory(categoryIndex);
        ArrayList<Phrase> phrases = category.getPhrases();
        buttons = new Button[phrases.size()];
        for(int i = 0; i < phrases.size(); i++){
            Button button = createButton(phrases.get(i).getPhrase(), i);
            //button.setOnClickListener(categoryButtonClicked);
            buttons[i] = button;

            linear.addView(button);
        }
    }

    protected Button createButton(String text, int tag){
        Button button = new Button(this);
        button.setText(text);
        button.setTag(tag);
        return button;
    }
}
