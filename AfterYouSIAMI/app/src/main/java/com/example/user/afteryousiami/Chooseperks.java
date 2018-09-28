package com.example.user.afteryousiami;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.afteryousiami.DAO.DBConnect;
import com.example.user.afteryousiami.objects.Perks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chooseperks extends Activity {
    private final Context context = this;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private DBConnect db = new DBConnect();
    private List<Perks> perksList;
    private TableLayout layout;

    //colors to be used for the textview
    private final String WHITE_COLOR = "#ffffff";
    private final String BLUE_COLOR = "#0C4082";
    private final String GOLD_COLOR = "#FCB130";
    private final String GREY_COLOR = "#d3d3d3";
    private final int HEIGHT = 550;
    private final int WIDTH = 1100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseperks);
        spinner = (Spinner) findViewById(R.id.spinner);
        layout = (TableLayout) findViewById(R.id.tableLayout);
        new PerksAsyncTask().execute();
    }

    private void createSpinnerObjects(List<Perks> perksList) {
        this.perksList = perksList;     //set the object to be accessed by the spinenr selected method
        List<String> array = new ArrayList<>();
        for (Perks p : perksList) {
            String category = p.getCategory();
            if (!array.contains(category)) {
                array.add(p.getCategory());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                cleanTable();        //refresh the table items
                String category = adapter.getItemAtPosition(i).toString();
                updateUI(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    /***
     * used to update the UI based on the selected category from the spinner
     * @param selectedCategory
     */
    private void updateUI(String selectedCategory) {
        List<Perks> selected = new ArrayList<>();

        for (Perks p : perksList) {
            if (selectedCategory.equals(p.getCategory())) {
                selected.add(p);
            }
        }

        //update the texts or more items where possible
        for (int i = 0; i < selected.size(); i++) {
            Perks p = selected.get(i);
            TableRow headerRow = new TableRow(this);
            TableRow pictureRow = new TableRow(this);
            TableRow descriptionRow = new TableRow(this);
            TableRow addToCartRow = new TableRow(this);

            //setting layout params
            headerRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            headerRow.setPadding(0, 0, 0, 20);
            pictureRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            pictureRow.setPadding(0, 0, 0, 20);
            descriptionRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            descriptionRow.setPadding(0, 0, 0, 20);
            if (i < selected.size() - 1) {              //as long as i is still lesser than the last element, add the padding, else dont add when its the last
                addToCartRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                addToCartRow.setPadding(0, 0, 0, 20);
            }

            //add data from the textviews to the tablerows
            headerRow.addView(createTextView(p.getName(), true));
            createImageView(p.getName(), pictureRow);
            descriptionRow.addView(createTextView(p.getDescription(), false));
            addToCartRow.addView(createCartButton(p.getName(), p));


            //add to the layout
            layout.addView(headerRow);
            layout.addView(pictureRow);
            layout.addView(descriptionRow);
            layout.addView(addToCartRow);
        }

        //add the checkout button
        TableRow checkOutRow = new TableRow(this);
        checkOutRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        checkOutRow.addView(createCheckOutButton());
        layout.addView(checkOutRow);

    }

    /***
     * helper method to clean the table. do not clean the first 2 items because thats the header and the spinner
     */
    private void cleanTable() {

        int childCount = layout.getChildCount();

        for (int i = childCount - 1; i >= 2; i--) {
            TableRow row = (TableRow) layout.getChildAt(i);
            layout.removeView(row);
        }
    }

    /***
     * creates an imageview based on the name of the thing
     * @param name
     * @return imageview object
     */
    private ImageView createImageView(String name, TableRow pictureRow) {
        ImageView imageView = new ImageView(this);
        pictureRow.addView(imageView);
        imageView.getLayoutParams().height = HEIGHT;
        imageView.getLayoutParams().width = WIDTH;
        //replaces all of the () - into underscores "_" because android doesnt allow to compile if its not just letter and underscores only
        String imageString = name.replace(" ", "_").replace("(", "_").replace(")", "_").replace("-", "_").toLowerCase();
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), context.getResources().getIdentifier("drawable/" + imageString, null, context.getPackageName()));
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, WIDTH, HEIGHT, true);
        imageView.setImageBitmap(bMapScaled);
        return imageView;
    }

    private Button createCartButton(final String name, final Perks p) {
        final Button btn = new Button(this);
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        setProperties(p.isHasAdded(), btn, p, false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProperties(!p.isHasAdded(), btn, p, true); //pass in false, pass in true if otherwise
            }
        });

        return btn;
    }

    /***
     * set the formatting of the buttons
     * @param hasAdded if the items has already been added or not
     * @param btn button object
     * @param p perks object
     * @param fromClick true if this is invoked from click, false if otherwise (so that the toast will not show upon UI create)
     */
    private void setProperties(boolean hasAdded, Button btn, Perks p, boolean fromClick) {
        if (hasAdded) {       //if has added then put remove cart, else is other
            if(fromClick) {
                Toast.makeText(Chooseperks.this, p.getName() + " added to cart", Toast.LENGTH_LONG).show();
            }
            btn.setText("Remove from cart");
            btn.setBackgroundColor(Color.parseColor(GREY_COLOR));
            p.setHasAdded(true);
        } else {        //basically the action of removing the item from the cart
            if(fromClick) {
                Toast.makeText(Chooseperks.this, p.getName() + " removed from cart", Toast.LENGTH_LONG).show();
            }
            btn.setBackgroundColor(Color.parseColor(GOLD_COLOR));
            btn.setText("Add to cart");
            p.setHasAdded(false);           //means that it has not been added after its removed
        }
    }

    /***
     * creates the check out button
     * @return button object
     */
    private Button createCheckOutButton() {
        Button btn = new Button(this);
        btn.setBackgroundColor(Color.parseColor(BLUE_COLOR));
        btn.setText("Checkout");
        btn.setTextColor(Color.parseColor(WHITE_COLOR));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Perks> addedList = new ArrayList<>();
                for (Perks p : perksList) {
                    if (p.isHasAdded()) {
                        addedList.add(p);
                    }
                }
                Intent i = new Intent(Chooseperks.this, perks_summary.class);
                i.putExtra("AddedList", (Serializable) addedList);
                startActivity(i);


            }
        });

        return btn;

    }

    /***
     * Creates a textview to be shown to the user
     * @param value what you want to show to the user
     * @param isHeader true = header, false = description, different is just a slight difference in text size and text color
     * @return textview object
     */
    private TextView createTextView(String value, boolean isHeader) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView.setText(value);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        if (isHeader) {
            textView.setTextColor(Color.parseColor(GOLD_COLOR));
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(20);
        } else {
            textView.setTextColor(Color.parseColor(BLUE_COLOR));
            textView.setTextSize(16);
        }

        return textView;
    }

    private static void printDebug(String message) {
        Log.d("Chooseperks.java", message);
    }

    private static void printError(Exception e) {
        Log.e("Chooseperks.java", "error", e);
    }


    class PerksAsyncTask extends AsyncTask<Void, Void, List<Perks>> {
        ProgressDialog pdLoading = new ProgressDialog(Chooseperks.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected List<Perks> doInBackground(Void... params) {
            List<Perks> perksList = db.retrievePerks();
            return perksList;
        }

        @Override
        protected void onPostExecute(List<Perks> result) {
            super.onPostExecute(result);

            createSpinnerObjects(result);

            //this method will be running on UI thread
            pdLoading.dismiss();
        }

    }
}