package nksystems.cvmaker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Main extends AppCompatActivity {


    ActionBar myBar;
    ProgressBar progressBar;
    DatabaseObject dbObject;
    SQLiteDatabase theme;
    RelativeLayout rootMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbObject=new DatabaseObject(this);
        theme=dbObject.getConnection();

        Cursor tables=theme.rawQuery("select * from sqlite_master where type='table'",null);
        tables.moveToFirst();
        boolean isNew=false;
        for(int i=0;i<tables.getCount();i++){
            if(tables.getString(tables.getColumnIndex("tbl_name")).equalsIgnoreCase("database_theme")){
                isNew=true;
            }
            tables.moveToNext();
        }

        if(!isNew){
            theme.execSQL("create table database_theme(current_theme varchar primary key not null)");
            theme.execSQL("insert into database_theme(current_theme) values('green')");
            theme.execSQL("create table adcheck(isEnabled varchar primary key not null)");
            theme.execSQL("insert into adcheck(isEnabled) values('true')");
            isNew=true;
        }

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

        setContentView(R.layout.activity_main);

        rootMain=(RelativeLayout)findViewById(R.id.rootMain);
        rootMain.setBackgroundColor(getResources().getColor(R.color.greyColorPrimary));

        Drawable rootDrawable=rootMain.getBackground();

        switch (currentTheme.toLowerCase()){
            case "red":
                rootDrawable.setColorFilter(getResources().getColor(R.color.redColorPrimary), PorterDuff.Mode.SRC);
                break;
            case "pink":
                rootDrawable.setColorFilter(getResources().getColor(R.color.pinkColorPrimary), PorterDuff.Mode.SRC);
                break;
            case "purple":
                rootDrawable.setColorFilter(getResources().getColor(R.color.purpleColorPrimary), PorterDuff.Mode.SRC);
                break;
            case "blue":
                rootDrawable.setColorFilter(getResources().getColor(R.color.blueColorPrimary), PorterDuff.Mode.SRC);
                break;
            case "green":
                rootDrawable.setColorFilter(getResources().getColor(R.color.greenColorPrimary), PorterDuff.Mode.SRC);
                break;
            case "yellow":
                rootDrawable.setColorFilter(getResources().getColor(R.color.yellowColorPrimary), PorterDuff.Mode.SRC);
                break;
            case "orange":
                rootDrawable.setColorFilter(getResources().getColor(R.color.orangeColorPrimary), PorterDuff.Mode.SRC);
                break;
            case "grey":
                rootDrawable.setColorFilter(getResources().getColor(R.color.greyColorPrimary), PorterDuff.Mode.SRC);
                break;
        }

        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        myBar=getSupportActionBar();
        myBar.hide();

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                startActivity(new Intent(Main.this,ActivityMain.class));
                // Actions to do after 10 seconds
            }
        }, 2000);


    }

    public Context getContext(){
        return this;
    }


}
