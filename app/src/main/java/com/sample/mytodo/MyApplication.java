package com.sample.mytodo;

import android.app.Application;
import android.arch.persistence.room.Room;

public class MyApplication extends Application {
    private static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "todo").build();
    }

    public static AppDatabase getDb() {
        return db;
    }
}
