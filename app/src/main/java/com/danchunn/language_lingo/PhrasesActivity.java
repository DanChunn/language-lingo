package com.danchunn.language_lingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;

//TODO: class is very similar to CategoriesActivity, could do some redesign and refactoring
public class PhrasesActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.language_lingo.CAT_AND_PHRASE_ID";

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
        categoryIndex = Integer.parseInt(intentMessage);
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
            button.setOnClickListener(phraseButtonClicked);
            buttons[i] = button;
            linear.addView(button);
        }
    }

    OnClickListener phraseButtonClicked = new OnClickListener() {
        @Override
        public void onClick(View view){
            Intent intent=new Intent(view.getContext(),TranslationsActivity.class);
            Object tag = view.getTag();
            String extraMessage = categoryIndex + " " + tag;
            intent.putExtra(EXTRA_MESSAGE, extraMessage);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), tag + " clicked button", Toast.LENGTH_SHORT).show();
        }
    };

    protected Button createButton(String text, int tag){
        Button button = new Button(this);
        button.setText(text);
        button.setTag(tag);
        return button;
    }
}
