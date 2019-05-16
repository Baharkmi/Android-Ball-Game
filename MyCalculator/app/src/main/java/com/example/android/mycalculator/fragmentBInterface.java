package com.example.android.mycalculator;


public interface fragmentBInterface{
    String getResult();
    void setResult(String text);
    void addLog(String text);
    void setJustOpPressed(boolean b);
    boolean getJustOpPressed();
    void setPrevOp(String s);
    String getPrevOp();
    void setResNum(float f);
    float getResNum();
}