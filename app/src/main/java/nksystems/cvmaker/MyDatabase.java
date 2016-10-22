package nksystems.cvmaker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Charmy on 12/06/2016.
 */
public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "cvmaker.sqlite";
    private static int DATABASE_VERSION = 1;

    public MyDatabase(Context context){


        super(context, DATABASE_NAME, null, DATABASE_VERSION);





        setForcedUpgrade(DATABASE_VERSION);
    }

    public void setDatabaseVersionVar(int versionVar){

        DATABASE_VERSION=versionVar;

    }
}
