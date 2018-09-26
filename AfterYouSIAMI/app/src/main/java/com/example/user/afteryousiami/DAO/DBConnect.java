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


    public boolean insertData() {
        Connection con = null;
        PreparedStatement pmt = null;
        try {
            con = ConnectionManager.getConnection();
            pmt = null;
            if (con != null) {
                pmt = con.prepareStatement("insert into user (firstName, lastName, phone, email, krisflyerNum)" + " values (?, ?, ?, ?, ?)");
                pmt.setString(1, "herp");
                pmt.setString(2, "derp");
                pmt.setInt(3, 98247512);
                pmt.setString(4, "herpderp@gmail.com");
                pmt.setInt(5, 124719285);
                pmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
                pmt = conn.prepareStatement("SELECT bidCatType.name, bidCatType.sponsor, bidCat.catPricePerUnit, bidCat.catDescription FROM bidCatType INNER JOIN bidCat ON bidCatType.bidCatTypeId = bidCat.bidCatTypeId;");
                rs = pmt.executeQuery();

                final int TYPE = 1;     //type of the product (e.g. singapore airlines/ travel services/ etc)
                final int NAME = 2;     //name of the product
                final int BIDCAT_CAT_PRICE_PER_UNIT = 3;
                final int BIDCAT_DESCRIPTION = 4;

                while (rs.next()) {
                    String type = rs.getString(TYPE);
                    String name = rs.getString(NAME);
                    String description = rs.getString(BIDCAT_DESCRIPTION);
                    double pricePerUnit = rs.getDouble(BIDCAT_CAT_PRICE_PER_UNIT);

                    perksList.add(new Perks(name, type, description, pricePerUnit));
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

    public static void main(String[] args) {
        DBConnect db = new DBConnect();
        db.insertData();
    }
}
