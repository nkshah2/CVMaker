package nksystems.cvmaker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StatisticsTab extends Fragment {
    AdView mAdview;
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLScy2LT_uJpAmR1vyR1zxGuAmoMufQQoBuyFqcKtbMmTeYw44Q/formResponse";
    //input element ids found from the live form page
    public static final String NAME_KEY="entry.636248081";
    public static final String EMAIL_KEY="entry.711871259";
    public static final String COMMENT_KEY="entry.186916549";

    private final Context context = StatisticsTab.this.getContext();
    private EditText etEmail;
    private EditText etName;
    private EditText etComment;
    private ProgressBar progressBar;
    Button submit;
    DatabaseObject dbObject;
    SQLiteDatabase theme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbObject=new DatabaseObject(getContext());
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();
        String currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));


        View rootView = inflater.inflate(R.layout.activity_statistics_tab, container, false);
        /*submit=(Button)rootView.findViewById(R.id.btSubmit);*/

        Button sendButton = (Button)rootView.findViewById(R.id.btnSave);
        etEmail = (EditText)rootView.findViewById(R.id.etEmail);
        etName = (EditText)rootView.findViewById(R.id.etName);
        etComment=(EditText)rootView.findViewById(R.id.etComment);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);

        MobileAds.initialize(StatisticsTab.this.getContext(),"ca-app-pub-2342189677319514/4444401382");
        mAdview=(AdView)rootView.findViewById(R.id.homeAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Make sure all the fields are filled with values
                if(TextUtils.isEmpty(etEmail.getText().toString()) ||
                        TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etComment.getText().toString()))
                {
                    Toast.makeText(StatisticsTab.this.getContext(),StatisticsTab.this.getResources().getString(R.string.feedbackAllWarning),Toast.LENGTH_LONG).show();
                    return;
                }
                //Check if a valid email is entered
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches())
                {
                    Toast.makeText(StatisticsTab.this.getContext(),StatisticsTab.this.getResources().getString(R.string.feedbackEmailWarning),Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //Create an object for PostDataTask AsyncTask
                PostDataTask postDataTask = new PostDataTask();

                //execute asynctask
                postDataTask.execute(URL,etEmail.getText().toString(),
                        etName.getText().toString(),etComment.getText().toString());
            }
        });






        Drawable myDrawable=sendButton.getBackground();
        int color;
        switch (currentTheme.toLowerCase()){
            case "red":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.redColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "pink":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.pinkColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "purple":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.purpleColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "blue":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.blueColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "green":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.greenColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "yellow":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.yellowColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "orange":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.orangeColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "grey":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(getContext(),R.color.greyColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
        }

        Log.i("check","stats rootview returned");

        return rootView;
    }


    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String email = contactData[1];
            String name = contactData[2];
            String comment= contactData[3];
            String postBody="";

            try {
                //all values must be URL encoded to make sure that special characters like & | ",etc.
                //do not cause problems
                postBody = NAME_KEY+"=" + URLEncoder.encode(name,"UTF-8") +
                        "&" + EMAIL_KEY + "=" + URLEncoder.encode(email,"UTF-8") +
                        "&" + COMMENT_KEY + "=" + URLEncoder.encode(comment,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result=false;
            }

            /*
            //If you want to use HttpRequest class from http://stackoverflow.com/a/2253280/1261816
            try {
			HttpRequest httpRequest = new HttpRequest();
			httpRequest.sendPost(url, postBody);
		}catch (Exception exception){
			result = false;
		}
            */

            try{
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();
                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                //Send the request
                Response response = client.newCall(request).execute();
            }catch (IOException exception){
                result=false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result){
            //Print Success or failure message accordingly
            Toast.makeText(StatisticsTab.this.getContext(),result?StatisticsTab.this.getResources().getString(R.string.feedbackSuccess):StatisticsTab.this.getResources().getString(R.string.feedbackFailure),Toast.LENGTH_LONG).show();
            startActivity(new Intent(StatisticsTab.this.getContext(),ActivityMain.class));
        }

    }
}



/*import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StatisticsTab extends AppCompatActivity {

    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLScy2LT_uJpAmR1vyR1zxGuAmoMufQQoBuyFqcKtbMmTeYw44Q/formResponse";
    //input element ids found from the live form page
    public static final String NAME_KEY="entry.636248081";
    public static final String EMAIL_KEY="entry.711871259";
    public static final String COMMENT_KEY="entry.186916549";

    private final Context context = this;
    private EditText etEmail;
    private EditText etName;
    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_tab);

        //save the activity in a context variable to be used afterwards
        *//*context =this;*//*

        //Get references to UI elements in the layout
        Button sendButton = (Button)findViewById(R.id.btnSave);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etName = (EditText)findViewById(R.id.etName);
        etComment=(EditText)findViewById(R.id.etComment);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Make sure all the fields are filled with values
                if(TextUtils.isEmpty(etEmail.getText().toString()) ||
                        TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etComment.getText().toString()))
                {
                    Toast.makeText(context,"All fields are mandatory.",Toast.LENGTH_LONG).show();
                    return;
                }
                //Check if a valid email is entered
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches())
                {
                    Toast.makeText(context,"Please enter a valid email.",Toast.LENGTH_LONG).show();
                    return;
                }

                //Create an object for PostDataTask AsyncTask
                PostDataTask postDataTask = new PostDataTask();

                //execute asynctask
                postDataTask.execute(URL,etEmail.getText().toString(),
                        etName.getText().toString(),etComment.getText().toString());
            }
        });
    }

    //AsyncTask to send data as a http POST request
    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String email = contactData[1];
            String name = contactData[2];
            String comment= contactData[3];
            String postBody="";

            try {
                //all values must be URL encoded to make sure that special characters like & | ",etc.
                //do not cause problems
                postBody = NAME_KEY+"=" + URLEncoder.encode(name,"UTF-8") +
                        "&" + EMAIL_KEY + "=" + URLEncoder.encode(email,"UTF-8") +
                        "&" + COMMENT_KEY + "=" + URLEncoder.encode(comment,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result=false;
            }

            *//*
            //If you want to use HttpRequest class from http://stackoverflow.com/a/2253280/1261816
            try {
			HttpRequest httpRequest = new HttpRequest();
			httpRequest.sendPost(url, postBody);
		}catch (Exception exception){
			result = false;
		}
            *//*

            try{
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();
                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                //Send the request
                Response response = client.newCall(request).execute();
            }catch (IOException exception){
                result=false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result){
            //Print Success or failure message accordingly
            Toast.makeText(context,result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
        }

    }
}*/


