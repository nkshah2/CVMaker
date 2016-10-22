package nksystems.cvmaker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import nksystems.cvmaker.adapter.ThemeRecyclerAdapter;

public class
activity_settings extends AppCompatActivity {


    DatabaseObject dbObject;
    SQLiteDatabase theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Test","commit test");

        dbObject=new DatabaseObject(this);
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();
        String currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));

        switch (currentTheme.toLowerCase()){
            case "red":
                setTheme(R.style.redTheme);
                break;
            case "pink":
                setTheme(R.style.pinkTheme);
                break;
            case "purple":
                setTheme(R.style.purpleTheme);
                break;
            case "blue":
                setTheme(R.style.blueTheme);
                break;
            case "green":
                setTheme(R.style.greenTheme);
                break;
            case "yellow":
                setTheme(R.style.yellowTheme);
                break;
            case "orange":
                setTheme(R.style.orangeTheme);
                break;
            case "grey":
                setTheme(R.style.greyTheme);
                break;
        }

        setContentView(R.layout.activity_settings);



        RecyclerView list=(RecyclerView)findViewById(R.id.themes);
        String[] array=getResources().getStringArray(R.array.themeColors);
        List listColor= Arrays.asList(array);
        ThemeRecyclerAdapter myAdapter=new ThemeRecyclerAdapter(listColor,this);
        RecyclerView.LayoutManager manager= new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,ActivityMain.class));
    }
}
