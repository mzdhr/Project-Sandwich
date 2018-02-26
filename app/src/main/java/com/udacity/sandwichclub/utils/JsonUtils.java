package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        // Creating JSON Objects and Arrays
        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonObjectName = jsonObject.getJSONObject("name");
        JSONArray jsonArrayAlsoKnownAs = jsonObjectName.getJSONArray("alsoKnownAs");
        JSONArray jsonArrayIngredients = jsonObject.getJSONArray("ingredients");

        // Getting values from JSON Objects into my strings
        String mainName = jsonObjectName.getString("mainName");
        // Handling if there is no value in "placeOfOrigin"
        String placeOfOrigin = jsonObject.getString("placeOfOrigin");
        if (placeOfOrigin.isEmpty()) {
            placeOfOrigin = "Unknown";
        }
        String description = jsonObject.getString("description");
        String image = jsonObject.getString("image");

        // Getting values from JSON Arrays into my strings list
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        // Handling if there is no values in "alsoKnownAs"
        if (jsonArrayAlsoKnownAs.length() != 0) {
            for (int i = 0; i < jsonArrayAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(String.valueOf(jsonArrayAlsoKnownAs.get(i)) + "\n");
            }
        } else {
            alsoKnownAs.add("None");
        }

        for (int i = 0; i < jsonArrayIngredients.length(); i++) {
            ingredients.add(String.valueOf(jsonArrayIngredients.get(i)) + "\n");
        }

        // Creating Sandwich object from extracted values, and return it to the caller in DetailActivity
        Sandwich sandwich = new Sandwich(mainName,
                alsoKnownAs,
                placeOfOrigin,
                description,
                image,
                ingredients);

        return sandwich;
    }
}
