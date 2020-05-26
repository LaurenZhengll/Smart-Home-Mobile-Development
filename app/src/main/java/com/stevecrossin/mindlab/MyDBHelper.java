package com.stevecrossin.mindlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MyDBHelper extends SQLiteOpenHelper {

    private Context context;

    public MyDBHelper(Context context) {
        //Database name
        super(context, "test", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table steps(" +
                "id varchar(50) primary key," +
                "day_time INTEGER," +
                "steps_count INTEGER)");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //Insert
        c.add(Calendar.DAY_OF_MONTH, -1);
        sqLiteDatabase.execSQL("insert into steps values('" + UUID.randomUUID().toString() + "'," + c.getTime().getTime() + ",3000)");
        c.add(Calendar.DAY_OF_MONTH, -1);
        sqLiteDatabase.execSQL("insert into steps values('" + UUID.randomUUID().toString() + "'," + c.getTime().getTime() + ",7000)");
        c.add(Calendar.DAY_OF_MONTH, -1);
        sqLiteDatabase.execSQL("insert into steps values('" + UUID.randomUUID().toString() + "'," + c.getTime().getTime() + ",4000)");
        c.add(Calendar.DAY_OF_MONTH, -1);
        sqLiteDatabase.execSQL("insert into steps values('" + UUID.randomUUID().toString() + "'," + c.getTime().getTime() + ",6000)");
        c.add(Calendar.DAY_OF_MONTH, -1);
        sqLiteDatabase.execSQL("insert into steps values('" + UUID.randomUUID().toString() + "'," + c.getTime().getTime() + ",2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
