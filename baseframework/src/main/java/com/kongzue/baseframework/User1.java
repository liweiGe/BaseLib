package com.kongzue.baseframework;

import android.util.Log;

import java.util.stream.LongStream;

public class User1 implements User {


    @Override
    public void set(String name, int age) {
        System.out.println("set: name和age"+name);
    }

    @Override
    public void set(int oo) {
        System.out.println("单函数"+oo);
//        Log.i(TAG, "setoo: "+oo);
    }
}
