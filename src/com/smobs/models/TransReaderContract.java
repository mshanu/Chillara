package com.smobs.models;

import android.provider.BaseColumns;

public class TransReaderContract {

    public abstract class TransEntry implements BaseColumns {
    	
        public static final String TABLE_NAME = "transactions";
        public static final String PERSON_NAME_HASH = "person_name_hash";
        public static final String PERSON_NAME = "person_name";
        public static final String TRANS_TYPE = "trans_type";
        public static final String TRANS_DATE = "trans_date";
        public static final String TRANS_AMOUNT = "trans_amount";
        
       

    }

    private TransReaderContract() {
    }
    
    public static String[] allColumns(){
    	return new String[]{TransEntry._ID,TransEntry.PERSON_NAME_HASH,TransEntry.PERSON_NAME,TransEntry.TRANS_TYPE,TransEntry.TRANS_AMOUNT,TransEntry.TRANS_DATE};
    }
}
