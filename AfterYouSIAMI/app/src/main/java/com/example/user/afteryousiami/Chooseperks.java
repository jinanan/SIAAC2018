package com.example.user.afteryousiami;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.user.afteryousiami.DAO.DBConnect;
import com.example.user.afteryousiami.objects.Perks;

import java.util.ArrayList;
import java.util.List;

public class Chooseperks extends Activity {
    final Context context = this;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    DBConnect db = new DBConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseperks);

        spinner = (Spinner) findViewById(R.id.spinner);

        new PerksAsyncTask().execute();

    }

    private void createSpinnerObjects(List<Perks> perksList){
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
//        spinner.invalidate();         //refresh the spinner

    }

    public void addToCart(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("SIAMI");
        builder.setMessage("Item added to Cart");
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void nxtPageBtn(View v) {
        Intent i = new Intent(Chooseperks.this, perks_summary.class);
        startActivity(i);
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
        protected void onPostExecute(List<Perks>  result) {
            super.onPostExecute(result);

            createSpinnerObjects(result);

            //this method will be running on UI thread
            pdLoading.dismiss();
        }

    }
}