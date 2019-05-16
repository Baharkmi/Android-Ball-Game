package com.example.android.mycalculator;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class fragment_a extends Fragment implements fragmentBInterface, MainActivityInterface{

    private fragmentAInterface fai;

    protected float resNum;
    protected String prevOp;
    protected boolean justOpPressed;
    static View view;
    String log;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_a, container, false);
        setupEvents();
        return view;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            fai = (fragmentAInterface) activity;
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
        justOpPressed = b;
    }

    @Override
    public boolean getJustOpPressed() {
        return justOpPressed;
    }

    @Override
    public void setPrevOp(String s) {
        prevOp = s;
    }

    @Override
    public String getPrevOp() {
        return prevOp;
    }

    @Override
    public void setResNum(float f) {
        resNum = f;
    }

    @Override
    public float getResNum() {
        return resNum;
    }

    //View.OnClickListener numKeyListener = new View.OnClickListener() {  // this creates an anonymus class
    class NumKeyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;

            String result = fai.getResult();
            if(justOpPressed || result.equals("0"))
                fai.setResult(btn.getText().toString());
            else
                fai.setResult(fai.getResult() + btn.getText());
                //result.getText().append(btn.getText());

            justOpPressed = false;
            log = "Number " + fai.getResult() + " was selected. ";
            fai.addLog(log);
        }
    };

    class OpKeyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;
            String op = btn.getText().toString();
            if(!justOpPressed) {
                float curNum = Float.parseFloat(fai.getResult());

                switch (prevOp) {
                    case "+":
                        resNum += curNum;
                        break;
                    case "-":
                        resNum -= curNum;
                        break;
                    case "*":
                        resNum *= curNum;
                        break;
                    case "/":
                        resNum /= curNum;
                        break;
                    case "=":
                        resNum = curNum;
                        break;
                    case "":
                        resNum = curNum; // case when the first time after clearing, an operator is clicked (prevOp is empty)
                }
                fai.setResult("" + resNum);
                justOpPressed = true;
            }
            prevOp = op;
            log = "Operation " + prevOp + " was selected. ";
            fai.addLog(log);
        }
    };

    public void setupEvents() {
        fai.setResult("0");
        resNum = 0.0f;
        prevOp = "";
        justOpPressed = false;

        // clear and backspace buttons
        Button clearBtn = (Button) view.findViewById(R.id.clearBtn);
        Button backspaceBtn = (Button) view.findViewById(R.id.backspaceBtn);

        // decimal point button
        Button dotBtn = (Button) view.findViewById(R.id.btnDot);

        // number buttons
        // Button btn0 = (Button) findViewById(R.id.btn0);
        // Button btn1 = (Button) findViewById(R.id.btn1);
        // ...
        // having findViewByName() method defined in this class, the above 10 lines can be written as:
        Button [] numBtns = new Button[10];
        for(int i = 0; i < numBtns.length; i++)
            numBtns[i] = (Button) findViewByName("btn" + i);

        // operation buttons
        String [] ops = new String[] {"addBtn", "subtractBtn", "multiplyBtn", "divisionBtn", "equalsBtn" };
        Button [] opBtns = new Button[ops.length];
        for(int i = 0; i < opBtns.length; i++)
            opBtns[i] = (Button) findViewByName(ops[i]);

        // set up Listeners for different buttons
        // clear & backspace button
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fai.setResult("0");
                prevOp = "";
                justOpPressed = false;
            }
        });
        backspaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = fai.getResult();
                fai.setResult((s.length() > 1) ? s.substring(0, s.length() - 1) : "0");
                if(justOpPressed) {
                    prevOp = "";
                }
                justOpPressed = false;
            }
        });

        // decimal point button
        dotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = fai.getResult().toString();
                if(justOpPressed)
                    fai.setResult("0.");
                //result.append(s.contains(".") ? "" : ".");
                if(!fai.getResult().contains(".")){
                    fai.setResult(fai.getResult() + ".");
                }
                justOpPressed = false;
            }
        });

        // number buttons
        fragment_a.NumKeyListener numKeyListener = new fragment_a.NumKeyListener();
        for(int i = 0; i < numBtns.length ; i++)
            numBtns[i].setOnClickListener(numKeyListener);

        // operation buttons
        fragment_a.OpKeyListener opKeyListener = new fragment_a.OpKeyListener();
        for(int i = 0; i < opBtns.length ; i++)
            opBtns[i].setOnClickListener(opKeyListener);
    }

}
