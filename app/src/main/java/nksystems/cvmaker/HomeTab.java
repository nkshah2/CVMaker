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
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.MaterialShowcaseDrawer;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nksystems.cvmaker.adapter.RecyclerViewAdapter;

import static nksystems.cvmaker.R.id.file;
import static nksystems.cvmaker.R.id.rootView;

public class HomeTab extends Fragment {
    FloatingActionButton myFab;
    RecyclerView rv;
String currentTheme;
    AdView mAdview;
    TextView instruction;
    RelativeLayout root;
    boolean isUndo=false;
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


        dbObject=new DatabaseObject(HomeTab.this.getContext());
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();



        currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));




        int permission = ActivityCompat.checkSelfPermission(HomeTab.this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeTab.this.getActivity() , PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
        final View rootView = inflater.inflate(R.layout.activity_home_tab, container, false);


        rv=(RecyclerView)rootView.findViewById(R.id.rvFilenames);
        MobileAds.initialize(HomeTab.this.getContext(),"ca-app-pub-2342189677319514/1490934981");
        mAdview=(AdView)rootView.findViewById(R.id.homeAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);


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


        Log.i("check","hometab rootview returned");

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fileList = fetchPdfFiles();
        if(!fileList.isEmpty()){
//            ArrayAdapter adapter = new ArrayAdapter<>(HomeTab.this.getContext(),android.R.layout.simple_list_item_1,
//                    android.R.id.text1,fileList);

            listView = (ListView) view.findViewById(R.id.filenames);
            instruction=(TextView)view.findViewById(R.id.tvInstruction);
            root=(RelativeLayout)view.findViewById(R.id.rootviewHomeTab);
            instruction.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager myManager= new LinearLayoutManager(this.getContext());

            final RecyclerViewAdapter adapter= new RecyclerViewAdapter(fileList,HomeTab.this.getContext());

            ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                    final int position= viewHolder.getAdapterPosition();

                    String info=fileList.get(position);
                    dbQueries= new DatabaseQueries(getContext().getApplicationContext());
                    String[] infos= info.split("~");
                    final String filename=infos[0];


                    final Snackbar mySnackBar= Snackbar.make(root,HomeTab.this.getResources().getString(R.string.deleteWarning),Snackbar.LENGTH_LONG);
                    mySnackBar.setAction(HomeTab.this.getResources().getString(R.string.deleteSnackbarButton), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbQueries.deleteFileFromDb(filename);


                            fileList.remove(position);
                            adapter.notifyDataSetChanged();

                            mySnackBar.dismiss();



                        }
                    });

                    mySnackBar.setCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            super.onDismissed(snackbar, event);
                            getActivity().finish();
                            startActivity(new Intent(getContext(),ActivityMain.class));
                        }
                    });


                    mySnackBar.setActionTextColor(getResources().getColor(R.color.redColorPrimary));
                    mySnackBar.show();







                }

//
            };


            ItemTouchHelper ith= new ItemTouchHelper(simpleCallback);
            ith.attachToRecyclerView(rv);


            rv.setLayoutManager(myManager);
            rv.setAdapter(adapter);



            listView.setAdapter(new CustomAdapter(HomeTab.this.getContext(),fileList));
//            listView.setAdapter(adapter);
        }else{
            instruction=(TextView)view.findViewById(R.id.tvInstruction);
            instruction.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }




        Cursor test=theme.rawQuery("select * from tutorial",null);
        test.moveToFirst();
        String isMenuNeeded=test.getString(test.getColumnIndex("menubutton"));
        final String isCreateNeeded=test.getString(test.getColumnIndex("createresume"));
        final String isFileListNeeded=test.getString(test.getColumnIndex("filelist"));

        TextPaint myPaint=new TextPaint();
        myPaint.bgColor=getResources().getColor(R.color.pureWhite);

        final Button myButton=new Button(getContext());
        myButton.setBackgroundColor(Color.TRANSPARENT);
        myButton.setClickable(false);
        myButton.setText("");
        myButton.setEnabled(false);
        myButton.setVisibility(View.GONE);

        Display display= getActivity().getWindowManager().getDefaultDisplay();
        final Point point= new Point();
        display.getSize(point);



        if(isMenuNeeded.equalsIgnoreCase("yes")){
            ShowcaseView myShowcase= new ShowcaseView.Builder(getActivity())
                    .setTarget(new PointTarget(point.x,point.y/20))
                    .useDecorViewAsParent()
                    .setContentTitle(HomeTab.this.getResources().getString(R.string.tutorialOptionsMenuTitle))
                    .setContentText(HomeTab.this.getResources().getString(R.string.tutorialOptionsMenuContent))
                    .hideOnTouchOutside()
                    .setStyle(R.style.ShowCaseViewGrey)
                    .setContentTitlePaint(myPaint)
                    .setContentTextPaint(myPaint)
                    .replaceEndButton(myButton)
                    .setShowcaseEventListener(new OnShowcaseEventListener() {
                        @Override
                        public void onShowcaseViewHide(ShowcaseView showcaseView) {

                            theme.execSQL("update tutorial set menubutton='no'");

                            if(!isCreateNeeded.equalsIgnoreCase("yes")){

                            }else{



                                final Button myButton=new Button(getContext());
                                myButton.setBackgroundColor(Color.TRANSPARENT);
                                myButton.setClickable(false);
                                myButton.setText("");
                                myButton.setEnabled(false);
                                myButton.setVisibility(View.GONE);

                                ShowcaseView myCreateShowcase= new ShowcaseView.Builder(getActivity())
                                        .setTarget(new ViewTarget(view.findViewById(R.id.fab)))
                                        .setContentTitle(HomeTab.this.getResources().getString(R.string.tutorialFABTitle))
                                        .setContentText(HomeTab.this.getResources().getString(R.string.tutorialFABContent))
                                        .hideOnTouchOutside()
                                        .setStyle(R.style.ShowCaseViewGrey)
                                        .replaceEndButton(myButton)
                                        .setShowcaseEventListener(new OnShowcaseEventListener() {
                                            @Override
                                            public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                                theme.execSQL("update tutorial set createresume='no'");

                                                if(!fileList.isEmpty()&&isFileListNeeded.equalsIgnoreCase("yes")){
                                                    View firstItem=rv.getLayoutManager().findViewByPosition(0);

                                                    final Button myButton=new Button(getContext());
                                                    myButton.setBackgroundColor(Color.TRANSPARENT);
                                                    myButton.setClickable(false);
                                                    myButton.setText("");
                                                    myButton.setEnabled(false);
                                                    myButton.setVisibility(View.GONE);

                                                    ShowcaseView myListShowcase= new ShowcaseView.Builder(getActivity())
                                                            .setTarget(new PointTarget(point.x / 4, point.y / 4))
                                                            .useDecorViewAsParent()
                                                            .setContentTitle(HomeTab.this.getResources().getString(R.string.tutorialInteractTitle))
                                                            .setContentText(HomeTab.this.getResources().getString(R.string.tutorialInteractContent))
                                                            .hideOnTouchOutside()
                                                            .withMaterialShowcase()
                                                            .setStyle(R.style.ShowCaseViewGrey)
                                                            .replaceEndButton(myButton)
                                                            .setShowcaseEventListener(new OnShowcaseEventListener() {
                                                                @Override
                                                                public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                                                    theme.execSQL("update tutorial set filelist='no'");
                                                                }

                                                                @Override
                                                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                                                                }

                                                                @Override
                                                                public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                                                }

                                                                @Override
                                                                public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                                                }
                                                            }).build();


                                                }

                                            }

                                            @Override
                                            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                                            }

                                            @Override
                                            public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                            }

                                            @Override
                                            public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                            }
                                        }).build();


                            }

                        }

                        @Override
                        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                        }

                        @Override
                        public void onShowcaseViewShow(ShowcaseView showcaseView) {

                        }

                        @Override
                        public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                        }
                    }).build();
//

            /*switch (currentTheme.toLowerCase()){
                case "red":
                    myShowcase.setStyle(R.style.ShowCaseViewRed);
                    myShowcase.build();
                    break;
                case "pink":
                    myShowcase.setStyle(R.style.ShowCaseViewPink);
                    myShowcase.build();
                    break;
                case "purple":
                    myShowcase.setStyle(R.style.ShowCaseViewPurple);
                    myShowcase.build();
                    break;
                case "blue":
                    myShowcase.setStyle(R.style.ShowCaseViewBlue);
                    myShowcase.build();
                    break;
                case "green":
                    myShowcase.setStyle(R.style.ShowCaseViewGreen);
                    myShowcase.build();
                    break;
                case "yellow":
                    myShowcase.setStyle(R.style.ShowCaseViewYellow);
                    myShowcase.build();
                    break;
                case "orange":
                    myShowcase.setStyle(R.style.ShowCaseViewOrange);
                    myShowcase.build();
                    break;
                case "grey":
                    myShowcase.setStyle(R.style.ShowCaseViewGrey);
                    myShowcase.build();
                    break;*/


        }else {
            if (!fileList.isEmpty() && isFileListNeeded.equalsIgnoreCase("yes")) {
                View firstItem = rv.getLayoutManager().findViewByPosition(0);

                final Button myButton2 = new Button(getContext());
                myButton2.setBackgroundColor(Color.TRANSPARENT);
                myButton2.setClickable(false);
                myButton2.setText("");
                myButton2.setEnabled(false);
                myButton2.setVisibility(View.GONE);


                ShowcaseView myListShowcase = new ShowcaseView.Builder(getActivity())
                        .setTarget(new PointTarget(point.x / 4, point.y / 4))
                        .useDecorViewAsParent()
                        .setContentTitle(HomeTab.this.getResources().getString(R.string.tutorialInteractTitle))
                        .setContentText(HomeTab.this.getResources().getString(R.string.tutorialInteractContent))
                        .hideOnTouchOutside()
                        .setStyle(R.style.ShowCaseViewGrey)
                        .withMaterialShowcase()
                        .replaceEndButton(myButton2)
                        .setShowcaseEventListener(new OnShowcaseEventListener() {
                            @Override
                            public void onShowcaseViewHide(ShowcaseView showcaseView) {

                                theme.execSQL("update tutorial set filelist='no'");
                            }

                            @Override
                            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                            }

                            @Override
                            public void onShowcaseViewShow(ShowcaseView showcaseView) {

                            }

                            @Override
                            public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                            }
                        }).build();


            }
        }


    }

    private List<String> fetchPdfFiles(){
        dbQueries = new DatabaseQueries(getActivity().getApplicationContext());
        final Cursor myCursor=dbQueries.getFileList();




        myCursor.moveToFirst();
        String filename;
        List<String> list = new ArrayList<>();
        

        while(myCursor.isAfterLast() == false){
            filename = myCursor.getString(myCursor.getColumnIndex("filename"));
            //filename = filename.split("~")[0];
            //filename = filename.substring(0,filename.lastIndexOf("."));
            if(!filename.equalsIgnoreCase("")) {
                list.add(filename);
            }
            myCursor.moveToNext();
        }

        myCursor.moveToFirst();
        return list;
    }




}
