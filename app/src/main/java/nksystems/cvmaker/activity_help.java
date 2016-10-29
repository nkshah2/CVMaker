package nksystems.cvmaker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_help extends AppCompatActivity {
    DatabaseObject dbObject;
    SQLiteDatabase theme;
    Button menuButton;

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,ActivityMain.class));
    }

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


        setContentView(R.layout.activity_help);

        menuButton=(Button)findViewById(R.id.menuButton);


        Drawable myDrawable=menuButton.getBackground();
        int color;

        switch (currentTheme.toLowerCase()){
            case "red":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.redColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "pink":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(this,R.color.pinkColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "purple":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(this,R.color.purpleColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "blue":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(this,R.color.blueColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "green":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(this,R.color.greenColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "yellow":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(this,R.color.yellowColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "orange":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(this,R.color.orangeColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "grey":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(this,R.color.greyColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
        }

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(activity_help.this,ActivityMain.class));
            }
        });


    }
}
