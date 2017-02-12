package nksystems.cvmaker.createresume;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import nksystems.cvmaker.CreateResume;
import nksystems.cvmaker.DatabaseObject;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Charmy on 11/02/2017.
 */

public class UploadProfilePhoto extends Fragment {

    //LogCat tag
    private static final String TAG = UploadProfilePhoto.class.getSimpleName();

    //Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static int RESULT_LOAD_IMAGE = 1;

    public static final int MEDIA_TYPE_IMAGE = 1;

    View rootView;
    DatabaseQueries dbQueries;
    private Button btnCapturePicture, btnRemovePhoto;
    private ImageView imgPreview;
    DatabaseObject dbObject;
    SQLiteDatabase theme;
    String currentTheme="";
    String filename="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.resume_photo_upload, container, false);
        dbQueries=new DatabaseQueries(UploadProfilePhoto.this.getContext());
        btnCapturePicture = (Button) rootView.findViewById(R.id.btnCapturePicture);
        btnRemovePhoto=(Button)rootView.findViewById(R.id.btnRemovePhoto);

        dbObject=new DatabaseObject(UploadProfilePhoto.this.getContext());
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();
        currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));

        Drawable myDrawable=btnCapturePicture.getBackground();
        Drawable myDrawable2=btnRemovePhoto.getBackground();
        int color;
        int color2;

        switch (currentTheme.toLowerCase()){
            case "red":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.redColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.redColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "pink":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.pinkColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.pinkColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "purple":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.purpleColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.purpleColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "blue":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.blueColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.blueColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);


                break;
            case "green":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.greenColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.greenColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "yellow":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.yellowColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.yellowColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "orange":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.orangeColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.orangeColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
            case "grey":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.greyColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                myDrawable2.clearColorFilter();
                color2= ContextCompat.getColor(UploadProfilePhoto.this.getContext(),R.color.greyColorAccent);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable2.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                break;
        }
        imgPreview=(ImageView) rootView.findViewById(R.id.imgPreview);


        btnCapturePicture.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPreview.setImageBitmap(null);

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        CreateResume resume = new CreateResume();
        Boolean isEdit = getActivity().getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            String ctNameId = resume.getEditDetails(dbQueries,getActivity().getIntent());
            String fileid = ctNameId.split("~")[1];
            Cursor profilePhotoCursor= dbQueries.selectProfilePhoto(fileid);
            profilePhotoCursor.moveToFirst();
            String profilephotoname="";
            if(profilePhotoCursor.getCount() > 0)
                profilephotoname=profilePhotoCursor.getString(profilePhotoCursor.getColumnIndex("imagename"));

            File sourceFile = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+profilephotoname);
            filename = sourceFile.getName();
            previewImage(filename);
        }
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE){
            if (resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                File sourceFile = new File(picturePath);
                String filename = sourceFile.getName();
                File myDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker");
                if(!myDirectory.exists()){
                    myDirectory.mkdir();
                }
                File destinationFile = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+filename);

                try{
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            destinationFile);

                    int bufferSize;
                    byte[] buffer = new byte[512];
                    while ((bufferSize = fileInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, bufferSize);
                    }
                    fileInputStream.close();
                    fileOutputStream.close();
                }
                catch(Exception e){

                }

                previewImage(filename);
            }
        }
    }

    private void previewImage(String filename){
        imgPreview = (ImageView) rootView.findViewById(R.id.imgPreview);
        imgPreview.setVisibility(View.VISIBLE);
        // bitmap factory
        BitmapFactory.Options options = new BitmapFactory.Options();
        // down sizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;
        final Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+
                File.separator+filename, options);
        imgPreview.setImageBitmap(bitmap);

        if(imgPreview.getDrawable()==null){
            filename="";
        }

        imgPreview.setTag(filename);
        Log.i("filename",filename);
    }
}
