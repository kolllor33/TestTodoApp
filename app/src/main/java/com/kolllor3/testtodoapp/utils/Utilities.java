package com.kolllor3.testtodoapp.utils;

import android.os.AsyncTask;

import java.util.Random;

public class Utilities {

    public static boolean isNull(Object obj){
        return obj == null;
    }

    public static boolean isNotNull(Object obj){
        return obj != null;
    }

    public static String getRandomString(int length){
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append((char)(r.nextInt(96) + 32));
        return builder.toString();
    }

    public static void doInBackground(Runnable runnable){
        AsyncTask.execute(runnable);
    }
}
