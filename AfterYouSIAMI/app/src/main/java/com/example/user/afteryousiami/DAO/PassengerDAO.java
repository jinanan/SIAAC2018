package com.example.user.afteryousiami.DAO;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.user.afteryousiami.objects.Passenger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * class not being used as we can get the pax info from the web server instead
 */
public class PassengerDAO {

    private List<Passenger> paxList;
    private String flightNo;
    private Date flightDate;
    private AssetManager asset;


//    public PassengerDAO(String flightNo, Date flightDate, AssetManager asset) {
//        try {
//            this.flightDate = flightDate;
//            this.flightNo = flightNo;
//            this.asset = asset;
//
//            paxList = new ArrayList<>();
//            getPassengerInfo();     //invoke to populate the list
//        } catch (IOException e) {
//            printError(e);
//        }
//    }

    /***
     * (METHOD NOT USED) returns the information of the passenger such as KFnumber, KFtier, last name, first name, booking class
     * @return list of passengers
     */
//    private void getPassengerInfo() throws IOException {
//        try {
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
//            String date = df.format(flightDate);
//
//            //things needed to send as part of request: { "flightNo": "SQ890", "flightDate": "2018-07-20" }
//
//            //post the request and return the json string
//            String result = Connection.getPostResponseFromExternalFile("passenger.json", asset);
//            JSONObject jsonObject = new JSONObject(result);
//            JSONArray array = jsonObject.getJSONObject("response").getJSONArray("passengerList");
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject currObj = array.getJSONObject(i);
//                String firstname = currObj.getString("firstName");
//                String lastname = currObj.getString("lastName");
//                String bookingClass = currObj.getString("bookingClass");
//                String kfNumber = currObj.getString("KFNumber");
//                String KFTier = currObj.getString("KFTier");
//                String checkinStatus = currObj.getString("checkInStatus");
//
//                paxList.add(new Passenger(firstname, lastname, bookingClass, kfNumber, KFTier, checkinStatus));
//            }
//            printDebug("offloadpaxCount> " + paxOffLoadCount("123"));       //for debug
//
//        } catch (JSONException e) {
//            printError(e);
//        }
//    }

    /**
     * gets a list of pax to be off loaded based on a matrix and risk calculation
     *
     * @return list of pax to be offloaded
     */
    public List<Passenger> getOffloadPaxList() {
        return null;
    }

    /***
     * check if the passenger should be offloaded or not based on the matrix and risk calculation
     * @param KFNumber of the pax, as it is unique
     * @return
     */
    private boolean toOffLoadPassenger(String KFNumber) {

        //choose amount of passengers to offload using daniel's matrix

        //sort all pax by their bid, retrieve lowest pax that has bidded from bid table in database (need to consolidate multiple bids based on the same bookingID)
        //check if selected pax has been offloaded before, if he's offloaded before, then select the next pax to be offloaded



        return false;
    }

    /***
     * returns the count of being offloaded based on the pax name
     * @param KFNumber of the pax, as it is unique
     * @return
     */
    private int paxOffLoadCount(String KFNumber) throws IOException, JSONException {

        int count = 0;
        String response = Connection.getPostResponseFromExternalFile("pax_info.json", asset);

        //should get the json based on the KF number as the request, currently is taking directly from file
        //things needed to send as part of the request: {"krisflyerNumber": "5918588234"}


        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = jsonObject.getJSONObject("response").getJSONArray("passengerExperienceHistory");
        for (int i = 0; i < array.length(); i++) {
            JSONObject currObj = array.getJSONObject(i);
            String category = currObj.getString("category");
            if (category.equals("OffloadedFromFlight")) {     //assuming that all offloaded categories is under the same header
                count++;
            }
        }
        return count;
    }

    private static void printDebug(String message) {
        Log.d("PassengerDAO.java", message);
    }

    private static void printError(Exception e) {
        Log.e("PassengerDAO.java", "error", e);
    }

}
