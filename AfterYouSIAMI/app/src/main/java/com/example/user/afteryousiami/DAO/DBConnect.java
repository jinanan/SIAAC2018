package com.example.user.afteryousiami.DAO;

import android.util.Log;

import com.example.user.afteryousiami.objects.Perks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {


    /***
     * inserts into the database with the added perks
     * @param perksList added perks list
     * @return
     */
    public boolean insertBid(List<Perks> perksList, int bookingID) {
        Connection con = null;
        PreparedStatement pmt = null;
        try {
            con = ConnectionManager.getConnection();
            pmt = null;
            if (con != null) {
                //loop thru the perksList
                for (Perks p : perksList) {
                    pmt = con.prepareStatement("insert into bid (bidCatId, bookingId, bidPrice)" + " values (?, ?, ?)");
                    pmt.setInt(1, p.getPerksID());
                    pmt.setInt(2, bookingID);
                    pmt.setDouble(3, p.getTotalPrice());
                    pmt.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            printError(e);
        } finally {
            ConnectionManager.close(con, pmt, null);
        }
        return false;
    }

    /***
     * retrieves a list of perks from the database
     * @return list of perks object
     */
    public List<Perks> retrievePerks() {
        List<Perks> perksList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            if (conn != null) {
                pmt = conn.prepareStatement("SELECT bidCat.bidCatId, bidCatType.name, bidCatType.sponsor, bidCat.catPricePerUnit, bidCat.catDescription FROM bidCatType INNER JOIN bidCat ON bidCatType.bidCatTypeId = bidCat.bidCatTypeId;");
                rs = pmt.executeQuery();

                final int ID = 1;
                final int TYPE = 2;     //type of the product (e.g. singapore airlines/ travel services/ etc)
                final int NAME = 3;     //name of the product
                final int BIDCAT_CAT_PRICE_PER_UNIT = 4;
                final int BIDCAT_DESCRIPTION = 5;

                while (rs.next()) {
                    int id = rs.getInt(ID);
                    String type = rs.getString(TYPE);
                    String name = rs.getString(NAME);
                    String description = rs.getString(BIDCAT_DESCRIPTION);
                    double pricePerUnit = rs.getDouble(BIDCAT_CAT_PRICE_PER_UNIT);

                    perksList.add(new Perks(id, name, type, description, pricePerUnit));
                }
            }

        } catch (SQLException ex) {
            printError(ex);
        } finally {
            ConnectionManager.close(conn, pmt, rs);
        }

        return perksList;
    }


    private static void printDebug(String message) {
        Log.d("DBConnect.java", message);
    }

    private static void printError(Exception e) {
        Log.e("DBConnect.java", "error", e);
    }
}
