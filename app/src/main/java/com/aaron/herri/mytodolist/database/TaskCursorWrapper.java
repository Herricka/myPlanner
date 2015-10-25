package com.aaron.herri.mytodolist.database;


import android.database.Cursor;
import android.database.CursorWrapper;
import android.widget.Spinner;

import com.aaron.herri.mytodolist.Task;

import java.util.Date;
import java.util.UUID;

import com.aaron.herri.mytodolist.database.TaskDbSchema.TaskTable;

/**
 * Created by herri on 10/12/2015.
 */
public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskTable.Cols.UUID));
        String title = getString(getColumnIndex(TaskTable.Cols.TITLE));
        String description = getString(getColumnIndex(TaskTable.Cols.DESCRIPTION));
       // Spinner spinner = getSpinner(getColumnIndex(TaskTable.Cols.PRIORITY));
        long date = getLong(getColumnIndex(TaskTable.Cols.DATE));
        int isFinished = getInt(getColumnIndex(TaskTable.Cols.FINISHED));

        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setDescription(description);
       // task.setPriority(new Spinner(spinner));
        task.setDate(new Date(date));
        task.setFinished(isFinished != 0);

        return task;
    }




}
