package nksystems.cvmaker;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Charmy on 12/06/2016.
 */
public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "cvmaker.sqlite";
    private static final int DATABASE_VERSION = 4;

    public MyDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
