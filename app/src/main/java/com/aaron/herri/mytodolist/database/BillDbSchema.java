package com.aaron.herri.mytodolist.database;

/**
 * Created by herri on 10/21/2015.
 */
public class BillDbSchema {
    public static final class BillTable {
        public static final String NAME = "bills";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String PRIORITY = "priority";
            public static final String DATE = "date";
            public static final String PAID = "paid";
        }
    }
}
