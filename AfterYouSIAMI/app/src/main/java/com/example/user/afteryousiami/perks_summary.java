package com.example.user.afteryousiami;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.afteryousiami.DAO.Connection;
import com.example.user.afteryousiami.DAO.DBConnect;
import com.example.user.afteryousiami.DAO.FlightDAO;
import com.example.user.afteryousiami.objects.Flight;
import com.example.user.afteryousiami.objects.Passenger;
import com.example.user.afteryousiami.objects.Perks;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class perks_summary extends Activity {
    private final Context context = this;
    private List<Perks> addedList;
    private DBConnect db = new DBConnect();
    private TableLayout layout;
    private final String WHITE_COLOR = "#ffffff";
    private final String BLUE_COLOR = "#0C4082";
    private final String GOLD_COLOR = "#FCB130";
    private final String GREY_COLOR = "#d3d3d3";
    private final String BLACK_COLOR = "#BDBDBD";
    private DecimalFormat df = new DecimalFormat("##.##");
    private Passenger currPax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perks_summary);
        layout = (TableLayout) findViewById(R.id.tableLayout);
        addedList = (List<Perks>) getIntent().getSerializableExtra("AddedList");
        currPax = Connection.getPax(getAssets());

        initPerksUI();
    }


    /***
     * helper method to clean the table. do not clean the first item because thats the header
     */
    private void cleanTable() {

        int childCount = layout.getChildCount();

        for (int i = childCount - 1; i >= 1; i--) {
            TableRow row = (TableRow) layout.getChildAt(i);
            layout.removeView(row);
        }
    }

    private void initPerksUI() {
        cleanTable();       //invoke cleantable to clear the table before populating it with the new items
        double sumTotal = 0;

        for (Perks p : addedList) {
            TableRow contents = new TableRow(this);
            contents.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            contents.setBackgroundColor(Color.parseColor(WHITE_COLOR));
            contents.setGravity(Gravity.CENTER_HORIZONTAL);
            contents.setWeightSum(1f);
            printContentRow(p, contents);
            printDivider();
            contents.setPadding(0, 0, 0, 50);

            sumTotal += p.getTotalPrice();

            layout.addView(contents);
        }

        //for the total details
        TableRow totalRow = new TableRow(this);
        totalRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        totalRow.setBackgroundColor(Color.parseColor(WHITE_COLOR));
        totalRow.setGravity(Gravity.CENTER_HORIZONTAL);
        totalRow.setWeightSum(1f);
        printTotalRow(sumTotal, totalRow);
        layout.addView(totalRow);


        //for the final button
        TableRow confirmRow = new TableRow(this);
        confirmRow.setPadding(0, 100, 0, 0);
        confirmRow.setGravity(Gravity.CENTER_HORIZONTAL);
        confirmRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        confirmRow.addView(createConfirmButton());
        layout.addView(confirmRow);
    }

    /***
     * creates the check out button
     * @return button object
     */
    private Button createConfirmButton() {
        Button btn = new Button(this);
        btn.setBackgroundColor(Color.parseColor(BLUE_COLOR));
        btn.setText("Confirm");
        btn.setTextColor(Color.parseColor(WHITE_COLOR));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new perks_summary.PerksAsyncTask().execute();
            }
        });

        return btn;

    }

    private void printTotalRow(double sumTotal, TableRow tr) {
        printDivider();

        //for the items selected
        TextView totalLabel = new TextView(this);
        tr.addView(totalLabel);
        TableRow.LayoutParams nameViewLP = (TableRow.LayoutParams) totalLabel.getLayoutParams();
        nameViewLP.width = 0;
        nameViewLP.height = TableLayout.LayoutParams.WRAP_CONTENT;
        nameViewLP.weight = 0.5f;
        totalLabel.setLayoutParams(nameViewLP);
        totalLabel.setTextSize(20);
        totalLabel.setText("Total: ");
        totalLabel.setTextColor(Color.parseColor(BLUE_COLOR));
        totalLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        //for the items selected
        TextView sumView = new TextView(this);
        tr.addView(sumView);
        TableRow.LayoutParams sumViewLP = (TableRow.LayoutParams) sumView.getLayoutParams();
        sumViewLP.width = 0;
        sumViewLP.height = TableLayout.LayoutParams.WRAP_CONTENT;
        sumViewLP.weight = 0.5f;
        sumView.setLayoutParams(sumViewLP);
        sumView.setTextSize(20);
        sumView.setText(df.format(sumTotal) + " credits");
        sumView.setTextColor(Color.parseColor(BLUE_COLOR));
        sumView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

        printDivider();
    }

    /***
     * creates the text views of for the rows
     * @param p perks object to be manipulated
     * @param tr row object to be passed in to be added into the view
     */
    private void printContentRow(final Perks p, TableRow tr) {
        //for the items selected
        TextView nameView = new TextView(this);
        tr.addView(nameView);
        TableRow.LayoutParams nameViewLP = (TableRow.LayoutParams) nameView.getLayoutParams();
        nameViewLP.width = 0;
        nameViewLP.height = TableLayout.LayoutParams.WRAP_CONTENT;
        nameViewLP.weight = 0.4f;
        nameView.setGravity(Gravity.CENTER_HORIZONTAL);
        nameView.setLayoutParams(nameViewLP);
        nameView.setTextSize(16);
        nameView.setText(p.getName());
        nameView.setTextColor(Color.parseColor(GOLD_COLOR));

        //for the total price
        TextView priceView = new TextView(this);
        tr.addView(priceView);
        TableRow.LayoutParams priceViewLP = (TableRow.LayoutParams) priceView.getLayoutParams();
        priceViewLP.width = 0;
        priceViewLP.height = TableLayout.LayoutParams.WRAP_CONTENT;
        priceViewLP.weight = 0.5f;
        priceView.setGravity(Gravity.CENTER_HORIZONTAL);
        priceView.setLayoutParams(priceViewLP);
        priceView.setTextSize(16);
        priceView.setText(String.valueOf(p.getTotalPrice()) + " credits");
        priceView.setTextColor(Color.parseColor(GOLD_COLOR));

        //for the delete button
        ImageButton btn = new ImageButton(this);
        tr.addView(btn);
        btn.setImageResource(R.drawable.delete_cross);
        btn.setBackground(null);
        TableRow.LayoutParams btnLP = (TableRow.LayoutParams) btn.getLayoutParams();
        btnLP.weight = 0.1f;
        btnLP.height = 100;
        btnLP.width = 100;
        btn.setLayoutParams(btnLP);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the alert box to remind them if they want to delete
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                removeObjectFromArrayList(p.getName());

                                if (addedList.isEmpty()) {
                                    //route to the choose perks page
                                    Intent i = new Intent(perks_summary.this, Chooseperks.class);
                                    startActivity(i);
                                } else {
                                    initPerksUI();      //refresh the table
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //do nothing
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Confirm to remove " + p.getName() + "?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    private void removeObjectFromArrayList(String name) {
        for (int i = 0; i < addedList.size(); i++) {
            Perks p = addedList.get(i);
            if (p.getName().equals(name)) {
                addedList.remove(i);        //remove from the list
            }
        }
    }

    private void printDivider() {
        TableRow divider = new TableRow(this);
        layout.addView(divider);
        divider.getLayoutParams().height = 1;
        divider.setBackgroundColor(Color.parseColor(BLACK_COLOR));
        TextView tv = new TextView(this);
        divider.addView(tv);
        TableRow.LayoutParams params = (TableRow.LayoutParams) tv.getLayoutParams();
        params.height = 1;
        params.width = TableLayout.LayoutParams.MATCH_PARENT;
        params.span = 2;
    }


    /***
     * invoke for notification
     */
    private void notificationcall() {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this).setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.drawable.logo).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo)).setContentTitle("Notification Call").setContentText("Test");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private static void printDebug(String message) {
        Log.d("perks_summary.java", message);
    }

    private static void printError(Exception e) {
        Log.e("perks_summary.java", "error", e);
    }

    class PerksAsyncTask extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pdLoading = new ProgressDialog(perks_summary.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tAdding bids...");
            pdLoading.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return db.insertBid(addedList, currPax.getBookingID()) && db.insertUser(currPax);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
            pdLoading.dismiss();

            try {

                //because the data are fixed we use a properties file to retrieve the fixed data
                Properties prop = new Properties();              //access the properties file
                prop.load(getAssets().open("app.properties"));

                String origin = prop.getProperty("flight.origin");
                String dest = prop.getProperty("flight.dest");
                String currDateString = prop.getProperty("flight.departureDate");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");   //2018-09-24T09:20:00
                Date currDate = formatter.parse(currDateString);

                //retrieve the next alternate flight
                FlightDAO fd = new FlightDAO(getAssets());
                List<Flight> fList = fd.getAlternativeFlights(origin, dest, currDate);

                String nextFlightNumber = "SQ" + fList.get(0).getFlightNo();
                SimpleDateFormat showUser = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String nextDate = showUser.format(fList.get(0).getdepartureDate());

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Success");

                // set dialog message
                alertDialogBuilder.setMessage("Your next flight will be on " + nextFlightNumber + " on " + nextDate)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(perks_summary.this, "Program complete.", Toast.LENGTH_LONG).show();
                                perks_summary.this.finish();
                                notificationcall();     //invoke the notification
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            } catch (IOException e) {
                printError(e);
            } catch (ParseException e) {
                printError(e);
            } catch (JSONException e) {
                printError(e);
            }

        }

    }

}
