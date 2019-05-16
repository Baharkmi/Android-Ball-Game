package com.example.android.colorballs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.Scanner;

public class StatsActivity extends AppCompatActivity {

    //String[][] array = new String[20][1];
    Integer[] array = new Integer[20];

    boolean ok = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        try {
            DataInputStream textFileStream = new DataInputStream(this.openFileInput("scores.txt"));
            Scanner sc = new Scanner(textFileStream);
            int i = 0;
            while (sc.hasNextInt()) {
                ok = true;
                array[i] = sc.nextInt();
                i++;
            }
            sc.close();
        }
        catch (Exception e) {
        }

        if(ok){
            ArrayAdapter adapter = new ArrayAdapter<Integer>(this, R.layout.activity_listview, array);

            final ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        }


    }
}
