package nksystems.cvmaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itextpdf.xmp.impl.Utils;

import java.util.List;

import nksystems.cvmaker.ActivityMain;
import nksystems.cvmaker.DatabaseObject;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.HomeTab;
import nksystems.cvmaker.R;
import nksystems.cvmaker.activity_settings;

/**
 * Created by apple on 11/10/16.
 */

public class ThemeRecyclerAdapter extends RecyclerView.Adapter<ThemeRecyclerAdapter.ThemeRecyclerHolder> {

    List colors;
    Context context;
    SQLiteDatabase db;
    DatabaseObject dbObject;

    public ThemeRecyclerAdapter(List colors, Context context){
        this.colors=colors;
        this.context=context;
        dbObject=new DatabaseObject(context);
        db=dbObject.getConnection();

    }

    @Override
    public ThemeRecyclerAdapter.ThemeRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_theme_list_item,parent,false);
        ThemeRecyclerHolder rh=new ThemeRecyclerHolder(item);
        return rh;
    }

    @Override
    public void onBindViewHolder(ThemeRecyclerHolder holder, int position) {

        String color= colors.get(position).toString();
        holder.colorName.setText(color);
        Log.i("RecyclerView",""+colors.size());
        switch (color.toLowerCase()){
            case "red":
                String[] colorArray=context.getResources().getStringArray(R.array.redCols);
                String code=colorArray[0];
                int myColor=Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
            case "pink":
                colorArray = context.getResources().getStringArray(R.array.pinkCols);
                code = colorArray[0];
                myColor = Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
            case "purple":
                colorArray = context.getResources().getStringArray(R.array.purpleCols);
                code = colorArray[0];
                myColor = Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
            case "blue":
                colorArray = context.getResources().getStringArray(R.array.indigoCols);
                code = colorArray[0];
                myColor = Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
            case "green":
                colorArray = context.getResources().getStringArray(R.array.greenCols);
                code = colorArray[0];
                myColor = Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
            case "yellow":
                colorArray = context.getResources().getStringArray(R.array.yellowCols);
                code = colorArray[0];
                myColor = Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
            case "orange":
                colorArray = context.getResources().getStringArray(R.array.orangeCols);
                code = colorArray[0];
                myColor = Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
            case "grey":
                colorArray = context.getResources().getStringArray(R.array.greyCols);
                code = colorArray[0];
                myColor = Color.parseColor(code);
                holder.colorName.setBackgroundColor(myColor);
                break;
        }




    }



    @Override
    public int getItemCount() {
        return colors.size();
    }


    public class ThemeRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView colorName;
        TextView tempText;

        public ThemeRecyclerHolder(View itemView) {
            super(itemView);

            colorName=(TextView)itemView.findViewById(R.id.colorName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            tempText=(TextView)v.findViewById(R.id.colorName);
            String color=tempText.getText().toString();
            switch (color.toLowerCase()){
                case "red":
                    setTheme("red");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));

                    break;
                case "pink":
                    setTheme("pink");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));
                    break;
                case "purple":
                    setTheme("purple");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));
                    break;
                case "blue":
                    setTheme("blue");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));
                    break;
                case "green":
                    setTheme("green");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));
                    break;
                case "yellow":
                    setTheme("yellow");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));
                    break;
                case "orange":
                    setTheme("orange");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));
                    break;
                case "grey":
                    setTheme("grey");
                    ((Activity)context).finish();
                    context.startActivity(new Intent(context,activity_settings.class));

                    break;
            }
        }
    }

    private void setTheme(String newTheme){

        String updateTheme="update database_theme set current_theme='"+newTheme+"'";
        db.execSQL(updateTheme);

    }
}
