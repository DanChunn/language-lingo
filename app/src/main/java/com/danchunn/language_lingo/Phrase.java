package com.danchunn.language_lingo;

import android.util.Log;

/**
 * Created by echo on 8/17/17.
 */

public class Phrase {

    private final String phrase;
    private final String japaneseTranslation;
    private final String romanization;

    public Phrase(String phrase, String japaneseTranslation, String romanization){
        this.phrase = phrase;
        this.japaneseTranslation = japaneseTranslation;
        this.romanization = romanization;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getJapaneseTranslation() {
        return japaneseTranslation;
    }

    public String getRomanization() {
        return romanization;
    }

    public void print(){
        Log.d("PHRASE", "    phrase: " +  phrase);
        Log.d("PHRASE", "      japaneseTranslation: " +  japaneseTranslation);
        Log.d("PHRASE", "      romanization: " +  romanization);
    }
}
