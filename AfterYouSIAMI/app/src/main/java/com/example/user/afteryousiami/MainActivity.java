package com.example.user.afteryousiami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nxtPageBtn(View v){
        Intent i =  new Intent(MainActivity.this, Chooseperks.class );
        startActivity(i);
    }
}
