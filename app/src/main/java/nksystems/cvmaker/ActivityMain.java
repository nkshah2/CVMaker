package nksystems.cvmaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import nksystems.cvmaker.adapter.TabPagerAdapter;

public class ActivityMain extends AppCompatActivity {

    private int backCount = 0;
    private Context context;
    //TabHost myTabHost;
    ViewPager viewPager;
    private String[] tabs = {"Home", "Templates", "Feedback"};
    private android.support.v7.app.ActionBar actionBar;
    private TabPagerAdapter mAdapter;
    DatabaseObject dbObject;
    SQLiteDatabase theme;


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


        setContentView(R.layout.activity_activity_main);

        context = ActivityMain.this;

        //Initialization
        viewPager = (ViewPager) findViewById(R.id.activity_main);
        actionBar = getSupportActionBar();
        mAdapter = new TabPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        android.support.v7.app.ActionBar.TabListener tabListener = new android.support.v7.app.ActionBar.TabListener() {

            @Override
            public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }

        };

        //Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(tabListener));
        }


        //Creating menu in the toolbar
        /*@Override
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu,menu);
            return true;
        }*/

        /*@Override
        public boolean onOptionsItemSelected(MenuItem item){
            //Handle item selection
            switch(item.getItemId()){
                case R.id.id_create_group_menu:
                    Intent intent_group = new Intent(HomePage.this, ManageGroup.class);
                    startActivity(intent_group);
                    return true;
                case R.id.id_statistics_menu:
                    Intent intent_statistics = new Intent(HomePage.this, Statistics.class);
                    startActivity(intent_statistics);
                    return true;
                case R.id.id_logout_menu:
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove("logged");
                    editor.commit();
                    Intent intent_logout = new Intent(HomePage.this,Main.class);
                    startActivity(intent_logout);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }*/


        /*myTabHost=(TabHost)findViewById(R.id.tabhost);
        myTabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = myTabHost.newTabSpec("HOME");
        spec.setContent(R.id.home);
        spec.setIndicator("HOME");

        myTabHost.addTab(spec);

        //Tab 2
        spec = myTabHost.newTabSpec("Templates");
        spec.setContent(R.id.templates);
        spec.setIndicator("Templates");
        myTabHost.addTab(spec);

        //Tab 3
        spec = myTabHost.newTabSpec("Statistics");
        spec.setContent(R.id.stats);
        spec.setIndicator("Statistics");
        myTabHost.addTab(spec);*/

        Intent intent = getIntent();
        //Boolean isPDF= intent.getBooleanExtra("isForPDF",false);
        Boolean openPDF = intent.getBooleanExtra("openPDF", false);
        /*if (isPDF){
            File file=(File)intent.getExtras().get("file");
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setDataAndType(Uri.fromFile(file),"application/pdf");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent1);
            finish();
        }*/

        if (openPDF) {
            File file = (File) intent.getExtras().get("file");
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent1);
        }

    }


    @Override
    public void onBackPressed() {
        if (backCount == 0) {
            backCount++;
            Toast.makeText(this, "Press back key again to close application", Toast.LENGTH_SHORT).show();
        } else {
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    public Context getContext() {
        return context;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.theme:
                startActivity(new Intent(ActivityMain.this,activity_settings.class));
                break;
            case R.id.rateus:
                Log.i("ActivityMain","Rate Us");
                break;
            case R.id.removeads:
                Log.i("ActivityMain","Remove Ads");
                break;
            case R.id.help:
                Log.i("ActivityMain","Help");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

