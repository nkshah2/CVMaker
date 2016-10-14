package nksystems.cvmaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Charmy on 12/06/2016.
 */
public class DatabaseObject extends MyDatabase {

    SQLiteDatabase db;

    public DatabaseObject(Context context){
        super(context);
        db = getWritableDatabase();
    }

    public SQLiteDatabase getConnection(){
        return db;
    }

    public void closeConnection(){
        db.close();
    }
}
