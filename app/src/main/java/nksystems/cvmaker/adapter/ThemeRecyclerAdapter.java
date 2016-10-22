package nksystems.cvmaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nksystems.cvmaker.DatabaseObject;
import nksystems.cvmaker.R;
import nksystems.cvmaker.ThemeActivity;

/**
 * Created by apple on 11/10/16.
 */

public class ThemeRecyclerAdapter extends BaseAdapter {
    private Context mContext;
    List colors;
    SQLiteDatabase db;
    DatabaseObject dbObject;

    public ThemeRecyclerAdapter(List colors, Context context){
        mContext = context;
        this.colors = colors;
        dbObject=new DatabaseObject(context);
        db=dbObject.getConnection();
    }

    public int getCount(){
        return colors.size();
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        TextView textView;

        if(convertView == null){
            textView = new TextView(mContext);
        }
        else{
            textView = (TextView) convertView;
        }
        final String colorname = colors.get(position).toString();
        textView.setHeight(400);
        textView.setBackgroundResource(R.drawable.theme_circle);
        GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme(colorname.toLowerCase().toString());
                ((Activity)mContext).finish();
                mContext.startActivity(new Intent(mContext,ThemeActivity.class));
            }
        });
        String code;
        String[] colorArray=null;
        switch(colorname.toLowerCase()){
            case "red":
                colorArray=mContext.getResources().getStringArray(R.array.redCols);
                break;
            case "pink":
                colorArray = mContext.getResources().getStringArray(R.array.pinkCols);
                break;
            case "purple":
                colorArray = mContext.getResources().getStringArray(R.array.purpleCols);
                break;
            case "blue":
                colorArray = mContext.getResources().getStringArray(R.array.indigoCols);
                break;
            case "green":
                colorArray = mContext.getResources().getStringArray(R.array.greenCols);
                break;
            case "yellow":
                colorArray = mContext.getResources().getStringArray(R.array.yellowCols);
                break;
            case "orange":
                colorArray = mContext.getResources().getStringArray(R.array.orangeCols);
                break;
            case "grey":
                colorArray = mContext.getResources().getStringArray(R.array.greyCols);
                break;
        }

        code=colorArray[0];
        int myColor=Color.parseColor(code);
        drawable.setColor(myColor);
        return textView;
    }

    private void setTheme(String newTheme){

        String updateTheme="update database_theme set current_theme='"+newTheme+"'";
        db.execSQL(updateTheme);

    }
}
