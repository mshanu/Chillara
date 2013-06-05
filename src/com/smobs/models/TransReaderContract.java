package com.smobs.models;

import android.provider.BaseColumns;

public class TransReaderContract {

    public abstract class TransEntry implements BaseColumns {

        public static final String TABLE_NAME = "transactions";
        public static final String TRANS_USER_ID = "trans_user_id";
        public static final String TRANS_TYPE = "trans_type";
        public static final String TRANS_DATE = "trans_date";
        public static final String TRANS_AMOUNT = "trans_amount";


    }

    public abstract class TransUser implements BaseColumns {
        public static final String TABLE_NAME = "transaction_user";
        public static final String TRANS_USER_HASH = "user_name_hash";
        public static final String TRANS_USER_NAME = "user_name";
        public static final String TRANS_USER_TOTAL_CREDIT = "user_total_credit";
        public static final String TRANS_USER_TOTAL_DEBIT = "user_total_debit";
    }

    private TransReaderContract() {
    }

    public static String[] allTransEntryColumns() {
        return new String[]{TransEntry._ID, TransEntry.TRANS_USER_ID, TransEntry.TRANS_TYPE, TransEntry.TRANS_AMOUNT, TransEntry.TRANS_DATE};
    }
}
