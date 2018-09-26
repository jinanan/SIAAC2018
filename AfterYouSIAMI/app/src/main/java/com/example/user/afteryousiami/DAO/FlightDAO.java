package com.example.user.afteryousiami.DAO;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.user.afteryousiami.objects.Flight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class FlightDAO {

    private AssetManager asset; //used to access the properties file
    private String keyValue;    //value of the api key e.g. x-apiKey
    private String key;         //value of the key e.g. some hashed stuff

    public FlightDAO(AssetManager asset) {
        try {
            this.asset = asset;

            Properties prop = new Properties();              //access the properties file
            prop.load(asset.open("app.properties"));

            keyValue = prop.getProperty("sita.keyvalue");
            key = prop.getProperty("sita.key");

        } catch (IOException e) {
            printError(e);
        }
    }

    /***
     * gets flight details based on the firstname of the passenger
     * @param firstname firstname of the passenger
     * @return flight details such as: time of departure,
     */
    public Flight getFlightDetailsOfPax(String firstname) {
        return null;
    }

    /***
     * get list of alternative flights via the SIA json API request
     * @param origin origin of the flight
     * @param dest destination of the flight
     * @param date date to fly
     * @return return list of alternative flights
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    public List<Flight> getAlternativeFlights(String origin, String dest, Date date) throws IOException, JSONException, ParseException {
        ArrayList<Flight> list = new ArrayList();

        //post the request and return the json string
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");   //2018-09-24T09:20:00
        String result = Connection.getPostResponseFromExternalFile("flightschedule.json", asset);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject rootObject = jsonObject.getJSONObject("response").getJSONObject("departureFlights");
        JSONArray flightsArray = rootObject.getJSONArray("flights");
        String flightNo = rootObject.getJSONArray("flightLegs").getJSONObject(0).getString("flightNumber");      //get the flight number of the first object in the array only

        for (int i = 0; i < flightsArray.length(); i++) {
            JSONObject currObj = flightsArray.getJSONObject(i);
            Date departureDate = null;
            JSONArray segments = currObj.getJSONArray("segments");      //probably different segments for different leg of the flights
            for (int j = 0; j < segments.length(); j++) {
                String dateInString = segments.getJSONObject(j).getJSONArray("legs").getJSONObject(0).getString("departureDateTime");
                departureDate = formatter.parse(dateInString);

            }

            list.add(new Flight(origin, dest, flightNo, departureDate));
        }


        for (Flight f : list) {
            printDebug(f);
        }

        return list;

    }



    public List<String> getListOfIATAAirportCodes() {
        ArrayList<String> list = new ArrayList<>();
        list.add("SIN");
        list.add("HKG");


        return list;
    }


    private static void printDebug(Object message) {
        Log.d("FlightDAO.java", message.toString());
    }

    private static void printError(Exception e) {
        Log.e("FlightDAO.java", "error", e);
    }

    /***
     * (METHOD IS NOT USED) gets a list of alternate flights  + 4 hours from the origin airport
     * @param origin origin of the user (airport code)
     * @return empty list if there is an error or there is no flights
     */
    private List<Flight> getAlternateFlightsFromSita(String origin) {
        ArrayList<Flight> list = new ArrayList();
        try {
            String request = "https://flifo-qa.api.aero/flifo/v3/flights/" + origin + "/d?futureWindow=4";
            String keyvaluePair = keyValue + "," + key;
            String response = Connection.sendGet(request, keyvaluePair);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("flightRecord");

            try {
                //add to the flight object
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject rootObj = jsonArray.getJSONObject(i);
                    //ignore all that are not "SQ" flights
                    JSONObject jsonObject_operatingCarrier = rootObj.getJSONObject("operatingCarrier");
                    if (jsonObject_operatingCarrier.getString("airlineCode").equals("SQ")) {  //only process the data if its "SQ"
                        String destination = rootObj.getString("airportCode");
                        String flightNo = jsonObject_operatingCarrier.getString("flightNumber");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");   //2018-09-24T09:20:00+0800
                        String dateInString = rootObj.getString("scheduled");
                        Date departureDate = formatter.parse(dateInString);
                        Flight flight = new Flight(origin, destination, flightNo, departureDate);
                        list.add(flight);
                    }
                }
            } catch (ParseException e) {
                printError(e);
            }
        } catch (Exception e) {
            printError(e);
        }

        return list;
    }


}