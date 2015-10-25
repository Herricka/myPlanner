package com.aaron.herri.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aaron.herri.mytodolist.database.BillBaseHelper;
import com.aaron.herri.mytodolist.database.BillCursorWrapper;
import com.aaron.herri.mytodolist.database.BillDbSchema.BillTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by herri on 10/21/2015.
 */
public class BillLab {
    private static BillLab sBillLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static BillLab get(Context context) {
        if (sBillLab == null) {
            sBillLab = new BillLab(context);
        }
        return sBillLab;
    }

    private BillLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BillBaseHelper(mContext)
                .getWritableDatabase();

    }

    private static ContentValues getContentValues(Bill bill) {
        ContentValues values = new ContentValues();
        values.put(BillTable.Cols.UUID, bill.getId().toString());
        values.put(BillTable.Cols.TITLE, bill.getTitle());
        values.put(BillTable.Cols.DESCRIPTION, bill.getDescription());
        //  values.put(TaskTable.Cols.PRIORITY, task.getPriority());
        values.put(BillTable.Cols.DATE, bill.getDate().getTime());
        values.put(BillTable.Cols.PAID, bill.isPaid() ? 1 : 0);

        return values;
    }

    public void addBill(Bill c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(BillTable.NAME, null, values);
    }

    public void updateBill(Bill bill) {
        String uuidString = bill.getId().toString();
        ContentValues values = getContentValues(bill);

        mDatabase.update(BillTable.NAME, values,
                BillTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private BillCursorWrapper queryBills(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BillTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new BillCursorWrapper(cursor);
    }

    public List<Bill> getBills() {
        List<Bill> bills = new ArrayList<>();

        BillCursorWrapper cursor = queryBills(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            bills.add(cursor.getBill());
            cursor.moveToNext();

        }
        cursor.close();

        return bills;
    }

    public Bill getBill(UUID id) {
        BillCursorWrapper cursor = queryBills(
                BillTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBill();
        } finally {
            cursor.close();
        }
    }

    public void deleteBill(UUID billId){
        String uuidString = billId.toString();

        mDatabase.delete(BillTable.NAME, BillTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

}
