package com.example.android.mycalculator;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class fragment_b extends Fragment {

    private fragmentBInterface fbi = new fragmentBInterface() {
        @Override
        public String getResult() {
            return null;
        }

        @Override
        public void setResult(String text) {

        }

        @Override
        public void addLog(String text) {

        }

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

        @Override
        public void setResNum(float f) {

        }

        @Override
        public float getResNum() {
            return 0;
        }
    };

    static View view;
    String log;
    float resNum;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_b, container, false);
        setupEvents();
        return view;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            fbi = (fragmentBInterface) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() +
                    "must implement fragmentAInterface");
        }
        //setupEvents();
    }

    public View findViewByName(String name){
        // use the getIdentifier() method
        return view.findViewById(getResources().getIdentifier(name, "id", getActivity().getPackageName()));

        //// or do the same task using Java reflection
        // try {
        //     Field fieldObj = R.id.class.getDeclaredField(name);
        //     return findViewById(fieldObj.getInt(fieldObj));
        // } catch (Exception e) {
        //     throw new RuntimeException("No resource id with name '" + name +"' found.", e);
        // }
    }

    class OpKeyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;
            String op = btn.getText().toString();
            if(!fbi.getJustOpPressed()) {
                float curNum = Float.parseFloat(fbi.getResult());
               String prevOp;
                if(fbi.getPrevOp() == null)
                    prevOp = "";
                else
                    prevOp = fbi.getPrevOp();

                switch (prevOp) {
                    case "^":
                        resNum = (float) Math.pow(fbi.getResNum(),curNum);
                        break;
                    case "√":
                        resNum = (float) Math.sqrt(curNum);
                        break;
                    case "log":
                        resNum = (float) Math.log10(curNum);
                        break;
                    case "ln":
                        resNum = (float) Math.log(curNum);
                        break;
                    case "sin":
                        resNum = (float) Math.sin(curNum);
                        break;
                    case "cos":
                        resNum = (float) Math.cos(curNum);
                        break;
                    case "tan":
                        resNum = (float) Math.tan(curNum);
                        break;
                    case "1/x":
                        resNum = ((float) 1/curNum);
                        break;
                    case "":
                        resNum = curNum; // case when the first time after clearing, an operator is clicked (prevOp is empty)
                }
                fbi.setResult("" + resNum);
                fbi.setJustOpPressed(true);
            }
            fbi.setPrevOp(op);
            log = "Operation " + fbi.getPrevOp() + " was selected. ";
            fbi.addLog(log);
        }
    };

    public void setupEvents() {
        fbi.setResult("0");
        fbi.setResNum(0.0f);
        fbi.setPrevOp("");
        fbi.setJustOpPressed(false);

        // e and pi buttons
        Button e = (Button) view.findViewById(R.id.e);
        Button pi = (Button) view.findViewById(R.id.pi);

        // operation buttons
        String [] ops = new String[] {"power", "radical", "log", "ln", "sin", "cos", "tan", "frac"};
        Button [] opBtns = new Button[ops.length];
        for(int i = 0; i < opBtns.length; i++)
            opBtns[i] = (Button) findViewByName(ops[i]);

        // set up Listeners for different buttons
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fbi.getJustOpPressed() || fbi.getResult().equals("0"))
                    fbi.setResult(Math.E + "");
                else
                    fbi.setResult( (Float.parseFloat(fbi.getResult()) * Math.E) + "" );
                fbi.setJustOpPressed(false);
            }
        });

        // pi button
        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fbi.getJustOpPressed() || fbi.getResult().equals("0"))
                    fbi.setResult(Math.PI + "");
                else
                    fbi.setResult( (Float.parseFloat(fbi.getResult()) * Math.PI) + "" );

                fbi.setJustOpPressed(false);
            }
        });

        // operation buttons
        fragment_b.OpKeyListener opKeyListener = new fragment_b.OpKeyListener();
        for(int i = 0; i < opBtns.length ; i++)
            opBtns[i].setOnClickListener(opKeyListener);
    }

}