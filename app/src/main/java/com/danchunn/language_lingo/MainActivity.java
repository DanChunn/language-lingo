package com.danchunn.language_lingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.R.attr.category;
import static android.R.attr.key;

public class MainActivity extends AppCompatActivity {
    private final String jsonFile = "jpn.json";
    private LanguagePack languagePackObj;
    Button[] buttons;
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LanguagePack languagePack = readJson();
        buttons = new Button[languagePack.numberOfCategories()];
        languagePack.printCategories();

        //fill categories
        linear = (LinearLayout) findViewById(R.id.categories);
        fillList();
    }

    protected void fillList(){
        ArrayList<Category> categories = languagePackObj.getCategories();
        for(int i = 0; i < categories.size(); i++){
            Button button = createButton(categories.get(i).getCategoryName(), i);
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

    private void printLanguagePack(LanguagePack lp) {
        lp.printCategories();
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
