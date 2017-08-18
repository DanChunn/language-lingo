package com.danchunn.language_lingo;

import android.util.Log;

public class Phrase {

    private final String phrase;
    private final String originalTranslation;
    private final String romanization;

    public Phrase(String phrase, String originalTranslation, String romanization){
        this.phrase = phrase;
        this.originalTranslation = originalTranslation;
        this.romanization = romanization;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getOriginalTranslation() {
        return originalTranslation;
    }

    public String getRomanization() {
        return romanization;
    }

    public void print(){
        Log.d("PHRASE", "    phrase: " +  phrase);
        Log.d("PHRASE", "      originalTranslation: " +  originalTranslation);
        Log.d("PHRASE", "      romanization: " +  romanization);
    }
}
