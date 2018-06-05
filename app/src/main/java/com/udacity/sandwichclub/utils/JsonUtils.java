package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_TITLE= "mainName";
    private static final String SANDWICH_ALSO_KNOWN_AS= "alsoKnownAs";
    private static final String SANDWICH_PLACE_ORIGIN= "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION= "description";
    private static final String SANDWICH_IMAGE= "image";
    private static final String SANDWICH_INGREDIENTS= "ingredients";





    public static Sandwich parseSandwichJson(String json) {

        JSONObject jsonObject;
        String title = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();
        List<String> alsoKnownAs = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json);
            JSONObject jsonObjectName = jsonObject.getJSONObject(SANDWICH_NAME);
            title = jsonObjectName.optString(SANDWICH_TITLE);
            placeOfOrigin = jsonObject.optString(SANDWICH_PLACE_ORIGIN);
            description = jsonObject.optString(SANDWICH_DESCRIPTION);
            image = jsonObject.optString(SANDWICH_IMAGE);
            alsoKnownAs = jsonArrayList(jsonObjectName.getJSONArray(SANDWICH_ALSO_KNOWN_AS));
            ingredients = jsonArrayList(jsonObject.getJSONArray(SANDWICH_INGREDIENTS));


        } catch (JSONException t) {
            Log.e(TAG, "Something is wrong",t);
        }
        return new Sandwich(title, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> jsonArrayList(JSONArray jsonArray){
        List<String> list = new ArrayList<>(0);
        if (jsonArray!=null){
            for (int i=0; i<jsonArray.length();i++){
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    Log.e(TAG, "Problems with array list", e);
                }
            }
        }
        return list;
    }
}
