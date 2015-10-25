package com.aaron.herri.mytodolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.aaron.herri.mytodolist.Bill;

import java.util.Date;
import java.util.UUID;

/**
 * Created by herri on 10/21/2015.
 */
public class BillCursorWrapper extends CursorWrapper {
    public BillCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Bill getBill() {
        String uuidString = getString(getColumnIndex(BillDbSchema.BillTable.Cols.UUID));
        String title = getString(getColumnIndex(BillDbSchema.BillTable.Cols.TITLE));
        String description = getString(getColumnIndex(BillDbSchema.BillTable.Cols.DESCRIPTION));
        // Spinner spinner = getSpinner(getColumnIndex(TaskTable.Cols.PRIORITY));
        long date = getLong(getColumnIndex(BillDbSchema.BillTable.Cols.DATE));
        int isFinished = getInt(getColumnIndex(BillDbSchema.BillTable.Cols.PAID));

        Bill bill = new Bill(UUID.fromString(uuidString));
        bill.setTitle(title);
        bill.setDescription(description);
        // task.setPriority(new Spinner(spinner));
        bill.setDate(new Date(date));
        bill.setPaid(isFinished != 0);

        return bill;
    }
}