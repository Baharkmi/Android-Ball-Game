package com.example.android.mycalculator;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MotionEvent;
import android.widget.*;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class MainActivity extends ActionBarActivity implements fragmentAInterface, fragmentBInterface {

    MainActivityInterface mai = new MainActivityInterface() {
        @Override
        public void setJustOpPressed(boolean b) {

        }

        @Override
        public boolean getJustOpPressed() {
            return false;
        }

        @Override
        public void setPrevOp(String s) {

        }

        @Override
        public String getPrevOp() {
            return null;
        }
    };

    fragment_a fragA = null;
    fragment_b fragB = null;

    public fragment_a getFragmentA(){  return (fragA != null) ? fragA : new fragment_a();  }
    public fragment_b getFragmentB(){  return (fragB != null) ? fragB : new fragment_b();  }

    private float xs, xe;
    static final int MIN_SWEEP = 5;
    protected EditText result;
    protected final String logfileName = "calc.log";
    protected final String logallfileName = "calc_all.log";
    static String log = "";


    @Override
    public String getResult() {
        return result.getText().toString();
    }

    @Override
    public void setResult(String text) {
        result.setText(text);
    }

    @Override
    public void addLog(String text) {
        addToLogFile(text, logfileName);
        addToLogFile(text, logallfileName);
    }

    @Override
    public void setJustOpPressed(boolean b) {
        mai.setJustOpPressed(b);
    }

    @Override
    public boolean getJustOpPressed() {
        return mai.getJustOpPressed();
    }

    @Override
    public void setPrevOp(String s) {
        if(!s.equals(""))
            mai.setPrevOp(s);
    }

    @Override
    public String getPrevOp() {
        return mai.getPrevOp();
    }

    @Override
    public void setResNum(float f) {
    }

    @Override
    public float getResNum() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (EditText) findViewById(R.id.resultBox);

        /*File file = new File(getFilesDir(), logfileName);
        file.delete();*/

        clearLog(logfileName);

        // Common Portrait and Landscape Configuration:
        //   add FragmentA
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(findViewById(R.id.container_a) != null) {
            transaction.add(R.id.container_a, getFragmentA());
        }

        // Landscape Configuration
        //  add Fra5gmentB, this is done just in landscape config where container_b exists
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.add(R.id.container_b, getFragmentB());
        }

        transaction.commit();
        // Portrait Configuration:
       if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //   add code for switching between fragments by clicking on a button
            /*Button switchBtn = (Button) findViewById(R.id.switch_fragment_btn);
            switchBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View btn) {
                    MainActivity.this.toggleFragments();
                }
            });*/

            //  add code for switching between fragments by toch sweeping to left or right
            findViewById(R.id.container_root).setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            xs = event.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            xe = event.getX();
                            if (Math.abs(xe - xs) >= MIN_SWEEP) {
                                MainActivity.this.toggleFragments();
                            }
                            break;
                    }
                    return true;
                }
            });
        }

    }

    private boolean fragAShown = true;
    public void toggleFragments(){
        // if fragmentA is shown show fragmentB and vice versa
        Fragment fragment = fragAShown ?
                MainActivity.this.getFragmentB() : MainActivity.this.getFragmentA();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragment)
                .addToBackStack("SwitchFragmentsA&B")
                .commit();

        fragAShown = !fragAShown;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically ha
        // ndle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_exit){
            finish();
        }
        if (id == R.id.copy){
            copyToClipboard(result.getText().toString());
        }
        if (id == R.id.paste){
            result.setText(getFromClipboard());
        }
        if(id == R.id.view_log){
            String s = loadLogFile(logfileName);
            Intent intent = new Intent(MainActivity.this, ViewLogActivity.class);
            intent.putExtra("log", s);
            startActivity(intent);
        }
        if(id == R.id.view_log_all){
            String s = loadLogFile(logallfileName);
            Intent intent = new Intent(MainActivity.this, ViewLogActivity.class);
            intent.putExtra("log", s);
            startActivity(intent);
        }
        if(id == R.id.clear_log){
            clearLog(logallfileName);
        }
        return super.onOptionsItemSelected(item);
    }


    public View findViewByName(String name) {
        // use the getIdentifier() method
        return findViewById(getResources().getIdentifier(name, "id", getPackageName()));

        //// or do the same task using Java reflection
        // try {
        //     Field fieldObj = R.id.class.getDeclaredField(name);
        //     return findViewById(fieldObj.getInt(fieldObj));
        // } catch (Exception e) {
        //     throw new RuntimeException("No resource id with name '" + name +"' found.", e);
        // }
    }


    // working with clipboard
    protected void copyToClipboard(String str) {
        ClipboardManager clipboardMgr = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Calculator Result", str);
        clipboardMgr.setPrimaryClip(clipData);
    }

    protected String getFromClipboard(){
        ClipboardManager clipboardMgr = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if(clipboardMgr.hasPrimaryClip()){
            //if(clipboardMgr.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){ // ClipDescription (API Level >= 11)
            if(clipboardMgr.getPrimaryClipDescription().hasMimeType("text/plain")){
                return clipboardMgr.getPrimaryClip().getItemAt(0).getText().toString();
            }
        }
        return "";
    }

    // working with files
    protected void addToLogFile(String logStr, String fileName){
        try {
            FileOutputStream fout = openFileOutput(fileName, MODE_APPEND);
            OutputStreamWriter fw = new OutputStreamWriter(fout);
            fw.write(logStr + "\n");
            fw.close();
        }catch (Exception e){
        }
        return;
    }
    protected String loadLogFile(String fileName){
        try {
            FileInputStream fileIn = openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer= new char[1000];
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                log +=readstring;
            }
            InputRead.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }

    protected void clearLog(String fileName){
        try {
/*            FileWriter fw = new FileWriter(logallfileName);
            fw.write("");
            fw.close();*/
            PrintWriter pw = new PrintWriter(fileName);
            pw.write("");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
