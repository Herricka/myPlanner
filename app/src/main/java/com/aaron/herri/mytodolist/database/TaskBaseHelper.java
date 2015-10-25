package com.aaron.herri.mytodolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aaron.herri.mytodolist.database.TaskDbSchema.TaskTable;

/**
 * Created by herri on 10/9/2015.
 */
public class TaskBaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "plannerBase.db";

    // Tasks Table Columns names
    private static final String TASK_ID = "id";
    private static final String TASK_TITLE = "title";

    // Bills Table Columns names
    private static final String BILL_ID = "id";
    private static final String BILL_TiTLE = "title";

    public TaskBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE" + DATABASE_NAME + "("
                + TASK_ID + " INTEGER PRIMARY KEY," + TASK_TITLE + " TEXT,"

    }


    /*@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TaskTable.NAME + "(" +
        " _id integer primary key autoincrement, " +
        TaskTable.Cols.UUID + ", " +
        TaskTable.Cols.TITLE + ", " +
        TaskTable.Cols.DESCRIPTION + ", " +
        TaskTable.Cols.PRIORITY + ", " +
        TaskTable.Cols.DATE + ", " +
        TaskTable.Cols.FINISHED +
        ")"
        );
    }
    */


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
