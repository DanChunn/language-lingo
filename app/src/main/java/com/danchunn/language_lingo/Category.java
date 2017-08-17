package com.danchunn.language_lingo;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by echo on 8/17/17.
 */

public class Category {

    private final String category;
    private final ArrayList<Phrase> phrases;

    public Category(String category){
        this.category = category;
        phrases = new ArrayList<>();
    }

    public void addPhrase(Phrase phrase){
        phrases.add(phrase);
    }

    public String getCategoryName(){
        return category;
    }

    public void printPhrases(){
        Log.d("CATGRY", "  category: " +  category);
        for(int i = 0; i < phrases.size(); i++){
            Phrase phrase = phrases.get(i);
            phrase.print();
        }
    }
}
