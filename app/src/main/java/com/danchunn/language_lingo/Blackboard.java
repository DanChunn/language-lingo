package com.danchunn.language_lingo;

//used to hold data

import java.util.ArrayList;
import java.util.HashMap;


//Singleton to hold data
public class Blackboard {

    private static final HashMap<String, LanguagePack> languagePackMap = new HashMap<>();
    private static final Blackboard blackboard = new Blackboard();

    private static LanguagePack currentLanguagePack;

    public static Blackboard getInstance() {
        return blackboard;
    }

    public LanguagePack getLanguagePack(String language){
        if(languagePackMap.containsKey(language)){
            return languagePackMap.get(language);
        }
        return null;
    }

    public void addLanguagePack(LanguagePack languagePack){
        languagePackMap.put(languagePack.getLanguage(), languagePack);
    }

    public void setCurrentLanguagePack(LanguagePack languagePack){
        currentLanguagePack = languagePack;
    }

    //add exceptions
    public void setCurrentLanguagePack(String language){
        if(languagePackMap.containsKey(language)) {
            currentLanguagePack = languagePackMap.get(language);
        }
    }

    public LanguagePack getCurrentLanguagePack(){
        return currentLanguagePack;
    }


}
