package nksystems.cvmaker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.Arrays;
import java.util.List;

import nksystems.cvmaker.adapter.ThemeRecyclerAdapter;

public class ThemeActivity extends AppCompatActivity {


    DatabaseObject dbObject;
    SQLiteDatabase theme;
    FloatingActionButton fabBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        dbObject=new DatabaseObject(this);
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();
        String currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));

        switch (currentTheme.toLowerCase()){
            case "red":
                setTheme(R.style.redTheme);
                //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redColorPrimary)));
                break;
            case "pink":
                setTheme(R.style.pinkTheme);
                //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pinkColorPrimary)));
                break;
            case "purple":
                setTheme(R.style.purpleTheme);
               // actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purpleColorPrimary)));
                break;
            case "blue":
                setTheme(R.style.blueTheme);
                //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blueColorPrimary)));
                break;
            case "green":
                setTheme(R.style.greenTheme);
                //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.greenColorPrimary)));
                break;
            case "yellow":
                setTheme(R.style.yellowTheme);
                //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yellowColorPrimary)));
                break;
            case "orange":
                setTheme(R.style.orangeTheme);
                //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orangeColorPrimary)));
                break;
            case "grey":
                setTheme(R.style.greyTheme);
                //actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.greyColorPrimary)));
                break;
        }

        setContentView(R.layout.activity_settings);

        fabBack=(FloatingActionButton)findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemeActivity.this, ActivityMain.class);
                finish();
                startActivity(intent);
            }
        });

        GridView gridView = (GridView) findViewById(R.id.gvThemes);
        String[] array = getResources().getStringArray(R.array.themeColors);
        List listColor = Arrays.asList(array);
        ThemeRecyclerAdapter myAdapter = new ThemeRecyclerAdapter(listColor, this);
        gridView.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,ActivityMain.class));
    }
}