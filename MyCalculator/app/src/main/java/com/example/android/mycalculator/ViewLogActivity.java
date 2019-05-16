package com.example.android.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        TextView textView = (TextView) findViewById(R.id.log);
        String s = getIntent().getStringExtra("log");
        textView.setText(s);
    }
}
