package com.danchunn.language_lingo;

import android.util.Log;

import java.util.ArrayList;

import static android.R.attr.category;

/**
 * Created by echo on 8/17/17.
 */

public class LanguagePack {

    private final String language;
    private final ArrayList<Category> categories;

    public LanguagePack(String language){
        this.language = language;
        categories = new ArrayList<>();
    }

    public void addCategory(Category category){
        categories.add(category);
    }

    public String getLanguage(){
        return language;
    }

    public void printCategories(){
        Log.d("LNGPCK", "language: " +  language);
        for(int i = 0; i < categories.size(); i++){
            Category category = categories.get(i);
            category.printPhrases();
        }
    }

}
