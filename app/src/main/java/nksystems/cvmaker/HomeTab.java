package nksystems.cvmaker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nksystems.cvmaker.adapter.RecyclerViewAdapter;

import static nksystems.cvmaker.R.id.rootView;

public class HomeTab extends Fragment {
    FloatingActionButton myFab;
    RecyclerView rv;

    AdView mAdview;
    TextView instruction;
    ListView listView;
    private List<String> fileList;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    DatabaseQueries dbQueries;
    DatabaseObject dbObject;
    SQLiteDatabase theme;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("Test","This should show in git again");

        dbObject=new DatabaseObject(HomeTab.this.getContext());
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();
        String currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));




        int permission = ActivityCompat.checkSelfPermission(HomeTab.this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeTab.this.getActivity() , PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
        final View rootView = inflater.inflate(R.layout.activity_home_tab, container, false);


        rv=(RecyclerView)rootView.findViewById(R.id.rvFilenames);
        MobileAds.initialize(HomeTab.this.getContext(),"ca-app-pub-2342189677319514/1490934981");
//        mAdview=(AdView)rootView.findViewById(R.id.homeAdview);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdview.loadAd(adRequest);


        myFab=(FloatingActionButton)rootView.findViewById(R.id.fab);
        Drawable myDrawable=myFab.getBackground();
        int color;

        switch (currentTheme.toLowerCase()){
            case "red":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.redColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "pink":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.pinkColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "purple":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.purpleColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "blue":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.blueColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "green":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.greenColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "yellow":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.yellowColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "orange":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.orangeColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "grey":
                myDrawable.clearColorFilter();
                color=ContextCompat.getColor(getContext(),R.color.greyColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
        }

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(HomeTab.this.getActivity(),CreateResume.class).putExtra("forward",false));

            }
        });




        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fileList = fetchPdfFiles();
        if(!fileList.isEmpty()){
//            ArrayAdapter adapter = new ArrayAdapter<>(HomeTab.this.getContext(),android.R.layout.simple_list_item_1,
//                    android.R.id.text1,fileList);

            listView = (ListView) view.findViewById(R.id.filenames);
            instruction=(TextView)view.findViewById(R.id.tvInstruction);
            instruction.setVisibility(View.GONE);




            listView.setAdapter(new CustomAdapter(HomeTab.this.getContext(),fileList));
//            listView.setAdapter(adapter);
        }else{
            instruction=(TextView)view.findViewById(R.id.tvInstruction);
            instruction.setVisibility(View.VISIBLE);
        }



    }

    private List<String> fetchPdfFiles(){
        dbQueries = new DatabaseQueries(getActivity().getApplicationContext());
        final Cursor myCursor=dbQueries.getFileList();
        RecyclerView.LayoutManager myManager= new LinearLayoutManager(this.getContext());

        final RecyclerViewAdapter adapter= new RecyclerViewAdapter(myCursor,HomeTab.this.getContext());

        ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

//
        };


        ItemTouchHelper ith= new ItemTouchHelper(simpleCallback);
        ith.attachToRecyclerView(rv);


        rv.setLayoutManager(myManager);
        rv.setAdapter(adapter);
        myCursor.moveToFirst();
        String filename;
        List<String> list = new ArrayList<>();
        

        while(myCursor.isAfterLast() == false){
            filename = myCursor.getString(myCursor.getColumnIndex("filename"));
            //filename = filename.split("~")[0];
            //filename = filename.substring(0,filename.lastIndexOf("."));
            list.add(filename);
            myCursor.moveToNext();
        }

        myCursor.moveToFirst();
        return list;
    }


}
