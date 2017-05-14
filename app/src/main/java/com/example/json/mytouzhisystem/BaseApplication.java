package com.example.json.mytouzhisystem;

import android.app.Application;

import com.example.json.mytouzhisystem.Utils.DBUserInvestmentUtils;

/**
 * Created by Json on 2017/3/29.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBUserInvestmentUtils.Init(getApplicationContext());
    }
}
