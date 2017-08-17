package com.danchunn.language_lingo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by echo on 8/17/17.
 */

public class JsonHandler extends AppCompatActivity {
    private final String jsonFile = "jpn.json";

    public JsonHandler(){}


    //https://stackoverflow.com/questions/9151619/how-to-iterate-over-a-jsonobject
    public void readJson(){
        String in = loadJSONFromAsset();
        //Log.d("CREATION", "in: " +in);
        try {
            HashMap<String, HashMap<String, ArrayList<String>>> categoryMap = new HashMap<>(); //categories and map of phrases
            JSONObject reader = new JSONObject(in);
            JSONArray categoriesArray = reader.getJSONArray("categories");
            JSONObject jpp = categoriesArray.getJSONObject(0);
            Iterator<?> languagePackKeys = jpp.keys();

            LanguagePack languagePack = new LanguagePack("s");

            while( languagePackKeys.hasNext() ) {
                String key = (String)languagePackKeys.next();
                JSONObject categoryObj = jpp.getJSONObject(key);

                Iterator<?> categoryObjKeys = categoryObj.keys();
                //Log.d("CREATION", "key: " + key); //food, travel, etc.
                HashMap<String, ArrayList<String>> emptyMap = new HashMap<>();
                categoryMap.put(key, emptyMap);

                while(categoryObjKeys.hasNext()){
                    String innerKey = (String)categoryObjKeys.next();
                    JSONArray textArray = categoryObj.getJSONArray(innerKey);

                    ArrayList<String> list = JSONArrayToStringArray(textArray);
                    /*
                    for(int i = 0; i < list.size(); i++){
                        Log.d("CREATION", "list element: " + list.get(i));
                    }*/

                    HashMap<String, ArrayList<String>> tempPhraseMap = categoryMap.get(key);
                    tempPhraseMap.put(innerKey,list);
                    //Log.d("CREATION", "phrase: " + innerKey + " : " + tempPhraseMap.get(innerKey));
                }


            }

            printMap(categoryMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void printMap( HashMap<String, HashMap<String, ArrayList<String>>> map){
        Log.d("CREATION", "Map Size: " +  map.size());

        for(HashMap.Entry<String, HashMap<String, ArrayList<String>>> entry : map.entrySet()){
            String key = entry.getKey();
            HashMap<String, ArrayList<String>> value = entry.getValue();
            Log.d("CREATION", "Category: " +  key);

            for(HashMap.Entry<String, ArrayList<String>> innerEntry : value.entrySet()) {
                String innerKey = innerEntry.getKey();
                ArrayList<String> innerValue = innerEntry.getValue();
                Log.d("CREATION", "  Phrase: " +  innerKey);
                for (int i = 0; i < innerValue.size(); i++) {
                    Log.d("CREATION", "   Translations: " +  innerValue.get(i));

                }
            }
        }
    }

    private ArrayList<String> JSONArrayToStringArray(JSONArray arr){
        ArrayList<String> list = new ArrayList<>();
        try {
            for(int i = 0; i < arr.length(); i++){
                list.add(arr.get(i).toString());
                //Log.d("CREATION", "Added to List: " + list.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
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
