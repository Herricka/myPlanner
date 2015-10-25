package com.aaron.herri.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aaron.herri.mytodolist.database.TaskBaseHelper;
import com.aaron.herri.mytodolist.database.TaskCursorWrapper;
import com.aaron.herri.mytodolist.database.TaskDbSchema.TaskTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by herri on 10/6/2015.
 */
public class TaskLab {
    private static TaskLab sTaskLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    private TaskLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext)
                .getWritableDatabase();

    }

    private static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.UUID, task.getId().toString());
        values.put(TaskTable.Cols.TITLE, task.getTitle());
        values.put(TaskTable.Cols.DESCRIPTION, task.getDescription());
      //  values.put(TaskTable.Cols.PRIORITY, task.getPriority());
        values.put(TaskTable.Cols.DATE, task.getDate().getTime());
        values.put(TaskTable.Cols.FINISHED, task.isFinished() ? 1 : 0);

        return values;
    }

    public void addTask(Task c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(TaskTable.NAME, null, values);
    }

    public void updateTask(Task task) {
        String uuidString = task.getId().toString();
        ContentValues values = getContentValues(task);

        mDatabase.update(TaskTable.NAME, values,
                TaskTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new TaskCursorWrapper(cursor);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        TaskCursorWrapper cursor = queryTasks(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tasks.add(cursor.getTask());
            cursor.moveToNext();

        }
        cursor.close();

        return tasks;
    }

    public Task getTask(UUID id) {
        TaskCursorWrapper cursor = queryTasks(
                TaskTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

    public void deleteTask(UUID taskId){
        String uuidString = taskId.toString();

        mDatabase.delete(TaskTable.NAME, TaskTable.Cols.UUID + " = ?", new String[] {uuidString});
    }
}
