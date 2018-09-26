package com.example.user.afteryousiami.DAO;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.user.afteryousiami.objects.Attraction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttractionDAO {

    private AssetManager asset;

    public AttractionDAO(AssetManager asset) {
        this.asset = asset;


    }

    /***
     * returns a list of attractions from STB api
     * @return
     */
    public List<Attraction> getAllAttractions() throws IOException {
        List<Attraction> list = new ArrayList<>();
//        List<String> categories = getAllAttractionCategories();       //error with SSL
        String request = "https://tih-api.stb.gov.sg/content/v1/tag?apikey=t5XblPNbklytRwIx6yGIx1RE2cfiucBW";

        //using external json as a quick fix
        String response = Connection.getPostResponseFromExternalFile("attraction.json", asset);


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject currObject = array.getJSONObject(i);
                String type = currObject.getString("type");
                String name= currObject.getString("name");
                double rating = currObject.getDouble("rating");

                Attraction attraction = new Attraction(type, name, 0, rating);  //missing out the price because the price is not in this dataset
                list.add(attraction);
            }
        } catch (JSONException e) {
            printError(e);
        }

        Collections.sort(list);     //sorts the list based on the comparable implementation

        //for debug
        for(Attraction a : list){
            printDebug(a);
        }
        return list;
    }

    /***
     * gets the list of attraction categories from the web server to be used to get all of the attractions
     * @return string list of attraction categories
     */
    private List<String> getAllAttractionCategories() {
        List<String> list = new ArrayList<>();
        String request = "https://tih-api.stb.gov.sg/content/v1/tag?apikey=t5XblPNbklytRwIx6yGIx1RE2cfiucBW";
        String nextToken = "";          //the api uses next token to segregate their data
        boolean stillHave = false;

        do {
            printDebug("getting attraction category...");

            //if nexttoken is not empty, add to the request
            if (!nextToken.isEmpty()) {
                request += "&nextToken=" + nextToken;
            }

            try {
                String response = Connection.sendGet(request, null);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray array = jsonObject.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject currObj = array.getJSONObject(i);
                    list.add(currObj.getString("name"));        //saves the name of the category
                }

                //check if there is a next token
                try {
                    nextToken = jsonObject.getString("nextToken");
                } catch (JSONException e) {
                    printError(e);
                }

                //reset the request to prevent duplicated nexttoken parameter
                request = "https://tih-api.stb.gov.sg/content/v1/tag?apikey=t5XblPNbklytRwIx6yGIx1RE2cfiucBW";


            } catch (Exception e) {
                printError(e);
            }
        } while (stillHave);

        return list;
    }


    private static void printDebug(Object message) {
        Log.d("AttractionDAO.java", message.toString());
    }

    private static void printError(Exception e) {
        Log.e("AttractionDAO.java", "error", e);
    }

}
