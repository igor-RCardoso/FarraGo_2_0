package com.example.geraldo.farrago_2_0.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Fábio on 22/06/2017.
 */

public class MyApp extends Application {
    private static Context context;
    public static Context getContext(){
        return MyApp.context;
    }
    public void onCreate(){
        super.onCreate();
        MyApp.context = getApplicationContext();
    }
}
