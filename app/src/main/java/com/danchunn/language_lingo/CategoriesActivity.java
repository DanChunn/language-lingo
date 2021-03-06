package com.danchunn.language_lingo;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.language_lingo.CATEGORY_ID";
    private final String myGitHub = "github.com/danchunn";
    private final String jsonFile = "jpn.json";
    private LanguagePack languagePackObj;
    Button[] buttons;
    LinearLayout linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        LanguagePack languagePack = readJson();
        buttons = new Button[languagePack.numberOfCategories()];
        languagePack.print();
        Blackboard.getInstance().addLanguagePack(languagePack);
        Blackboard.getInstance().setCurrentLanguagePack(languagePack.getLanguage());

        //fill categories
        linear = (LinearLayout) findViewById(R.id.categories);
        TextView meTextView = new TextView(this);
        meTextView.setText(myGitHub); //remove eventually
        linear.addView(meTextView);
        fillList();
    }

    protected void fillList(){
        ArrayList<Category> categories = languagePackObj.getCategories();
        for(int i = 0; i < categories.size(); i++){
            Button button = createButton(categories.get(i).getCategoryName(), i);
            button.setOnClickListener(categoryButtonClicked);
            buttons[i] = button;
            linear.addView(button);
        }
    }



    //consider using fragments?
    //activities - want to be slim as possible, fragments should do work
    //more reading here: https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity
    OnClickListener categoryButtonClicked = new OnClickListener() {
        @Override
        public void onClick(View view){
            Intent intent=new Intent(view.getContext(),PhrasesActivity.class);
            Object tag = view.getTag();
            intent.putExtra(EXTRA_MESSAGE, tag.toString());
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

    //parses json file to add phrases to categories and categories to a languagepack
    //https://stackoverflow.com/questions/9151619/how-to-iterate-over-a-jsonobject
    protected LanguagePack readJson(){
        String in = loadJSONFromAsset();
        try {
            //finds array of categories
            JSONObject reader = new JSONObject(in);
            JSONArray categoriesArray = reader.getJSONArray("categories");
            languagePackObj = new LanguagePack(reader.getString("language"));

            //iterate through categories to find array of phrases
            for(int i = 0; i < categoriesArray.length(); i++){
                JSONObject categoryJSONObject = categoriesArray.getJSONObject(i);
                String categoryName = categoryJSONObject.getString("category");
                Category categoryObj = new Category(categoryName);

                //iterate through phrases
                JSONArray phrasesArray = categoryJSONObject.getJSONArray("phrases");
                for(int j = 0; j < phrasesArray.length(); j++){
                    JSONObject phraseJSONObject = phrasesArray.getJSONObject(j);
                    String phrase = phraseJSONObject.getString("phrase");
                    String originalTranslation = phraseJSONObject.getString("originalTranslation");
                    String romanization = phraseJSONObject.getString("romanization");

                    Phrase phraseObj = new Phrase(phrase, originalTranslation, romanization);
                    categoryObj.addPhrase(phraseObj);
                }
                languagePackObj.addCategory(categoryObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return languagePackObj;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
